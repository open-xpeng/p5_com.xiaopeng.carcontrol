package com.xiaopeng.carcontrol.bean;

/* loaded from: classes.dex */
public class ControlEventMsg {
    public static final int MEDITATION_END = 0;
    public static final int MEDITATION_RESUME = 2;
    public static final int MEDITATION_START = 1;
    public static final String TYPE_ANALOG_SOUND = "ANALOG_SOUND";
    public static final String TYPE_CHAIR_POS_BUBBLE = "CHAIR_POS_BUBBLE";
    public static final String TYPE_LAMP_AUTO = "LAMP_AUTO";
    public static final String TYPE_LAMP_CLOSE = "LAMP_CLOSE";
    public static final String TYPE_LAMP_LOCATION = "LAMP_LOCATION";
    public static final String TYPE_LAMP_NEAR = "LAMP_NEAR";
    public static final String TYPE_MEDITATION_MODE = "MEDITATION_MODE";
    public static final String TYPE_REAR_MIRROR = "REAR_MIRROR";
    public static final String TYPE_TRUNK_STATE = "TRUNK_STATE";
    public static final String TYPE_WELCOME_MODE = "WELCOME_MODE";
    public static final String TYPE_WIPER = "TYPE_WIPER";
    private int index;
    private boolean isChecked;
    private String name;
    private int value;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isChecked() {
        return this.isChecked;
    }

    public void setChecked(boolean checked) {
        this.isChecked = checked;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("ControlEventMsg{");
        stringBuffer.append("name='").append(this.name).append('\'');
        stringBuffer.append(", index=").append(this.index);
        stringBuffer.append(", isChecked=").append(this.isChecked);
        stringBuffer.append(", value=").append(this.value);
        stringBuffer.append('}');
        return stringBuffer.toString();
    }
}
