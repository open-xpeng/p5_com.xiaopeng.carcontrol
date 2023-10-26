package com.xiaopeng.smartcontrol.ipc.client.ipc;

/* loaded from: classes2.dex */
public interface IpcClient {

    /* loaded from: classes2.dex */
    public interface IpcCallback {
        public static final int STATUS_DIED = 2;
        public static final int STATUS_START = 1;

        void onReceived(String str, String str2, String str3, String str4);

        void onServerStatus(String str, int i);
    }

    String call(String str, String str2, String str3, String str4);
}
