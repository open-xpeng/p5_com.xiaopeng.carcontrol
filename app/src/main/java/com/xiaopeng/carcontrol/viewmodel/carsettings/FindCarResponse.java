package com.xiaopeng.carcontrol.viewmodel.carsettings;

/* loaded from: classes2.dex */
public enum FindCarResponse {
    LightAndHorn,
    Light;

    public static FindCarResponse fromBcmState(int value) {
        if (value != 1) {
            if (value != 2) {
                return null;
            }
            return Light;
        }
        return LightAndHorn;
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.carsettings.FindCarResponse$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$FindCarResponse;

        static {
            int[] iArr = new int[FindCarResponse.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$FindCarResponse = iArr;
            try {
                iArr[FindCarResponse.LightAndHorn.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$FindCarResponse[FindCarResponse.Light.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public int toBcmCmd() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$FindCarResponse[ordinal()];
        if (i != 1) {
            if (i == 2) {
                return 2;
            }
            throw new IllegalArgumentException("Unknown find car response type:" + this);
        }
        return 1;
    }
}
