package com.xiaopeng.carcontrol.viewmodel.lamp;

import com.xiaopeng.carcontrol.viewmodel.IBaseViewModel;

/* loaded from: classes2.dex */
public interface ILluViewModel extends IBaseViewModel {
    int getLluAwakeMode();

    boolean getLluChargingSw();

    int getLluEffect(int effectName);

    int getLluSleepMode();

    boolean getLluSleepSw();

    int getLluSpeedLimitCfg();

    boolean getLluWakeWaitSw();

    LluEffect getRunningLluEffect();

    boolean isLLuSwEnabled();

    int isLightDanceAvailable();

    boolean isLluChargingEnable();

    boolean isLluLockUnlockEleEnabled();

    boolean isLluPhotoEnable();

    boolean isLluShowOffEnable();

    boolean isLluSleepEnable();

    boolean isLluWakeWaitEnable();

    boolean isSayHiEnabled();

    void playLluEffect(boolean pause);

    void setLangLightMusicEffect(String effectName);

    void setLluAwakeMode(int mode);

    void setLluChargingSwitch(boolean enable);

    int setLluDancePreview(String filename);

    void setLluEffect(int effectName, int effectMode);

    void setLluEffectPreview(String effect);

    void setLluLockUnlockEleSw(boolean enable);

    void setLluMultiSwEnable(boolean enable);

    void setLluPhotoSwitch(boolean enable);

    void setLluShowOffSwitch(boolean enable);

    void setLluSleepMode(int mode);

    void setLluSleepSwitch(boolean enable);

    void setLluSpeedLimitCfg(int speed);

    void setLluSwEnable(boolean enable);

    void setLluWakeWaitSwitch(boolean enable);

    void setSayHiEnable(boolean enable);

    void stopLluEffectPreview();
}
