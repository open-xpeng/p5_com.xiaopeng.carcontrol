package com.xiaopeng.carcontrol.viewmodel.cabin;

/* loaded from: classes2.dex */
public enum MirrorDirection {
    Left,
    Right,
    Up,
    Down;

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.cabin.MirrorDirection$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$MirrorDirection;

        static {
            int[] iArr = new int[MirrorDirection.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$MirrorDirection = iArr;
            try {
                iArr[MirrorDirection.Left.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$MirrorDirection[MirrorDirection.Right.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$MirrorDirection[MirrorDirection.Up.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$MirrorDirection[MirrorDirection.Down.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public int toBcmDirection() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$MirrorDirection[ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i == 4) {
                        return 2;
                    }
                    throw new IllegalArgumentException("Unknown mirror direction: " + this);
                }
                return 1;
            }
            return 4;
        }
        return 3;
    }
}
