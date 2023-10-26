package com.xiaopeng.carcontrol.viewmodel.hvac;

/* loaded from: classes2.dex */
public enum HvacWindBlowMode {
    Face(1),
    FaceAndFoot(2),
    Foot(3),
    FootWindshield(4),
    FrontDefrost(5),
    Windshield(6),
    AutoDefrost(7),
    FaceWindshield(8),
    FaceFootWindshield(9),
    AutoMode(14);
    
    private int mValue;

    HvacWindBlowMode(int value) {
        this.mValue = value;
    }

    int getValue() {
        return this.mValue;
    }

    public static HvacWindBlowMode fromHvacState(int value) {
        if (value != 14) {
            switch (value) {
                case 1:
                    return Face;
                case 2:
                    return FaceAndFoot;
                case 3:
                    return Foot;
                case 4:
                    return FootWindshield;
                case 5:
                    return FrontDefrost;
                case 6:
                    return Windshield;
                case 7:
                    return AutoDefrost;
                case 8:
                    return FaceWindshield;
                case 9:
                    return FaceFootWindshield;
                default:
                    return null;
            }
        }
        return AutoMode;
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.hvac.HvacWindBlowMode$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacWindBlowMode;

        static {
            int[] iArr = new int[HvacWindBlowMode.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacWindBlowMode = iArr;
            try {
                iArr[HvacWindBlowMode.Face.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacWindBlowMode[HvacWindBlowMode.Foot.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacWindBlowMode[HvacWindBlowMode.Windshield.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacWindBlowMode[HvacWindBlowMode.FaceAndFoot.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacWindBlowMode[HvacWindBlowMode.FootWindshield.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacWindBlowMode[HvacWindBlowMode.FrontDefrost.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public static int toHvacCmd(HvacWindBlowMode windMode) {
        if (windMode == null) {
            return -1;
        }
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacWindBlowMode[windMode.ordinal()];
        if (i != 1) {
            if (i != 2) {
                return i != 3 ? -1 : 1;
            }
            return 3;
        }
        return 2;
    }

    public static int toHvacCmdD21(HvacWindBlowMode windMode) {
        if (windMode == null) {
            return -1;
        }
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacWindBlowMode[windMode.ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i != 4) {
                    return i != 5 ? -1 : 4;
                }
                return 2;
            }
            return 3;
        }
        return 1;
    }

    public static int toStatisticType(HvacWindBlowMode windMode) {
        if (windMode == null) {
            return -1;
        }
        switch (AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacWindBlowMode[windMode.ordinal()]) {
            case 1:
                return 1;
            case 2:
                return 3;
            case 3:
            case 6:
                return 2;
            case 4:
                return 4;
            case 5:
                return 5;
            default:
                return -1;
        }
    }
}
