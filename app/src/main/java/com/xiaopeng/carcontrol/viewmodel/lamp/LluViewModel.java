package com.xiaopeng.carcontrol.viewmodel.lamp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;

/* loaded from: classes2.dex */
public class LluViewModel extends LluBaseViewModel {
    private static final String EFFECT_TYPE = "carcontrol";
    private static final String TAG = "LluViewModel";
    private final MutableLiveData<Boolean> mLluSw = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mLluAwakeSwitch = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mLluSleepSwitch = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mLluShowOffSwitch = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mLluChargeSwitch = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mLluPhotoSwitch = new MutableLiveData<>();
    private final MutableLiveData<LluUnlockEffect> mLluAwakeMode = new MutableLiveData<>();
    private final MutableLiveData<LluLockEffect> mLluSleepMode = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mLluLinkToShowEleSw = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mLluDanceStartPlayData = new MutableLiveData<>();
    private final MutableLiveData<LluPreviewState> mLluPreviewStateData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mLluSayHiSwData = new MutableLiveData<>();
    private final Runnable mIdleTask = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.lamp.-$$Lambda$LluViewModel$3DioJWsQQXSzJg5FmLsKy5JXYtc
        @Override // java.lang.Runnable
        public final void run() {
            LluViewModel.this.lambda$new$0$LluViewModel();
        }
    };
    private boolean mLluPreviewStartedSuccess = false;
    private final int mPreviewInactiveTime = BaseFeatureOption.getInstance().getSayHiPreviewInactiveTime();
    private final Runnable mPreviewTask = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.lamp.-$$Lambda$LluViewModel$kvdIS1p6HK95V6FQ0wVu0lRZvtU
        @Override // java.lang.Runnable
        public final void run() {
            LluViewModel.this.lambda$new$1$LluViewModel();
        }
    };
    private final MutableLiveData<Integer> mXuiFindCarModeData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mXuiAwakeWaitModeData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mXuiAcChargeModeData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mXuiDcChargeModeData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mXuiSleepModeData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mXuiShowoffModeData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mXuiTakePhotoModeData = new MutableLiveData<>();

    public /* synthetic */ void lambda$new$0$LluViewModel() {
        handleLluPreviewStateUpdate(LluPreviewState.Idle, null);
    }

    public /* synthetic */ void lambda$new$1$LluViewModel() {
        handleLluPreviewStateUpdate(LluPreviewState.Previewing, this.mLluPreviewStateData.getValue().getEffect());
    }

    public LluViewModel() {
        if (this.mLluController != null) {
            this.mLluController.registerCallback(this);
        }
        LluEffect runningLluEffect = getRunningLluEffect();
        handleLluPreviewStateUpdate(runningLluEffect == null ? LluPreviewState.Idle : LluPreviewState.Previewing, runningLluEffect);
        ThreadUtils.postDelayed(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.lamp.-$$Lambda$LluViewModel$j6RNT6WiIwGyAIyVmKblH6cyens
            @Override // java.lang.Runnable
            public final void run() {
                LluViewModel.this.lambda$new$2$LluViewModel();
            }
        }, 10000L);
    }

    public /* synthetic */ void lambda$new$2$LluViewModel() {
        LluEffect runningLluEffect = getRunningLluEffect();
        handleLluPreviewStateUpdate(runningLluEffect == null ? LluPreviewState.Idle : LluPreviewState.Previewing, runningLluEffect);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController.Callback
    public void onLluEnableChanged(boolean enabled) {
        this.mLluSw.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController.Callback
    public void onLluAwakeModeChanged(int mode) {
        try {
            this.mLluAwakeMode.postValue(LluUnlockEffect.fromLluValue(mode));
        } catch (Exception e) {
            LogUtils.d(TAG, e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController.Callback
    public void onLluSleepModeChanged(int mode) {
        try {
            this.mLluSleepMode.postValue(LluLockEffect.fromLluValue(mode));
        } catch (Exception e) {
            LogUtils.d(TAG, e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController.Callback
    public void onLluWakeWaitChanged(boolean enable) {
        this.mLluAwakeSwitch.postValue(Boolean.valueOf(enable));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController.Callback
    public void onLluShowOffChanged(boolean enable) {
        this.mLluShowOffSwitch.postValue(Boolean.valueOf(enable));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController.Callback
    public void onLluSleepChanged(boolean enable) {
        this.mLluSleepSwitch.postValue(Boolean.valueOf(enable));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController.Callback
    public void onLluChargingChanged(boolean enable) {
        this.mLluChargeSwitch.postValue(Boolean.valueOf(enable));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController.Callback
    public void onLluPhotoChanged(boolean enable) {
        this.mLluPhotoSwitch.postValue(Boolean.valueOf(enable));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController.Callback
    public void onLluLinkToShowEleChanged(boolean enable) {
        this.mLluLinkToShowEleSw.postValue(Boolean.valueOf(enable));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController.Callback
    public void onLluPreviewShowError(String effectName, int errCode) {
        LogUtils.d(TAG, "onLluPreviewShowError, effectName = " + effectName + "    errorCode = " + errCode);
        if (errCode == -4096) {
            this.mLluDanceStartPlayData.postValue(false);
        }
        if (LluEffect.fromLluCmd(effectName) == null) {
            return;
        }
        LluPreviewState value = this.mLluPreviewStateData.getValue();
        if (value == LluPreviewState.Starting || value == LluPreviewState.Switching) {
            if (effectName.equals(value.getEffect().toLluCmd())) {
                ThreadUtils.removeRunnable(this.mPreviewTask);
                handleLluPreviewStateUpdate(LluPreviewState.Stopping, null);
                ThreadUtils.removeRunnable(this.mIdleTask);
                ThreadUtils.postDelayed(0, this.mIdleTask, this.mPreviewInactiveTime);
                return;
            }
            return;
        }
        handleLluPreviewStateUpdate(LluPreviewState.Stopping, null);
        ThreadUtils.removeRunnable(this.mIdleTask);
        ThreadUtils.postDelayed(0, this.mIdleTask, this.mPreviewInactiveTime);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController.Callback
    public void onLluSayHiSwChanged(boolean enable) {
        this.mLluSayHiSwData.postValue(Boolean.valueOf(enable));
    }

    public LiveData<Boolean> getLluSyaHiSwData() {
        return this.mLluSayHiSwData;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController.Callback
    public void onLluPreviewShowFinish(String effectName, String effectType) {
        LogUtils.d(TAG, "onLluPreviewShowFinish, effectName = " + effectName + "    effectType = " + effectType);
        if ("dance".equals(effectType) || "lightanddance".equals(effectType)) {
            this.mLluDanceStartPlayData.postValue(false);
        }
        if ("carcontrol".equals(effectType)) {
            handleLluPreviewStateUpdate(LluPreviewState.Stopping, null);
            ThreadUtils.removeRunnable(this.mIdleTask);
            ThreadUtils.postDelayed(0, this.mIdleTask, this.mPreviewInactiveTime);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController.Callback
    public void onLluPreviewShowStop(String effectName, String effectType) {
        LogUtils.d(TAG, "onLluPreviewShowStop, effectName = " + effectName + "    effectType = " + effectType);
        if ("dance".equals(effectType) || "lightanddance".equals(effectType)) {
            this.mLluDanceStartPlayData.postValue(false);
        }
        if ("carcontrol".equals(effectType)) {
            LluPreviewState value = this.mLluPreviewStateData.getValue();
            if (value.getEffect() == null || (value.getEffect().toLluCmd().equals(effectName) && value != LluPreviewState.Switching)) {
                handleLluPreviewStateUpdate(LluPreviewState.Stopping, null);
                ThreadUtils.removeRunnable(this.mPreviewTask);
                ThreadUtils.removeRunnable(this.mIdleTask);
                ThreadUtils.postDelayed(0, this.mIdleTask, this.mPreviewInactiveTime);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController.Callback
    public void onLluPreviewShowStart(String effectName, String effectType) {
        LogUtils.d(TAG, "onLluPreviewShowStart, effectName = " + effectName + "    effectType = " + effectType);
        if ("dance".equals(effectType) || "lightanddance".equals(effectType)) {
            this.mLluDanceStartPlayData.postValue(true);
        }
        if ("carcontrol".equals(effectType)) {
            this.mLluPreviewStartedSuccess = true;
            LluEffect fromLluCmd = LluEffect.fromLluCmd(effectName);
            if (fromLluCmd != null && this.mLluPreviewStateData.getValue() != LluPreviewState.Starting && this.mLluPreviewStateData.getValue() != LluPreviewState.Switching) {
                handleLluPreviewStateUpdate(LluPreviewState.Previewing, fromLluCmd);
                ThreadUtils.removeRunnable(this.mIdleTask);
            }
            if (this.mLluPreviewStateData.getValue() == LluPreviewState.Switching) {
                handleLluPreviewStateUpdate(LluPreviewState.Starting, this.mLluPreviewStateData.getValue().getEffect());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopUnexpectedLluPreview() {
        if (this.mLluPreviewStateData.getValue() == null || this.mLluPreviewStateData.getValue() == LluPreviewState.Idle || this.mLluPreviewStateData.getValue() == LluPreviewState.Stopping) {
            return;
        }
        LogUtils.i(TAG, "hasUnexpectedLluPreview");
        handleLluPreviewStateUpdate(LluPreviewState.Stopping, null);
        ThreadUtils.removeRunnable(this.mPreviewTask);
        ThreadUtils.removeRunnable(this.mIdleTask);
        ThreadUtils.postDelayed(0, this.mIdleTask, this.mPreviewInactiveTime);
    }

    public LiveData<Boolean> getLluDanceStartPlayData() {
        return this.mLluDanceStartPlayData;
    }

    public LiveData<LluPreviewState> getLluPreviewStateData() {
        return this.mLluPreviewStateData;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.carcontrol.viewmodel.lamp.LluViewModel$2  reason: invalid class name */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluPreviewState;

        static {
            int[] iArr = new int[LluPreviewState.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluPreviewState = iArr;
            try {
                iArr[LluPreviewState.Idle.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluPreviewState[LluPreviewState.Stopping.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluPreviewState[LluPreviewState.Starting.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluPreviewState[LluPreviewState.Switching.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluPreviewState[LluPreviewState.Previewing.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    private void handleLluPreviewStateUpdate(LluPreviewState state, LluEffect effect) {
        int i = AnonymousClass2.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluPreviewState[state.ordinal()];
        if (i == 1 || i == 2) {
            state.setEffect(null);
        } else if (i == 3 || i == 4 || i == 5) {
            state.setEffect(effect);
        }
        this.mLluPreviewStateData.postValue(state);
    }

    public LiveData<Boolean> getLluSwData() {
        return this.mLluSw;
    }

    public void setLluEffectPreview(LluEffect effect) {
        if (effect != null) {
            this.mLluPreviewStartedSuccess = getRunningLluEffect() == effect;
            handleLluPreviewStateUpdate(getRunningLluEffect() == null ? LluPreviewState.Starting : LluPreviewState.Switching, effect);
            setLluEffectPreview(effect.toLluCmd());
            ThreadUtils.removeRunnable(this.mPreviewTask);
            ThreadUtils.postDelayed(0, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.lamp.LluViewModel.1
                @Override // java.lang.Runnable
                public void run() {
                    if (LluViewModel.this.mLluPreviewStartedSuccess) {
                        LluViewModel.this.mPreviewTask.run();
                    } else {
                        LluViewModel.this.stopUnexpectedLluPreview();
                    }
                }
            }, this.mPreviewInactiveTime);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.LluBaseViewModel, com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel
    public int setLluDancePreview(String filename) {
        int lluDancePreview = super.setLluDancePreview(filename);
        LogUtils.i(TAG, "setLluDancePreview result=" + lluDancePreview);
        return lluDancePreview;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.LluBaseViewModel, com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel
    public void stopLluEffectPreview() {
        this.mLluDanceStartPlayData.postValue(false);
        super.stopLluEffectPreview();
    }

    public LiveData<LluUnlockEffect> getLluAwakeModeData() {
        return this.mLluAwakeMode;
    }

    public void setLluAwakeMode(LluUnlockEffect effect) {
        if (effect != null) {
            setLluAwakeMode(effect.toLluCmd());
        }
    }

    public LluUnlockEffect getLluAwakeModeValue() {
        try {
            return LluUnlockEffect.fromLluValue(getLluAwakeMode());
        } catch (Exception e) {
            LogUtils.d(TAG, e.getMessage(), false);
            return LluUnlockEffect.EffectA;
        }
    }

    public LiveData<LluLockEffect> getLluSleepModeData() {
        return this.mLluSleepMode;
    }

    public void setLluSleepMode(LluLockEffect effect) {
        if (effect != null) {
            setLluSleepMode(effect.toLluCmd());
        }
    }

    public LluLockEffect getLluSleepModeValue() {
        try {
            return LluLockEffect.fromLluValue(getLluSleepMode());
        } catch (Exception e) {
            LogUtils.d(TAG, e.getMessage(), false);
            return LluLockEffect.EffectA;
        }
    }

    public void setLluEffect(LluEffect effect, int effectMode) {
        try {
            setLluEffect(effect.toXuiCmd(), effectMode);
        } catch (Exception e) {
            LogUtils.d(TAG, e.getMessage(), false);
        }
    }

    public int getLluEffect(LluEffect effect) {
        int lluEffect = getLluEffect(effect.toXuiCmd());
        LogUtils.i(TAG, effect.name() + " current effect mode=" + lluEffect);
        return lluEffect;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController.Callback
    public void onXuiLluEffectSetFinish(int effectName, int effectMode) {
        if (effectName == 1) {
            this.mXuiFindCarModeData.postValue(Integer.valueOf(effectMode));
        } else if (effectName == 2) {
            this.mXuiAwakeWaitModeData.postValue(Integer.valueOf(effectMode));
        } else if (effectName == 5) {
            this.mXuiSleepModeData.postValue(Integer.valueOf(effectMode));
        } else if (effectName == 6) {
            this.mXuiAcChargeModeData.postValue(Integer.valueOf(effectMode));
        } else if (effectName == 7) {
            this.mXuiDcChargeModeData.postValue(Integer.valueOf(effectMode));
        } else if (effectName == 9) {
            this.mXuiTakePhotoModeData.postValue(Integer.valueOf(effectMode));
        } else if (effectName == 10) {
            this.mXuiShowoffModeData.postValue(Integer.valueOf(effectMode));
        } else {
            LogUtils.d(TAG, "unknown effect :" + effectName, false);
        }
    }

    public LiveData<Integer> getXuiFindCarModeData() {
        return this.mXuiFindCarModeData;
    }

    public LiveData<Integer> getXuiAwakeWaitModeData() {
        return this.mXuiAwakeWaitModeData;
    }

    public LiveData<Integer> getXuiAcChargeModeData() {
        return this.mXuiAcChargeModeData;
    }

    public LiveData<Integer> getXuiDcChargeModeData() {
        return this.mXuiDcChargeModeData;
    }

    public LiveData<Integer> getXuiSleepModeData() {
        return this.mXuiSleepModeData;
    }

    public LiveData<Integer> getXuiShowoffModeData() {
        return this.mXuiShowoffModeData;
    }

    public LiveData<Integer> getXuiTakePhotoModeData() {
        return this.mXuiTakePhotoModeData;
    }

    public LiveData<Boolean> getLluAwakeData() {
        return this.mLluAwakeSwitch;
    }

    public LiveData<Boolean> getLluSleepData() {
        return this.mLluSleepSwitch;
    }

    public LiveData<Boolean> getLluShowOffData() {
        return this.mLluShowOffSwitch;
    }

    public LiveData<Boolean> getLluChargeData() {
        return this.mLluChargeSwitch;
    }

    public LiveData<Boolean> getLluPhotoData() {
        return this.mLluPhotoSwitch;
    }

    public LiveData<Boolean> getLluLinkToShowEleData() {
        return this.mLluLinkToShowEleSw;
    }
}
