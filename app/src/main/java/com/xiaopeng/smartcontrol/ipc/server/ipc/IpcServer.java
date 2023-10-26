package com.xiaopeng.smartcontrol.ipc.server.ipc;

/* loaded from: classes2.dex */
public interface IpcServer {

    /* loaded from: classes2.dex */
    public interface IpcCallback {
        String onCall(String str, String str2, String str3);
    }

    String onCall(String str, String str2, String str3);

    void send(String str, String str2, String str3);

    void setIpcCallback(IpcCallback ipcCallback);
}
