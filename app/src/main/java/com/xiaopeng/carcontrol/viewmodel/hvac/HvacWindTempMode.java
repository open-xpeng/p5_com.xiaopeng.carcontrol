package com.xiaopeng.carcontrol.viewmodel.hvac;

/* loaded from: classes2.dex */
public enum HvacWindTempMode {
    Nature,
    Cold,
    Hot;

    public static HvacWindTempMode fromHvacState(int value) {
        if (value != 2) {
            if (value == 3) {
                return Hot;
            }
            return Nature;
        }
        return Cold;
    }
}
