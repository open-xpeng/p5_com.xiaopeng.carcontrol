package com.xiaopeng.carcontrol.viewmodel.cabin;

import com.xiaopeng.xvs.xid.BuildConfig;

/* loaded from: classes2.dex */
public class SeatViewModelFactory {
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static ISeatViewModel createSeatViewModel(String carType) {
        char c;
        switch (carType.hashCode()) {
            case 66947:
                if (carType.equals("D21")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 67044:
                if (carType.equals("D55")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 67915:
                if (carType.equals(BuildConfig.LIB_PRODUCT)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 67946:
                if (carType.equals("E38")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 68899:
                if (carType.equals("F30")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 2075423:
                if (carType.equals("D21B")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        if (c == 0 || c == 1 || c == 2) {
            return new SeatViewModel();
        }
        if (c == 3) {
            return new D55SeatViewModel();
        }
        return new D2SeatViewModel();
    }
}
