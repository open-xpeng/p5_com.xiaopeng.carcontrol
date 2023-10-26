package com.xiaopeng.carcontrol.viewmodel.chassis;

import com.xiaopeng.carcontrol.viewmodel.IBaseViewModel;

/* loaded from: classes2.dex */
public interface IChassisViewModel extends IBaseViewModel {
    public static final int APB_SYSTEM_STATUS_CLOSED = 2;
    public static final int APB_SYSTEM_STATUS_LOCKING = 4;
    public static final int APB_SYSTEM_STATUS_RELEASED = 1;
    public static final int APB_SYSTEM_STATUS_RELEASING = 3;
    public static final int CDC_MODE_COMFORT = 2;
    public static final int CDC_MODE_SPORT = 3;
    public static final int CDC_MODE_STANDARD = 1;
    public static final int TTM_OPERATING_TIME = 10000;
    public static final int TTM_RESET_TIME = 10000;

    void calibrateTyrePressure();

    int getAirSuspensionHeightMode();

    boolean getAirSuspensionRepairMode();

    int getAirSuspensionSoftMode();

    int getApbSystemStatus();

    int getAsAutoLevelingResult();

    boolean getAsCampingMode();

    boolean getAsCustomerModeFlagSwitchStatus();

    boolean getAsFaultStatus();

    boolean getAsHeightChangingStatus();

    boolean getAsHoistModeSwitchStatus();

    boolean getAsLocationControlSw();

    boolean getAsLocationSw();

    boolean getAsLockModeStatus();

    int getAsRequestEspState();

    boolean getAvhFault();

    boolean getAvhForUI();

    boolean getAvhSw();

    int getCdcMode();

    boolean getCdcuTrailerModeStatus();

    boolean getDtcFaultStatus();

    boolean getEbw();

    int getEngineeringModeStatus();

    boolean getEpsWarningState();

    int getEspBpfMode();

    boolean getEspCstSw();

    boolean getEspFault();

    boolean getEspForUI();

    boolean getEspSw();

    boolean getHdc();

    boolean getHdcFault();

    float getSteeringAngle();

    int getSteeringEps();

    float getTorsionBarTorque();

    int getTpmsCalibrateState();

    int getTpmsTemperature(int position);

    boolean getTrailerModeStatus();

    boolean getTsmFaultStatus();

    boolean getTtmDenormalizeStatus();

    int getTtmHookMotorStatus();

    boolean getTtmLampConnectStatus();

    int getTtmSwitch();

    int getTtmSwitchStatusForUI();

    boolean getTtmSystemError();

    String getTyrePressure(int position);

    boolean isAirSuspensionWelcomeEnable();

    boolean isDrvBeltBuckled();

    boolean isDrvDoorClosed();

    boolean isEasyLoadingEnable();

    boolean isTpmsPressureNormal();

    boolean[] isTpmsTempWarning();

    void resetTtmSwitchStatus();

    void saveAsLocationInfo(int asHeight);

    void setAirSuspensionHeightMode(int height, boolean storeEnable);

    void setAirSuspensionRepairMode(boolean enable);

    void setAirSuspensionSoftMode(int soft);

    void setAirSuspensionWelcome(boolean enable);

    void setAsCampingMode(boolean enable);

    void setAsCustomerModeFlagSwitchStatus(boolean enable);

    void setAsLocationControlSw(boolean on);

    void setAsLocationSw(boolean on);

    void setAsLocationSw(boolean on, boolean storeEnable);

    void setAvhSw(boolean enable);

    void setCdcMode(int cdc);

    void setEasyLoadingSwitch(boolean enable);

    void setEbw(boolean enable);

    void setEngineeringModeStatus(int status);

    void setEpbSystemStatus(boolean enable);

    void setEsp(boolean enable);

    void setEspBpfMode(int mode);

    void setEspCstSw(boolean enable);

    void setEspSw();

    void setHdc(boolean enable);

    void setSteeringEps(int eps, boolean storeEnable);

    void setTrailerModeStatus(boolean enable);

    void setTtmSwitch(boolean enable);

    void setTtmSwitchStatusForUI(boolean enable);
}
