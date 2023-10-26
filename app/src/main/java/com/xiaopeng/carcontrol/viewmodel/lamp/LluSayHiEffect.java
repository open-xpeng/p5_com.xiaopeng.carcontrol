package com.xiaopeng.carcontrol.viewmodel.lamp;

import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrolmodule.R;

/* loaded from: classes2.dex */
public enum LluSayHiEffect {
    EffectA,
    EffectB,
    EffectC;
    
    private static final String TAG = "LluSayHiEffect";

    public static LluSayHiEffect fromLluValue(int mode) {
        if (mode != 1) {
            if (mode != 2) {
                if (mode == 3) {
                    return EffectC;
                }
                LogUtils.e(TAG, "Unknown llu sayhi effect mode value: " + mode + ", return default mode", false);
                return EffectA;
            }
            return EffectB;
        }
        return EffectA;
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.lamp.LluSayHiEffect$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluSayHiEffect;

        static {
            int[] iArr = new int[LluSayHiEffect.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluSayHiEffect = iArr;
            try {
                iArr[LluSayHiEffect.EffectA.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluSayHiEffect[LluSayHiEffect.EffectB.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluSayHiEffect[LluSayHiEffect.EffectC.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public int toLluCmd() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluSayHiEffect[ordinal()];
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                if (i == 3) {
                    return 3;
                }
                throw new IllegalArgumentException("Unknown llu sayhi effect: " + this);
            }
        }
        return i2;
    }

    public int getDescId() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluSayHiEffect[ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    return 0;
                }
                return R.string.llu_effect_sayhi_mode_3;
            }
            return R.string.llu_effect_sayhi_mode_2;
        }
        return R.string.llu_effect_sayhi_mode_1;
    }
}
