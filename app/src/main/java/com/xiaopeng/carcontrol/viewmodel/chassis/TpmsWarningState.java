package com.xiaopeng.carcontrol.viewmodel.chassis;

/* loaded from: classes2.dex */
public enum TpmsWarningState {
    NORMAL,
    LOW_PRESSURE,
    HIGH_PRESSURE,
    INVALID;

    public static TpmsWarningState fromTpms(int value) {
        if (value != 0) {
            if (value != 2) {
                if (value == 4) {
                    return HIGH_PRESSURE;
                }
                return INVALID;
            }
            return LOW_PRESSURE;
        }
        return NORMAL;
    }
}
