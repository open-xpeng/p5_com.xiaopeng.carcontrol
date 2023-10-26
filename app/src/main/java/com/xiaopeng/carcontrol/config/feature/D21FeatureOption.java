package com.xiaopeng.carcontrol.config.feature;

/* loaded from: classes2.dex */
public class D21FeatureOption extends DxFeatureOption {
    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public int getAlcSpeed() {
        return 65;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public long getWindowLoadingTime() {
        return 5500L;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public long getWindowVentingTime() {
        return 2000L;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSeatHeatVentGather() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportAllWindowState() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportAvasNewMemKey() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportGuiPageOpen() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean isSupportVui() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.feature.BaseFeatureOption
    public boolean shouldIgnoreDrvOccupied() {
        return false;
    }
}
