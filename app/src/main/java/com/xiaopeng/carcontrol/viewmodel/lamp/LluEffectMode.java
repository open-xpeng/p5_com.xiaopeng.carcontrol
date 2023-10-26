package com.xiaopeng.carcontrol.viewmodel.lamp;

/* loaded from: classes2.dex */
public enum LluEffectMode {
    Mode1,
    Mode2,
    Mode3;

    public static LluEffectMode fromLluValue(int mode) {
        if (mode != 1) {
            if (mode != 2) {
                if (mode == 3) {
                    return Mode3;
                }
                throw new IllegalArgumentException("Unknown llu effect mode value: " + mode);
            }
            return Mode2;
        }
        return Mode1;
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.lamp.LluEffectMode$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluEffectMode;

        static {
            int[] iArr = new int[LluEffectMode.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluEffectMode = iArr;
            try {
                iArr[LluEffectMode.Mode1.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluEffectMode[LluEffectMode.Mode2.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluEffectMode[LluEffectMode.Mode3.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public int toLluCmd() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluEffectMode[ordinal()];
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                if (i == 3) {
                    return 3;
                }
                throw new IllegalArgumentException("Unknown llu effect mode: " + this);
            }
        }
        return i2;
    }
}
