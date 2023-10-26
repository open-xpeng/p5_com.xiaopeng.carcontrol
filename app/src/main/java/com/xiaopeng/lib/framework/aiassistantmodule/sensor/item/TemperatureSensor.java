package com.xiaopeng.lib.framework.aiassistantmodule.sensor.item;

import com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.sensor.BaseSensor;

/* loaded from: classes2.dex */
public abstract class TemperatureSensor extends BaseSensor {
    public static final int HIGH_TEMPERATURE = 1;
    public static final int NORMAL_TEMPERATURE = 0;
    private int highTemperatureState;

    public int getHighTemperatureState() {
        return this.highTemperatureState;
    }

    public void setHighTemperatureState(int i) {
        this.highTemperatureState = i;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.sensor.BaseSensor
    public String sensorName() {
        return TemperatureSensor.class.getSimpleName();
    }
}
