package com.xiaopeng.speech.protocol.query.carair;

import com.alibaba.fastjson.parser.JSONLexer;
import com.xiaopeng.speech.annotation.IQueryProcessor;
import com.xiaopeng.speech.protocol.event.query.QueryCarAirEvent;

/* loaded from: classes2.dex */
public class CarAirQuery_Processor implements IQueryProcessor {
    private CarAirQuery mTarget;

    public CarAirQuery_Processor(CarAirQuery carAirQuery) {
        this.mTarget = carAirQuery;
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public Object querySensor(String str, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -2107526581:
                if (str.equals(QueryCarAirEvent.IS_SUPPORT_MP_QUERY)) {
                    c = 0;
                    break;
                }
                break;
            case -1859599012:
                if (str.equals(QueryCarAirEvent.OUTSIDE_MP_LEVEL_QUERY)) {
                    c = 1;
                    break;
                }
                break;
            case -1730121586:
                if (str.equals(QueryCarAirEvent.GET_AC_LEFT_REAR_SEAT_HEAT_LV)) {
                    c = 2;
                    break;
                }
                break;
            case -1412576213:
                if (str.equals(QueryCarAirEvent.IS_SUPPORT_PSN_SEAT_HEAT)) {
                    c = 3;
                    break;
                }
                break;
            case -1303288855:
                if (str.equals(QueryCarAirEvent.IS_SUPPORT_DECIMAL_VALUE)) {
                    c = 4;
                    break;
                }
                break;
            case -1296863527:
                if (str.equals(QueryCarAirEvent.GET_WINDS_STATUS)) {
                    c = 5;
                    break;
                }
                break;
            case -1288077686:
                if (str.equals(QueryCarAirEvent.AC_PANEL_STATUS)) {
                    c = 6;
                    break;
                }
                break;
            case -1281041456:
                if (str.equals(QueryCarAirEvent.IS_SUPPORT_SEAT_BLOW)) {
                    c = 7;
                    break;
                }
                break;
            case -1280869874:
                if (str.equals(QueryCarAirEvent.IS_SUPPORT_SEAT_HEAT)) {
                    c = '\b';
                    break;
                }
                break;
            case -1142835157:
                if (str.equals(QueryCarAirEvent.IS_SUPPORT_TEMP_DUAL)) {
                    c = '\t';
                    break;
                }
                break;
            case -882663067:
                if (str.equals(QueryCarAirEvent.GET_AIR_LEVEL)) {
                    c = '\n';
                    break;
                }
                break;
            case -538746711:
                if (str.equals(QueryCarAirEvent.GET_AC_INTELLIGENT_PASSENGER)) {
                    c = 11;
                    break;
                }
                break;
            case -510636961:
                if (str.equals(QueryCarAirEvent.IS_FAST_FRIDGE)) {
                    c = '\f';
                    break;
                }
                break;
            case -508546549:
                if (str.equals(QueryCarAirEvent.IS_SUPPORT_PM25)) {
                    c = '\r';
                    break;
                }
                break;
            case 173810349:
                if (str.equals(QueryCarAirEvent.AC_IS_SUPPORT_PERFUME)) {
                    c = 14;
                    break;
                }
                break;
            case 389659321:
                if (str.equals(QueryCarAirEvent.DRI_WIND_DIRECTION_MODE)) {
                    c = 15;
                    break;
                }
                break;
            case 433798074:
                if (str.equals(QueryCarAirEvent.AC_SUPPORT_REAR_SEAT_HEAT)) {
                    c = 16;
                    break;
                }
                break;
            case 500789806:
                if (str.equals(QueryCarAirEvent.GET_TEMP_MAX)) {
                    c = 17;
                    break;
                }
                break;
            case 500790044:
                if (str.equals(QueryCarAirEvent.GET_TEMP_MIN)) {
                    c = 18;
                    break;
                }
                break;
            case 656839565:
                if (str.equals(QueryCarAirEvent.GET_SEAT_BLOW_MAX)) {
                    c = 19;
                    break;
                }
                break;
            case 656839803:
                if (str.equals(QueryCarAirEvent.GET_SEAT_BLOW_MIN)) {
                    c = 20;
                    break;
                }
                break;
            case 706847842:
                if (str.equals(QueryCarAirEvent.GET_WIND_MAX)) {
                    c = 21;
                    break;
                }
                break;
            case 706848080:
                if (str.equals(QueryCarAirEvent.GET_WIND_MIN)) {
                    c = 22;
                    break;
                }
                break;
            case 722046538:
                if (str.equals(QueryCarAirEvent.GET_HEAT_MAX)) {
                    c = 23;
                    break;
                }
                break;
            case 722046776:
                if (str.equals(QueryCarAirEvent.GET_HEAT_MIN)) {
                    c = 24;
                    break;
                }
                break;
            case 783003410:
                if (str.equals(QueryCarAirEvent.IS_FRESH_AIR)) {
                    c = 25;
                    break;
                }
                break;
            case 844632651:
                if (str.equals(QueryCarAirEvent.GET_AC_RIGHT_REAR_SEAT_HEAT_LV)) {
                    c = JSONLexer.EOI;
                    break;
                }
                break;
            case 1338650683:
                if (str.equals(QueryCarAirEvent.IS_SUPPORT_AUTO_OFF)) {
                    c = 27;
                    break;
                }
                break;
            case 1386894569:
                if (str.equals(QueryCarAirEvent.IS_SUPPORT_MIRROR_HEAT)) {
                    c = 28;
                    break;
                }
                break;
            case 1399563517:
                if (str.equals("ac.wind.mode")) {
                    c = 29;
                    break;
                }
                break;
            case 1460629481:
                if (str.equals(QueryCarAirEvent.PSN_WIND_DIRECTION_MODE)) {
                    c = 30;
                    break;
                }
                break;
            case 1639524806:
                if (str.equals(QueryCarAirEvent.OUTSIDE_MP_QUERY)) {
                    c = 31;
                    break;
                }
                break;
            case 1649865015:
                if (str.equals(QueryCarAirEvent.IS_SUPPORT_PURIFIER)) {
                    c = ' ';
                    break;
                }
                break;
            case 2119585779:
                if (str.equals(QueryCarAirEvent.IS_SUPPORT_DEMIST_FOOT)) {
                    c = '!';
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return Integer.valueOf(this.mTarget.isSupportMpQuery(str, str2));
            case 1:
                return Integer.valueOf(this.mTarget.getPmLevelQuality(str, str2));
            case 2:
                return Integer.valueOf(this.mTarget.getLeftRearSeatHeatLevel(str, str2));
            case 3:
                return Boolean.valueOf(this.mTarget.isSupportPsnSeatHeat(str, str2));
            case 4:
                return Boolean.valueOf(this.mTarget.isSupportDecimalValue(str, str2));
            case 5:
                return this.mTarget.getWindsStatus(str, str2);
            case 6:
                return Integer.valueOf(this.mTarget.getAcPanelStatus(str, str2));
            case 7:
                return Boolean.valueOf(this.mTarget.isSupportSeatBlow(str, str2));
            case '\b':
                return Boolean.valueOf(this.mTarget.isSupportSeatHeat(str, str2));
            case '\t':
                return Boolean.valueOf(this.mTarget.isSupportTempDual(str, str2));
            case '\n':
                return Integer.valueOf(this.mTarget.getAirLevel(str, str2));
            case 11:
                return Integer.valueOf(this.mTarget.getIntelligentPassengerStatus(str, str2));
            case '\f':
                return Boolean.valueOf(this.mTarget.isFastFridge(str, str2));
            case '\r':
                return Boolean.valueOf(this.mTarget.isSupportPm25(str, str2));
            case 14:
                return Boolean.valueOf(this.mTarget.isAcSupportPerfume(str, str2));
            case 15:
                return Integer.valueOf(this.mTarget.getDriWindDirectionMode(str, str2));
            case 16:
                return Boolean.valueOf(this.mTarget.isSupportRearSeatHeat(str, str2));
            case 17:
                return Double.valueOf(this.mTarget.getTempMax(str, str2));
            case 18:
                return Double.valueOf(this.mTarget.getTempMin(str, str2));
            case 19:
                return Integer.valueOf(this.mTarget.getSeatBlowMax(str, str2));
            case 20:
                return Integer.valueOf(this.mTarget.getSeatBlowMin(str, str2));
            case 21:
                return Integer.valueOf(this.mTarget.getWindMax(str, str2));
            case 22:
                return Integer.valueOf(this.mTarget.getWindMin(str, str2));
            case 23:
                return Integer.valueOf(this.mTarget.getHeatMax(str, str2));
            case 24:
                return Integer.valueOf(this.mTarget.getHeatMin(str, str2));
            case 25:
                return Boolean.valueOf(this.mTarget.isFreshAir(str, str2));
            case 26:
                return Integer.valueOf(this.mTarget.getRightRearSeatHeatLevel(str, str2));
            case 27:
                return Boolean.valueOf(this.mTarget.isSupportAutoOff(str, str2));
            case 28:
                return Integer.valueOf(this.mTarget.isSupportMirrorHeat(str, str2));
            case 29:
                return Integer.valueOf(this.mTarget.getWindMode(str, str2));
            case 30:
                return Integer.valueOf(this.mTarget.getPsnWindDirectionMode(str, str2));
            case 31:
                return Integer.valueOf(this.mTarget.getOutSidePmQuality(str, str2));
            case ' ':
                return Boolean.valueOf(this.mTarget.isSupportPurifier(str, str2));
            case '!':
                return Boolean.valueOf(this.mTarget.isSupportDemistFoot(str, str2));
            default:
                return null;
        }
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public String[] getQueryEvents() {
        return new String[]{QueryCarAirEvent.IS_SUPPORT_AUTO_OFF, QueryCarAirEvent.IS_SUPPORT_DEMIST_FOOT, QueryCarAirEvent.GET_WIND_MAX, QueryCarAirEvent.GET_WIND_MIN, QueryCarAirEvent.GET_TEMP_MAX, QueryCarAirEvent.GET_TEMP_MIN, QueryCarAirEvent.IS_SUPPORT_DECIMAL_VALUE, QueryCarAirEvent.IS_SUPPORT_PURIFIER, QueryCarAirEvent.IS_SUPPORT_PM25, QueryCarAirEvent.GET_AIR_LEVEL, QueryCarAirEvent.IS_SUPPORT_SEAT_HEAT, QueryCarAirEvent.IS_SUPPORT_PSN_SEAT_HEAT, QueryCarAirEvent.GET_HEAT_MAX, QueryCarAirEvent.GET_HEAT_MIN, QueryCarAirEvent.IS_SUPPORT_SEAT_BLOW, QueryCarAirEvent.GET_SEAT_BLOW_MAX, QueryCarAirEvent.GET_SEAT_BLOW_MIN, QueryCarAirEvent.IS_FAST_FRIDGE, QueryCarAirEvent.IS_FRESH_AIR, QueryCarAirEvent.IS_SUPPORT_TEMP_DUAL, QueryCarAirEvent.GET_WINDS_STATUS, QueryCarAirEvent.IS_SUPPORT_MP_QUERY, "ac.wind.mode", QueryCarAirEvent.IS_SUPPORT_MIRROR_HEAT, QueryCarAirEvent.OUTSIDE_MP_QUERY, QueryCarAirEvent.DRI_WIND_DIRECTION_MODE, QueryCarAirEvent.PSN_WIND_DIRECTION_MODE, QueryCarAirEvent.AC_PANEL_STATUS, QueryCarAirEvent.OUTSIDE_MP_LEVEL_QUERY, QueryCarAirEvent.GET_AC_INTELLIGENT_PASSENGER, QueryCarAirEvent.AC_SUPPORT_REAR_SEAT_HEAT, QueryCarAirEvent.GET_AC_LEFT_REAR_SEAT_HEAT_LV, QueryCarAirEvent.GET_AC_RIGHT_REAR_SEAT_HEAT_LV, QueryCarAirEvent.AC_IS_SUPPORT_PERFUME};
    }
}
