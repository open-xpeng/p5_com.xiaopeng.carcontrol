package com.xiaopeng.carcontrol.viewmodel.light;

import android.provider.Settings;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.carmanager.controller.IAtlController;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.util.LogUtils;

/* loaded from: classes2.dex */
public class AtlViewModel extends AtlBaseViewModel {
    private static final String TAG = "AtlViewModel";
    private final MutableLiveData<Boolean> mAtlAutoBrightness;
    private final MutableLiveData<Integer> mAtlBrightness;
    private final MutableLiveData<Boolean> mAtlDualColor;
    private final MutableLiveData<AtlEffect> mAtlEffectType;
    private final MutableLiveData<AtlColor> mAtlFirstColor;
    private final MutableLiveData<AtlColor> mAtlSecondColor;
    private final MutableLiveData<Boolean> mAtlSpeakerSw;
    private final MutableLiveData<Boolean> mAtlSw;

    public int[] convertDoubleColor(int colorId) {
        int[] iArr = new int[2];
        switch (colorId) {
            case 0:
                iArr[0] = 1;
                iArr[1] = 6;
                return iArr;
            case 1:
                iArr[0] = 2;
                iArr[1] = 5;
                return iArr;
            case 2:
                iArr[0] = 5;
                iArr[1] = 11;
                return iArr;
            case 3:
                iArr[0] = 14;
                iArr[1] = 6;
                return iArr;
            case 4:
                iArr[0] = 15;
                iArr[1] = 11;
                return iArr;
            case 5:
                iArr[0] = 15;
                iArr[1] = 18;
                return iArr;
            case 6:
                iArr[0] = 17;
                iArr[1] = 20;
                return iArr;
            default:
                return null;
        }
    }

    public AtlViewModel() {
        MutableLiveData<Boolean> mutableLiveData = new MutableLiveData<>();
        this.mAtlSw = mutableLiveData;
        this.mAtlSpeakerSw = new MutableLiveData<>();
        this.mAtlAutoBrightness = new MutableLiveData<>();
        this.mAtlBrightness = new MutableLiveData<>();
        this.mAtlEffectType = new MutableLiveData<>();
        this.mAtlDualColor = new MutableLiveData<>();
        this.mAtlFirstColor = new MutableLiveData<>();
        this.mAtlSecondColor = new MutableLiveData<>();
        if (this.mAtlController != null) {
            this.mAtlController.registerCallback(this);
        }
        if (CarBaseConfig.getInstance().isSupportFullAtl()) {
            return;
        }
        int i = Settings.System.getInt(App.getInstance().getContentResolver(), IAtlController.ATL_SW_KEY, 1);
        LogUtils.i(TAG, "get atl sw by provider: isAmbientLightOpen=" + i);
        mutableLiveData.postValue(Boolean.valueOf(i == 1));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAtlController.Callback
    public void onAtlSwChanged(boolean enabled) {
        this.mAtlSw.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAtlController.Callback
    public void onAtlAutoBrightSwChanged(boolean enabled) {
        this.mAtlAutoBrightness.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAtlController.Callback
    public void onAtlManualBrightChanged(int brightness) {
        this.mAtlBrightness.postValue(Integer.valueOf(brightness));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAtlController.Callback
    public void onAtlEffectTypeChanged(String type) {
        this.mEffectType = type;
        this.mAtlEffectType.postValue(AtlEffect.fromAtlStatus(type));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAtlController.Callback
    public void onAtlDoubleColorEnableChanged(String effectType, boolean enable) {
        this.mAtlDualColor.postValue(Boolean.valueOf(enable));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAtlController.Callback
    public void onAtlMonoColorChanged(String effectType, int color) {
        this.mAtlFirstColor.postValue(AtlColor.fromAtlStatus(color));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAtlController.Callback
    public void onAtlDoubleColorChanged(String effectType, int first_color, int second_color) {
        this.mAtlSecondColor.postValue(AtlColor.fromAtlStatus(second_color));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAtlController.Callback
    public void onAtlSpeakerSwChanged(boolean enable) {
        this.mAtlSpeakerSw.postValue(Boolean.valueOf(enable));
    }

    public LiveData<Boolean> getAtlSwitchData() {
        return this.mAtlSw;
    }

    public LiveData<Boolean> getAtlSpeakerSwitchData() {
        return this.mAtlSpeakerSw;
    }

    public LiveData<Boolean> getAtlAutoBrightnessData() {
        return this.mAtlAutoBrightness;
    }

    public LiveData<Integer> getAtlBrightnessData() {
        return this.mAtlBrightness;
    }

    public AtlEffect getAtlEffect(AtlEffect defValue) {
        try {
            return AtlEffect.fromAtlStatus(getAtlEffect());
        } catch (Exception e) {
            LogUtils.d(TAG, e.getMessage(), false);
            return defValue;
        }
    }

    public void setAtlEffect(AtlEffect effect) {
        if (effect != null) {
            setAtlEffect(effect.toAtlCmd());
        }
    }

    public LiveData<AtlEffect> getAtlEffectTypeData() {
        return this.mAtlEffectType;
    }

    public LiveData<Boolean> getAtlDualColorData() {
        return this.mAtlDualColor;
    }

    public LiveData<AtlColor> getAtlFirstColorData() {
        return this.mAtlFirstColor;
    }

    public void setAtlSingleColor(AtlColor atlColor) {
        setAtlSingleColor(atlColor.toAtlCmd());
    }

    public LiveData<AtlColor> getAtlSecondColorData() {
        return this.mAtlSecondColor;
    }

    public void setAtlDualColor(AtlColor atlColor1, AtlColor atlColor2) {
        setAtlDualColor(atlColor1.toAtlCmd(), atlColor2.toAtlCmd());
    }

    public boolean isSupportDoubleThemeColor(AtlEffect atlEffect) {
        return isSupportDoubleThemeColor(atlEffect.toAtlCmd());
    }

    public int getDoubleColor() {
        int atlDualFirstColor = getAtlDualFirstColor();
        int atlDualSecondColor = getAtlDualSecondColor();
        LogUtils.d(TAG, "getDoubleColor:first=" + atlDualFirstColor + "second=" + atlDualSecondColor, false);
        if (atlDualFirstColor == 2 && atlDualSecondColor == 5) {
            return 1;
        }
        if (atlDualFirstColor == 5 && atlDualSecondColor == 11) {
            return 2;
        }
        if (atlDualFirstColor == 14 && atlDualSecondColor == 6) {
            return 3;
        }
        if (atlDualFirstColor == 15 && atlDualSecondColor == 11) {
            return 4;
        }
        if (atlDualFirstColor == 15 && atlDualSecondColor == 18) {
            return 5;
        }
        return (atlDualFirstColor == 17 && atlDualSecondColor == 20) ? 6 : 0;
    }
}
