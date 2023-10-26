package com.xiaopeng.carcontrol.viewmodel.sfs;

/* loaded from: classes2.dex */
public enum SfsChannel {
    Channel1,
    Channel2,
    Channel3;

    public static SfsChannel fromHvacSfsChannel(int sfsChannel) {
        if (sfsChannel != 1) {
            if (sfsChannel == 2) {
                return Channel3;
            }
            return Channel1;
        }
        return Channel2;
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.sfs.SfsChannel$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$sfs$SfsChannel;

        static {
            int[] iArr = new int[SfsChannel.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$sfs$SfsChannel = iArr;
            try {
                iArr[SfsChannel.Channel1.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$sfs$SfsChannel[SfsChannel.Channel2.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$sfs$SfsChannel[SfsChannel.Channel3.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public int toSfsCmd() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$sfs$SfsChannel[ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i == 3) {
                    return 2;
                }
                throw new IllegalArgumentException("Unknown SFS channel: " + this);
            }
            return 1;
        }
        return 0;
    }
}
