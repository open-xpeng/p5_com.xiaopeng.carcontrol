package com.xiaopeng.carcontrol.viewmodel.lamp;

/* loaded from: classes2.dex */
public enum DomeLightState {
    Auto,
    On,
    Off;

    public static DomeLightState fromBcmDomeStatus(int status) {
        if (status != 1) {
            if (status != 2) {
                if (status != 3) {
                    return null;
                }
                return Off;
            }
            return On;
        }
        return Auto;
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.lamp.DomeLightState$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$DomeLightState;

        static {
            int[] iArr = new int[DomeLightState.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$DomeLightState = iArr;
            try {
                iArr[DomeLightState.Auto.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$DomeLightState[DomeLightState.On.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$DomeLightState[DomeLightState.Off.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public int toBcmDomeCmd() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$DomeLightState[ordinal()];
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                if (i == 3) {
                    return 3;
                }
                throw new IllegalArgumentException("Unknown dome light state: " + this);
            }
        }
        return i2;
    }
}
