package com.xiaopeng.carcontrol.viewmodel.hvac;

/* loaded from: classes2.dex */
public enum HvacSwitchStatus {
    OFF,
    ON,
    ERROR;

    public static HvacSwitchStatus fromHvacStatus(int status) {
        if (status != 0) {
            if (status == 1) {
                return ON;
            }
            return ERROR;
        }
        return OFF;
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.hvac.HvacSwitchStatus$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacSwitchStatus;

        static {
            int[] iArr = new int[HvacSwitchStatus.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacSwitchStatus = iArr;
            try {
                iArr[HvacSwitchStatus.OFF.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacSwitchStatus[HvacSwitchStatus.ON.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacSwitchStatus[HvacSwitchStatus.ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public static int toHvacCmd(HvacSwitchStatus hvacSwitchStatus) {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacSwitchStatus[hvacSwitchStatus.ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i == 3) {
                    return 2;
                }
                throw new IllegalArgumentException("Unknown hvacSwitchStatus: " + hvacSwitchStatus);
            }
            return 1;
        }
        return 0;
    }
}
