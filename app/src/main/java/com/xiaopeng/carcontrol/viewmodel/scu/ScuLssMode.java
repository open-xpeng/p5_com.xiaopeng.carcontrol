package com.xiaopeng.carcontrol.viewmodel.scu;

/* loaded from: classes2.dex */
public enum ScuLssMode {
    Off,
    Ldw,
    Lka,
    All,
    Unavailable;

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.scu.ScuLssMode$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuLssMode;

        static {
            int[] iArr = new int[ScuLssMode.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuLssMode = iArr;
            try {
                iArr[ScuLssMode.Off.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuLssMode[ScuLssMode.Ldw.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuLssMode[ScuLssMode.Lka.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuLssMode[ScuLssMode.All.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuLssMode[ScuLssMode.Unavailable.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public int toDisplayIndex() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuLssMode[ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i != 4) {
                        if (i == 5) {
                            return 4;
                        }
                        throw new IllegalArgumentException("Unknown SCU LSS mode: " + this);
                    }
                    return 3;
                }
                return 2;
            }
            return 1;
        }
        return 0;
    }
}
