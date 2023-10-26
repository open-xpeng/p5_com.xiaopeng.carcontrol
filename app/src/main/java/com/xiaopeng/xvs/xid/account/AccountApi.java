package com.xiaopeng.xvs.xid.account;

import com.xiaopeng.xvs.xid.account.api.IAccount;

/* loaded from: classes2.dex */
public class AccountApi {
    public static IAccount getApi() {
        return AccountClientImpl.getInstance();
    }
}
