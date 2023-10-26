package com.xiaopeng.lib.framework.aiassistantmodule.sensor.item;

import com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.sensor.BaseSensor;

/* loaded from: classes2.dex */
public abstract class WeatherSensor extends BaseSensor {
    private boolean isFogType;
    private boolean isHazeType;
    private boolean isRainType;
    private String weatherType;

    public String getWeatherType() {
        return this.weatherType;
    }

    public void setWeatherType(String str) {
        this.weatherType = str;
    }

    public boolean isFogType() {
        return this.isFogType;
    }

    public void setFogType(boolean z) {
        this.isFogType = z;
    }

    public boolean isHazeType() {
        return this.isHazeType;
    }

    public void setHazeType(boolean z) {
        this.isHazeType = z;
    }

    public boolean isRainType() {
        return this.isRainType;
    }

    public void setRainType(boolean z) {
        this.isRainType = z;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.sensor.BaseSensor
    public String sensorName() {
        return WeatherSensor.class.getSimpleName();
    }
}
