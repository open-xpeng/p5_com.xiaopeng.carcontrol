package com.xiaopeng.carcontrol.viewmodel.carsettings;

import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.ICiuController;
import com.xiaopeng.carcontrol.carmanager.controller.IMcuController;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class D2CarBodyViewModel extends CarBodyViewModel {
    private static final String TAG = "D2CarBodyViewModel";
    ICiuController mCiuController;
    final IMcuController.Callback mMcuCallback;
    IMcuController mMcuController;

    public D2CarBodyViewModel() {
        IMcuController.Callback callback = new IMcuController.Callback() { // from class: com.xiaopeng.carcontrol.viewmodel.carsettings.D2CarBodyViewModel.1
            @Override // com.xiaopeng.carcontrol.carmanager.controller.IMcuController.Callback
            public void onAutoPowerOffConfig(boolean enable) {
                D2CarBodyViewModel.this.handleAutoPowerOffConfigChanged(enable);
            }
        };
        this.mMcuCallback = callback;
        ICiuController iCiuController = (ICiuController) CarClientWrapper.getInstance().getController(CarClientWrapper.XP_CIU_SERVICE);
        this.mCiuController = iCiuController;
        if (iCiuController != null) {
            iCiuController.registerCallback(this.mCiuCallBack);
        }
        IMcuController iMcuController = (IMcuController) CarClientWrapper.getInstance().getController(CarClientWrapper.XP_MCU_SERVICE);
        this.mMcuController = iMcuController;
        iMcuController.registerCallback(callback);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyViewModel, com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public void setWiperInterval(WiperInterval interval) {
        if (interval != null) {
            if (this.mMcuController.getCiuState()) {
                setCiuRainWiperInterval(interval.toCiuCmd());
            } else {
                setWiperInterval(interval.toBcmCmd());
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyViewModel, com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public WiperInterval getWiperIntervalValue() {
        if (this.mWiperInterval.getValue() != null) {
            return this.mWiperInterval.getValue();
        }
        WiperInterval wiperInterval = null;
        try {
            if (this.mMcuController.getCiuState()) {
                wiperInterval = WiperInterval.fromCiuState(getCiuRainWiperInterval());
            } else {
                wiperInterval = WiperInterval.fromBcmState(getWiperInterval());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wiperInterval;
    }

    private void setCiuRainWiperInterval(int interval) {
        this.mCiuController.setCiuWiperLevel(interval);
    }

    private int getCiuRainWiperInterval() {
        return this.mCiuController.getCiuWiperLevel();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyBaseViewModel, com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public void setCiuRainSwEnable(boolean enable) {
        this.mCiuController.setCiuRainEnable(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyBaseViewModel, com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public boolean isCiuRainSwEnable() {
        return this.mCiuController.isCiuRainEnable();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyViewModel, com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public void setAutoPowerOffConfig(boolean status) {
        this.mMcuController.setAutoPowerOffSwitch(status);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyViewModel, com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public boolean getAutoPowerOffConfig() {
        return this.mMcuController.isAutoPowerOffEnable();
    }
}
