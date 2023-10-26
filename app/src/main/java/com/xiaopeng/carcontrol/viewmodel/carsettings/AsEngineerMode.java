package com.xiaopeng.carcontrol.viewmodel.carsettings;

/* loaded from: classes2.dex */
public enum AsEngineerMode {
    NO_REQUEST,
    MAINTENANCE,
    TRANSPORT,
    TRAILER,
    CAMPING;

    public static AsEngineerMode fromBcmState(int value) {
        if (value != 0) {
            if (value != 1) {
                if (value != 2) {
                    if (value != 3) {
                        if (value != 6) {
                            return null;
                        }
                        return CAMPING;
                    }
                    return TRAILER;
                }
                return TRANSPORT;
            }
            return MAINTENANCE;
        }
        return NO_REQUEST;
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.carsettings.AsEngineerMode$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$AsEngineerMode;

        static {
            int[] iArr = new int[AsEngineerMode.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$AsEngineerMode = iArr;
            try {
                iArr[AsEngineerMode.NO_REQUEST.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$AsEngineerMode[AsEngineerMode.MAINTENANCE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$AsEngineerMode[AsEngineerMode.TRANSPORT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$AsEngineerMode[AsEngineerMode.CAMPING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public int toBcmCmd() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$AsEngineerMode[ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i == 4) {
                        return 6;
                    }
                    throw new IllegalArgumentException("Unknown As Engineer: " + this);
                }
                return 2;
            }
            return 1;
        }
        return 0;
    }
}
