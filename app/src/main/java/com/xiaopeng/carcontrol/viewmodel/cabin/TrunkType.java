package com.xiaopeng.carcontrol.viewmodel.cabin;

/* loaded from: classes2.dex */
public enum TrunkType {
    Open,
    Pause,
    Close;

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.cabin.TrunkType$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$TrunkType;

        static {
            int[] iArr = new int[TrunkType.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$TrunkType = iArr;
            try {
                iArr[TrunkType.Open.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$TrunkType[TrunkType.Close.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$TrunkType[TrunkType.Pause.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public int toBcmTrunkType() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$TrunkType[ordinal()];
        int i2 = 1;
        if (i != 1) {
            i2 = 3;
            if (i != 2) {
                if (i == 3) {
                    return 2;
                }
                throw new IllegalArgumentException("Unknown TrunkType : " + this);
            }
        }
        return i2;
    }
}
