package com.xiaopeng.carcontrol.viewmodel.scu;

/* loaded from: classes2.dex */
public enum ScuIslaMode {
    OFF,
    SLA,
    ISLA,
    FAIL;

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.scu.ScuIslaMode$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuIslaMode;

        static {
            int[] iArr = new int[ScuIslaMode.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuIslaMode = iArr;
            try {
                iArr[ScuIslaMode.SLA.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuIslaMode[ScuIslaMode.ISLA.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuIslaMode[ScuIslaMode.FAIL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuIslaMode[ScuIslaMode.OFF.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public int toDisplayIndex() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuIslaMode[ordinal()];
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                return 0;
            }
        }
        return i2;
    }

    public static ScuIslaMode fromScuState(int value) {
        if (value != 0) {
            if (value != 2) {
                if (value == 3) {
                    return ISLA;
                }
                return OFF;
            }
            return SLA;
        }
        return FAIL;
    }
}
