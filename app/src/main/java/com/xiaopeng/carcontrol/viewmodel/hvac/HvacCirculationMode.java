package com.xiaopeng.carcontrol.viewmodel.hvac;

/* loaded from: classes2.dex */
public enum HvacCirculationMode {
    Inner,
    Outside,
    Auto;

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.hvac.HvacCirculationMode$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacCirculationMode;

        static {
            int[] iArr = new int[HvacCirculationMode.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacCirculationMode = iArr;
            try {
                iArr[HvacCirculationMode.Outside.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    public HvacCirculationMode toggle() {
        if (AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacCirculationMode[ordinal()] == 1) {
            return Inner;
        }
        return Outside;
    }

    public static HvacCirculationMode fromHvacState(int mode) {
        if (mode != 1) {
            if (mode == 6) {
                return Auto;
            }
            return Outside;
        }
        return Inner;
    }

    public static int toHvacCmd(HvacCirculationMode mode) {
        return Inner == mode ? 1 : 2;
    }
}
