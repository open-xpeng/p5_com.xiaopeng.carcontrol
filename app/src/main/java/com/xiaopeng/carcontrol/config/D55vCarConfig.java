package com.xiaopeng.carcontrol.config;

/* loaded from: classes.dex */
public class D55vCarConfig extends D55CarConfig {
    @Override // com.xiaopeng.carcontrol.config.DxCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isFollowVehicleLostConfigUseNew() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.D55CarConfig, com.xiaopeng.carcontrol.config.DxCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportCNgp() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.D55CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDriveEnergyReset() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.D55CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportInductionLock() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportLluPreview() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.D55CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMaintenanceInfo() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.D55CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMemPark() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.D55CarConfig, com.xiaopeng.carcontrol.config.DxCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportNgp() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportOfflineUserPortfolioPage() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.D55CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportPM25Out() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportRearBeltWarningSwitch() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportRemoteCamera() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.D55CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportSrrMiss() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.D55CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportSwitchMedia() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.D55CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportTboxPowerSw() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.D55CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportVipSeat() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.D55CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportXPilotSafeExam() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.DxCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportXPilotTtsCfg() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportXkeyDisable() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAutoPowerOff() {
        return isSupportTboxPowerSw();
    }

    @Override // com.xiaopeng.carcontrol.config.D55CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportIsla() {
        return !isLowConfig();
    }

    @Override // com.xiaopeng.carcontrol.config.D55CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAlc() {
        return isSupportXpu();
    }

    @Override // com.xiaopeng.carcontrol.config.D55CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDrvSeatHeat() {
        return hasFeature(CarBaseConfig.PROPERTY_PACKAGE_1) || getCfcCode() >= 5;
    }

    @Override // com.xiaopeng.carcontrol.config.D55CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportPsnSeatHeat() {
        return hasFeature(CarBaseConfig.PROPERTY_PACKAGE_1) || getCfcCode() >= 5;
    }

    @Override // com.xiaopeng.carcontrol.config.DxCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportRearSeatHeat() {
        return hasFeature(CarBaseConfig.PROPERTY_PACKAGE_1) || getCfcCode() >= 5;
    }
}
