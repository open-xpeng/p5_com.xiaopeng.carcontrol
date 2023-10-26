package com.xiaopeng.carcontrol.viewmodel.carsettings;

/* loaded from: classes2.dex */
public enum AsHeight {
    Off,
    HL2,
    HL1,
    H0,
    LL1,
    LL2,
    LL3;

    public static AsHeight fromBcmState(int value) {
        switch (value) {
            case 0:
                return Off;
            case 1:
                return HL2;
            case 2:
                return HL1;
            case 3:
                return H0;
            case 4:
                return LL1;
            case 5:
            case 6:
                return LL3;
            default:
                return null;
        }
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.carsettings.AsHeight$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$AsHeight;

        static {
            int[] iArr = new int[AsHeight.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$AsHeight = iArr;
            try {
                iArr[AsHeight.Off.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$AsHeight[AsHeight.HL2.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$AsHeight[AsHeight.HL1.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$AsHeight[AsHeight.H0.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$AsHeight[AsHeight.LL1.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$AsHeight[AsHeight.LL2.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$AsHeight[AsHeight.LL3.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public int toBcmCmd() {
        switch (AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$AsHeight[ordinal()]) {
            case 1:
                return 0;
            case 2:
                return 1;
            case 3:
                return 2;
            case 4:
                return 3;
            case 5:
                return 4;
            case 6:
                return 5;
            case 7:
                return 6;
            default:
                throw new IllegalArgumentException("Unknown As Height: " + this);
        }
    }
}
