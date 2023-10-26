package com.xiaopeng.carcontrol.viewmodel.meter;

/* loaded from: classes2.dex */
public enum AlarmVolume {
    Soft,
    Standard,
    Power;

    public static AlarmVolume fromIcmValue(int value) {
        if (value != 0) {
            if (value != 1) {
                if (value == 2) {
                    return Power;
                }
                throw new IllegalArgumentException("Unknown Meter alarm volume value: " + value);
            }
            return Standard;
        }
        return Soft;
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.meter.AlarmVolume$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$AlarmVolume;

        static {
            int[] iArr = new int[AlarmVolume.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$AlarmVolume = iArr;
            try {
                iArr[AlarmVolume.Soft.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$AlarmVolume[AlarmVolume.Standard.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$AlarmVolume[AlarmVolume.Power.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public int toIcmCmd() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$AlarmVolume[ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i == 3) {
                    return 2;
                }
                throw new IllegalArgumentException("Unknown AlarmVolume: " + this);
            }
            return 1;
        }
        return 0;
    }
}
