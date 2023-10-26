package com.xiaopeng.speech;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.UserHandle;
import com.xiaopeng.speech.ISpeechEngine;
import com.xiaopeng.speech.common.SpeechConstant;
import com.xiaopeng.speech.common.util.LogUtils;
import com.xiaopeng.speech.common.util.SimpleCallbackList;
import com.xiaopeng.speech.common.util.WorkerHandler;
import java.util.Random;

/* loaded from: classes2.dex */
public class ConnectManager extends BroadcastReceiver implements ServiceConnection {
    private static final int MAX_RECONNECT_PERIOD = 3000;
    private static final int MIN_RECONNECT_PERIOD = 1500;
    private static final String TAG = "ConnectManager";
    private Context mContext;
    private volatile ISpeechEngine mSpeechEngine;
    private WorkerHandler mWorkerHandler;
    private SimpleCallbackList<OnConnectCallback> mCallbackList = new SimpleCallbackList<>();
    private Runnable mDoConnectRunner = new Runnable() { // from class: com.xiaopeng.speech.ConnectManager.5
        @Override // java.lang.Runnable
        public void run() {
            ConnectManager.this.connect();
        }
    };
    private Random mRandom = new Random();

    /* loaded from: classes2.dex */
    public interface OnConnectCallback {
        void onConnect(ISpeechEngine iSpeechEngine);

        void onDisconnect();
    }

    public ConnectManager(Context context) {
        this.mContext = context;
    }

    public void init(WorkerHandler workerHandler) {
        this.mRandom = new Random();
        this.mWorkerHandler = workerHandler;
    }

    public void registerReceiver() {
        if (this.mContext == null) {
            return;
        }
        LogUtils.i(TAG, "registerReceiver");
        this.mContext.registerReceiver(this, new IntentFilter("carspeechservice.SpeechServer.Start"));
    }

    public void connect() {
        this.mWorkerHandler.optPost(new Runnable() { // from class: com.xiaopeng.speech.ConnectManager.1
            @Override // java.lang.Runnable
            public void run() {
                LogUtils.i("connect ");
                if (ConnectManager.this.mSpeechEngine != null) {
                    LogUtils.i("connected,return");
                    return;
                }
                Intent intent = new Intent(SpeechConstant.SPEECH_SERVER_ACTION);
                intent.setPackage("com.xiaopeng.carspeechservice");
                ConnectManager.this.mContext.bindServiceAsUser(intent, ConnectManager.this, 1, UserHandle.CURRENT);
            }
        });
    }

    public void addCallback(OnConnectCallback onConnectCallback) {
        this.mCallbackList.addCallback(onConnectCallback);
    }

    public void removeCallback(OnConnectCallback onConnectCallback) {
        this.mCallbackList.removeCallback(onConnectCallback);
    }

    private void queryBinder(IBinder iBinder) {
        LogUtils.i("queryBinder");
        this.mSpeechEngine = ISpeechEngine.Stub.asInterface(iBinder);
        if (this.mSpeechEngine == null) {
            LogUtils.e("mSpeechEngine == null");
        } else {
            this.mWorkerHandler.optPost(new Runnable() { // from class: com.xiaopeng.speech.ConnectManager.2
                @Override // java.lang.Runnable
                public void run() {
                    Object[] collectCallbacks = ConnectManager.this.mCallbackList.collectCallbacks();
                    if (collectCallbacks != null) {
                        for (Object obj : collectCallbacks) {
                            if (ConnectManager.this.mSpeechEngine != null) {
                                ((OnConnectCallback) obj).onConnect(ConnectManager.this.mSpeechEngine);
                            }
                        }
                    }
                }
            });
        }
    }

    private void disconnect() {
        LogUtils.e("SpeechProxy binderDied");
        this.mSpeechEngine = null;
        this.mWorkerHandler.optPost(new Runnable() { // from class: com.xiaopeng.speech.ConnectManager.3
            @Override // java.lang.Runnable
            public void run() {
                Object[] collectCallbacks = ConnectManager.this.mCallbackList.collectCallbacks();
                if (collectCallbacks != null) {
                    for (Object obj : collectCallbacks) {
                        ((OnConnectCallback) obj).onDisconnect();
                    }
                }
                ConnectManager.this.reconnect();
            }
        });
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        queryBinder(iBinder);
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        disconnect();
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("carspeechservice.SpeechServer.Start")) {
            LogUtils.i("onReceive SPEECH_SERVER_ACTION_START");
            if (this.mSpeechEngine == null) {
                connect();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reconnect() {
        this.mWorkerHandler.optPost(new Runnable() { // from class: com.xiaopeng.speech.ConnectManager.4
            @Override // java.lang.Runnable
            public void run() {
                long createRandomKey = ConnectManager.this.createRandomKey(ConnectManager.MIN_RECONNECT_PERIOD, 3000);
                ConnectManager.this.mWorkerHandler.removeCallbacks(ConnectManager.this.mDoConnectRunner);
                ConnectManager.this.mWorkerHandler.postDelayed(ConnectManager.this.mDoConnectRunner, createRandomKey);
                LogUtils.i(this, "reconnect delay:%d", Long.valueOf(createRandomKey));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long createRandomKey(int i, int i2) {
        return i >= i2 ? i : (int) ((this.mRandom.nextFloat() * (i2 - i)) + i);
    }
}
