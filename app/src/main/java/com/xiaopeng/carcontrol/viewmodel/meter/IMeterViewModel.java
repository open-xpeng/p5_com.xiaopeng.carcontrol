package com.xiaopeng.carcontrol.viewmodel.meter;

import com.xiaopeng.carcontrol.viewmodel.IBaseViewModel;

/* loaded from: classes2.dex */
public interface IMeterViewModel extends IBaseViewModel {
    public static final String SETTINGS_KEY_ECALL_AVAILABLE = "xp_ecall_enable";
    public static final String SETTINGS_KEY_ECALL_SHOW = "xp_statusbar_ecall_show";

    boolean getBrightSw();

    boolean getDoorBossKeySw();

    int getDoorKeyForCustomer();

    boolean getMediaSw();

    String getMileageA();

    String getMileageB();

    String getMileageSinceLastCharge();

    String getMileageSinceStartUp();

    String getMileageTotal();

    boolean getSpeedWarningSw();

    int getSpeedWarningValue();

    boolean getTempSw();

    int getTouchRotationDirection();

    boolean getWindModeSw();

    boolean getWindPowerSw();

    int getXKeyForCustomer();

    boolean isEcallAvailable();

    boolean isWheelKeyProtectEnabled();

    void requestMaintainData();

    void resetMeterMileageA();

    void resetMeterMileageB();

    void setBrightSw(boolean enable);

    void setDoorBossKeySw(boolean enable);

    void setDoorKeyForCustomer(int keyFunc);

    void setMediaSw(boolean enable);

    void setShowEcallOnStatus(boolean show);

    void setSpeedWarningSw(boolean enable);

    void setSpeedWarningValue(int speed);

    void setTempSw(boolean enable);

    void setTouchRotationDirection(int direction);

    void setUserScenarioInfo(int[] info);

    void setWheelKeyProtectSw(boolean enable);

    void setWindModeSw(boolean enable);

    void setWindPowerSw(boolean enable);

    void setXKeyForCustomer(int keyFunc);

    boolean shouldShowEcallOnStatus();
}
