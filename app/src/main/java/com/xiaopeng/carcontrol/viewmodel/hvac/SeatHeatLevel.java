package com.xiaopeng.carcontrol.viewmodel.hvac;

/* loaded from: classes2.dex */
public enum SeatHeatLevel {
    Off,
    Level1,
    Level2,
    Level3;

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.hvac.SeatHeatLevel$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$SeatHeatLevel;

        static {
            int[] iArr = new int[SeatHeatLevel.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$SeatHeatLevel = iArr;
            try {
                iArr[SeatHeatLevel.Off.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$SeatHeatLevel[SeatHeatLevel.Level3.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$SeatHeatLevel[SeatHeatLevel.Level2.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$SeatHeatLevel[SeatHeatLevel.Level1.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public SeatHeatLevel toggle() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$SeatHeatLevel[ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i == 3) {
                    return Level1;
                }
                return Off;
            }
            return Level2;
        }
        return Level3;
    }

    public static SeatHeatLevel fromBcmState(int value) {
        if (value != 1) {
            if (value != 2) {
                if (value == 3) {
                    return Level3;
                }
                return Off;
            }
            return Level2;
        }
        return Level1;
    }

    public static SeatHeatLevel getValueByIndex(int index) {
        try {
            return values()[index];
        } catch (Exception unused) {
            return null;
        }
    }
}
