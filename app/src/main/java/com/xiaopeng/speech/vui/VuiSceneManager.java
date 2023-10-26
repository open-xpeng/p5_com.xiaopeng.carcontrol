package com.xiaopeng.speech.vui;

import android.app.ActivityManager;
import android.app.Application;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import android.widget.ListView;
import android.widget.ScrollView;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.google.gson.Gson;
import com.xiaopeng.carcontrol.carmanager.controller.IScenarioController;
import com.xiaopeng.carcontrol.speech.VuiManager;
import com.xiaopeng.lib.apirouter.ApiRouter;
import com.xiaopeng.lludancemanager.Constant;
import com.xiaopeng.speech.apirouter.Utils;
import com.xiaopeng.speech.protocol.bean.recommend.RecommendBean;
import com.xiaopeng.speech.protocol.event.VuiEvent;
import com.xiaopeng.speech.vui.cache.VuiDisplayLocationInfoCache;
import com.xiaopeng.speech.vui.cache.VuiSceneBuildCache;
import com.xiaopeng.speech.vui.cache.VuiSceneCache;
import com.xiaopeng.speech.vui.cache.VuiSceneCacheFactory;
import com.xiaopeng.speech.vui.cache.VuiSceneRemoveCache;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import com.xiaopeng.speech.vui.listener.IVuiEventListener;
import com.xiaopeng.speech.vui.listener.IXpVuiSceneListener;
import com.xiaopeng.speech.vui.model.VuiEventInfo;
import com.xiaopeng.speech.vui.model.VuiFeedback;
import com.xiaopeng.speech.vui.model.VuiScene;
import com.xiaopeng.speech.vui.model.VuiSceneInfo;
import com.xiaopeng.speech.vui.model.VuiSceneState;
import com.xiaopeng.speech.vui.utils.LogUtils;
import com.xiaopeng.speech.vui.utils.ResourceUtil;
import com.xiaopeng.speech.vui.utils.VuiUtils;
import com.xiaopeng.vui.commons.IVuiElement;
import com.xiaopeng.vui.commons.IVuiElementChangedListener;
import com.xiaopeng.vui.commons.IVuiSceneListener;
import com.xiaopeng.vui.commons.VuiAction;
import com.xiaopeng.vui.commons.VuiElementType;
import com.xiaopeng.vui.commons.VuiFeedbackType;
import com.xiaopeng.vui.commons.model.VuiElement;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class VuiSceneManager {
    private static int SEND_UPLOAD_MESSAGE = 1;
    public static final int TYPE_ADD = 2;
    public static final int TYPE_BUILD = 0;
    public static final int TYPE_DISPLAY_LOCATION = 5;
    public static final int TYPE_REMOVE = 3;
    public static final int TYPE_UPDATE = 1;
    public static final int TYPE_UPDATEATTR = 4;
    private static final int VUI_RETRY_MAX_COUNT = 3;
    private static final String VUI_SCENE_AUTHORITY = "com.xiaopeng.speech.vuiscene";
    private static final int VUI_UPDATE_FAILED_ERRO_CODE = -500;
    private final String TAG;
    Map<String, Integer> feedbackInfo;
    private String mActiveSceneId;
    private Handler mApiRouterHandler;
    private HandlerThread mApiRouterThread;
    private Binder mBinder;
    private Context mContext;
    private Handler mHandler;
    private boolean mIsInSpeech;
    private Handler mLocalVuiRouterHandler;
    private HandlerThread mLocalVuiThread;
    private String mObserver;
    private String mPackageName;
    private String mPackageVersion;
    private String mProcessName;
    private VuiBroadCastReceiver mReceiver;
    private HandlerThread mThread;
    private ConcurrentHashMap<String, VuiSceneInfo> mVuiSceneInfoMap;
    private ConcurrentHashMap<String, VuiSceneInfo> mVuiSubSceneInfoMap;
    private List<String> sceneIds;
    private VuiEngineImpl vuiEngine;
    private static final Uri VUI_SCENE_URI = Uri.parse("content://com.xiaopeng.speech.vuiscene/scene");
    private static final Uri VUI_SCENE_DELETE_URI = Uri.parse("content://com.xiaopeng.speech.vuiscene/scene/delete/");
    private static int REMOVE_FEED_BACK = 2;

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasProcessFeature() {
        return true;
    }

    public void setInSpeech(boolean z) {
        this.mIsInSpeech = z;
        if (z) {
            sendSceneData(null);
        }
    }

    public void setEngine(VuiEngineImpl vuiEngineImpl) {
        this.vuiEngine = vuiEngineImpl;
    }

    public boolean isInSpeech() {
        return this.mIsInSpeech;
    }

    private VuiSceneManager() {
        this.TAG = "VuiSceneManager";
        this.mIsInSpeech = false;
        this.mBinder = null;
        this.mReceiver = null;
        this.feedbackInfo = new HashMap();
        this.mProcessName = null;
        this.sceneIds = new ArrayList();
        this.mVuiSceneInfoMap = new ConcurrentHashMap<>();
        this.mVuiSubSceneInfoMap = new ConcurrentHashMap<>();
        lazyInitThread();
    }

    public static final VuiSceneManager instance() {
        return Holder.Instance;
    }

    public void subscribe(String str) {
        if (!Utils.isCorrectObserver(this.mPackageName, str)) {
            LogUtils.e("VuiSceneManager", "注册observer不合法,observer是app的包名加observer的类名组成");
            return;
        }
        this.mObserver = str;
        if (VuiUtils.canUseVuiFeature()) {
            subscribe(false);
            sendBroadCast();
            registerReceiver();
        }
    }

    private void lazyInitThread() {
        if (this.mThread == null) {
            HandlerThread handlerThread = new HandlerThread("VuiSceneManager-Thread");
            this.mThread = handlerThread;
            handlerThread.start();
            this.mHandler = new VuiSceneHandler(this.mThread.getLooper());
        }
        if (this.mApiRouterThread == null) {
            HandlerThread handlerThread2 = new HandlerThread("VuiSceneManager-Apirouter-Thread");
            this.mApiRouterThread = handlerThread2;
            handlerThread2.start();
            this.mApiRouterHandler = new Handler(this.mApiRouterThread.getLooper());
        }
        if (this.mLocalVuiThread == null) {
            HandlerThread handlerThread3 = new HandlerThread("VuiSceneManager-LocalVui-Thread");
            this.mLocalVuiThread = handlerThread3;
            handlerThread3.start();
            this.mLocalVuiRouterHandler = new Handler(this.mLocalVuiThread.getLooper());
        }
    }

    public void reSetBinder() {
        this.mBinder = null;
    }

    public void setFeatureState(boolean z) {
        String str;
        try {
            if (VuiUtils.isFeatureDisabled() != z) {
                if (z) {
                    VuiSceneBuildCache vuiSceneBuildCache = (VuiSceneBuildCache) VuiSceneCacheFactory.instance().getSceneCache(VuiSceneCacheFactory.CacheType.BUILD.getType());
                    if (VuiEngineImpl.mActiveSceneIds.size() > 0 && (str = this.mPackageName) != null && str.equals(getTopRunningPackageName())) {
                        handlerActiveScene(VuiEngineImpl.mActiveSceneIds.get(VuiConstants.SCREEN_DISPLAY_LF), VuiConstants.SCREEN_DISPLAY_LF);
                        handlerActiveScene(VuiEngineImpl.mActiveSceneIds.get(VuiConstants.SCREEN_DISPLAY_RF), VuiConstants.SCREEN_DISPLAY_RF);
                    }
                    if (VuiEngineImpl.mLeftPopPanelStack.size() > 0) {
                        Iterator<String> it = VuiEngineImpl.mLeftPopPanelStack.iterator();
                        while (it.hasNext()) {
                            handlerActiveScene(it.next(), VuiConstants.SCREEN_DISPLAY_LF);
                        }
                    }
                    if (VuiEngineImpl.mRightPopPanelStack.size() > 0) {
                        Iterator<String> it2 = VuiEngineImpl.mRightPopPanelStack.iterator();
                        while (it2.hasNext()) {
                            handlerActiveScene(it2.next(), VuiConstants.SCREEN_DISPLAY_RF);
                        }
                        return;
                    }
                    return;
                }
                handleAllSceneCache(true);
                handleSceneDataInfo();
            }
        } catch (Exception unused) {
        }
    }

    private void handlerActiveScene(String str, String str2) {
        IVuiSceneListener vuiSceneListener;
        VuiSceneBuildCache vuiSceneBuildCache = (VuiSceneBuildCache) VuiSceneCacheFactory.instance().getSceneCache(VuiSceneCacheFactory.CacheType.BUILD.getType());
        if (TextUtils.isEmpty(str)) {
            return;
        }
        List<VuiElement> cache = vuiSceneBuildCache.getCache(str);
        if ((cache == null || cache.isEmpty()) && (vuiSceneListener = instance().getVuiSceneListener(str)) != null) {
            vuiSceneListener.onBuildScene();
        }
        enterScene(str, this.mPackageName, true, str2);
    }

    public void storeFeedbackInfo(int i, String str, String str2) {
        LogUtils.logInfo("VuiSceneManager", "storeFeedbackInfo:" + str2 + ",soundArea:" + i);
        this.feedbackInfo.put(str2, Integer.valueOf(i));
        Message obtainMessage = this.mHandler.obtainMessage();
        obtainMessage.what = REMOVE_FEED_BACK;
        obtainMessage.obj = str2;
        this.mHandler.sendMessageDelayed(obtainMessage, 1000L);
    }

    public synchronized void insertVuiProviderWhenDeath() {
        LogUtils.d("VuiSceneManager", "insertVuiProviderWhenDeath");
        String str = VuiEngineImpl.mActiveSceneIds.get(VuiConstants.SCREEN_DISPLAY_LF);
        String str2 = VuiEngineImpl.mActiveSceneIds.get(VuiConstants.SCREEN_DISPLAY_RF);
        if (!TextUtils.isEmpty(str)) {
            reBuildSceneToVuiProvider(str);
        }
        if (!TextUtils.isEmpty(str2)) {
            reBuildSceneToVuiProvider(str2);
        }
        if (VuiEngineImpl.mLeftPopPanelStack.size() > 0) {
            Iterator<String> it = VuiEngineImpl.mLeftPopPanelStack.iterator();
            while (it.hasNext()) {
                String next = it.next();
                if (!TextUtils.isEmpty(next)) {
                    reBuildSceneToVuiProvider(next);
                }
            }
        }
        if (VuiEngineImpl.mRightPopPanelStack.size() > 0) {
            Iterator<String> it2 = VuiEngineImpl.mRightPopPanelStack.iterator();
            while (it2.hasNext()) {
                String next2 = it2.next();
                if (!TextUtils.isEmpty(next2)) {
                    reBuildSceneToVuiProvider(next2);
                }
            }
        }
    }

    public synchronized void reBuildSceneToVuiProvider(final String str) {
        try {
            this.mHandler.post(new Runnable() { // from class: com.xiaopeng.speech.vui.VuiSceneManager.1
                @Override // java.lang.Runnable
                public void run() {
                    List<VuiElement> cache;
                    VuiScene build = new VuiScene.Builder().sceneId(str).appVersion(VuiSceneManager.this.mPackageVersion).packageName(VuiSceneManager.this.mPackageName).timestamp(System.currentTimeMillis()).build();
                    VuiSceneCache sceneCache = VuiSceneCacheFactory.instance().getSceneCache(VuiSceneCacheFactory.CacheType.BUILD.getType());
                    if (sceneCache == null || (cache = sceneCache.getCache(str)) == null || cache.isEmpty()) {
                        return;
                    }
                    build.setElements(sceneCache.getCache(str));
                    VuiSceneManager.this.sendSceneData(0, false, build, false);
                }
            });
        } catch (Throwable unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void insertVuiSceneToProvider(String str, String str2, String str3) {
        char c;
        LogUtils.logInfo("VuiSceneManager", "insertVuiSceneToProvider:sceneId = " + str);
        ContentResolver contentResolver = this.mContext.getContentResolver();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("scene_id", str);
            contentValues.put("scene_info", str2);
            contentValues.put("scene_type", str3);
            Uri insert = contentResolver.insert(VUI_SCENE_URI, contentValues);
            long parseId = ContentUris.parseId(insert);
            if (insert != null && parseId > 0) {
                LogUtils.d("VuiSceneManager", "insertVuiSceneToProvider" + str3 + IScenarioController.RET_SUCCESS);
                contentResolver.notifyChange(insert, null);
                return;
            }
            int hashCode = str3.hashCode();
            boolean z = false;
            if (hashCode == -838846263) {
                if (str3.equals("update")) {
                    c = 1;
                }
                c = 65535;
            } else if (hashCode != 94094958) {
                if (hashCode == 1671764162 && str3.equals("display")) {
                    c = 2;
                }
                c = 65535;
            } else {
                if (str3.equals("build")) {
                    c = 0;
                }
                c = 65535;
            }
            if (c == 0) {
                LogUtils.d("VuiSceneManager", "insertVuiSceneToProvider build retryResult" + retryInsertVuiProvider(contentValues));
            } else if ((c == 1 || c == 2) && parseId == -500) {
                VuiScene build = new VuiScene.Builder().sceneId(str).appVersion(this.mPackageVersion).packageName(this.mPackageName).timestamp(System.currentTimeMillis()).build();
                VuiSceneCache sceneCache = VuiSceneCacheFactory.instance().getSceneCache(VuiSceneCacheFactory.CacheType.BUILD.getType());
                if (sceneCache != null) {
                    List<VuiElement> cache = sceneCache.getCache(str);
                    if (cache != null && !cache.isEmpty()) {
                        build.setElements(sceneCache.getCache(str));
                        String vuiSceneConvertToString = VuiUtils.vuiSceneConvertToString(build);
                        ContentValues contentValues2 = new ContentValues();
                        contentValues2.put("scene_id", str);
                        contentValues2.put("scene_info", vuiSceneConvertToString);
                        contentValues2.put("scene_type", "build");
                        z = retryInsertVuiProvider(contentValues2);
                    }
                    if (z) {
                        LogUtils.d("VuiSceneManager", "insertVuiSceneToProvider update retryResult" + retryInsertVuiProvider(contentValues));
                    }
                }
            }
        } catch (Throwable unused) {
        }
    }

    private boolean retryInsertVuiProvider(ContentValues contentValues) {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        for (int i = 3; i > 0; i--) {
            Uri insert = contentResolver.insert(VUI_SCENE_URI, contentValues);
            long parseId = ContentUris.parseId(insert);
            if (insert != null && parseId > 0) {
                contentResolver.notifyChange(insert, null);
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteVuiSceneById(String str) {
        long j;
        ContentResolver contentResolver = this.mContext.getContentResolver();
        Cursor cursor = null;
        try {
            Uri uri = VUI_SCENE_URI;
            Cursor query = contentResolver.query(uri, null, "scene_id = ?", new String[]{str}, null);
            if (query != null) {
                try {
                    if (query.moveToNext()) {
                        j = query.getLong(query.getColumnIndex("_id"));
                        if (contentResolver.delete(uri, "scene_id = ?", new String[]{str}) > 0 && j != -1) {
                            contentResolver.notifyChange(ContentUris.withAppendedId(VUI_SCENE_DELETE_URI, j), null);
                        }
                        query.close();
                    }
                } catch (Throwable unused) {
                    cursor = query;
                    cursor.close();
                    return;
                }
            }
            j = -1;
            if (contentResolver.delete(uri, "scene_id = ?", new String[]{str}) > 0) {
                contentResolver.notifyChange(ContentUris.withAppendedId(VUI_SCENE_DELETE_URI, j), null);
            }
            query.close();
        } catch (Throwable unused2) {
        }
    }

    public void vuiFeedBack(final String str, final String str2) {
        if (this.mApiRouterHandler == null || !this.feedbackInfo.containsKey(str)) {
            return;
        }
        final int intValue = this.feedbackInfo.get(str).intValue();
        this.feedbackInfo.remove(str);
        LogUtils.logInfo("VuiSceneManager", "vuiFeedBack:" + str + ",soundArea:" + intValue + ",content:" + str2);
        this.mApiRouterHandler.post(new Runnable() { // from class: com.xiaopeng.speech.vui.VuiSceneManager.2
            @Override // java.lang.Runnable
            public void run() {
                Uri.Builder builder = new Uri.Builder();
                builder.authority(VuiSceneManager.this.getAuthority()).path("vuiSoundAreaFeedback").appendQueryParameter(VuiConstants.SCENE_PACKAGE_NAME, VuiSceneManager.this.mPackageName).appendQueryParameter("packageVersion", VuiSceneManager.this.mPackageVersion).appendQueryParameter("resourceName", str).appendQueryParameter("state", Constant.DEFAULT_ERROR_RSC_ID).appendQueryParameter(VuiConstants.ELEMENT_TYPE, VuiFeedbackType.TTS.getType()).appendQueryParameter("content", str2).appendQueryParameter("soundArea", "" + intValue);
                try {
                    LogUtils.logDebug("VuiSceneManager", "vuiSoundAreaFeedback ");
                    String str3 = (String) ApiRouter.route(builder.build());
                    LogUtils.logInfo("VuiSceneManager", "vuiSoundAreaFeedback success");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void updateListIndexState() {
        VuiEngineImpl vuiEngineImpl = this.vuiEngine;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.updateListIndexState();
        }
    }

    public void onVuiQueryCallBack(final String str, final String str2, final String str3) {
        Handler handler;
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || (handler = this.mApiRouterHandler) == null) {
            return;
        }
        handler.post(new Runnable() { // from class: com.xiaopeng.speech.vui.VuiSceneManager.3
            @Override // java.lang.Runnable
            public void run() {
                LogUtils.logInfo("VuiSceneManager", "onVuiQueryCallBack:" + str + ",event:" + str2 + ",result:" + str3);
                Uri.Builder builder = new Uri.Builder();
                builder.authority(VuiSceneManager.this.getAuthority()).path("onVuiQueryCallBack").appendQueryParameter(VuiConstants.SCENE_ID, str).appendQueryParameter(NotificationCompat.CATEGORY_EVENT, str2).appendQueryParameter(RecommendBean.SHOW_TIME_RESULT, str3);
                try {
                    LogUtils.logDebug("VuiSceneManager", "onVuiQueryCallBack ");
                    String str4 = (String) ApiRouter.route(builder.build());
                    LogUtils.logInfo("VuiSceneManager", "onVuiQueryCallBack success");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void exitScene(final String str, final String str2, final boolean z, final String str3, boolean z2) {
        if (str == null || str2 == null || !VuiUtils.canUseVuiFeature()) {
            return;
        }
        if (!z2) {
            if (getVuiSceneState(str) == VuiSceneState.ACTIVE.getState()) {
                updateSceneState(str, VuiSceneState.UNACTIVE.getState());
            } else {
                LogUtils.e("VuiSceneManager", "场景未激活不能执行退出");
                return;
            }
        }
        this.mApiRouterHandler.post(new Runnable() { // from class: com.xiaopeng.speech.vui.VuiSceneManager.4
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (z) {
                        Uri.Builder builder = new Uri.Builder();
                        if (!VuiUtils.is3DUIPlatForm()) {
                            builder.authority(VuiSceneManager.this.getAuthority()).path("exitScene").appendQueryParameter(VuiConstants.SCENE_ID, str).appendQueryParameter(VuiConstants.SCENE_PACKAGE_NAME, str2).appendQueryParameter("packageVersion", VuiSceneManager.this.mPackageVersion);
                        } else {
                            builder.authority(VuiSceneManager.this.getAuthority()).path("exitDisplayLocationScene").appendQueryParameter(VuiConstants.SCENE_ID, str).appendQueryParameter(VuiConstants.SCENE_PACKAGE_NAME, str2).appendQueryParameter("packageVersion", VuiSceneManager.this.mPackageVersion).appendQueryParameter("displayLocation", str3);
                        }
                        LogUtils.logDebug("VuiSceneManager", "exitScene-------------- " + str + ",displayLocation:" + str3);
                        ApiRouter.route(builder.build());
                        LogUtils.logDebug("VuiSceneManager", "exitScene---success---------- " + str);
                    }
                } catch (Exception e) {
                    LogUtils.e("VuiSceneManager", "exitScene--e: " + e.fillInStackTrace());
                    Uri.Builder builder2 = new Uri.Builder();
                    if (VuiUtils.is3DUIPlatForm()) {
                        builder2.authority(VuiSceneManager.this.getAuthority()).path("exitScene").appendQueryParameter(VuiConstants.SCENE_ID, str).appendQueryParameter(VuiConstants.SCENE_PACKAGE_NAME, str2).appendQueryParameter("packageVersion", VuiSceneManager.this.mPackageVersion);
                    }
                    try {
                        ApiRouter.route(builder2.build());
                    } catch (Exception unused) {
                    }
                }
            }
        });
    }

    public boolean isNaviTop() {
        try {
            Uri.Builder builder = new Uri.Builder();
            builder.authority(getAuthority()).path("isNaviTop");
            return ((Boolean) ApiRouter.route(builder.build())).booleanValue();
        } catch (RemoteException e) {
            e.printStackTrace();
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public class VuiSceneHandler extends Handler {
        public VuiSceneHandler() {
        }

        public VuiSceneHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != VuiSceneManager.SEND_UPLOAD_MESSAGE) {
                if (message.what == VuiSceneManager.REMOVE_FEED_BACK) {
                    String str = (String) message.obj;
                    if (VuiSceneManager.this.feedbackInfo.containsKey(str)) {
                        VuiSceneManager.this.feedbackInfo.remove(str);
                        return;
                    }
                    return;
                }
                return;
            }
            int i = message.arg1;
            boolean z = message.arg2 == 1;
            if (i == 2) {
                VuiSceneManager.this.addSceneElementGroup((VuiScene) message.obj, z);
            } else if (i == 0) {
                VuiSceneManager.this.buildScene((VuiScene) message.obj, z, true);
            } else if (i == 1) {
                VuiSceneManager.this.updateDynamicScene((VuiScene) message.obj, z);
            } else if (i == 4) {
                VuiSceneManager.this.updateSceneElementAttr((VuiScene) message.obj, z);
            } else if (i == 5) {
                VuiSceneManager.this.updateDisplayLocation((VuiScene) message.obj, z);
            } else {
                String str2 = (String) message.obj;
                int indexOf = str2.indexOf(",");
                VuiSceneManager.this.removeSceneElementGroup(str2.substring(0, indexOf), str2.substring(indexOf + 1), z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDisplayLocation(final VuiScene vuiScene, final boolean z) {
        if (VuiUtils.isMultiScreenPlatForm()) {
            LogUtils.i("VuiSceneManager", "updateDisplayLocation  =======   ");
            Handler handler = this.mApiRouterHandler;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.xiaopeng.speech.vui.VuiSceneManager.5
                    @Override // java.lang.Runnable
                    public void run() {
                        VuiScene vuiScene2;
                        String sceneId;
                        if (VuiUtils.cannotUpload() || (vuiScene2 = vuiScene) == null || (sceneId = vuiScene2.getSceneId()) == null) {
                            return;
                        }
                        VuiDisplayLocationInfoCache vuiDisplayLocationInfoCache = (VuiDisplayLocationInfoCache) VuiSceneCacheFactory.instance().getSceneCache(VuiSceneCacheFactory.CacheType.DISPLAY_LOCATION.getType());
                        if (z) {
                            vuiDisplayLocationInfoCache.setCache(sceneId, vuiScene.getDisplayLocation());
                        }
                        String vuiSceneConvertToString = VuiUtils.vuiSceneConvertToString(vuiScene);
                        LogUtils.i("VuiSceneManager", "updateDisplayLocation  =======   " + vuiSceneConvertToString);
                        if (VuiSceneManager.this.isUploadScene(sceneId)) {
                            vuiDisplayLocationInfoCache.setSendState(true);
                            Uri.Builder builder = new Uri.Builder();
                            builder.authority(VuiSceneManager.this.getAuthority()).path("updateDisplayLocation").appendQueryParameter(VuiConstants.SCENE_ID, sceneId).appendQueryParameter(VuiConstants.SCENE_PACKAGE_NAME, vuiScene.getPackageName()).appendQueryParameter("packageVersion", vuiScene.getVersion()).appendQueryParameter("sceneData", vuiSceneConvertToString);
                            try {
                                LogUtils.logDebug("VuiSceneManager", " updateDisplayLocation to CarSpeech" + sceneId);
                                String str = (String) ApiRouter.route(builder.build());
                                vuiDisplayLocationInfoCache.setSendState(false);
                                LogUtils.logInfo("VuiSceneManager", "updateDisplayLocation to CarSpeech success" + sceneId + ",result:" + str);
                                if (!TextUtils.isEmpty(str) && vuiDisplayLocationInfoCache != null && vuiScene.getDisplayLocation().equals(vuiDisplayLocationInfoCache.getDisplayCache(sceneId))) {
                                    vuiDisplayLocationInfoCache.removeDisplayCache(sceneId);
                                }
                            } catch (RemoteException e) {
                                e.printStackTrace();
                                LogUtils.e("VuiSceneManager", "updateDisplayLocation " + e.fillInStackTrace());
                                vuiDisplayLocationInfoCache.setSendState(false);
                            }
                            if ("user".equals(Build.TYPE) || LogUtils.getLogLevel() > LogUtils.LOG_DEBUG_LEVEL) {
                                return;
                            }
                            LogUtils.logDebug("VuiSceneManager", "updateDisplayLocation " + VuiUtils.vuiSceneConvertToString(vuiScene));
                        }
                    }
                });
            }
        }
    }

    private void registerReceiver() {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.xiaopeng.speech.vui.VuiSceneManager.6
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (VuiUtils.canUseVuiFeature()) {
                            IntentFilter intentFilter = new IntentFilter();
                            intentFilter.addAction("carspeechservice.SpeechServer.Start");
                            intentFilter.addAction(VuiConstants.INTENT_ACTION_ENV_CHANGED);
                            intentFilter.addAction(VuiConstants.INTENT_ACTION_VUIPROVIDER_DEATH);
                            intentFilter.addAction(VuiConstants.INTENT_ACTION_LOCAL_SCENE_NOT_FOUND);
                            VuiSceneManager.this.mReceiver = new VuiBroadCastReceiver();
                            ((Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication", new Class[0]).invoke(null, null)).registerReceiver(VuiSceneManager.this.mReceiver, intentFilter);
                        }
                    } catch (Exception e) {
                        LogUtils.e("VuiSceneManager", "registerReceiver e:" + e.getMessage());
                    }
                }
            });
        }
    }

    public void handleSceneDataInfo() {
        if (VuiEngineImpl.mActiveSceneIds.size() > 0) {
            String str = VuiEngineImpl.mActiveSceneIds.get(VuiConstants.SCREEN_DISPLAY_RF);
            String str2 = VuiEngineImpl.mActiveSceneIds.get(VuiConstants.SCREEN_DISPLAY_LF);
            if (!TextUtils.isEmpty(str2)) {
                enterScene(str2, this.mPackageName, true, VuiConstants.SCREEN_DISPLAY_LF);
            }
            if (!TextUtils.isEmpty(str)) {
                enterScene(str, this.mPackageName, true, VuiConstants.SCREEN_DISPLAY_RF);
            }
        }
        if (VuiEngineImpl.mLeftPopPanelStack.size() > 0) {
            Iterator<String> it = VuiEngineImpl.mLeftPopPanelStack.iterator();
            while (it.hasNext()) {
                String next = it.next();
                if (!TextUtils.isEmpty(next)) {
                    enterScene(next, this.mPackageName, true, VuiConstants.SCREEN_DISPLAY_LF);
                }
            }
        }
        if (VuiEngineImpl.mRightPopPanelStack.size() > 0) {
            Iterator<String> it2 = VuiEngineImpl.mRightPopPanelStack.iterator();
            while (it2.hasNext()) {
                String next2 = it2.next();
                if (!TextUtils.isEmpty(next2)) {
                    enterScene(next2, this.mPackageName, true, VuiConstants.SCREEN_DISPLAY_RF);
                }
            }
        }
    }

    public void handleAllSceneCache(boolean z) {
        try {
            if (this.sceneIds == null) {
                return;
            }
            for (int i = 0; i < this.sceneIds.size(); i++) {
                String str = this.sceneIds.get(i);
                if (z) {
                    VuiSceneCacheFactory.instance().removeAllCache(str);
                    VuiSceneInfo vuiSceneInfo = this.mVuiSceneInfoMap.get(str);
                    if (vuiSceneInfo != null) {
                        vuiSceneInfo.reset(false);
                        this.mVuiSceneInfoMap.put(str, vuiSceneInfo);
                    }
                } else {
                    ((VuiSceneBuildCache) VuiSceneCacheFactory.instance().getSceneCache(VuiSceneCacheFactory.CacheType.BUILD.getType())).setUploadedState(str, false);
                    VuiSceneCacheFactory.instance().removeOtherCache(str);
                }
            }
        } catch (Exception e) {
            LogUtils.e("VuiSceneManager", "handleAllSceneCache e:" + e.getMessage());
        }
    }

    public void sendBroadCast() {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.xiaopeng.speech.vui.VuiSceneManager.7
                @Override // java.lang.Runnable
                public void run() {
                    if (VuiSceneManager.this.mBinder == null) {
                        VuiSceneManager.this.mBinder = new Binder();
                    }
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    intent.setAction("com.xiaopeng.speech.vuiengine.start");
                    intent.setPackage("com.xiaopeng.carspeechservice");
                    bundle.putBinder("client", VuiSceneManager.this.mBinder);
                    bundle.putString("name", VuiSceneManager.this.mPackageName);
                    bundle.putString("version", VuiSceneManager.this.mPackageVersion);
                    if (VuiSceneManager.this.hasProcessFeature()) {
                        bundle.putString("processName", VuiSceneManager.this.getProcessName());
                    }
                    intent.putExtra("bundle", bundle);
                    VuiSceneManager.this.mContext.sendBroadcast(intent);
                }
            });
        }
    }

    public void subscribeVuiFeature() {
        if (VuiUtils.canUseVuiFeature()) {
            subscribe(false);
            sendBroadCast();
            registerReceiver();
        }
    }

    public void unSubscribeVuiFeature() {
        Handler handler;
        if (VuiUtils.canUseVuiFeature() && (handler = this.mHandler) != null) {
            handler.post(new Runnable() { // from class: com.xiaopeng.speech.vui.VuiSceneManager.8
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        ApiRouter.route(new Uri.Builder().authority(VuiSceneManager.this.getAuthority()).path("unsubscribeVuiFeatureProcess").appendQueryParameter(VuiConstants.SCENE_PACKAGE_NAME, VuiSceneManager.this.mPackageName).appendQueryParameter("packageVersion", VuiSceneManager.this.mPackageVersion).appendQueryParameter("processName", VuiSceneManager.this.getProcessName()).build());
                    } catch (Exception unused) {
                    }
                }
            });
        }
    }

    public void subscribe(final boolean z) {
        Handler handler = this.mApiRouterHandler;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.xiaopeng.speech.vui.VuiSceneManager.9
                @Override // java.lang.Runnable
                public void run() {
                    if (VuiUtils.canUseVuiFeature()) {
                        LogUtils.logInfo("VuiSceneManager", "subscribe：" + VuiSceneManager.this.mObserver);
                        if (TextUtils.isEmpty(VuiSceneManager.this.mObserver)) {
                            LogUtils.e("VuiSceneManager", "mObserver == null");
                            try {
                                String str = (String) ApiRouter.route(new Uri.Builder().authority(VuiSceneManager.this.getAuthority()).path("subscribeVuiFeatureProcess").appendQueryParameter(VuiConstants.SCENE_PACKAGE_NAME, VuiSceneManager.this.mPackageName).appendQueryParameter("packageVersion", VuiSceneManager.this.mPackageVersion).appendQueryParameter("processName", VuiSceneManager.this.getProcessName()).build());
                                LogUtils.logDebug("VuiSceneManager", "subscribeVuiFeature：" + str);
                                if (!TextUtils.isEmpty(str)) {
                                    if (str.contains("dm_start")) {
                                        VuiSceneManager.this.mIsInSpeech = true;
                                    } else if (str.contains("dm_end")) {
                                        VuiSceneManager.this.mIsInSpeech = false;
                                    } else if (str.contains("vui_disabled")) {
                                        VuiSceneManager.this.setFeatureState(false);
                                        VuiUtils.disableVuiFeature();
                                    } else if (str.contains("vui_enable")) {
                                        VuiSceneManager.this.setFeatureState(true);
                                        VuiUtils.enableVuiFeature();
                                    }
                                }
                                return;
                            } catch (Exception unused) {
                                return;
                            }
                        }
                        new String[]{VuiEvent.SCENE_CONTROL};
                        try {
                            if (VuiSceneManager.this.hasProcessFeature()) {
                                ApiRouter.route(new Uri.Builder().authority(VuiSceneManager.this.getAuthority()).path("subscribeProcess").appendQueryParameter("observer", VuiSceneManager.this.mObserver).appendQueryParameter("param", "").appendQueryParameter(VuiConstants.SCENE_PACKAGE_NAME, VuiSceneManager.this.mPackageName).appendQueryParameter("packageVersion", VuiSceneManager.this.mPackageVersion).appendQueryParameter("sceneList", VuiSceneManager.this.sceneIds.toString()).appendQueryParameter("processName", VuiSceneManager.this.getProcessName()).build());
                            } else {
                                ApiRouter.route(new Uri.Builder().authority(VuiSceneManager.this.getAuthority()).path("subscribe").appendQueryParameter("observer", VuiSceneManager.this.mObserver).appendQueryParameter("param", "").appendQueryParameter(VuiConstants.SCENE_PACKAGE_NAME, VuiSceneManager.this.mPackageName).appendQueryParameter("packageVersion", VuiSceneManager.this.mPackageVersion).appendQueryParameter("sceneList", VuiSceneManager.this.sceneIds.toString()).build());
                            }
                            if (z) {
                                VuiSceneManager.this.handleSceneDataInfo();
                            }
                        } catch (Exception e) {
                            LogUtils.e("VuiSceneManager", "subscribe e:" + e.fillInStackTrace());
                            VuiSceneManager.this.subscribe(z);
                        }
                    }
                }
            });
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x00a3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String getProcessName() {
        /*
            r6 = this;
            java.lang.String r0 = "main"
            java.lang.String r1 = r6.mProcessName
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 != 0) goto Ld
            java.lang.String r0 = r6.mProcessName
            return r0
        Ld:
            r1 = 0
            java.io.File r2 = new java.io.File     // Catch: java.lang.Throwable -> L8b java.lang.Exception -> L8d
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L8b java.lang.Exception -> L8d
            r3.<init>()     // Catch: java.lang.Throwable -> L8b java.lang.Exception -> L8d
            java.lang.String r4 = "/proc/"
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch: java.lang.Throwable -> L8b java.lang.Exception -> L8d
            int r4 = android.os.Process.myPid()     // Catch: java.lang.Throwable -> L8b java.lang.Exception -> L8d
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch: java.lang.Throwable -> L8b java.lang.Exception -> L8d
            java.lang.String r4 = "/cmdline"
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch: java.lang.Throwable -> L8b java.lang.Exception -> L8d
            java.lang.String r3 = r3.toString()     // Catch: java.lang.Throwable -> L8b java.lang.Exception -> L8d
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L8b java.lang.Exception -> L8d
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L8b java.lang.Exception -> L8d
            java.io.FileReader r4 = new java.io.FileReader     // Catch: java.lang.Throwable -> L8b java.lang.Exception -> L8d
            r4.<init>(r2)     // Catch: java.lang.Throwable -> L8b java.lang.Exception -> L8d
            r3.<init>(r4)     // Catch: java.lang.Throwable -> L8b java.lang.Exception -> L8d
            java.lang.String r1 = r3.readLine()     // Catch: java.lang.Exception -> L86 java.lang.Throwable -> L9f
            java.lang.String r1 = r1.trim()     // Catch: java.lang.Exception -> L86 java.lang.Throwable -> L9f
            java.lang.String r2 = r6.mPackageName     // Catch: java.lang.Exception -> L84 java.lang.Throwable -> L9f
            boolean r2 = r1.startsWith(r2)     // Catch: java.lang.Exception -> L84 java.lang.Throwable -> L9f
            if (r2 == 0) goto L52
            java.lang.String r2 = r6.mPackageName     // Catch: java.lang.Exception -> L84 java.lang.Throwable -> L9f
            java.lang.String r4 = ""
            java.lang.String r1 = r1.replace(r2, r4)     // Catch: java.lang.Exception -> L84 java.lang.Throwable -> L9f
        L52:
            boolean r2 = android.text.TextUtils.isEmpty(r1)     // Catch: java.lang.Exception -> L84 java.lang.Throwable -> L9f
            if (r2 == 0) goto L59
            goto L5e
        L59:
            r0 = 1
            java.lang.String r0 = r1.substring(r0)     // Catch: java.lang.Exception -> L84 java.lang.Throwable -> L9f
        L5e:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L86 java.lang.Throwable -> L9f
            r1.<init>()     // Catch: java.lang.Exception -> L86 java.lang.Throwable -> L9f
            java.lang.StringBuilder r1 = r1.append(r0)     // Catch: java.lang.Exception -> L86 java.lang.Throwable -> L9f
            java.lang.String r2 = ",pid_"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch: java.lang.Exception -> L86 java.lang.Throwable -> L9f
            int r2 = android.os.Process.myPid()     // Catch: java.lang.Exception -> L86 java.lang.Throwable -> L9f
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch: java.lang.Exception -> L86 java.lang.Throwable -> L9f
            java.lang.String r1 = r1.toString()     // Catch: java.lang.Exception -> L86 java.lang.Throwable -> L9f
            r6.mProcessName = r1     // Catch: java.lang.Exception -> L86 java.lang.Throwable -> L9f
            r3.close()     // Catch: java.lang.Exception -> L7f
            goto L83
        L7f:
            r0 = move-exception
            r0.printStackTrace()
        L83:
            return r1
        L84:
            r0 = move-exception
            goto L91
        L86:
            r1 = move-exception
            r5 = r1
            r1 = r0
            r0 = r5
            goto L91
        L8b:
            r0 = move-exception
            goto La1
        L8d:
            r2 = move-exception
            r3 = r1
            r1 = r0
            r0 = r2
        L91:
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L9f
            if (r3 == 0) goto L9e
            r3.close()     // Catch: java.lang.Exception -> L9a
            goto L9e
        L9a:
            r0 = move-exception
            r0.printStackTrace()
        L9e:
            return r1
        L9f:
            r0 = move-exception
            r1 = r3
        La1:
            if (r1 == 0) goto Lab
            r1.close()     // Catch: java.lang.Exception -> La7
            goto Lab
        La7:
            r1 = move-exception
            r1.printStackTrace()
        Lab:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.speech.vui.VuiSceneManager.getProcessName():java.lang.String");
    }

    public void setProcessName(String str) {
        this.mProcessName = str + ",pid_" + Process.myPid();
    }

    public void unSubscribe() {
        Handler handler;
        if (VuiUtils.canUseVuiFeature() && (handler = this.mHandler) != null) {
            handler.post(new Runnable() { // from class: com.xiaopeng.speech.vui.VuiSceneManager.10
                @Override // java.lang.Runnable
                public void run() {
                    VuiSceneManager.this.unsubscribe();
                }
            });
        }
    }

    public void unsubscribe() {
        if (TextUtils.isEmpty(this.mObserver)) {
            LogUtils.e("VuiSceneManager", "mObserver == null");
            return;
        }
        Handler handler = this.mApiRouterHandler;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.xiaopeng.speech.vui.VuiSceneManager.11
                @Override // java.lang.Runnable
                public void run() {
                    if (VuiSceneManager.this.hasProcessFeature()) {
                        try {
                            ApiRouter.route(new Uri.Builder().authority(VuiSceneManager.this.getAuthority()).path("unsubscribeProcess").appendQueryParameter("observer", VuiSceneManager.this.mObserver).appendQueryParameter(VuiConstants.SCENE_PACKAGE_NAME, VuiSceneManager.this.mPackageName).appendQueryParameter("packageVersion", VuiSceneManager.this.mPackageVersion).appendQueryParameter("processName", VuiSceneManager.this.getProcessName()).build());
                            return;
                        } catch (Throwable th) {
                            th.printStackTrace();
                            LogUtils.e("VuiSceneManager", "unsubscribe e:" + th.getMessage());
                            return;
                        }
                    }
                    try {
                        ApiRouter.route(new Uri.Builder().authority(VuiSceneManager.this.getAuthority()).path("unsubscribe").appendQueryParameter("observer", VuiSceneManager.this.mObserver).appendQueryParameter(VuiConstants.SCENE_PACKAGE_NAME, VuiSceneManager.this.mPackageName).appendQueryParameter("packageVersion", VuiSceneManager.this.mPackageVersion).build());
                    } catch (Throwable th2) {
                        th2.printStackTrace();
                        LogUtils.e("VuiSceneManager", "unsubscribe e:" + th2.getMessage());
                    }
                }
            });
        }
    }

    public synchronized void sendSceneData(final String str) {
        try {
            this.mHandler.post(new Runnable() { // from class: com.xiaopeng.speech.vui.VuiSceneManager.12
                @Override // java.lang.Runnable
                public void run() {
                    if (VuiUtils.cannotUpload()) {
                        return;
                    }
                    String str2 = str;
                    if (!TextUtils.isEmpty(str2)) {
                        VuiSceneManager.this.checkUploadScene(str2);
                        return;
                    }
                    String str3 = VuiEngineImpl.mActiveSceneIds.get(VuiConstants.SCREEN_DISPLAY_LF);
                    String str4 = VuiEngineImpl.mActiveSceneIds.get(VuiConstants.SCREEN_DISPLAY_RF);
                    LogUtils.i("VuiSceneManager", "sendSceneData   ========   " + str3 + "   ::::  " + str4);
                    if (!TextUtils.isEmpty(str3)) {
                        VuiSceneManager.this.checkUploadScene(str3);
                    }
                    if (!TextUtils.isEmpty(str4)) {
                        VuiSceneManager.this.checkUploadScene(str4);
                    }
                    if (VuiEngineImpl.mLeftPopPanelStack.size() > 0) {
                        Iterator<String> it = VuiEngineImpl.mLeftPopPanelStack.iterator();
                        while (it.hasNext()) {
                            String next = it.next();
                            if (!TextUtils.isEmpty(next)) {
                                VuiSceneManager.this.checkUploadScene(next);
                            }
                        }
                    }
                    if (VuiEngineImpl.mRightPopPanelStack.size() > 0) {
                        Iterator<String> it2 = VuiEngineImpl.mRightPopPanelStack.iterator();
                        while (it2.hasNext()) {
                            String next2 = it2.next();
                            if (!TextUtils.isEmpty(next2)) {
                                VuiSceneManager.this.checkUploadScene(next2);
                            }
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.fillInStackTrace();
            LogUtils.e("VuiSceneManager", "sendSceneData e:" + e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkUploadScene(String str) {
        VuiSceneCache sceneCache;
        int fusionType;
        VuiDisplayLocationInfoCache vuiDisplayLocationInfoCache;
        List<VuiElement> cache;
        if (!isUploadScene(str) || (sceneCache = VuiSceneCacheFactory.instance().getSceneCache(VuiSceneCacheFactory.CacheType.UPDATE.getType())) == null || (fusionType = sceneCache.getFusionType(str)) == VuiSceneCacheFactory.CacheType.DEFAULT.getType()) {
            return;
        }
        VuiScene build = new VuiScene.Builder().sceneId(str).appVersion(this.mPackageVersion).packageName(this.mPackageName).timestamp(System.currentTimeMillis()).build();
        if (fusionType == VuiSceneCacheFactory.CacheType.UPDATE.getType()) {
            build.setElements(sceneCache.getCache(str));
            sendSceneData(1, false, build, false);
        } else if (fusionType == VuiSceneCacheFactory.CacheType.BUILD.getType()) {
            VuiSceneCache sceneCache2 = VuiSceneCacheFactory.instance().getSceneCache(VuiSceneCacheFactory.CacheType.BUILD.getType());
            if (sceneCache2 == null || (cache = sceneCache2.getCache(str)) == null || cache.isEmpty()) {
                return;
            }
            build.setElements(sceneCache2.getCache(str));
            sendSceneData(0, false, build, false);
        } else if (fusionType == VuiSceneCacheFactory.CacheType.REMOVE.getType()) {
            VuiSceneRemoveCache vuiSceneRemoveCache = (VuiSceneRemoveCache) VuiSceneCacheFactory.instance().getSceneCache(VuiSceneCacheFactory.CacheType.REMOVE.getType());
            if (vuiSceneRemoveCache != null) {
                sendSceneData(3, false, str + "," + vuiSceneRemoveCache.getRemoveCache(str).toString().replace("[", "").replace("]", ""), false);
            }
        } else if (fusionType != VuiSceneCacheFactory.CacheType.DISPLAY_LOCATION.getType() || (vuiDisplayLocationInfoCache = (VuiDisplayLocationInfoCache) VuiSceneCacheFactory.instance().getSceneCache(VuiSceneCacheFactory.CacheType.DISPLAY_LOCATION.getType())) == null) {
        } else {
            String displayCache = vuiDisplayLocationInfoCache.getDisplayCache(build.getSceneId());
            if (TextUtils.isEmpty(displayCache)) {
                return;
            }
            build.setDisplayLocation(displayCache);
            updateDisplayLocation(build, true);
        }
    }

    public void sendSceneData(int i, boolean z, Object obj) {
        sendSceneData(i, z, obj, true);
    }

    public void sendSceneData(final int i, boolean z, final Object obj, boolean z2) {
        Handler handler;
        Message obtainMessage = this.mHandler.obtainMessage();
        obtainMessage.arg1 = i;
        obtainMessage.what = SEND_UPLOAD_MESSAGE;
        obtainMessage.arg2 = z ? 1 : 0;
        obtainMessage.obj = obj;
        this.mHandler.sendMessage(obtainMessage);
        if (VuiUtils.is3DUIPlatForm() && z2 && (handler = this.mLocalVuiRouterHandler) != null && obj != null && (obj instanceof VuiScene)) {
            handler.post(new Runnable() { // from class: com.xiaopeng.speech.vui.VuiSceneManager.13
                @Override // java.lang.Runnable
                public void run() {
                    int i2 = i;
                    if (i2 == 0) {
                        VuiScene vuiScene = (VuiScene) obj;
                        String vuiSceneConvertToString = VuiUtils.vuiSceneConvertToString(vuiScene);
                        if (TextUtils.isEmpty(vuiSceneConvertToString)) {
                            return;
                        }
                        VuiSceneManager.this.insertVuiSceneToProvider(vuiScene.getSceneId(), vuiSceneConvertToString, "build");
                    } else if (i2 == 1) {
                        VuiScene vuiScene2 = (VuiScene) obj;
                        String vuiSceneConvertToString2 = VuiUtils.vuiSceneConvertToString(vuiScene2);
                        if (TextUtils.isEmpty(vuiSceneConvertToString2)) {
                            return;
                        }
                        VuiSceneManager.this.insertVuiSceneToProvider(vuiScene2.getSceneId(), vuiSceneConvertToString2, "update");
                    } else if (i2 == 3) {
                        VuiScene vuiScene3 = (VuiScene) obj;
                        if (vuiScene3 == null || TextUtils.isEmpty(vuiScene3.getSceneId())) {
                            return;
                        }
                        VuiSceneManager.this.deleteVuiSceneById(vuiScene3.getSceneId());
                    } else if (i2 != 5) {
                    } else {
                        VuiScene vuiScene4 = (VuiScene) obj;
                        String vuiSceneConvertToString3 = VuiUtils.vuiSceneConvertToString(vuiScene4);
                        if (TextUtils.isEmpty(vuiSceneConvertToString3)) {
                            return;
                        }
                        VuiSceneManager.this.insertVuiSceneToProvider(vuiScene4.getSceneId(), vuiSceneConvertToString3, "display");
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class Holder {
        private static final VuiSceneManager Instance = new VuiSceneManager();

        private Holder() {
        }
    }

    public void buildScene(final VuiScene vuiScene, final boolean z, final boolean z2) {
        Handler handler = this.mApiRouterHandler;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.xiaopeng.speech.vui.VuiSceneManager.14
                @Override // java.lang.Runnable
                public void run() {
                    VuiScene vuiScene2;
                    String sceneId;
                    if (VuiUtils.cannotUpload() || (vuiScene2 = vuiScene) == null || vuiScene2.getElements() == null || vuiScene.getElements().size() < 0 || (sceneId = vuiScene.getSceneId()) == null) {
                        return;
                    }
                    VuiSceneCache sceneCache = VuiSceneCacheFactory.instance().getSceneCache(VuiSceneCacheFactory.CacheType.BUILD.getType());
                    if (z || z2 || !sceneCache.getUploadedState(sceneId)) {
                        if (sceneCache != null && z) {
                            List<VuiElement> cache = sceneCache.getCache(sceneId);
                            if (cache != null && !cache.isEmpty()) {
                                vuiScene.setElements(sceneCache.getUpdateFusionCache(sceneId, vuiScene.getElements(), false));
                            }
                            sceneCache.setCache(sceneId, vuiScene.getElements());
                        }
                        String vuiSceneConvertToString = VuiUtils.vuiSceneConvertToString(vuiScene);
                        VuiSceneInfo sceneInfo = VuiSceneManager.instance().getSceneInfo(sceneId);
                        if (sceneInfo != null && sceneInfo.isWholeScene() && sceneInfo.isFull()) {
                            sceneInfo.setLastAddStr(null);
                            sceneInfo.setLastUpdateStr(null);
                            LogUtils.logDebug("VuiSceneManager", "build full_scene_info:" + vuiSceneConvertToString);
                            if (VuiSceneManager.this.isUploadScene(sceneId)) {
                                Uri.Builder builder = new Uri.Builder();
                                builder.authority(VuiSceneManager.this.getAuthority()).path("buildScene").appendQueryParameter(VuiConstants.SCENE_ID, sceneId).appendQueryParameter(VuiConstants.SCENE_PACKAGE_NAME, vuiScene.getPackageName()).appendQueryParameter("packageVersion", vuiScene.getVersion()).appendQueryParameter("sceneData", vuiSceneConvertToString);
                                try {
                                    LogUtils.logDebug("VuiSceneManager", " send buildScene to CarSpeech" + sceneId);
                                    if (sceneCache != null) {
                                        sceneCache.setUploadedState(sceneId, false);
                                    }
                                    String str = (String) ApiRouter.route(builder.build());
                                    if (!TextUtils.isEmpty(str) && sceneCache != null) {
                                        sceneCache.setUploadedState(sceneId, true);
                                    }
                                    LogUtils.logInfo("VuiSceneManager", " send buildScene to CarSpeech success" + sceneId + ",result:" + str);
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                    if (sceneCache != null) {
                                        sceneCache.setUploadedState(sceneId, false);
                                    }
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    public void addSceneElement(final VuiScene vuiScene, final String str) {
        Handler handler = this.mApiRouterHandler;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.xiaopeng.speech.vui.VuiSceneManager.15
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (VuiUtils.cannotUpload()) {
                            return;
                        }
                        String vuiSceneConvertToString = VuiUtils.vuiSceneConvertToString(vuiScene);
                        Uri.Builder builder = new Uri.Builder();
                        builder.authority(VuiSceneManager.this.getAuthority()).path("addSceneElement").appendQueryParameter(VuiConstants.SCENE_ID, vuiScene.getSceneId()).appendQueryParameter("parentId", str).appendQueryParameter(VuiConstants.SCENE_PACKAGE_NAME, vuiScene.getPackageName()).appendQueryParameter("packageVersion", vuiScene.getVersion()).appendQueryParameter("sceneData", vuiSceneConvertToString);
                        LogUtils.logDebug("VuiSceneManager", "addSceneElement to CarSpeech " + vuiScene.getSceneId());
                        LogUtils.logInfo("VuiSceneManager", "addSceneElement to CarSpeech success" + vuiScene.getSceneId() + ",result:" + ((String) ApiRouter.route(builder.build())));
                    } catch (Exception e) {
                        e.printStackTrace();
                        LogUtils.e("VuiSceneManager", "addSceneElement e:" + e.getMessage());
                    }
                }
            });
        }
    }

    public void addSceneElementGroup(final VuiScene vuiScene, boolean z) {
        Handler handler = this.mApiRouterHandler;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.xiaopeng.speech.vui.VuiSceneManager.16
                @Override // java.lang.Runnable
                public void run() {
                    VuiScene vuiScene2;
                    String sceneId;
                    VuiSceneRemoveCache vuiSceneRemoveCache;
                    List<VuiElement> elements;
                    if (VuiUtils.cannotUpload() || (vuiScene2 = vuiScene) == null || (sceneId = vuiScene2.getSceneId()) == null) {
                        return;
                    }
                    VuiSceneCache sceneCache = VuiSceneCacheFactory.instance().getSceneCache(VuiSceneCacheFactory.CacheType.UPDATE.getType());
                    VuiSceneCache sceneCache2 = VuiSceneCacheFactory.instance().getSceneCache(VuiSceneCacheFactory.CacheType.BUILD.getType());
                    int type = VuiSceneCacheFactory.CacheType.DEFAULT.getType();
                    List<VuiElement> list = null;
                    if (sceneCache != null) {
                        type = sceneCache.getFusionType(sceneId);
                        if (type == VuiSceneCacheFactory.CacheType.UPDATE.getType()) {
                            list = sceneCache.getUpdateFusionCache(sceneId, vuiScene.getElements(), false);
                        } else if (type == VuiSceneCacheFactory.CacheType.BUILD.getType()) {
                            if (sceneCache2 != null) {
                                list = sceneCache2.getFusionCache(sceneId, vuiScene.getElements(), false);
                            }
                        } else if (type == VuiSceneCacheFactory.CacheType.REMOVE.getType() && (vuiSceneRemoveCache = (VuiSceneRemoveCache) VuiSceneCacheFactory.instance().getSceneCache(VuiSceneCacheFactory.CacheType.REMOVE.getType())) != null && (elements = vuiScene.getElements()) != null && elements.size() == 1) {
                            vuiSceneRemoveCache.deleteRemoveIdFromCache(sceneId, vuiScene.getElements().get(0).id);
                        }
                        if (list != null) {
                            vuiScene.setElements(list);
                        }
                    }
                    if (VuiSceneManager.this.isUploadScene(sceneId)) {
                        if (type == VuiSceneCacheFactory.CacheType.BUILD.getType()) {
                            VuiSceneManager.this.sendBuildCacheInOther(sceneId, vuiScene, sceneCache2);
                            return;
                        }
                        String vuiSceneConvertToString = VuiUtils.vuiSceneConvertToString(vuiScene);
                        Uri.Builder builder = new Uri.Builder();
                        builder.authority(VuiSceneManager.this.getAuthority()).path("addSceneElementGroup").appendQueryParameter(VuiConstants.SCENE_ID, sceneId).appendQueryParameter(VuiConstants.SCENE_PACKAGE_NAME, vuiScene.getPackageName()).appendQueryParameter("packageVersion", vuiScene.getVersion()).appendQueryParameter("sceneData", vuiSceneConvertToString);
                        try {
                            LogUtils.logDebug("VuiSceneManager", "addSceneElementGroup to CarSpeech " + sceneId);
                            String str = (String) ApiRouter.route(builder.build());
                            LogUtils.logInfo("VuiSceneManager", "addSceneElementGroup to CarSpeech success " + sceneId + ",result:" + str);
                            if (TextUtils.isEmpty(str)) {
                                if (sceneCache != null) {
                                    sceneCache.setCache(sceneId, vuiScene.getElements());
                                }
                            } else if (type == VuiSceneCacheFactory.CacheType.UPDATE.getType()) {
                                sceneCache.removeCache(sceneId);
                            }
                        } catch (RemoteException e) {
                            LogUtils.e("VuiSceneManager", "addSceneElementGroup " + e.fillInStackTrace());
                            e.printStackTrace();
                            if (sceneCache != null) {
                                sceneCache.setCache(sceneId, vuiScene.getElements());
                            }
                        }
                        if (sceneCache2 != null) {
                            List<VuiElement> fusionCache = sceneCache2.getFusionCache(sceneId, vuiScene.getElements(), false);
                            if (fusionCache != null) {
                                sceneCache2.setCache(sceneId, fusionCache);
                            }
                            vuiScene.setElements(fusionCache);
                        }
                        if ("user".equals(Build.TYPE) || LogUtils.getLogLevel() > LogUtils.LOG_DEBUG_LEVEL) {
                            return;
                        }
                        LogUtils.logDebug("VuiSceneManager", "addSceneElementGroup full_scene_info:" + VuiUtils.vuiSceneConvertToString(vuiScene));
                        return;
                    }
                    if (type != VuiSceneCacheFactory.CacheType.BUILD.getType()) {
                        if (sceneCache != null) {
                            sceneCache.setCache(sceneId, vuiScene.getElements());
                        }
                        if (sceneCache2 != null) {
                            List<VuiElement> fusionCache2 = sceneCache2.getFusionCache(sceneId, vuiScene.getElements(), false);
                            if (fusionCache2 != null) {
                                sceneCache2.setCache(sceneId, fusionCache2);
                            }
                            vuiScene.setElements(fusionCache2);
                        }
                    } else if (sceneCache2 != null) {
                        sceneCache2.setCache(sceneId, vuiScene.getElements());
                    }
                    if ("user".equals(Build.TYPE) || LogUtils.getLogLevel() > LogUtils.LOG_DEBUG_LEVEL) {
                        return;
                    }
                    LogUtils.logDebug("VuiSceneManager", "addSceneElementGroup from full_scene_info:" + VuiUtils.vuiSceneConvertToString(vuiScene));
                }
            });
        }
    }

    public void removeSceneElementGroup(final String str, final String str2, final boolean z) {
        Handler handler = this.mApiRouterHandler;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.xiaopeng.speech.vui.VuiSceneManager.17
                /* JADX WARN: Removed duplicated region for block: B:40:0x0168  */
                /* JADX WARN: Removed duplicated region for block: B:73:0x023b  */
                @Override // java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public void run() {
                    /*
                        Method dump skipped, instructions count: 762
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.speech.vui.VuiSceneManager.AnonymousClass17.run():void");
                }
            });
        }
    }

    public void vuiFeedBack(final View view, final VuiFeedback vuiFeedback) {
        Handler handler = this.mApiRouterHandler;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.xiaopeng.speech.vui.VuiSceneManager.18
                @Override // java.lang.Runnable
                public void run() {
                    View view2;
                    if (vuiFeedback == null || (view2 = view) == null) {
                        return;
                    }
                    String str = null;
                    if (view2 != null && view2.getId() != -1 && view.getId() != 0) {
                        str = VuiUtils.getResourceName(view.getId());
                    }
                    if (VuiUtils.is3DUIPlatForm()) {
                        VuiSceneManager.this.vuiFeedBack(str, vuiFeedback.content);
                        return;
                    }
                    Uri.Builder builder = new Uri.Builder();
                    builder.authority(VuiSceneManager.this.getAuthority()).path("vuiFeedback").appendQueryParameter(VuiConstants.SCENE_PACKAGE_NAME, VuiSceneManager.this.mPackageName).appendQueryParameter("packageVersion", VuiSceneManager.this.mPackageVersion).appendQueryParameter("resourceName", str).appendQueryParameter("state", "" + vuiFeedback.state).appendQueryParameter(VuiConstants.ELEMENT_TYPE, vuiFeedback.getFeedbackType().getType()).appendQueryParameter("content", vuiFeedback.content);
                    try {
                        LogUtils.logDebug("VuiSceneManager", "vuiFeedBack ");
                        String str2 = (String) ApiRouter.route(builder.build());
                        LogUtils.logInfo("VuiSceneManager", "vuiFeedBack success");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void vuiFeedBack(final String str, final VuiFeedback vuiFeedback) {
        Handler handler = this.mApiRouterHandler;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.xiaopeng.speech.vui.VuiSceneManager.19
                @Override // java.lang.Runnable
                public void run() {
                    if (vuiFeedback == null || TextUtils.isEmpty(str)) {
                        return;
                    }
                    String str2 = "";
                    if (VuiUtils.is3DUIPlatForm()) {
                        int intValue = Integer.valueOf(str).intValue();
                        if (intValue != -1 && intValue != 0) {
                            str2 = VuiUtils.getResourceName(intValue);
                        }
                        VuiSceneManager.this.vuiFeedBack(str2, vuiFeedback.content);
                        return;
                    }
                    Uri.Builder builder = new Uri.Builder();
                    builder.authority(VuiSceneManager.this.getAuthority()).path("vuiFeedback").appendQueryParameter(VuiConstants.SCENE_PACKAGE_NAME, VuiSceneManager.this.mPackageName).appendQueryParameter("packageVersion", VuiSceneManager.this.mPackageVersion).appendQueryParameter("resourceName", "").appendQueryParameter("state", "" + vuiFeedback.state).appendQueryParameter(VuiConstants.ELEMENT_TYPE, vuiFeedback.getFeedbackType().getType()).appendQueryParameter("content", vuiFeedback.content);
                    try {
                        LogUtils.logDebug("VuiSceneManager", "vuiFeedBack ");
                        String str3 = (String) ApiRouter.route(builder.build());
                        LogUtils.logInfo("VuiSceneManager", "vuiFeedBack success");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void updateSceneElementAttr(final VuiScene vuiScene, boolean z) {
        Handler handler = this.mApiRouterHandler;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.xiaopeng.speech.vui.VuiSceneManager.20
                @Override // java.lang.Runnable
                public void run() {
                    VuiScene vuiScene2;
                    String sceneId;
                    if (VuiUtils.cannotUpload() || (vuiScene2 = vuiScene) == null || vuiScene2.getElements() == null || vuiScene.getElements().size() < 0 || (sceneId = vuiScene.getSceneId()) == null) {
                        return;
                    }
                    VuiSceneCache sceneCache = VuiSceneCacheFactory.instance().getSceneCache(VuiSceneCacheFactory.CacheType.BUILD.getType());
                    VuiSceneCache sceneCache2 = VuiSceneCacheFactory.instance().getSceneCache(VuiSceneCacheFactory.CacheType.UPDATE.getType());
                    int type = VuiSceneCacheFactory.CacheType.DEFAULT.getType();
                    List<VuiElement> list = null;
                    if (sceneCache2 != null) {
                        type = sceneCache2.getFusionType(sceneId);
                        if (type == VuiSceneCacheFactory.CacheType.UPDATE.getType()) {
                            list = sceneCache2.getUpdateFusionCache(sceneId, vuiScene.getElements(), true);
                        } else if (type == VuiSceneCacheFactory.CacheType.BUILD.getType() && sceneCache != null) {
                            list = sceneCache.getFusionCache(sceneId, vuiScene.getElements(), true);
                        }
                        if (list != null) {
                            vuiScene.setElements(list);
                        }
                    }
                    String vuiSceneConvertToString = VuiUtils.vuiSceneConvertToString(vuiScene);
                    if (VuiSceneManager.this.isUploadScene(sceneId)) {
                        if (type == VuiSceneCacheFactory.CacheType.BUILD.getType()) {
                            VuiSceneManager.this.sendBuildCacheInOther(sceneId, vuiScene, sceneCache);
                            return;
                        }
                        Uri.Builder builder = new Uri.Builder();
                        builder.authority(VuiSceneManager.this.getAuthority()).path("updateScene").appendQueryParameter(VuiConstants.SCENE_ID, sceneId).appendQueryParameter(VuiConstants.SCENE_PACKAGE_NAME, vuiScene.getPackageName()).appendQueryParameter("packageVersion", vuiScene.getVersion()).appendQueryParameter("sceneData", vuiSceneConvertToString);
                        try {
                            LogUtils.logDebug("VuiSceneManager", " updateSceneElementAttr to CarSpeech" + sceneId);
                            String str = (String) ApiRouter.route(builder.build());
                            LogUtils.logInfo("VuiSceneManager", "updateSceneElementAttr to CarSpeech success" + sceneId + ",result:" + str);
                            if (TextUtils.isEmpty(str)) {
                                if (sceneCache2 != null) {
                                    sceneCache2.setCache(sceneId, vuiScene.getElements());
                                }
                            } else if (sceneCache2 != null) {
                                sceneCache2.removeCache(sceneId);
                            }
                        } catch (RemoteException e) {
                            e.printStackTrace();
                            LogUtils.e("VuiSceneManager", "updateSceneElementAttr " + e.fillInStackTrace());
                            if (sceneCache2 != null) {
                                sceneCache2.setCache(sceneId, vuiScene.getElements());
                            }
                        }
                        if (sceneCache != null) {
                            List<VuiElement> fusionCache = sceneCache.getFusionCache(sceneId, vuiScene.getElements(), true);
                            if (fusionCache != null) {
                                sceneCache.setCache(sceneId, fusionCache);
                            }
                            vuiScene.setElements(fusionCache);
                        }
                        if ("user".equals(Build.TYPE) || LogUtils.getLogLevel() > LogUtils.LOG_DEBUG_LEVEL) {
                            return;
                        }
                        LogUtils.logDebug("VuiSceneManager", "updateSceneElementAttr " + VuiUtils.vuiSceneConvertToString(vuiScene));
                        return;
                    }
                    if (type != VuiSceneCacheFactory.CacheType.BUILD.getType()) {
                        if (sceneCache2 != null) {
                            sceneCache2.setCache(sceneId, ((VuiScene) new Gson().fromJson(vuiSceneConvertToString, (Class<Object>) VuiScene.class)).getElements());
                        }
                        if (sceneCache != null) {
                            List<VuiElement> fusionCache2 = sceneCache.getFusionCache(sceneId, vuiScene.getElements(), true);
                            if (fusionCache2 != null) {
                                sceneCache.setCache(sceneId, fusionCache2);
                            }
                            vuiScene.setElements(fusionCache2);
                        }
                    } else if (sceneCache != null) {
                        sceneCache.setCache(sceneId, vuiScene.getElements());
                    }
                    if ("user".equals(Build.TYPE) || LogUtils.getLogLevel() > LogUtils.LOG_DEBUG_LEVEL) {
                        return;
                    }
                    LogUtils.logDebug("VuiSceneManager", "updateSceneElementAttr cache" + VuiUtils.vuiSceneConvertToString(vuiScene));
                }
            });
        }
    }

    public void updateDynamicScene(final VuiScene vuiScene, final boolean z) {
        Handler handler = this.mApiRouterHandler;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.xiaopeng.speech.vui.VuiSceneManager.21
                @Override // java.lang.Runnable
                public void run() {
                    VuiScene vuiScene2;
                    String sceneId;
                    if (VuiUtils.cannotUpload() || (vuiScene2 = vuiScene) == null || vuiScene2.getElements() == null || vuiScene.getElements().size() < 0 || (sceneId = vuiScene.getSceneId()) == null || VuiSceneManager.this.getSceneInfo(sceneId) == null) {
                        return;
                    }
                    VuiSceneCache sceneCache = VuiSceneCacheFactory.instance().getSceneCache(VuiSceneCacheFactory.CacheType.BUILD.getType());
                    VuiSceneCache sceneCache2 = VuiSceneCacheFactory.instance().getSceneCache(VuiSceneCacheFactory.CacheType.UPDATE.getType());
                    if (!z) {
                        if (VuiSceneManager.this.isUploadScene(sceneId) && sceneCache2.getFusionType(sceneId) == VuiSceneCacheFactory.CacheType.UPDATE.getType()) {
                            String vuiSceneConvertToString = VuiUtils.vuiSceneConvertToString(vuiScene);
                            Uri.Builder builder = new Uri.Builder();
                            builder.authority(VuiSceneManager.this.getAuthority()).path("updateScene").appendQueryParameter(VuiConstants.SCENE_ID, sceneId).appendQueryParameter(VuiConstants.SCENE_PACKAGE_NAME, vuiScene.getPackageName()).appendQueryParameter("packageVersion", vuiScene.getVersion()).appendQueryParameter("sceneData", vuiSceneConvertToString);
                            try {
                                LogUtils.logDebug("VuiSceneManager", " updateScene to CarSpeech " + sceneId);
                                String str = (String) ApiRouter.route(builder.build());
                                if (TextUtils.isEmpty(str)) {
                                    if (sceneCache2 != null) {
                                        sceneCache2.setCache(sceneId, vuiScene.getElements());
                                    }
                                } else if (sceneCache2 != null) {
                                    sceneCache2.removeCache(sceneId);
                                }
                                LogUtils.logInfo("VuiSceneManager", " updateScene to CarSpeech success" + sceneId + ",result:" + str);
                                return;
                            } catch (RemoteException e) {
                                e.printStackTrace();
                                LogUtils.e("VuiSceneManager", "updateScene " + e.fillInStackTrace());
                                if (sceneCache2 != null) {
                                    sceneCache2.setCache(sceneId, vuiScene.getElements());
                                    return;
                                }
                                return;
                            }
                        }
                        return;
                    }
                    int type = VuiSceneCacheFactory.CacheType.DEFAULT.getType();
                    List<VuiElement> list = null;
                    if (sceneCache2 != null) {
                        type = sceneCache2.getFusionType(sceneId);
                        if (type == VuiSceneCacheFactory.CacheType.UPDATE.getType()) {
                            list = sceneCache2.getUpdateFusionCache(sceneId, vuiScene.getElements(), false);
                        } else if (type == VuiSceneCacheFactory.CacheType.BUILD.getType() && sceneCache != null) {
                            list = sceneCache.getCache(sceneId);
                        }
                        if (list != null) {
                            vuiScene.setElements(list);
                        }
                    }
                    if (VuiSceneManager.this.isUploadScene(sceneId)) {
                        if (type == VuiSceneCacheFactory.CacheType.BUILD.getType()) {
                            VuiSceneManager.this.sendBuildCacheInOther(sceneId, vuiScene, sceneCache);
                            return;
                        }
                        String vuiSceneConvertToString2 = VuiUtils.vuiSceneConvertToString(vuiScene);
                        Uri.Builder builder2 = new Uri.Builder();
                        builder2.authority(VuiSceneManager.this.getAuthority()).path("updateScene").appendQueryParameter(VuiConstants.SCENE_ID, sceneId).appendQueryParameter(VuiConstants.SCENE_PACKAGE_NAME, vuiScene.getPackageName()).appendQueryParameter("packageVersion", vuiScene.getVersion()).appendQueryParameter("sceneData", vuiSceneConvertToString2);
                        try {
                            LogUtils.logDebug("VuiSceneManager", " updateScene to CarSpeech" + sceneId);
                            String str2 = (String) ApiRouter.route(builder2.build());
                            LogUtils.logInfo("VuiSceneManager", "updateScene to CarSpeech success" + sceneId + ",result:" + str2);
                            if (TextUtils.isEmpty(str2)) {
                                if (sceneCache2 != null) {
                                    sceneCache2.setCache(sceneId, vuiScene.getElements());
                                }
                            } else if (sceneCache2 != null) {
                                sceneCache2.removeCache(sceneId);
                            }
                        } catch (RemoteException e2) {
                            e2.printStackTrace();
                            LogUtils.e("VuiSceneManager", "updateScene " + e2.fillInStackTrace());
                            if (sceneCache2 != null) {
                                sceneCache2.setCache(sceneId, vuiScene.getElements());
                            }
                        }
                    } else if (type == VuiSceneCacheFactory.CacheType.BUILD.getType() || sceneCache2 == null) {
                    } else {
                        sceneCache2.setCache(sceneId, vuiScene.getElements());
                    }
                }
            });
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0083, code lost:
        if (isNeedBuild(r6) != false) goto L14;
     */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00e6 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00e7 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String enterScene(final java.lang.String r6, final java.lang.String r7, boolean r8, final java.lang.String r9) {
        /*
            r5 = this;
            java.lang.String r0 = "VuiSceneManager"
            java.lang.String r1 = ""
            if (r6 == 0) goto Le8
            if (r7 != 0) goto La
            goto Le8
        La:
            boolean r2 = com.xiaopeng.speech.vui.utils.VuiUtils.canUseVuiFeature()     // Catch: java.lang.Exception -> Lc1
            if (r2 != 0) goto L11
            return r1
        L11:
            int r2 = r5.getVuiSceneState(r6)     // Catch: java.lang.Exception -> Lc1
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> Lc1
            r3.<init>()     // Catch: java.lang.Exception -> Lc1
            java.lang.String r4 = "enterScene: sceneState:"
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch: java.lang.Exception -> Lc1
            java.lang.StringBuilder r3 = r3.append(r2)     // Catch: java.lang.Exception -> Lc1
            java.lang.String r4 = ",sceneId:"
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch: java.lang.Exception -> Lc1
            java.lang.StringBuilder r3 = r3.append(r6)     // Catch: java.lang.Exception -> Lc1
            java.lang.String r3 = r3.toString()     // Catch: java.lang.Exception -> Lc1
            com.xiaopeng.speech.vui.utils.LogUtils.logInfo(r0, r3)     // Catch: java.lang.Exception -> Lc1
            com.xiaopeng.speech.vui.model.VuiSceneState r3 = com.xiaopeng.speech.vui.model.VuiSceneState.INIT     // Catch: java.lang.Exception -> Lc1
            int r3 = r3.getState()     // Catch: java.lang.Exception -> Lc1
            r4 = 0
            if (r2 != r3) goto L4d
            if (r8 == 0) goto L43
            r5.cleanExpiredSceneTime(r6)     // Catch: java.lang.Exception -> Lc1
        L43:
            com.xiaopeng.speech.vui.model.VuiSceneState r2 = com.xiaopeng.speech.vui.model.VuiSceneState.ACTIVE     // Catch: java.lang.Exception -> Lc1
            int r2 = r2.getState()     // Catch: java.lang.Exception -> Lc1
            r5.updateSceneState(r6, r2)     // Catch: java.lang.Exception -> Lc1
            goto L87
        L4d:
            com.xiaopeng.speech.vui.model.VuiSceneState r3 = com.xiaopeng.speech.vui.model.VuiSceneState.UNACTIVE     // Catch: java.lang.Exception -> Lc1
            int r3 = r3.getState()     // Catch: java.lang.Exception -> Lc1
            if (r2 != r3) goto L69
            boolean r2 = r5.isNeedBuild(r6)     // Catch: java.lang.Exception -> Lc1
            if (r2 == 0) goto L5c
            goto L5d
        L5c:
            r4 = r1
        L5d:
            com.xiaopeng.speech.vui.model.VuiSceneState r2 = com.xiaopeng.speech.vui.model.VuiSceneState.ACTIVE     // Catch: java.lang.Exception -> L67
            int r2 = r2.getState()     // Catch: java.lang.Exception -> L67
            r5.updateSceneState(r6, r2)     // Catch: java.lang.Exception -> L67
            goto L87
        L67:
            r6 = move-exception
            goto Lc3
        L69:
            com.xiaopeng.speech.vui.model.VuiSceneState r3 = com.xiaopeng.speech.vui.model.VuiSceneState.IDLE     // Catch: java.lang.Exception -> Lc1
            int r3 = r3.getState()     // Catch: java.lang.Exception -> Lc1
            if (r2 != r3) goto L77
            java.lang.String r2 = "未注册场景信息，场景数据将不能使用"
            com.xiaopeng.speech.vui.utils.LogUtils.e(r0, r2)     // Catch: java.lang.Exception -> Lc1
            goto L86
        L77:
            com.xiaopeng.speech.vui.model.VuiSceneState r3 = com.xiaopeng.speech.vui.model.VuiSceneState.ACTIVE     // Catch: java.lang.Exception -> Lc1
            int r3 = r3.getState()     // Catch: java.lang.Exception -> Lc1
            if (r2 != r3) goto L86
            boolean r2 = r5.isNeedBuild(r6)     // Catch: java.lang.Exception -> Lc1
            if (r2 == 0) goto L86
            goto L87
        L86:
            r4 = r1
        L87:
            java.lang.String r2 = "com.xiaopeng.montecarlo"
            java.lang.String r3 = r5.mPackageName     // Catch: java.lang.Exception -> L67
            boolean r2 = r2.equals(r3)     // Catch: java.lang.Exception -> L67
            if (r2 == 0) goto La5
            java.lang.String r2 = r5.mPackageName     // Catch: java.lang.Exception -> L67
            java.lang.String r3 = r5.getTopRunningPackageName()     // Catch: java.lang.Exception -> L67
            boolean r2 = r2.equals(r3)     // Catch: java.lang.Exception -> L67
            if (r2 != 0) goto La5
            boolean r6 = com.xiaopeng.speech.vui.utils.VuiUtils.cannotUpload()     // Catch: java.lang.Exception -> L67
            if (r6 == 0) goto La4
            return r1
        La4:
            return r4
        La5:
            if (r8 == 0) goto Le0
            boolean r8 = r5.isUploadScene(r6)     // Catch: java.lang.Exception -> L67
            if (r8 == 0) goto Lb6
            boolean r8 = com.xiaopeng.speech.vui.utils.VuiUtils.cannotUpload()     // Catch: java.lang.Exception -> L67
            if (r8 != 0) goto Lb6
            r5.sendSceneData(r6)     // Catch: java.lang.Exception -> L67
        Lb6:
            android.os.Handler r8 = r5.mApiRouterHandler     // Catch: java.lang.Exception -> L67
            com.xiaopeng.speech.vui.VuiSceneManager$22 r2 = new com.xiaopeng.speech.vui.VuiSceneManager$22     // Catch: java.lang.Exception -> L67
            r2.<init>()     // Catch: java.lang.Exception -> L67
            r8.post(r2)     // Catch: java.lang.Exception -> L67
            goto Le0
        Lc1:
            r6 = move-exception
            r4 = r1
        Lc3:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "enterScene--------------e: "
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.Throwable r8 = r6.fillInStackTrace()
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r7 = r7.toString()
            com.xiaopeng.speech.vui.utils.LogUtils.e(r0, r7)
            r6.printStackTrace()
        Le0:
            boolean r6 = com.xiaopeng.speech.vui.utils.VuiUtils.cannotUpload()
            if (r6 == 0) goto Le7
            return r1
        Le7:
            return r4
        Le8:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.speech.vui.VuiSceneManager.enterScene(java.lang.String, java.lang.String, boolean, java.lang.String):java.lang.String");
    }

    private void updateSceneState(String str, int i) {
        VuiSceneInfo sceneInfo = getSceneInfo(str);
        if (sceneInfo != null) {
            sceneInfo.setState(i);
        }
    }

    public void exitScene(String str, String str2, boolean z, String str3) {
        exitScene(str, str2, z, str3, false);
    }

    public void destroyScene(final String str) {
        Handler handler;
        if (str == null || (handler = this.mApiRouterHandler) == null) {
            return;
        }
        handler.post(new Runnable() { // from class: com.xiaopeng.speech.vui.VuiSceneManager.23
            @Override // java.lang.Runnable
            public void run() {
                VuiSceneBuildCache vuiSceneBuildCache = (VuiSceneBuildCache) VuiSceneCacheFactory.instance().getSceneCache(VuiSceneCacheFactory.CacheType.BUILD.getType());
                if (vuiSceneBuildCache != null && vuiSceneBuildCache.getUploadedState(str) && !VuiUtils.cannotUpload()) {
                    try {
                        Uri.Builder builder = new Uri.Builder();
                        if (!VuiSceneManager.this.hasProcessFeature()) {
                            builder.authority(VuiSceneManager.this.getAuthority()).path("destroyScene").appendQueryParameter("sceneIds", str).appendQueryParameter(VuiConstants.SCENE_PACKAGE_NAME, VuiSceneManager.this.mPackageName).appendQueryParameter("packageVersion", VuiSceneManager.this.mPackageVersion);
                        } else {
                            builder.authority(VuiSceneManager.this.getAuthority()).path("destroyProcessScene").appendQueryParameter("sceneIds", str).appendQueryParameter(VuiConstants.SCENE_PACKAGE_NAME, VuiSceneManager.this.mPackageName).appendQueryParameter("packageVersion", VuiSceneManager.this.mPackageVersion).appendQueryParameter("processName", VuiSceneManager.this.getProcessName());
                        }
                        LogUtils.logDebug("VuiSceneManager", "destroyScene-------------- " + str);
                        ApiRouter.route(builder.build());
                        LogUtils.logDebug("VuiSceneManager", "destroyScene--------------success " + str);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                LogUtils.logDebug("VuiSceneManager", "destory removeAllCache--------------" + str);
                VuiSceneCacheFactory.instance().removeAllCache(str);
            }
        });
    }

    public void cleanExpiredSceneTime(final String str) {
        Handler handler;
        if (str == null || (handler = this.mApiRouterHandler) == null) {
            return;
        }
        handler.post(new Runnable() { // from class: com.xiaopeng.speech.vui.VuiSceneManager.24
            @Override // java.lang.Runnable
            public void run() {
                Uri.Builder builder = new Uri.Builder();
                builder.authority(VuiSceneManager.this.getAuthority()).path("cleanExpiredSceneTime").appendQueryParameter(VuiConstants.SCENE_ID, str);
                LogUtils.logDebug("VuiSceneManager", "cleanExpiredSceneTime-------------- " + str);
                try {
                    ApiRouter.route(builder.build());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public VuiSceneInfo getSceneInfo(String str) {
        ConcurrentHashMap<String, VuiSceneInfo> concurrentHashMap;
        if (str == null || (concurrentHashMap = this.mVuiSceneInfoMap) == null || !concurrentHashMap.containsKey(str)) {
            return null;
        }
        return this.mVuiSceneInfoMap.get(str);
    }

    public void setWholeSceneId(String str, String str2) {
        VuiSceneInfo sceneInfo = getSceneInfo(str);
        if (sceneInfo != null) {
            sceneInfo.setWholeSceneId(str2);
            this.mVuiSceneInfoMap.put(str, sceneInfo);
        }
    }

    public void setIsWholeScene(String str, boolean z) {
        VuiSceneInfo sceneInfo = getSceneInfo(str);
        if (sceneInfo != null) {
            sceneInfo.setWholeScene(z);
            this.mVuiSceneInfoMap.put(str, sceneInfo);
        }
    }

    public void addSubSceneIds(String str, List<String> list) {
        VuiSceneInfo vuiSceneInfo;
        if (this.mVuiSceneInfoMap.containsKey(str) && (vuiSceneInfo = this.mVuiSceneInfoMap.get(str)) != null) {
            List<String> subSceneList = vuiSceneInfo.getSubSceneList();
            if (subSceneList == null) {
                subSceneList = new ArrayList<>();
            }
            subSceneList.addAll(list);
            vuiSceneInfo.setSubSceneList(subSceneList);
            this.mVuiSceneInfoMap.put(str, vuiSceneInfo);
        }
    }

    public void removeSubSceneIds(String str, String str2) {
        VuiSceneInfo vuiSceneInfo;
        if (this.mVuiSceneInfoMap.containsKey(str) && (vuiSceneInfo = this.mVuiSceneInfoMap.get(str)) != null) {
            List<String> subSceneList = vuiSceneInfo.getSubSceneList();
            if (subSceneList == null) {
                subSceneList = new ArrayList<>();
            }
            if (subSceneList.contains(str2)) {
                subSceneList.remove(str2);
            }
            vuiSceneInfo.setSubSceneList(subSceneList);
            this.mVuiSceneInfoMap.put(str, vuiSceneInfo);
        }
    }

    public void setSceneIdList(String str, List<String> list) {
        VuiSceneInfo sceneInfo = getSceneInfo(str);
        if (sceneInfo != null) {
            sceneInfo.setIdList(list);
            this.mVuiSceneInfoMap.put(str, sceneInfo);
        }
    }

    public List<String> getSceneIdList(String str) {
        VuiSceneInfo sceneInfo = getSceneInfo(str);
        if (sceneInfo != null) {
            return sceneInfo.getIdList();
        }
        return null;
    }

    public void addVuiSceneListener(String str, View view, IVuiSceneListener iVuiSceneListener, IVuiElementChangedListener iVuiElementChangedListener, boolean z) {
        LogUtils.logInfo("VuiSceneManager", "addVuiSceneListener-- " + str + ",needBuild:" + z);
        if (VuiUtils.canUseVuiFeature()) {
            LogUtils.logInfo("VuiSceneManager", "sceneId-- " + str + ",listener:" + iVuiSceneListener);
            if (TextUtils.isEmpty(str) || iVuiSceneListener == null) {
                LogUtils.logInfo("VuiSceneManager", "sceneId-- " + str + ",listener:" + iVuiSceneListener);
                LogUtils.e("VuiSceneManager", "场景注册时所需变量不能为空");
            } else if (view == null && !(iVuiSceneListener instanceof IXpVuiSceneListener) && !VuiUtils.is3DApp(this.mPackageName) && !VuiUtils.isThirdApp(this.mPackageName) && !str.endsWith(VuiManager.SUFFIX_OF_DIALOG_SCENE) && !str.endsWith("dialog")) {
                LogUtils.e("VuiSceneManager", "场景注册时所需变量不能为空");
            } else {
                if (z && (this.mVuiSceneInfoMap.containsKey(str) || this.sceneIds.contains(str))) {
                    if (iVuiSceneListener != null && iVuiSceneListener.equals(getVuiSceneListener(str))) {
                        LogUtils.w("VuiSceneManager", "上次场景撤销未调用removeVuiSceneListener或重复创建场景");
                    }
                    VuiSceneCacheFactory.instance().removeAllCache(str);
                }
                VuiSceneInfo vuiSceneInfo = new VuiSceneInfo();
                boolean z2 = true;
                if (this.mVuiSubSceneInfoMap.containsKey(str)) {
                    vuiSceneInfo = this.mVuiSubSceneInfoMap.get(str);
                    this.mVuiSubSceneInfoMap.remove(str);
                } else {
                    if (this.mVuiSceneInfoMap.containsKey(str)) {
                        vuiSceneInfo = this.mVuiSceneInfoMap.get(str);
                        if (z) {
                            vuiSceneInfo.reset(true);
                        }
                    }
                    z2 = false;
                }
                vuiSceneInfo.setListener(iVuiSceneListener);
                vuiSceneInfo.setRootView(view);
                vuiSceneInfo.setElementChangedListener(iVuiElementChangedListener);
                if (z) {
                    vuiSceneInfo.setState(VuiSceneState.INIT.getState());
                } else if (vuiSceneInfo.isBuildComplete()) {
                    vuiSceneInfo.setState(VuiSceneState.UNACTIVE.getState());
                } else {
                    vuiSceneInfo.reset(false);
                    VuiSceneCacheFactory.instance().removeAllCache(str);
                }
                LogUtils.logDebug("VuiSceneManager", "build:" + vuiSceneInfo.isBuild());
                this.mVuiSceneInfoMap.put(str, vuiSceneInfo);
                if (iVuiSceneListener != null && z2) {
                    LogUtils.i("VuiSceneManager", "onBuildScene");
                    iVuiSceneListener.onBuildScene();
                }
                if (this.sceneIds.contains(str)) {
                    return;
                }
                this.sceneIds.add(str);
            }
        }
    }

    public void initSubSceneInfo(String str, String str2) {
        LogUtils.d("VuiSceneManager", "initSubSceneInfo subSceneId:" + str + ",sceneId:" + str2);
        VuiSceneInfo vuiSceneInfo = new VuiSceneInfo();
        if (this.mVuiSubSceneInfoMap.containsKey(str)) {
            vuiSceneInfo = this.mVuiSubSceneInfoMap.get(str);
        }
        vuiSceneInfo.setWholeScene(false);
        vuiSceneInfo.setWholeSceneId(str2);
        this.mVuiSubSceneInfoMap.put(str, vuiSceneInfo);
    }

    public void removeVuiSceneListener(String str, boolean z, boolean z2, IVuiSceneListener iVuiSceneListener) {
        if (VuiUtils.canUseVuiFeature()) {
            if (str == null) {
                LogUtils.e("VuiSceneManager", "销毁场景时SceneId不能为空");
                return;
            }
            if (!this.mVuiSceneInfoMap.containsKey(str) || !this.sceneIds.contains(str)) {
                LogUtils.w("VuiSceneManager", "销毁场景前请先注册场景，重复销毁信息");
            }
            if (this.mVuiSceneInfoMap.containsKey(str)) {
                VuiSceneInfo vuiSceneInfo = this.mVuiSceneInfoMap.get(str);
                if (iVuiSceneListener != null && vuiSceneInfo.getListener() != null && !iVuiSceneListener.equals(vuiSceneInfo.getListener())) {
                    LogUtils.w("VuiSceneManager", "要销毁的场景和目前持有的场景数据不一致");
                    return;
                } else if (z2) {
                    LogUtils.logInfo("VuiSceneManager", "removeVuiSceneListener-------------- " + z2 + ",info:" + vuiSceneInfo);
                    vuiSceneInfo.resetViewInfo();
                    this.mVuiSceneInfoMap.put(str, vuiSceneInfo);
                } else {
                    List<String> subSceneList = vuiSceneInfo.getSubSceneList();
                    if (subSceneList != null) {
                        for (String str2 : subSceneList) {
                            VuiSceneInfo vuiSceneInfo2 = this.mVuiSceneInfoMap.get(str2);
                            if (vuiSceneInfo2 != null && vuiSceneInfo2.getWholeSceneId() != null && vuiSceneInfo2.getWholeSceneId().contains(str)) {
                                vuiSceneInfo2.getWholeSceneId().remove(str);
                                this.mVuiSceneInfoMap.put(str2, vuiSceneInfo2);
                            }
                        }
                    }
                    vuiSceneInfo.reset(true);
                    this.mVuiSceneInfoMap.remove(str);
                    if (this.sceneIds.contains(str)) {
                        this.sceneIds.remove(str);
                    }
                }
            }
            if (z2) {
                return;
            }
            if (z) {
                destroyScene(str);
            } else {
                VuiSceneCacheFactory.instance().removeAllCache(str);
            }
        }
    }

    public IVuiSceneListener getVuiSceneListener(String str) {
        VuiSceneInfo sceneInfo = getSceneInfo(str);
        if (sceneInfo != null) {
            return sceneInfo.getListener();
        }
        return null;
    }

    public View getRootView(String str) {
        VuiSceneInfo sceneInfo = getSceneInfo(str);
        if (sceneInfo != null) {
            return sceneInfo.getRootView();
        }
        return null;
    }

    public void setmPackageName(String str) {
        this.mPackageName = str;
    }

    public void setmPackageVersion(String str) {
        this.mPackageVersion = str;
    }

    public String getmPackageName() {
        return this.mPackageName;
    }

    public String getmPackageVersion() {
        return this.mPackageVersion;
    }

    public boolean getVuiSceneBuild(String str) {
        VuiSceneInfo sceneInfo = getSceneInfo(str);
        if (sceneInfo != null) {
            return sceneInfo.isBuild();
        }
        return false;
    }

    public int getVuiSceneState(String str) {
        VuiSceneInfo sceneInfo = getSceneInfo(str);
        if (sceneInfo != null) {
            return sceneInfo.getState();
        }
        return VuiSceneState.IDLE.getState();
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    private String getTopRunningPackageName() {
        List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) this.mContext.getSystemService(IScenarioController.SPACE_CAPSULE_SOURCE_ACTIVITY)).getRunningTasks(1);
        if (runningTasks == null || runningTasks.size() <= 0) {
            return null;
        }
        return runningTasks.get(0).topActivity.getPackageName();
    }

    public String checkScrollSubViewIsVisible(String str, String str2) {
        if (str == null || str2 == null || VuiUtils.cannotUpload()) {
            return "";
        }
        try {
            JSONObject jSONObject = new JSONObject(str2);
            JSONArray optJSONArray = jSONObject.optJSONArray(VuiConstants.SCENE_ELEMENTS);
            if (optJSONArray != null || optJSONArray.length() > 0) {
                JSONObject jSONObject2 = null;
                JSONArray jSONArray = new JSONArray();
                if (!VuiUtils.is3DApp(VuiUtils.getPackageNameFromSceneId(str))) {
                    for (int i = 0; i < optJSONArray.length(); i++) {
                        JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                        if (optJSONObject != null) {
                            String optString = optJSONObject.optString("elementId");
                            String optString2 = optJSONObject.optString("scrollViewId");
                            JSONObject jSONObject3 = new JSONObject();
                            jSONObject3.put("elementId", optString);
                            VuiEventInfo findView = findView(str, optString);
                            VuiEventInfo findView2 = findView(str, optString2);
                            if (findView != null && findView.hitView != null) {
                                if (findView2 != null && findView2.hitView != null) {
                                    if (findView2.hitView instanceof ScrollView) {
                                        Rect rect = new Rect();
                                        findView2.hitView.getHitRect(rect);
                                        if (findView.hitView.getLocalVisibleRect(rect)) {
                                            jSONObject3.put("visible", true);
                                        } else {
                                            jSONObject3.put("visible", false);
                                        }
                                    }
                                } else {
                                    jSONObject3.put("visible", true);
                                }
                            }
                            jSONObject2 = jSONObject3;
                        }
                        jSONArray.put(jSONObject2);
                    }
                }
                jSONObject.put(VuiConstants.SCENE_ELEMENTS, jSONArray);
                return String.valueOf(jSONObject);
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getElementState(String str, String str2) {
        VuiElement vuiElementById;
        ViewPager viewPager;
        JSONObject jSONObject;
        JSONObject vuiProps;
        if (str == null || str2 == null) {
            return null;
        }
        if (VuiUtils.cannotUpload() || (vuiElementById = VuiSceneCacheFactory.instance().getSceneCache(VuiSceneCacheFactory.CacheType.BUILD.getType()).getVuiElementById(str, str2)) == null) {
            return null;
        }
        boolean is3DApp = VuiUtils.is3DApp(VuiUtils.getPackageNameFromSceneId(str));
        IVuiSceneListener vuiSceneListener = instance().getVuiSceneListener(str);
        VuiEventInfo findView = (VuiUtils.isThirdApp(VuiUtils.getPackageNameFromSceneId(str)) || is3DApp || (vuiSceneListener != null && (vuiSceneListener instanceof IXpVuiSceneListener))) ? null : findView(str, str2);
        String str3 = "VuiSceneManager";
        if (VuiElementType.RECYCLEVIEW.getType().equals(vuiElementById.getType())) {
            if (findView != null && findView.hitView != null && (findView.hitView instanceof RecyclerView)) {
                RecyclerView recyclerView = (RecyclerView) findView.hitView;
                if ((recyclerView instanceof IVuiElement) && (vuiProps = ((IVuiElement) recyclerView).getVuiProps()) != null && vuiProps.has(VuiConstants.PROPS_DISABLETPL)) {
                    try {
                        if (vuiProps.getBoolean(VuiConstants.PROPS_DISABLETPL)) {
                            JSONObject jSONObject2 = new JSONObject();
                            jSONObject2.put(VuiConstants.PROPS_SCROLLUP, true);
                            jSONObject2.put(VuiConstants.PROPS_SCROLLDOWN, true);
                            return jSONObject2.toString();
                        }
                    } catch (Exception unused) {
                    }
                }
                boolean canScrollVertically = recyclerView.canScrollVertically(-1);
                boolean canScrollVertically2 = recyclerView.canScrollVertically(1);
                boolean canScrollHorizontally = recyclerView.canScrollHorizontally(-1);
                boolean canScrollHorizontally2 = recyclerView.canScrollHorizontally(1);
                try {
                    jSONObject = new JSONObject();
                } catch (JSONException e) {
                    e = e;
                }
                try {
                    if (((IVuiElement) recyclerView).getVuiAction().equals(VuiAction.SCROLLBYY.getName())) {
                        jSONObject.put(VuiConstants.PROPS_SCROLLUP, canScrollVertically);
                        jSONObject.put(VuiConstants.PROPS_SCROLLDOWN, canScrollVertically2);
                    } else {
                        jSONObject.put(VuiConstants.PROPS_SCROLLLEFT, canScrollHorizontally);
                        jSONObject.put(VuiConstants.PROPS_SCROLLRIGHT, canScrollHorizontally2);
                    }
                    str3 = "VuiSceneManager";
                    LogUtils.logInfo(str3, "getElementState jsonObject: " + jSONObject.toString() + ",sceneId:" + str + ",elementId:" + str2);
                    return jSONObject.toString();
                } catch (JSONException e2) {
                    e = e2;
                    str3 = "VuiSceneManager";
                    LogUtils.e(str3, "getElementState e:" + e.getMessage());
                    return null;
                }
            } else if (vuiElementById.getProps() != null) {
                return vuiElementById.getProps().toString();
            } else {
                try {
                    JSONObject jSONObject3 = new JSONObject();
                    jSONObject3.put(VuiConstants.PROPS_SCROLLUP, true);
                    jSONObject3.put(VuiConstants.PROPS_SCROLLDOWN, true);
                    LogUtils.logInfo("VuiSceneManager", "getElementState jsonObject: " + jSONObject3.toString() + ",sceneId:" + str + ",elementId:" + str2);
                    return jSONObject3.toString();
                } catch (JSONException e3) {
                    LogUtils.e("VuiSceneManager", "getElementState e:" + e3.getMessage());
                    return null;
                }
            }
        } else if (VuiElementType.VIEWPAGER.getType().equals(vuiElementById.getType())) {
            if (VuiUtils.getExtraPage(vuiElementById) != -1) {
                try {
                    JSONObject jSONObject4 = new JSONObject();
                    jSONObject4.put(VuiConstants.PROPS_SCROLLLEFT, true);
                    jSONObject4.put(VuiConstants.PROPS_SCROLLRIGHT, true);
                    LogUtils.logInfo("VuiSceneManager", "getElementState jsonObject: " + jSONObject4.toString() + ",sceneId:" + str + ",elementId:" + str2);
                    return jSONObject4.toString();
                } catch (JSONException e4) {
                    LogUtils.e("VuiSceneManager", "getElementState e:" + e4.getMessage());
                    return null;
                }
            } else if (findView == null || findView.hitView == null) {
                return null;
            } else {
                if (!(findView.hitView instanceof ViewPager)) {
                    viewPager = VuiUtils.findViewPager(findView.hitView);
                } else {
                    viewPager = (ViewPager) findView.hitView;
                }
                if (viewPager != null) {
                    try {
                        boolean canScrollHorizontally3 = viewPager.canScrollHorizontally(-1);
                        boolean canScrollHorizontally4 = viewPager.canScrollHorizontally(1);
                        JSONObject jSONObject5 = new JSONObject();
                        jSONObject5.put(VuiConstants.PROPS_SCROLLLEFT, canScrollHorizontally3);
                        jSONObject5.put(VuiConstants.PROPS_SCROLLRIGHT, canScrollHorizontally4);
                        LogUtils.logInfo("VuiSceneManager", "getElementState jsonObject: " + jSONObject5.toString() + ",sceneId:" + str + ",elementId:" + str2);
                        return jSONObject5.toString();
                    } catch (JSONException e5) {
                        LogUtils.e("VuiSceneManager", "getElementState e:" + e5.getMessage());
                        return null;
                    }
                }
                return null;
            }
        } else {
            boolean z = false;
            if (VuiElementType.SCROLLVIEW.getType().equals(vuiElementById.getType())) {
                if (vuiElementById == null || vuiElementById.getActions() == null) {
                    return null;
                }
                ArrayList arrayList = new ArrayList(vuiElementById.actions.entrySet());
                if (arrayList.isEmpty()) {
                    return null;
                }
                if (VuiAction.SCROLLBYY.getName().equals(((Map.Entry) arrayList.get(0)).getKey())) {
                    if (findView == null || findView.hitView == null) {
                        return null;
                    }
                    if (findView.hitView instanceof ScrollView) {
                        View childAt = ((ViewGroup) findView.hitView).getChildAt(0);
                        if (childAt == null) {
                            return null;
                        }
                        try {
                            JSONObject jSONObject6 = new JSONObject();
                            int scrollY = findView.hitView.getScrollY();
                            jSONObject6.put(VuiConstants.PROPS_SCROLLUP, scrollY != 0);
                            if (scrollY + findView.hitView.getHeight() != childAt.getMeasuredHeight()) {
                                z = true;
                            }
                            jSONObject6.put(VuiConstants.PROPS_SCROLLDOWN, z);
                            LogUtils.logInfo("VuiSceneManager", "getElementState jsonObject: " + jSONObject6.toString() + ",sceneId:" + str + ",elementId:" + str2);
                            return jSONObject6.toString();
                        } catch (Exception e6) {
                            e6.printStackTrace();
                            LogUtils.e("VuiSceneManager", "getElementState e:" + e6.getMessage());
                            return null;
                        }
                    }
                    Rect rect = new Rect();
                    findView.hitView.getGlobalVisibleRect(rect);
                    try {
                        JSONObject jSONObject7 = new JSONObject();
                        int scrollY2 = findView.hitView.getScrollY();
                        jSONObject7.put(VuiConstants.PROPS_SCROLLUP, scrollY2 != 0);
                        if (scrollY2 + rect.height() < findView.hitView.getMeasuredHeight()) {
                            z = true;
                        }
                        jSONObject7.put(VuiConstants.PROPS_SCROLLDOWN, z);
                        LogUtils.logInfo("VuiSceneManager", "getElementState jsonObject: " + jSONObject7.toString() + ",sceneId:" + str + ",elementId:" + str2);
                        return jSONObject7.toString();
                    } catch (Exception e7) {
                        e7.printStackTrace();
                        LogUtils.e("VuiSceneManager", "getElementState e:" + e7.getMessage());
                        return null;
                    }
                } else if (!VuiAction.SCROLLBYX.getName().equals(((Map.Entry) arrayList.get(0)).getKey()) || findView == null || findView.hitView == null || (findView.hitView instanceof ScrollView)) {
                    return null;
                } else {
                    View view = (View) findView.hitView.getParent();
                    if (view.getWidth() < findView.hitView.getWidth()) {
                        try {
                            JSONObject jSONObject8 = new JSONObject();
                            int scrollX = view.getScrollX();
                            LogUtils.e("VuiSceneManager", "view width:" + findView.hitView.getWidth() + ",parent:" + view.getWidth() + ",scrollX:" + scrollX);
                            jSONObject8.put(VuiConstants.PROPS_SCROLLLEFT, scrollX != 0);
                            if (scrollX + view.getWidth() < findView.hitView.getWidth()) {
                                z = true;
                            }
                            jSONObject8.put(VuiConstants.PROPS_SCROLLRIGHT, z);
                            LogUtils.logInfo("VuiSceneManager", "getElementState jsonObject: " + jSONObject8.toString() + ",sceneId:" + str + ",elementId:" + str2);
                            return jSONObject8.toString();
                        } catch (Exception e8) {
                            e8.printStackTrace();
                            LogUtils.e("VuiSceneManager", "getElementState e:" + e8.getMessage());
                            return null;
                        }
                    }
                    return null;
                }
            }
            if (findView != null && findView.hitView != null) {
                vuiElementById.setEnabled(findView.hitView.isEnabled() ? null : false);
            }
            String json = new Gson().toJson(vuiElementById);
            LogUtils.logInfo("VuiSceneManager", "getElementState:  result:  " + json);
            return json;
        }
    }

    public VuiEventInfo findView(String str, String str2) {
        View hitView;
        if (str != null && str2 != null) {
            try {
                VuiEventInfo findViewFromSceneInfo = findViewFromSceneInfo(str, str2);
                if (findViewFromSceneInfo != null) {
                    LogUtils.logDebug("VuiSceneManager", "findViewFromSceneInfo elementId:" + str2 + ",view:" + findViewFromSceneInfo.hitView + ",sceneId:" + findViewFromSceneInfo.sceneId);
                    return findViewFromSceneInfo;
                }
                VuiEventInfo findRootView = findRootView(str, str2);
                LogUtils.logDebug("VuiSceneManager", "findView elementId:" + str2 + ",rootView:" + (findRootView != null ? findRootView.hitView : null) + ",sceneId:" + (findRootView != null ? findRootView.sceneId : ""));
                if (findRootView != null && findRootView.hitView != null) {
                    return new VuiEventInfo(getHitView(findRootView.hitView, str2), findRootView.sceneId);
                }
                VuiSceneInfo sceneInfo = getSceneInfo(str);
                LogUtils.logInfo("VuiSceneManager", "findView view by rootview");
                View rootView = sceneInfo == null ? null : sceneInfo.getRootView();
                if (rootView != null) {
                    View hitView2 = getHitView(rootView, str2);
                    if (hitView2 == null) {
                        List<String> subSceneList = sceneInfo.getSubSceneList();
                        int size = subSceneList == null ? 0 : subSceneList.size();
                        for (int i = 0; i < size; i++) {
                            String str3 = subSceneList.get(i);
                            VuiSceneInfo sceneInfo2 = TextUtils.isEmpty(str3) ? null : getSceneInfo(str3);
                            View rootView2 = sceneInfo2 == null ? null : sceneInfo2.getRootView();
                            if (rootView2 != null && (hitView = getHitView(rootView2, str2)) != null) {
                                return new VuiEventInfo(hitView, str3);
                            }
                        }
                        return findViewFromSceneInfo;
                    }
                    return new VuiEventInfo(hitView2, str);
                }
                return findViewFromSceneInfo;
            } catch (Exception e) {
                LogUtils.e("VuiSceneManager", "findView e:" + e.getMessage());
            }
        }
        return null;
    }

    private View getHitView(View view, String str) {
        if (view != null) {
            View findViewWithTag = view.findViewWithTag(str);
            if (findViewWithTag == null) {
                View findViewWithId = findViewWithId(str, view);
                if (findViewWithId != null) {
                    LogUtils.logDebug("VuiSceneManager", "findViewWithId:   Tag====  " + findViewWithId.getTag());
                    return findViewWithId;
                }
                LogUtils.e("VuiSceneManager", "findViewWithId  View is null");
                return findViewWithId;
            }
            return findViewWithTag;
        }
        return null;
    }

    private VuiEventInfo findViewFromSceneInfo(String str, String str2) {
        VuiSceneInfo sceneInfo;
        List<SoftReference<View>> notChildrenViewList;
        if (str != null && str2 != null && (sceneInfo = getSceneInfo(str)) != null && sceneInfo.isContainNotChildrenView() && (notChildrenViewList = sceneInfo.getNotChildrenViewList()) != null) {
            for (int i = 0; i < notChildrenViewList.size(); i++) {
                SoftReference<View> softReference = notChildrenViewList.get(i);
                if (softReference != null && softReference.get() != null) {
                    View findViewWithTag = softReference.get().findViewWithTag(str2);
                    if (findViewWithTag != null) {
                        return new VuiEventInfo(findViewWithTag, str);
                    }
                    View findViewWithId = findViewWithId(str2, softReference.get());
                    if (findViewWithId != null) {
                        LogUtils.logDebug("VuiSceneManager", "findViewWithId:   Tag====  " + findViewWithId.getTag());
                        return new VuiEventInfo(findViewWithId, str);
                    }
                }
            }
        }
        return null;
    }

    private VuiEventInfo findRootView(String str, String str2) {
        VuiSceneInfo sceneInfo;
        VuiEventInfo vuiEventInfo = null;
        if (str == null || str2 == null || (sceneInfo = getSceneInfo(str)) == null) {
            return null;
        }
        LogUtils.logDebug("VuiSceneManager", "findRootView idList:" + sceneInfo.getIdList());
        if (sceneInfo.getIdList() != null && sceneInfo.getIdList().contains(str2)) {
            return new VuiEventInfo(getRootView(str), str);
        }
        List<String> subSceneList = sceneInfo.getSubSceneList();
        if (subSceneList != null) {
            LogUtils.logDebug("VuiSceneManager", "findRootView subSceneList:" + subSceneList);
        }
        if (subSceneList != null) {
            int size = subSceneList.size();
            for (int i = 0; i < size; i++) {
                vuiEventInfo = findRootView(subSceneList.get(i), str2);
                if (vuiEventInfo != null) {
                    return vuiEventInfo;
                }
            }
            return vuiEventInfo;
        }
        return null;
    }

    public View findViewWithId(String str, View view) {
        String substring;
        LogUtils.logInfo("VuiSceneManager", "findViewWithId  ===  " + str);
        if (view == null || str == null) {
            return view;
        }
        if (str.indexOf("_") != -1) {
            String substring2 = str.substring(0, str.indexOf("_"));
            if (TextUtils.isEmpty(substring2)) {
                return null;
            }
            if (substring2.length() > 4) {
                String[] split = str.split("_");
                if (split.length <= 2) {
                    substring = split[1];
                } else {
                    substring = str.substring(str.indexOf("_", 1) + 1, str.indexOf("_", str.indexOf("_") + 1));
                }
                if (substring.length() < 4) {
                    int id = ResourceUtil.getId(this.mContext, substring2);
                    LogUtils.logInfo("VuiSceneManager", "findViewWithId view tag: " + view.findViewById(id).getTag());
                    View listView = getListView(view.findViewById(id));
                    if (listView == null) {
                        return null;
                    }
                    if (listView instanceof RecyclerView) {
                        view = ((RecyclerView) listView).getLayoutManager().findViewByPosition(Integer.valueOf(substring).intValue()).findViewById(id);
                    }
                    return split.length <= 2 ? view : findViewWithId(str.substring(str.indexOf("_", str.indexOf("_") + 1) + 1), view);
                }
                return findViewWithId(str.substring(str.indexOf("_") + 1), view.findViewById(ResourceUtil.getId(this.mContext, substring2)));
            }
            return findViewWithId(str.substring(str.indexOf("_") + 1), view);
        }
        return view.findViewById(ResourceUtil.getId(this.mContext, str));
    }

    private View getListView(View view) {
        if (view == null || (view instanceof ListView) || (view instanceof RecyclerView)) {
            return view;
        }
        if (view.getParent() == null) {
            return null;
        }
        return view.getParent() instanceof ViewRootImpl ? view : getListView((View) view.getParent());
    }

    private View getScrollView(View view) {
        if (view == null || (view instanceof ListView) || (view instanceof ScrollView)) {
            return view;
        }
        if (view.getParent() == null) {
            return null;
        }
        return view.getParent() instanceof ViewRootImpl ? view : getScrollView((View) view.getParent());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isUploadScene(String str) {
        LogUtils.logDebug("VuiSceneManager", "isUploadScene sceneId:" + str + ",getTopRunningPackageName:" + getTopRunningPackageName() + ",mIsInSpeech:" + this.mIsInSpeech + ",VuiEngine.mLFActiveSceneId:" + VuiEngineImpl.mActiveSceneIds.get(VuiConstants.SCREEN_DISPLAY_LF) + " VuiEngine.mRFActiveSceneId: " + VuiEngineImpl.mActiveSceneIds.get(VuiConstants.SCREEN_DISPLAY_RF) + ",mPackageName" + this.mPackageName);
        String str2 = this.mPackageName;
        if (str2 == null || str == null) {
            return false;
        }
        if ("com.android.systemui".equals(str2)) {
            return true;
        }
        if (this.mIsInSpeech && (VuiEngineImpl.mLeftPopPanelStack.contains(str) || VuiEngineImpl.mRightPopPanelStack.contains(str))) {
            return true;
        }
        if (this.mIsInSpeech && VuiEngineImpl.mActiveSceneIds.containsValue(str) && (str.endsWith(VuiManager.SUFFIX_OF_DIALOG_SCENE) || str.endsWith("dialog"))) {
            return true;
        }
        if (this.mIsInSpeech && VuiEngineImpl.mActiveSceneIds.containsValue(str) && "com.xiaopeng.carspeechservice".equals(this.mPackageName)) {
            return true;
        }
        if ((this.mIsInSpeech && VuiEngineImpl.mActiveSceneIds.containsValue(str) && ("com.xiaopeng.car.settings".equals(this.mPackageName) || "com.xiaopeng.carcontrol".equals(this.mPackageName) || "com.xiaopeng.chargecontrol".equals(this.mPackageName) || VuiConstants.UNITY.equals(this.mPackageName))) || "com.xiaopeng.caraccount".equals(this.mPackageName)) {
            return true;
        }
        return this.mIsInSpeech && this.mPackageName.equals(getTopRunningPackageName()) && VuiEngineImpl.mActiveSceneIds.containsValue(str);
    }

    public boolean canUpdateScene(String str) {
        if (str == null) {
            return false;
        }
        if (!getVuiSceneBuild(str)) {
            LogUtils.logDebug("VuiSceneManager", str + "场景数据的update必须在场build后");
            return false;
        } else if (getVuiSceneState(str) == VuiSceneState.IDLE.getState()) {
            LogUtils.logDebug("VuiSceneManager", str + "场景数据的build必须在场景被激活后");
            return false;
        } else {
            return true;
        }
    }

    private boolean isNeedBuild(String str) {
        VuiSceneInfo sceneInfo = getSceneInfo(str);
        if (sceneInfo != null) {
            return !sceneInfo.isBuild();
        }
        return true;
    }

    public boolean canRunUpdateSceneTask(String str) {
        List<VuiElement> cache;
        if (str == null || (cache = ((VuiSceneBuildCache) VuiSceneCacheFactory.instance().getSceneCache(VuiSceneCacheFactory.CacheType.BUILD.getType())).getCache(str)) == null || cache.isEmpty()) {
            return false;
        }
        if (getVuiSceneState(str) == VuiSceneState.IDLE.getState()) {
            LogUtils.logDebug("VuiSceneManager", str + "场景数据的build必须在场景被激活后");
            return false;
        }
        return true;
    }

    public void addVuiEventListener(String str, IVuiEventListener iVuiEventListener) {
        VuiSceneInfo sceneInfo;
        LogUtils.logInfo("VuiSceneManager", "addVuiEventListener-- " + str);
        if (!VuiUtils.canUseVuiFeature() || str == null || iVuiEventListener == null || (sceneInfo = getSceneInfo(str)) == null) {
            return;
        }
        sceneInfo.setEventListener(iVuiEventListener);
    }

    public IVuiEventListener getVuiEventListener(String str) {
        VuiSceneInfo sceneInfo = getSceneInfo(str);
        if (sceneInfo == null) {
            return null;
        }
        return sceneInfo.getEventListener();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendBuildCacheInOther(String str, VuiScene vuiScene, VuiSceneCache vuiSceneCache) {
        VuiSceneInfo sceneInfo = getSceneInfo(str);
        if (sceneInfo == null || !sceneInfo.isBuildComplete()) {
            return;
        }
        buildScene(vuiScene, false, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getAuthority() {
        return !Utils.isXpDevice() ? VuiConstants.VUI_SCENE_THIRD_AUTHORITY : VuiConstants.VUI_SCENE_AUTHORITY;
    }
}
