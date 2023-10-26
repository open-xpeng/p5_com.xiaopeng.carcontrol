package com.xiaopeng.carcontrol.config.feature;

import com.xiaopeng.carcontrol.config.CarBaseConfig;

/* loaded from: classes2.dex */
public class E28FeatureOption extends BaseFeatureOption {
    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public long getWindowLoadingTime() {
        return 2000L;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public long getWindowVentingTime() {
        return 2000L;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isNewFcwSignal() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSeatHeatVentGather() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isShowSeatRealPosition() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSignalRegisterDynamically() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportCallAvatar() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportCarStatusBIUpload() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportChargePortPGearConstrain() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportDriveAutoLock() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportDriveInSettingsPage() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportEcoDriveMode() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportGuiPageOpen() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportHvacColdHeatNature() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportHvacPsnSeatRecovery() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportHvacShowAuto() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportHvacVentControl() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportInflateTwice() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportIslaMemoryFunc() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportLeftCarImgChange() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportLightMeHomeInSettings() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportLluAllOn() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportLluVideo() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportMirrorFoldUnfoldFuzzySpeech() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportMirrorMemoryInSettings() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportNeedReceiveGreet() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportNewSelfCheckArch() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportPopPanel() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportPsnSeatManualSave() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportSingleMode() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportVui() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean shouldIgnoreDrvOccupied() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportLluSyncShow() {
        return CarBaseConfig.getInstance().isSupportLlu();
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportWiperSenCfgShow() {
        return !CarBaseConfig.hasFeature(CarBaseConfig.PROPERTY_PACKAGE_3);
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean shouldRecoverMirror() {
        return CarBaseConfig.getInstance().isSupportMirrorMemory();
    }
}
