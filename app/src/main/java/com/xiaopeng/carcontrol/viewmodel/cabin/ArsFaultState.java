package com.xiaopeng.carcontrol.viewmodel.cabin;

/* loaded from: classes2.dex */
public enum ArsFaultState {
    NO_FAULT(0),
    FAULT(1);
    
    private int intValue;

    ArsFaultState(int value) {
        this.intValue = value;
    }

    public int getIntValue() {
        return this.intValue;
    }

    public static final ArsFaultState fromBcmValue(int state) {
        if (state == 1) {
            return FAULT;
        }
        return NO_FAULT;
    }
}
