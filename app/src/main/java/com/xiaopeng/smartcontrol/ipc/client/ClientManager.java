package com.xiaopeng.smartcontrol.ipc.client;

import android.content.Context;
import com.xiaopeng.smartcontrol.ipc.client.ipc.AppClient;
import com.xiaopeng.smartcontrol.ipc.client.ipc.IpcClient;
import com.xiaopeng.smartcontrol.sdk.client.ClientConfig;
import com.xiaopeng.smartcontrol.utils.Constants;
import com.xiaopeng.smartcontrol.utils.LogUtils;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes2.dex */
public class ClientManager {
    private static final String TAG = "ClientManager";
    private static final int THREAD_COUNT = 5;
    private ExecutorService mAsyncExecutor;
    private HashMap<String, AppClient> mClientMap;
    private final IpcClient.IpcCallback mIpcCallback;
    private ClientListener mListener;

    /* loaded from: classes2.dex */
    private static class SingletonHolder {
        private static final ClientManager INSTANCE = new ClientManager();

        private SingletonHolder() {
        }
    }

    public static ClientManager get() {
        return SingletonHolder.INSTANCE;
    }

    private ClientManager() {
        this.mIpcCallback = new IpcClient.IpcCallback() { // from class: com.xiaopeng.smartcontrol.ipc.client.ClientManager.1
            @Override // com.xiaopeng.smartcontrol.ipc.client.ipc.IpcClient.IpcCallback
            public void onReceived(String str, String str2, String str3, String str4) {
                LogUtils.i(ClientManager.TAG, String.format("onReceived-- appId:%s, module:%s, method:%s, data:%s", str, str2, str3, LogUtils.stringLog(str4)));
                if (ClientManager.this.mListener != null) {
                    ClientManager.this.mListener.onReceived(str, str2, str3, str4);
                } else {
                    LogUtils.e(ClientManager.TAG, "onReceived listener is null");
                }
            }

            @Override // com.xiaopeng.smartcontrol.ipc.client.ipc.IpcClient.IpcCallback
            public void onServerStatus(String str, int i) {
                LogUtils.i(ClientManager.TAG, String.format("onServerStatus-- appId:%s, status:%d", str, Integer.valueOf(i)));
                if (ClientManager.this.mListener != null) {
                    ClientManager.this.mListener.onServerStatus(str, i);
                } else {
                    LogUtils.e(ClientManager.TAG, "onServerStatus listener is null");
                }
            }
        };
    }

    public void init(Context context, ClientConfig clientConfig) {
        LogUtils.i(TAG, "init");
        HashMap<String, AppClient> hashMap = new HashMap<>();
        this.mClientMap = hashMap;
        hashMap.put("carcontrol", new AppClient(context, "carcontrol", this.mIpcCallback, clientConfig));
        this.mClientMap.put(Constants.APPID.SETTINGS, new AppClient(context, Constants.APPID.SETTINGS, this.mIpcCallback, clientConfig));
        this.mClientMap.put(Constants.APPID.POWERCENTER, new AppClient(context, Constants.APPID.POWERCENTER, this.mIpcCallback, clientConfig));
        this.mClientMap.put("hvac", new AppClient(context, "hvac", this.mIpcCallback, clientConfig));
        this.mAsyncExecutor = Executors.newFixedThreadPool(5);
    }

    public void setClientListener(ClientListener clientListener) {
        this.mListener = clientListener;
    }

    public void bindService(String str) {
        LogUtils.i(TAG, String.format("bindService  appId:%s", str));
        AppClient appClient = this.mClientMap.get(str);
        if (appClient != null) {
            appClient.bindService();
        }
    }

    public String call(String str, String str2, String str3, String str4) {
        String str5;
        LogUtils.i(TAG, String.format("call start = appId:%s, module:%s, method:%s, param:%s", str, str2, str3, str4));
        long currentTimeMillis = System.currentTimeMillis();
        AppClient appClient = this.mClientMap.get(str);
        if (appClient != null) {
            str5 = appClient.call(str, str2, str3, str4);
        } else {
            LogUtils.e(TAG, "no proper AppClient!!!");
            str5 = null;
        }
        LogUtils.i(TAG, String.format("call end = time:%s, appId:%s, module:%s, method:%s, param:%s, result:%s", Long.valueOf(System.currentTimeMillis() - currentTimeMillis), str, str2, str3, LogUtils.stringLog(str4), LogUtils.stringLog(str5)));
        return str5;
    }

    public void callAsync(String str, String str2, String str3, String str4, String str5) {
        this.mAsyncExecutor.submit(new AsyncRunnable(str2, str3, str4, str5, str));
    }

    public void destroy() {
        LogUtils.i(TAG, "destroy");
        for (String str : this.mClientMap.keySet()) {
            AppClient appClient = this.mClientMap.get(str);
            if (appClient != null) {
                appClient.destroy();
            }
        }
        this.mClientMap.clear();
    }

    /* loaded from: classes2.dex */
    private class AsyncRunnable implements Runnable {
        private String appId;
        private String method;
        private String module;
        private String param;
        private String requestId;

        AsyncRunnable(String str, String str2, String str3, String str4, String str5) {
            this.appId = str;
            this.module = str2;
            this.method = str3;
            this.param = str4;
            this.requestId = str5;
        }

        @Override // java.lang.Runnable
        public void run() {
            String call = ClientManager.this.call(this.appId, this.module, this.method, this.param);
            if (ClientManager.this.mListener != null) {
                ClientManager.this.mListener.onAsyncCallResult(this.requestId, this.appId, this.module, this.method, call);
            } else {
                LogUtils.e(ClientManager.TAG, "onAsyncCallResult listener is null");
            }
        }
    }
}
