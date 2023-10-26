package com.xiaopeng.carcontrol.config.feature;

/* loaded from: classes2.dex */
public class D55VFeatureOption extends D55FeatureOption {
    @Override // com.xiaopeng.carcontrol.config.feature.D55FeatureOption, com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public int getAlcSpeed() {
        return 65;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isForceCloseAlcWhenIgOn() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isForceTurnOnElkWhenIgOn() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.D55FeatureOption, com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportCarLife() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportChangeableDialogTitleSize() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportForceOpenDsm() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportGeoFence() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportOpenAlcWhenNotInPGear() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportRemainingMileageInSleepMode() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportShowUserBook() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportTpmsWarning() {
        return true;
    }
}
