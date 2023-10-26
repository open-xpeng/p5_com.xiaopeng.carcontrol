package com.xiaopeng.carcontrol.viewmodel.vcu;

/* loaded from: classes2.dex */
public enum ChargeGunStatus {
    NoLink,
    AcLink,
    DcLink,
    BothLink,
    V2L,
    V2LNotSupport;

    public static ChargeGunStatus fromVcuStatus(int value) {
        if (value != 0) {
            if (value != 1) {
                if (value != 2) {
                    if (value != 3) {
                        if (value != 4) {
                            if (value == 5) {
                                return V2LNotSupport;
                            }
                            throw new IllegalArgumentException("Unknown charge gun type: " + value);
                        }
                        return V2L;
                    }
                    return BothLink;
                }
                return DcLink;
            }
            return AcLink;
        }
        return NoLink;
    }
}
