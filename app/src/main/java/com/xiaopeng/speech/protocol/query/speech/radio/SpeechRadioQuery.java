package com.xiaopeng.speech.protocol.query.speech.radio;

import com.xiaopeng.speech.SpeechQuery;

/* loaded from: classes2.dex */
public class SpeechRadioQuery extends SpeechQuery<ISpeechRadioQueryCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public String getAudioDspStatus(String str, String str2) {
        return ((ISpeechRadioQueryCaller) this.mQueryCaller).getAudioDspStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getRadioStatus(String str, String str2) {
        return ((ISpeechRadioQueryCaller) this.mQueryCaller).getRadioStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getRadioVolumeAutoFocus(String str, String str2) {
        return ((ISpeechRadioQueryCaller) this.mQueryCaller).getRadioVolumeAutoFocus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int[] getRadioFrequency(String str, String str2) {
        return ((ISpeechRadioQueryCaller) this.mQueryCaller).getRadioFrequency();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int[] getAudioMode(String str, String str2) {
        return ((ISpeechRadioQueryCaller) this.mQueryCaller).getAudioMode();
    }
}
