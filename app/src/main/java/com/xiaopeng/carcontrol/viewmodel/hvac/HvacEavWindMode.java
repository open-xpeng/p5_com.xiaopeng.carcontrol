package com.xiaopeng.carcontrol.viewmodel.hvac;

/* loaded from: classes2.dex */
public enum HvacEavWindMode {
    Single(1),
    Mirror(2),
    Free(3),
    NoDisplay(6);
    
    private int value;

    HvacEavWindMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static HvacEavWindMode fromHvacState(int value) {
        if (value != 1) {
            if (value != 2) {
                if (value != 3) {
                    if (value != 6) {
                        return null;
                    }
                    return NoDisplay;
                }
                return Free;
            }
            return Mirror;
        }
        return Single;
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.hvac.HvacEavWindMode$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacEavWindMode;

        static {
            int[] iArr = new int[HvacEavWindMode.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacEavWindMode = iArr;
            try {
                iArr[HvacEavWindMode.Single.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacEavWindMode[HvacEavWindMode.Mirror.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public static int toHvacCmd(HvacEavWindMode mode) {
        if (mode == null) {
            return 3;
        }
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacEavWindMode[mode.ordinal()];
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                return 3;
            }
        }
        return i2;
    }
}
