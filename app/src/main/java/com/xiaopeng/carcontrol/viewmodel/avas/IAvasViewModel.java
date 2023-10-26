package com.xiaopeng.carcontrol.viewmodel.avas;

import com.xiaopeng.carcontrol.viewmodel.IBaseViewModel;

/* loaded from: classes2.dex */
public interface IAvasViewModel extends IBaseViewModel {
    public static final int AVAS_LOW_SPD_VOLUME_MAX = 100;
    public static final int AVAS_LOW_SPD_VOLUME_MIN = 1;
    public static final String SETTING_KEY_LOW_SPD_SW = "xp_low_speed_sound";
    public static final int SETTING_LOW_SPD_ON = 1;

    int getAvasExternalVolume();

    boolean getAvasSayHiSw();

    boolean getAvasSpeakerSw();

    int getBootEffectBeforeSw();

    int getBootSoundEffect();

    String getBootSoundPreviewPath(int type);

    int getFriendEffect();

    int getLowSpdEffect();

    int getLowSpdVolume();

    boolean isAvasAcChargingEnable();

    boolean isAvasDcChargingEnable();

    boolean isAvasDisconnectChargingEnable();

    boolean isAvasExternalSwEnabled();

    boolean isAvasSleepEnable();

    boolean isAvasTakePhotoEnable();

    boolean isAvasWakeWaitEnable();

    boolean isAvasWakeWaitFullChargeEnable();

    boolean isLowSpdEnabled();

    void setAvasAcChargingSwitch(boolean enable);

    void setAvasDcChargingSwitch(boolean enable);

    void setAvasDisconnectChargingSwitch(boolean enable);

    void setAvasExternalSound(int type);

    void setAvasExternalSw(boolean enable);

    void setAvasExternalVolume(int volume);

    void setAvasSayHiSw(boolean enable);

    void setAvasSleepSwitch(boolean enable);

    void setAvasSpeakerSw(boolean enable);

    void setAvasTakePhotoSwitch(boolean enable);

    void setAvasWakeWaitFullChargeSwitch(boolean enable);

    void setAvasWakeWaitSwitch(boolean enable);

    void setBootEffectBeforeSw(int type);

    void setBootSoundEffect(int type);

    void setExternalSoundModeCmd(int mode);

    void setFriendEffect(int type);

    void setLowSpdEffect(int type);

    void setLowSpdEnable(boolean enable);

    void setLowSpdVolume(int volume);
}
