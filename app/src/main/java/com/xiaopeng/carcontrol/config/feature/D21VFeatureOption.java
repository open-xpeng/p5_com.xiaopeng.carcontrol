package com.xiaopeng.carcontrol.config.feature;

/* loaded from: classes2.dex */
public class D21VFeatureOption extends D21FeatureOption {
    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportShowAvasSwitch() {
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
