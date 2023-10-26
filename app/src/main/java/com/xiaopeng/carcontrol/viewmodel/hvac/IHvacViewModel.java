package com.xiaopeng.carcontrol.viewmodel.hvac;

import com.xiaopeng.carcontrol.viewmodel.IBaseViewModel;

/* loaded from: classes2.dex */
public interface IHvacViewModel extends IBaseViewModel {
    public static final int AC_BLOW_WIND_LEVEL_MIN = 1;
    public static final float AC_TEMP_MAX = 32.0f;
    public static final float AC_TEMP_MIN = 18.0f;
    public static final int HVAC_INNER_PM25_ERROR_CODE = 1023;
    public static final int HVAC_INNER_PM25_LEVEL_BAD = 200;
    public static final int HVAC_INNER_PM25_LEVEL_EXCELLENT = 50;
    public static final int HVAC_INNER_PM25_LEVEL_MIDDLE = 150;
    public static final int HVAC_INNER_PM25_LEVEL_WELL = 100;
    public static final int HVAC_SET_BLOW_MODE_DELAY = 100;
    public static final int HVAC_VENT_ERROR_STATUS = 15;
    public static final int HVAC_VENT_OFF_STATUS = 14;
    public static final int HVAC_VENT_ON_ACTION = 13;
    public static final int HVAC_VENT_POSITION_DRV_LEFT = 0;
    public static final int HVAC_VENT_POSITION_DRV_RIGHT = 1;
    public static final int HVAC_VENT_POSITION_PSN_LEFT = 2;
    public static final int HVAC_VENT_POSITION_PSN_RIGHT = 3;

    void closeHvacWindBlowFace();

    void closeHvacWindBlowFoot();

    void closeHvacWindBlowWin();

    void enterHvacSingleMode();

    int getAcHeatNatureMode();

    HvacAirAutoProtect getAirAutoProtectMode();

    String getAirAutoProtectSound();

    int getFanMaxLevel();

    int getHvacAqsLevel();

    int getHvacAqsMode();

    int getHvacCirculationMode();

    int getHvacCirculationTime();

    int getHvacDeodorantCountdownTime();

    float getHvacDriverTemp();

    int getHvacEAVDriverLeftHPos();

    int getHvacEAVDriverLeftVPos();

    int getHvacEAVDriverRightHPos();

    int getHvacEAVDriverRightVPos();

    int getHvacEAVPsnLeftHPos();

    int getHvacEAVPsnLeftVPos();

    int getHvacEAVPsnRightHPos();

    int getHvacEAVPsnRightVPos();

    int getHvacEavDriverWindMode();

    int getHvacEavPsnWindMode();

    int getHvacEavSweepMode();

    int getHvacEconMode();

    float getHvacExternalTemp();

    int getHvacInnerPM25();

    float getHvacInnerTemp();

    int getHvacIonizerMode();

    float getHvacPsnTemp();

    int getHvacRapidCoolingCountdownTime();

    int getHvacRapidHeatCountdownTime();

    float getHvacRearTempDriver();

    float getHvacRearTempPsn();

    int getHvacRearWindBlowMode();

    int getHvacRearWindSpeedLevel();

    float getHvacThirdRowTempDriver();

    int getHvacThirdRowWindBlowMode();

    int getHvacVentClosedCount();

    int getHvacWindBlowMode();

    int getHvacWindSpeedLevel();

    int getOutsidePm25();

    int getPsnSeatHeatLevel();

    int getPsnSeatVentLevel();

    int getRLSeatHeatLevel();

    int getRRSeatHeatLevel();

    int getSeatHeatLevel();

    int getSeatVentLevel();

    int getSteerHeatLevel();

    int getWindModeColor();

    boolean isAutoDefogSwitch();

    boolean isAutoDefogWorkSt();

    boolean isFrontMirrorHeatEnabled();

    boolean isHvacAcModeOn();

    boolean isHvacAirDistributionAutoEnable();

    boolean isHvacAirIntakeAutoEnable();

    boolean isHvacAutoModeOn();

    boolean isHvacDeodorantEnable();

    boolean isHvacDriverSyncMode();

    boolean isHvacFanSpeedAutoEnable();

    boolean isHvacFrontDefrostOn();

    boolean isHvacNIVentOn();

    boolean isHvacNewFreshSwitchOn();

    boolean isHvacPowerModeOn();

    boolean isHvacPsnSyncMode();

    boolean isHvacQualityPurgeEnable();

    boolean isHvacRapidCoolingEnable();

    boolean isHvacRapidHeatEnable();

    boolean isHvacRearAutoModeOn();

    boolean isHvacRearPowerModeOn();

    boolean isHvacSelfDryOn();

    boolean isHvacSingleMode();

    boolean isHvacSingleModeActive();

    boolean isHvacSweepEnable();

    boolean isHvacVentOpen(int position);

    boolean isMirrorHeatEnabled();

    boolean isSmartHvacEnabled();

    boolean isSmartModeInProtected();

    boolean isSupportDrvSeatHeat();

    boolean isSupportDrvSeatVent();

    boolean isSupportDulTemp();

    boolean isSupportPsnSeatHeat();

    boolean isSupportRearSeatHeat();

    boolean isSupportXFreeBreath();

    void openHvacWindModeFace();

    void setAcHeatNatureMode(int mode);

    void setAirAutoProtectSound(String soundType);

    void setAirAutoProtectedMode(HvacAirAutoProtect mode);

    void setAutoDefogSwitch(boolean enable);

    void setFrontMirrorHeatEnable(boolean enable);

    void setHvacAcMode(boolean enable);

    void setHvacAqsLevel(int level);

    void setHvacAqsMode(int status);

    void setHvacAutoMode(boolean enable);

    void setHvacCirculationInner();

    void setHvacCirculationMode(int mode);

    void setHvacCirculationOut();

    void setHvacCirculationTime(int time);

    void setHvacDeodorantCountdownTime(int time);

    void setHvacDeodorantEnable(boolean enable);

    void setHvacDriverSyncMode(boolean enable);

    void setHvacEAVDriverLeftHPos(int pos);

    void setHvacEAVDriverLeftHPosDirect(int pos);

    void setHvacEAVDriverLeftVPos(int pos);

    void setHvacEAVDriverLeftVPosDirect(int pos);

    void setHvacEAVDriverRightHPos(int pos);

    void setHvacEAVDriverRightHPosDirect(int pos);

    void setHvacEAVDriverRightVPos(int pos);

    void setHvacEAVDriverRightVPosDirect(int pos);

    void setHvacEAVPsnLeftHPos(int pos);

    void setHvacEAVPsnLeftHPosDirect(int pos);

    void setHvacEAVPsnLeftVPos(int pos);

    void setHvacEAVPsnLeftVPosDirect(int pos);

    void setHvacEAVPsnRightHPos(int pos);

    void setHvacEAVPsnRightHPosDirect(int pos);

    void setHvacEAVPsnRightVPos(int pos);

    void setHvacEAVPsnRightVPosDirect(int pos);

    void setHvacEavDriverWindMode(int mode);

    void setHvacEavPsnWindMode(int mode);

    void setHvacEavSweepMode(int status);

    void setHvacEconMode(int status);

    void setHvacFrontDefrost(boolean enable);

    void setHvacFrontDefrostOnly(boolean enable);

    void setHvacHeatMode(boolean enable);

    void setHvacModeData(boolean store);

    void setHvacNIVent(boolean enable);

    void setHvacNatureMode(boolean enable);

    void setHvacNewFreshSwitchStatus(boolean enable);

    void setHvacPowerMode(boolean enable);

    void setHvacPsnSyncMode(boolean enable);

    void setHvacQualityPurgeMode(boolean enable);

    void setHvacRapidCoolingCountdownTime(int time);

    void setHvacRapidCoolingEnable(boolean enable);

    void setHvacRapidHeatCountdownTime(int time);

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

    void setHvacVentStatus(int position, boolean status, boolean isManual);

    void setHvacWindBlowFace();

    void setHvacWindBlowFaceFoot();

    void setHvacWindBlowFoot();

    void setHvacWindBlowMode(int mode);

    void setHvacWindBlowModeGroup(int mode);

    void setHvacWindBlowWinFoot();

    void setHvacWindBlowWindow();

    void setHvacWindSpeedDown();

    void setHvacWindSpeedLevel(int level);

    void setHvacWindSpeedMax();

    void setHvacWindSpeedMin();

    void setHvacWindSpeedUp();

    void setMirrorHeatEnable(boolean enable);

    void setPsnSeatHeatLevel(int level);

    void setPsnSeatVentLevel(int level);

    void setRLSeatHeatLevel(int level);

    void setRRSeatHeatLevel(int level);

    void setSeatHeatLevel(int level);

    void setSeatHeatLevel(int level, boolean storeEnable);

    void setSeatVentLevel(int level);

    void setSeatVentLevel(int level, boolean storeEnable);

    void setSmartHvacEnable(boolean enable);

    void setSteerHeatLevel(int level);

    void setSteerHeatLevel(int level, boolean storeEnable);

    void updateWeatherFromServer();
}
