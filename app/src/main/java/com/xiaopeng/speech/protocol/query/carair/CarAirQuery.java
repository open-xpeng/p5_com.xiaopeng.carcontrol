package com.xiaopeng.speech.protocol.query.carair;

import com.xiaopeng.speech.SpeechQuery;

/* loaded from: classes2.dex */
public class CarAirQuery extends SpeechQuery<ICarAirCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSupportAutoOff(String str, String str2) {
        return ((ICarAirCaller) this.mQueryCaller).isSupportAutoOff();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSupportDemistFoot(String str, String str2) {
        return ((ICarAirCaller) this.mQueryCaller).isSupportDemistFoot();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getWindMax(String str, String str2) {
        return ((ICarAirCaller) this.mQueryCaller).getWindMax();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getWindMin(String str, String str2) {
        return ((ICarAirCaller) this.mQueryCaller).getWindMin();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public double getTempMax(String str, String str2) {
        return ((ICarAirCaller) this.mQueryCaller).getTempMax();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public double getTempMin(String str, String str2) {
        return ((ICarAirCaller) this.mQueryCaller).getTempMin();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSupportDecimalValue(String str, String str2) {
        return ((ICarAirCaller) this.mQueryCaller).isSupportDecimalValue();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSupportPurifier(String str, String str2) {
        return ((ICarAirCaller) this.mQueryCaller).isSupportPurifier();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSupportPm25(String str, String str2) {
        return ((ICarAirCaller) this.mQueryCaller).isSupportPm25();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getAirLevel(String str, String str2) {
        return ((ICarAirCaller) this.mQueryCaller).getAirLevel();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSupportSeatHeat(String str, String str2) {
        return ((ICarAirCaller) this.mQueryCaller).isSupportSeatHeat();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSupportPsnSeatHeat(String str, String str2) {
        return ((ICarAirCaller) this.mQueryCaller).isSupportPsnSeatHeat();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getHeatMax(String str, String str2) {
        return ((ICarAirCaller) this.mQueryCaller).getHeatMax();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getHeatMin(String str, String str2) {
        return ((ICarAirCaller) this.mQueryCaller).getHeatMin();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSupportSeatBlow(String str, String str2) {
        return ((ICarAirCaller) this.mQueryCaller).isSupportSeatBlow();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getSeatBlowMax(String str, String str2) {
        return ((ICarAirCaller) this.mQueryCaller).getSeatBlowMax();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getSeatBlowMin(String str, String str2) {
        return ((ICarAirCaller) this.mQueryCaller).getSeatBlowMin();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isFastFridge(String str, String str2) {
        return ((ICarAirCaller) this.mQueryCaller).isFastFridge();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isFreshAir(String str, String str2) {
        return ((ICarAirCaller) this.mQueryCaller).isFreshAir();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSupportTempDual(String str, String str2) {
        return ((ICarAirCaller) this.mQueryCaller).isSupportTempDual();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int[] getWindsStatus(String str, String str2) {
        return ((ICarAirCaller) this.mQueryCaller).getWindsStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int isSupportMpQuery(String str, String str2) {
        return ((ICarAirCaller) this.mQueryCaller).isSupportOutSidePm();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getWindMode(String str, String str2) {
        return ((ICarAirCaller) this.mQueryCaller).getCurrWindMode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int isSupportMirrorHeat(String str, String str2) {
        return ((ICarAirCaller) this.mQueryCaller).isSupportMirrorHeat();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getOutSidePmQuality(String str, String str2) {
        return ((ICarAirCaller) this.mQueryCaller).getOutSidePmQuality();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getDriWindDirectionMode(String str, String str2) {
        return ((ICarAirCaller) this.mQueryCaller).getDriWindDirectionMode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getPsnWindDirectionMode(String str, String str2) {
        return ((ICarAirCaller) this.mQueryCaller).getPsnWindDirectionMode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getAcPanelStatus(String str, String str2) {
        return ((ICarAirCaller) this.mQueryCaller).getAcPanelStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getPmLevelQuality(String str, String str2) {
        return ((ICarAirCaller) this.mQueryCaller).getOutSidePmLevelQuality();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getIntelligentPassengerStatus(String str, String str2) {
        return ((ICarAirCaller) this.mQueryCaller).getIntelligentPassengerStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSupportRearSeatHeat(String str, String str2) {
        return ((ICarAirCaller) this.mQueryCaller).isSupportRearSeatHeat();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getLeftRearSeatHeatLevel(String str, String str2) {
        return ((ICarAirCaller) this.mQueryCaller).getLeftRearSeatHeatLevel();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getRightRearSeatHeatLevel(String str, String str2) {
        return ((ICarAirCaller) this.mQueryCaller).getRightRearSeatHeatLevel();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isAcSupportPerfume(String str, String str2) {
        return ((ICarAirCaller) this.mQueryCaller).isAcSupportPerfume();
    }
}
