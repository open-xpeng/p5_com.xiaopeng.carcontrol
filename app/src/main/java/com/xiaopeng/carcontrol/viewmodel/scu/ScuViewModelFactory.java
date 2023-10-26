package com.xiaopeng.carcontrol.viewmodel.scu;

import com.xiaopeng.carcontrol.config.CarBaseConfig;

/* loaded from: classes2.dex */
public class ScuViewModelFactory {
    public static IScuViewModel createViewModel() {
        if (CarBaseConfig.getInstance().isNewScuArch()) {
            return new ScuViewModel();
        }
        return new ScuOldViewModel();
    }
}
