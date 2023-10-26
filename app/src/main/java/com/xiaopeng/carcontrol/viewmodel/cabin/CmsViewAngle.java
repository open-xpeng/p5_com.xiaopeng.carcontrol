package com.xiaopeng.carcontrol.viewmodel.cabin;

/* loaded from: classes2.dex */
public enum CmsViewAngle {
    NO_COMMAND,
    FLAT,
    WIDE;

    public static CmsViewAngle fromBcmViewAngle(int viewAngle) {
        if (viewAngle != 0) {
            if (viewAngle != 1) {
                if (viewAngle == 2) {
                    return WIDE;
                }
                throw new IllegalArgumentException("Unknown cms view angle");
            }
            return FLAT;
        }
        return NO_COMMAND;
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.cabin.CmsViewAngle$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$CmsViewAngle;

        static {
            int[] iArr = new int[CmsViewAngle.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$CmsViewAngle = iArr;
            try {
                iArr[CmsViewAngle.FLAT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$CmsViewAngle[CmsViewAngle.WIDE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public int toBcmViewAngle() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$CmsViewAngle[ordinal()];
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                return 0;
            }
        }
        return i2;
    }
}
