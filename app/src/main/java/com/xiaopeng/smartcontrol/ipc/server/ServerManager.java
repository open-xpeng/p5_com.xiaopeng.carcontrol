package com.xiaopeng.smartcontrol.ipc.server;

import android.content.Context;
import com.xiaopeng.smartcontrol.ipc.server.ipc.AppServer;
import com.xiaopeng.smartcontrol.ipc.server.ipc.IpcServer;
import com.xiaopeng.smartcontrol.sdk.server.ServerConfig;
import com.xiaopeng.smartcontrol.utils.HandlerThreadHelper;
import com.xiaopeng.smartcontrol.utils.LogUtils;

/* loaded from: classes2.dex */
public class ServerManager {
    private static final String TAG = "SerMg";
    private ServerConfig mConfig;
    private HandlerThreadHelper mHandlerThreadHelper;
    private ServerListener mServerListener;

    private ServerManager() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class SingletonHolder {
        private static final ServerManager INSTANCE = new ServerManager();

        private SingletonHolder() {
        }
    }

    public static ServerManager get() {
        return SingletonHolder.INSTANCE;
    }

    public void init(Context context, ServerConfig serverConfig) {
        this.mConfig = serverConfig;
        this.mHandlerThreadHelper = new HandlerThreadHelper("Server_Send_Thread");
        AppServer.get().init(context);
        AppServer.get().setIpcCallback(new IpcServer.IpcCallback() { // from class: com.xiaopeng.smartcontrol.ipc.server.-$$Lambda$ServerManager$xUkEI8qNnXJcUquMCiq18UU8Uak
            @Override // com.xiaopeng.smartcontrol.ipc.server.ipc.IpcServer.IpcCallback
            public final String onCall(String str, String str2, String str3) {
                return ServerManager.this.lambda$init$0$ServerManager(str, str2, str3);
            }
        });
    }

    public /* synthetic */ String lambda$init$0$ServerManager(String str, String str2, String str3) {
        LogUtils.i(TAG, String.format("onCall-- module:%s, method:%s, param:%s", str, str2, LogUtils.stringLog(str3)));
        ServerListener serverListener = this.mServerListener;
        if (serverListener == null) {
            LogUtils.e(TAG, "mServerListener is null");
            return null;
        }
        try {
            return serverListener.onCall(str, str2, str3);
        } catch (Throwable th) {
            th.printStackTrace();
            LogUtils.e(TAG, "mServerListener onCall error");
            return null;
        }
    }

    public void setServerListener(ServerListener serverListener) {
        this.mServerListener = serverListener;
    }

    public void send(final String str, final String str2, final String str3) {
        ServerConfig serverConfig;
        if (this.mHandlerThreadHelper != null && (serverConfig = this.mConfig) != null && serverConfig.sendAsync()) {
            this.mHandlerThreadHelper.post(new Runnable() { // from class: com.xiaopeng.smartcontrol.ipc.server.-$$Lambda$ServerManager$8AHyaUsrdz2w2EOgvLEStQd_Dl8
                @Override // java.lang.Runnable
                public final void run() {
                    ServerManager.lambda$send$1(str, str2, str3);
                }
            });
            return;
        }
        LogUtils.i(TAG, String.format("send  module:%s, msgId:%s, data:%s", str, str2, LogUtils.stringLog(str3)));
        AppServer.get().send(str, str2, str3);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$send$1(String str, String str2, String str3) {
        LogUtils.i(TAG, String.format("send  module:%s, msgId:%s, data:%s", str, str2, LogUtils.stringLog(str3)));
        AppServer.get().send(str, str2, str3);
    }
}
