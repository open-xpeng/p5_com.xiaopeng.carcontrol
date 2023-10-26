package com.xiaopeng.lib.framework.moduleinterface.syncmodule;

/* loaded from: classes2.dex */
public final class SyncOTPEvent {
    public IOTPCallback callback;
    public String deviceID;
    public String seq;

    public SyncOTPEvent(String str, String str2, IOTPCallback iOTPCallback) {
        this.seq = str;
        this.deviceID = str2;
        this.callback = iOTPCallback;
    }

    public String toString() {
        return "SyncOTPEvent { seq:" + this.seq + "; deviceID:" + this.deviceID + "; }";
    }
}
