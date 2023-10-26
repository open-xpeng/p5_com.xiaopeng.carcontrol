package com.xiaopeng.carcontrol.viewmodel.vcu;

/* loaded from: classes2.dex */
public enum EnergyRecycleGrade {
    High,
    Middle,
    Low,
    Auto;

    public static EnergyRecycleGrade fromVcuRecycleState(int value) {
        if (value != 1) {
            if (value != 2) {
                if (value != 3) {
                    if (value != 5) {
                        return null;
                    }
                    return Auto;
                }
                return High;
            }
            return Middle;
        }
        return Low;
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.vcu.EnergyRecycleGrade$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$EnergyRecycleGrade;

        static {
            int[] iArr = new int[EnergyRecycleGrade.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$EnergyRecycleGrade = iArr;
            try {
                iArr[EnergyRecycleGrade.Low.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$EnergyRecycleGrade[EnergyRecycleGrade.Middle.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$EnergyRecycleGrade[EnergyRecycleGrade.High.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$EnergyRecycleGrade[EnergyRecycleGrade.Auto.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public int toVcuRecycleCmd() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$EnergyRecycleGrade[ordinal()];
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                i2 = 3;
                if (i != 3) {
                    if (i == 4) {
                        return 5;
                    }
                    throw new IllegalArgumentException("Unknown energy recycle mode: " + this);
                }
            }
        }
        return i2;
    }
}
