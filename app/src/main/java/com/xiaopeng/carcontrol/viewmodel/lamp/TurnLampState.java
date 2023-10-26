package com.xiaopeng.carcontrol.viewmodel.lamp;

/* loaded from: classes2.dex */
public enum TurnLampState {
    Off,
    Left,
    Right,
    Both;

    public static TurnLampState fromBcmStatus(int[] state) {
        if (state == null || state.length < 2) {
            return Off;
        }
        if (state[0] == 1 && state[1] == 1) {
            return Both;
        }
        if (state[0] == 1) {
            return Left;
        }
        if (state[1] == 1) {
            return Right;
        }
        return Off;
    }
}
