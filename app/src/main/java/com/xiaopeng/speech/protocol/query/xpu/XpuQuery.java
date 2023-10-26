package com.xiaopeng.speech.protocol.query.xpu;

import com.xiaopeng.speech.SpeechQuery;

/* loaded from: classes2.dex */
public class XpuQuery extends SpeechQuery<IXpuCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public int getAutoPilotStatus(String str, String str2) {
        return ((IXpuCaller) this.mQueryCaller).getAutoPilotStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getALCStatus(String str, String str2) {
        return ((IXpuCaller) this.mQueryCaller).getALCStatus();
    }
}
