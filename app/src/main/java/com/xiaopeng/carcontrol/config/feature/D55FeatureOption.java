package com.xiaopeng.carcontrol.config.feature;

import com.xiaopeng.carcontrol.config.CarBaseConfig;

/* loaded from: classes2.dex */
public class D55FeatureOption extends DxFeatureOption {
    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public int getAlcSpeed() {
        return 45;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public int getSeatTiltMovingSafePos() {
        return 30;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public long getWindowLoadingTime() {
        return 3000L;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public long getWindowVentingTime() {
        return 3000L;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isDrvRightVentOnTop() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportCarLife() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportECall() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportEspFeedback() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportFullscreenPanel() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportGuiPageOpen() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportIhbInSettings() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportIntellDriveSysSelfCheck() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportLidarSafeExam() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportPopPanel() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportPsnSeatManualSave() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportPsnSeatPosCallback() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportSeatSectionalRecovery() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportVui() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean needCheckValidMirrorPos() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean shouldIgnoreDrvOccupied() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean showXPilotStAsTitle() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.DxFeatureOption, com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportWiperSenCfgShow() {
        return CarBaseConfig.getInstance().isSupportWiperSenCfg() && !CarBaseConfig.getInstance().isWiperSensitiveNegative();
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isShowXkeyNraView() {
        return CarBaseConfig.getInstance().isSupportXpu();
    }
}
