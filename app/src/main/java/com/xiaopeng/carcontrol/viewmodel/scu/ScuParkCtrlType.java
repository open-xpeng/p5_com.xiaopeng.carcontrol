package com.xiaopeng.carcontrol.viewmodel.scu;

/* loaded from: classes2.dex */
public enum ScuParkCtrlType {
    LongPress,
    Double;

    public static ScuParkCtrlType fromScuState(int value) {
        if (value != 1) {
            if (value == 2) {
                return LongPress;
            }
            throw new IllegalArgumentException("Unknown control type: " + value);
        }
        return Double;
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.scu.ScuParkCtrlType$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuParkCtrlType;

        static {
            int[] iArr = new int[ScuParkCtrlType.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuParkCtrlType = iArr;
            try {
                iArr[ScuParkCtrlType.Double.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuParkCtrlType[ScuParkCtrlType.LongPress.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public int toScuCmd() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuParkCtrlType[ordinal()];
        if (i != 1) {
            if (i == 2) {
                return 2;
            }
            throw new IllegalArgumentException("Unknown control type: " + this);
        }
        return 1;
    }
}
