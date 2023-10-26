package com.xiaopeng.carcontrol.viewmodel.cabin;

/* loaded from: classes2.dex */
public enum ArsFoldState {
    FOLDED(0),
    MOVING(1),
    UNFOLDED(2);
    
    private int intValue;

    ArsFoldState(int intValue) {
        this.intValue = intValue;
    }

    public int getIntValue() {
        return this.intValue;
    }

    public static final ArsFoldState from(int value) {
        if (value != 0) {
            if (value != 1) {
                if (value != 2) {
                    return null;
                }
                return UNFOLDED;
            }
            return MOVING;
        }
        return FOLDED;
    }

    public static final ArsFoldState fromBcmValue(int workState, int position) {
        if (workState == 0) {
            if (position != 0) {
                if (position == 100) {
                    return UNFOLDED;
                }
                return UNFOLDED;
            }
            return FOLDED;
        }
        return MOVING;
    }
}
