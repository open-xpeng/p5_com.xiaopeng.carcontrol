package com.xiaopeng.speech.protocol.query.carcontrol;

import com.alibaba.fastjson.parser.JSONLexer;
import com.xiaopeng.speech.annotation.IQueryProcessor;
import com.xiaopeng.speech.protocol.event.query.QueryCarControlEvent;

/* loaded from: classes2.dex */
public class CarControlQuery_Processor implements IQueryProcessor {
    private CarControlQuery mTarget;

    public CarControlQuery_Processor(CarControlQuery carControlQuery) {
        this.mTarget = carControlQuery;
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public Object querySensor(String str, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -2121187452:
                if (str.equals(QueryCarControlEvent.CONTROL_COMFORTABLE_DRIVING_MODE_SUPPORT)) {
                    c = 0;
                    break;
                }
                break;
            case -2054773087:
                if (str.equals(QueryCarControlEvent.GET_PAGE_OPEN_STATUS)) {
                    c = 1;
                    break;
                }
                break;
            case -2034277098:
                if (str.equals(QueryCarControlEvent.CONTROL_SCISSOR_DOOR_LEFT_RUNNING_SUPPORT)) {
                    c = 2;
                    break;
                }
                break;
            case -1897516928:
                if (str.equals(QueryCarControlEvent.GET_STATUS_LIGHT_ATMOSPHERE_COLOR)) {
                    c = 3;
                    break;
                }
                break;
            case -1678437728:
                if (str.equals(QueryCarControlEvent.CONTROL_LEG_HEIGHT_GET)) {
                    c = 4;
                    break;
                }
                break;
            case -1656659569:
                if (str.equals(QueryCarControlEvent.GET_EXTRA_TRUNK_STATUS)) {
                    c = 5;
                    break;
                }
                break;
            case -1550136303:
                if (str.equals(QueryCarControlEvent.CONTROL_SCISSOR_DOOR_RIGHT_RUNNING_SUPPORT)) {
                    c = 6;
                    break;
                }
                break;
            case -1521777381:
                if (str.equals(QueryCarControlEvent.GET_CURR_WIPER_LEVEL)) {
                    c = 7;
                    break;
                }
                break;
            case -1482534751:
                if (str.equals(QueryCarControlEvent.GET_SUPPORT_SEAT)) {
                    c = '\b';
                    break;
                }
                break;
            case -1431154399:
                if (str.equals(QueryCarControlEvent.CONTROL_SUPPORT_ENERGY_RECYCLE_REASON)) {
                    c = '\t';
                    break;
                }
                break;
            case -1414817355:
                if (str.equals(QueryCarControlEvent.CONTROL_CAPSULE_MODE)) {
                    c = '\n';
                    break;
                }
                break;
            case -1412321594:
                if (str.equals(QueryCarControlEvent.GET_SET_SPEECH_WAKEUP_STATUS)) {
                    c = 11;
                    break;
                }
                break;
            case -1392585057:
                if (str.equals(QueryCarControlEvent.GET_STATUS_OPEN_R_CHARGE_PORT)) {
                    c = '\f';
                    break;
                }
                break;
            case -1268774449:
                if (str.equals(QueryCarControlEvent.CONTROL_SCISSOR_DOOR_LEFT_CLOSE_SUPPORT)) {
                    c = '\r';
                    break;
                }
                break;
            case -1193245651:
                if (str.equals(QueryCarControlEvent.GET_SUPPORT_OPEN_TRUNK)) {
                    c = 14;
                    break;
                }
                break;
            case -1144792468:
                if (str.equals(QueryCarControlEvent.GET_STATUS_LIGHT_ATMOSPHERE_BRIGHTNESS)) {
                    c = 15;
                    break;
                }
                break;
            case -1139999239:
                if (str.equals(QueryCarControlEvent.IS_SUPPORT_CLOSE_MIRROR)) {
                    c = 16;
                    break;
                }
                break;
            case -1102263664:
                if (str.equals(QueryCarControlEvent.IS_SUPPORT_UNLOCK_TRUNK)) {
                    c = 17;
                    break;
                }
                break;
            case -952459949:
                if (str.equals(QueryCarControlEvent.GET_STATUS_CLOSE_R_CHARGE_PORT)) {
                    c = 18;
                    break;
                }
                break;
            case -945429027:
                if (str.equals(QueryCarControlEvent.CONTROL_VIP_CHAIR_STATUS)) {
                    c = 19;
                    break;
                }
                break;
            case -932480699:
                if (str.equals(QueryCarControlEvent.GET_STATUS_OPEN_L_CHARGE_PORT)) {
                    c = 20;
                    break;
                }
                break;
            case -894608748:
                if (str.equals(QueryCarControlEvent.CONTROL_XPEDAL_SUPPORT)) {
                    c = 21;
                    break;
                }
                break;
            case -598119942:
                if (str.equals(QueryCarControlEvent.XPU_NGP_STATUS)) {
                    c = 22;
                    break;
                }
                break;
            case -178106686:
                if (str.equals(QueryCarControlEvent.IS_SUPPORT_OPEN_R_CHARGE_PORT)) {
                    c = 23;
                    break;
                }
                break;
            case 6319454:
                if (str.equals(QueryCarControlEvent.CONTROL_GET_WINDOWS_STATE_SUPPORT)) {
                    c = 24;
                    break;
                }
                break;
            case 264514798:
                if (str.equals(QueryCarControlEvent.IS_SUPPORT_DRIVING_MODE)) {
                    c = 25;
                    break;
                }
                break;
            case 325191539:
                if (str.equals(QueryCarControlEvent.GET_WINDOW_STATUS)) {
                    c = JSONLexer.EOI;
                    break;
                }
                break;
            case 355863260:
                if (str.equals(QueryCarControlEvent.GET_MIRROR_STATUS)) {
                    c = 27;
                    break;
                }
                break;
            case 359526324:
                if (str.equals(QueryCarControlEvent.IS_SUPPORT_OPEN_L_CHARGE_PORT)) {
                    c = 28;
                    break;
                }
                break;
            case 425873261:
                if (str.equals(QueryCarControlEvent.GET_STATUS_CLOSE_L_CHARGE_PORT)) {
                    c = 29;
                    break;
                }
                break;
            case 484711863:
                if (str.equals(QueryCarControlEvent.IS_SUPPORT_CONTROL_MIRROR)) {
                    c = 30;
                    break;
                }
                break;
            case 548112633:
                if (str.equals(QueryCarControlEvent.IS_STEERING_MODE_ADJUSTABLE)) {
                    c = 31;
                    break;
                }
                break;
            case 580869774:
                if (str.equals(QueryCarControlEvent.IS_SUPPORT_CLOSE_R_CHARGE_PORT)) {
                    c = ' ';
                    break;
                }
                break;
            case 631100506:
                if (str.equals(QueryCarControlEvent.CONTROL_SCISSOR_DOOR_RIGHT_OPEN_SUPPORT)) {
                    c = '!';
                    break;
                }
                break;
            case 676171332:
                if (str.equals(QueryCarControlEvent.CONTROL_LOW_SPEED_ANALOG_SOUND_SUPPORT)) {
                    c = '\"';
                    break;
                }
                break;
            case 689991955:
                if (str.equals(QueryCarControlEvent.CONTROL_ELECTRIC_CURTAIN_SUPPORT)) {
                    c = '#';
                    break;
                }
                break;
            case 700759210:
                if (str.equals(QueryCarControlEvent.IS_SUPPORT_LITE_ATMOSPHERE)) {
                    c = '$';
                    break;
                }
                break;
            case 956386737:
                if (str.equals(QueryCarControlEvent.IS_SUPPORT_ENERGY_RECYCLE)) {
                    c = '%';
                    break;
                }
                break;
            case 1200226524:
                if (str.equals(QueryCarControlEvent.IS_SUPPORT_CLOSE_L_CHARGE_PORT)) {
                    c = '&';
                    break;
                }
                break;
            case 1333952193:
                if (str.equals(QueryCarControlEvent.GET_SUPPORT_CLOSE_TRUNK)) {
                    c = '\'';
                    break;
                }
                break;
            case 1338946660:
                if (str.equals(QueryCarControlEvent.GET_PSN_SUPPORT_SEAT)) {
                    c = '(';
                    break;
                }
                break;
            case 1364128650:
                if (str.equals(QueryCarControlEvent.CONTROL_SCISSOR_DOOR_RIGHT_CLOSE_SUPPORT)) {
                    c = ')';
                    break;
                }
                break;
            case 1397046919:
                if (str.equals(QueryCarControlEvent.IS_TAIRPRESSURE_NORMAL)) {
                    c = '*';
                    break;
                }
                break;
            case 1654546805:
                if (str.equals(QueryCarControlEvent.CONTROL_SCISSOR_DOOR_LEFT_OPEN_SUPPORT)) {
                    c = '+';
                    break;
                }
                break;
            case 1697319758:
                if (str.equals(QueryCarControlEvent.CONTROL_LAMP_SIGNAL_SUPPORT)) {
                    c = ',';
                    break;
                }
                break;
            case 1961007153:
                if (str.equals(QueryCarControlEvent.GET_TRUNK_STATUS)) {
                    c = '-';
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return Integer.valueOf(this.mTarget.getControlComfortableDrivingModeSupport(str, str2));
            case 1:
                return Integer.valueOf(this.mTarget.getGuiPageOpenState(str, str2));
            case 2:
                return Integer.valueOf(this.mTarget.getControlScissorDoorLeftRunningSupport(str, str2));
            case 3:
                return Integer.valueOf(this.mTarget.getAtmosphereColorStatus(str, str2));
            case 4:
                return Integer.valueOf(this.mTarget.getLegHeight(str, str2));
            case 5:
                return Integer.valueOf(this.mTarget.getExtraTrunkStatus(str, str2));
            case 6:
                return Integer.valueOf(this.mTarget.getControlScissorDoorRightRunningSupport(str, str2));
            case 7:
                return Integer.valueOf(this.mTarget.getWiperInterval(str, str2));
            case '\b':
                return Integer.valueOf(this.mTarget.getSupportSeat(str, str2));
            case '\t':
                return Integer.valueOf(this.mTarget.getControlSupportEnergyRecycleReason(str, str2));
            case '\n':
                return Integer.valueOf(this.mTarget.getCapsuleCurrentMode(str, str2));
            case 11:
                return Integer.valueOf(this.mTarget.getDoorKeyValue(str, str2));
            case '\f':
                return Integer.valueOf(this.mTarget.getStatusOpenRightChargePort(str, str2));
            case '\r':
                return Integer.valueOf(this.mTarget.getControlScissorDoorLeftCloseSupport(str, str2));
            case 14:
                return Integer.valueOf(this.mTarget.getSupportOpenTrunk(str, str2));
            case 15:
                return Integer.valueOf(this.mTarget.getAtmosphereBrightnessStatus(str, str2));
            case 16:
                return Boolean.valueOf(this.mTarget.isSupportCloseMirror(str, str2));
            case 17:
                return Boolean.valueOf(this.mTarget.isSupportUnlockTrunk(str, str2));
            case 18:
                return Integer.valueOf(this.mTarget.getStatusCloseRightChargePort(str, str2));
            case 19:
                return Integer.valueOf(this.mTarget.getVipChairStatus(str, str2));
            case 20:
                return Integer.valueOf(this.mTarget.getStatusOpenLeftChargePort(str, str2));
            case 21:
                return Integer.valueOf(this.mTarget.getControlXpedalSupport(str, str2));
            case 22:
                return Integer.valueOf(this.mTarget.getNgpStatus(str, str2));
            case 23:
                return Boolean.valueOf(this.mTarget.isSupportOpenRightChargePort(str, str2));
            case 24:
                return this.mTarget.getControlWindowsStateSupport(str, str2);
            case 25:
                return Boolean.valueOf(this.mTarget.isSupportDrivingMode(str, str2));
            case 26:
                return Integer.valueOf(this.mTarget.getWindowStatus(str, str2));
            case 27:
                return Integer.valueOf(this.mTarget.getMirrorStatus(str, str2));
            case 28:
                return Boolean.valueOf(this.mTarget.isSupportOpenLeftChargePort(str, str2));
            case 29:
                return Integer.valueOf(this.mTarget.getStatusCloseLeftChargePort(str, str2));
            case 30:
                return Boolean.valueOf(this.mTarget.isSupportControlMirror(str, str2));
            case 31:
                return Integer.valueOf(this.mTarget.isSteeringModeAdjustable(str, str2));
            case ' ':
                return Boolean.valueOf(this.mTarget.isSupportCloseRightChargePort(str, str2));
            case '!':
                return Integer.valueOf(this.mTarget.getControlScissorDoorCloseSupport(str, str2));
            case '\"':
                return Integer.valueOf(this.mTarget.getControlLowSpeedAnalogSoundSupport(str, str2));
            case '#':
                return Integer.valueOf(this.mTarget.getControlElectricCurtainSupport(str, str2));
            case '$':
                return Boolean.valueOf(this.mTarget.isSupportAtmosphere(str, str2));
            case '%':
                return Boolean.valueOf(this.mTarget.isSupportEngryRecycle(str, str2));
            case '&':
                return Boolean.valueOf(this.mTarget.isSupportCloseLeftChargePort(str, str2));
            case '\'':
                return Integer.valueOf(this.mTarget.getSupportCloseTrunk(str, str2));
            case '(':
                return Integer.valueOf(this.mTarget.getSupportPsnSeat(str, str2));
            case ')':
                return Integer.valueOf(this.mTarget.getControlScissorDoorRightCloseSupport(str, str2));
            case '*':
                return Boolean.valueOf(this.mTarget.isTirePressureNormal(str, str2));
            case '+':
                return Integer.valueOf(this.mTarget.getControlScissorDoorLeftOpenSupport(str, str2));
            case ',':
                return Integer.valueOf(this.mTarget.getControlLampSignalSupport(str, str2));
            case '-':
                return Integer.valueOf(this.mTarget.getTrunkStatus(str, str2));
            default:
                return null;
        }
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public String[] getQueryEvents() {
        return new String[]{QueryCarControlEvent.IS_SUPPORT_CLOSE_MIRROR, QueryCarControlEvent.GET_MIRROR_STATUS, QueryCarControlEvent.GET_SUPPORT_OPEN_TRUNK, QueryCarControlEvent.GET_SUPPORT_CLOSE_TRUNK, QueryCarControlEvent.GET_WINDOW_STATUS, QueryCarControlEvent.IS_SUPPORT_ENERGY_RECYCLE, QueryCarControlEvent.GET_SUPPORT_SEAT, QueryCarControlEvent.IS_SUPPORT_DRIVING_MODE, QueryCarControlEvent.IS_SUPPORT_LITE_ATMOSPHERE, QueryCarControlEvent.IS_TAIRPRESSURE_NORMAL, QueryCarControlEvent.IS_SUPPORT_UNLOCK_TRUNK, QueryCarControlEvent.IS_SUPPORT_CLOSE_L_CHARGE_PORT, QueryCarControlEvent.IS_SUPPORT_CLOSE_R_CHARGE_PORT, QueryCarControlEvent.IS_SUPPORT_OPEN_L_CHARGE_PORT, QueryCarControlEvent.IS_SUPPORT_OPEN_R_CHARGE_PORT, QueryCarControlEvent.IS_SUPPORT_CONTROL_MIRROR, QueryCarControlEvent.CONTROL_LEG_HEIGHT_GET, QueryCarControlEvent.GET_STATUS_CLOSE_L_CHARGE_PORT, QueryCarControlEvent.GET_STATUS_CLOSE_R_CHARGE_PORT, QueryCarControlEvent.GET_STATUS_OPEN_L_CHARGE_PORT, QueryCarControlEvent.GET_STATUS_OPEN_R_CHARGE_PORT, QueryCarControlEvent.GET_STATUS_LIGHT_ATMOSPHERE_BRIGHTNESS, QueryCarControlEvent.GET_STATUS_LIGHT_ATMOSPHERE_COLOR, QueryCarControlEvent.IS_STEERING_MODE_ADJUSTABLE, QueryCarControlEvent.GET_PAGE_OPEN_STATUS, QueryCarControlEvent.GET_CURR_WIPER_LEVEL, QueryCarControlEvent.GET_PSN_SUPPORT_SEAT, QueryCarControlEvent.GET_EXTRA_TRUNK_STATUS, QueryCarControlEvent.GET_TRUNK_STATUS, QueryCarControlEvent.GET_SET_SPEECH_WAKEUP_STATUS, QueryCarControlEvent.CONTROL_LOW_SPEED_ANALOG_SOUND_SUPPORT, QueryCarControlEvent.CONTROL_XPEDAL_SUPPORT, QueryCarControlEvent.CONTROL_SUPPORT_ENERGY_RECYCLE_REASON, QueryCarControlEvent.CONTROL_SCISSOR_DOOR_LEFT_OPEN_SUPPORT, QueryCarControlEvent.CONTROL_SCISSOR_DOOR_RIGHT_OPEN_SUPPORT, QueryCarControlEvent.CONTROL_SCISSOR_DOOR_LEFT_CLOSE_SUPPORT, QueryCarControlEvent.CONTROL_SCISSOR_DOOR_RIGHT_CLOSE_SUPPORT, QueryCarControlEvent.CONTROL_SCISSOR_DOOR_LEFT_RUNNING_SUPPORT, QueryCarControlEvent.CONTROL_SCISSOR_DOOR_RIGHT_RUNNING_SUPPORT, QueryCarControlEvent.CONTROL_ELECTRIC_CURTAIN_SUPPORT, QueryCarControlEvent.CONTROL_GET_WINDOWS_STATE_SUPPORT, QueryCarControlEvent.CONTROL_COMFORTABLE_DRIVING_MODE_SUPPORT, QueryCarControlEvent.CONTROL_LAMP_SIGNAL_SUPPORT, QueryCarControlEvent.XPU_NGP_STATUS, QueryCarControlEvent.CONTROL_VIP_CHAIR_STATUS, QueryCarControlEvent.CONTROL_CAPSULE_MODE};
    }
}
