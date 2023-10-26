package com.xiaopeng.speech.protocol.query.speech.radar;

import com.xiaopeng.speech.annotation.IQueryProcessor;
import com.xiaopeng.speech.protocol.event.query.speech.SpeechRadarEvent;

/* loaded from: classes2.dex */
public class SpeechRadarQuery_Processor implements IQueryProcessor {
    private SpeechRadarQuery mTarget;

    public SpeechRadarQuery_Processor(SpeechRadarQuery speechRadarQuery) {
        this.mTarget = speechRadarQuery;
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public Object querySensor(String str, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -2090752681:
                if (str.equals(SpeechRadarEvent.RADAR_FRONT_LV)) {
                    c = 0;
                    break;
                }
                break;
            case -636748384:
                if (str.equals(SpeechRadarEvent.RADAR_TAIL_DATA)) {
                    c = 1;
                    break;
                }
                break;
            case -3443915:
                if (str.equals(SpeechRadarEvent.RADAR_FRONT_FAULT)) {
                    c = 2;
                    break;
                }
                break;
            case 781459712:
                if (str.equals(SpeechRadarEvent.RADAR_TAIL_LV)) {
                    c = 3;
                    break;
                }
                break;
            case 831113271:
                if (str.equals(SpeechRadarEvent.RADAR_FRONT_DATA)) {
                    c = 4;
                    break;
                }
                break;
            case 1737485036:
                if (str.equals(SpeechRadarEvent.RADAR_TAIL_FAULT)) {
                    c = 5;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return this.mTarget.getFrontRadarLevel(str, str2);
            case 1:
                return this.mTarget.getTailRadarData(str, str2);
            case 2:
                return this.mTarget.getFrontRadarFaultSt(str, str2);
            case 3:
                return this.mTarget.getTailRadarLevel(str, str2);
            case 4:
                return this.mTarget.getFrontRadarData(str, str2);
            case 5:
                return this.mTarget.getTailRadarFaultSt(str, str2);
            default:
                return null;
        }
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public String[] getQueryEvents() {
        return new String[]{SpeechRadarEvent.RADAR_FRONT_DATA, SpeechRadarEvent.RADAR_TAIL_DATA, SpeechRadarEvent.RADAR_FRONT_LV, SpeechRadarEvent.RADAR_TAIL_LV, SpeechRadarEvent.RADAR_FRONT_FAULT, SpeechRadarEvent.RADAR_TAIL_FAULT};
    }
}
