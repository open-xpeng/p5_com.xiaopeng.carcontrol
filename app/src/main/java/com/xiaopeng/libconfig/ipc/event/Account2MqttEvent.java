package com.xiaopeng.libconfig.ipc.event;

/* loaded from: classes2.dex */
public class Account2MqttEvent {
    private int msgType;
    private String value;

    public Account2MqttEvent(int i) {
        this.msgType = i;
    }

    public int getMsgType() {
        return this.msgType;
    }

    public void setMsgType(int i) {
        this.msgType = i;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String str) {
        this.value = str;
    }

    public String toString() {
        return "Account2MqttEvent{msgType=" + this.msgType + ", value='" + this.value + "'}";
    }
}
