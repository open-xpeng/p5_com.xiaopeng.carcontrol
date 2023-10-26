package com.xiaopeng.carcontrol.viewmodel.vcu;

import com.xiaopeng.carcontrol.config.CarBaseConfig;

/* loaded from: classes2.dex */
public enum DriveMode {
    Eco,
    EcoPlus,
    EcoPlusOff,
    Comfort,
    ComfortOff,
    Sport,
    Normal,
    Adaptive,
    OffRoad,
    NoCommand;

    public static DriveMode fromVcuDriveMode(int value) {
        if (value != 0) {
            if (value == 1) {
                return Eco;
            }
            if (value == 2) {
                return Sport;
            }
            if (value == 5) {
                return EcoPlus;
            }
            if (value == 6) {
                return EcoPlusOff;
            }
            if (value == 7) {
                return Comfort;
            }
            if (value == 8) {
                return ComfortOff;
            }
            if (value != 10) {
                if (value != 12) {
                    if (value != 14) {
                        if (value != 16) {
                            return null;
                        }
                        return OffRoad;
                    }
                    return Adaptive;
                }
                return NoCommand;
            }
        }
        return Normal;
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.vcu.DriveMode$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$DriveMode;

        static {
            int[] iArr = new int[DriveMode.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$DriveMode = iArr;
            try {
                iArr[DriveMode.Normal.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$DriveMode[DriveMode.Eco.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$DriveMode[DriveMode.EcoPlus.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$DriveMode[DriveMode.EcoPlusOff.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$DriveMode[DriveMode.Sport.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$DriveMode[DriveMode.Comfort.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$DriveMode[DriveMode.ComfortOff.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$DriveMode[DriveMode.Adaptive.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$DriveMode[DriveMode.OffRoad.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$DriveMode[DriveMode.NoCommand.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
        }
    }

    public int toVcuCmd() {
        switch (AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$DriveMode[ordinal()]) {
            case 1:
                return CarBaseConfig.getInstance().isSupportNormalDriveMode() ? 10 : 0;
            case 2:
                return 1;
            case 3:
                return 5;
            case 4:
                return 6;
            case 5:
                return 2;
            case 6:
                return 7;
            case 7:
                return 8;
            case 8:
                return 14;
            case 9:
                return 16;
            case 10:
                return 12;
            default:
                throw new IllegalArgumentException("Unknown drive mode: " + this);
        }
    }
}
