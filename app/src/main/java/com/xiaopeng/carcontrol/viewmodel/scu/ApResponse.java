package com.xiaopeng.carcontrol.viewmodel.scu;

/* loaded from: classes2.dex */
public enum ApResponse {
    AP_INITIAL,
    ON,
    OFF,
    AP_NO_LICENSE,
    OFF_BY_TTM,
    OFF_BY_MRR;

    public static ApResponse fromScuState(int value) {
        if (value != 0) {
            if (value != 1) {
                if (value != 2) {
                    if (value != 4) {
                        if (value == 5) {
                            return OFF_BY_MRR;
                        }
                        return AP_NO_LICENSE;
                    }
                    return OFF_BY_TTM;
                }
                return OFF;
            }
            return ON;
        }
        return AP_INITIAL;
    }
}
