package com.xiaopeng.speech.protocol.query.appstore;

import com.xiaopeng.speech.SpeechQuery;

/* loaded from: classes2.dex */
public class AppStoreQuery extends SpeechQuery<IAppStoreCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public int getOpenStatus(String str, String str2) {
        return ((IAppStoreCaller) this.mQueryCaller).getOpenStatus(str2);
    }
}
