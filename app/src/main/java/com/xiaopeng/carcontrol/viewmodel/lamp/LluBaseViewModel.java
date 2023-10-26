package com.xiaopeng.carcontrol.viewmodel.lamp;

import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.ILluController;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.util.LogUtils;

/* loaded from: classes2.dex */
public abstract class LluBaseViewModel implements ILluViewModel, ILluController.Callback {
    private static final String TAG = "LluBaseViewModel";
    ILluController mLluController = (ILluController) CarClientWrapper.getInstance().getController(CarClientWrapper.XP_LLU_SERVICE);

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel
    public boolean isLLuSwEnabled() {
        return this.mLluController.isLluEnabled();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel
    public void setLluSwEnable(boolean enable) {
        this.mLluController.setLluEnable(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel
    public void setLluMultiSwEnable(boolean enable) {
        if (!this.mLluController.isLluEnabled()) {
            this.mLluController.setLluWakeWaitSwitch(enable, false, true);
            this.mLluController.setLluSleepSwitch(enable, false, true);
            this.mLluController.setLluChargingSwitch(enable, false, true);
        } else {
            this.mLluController.setLluWakeWaitSwitch(enable, true, true);
            this.mLluController.setLluSleepSwitch(enable, true, true);
            this.mLluController.setLluChargingSwitch(enable, true, true);
        }
        ILluController iLluController = this.mLluController;
        if (BaseFeatureOption.getInstance().isSupportLluAllOn()) {
            enable = true;
        }
        iLluController.setLluEnable(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel
    public void stopLluEffectPreview() {
        this.mLluController.stopLluEffectPreview();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel
    public void setLluEffectPreview(String effect) {
        this.mLluController.setLluEffectPreview(effect, null);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel
    public int setLluDancePreview(String filename) {
        return this.mLluController.setLluEffectPreview(ILluController.LLU_EFFECT_DANCE, filename);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel
    public LluEffect getRunningLluEffect() {
        return LluEffect.fromLluCmd(this.mLluController.getRunningLluEffectName());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel
    public void setLluAwakeMode(int mode) {
        this.mLluController.setLluAwakeMode(mode);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel
    public int getLluAwakeMode() {
        return this.mLluController.getLluAwakeMode();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel
    public void setLluSleepMode(int mode) {
        this.mLluController.setLluSleepMode(mode);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel
    public int getLluSleepMode() {
        return this.mLluController.getLluSleepMode();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel
    public void setLluLockUnlockEleSw(boolean enable) {
        this.mLluController.setLluLockUnlockEleSw(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel
    public boolean isLluLockUnlockEleEnabled() {
        return this.mLluController.isLluLockUnlockEleEnabled();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel
    public void setLluWakeWaitSwitch(boolean enable) {
        if (enable && !this.mLluController.isLluEnabled()) {
            this.mLluController.setLluWakeWaitSwitch(true, false, true);
            this.mLluController.setLluEnable(true);
            return;
        }
        this.mLluController.setLluWakeWaitSwitch(enable, true, true);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel
    public boolean isLluWakeWaitEnable() {
        return this.mLluController.isLluWakeWaitEnable();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel
    public boolean getLluWakeWaitSw() {
        return this.mLluController.getLluWakeWaitSw();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel
    public void setLluSleepSwitch(boolean enable) {
        if (enable && !this.mLluController.isLluEnabled()) {
            this.mLluController.setLluSleepSwitch(true, false, true);
            this.mLluController.setLluEnable(true);
            return;
        }
        this.mLluController.setLluSleepSwitch(enable, true, true);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel
    public boolean isLluSleepEnable() {
        return this.mLluController.isLluSleepEnable();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel
    public boolean getLluSleepSw() {
        return this.mLluController.getLluSleepSw();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel
    public void setLluChargingSwitch(boolean enable) {
        if (enable && !this.mLluController.isLluEnabled()) {
            this.mLluController.setLluChargingSwitch(true, false, true);
            this.mLluController.setLluEnable(true);
            return;
        }
        this.mLluController.setLluChargingSwitch(enable, true, true);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel
    public boolean isLluChargingEnable() {
        return this.mLluController.isLluChargingEnable();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel
    public boolean getLluChargingSw() {
        return this.mLluController.getLluChargingSw();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel
    public void setLluPhotoSwitch(boolean enable) {
        try {
            this.mLluController.setLluPhotoSwitch(enable);
        } catch (Exception e) {
            LogUtils.e(TAG, (String) null, e);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel
    public boolean isLluPhotoEnable() {
        return this.mLluController.isLluPhotoEnable();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel
    public void setLluShowOffSwitch(boolean enable) {
        try {
            this.mLluController.setLluShowOffSwitch(enable);
        } catch (Exception e) {
            LogUtils.e(TAG, (String) null, e);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel
    public boolean isLluShowOffEnable() {
        return this.mLluController.isLluShowOffEnable();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel
    public void setLangLightMusicEffect(String effectName) {
        try {
            this.mLluController.setLangLightMusicEffect(effectName);
        } catch (Exception e) {
            LogUtils.e(TAG, (String) null, e);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel
    public void playLluEffect(boolean pause) {
        try {
            this.mLluController.setPause(pause);
        } catch (Exception e) {
            LogUtils.e(TAG, (String) null, e);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel
    public void setLluEffect(int effectName, int effectMode) {
        this.mLluController.setLluEffect(effectName, effectMode);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel
    public int getLluEffect(int effectName) {
        return this.mLluController.getLluEffect(effectName);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel
    public void setLluSpeedLimitCfg(int speed) {
        this.mLluController.setLluSpeedLimitCfg(speed);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel
    public int getLluSpeedLimitCfg() {
        return this.mLluController.getLluSpeedLimitCfg();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel
    public boolean isSayHiEnabled() {
        return this.mLluController.isSayHiEnabled();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel
    public void setSayHiEnable(boolean enable) {
        this.mLluController.setSayHiEnable(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel
    public int isLightDanceAvailable() {
        return this.mLluController.isLightDanceAvailable();
    }
}
