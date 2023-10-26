package com.xiaopeng.carcontrol.viewmodel.hvac;

/* loaded from: classes2.dex */
public enum AcHeatNatureMode {
    HVAC_AC_OFF,
    HVAC_AC_ON,
    HVAC_HEAT_OFF,
    HVAC_HEAT_ON,
    HVAC_NATURE_OFF,
    HVAC_NATURE_ON,
    HVAC_AUTO_OFF,
    HVAC_AUTO_ON;

    public static AcHeatNatureMode fromHvacMode(int mode) {
        switch (mode) {
            case 0:
                return HVAC_AC_OFF;
            case 1:
                return HVAC_AC_ON;
            case 2:
                return HVAC_HEAT_OFF;
            case 3:
                return HVAC_HEAT_ON;
            case 4:
                return HVAC_NATURE_OFF;
            case 5:
                return HVAC_NATURE_ON;
            case 6:
                return HVAC_AUTO_OFF;
            case 7:
                return HVAC_AUTO_ON;
            default:
                return null;
        }
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.hvac.AcHeatNatureMode$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$AcHeatNatureMode;

        static {
            int[] iArr = new int[AcHeatNatureMode.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$AcHeatNatureMode = iArr;
            try {
                iArr[AcHeatNatureMode.HVAC_AC_OFF.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$AcHeatNatureMode[AcHeatNatureMode.HVAC_NATURE_ON.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$AcHeatNatureMode[AcHeatNatureMode.HVAC_AC_ON.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$AcHeatNatureMode[AcHeatNatureMode.HVAC_HEAT_OFF.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$AcHeatNatureMode[AcHeatNatureMode.HVAC_HEAT_ON.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$AcHeatNatureMode[AcHeatNatureMode.HVAC_NATURE_OFF.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$AcHeatNatureMode[AcHeatNatureMode.HVAC_AUTO_OFF.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$AcHeatNatureMode[AcHeatNatureMode.HVAC_AUTO_ON.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    public static int toggle(AcHeatNatureMode acHeatNatureMode) {
        if (acHeatNatureMode == null) {
            return -1;
        }
        switch (AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$AcHeatNatureMode[acHeatNatureMode.ordinal()]) {
            case 1:
            case 2:
                return 1;
            case 3:
            case 4:
                return 2;
            case 5:
            case 6:
            case 7:
            case 8:
                return 3;
            default:
                return -1;
        }
    }

    public static int toHvacCmd(AcHeatNatureMode acHeatNatureMode) {
        if (acHeatNatureMode == null) {
            return -1;
        }
        switch (AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$AcHeatNatureMode[acHeatNatureMode.ordinal()]) {
            case 1:
            case 3:
                return 1;
            case 2:
            case 6:
                return 3;
            case 4:
            case 5:
                return 2;
            default:
                return -1;
        }
    }

    public static int d55Toggle(AcHeatNatureMode acHeatNatureMode) {
        return HVAC_AUTO_ON == acHeatNatureMode ? 3 : 4;
    }

    public static int toD55HvacCmd(AcHeatNatureMode acHeatNatureMode) {
        return HVAC_AUTO_ON == acHeatNatureMode ? 4 : 3;
    }
}
