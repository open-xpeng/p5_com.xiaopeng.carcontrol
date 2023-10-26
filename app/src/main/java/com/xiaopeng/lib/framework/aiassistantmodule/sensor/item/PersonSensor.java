package com.xiaopeng.lib.framework.aiassistantmodule.sensor.item;

import com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.sensor.BaseSensor;

/* loaded from: classes2.dex */
public abstract class PersonSensor extends BaseSensor {
    public static final int DISTRACTION_LEVEL_EXHIBITION = 3;
    public static final int DISTRACTION_LEVEL_STRAIGHT = 1;
    public static final int DISTRACTION_LEVEL_SWERVE = 2;
    public static final int FATIGUE_LEVEL_1 = 1;
    public static final int FATIGUE_LEVEL_2 = 2;
    public static final int FATIGUE_LEVEL_EXHIBITION = 3;
    public static final String FIELD_DISTRACTION_LEVEL = "distractionLevel";
    public static final String FIELD_FATIGUE_LEVEL = "fatigueLevel";
    private int distractionLevel;
    private int fatigueLevel;

    public int getFatigueLevel() {
        return this.fatigueLevel;
    }

    public void setFatigueLevel(int i) {
        this.fatigueLevel = i;
    }

    public int getDistractionLevel() {
        return this.distractionLevel;
    }

    public void setDistractionLevel(int i) {
        this.distractionLevel = i;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.sensor.BaseSensor
    public String sensorName() {
        return PersonSensor.class.getSimpleName();
    }
}
