package com.xiaopeng.carcontrol.viewmodel.carsettings;

/* loaded from: classes2.dex */
public enum WiperInterval {
    Slow,
    Medium,
    Fast,
    Ultra,
    Auto;

    public static WiperInterval fromBcmState(int value) {
        if (value != 1) {
            if (value != 2) {
                if (value != 3) {
                    if (value != 4) {
                        return null;
                    }
                    return Slow;
                }
                return Medium;
            }
            return Fast;
        }
        return Ultra;
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.carsettings.WiperInterval$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$WiperInterval;

        static {
            int[] iArr = new int[WiperInterval.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$WiperInterval = iArr;
            try {
                iArr[WiperInterval.Slow.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$WiperInterval[WiperInterval.Medium.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$WiperInterval[WiperInterval.Fast.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$WiperInterval[WiperInterval.Ultra.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$WiperInterval[WiperInterval.Auto.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public int toBcmCmd() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$WiperInterval[ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i == 4) {
                        return 1;
                    }
                    throw new IllegalArgumentException("Unknown wiper interval type: " + this);
                }
                return 2;
            }
            return 3;
        }
        return 4;
    }

    public static WiperInterval fromCiuState(int value) {
        if (value != 0) {
            if (value != 1) {
                if (value != 2) {
                    if (value != 3) {
                        if (value != 4) {
                            return null;
                        }
                        return Ultra;
                    }
                    return Fast;
                }
                return Medium;
            }
            return Slow;
        }
        return Auto;
    }

    public int toCiuCmd() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$WiperInterval[ordinal()];
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                i2 = 3;
                if (i != 3) {
                    i2 = 4;
                    if (i != 4) {
                        if (i == 5) {
                            return 0;
                        }
                        throw new IllegalArgumentException("Unknown ciu rain wiper interval type: " + this);
                    }
                }
            }
        }
        return i2;
    }

    public static WiperInterval fromSensitivityBcmState(int value) {
        if (value != 1) {
            if (value != 2) {
                if (value != 3) {
                    if (value != 4) {
                        return null;
                    }
                    return Ultra;
                }
                return Fast;
            }
            return Medium;
        }
        return Slow;
    }

    public int toSensitivityBcmCmd() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$WiperInterval[ordinal()];
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                i2 = 3;
                if (i != 3) {
                    if (i == 4) {
                        return 4;
                    }
                    throw new IllegalArgumentException("Unknown wiper sensitivity type: " + this);
                }
            }
        }
        return i2;
    }

    public static WiperInterval getIndexValue(int index) {
        try {
            return values()[index];
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }
}
