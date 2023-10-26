package com.xiaopeng.lib.framework.aiassistantmodule.sensor.item;

import com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.sensor.BaseSensor;

/* loaded from: classes2.dex */
public abstract class SeatSensor extends BaseSensor {
    private boolean electricSeatBelt;
    private boolean hasBlowFunction;
    private boolean hasHeatFunction;
    private int seatBlowLevel;
    private int seatHeatLevel;
    private int seatState;

    public int getSeatState() {
        return this.seatState;
    }

    public void setSeatState(int i) {
        this.seatState = i;
    }

    public int getSeatBlowLevel() {
        return this.seatBlowLevel;
    }

    public void setSeatBlowLevel(int i) {
        this.seatBlowLevel = i;
    }

    public int getSeatHeatLevel() {
        return this.seatHeatLevel;
    }

    public void setSeatHeatLevel(int i) {
        this.seatHeatLevel = i;
    }

    public boolean isHasBlowFunction() {
        return this.hasBlowFunction;
    }

    public void setHasBlowFunction(boolean z) {
        this.hasBlowFunction = z;
    }

    public boolean isHasHeatFunction() {
        return this.hasHeatFunction;
    }

    public void setHasHeatFunction(boolean z) {
        this.hasHeatFunction = z;
    }

    public boolean isElectricSeatBelt() {
        return this.electricSeatBelt;
    }

    public void setElectricSeatBelt(boolean z) {
        this.electricSeatBelt = z;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.sensor.BaseSensor
    public String sensorName() {
        return SeatSensor.class.getSimpleName();
    }
}
