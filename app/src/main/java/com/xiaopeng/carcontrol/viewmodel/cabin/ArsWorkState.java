package com.xiaopeng.carcontrol.viewmodel.cabin;

/* loaded from: classes2.dex */
public enum ArsWorkState {
    STOP(0),
    FOLDING(1),
    FOLD_DURG(2),
    UNFOLDING(3),
    UNFOLD_DURG(4),
    ZERO_POS_LRN(5),
    INITING(6);
    
    private int intValue;

    ArsWorkState(int intValue) {
        this.intValue = intValue;
    }

    public int getIntValue() {
        return this.intValue;
    }

    public static final ArsWorkState from(int value) {
        switch (value) {
            case 0:
                return STOP;
            case 1:
                return FOLDING;
            case 2:
                return FOLD_DURG;
            case 3:
                return UNFOLDING;
            case 4:
                return UNFOLD_DURG;
            case 5:
                return ZERO_POS_LRN;
            case 6:
                return INITING;
            default:
                return null;
        }
    }

    public static final ArsWorkState fromBcmValue(int workState) {
        switch (workState) {
            case 0:
                return STOP;
            case 1:
                return FOLDING;
            case 2:
                return FOLD_DURG;
            case 3:
                return UNFOLDING;
            case 4:
                return UNFOLD_DURG;
            case 5:
                return ZERO_POS_LRN;
            case 6:
                return INITING;
            default:
                return STOP;
        }
    }
}
