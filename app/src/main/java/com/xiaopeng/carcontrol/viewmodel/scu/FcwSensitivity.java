package com.xiaopeng.carcontrol.viewmodel.scu;

/* loaded from: classes2.dex */
public enum FcwSensitivity {
    Low,
    Medium,
    High,
    Unavailable;

    public static FcwSensitivity fromXpuState(int value) {
        if (value != 1) {
            if (value != 2) {
                if (value == 3) {
                    return High;
                }
                return Unavailable;
            }
            return Low;
        }
        return Medium;
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.scu.FcwSensitivity$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$FcwSensitivity;

        static {
            int[] iArr = new int[FcwSensitivity.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$FcwSensitivity = iArr;
            try {
                iArr[FcwSensitivity.Low.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$FcwSensitivity[FcwSensitivity.High.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$FcwSensitivity[FcwSensitivity.Medium.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public static int toXpuCmd(FcwSensitivity sensitivity) {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$FcwSensitivity[sensitivity.ordinal()];
        if (i != 1) {
            if (i != 2) {
                return i != 3 ? 0 : 1;
            }
            return 3;
        }
        return 2;
    }

    public static FcwSensitivity valueOf(int ordinal) {
        if (ordinal < 0 || ordinal >= values().length) {
            throw new IndexOutOfBoundsException("Invalid ordinal");
        }
        return values()[ordinal];
    }
}
