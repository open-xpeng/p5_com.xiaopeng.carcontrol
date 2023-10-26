package com.xiaopeng.carcontrol.viewmodel.chassis;

/* loaded from: classes2.dex */
public enum SteeringEpsMode {
    Soft,
    Standard,
    Sport;

    public static SteeringEpsMode fromEpsState(int value) {
        if (value != 0) {
            if (value != 1) {
                if (value != 2) {
                    return null;
                }
                return Sport;
            }
            return Soft;
        }
        return Standard;
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.chassis.SteeringEpsMode$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$chassis$SteeringEpsMode;

        static {
            int[] iArr = new int[SteeringEpsMode.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$chassis$SteeringEpsMode = iArr;
            try {
                iArr[SteeringEpsMode.Standard.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$chassis$SteeringEpsMode[SteeringEpsMode.Soft.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$chassis$SteeringEpsMode[SteeringEpsMode.Sport.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public int toEpsCmd() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$chassis$SteeringEpsMode[ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i == 3) {
                    return 2;
                }
                throw new IllegalArgumentException("Invalid steering eps mode: " + this);
            }
            return 1;
        }
        return 0;
    }
}
