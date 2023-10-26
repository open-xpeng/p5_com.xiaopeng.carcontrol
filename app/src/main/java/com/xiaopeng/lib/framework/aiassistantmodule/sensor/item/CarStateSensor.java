package com.xiaopeng.lib.framework.aiassistantmodule.sensor.item;

import com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.sensor.BaseSensor;

/* loaded from: classes2.dex */
public abstract class CarStateSensor extends BaseSensor {
    private long acUsingTime;
    private int circulationMode;
    private int fogLampState;
    private float gapTemperature;
    private long gearDlevelTime;
    private long gearPlevelTime;
    private boolean hasControlWindow;
    private int headLampGroup;
    private float insideTemp;
    private boolean isACMode;
    private boolean isAllWindowClose;
    private boolean isHvacOpen;
    private float outsideTemp;
    private long seatHeatUsingTime;
    private long wasteACTime;
    private int wiperIntermittentMode;
    private long wiperUsingTime;

    public boolean isACMode() {
        return this.isACMode;
    }

    public void setACMode(boolean z) {
        this.isACMode = z;
    }

    public boolean isHvacOpen() {
        return this.isHvacOpen;
    }

    public void setHvacOpen(boolean z) {
        this.isHvacOpen = z;
    }

    public int getCirculationMode() {
        return this.circulationMode;
    }

    public void setCirculationMode(int i) {
        this.circulationMode = i;
    }

    public long getAcUsingTime() {
        return this.acUsingTime;
    }

    public void setAcUsingTime(long j) {
        this.acUsingTime = j;
    }

    public long getWiperUsingTime() {
        return this.wiperUsingTime;
    }

    public void setWiperUsingTime(long j) {
        this.wiperUsingTime = j;
    }

    public long getSeatHeatUsingTime() {
        return this.seatHeatUsingTime;
    }

    public void setSeatHeatUsingTime(long j) {
        this.seatHeatUsingTime = j;
    }

    public float getInsideTemp() {
        return this.insideTemp;
    }

    public void setInsideTemp(float f) {
        this.insideTemp = f;
    }

    public float getOutsideTemp() {
        return this.outsideTemp;
    }

    public void setOutsideTemp(float f) {
        this.outsideTemp = f;
    }

    public float getGapTemperature() {
        return this.gapTemperature;
    }

    public void setGapTemperature(float f) {
        this.gapTemperature = f;
    }

    public int getWiperIntermittentMode() {
        return this.wiperIntermittentMode;
    }

    public void setWiperIntermittentMode(int i) {
        this.wiperIntermittentMode = i;
    }

    public int getHeadLampGroup() {
        return this.headLampGroup;
    }

    public void setHeadLampGroup(int i) {
        this.headLampGroup = i;
    }

    public int getFogLampState() {
        return this.fogLampState;
    }

    public void setFogLampState(int i) {
        this.fogLampState = i;
    }

    public long getWasteACTime() {
        return this.wasteACTime;
    }

    public void setWasteACTime(long j) {
        this.wasteACTime = j;
    }

    public long getGearPlevelTime() {
        return this.gearPlevelTime;
    }

    public void setGearPlevelTime(long j) {
        this.gearPlevelTime = j;
    }

    public long getGearDlevelTime() {
        return this.gearDlevelTime;
    }

    public void setGearDlevelTime(long j) {
        this.gearDlevelTime = j;
    }

    public boolean isAllWindowClose() {
        return this.isAllWindowClose;
    }

    public void setAllWindowClose(boolean z) {
        this.isAllWindowClose = z;
    }

    public boolean isHasControlWindow() {
        return this.hasControlWindow;
    }

    public void setHasControlWindow(boolean z) {
        this.hasControlWindow = z;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.sensor.BaseSensor
    public String sensorName() {
        return CarStateSensor.class.getSimpleName();
    }
}
