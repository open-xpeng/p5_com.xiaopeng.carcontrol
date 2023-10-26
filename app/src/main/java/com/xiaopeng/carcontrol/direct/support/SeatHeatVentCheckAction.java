package com.xiaopeng.carcontrol.direct.support;

import com.xiaopeng.carcontrol.config.CarBaseConfig;

/* loaded from: classes2.dex */
public class SeatHeatVentCheckAction implements SupportCheckAction {
    @Override // com.xiaopeng.carcontrol.direct.support.SupportCheckAction
    public boolean checkSupport() {
        CarBaseConfig carBaseConfig = CarBaseConfig.getInstance();
        return carBaseConfig.isSupportDrvSeatHeat() || carBaseConfig.isSupportPsnSeatHeat() || carBaseConfig.isSupportRearSeatHeat() || carBaseConfig.isSupportDrvSeatVent();
    }
}
