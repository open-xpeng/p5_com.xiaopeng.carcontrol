package com.xiaopeng.carcontrol.viewmodel.sfs;

/* loaded from: classes2.dex */
public enum SfsType {
    Null,
    Type1,
    Type2,
    Type3,
    Type4;

    public static SfsType fromHvacSfsType(int sfsType) {
        if (sfsType != 1) {
            if (sfsType != 2) {
                if (sfsType != 3) {
                    if (sfsType == 4) {
                        return Type4;
                    }
                    return Null;
                }
                return Type3;
            }
            return Type2;
        }
        return Type1;
    }
}
