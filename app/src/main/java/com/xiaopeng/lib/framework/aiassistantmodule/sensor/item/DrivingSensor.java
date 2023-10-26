package com.xiaopeng.lib.framework.aiassistantmodule.sensor.item;

import com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.sensor.BaseSensor;

/* loaded from: classes2.dex */
public abstract class DrivingSensor extends BaseSensor {
    private float carSpeed;
    private int drivingMode;
    private int drivingTime;
    private int energyRecycleLevel;
    private int gearLevel;
    private float lastStartUpMileage;

    public int getDrivingTime() {
        return this.drivingTime;
    }

    public void setDrivingTime(int i) {
        this.drivingTime = i;
    }

    public int getGearLevel() {
        return this.gearLevel;
    }

    public void setGearLevel(int i) {
        this.gearLevel = i;
    }

    public float getCarSpeed() {
        return this.carSpeed;
    }

    public void setCarSpeed(float f) {
        this.carSpeed = f;
    }

    public int getDrivingMode() {
        return this.drivingMode;
    }

    public void setDrivingMode(int i) {
        this.drivingMode = i;
    }

    public int getEnergyRecycleLevel() {
        return this.energyRecycleLevel;
    }

    public void setEnergyRecycleLevel(int i) {
        this.energyRecycleLevel = i;
    }

    public float getLastStartUpMileage() {
        return this.lastStartUpMileage;
    }

    public void setLastStartUpMileage(float f) {
        this.lastStartUpMileage = f;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.sensor.BaseSensor
    public String sensorName() {
        return DrivingSensor.class.getSimpleName();
    }
}
