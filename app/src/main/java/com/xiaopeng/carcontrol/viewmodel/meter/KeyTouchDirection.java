package com.xiaopeng.carcontrol.viewmodel.meter;

/* loaded from: classes2.dex */
public enum KeyTouchDirection {
    Inward,
    Outward;

    public static KeyTouchDirection fromIcmStatus(int value) {
        try {
            return values()[value];
        } catch (IndexOutOfBoundsException unused) {
            throw new IllegalArgumentException("Unknown llu effect value: " + value);
        }
    }

    public int toIcmCmd() {
        return ordinal();
    }

    public static KeyTouchDirection getIndexValue(int index) {
        try {
            return values()[index];
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }
}
