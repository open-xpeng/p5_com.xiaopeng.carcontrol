package com.xiaopeng.carcontrol.viewmodel.cabin;

/* loaded from: classes2.dex */
public enum WindowKeyCtrlMode {
    Off,
    Auto,
    Manual;

    public static WindowKeyCtrlMode fromBcmState(int value) {
        if (value != 1) {
            if (value != 2) {
                if (value != 3) {
                    return null;
                }
                return Off;
            }
            return Manual;
        }
        return Auto;
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.cabin.WindowKeyCtrlMode$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$WindowKeyCtrlMode;

        static {
            int[] iArr = new int[WindowKeyCtrlMode.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$WindowKeyCtrlMode = iArr;
            try {
                iArr[WindowKeyCtrlMode.Off.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$WindowKeyCtrlMode[WindowKeyCtrlMode.Auto.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$WindowKeyCtrlMode[WindowKeyCtrlMode.Manual.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public int toBcmCmd() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$WindowKeyCtrlMode[ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i == 3) {
                    return 2;
                }
                throw new IllegalArgumentException("Unknown key control mode: " + this);
            }
            return 1;
        }
        return 3;
    }

    public static WindowKeyCtrlMode getIndexValue(int index) {
        try {
            return values()[index];
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }
}
