package com.xiaopeng.carcontrol.viewmodel.carsettings;

/* loaded from: classes2.dex */
public enum UnlockResponse {
    Light,
    LightAndHorn,
    LightAndAvas;

    public static UnlockResponse fromBcmState(int value) {
        if (value != 0) {
            if (value == 2) {
                return LightAndAvas;
            }
            return Light;
        }
        return LightAndHorn;
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.carsettings.UnlockResponse$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$UnlockResponse;

        static {
            int[] iArr = new int[UnlockResponse.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$UnlockResponse = iArr;
            try {
                iArr[UnlockResponse.LightAndHorn.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$UnlockResponse[UnlockResponse.LightAndAvas.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$UnlockResponse[UnlockResponse.Light.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public int toBcmCmd() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$UnlockResponse[ordinal()];
        if (i != 1) {
            return i != 2 ? 1 : 2;
        }
        return 0;
    }
}
