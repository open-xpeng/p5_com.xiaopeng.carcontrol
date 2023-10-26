package com.xiaopeng.speech.protocol.query.speech.ac;

import com.xiaopeng.speech.annotation.IQueryProcessor;
import com.xiaopeng.speech.protocol.event.query.speech.SpeechACEvent;

/* loaded from: classes2.dex */
public class SpeechACQuery_Processor implements IQueryProcessor {
    private SpeechACQuery mTarget;

    public SpeechACQuery_Processor(SpeechACQuery speechACQuery) {
        this.mTarget = speechACQuery;
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public Object querySensor(String str, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -1445200882:
                if (str.equals(SpeechACEvent.AC_QUALITY_OUTSIDE_LV)) {
                    c = 0;
                    break;
                }
                break;
            case -1366962097:
                if (str.equals(SpeechACEvent.AC_PM25_VALUE)) {
                    c = 1;
                    break;
                }
                break;
            case -1250437706:
                if (str.equals(SpeechACEvent.AC_CIRCULATION_MODE)) {
                    c = 2;
                    break;
                }
                break;
            case -1220798579:
                if (str.equals(SpeechACEvent.AC_QUALITY_OUTSIDE_STATE)) {
                    c = 3;
                    break;
                }
                break;
            case -1002248266:
                if (str.equals(SpeechACEvent.AC_AUTO_MODE)) {
                    c = 4;
                    break;
                }
                break;
            case -611810135:
                if (str.equals(SpeechACEvent.AC_SEAT_BLOW_LV)) {
                    c = 5;
                    break;
                }
                break;
            case -6974638:
                if (str.equals(SpeechACEvent.AC_FRONT_DEFROST)) {
                    c = 6;
                    break;
                }
                break;
            case 204821931:
                if (str.equals(SpeechACEvent.AC_SEAT_HEAT_LV)) {
                    c = 7;
                    break;
                }
                break;
            case 262066763:
                if (str.equals(SpeechACEvent.AC_BACK_DEFROST_STATE)) {
                    c = '\b';
                    break;
                }
                break;
            case 634597058:
                if (str.equals(SpeechACEvent.AC_AUTO_MODE_LV)) {
                    c = '\t';
                    break;
                }
                break;
            case 641228857:
                if (str.equals(SpeechACEvent.AC_POWER)) {
                    c = '\n';
                    break;
                }
                break;
            case 846916078:
                if (str.equals(SpeechACEvent.AC_PSN_SEAT_HEAT_LV)) {
                    c = 11;
                    break;
                }
                break;
            case 1036740945:
                if (str.equals(SpeechACEvent.AC_PSN_TEMP)) {
                    c = '\f';
                    break;
                }
                break;
            case 1221934520:
                if (str.equals(SpeechACEvent.AC_INNER_TEMP)) {
                    c = '\r';
                    break;
                }
                break;
            case 1268308502:
                if (str.equals(SpeechACEvent.AC_QUALITY_STATE)) {
                    c = 14;
                    break;
                }
                break;
            case 1399563517:
                if (str.equals("ac.wind.mode")) {
                    c = 15;
                    break;
                }
                break;
            case 1486533834:
                if (str.equals(SpeechACEvent.AC_SYNC_STATE)) {
                    c = 16;
                    break;
                }
                break;
            case 1626812139:
                if (str.equals(SpeechACEvent.AC_WIND_SPEED_LV)) {
                    c = 17;
                    break;
                }
                break;
            case 1982026407:
                if (str.equals(SpeechACEvent.AC_BACK_MIRROR_HEAT_STATE)) {
                    c = 18;
                    break;
                }
                break;
            case 2074304014:
                if (str.equals(SpeechACEvent.AC_DRIVER_TEMP)) {
                    c = 19;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return Integer.valueOf(this.mTarget.getHvacQualityOutsideLevel(str, str2));
            case 1:
                return Integer.valueOf(this.mTarget.getHvacQualityInnerPM25Value(str, str2));
            case 2:
                return Integer.valueOf(this.mTarget.getHvacCirculationMode(str, str2));
            case 3:
                return Integer.valueOf(this.mTarget.getHvacQualityOutsideStatus(str, str2));
            case 4:
                return Boolean.valueOf(this.mTarget.getHvacAutoMode(str, str2));
            case 5:
                return Integer.valueOf(this.mTarget.getBCMSeatBlowLevel(str, str2));
            case 6:
                return Boolean.valueOf(this.mTarget.getHvacFrontDefrostMode(str, str2));
            case 7:
                return Integer.valueOf(this.mTarget.getBCMSeatHeatLevel(str, str2));
            case '\b':
                return Boolean.valueOf(this.mTarget.getBCMBackDefrostMode(str, str2));
            case '\t':
                return Integer.valueOf(this.mTarget.getHvacAutoModeBlowLevel(str, str2));
            case '\n':
                return Boolean.valueOf(this.mTarget.getHvacPowerMode(str, str2));
            case 11:
                return Integer.valueOf(this.mTarget.getPsnSeatHeatLv(str, str2));
            case '\f':
                return Double.valueOf(this.mTarget.getHvacTempPsnValue(str, str2));
            case '\r':
                return Double.valueOf(this.mTarget.getHvacInnerTemp(str, str2));
            case 14:
                return Boolean.valueOf(this.mTarget.getHvacQualityPurgeMode(str, str2));
            case 15:
                return Integer.valueOf(this.mTarget.getHvacWindBlowMode(str, str2));
            case 16:
                return Boolean.valueOf(this.mTarget.getHvacTempSyncMode(str, str2));
            case 17:
                return Integer.valueOf(this.mTarget.getHvacWindSpeedLevel(str, str2));
            case 18:
                return Boolean.valueOf(this.mTarget.getBCMBackMirrorHeatMode(str, str2));
            case 19:
                return Double.valueOf(this.mTarget.getHvacTempDriverValue(str, str2));
            default:
                return null;
        }
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public String[] getQueryEvents() {
        return new String[]{SpeechACEvent.AC_POWER, SpeechACEvent.AC_DRIVER_TEMP, SpeechACEvent.AC_PSN_TEMP, SpeechACEvent.AC_SYNC_STATE, SpeechACEvent.AC_AUTO_MODE, SpeechACEvent.AC_AUTO_MODE_LV, SpeechACEvent.AC_CIRCULATION_MODE, SpeechACEvent.AC_FRONT_DEFROST, "ac.wind.mode", SpeechACEvent.AC_WIND_SPEED_LV, SpeechACEvent.AC_QUALITY_STATE, SpeechACEvent.AC_PM25_VALUE, SpeechACEvent.AC_QUALITY_OUTSIDE_STATE, SpeechACEvent.AC_QUALITY_OUTSIDE_LV, SpeechACEvent.AC_BACK_DEFROST_STATE, SpeechACEvent.AC_BACK_MIRROR_HEAT_STATE, SpeechACEvent.AC_SEAT_HEAT_LV, SpeechACEvent.AC_SEAT_BLOW_LV, SpeechACEvent.AC_INNER_TEMP, SpeechACEvent.AC_PSN_SEAT_HEAT_LV};
    }
}
