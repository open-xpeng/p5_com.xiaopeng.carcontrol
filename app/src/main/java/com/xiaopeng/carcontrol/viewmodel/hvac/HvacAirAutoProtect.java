package com.xiaopeng.carcontrol.viewmodel.hvac;

/* loaded from: classes2.dex */
public enum HvacAirAutoProtect {
    Off,
    Remind,
    Auto;

    public static HvacAirAutoProtect fromValue(int value) {
        try {
            return values()[value];
        } catch (IndexOutOfBoundsException unused) {
            return Remind;
        }
    }
}
