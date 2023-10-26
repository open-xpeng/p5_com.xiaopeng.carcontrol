package com.xiaopeng.carcontrol.helper;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.SparseArray;
import androidx.core.app.NotificationCompat;
import com.google.gson.Gson;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.IpcRouterService;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.speech.VuiManager;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.lib.framework.aiassistantmodule.AiAssistantModuleEntry;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.notification.INotification;
import com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.notification.INotificationCallback;
import com.xiaopeng.libconfig.ipc.bean.MessageCenterBean;
import com.xiaopeng.libconfig.ipc.bean.MessageContentBean;
import com.xiaopeng.lludancemanager.util.ResUtil;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.app.XToast;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public final class NotificationHelper {
    private static final int BIZTYPE = 35;
    private static final String CHANNEL_ID = "CarControl_channel_01";
    public static final int D21_SCENE_100_KM = 5005;
    public static final int D21_SCENE_30_KM = 5002;
    public static final int D21_SCENE_60_KM = 5001;
    public static final int D21_SCENE_OUTSIDE_AIR_QUALITY = 8002;
    public static final int D21_SCENE_XFREE_BREATH = 8001;
    private static final long DEFAULT_MESSAGE_VALID_TIME = 1800000;
    public static final int NOTIFY_ID_HVAC_AQ = 513;
    private static final int NOTIFY_ID_INVALID = -1;
    public static final int SCENE_100_KM = 35003;
    public static final int SCENE_30_KM = 35002;
    public static final int SCENE_60_KM = 35001;
    public static final int SCENE_CAPSULE_SLEEP_OVER_TIME = 1040;
    public static final int SCENE_CNGP_MAP_DOWNLOAD = 2518;
    public static final int SCENE_OUTSIDE_AIR_QUALITY = 35006;
    public static final int SCENE_REMIND_WARNING = 36001;
    public static final int SCENE_SMART_DRIVE_SCORE = 35009;
    public static final int SCENE_XFREE_BREATH = 35004;
    private static final String TAG = "NotificationHelper";
    public static final String TOAST_AIR_BED = "airbed";
    public static final String TOAST_CAR_CONTROL_MODULE = "c_c";
    public static final String TOAST_HVAC_MODULE = "hvac";
    public static final String TOAST_SEAT_ADJUST = "seat_adjust";
    public static final String TOAST_SEAT_MASSAGE_MODULE = "seat_massage";
    private final boolean isSupportDualScreen;
    private final Handler mHandler;
    private IntentFilter mIntentFilter;
    private final SparseArray<OnNotificationListener> mListeners;
    private final Object mLock;
    private final NotificationManager mNotificationMgr;
    private BroadcastReceiver mReceiver;
    private Map<String, Integer> mSharedIdMap;

    /* loaded from: classes2.dex */
    public interface OnNotificationListener {
        void onCancel();

        void onConfirm();
    }

    /* loaded from: classes2.dex */
    private static class SingleHolder {
        private static final NotificationHelper sInstance = new NotificationHelper();

        private SingleHolder() {
        }
    }

    public static NotificationHelper getInstance() {
        return SingleHolder.sInstance;
    }

    private NotificationHelper() {
        this.mLock = new Object();
        this.mListeners = new SparseArray<>();
        this.mSharedIdMap = new HashMap();
        Context applicationContext = App.getInstance().getApplicationContext();
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mNotificationMgr = (NotificationManager) applicationContext.getSystemService("notification");
        createNotificationChannel(applicationContext);
        this.isSupportDualScreen = BaseFeatureOption.getInstance().isSupportDualScreen();
    }

    public void onScreenSwiped(String moduleName, int sharedId) {
        if (TextUtils.isEmpty(moduleName)) {
            this.mSharedIdMap.put("c_c", Integer.valueOf(sharedId));
        } else {
            this.mSharedIdMap.put(moduleName, Integer.valueOf(sharedId));
        }
    }

    public void sendMessageToMessageCenter(int scene, String title, String subTitle, String promptTts, String wakeWords, String responseTts, String buttonTitle, boolean realTime, long validTime, IpcRouterService.ICallback callback) {
        sendMessageToMessageCenter(scene, title, subTitle, promptTts, wakeWords, responseTts, buttonTitle, realTime, validTime, false, callback);
    }

    public void sendMessageToMessageCenter(int scene, String title, String subTitle, String promptTts, String wakeWords, String responseTts, String buttonTitle, boolean realTime, long validTime, boolean permanent, final IpcRouterService.ICallback callback) {
        MessageContentBean createContent = MessageContentBean.createContent();
        createContent.setType(1);
        createContent.setValidTime(0L);
        createContent.addTitle(title);
        createContent.addTitle(subTitle);
        createContent.setTts(promptTts);
        MessageContentBean.MsgButton create = MessageContentBean.MsgButton.create(buttonTitle, App.getInstance().getPackageName(), scene + "");
        create.setSpeechResponse(true);
        create.setResponseWord(wakeWords);
        create.setResponseTTS(responseTts);
        createContent.addButton(create);
        long currentTimeMillis = System.currentTimeMillis();
        long j = (validTime <= 0 ? DEFAULT_MESSAGE_VALID_TIME : validTime) + currentTimeMillis;
        if (!realTime) {
            createContent.setValidTime(j);
        }
        if (permanent) {
            createContent.setPermanent(1);
        }
        MessageCenterBean create2 = MessageCenterBean.create(35, createContent);
        create2.setScene(scene);
        create2.setValidStartTs(currentTimeMillis);
        create2.setValidEndTs(j);
        if (BaseFeatureOption.getInstance().isSupportIpcModule()) {
            INotification iNotification = (INotification) Module.get(AiAssistantModuleEntry.class).get(INotification.class);
            iNotification.send(new Gson().toJson(create2));
            iNotification.setOnNotificationCallback(new INotificationCallback() { // from class: com.xiaopeng.carcontrol.helper.-$$Lambda$NotificationHelper$wN4Bv_bx_fCS-6W0RFOoG5jNZ4A
                @Override // com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.notification.INotificationCallback
                public final void onMessageClick(String str) {
                    NotificationHelper.lambda$sendMessageToMessageCenter$0(IpcRouterService.ICallback.this, str);
                }
            });
            return;
        }
        IpcRouterService.sendMessageToMessageCenter(scene, create2, callback);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$sendMessageToMessageCenter$0(final IpcRouterService.ICallback callback, String content) {
        if (callback != null) {
            callback.onCallback(content);
        }
    }

    public XDialog showDialog(String title, String message, String negative, XDialogInterface.OnClickListener negativeListener, String positive, XDialogInterface.OnClickListener positiveListener, String sceneId) {
        XDialog positiveButton = new XDialog(App.getInstance()).setTitle(title).setMessage(message).setNegativeButton(negative, negativeListener).setPositiveButton(positive, positiveListener);
        positiveButton.setSystemDialog(2008);
        positiveButton.getDialog().setCanceledOnTouchOutside(true);
        positiveButton.show();
        VuiManager.instance().initVuiDialog(positiveButton, App.getInstance(), sceneId);
        return positiveButton;
    }

    public XDialog showConfirmDialog(String title, String message, String negative, XDialogInterface.OnClickListener negativeListener, String positive, XDialogInterface.OnClickListener positiveListener, String sceneId, int countDownSceconds) {
        XDialog positiveButton = new XDialog(App.getInstance()).setTitle(title).setMessage(message).setNegativeButton(negative, negativeListener).setPositiveButton(positive, positiveListener);
        positiveButton.setSystemDialog(2008);
        positiveButton.getDialog().setCanceledOnTouchOutside(true);
        positiveButton.setPositiveButtonEnable(false);
        positiveButton.setOnCountDownListener(new XDialogInterface.OnCountDownListener() { // from class: com.xiaopeng.carcontrol.helper.-$$Lambda$NotificationHelper$ojUD4_6OFaipzZ10aFjMlMupUjw
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnCountDownListener
            public final boolean onCountDown(XDialog xDialog, int i) {
                return NotificationHelper.lambda$showConfirmDialog$1(xDialog, i);
            }
        });
        positiveButton.show(countDownSceconds, 0);
        VuiManager.instance().initVuiDialog(positiveButton, App.getInstance(), sceneId);
        return positiveButton;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean lambda$showConfirmDialog$1(XDialog dialogView, int which) {
        dialogView.setPositiveButtonEnable(true);
        return true;
    }

    public void showToast(int msgResId) {
        showToast(msgResId, false, "c_c");
    }

    public void showToast(int msgResId, boolean showByScreenId) {
        showToast(msgResId, showByScreenId, "c_c");
    }

    public void showToast(int msgResId, boolean showByScreenId, String moduleName) {
        if (this.isSupportDualScreen && showByScreenId) {
            showToast(msgResId, getSharedId(moduleName));
        } else {
            showToast(msgResId, -1);
        }
    }

    public void showToast(String s) {
        showToast(s, false, "c_c");
    }

    public void showToast(String s, boolean showByScreenId) {
        showToast(s, showByScreenId, "c_c");
    }

    public void showToast(String s, boolean showByScreenId, String moduleName) {
        if (this.isSupportDualScreen && showByScreenId) {
            showToast(s, getSharedId(moduleName));
        } else {
            showToast(s, -1);
        }
    }

    public void showToastLong(int msgResId) {
        showToastLong(msgResId, false, "c_c");
    }

    public void showToastLong(int msgResId, boolean showByScreenId) {
        showToastLong(msgResId, showByScreenId, "c_c");
    }

    public void showToastLong(int msgResId, boolean showByScreenId, String moduleName) {
        if (this.isSupportDualScreen && showByScreenId) {
            showToastLong(msgResId, getSharedId(moduleName));
        } else {
            showToastLong(msgResId, -1);
        }
    }

    public void showToastLong(String s) {
        showToastLong(s, false, "c_c");
    }

    public void showToastLong(String s, boolean showByScreenId) {
        showToastLong(s, showByScreenId, "c_c");
    }

    public void showToastLong(String s, boolean showByScreenId, String moduleName) {
        if (this.isSupportDualScreen && showByScreenId) {
            showToastLong(s, getSharedId(moduleName));
        } else {
            showToastLong(s, -1);
        }
    }

    public void showToastLonger(int msgResId) {
        showToastLonger(msgResId, false, "c_c");
    }

    public void showToastLonger(int msgResId, boolean showByScreenId) {
        showToastLonger(msgResId, showByScreenId, "c_c");
    }

    public void showToastLonger(int msgResId, boolean showByScreenId, String moduleName) {
        if (this.isSupportDualScreen && showByScreenId) {
            showToastLonger(msgResId, getSharedId(moduleName));
        } else {
            showToastLonger(msgResId, -1);
        }
    }

    public void showToastLonger(String s) {
        showToastLonger(s, false);
    }

    public void showToastLonger(String s, boolean showByScreenId) {
        showToastLonger(s, showByScreenId, "c_c");
    }

    public void showToastLonger(String s, boolean showByScreenId, String moduleName) {
        if (this.isSupportDualScreen && showByScreenId) {
            showToastLonger(s, getSharedId(moduleName));
        } else {
            showToastLonger(s, -1);
        }
    }

    public int getSharedId(String moduleName) {
        Integer num;
        if (TextUtils.isEmpty(moduleName) || (num = this.mSharedIdMap.get(moduleName)) == null) {
            return -1;
        }
        return num.intValue();
    }

    public void showToast(int msgResId, int screenId) {
        showToast(ResUtils.getString(msgResId), screenId);
    }

    public void showToast(final String s, final int screenId) {
        this.mHandler.post(new Runnable() { // from class: com.xiaopeng.carcontrol.helper.-$$Lambda$NotificationHelper$db_MzNlFofaq_Oh54frfh54lRAI
            @Override // java.lang.Runnable
            public final void run() {
                XToast.show(s, 0, screenId);
            }
        });
    }

    public void showToastLong(int msgResId, int screenId) {
        showToastLong(ResUtils.getString(msgResId), screenId);
    }

    public void showToastLong(final String s, final int screenId) {
        this.mHandler.post(new Runnable() { // from class: com.xiaopeng.carcontrol.helper.-$$Lambda$NotificationHelper$5T5uKQcplxHe9QkY798PkWEqV_c
            @Override // java.lang.Runnable
            public final void run() {
                XToast.show(s, 1, screenId);
            }
        });
    }

    public void showToastLonger(int msgResId, int screenId) {
        showToastLonger(ResUtils.getString(msgResId), screenId);
    }

    public void showToastLonger(final String s, final int screenId) {
        this.mHandler.post(new Runnable() { // from class: com.xiaopeng.carcontrol.helper.-$$Lambda$NotificationHelper$-XzPDS_l5kW4EeDOSdxr_kfGxqI
            @Override // java.lang.Runnable
            public final void run() {
                XToast.show(s, 2, screenId);
            }
        });
    }

    public static void showOsd(Context context, int titleId, int iconId, int contentId) {
        showOsd(context, titleId, iconId, contentId, -1);
    }

    public static void showOsd(Context context, int titleId, final int iconId, final int contentId, final int sharedId) {
        ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.helper.-$$Lambda$NotificationHelper$gpbK7v0X_dVHj2urwi9Q18izFeI
            @Override // java.lang.Runnable
            public final void run() {
                XToast.show(ResUtil.getString(contentId), 0, sharedId, iconId);
            }
        });
    }

    public void showInfoCard(int id, OnNotificationListener listener) {
        if (id == -1) {
            throw new IllegalArgumentException("You must not use -1 as your notification's id");
        }
        synchronized (this.mLock) {
            this.mListeners.put(id, listener);
        }
        registerBroadcastIfNeeded();
        NotificationCompat.Builder contentText = new NotificationCompat.Builder(App.getInstance(), CHANNEL_ID).setContentTitle("My notification").setContentText("Much longer text that cannot fit one line...");
        contentText.getExtras().putInt("cardType", 1);
        contentText.getExtras().putInt("cardId", id);
        this.mNotificationMgr.notify(id, contentText.build());
    }

    private void registerBroadcastIfNeeded() {
        if (this.mReceiver == null) {
            this.mReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.carcontrol.helper.NotificationHelper.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    OnNotificationListener onNotificationListener;
                    int intExtra = intent.getIntExtra("cardId", -1);
                    synchronized (NotificationHelper.this.mLock) {
                        onNotificationListener = (OnNotificationListener) NotificationHelper.this.mListeners.get(intExtra);
                    }
                    if (onNotificationListener != null) {
                        String action = intent.getAction();
                        if (GlobalConstant.ACTION.ACTION_POSITIVE.equals(action)) {
                            onNotificationListener.onConfirm();
                        } else if (!GlobalConstant.ACTION.ACTION_NEGATIVE.equals(action)) {
                            LogUtils.w(NotificationHelper.TAG, "UnHandled action: " + action);
                        } else {
                            onNotificationListener.onCancel();
                        }
                    }
                    NotificationHelper.this.trimListeners(intExtra);
                }
            };
            this.mIntentFilter = new IntentFilter();
        }
        synchronized (this.mLock) {
            if (this.mListeners.size() > 0) {
                App.getInstance().registerReceiver(this.mReceiver, this.mIntentFilter);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void trimListeners(int id) {
        synchronized (this.mLock) {
            this.mListeners.remove(id);
            if (this.mListeners.size() == 0) {
                try {
                    App.getInstance().unregisterReceiver(this.mReceiver);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void createNotificationChannel(Context context) {
        String string = context.getString(R.string.channel_name);
        String string2 = context.getString(R.string.channel_description);
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, string, 3);
        notificationChannel.setDescription(string2);
        NotificationManager notificationManager = this.mNotificationMgr;
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public void showInfoCard(int cardType, String ticker, String title, String content, String jumpAction, Map<String, String> params) {
        Notification.Builder builder = new Notification.Builder(App.getInstance().getApplicationContext(), CHANNEL_ID);
        builder.setTicker(ticker).setSmallIcon(R.drawable.ic_launcher).setWhen(System.currentTimeMillis()).setContentTitle(title).setContentText(content).setDisplayFlag(32).setClearFlag(1).setAutoCancel(true);
        builder.getExtras().putInt("cardType", cardType);
        builder.getExtras().putString("cardJumpAction", jumpAction);
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.getExtras().putString(entry.getKey(), entry.getValue());
            }
        }
        this.mNotificationMgr.notify(cardType, builder.build());
    }

    public void showSelfCheckCard(int type, String title, String content, String extraData) {
        Notification.Builder builder = new Notification.Builder(App.getInstance().getApplicationContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_launcher).setWhen(System.currentTimeMillis()).setContentTitle(title).setContentText(content).setDisplayFlag(32).setAutoCancel(true);
        builder.getExtras().putInt("cardType", type);
        builder.getExtras().putString(GlobalConstant.NOTIFICATION.KEY_CARD_EXTRA_DATA, extraData);
        LogUtils.d(TAG, "showSelfCheckCard extraData=" + extraData, false);
        this.mNotificationMgr.notify(type, builder.build());
    }

    public void cancelInfoflowCard(int id) {
        LogUtils.d(TAG, "cancelInfoflowCard id=" + id);
        this.mNotificationMgr.cancel(id);
    }
}
