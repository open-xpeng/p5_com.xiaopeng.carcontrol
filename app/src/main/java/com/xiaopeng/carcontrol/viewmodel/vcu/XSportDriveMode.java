package com.xiaopeng.carcontrol.viewmodel.vcu;

/* loaded from: classes2.dex */
public enum XSportDriveMode {
    NoCommand,
    XPower,
    Geek,
    AI,
    Race;

    public static XSportDriveMode fromVcuState(int value) {
        if (value != 0) {
            if (value != 1) {
                if (value != 2) {
                    if (value != 3) {
                        if (value != 4) {
                            return null;
                        }
                        return Race;
                    }
                    return XPower;
                }
                return AI;
            }
            return Geek;
        }
        return NoCommand;
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.vcu.XSportDriveMode$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$XSportDriveMode;

        static {
            int[] iArr = new int[XSportDriveMode.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$XSportDriveMode = iArr;
            try {
                iArr[XSportDriveMode.XPower.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$XSportDriveMode[XSportDriveMode.Geek.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$XSportDriveMode[XSportDriveMode.AI.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$XSportDriveMode[XSportDriveMode.Race.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$XSportDriveMode[XSportDriveMode.NoCommand.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public int toVcuCmd() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$XSportDriveMode[ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i != 4) {
                        if (i == 5) {
                            return 0;
                        }
                        throw new IllegalArgumentException("Unknown XSport DriveMode mode: " + this);
                    }
                    return 4;
                }
                return 2;
            }
            return 1;
        }
        return 3;
    }
}
