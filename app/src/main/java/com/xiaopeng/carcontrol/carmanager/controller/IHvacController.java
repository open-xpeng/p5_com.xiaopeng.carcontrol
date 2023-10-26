package com.xiaopeng.carcontrol.carmanager.controller;

import com.xiaopeng.carcontrol.carmanager.IBaseCallback;
import com.xiaopeng.carcontrol.carmanager.IBaseCarController;

/* loaded from: classes.dex */
public interface IHvacController extends IBaseCarController<Callback> {
    public static final int HVAC_AC_ON = 1;
    public static final int HVAC_AIR_QUALITY_LEVEL_MAX_PERCENT = 10;
    public static final int HVAC_AIR_QUALITY_LEVEL_POLLUTION_PERCENT = 3;
    public static final int HVAC_AQS_LEVEL_HIGH = 3;
    public static final int HVAC_AQS_LEVEL_LOW = 1;
    public static final int HVAC_AQS_LEVEL_MIDDLE = 2;
    public static final int HVAC_AUTO_AC_HEAT_NATURE_ON = 4;
    public static final int HVAC_BLOW_WIND_LEVEL_AUTO = 14;
    public static final int HVAC_BLOW_WIND_LEVEL_MIN = 1;
    public static final int HVAC_BLOW_WIND_MODE_FACE = 2;
    public static final int HVAC_BLOW_WIND_MODE_FOOT = 3;
    public static final int HVAC_BLOW_WIND_MODE_WINDSHIELD = 1;
    public static final int HVAC_CIRCULATION_10M = 1;
    public static final int HVAC_CIRCULATION_15M = 2;
    public static final int HVAC_CIRCULATION_20M = 3;
    public static final int HVAC_CIRCULATION_OFF = 6;
    public static final int HVAC_CIRCULATION_STATUS_AUTO = 6;
    public static final int HVAC_CIRCULATION_STATUS_INNER = 1;
    public static final int HVAC_CIRCULATION_STATUS_OUTSIDE = 2;
    public static final int HVAC_EAV_WIND_MODE_FREE = 3;
    public static final int HVAC_EAV_WIND_MODE_MIRROR = 2;
    public static final int HVAC_EAV_WIND_MODE_SINGLE = 1;
    public static final int HVAC_GET_WIND_MODE_AUTO_FRONT_DEFROST = 7;
    public static final int HVAC_GET_WIND_MODE_AUTO_MODE = 14;
    public static final int HVAC_GET_WIND_MODE_FACE = 1;
    public static final int HVAC_GET_WIND_MODE_FACE_FOOT = 2;
    public static final int HVAC_GET_WIND_MODE_FACE_FOOT_WINDSHIELD = 9;
    public static final int HVAC_GET_WIND_MODE_FACE_WINDSHIELD = 8;
    public static final int HVAC_GET_WIND_MODE_FOOT = 3;
    public static final int HVAC_GET_WIND_MODE_FOOT_WINDSHIELD = 4;
    public static final int HVAC_GET_WIND_MODE_FRONT_DEFROST = 5;
    public static final int HVAC_GET_WIND_MODE_WINDSHIELD = 6;
    public static final int HVAC_HEAT_ON = 2;
    public static final int HVAC_LEFT_SYNC_ON = 1;
    public static final int HVAC_NATURE_ON = 3;
    public static final int HVAC_OUTSIDE_PM25_ERROR_VALUE = -1;
    public static final String HVAC_OUTSIDE_PM25_KEY = "pm25";
    public static final int HVAC_RIGHT_SYNC_ON = 2;
    public static final int HVAC_SET_BLOW_WIND_MODE_FACE = 1;
    public static final int HVAC_SET_BLOW_WIND_MODE_FACE_FOOT = 2;
    public static final int HVAC_SET_BLOW_WIND_MODE_FACE_FOOT_WINDSHIELD = 7;
    public static final int HVAC_SET_BLOW_WIND_MODE_FACE_WINDSHIELD = 5;
    public static final int HVAC_SET_BLOW_WIND_MODE_FOOT = 3;
    public static final int HVAC_SET_BLOW_WIND_MODE_FOOT_WINDSHIELD = 4;
    public static final int HVAC_SET_BLOW_WIND_MODE_WINDSHIELD = 6;
    public static final int HVAC_STATUS_AC_OFF = 0;
    public static final int HVAC_STATUS_AC_ON = 1;
    public static final int HVAC_STATUS_AUTO_WIND_OFF = 6;
    public static final int HVAC_STATUS_AUTO_WIND_ON = 7;
    public static final int HVAC_STATUS_ERROR = 2;
    public static final int HVAC_STATUS_HEAT_OFF = 2;
    public static final int HVAC_STATUS_HEAT_ON = 3;
    public static final int HVAC_STATUS_NATURE_OFF = 4;
    public static final int HVAC_STATUS_NATURE_ON = 5;
    public static final int HVAC_STATUS_OFF = 0;
    public static final int HVAC_STATUS_ON = 1;
    public static final int HVAC_SYNC_MODE_OFF = 3;
    public static final float HVAC_TEMP_MAX = 32.0f;
    public static final float HVAC_TEMP_MIN = 18.0f;
    public static final float HVAC_TEMP_STEP = 0.5f;
    public static final int HVAC_VENT_ERROR_STATUS = 15;
    public static final int HVAC_VENT_OFF_STATUS = 14;
    public static final int HVAC_VENT_ON_ACTION = 13;
    public static final int HVAC_VENT_POSITION_DRV_LEFT = 0;
    public static final int HVAC_VENT_POSITION_DRV_RIGHT = 1;
    public static final int HVAC_VENT_POSITION_PSN_LEFT = 2;
    public static final int HVAC_VENT_POSITION_PSN_RIGHT = 3;
    public static final int HVAC_WIND_MODE_COLOUR_COLD = 2;
    public static final int HVAC_WIND_MODE_COLOUR_HOT = 3;
    public static final int HVAC_WIND_MODE_COLOUR_NATURE = 1;

    /* loaded from: classes.dex */
    public interface Callback extends IBaseCallback {
        void onHvacAcHeatNatureChanged(int mode);

        default void onHvacAirDistributionAutoChanged(boolean enable) {
        }

        default void onHvacAirIntakeAutoChanged(boolean enable) {
        }

        void onHvacAirProtectModeChanged(int mode);

        default void onHvacAirQualityLevel(int level) {
        }

        default void onHvacAirQualityOutside(boolean isPolluted) {
        }

        default void onHvacAqsLevelChange(int level) {
        }

        void onHvacAqsModeChange(int status);

        default void onHvacAutoDefogSwitchChanged(boolean enable) {
        }

        default void onHvacAutoDefogWorkStChanged(boolean enable) {
        }

        void onHvacAutoModeChanged(boolean enabled);

        void onHvacCirculationModeChanged(int mode);

        default void onHvacCirculationTimeChanged(int time) {
        }

        void onHvacDeodorantChanged(boolean enable);

        default void onHvacDeodorantTimerChanged(int time) {
        }

        void onHvacDrvSyncModeChanged(boolean enabled);

        default void onHvacEavDrvLeftHPosChanged(int pos) {
        }

        default void onHvacEavDrvLeftVPosChanged(int pos) {
        }

        default void onHvacEavDrvRightHPosChanged(int pos) {
        }

        default void onHvacEavDrvRightVPosChanged(int pos) {
        }

        default void onHvacEavDrvWindModeChange(int mode) {
        }

        default void onHvacEavPsnLeftHPosChanged(int pos) {
        }

        default void onHvacEavPsnLeftVPosChanged(int pos) {
        }

        default void onHvacEavPsnRightHPosChanged(int pos) {
        }

        default void onHvacEavPsnRightVPosChanged(int pos) {
        }

        default void onHvacEavPsnWindModeChange(int mode) {
        }

        default void onHvacEavSweepModeChanged(int status) {
        }

        void onHvacEconModeChange(int status);

        void onHvacExternalTempChanged(float temp);

        void onHvacFanSpeedAutoChanged(boolean enable);

        void onHvacFrontDefrostChanged(boolean enabled);

        void onHvacInnerPM25Changed(int value);

        default void onHvacInnerTempChanged(float temp) {
        }

        default void onHvacIonizerModeChanged(int status) {
        }

        void onHvacNIVentChanged(boolean enabled);

        void onHvacNewFreshSwitchChanged(boolean enabled);

        default void onHvacOutsidePm25Changed(int value) {
        }

        void onHvacPowerModeChanged(boolean enabled);

        void onHvacPsnSyncModeChanged(boolean enabled);

        void onHvacQualityPurgeChanged(boolean enable);

        void onHvacRapidCoolingChanged(boolean enable);

        default void onHvacRapidCoolingTimerChanged(int time) {
        }

        default void onHvacRapidHeatChanged(boolean enable) {
        }

        default void onHvacRapidHeatTimerChanged(int time) {
        }

        void onHvacRearAutoModeChanged(boolean enabled);

        void onHvacRearPowerModeChanged(boolean enabled);

        void onHvacRearTempDrvChanged(float temp);

        void onHvacRearTempPsnChanged(float temp);

        void onHvacRearWindBlowModeChanged(int mode);

        void onHvacRearWindSpeedLevelChanged(int level);

        default void onHvacSelfDryChanged(boolean enabled) {
        }

        default void onHvacSfsSwitchChanged(boolean enable) {
        }

        default void onHvacSingleModeActivated(boolean enable) {
        }

        void onHvacSingleModeChanged(boolean enable);

        default void onHvacSmartSwChanged(boolean enabled) {
        }

        void onHvacTempDrvChanged(float temp);

        void onHvacTempPsnChanged(float temp);

        void onHvacThirdRowTempChanged(float temp);

        void onHvacThirdRowWindBlowModeChanged(int mode);

        void onHvacWindBlowModeChanged(int mode);

        void onHvacWindModEconLourChanged(int value);

        void onHvacWindSpeedLevelChanged(int level);
    }

    int getAcHeatNatureMode();

    int getAirAutoProtectMode();

    String getAirAutoProtectSound();

    int getHvacAcpConsumption();

    int getHvacAqsLevel();

    int getHvacAqsMode();

    int getHvacCirculationMode();

    int getHvacCirculationTime();

    int getHvacDeodorantCountDownTimer();

    boolean getHvacDriverSyncMode();

    int getHvacEavDrvLeftHPos();

    int getHvacEavDrvLeftVPos();

    int getHvacEavDrvRightHPos();

    int getHvacEavDrvRightVPos();

    int getHvacEavDrvWindMode();

    int getHvacEavPsnLeftHPos();

    int getHvacEavPsnLeftVPos();

    int getHvacEavPsnRightHPos();

    int getHvacEavPsnRightVPos();

    int getHvacEavPsnWindMode();

    int getHvacEavSweepMode();

    int getHvacEconMode();

    float getHvacExternalTemp();

    int getHvacInnerPm25();

    float getHvacInnerTemp();

    int getHvacIonizerMode();

    boolean getHvacPsnSyncMode();

    int getHvacPtcConsumption();

    int getHvacRapidCoolingCountDownTimer();

    int getHvacRapidHeatCountDownTimer();

    float getHvacRearTempDriver();

    float getHvacRearTempPsn();

    int getHvacRearWindBlowMode();

    int getHvacRearWindSpeedLevel();

    float getHvacTempDriver();

    float getHvacTempPsn();

    float getHvacThirdRowTempDriver();

    int getHvacThirdRowWindBlowMode();

    int getHvacWindBlowMode();

    int getHvacWindModEconLour();

    int getHvacWindSpeedLevel();

    int getOutsidePm25();

    int getWindMaxLevel();

    boolean isAutoDefogSwitch();

    boolean isAutoDefogWorkSt();

    boolean isHvacAcModeOn();

    boolean isHvacAirDistributionAutoEnable();

    boolean isHvacAirIntakeAutoEnable();

    boolean isHvacAutoModeOn();

    boolean isHvacDeodorantEnable();

    boolean isHvacFanSpeedAutoEnable();

    boolean isHvacFrontDefrostOn();

    boolean isHvacNIVentOn();

    boolean isHvacNewFreshSwitchOn();

    boolean isHvacPowerModeOn();

    boolean isHvacQualityPurgeEnable();

    boolean isHvacRapidCoolingEnable();

    boolean isHvacRapidHeatEnable();

    boolean isHvacRearAutoModeOn();

    boolean isHvacRearPowerModeOn();

    boolean isHvacSelfDryOn();

    boolean isHvacSingleModeActive();

    boolean isHvacSingleModeEnable();

    boolean isSmartHvacEnabled();

    void setAcHeatNatureMode(int mode);

    void setAirAutoProtectSound(String soundType);

    void setAirAutoProtectedMode(int mode);

    void setAutoDefogSwitch(boolean enable);

    void setHvacAcMode(boolean enable);

    void setHvacAqsLevel(int level);

    void setHvacAqsMode(int status);

    void setHvacAutoMode(boolean enable);

    void setHvacCirculationMode(int mode);

    void setHvacCirculationTime(int time);

    void setHvacDeodorantCountDownTimer(int time);

    void setHvacDeodorantEnable(boolean enable);

    void setHvacDriverSyncMode(boolean enable);

    void setHvacEavDrvLeftHPos(int pos);

    void setHvacEavDrvLeftVPos(int pos);

    void setHvacEavDrvRightHPos(int pos);

    void setHvacEavDrvRightVPos(int pos);

    void setHvacEavDrvWindMode(int mode);

    void setHvacEavPsnLeftHPos(int pos);

    void setHvacEavPsnLeftVPos(int pos);

    void setHvacEavPsnRightHPos(int pos);

    void setHvacEavPsnRightVPos(int pos);

    void setHvacEavPsnWindMode(int mode);

    void setHvacEavSweepMode(int status);

    void setHvacEconMode(int status);

    void setHvacFrontDefrost(boolean enable);

    void setHvacNIVent(boolean enable);

    void setHvacNewFreshSwitchStatus(boolean enable);

    void setHvacPowerMode(boolean enable);

    void setHvacPsnSyncMode(boolean enable);

    void setHvacQualityPurgeMode(boolean enable);

    void setHvacRapidCoolingCountDownTimer(int time);

    void setHvacRapidCoolingEnable(boolean enable);

    void setHvacRapidHeatCountDownTimer(int time);

    void setHvacRapidHeatEnable(boolean enable);

    void setHvacRearAutoMode(boolean enable);

    void setHvacRearPowerMode(boolean enable);

    void setHvacRearTempDriver(float temperature);

    void setHvacRearTempDriverStep(boolean isUp);

    void setHvacRearTempPsn(float temperature);

    void setHvacRearTempPsnStep(boolean isUp);

    void setHvacRearVoiceWindBlowMode(int mode);

    void setHvacRearWindBlowMode(int mode);

    void setHvacRearWindSpeedLevel(int level);

    void setHvacRearWindSpeedStep(boolean isUp);

    void setHvacSelfDryEnable(boolean enable);

    void setHvacSingleMode(boolean enable);

    void setHvacSingleModeActive(boolean enable);

    void setHvacTempDriver(float temperature);

    void setHvacTempDriverStep(boolean isUp);

    void setHvacTempPsn(float temperature);

    void setHvacTempPsnStep(boolean isUp);

    void setHvacThirdRowTempDriver(float temperature);

    void setHvacThirdRowTempDriverStep(boolean isUp);

    void setHvacThirdRowTempStep(boolean isUp);

    void setHvacThirdRowWindBlowMode(int mode);

    void setHvacWindBlowMode(int mode);

    void setHvacWindBlowModeGroup(int mode);

    void setHvacWindSpeedLevel(int level);

    void setHvacWindSpeedStep(boolean isUp);

    void setSmartHvacEnable(boolean enable);

    void updateWeatherFromServer();
}
