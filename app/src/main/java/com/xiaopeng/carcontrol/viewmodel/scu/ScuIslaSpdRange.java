package com.xiaopeng.carcontrol.viewmodel.scu;

/* loaded from: classes2.dex */
public enum ScuIslaSpdRange {
    LOW,
    HIGH;

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.scu.ScuIslaSpdRange$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuIslaSpdRange;

        static {
            int[] iArr = new int[ScuIslaSpdRange.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuIslaSpdRange = iArr;
            try {
                iArr[ScuIslaSpdRange.LOW.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuIslaSpdRange[ScuIslaSpdRange.HIGH.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public int toDisplayIndex() {
        return AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuIslaSpdRange[ordinal()] != 1 ? 1 : 0;
    }

    public static ScuIslaSpdRange fromScuState(int value) {
        if (value == 1) {
            return LOW;
        }
        return HIGH;
    }

    public int toScuCmd() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuIslaSpdRange[ordinal()];
        if (i != 1) {
            if (i == 2) {
                return 2;
            }
            throw new IllegalArgumentException("Unknown ISLA speed range type: " + this);
        }
        return 1;
    }
}
