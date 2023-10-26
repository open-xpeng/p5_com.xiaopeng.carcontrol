package com.xiaopeng.libcarcontrol;

/* loaded from: classes2.dex */
public enum ChargePortState {
    OPENED,
    MIDDLE,
    CLOSED,
    FAULT,
    UNKNOWN;
    
    private static final int BCM_CHARGE_PORT_CLOSED = 2;
    private static final int BCM_CHARGE_PORT_FAULT = 3;
    private static final int BCM_CHARGE_PORT_MIDDLE = 1;
    private static final int BCM_CHARGE_PORT_OPEN = 0;
    private static final int BCM_CHARGE_PORT_UNKNOWN = -1;

    public static ChargePortState fromBcmValue(int value) {
        if (value != 0) {
            if (value != 1) {
                if (value != 2) {
                    if (value == 3) {
                        return FAULT;
                    }
                    return UNKNOWN;
                }
                return CLOSED;
            }
            return MIDDLE;
        }
        return OPENED;
    }
}
