package com.xiaopeng.carcontrol.viewmodel.strategy;

/* loaded from: classes2.dex */
public enum Direction {
    LL,
    LR,
    LU,
    LD,
    RL,
    RR,
    RU,
    RD;

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.strategy.Direction$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$strategy$Direction;

        static {
            int[] iArr = new int[Direction.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$strategy$Direction = iArr;
            try {
                iArr[Direction.RL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$strategy$Direction[Direction.RR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$strategy$Direction[Direction.RU.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$strategy$Direction[Direction.RD.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$strategy$Direction[Direction.LL.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$strategy$Direction[Direction.LR.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$strategy$Direction[Direction.LU.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$strategy$Direction[Direction.LD.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    public boolean isLeft() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$strategy$Direction[ordinal()];
        return (i == 1 || i == 2 || i == 3 || i == 4) ? false : true;
    }

    public Direction opposite() {
        switch (AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$strategy$Direction[ordinal()]) {
            case 1:
                return RR;
            case 2:
                return RL;
            case 3:
                return RD;
            case 4:
                return RU;
            case 5:
                return LR;
            case 6:
                return LL;
            case 7:
                return LD;
            case 8:
                return LU;
            default:
                return null;
        }
    }
}
