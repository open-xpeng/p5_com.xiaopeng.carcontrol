package com.xiaopeng.carcontrol.viewmodel.carsettings;

/* loaded from: classes2.dex */
public enum AsSoft {
    Soft,
    Medium,
    Hard;

    public static AsSoft fromBcmState(int value) {
        if (value != 1) {
            if (value != 2) {
                if (value != 3) {
                    return null;
                }
                return Hard;
            }
            return Medium;
        }
        return Soft;
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.carsettings.AsSoft$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$AsSoft;

        static {
            int[] iArr = new int[AsSoft.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$AsSoft = iArr;
            try {
                iArr[AsSoft.Soft.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$AsSoft[AsSoft.Medium.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$AsSoft[AsSoft.Hard.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public int toBcmCmd() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$AsSoft[ordinal()];
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                if (i == 3) {
                    return 3;
                }
                throw new IllegalArgumentException("Unknown As Soft: " + this);
            }
        }
        return i2;
    }
}
