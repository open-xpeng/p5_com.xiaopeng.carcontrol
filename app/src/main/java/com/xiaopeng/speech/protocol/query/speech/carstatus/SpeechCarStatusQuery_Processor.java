package com.xiaopeng.speech.protocol.query.speech.carstatus;

import com.xiaopeng.speech.annotation.IQueryProcessor;
import com.xiaopeng.speech.protocol.event.query.speech.SpeechCarStatusEvent;

/* loaded from: classes2.dex */
public class SpeechCarStatusQuery_Processor implements IQueryProcessor {
    private SpeechCarStatusQuery mTarget;

    public SpeechCarStatusQuery_Processor(SpeechCarStatusQuery speechCarStatusQuery) {
        this.mTarget = speechCarStatusQuery;
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public Object querySensor(String str, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -1466888283:
                if (str.equals(SpeechCarStatusEvent.STATUS_AC_TEMPDRIVERVALUE)) {
                    c = 0;
                    break;
                }
                break;
            case -340187300:
                if (str.equals(SpeechCarStatusEvent.STATUS_AC_QUALITYPURGE)) {
                    c = 1;
                    break;
                }
                break;
            case 44068250:
                if (str.equals(SpeechCarStatusEvent.STATUS_AC_CIRCULATIONMODE)) {
                    c = 2;
                    break;
                }
                break;
            case 147719594:
                if (str.equals(SpeechCarStatusEvent.STATUS_AC_TEMPPSNVALUE)) {
                    c = 3;
                    break;
                }
                break;
            case 513100557:
                if (str.equals(SpeechCarStatusEvent.STATUS_CUR_MODE)) {
                    c = 4;
                    break;
                }
                break;
            case 641487541:
                if (str.equals(SpeechCarStatusEvent.STATUS_AC_POWER)) {
                    c = 5;
                    break;
                }
                break;
            case 1438013613:
                if (str.equals(SpeechCarStatusEvent.STATUS_AC_WINDBLOWMODE)) {
                    c = 6;
                    break;
                }
                break;
            case 1962152437:
                if (str.equals(SpeechCarStatusEvent.STATUS_AC_WINDSPEEDLEVEL)) {
                    c = 7;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return Float.valueOf(this.mTarget.getAcTempDriverValue(str, str2));
            case 1:
                return Boolean.valueOf(this.mTarget.getAcQualityPurgeStatus(str, str2));
            case 2:
                return Integer.valueOf(this.mTarget.getAcCirculationMode(str, str2));
            case 3:
                return Float.valueOf(this.mTarget.getAcTempPsnValue(str, str2));
            case 4:
                return Integer.valueOf(this.mTarget.getCurrentMode(str, str2));
            case 5:
                return Boolean.valueOf(this.mTarget.getAcPowerStatus(str, str2));
            case 6:
                return Integer.valueOf(this.mTarget.getAcWindBlowMode(str, str2));
            case 7:
                return Integer.valueOf(this.mTarget.getAcWindSpeedLevel(str, str2));
            default:
                return null;
        }
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public String[] getQueryEvents() {
        return new String[]{SpeechCarStatusEvent.STATUS_CUR_MODE, SpeechCarStatusEvent.STATUS_AC_POWER, SpeechCarStatusEvent.STATUS_AC_QUALITYPURGE, SpeechCarStatusEvent.STATUS_AC_TEMPDRIVERVALUE, SpeechCarStatusEvent.STATUS_AC_TEMPPSNVALUE, SpeechCarStatusEvent.STATUS_AC_WINDSPEEDLEVEL, SpeechCarStatusEvent.STATUS_AC_WINDBLOWMODE, SpeechCarStatusEvent.STATUS_AC_CIRCULATIONMODE};
    }
}
