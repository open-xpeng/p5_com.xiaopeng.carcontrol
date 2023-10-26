package com.xiaopeng.carcontrol.config;

/* loaded from: classes.dex */
public class D22aCarConfig extends D22CarConfig {
    /* JADX INFO: Access modifiers changed from: package-private */
    public D22aCarConfig() {
        sIsD2xSeries = true;
    }

    @Override // com.xiaopeng.carcontrol.config.D21CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDrvSeatVent() {
        return hasFeature(CarBaseConfig.PROPERTY_SEAT_VENT);
    }

    @Override // com.xiaopeng.carcontrol.config.D22CarConfig, com.xiaopeng.carcontrol.config.DxCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportRearSeatHeat() {
        return hasFeature(CarBaseConfig.PROPERTY_REAR_SEAT_HEAT);
    }

    @Override // com.xiaopeng.carcontrol.config.D22CarConfig, com.xiaopeng.carcontrol.config.D21CarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMirrorDown() {
        return hasFeature(CarBaseConfig.PROPERTY_MIRROR);
    }
}
