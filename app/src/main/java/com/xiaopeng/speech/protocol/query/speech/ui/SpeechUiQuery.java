package com.xiaopeng.speech.protocol.query.speech.ui;

import com.xiaopeng.speech.SpeechQuery;

/* loaded from: classes2.dex */
public class SpeechUiQuery extends SpeechQuery<ISpeechUiCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSuperDialogueWhitelist(String str, String str2) {
        return ((ISpeechUiCaller) this.mQueryCaller).isSuperDialogueWhitelist();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSuperDialogueOpened(String str, String str2) {
        return ((ISpeechUiCaller) this.mQueryCaller).isSuperDialogueOpened();
    }
}
