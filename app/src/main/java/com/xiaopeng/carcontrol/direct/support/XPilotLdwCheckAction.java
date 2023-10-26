package com.xiaopeng.carcontrol.direct.support;

import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.DxCarConfig;

/* loaded from: classes2.dex */
public class XPilotLdwCheckAction implements SupportCheckAction {
    @Override // com.xiaopeng.carcontrol.direct.support.SupportCheckAction
    public boolean checkSupport() {
        return (CarBaseConfig.getInstance().isSupportLka() || DxCarConfig.getInstance().isLowConfig()) ? false : true;
    }
}
