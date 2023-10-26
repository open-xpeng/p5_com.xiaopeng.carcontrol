package com.xiaopeng.carcontrol.viewmodel.vcu;

import com.xiaopeng.carcontrol.model.FunctionModel;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;

/* loaded from: classes2.dex */
public class D2VcuViewModel extends VcuViewModel {
    private final String TAG = getClass().getSimpleName();

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.VcuBaseViewModel, com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public void setDriveModeByUser(DriveMode driveMode) {
        setDriveModeByUser(driveMode, true);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.VcuBaseViewModel, com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public void setDriveModeByUser(DriveMode driveMode, boolean storeEnable) {
        DriveMode driveModeValue = getDriveModeValue();
        final boolean z = false;
        LogUtils.d(this.TAG, "setDriveModeByUser, currentDriveMode: " + driveModeValue + ", driveMode: " + driveMode, false);
        if ((driveModeValue == DriveMode.Normal || driveModeValue == DriveMode.Sport || driveModeValue == DriveMode.Eco) && (driveMode == DriveMode.ComfortOff || driveMode == DriveMode.EcoPlusOff)) {
            return;
        }
        final DriveMode driveModeValueByUser = (driveMode == DriveMode.ComfortOff || driveMode == DriveMode.EcoPlusOff) ? getDriveModeValueByUser() : driveMode;
        if ((driveModeValue == DriveMode.Comfort || driveModeValue == DriveMode.EcoPlus) && driveMode != DriveMode.Comfort && driveMode != DriveMode.EcoPlus) {
            z = true;
        }
        final EnergyRecycleGrade energyRecycleByUser = z ? getEnergyRecycleByUser() : null;
        setDriveMode(driveMode.toVcuCmd(), true, storeEnable);
        FunctionModel.getInstance().setDriveModeChangedByUser(true);
        if (z) {
            this.mVcuController.setEnergyRecycleGrade(energyRecycleByUser.toVcuRecycleCmd(), storeEnable);
        }
        ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.vcu.-$$Lambda$D2VcuViewModel$EcVpmBEn99ny4QPuXqF8_1wSGiM
            @Override // java.lang.Runnable
            public final void run() {
                D2VcuViewModel.this.lambda$setDriveModeByUser$0$D2VcuViewModel(driveModeValueByUser, z, energyRecycleByUser);
            }
        }, 500L);
    }

    public /* synthetic */ void lambda$setDriveModeByUser$0$D2VcuViewModel(final DriveMode finalTargetDriveMode, final boolean restoreEnergyRecycle, final EnergyRecycleGrade targetGrade) {
        mockPostDriveMode(finalTargetDriveMode);
        if (restoreEnergyRecycle) {
            mockPostEnergyRecycleGrade(targetGrade);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.VcuViewModel
    public void setEnergyRecycleGrade(EnergyRecycleGrade grade) {
        setEnergyRecycleGrade(grade, true);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.VcuViewModel
    public void setEnergyRecycleGrade(final EnergyRecycleGrade grade, boolean storeEnable) {
        DriveMode driveModeValue = getDriveModeValue();
        final boolean z = false;
        LogUtils.d(this.TAG, "setEnergyRecycleGrade, currentDriveMode: " + driveModeValue + ", currentGrade: " + getEnergyRecycle() + ", grade: " + grade, false);
        if (driveModeValue == DriveMode.EcoPlus || driveModeValue == DriveMode.Comfort) {
            z = true;
        }
        final DriveMode driveModeValueByUser = z ? getDriveModeValueByUser() : null;
        if (z && driveModeValueByUser != null) {
            setDriveMode(driveModeValueByUser.toVcuCmd(), true, storeEnable);
        }
        super.setEnergyRecycleGrade(grade, storeEnable);
        ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.vcu.-$$Lambda$D2VcuViewModel$95AQO1gB96DoMKq9VbgFehTn1FQ
            @Override // java.lang.Runnable
            public final void run() {
                D2VcuViewModel.this.lambda$setEnergyRecycleGrade$1$D2VcuViewModel(z, driveModeValueByUser, grade);
            }
        }, 500L);
    }

    public /* synthetic */ void lambda$setEnergyRecycleGrade$1$D2VcuViewModel(final boolean restoreDriveMode, final DriveMode targetDriveMode, final EnergyRecycleGrade grade) {
        if (restoreDriveMode) {
            mockPostDriveMode(targetDriveMode);
        }
        mockPostEnergyRecycleGrade(grade);
    }
}
