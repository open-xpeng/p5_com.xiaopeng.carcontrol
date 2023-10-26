package com.xiaopeng.carcontrol.viewmodel.ciu;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.ICiuController;
import com.xiaopeng.carcontrol.carmanager.controller.IMcuController;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;

/* loaded from: classes2.dex */
public class CiuViewModel implements ICiuViewModel, ICiuController.Callback, IMcuController.Callback {
    private static final String TAG = "CiuViewModel";
    private final MutableLiveData<Boolean> mCiuStateData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mDmsSwData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mFaceIdSwData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mFatigueSwData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mDistractSwData = new MutableLiveData<>();
    private IMcuController mMcuController = (IMcuController) CarClientWrapper.getInstance().getController(CarClientWrapper.XP_MCU_SERVICE);
    private ICiuController mCiuController = (ICiuController) CarClientWrapper.getInstance().getController(CarClientWrapper.XP_CIU_SERVICE);

    public CiuViewModel() {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.ciu.-$$Lambda$CiuViewModel$p5GrztoIHivOrdYprOMU8yldWoU
            @Override // java.lang.Runnable
            public final void run() {
                CiuViewModel.this.lambda$new$0$CiuViewModel();
            }
        });
    }

    public /* synthetic */ void lambda$new$0$CiuViewModel() {
        this.mMcuController.registerCallback(this);
        if (this.mCiuController.getDmsSw()) {
            this.mDmsSwData.postValue(Boolean.valueOf(this.mCiuController.getDmsSw()));
            this.mFaceIdSwData.postValue(Boolean.valueOf(this.mCiuController.getFaceIdSw()));
            this.mFatigueSwData.postValue(Boolean.valueOf(this.mCiuController.getFatigueSw()));
            this.mDistractSwData.postValue(Boolean.valueOf(this.mCiuController.getDistractSw()));
        } else {
            this.mDmsSwData.postValue(false);
            this.mFaceIdSwData.postValue(false);
            this.mFatigueSwData.postValue(false);
            this.mDistractSwData.postValue(false);
        }
        this.mCiuController.registerCallback(this);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.ciu.ICiuViewModel
    public boolean isCiuExist() {
        return this.mMcuController.getCiuState();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.ciu.ICiuViewModel
    public boolean isDmsSwEnabled() {
        return this.mCiuController.getDmsSw();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.ciu.ICiuViewModel
    public void setDmsSwEnable(boolean enable) {
        LogUtils.d(TAG, "setDmsSwEnable with: enable = " + enable, false);
        this.mCiuController.setDmsSw(enable);
        this.mDmsSwData.setValue(Boolean.valueOf(enable));
        if (enable) {
            this.mFaceIdSwData.postValue(Boolean.valueOf(this.mCiuController.getFaceIdSw()));
            this.mFatigueSwData.postValue(Boolean.valueOf(this.mCiuController.getFatigueSw()));
            this.mDistractSwData.postValue(Boolean.valueOf(this.mCiuController.getDistractSw()));
        } else {
            this.mFaceIdSwData.postValue(false);
            this.mFatigueSwData.postValue(false);
            this.mDistractSwData.postValue(false);
            CiuSmartControl.getInstance().setSettingAutoBrightnessdEnable(false);
        }
        if (this.mCiuController.isCiuValid()) {
            LogUtils.d(TAG, "controlDmsSwitch " + enable, false);
            this.mCiuController.controlDmsSwitch(enable);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.ciu.ICiuViewModel
    public boolean isFaceIdSwEnabled() {
        return this.mCiuController.getFaceIdSw();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.ciu.ICiuViewModel
    public void setFaceIdSwEnable(boolean enable) {
        LogUtils.d(TAG, "setFaceIdSwEnable with: enable = " + enable, false);
        setFaceIdSwEnable(enable, false);
    }

    private void setFaceIdSwEnable(boolean enable, boolean onlyRefreshUi) {
        LogUtils.d(TAG, "setFaceIdSwEnable with: enable = " + enable, false);
        this.mCiuController.setFaceIdSw(enable);
        this.mFaceIdSwData.setValue(Boolean.valueOf(enable));
        if (enable && !this.mCiuController.getDmsSw()) {
            this.mCiuController.setDmsSw(true);
            this.mDmsSwData.setValue(true);
            this.mCiuController.setFatigueSw(false);
            this.mCiuController.setDistractSw(false);
            if (!this.mCiuController.isCiuValid() || onlyRefreshUi) {
                return;
            }
            this.mCiuController.setMultiDms(true, true, false, false);
        } else if (!this.mCiuController.isCiuValid() || onlyRefreshUi) {
        } else {
            LogUtils.d(TAG, "controlFaceIdSwitch", false);
            this.mCiuController.controlFaceIdSwitch(enable, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.ciu.ICiuViewModel
    public boolean isFatigueSwEnabled() {
        return this.mCiuController.getFatigueSw();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.ciu.ICiuViewModel
    public void setFatigueSwEnable(boolean enable) {
        LogUtils.d(TAG, "setFatigueSwEnable with: enable = " + enable, false);
        this.mCiuController.setFatigueSw(enable);
        this.mFatigueSwData.setValue(Boolean.valueOf(enable));
        if (enable && !this.mCiuController.getDmsSw()) {
            LogUtils.d(TAG, "setMultiDms", false);
            this.mCiuController.setDmsSw(true);
            this.mDmsSwData.setValue(true);
            this.mCiuController.setFaceIdSw(false);
            this.mCiuController.setDistractSw(false);
            if (this.mCiuController.isCiuValid()) {
                this.mCiuController.setMultiDms(true, false, true, false);
            }
        } else if (this.mCiuController.isCiuValid()) {
            LogUtils.d(TAG, "controlFatigueSwitch", false);
            this.mCiuController.controlFatigueSwitch(enable, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.ciu.ICiuViewModel
    public boolean isDistractSwEnabled() {
        return this.mCiuController.getDistractSw();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.ciu.ICiuViewModel
    public void setDistractSwEnable(boolean enable) {
        LogUtils.d(TAG, "setDistractSwEnable with: enable = " + enable, false);
        this.mCiuController.setDistractSw(enable);
        this.mDistractSwData.setValue(Boolean.valueOf(enable));
        if (enable && !this.mCiuController.getDmsSw()) {
            this.mCiuController.setDmsSw(true);
            this.mDmsSwData.setValue(true);
            this.mCiuController.setFaceIdSw(false);
            this.mCiuController.setFatigueSw(false);
            if (this.mCiuController.isCiuValid()) {
                this.mCiuController.setMultiDms(true, false, false, true);
            }
        } else if (this.mCiuController.isCiuValid()) {
            LogUtils.d(TAG, "controlDistractSwitch", false);
            this.mCiuController.controlDistractSwitch(enable, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ICiuController.Callback
    public void onDmsSwChanged(boolean enabled) {
        if (this.mCiuController.isCiuValid()) {
            this.mDmsSwData.postValue(Boolean.valueOf(enabled));
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ICiuController.Callback
    public void onFaceIdSwChanged(boolean enabled) {
        if (this.mCiuController.isCiuValid()) {
            this.mFaceIdSwData.postValue(Boolean.valueOf(enabled));
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ICiuController.Callback
    public void onFatigueSwChanged(boolean enabled) {
        if (this.mCiuController.isCiuValid()) {
            this.mFatigueSwData.postValue(Boolean.valueOf(enabled));
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ICiuController.Callback
    public void onDistractSwChanged(boolean enabled) {
        if (this.mCiuController.isCiuValid()) {
            this.mDistractSwData.postValue(Boolean.valueOf(enabled));
        }
    }

    public LiveData<Boolean> getCiuStateData() {
        return this.mCiuStateData;
    }

    public LiveData<Boolean> getDmsSwData() {
        return this.mDmsSwData;
    }

    public LiveData<Boolean> getFaceIdSwData() {
        return this.mFaceIdSwData;
    }

    public LiveData<Boolean> getFatigueSwData() {
        return this.mFatigueSwData;
    }

    public LiveData<Boolean> getDistractionSwData() {
        return this.mDistractSwData;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMcuController.Callback
    public void onCiuStateChanged(boolean isExisted) {
        this.mCiuStateData.postValue(Boolean.valueOf(isExisted));
    }
}
