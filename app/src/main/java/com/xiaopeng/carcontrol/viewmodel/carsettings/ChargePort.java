package com.xiaopeng.carcontrol.viewmodel.carsettings;

/* loaded from: classes2.dex */
public enum ChargePort {
    Left,
    Right;

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.carsettings.ChargePort$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$ChargePort;

        static {
            int[] iArr = new int[ChargePort.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$ChargePort = iArr;
            try {
                iArr[ChargePort.Left.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$ChargePort[ChargePort.Right.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public int toBcmPortValue() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$ChargePort[ordinal()];
        if (i != 1) {
            if (i == 2) {
                return 2;
            }
            throw new IllegalArgumentException("Unknown charge port: " + this);
        }
        return 1;
    }
}
