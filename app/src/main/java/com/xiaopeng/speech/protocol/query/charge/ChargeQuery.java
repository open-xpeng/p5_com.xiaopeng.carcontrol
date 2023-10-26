package com.xiaopeng.speech.protocol.query.charge;

import com.xiaopeng.speech.SpeechQuery;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class ChargeQuery extends SpeechQuery<IChargeCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSupportOpenPort(String str, String str2) {
        return ((IChargeCaller) this.mQueryCaller).isSupportOpenPort();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getStartStatus(String str, String str2) {
        return ((IChargeCaller) this.mQueryCaller).getStartStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getStopStatus(String str, String str2) {
        return ((IChargeCaller) this.mQueryCaller).getStopStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getRestartStatus(String str, String str2) {
        return ((IChargeCaller) this.mQueryCaller).getRestartStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSupportLimitsAdjust(String str, String str2) {
        return ((IChargeCaller) this.mQueryCaller).isSupportLimitsAdjust();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getLimitsAdjustMin(String str, String str2) {
        return ((IChargeCaller) this.mQueryCaller).getLimitsAdjustMin();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getLimitsAdjustMax(String str, String str2) {
        return ((IChargeCaller) this.mQueryCaller).getLimitsAdjustMax();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSupportSmartMode(String str, String str2) {
        return ((IChargeCaller) this.mQueryCaller).isSupportSmartMode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isHasChargingOrder(String str, String str2) {
        return ((IChargeCaller) this.mQueryCaller).isHasChargingOrder();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isChargeReadyPage(String str, String str2) {
        return ((IChargeCaller) this.mQueryCaller).isChargeReadyPage();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getChargeStatus(String str, String str2) {
        return ((IChargeCaller) this.mQueryCaller).getChargeStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getPageOpenStatus(String str, String str2) {
        return ((IChargeCaller) this.mQueryCaller).getGuiPageOpenState(str2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean hasCarRefrigerator(String str, String str2) {
        return ((IChargeCaller) this.mQueryCaller).hasCarRefrigerator();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean hasSunRoof(String str, String str2) {
        return ((IChargeCaller) this.mQueryCaller).hasSunRoof();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getTrunkPowerStatus(String str, String str2) {
        return ((IChargeCaller) this.mQueryCaller).getTrunkPowerStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getMinDischargeLimit(String str, String str2) {
        return ((IChargeCaller) this.mQueryCaller).getMinDischargeLimit();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getMaxDischargeLimit(String str, String str2) {
        return ((IChargeCaller) this.mQueryCaller).getMaxDischargeLimit();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getTrunkPowerStatusForOpen(String str, String str2) {
        return ((IChargeCaller) this.mQueryCaller).getTrunkPowerStatusForOpen();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSupportEnduranceMode(String str, String str2) {
        int i = 1;
        try {
            i = new JSONObject(str2).optInt("mode", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ((IChargeCaller) this.mQueryCaller).isSupportEnduranceMode(i);
    }
}
