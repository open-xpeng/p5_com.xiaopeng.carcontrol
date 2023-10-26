package com.xiaopeng.carcontrol.viewmodel.lamp;

/* loaded from: classes2.dex */
public enum LluUnlockEffect {
    EffectA,
    EffectB,
    EffectC;

    public static LluUnlockEffect fromLluValue(int mode) {
        if (mode != 2) {
            if (mode == 3) {
                return EffectC;
            }
            return EffectA;
        }
        return EffectB;
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.lamp.LluUnlockEffect$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluUnlockEffect;

        static {
            int[] iArr = new int[LluUnlockEffect.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluUnlockEffect = iArr;
            try {
                iArr[LluUnlockEffect.EffectA.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluUnlockEffect[LluUnlockEffect.EffectB.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluUnlockEffect[LluUnlockEffect.EffectC.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public int toLluCmd() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluUnlockEffect[ordinal()];
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                if (i == 3) {
                    return 3;
                }
                throw new IllegalArgumentException("Unknown llu unlock effect: " + this);
            }
        }
        return i2;
    }
}
