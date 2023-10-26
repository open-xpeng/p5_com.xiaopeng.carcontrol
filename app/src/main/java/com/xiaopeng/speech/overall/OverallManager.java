package com.xiaopeng.speech.overall;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import com.google.gson.Gson;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingConstants;
import com.xiaopeng.lib.apirouter.ApiRouter;
import com.xiaopeng.speech.apirouter.Utils;
import com.xiaopeng.speech.overall.listener.IXpOverallListener;
import com.xiaopeng.speech.overall.listener.IXpRecordListener;
import com.xiaopeng.speech.protocol.bean.recommend.RecommendBean;
import com.xiaopeng.speech.protocol.event.OOBEEvent;
import com.xiaopeng.speech.vui.constants.OverallConstants;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import com.xiaopeng.speech.vui.utils.LogUtils;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class OverallManager {
    private static int DELAY_TIME = 200;
    private final String TAG;
    private final String asr_cmd;
    private Map<IXpOverallListener, String[]> listenerEventMap;
    private Map<IXpOverallListener, String[]> listenerQueryMap;
    private String mCallback;
    private Context mContext;
    private String mEvent;
    private String mEventData;
    private Runnable mEventRun;
    private HashSet<String> mEvents;
    private Handler mHandler;
    private IXpOverallListener mListener;
    private Map<String, HashSet<IXpOverallListener>> mListeners;
    private Handler mMainHandler;
    private String mObserver;
    private String[] mObserverEvents;
    private String mPackageName;
    private String mQuery;
    private String mQueryData;
    private Runnable mQueryRun;
    private HashSet<String> mQuerys;
    private IXpRecordListener mRecordListener;
    private String mRecordParam;
    private HandlerThread mThread;

    public void initRecord(Context context, final String str, IXpRecordListener iXpRecordListener) {
        this.mRecordListener = iXpRecordListener;
        this.mRecordParam = str;
        if (this.mHandler != null) {
            this.mContext = context.getApplicationContext();
            if (this.mObserver == null) {
                this.mObserver = this.mContext.getPackageName() + ".ApiRouterOverallService";
                registerReceiver();
            }
            String[] strArr = this.mObserverEvents;
            if (strArr != null) {
                if (!Arrays.asList(strArr).contains("xiaopeng.asr.result")) {
                    String[] strArr2 = this.mObserverEvents;
                    String[] strArr3 = new String[strArr2.length + 1];
                    System.arraycopy(strArr2, 0, strArr3, 0, strArr2.length);
                    strArr3[this.mObserverEvents.length] = "xiaopeng.asr.result";
                    this.mObserverEvents = strArr3;
                }
            } else {
                this.mObserverEvents = new String[]{"xiaopeng.asr.result"};
            }
            subscribe(this.mObserver, this.mObserverEvents);
            this.mHandler.post(new Runnable() { // from class: com.xiaopeng.speech.overall.OverallManager.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        ApiRouter.route(new Uri.Builder().authority(OverallManager.this.getAuthority()).path("initRecognizer").appendQueryParameter("param", str).build());
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }
            });
        }
    }

    public void startRecord(final String str) {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.xiaopeng.speech.overall.OverallManager.2
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        ApiRouter.route(new Uri.Builder().authority(OverallManager.this.getAuthority()).path("startRecord").appendQueryParameter("param", str).build());
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }
            });
        }
    }

    public void stopRecord() {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.xiaopeng.speech.overall.OverallManager.3
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        ApiRouter.route(new Uri.Builder().authority(OverallManager.this.getAuthority()).path("stopRecord").build());
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }
            });
        }
    }

    public void destroyRecord(IXpRecordListener iXpRecordListener) {
        if (this.mRecordListener == iXpRecordListener) {
            this.mRecordListener = null;
        }
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.xiaopeng.speech.overall.OverallManager.4
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        ApiRouter.route(new Uri.Builder().authority(OverallManager.this.getAuthority()).path("destroyRecord").build());
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                    OverallManager overallManager = OverallManager.this;
                    overallManager.unsubscribe(overallManager.mObserver);
                }
            });
        }
    }

    public boolean isSupportRecord() {
        try {
            ApiRouter.route(new Uri.Builder().authority(getAuthority()).path("destroyRecord").build());
            return true;
        } catch (Throwable th) {
            LogUtils.e("OverallManager", "isSupportRecord: " + th.getMessage());
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class Holder {
        private static final OverallManager Instance = new OverallManager();

        private Holder() {
        }
    }

    private OverallManager() {
        this.mListeners = new ConcurrentHashMap();
        this.listenerEventMap = new ConcurrentHashMap();
        this.listenerQueryMap = new ConcurrentHashMap();
        this.asr_cmd = "xiaopeng.asr.result";
        this.TAG = "OverallManager";
        this.mObserver = null;
        this.mObserverEvents = null;
        this.mMainHandler = new Handler(Looper.getMainLooper());
        this.mEvents = new HashSet<>();
        this.mQuerys = new HashSet<>();
        this.mEvent = null;
        this.mEventData = null;
        this.mEventRun = new Runnable() { // from class: com.xiaopeng.speech.overall.OverallManager.21
            @Override // java.lang.Runnable
            public void run() {
                try {
                    OverallManager.this.mHandler.removeCallbacks(OverallManager.this.mEventRun);
                    if (TextUtils.isEmpty(OverallManager.this.mEvent)) {
                        return;
                    }
                    OverallManager overallManager = OverallManager.this;
                    overallManager.dispatchOverallEvent(overallManager.mEvent, OverallManager.this.mEventData);
                } catch (Exception unused) {
                }
            }
        };
        this.mQuery = null;
        this.mQueryData = null;
        this.mCallback = null;
        this.mQueryRun = new Runnable() { // from class: com.xiaopeng.speech.overall.OverallManager.22
            @Override // java.lang.Runnable
            public void run() {
                try {
                    LogUtils.logInfo("OverallManager", "mQueryRun:" + OverallManager.this.mQuery + ",mQueryData:" + OverallManager.this.mQueryData);
                    OverallManager.this.mHandler.removeCallbacks(OverallManager.this.mQueryRun);
                    if (TextUtils.isEmpty(OverallManager.this.mQuery)) {
                        return;
                    }
                    LogUtils.logInfo("OverallManager", "mQueryRun:" + OverallManager.this.mQuery + ",mQueryData:" + OverallManager.this.mQueryData);
                    OverallManager overallManager = OverallManager.this;
                    overallManager.dispatchOverallQuery(overallManager.mQuery, OverallManager.this.mQueryData, OverallManager.this.mCallback);
                } catch (Exception unused) {
                }
            }
        };
        if (this.mThread == null) {
            HandlerThread handlerThread = new HandlerThread("VuiEngine-overall");
            this.mThread = handlerThread;
            handlerThread.start();
            this.mHandler = new Handler(this.mThread.getLooper());
        }
    }

    public static final OverallManager instance() {
        return Holder.Instance;
    }

    public void init(Context context) {
        init(context, null);
    }

    public void init(Context context, IXpOverallListener iXpOverallListener) {
        this.mContext = context.getApplicationContext();
        this.mPackageName = context.getPackageName();
        this.mListener = iXpOverallListener;
        this.mObserver = this.mPackageName + ".ApiRouterOverallService";
        String[] observerEvent = OverallUtils.getObserverEvent(this.mPackageName);
        this.mObserverEvents = observerEvent;
        if (observerEvent != null) {
            subscribe(this.mObserver, observerEvent);
        }
        registerReceiver();
    }

    public void subscribe() {
        Context context;
        IXpRecordListener iXpRecordListener = this.mRecordListener;
        if (iXpRecordListener != null && (context = this.mContext) != null) {
            initRecord(context, this.mRecordParam, iXpRecordListener);
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("messageCode", "501");
                this.mRecordListener.onResult(jSONObject.toString());
                return;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        subscribe(this.mObserver, this.mObserverEvents);
    }

    public void addObserverEvents(String[] strArr, String[] strArr2, IXpOverallListener iXpOverallListener) {
        for (int i = 0; strArr != null && i < strArr.length; i++) {
            updateListener(strArr[i], iXpOverallListener);
            this.mEvents.add(strArr[i]);
        }
        for (int i2 = 0; strArr2 != null && i2 < strArr2.length; i2++) {
            updateListener(strArr2[i2], iXpOverallListener);
            this.mQuerys.add(strArr2[i2]);
        }
        if (strArr != null) {
            this.listenerEventMap.put(iXpOverallListener, strArr);
        }
        if (strArr2 != null) {
            this.listenerQueryMap.put(iXpOverallListener, strArr2);
        }
        int length = (strArr == null ? 0 : strArr.length) + (strArr2 == null ? 0 : strArr2.length);
        String[] strArr3 = new String[length];
        if (strArr != null) {
            System.arraycopy(strArr, 0, strArr3, 0, strArr.length);
        }
        if (strArr2 != null) {
            System.arraycopy(strArr2, 0, strArr3, strArr == null ? 0 : strArr.length, strArr2.length);
        }
        String[] strArr4 = this.mObserverEvents;
        if (strArr4 != null) {
            String[] strArr5 = new String[strArr4.length + length];
            System.arraycopy(strArr4, 0, strArr5, 0, strArr4.length);
            System.arraycopy(strArr3, 0, strArr5, this.mObserverEvents.length, length);
            this.mObserverEvents = strArr5;
        } else {
            this.mObserverEvents = strArr3;
        }
        subscribe(this.mObserver, strArr3);
    }

    public void updateListener(String str, IXpOverallListener iXpOverallListener) {
        HashSet<IXpOverallListener> hashSet = this.mListeners.get(str);
        if (hashSet == null) {
            HashSet<IXpOverallListener> hashSet2 = new HashSet<>();
            hashSet2.add(iXpOverallListener);
            this.mListeners.put(str, hashSet2);
            return;
        }
        hashSet.add(iXpOverallListener);
    }

    private void registerReceiver() {
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("carspeechservice.SpeechServer.Start");
            ((Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication", new Class[0]).invoke(null, null)).registerReceiver(new OverAllBroadCastReceiver(), intentFilter);
        } catch (Exception e) {
            LogUtils.e("OverallManager", "registerReceiver e:" + e.getMessage());
        }
    }

    public void subscribe(Context context, String str, String[] strArr) {
        if (this.mContext == null) {
            this.mContext = context.getApplicationContext();
            this.mPackageName = context.getPackageName();
        }
        if (this.mObserver == null) {
            this.mObserver = str;
        }
        if (this.mObserverEvents == null) {
            this.mObserverEvents = strArr;
        }
        subscribe(this.mObserver, this.mObserverEvents);
        registerReceiver();
    }

    private void subscribe(final String str, final String[] strArr) {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.xiaopeng.speech.overall.OverallManager.5
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (strArr != null && !TextUtils.isEmpty(str)) {
                            LogUtils.logInfo("OverallManager", "subscribe:" + str + "events:" + Arrays.asList(strArr));
                            ApiRouter.route(new Uri.Builder().authority(OverallManager.this.getAuthority()).path("subscribe").appendQueryParameter("observer", str).appendQueryParameter("param", new Gson().toJson(strArr)).build());
                        }
                    } catch (Exception e) {
                        LogUtils.e("OverallManager", "subscribe:" + str + "e:" + e.getMessage());
                    }
                }
            });
        }
    }

    public void unsubscribe(final String str) {
        this.mHandler.post(new Runnable() { // from class: com.xiaopeng.speech.overall.OverallManager.6
            @Override // java.lang.Runnable
            public void run() {
                try {
                    OverallManager.this.mObserverEvents = null;
                    if (TextUtils.isEmpty(str)) {
                        return;
                    }
                    ApiRouter.route(new Uri.Builder().authority(OverallManager.this.getAuthority()).path("unsubscribe").appendQueryParameter("observer", str).build());
                } catch (Exception unused) {
                }
            }
        });
    }

    public void unsubscribe(final String str, final String[] strArr) {
        this.mHandler.post(new Runnable() { // from class: com.xiaopeng.speech.overall.OverallManager.7
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (TextUtils.isEmpty(str)) {
                        return;
                    }
                    ApiRouter.route(new Uri.Builder().authority(OverallManager.this.getAuthority()).path("unsubscribe").appendQueryParameter("observer", str).appendQueryParameter("events", new Gson().toJson(strArr)).build());
                } catch (Exception unused) {
                }
            }
        });
    }

    public void triggerIntent(final String str, final String str2, final String str3, final String str4) {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.xiaopeng.speech.overall.OverallManager.8
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        ApiRouter.route(new Uri.Builder().authority(OverallManager.this.getAuthority()).path("triggerIntent").appendQueryParameter("skill", str).appendQueryParameter("task", str2).appendQueryParameter("intent", str3).appendQueryParameter("slots", str4).build());
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }
            });
        }
    }

    public void triggerEvent(final String str, final String str2) {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.xiaopeng.speech.overall.OverallManager.9
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        ApiRouter.route(new Uri.Builder().authority(OverallManager.this.getAuthority()).path("triggerEvent").appendQueryParameter(NotificationCompat.CATEGORY_EVENT, str).appendQueryParameter("data", str2).build());
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }
            });
        }
    }

    public void stopDialog() {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.xiaopeng.speech.overall.OverallManager.10
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        ApiRouter.route(new Uri.Builder().authority(OverallManager.this.getAuthority()).path("stopDialog").build());
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }
            });
        }
    }

    public void sendEvent(final String str, final String str2) {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.xiaopeng.speech.overall.OverallManager.11
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        ApiRouter.route(new Uri.Builder().authority(OverallManager.this.getAuthority()).path("sendEvent").appendQueryParameter(NotificationCompat.CATEGORY_EVENT, str).appendQueryParameter("data", str2).build());
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }
            });
        }
    }

    public void startDialogFrom(final String str) {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.xiaopeng.speech.overall.OverallManager.12
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        ApiRouter.route(new Uri.Builder().authority(OverallManager.this.getAuthority()).path("startDialogFrom").appendQueryParameter(VuiConstants.ELEMENT_TYPE, str).build());
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }
            });
        }
    }

    public void feedbackResult(final String str, final String str2) {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.xiaopeng.speech.overall.OverallManager.13
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        ApiRouter.route(new Uri.Builder().authority(OverallManager.this.getAuthority()).path("feedbackResult").appendQueryParameter(NotificationCompat.CATEGORY_EVENT, str).appendQueryParameter("data", str2).build());
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }
            });
        }
    }

    public void replySupport(final String str, final boolean z, final String str2) {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.xiaopeng.speech.overall.OverallManager.14
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        ApiRouter.route(new Uri.Builder().authority(OverallManager.this.getAuthority()).path("replySupport").appendQueryParameter(NotificationCompat.CATEGORY_EVENT, str).appendQueryParameter("isSupport", z + "").appendQueryParameter("text", str2).build());
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }
            });
        }
    }

    public void replySupport(String str, boolean z) {
        replySupport(str, z, "");
    }

    public void speak(final String str) {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.xiaopeng.speech.overall.OverallManager.15
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        ApiRouter.route(new Uri.Builder().authority(OverallManager.this.getAuthority()).path("speak").appendQueryParameter("tts", str).build());
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }
            });
        }
    }

    public void setOverallListener(IXpOverallListener iXpOverallListener) {
        this.mListener = iXpOverallListener;
    }

    public void addOverallListener(IXpOverallListener iXpOverallListener) {
        this.mListener = null;
        if (this.mListeners.size() > 0) {
            for (Map.Entry<String, HashSet<IXpOverallListener>> entry : this.mListeners.entrySet()) {
                HashSet<IXpOverallListener> value = entry.getValue();
                value.add(iXpOverallListener);
                this.mListeners.put(entry.getKey(), value);
            }
        }
        if (this.mEvents.size() > 0) {
            String[] strArr = new String[this.mEvents.size()];
            this.mEvents.toArray(strArr);
            this.listenerEventMap.put(iXpOverallListener, strArr);
        }
        if (this.mQuerys.size() > 0) {
            String[] strArr2 = new String[this.mQuerys.size()];
            this.mQuerys.toArray(strArr2);
            this.listenerQueryMap.put(iXpOverallListener, strArr2);
        }
    }

    public void removeOverallListener(IXpOverallListener iXpOverallListener) {
        String[] strArr;
        String[] strArr2;
        if (this.mListener == null && this.mListeners.size() == 0) {
            return;
        }
        if (this.mListener.equals(iXpOverallListener)) {
            this.mListener = null;
        }
        if (this.listenerEventMap.containsKey(iXpOverallListener) && (strArr2 = this.listenerEventMap.get(iXpOverallListener)) != null) {
            for (int i = 0; i < strArr2.length; i++) {
                if (this.mListeners.containsKey(strArr2[i])) {
                    HashSet<IXpOverallListener> hashSet = this.mListeners.get(strArr2[i]);
                    hashSet.remove(iXpOverallListener);
                    if (hashSet.size() == 0) {
                        this.mListeners.remove(strArr2[i]);
                    } else {
                        this.mListeners.put(strArr2[i], hashSet);
                    }
                }
            }
        }
        if (!this.listenerQueryMap.containsKey(iXpOverallListener) || (strArr = this.listenerQueryMap.get(iXpOverallListener)) == null) {
            return;
        }
        for (int i2 = 0; i2 < strArr.length; i2++) {
            if (this.mListeners.containsKey(strArr[i2])) {
                HashSet<IXpOverallListener> hashSet2 = this.mListeners.get(strArr[i2]);
                hashSet2.remove(iXpOverallListener);
                if (hashSet2.size() == 0) {
                    this.mListeners.remove(strArr[i2]);
                } else {
                    this.mListeners.put(strArr[i2], hashSet2);
                }
            }
        }
    }

    public void dispatchOverallEvent(final String str, final String str2) {
        try {
            if ("xiaopeng.asr.result".equals(str) && this.mRecordListener != null) {
                this.mMainHandler.post(new Runnable() { // from class: com.xiaopeng.speech.overall.OverallManager.16
                    @Override // java.lang.Runnable
                    public void run() {
                        OverallManager.this.mRecordListener.onResult(str2);
                    }
                });
            } else if (!TextUtils.isEmpty(this.mPackageName) && this.mListener != null) {
                List<String> events = OverallUtils.getEvents(this.mPackageName);
                if (events != null) {
                    int indexOf = events.indexOf(str);
                    if (indexOf != -1) {
                        final String str3 = OverallUtils.getPackageEvents(this.mPackageName).get(indexOf);
                        LogUtils.logInfo("OverallManager", "dispatchOverallEvent eventStr:" + str3);
                        if (TextUtils.isEmpty(str3)) {
                            return;
                        }
                        if (!str3.contains("|")) {
                            this.mMainHandler.post(new Runnable() { // from class: com.xiaopeng.speech.overall.OverallManager.17
                                @Override // java.lang.Runnable
                                public void run() {
                                    OverallManager.this.mListener.onEvent(str3, str2);
                                }
                            });
                            return;
                        }
                        String[] split = str3.split("\\|");
                        for (String str4 : split[0].split(",")) {
                            String[] split2 = str4.split(QuickSettingConstants.JOINER);
                            Object onQuery = this.mListener.onQuery(split2[0]);
                            LogUtils.logInfo("OverallManager", "dispatchOverallEvent eventStr:" + split2[1] + ",obj:" + onQuery);
                            if (onQuery == null) {
                                return;
                            }
                            if (onQuery instanceof Boolean) {
                                if ((!split2[1].equals(OOBEEvent.STRING_TRUE) || !((Boolean) onQuery).booleanValue()) && (!split2[1].equals(OOBEEvent.STRING_FALSE) || ((Boolean) onQuery).booleanValue())) {
                                    return;
                                }
                            } else if (onQuery instanceof Integer) {
                                if (!isNumber(split2[1]) || ((Integer) onQuery).intValue() != Integer.parseInt(split2[1])) {
                                    return;
                                }
                            } else if (!(onQuery instanceof String) || !((String) onQuery).equals(split2[1])) {
                                return;
                            }
                        }
                        LogUtils.logInfo("OverallManager", "dispatchOverallEvent run:" + split[1] + ",data:" + str2);
                        final String str5 = split[1];
                        this.mMainHandler.post(new Runnable() { // from class: com.xiaopeng.speech.overall.OverallManager.18
                            @Override // java.lang.Runnable
                            public void run() {
                                OverallManager.this.mListener.onEvent(str5, str2);
                            }
                        });
                        return;
                    }
                    int indexOf2 = OverallUtils.getQueryEvents(this.mPackageName).indexOf(str);
                    if (indexOf2 != -1) {
                        String str6 = OverallUtils.getPackageQueryEvents(this.mPackageName).get(indexOf2);
                        if (TextUtils.isEmpty(str6)) {
                            return;
                        }
                        JSONObject jSONObject = new JSONObject();
                        if (str6.contains("|")) {
                            String[] split3 = str6.split("\\|");
                            for (int i = 0; i < split3.length; i++) {
                                Object onQuery2 = this.mListener.onQuery(split3[i]);
                                if (onQuery2 != null) {
                                    jSONObject.put(split3[i], onQuery2);
                                }
                            }
                        } else {
                            Object onQuery3 = this.mListener.onQuery(str6);
                            if (onQuery3 != null) {
                                jSONObject.put(str, onQuery3);
                            }
                        }
                        if (jSONObject.length() > 0) {
                            LogUtils.logInfo("OverallManager", "feedbackResult:" + jSONObject.toString());
                            ApiRouter.route(new Uri.Builder().authority(getAuthority()).path("feedbackResult").appendQueryParameter(NotificationCompat.CATEGORY_EVENT, str).appendQueryParameter("data", jSONObject.toString()).build());
                            return;
                        }
                        return;
                    }
                    return;
                }
                HashSet<String> hashSet = this.mEvents;
                if (hashSet != null && Arrays.asList(hashSet).contains(str)) {
                    this.mMainHandler.post(new Runnable() { // from class: com.xiaopeng.speech.overall.OverallManager.19
                        @Override // java.lang.Runnable
                        public void run() {
                            OverallManager.this.mListener.onEvent(str, str2);
                        }
                    });
                    return;
                }
                HashSet<String> hashSet2 = this.mQuerys;
                if (hashSet2 == null || !Arrays.asList(hashSet2).contains(str)) {
                    return;
                }
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put(str, this.mListener.onQuery(str));
                ApiRouter.route(new Uri.Builder().authority(getAuthority()).path("feedbackResult").appendQueryParameter(NotificationCompat.CATEGORY_EVENT, str).appendQueryParameter("data", jSONObject2.toString()).build());
            } else if (TextUtils.isEmpty(this.mPackageName) && this.mListener == null) {
                this.mEvent = str;
                this.mEventData = str2;
                this.mHandler.postDelayed(this.mEventRun, DELAY_TIME);
            } else if (this.mListener == null) {
                LogUtils.logInfo("OverallManager", "dispatchOverallEvent mListeners:" + this.mListeners);
                if (this.mListeners.containsKey(str)) {
                    LogUtils.logInfo("OverallManager", "dispatchOverallEvent mListeners:" + this.mListeners);
                    Iterator<IXpOverallListener> it = this.mListeners.get(str).iterator();
                    while (it.hasNext()) {
                        final IXpOverallListener next = it.next();
                        HashSet<String> hashSet3 = this.mEvents;
                        if (hashSet3 != null && hashSet3.contains(str)) {
                            this.mMainHandler.post(new Runnable() { // from class: com.xiaopeng.speech.overall.OverallManager.20
                                @Override // java.lang.Runnable
                                public void run() {
                                    LogUtils.logInfo("OverallManager", "dispatchOverallEvent listener:" + next);
                                    next.onEvent(str, str2);
                                }
                            });
                        } else {
                            HashSet<String> hashSet4 = this.mQuerys;
                            if (hashSet4 != null && hashSet4.contains(str)) {
                                JSONObject jSONObject3 = new JSONObject();
                                jSONObject3.put(str, next.onQuery(str));
                                ApiRouter.route(new Uri.Builder().authority(getAuthority()).path("feedbackResult").appendQueryParameter(NotificationCompat.CATEGORY_EVENT, str).appendQueryParameter("data", jSONObject3.toString()).build());
                            }
                        }
                    }
                } else if (this.mListeners == null) {
                    this.mEvent = str;
                    this.mEventData = str2;
                    this.mHandler.postDelayed(this.mEventRun, DELAY_TIME);
                } else if (OverallUtils.getQueryEvents(this.mPackageName).indexOf(str) != -1) {
                    ApiRouter.route(new Uri.Builder().authority(getAuthority()).path("feedbackResult").appendQueryParameter(NotificationCompat.CATEGORY_EVENT, str).appendQueryParameter("data", new JSONObject().toString()).build());
                }
            }
        } catch (Exception unused) {
        }
    }

    private boolean isNumber(String str) {
        return Pattern.compile("[0-9]+(_[0-9]+)*").matcher(str).matches();
    }

    public void dispatchOverallQuery(String str, String str2, String str3) {
        Object onQuery;
        Object onQuery2;
        Object onQuery3;
        try {
            this.mCallback = str3;
            if (!TextUtils.isEmpty(this.mPackageName) && this.mListener != null) {
                int indexOf = OverallUtils.getQueryEvents(this.mPackageName).indexOf(str);
                if (indexOf != -1) {
                    String str4 = OverallUtils.getPackageQueryEvents(this.mPackageName).get(indexOf);
                    if (TextUtils.isEmpty(str4) || str4.contains("|") || (onQuery3 = this.mListener.onQuery(str4)) == null || TextUtils.isEmpty(str3)) {
                        return;
                    }
                    ApiRouter.route(Uri.parse(str3).buildUpon().appendQueryParameter(RecommendBean.SHOW_TIME_RESULT, new SpeechResult(str, onQuery3).toString()).build());
                } else if (!Arrays.asList(this.mQuerys).contains(str) || (onQuery2 = this.mListener.onQuery(str)) == null || TextUtils.isEmpty(str3)) {
                } else {
                    ApiRouter.route(Uri.parse(str3).buildUpon().appendQueryParameter(RecommendBean.SHOW_TIME_RESULT, new SpeechResult(str, onQuery2).toString()).build());
                }
            } else if (TextUtils.isEmpty(this.mPackageName) && this.mListener == null) {
                this.mQuery = str;
                this.mQueryData = str2;
                this.mHandler.postDelayed(this.mQueryRun, DELAY_TIME);
            } else if (this.mListener == null) {
                LogUtils.logInfo("OverallManager", "dispatchOverallEvent mListeners:" + this.mListeners);
                Map<String, HashSet<IXpOverallListener>> map = this.mListeners;
                if (map != null && map.size() > 0) {
                    LogUtils.logInfo("OverallManager", "dispatchOverallEvent mListeners:" + this.mListeners);
                    Iterator<IXpOverallListener> it = this.mListeners.get(str).iterator();
                    while (it.hasNext()) {
                        IXpOverallListener next = it.next();
                        LogUtils.logInfo("OverallManager", "dispatchOverallEvent listener:" + next + ",event:" + str);
                        HashSet<String> hashSet = this.mQuerys;
                        if (hashSet != null && hashSet.contains(str) && (onQuery = next.onQuery(str)) != null && !TextUtils.isEmpty(str3)) {
                            try {
                                ApiRouter.route(Uri.parse(str3).buildUpon().appendQueryParameter(RecommendBean.SHOW_TIME_RESULT, new SpeechResult(str, onQuery).toString()).build());
                            } catch (Exception unused) {
                            }
                        }
                    }
                } else if (this.mListeners == null) {
                    this.mQuery = str;
                    this.mQueryData = str2;
                    this.mHandler.postDelayed(this.mQueryRun, DELAY_TIME);
                }
            }
        } catch (Exception unused2) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getAuthority() {
        return !Utils.isXpDevice() ? OverallConstants.OVERAll_THIRD_AUTHORITY : OverallConstants.OVERAll_AUTHORITY;
    }
}
