package com.xiaopeng.carcontrol.viewmodel.lamp;

import com.xiaopeng.carcontrol.viewmodel.IBaseViewModel;

/* loaded from: classes2.dex */
public interface ILampViewModel extends IBaseViewModel {
    public static final int ALL_DOME_LIGHT = 4;
    public static final int ALL_FRONT_DOME_LIGHT = 5;
    public static final int ALL_REAR_DOME_LIGHT = 6;
    public static final int ALL_THIRD_ROW_DOME_LIGHT = 9;
    public static final int DOME_LIGHT_BRIGHT_LEVEL_HIGH = 5;
    public static final int DOME_LIGHT_BRIGHT_LEVEL_LOW = 1;
    public static final int DOME_LIGHT_BRIGHT_LEVEL_MEDIUM = 3;
    public static final int FL_DOME_LIGHT = 0;
    public static final int FR_DOME_LIGHT = 1;
    public static final int RL_DOME_LIGHT = 2;
    public static final int RR_DOME_LIGHT = 3;
    public static final String SETTING_KEY_WAIT_MODE = "wait_mode";
    public static final int WAIT_MODE_OFF = 0;
    public static final int WAIT_MODE_ON = 1;

    boolean getCarpetLightWelcomeMode();

    int getDaytimeRunningLightsOutputStatus();

    int getDomeLightBrightness();

    int getDomeLightState();

    boolean getDomeLightSw(int lightType);

    int getHeadLampGroup();

    int getHeadLampGroupSp();

    boolean getHighBeamState();

    int getLampHeightLevel();

    int getLightMeHomeTime();

    boolean getLowBeamState();

    boolean getParkLampState();

    int[] getParkingLampStates();

    boolean getPollingLightWelcomeMode();

    boolean getRearFogLampState();

    boolean getRearLogoLightSw();

    TurnLampState getTurnLampState();

    boolean isAutoLampHeight();

    boolean isLedDrlEnabled();

    boolean isLightMeHomeEnable();

    boolean isParkLampIncludeFmB();

    boolean isTurnLampOn();

    void setAutoLampHeight(boolean auto);

    void setAutoLampHeight(boolean auto, boolean needSave);

    void setCarpetLightWelcomeMode(boolean enable);

    void setDomeLight(int value);

    void setDomeLightBrightness(int brightness);

    void setDomeLightBrightness(int brightness, boolean needSave);

    void setDomeLightSw(int lightType, boolean enable);

    void setHeadLampGroup(int groupId);

    void setHeadLampGroup(int groupId, boolean needSave);

    void setLampHeightLevel(int level);

    void setLampHeightLevel(int level, boolean needSave);

    void setLedDrlEnable(boolean enable);

    void setLightMeHome(boolean enable);

    void setLightMeHomeTime(int timeCfg);

    void setLowBeamOffConfirmSt(boolean confirmSt);

    void setParkLampIncludeFmB(boolean enable);

    void setPollingLightWelcomeMode(boolean enable);

    void setRearFogLamp(boolean enable);

    void setRearLogoLightSw(boolean enable);

    void showRearLogoLightConfirmDialog();
}
