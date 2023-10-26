package com.xiaopeng.carcontrol.viewmodel.scu;

/* loaded from: classes2.dex */
public enum DsmState {
    Off,
    On,
    Failure,
    TurnOning;

    public static DsmState fromScuState(int state) {
        if (state != 0) {
            if (state != 4) {
                if (state == 6) {
                    return TurnOning;
                }
                return On;
            }
            return Failure;
        }
        return Off;
    }
}
