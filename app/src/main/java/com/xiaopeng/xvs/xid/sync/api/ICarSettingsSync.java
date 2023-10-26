package com.xiaopeng.xvs.xid.sync.api;

/* loaded from: classes2.dex */
public interface ICarSettingsSync {
    public static final String IS_24_HOUR_FORMAT = "is_24_hour_format";
    public static final String KEY_ANNOUNCEMENT_VOICE = "KEY_ANNOUNCEMENT_VOICE";
    public static final String KEY_APP_STORE_NOTIFY = "KEY_APP_STORE_NOTIFY";
    public static final String KEY_AUTO_BRIGHTNESS = "KEY_AUTO_BRIGHTNESS";
    public static final String KEY_CAR_CONTROL_NOTIFY = "KEY_CAR_CONTROL_NOTIFY";
    public static final String KEY_DARK_LIGHT_ADAPTATION = "KEY_DARK_LIGHT_ADAPTATION";
    public static final String KEY_DOOR_OPEN = "KEY_DOOR_OPEN";
    public static final String KEY_FONT_SIZE = "KEY_FONT_SIZE";
    public static final String KEY_LBS_MEDIUM_VOICE = "KEY_LBS_MEDIUM_VOICE";
    public static final String KEY_METER_AUTO_BRIGHTNESS = "KEY_METER_AUTO_BRIGHTNESS";
    public static final String KEY_METER_DARK_LIGHT_ADAPTATION = "KEY_METER_DARK_LIGHT_ADAPTATION";
    public static final String KEY_OPERATING_NOTIFY = "KEY_OPERATING_NOTIFY";
    public static final String KEY_OTA_NOTIFY = "KEY_OTA_NOTIFY";
    public static final String KEY_REAR_SCREEN_CHILD_LOCK = "KEY_REAR_SCREEN_CHILD_LOCK";
    public static final String KEY_R_STALL = "KEY_R_STALL";
    public static final String KEY_VALET_PARKING_ASSISTANCE_NOTIFY = "KEY_VALET_PARKING_ASSISTANCE_NOTIFY";
    public static final String KEY_VEHICLE_CHECK_NOTIFY = "KEY_VEHICLE_CHECK_NOTIFY";
    public static final String KEY_XPILOT_NOTIFY = "KEY_XPILOT_NOTIFY";
    public static final String KEY_XP_MUSIC_NOTIFY = "KEY_XP_MUSIC_NOTIFY";
    public static final String MAIN_DRIVER_EXCLUSIVE = "main_driver_exclusive";
    public static final String METER_VOLUME = "meter_volume";
    public static final String SYSTEM_SOUND_ENABLE = "system_sound_enable";

    String get24Format(String str);

    String getAutoBrightnessEnable(String str);

    String getDarkLightAdaptation(String str);

    String getFontScale(String str);

    String getKeyAnnouncementVoice(String str);

    String getKeyAppStoreNotify(String str);

    String getKeyCarControlNotify(String str);

    String getKeyLbsMediumVoice(String str);

    String getKeyOperatingNotify(String str);

    String getKeyOtaNotify(String str);

    String getKeyRearScreenChildLock(String str);

    String getKeyValetParkingAssistanceNotify(String str);

    String getKeyVehicleCheckNotify(String str);

    String getKeyXpMusicNotify(String str);

    String getKeyXpilotNotify(String str);

    String getMainDriverExclusive(String str);

    String getMeterAutoBrightness(String str);

    String getMeterDarkLightAdaptation(String str);

    String getMeterVolume(String str);

    String getSystemSoundEnabled(String str);

    String getVolumeDownInReverseMode(String str);

    String getVolumeDownWithDoorOpen(String str);

    void put24Format(String str);

    void putAutoBrightnessEnable(String str);

    void putDarkLightAdaptation(String str);

    void putFontScale(String str);

    void putKeyLbsMediumVoice(String str);

    void putKeyXpMusicNotify(String str);

    void putMainDriverExclusive(String str);

    void putMeterAutoBrightness(String str);

    void putMeterDarkLightAdaptation(String str);

    void putMeterVolume(String str);

    void putSystemSoundEnabled(String str);

    void putVolumeDownInReverseMode(String str);

    void putVolumeDownWithDoorOpen(String str);

    void setKeyAnnouncementVoice(String str);

    void setKeyAppStoreNotify(String str);

    void setKeyCarControlNotify(String str);

    void setKeyOperatingNotify(String str);

    void setKeyOtaNotify(String str);

    void setKeyRearScreenChildLock(String str);

    void setKeyValetParkingAssistanceNotify(String str);

    void setKeyVehicleCheckNotify(String str);

    void setKeyXpilotNotify(String str);
}
