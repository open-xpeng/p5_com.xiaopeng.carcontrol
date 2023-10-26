package com.xiaopeng.carcontrol.viewmodel.service;

import com.xiaopeng.xvs.xid.BuildConfig;

/* loaded from: classes2.dex */
public class ServiceViewModelFactory {
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static IServiceViewModel createViewModel(String carType) {
        char c;
        switch (carType.hashCode()) {
            case 66947:
                if (carType.equals("D21")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 66948:
                if (carType.equals("D22")) {
                    c = 1;
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
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 67946:
                if (carType.equals("E38")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 68899:
                if (carType.equals("F30")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 2075423:
                if (carType.equals("D21B")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        if (c == 0 || c == 1 || c == 2) {
            return new D2ServiceViewModel();
        }
        if (c == 3) {
            return new D55ServiceViewModel();
        }
        return new ServiceViewModel();
    }
}
