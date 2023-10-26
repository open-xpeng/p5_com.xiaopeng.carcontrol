package com.xiaopeng.speech.protocol.query.speech.charge;

import com.xiaopeng.speech.SpeechQuery;

/* loaded from: classes2.dex */
public class SpeechChargeQuery extends SpeechQuery<ISpeechChargeQueryCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public int getAddedElectricity(String str, String str2) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getAddedElectricity();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getChargingError(String str, String str2) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getChargingError();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getChargingGunStatus(String str, String str2) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getChargingGunStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getChargeStatus(String str, String str2) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getChargeStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getMileageNumber(String str, String str2) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getMileageNumber();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getWltpMileageNumber(String str, String str2) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getWltpMileageNumber();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getCltcMileageNumber(String str, String str2) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getCltcMieageNumber();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getEnduranceMileageMode(String str, String str2) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getEnduranceMileageMode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public double getElectricityPercent(String str, String str2) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getElectricityPercent();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getColdWarningTips(String str, String str2) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getColdWarningTips();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public double getHvacConsume(String str, String str2) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getHvacConsume();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getBatteryWarmingStatus(String str, String str2) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getBatteryWarmingStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public double getACChargingCurrent(String str, String str2) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getACChargingCurrent();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public double getACChargingVolt(String str, String str2) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getACChargingVolt();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public double getDCChargingCurrent(String str, String str2) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getDCChargingCurrent();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public double getDCChargingVolt(String str, String str2) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getDCChargingVolt();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getACInputStatus(String str, String str2) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getACInputStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getAverageVehConsume(String str, String str2) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getAverageVehConsume();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int[] getChargingMaxSoc(String str, String str2) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getChargingMaxSoc();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public double getSOH(String str, String str2) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getSOH();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getChargeGunLockSt(String str, String str2) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getChargeGunLockSt();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getChargeLimitSoc(String str, String str2) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getChargeLimitSoc();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getBatteryCoolStatus(String str, String str2) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getBatteryCoolStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getAverageVehConsume100km(String str, String str2) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getAverageVehConsume100km();
    }
}
