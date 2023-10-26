package com.xiaopeng.carcontrol.viewmodel.vcu;

/* loaded from: classes2.dex */
public enum GearLevel {
    Invalid,
    D,
    N,
    R,
    P;

    public static GearLevel fromVcuGearLevel(int value) {
        if (value != 1) {
            if (value != 2) {
                if (value != 3) {
                    if (value == 4) {
                        return P;
                    }
                    return Invalid;
                }
                return R;
            }
            return N;
        }
        return D;
    }
}
