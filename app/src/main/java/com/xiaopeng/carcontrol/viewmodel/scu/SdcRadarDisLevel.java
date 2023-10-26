package com.xiaopeng.carcontrol.viewmodel.scu;

/* loaded from: classes2.dex */
public enum SdcRadarDisLevel {
    Off,
    Level1,
    Level2;

    public static SdcRadarDisLevel fromScuState(int value) {
        if (value != 1) {
            if (value == 2) {
                return Level2;
            }
            return Off;
        }
        return Level1;
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.scu.SdcRadarDisLevel$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$SdcRadarDisLevel;

        static {
            int[] iArr = new int[SdcRadarDisLevel.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$SdcRadarDisLevel = iArr;
            try {
                iArr[SdcRadarDisLevel.Level1.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$SdcRadarDisLevel[SdcRadarDisLevel.Level2.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public int toScuCode() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$SdcRadarDisLevel[ordinal()];
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                return 0;
            }
        }
        return i2;
    }

    public String toStringParams() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$SdcRadarDisLevel[ordinal()];
        return i != 1 ? i != 2 ? "0" : "2" : "1";
    }
}
