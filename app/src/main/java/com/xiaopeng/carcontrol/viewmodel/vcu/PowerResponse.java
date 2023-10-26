package com.xiaopeng.carcontrol.viewmodel.vcu;

/* loaded from: classes2.dex */
public enum PowerResponse {
    Standard,
    Slow,
    Fast,
    SuperSlow,
    SuperFast,
    Intelligent;

    public static PowerResponse fromVcuState(int value) {
        switch (value) {
            case 1:
                return Standard;
            case 2:
                return Slow;
            case 3:
                return Fast;
            case 4:
                return SuperSlow;
            case 5:
                return SuperFast;
            case 6:
                return Intelligent;
            default:
                return null;
        }
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.vcu.PowerResponse$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$PowerResponse;

        static {
            int[] iArr = new int[PowerResponse.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$PowerResponse = iArr;
            try {
                iArr[PowerResponse.Standard.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$PowerResponse[PowerResponse.Slow.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$PowerResponse[PowerResponse.Fast.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$PowerResponse[PowerResponse.SuperSlow.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$PowerResponse[PowerResponse.SuperFast.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$PowerResponse[PowerResponse.Intelligent.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public int toVcuCmd() {
        switch (AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$PowerResponse[ordinal()]) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
                return 6;
            default:
                throw new IllegalArgumentException("Unknown Power Response mode: " + this);
        }
    }
}
