package com.xiaopeng.carcontrol.viewmodel.hvac;

import com.xiaopeng.carcontrol.util.CarStatusUtils;
import com.xiaopeng.speech.vui.utils.VuiUtils;

/* loaded from: classes2.dex */
public class HvacViewModelFactory {
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static IHvacViewModel createHvacViewModel() {
        char c;
        String xpCduType = CarStatusUtils.getXpCduType();
        int hashCode = xpCduType.hashCode();
        if (hashCode != 2577) {
            switch (hashCode) {
                case 2560:
                    if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q1)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q2)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q3)) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2564:
                            if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q5)) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2565:
                            if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q6)) {
                                c = 3;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q7)) {
                                c = 6;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q8)) {
                                c = 5;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (xpCduType.equals("Q9")) {
                                c = 7;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (xpCduType.equals("QB")) {
                c = '\b';
            }
            c = 65535;
        }
        if (c != 0) {
            if (c == 1 || c == 2 || c == 3) {
                return new D2HvacViewModel();
            }
            return new XpHvacViewModel();
        }
        return new HvacViewModel();
    }
}
