package com.xiaopeng.carcontrol.viewmodel.cabin;

/* loaded from: classes2.dex */
public enum ArsWorkMode {
    MANUAL(0),
    AUTO(1);
    
    private int intValue;

    ArsWorkMode(int value) {
        this.intValue = value;
    }

    public int getIntValue() {
        return this.intValue;
    }

    public static ArsWorkMode fromIntValue(int mode) {
        ArsWorkMode[] values;
        for (ArsWorkMode arsWorkMode : values()) {
            if (arsWorkMode.intValue == mode) {
                return arsWorkMode;
            }
        }
        return null;
    }

    public static ArsWorkMode fromBcmValue(int mode) {
        if (mode != 1) {
            if (mode == 2) {
                return MANUAL;
            }
            return MANUAL;
        }
        return AUTO;
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.cabin.ArsWorkMode$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$ArsWorkMode;

        static {
            int[] iArr = new int[ArsWorkMode.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$ArsWorkMode = iArr;
            try {
                iArr[ArsWorkMode.MANUAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$ArsWorkMode[ArsWorkMode.AUTO.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public int toBcmValue() {
        return AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$ArsWorkMode[ordinal()] != 2 ? 2 : 1;
    }
}
