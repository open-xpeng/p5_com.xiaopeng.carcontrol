package com.xiaopeng.carcontrol.carmanager.controller;

import com.xiaopeng.carcontrol.carmanager.IBaseCallback;
import com.xiaopeng.carcontrol.carmanager.IBaseCarController;

/* loaded from: classes.dex */
public interface ILluController extends IBaseCarController<Callback> {
    public static final int LIGHT_EFFECT_ACCHARGE = 6;
    public static final int LIGHT_EFFECT_AWAKE = 2;
    public static final int LIGHT_EFFECT_DCCHARGE = 7;
    public static final int LIGHT_EFFECT_FINDCAR = 1;
    public static final int LIGHT_EFFECT_SAYHI = 10;
    public static final int LIGHT_EFFECT_SLEEP = 5;
    public static final int LIGHT_EFFECT_TAKEPHOTO = 9;
    public static final String LLU_EFFECT_AC_CHARGE = "android_ac";
    public static final String LLU_EFFECT_AWAKE = "android_unlock_01";
    public static final String LLU_EFFECT_AWAKE2 = "android_unlock_02";
    public static final String LLU_EFFECT_AWAKE3 = "android_unlock_03";
    public static final int LLU_EFFECT_CLOSE = 0;
    public static final String LLU_EFFECT_DANCE = "WeddingMarch";
    public static final String LLU_EFFECT_DC_CHARGE = "android_dc";
    public static final String LLU_EFFECT_FIND_CAR = "android_findcar";
    public static final String LLU_EFFECT_FULL_CHARGED = "fullcharged";
    public static final int LLU_EFFECT_MODE_A = 1;
    public static final int LLU_EFFECT_MODE_B = 2;
    public static final int LLU_EFFECT_MODE_C = 3;
    public static final String LLU_EFFECT_SAYHI = "android_sayhi_01";
    public static final String LLU_EFFECT_SAYHI2 = "android_sayhi_02";
    public static final String LLU_EFFECT_SAYHI3 = "android_sayhi_03";
    public static final String LLU_EFFECT_SLEEP = "android_lock_01";
    public static final String LLU_EFFECT_SLEEP2 = "android_lock_02";
    public static final String LLU_EFFECT_SLEEP3 = "android_lock_03";
    public static final String LLU_EFFECT_TAKE_PHOTO = "takephoto";
    public static final int LLU_MODE_1 = 1;
    public static final int LLU_MODE_2 = 2;
    public static final int LLU_MODE_3 = 3;
    public static final String LLU_SYNC_DANCE_SHOW = "e28s-test";
    public static final String LLU_SYNC_DANCE_SHOW_01 = "remote_show_01";
    public static final String LLU_SYNC_DANCE_SHOW_02 = "remote_show_02";
    public static final String LLU_SYNC_DANCE_SHOW_03 = "remote_show_03";

    /* loaded from: classes.dex */
    public interface Callback extends IBaseCallback {
        default void onLluAwakeModeChanged(int mode) {
        }

        default void onLluChargingChanged(boolean enable) {
        }

        default void onLluEnableChanged(boolean enabled) {
        }

        default void onLluLinkToShowEleChanged(boolean enable) {
        }

        default void onLluPhotoChanged(boolean enable) {
        }

        default void onLluPreviewShowError(String effectName, int errCode) {
        }

        default void onLluPreviewShowFinish(String effectName, String effectType) {
        }

        default void onLluPreviewShowStart(String effectName, String effectType) {
        }

        default void onLluPreviewShowStop(String effectName, String effectType) {
        }

        default void onLluSayHiSwChanged(boolean enable) {
        }

        default void onLluShowOffChanged(boolean enable) {
        }

        default void onLluSleepChanged(boolean enable) {
        }

        default void onLluSleepModeChanged(int mode) {
        }

        default void onLluWakeWaitChanged(boolean enable) {
        }

        default void onXuiLluEffectSetFinish(int effectName, int effectMode) {
        }
    }

    int getLluAwakeMode();

    boolean getLluChargingSw();

    int getLluEffect(int effectName);

    int getLluSleepMode();

    boolean getLluSleepSw();

    int getLluSpeedLimitCfg();

    boolean getLluWakeWaitSw();

    String getRunningLluEffectName();

    int isLightDanceAvailable();

    boolean isLluChargingEnable();

    boolean isLluEnabled();

    boolean isLluLockUnlockEleEnabled();

    boolean isLluPhotoEnable();

    boolean isLluShowOffEnable();

    boolean isLluSleepEnable();

    boolean isLluWakeWaitEnable();

    boolean isSayHiEnabled();

    void setLangLightMusicEffect(String effectName);

    void setLluAwakeMode(int mode);

    void setLluChargingSwitch(boolean enable, boolean sendCmd, boolean needSave);

    void setLluEffect(int effectName, int effectMode);

    int setLluEffectPreview(String effect, String filename);

    int setLluEffectPreview(String effect, String filename, int times);

    void setLluEnable(boolean enable);

    void setLluLockUnlockEleSw(boolean enable);

    void setLluPhotoSwitch(boolean enable);

    void setLluShowOffSwitch(boolean enable);

    void setLluSleepMode(int mode);

    void setLluSleepSwitch(boolean enable, boolean sendCmd, boolean needSave);

    void setLluSpeedLimitCfg(int speed);

    void setLluWakeWaitSwitch(boolean enable, boolean sendCmd, boolean needSave);

    void setPause(boolean pause);

    void setSayHiEnable(boolean enable);

    void stopLluEffectPreview();
}
