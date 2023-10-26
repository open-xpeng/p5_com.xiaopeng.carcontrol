package com.xiaopeng.carcontrol.carmanager.controller;

import com.xiaopeng.carcontrol.carmanager.IBaseCallback;
import com.xiaopeng.carcontrol.carmanager.IBaseCarController;
import java.util.HashMap;

/* loaded from: classes.dex */
public interface IIcmController extends IBaseCarController<Callback> {
    public static final int BRAKE_FLUID_LEVEL_LOW = 1;
    public static final int BRAKE_FLUID_LEVEL_NORMAL = 0;
    public static final int CUSTOM_DOOR_KEY_DISABLE = 0;
    public static final int CUSTOM_DOOR_KEY_DVR_CAPTURE = 5;
    public static final int CUSTOM_DOOR_KEY_MUTE = 2;
    public static final int CUSTOM_DOOR_KEY_SHOW_OFF = 4;
    public static final int CUSTOM_DOOR_KEY_SPEECH = 1;
    public static final int CUSTOM_DOOR_KEY_SWITCH_MEDIA = 3;
    public static final int CUSTOM_X_KEY_AUTO_DRIVE = 5;
    public static final int CUSTOM_X_KEY_AUTO_PARK = 4;
    public static final int CUSTOM_X_KEY_DISABLE = 0;
    public static final int CUSTOM_X_KEY_DVR_CAPTURE = 3;
    public static final int CUSTOM_X_KEY_NRA = 10;
    public static final int CUSTOM_X_KEY_SHOW_OFF = 2;
    public static final int CUSTOM_X_KEY_SWITCH_MEDIA = 1;
    public static final int CUSTOM_X_KEY_TEAM_TALK = 7;
    public static final int CUSTOM_X_KEY_UNLOCK_TRUNK = 8;
    public static final int CUSTOM_X_KEY_WETCHAT = 6;
    public static final int CUSTOM_X_KEY_XPOWER = 9;
    public static final int ID_ICM_ALARM_VOLUME = 557848069;
    public static final int ID_ICM_MEDIA_SOURCE = 557848118;
    public static final int ID_ICM_SCREEN_LIGHT = 557848119;
    public static final int ID_ICM_SPEED_LIMIT_WARNING_SWITCH = 557848098;
    public static final int ID_ICM_SPEED_LIMIT_WARNING_VALUE = 557848109;
    public static final int ID_ICM_TEMPERATURE = 557848101;
    public static final int ID_ICM_WIND_MODE = 557848097;
    public static final int ID_ICM_WIND_POWER = 557848103;
    public static final String KEY_ICM_BRIGHTNESS = "icm_brightness";
    public static final String KEY_SYSTEM_MEDITATION_MODE = "key_system_meditation_mode";
    public static final String KEY_TOUCH_ROTATION_SPEED = "xp_touch_rotation_speed";
    public static final String KEY_WHEEL_KEY_PROTECT = "XpWheelkeyIgnore";
    public static final int METER_ALARM_VOLUME_POWER = 2;
    public static final int METER_ALARM_VOLUME_SOFT = 0;
    public static final int METER_ALARM_VOLUME_STANDARD = 1;
    public static final int TOUCH_ROTATION_INWARD = 0;
    public static final int TOUCH_ROTATION_OUTWARD = 1;
    public static final int TOUCH_ROTATION_SPEED_HIGH = 1;
    public static final int TOUCH_ROTATION_SPEED_LOW = 3;
    public static final int TOUCH_ROTATION_SPEED_MIDDLE = 2;
    public static final int WHEEL_KEY_PROTECT_OFF = 0;
    public static final int WHEEL_KEY_PROTECT_ON = 1;

    /* loaded from: classes.dex */
    public interface Callback extends IBaseCallback {
        void onBrightSwChanged(boolean enabled);

        void onDoorKeyChanged(int keyValue);

        void onMediaSwChanged(boolean enabled);

        void onMileageAChanged(float value);

        void onMileageBChanged(float value);

        void onMileageLastChargeChanged(float value);

        void onMileageStartUpChanged(float value);

        void onMileageTotalChanged(float value);

        void onSpeedWarningSwChanged(boolean enabled);

        void onSpeedWarningValueChanged(int value);

        void onTempSwChanged(boolean enabled);

        void onWindModeSwChanged(boolean enabled);

        void onWindPowerSwChanged(boolean enabled);

        void onXKeyChanged(int keyValue);
    }

    int getBrakeFluidLevelWarningMessage();

    boolean getBrightSw();

    boolean getDoorBossKeySw();

    int getDoorKeyForCustomer();

    int getIcmBrightness();

    boolean getMediaSw();

    float getMileageA();

    float getMileageB();

    float getMileageSinceLastCharge();

    float getMileageSinceStartUp();

    float getMileageTotal();

    int getSpeedLimitValueFromDataSync();

    boolean getSpeedWarningSw();

    int getSpeedWarningValue();

    boolean getTempSw();

    int getTouchRotationDirection();

    int getTouchRotationSpeed();

    boolean getWindModeSw();

    boolean getWindPowerSw();

    int getXKeyForCustomer();

    boolean isWheelKeyProtectEnabled();

    void resetMeterMileageA();

    void resetMeterMileageB();

    void setBrightSw(boolean enable);

    void setDoorBossKeySw(boolean enable);

    void setDoorKeyForCustomer(int value);

    void setIcmBrightness(int value);

    void setIcmDayNightMode(int mode);

    void setIcmDrvTemp(float temp);

    void setIcmMeditationMode(boolean enter);

    void setIcmWindBlowMode(int mode);

    void setIcmWindLevel(int level);

    void setMediaSw(boolean enable);

    void setMeterMultiProperties(HashMap<Integer, Object> propertyMap);

    void setSpeedWarningSw(boolean enable);

    void setSpeedWarningValue(int speed);

    void setTempSw(boolean enable);

    void setTouchRotationDirection(int direction);

    void setTouchRotationSpeed(int speed, boolean needSave);

    void setUserScenarioInfo(int[] info);

    void setWheelKeyProtectSw(boolean enable);

    void setWindModeSw(boolean enable);

    void setWindPowerSw(boolean enable);

    void setWiperSensitivity(int sensitivity);

    void setXKeyForCustomer(int keyFunc);
}
