package com.xiaopeng.carcontrol.viewmodel.hvac;

/* loaded from: classes2.dex */
public enum AqsSensitivityLevel {
    Low,
    Middle,
    High;

    public static AqsSensitivityLevel fromHvacState(int level) {
        if (level != 1) {
            if (level != 2) {
                if (level != 3) {
                    return null;
                }
                return High;
            }
            return Middle;
        }
        return Low;
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.hvac.AqsSensitivityLevel$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$AqsSensitivityLevel;

        static {
            int[] iArr = new int[AqsSensitivityLevel.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$AqsSensitivityLevel = iArr;
            try {
                iArr[AqsSensitivityLevel.Low.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$AqsSensitivityLevel[AqsSensitivityLevel.Middle.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$AqsSensitivityLevel[AqsSensitivityLevel.High.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public static int toHvacCmd(AqsSensitivityLevel aqsSensitivityLevel) {
        if (aqsSensitivityLevel == null) {
            return -1;
        }
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$AqsSensitivityLevel[aqsSensitivityLevel.ordinal()];
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                i2 = 3;
                if (i != 3) {
                    return -1;
                }
            }
        }
        return i2;
    }

    public static AqsSensitivityLevel getIndexValue(int index) {
        try {
            return values()[index];
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }
}
