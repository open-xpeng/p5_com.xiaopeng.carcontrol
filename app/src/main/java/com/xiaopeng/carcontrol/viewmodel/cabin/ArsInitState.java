package com.xiaopeng.carcontrol.viewmodel.cabin;

/* loaded from: classes2.dex */
public enum ArsInitState {
    UNINITIALIZED(0),
    INITIALIZING(1),
    INITIALIZED(2),
    INVALID(-1);
    
    int intValue;

    ArsInitState(int intValue) {
        this.intValue = intValue;
    }

    public int getIntValue() {
        return this.intValue;
    }

    public static ArsInitState from(int value) {
        if (value != 0) {
            if (value != 1) {
                if (value == 2) {
                    return INITIALIZED;
                }
                return INVALID;
            }
            return INITIALIZING;
        }
        return UNINITIALIZED;
    }

    public static ArsInitState fromBcmValue(int status) {
        if (status != 0) {
            if (status != 1) {
                if (status == 2) {
                    return INITIALIZING;
                }
                return INVALID;
            }
            return INITIALIZED;
        }
        return UNINITIALIZED;
    }
}
