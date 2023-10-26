package com.xiaopeng.carcontrol.carmanager.impl.oldarch;

import android.car.Car;
import com.xiaopeng.carcontrol.carmanager.impl.VcuController;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.viewmodel.service.ShowCarControl;

/* loaded from: classes.dex */
public class VcuOldController extends VcuController {
    @Override // com.xiaopeng.carcontrol.carmanager.impl.VcuController, com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public boolean isAutoDriveModeEnabled() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.VcuController, com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public void setAutoDriveMode(boolean enable) {
    }

    public VcuOldController(Car carClient) {
        super(carClient);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.VcuController, com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public void setDriveMode(int driveMode) {
        setDriveMode(driveMode, true);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.VcuController, com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public void setDriveMode(final int driveMode, boolean storeEnable) {
        if (ShowCarControl.getInstance().isShowCarDriveDisable()) {
            LogUtils.i("VcuController", "current is vcu exhibition mode", false);
            return;
        }
        super.setDriveMode(driveMode, storeEnable);
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.oldarch.-$$Lambda$VcuOldController$dnZaRMDVlS5_WrI9qK7czKBB718
            @Override // java.lang.Runnable
            public final void run() {
                VcuOldController.this.lambda$setDriveMode$0$VcuOldController(driveMode);
            }
        });
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.VcuController, com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public int getDriveMode() {
        if (this.mDataSync.isAntiSicknessEnabled()) {
            return 7;
        }
        if (this.mDataSync.isXpedal()) {
            return 5;
        }
        return this.mDataSync.getDriveMode();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.VcuController, com.xiaopeng.carcontrol.carmanager.controller.IVcuController
    public int getEnergyRecycleGrade() {
        return this.mDataSync.getRecycleGrade();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.impl.VcuController
    /* renamed from: handleDriveModeUpdate */
    public void lambda$setDriveMode$0$VcuOldController(int mode) {
        if (mode == 0 || mode == 1 || mode == 2 || mode == 5 || mode == 7) {
            super.handleDriveModeUpdate(mode);
        } else {
            LogUtils.w("VcuController", "Unknown drive mode = " + mode, false);
        }
    }
}
