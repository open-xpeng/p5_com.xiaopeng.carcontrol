package com.xiaopeng.carcontrol.direct.support;

import com.xiaopeng.carcontrol.config.CarBaseConfig;

/* loaded from: classes2.dex */
public class DoorKeyCheckAction implements SupportCheckAction {
    @Override // com.xiaopeng.carcontrol.direct.support.SupportCheckAction
    public boolean checkSupport() {
        return CarBaseConfig.getInstance().isSupportDoorKeySetting();
    }
}
