package com.xiaopeng.carcontrol.config;

/* loaded from: classes.dex */
public class E28vCarConfig extends E28CarConfig {
    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isChargePortSignalErr() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.E28CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isFollowVehicleLostConfigUseNew() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSingleChargePort() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.E28CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSopStage() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.E28CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDriveEnergyReset() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.E28CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDsmCamera() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.E28CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportIhb() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.E28CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportInductionLock() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportLluPreview() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.E28CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMemPark() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.E28CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportNgp() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportOfflineUserPortfolioPage() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.E28CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportOldIsla() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.E28CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportPM25Out() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.E28CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportPollingLock() {
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

    @Override // com.xiaopeng.carcontrol.config.E28CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportSimpleSas() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.E28CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportSwitchMedia() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportTboxPowerSw() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportXPilotSafeExam() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.E28CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportXPilotTtsCfg() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportXpilotLccBindLdw() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.E28CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportXpuNedc() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.E28CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isVpmHwMiss() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.E28CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isVpmNotReady() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.E28CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isVpmSwReady() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAutoPowerOff() {
        return isSupportTboxPowerSw();
    }

    @Override // com.xiaopeng.carcontrol.config.E28CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportRearSeatHeat() {
        return hasFeature(CarBaseConfig.PROPERTY_PACKAGE_1);
    }
}
