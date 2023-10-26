package com.xiaopeng.carcontrol.viewmodel.hvac;

/* loaded from: classes2.dex */
public enum SeatVentLevel {
    Off,
    Level1,
    Level2,
    Level3;

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.hvac.SeatVentLevel$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$SeatVentLevel;

        static {
            int[] iArr = new int[SeatVentLevel.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$SeatVentLevel = iArr;
            try {
                iArr[SeatVentLevel.Off.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$SeatVentLevel[SeatVentLevel.Level3.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$SeatVentLevel[SeatVentLevel.Level2.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$SeatVentLevel[SeatVentLevel.Level1.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public SeatVentLevel toggle() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$SeatVentLevel[ordinal()];
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

    public static SeatVentLevel fromBcmState(int value) {
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

    public static SeatVentLevel getValueByIndex(int index) {
        try {
            return values()[index];
        } catch (Exception unused) {
            return null;
        }
    }
}
