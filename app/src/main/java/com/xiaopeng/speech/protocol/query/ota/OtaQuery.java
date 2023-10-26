package com.xiaopeng.speech.protocol.query.ota;

import com.xiaopeng.speech.SpeechQuery;

/* loaded from: classes2.dex */
public class OtaQuery extends SpeechQuery<IOtaCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isLatestVersion(String str, String str2) {
        return ((IOtaCaller) this.mQueryCaller).isLatestVersion();
    }
}
