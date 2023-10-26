package com.xiaopeng.xvs.xid.sync.api;

/* loaded from: classes2.dex */
public interface ICarControlSync {
    public static final String ADAPTIVE_MODE = "ADAPTIVE_MODE";
    public static final String ANTISICK_MODE = "ANTISICK_MODE";
    public static final String AS_WELCOME_MODE = "AS_WELCOME_MODE";
    public static final String ATL_BRIGHT = "ATL_BRIGHT";
    public static final String ATL_DUAL_COLOR = "ATL_DUAL_COLOR";
    public static final String ATL_DUAL_COLOR_SW = "ATL_DUAL_COLOR_SW";
    public static final String ATL_EFFECT = "ATL_EFFECT";
    public static final String ATL_SINGLE_COLOR = "ATL_SINGLE_COLOR";
    public static final String ATL_SW = "ATL_SW";
    public static final String AUTO_DHC = "AUTO_DHC";
    public static final String AUTO_LAMP_HEIGHT = "AUTO_LAMP_HEIGHT";
    public static final String AUTO_PARKING_NEW = "AUTO_PARKING_NEW";
    public static final String AVAS_BOOT_EFFECT = "AVAS_BOOT_EFFECT";
    public static final String AVAS_BOOT_EFFECT_BEFORE_SW = "AVAS_BOOT_EFFECT_BEFORE_SW";
    public static final String AVAS_EFFECT = "AVAS_EFFECT";
    public static final String AVAS_EFFECT_NEW = "AVAS_EFFECT_NEW";
    public static final String AVAS_SAYHI_EFFECT = "AVAS_SAYHI_EFFECT";
    public static final String BLIND_DETECTION_WARNING = "BLIND_DETECTION_WARNING";
    public static final String BRAKE_WARNING = "BRAKE_WARNING";
    public static final String CDC_MODE = "CDC_MODE";
    public static final String CHAIR_POSITION = "CHAIR_POSITION";
    public static final String CHAIR_POSITION_NEW = "CHAIR_POSITION_NEW";
    public static final String CHAIR_POSITION_PSN = "CHAIR_POSITION_PSN";
    public static final String CHAIR_POSITION_THREE = "CHAIR_POSITION_THREE";
    public static final String CHAIR_POSITION_TWO = "CHAIR_POSITION_TWO";
    public static final String CHAIR_POS_INDEX = "CHAIR_POS_INDEX";
    public static final String CHASSIS_AVH = "CHASSIS_AVH";
    public static final String CHILD_LEFT_LOCK = "CHILD_LEFT_LOCK";
    public static final String CHILD_RIGHT_LOCK = "CHILD_RIGHT_LOCK";
    public static final String CITY_NGP_SW = "CITY_NGP_SW";
    public static final String CMS_AUTO_BRIGHTNESS_SW = "CMS_AUTO_BRIGHTNESS_SW";
    public static final String CMS_BRIGHTNESS = "CMS_BRIGHTNESS";
    public static final String CMS_HIGH_SPD_SW = "CMS_HIGH_SPD_SW";
    public static final String CMS_LOW_SPD_SW = "CMS_LOW_SPD_SW";
    public static final String CMS_OBJECT_RECOGNIZE_SW = "CMS_OBJECT_RECOGNIZE_SW";
    public static final String CMS_POS = "CMS_POS";
    public static final String CMS_REVERSE_SW = "CMS_REVERSE_SW";
    public static final String CMS_TURN_EXTN_SW = "CMS_TURN_EXTN_SW";
    public static final String CMS_VIEW_ANGLE = "CMS_VIEW_ANGLE";
    public static final String CMS_VIEW_RECOVERY_SW = "CMS_VIEW_RECOVERY_SW";
    public static final String CROSS_BARRIERS = "CROSS_BARRIERS";
    public static final String CWC_SW = "CWC_SW";
    public static final String DOME_LIGHT_BRIGHT = "DOME_LIGHT_BRIGHT";
    public static final String DOOR_BOSS_KEY = "DOOR_BOSS_KEY";
    public static final String DOOR_BOSS_KEY_SW = "DOOR_BOSS_KEY_SW";
    public static final String DRIVE_AUTO_LOCK = "DRIVE_AUTO_LOCK";
    public static final String DRIVE_MODE = "DRIVE_MODE";
    public static final String DRV_SEAT_ESB = "DRV_SEAT_ESB";
    public static final String HIGH_SPD_CLOSE_WIN = "HIGH_SPD_CLOSE_WIN";
    public static final String KEY_IMS_STREAMING_MEDIA = "KEY_IMS_STREAMING_MEDIA";
    public static final String KEY_RECYCLE_GRADE = "KEY_RECYCLE_GRADE";
    public static final String KEY_XPILOT_NRA = "KEY_XPILOT_NRA";
    public static final String LAMP_HEIGHT_LEVEL = "LAMP_HEIGHT_LEVEL";
    public static final String LANE_DEPARTURE_WARNING = "LANE_DEPARTURE_WARNING";
    public static final String LEFT_DOOR_HOT_KEY = "LEFT_DOOR_HOT_KEY";
    public static final String LIGHT_ME_HOME = "LIGHT_ME_HOME";
    public static final String LIGHT_ME_HOME_TIME = "LIGHT_ME_HOME_TIME";
    public static final String LLU_CHARGE_SW = "LLU_CHARGE_SW";
    public static final String LLU_LOCK_SW = "LLU_LOCK_SW";
    public static final String LLU_SW = "LLU_SW";
    public static final String LLU_UNLOCK_SW = "LLU_UNLOCK_SW";
    public static final String LOCK_CLOSE_WIN = "LOCK_CLOSE_WIN";
    public static final String MEM_PARK_SW = "MEM_PARK_SW";
    public static final String METER_DEFINE_MEDIA_SOURCE = "METER_DEFINE_MEDIA_SOURCE";
    public static final String METER_DEFINE_SCREEN_LIGHT = "METER_DEFINE_SCREEN_LIGHT";
    public static final String METER_DEFINE_TEMPERATURE = "METER_DEFINE_TEMPERATURE";
    public static final String METER_DEFINE_WIND_MODE = "METER_DEFINE_WIND_MODE";
    public static final String METER_DEFINE_WIND_POWER = "METER_DEFINE_WIND_POWER";
    public static final String MICROPHONE_MUTE = "MICROPHONE_MUTE";
    public static final String MIRROR_AUTO_DOWN = "MIRROR_AUTO_DOWN";
    public static final String MIRROR_POS = "MIRROR_POS";
    public static final String MIRROR_REVERSE = "MIRROR_REVERSE";
    public static final String NGP_LC_MODE = "NGP_LC_MODE";
    public static final String NGP_REMIND_MODE = "NGP_REMIND_MODE";
    public static final String NGP_SAFE_EXAM = "NGP_SAFE_EXAM";
    public static final String NGP_SW = "NGP_SW";
    public static final String NGP_TIP_WIN = "NGP_TIP_WIN";
    public static final String N_GEAR_PROTECT = "N_GEAR_PROTECT";
    public static final String PARK_LAMP_B = "PARK_LAMP_B";
    public static final String REAR_SEAT_WARNING = "REAR_SEAT_WARNING";
    public static final String REAR_SEAT_WELCOME_MODE = "REAR_SEAT_WELCOME_MODE";
    public static final String RIGHT_DOOR_HOT_KEY = "RIGHT_DOOR_HOT_KEY";
    public static final String SDC_BREAK_CLOSE = "SDC_BREAK_CLOSE";
    public static final String SENSOR_TRUNK_SW = "SENSOR_TRUNK_SW";
    public static final String SIDE_REVERSING_WARNING = "SIDE_REVERSING_WARNING";
    public static final String SINGLE_PEDAL = "SINGLEPEDAL";
    public static final String SMART_SPEED_LIMIT = "SMART_SPEED_LIMIT";
    public static final String SPEED_LIMIT = "SPEED_LIMIT";
    public static final String SPEED_LIMIT_VALUE = "SPEED_LIMIT_VALUE";
    public static final String STEER_MODE = "STEER_MODE";
    public static final String STEER_POS = "STEER_POS";
    public static final String STOP_AUTO_UNLOCK = "STOP_AUTO_UNLOCK";
    public static final String TRAFFIC_LIGHT = "TRAFFIC_LIGHT";
    public static final String TRUNK_OPEN_POS = "TRUNK_OPEN_POS";
    public static final String UNLOCK_RESPONSE = "UNLOCK_RESPONSE";
    public static final String WELCOME_MODE = "WELCOME_MODE";
    public static final String WHEEL_KEY_PROTECT = "WHEEL_KEY_PROTECT";
    public static final String WHEEL_X_KEY = "WHEEL_X_KEY";
    public static final String WIPER_SENSITIVITY = "WIPER_SENSITIVITY";
    public static final String XPEDAL_MODE = "XPEDAL_MODE";
    public static final String XPILOT_ALC = "XPILOT_ALC";
    public static final String XPILOT_AUTO_PARK_SW = "XPILOT_AUTO_PARK_SW";
    public static final String XPILOT_LCC = "XPILOT_LCC";
    public static final String XPILOT_LSS_STATE = "XPILOT_AUTO_PARK_SW";

    String getATLDualColor(String str);

    String getATLDualColorSW(String str);

    String getATLEffect(String str);

    String getATLLight(String str);

    String getATLSW(String str);

    String getATLSingleColor(String str);

    String getAdaptiveMode(String str);

    String getAntisickMode(String str);

    String getAsWelcomeMode(String str);

    String getAutoDHC(String str);

    String getAutoHoldSw(String str);

    String getAutoLampHeight(String str);

    String getAutoParkSound(String str);

    String getAutoParkSw(String str);

    String getAvasBootEffect(String str);

    String getAvasBootEffectBefore(String str);

    String getAvasEffect(String str);

    String getAvasEffectNew(String str);

    String getAvasSayHiEffect(String str);

    String getBsdSw(String str);

    String getCdcMode(String str);

    String getChairPosIndex(String str);

    String getChairPosNew(String str);

    String getChairPosPsn(String str);

    String getChairPositionOne(String str);

    String getChairPositionThree(String str);

    String getChairPositionTwo(String str);

    String getChargeSW(String str);

    String getChildLeftLock(String str);

    String getChildRightLock(String str);

    String getCityNgpSw(String str);

    String getCmsAutoBrightnessSw(String str);

    String getCmsBrightness(String str);

    String getCmsHighSpdSw(String str);

    String getCmsLowSpdSw(String str);

    String getCmsObjectRecognizeSw(String str);

    String getCmsPos(String str);

    String getCmsReverseSw(String str);

    String getCmsTurnExtnSw(String str);

    String getCmsViewAngle(String str);

    String getCmsViewRecoverySw(String str);

    String getCrossBarriers(String str);

    String getCwcSw(String str);

    String getDomeLightBright(String str);

    String getDoorBossKey(String str);

    String getDoorBossKeySW(String str);

    String getDriveAutoLock(String str);

    String getDriveMode(String str);

    String getDrvSeatESB(String str);

    String getEbwSw(String str);

    String getHighSPDCloseWin(String str);

    String getIsLcSw(String str);

    String getKeyImsStreamingMedia(String str);

    String getKeyXPilotNra(String str);

    String getLampHeightLevel(String str);

    String getLdwSw(String str);

    String getLeftDoorHotKey(String str);

    String getLightMeHome(String str);

    String getLightMeHomeTime(String str);

    String getLluSW(String str);

    String getLockCloseWin(String str);

    String getLockSW(String str);

    String getLssState(String str);

    String getMemParkSw(String str);

    String getMeterMediaSource(String str);

    String getMeterScreenLight(String str);

    String getMeterTemperature(String str);

    String getMeterWindMode(String str);

    String getMeterWindPower(String str);

    String getMicrophoneMute(String str);

    String getMirrorAutoDownSw(String str);

    String getMirrorPos(String str);

    String getMirrorReverse(String str);

    String getNGearProtectSw(String str);

    String getNgpLcMode(String str);

    String getNgpRemindMode(String str);

    String getNgpSafeExam(String str);

    String getNgpSw(String str);

    String getNgpTipWin(String str);

    String getParkLampB(String str);

    String getRctaSw(String str);

    String getRearSeatWarning(String str);

    String getRearSeatWelcomeMode(String str);

    String getRecycleGrade(String str);

    String getRightDoorHotKey(String str);

    String getSdcBreakCloseType(String str);

    String getSensorTrunkSw(String str);

    String getSinglePedal(String str);

    String getSpeedLimitSw(String str);

    String getSpeedLimitValue(String str);

    String getSteerMode(String str);

    String getSteerPos(String str);

    String getStopAutoUnLock(String str);

    String getTrafficLight(String str);

    String getTrunkOpenPos(String str);

    String getUnLockResponse(String str);

    String getUnLockSW(String str);

    String getWelcomeMode(String str);

    String getWheelKeyProtectSw(String str);

    String getWheelXKey(String str);

    String getWiperSensitivity(String str);

    String getXPilotAlc(String str);

    String getXPilotLcc(String str);

    String getXpedalMode(String str);

    void putAntisickMode(String str);

    void putAutoHoldSw(String str);

    void putAutoLampHeight(String str);

    void putAutoParkSound(String str);

    void putAutoParkSw(String str);

    void putBsdSw(String str);

    void putDriveAutoLock(String str);

    void putDriveMode(String str);

    void putEbwSw(String str);

    void putIsLcSw(String str);

    void putLampHeightLevel(String str);

    void putLdwSw(String str);

    void putLeftDoorHotKey(String str);

    void putLssState(String str);

    void putMemParkSw(String str);

    void putMeterMediaSource(String str);

    void putMeterScreenLight(String str);

    void putMeterTemperature(String str);

    void putMeterWindMode(String str);

    void putMeterWindPower(String str);

    void putMicrophoneMute(String str);

    void putMirrorAutoDownSw(String str);

    void putNGearProtectSw(String str);

    void putNgpLcMode(String str);

    void putNgpRemindMode(String str);

    void putNgpSafeExam(String str);

    void putNgpSw(String str);

    void putNgpTipWin(String str);

    void putRctaSw(String str);

    void putRearSeatWarning(String str);

    void putRecycleGrade(String str);

    void putRightDoorHotKey(String str);

    void putSpeedLimitSw(String str);

    void putSpeedLimitValue(String str);

    void putSteerMode(String str);

    void putSteerPos(String str);

    void putWheelKeyProtectSw(String str);

    void putXPilotAlc(String str);

    void putXPilotLcc(String str);

    void putXpedalMode(String str);

    void setATLDualColor(String str);

    void setATLDualColorSW(String str);

    void setATLEffect(String str);

    void setATLLight(String str);

    void setATLSW(String str);

    void setATLSingleColor(String str);

    void setAdaptiveMode(String str);

    void setAsWelcomeMode(String str);

    void setAutoDHC(String str);

    void setAvasBootEffect(String str);

    void setAvasBootEffectBefore(String str);

    void setAvasEffect(String str);

    void setAvasEffectNew(String str);

    void setAvasSayHiEffect(String str);

    void setCdcMode(String str);

    void setChairPosIndex(String str);

    void setChairPosNew(int i, String str);

    void setChairPosNew(String str);

    void setChairPosPsn(String str);

    void setChairPositionOne(String str);

    void setChairPositionThree(String str);

    void setChairPositionTwo(String str);

    void setChargeSW(String str);

    void setChildLeftLock(String str);

    void setChildRightLock(String str);

    void setCityNgpSw(String str);

    void setCmsAutoBrightnessSw(String str);

    void setCmsBrightness(String str);

    void setCmsHighSpdSw(String str);

    void setCmsLowSpdSw(String str);

    void setCmsObjectRecognizeSw(String str);

    void setCmsPos(String str);

    void setCmsReverseSw(String str);

    void setCmsTurnExtnSw(String str);

    void setCmsViewAngle(String str);

    void setCmsViewRecoverySw(String str);

    void setCrossBarriers(String str);

    void setCwcSw(String str);

    void setDomeLightBright(String str);

    void setDoorBossKey(String str);

    void setDoorBossKeySW(String str);

    void setDrvSeatESB(String str);

    void setHighSPDCloseWin(String str);

    void setKeyImsStreamingMedia(String str);

    void setKeyXPilotNra(String str);

    void setLightMeHome(String str);

    void setLightMeHomeTime(String str);

    void setLluSW(String str);

    void setLockCloseWin(String str);

    void setLockSW(String str);

    void setMirrorPos(String str);

    void setMirrorReverse(String str);

    void setParkLampB(String str);

    void setRearSeatWelcomeMode(String str);

    void setSdcBreakCloseType(String str);

    void setSensorTrunkSw(String str);

    void setSinglePedal(String str);

    void setStopAutoUnLock(String str);

    void setTrafficLight(String str);

    void setTrunkOpenPos(String str);

    void setUnLockResponse(String str);

    void setUnLockSW(String str);

    void setWelcomeMode(String str);

    void setWheelXKey(String str);

    void setWiperSensitivity(String str);
}
