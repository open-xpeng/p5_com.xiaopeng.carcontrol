package com.xiaopeng.carcontrol.viewmodel.hvac;

import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.model.FunctionModel;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrolmodule.R;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class XpHvacSmartControl extends D2HvacSmartControl {
    private static final String TAG = "XpHvacSmartControl";

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.D2HvacSmartControl, com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    void hvacDeodorantStatusCheck() {
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.D2HvacSmartControl, com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    void hvacRapidCoolingStatusCheck() {
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    public void onAcHeatNatureModeChange(int mode) {
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.D2HvacSmartControl, com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    public void onAirAualityPurgeChanged(boolean enable) {
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.D2HvacSmartControl, com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    public void onAqsModeChange(boolean enable) {
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    public void onCirculationModeChanged(int mode) {
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.D2HvacSmartControl, com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    public void onFrontDefrostChange(boolean enable) {
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    public void onHvacAutoModeChange(Boolean enabled) {
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    public void onHvacDrvTempChange(float temp) {
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    public void onHvacEconModeChange(int status) {
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    public void onHvacPsnTempChange(float temp) {
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.D2HvacSmartControl, com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    public void onWindBlowModeChanged(int mode) {
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.D2HvacSmartControl, com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    public void onWindSpeedLevelChange(int level) {
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.D2HvacSmartControl, com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    protected void resetAirPurge() {
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    public void resetSmartMode() {
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    public void stopHvacSmartMode() {
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.D2HvacSmartControl, com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    public void onHvacRapidCoolingChanged(boolean enable) {
        boolean isLocalIgOn = isLocalIgOn();
        if (enable) {
            long currentTimeMillis = (System.currentTimeMillis() - FunctionModel.getInstance().getHvacRapidCoolingTime()) / 1000;
            LogUtils.d(TAG, "onHvacRapidCoolingChanged timeDiff:" + currentTimeMillis, false);
            int i = 180;
            if (currentTimeMillis < 180 && currentTimeMillis > 10) {
                i = 180 - ((int) currentTimeMillis);
            }
            if (this.mCarBaseConfig.isSupportDrvSeatVent()) {
                this.mFunctionModel.setHvacSmartDrvSeatVent(this.mHvacBaseVm.getSeatVentLevel());
                this.mHvacBaseVm.setSeatVentLevel(SeatVentLevel.Level3.ordinal(), isLocalIgOn);
            }
            if (this.mCarBaseConfig.isSupportPsnSeatVent()) {
                this.mFunctionModel.setHvacSmartPsnSeatVent(this.mHvacBaseVm.getPsnSeatVentLevel());
                this.mHvacBaseVm.setPsnSeatVentLevel(SeatVentLevel.Level3.ordinal());
            }
            if (this.mCarBaseConfig.isSupportDrvSeatHeat()) {
                this.mHvacBaseVm.setSeatHeatLevel(SeatHeatLevel.Off.ordinal(), isLocalIgOn);
            }
            if (this.mCarBaseConfig.isSupportPsnSeatHeat()) {
                this.mHvacBaseVm.setPsnSeatHeatLevel(SeatHeatLevel.Off.ordinal());
            }
            if (this.mCarBaseConfig.isSupportRearSeatHeat()) {
                this.mHvacBaseVm.setRLSeatHeatLevel(SeatHeatLevel.Off.ordinal());
                this.mHvacBaseVm.setRRSeatHeatLevel(SeatHeatLevel.Off.ordinal());
            }
            if (BaseFeatureOption.getInstance().isSupportSmartModeCountdown()) {
                startHvacRapidCoolingCountDownDelay(i);
                return;
            } else {
                stopHvacDeodorantCountdownDelay();
                return;
            }
        }
        if (this.mCarBaseConfig.isSupportDrvSeatVent()) {
            this.mHvacBaseVm.setSeatVentLevel(this.mFunctionModel.getHvacSmartDrvSeatVent(), isLocalIgOn);
        }
        if (this.mCarBaseConfig.isSupportPsnSeatVent()) {
            this.mHvacBaseVm.setPsnSeatVentLevel(this.mFunctionModel.getHvacSmartPsnSeatVent());
        }
        stopHvacRapidCoolingCountDownDelay();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.D2HvacSmartControl, com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    public void onHvacDeodorantChanged(boolean enable) {
        if (enable) {
            long currentTimeMillis = (System.currentTimeMillis() - FunctionModel.getInstance().getHvacDeodorantTime()) / 1000;
            LogUtils.d(TAG, "onHvacDeodorantChanged timeDiff:" + currentTimeMillis, false);
            int i = 180;
            if (currentTimeMillis < 180 && currentTimeMillis > 10) {
                i = 180 - ((int) currentTimeMillis);
            }
            startHvacDeodorantCountdownDelay(i);
            controlDeodorantOpen();
            return;
        }
        stopHvacDeodorantCountdownDelay();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    public void onHvacRapidHeatChanged(boolean enable) {
        boolean isLocalIgOn = isLocalIgOn();
        if (enable) {
            long currentTimeMillis = (System.currentTimeMillis() - FunctionModel.getInstance().getHvacRapidHeatTime()) / 1000;
            LogUtils.d(TAG, "onHvacRapidHeatChanged timeDiff:" + currentTimeMillis, false);
            int i = 180;
            if (currentTimeMillis < 180 && currentTimeMillis > 10) {
                i = 180 - ((int) currentTimeMillis);
            }
            if (this.mCarBaseConfig.isSupportDrvSeatVent()) {
                this.mFunctionModel.setHvacSmartDrvSeatVent(this.mHvacBaseVm.getSeatVentLevel());
                this.mHvacBaseVm.setSeatVentLevel(SeatVentLevel.Off.ordinal(), isLocalIgOn);
            }
            if (this.mCarBaseConfig.isSupportPsnSeatVent()) {
                this.mFunctionModel.setHvacSmartPsnSeatVent(this.mHvacBaseVm.getPsnSeatVentLevel());
                this.mHvacBaseVm.setPsnSeatVentLevel(SeatVentLevel.Off.ordinal());
            }
            if (this.mCarBaseConfig.isSupportDrvSeatHeat()) {
                this.mFunctionModel.setHvacSmartDrvSeatHeat(this.mHvacBaseVm.getSeatHeatLevel());
                this.mHvacBaseVm.setSeatHeatLevel(SeatHeatLevel.Level3.ordinal(), isLocalIgOn);
            }
            if (this.mCarBaseConfig.isSupportPsnSeatHeat()) {
                this.mFunctionModel.setHvacSmartPsnSeatHeat(this.mHvacBaseVm.getPsnSeatHeatLevel());
                this.mHvacBaseVm.setPsnSeatHeatLevel(SeatHeatLevel.Level3.ordinal());
            }
            if (this.mCarBaseConfig.isSupportRearSeatHeat()) {
                this.mFunctionModel.setHvacSmartRlSeatHeat(this.mHvacBaseVm.getRLSeatHeatLevel());
                this.mFunctionModel.setHvacSmartRrSeatHeat(this.mHvacBaseVm.getRRSeatHeatLevel());
                this.mHvacBaseVm.setRLSeatHeatLevel(SeatHeatLevel.Level3.ordinal());
                this.mHvacBaseVm.setRRSeatHeatLevel(SeatHeatLevel.Level3.ordinal());
            }
            if (this.mCarBaseConfig.isSupportSteerHeat()) {
                this.mFunctionModel.setHvacSmartSteerHeat(this.mHvacBaseVm.getSteerHeatLevel());
                this.mHvacBaseVm.setSteerHeatLevel(3, isLocalIgOn);
            }
            if (BaseFeatureOption.getInstance().isSupportSmartModeCountdown()) {
                startHvacRapidHeatCountdownDelay(i);
                return;
            } else {
                stopHvacDeodorantCountdownDelay();
                return;
            }
        }
        stopHvacRapidHeatCountdownDelay();
        if (this.mCarBaseConfig.isSupportDrvSeatHeat()) {
            this.mHvacBaseVm.setSeatHeatLevel(this.mFunctionModel.getHvacSmartDrvSeatHeat(), isLocalIgOn);
        }
        if (this.mCarBaseConfig.isSupportPsnSeatHeat()) {
            this.mHvacBaseVm.setPsnSeatHeatLevel(this.mFunctionModel.getHvacSmartPsnSeatHeat());
        }
        if (this.mCarBaseConfig.isSupportRearSeatHeat()) {
            this.mHvacBaseVm.setRLSeatHeatLevel(this.mFunctionModel.getHvacSmartRlSeatHeat());
            this.mHvacBaseVm.setRRSeatHeatLevel(this.mFunctionModel.getHvacSmartRrSeatHeat());
        }
        if (this.mCarBaseConfig.isSupportSteerHeat()) {
            this.mHvacBaseVm.setSteerHeatLevel(this.mFunctionModel.getHvacSmartSteerHeat(), isLocalIgOn);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.D2HvacSmartControl, com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    void controlDeodorantOpen() {
        if (this.mMcuController != null && this.mMcuController.getIgStatusFromMcu() != 1) {
            LogUtils.d(TAG, "controlDeodorantOpen is not local ig on", false);
        } else if (!this.mWindowBaseVm.isWindowLockActive()) {
            this.mWindowBaseVm.controlWindowVent();
        } else {
            LogUtils.d(TAG, "WindowLockActive");
            NotificationHelper.getInstance().showToast(R.string.win_lock_is_active, true, "hvac");
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    public void initHvacSmartMode() {
        if (this.mHvacBaseVm.isHvacRapidCoolingEnable()) {
            onHvacRapidCoolingChanged(this.mHvacBaseVm.isHvacRapidCoolingEnable());
        }
        if (this.mHvacBaseVm.isHvacDeodorantEnable()) {
            onHvacDeodorantChanged(this.mHvacBaseVm.isHvacDeodorantEnable());
        }
        if (this.mHvacBaseVm.isHvacRapidHeatEnable()) {
            onHvacRapidHeatChanged(this.mHvacBaseVm.isHvacRapidHeatEnable());
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    public void executeHvacDrvTempSync() {
        if (BaseFeatureOption.getInstance().isHvacDataMemoryFromRDCU()) {
            return;
        }
        super.executeHvacDrvTempSync();
    }
}
