package com.xiaopeng.carcontrol.viewmodel.lamp;

/* loaded from: classes2.dex */
public enum LampHeightLevel {
    Highest,
    High,
    Low,
    Lowest;

    public static LampHeightLevel fromBcmValue(int value) {
        int length = values().length;
        if (value < 0 || value >= length) {
            return null;
        }
        return values()[value];
    }

    public int toBcmValue() {
        return ordinal();
    }
}
