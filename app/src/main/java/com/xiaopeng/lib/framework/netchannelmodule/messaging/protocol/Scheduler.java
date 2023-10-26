package com.xiaopeng.lib.framework.netchannelmodule.messaging.protocol;

import com.xiaopeng.lib.framework.netchannelmodule.common.BackgroundHandler;
import com.xiaopeng.lib.framework.netchannelmodule.common.ContextNetStatusProvider;
import com.xiaopeng.lib.framework.netchannelmodule.messaging.events.EventSender;
import com.xiaopeng.lib.utils.LogUtils;

/* loaded from: classes2.dex */
public class Scheduler {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int RECONNECTION_LONG_TIME_FAILED_CNT = 60;
    private static final int RECONNECTION_MAX_DELAY = 10000;
    private static final int RECONNECTION_MIN_DELAY = 2000;
    private static final int RECONNECTION_NORMAL_DELAY = 2000;
    private static final String TAG = "NetChannel-Scheduler";
    private static final int THRESHOLD_FAILED_COUNT_TO_DISCONNECT = 20;
    private static final int THRESHOLD_FAILED_COUNT_TO_REINIT = 50;
    private volatile Client mClient;
    private volatile STATE mLastState;
    private BackgroundHandler mMqttHandler;
    private volatile int mConnectedNetworkType = 0;
    private volatile STATE mState = STATE.INITIALIZING;
    private volatile int mContinuousConnectionFailed = 0;
    private volatile int mReconnectionDelay = 2000;
    private volatile int mFailedCount = 0;

    /* loaded from: classes2.dex */
    public interface Client {
        void disconnect();

        void disconnectedDueToException(Throwable th);

        void scheduleReopen();

        void startBackgroundReconnection(int i);

        void startConnectImmediately(int i);

        void startExtremePingSender();

        void stopBackgroundReconnection();

        void stopExtremePingSender();

        void stopReopenAction();
    }

    /* loaded from: classes2.dex */
    public enum STATE {
        INITIALIZING,
        DISCONNECTED,
        CONNECTING,
        CONNECTED,
        DISCONNECTING,
        NETWORK_CHANGED
    }

    public Scheduler(Client client, BackgroundHandler backgroundHandler) {
        this.mClient = client;
        this.mMqttHandler = backgroundHandler;
    }

    public synchronized void moveTo(final STATE state) {
        String str = "[MQTTSTATUS] Move from state:" + this.mState + " to " + state;
        LogUtils.d(TAG, str);
        this.mLastState = this.mState;
        this.mState = state;
        if (state != STATE.INITIALIZING && state != STATE.DISCONNECTING) {
            this.mMqttHandler.post(new Runnable() { // from class: com.xiaopeng.lib.framework.netchannelmodule.messaging.protocol.Scheduler.1
                @Override // java.lang.Runnable
                public void run() {
                    Scheduler scheduler = Scheduler.this;
                    scheduler.scheduleAction(scheduler.mLastState, state);
                }
            });
            if (MqttChannel.getInstance().sendoutAllLogs()) {
                EventSender.getCurrent().postTracingLog(str);
            }
        }
    }

    public synchronized void increaseFail() {
        this.mFailedCount++;
        this.mMqttHandler.post(new Runnable() { // from class: com.xiaopeng.lib.framework.netchannelmodule.messaging.protocol.Scheduler.2
            @Override // java.lang.Runnable
            public void run() {
                Scheduler.this.doCheckFailedCount();
            }
        });
    }

    public synchronized void increaseFailTwice() {
        this.mFailedCount += 2;
        this.mMqttHandler.post(new Runnable() { // from class: com.xiaopeng.lib.framework.netchannelmodule.messaging.protocol.Scheduler.3
            @Override // java.lang.Runnable
            public void run() {
                Scheduler.this.doCheckFailedCount();
            }
        });
    }

    public synchronized void resetFail() {
        if (this.mFailedCount > 0) {
            this.mClient.stopReopenAction();
        }
        this.mFailedCount = 0;
    }

    public synchronized void reset() {
        this.mState = STATE.DISCONNECTED;
        this.mClient.stopBackgroundReconnection();
        this.mClient.stopExtremePingSender();
        if (this.mFailedCount > 0) {
            this.mClient.stopReopenAction();
        }
        this.mFailedCount = 0;
    }

    public synchronized boolean allowToConnect() {
        boolean z;
        if (this.mState != STATE.DISCONNECTED) {
            z = this.mState == STATE.NETWORK_CHANGED;
        }
        return z;
    }

    public synchronized boolean allowToSubscribe() {
        return this.mState == STATE.CONNECTED;
    }

    public synchronized boolean connected() {
        return this.mState == STATE.CONNECTED;
    }

    public STATE state() {
        return this.mState;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void doCheckFailedCount() {
        LogUtils.d(TAG, "Failed:" + this.mFailedCount);
        if (this.mFailedCount > 50) {
            this.mFailedCount = 0;
            this.mClient.scheduleReopen();
            return;
        }
        if (this.mFailedCount % 20 == 0) {
            if (this.mState != STATE.CONNECTING && this.mState != STATE.INITIALIZING) {
                LogUtils.d(TAG, "Try to disconnect the connection.");
                this.mClient.disconnect();
            }
            LogUtils.d(TAG, "Try to reopen the connection.");
            this.mClient.scheduleReopen();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.lib.framework.netchannelmodule.messaging.protocol.Scheduler$4  reason: invalid class name */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$lib$framework$netchannelmodule$messaging$protocol$Scheduler$STATE;

        static {
            int[] iArr = new int[STATE.values().length];
            $SwitchMap$com$xiaopeng$lib$framework$netchannelmodule$messaging$protocol$Scheduler$STATE = iArr;
            try {
                iArr[STATE.DISCONNECTED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$lib$framework$netchannelmodule$messaging$protocol$Scheduler$STATE[STATE.CONNECTED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$lib$framework$netchannelmodule$messaging$protocol$Scheduler$STATE[STATE.NETWORK_CHANGED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$lib$framework$netchannelmodule$messaging$protocol$Scheduler$STATE[STATE.CONNECTING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$xiaopeng$lib$framework$netchannelmodule$messaging$protocol$Scheduler$STATE[STATE.INITIALIZING.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$xiaopeng$lib$framework$netchannelmodule$messaging$protocol$Scheduler$STATE[STATE.DISCONNECTING.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void scheduleAction(STATE state, STATE state2) {
        int i = AnonymousClass4.$SwitchMap$com$xiaopeng$lib$framework$netchannelmodule$messaging$protocol$Scheduler$STATE[state2.ordinal()];
        if (i == 1) {
            this.mClient.stopBackgroundReconnection();
            this.mClient.stopExtremePingSender();
            this.mConnectedNetworkType = 0;
            if (state == STATE.INITIALIZING) {
                this.mClient.startConnectImmediately(2000);
                this.mReconnectionDelay = 2000;
            } else {
                this.mContinuousConnectionFailed++;
                if (this.mContinuousConnectionFailed > 60) {
                    this.mReconnectionDelay = 10000;
                }
                this.mClient.startBackgroundReconnection(this.mReconnectionDelay);
            }
        } else if (i == 2) {
            this.mContinuousConnectionFailed = 0;
            this.mClient.stopBackgroundReconnection();
            this.mClient.startExtremePingSender();
            this.mReconnectionDelay = 2000;
            this.mConnectedNetworkType = getNetworkType();
        } else if (i != 3) {
            if (i == 4) {
                resetFail();
                this.mReconnectionDelay = 2000;
                this.mClient.stopBackgroundReconnection();
                this.mClient.startBackgroundReconnection(this.mReconnectionDelay);
            }
        } else if (state == STATE.CONNECTED && getNetworkType() == this.mConnectedNetworkType) {
            this.mState = STATE.CONNECTED;
            LogUtils.d(TAG, "Skip the network change event cause of same network type!");
        } else {
            if (state == STATE.CONNECTED) {
                this.mClient.scheduleReopen();
            } else if (state == STATE.DISCONNECTED) {
                this.mClient.stopBackgroundReconnection();
                this.mClient.startConnectImmediately(2000);
            }
            this.mReconnectionDelay = 2000;
        }
    }

    private int getNetworkType() {
        int netType = ContextNetStatusProvider.getNetType();
        ContextNetStatusProvider.getNetStrength(netType);
        return netType;
    }
}
