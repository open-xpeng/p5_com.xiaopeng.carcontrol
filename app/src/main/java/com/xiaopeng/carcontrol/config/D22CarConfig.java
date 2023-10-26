package com.xiaopeng.carcontrol.config;

/* loaded from: classes.dex */
public class D22CarConfig extends D21CarConfig {
    @Override // com.xiaopeng.carcontrol.config.D21CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isNewAvasArch() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.DxCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportCiuConfig() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.D21CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDhc() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.D21CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportIslc() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.D21CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportIslcInActive() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.D21CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMainWiperInterval() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMcuKeyOpenFailed() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportNewParkLampFmB() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportOldLka() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.D21CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportPM25Out() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.D21CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportTopCamera() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.DxCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportTurnActive() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportXPilotSafeExam() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public D22CarConfig() {
        sIsD2xSeries = true;
    }

    @Override // com.xiaopeng.carcontrol.config.D21CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportLlu() {
        return hasFeature(CarBaseConfig.PROPERTY_LLU);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportLluDance() {
        return isSupportLlu();
    }

    @Override // com.xiaopeng.carcontrol.config.D21CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMirrorDown() {
        return getCfcCode() == 3;
    }

    @Override // com.xiaopeng.carcontrol.config.DxCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDsmCamera() {
        return hasFeature(CarBaseConfig.PROPERTY_DSM_CAMERA);
    }

    @Override // com.xiaopeng.carcontrol.config.D21CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportLka() {
        int cfcCode = getCfcCode();
        return cfcCode == 3 || cfcCode == 2;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportElk() {
        return !isLowConfig();
    }

    @Override // com.xiaopeng.carcontrol.config.D21CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportIsla() {
        return !isLowConfig();
    }

    @Override // com.xiaopeng.carcontrol.config.D21CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAutoPark() {
        int cfcCode = getCfcCode();
        return cfcCode == 3 || cfcCode == 2;
    }

    @Override // com.xiaopeng.carcontrol.config.DxCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportXSayHi() {
        return getCfcCode() >= 1;
    }

    @Override // com.xiaopeng.carcontrol.config.DxCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportRearSeatHeat() {
        return isHighConfig();
    }

    @Override // com.xiaopeng.carcontrol.config.D21CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportCwc() {
        return hasFeature(CarBaseConfig.PROPERTY_CWC);
    }

    @Override // com.xiaopeng.carcontrol.config.D21CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportEsb() {
        return hasFeature(CarBaseConfig.PROPERTY_MSB);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportNra() {
        return hasFeature(CarBaseConfig.PROPERTY_AVM);
    }
}
