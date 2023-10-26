package com.xiaopeng.carcontrol.viewmodel.avas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;

/* loaded from: classes2.dex */
public class AvasViewModel extends AvasBaseViewModel {
    private static final String TAG = "AvasViewModel";
    private final MutableLiveData<Boolean> mLowSpdSw = new MutableLiveData<>();
    private final MutableLiveData<AvasEffect> mLowSpdEffect = new MutableLiveData<>();
    private final MutableLiveData<Integer> mLowSpdVolume = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mAvasWakeWaitSw = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mAvasWakeWaitFullChargeSw = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mAvasSleepSw = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mAvasAcChargingSw = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mAvasDcChargingSw = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mAvasDisconnectChargingSw = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mAvasExternalSw = new MutableLiveData<>();
    private final MutableLiveData<Integer> mAvasExternalVolume = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mAvasTakePhotoSw = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mAvasSayHiSw = new MutableLiveData<>();
    private final MutableLiveData<Integer> mAvasBootEffect = new MutableLiveData<>();
    private final MutableLiveData<Integer> mFriendEffect = new MutableLiveData<>();

    public AvasViewModel() {
        this.mAvasController.registerCallback(this);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController.Callback
    public void onAvasLowSpdChanged(boolean enabled) {
        this.mLowSpdSw.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController.Callback
    public void onAvasLowSpdEffectChanged(int effect) {
        AvasEffect fromAvasType = AvasEffect.fromAvasType(effect);
        if (fromAvasType != null) {
            this.mLowSpdEffect.postValue(fromAvasType);
        } else {
            LogUtils.d(TAG, "onAvasLowSpdEffectChanged, error effect : " + effect, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController.Callback
    public void onAvasLowSpdVolumeChanged(int volume) {
        if (volume < 1 || volume > 100) {
            return;
        }
        this.mLowSpdVolume.postValue(Integer.valueOf(volume));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController.Callback
    public void onAvasWakeWaitChanged(boolean enable) {
        this.mAvasWakeWaitSw.postValue(Boolean.valueOf(enable));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController.Callback
    public void onAvasWakeWaitFullChargeChanged(boolean enable) {
        this.mAvasWakeWaitFullChargeSw.postValue(Boolean.valueOf(enable));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController.Callback
    public void onAvasSleepChanged(boolean enable) {
        this.mAvasSleepSw.postValue(Boolean.valueOf(enable));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController.Callback
    public void onAvasAcChargingChanged(boolean enable) {
        this.mAvasAcChargingSw.postValue(Boolean.valueOf(enable));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController.Callback
    public void onAvasDcChargingChanged(boolean enable) {
        this.mAvasDcChargingSw.postValue(Boolean.valueOf(enable));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController.Callback
    public void onAvasDisconnectChargingChanged(boolean enable) {
        this.mAvasDisconnectChargingSw.postValue(Boolean.valueOf(enable));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController.Callback
    public void onAvasExternalSwChanged(boolean enable) {
        this.mAvasExternalSw.postValue(Boolean.valueOf(enable));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController.Callback
    public void onAvasExternalVolumeChanged(int volume) {
        this.mAvasExternalVolume.postValue(Integer.valueOf(volume));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController.Callback
    public void onAvasTakePhotoChanged(boolean enable) {
        this.mAvasTakePhotoSw.postValue(Boolean.valueOf(enable));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController.Callback
    public void onAvasSayHiSwChanged(boolean enable) {
        this.mAvasSayHiSw.postValue(Boolean.valueOf(enable));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController.Callback
    public void onBootEffectChanged(int effect) {
        this.mAvasBootEffect.postValue(Integer.valueOf(effect));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController.Callback
    public void onFriendSoundTypeChanged(int type) {
        this.mFriendEffect.postValue(Integer.valueOf(type));
    }

    public LiveData<Integer> getFriendEffectData() {
        return this.mFriendEffect;
    }

    public LiveData<Boolean> getAvasSayHiSwData() {
        return this.mAvasSayHiSw;
    }

    public LiveData<Boolean> getLowSpdSwData() {
        return this.mLowSpdSw;
    }

    public LiveData<AvasEffect> getAvasLowSpdEffectData() {
        return this.mLowSpdEffect;
    }

    public void setAvasLowSpdEffect(AvasEffect effect) {
        if (effect != null) {
            setLowSpdEffect(effect.toAvasType());
            if (isLowSpdEnabled()) {
                return;
            }
            ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.avas.-$$Lambda$AvasViewModel$PWmnQxoXCjKRuFX05gaPaVhNrU0
                @Override // java.lang.Runnable
                public final void run() {
                    AvasViewModel.this.lambda$setAvasLowSpdEffect$0$AvasViewModel();
                }
            }, 100L);
        }
    }

    public /* synthetic */ void lambda$setAvasLowSpdEffect$0$AvasViewModel() {
        setLowSpdEnable(true);
    }

    public LiveData<Integer> getAvasLowSpdVolumeData() {
        return this.mLowSpdVolume;
    }

    public LiveData<Boolean> getAvasWakeWaitSwData() {
        return this.mAvasWakeWaitSw;
    }

    public LiveData<Boolean> getAvasWakeWaitFullChargeSwData() {
        return this.mAvasWakeWaitFullChargeSw;
    }

    public LiveData<Boolean> getAvasSleepSwData() {
        return this.mAvasSleepSw;
    }

    public LiveData<Boolean> getAvasAcChargingSwData() {
        return this.mAvasAcChargingSw;
    }

    public LiveData<Boolean> getAvasDcChargingSwData() {
        return this.mAvasDcChargingSw;
    }

    public LiveData<Boolean> getAvasDisconnectChargingSwData() {
        return this.mAvasDisconnectChargingSw;
    }

    public LiveData<Boolean> getAvasExternalSwData() {
        return this.mAvasExternalSw;
    }

    public LiveData<Integer> getAvasExternalVolumeData() {
        return this.mAvasExternalVolume;
    }

    public LiveData<Boolean> getAvasTakePhotoSw() {
        return this.mAvasTakePhotoSw;
    }

    public LiveData<Integer> getAvasBootEffectData() {
        return this.mAvasBootEffect;
    }

    public void setAvasFriendEffect(AvasEffect effect) {
        if (effect != null) {
            setFriendEffect(effect.toAvasType());
        }
    }

    public void setBootSoundEffectValue(BootSoundEffect effect) {
        if (effect != null) {
            setBootSoundEffect(effect.toXuiCmd());
        }
    }

    public BootSoundEffect getBootSoundEffectValue() {
        try {
            return BootSoundEffect.fromXuiCmd(getBootSoundEffect());
        } catch (Exception e) {
            LogUtils.d(TAG, e.getMessage(), false);
            return BootSoundEffect.EffectA;
        }
    }
}
