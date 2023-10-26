package com.xiaopeng.libconfig.ipc.event;

/* loaded from: classes2.dex */
public class CarControlEvent {
    public static final int CODE_SHOW_BIG_WINDOW = 1;
    public static final int TYPE_CANCEL_MOVE_SEAT = 3;
    public static final int TYPE_CANCEL_PREPARE_TO_SAVE_SEAT_INTO = 8;
    public static final int TYPE_GET_ONLINE_ACCOUNT = 4;
    public static final int TYPE_PREPARE_TO_SAVE_SEAT_INTO = 7;
    public static final int TYPE_SEAT_CHANGE = 1;
    public static final int TYPE_SEAT_MOVE_STOP = 6;
    public static final int TYPE_SEAT_NO_CHANGE = 2;
    public static final int TYPE_START_TO_SAVE_SEAT_INFO = 9;
    public static final int TYPE_SWITCH_TEMP = 5;
    private int msgCode;
    private int msgType;
    private String msgValue;

    public CarControlEvent(int i) {
        this.msgType = i;
    }

    public CarControlEvent(int i, int i2) {
        this.msgType = i;
        this.msgCode = i2;
    }

    public int getMsgType() {
        return this.msgType;
    }

    public void setMsgType(int i) {
        this.msgType = i;
    }

    public int getMsgCode() {
        return this.msgCode;
    }

    public void setMsgCode(int i) {
        this.msgCode = i;
    }

    public String getMsgValue() {
        return this.msgValue;
    }

    public void setMsgValue(String str) {
        this.msgValue = str;
    }

    public String toString() {
        return "CarControlEvent{msgType=" + this.msgType + ", msgCode=" + this.msgCode + ", msgValue='" + this.msgValue + "'}";
    }
}
