package com.xiaopeng.carcontrol.viewmodel.lamp;

import android.text.TextUtils;
import com.xiaopeng.carcontrol.carmanager.controller.ILluController;
import com.xiaopeng.carcontrolmodule.R;

/* loaded from: classes2.dex */
public enum LluEffect {
    Dance(R.string.llu_effect_dance, R.string.llu_effect_dance_desc),
    SayHi(R.string.llu_effect_show_off, R.string.llu_effect_show_off_desc),
    SayHi2(R.string.llu_effect_show_off, R.string.llu_effect_show_off_desc),
    SayHi3(R.string.llu_effect_show_off, R.string.llu_effect_show_off_desc),
    FindCar(R.string.llu_effect_find_car, R.string.llu_effect_find_car_desc),
    AwakeWait(R.string.llu_effect_awake, R.string.llu_effect_awake_desc),
    AwakeWait2(R.string.llu_effect_awake, R.string.llu_effect_awake_desc),
    AwakeWait3(R.string.llu_effect_awake, R.string.llu_effect_awake_desc),
    Sleep(R.string.llu_effect_sleep, R.string.llu_effect_sleep_desc),
    Sleep2(R.string.llu_effect_sleep, R.string.llu_effect_sleep_desc),
    Sleep3(R.string.llu_effect_sleep, R.string.llu_effect_sleep_desc),
    DcCharged(R.string.llu_effect_dc_charge, R.string.llu_effect_dc_charge_desc),
    AcCharged(R.string.llu_effect_ac_charge, R.string.llu_effect_ac_charge_desc),
    FullCharged(R.string.llu_effect_full_charged, R.string.llu_effect_full_charged_desc),
    TakePhoto(R.string.llu_effect_take_photo, R.string.llu_effect_take_photo),
    SYNC_DANCE_SHOW(R.string.llu_effect_sync_dance_show_title, R.string.llu_effect_sync_dance_show_desc),
    SYNC_DANCE_01_SHOW(R.string.llu_effect_sync_remote_show_01_title, R.string.llu_effect_sync_dance_show_desc),
    SYNC_DANCE_02_SHOW(R.string.llu_effect_sync_remote_show_02_title, R.string.llu_effect_sync_dance_show_desc),
    SYNC_DANCE_03_SHOW(R.string.llu_effect_sync_remote_show_03_title, R.string.llu_effect_sync_dance_show_desc);
    
    private int mDescId;
    private int mTitleId;

    LluEffect(int titleId, int descId) {
        this.mTitleId = titleId;
        this.mDescId = descId;
    }

    public int getTitleId() {
        return this.mTitleId;
    }

    public int getDescId() {
        return this.mDescId;
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.lamp.LluEffect$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluEffect;

        static {
            int[] iArr = new int[LluEffect.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluEffect = iArr;
            try {
                iArr[LluEffect.FindCar.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluEffect[LluEffect.AwakeWait.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluEffect[LluEffect.AwakeWait2.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluEffect[LluEffect.AwakeWait3.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluEffect[LluEffect.FullCharged.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluEffect[LluEffect.AcCharged.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluEffect[LluEffect.DcCharged.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluEffect[LluEffect.Sleep.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluEffect[LluEffect.Sleep2.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluEffect[LluEffect.Sleep3.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluEffect[LluEffect.SayHi.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluEffect[LluEffect.SayHi2.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluEffect[LluEffect.SayHi3.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluEffect[LluEffect.TakePhoto.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluEffect[LluEffect.Dance.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluEffect[LluEffect.SYNC_DANCE_SHOW.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluEffect[LluEffect.SYNC_DANCE_01_SHOW.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluEffect[LluEffect.SYNC_DANCE_02_SHOW.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluEffect[LluEffect.SYNC_DANCE_03_SHOW.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
        }
    }

    public String toLluCmd() {
        switch (AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluEffect[ordinal()]) {
            case 1:
                return ILluController.LLU_EFFECT_FIND_CAR;
            case 2:
                return ILluController.LLU_EFFECT_AWAKE;
            case 3:
                return ILluController.LLU_EFFECT_AWAKE2;
            case 4:
                return ILluController.LLU_EFFECT_AWAKE3;
            case 5:
                return ILluController.LLU_EFFECT_FULL_CHARGED;
            case 6:
                return ILluController.LLU_EFFECT_AC_CHARGE;
            case 7:
                return ILluController.LLU_EFFECT_DC_CHARGE;
            case 8:
                return ILluController.LLU_EFFECT_SLEEP;
            case 9:
                return ILluController.LLU_EFFECT_SLEEP2;
            case 10:
                return ILluController.LLU_EFFECT_SLEEP3;
            case 11:
                return ILluController.LLU_EFFECT_SAYHI;
            case 12:
                return ILluController.LLU_EFFECT_SAYHI2;
            case 13:
                return ILluController.LLU_EFFECT_SAYHI3;
            case 14:
                return ILluController.LLU_EFFECT_TAKE_PHOTO;
            case 15:
                return ILluController.LLU_EFFECT_DANCE;
            case 16:
                return ILluController.LLU_SYNC_DANCE_SHOW;
            case 17:
                return ILluController.LLU_SYNC_DANCE_SHOW_01;
            case 18:
                return ILluController.LLU_SYNC_DANCE_SHOW_02;
            case 19:
                return ILluController.LLU_SYNC_DANCE_SHOW_03;
            default:
                return null;
        }
    }

    public int toXuiCmd() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluEffect[ordinal()];
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                i2 = 6;
                if (i != 6) {
                    i2 = 7;
                    if (i != 7) {
                        if (i != 8) {
                            if (i != 11) {
                                if (i == 14) {
                                    return 9;
                                }
                                throw new IllegalArgumentException("can convert LluEffect: " + this);
                            }
                            return 10;
                        }
                        return 5;
                    }
                }
            }
        }
        return i2;
    }

    public static LluEffect fromLluCmd(String effectName) {
        if (!TextUtils.isEmpty(effectName)) {
            effectName.hashCode();
            char c = 65535;
            switch (effectName.hashCode()) {
                case -1217166933:
                    if (effectName.equals(ILluController.LLU_EFFECT_FIND_CAR)) {
                        c = 0;
                        break;
                    }
                    break;
                case -1115935377:
                    if (effectName.equals(ILluController.LLU_EFFECT_DANCE)) {
                        c = 1;
                        break;
                    }
                    break;
                case -382043964:
                    if (effectName.equals(ILluController.LLU_EFFECT_SAYHI)) {
                        c = 2;
                        break;
                    }
                    break;
                case -382043963:
                    if (effectName.equals(ILluController.LLU_EFFECT_SAYHI2)) {
                        c = 3;
                        break;
                    }
                    break;
                case -382043962:
                    if (effectName.equals(ILluController.LLU_EFFECT_SAYHI3)) {
                        c = 4;
                        break;
                    }
                    break;
                case -25292859:
                    if (effectName.equals(ILluController.LLU_EFFECT_SLEEP)) {
                        c = 5;
                        break;
                    }
                    break;
                case -25292858:
                    if (effectName.equals(ILluController.LLU_EFFECT_SLEEP2)) {
                        c = 6;
                        break;
                    }
                    break;
                case -25292857:
                    if (effectName.equals(ILluController.LLU_EFFECT_SLEEP3)) {
                        c = 7;
                        break;
                    }
                    break;
                case 150947596:
                    if (effectName.equals(ILluController.LLU_EFFECT_AWAKE)) {
                        c = '\b';
                        break;
                    }
                    break;
                case 150947597:
                    if (effectName.equals(ILluController.LLU_EFFECT_AWAKE2)) {
                        c = '\t';
                        break;
                    }
                    break;
                case 150947598:
                    if (effectName.equals(ILluController.LLU_EFFECT_AWAKE3)) {
                        c = '\n';
                        break;
                    }
                    break;
                case 722989042:
                    if (effectName.equals(ILluController.LLU_EFFECT_AC_CHARGE)) {
                        c = 11;
                        break;
                    }
                    break;
                case 722989135:
                    if (effectName.equals(ILluController.LLU_EFFECT_DC_CHARGE)) {
                        c = '\f';
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    return FindCar;
                case 1:
                    return Dance;
                case 2:
                case 3:
                case 4:
                    return SayHi;
                case 5:
                case 6:
                case 7:
                    return Sleep;
                case '\b':
                case '\t':
                case '\n':
                    return AwakeWait;
                case 11:
                    return AcCharged;
                case '\f':
                    return DcCharged;
            }
        }
        return null;
    }
}
