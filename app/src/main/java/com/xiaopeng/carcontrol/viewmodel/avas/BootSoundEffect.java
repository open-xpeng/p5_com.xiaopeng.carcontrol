package com.xiaopeng.carcontrol.viewmodel.avas;

/* loaded from: classes2.dex */
public enum BootSoundEffect {
    Off,
    EffectA,
    EffectB,
    EffectC,
    EffectD;

    public static BootSoundEffect fromXuiCmd(int type) {
        if (type != 0) {
            if (type != 1) {
                if (type != 2) {
                    if (type != 3) {
                        if (type != 4) {
                            return null;
                        }
                        return EffectD;
                    }
                    return EffectC;
                }
                return EffectB;
            }
            return EffectA;
        }
        return Off;
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.avas.BootSoundEffect$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$avas$BootSoundEffect;

        static {
            int[] iArr = new int[BootSoundEffect.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$avas$BootSoundEffect = iArr;
            try {
                iArr[BootSoundEffect.Off.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$avas$BootSoundEffect[BootSoundEffect.EffectB.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$avas$BootSoundEffect[BootSoundEffect.EffectC.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$avas$BootSoundEffect[BootSoundEffect.EffectD.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$avas$BootSoundEffect[BootSoundEffect.EffectA.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public int toXuiCmd() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$avas$BootSoundEffect[ordinal()];
        if (i != 1) {
            int i2 = 2;
            if (i != 2) {
                i2 = 3;
                if (i != 3) {
                    i2 = 4;
                    if (i != 4) {
                        return 1;
                    }
                }
            }
            return i2;
        }
        return 0;
    }
}
