package com.xiaopeng.carcontrol.carmanager.controller;

import com.xiaopeng.carcontrol.carmanager.IBaseCallback;
import com.xiaopeng.carcontrol.carmanager.IBaseCarController;
import com.xiaopeng.xuimanager.soundresource.data.BootSoundResource;

/* loaded from: classes.dex */
public interface IAvasController extends IBaseCarController<Callback> {
    public static final int AVAS_LOW_SPD_VOLUME_MAX = 100;
    public static final int AVAS_LOW_SPD_VOLUME_MIN = 1;
    public static final int AVAS_SOUND_EFFECT_1 = 1;
    public static final int AVAS_SOUND_EFFECT_2 = 2;
    public static final int AVAS_SOUND_EFFECT_3 = 3;
    public static final int AVAS_SOUND_EFFECT_4 = 4;
    public static final int BOOT_SOUND_DISABLE = 0;
    public static final int BOOT_SOUND_EFFECT_A = 1;
    public static final int BOOT_SOUND_EFFECT_B = 2;
    public static final int BOOT_SOUND_EFFECT_C = 3;
    public static final int BOOT_SOUND_EFFECT_D = 4;

    /* loaded from: classes.dex */
    public interface Callback extends IBaseCallback {
        default void onAvasAcChargingChanged(boolean enable) {
        }

        default void onAvasDcChargingChanged(boolean enable) {
        }

        default void onAvasDisconnectChargingChanged(boolean enable) {
        }

        default void onAvasExternalSwChanged(boolean enable) {
        }

        default void onAvasExternalVolumeChanged(int volume) {
        }

        default void onAvasLowSpdChanged(boolean enabled) {
        }

        default void onAvasLowSpdEffectChanged(int effect) {
        }

        default void onAvasLowSpdVolumeChanged(int volume) {
        }

        default void onAvasSayHiSwChanged(boolean enable) {
        }

        default void onAvasSleepChanged(boolean enable) {
        }

        default void onAvasTakePhotoChanged(boolean enable) {
        }

        default void onAvasWakeWaitChanged(boolean enable) {
        }

        default void onAvasWakeWaitFullChargeChanged(boolean enable) {
        }

        default void onBootEffectChanged(int effect) {
        }

        default void onFriendSoundTypeChanged(int type) {
        }
    }

    int getAvasExternalVolume();

    boolean getAvasSayHiSw();

    boolean getAvasSpeakerSw();

    int getBootEffectBeforeSw();

    int getBootSoundEffect();

    BootSoundResource[] getBootSoundResource();

    int getFriendSoundType();

    int getLowSpdSoundType();

    int getLowSpdSoundVolume();

    boolean isAvasAcChargingEnable();

    boolean isAvasDcChargingEnable();

    boolean isAvasDisconnectChargingEnable();

    boolean isAvasExternalEnabled();

    boolean isAvasSleepEnable();

    boolean isAvasTakePhotoEnable();

    boolean isAvasWakeWaitEnable();

    boolean isAvasWakeWaitFullChargeEnable();

    boolean isLowSpdSoundEnabled();

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

    void setFriendSoundType(int type);

    void setLockAvasSw(boolean enable);

    void setLowSpdSoundEnable(boolean enable);

    void setLowSpdSoundType(int type);

    void setLowSpdSoundVolume(int volume);
}
