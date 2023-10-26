package com.xiaopeng.speech.protocol.query.speech.radio;

import com.xiaopeng.speech.annotation.IQueryProcessor;
import com.xiaopeng.speech.protocol.event.query.speech.SpeechRadioEvent;

/* loaded from: classes2.dex */
public class SpeechRadioQuery_Processor implements IQueryProcessor {
    private SpeechRadioQuery mTarget;

    public SpeechRadioQuery_Processor(SpeechRadioQuery speechRadioQuery) {
        this.mTarget = speechRadioQuery;
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public Object querySensor(String str, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -1465934626:
                if (str.equals(SpeechRadioEvent.RADIO_STATE)) {
                    c = 0;
                    break;
                }
                break;
            case -971371186:
                if (str.equals(SpeechRadioEvent.RADIO_DSP)) {
                    c = 1;
                    break;
                }
                break;
            case -47471690:
                if (str.equals(SpeechRadioEvent.RADIO_MODE)) {
                    c = 2;
                    break;
                }
                break;
            case 928420887:
                if (str.equals(SpeechRadioEvent.RADIO_VOLUME_FOCUS)) {
                    c = 3;
                    break;
                }
                break;
            case 1543073577:
                if (str.equals(SpeechRadioEvent.RADIO_FREQUENCY)) {
                    c = 4;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return this.mTarget.getRadioStatus(str, str2);
            case 1:
                return this.mTarget.getAudioDspStatus(str, str2);
            case 2:
                return this.mTarget.getAudioMode(str, str2);
            case 3:
                return Integer.valueOf(this.mTarget.getRadioVolumeAutoFocus(str, str2));
            case 4:
                return this.mTarget.getRadioFrequency(str, str2);
            default:
                return null;
        }
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public String[] getQueryEvents() {
        return new String[]{SpeechRadioEvent.RADIO_DSP, SpeechRadioEvent.RADIO_STATE, SpeechRadioEvent.RADIO_VOLUME_FOCUS, SpeechRadioEvent.RADIO_FREQUENCY, SpeechRadioEvent.RADIO_MODE};
    }
}
