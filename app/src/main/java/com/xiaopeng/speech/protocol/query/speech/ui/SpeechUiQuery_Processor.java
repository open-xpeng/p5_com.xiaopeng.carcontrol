package com.xiaopeng.speech.protocol.query.speech.ui;

import com.xiaopeng.speech.annotation.IQueryProcessor;
import com.xiaopeng.speech.protocol.event.query.speech.SpeechQueryEvent;

/* loaded from: classes2.dex */
public class SpeechUiQuery_Processor implements IQueryProcessor {
    private SpeechUiQuery mTarget;

    public SpeechUiQuery_Processor(SpeechUiQuery speechUiQuery) {
        this.mTarget = speechUiQuery;
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public Object querySensor(String str, String str2) {
        str.hashCode();
        if (str.equals(SpeechQueryEvent.IS_SUPERDIALOGUE_WHITELIST)) {
            return Boolean.valueOf(this.mTarget.isSuperDialogueWhitelist(str, str2));
        }
        if (str.equals(SpeechQueryEvent.IS_SUPERDIALOGUE_OPENED)) {
            return Boolean.valueOf(this.mTarget.isSuperDialogueOpened(str, str2));
        }
        return null;
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public String[] getQueryEvents() {
        return new String[]{SpeechQueryEvent.IS_SUPERDIALOGUE_WHITELIST, SpeechQueryEvent.IS_SUPERDIALOGUE_OPENED};
    }
}
