package com.xiaopeng.lib.framework.aiassistantmodule.sensor.item;

import com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.sensor.BaseSensor;

/* loaded from: classes2.dex */
public abstract class BatterySensor extends BaseSensor {
    private int chargingGunStatus;
    private float electricityPercent;
    private int mileageNumber;

    public int getMileageNumber() {
        return this.mileageNumber;
    }

    public void setMileageNumber(int i) {
        this.mileageNumber = i;
    }

    public float getElectricityPercent() {
        return this.electricityPercent;
    }

    public void setElectricityPercent(float f) {
        this.electricityPercent = f;
    }

    public int getChargingGunStatus() {
        return this.chargingGunStatus;
    }

    public void setChargingGunStatus(int i) {
        this.chargingGunStatus = i;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.sensor.BaseSensor
    public String sensorName() {
        return BatterySensor.class.getSimpleName();
    }
}
