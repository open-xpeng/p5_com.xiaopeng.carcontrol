package com.xiaopeng.xvs.xid.auth;

import com.xiaopeng.xvs.xid.auth.api.IOAuth;

/* loaded from: classes2.dex */
public final class OAuthApi {
    public static IOAuth getApi() {
        return OAuthClientImpl.getInstance();
    }
}
