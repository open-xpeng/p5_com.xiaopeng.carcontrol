package com.alibaba.sdk.android.beacon;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class Beacon {
    private int a;

    /* renamed from: a  reason: collision with other field name */
    private final HandlerThread f70a;

    /* renamed from: a  reason: collision with other field name */
    private final b f71a;

    /* renamed from: a  reason: collision with other field name */
    private final List<OnUpdateListener> f72a;
    private final List<OnServiceErrListener> b;
    private final String mAppKey;
    private final String mAppSecret;
    private final Map<String, String> mExtras;
    private Handler mHandler;
    private long mLoopInterval;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public final class BeaconHandler extends Handler {
        static final int MSG_ADD_ERR_LISTENER = 6;
        static final int MSG_ADD_UPDATE_LISTENER = 4;
        static final int MSG_ERR_CALLBACK = 7;
        static final int MSG_REMOVE_UPDATE_LISTENER = 5;
        static final int MSG_START = 0;
        static final int MSG_START_POLLING = 2;
        static final int MSG_STOP_POLLING = 3;
        static final int MSG_UPDATE = 1;

        BeaconHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            try {
                switch (message.what) {
                    case 0:
                        Beacon.this.c((Context) message.obj);
                        break;
                    case 1:
                        Beacon.this.d((Context) message.obj);
                        break;
                    case 2:
                        Beacon.this.e((Context) message.obj);
                        break;
                    case 3:
                        Beacon.this.b();
                        break;
                    case 4:
                        Beacon.this.a((OnUpdateListener) message.obj);
                        break;
                    case 5:
                        Beacon.this.b((OnUpdateListener) message.obj);
                        break;
                    case 6:
                        Beacon.this.a((OnServiceErrListener) message.obj);
                        break;
                    case 7:
                        Beacon.this.b((Error) message.obj);
                        break;
                }
            } catch (Exception e) {
                Log.i("beacon", e.getMessage(), e);
            }
        }
    }

    /* loaded from: classes.dex */
    public static final class Builder {
        String mAppKey;
        String mAppSecret;
        Map<String, String> mExtras = new HashMap();
        long mLoopInterval = 300000;

        public Builder appKey(String str) {
            this.mAppKey = str.trim();
            return this;
        }

        public Builder appSecret(String str) {
            this.mAppSecret = str.trim();
            return this;
        }

        public Beacon build() {
            return new Beacon(this);
        }

        public Builder extras(Map<String, String> map) {
            this.mExtras.putAll(map);
            return this;
        }

        public Builder loopInterval(long j) {
            if (j < 60000) {
                this.mLoopInterval = 60000L;
            } else {
                this.mLoopInterval = j;
            }
            return this;
        }
    }

    /* loaded from: classes.dex */
    public static final class Config {
        public final String key;
        public final String value;

        public Config(String str, String str2) {
            this.key = str;
            this.value = str2;
        }
    }

    /* loaded from: classes.dex */
    public static final class Error {
        public final String errCode;
        public final String errMsg;

        /* JADX INFO: Access modifiers changed from: package-private */
        public Error(String str, String str2) {
            this.errCode = str;
            this.errMsg = str2;
        }
    }

    /* loaded from: classes.dex */
    public interface OnServiceErrListener {
        void onErr(Error error);
    }

    /* loaded from: classes.dex */
    public interface OnUpdateListener {
        void onUpdate(List<Config> list);
    }

    private Beacon(Builder builder) {
        this.f72a = new ArrayList();
        this.b = new ArrayList();
        this.a = 255;
        this.mAppKey = builder.mAppKey;
        this.mAppSecret = builder.mAppSecret;
        this.mExtras = builder.mExtras;
        this.mLoopInterval = builder.mLoopInterval;
        this.f71a = new b(this);
        HandlerThread handlerThread = new HandlerThread("Beacon Daemon");
        this.f70a = handlerThread;
        handlerThread.start();
        a();
    }

    private void a() {
        this.mHandler = new BeaconHandler(this.f70a.getLooper());
    }

    private void a(Context context) {
        Message obtain = Message.obtain();
        obtain.what = 1;
        obtain.obj = context;
        this.mHandler.sendMessage(obtain);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(OnServiceErrListener onServiceErrListener) {
        this.b.add(onServiceErrListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(OnUpdateListener onUpdateListener) {
        this.f72a.add(onUpdateListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        if (Build.VERSION.SDK_INT >= 18) {
            this.mHandler.getLooper().quitSafely();
        } else {
            this.mHandler.getLooper().quit();
        }
        a();
    }

    private void b(Context context) {
        Message obtain = Message.obtain();
        obtain.what = 2;
        obtain.obj = context;
        this.mHandler.sendMessage(obtain);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(Error error) {
        for (OnServiceErrListener onServiceErrListener : this.b) {
            onServiceErrListener.onErr(error);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(OnUpdateListener onUpdateListener) {
        this.f72a.remove(onUpdateListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(Context context) {
        b(context);
        this.a = 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d(Context context) {
        this.f71a.m33a(context, this.mAppKey, this.mAppSecret, this.mExtras);
        List<Config> a = this.f71a.a();
        for (OnUpdateListener onUpdateListener : this.f72a) {
            onUpdateListener.onUpdate(a);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e(Context context) {
        if (this.mHandler.hasMessages(2)) {
            this.mHandler.removeMessages(2);
        }
        a(context);
        this.mHandler.sendEmptyMessageDelayed(2, this.mLoopInterval);
    }

    private boolean isStarted() {
        return this.a == 1;
    }

    public static final void setPrepare(boolean z) {
        a.a = z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(Error error) {
        Message obtain = Message.obtain();
        obtain.what = 7;
        obtain.obj = error;
        this.mHandler.sendMessage(obtain);
    }

    public void addServiceErrListener(OnServiceErrListener onServiceErrListener) {
        Message obtain = Message.obtain();
        obtain.what = 6;
        obtain.obj = onServiceErrListener;
        this.mHandler.sendMessage(obtain);
    }

    public void addUpdateListener(OnUpdateListener onUpdateListener) {
        Message obtain = Message.obtain();
        obtain.what = 4;
        obtain.obj = onUpdateListener;
        this.mHandler.sendMessage(obtain);
    }

    public List<Config> getConfigs() {
        return this.f71a.a();
    }

    public void start(Context context) {
        if (isStarted()) {
            return;
        }
        Message obtain = Message.obtain();
        obtain.what = 0;
        obtain.obj = context;
        this.mHandler.sendMessage(obtain);
    }

    public void stop() {
        if (isStarted()) {
            Message obtain = Message.obtain();
            obtain.what = 3;
            this.mHandler.sendMessage(obtain);
        }
    }
}
