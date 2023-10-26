package com.alibaba.sdk.android.oss.model;

/* loaded from: classes.dex */
public class OSSRequest {
    private boolean isAuthorizationRequired = true;
    private Enum CRC64 = CRC64Config.NULL;

    /* loaded from: classes.dex */
    public enum CRC64Config {
        NULL,
        YES,
        NO
    }

    public boolean isAuthorizationRequired() {
        return this.isAuthorizationRequired;
    }

    public void setIsAuthorizationRequired(boolean z) {
        this.isAuthorizationRequired = z;
    }

    public Enum getCRC64() {
        return this.CRC64;
    }

    public void setCRC64(Enum r1) {
        this.CRC64 = r1;
    }
}
