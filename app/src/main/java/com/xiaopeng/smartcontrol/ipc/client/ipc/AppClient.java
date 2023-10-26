package com.xiaopeng.smartcontrol.ipc.client.ipc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import com.xiaopeng.ipc.aidl.IPipeBus;
import com.xiaopeng.ipc.aidl.IPipeBusListener;
import com.xiaopeng.smartcontrol.ipc.client.ipc.AppClient;
import com.xiaopeng.smartcontrol.ipc.client.ipc.IpcClient;
import com.xiaopeng.smartcontrol.sdk.client.ClientConfig;
import com.xiaopeng.smartcontrol.utils.Apps;
import com.xiaopeng.smartcontrol.utils.Constants;
import com.xiaopeng.smartcontrol.utils.LogUtils;

/* loaded from: classes2.dex */
public class AppClient implements IpcClient {
    private static final int MAX_RETRY_COUNT = 5;
    private static final int RETRY_DELAY_TIME = 150;
    private static final String TAG = "AppClient";
    private static final int WAIT_TIMEOUT_DEFAULT = 2;
    private final String mAppId;
    private long mBindTs;
    private Handler mCallbackHandler;
    private final Context mContext;
    private Handler mHandler;
    private final IpcClient.IpcCallback mIpcCallback;
    private IPipeBus mPipeBusClient;
    private IPipeBusListener mPipeBusListener;
    private final String mPkgName;
    private int mWaitTimeout;
    private int retryCount;
    private Runnable mNotifyDied = new Runnable() { // from class: com.xiaopeng.smartcontrol.ipc.client.ipc.AppClient.1
        @Override // java.lang.Runnable
        public void run() {
            if (AppClient.this.mIpcCallback != null) {
                AppClient.this.mIpcCallback.onServerStatus(AppClient.this.mAppId, 2);
            }
        }
    };
    private Runnable mNotifyConnected = new Runnable() { // from class: com.xiaopeng.smartcontrol.ipc.client.ipc.AppClient.2
        @Override // java.lang.Runnable
        public void run() {
            if (AppClient.this.mIpcCallback != null) {
                AppClient.this.mIpcCallback.onServerStatus(AppClient.this.mAppId, 1);
            }
        }
    };
    private ServiceConnection connection = new ServiceConnection() { // from class: com.xiaopeng.smartcontrol.ipc.client.ipc.AppClient.3
        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            LogUtils.i(AppClient.TAG, "onServiceDisconnected: " + AppClient.this.mPkgName + " " + (System.currentTimeMillis() - AppClient.this.mBindTs));
            AppClient.this.deInitConnection();
            AppClient.this.bindService();
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            LogUtils.i(AppClient.TAG, "onServiceConnected: " + AppClient.this.mPkgName + " " + (System.currentTimeMillis() - AppClient.this.mBindTs));
            AppClient.this.initConnection(iBinder);
        }
    };

    public AppClient(Context context, String str, IpcClient.IpcCallback ipcCallback, ClientConfig clientConfig) {
        this.mWaitTimeout = 2;
        LogUtils.i(TAG, "init: " + str);
        this.mContext = context.getApplicationContext();
        this.mAppId = str;
        this.mPkgName = Apps.getPackageNames(str);
        this.mPipeBusListener = new BusListener();
        this.mIpcCallback = ipcCallback;
        if (clientConfig != null) {
            this.mWaitTimeout = clientConfig.getWaitTimeout();
            this.mCallbackHandler = clientConfig.getCallbackHandler();
        }
        this.mHandler = new Handler(Looper.getMainLooper()) { // from class: com.xiaopeng.smartcontrol.ipc.client.ipc.AppClient.4
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what == 1) {
                    AppClient.this.bindService();
                }
            }
        };
    }

    public void bindService() {
        IBinder service;
        boolean z = this.mPipeBusClient != null;
        if (!z && (service = ServiceManager.getService(Constants.SDK_BINDER_PREFFIX + this.mAppId)) != null) {
            LogUtils.i(TAG, "getBinder: success");
            initConnection(service);
            z = true;
        }
        if (z) {
            return;
        }
        LogUtils.i(TAG, "bindService: " + this.mPkgName);
        this.mBindTs = System.currentTimeMillis();
        Intent intent = new Intent(Constants.BIND_SERVICE_ACTION);
        intent.setPackage(this.mPkgName);
        this.mContext.bindService(intent, this.connection, 1);
        int i = this.retryCount;
        if (i < 5) {
            this.retryCount = i + 1;
            this.mHandler.sendEmptyMessageDelayed(1, 150L);
        }
    }

    @Override // com.xiaopeng.smartcontrol.ipc.client.ipc.IpcClient
    public String call(String str, String str2, String str3, String str4) {
        LogUtils.i(TAG, String.format("call: %s %s %s", str2, str3, str4));
        blockIfDisconnect();
        IPipeBus iPipeBus = this.mPipeBusClient;
        if (iPipeBus != null) {
            String[] strArr = new String[1];
            try {
                iPipeBus.call(str2, str3, str4, strArr);
                return strArr[0];
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        LogUtils.e(TAG, "service not connected!!!");
        return null;
    }

    public void destroy() {
        IPipeBusListener iPipeBusListener;
        LogUtils.i(TAG, "destroy");
        IPipeBus iPipeBus = this.mPipeBusClient;
        if (iPipeBus == null || (iPipeBusListener = this.mPipeBusListener) == null) {
            return;
        }
        try {
            iPipeBus.unRegisterListener(iPipeBusListener);
            this.mContext.unbindService(this.connection);
        } catch (RemoteException e) {
            LogUtils.e(TAG, "unRegisterListener exception=" + e.getMessage());
        }
    }

    private void blockIfDisconnect() {
        long currentTimeMillis = System.currentTimeMillis();
        while (this.mPipeBusClient == null) {
            try {
                Thread.sleep(50L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
            if (currentTimeMillis2 > this.mWaitTimeout * 1000) {
                LogUtils.i(TAG, "wait timeout !!! " + currentTimeMillis2);
                return;
            }
            IBinder service = ServiceManager.getService(Constants.SDK_BINDER_PREFFIX + this.mAppId);
            LogUtils.i(TAG, "wait getBinder: " + service);
            if (service != null) {
                initConnection(service);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initConnection(IBinder iBinder) {
        IPipeBusListener iPipeBusListener;
        IPipeBus asInterface = IPipeBus.Stub.asInterface(iBinder);
        this.mPipeBusClient = asInterface;
        if (asInterface != null && (iPipeBusListener = this.mPipeBusListener) != null) {
            try {
                asInterface.registerListener(iPipeBusListener);
                LogUtils.i(TAG, "registerListener true");
            } catch (RemoteException e) {
                LogUtils.e(TAG, "registerListener exception=" + e.getMessage());
            }
        } else {
            LogUtils.e(TAG, "registerListener false!!!");
        }
        Handler handler = this.mCallbackHandler;
        if (handler != null) {
            handler.post(this.mNotifyConnected);
        } else {
            this.mHandler.post(this.mNotifyConnected);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deInitConnection() {
        this.mPipeBusClient = null;
        Handler handler = this.mCallbackHandler;
        if (handler != null) {
            handler.post(this.mNotifyDied);
        } else {
            this.mHandler.post(this.mNotifyDied);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class BusListener extends IPipeBusListener.Stub {
        private BusListener() {
        }

        @Override // com.xiaopeng.ipc.aidl.IPipeBusListener
        public void onPipeBusEvent(final String str, final String str2, final String str3) throws RemoteException {
            LogUtils.i(AppClient.TAG, String.format("onReceived %s %s %s %s", AppClient.this.mPkgName, str, str2, str3));
            if (AppClient.this.mCallbackHandler != null) {
                AppClient.this.mCallbackHandler.post(new Runnable() { // from class: com.xiaopeng.smartcontrol.ipc.client.ipc.-$$Lambda$AppClient$BusListener$xFELtSm227FGksI6hL46WElcejI
                    @Override // java.lang.Runnable
                    public final void run() {
                        AppClient.BusListener.this.lambda$onPipeBusEvent$0$AppClient$BusListener(str, str2, str3);
                    }
                });
            } else {
                AppClient.this.mHandler.post(new Runnable() { // from class: com.xiaopeng.smartcontrol.ipc.client.ipc.-$$Lambda$AppClient$BusListener$emishgrBfxe90LSQFCKdecQxqaQ
                    @Override // java.lang.Runnable
                    public final void run() {
                        AppClient.BusListener.this.lambda$onPipeBusEvent$1$AppClient$BusListener(str, str2, str3);
                    }
                });
            }
        }

        public /* synthetic */ void lambda$onPipeBusEvent$0$AppClient$BusListener(String str, String str2, String str3) {
            if (AppClient.this.mIpcCallback != null) {
                AppClient.this.mIpcCallback.onReceived(AppClient.this.mAppId, str, str2, str3);
            }
        }

        public /* synthetic */ void lambda$onPipeBusEvent$1$AppClient$BusListener(String str, String str2, String str3) {
            if (AppClient.this.mIpcCallback != null) {
                AppClient.this.mIpcCallback.onReceived(AppClient.this.mAppId, str, str2, str3);
            }
        }
    }
}
