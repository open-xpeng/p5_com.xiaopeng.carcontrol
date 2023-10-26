package com.xiaopeng.xvs.xid.sync.api;

/* loaded from: classes2.dex */
public enum SyncStatus {
    SYNC(0),
    ADD(1),
    UPDATE(2),
    DELETE(3),
    CONFLICT(4);
    
    private final int status;

    SyncStatus(int i) {
        this.status = i;
    }

    public int getStatus() {
        return this.status;
    }
}
