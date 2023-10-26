package com.xiaopeng.carcontrol.viewmodel.xpu;

/* loaded from: classes2.dex */
public enum NedcState {
    On,
    Off,
    Failure,
    TurnOning,
    TurnOffing,
    TurnOnFailure;

    public static NedcState fromXpuCmd(int state) {
        if (state != 0) {
            if (state != 1) {
                if (state != 2) {
                    if (state != 3) {
                        if (state != 4) {
                            if (state != 5) {
                                return null;
                            }
                            return TurnOnFailure;
                        }
                        return TurnOning;
                    }
                    return TurnOffing;
                }
                return Failure;
            }
            return On;
        }
        return Off;
    }
}
