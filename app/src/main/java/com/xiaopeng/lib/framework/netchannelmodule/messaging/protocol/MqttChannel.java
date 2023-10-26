package com.xiaopeng.lib.framework.netchannelmodule.messaging.protocol;

import android.app.Application;
import android.text.TextUtils;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.MessagingException;
import com.xiaopeng.lib.framework.netchannelmodule.common.BackgroundHandler;
import com.xiaopeng.lib.framework.netchannelmodule.messaging.events.EventSender;
import com.xiaopeng.lib.framework.netchannelmodule.messaging.exception.MessagingExceptionImpl;
import com.xiaopeng.lib.framework.netchannelmodule.messaging.profile.AbstractChannelProfile;
import com.xiaopeng.lib.framework.netchannelmodule.messaging.protocol.Scheduler;
import com.xiaopeng.lib.framework.netchannelmodule.messaging.statistic.Counter;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.NetUtils;
import com.xiaopeng.lib.utils.info.BuildInfoUtils;
import com.xiaopeng.xvs.xid.base.AbsException;
import java.io.EOFException;
import java.net.SocketException;
import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.android.service.MqttTraceHandler;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

/* loaded from: classes2.dex */
public class MqttChannel {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String CLASS_NAME = "MqttChannel";
    private static final String TAG = "NetChannel-MqttChannel";
    private Application mApplicationContext;
    private volatile AbstractChannelProfile mChannelProfile;
    private Counter mCounter;
    private EventSender mEventSender;
    private volatile ExtremePingSender mExtremePingSender;
    private volatile BackgroundHandler mHandler;
    private MqttClientPersistence mMemoryPersistence;
    private volatile MqttAndroidClient mMqttAndroidClient;
    private MqttCallbackImpl mMqttCallback;
    private MqttConnectOptions mMqttConnectOptions;
    private volatile Scheduler mScheduler;
    private volatile SchedulerClient mSchedulerClient;
    private boolean mSendOutAllLogs;

    public static MqttChannel getInstance() {
        return MqttChannelHolder.INSTANCE;
    }

    public static AbstractChannelProfile getCurrentChannelProfile() {
        return getInstance().mChannelProfile;
    }

    public synchronized void init(Application application, final AbstractChannelProfile abstractChannelProfile) throws MessagingException {
        if (this.mApplicationContext != null) {
            this.mHandler.post(new Runnable() { // from class: com.xiaopeng.lib.framework.netchannelmodule.messaging.protocol.MqttChannel.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        MqttChannel.this.reInitMqtt(abstractChannelProfile);
                    } catch (Exception unused) {
                    }
                }
            });
            return;
        }
        this.mApplicationContext = application;
        this.mChannelProfile = abstractChannelProfile;
        this.mSendOutAllLogs = abstractChannelProfile.sendOutAllLogs();
        LoggerFactory.setLogger(LoggerImpl.class.getName());
        this.mHandler = new BackgroundHandler(CLASS_NAME);
        this.mMqttCallback = new MqttCallbackImpl();
        this.mEventSender = new EventSender(abstractChannelProfile.channel());
        this.mExtremePingSender = new ExtremePingSender();
        this.mSchedulerClient = new SchedulerClient();
        this.mScheduler = new Scheduler(this.mSchedulerClient, this.mHandler);
        SystemStatusMonitor.getInstance().registerBroadcastReceiver(application);
        if (BuildInfoUtils.isEngVersion()) {
            DebugCommandMonitor.getInstance().registerBroadcastReceiver();
        }
        this.mCounter = new Counter(this.mApplicationContext, this.mHandler, this.mChannelProfile.needToCollectData());
        initMqttConnection();
    }

    public boolean sendoutAllLogs() {
        return this.mSendOutAllLogs;
    }

    public synchronized boolean isConnected() {
        if (this.mScheduler == null) {
            return false;
        }
        return this.mScheduler.connected();
    }

    public void networkChanged(final boolean z) {
        this.mHandler.post(new Runnable() { // from class: com.xiaopeng.lib.framework.netchannelmodule.messaging.protocol.MqttChannel.2
            @Override // java.lang.Runnable
            public void run() {
                if (MqttChannel.this.mScheduler == null) {
                    return;
                }
                if (z) {
                    MqttChannel.this.mScheduler.moveTo(Scheduler.STATE.NETWORK_CHANGED);
                    if (MqttChannel.this.mCounter != null) {
                        MqttChannel.this.mCounter.commitForcibly();
                        return;
                    }
                    return;
                }
                MqttChannel.this.mScheduler.moveTo(Scheduler.STATE.DISCONNECTED);
            }
        });
    }

    public void disconnectedDueToException() {
        disconnectedDueToException(null);
    }

    public void disconnectedDueToException(Throwable th) {
        if (realNetworkException(th)) {
            this.mScheduler.resetFail();
        }
        this.mSchedulerClient.disconnectedDueToException(th);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public synchronized void requestToDisconnect() {
        logInternal("requestToDisconnect");
        if (isConnected()) {
            this.mHandler.post(new Runnable() { // from class: com.xiaopeng.lib.framework.netchannelmodule.messaging.protocol.MqttChannel.3
                @Override // java.lang.Runnable
                public void run() {
                    MqttChannel.this.disconnectConnection();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public synchronized void requestToConnect() {
        logInternal("requestToConnect");
        if (isConnected()) {
            return;
        }
        this.mHandler.post(new Runnable() { // from class: com.xiaopeng.lib.framework.netchannelmodule.messaging.protocol.MqttChannel.4
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (MqttChannel.this.networkAvailable()) {
                        MqttChannel.this.connectMqtt();
                    } else {
                        LogUtils.d("NetChannel-MqttChannel", "Network is not available, delay to try...");
                    }
                } catch (Exception e) {
                    MqttChannel.this.log(e.toString());
                    e.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reInitMqttWithLastProfile() throws MessagingException {
        reInitMqtt(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void reInitMqtt(AbstractChannelProfile abstractChannelProfile) throws MessagingException {
        if (abstractChannelProfile == null) {
            abstractChannelProfile = this.mChannelProfile;
        }
        closeCurrentConnection();
        this.mEventSender.changeChannel(abstractChannelProfile.channel());
        this.mScheduler.reset();
        initMqttConnection();
    }

    private synchronized void closeCurrentConnection() {
        if (this.mMqttAndroidClient == null) {
            return;
        }
        try {
            this.mHandler.clearRunnables();
            this.mSchedulerClient.stopBackgroundReconnection();
            this.mSchedulerClient.stopReopenAction();
            this.mScheduler.reset();
            this.mMqttAndroidClient.unregisterResources();
            this.mMqttAndroidClient.disconnectConnection(null);
        } catch (Exception e) {
            log("Failed to disconnectConnection current connection!!!");
            e.printStackTrace();
        }
        this.mMqttAndroidClient = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void disconnectConnection() {
        if (this.mScheduler == null || !this.mScheduler.connected()) {
            return;
        }
        this.mScheduler.moveTo(Scheduler.STATE.DISCONNECTING);
        try {
            this.mMqttAndroidClient.disconnectConnection(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mScheduler.moveTo(Scheduler.STATE.DISCONNECTED);
        try {
            MqttClientPersistence mqttClientPersistence = this.mMemoryPersistence;
            if (mqttClientPersistence != null) {
                mqttClientPersistence.close();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void initMqttConnection() throws MessagingException {
        synchronized (this) {
            initMqttClient();
        }
        this.mScheduler.moveTo(Scheduler.STATE.DISCONNECTED);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean connectMqtt() throws MessagingException {
        if (this.mMqttAndroidClient == null || this.mMqttConnectOptions == null) {
            this.mScheduler.increaseFail();
            log("mMqttAndroidClient == null || mMqttConnectOptions == null, return...");
            return false;
        } else if (!this.mScheduler.allowToConnect()) {
            this.mScheduler.increaseFail();
            log("already connected or connecting! State:" + this.mScheduler.state());
            return false;
        } else {
            try {
                log(this.mMqttConnectOptions.toString());
                this.mScheduler.moveTo(Scheduler.STATE.CONNECTING);
                this.mMqttAndroidClient.connect(this.mMqttConnectOptions, this.mApplicationContext, new IMqttActionListener() { // from class: com.xiaopeng.lib.framework.netchannelmodule.messaging.protocol.MqttChannel.5
                    @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
                    public void onSuccess(IMqttToken iMqttToken) {
                        MqttChannel.this.logInternal("onSuccess asyncActionToken=" + iMqttToken);
                        try {
                            if (MqttChannel.this.mChannelProfile.canPublish()) {
                                MqttChannel.this.mMqttAndroidClient.setBufferOpts(MqttChannel.this.mChannelProfile.buildBufferOptions());
                            }
                            MqttChannel.this.mScheduler.resetFail();
                            MqttChannel.this.mScheduler.moveTo(Scheduler.STATE.CONNECTED);
                            MqttChannel.this.mEventSender.postConnected("");
                        } catch (Exception unused) {
                            MqttChannel.this.mScheduler.moveTo(Scheduler.STATE.DISCONNECTED);
                        }
                    }

                    @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
                    public void onFailure(IMqttToken iMqttToken, Throwable th) {
                        if (th instanceof MqttException) {
                            if (4 == ((MqttException) th).getReasonCode()) {
                                MqttChannel.this.mSchedulerClient.stopBackgroundReconnection();
                            }
                            MqttChannel.this.mEventSender.postDisconnected(th);
                        }
                        MqttChannel.this.mScheduler.moveTo(Scheduler.STATE.DISCONNECTED);
                        LogUtils.d("NetChannel-MqttChannel", "connectMqtt failed! release connection resources in time");
                        try {
                            MqttChannel.this.mMqttAndroidClient.unregisterResources();
                            MqttChannel.this.mMqttAndroidClient.disconnectConnection(null);
                        } catch (MqttException e) {
                            e.printStackTrace();
                        }
                        if (th != null) {
                            if (th.getCause() != null && (th.getCause() instanceof SocketException) && th.toString().indexOf("EBADF") > 0) {
                                MqttChannel.this.log("onFailure: Kill the process due to exception:" + th);
                                System.exit(0);
                            }
                            if (MqttChannel.this.realNetworkException(th.getCause())) {
                                MqttChannel.this.mScheduler.resetFail();
                            } else {
                                MqttChannel.this.mScheduler.increaseFail();
                            }
                        }
                        MqttChannel.this.log("onFailure asyncActionToken=" + iMqttToken + " exception=" + th);
                    }
                });
                return true;
            } catch (Exception e) {
                this.mScheduler.moveTo(Scheduler.STATE.DISCONNECTED);
                log(e.toString());
                e.printStackTrace();
                throw new MessagingExceptionImpl(18);
            }
        }
    }

    private void initMqttClient() throws MessagingException {
        if (this.mMqttAndroidClient == null) {
            initAndroidClientSuccess();
        }
        this.mMqttConnectOptions = this.mChannelProfile.buildConnectOptions();
    }

    private boolean initAndroidClientSuccess() throws MessagingException {
        LogUtils.i("NetChannel-MqttChannel", "initAndroidClientSuccess mMqttAndroidClient = " + this.mMqttAndroidClient);
        String serverUrl = this.mChannelProfile.serverUrl();
        if (TextUtils.isEmpty(serverUrl)) {
            Counter counter = this.mCounter;
            if (counter != null) {
                counter.increaseInitFailedCount();
            }
            throw new MessagingExceptionImpl(17);
        }
        String clientId = this.mChannelProfile.clientId();
        if (this.mMemoryPersistence == null) {
            this.mMemoryPersistence = this.mChannelProfile.mqttClientPersistence();
        }
        try {
            this.mMemoryPersistence.open(clientId, serverUrl);
        } catch (MqttPersistenceException e) {
            logInternal(e.toString());
            this.mMemoryPersistence = null;
        }
        MqttClientPersistence mqttClientPersistence = this.mMemoryPersistence;
        if (mqttClientPersistence instanceof MemoryPersistenceProxy) {
            ((MemoryPersistenceProxy) mqttClientPersistence).setTraceEnabled(this.mChannelProfile.enableTrace());
        }
        this.mMqttAndroidClient = new MqttAndroidClient(this.mApplicationContext, serverUrl, clientId, this.mMemoryPersistence);
        this.mMqttAndroidClient.setCallback(this.mMqttCallback);
        this.mMqttAndroidClient.setTraceEnabled(this.mChannelProfile.enableTrace());
        this.mMqttAndroidClient.setTraceCallback(new MqttTraceHandler() { // from class: com.xiaopeng.lib.framework.netchannelmodule.messaging.protocol.MqttChannel.6
            @Override // org.eclipse.paho.android.service.MqttTraceHandler
            public void traceDebug(String str, String str2) {
                MqttChannel.this.logInternal("traceDebug_source--> " + str + ", message --> " + str2);
            }

            @Override // org.eclipse.paho.android.service.MqttTraceHandler
            public void traceError(String str, String str2) {
                MqttChannel.this.log("traceError_source--> " + str + ", message --> " + str2);
            }

            @Override // org.eclipse.paho.android.service.MqttTraceHandler
            public void traceException(String str, String str2, Exception exc) {
                MqttChannel.this.log("traceException_source--> " + str + ", message --> " + str2);
            }
        });
        return true;
    }

    public synchronized void subscribe(final String str) throws MessagingException {
        if (this.mMqttAndroidClient == null) {
            log("subscribe mMqttAndroidClient not init.");
            throw new MessagingExceptionImpl(17);
        }
        this.mHandler.post(new Runnable() { // from class: com.xiaopeng.lib.framework.netchannelmodule.messaging.protocol.MqttChannel.7
            @Override // java.lang.Runnable
            public void run() {
                MqttChannel.this.subscribeInternal(str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void subscribeInternal(final String str) {
        if (this.mScheduler.allowToSubscribe()) {
            try {
                this.mMqttAndroidClient.subscribe(str, this.mChannelProfile.defaultQoSLevel(), (Object) null, new IMqttActionListener() { // from class: com.xiaopeng.lib.framework.netchannelmodule.messaging.protocol.MqttChannel.8
                    @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
                    public void onSuccess(IMqttToken iMqttToken) {
                        MqttChannel.this.logInternal("subscribe topicName onSuccess topicName=" + str);
                        MqttChannel.this.mEventSender.postSubscribed(str);
                        MqttChannel.this.mScheduler.resetFail();
                    }

                    @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
                    public void onFailure(IMqttToken iMqttToken, Throwable th) {
                        MqttChannel.this.mEventSender.postUnsubscribed();
                        MqttChannel.this.mScheduler.increaseFail();
                        MqttChannel.this.disconnectedDueToException(th);
                        MqttChannel.this.logInternal("subscribeToTopic topicName onFailure topicName=" + str);
                        if (th != null) {
                            MqttChannel.this.logInternal(th.toString());
                            th.printStackTrace();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                log(e.toString());
            }
        }
    }

    public long publishMessage(String str, String str2) throws MessagingException {
        return publishMessage(str, str2.getBytes(), this.mChannelProfile.defaultQoSLevel());
    }

    public long publishMessage(String str, byte[] bArr) throws MessagingException {
        return publishMessage(str, bArr, this.mChannelProfile.defaultQoSLevel());
    }

    public long publishMessage(String str, byte[] bArr, int i) throws MessagingException {
        long j;
        logInternal("publishMessage topic=" + str + " msg.length =" + bArr.length);
        if (this.mMqttAndroidClient == null) {
            logInternal("publishMessage mMqttAndroidClient not init.");
            throw new MessagingExceptionImpl(17);
        }
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(bArr);
        mqttMessage.setQos(i);
        synchronized (this) {
            j = 0;
            try {
                Counter counter = this.mCounter;
                if (counter != null) {
                    counter.increaseTryingToPublish();
                }
                j = this.mMqttAndroidClient.publish(str, mqttMessage, (Object) null, new IMqttActionListener() { // from class: com.xiaopeng.lib.framework.netchannelmodule.messaging.protocol.MqttChannel.9
                    @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
                    public void onSuccess(IMqttToken iMqttToken) {
                    }

                    @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
                    public void onFailure(IMqttToken iMqttToken, Throwable th) {
                        String str2;
                        MqttChannel.this.mScheduler.increaseFail();
                        if (MqttChannel.this.mCounter != null) {
                            MqttChannel.this.mCounter.increasePublishFailureCount();
                        }
                        if (th instanceof MqttException) {
                            MqttException mqttException = (MqttException) th;
                            MqttChannel.this.mEventSender.postDeliveryFailure(iMqttToken.getMessageId(), mqttException.getReasonCode());
                            str2 = "Failed to publish, exception (A):" + mqttException;
                            MqttChannel.this.logInternal(str2);
                        } else {
                            String str3 = "Failed to publish, exception (B):" + th;
                            MqttChannel.this.logInternal(str3);
                            MqttChannel.this.mEventSender.postDeliveryFailure(iMqttToken.getMessageId(), 0);
                            str2 = str3;
                        }
                        MqttChannel.this.mEventSender.postTracingLog(str2);
                    }
                }).getMessageId();
            } catch (Exception e) {
                Counter counter2 = this.mCounter;
                if (counter2 != null) {
                    counter2.increasePublishFailureCount();
                }
                if (e instanceof MqttException) {
                    this.mEventSender.postDeliveryFailure(0L, ((MqttException) e).getReasonCode());
                } else {
                    this.mEventSender.postDeliveryFailure(0L, 0);
                }
            }
        }
        return j;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean realNetworkException(Throwable th) {
        if (th == null) {
            return false;
        }
        log("Found exception:" + th.toString());
        return th instanceof EOFException;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logInternal(String str) {
        if (BuildInfoUtils.isUserVersion()) {
            return;
        }
        log(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void log(String str) {
        LogUtils.i("NetChannel-MqttChannel", str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class MqttChannelHolder {
        private static final MqttChannel INSTANCE = new MqttChannel();

        private MqttChannelHolder() {
        }
    }

    /* loaded from: classes2.dex */
    public class MqttCallbackImpl implements MqttCallbackExtended {
        public MqttCallbackImpl() {
        }

        @Override // org.eclipse.paho.client.mqttv3.MqttCallbackExtended
        public void connectComplete(boolean z, String str) {
            if (MqttChannel.this.mMqttAndroidClient == null) {
                return;
            }
            if (MqttChannel.this.mMqttAndroidClient.isConnected()) {
                MqttChannel.this.mScheduler.moveTo(Scheduler.STATE.CONNECTED);
            } else {
                MqttChannel.this.mScheduler.moveTo(Scheduler.STATE.DISCONNECTED);
            }
            MqttChannel.this.mEventSender.postConnected(str);
            MqttChannel.this.logInternal("connectComplete reconnect=" + z);
        }

        @Override // org.eclipse.paho.client.mqttv3.MqttCallback
        public void connectionLost(Throwable th) {
            MqttChannel.this.log("Connection lost due to " + th);
            MqttChannel.this.disconnectedDueToException(th);
        }

        @Override // org.eclipse.paho.client.mqttv3.MqttCallback
        public void messageArrived(String str, MqttMessage mqttMessage) {
            LogUtils.d("NetChannel-MqttChannel", "messageArrived: topic = " + str);
            MqttChannel.this.mEventSender.postReceivedMessage(str, mqttMessage.getPayload());
            MqttChannel.this.mScheduler.resetFail();
            if (MqttChannel.this.mCounter != null) {
                MqttChannel.this.mCounter.increaseReceivedCount();
            }
        }

        @Override // org.eclipse.paho.client.mqttv3.MqttCallback
        public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            if (BuildInfoUtils.isUserVersion()) {
                MqttChannel.this.mEventSender.postDeliveryCompleted(iMqttDeliveryToken.getMessageId());
            } else {
                try {
                    MqttChannel.this.mEventSender.postDeliveryCompleted(iMqttDeliveryToken.getMessageId(), iMqttDeliveryToken.getMessage().getPayload());
                } catch (Exception e) {
                    LogUtils.d("NetChannel-MqttChannel", "Delivered completely, but met exception while getting message:" + e);
                }
            }
            MqttChannel.this.mScheduler.resetFail();
            long j = 0;
            try {
                if (iMqttDeliveryToken.getMessage() != null && iMqttDeliveryToken.getMessage().getPayload() != null) {
                    j = iMqttDeliveryToken.getMessage().getPayload().length;
                }
            } catch (Exception unused) {
            }
            if (MqttChannel.this.mCounter != null) {
                MqttChannel.this.mCounter.increasePublishedCountWithSize(j);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class ExtremePingSender {
        private static final int EXTREME_PING_MAX_INTERVAL = 80000;
        private static final int EXTREME_PING_MIN_INTERVAL = 10000;
        private static final int EXTREME_PING_STABLE_THRESHOLD = 20;
        private static final int EXTREME_PING_TIMEOUT = 8000;
        private int mActiveInterval;
        private int mActiveRecoverDelay;
        private Runnable mExceptionHandlingRunnable;
        private Runnable mExtremePingRunnable;
        private IMqttActionListener mPingActionListener;
        private int mSucceedCount;

        private ExtremePingSender() {
            this.mActiveInterval = 10000;
            this.mActiveRecoverDelay = 8000;
            this.mPingActionListener = new IMqttActionListener() { // from class: com.xiaopeng.lib.framework.netchannelmodule.messaging.protocol.MqttChannel.ExtremePingSender.1
                @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
                public void onSuccess(IMqttToken iMqttToken) {
                    ExtremePingSender.access$1808(ExtremePingSender.this);
                    MqttChannel.this.mHandler.removeCallbacks(ExtremePingSender.this.mExceptionHandlingRunnable);
                    if (ExtremePingSender.this.mSucceedCount > 20) {
                        ExtremePingSender.this.mActiveInterval = ExtremePingSender.EXTREME_PING_MAX_INTERVAL;
                        ExtremePingSender.this.mActiveRecoverDelay = AbsException.ERROR_CODE_BASE_PAY;
                        ExtremePingSender.this.mSucceedCount = 0;
                    }
                }

                @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
                public void onFailure(IMqttToken iMqttToken, Throwable th) {
                    ExtremePingSender.this.mSucceedCount = 0;
                    ExtremePingSender.this.mActiveInterval = 10000;
                    ExtremePingSender.this.mActiveRecoverDelay = 8000;
                }
            };
            this.mExtremePingRunnable = new Runnable() { // from class: com.xiaopeng.lib.framework.netchannelmodule.messaging.protocol.MqttChannel.ExtremePingSender.2
                @Override // java.lang.Runnable
                public void run() {
                    if (MqttChannel.this.isConnected()) {
                        try {
                            ExtremePingSender extremePingSender = ExtremePingSender.this;
                            if (extremePingSender.sendExtremePing(extremePingSender.mPingActionListener)) {
                                MqttChannel.this.mHandler.postDelayed(ExtremePingSender.this.mExceptionHandlingRunnable, ExtremePingSender.this.mActiveRecoverDelay);
                            }
                        } catch (MessagingException e) {
                            MqttChannel.this.logInternal("Failed to send extreme ping:" + e);
                        }
                    }
                    MqttChannel.this.mHandler.postDelayed(ExtremePingSender.this.mExtremePingRunnable, ExtremePingSender.this.mActiveInterval);
                }
            };
        }

        static /* synthetic */ int access$1808(ExtremePingSender extremePingSender) {
            int i = extremePingSender.mSucceedCount;
            extremePingSender.mSucceedCount = i + 1;
            return i;
        }

        public void startExtremePingSender(Runnable runnable) {
            if (MqttChannel.this.mChannelProfile == null || !MqttChannel.this.mChannelProfile.enableExtremePing()) {
                return;
            }
            this.mSucceedCount = 0;
            this.mExceptionHandlingRunnable = runnable;
            this.mActiveInterval = 10000;
            this.mActiveRecoverDelay = 8000;
            MqttChannel.this.mHandler.removeCallbacks(this.mExtremePingRunnable);
            MqttChannel.this.mHandler.postDelayed(this.mExtremePingRunnable, 10000L);
            MqttChannel.this.logInternal("start ExtremePingSender");
        }

        public void stopExtremePingSender() {
            MqttChannel.this.mHandler.removeCallbacks(this.mExtremePingRunnable);
            MqttChannel.this.mHandler.removeCallbacks(this.mExceptionHandlingRunnable);
            MqttChannel.this.logInternal("stop ExtremePingSender");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean sendExtremePing(IMqttActionListener iMqttActionListener) throws MessagingException {
            boolean z = false;
            if (MqttChannel.this.isConnected()) {
                synchronized (this) {
                    try {
                        MqttChannel.this.mMqttAndroidClient.checkPing(iMqttActionListener);
                        z = true;
                    } catch (Exception e) {
                        iMqttActionListener.onFailure(null, e);
                    }
                }
                return z;
            }
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class SchedulerClient implements Scheduler.Client {
        private static final int REINIT_INTERVAL = 20000;
        private Runnable mConnectAction;
        private Runnable mReInitAction;
        private int mRepeatPeriod;
        private Runnable mUnexpectedHandlingAction;

        private SchedulerClient() {
            this.mReInitAction = new Runnable() { // from class: com.xiaopeng.lib.framework.netchannelmodule.messaging.protocol.MqttChannel.SchedulerClient.1
                @Override // java.lang.Runnable
                public void run() {
                    boolean networkAvailable = MqttChannel.this.networkAvailable();
                    MqttChannel.this.log("mReInitAction runs! netAvailable:" + networkAvailable);
                    if (networkAvailable) {
                        SchedulerClient.this.requestToReInitInternal();
                    }
                }
            };
            this.mConnectAction = new Runnable() { // from class: com.xiaopeng.lib.framework.netchannelmodule.messaging.protocol.MqttChannel.SchedulerClient.2
                @Override // java.lang.Runnable
                public void run() {
                    if (!MqttChannel.this.networkAvailable()) {
                        MqttChannel.this.log("Wifi or System APN is not available, delay to try...");
                        MqttChannel.this.mHandler.postDelayed(SchedulerClient.this.mConnectAction, SchedulerClient.this.mRepeatPeriod);
                        return;
                    }
                    boolean z = false;
                    try {
                        if (MqttChannel.this.mMqttAndroidClient != null) {
                            z = MqttChannel.this.connectMqtt();
                        } else {
                            SchedulerClient.this.mRepeatPeriod = 20000;
                            SchedulerClient.this.requestToReInitInternal();
                        }
                    } catch (Exception e) {
                        MqttChannel.this.log(e.toString());
                        e.printStackTrace();
                    }
                    if (z) {
                        MqttChannel.this.log("Connecting...");
                    }
                    MqttChannel.this.mHandler.postDelayed(SchedulerClient.this.mConnectAction, SchedulerClient.this.mRepeatPeriod);
                }
            };
            this.mUnexpectedHandlingAction = new Runnable() { // from class: com.xiaopeng.lib.framework.netchannelmodule.messaging.protocol.MqttChannel.SchedulerClient.3
                @Override // java.lang.Runnable
                public void run() {
                    if (MqttChannel.this.mMqttAndroidClient == null) {
                        return;
                    }
                    MqttChannel.this.mExtremePingSender.stopExtremePingSender();
                    LogUtils.d("NetChannel-MqttChannel", "mUnexpectedHandlingAction: connected state = " + MqttChannel.this.mScheduler.connected() + ", " + MqttChannel.this.mMqttAndroidClient.isConnected());
                    if (MqttChannel.this.mScheduler.connected() || MqttChannel.this.mMqttAndroidClient.isConnected()) {
                        MqttChannel.this.disconnectConnection();
                    } else if (MqttChannel.this.mScheduler != null) {
                        MqttChannel.this.mScheduler.moveTo(Scheduler.STATE.DISCONNECTED);
                        MqttChannel.this.mScheduler.increaseFailTwice();
                    }
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void requestToReInitInternal() {
            if (TextUtils.isEmpty(MqttChannel.this.mChannelProfile.serverUrl())) {
                MqttChannel.this.log("No valid connection options!");
                return;
            }
            MqttChannel.this.mScheduler.moveTo(Scheduler.STATE.INITIALIZING);
            try {
                MqttChannel.this.reInitMqttWithLastProfile();
            } catch (Exception e) {
                MqttChannel.this.mScheduler.moveTo(Scheduler.STATE.DISCONNECTED);
                e.printStackTrace();
            }
        }

        @Override // com.xiaopeng.lib.framework.netchannelmodule.messaging.protocol.Scheduler.Client
        public void scheduleReopen() {
            stopBackgroundReconnection();
            stopExtremePingSender();
            MqttChannel.this.mHandler.postDelayed(this.mReInitAction, 1000L);
        }

        @Override // com.xiaopeng.lib.framework.netchannelmodule.messaging.protocol.Scheduler.Client
        public void disconnect() {
            MqttChannel.this.mHandler.post(new Runnable() { // from class: com.xiaopeng.lib.framework.netchannelmodule.messaging.protocol.MqttChannel.SchedulerClient.4
                @Override // java.lang.Runnable
                public void run() {
                    MqttChannel.this.disconnectConnection();
                }
            });
        }

        @Override // com.xiaopeng.lib.framework.netchannelmodule.messaging.protocol.Scheduler.Client
        public void startExtremePingSender() {
            MqttChannel.this.mExtremePingSender.startExtremePingSender(this.mUnexpectedHandlingAction);
        }

        @Override // com.xiaopeng.lib.framework.netchannelmodule.messaging.protocol.Scheduler.Client
        public void stopExtremePingSender() {
            MqttChannel.this.mExtremePingSender.stopExtremePingSender();
        }

        @Override // com.xiaopeng.lib.framework.netchannelmodule.messaging.protocol.Scheduler.Client
        public void disconnectedDueToException(Throwable th) {
            MqttChannel.this.logInternal("disconnectedDueToException " + th);
            MqttChannel.this.mHandler.removeCallbacks(this.mUnexpectedHandlingAction);
            MqttChannel.this.mHandler.post(this.mUnexpectedHandlingAction);
        }

        @Override // com.xiaopeng.lib.framework.netchannelmodule.messaging.protocol.Scheduler.Client
        public void startBackgroundReconnection(int i) {
            this.mRepeatPeriod = i;
            MqttChannel.this.mHandler.removeCallbacks(this.mConnectAction);
            MqttChannel.this.mExtremePingSender.stopExtremePingSender();
            MqttChannel.this.mHandler.postDelayed(this.mConnectAction, i);
        }

        @Override // com.xiaopeng.lib.framework.netchannelmodule.messaging.protocol.Scheduler.Client
        public void stopBackgroundReconnection() {
            MqttChannel.this.mHandler.removeCallbacks(this.mConnectAction);
            MqttChannel.this.mHandler.removeCallbacks(this.mUnexpectedHandlingAction);
            stopReopenAction();
        }

        @Override // com.xiaopeng.lib.framework.netchannelmodule.messaging.protocol.Scheduler.Client
        public void stopReopenAction() {
            MqttChannel.this.mHandler.removeCallbacks(this.mReInitAction);
        }

        @Override // com.xiaopeng.lib.framework.netchannelmodule.messaging.protocol.Scheduler.Client
        public void startConnectImmediately(int i) {
            this.mRepeatPeriod = i;
            MqttChannel.this.mHandler.removeCallbacks(this.mConnectAction);
            MqttChannel.this.mHandler.post(this.mConnectAction);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean networkAvailable() {
        if (NetUtils.isNetworkAvailable(this.mApplicationContext)) {
            return !NetUtils.isMobileNetwork(this.mApplicationContext) || NetUtils.isSystemApnReady();
        }
        return false;
    }
}
