package com.xiaopeng.carcontrol.viewmodel.scu;

/* loaded from: classes2.dex */
public enum NgpChangeLaneMode {
    Off,
    Standard,
    Sensitive,
    Radical;

    public static NgpChangeLaneMode fromScuState(int value) {
        if (value != 1) {
            if (value == 2) {
                return Radical;
            }
            return Off;
        }
        return Standard;
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.scu.NgpChangeLaneMode$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$NgpChangeLaneMode;

        static {
            int[] iArr = new int[NgpChangeLaneMode.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$NgpChangeLaneMode = iArr;
            try {
                iArr[NgpChangeLaneMode.Standard.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$NgpChangeLaneMode[NgpChangeLaneMode.Sensitive.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$NgpChangeLaneMode[NgpChangeLaneMode.Radical.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$NgpChangeLaneMode[NgpChangeLaneMode.Off.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public int toScuCmd() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$NgpChangeLaneMode[ordinal()];
        if (i == 1 || i == 2) {
            return 1;
        }
        return i != 3 ? 0 : 2;
    }
}
