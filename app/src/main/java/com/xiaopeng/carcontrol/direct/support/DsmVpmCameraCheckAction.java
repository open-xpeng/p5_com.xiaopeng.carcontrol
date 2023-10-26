package com.xiaopeng.carcontrol.direct.support;

import com.xiaopeng.carcontrol.config.CarBaseConfig;

/* loaded from: classes2.dex */
public class DsmVpmCameraCheckAction implements SupportCheckAction {
    @Override // com.xiaopeng.carcontrol.direct.support.SupportCheckAction
    public boolean checkSupport() {
        if (CarBaseConfig.getInstance().isSupportDsmCamera()) {
            boolean isVpmHwMiss = CarBaseConfig.getInstance().isVpmHwMiss();
            boolean isVpmSwReady = CarBaseConfig.getInstance().isVpmSwReady();
            if (!isVpmHwMiss || !isVpmSwReady) {
            }
        }
        return false;
    }
}
