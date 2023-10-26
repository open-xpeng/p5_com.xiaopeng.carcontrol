package com.xiaopeng.carcontrol.viewmodel.chassis;

/* loaded from: classes2.dex */
public enum TpmsCalibrationStatus {
    NotFix,
    Fixing,
    Fixed,
    Fail;

    public static TpmsCalibrationStatus fromTpmsState(int state) {
        if (state != 0) {
            if (state != 1) {
                if (state != 2) {
                    if (state != 3) {
                        return null;
                    }
                    return Fail;
                }
                return Fixed;
            }
            return Fixing;
        }
        return NotFix;
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.chassis.TpmsCalibrationStatus$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$chassis$TpmsCalibrationStatus;

        static {
            int[] iArr = new int[TpmsCalibrationStatus.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$chassis$TpmsCalibrationStatus = iArr;
            try {
                iArr[TpmsCalibrationStatus.Fixing.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$chassis$TpmsCalibrationStatus[TpmsCalibrationStatus.Fixed.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$chassis$TpmsCalibrationStatus[TpmsCalibrationStatus.Fail.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$chassis$TpmsCalibrationStatus[TpmsCalibrationStatus.NotFix.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public int toTpmsCode() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$chassis$TpmsCalibrationStatus[ordinal()];
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                i2 = 3;
                if (i != 3) {
                    return 0;
                }
            }
        }
        return i2;
    }
}
