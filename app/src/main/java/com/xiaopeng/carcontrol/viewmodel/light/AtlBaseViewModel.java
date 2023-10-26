package com.xiaopeng.carcontrol.viewmodel.light;

import android.text.TextUtils;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.IAtlController;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.model.SharedPreferenceUtil;
import com.xiaopeng.carcontrol.util.LogUtils;

/* loaded from: classes2.dex */
public abstract class AtlBaseViewModel implements IAtlViewModel, IAtlController.Callback {
    private static final int MEDITATION_SINGLE_COLOR = 7;
    private static final String TAG = "AtlBaseViewModel";
    private int mAtlBrightness;
    private int mAtlDoubleColorFirst;
    private int mAtlDoubleColorSecond;
    private boolean mAtlEnable;
    private int mAtlSingleColor;
    private boolean mAtlThemeColorMode;
    private String mAtlffectType;
    String mEffectType;
    private boolean mInMeditation = false;
    IAtlController mAtlController = (IAtlController) CarClientWrapper.getInstance().getController(CarClientWrapper.XP_ATL_SERVICE);

    @Override // com.xiaopeng.carcontrol.viewmodel.light.IAtlViewModel
    public boolean isAtlEnabled() {
        return this.mAtlController.isAtlSwEnabled();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.light.IAtlViewModel
    public void setAtlSwitch(boolean enable) {
        if (CarBaseConfig.getInstance().isSupportAtl()) {
            if (this.mInMeditation) {
                SharedPreferenceUtil.setMeditationAtlSw(enable);
                LogUtils.d(TAG, "setMeditationAtlSw = " + enable, false);
            }
            this.mAtlController.setAtlSwEnable(enable);
            return;
        }
        LogUtils.d(TAG, "ATL feature not support", false);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.light.IAtlViewModel
    public boolean isAtlSpeakerSwEnabled() {
        return this.mAtlController.isAtlSpeakerSwEnabled();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.light.IAtlViewModel
    public void setAtlSpeakerSwEnabled(boolean enable) {
        this.mAtlController.setAtlSpeakerSwEnable(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.light.IAtlViewModel
    public boolean isAtlAutoBrightness() {
        return this.mAtlController.isAtlAutoBrightEnabled();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.light.IAtlViewModel
    public void setAtlAutoBrightness(boolean enable) {
        this.mAtlController.setAtlAutoBrightEnable(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.light.IAtlViewModel
    public int getAtlBrightnessValue() {
        return this.mAtlController.getAtlBrightness();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.light.IAtlViewModel
    public void setAtlBrightnessValue(int brightness) {
        if (CarBaseConfig.getInstance().isSupportAtl()) {
            if (!this.mAtlController.isAtlSwEnabled()) {
                this.mAtlController.setAtlSwEnable(true);
            }
            int i = brightness >= 10 ? brightness : 10;
            if (brightness > 100) {
                i = 100;
            }
            this.mAtlController.setAtlBrightness(i, true);
            return;
        }
        LogUtils.d(TAG, "setAtlBrightnessValue ATL feature is not support", false);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.light.IAtlViewModel
    public String getAtlEffect() {
        if (CarBaseConfig.getInstance().isSupportAtl() && TextUtils.isEmpty(this.mEffectType)) {
            this.mEffectType = this.mAtlController.getAtlEffect();
        }
        return this.mEffectType;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.light.IAtlViewModel
    public void setAtlEffect(String effect) {
        if (CarBaseConfig.getInstance().isSupportAtl()) {
            this.mAtlController.setAtlEffect(effect, true);
            this.mEffectType = effect;
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.light.IAtlViewModel
    public boolean getAtlThemeColorMode() {
        return this.mAtlController.isAtlDualColor(getAtlEffect());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.light.IAtlViewModel
    public void setAtlThemeColorMode(boolean isDualColor) {
        this.mAtlController.setAtlDualColor(getAtlEffect(), isDualColor, true);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.light.IAtlViewModel
    public int getAtlSingleColor() {
        return this.mAtlController.getAtlSingleColor(getAtlEffect());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.light.IAtlViewModel
    public void setAtlSingleColor(int color) {
        setAtlSingleColor(color, true);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.light.IAtlViewModel
    public void setAtlSingleColor(int color, boolean force) {
        setAtlSingleColor(color, force, true);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.light.IAtlViewModel
    public void setAtlSingleColor(int color, boolean force, boolean needSave) {
        if (!CarBaseConfig.getInstance().isSupportAtl()) {
            LogUtils.d(TAG, "setAtlSingleColor ATL feature not support", false);
        } else if (color < 1 || color > 20) {
        } else {
            this.mAtlController.setAtlSingleColor(getAtlEffect(), color, force, needSave);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.light.IAtlViewModel
    public int getAtlDualFirstColor() {
        return this.mAtlController.getAtlDualFirstColor(getAtlEffect());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.light.IAtlViewModel
    public int getAtlDualSecondColor() {
        return this.mAtlController.getAtlDualSecondColor(getAtlEffect());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.light.IAtlViewModel
    public void setAtlDualColor(int color1, int color2) {
        this.mAtlController.setAtlDualColor(getAtlEffect(), color1, color2, true);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.light.IAtlViewModel
    public void setAtlDualColor(int color1, int color2, boolean needSave) {
        this.mAtlController.setAtlDualColor(getAtlEffect(), color1, color2, true, needSave);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.light.IAtlViewModel
    public boolean isSupportDoubleThemeColor(String effectType) {
        return this.mAtlController.isSupportDoubleThemeColor(effectType);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.light.IAtlViewModel
    public void enterMeditationState() {
        String str = TAG;
        LogUtils.i(str, "enterMeditationState, mAtlEnable: " + this.mAtlEnable, false);
        if (this.mInMeditation) {
            return;
        }
        this.mAtlEnable = this.mAtlController.isAtlSwEnabled();
        String atlEffect = this.mAtlController.getAtlEffect();
        this.mAtlffectType = atlEffect;
        this.mAtlThemeColorMode = this.mAtlController.isAtlDualColor(atlEffect);
        LogUtils.i(str, "mAtlThemeColorMode, enterMeditation: " + this.mAtlThemeColorMode, false);
        this.mAtlSingleColor = this.mAtlController.getAtlSingleColor(this.mAtlffectType);
        this.mAtlDoubleColorFirst = this.mAtlController.getAtlDualFirstColor(this.mAtlffectType);
        this.mAtlDoubleColorSecond = this.mAtlController.getAtlDualSecondColor(this.mAtlffectType);
        this.mAtlBrightness = this.mAtlController.getAtlBrightness();
        this.mAtlController.setAtlEffect(IAtlController.ATL_EFFECT_BREATH, false, false);
        this.mAtlController.setAtlDualColor(IAtlController.ATL_EFFECT_BREATH, false, true);
        this.mAtlController.setAtlSingleColor(IAtlController.ATL_EFFECT_BREATH, 7, false, false);
        this.mAtlController.setAtlBrightness(10, false);
        this.mAtlController.setAtlSwEnable(SharedPreferenceUtil.getMeditationAtlSw());
        LogUtils.i(str, "mAtlThemeColorMode, onMeditation: " + this.mAtlController.isAtlDualColor(IAtlController.ATL_EFFECT_BREATH), false);
        this.mInMeditation = true;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.light.IAtlViewModel
    public void exitMeditationState() {
        String str = TAG;
        LogUtils.i(str, "exitMeditationState, mAtlEnable: " + this.mAtlEnable, false);
        if (this.mInMeditation) {
            this.mAtlController.setAtlEffect(this.mAtlffectType, false);
            this.mAtlController.setAtlDualColor(this.mAtlffectType, this.mAtlThemeColorMode, true);
            LogUtils.i(str, "mAtlThemeColorMode, beforeExitMeditation: " + this.mAtlThemeColorMode, false);
            if (this.mAtlThemeColorMode) {
                this.mAtlController.setAtlDualColor(this.mAtlffectType, this.mAtlDoubleColorFirst, this.mAtlDoubleColorSecond, true, true);
            } else {
                this.mAtlController.setAtlSingleColor(this.mAtlffectType, this.mAtlSingleColor, true, true);
            }
            LogUtils.i(str, "mAtlThemeColorMode, onExitMeditation: " + this.mAtlThemeColorMode, false);
            this.mAtlController.setAtlBrightness(this.mAtlBrightness, false);
            this.mAtlController.setAtlSwEnable(this.mAtlEnable);
            this.mInMeditation = false;
        }
    }
}
