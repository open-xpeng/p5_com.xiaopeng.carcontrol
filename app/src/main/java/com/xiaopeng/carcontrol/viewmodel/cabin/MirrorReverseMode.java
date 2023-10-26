package com.xiaopeng.carcontrol.viewmodel.cabin;

/* loaded from: classes2.dex */
public enum MirrorReverseMode {
    Off,
    Left,
    Right,
    Both;

    public static MirrorReverseMode fromBcmMirrorState(int value) {
        if (value != 0) {
            if (value != 1) {
                if (value == 2) {
                    return Right;
                }
                return Both;
            }
            return Left;
        }
        return Off;
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.cabin.MirrorReverseMode$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$MirrorReverseMode;

        static {
            int[] iArr = new int[MirrorReverseMode.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$MirrorReverseMode = iArr;
            try {
                iArr[MirrorReverseMode.Off.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$MirrorReverseMode[MirrorReverseMode.Left.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$MirrorReverseMode[MirrorReverseMode.Right.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$MirrorReverseMode[MirrorReverseMode.Both.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public int toBcmMirrorCmd() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$MirrorReverseMode[ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i == 4) {
                        return 3;
                    }
                    throw new IllegalArgumentException("Unknown mirror reverse mode: " + this);
                }
                return 2;
            }
            return 1;
        }
        return 0;
    }
}
