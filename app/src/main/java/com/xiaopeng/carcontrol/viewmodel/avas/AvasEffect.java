package com.xiaopeng.carcontrol.viewmodel.avas;

/* loaded from: classes2.dex */
public enum AvasEffect {
    SoundEffect1,
    SoundEffect2,
    SoundEffect3,
    SoundEffect4;

    public static AvasEffect fromAvasType(int value) {
        if (value != 1) {
            if (value != 2) {
                if (value != 3) {
                    if (value != 4) {
                        return null;
                    }
                    return SoundEffect4;
                }
                return SoundEffect3;
            }
            return SoundEffect2;
        }
        return SoundEffect1;
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.avas.AvasEffect$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$avas$AvasEffect;

        static {
            int[] iArr = new int[AvasEffect.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$avas$AvasEffect = iArr;
            try {
                iArr[AvasEffect.SoundEffect1.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$avas$AvasEffect[AvasEffect.SoundEffect2.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$avas$AvasEffect[AvasEffect.SoundEffect3.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$avas$AvasEffect[AvasEffect.SoundEffect4.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public int toAvasType() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$avas$AvasEffect[ordinal()];
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                i2 = 3;
                if (i != 3) {
                    if (i == 4) {
                        return 4;
                    }
                    throw new IllegalArgumentException("Unknown avas effect: " + this);
                }
            }
        }
        return i2;
    }
}
