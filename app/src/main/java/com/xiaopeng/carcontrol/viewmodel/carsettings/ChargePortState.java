package com.xiaopeng.carcontrol.viewmodel.carsettings;

/* loaded from: classes2.dex */
public enum ChargePortState {
    OPEN,
    MIDDLE,
    CLOSE,
    FAULT,
    UNKNOWN;

    public static ChargePortState fromBcmValue(int value) {
        if (value != -1) {
            if (value != 0) {
                if (value != 1) {
                    if (value != 2) {
                        if (value != 3) {
                            return null;
                        }
                        return FAULT;
                    }
                    return CLOSE;
                }
                return MIDDLE;
            }
            return OPEN;
        }
        return UNKNOWN;
    }
}
