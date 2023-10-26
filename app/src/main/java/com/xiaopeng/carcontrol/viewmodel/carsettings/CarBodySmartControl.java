package com.xiaopeng.carcontrol.viewmodel.carsettings;

import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;

/* loaded from: classes2.dex */
public class CarBodySmartControl {
    private static final String TAG = "CarBodySmartControl";
    private ICarBodyViewModel mCarBodyVm = (ICarBodyViewModel) ViewModelManager.getInstance().getViewModelImpl(ICarBodyViewModel.class);

    /* loaded from: classes2.dex */
    private static class SingleHolder {
        private static CarBodySmartControl sInstance = new CarBodySmartControl();

        private SingleHolder() {
        }
    }

    public static CarBodySmartControl getInstance() {
        return SingleHolder.sInstance;
    }

    CarBodySmartControl() {
    }

    public void setUnlockResponse(int unlockResponse) {
        ICarBodyViewModel iCarBodyViewModel = this.mCarBodyVm;
        if (iCarBodyViewModel != null) {
            iCarBodyViewModel.setUnlockResponse(unlockResponse);
        }
    }

    public void controlChargePort(boolean isLeft, boolean unlock) {
        if (this.mCarBodyVm.isChargePortEnable(isLeft, true)) {
            this.mCarBodyVm.setChargePortUnlock(isLeft ? 1 : 2, unlock);
        } else if (this.mCarBodyVm.isChargePortResetable(isLeft)) {
            this.mCarBodyVm.resetChargePort(isLeft);
        }
    }

    public void resetChargePort(boolean isLeft) {
        this.mCarBodyVm.resetChargePort(isLeft);
    }
}
