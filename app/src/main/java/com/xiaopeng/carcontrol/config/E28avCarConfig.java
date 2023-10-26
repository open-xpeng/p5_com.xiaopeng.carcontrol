package com.xiaopeng.carcontrol.config;

/* loaded from: classes.dex */
public class E28avCarConfig extends E28aCarConfig {
    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isChargePortSignalErr() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.E28aCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isFollowVehicleLostConfigUseNew() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isLssCertification() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSingleChargePort() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.E28aCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSopStage() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.E28aCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDriveEnergyReset() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportFcwSen() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.E28aCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportInductionLock() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.E28aCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportKeyPark() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.E28aCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportKeyParkAdvanced() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.E28aCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportLka() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportLluPreview() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.E28aCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMemPark() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.E28aCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMrrGeoFence() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.E28aCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportNgp() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportOfflineUserPortfolioPage() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.E28aCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
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

    @Override // com.xiaopeng.carcontrol.config.E28aCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportSimpleSas() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.E28aCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportSsa() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.E28aCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportSwitchMedia() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportTboxPowerSw() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.E28aCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportUnity3D() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportXPilotSafeExam() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.E28aCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportXPilotTtsCfg() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.E28aCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportXSayHi() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.E28aCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportXSport() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportXpilotLccBindLdw() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAutoPowerOff() {
        return isSupportTboxPowerSw();
    }

    @Override // com.xiaopeng.carcontrol.config.E28aCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportRaeb() {
        return isSupportXpu();
    }

    @Override // com.xiaopeng.carcontrol.config.E28aCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportElk() {
        return isSupportXpu();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportSpecialSas() {
        return isSupportIsla();
    }

    @Override // com.xiaopeng.carcontrol.config.E28aCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportNra() {
        return hasFeature(CarBaseConfig.PROPERTY_XPU);
    }

    @Override // com.xiaopeng.carcontrol.config.E28aCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportTurnAssist() {
        return hasFeature(CarBaseConfig.PROPERTY_XPU);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportLssSen() {
        return isSupportLka();
    }
}
