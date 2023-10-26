package com.xiaopeng.carcontrol.model;

import com.xiaopeng.carcontrol.util.CarStatusUtils;
import com.xiaopeng.xvs.xid.BuildConfig;

/* loaded from: classes2.dex */
class DataSyncModelFactory {
    DataSyncModelFactory() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static DataSyncModel createInstance() {
        String newHardwareCarType = CarStatusUtils.getNewHardwareCarType();
        newHardwareCarType.hashCode();
        char c = 65535;
        switch (newHardwareCarType.hashCode()) {
            case 66946:
                if (newHardwareCarType.equals("D20")) {
                    c = 0;
                    break;
                }
                break;
            case 66947:
                if (newHardwareCarType.equals("D21")) {
                    c = 1;
                    break;
                }
                break;
            case 66948:
                if (newHardwareCarType.equals("D22")) {
                    c = 2;
                    break;
                }
                break;
            case 67044:
                if (newHardwareCarType.equals("D55")) {
                    c = 3;
                    break;
                }
                break;
            case 67915:
                if (newHardwareCarType.equals(BuildConfig.LIB_PRODUCT)) {
                    c = 4;
                    break;
                }
                break;
            case 67946:
                if (newHardwareCarType.equals("E38")) {
                    c = 5;
                    break;
                }
                break;
            case 68899:
                if (newHardwareCarType.equals("F30")) {
                    c = 6;
                    break;
                }
                break;
            case 2075423:
                if (newHardwareCarType.equals("D21B")) {
                    c = 7;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
            case 7:
                return CarStatusUtils.isEURegion() ? new D21vDataSyncModel() : new D2DataSyncModel();
            case 3:
                return CarStatusUtils.isEURegion() ? new D55vDataSyncModel() : new D55DataSyncModel();
            case 4:
            case 5:
            case 6:
                return CarStatusUtils.isEURegion() ? new E28vDataSyncModel() : new DataSyncModel();
            default:
                return new D2DataSyncModel();
        }
    }
}
