package com.xiaopeng.carcontrol.viewmodel.hvac;

/* loaded from: classes2.dex */
public enum HvacCirculationTime {
    Off,
    Slow,
    Middle,
    Quick;

    public static HvacCirculationTime fromHvacState(int interval) {
        if (interval != 1) {
            if (interval != 2) {
                if (interval != 3) {
                    if (interval != 6) {
                        return null;
                    }
                    return Off;
                }
                return Quick;
            }
            return Middle;
        }
        return Slow;
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.hvac.HvacCirculationTime$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacCirculationTime;

        static {
            int[] iArr = new int[HvacCirculationTime.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacCirculationTime = iArr;
            try {
                iArr[HvacCirculationTime.Slow.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacCirculationTime[HvacCirculationTime.Middle.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacCirculationTime[HvacCirculationTime.Quick.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public static int toHvacCmd(HvacCirculationTime hvacCirculationTime) {
        if (hvacCirculationTime == null) {
            return 6;
        }
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacCirculationTime[hvacCirculationTime.ordinal()];
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                i2 = 3;
                if (i != 3) {
                    return 6;
                }
            }
        }
        return i2;
    }

    public static HvacCirculationTime getIndexValue(int index) {
        try {
            return values()[index];
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }
}
