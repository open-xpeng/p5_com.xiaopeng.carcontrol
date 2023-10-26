package com.xiaopeng.xvs.xid.sync;

import com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync;

/* loaded from: classes2.dex */
class CarSettingsSyncImpl implements ICarSettingsSync {
    SyncProviderWrapper mWrapper;

    /* JADX INFO: Access modifiers changed from: package-private */
    public CarSettingsSyncImpl(SyncProviderWrapper syncProviderWrapper) {
        this.mWrapper = syncProviderWrapper;
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync
    public String getKeyRearScreenChildLock(String str) {
        return this.mWrapper.get(ICarSettingsSync.KEY_REAR_SCREEN_CHILD_LOCK, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync
    public void setKeyRearScreenChildLock(String str) {
        this.mWrapper.put(ICarSettingsSync.KEY_REAR_SCREEN_CHILD_LOCK, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync
    public String getKeyAnnouncementVoice(String str) {
        return this.mWrapper.get(ICarSettingsSync.KEY_ANNOUNCEMENT_VOICE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync
    public void setKeyAnnouncementVoice(String str) {
        this.mWrapper.put(ICarSettingsSync.KEY_ANNOUNCEMENT_VOICE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync
    public String getKeyOperatingNotify(String str) {
        return this.mWrapper.get(ICarSettingsSync.KEY_OPERATING_NOTIFY, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync
    public void setKeyOperatingNotify(String str) {
        this.mWrapper.put(ICarSettingsSync.KEY_OPERATING_NOTIFY, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync
    public String getKeyVehicleCheckNotify(String str) {
        return this.mWrapper.get(ICarSettingsSync.KEY_VEHICLE_CHECK_NOTIFY, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync
    public void setKeyVehicleCheckNotify(String str) {
        this.mWrapper.put(ICarSettingsSync.KEY_VEHICLE_CHECK_NOTIFY, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync
    public String getKeyValetParkingAssistanceNotify(String str) {
        return this.mWrapper.get(ICarSettingsSync.KEY_VALET_PARKING_ASSISTANCE_NOTIFY, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync
    public void setKeyValetParkingAssistanceNotify(String str) {
        this.mWrapper.put(ICarSettingsSync.KEY_VALET_PARKING_ASSISTANCE_NOTIFY, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync
    public String getKeyXpilotNotify(String str) {
        return this.mWrapper.get(ICarSettingsSync.KEY_XPILOT_NOTIFY, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync
    public void setKeyXpilotNotify(String str) {
        this.mWrapper.put(ICarSettingsSync.KEY_XPILOT_NOTIFY, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync
    public String getKeyCarControlNotify(String str) {
        return this.mWrapper.get(ICarSettingsSync.KEY_CAR_CONTROL_NOTIFY, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync
    public void setKeyCarControlNotify(String str) {
        this.mWrapper.put(ICarSettingsSync.KEY_CAR_CONTROL_NOTIFY, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync
    public String getKeyAppStoreNotify(String str) {
        return this.mWrapper.get(ICarSettingsSync.KEY_APP_STORE_NOTIFY, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync
    public void setKeyAppStoreNotify(String str) {
        this.mWrapper.put(ICarSettingsSync.KEY_APP_STORE_NOTIFY, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync
    public String getKeyOtaNotify(String str) {
        return this.mWrapper.get(ICarSettingsSync.KEY_OTA_NOTIFY, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync
    public void setKeyOtaNotify(String str) {
        this.mWrapper.put(ICarSettingsSync.KEY_OTA_NOTIFY, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync
    public String getKeyXpMusicNotify(String str) {
        return this.mWrapper.get(ICarSettingsSync.KEY_XP_MUSIC_NOTIFY, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync
    public void putKeyXpMusicNotify(String str) {
        this.mWrapper.put(ICarSettingsSync.KEY_XP_MUSIC_NOTIFY, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync
    public String getKeyLbsMediumVoice(String str) {
        return this.mWrapper.get(ICarSettingsSync.KEY_LBS_MEDIUM_VOICE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync
    public void putKeyLbsMediumVoice(String str) {
        this.mWrapper.put(ICarSettingsSync.KEY_LBS_MEDIUM_VOICE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync
    public String getFontScale(String str) {
        return this.mWrapper.get(ICarSettingsSync.KEY_FONT_SIZE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync
    public void putFontScale(String str) {
        this.mWrapper.put(ICarSettingsSync.KEY_FONT_SIZE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync
    public String get24Format(String str) {
        return this.mWrapper.get(ICarSettingsSync.IS_24_HOUR_FORMAT, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync
    public void put24Format(String str) {
        this.mWrapper.put(ICarSettingsSync.IS_24_HOUR_FORMAT, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync
    public String getSystemSoundEnabled(String str) {
        return this.mWrapper.get(ICarSettingsSync.SYSTEM_SOUND_ENABLE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync
    public void putSystemSoundEnabled(String str) {
        this.mWrapper.put(ICarSettingsSync.SYSTEM_SOUND_ENABLE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync
    public String getMeterVolume(String str) {
        return this.mWrapper.get(ICarSettingsSync.METER_VOLUME, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync
    public void putMeterVolume(String str) {
        this.mWrapper.put(ICarSettingsSync.METER_VOLUME, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync
    public String getMainDriverExclusive(String str) {
        return this.mWrapper.get(ICarSettingsSync.MAIN_DRIVER_EXCLUSIVE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync
    public void putMainDriverExclusive(String str) {
        this.mWrapper.put(ICarSettingsSync.MAIN_DRIVER_EXCLUSIVE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync
    public String getVolumeDownWithDoorOpen(String str) {
        return this.mWrapper.get(ICarSettingsSync.KEY_DOOR_OPEN, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync
    public void putVolumeDownWithDoorOpen(String str) {
        this.mWrapper.put(ICarSettingsSync.KEY_DOOR_OPEN, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync
    public String getVolumeDownInReverseMode(String str) {
        return this.mWrapper.get(ICarSettingsSync.KEY_R_STALL, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync
    public void putVolumeDownInReverseMode(String str) {
        this.mWrapper.put(ICarSettingsSync.KEY_R_STALL, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync
    public String getAutoBrightnessEnable(String str) {
        return this.mWrapper.get(ICarSettingsSync.KEY_AUTO_BRIGHTNESS, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync
    public void putAutoBrightnessEnable(String str) {
        this.mWrapper.put(ICarSettingsSync.KEY_AUTO_BRIGHTNESS, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync
    public String getDarkLightAdaptation(String str) {
        return this.mWrapper.get(ICarSettingsSync.KEY_DARK_LIGHT_ADAPTATION, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync
    public void putDarkLightAdaptation(String str) {
        this.mWrapper.put(ICarSettingsSync.KEY_DARK_LIGHT_ADAPTATION, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync
    public String getMeterAutoBrightness(String str) {
        return this.mWrapper.get(ICarSettingsSync.KEY_METER_AUTO_BRIGHTNESS, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync
    public void putMeterAutoBrightness(String str) {
        this.mWrapper.put(ICarSettingsSync.KEY_METER_AUTO_BRIGHTNESS, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync
    public String getMeterDarkLightAdaptation(String str) {
        return this.mWrapper.get(ICarSettingsSync.KEY_METER_DARK_LIGHT_ADAPTATION, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync
    public void putMeterDarkLightAdaptation(String str) {
        this.mWrapper.put(ICarSettingsSync.KEY_METER_DARK_LIGHT_ADAPTATION, str);
    }
}
