package com.xiaopeng.speech.protocol.query.speech.carcontrol;

import com.alibaba.fastjson.parser.JSONLexer;
import com.xiaopeng.speech.annotation.IQueryProcessor;
import com.xiaopeng.speech.protocol.event.query.speech.SpeechCarControlEvent;

/* loaded from: classes2.dex */
public class SpeechCarControlQuery_Processor implements IQueryProcessor {
    private SpeechCarControlQuery mTarget;

    public SpeechCarControlQuery_Processor(SpeechCarControlQuery speechCarControlQuery) {
        this.mTarget = speechCarControlQuery;
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public Object querySensor(String str, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -2060488725:
                if (str.equals(SpeechCarControlEvent.FRONT_COLLISION_STATE)) {
                    c = 0;
                    break;
                }
                break;
            case -1974077749:
                if (str.equals(SpeechCarControlEvent.LIGHTMEHOME_STATE)) {
                    c = 1;
                    break;
                }
                break;
            case -1952147251:
                if (str.equals(SpeechCarControlEvent.SEAT_POSITION)) {
                    c = 2;
                    break;
                }
                break;
            case -1941037730:
                if (str.equals(SpeechCarControlEvent.BELT_ELECTRIC_STATE)) {
                    c = 3;
                    break;
                }
                break;
            case -1878001850:
                if (str.equals(SpeechCarControlEvent.LAMP_FAR)) {
                    c = 4;
                    break;
                }
                break;
            case -1741151858:
                if (str.equals(SpeechCarControlEvent.LAMP_HEADGROUP)) {
                    c = 5;
                    break;
                }
                break;
            case -1664162452:
                if (str.equals(SpeechCarControlEvent.WARN_BLIND_AREA_STATE)) {
                    c = 6;
                    break;
                }
                break;
            case -1599309710:
                if (str.equals(SpeechCarControlEvent.TRUNK_STATE)) {
                    c = 7;
                    break;
                }
                break;
            case -1375618344:
                if (str.equals(SpeechCarControlEvent.WARN_LANE_DEPARTURE_STATE)) {
                    c = '\b';
                    break;
                }
                break;
            case -1368961668:
                if (str.equals(SpeechCarControlEvent.GET_WIPER_LEVEL)) {
                    c = '\t';
                    break;
                }
                break;
            case -1288966170:
                if (str.equals(SpeechCarControlEvent.GET_TIRE_PRESSURE)) {
                    c = '\n';
                    break;
                }
                break;
            case -1209587430:
                if (str.equals(SpeechCarControlEvent.ICM_VOLUME_WARN)) {
                    c = 11;
                    break;
                }
                break;
            case -1073244956:
                if (str.equals(SpeechCarControlEvent.SEAT_ERROR)) {
                    c = '\f';
                    break;
                }
                break;
            case -1021329318:
                if (str.equals(SpeechCarControlEvent.GET_CRUISE_CONTROL_STATUS)) {
                    c = '\r';
                    break;
                }
                break;
            case -1008347830:
                if (str.equals(SpeechCarControlEvent.LCC_STATE)) {
                    c = 14;
                    break;
                }
                break;
            case -870356283:
                if (str.equals(SpeechCarControlEvent.LAMP_INNER)) {
                    c = 15;
                    break;
                }
                break;
            case -786298349:
                if (str.equals(SpeechCarControlEvent.LOCK_STATE)) {
                    c = 16;
                    break;
                }
                break;
            case -722435495:
                if (str.equals(SpeechCarControlEvent.METER_LIMIT_INTEL)) {
                    c = 17;
                    break;
                }
                break;
            case -555222776:
                if (str.equals(SpeechCarControlEvent.SPEED_CAR)) {
                    c = 18;
                    break;
                }
                break;
            case -501644054:
                if (str.equals(SpeechCarControlEvent.BELT_REARSEAT_STATE)) {
                    c = 19;
                    break;
                }
                break;
            case -372830826:
                if (str.equals(SpeechCarControlEvent.WINDOW_LOCK_STATE)) {
                    c = 20;
                    break;
                }
                break;
            case -346554662:
                if (str.equals(SpeechCarControlEvent.DOOR_STATE)) {
                    c = 21;
                    break;
                }
                break;
            case -258533676:
                if (str.equals(SpeechCarControlEvent.METER_LAST_START)) {
                    c = 22;
                    break;
                }
                break;
            case -139083951:
                if (str.equals(SpeechCarControlEvent.LOCK_DRIVE_AUTIO)) {
                    c = 23;
                    break;
                }
                break;
            case -126307048:
                if (str.equals(SpeechCarControlEvent.WINDOW_STATE)) {
                    c = 24;
                    break;
                }
                break;
            case -122050850:
                if (str.equals(SpeechCarControlEvent.IS_CAR_TRIP)) {
                    c = 25;
                    break;
                }
                break;
            case -86335604:
                if (str.equals(SpeechCarControlEvent.DRIVE_MODE)) {
                    c = JSONLexer.EOI;
                    break;
                }
                break;
            case -65424329:
                if (str.equals(SpeechCarControlEvent.ATWS_STATE)) {
                    c = 27;
                    break;
                }
                break;
            case -20170038:
                if (str.equals(SpeechCarControlEvent.RECYCLE_LV)) {
                    c = 28;
                    break;
                }
                break;
            case 106241666:
                if (str.equals(SpeechCarControlEvent.METER_LAST_CHARGE)) {
                    c = 29;
                    break;
                }
                break;
            case 224275343:
                if (str.equals(SpeechCarControlEvent.WARN_RADAR_SLOW_STATE)) {
                    c = 30;
                    break;
                }
                break;
            case 234544457:
                if (str.equals(SpeechCarControlEvent.LAMP_REARFOG)) {
                    c = 31;
                    break;
                }
                break;
            case 404150481:
                if (str.equals(SpeechCarControlEvent.ICM_STATE)) {
                    c = ' ';
                    break;
                }
                break;
            case 465177042:
                if (str.equals(SpeechCarControlEvent.SPEED_LIMIT_STATE)) {
                    c = '!';
                    break;
                }
                break;
            case 467392178:
                if (str.equals(SpeechCarControlEvent.SPEED_LIMIT_VALUE)) {
                    c = '\"';
                    break;
                }
                break;
            case 473786283:
                if (str.equals(SpeechCarControlEvent.BRAKE_WARN)) {
                    c = '#';
                    break;
                }
                break;
            case 646093110:
                if (str.equals(SpeechCarControlEvent.SEAT_WELCOME_MODE_STATE)) {
                    c = '$';
                    break;
                }
                break;
            case 714771718:
                if (str.equals(SpeechCarControlEvent.IG_STATE)) {
                    c = '%';
                    break;
                }
                break;
            case 717411520:
                if (str.equals(SpeechCarControlEvent.SHIFT_STATE)) {
                    c = '&';
                    break;
                }
                break;
            case 768526207:
                if (str.equals(SpeechCarControlEvent.SEAT_MAIN_HUMAN_STATE)) {
                    c = '\'';
                    break;
                }
                break;
            case 778341606:
                if (str.equals(SpeechCarControlEvent.ROTATION_STEER)) {
                    c = '(';
                    break;
                }
                break;
            case 793162096:
                if (str.equals(SpeechCarControlEvent.ICM_WIND_LV)) {
                    c = ')';
                    break;
                }
                break;
            case 918463002:
                if (str.equals(SpeechCarControlEvent.ICM_DRIVER_TEMP)) {
                    c = '*';
                    break;
                }
                break;
            case 1238236251:
                if (str.equals(SpeechCarControlEvent.SEAT_DIRECTION)) {
                    c = '+';
                    break;
                }
                break;
            case 1294982829:
                if (str.equals(SpeechCarControlEvent.LOCK_UNLOCK_RESPONSE)) {
                    c = ',';
                    break;
                }
                break;
            case 1319619428:
                if (str.equals(SpeechCarControlEvent.OLED_STATE)) {
                    c = '-';
                    break;
                }
                break;
            case 1355038943:
                if (str.equals(SpeechCarControlEvent.WARN_SIDE_REVER_STATE)) {
                    c = '.';
                    break;
                }
                break;
            case 1375556580:
                if (str.equals(SpeechCarControlEvent.GET_STATUS_LOW_SOC)) {
                    c = '/';
                    break;
                }
                break;
            case 1436055782:
                if (str.equals(SpeechCarControlEvent.LAMP_LOCATION)) {
                    c = '0';
                    break;
                }
                break;
            case 1501829463:
                if (str.equals(SpeechCarControlEvent.METER_A)) {
                    c = '1';
                    break;
                }
                break;
            case 1501829464:
                if (str.equals(SpeechCarControlEvent.METER_B)) {
                    c = '2';
                    break;
                }
                break;
            case 1574537530:
                if (str.equals(SpeechCarControlEvent.METER_TOTAL)) {
                    c = '3';
                    break;
                }
                break;
            case 1693782079:
                if (str.equals(SpeechCarControlEvent.ACC_STATE)) {
                    c = '4';
                    break;
                }
                break;
            case 1902828131:
                if (str.equals(SpeechCarControlEvent.LOCK_PARKING_AUTIO)) {
                    c = '5';
                    break;
                }
                break;
            case 1911726553:
                if (str.equals(SpeechCarControlEvent.LAMP_NEAR)) {
                    c = '6';
                    break;
                }
                break;
            case 1955438847:
                if (str.equals(SpeechCarControlEvent.MODE_WELCOME_STATE)) {
                    c = '7';
                    break;
                }
                break;
            case 2019589129:
                if (str.equals(SpeechCarControlEvent.ICM_WIND_MODE)) {
                    c = '8';
                    break;
                }
                break;
            case 2043201887:
                if (str.equals(SpeechCarControlEvent.GET_TIRE_PRESSURE_WARNINGS)) {
                    c = '9';
                    break;
                }
                break;
            case 2063463053:
                if (str.equals(SpeechCarControlEvent.LANE_CHANGE_ASSIST_STATE)) {
                    c = ':';
                    break;
                }
                break;
            case 2133982950:
                if (str.equals(SpeechCarControlEvent.STEER_EPS)) {
                    c = ';';
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return Integer.valueOf(this.mTarget.getFrontCollisionSecurity(str, str2));
            case 1:
                return Integer.valueOf(this.mTarget.getLightMeHome(str, str2));
            case 2:
                return this.mTarget.getChairLocationValue(str, str2);
            case 3:
                return Boolean.valueOf(this.mTarget.getElectricSeatBelt(str, str2));
            case 4:
                return Boolean.valueOf(this.mTarget.getFarLampState(str, str2));
            case 5:
                return Integer.valueOf(this.mTarget.getHeadLampGroup(str, str2));
            case 6:
                return Integer.valueOf(this.mTarget.getBlindAreaDetectionWarning(str, str2));
            case 7:
                return Integer.valueOf(this.mTarget.getTrunk(str, str2));
            case '\b':
                return Integer.valueOf(this.mTarget.getLaneDepartureWarning(str, str2));
            case '\t':
                return Integer.valueOf(this.mTarget.getWiperInterval(str, str2));
            case '\n':
                return this.mTarget.getTirePressureAll(str, str2);
            case 11:
                return Integer.valueOf(this.mTarget.getIcmAlarmVolume(str, str2));
            case '\f':
                return Boolean.valueOf(this.mTarget.getSeatErrorState(str, str2));
            case '\r':
                return Integer.valueOf(this.mTarget.getCruiseControlStatus(str, str2));
            case 14:
                return Integer.valueOf(this.mTarget.getLCCStatus(str, str2));
            case 15:
                return Boolean.valueOf(this.mTarget.getInternalLight(str, str2));
            case 16:
                return Boolean.valueOf(this.mTarget.getDoorLockState(str, str2));
            case 17:
                return Integer.valueOf(this.mTarget.getIntelligentSpeedLimit(str, str2));
            case 18:
                return Double.valueOf(this.mTarget.getCarSpeed(str, str2));
            case 19:
                return Boolean.valueOf(this.mTarget.getRearSeatBeltWarning(str, str2));
            case 20:
                return Integer.valueOf(this.mTarget.getWindowLockStatus(str, str2));
            case 21:
                return this.mTarget.getDoorsState(str, str2);
            case 22:
                return Double.valueOf(this.mTarget.getLastStartUpMileage(str, str2));
            case 23:
                return Boolean.valueOf(this.mTarget.getDriveAutoLock(str, str2));
            case 24:
                return this.mTarget.getWindowsState(str, str2);
            case 25:
                return Boolean.valueOf(this.mTarget.isCarTrip(str, str2));
            case 26:
                return Integer.valueOf(this.mTarget.getDrivingMode(str, str2));
            case 27:
                return Integer.valueOf(this.mTarget.getATWSState(str, str2));
            case 28:
                return Integer.valueOf(this.mTarget.getEnergyRecycleLevel(str, str2));
            case 29:
                return Double.valueOf(this.mTarget.getLastChargeMileage(str, str2));
            case 30:
                return Boolean.valueOf(this.mTarget.getRadarWarningVoiceStatus(str, str2));
            case 31:
                return Boolean.valueOf(this.mTarget.getRearFogLamp(str, str2));
            case ' ':
                return Boolean.valueOf(this.mTarget.getIcmConnectionState(str, str2));
            case '!':
                return Boolean.valueOf(this.mTarget.getSpeedLimitWarningSwitch(str, str2));
            case '\"':
                return Integer.valueOf(this.mTarget.getSpeedLimitWarningValue(str, str2));
            case '#':
                return Boolean.valueOf(this.mTarget.getEmergencyBrakeWarning(str, str2));
            case '$':
                return Boolean.valueOf(this.mTarget.getWelcomeModeBackStatus(str, str2));
            case '%':
                return Integer.valueOf(this.mTarget.getBCMIgStatus(str, str2));
            case '&':
                return Integer.valueOf(this.mTarget.getShiftStatus(str, str2));
            case '\'':
                return Integer.valueOf(this.mTarget.getDriverSeatStatus(str, str2));
            case '(':
                return Double.valueOf(this.mTarget.getSteerWheelRotationAngle(str, str2));
            case ')':
                return Integer.valueOf(this.mTarget.getICMWindLevel(str, str2));
            case '*':
                return Double.valueOf(this.mTarget.getICMDriverTempValue(str, str2));
            case '+':
                return this.mTarget.getChairDirection(str, str2);
            case ',':
                return Integer.valueOf(this.mTarget.getUnlockResponse(str, str2));
            case '-':
                return Integer.valueOf(this.mTarget.getOled(str, str2));
            case '.':
                return Integer.valueOf(this.mTarget.getSideReversingWarning(str, str2));
            case '/':
                return Integer.valueOf(this.mTarget.getLowSocStatus(str, str2));
            case '0':
                return Boolean.valueOf(this.mTarget.getLocationLampState(str, str2));
            case '1':
                return Double.valueOf(this.mTarget.getMeterMileageA(str, str2));
            case '2':
                return Double.valueOf(this.mTarget.getMeterMileageB(str, str2));
            case '3':
                return Double.valueOf(this.mTarget.getDriveTotalMileage(str, str2));
            case '4':
                return Integer.valueOf(this.mTarget.getACCStatus(str, str2));
            case '5':
                return Boolean.valueOf(this.mTarget.getParkingAutoUnlock(str, str2));
            case '6':
                return Boolean.valueOf(this.mTarget.getNearLampState(str, str2));
            case '7':
                return Boolean.valueOf(this.mTarget.getChairWelcomeMode(str, str2));
            case '8':
                return Integer.valueOf(this.mTarget.getICMWindBlowMode(str, str2));
            case '9':
                return this.mTarget.getAllTirePressureWarnings(str, str2);
            case ':':
                return Integer.valueOf(this.mTarget.getLaneChangeAssist(str, str2));
            case ';':
                return Integer.valueOf(this.mTarget.getSteeringWheelEPS(str, str2));
            default:
                return null;
        }
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public String[] getQueryEvents() {
        return new String[]{SpeechCarControlEvent.LAMP_REARFOG, SpeechCarControlEvent.LAMP_NEAR, SpeechCarControlEvent.LAMP_LOCATION, SpeechCarControlEvent.LAMP_FAR, SpeechCarControlEvent.LAMP_HEADGROUP, SpeechCarControlEvent.LAMP_INNER, SpeechCarControlEvent.BRAKE_WARN, SpeechCarControlEvent.ATWS_STATE, SpeechCarControlEvent.OLED_STATE, SpeechCarControlEvent.LIGHTMEHOME_STATE, SpeechCarControlEvent.LOCK_DRIVE_AUTIO, SpeechCarControlEvent.LOCK_PARKING_AUTIO, SpeechCarControlEvent.LOCK_STATE, SpeechCarControlEvent.TRUNK_STATE, SpeechCarControlEvent.MODE_WELCOME_STATE, SpeechCarControlEvent.BELT_ELECTRIC_STATE, SpeechCarControlEvent.BELT_REARSEAT_STATE, SpeechCarControlEvent.LOCK_UNLOCK_RESPONSE, SpeechCarControlEvent.DOOR_STATE, SpeechCarControlEvent.WINDOW_STATE, SpeechCarControlEvent.SEAT_DIRECTION, SpeechCarControlEvent.SEAT_ERROR, SpeechCarControlEvent.SEAT_POSITION, SpeechCarControlEvent.SEAT_WELCOME_MODE_STATE, SpeechCarControlEvent.DRIVE_MODE, SpeechCarControlEvent.STEER_EPS, SpeechCarControlEvent.ICM_VOLUME_WARN, SpeechCarControlEvent.SPEED_LIMIT_STATE, SpeechCarControlEvent.SPEED_LIMIT_VALUE, SpeechCarControlEvent.METER_A, SpeechCarControlEvent.METER_B, SpeechCarControlEvent.METER_TOTAL, SpeechCarControlEvent.METER_LAST_CHARGE, SpeechCarControlEvent.METER_LAST_START, SpeechCarControlEvent.FRONT_COLLISION_STATE, SpeechCarControlEvent.METER_LIMIT_INTEL, SpeechCarControlEvent.LANE_CHANGE_ASSIST_STATE, SpeechCarControlEvent.WARN_SIDE_REVER_STATE, SpeechCarControlEvent.WARN_LANE_DEPARTURE_STATE, SpeechCarControlEvent.WARN_BLIND_AREA_STATE, SpeechCarControlEvent.WARN_RADAR_SLOW_STATE, SpeechCarControlEvent.RECYCLE_LV, SpeechCarControlEvent.SHIFT_STATE, SpeechCarControlEvent.SEAT_MAIN_HUMAN_STATE, SpeechCarControlEvent.ROTATION_STEER, SpeechCarControlEvent.SPEED_CAR, SpeechCarControlEvent.ICM_STATE, SpeechCarControlEvent.IG_STATE, SpeechCarControlEvent.ICM_WIND_MODE, SpeechCarControlEvent.ICM_DRIVER_TEMP, SpeechCarControlEvent.ICM_WIND_LV, SpeechCarControlEvent.IS_CAR_TRIP, SpeechCarControlEvent.GET_WIPER_LEVEL, SpeechCarControlEvent.GET_TIRE_PRESSURE, SpeechCarControlEvent.GET_TIRE_PRESSURE_WARNINGS, SpeechCarControlEvent.ACC_STATE, SpeechCarControlEvent.LCC_STATE, SpeechCarControlEvent.WINDOW_LOCK_STATE, SpeechCarControlEvent.GET_STATUS_LOW_SOC, SpeechCarControlEvent.GET_CRUISE_CONTROL_STATUS};
    }
}
