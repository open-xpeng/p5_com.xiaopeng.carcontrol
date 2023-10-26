package com.xiaopeng.carcontrol.viewmodel.carsettings;

/* loaded from: classes2.dex */
public enum WiperSensitivity {
    Slow,
    Medium,
    Fast,
    Ultra;

    public static WiperSensitivity fromBcmState(int value) {
        if (value != 1) {
            if (value != 2) {
                if (value != 3) {
                    if (value != 4) {
                        return null;
                    }
                    return Ultra;
                }
                return Fast;
            }
            return Medium;
        }
        return Slow;
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.carsettings.WiperSensitivity$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$WiperSensitivity;

        static {
            int[] iArr = new int[WiperSensitivity.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$WiperSensitivity = iArr;
            try {
                iArr[WiperSensitivity.Slow.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$WiperSensitivity[WiperSensitivity.Medium.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$WiperSensitivity[WiperSensitivity.Fast.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$WiperSensitivity[WiperSensitivity.Ultra.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public int toBcmCmd() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$WiperSensitivity[ordinal()];
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                i2 = 3;
                if (i != 3) {
                    if (i == 4) {
                        return 4;
                    }
                    throw new IllegalArgumentException("Unknown wiper sensitivity type: " + this);
                }
            }
        }
        return i2;
    }

    public static WiperSensitivity getIndexValue(int index) {
        try {
            return values()[index];
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }
}
