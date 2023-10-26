package com.xiaopeng.smartcontrol.ipc.client;

/* loaded from: classes2.dex */
public interface ClientListener {
    default void onAsyncCallResult(String str, String str2, String str3, String str4, String str5) {
    }

    void onReceived(String str, String str2, String str3, String str4);

    default void onServerStatus(String str, int i) {
    }
}
