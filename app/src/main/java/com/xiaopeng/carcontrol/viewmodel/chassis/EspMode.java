package com.xiaopeng.carcontrol.viewmodel.chassis;

/* loaded from: classes2.dex */
public enum EspMode {
    Off,
    Normal,
    Sport;

    public static EspMode fromEspState(int mode) {
        if (mode != 1) {
            if (mode != 2) {
                if (mode == 3) {
                    return Off;
                }
                throw new IllegalArgumentException("Unknown esp status: " + mode);
            }
            return Sport;
        }
        return Normal;
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.chassis.EspMode$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$chassis$EspMode;

        static {
            int[] iArr = new int[EspMode.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$chassis$EspMode = iArr;
            try {
                iArr[EspMode.Off.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$chassis$EspMode[EspMode.Normal.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$chassis$EspMode[EspMode.Sport.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public int toEspCmd() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$chassis$EspMode[ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i == 3) {
                    return 2;
                }
                throw new IllegalArgumentException("Unknown esp mode: " + this);
            }
            return 1;
        }
        return 3;
    }
}
