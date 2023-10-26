package com.xiaopeng.speech.protocol.query.charge;

import com.xiaopeng.speech.IQueryCaller;

/* loaded from: classes2.dex */
public interface IChargeCaller extends IQueryCaller {
    int getChargeStatus();

    int getGuiPageOpenState(String str);

    int getLimitsAdjustMax();

    int getLimitsAdjustMin();

    default int getMaxDischargeLimit() {
        return 0;
    }

    default int getMinDischargeLimit() {
        return 0;
    }

    int getRestartStatus();

    int getStartStatus();

    int getStopStatus();

    default int getTrunkPowerStatus() {
        return -1;
    }

    default int getTrunkPowerStatusForOpen() {
        return -1;
    }

    default boolean hasCarRefrigerator() {
        return false;
    }

    default boolean hasSunRoof() {
        return false;
    }

    boolean isChargeReadyPage();

    boolean isHasChargingOrder();

    default boolean isSupportEnduranceMode(int i) {
        return true;
    }

    boolean isSupportLimitsAdjust();

    boolean isSupportOpenPort();

    boolean isSupportSmartMode();
}
