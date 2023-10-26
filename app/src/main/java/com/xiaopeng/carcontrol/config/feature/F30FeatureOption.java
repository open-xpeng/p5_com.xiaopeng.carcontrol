package com.xiaopeng.carcontrol.config.feature;

import com.xiaopeng.carcontrol.config.CarBaseConfig;

/* loaded from: classes2.dex */
public class F30FeatureOption extends E28FeatureOption {
    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean IsSupportGeekMode() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isAcAutoNature() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isHvacDataMemoryFromRDCU() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isNewLssSignal() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.E28FeatureOption, com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSeatHeatVentGather() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isShowDoorKeySpeech() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isShowReadyDisableCustomXDialog() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportChargePortOnOffConstraint() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.E28FeatureOption, com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportChargePortPGearConstrain() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportDomainControllerSeatWelcome() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.E28FeatureOption, com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportEcoDriveMode() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportEnhancedParkFunc() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportFollowMeHomeWhenRemoteInOn() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.E28FeatureOption, com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportHvacColdHeatNature() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.E28FeatureOption, com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportHvacPsnSeatRecovery() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.E28FeatureOption, com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportHvacShowAuto() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportLowBeamOffConfirm() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportNapa() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.E28FeatureOption, com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportPopPanel() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportPressSignal() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.E28FeatureOption, com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportPsnSeatManualSave() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportPsnSeatVerControl() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportShowAdaptiveDriveMode() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.E28FeatureOption, com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportSingleMode() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportSmartModeCountdown() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportStandardDriveMode() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportXSportApp() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.E28FeatureOption, com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSignalRegisterDynamically() {
        return !isSupportNapa();
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportSelfCheck() {
        return !isSupportNapa();
    }

    @Override // com.xiaopeng.carcontrol.config.feature.E28FeatureOption, com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportNewSelfCheckArch() {
        return isSupportSelfCheck();
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean IsSupportXpowerMode() {
        return CarBaseConfig.getInstance().isSupportXSport();
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isShowXkeyNraView() {
        return CarBaseConfig.getInstance().isSupportNra();
    }
}
