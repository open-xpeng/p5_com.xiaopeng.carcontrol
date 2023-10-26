package com.xiaopeng.carcontrol.config;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class D22vCarConfig extends D22CarConfig {
    @Override // com.xiaopeng.carcontrol.config.DxCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isFollowVehicleLostConfigUseNew() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.D21CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAlc() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAutoParkForXKey() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.D21CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDriveEnergyReset() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.D22CarConfig, com.xiaopeng.carcontrol.config.DxCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDsmCamera() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.D21CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportInductionLock() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportLluPreview() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.D21CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMaintenanceInfo() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMcuPowerSw() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportOfflineUserPortfolioPage() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.D22CarConfig, com.xiaopeng.carcontrol.config.D21CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportPM25Out() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.DxCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportPollingLock() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.DxCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportPollingOpenCfg() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportRearBeltWarningSwitch() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportRemoteCamera() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.D21CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportShowAutoPark() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.D21CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportSwitchMedia() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.D22CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
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
        return isSupportMcuPowerSw();
    }

    @Override // com.xiaopeng.carcontrol.config.D22CarConfig, com.xiaopeng.carcontrol.config.D21CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAutoPark() {
        int cfcCode = getCfcCode();
        return cfcCode == 3 || cfcCode == 2;
    }

    @Override // com.xiaopeng.carcontrol.config.D22CarConfig, com.xiaopeng.carcontrol.config.D21CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportCwc() {
        return hasFeature(CarBaseConfig.PROPERTY_CWC);
    }

    @Override // com.xiaopeng.carcontrol.config.D21CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDrvSeatVent() {
        return isHighConfig();
    }

    @Override // com.xiaopeng.carcontrol.config.D21CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDrvSeatHeat() {
        return !isLowConfig();
    }

    @Override // com.xiaopeng.carcontrol.config.D21CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportPsnSeatHeat() {
        return !isLowConfig();
    }

    @Override // com.xiaopeng.carcontrol.config.D22CarConfig, com.xiaopeng.carcontrol.config.DxCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportRearSeatHeat() {
        return !isLowConfig();
    }
}
