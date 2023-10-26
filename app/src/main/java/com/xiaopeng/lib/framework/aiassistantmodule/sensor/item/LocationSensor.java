package com.xiaopeng.lib.framework.aiassistantmodule.sensor.item;

import com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.sensor.BaseSensor;

/* loaded from: classes2.dex */
public abstract class LocationSensor extends BaseSensor {
    private float latitude;
    private long length;
    private float longitude;

    public float getLongitude() {
        return this.longitude;
    }

    public void setLongitude(float f) {
        this.longitude = f;
    }

    public float getLatitude() {
        return this.latitude;
    }

    public void setLatitude(float f) {
        this.latitude = f;
    }

    public long getLength() {
        return this.length;
    }

    public void setLength(long j) {
        this.length = j;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.sensor.BaseSensor
    public String sensorName() {
        return LocationSensor.class.getSimpleName();
    }
}
