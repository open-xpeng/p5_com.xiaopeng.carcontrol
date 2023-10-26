package com.xiaopeng.libcarcontrol;

import android.util.Log;

/* loaded from: classes2.dex */
public enum ChargeStatus {
    Prepare,
    Appointment,
    Charging,
    ChargeError,
    ChargeDone,
    Discharging,
    DischargeDone,
    DischargeError,
    FullyChargedD21,
    ChargerRemovedD21,
    WrongOpD21,
    ChargeStoppingD21,
    ChargeErrorD21;

    public static ChargeStatus fromVcuChargeStatus(int value) {
        switch (value) {
            case 0:
                return Prepare;
            case 1:
                return Appointment;
            case 2:
                return Charging;
            case 3:
                return ChargeError;
            case 4:
                return ChargeDone;
            case 5:
                return Discharging;
            case 6:
                return DischargeDone;
            case 7:
                return DischargeError;
            default:
                switch (value) {
                    case 16:
                        return FullyChargedD21;
                    case 17:
                        return ChargerRemovedD21;
                    case 18:
                        return WrongOpD21;
                    case 19:
                        return ChargeStoppingD21;
                    case 20:
                        return ChargeErrorD21;
                    default:
                        Log.d("ChargeStatus", "Unknown charge status, set as prepare status");
                        return Prepare;
                }
        }
    }
}
