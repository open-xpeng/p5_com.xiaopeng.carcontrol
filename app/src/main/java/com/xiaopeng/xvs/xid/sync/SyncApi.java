package com.xiaopeng.xvs.xid.sync;

import com.xiaopeng.xvs.xid.sync.api.ISync;

/* loaded from: classes2.dex */
public class SyncApi {
    public static ISync getApi() {
        return SyncProviderImpl.getInstance();
    }
}
