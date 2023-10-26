package com.xiaopeng.carcontrol.viewmodel.chassis;

/* loaded from: classes2.dex */
public enum TrailerHitchStatus {
    Close,
    Open,
    Closing,
    Opening,
    Fault;

    public static TrailerHitchStatus fromBcCmd(int value) {
        if (value != 0) {
            if (value != 1) {
                if (value != 2) {
                    if (value != 4) {
                        if (value != 5) {
                            return null;
                        }
                        return Opening;
                    }
                    return Closing;
                }
                return Fault;
            }
            return Open;
        }
        return Close;
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.chassis.TrailerHitchStatus$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$chassis$TrailerHitchStatus;

        static {
            int[] iArr = new int[TrailerHitchStatus.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$chassis$TrailerHitchStatus = iArr;
            try {
                iArr[TrailerHitchStatus.Close.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$chassis$TrailerHitchStatus[TrailerHitchStatus.Open.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$chassis$TrailerHitchStatus[TrailerHitchStatus.Closing.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$chassis$TrailerHitchStatus[TrailerHitchStatus.Opening.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$chassis$TrailerHitchStatus[TrailerHitchStatus.Fault.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public int toBcmCmd() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$chassis$TrailerHitchStatus[ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i != 4) {
                        if (i == 5) {
                            return 2;
                        }
                        throw new IllegalArgumentException("UnknownTrailer HitchStatus: " + this);
                    }
                    return 5;
                }
                return 4;
            }
            return 1;
        }
        return 0;
    }
}
