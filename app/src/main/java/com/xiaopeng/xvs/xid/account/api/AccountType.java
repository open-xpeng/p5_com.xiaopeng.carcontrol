package com.xiaopeng.xvs.xid.account.api;

/* loaded from: classes2.dex */
public enum AccountType {
    TEMP(0),
    OWNER(1),
    USER(2),
    TENANT(3),
    DRIVER(4);
    
    final int mType;

    AccountType(int i) {
        this.mType = i;
    }
}
