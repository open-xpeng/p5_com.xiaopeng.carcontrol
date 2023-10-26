package com.xiaopeng.carcontrol.viewmodel.lamp;

/* loaded from: classes2.dex */
public enum LampMode {
    Off,
    Position,
    LowBeam,
    Auto,
    Far;

    public static LampMode fromBcmState(int value) {
        if (value != 0) {
            if (value != 1) {
                if (value != 2) {
                    if (value != 3) {
                        return null;
                    }
                    return Auto;
                }
                return LowBeam;
            }
            return Position;
        }
        return Off;
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.lamp.LampMode$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LampMode;

        static {
            int[] iArr = new int[LampMode.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LampMode = iArr;
            try {
                iArr[LampMode.Off.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LampMode[LampMode.Position.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LampMode[LampMode.LowBeam.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LampMode[LampMode.Auto.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public int toBcmLampCmd() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LampMode[ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i == 4) {
                        return 3;
                    }
                    throw new IllegalArgumentException("Unknown head lamp state: " + this);
                }
                return 2;
            }
            return 1;
        }
        return 0;
    }
}
