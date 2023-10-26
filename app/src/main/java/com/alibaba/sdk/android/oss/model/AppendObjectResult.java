package com.alibaba.sdk.android.oss.model;

/* loaded from: classes.dex */
public class AppendObjectResult extends OSSResult {
    private long nextPosition;
    private String objectCRC64;

    public long getNextPosition() {
        return this.nextPosition;
    }

    public void setNextPosition(Long l) {
        this.nextPosition = l.longValue();
    }

    public String getObjectCRC64() {
        return this.objectCRC64;
    }

    public void setObjectCRC64(String str) {
        this.objectCRC64 = str;
    }
}
