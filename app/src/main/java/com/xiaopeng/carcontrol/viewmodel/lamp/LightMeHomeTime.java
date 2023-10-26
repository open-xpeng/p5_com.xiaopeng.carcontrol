package com.xiaopeng.carcontrol.viewmodel.lamp;

/* loaded from: classes2.dex */
public enum LightMeHomeTime {
    Off,
    Time15s,
    Time30s,
    Time60s;

    public static LightMeHomeTime fromBcmValue(int timeValue) {
        if (timeValue != 0) {
            if (timeValue != 2) {
                if (timeValue == 3) {
                    return Time60s;
                }
                return Time15s;
            }
            return Time30s;
        }
        return Off;
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.lamp.LightMeHomeTime$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LightMeHomeTime;

        static {
            int[] iArr = new int[LightMeHomeTime.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LightMeHomeTime = iArr;
            try {
                iArr[LightMeHomeTime.Off.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LightMeHomeTime[LightMeHomeTime.Time15s.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LightMeHomeTime[LightMeHomeTime.Time30s.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LightMeHomeTime[LightMeHomeTime.Time60s.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public int toBcmCmd() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LightMeHomeTime[ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i == 4) {
                        return 3;
                    }
                    throw new IllegalArgumentException("Unknown light me home time mode: " + this);
                }
                return 2;
            }
            return 1;
        }
        return 0;
    }

    public static LightMeHomeTime getIndexValue(int index) {
        if (index != 0) {
            if (index != 1) {
                if (index != 2) {
                    if (index != 3) {
                        return null;
                    }
                    return Time60s;
                }
                return Time30s;
            }
            return Time15s;
        }
        return Off;
    }
}
