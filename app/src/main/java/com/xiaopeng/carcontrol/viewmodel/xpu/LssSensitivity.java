package com.xiaopeng.carcontrol.viewmodel.xpu;

/* loaded from: classes2.dex */
public enum LssSensitivity {
    Low,
    Medium,
    High;

    public static LssSensitivity fromXpuState(int value) {
        if (value != 1) {
            if (value == 2) {
                return High;
            }
            return Medium;
        }
        return Low;
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.xpu.LssSensitivity$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$xpu$LssSensitivity;

        static {
            int[] iArr = new int[LssSensitivity.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$xpu$LssSensitivity = iArr;
            try {
                iArr[LssSensitivity.Low.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$xpu$LssSensitivity[LssSensitivity.High.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$xpu$LssSensitivity[LssSensitivity.Medium.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public int toXpuCmd() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$xpu$LssSensitivity[ordinal()];
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                return 0;
            }
        }
        return i2;
    }
}
