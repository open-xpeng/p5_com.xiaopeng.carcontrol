package com.xiaopeng.carcontrol.viewmodel.scu;

/* loaded from: classes2.dex */
public enum ScuResponse {
    OFF,
    ON,
    FAIL,
    UNAVAILABLE,
    INITIALIZATION,
    OFF_TTM_MODE,
    OFF_MRR_MODE,
    BLINDNESS;

    public static ScuResponse fromScuState(int value) {
        if (value != 0) {
            if (value == 1) {
                return ON;
            }
            if (value != 5) {
                if (value == 6) {
                    return UNAVAILABLE;
                }
                switch (value) {
                    case 8:
                        return INITIALIZATION;
                    case 9:
                        return OFF_TTM_MODE;
                    case 10:
                        return OFF_MRR_MODE;
                    case 11:
                        return BLINDNESS;
                    default:
                        return FAIL;
                }
            }
        }
        return OFF;
    }
}
