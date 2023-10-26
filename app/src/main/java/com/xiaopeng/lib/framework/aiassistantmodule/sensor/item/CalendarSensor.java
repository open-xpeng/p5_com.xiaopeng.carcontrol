package com.xiaopeng.lib.framework.aiassistantmodule.sensor.item;

import com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.sensor.BaseSensor;

/* loaded from: classes2.dex */
public abstract class CalendarSensor extends BaseSensor {
    private int date;
    private int hour;
    private boolean isWeek;
    private int minute;
    private int mouth;
    private int second;
    private int year;

    public int getYear() {
        return this.year;
    }

    public void setYear(int i) {
        this.year = i;
    }

    public int getMouth() {
        return this.mouth;
    }

    public void setMouth(int i) {
        this.mouth = i;
    }

    public int getDate() {
        return this.date;
    }

    public void setDate(int i) {
        this.date = i;
    }

    public int getHour() {
        return this.hour;
    }

    public void setHour(int i) {
        this.hour = i;
    }

    public int getMinute() {
        return this.minute;
    }

    public void setMinute(int i) {
        this.minute = i;
    }

    public int getSecond() {
        return this.second;
    }

    public void setSecond(int i) {
        this.second = i;
    }

    public boolean isWeek() {
        return this.isWeek;
    }

    public void setWeek(boolean z) {
        this.isWeek = z;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.sensor.BaseSensor
    public String sensorName() {
        return CalendarSensor.class.getSimpleName();
    }
}
