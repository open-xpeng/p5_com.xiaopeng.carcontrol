package com.xiaopeng.speech.protocol.query.carair;

import com.xiaopeng.speech.IQueryCaller;

/* loaded from: classes2.dex */
public interface ICarAirCaller extends IQueryCaller {
    int getAcPanelStatus();

    int getAirLevel();

    int getCurrWindMode();

    int getDriWindDirectionMode();

    int getHeatMax();

    int getHeatMin();

    int getIntelligentPassengerStatus();

    default int getLeftRearSeatHeatLevel() {
        return 0;
    }

    int getOutSidePmLevelQuality();

    int getOutSidePmQuality();

    int getPsnWindDirectionMode();

    default int getRightRearSeatHeatLevel() {
        return 0;
    }

    int getSeatBlowMax();

    int getSeatBlowMin();

    double getTempMax();

    double getTempMin();

    int getWindMax();

    int getWindMin();

    int[] getWindsStatus();

    default boolean isAcSupportPerfume() {
        return false;
    }

    boolean isFastFridge();

    boolean isFreshAir();

    boolean isSupportAutoOff();

    boolean isSupportDecimalValue();

    boolean isSupportDemistFoot();

    int isSupportMirrorHeat();

    int isSupportOutSidePm();

    boolean isSupportPm25();

    boolean isSupportPsnSeatHeat();

    boolean isSupportPurifier();

    default boolean isSupportRearSeatHeat() {
        return false;
    }

    boolean isSupportSeatBlow();

    boolean isSupportSeatHeat();

    boolean isSupportTempDual();
}
