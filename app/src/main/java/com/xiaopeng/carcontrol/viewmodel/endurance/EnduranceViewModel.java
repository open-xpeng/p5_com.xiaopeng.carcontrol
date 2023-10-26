package com.xiaopeng.carcontrol.viewmodel.endurance;

import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.IDcdcController;
import com.xiaopeng.carcontrol.carmanager.controller.IHvacController;
import com.xiaopeng.carcontrol.carmanager.controller.IVcuController;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.util.LogUtils;
import java.math.BigDecimal;

/* loaded from: classes2.dex */
public class EnduranceViewModel implements IEnduranceViewModel {
    protected static final String TAG = "EnduranceViewModel";
    private final CarBaseConfig mCarConfig = CarBaseConfig.getInstance();
    private IDcdcController mDcdcController;
    private IHvacController mHvacController;
    private IVcuController mVcuController;

    public EnduranceViewModel() {
        CarClientWrapper carClientWrapper = CarClientWrapper.getInstance();
        this.mVcuController = (IVcuController) carClientWrapper.getController(CarClientWrapper.XP_VCU_SERVICE);
        this.mHvacController = (IHvacController) carClientWrapper.getController("hvac");
        this.mDcdcController = (IDcdcController) carClientWrapper.getController(CarClientWrapper.XP_DCDC_SERVICE);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.endurance.IEnduranceViewModel
    public int getEstimateEndurance(long arrivalTime) {
        float checkHvacStableConsumption = checkHvacStableConsumption();
        float hvacInstantConsumption = getHvacInstantConsumption();
        float dcdcConsumption = getDcdcConsumption();
        float calculateLeftTimeInHour = calculateLeftTimeInHour(arrivalTime);
        float f = calculateLeftTimeInHour >= 0.5f ? ((hvacInstantConsumption - checkHvacStableConsumption) * 0.25f) + ((checkHvacStableConsumption + dcdcConsumption) * calculateLeftTimeInHour) : (((hvacInstantConsumption + checkHvacStableConsumption) * 0.5f) + dcdcConsumption) * calculateLeftTimeInHour;
        int currentEndurance = getCurrentEndurance();
        float powerConsumption = getPowerConsumption();
        LogUtils.i(TAG, "currentEndurance: " + currentEndurance + ", consumptionPer100Km " + powerConsumption, false);
        int round = Math.round(currentEndurance - ((f * 100.0f) / powerConsumption));
        LogUtils.i(TAG, "Estimate available distance: " + round + ", continued for " + calculateLeftTimeInHour + " hours", false);
        return round;
    }

    private float checkHvacStableConsumption() {
        boolean z = this.mHvacController.getHvacCirculationMode() == 1;
        float hvacTempDriver = this.mHvacController.getHvacTempDriver();
        float hvacExternalTemp = this.mHvacController.getHvacExternalTemp();
        int hvacStableConsumption = HvacConsumptionTable.getInstance().getHvacStableConsumption(z, hvacTempDriver, hvacExternalTemp);
        LogUtils.i(TAG, "Hvac circulation: " + z + ", target temp: " + hvacTempDriver + ", outer temp: " + hvacExternalTemp + ", result: " + hvacStableConsumption, false);
        return BigDecimal.valueOf((hvacStableConsumption * 1.0f) / 1000.0f).setScale(2, 4).floatValue();
    }

    private float getHvacInstantConsumption() {
        int hvacAcpConsumption = this.mHvacController.getHvacAcpConsumption();
        int hvacPtcConsumption = this.mHvacController.getHvacPtcConsumption();
        LogUtils.i(TAG, "Hvac module AcpConsumption: " + hvacAcpConsumption + ", PtcConsumption: " + hvacPtcConsumption, false);
        return BigDecimal.valueOf(((hvacAcpConsumption + hvacPtcConsumption) * 1.0f) / 1000.0f).setScale(2, 4).floatValue();
    }

    private float getDcdcConsumption() {
        int dcdcInputVoltage = this.mDcdcController.getDcdcInputVoltage();
        float dcdcInputCurrent = this.mDcdcController.getDcdcInputCurrent();
        LogUtils.i(TAG, "Dcdc module vol: " + dcdcInputCurrent + ", cur: " + dcdcInputCurrent, false);
        return BigDecimal.valueOf((dcdcInputVoltage * dcdcInputCurrent) / 1000.0f).setScale(2, 4).floatValue();
    }

    private int getCurrentEndurance() {
        return this.mVcuController.getAvailableMileage(true);
    }

    private float getPowerConsumption() {
        return this.mVcuController.getConsumptionPer100Km();
    }

    private float calculateLeftTimeInHour(long arrivalTime) {
        long currentTimeMillis = arrivalTime - System.currentTimeMillis();
        if (currentTimeMillis < 0) {
            LogUtils.w(TAG, "Arrival time is earlier than current time, not acceptable", false);
            return 0.0f;
        }
        if (currentTimeMillis < 300000) {
            LogUtils.w(TAG, "Arrival time is less than 5 minute", false);
            currentTimeMillis = 300000;
        }
        float floatValue = BigDecimal.valueOf((((float) currentTimeMillis) / 1000.0f) / 3600.0f).setScale(2, 4).floatValue();
        LogUtils.i(TAG, "calculateLeftTimeInHour arrivalTime: " + arrivalTime + ", leftTimeInHour: " + floatValue, false);
        return floatValue;
    }
}
