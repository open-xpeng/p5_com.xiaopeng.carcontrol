package com.xiaopeng.libconfig.ipc.event;

/* loaded from: classes2.dex */
public class Account2ManyEvent {
    public static final int CODE_SHOW_BIG_WINDOW = 1;
    public static final int TYPE_CHANGE_ACCOUNT = 2;
    public static final int TYPE_ONLINE_ACCOUNT = 1;
    public int code;
    public int msgType;
    public String msgValue;

    public int getMsgType() {
        return this.msgType;
    }

    public void setMsgType(int i) {
        this.msgType = i;
    }

    public String getMsgValue() {
        return this.msgValue;
    }

    public void setMsgValue(String str) {
        this.msgValue = str;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int i) {
        this.code = i;
    }

    public String toString() {
        return "Account2ManyEvent{msgType=" + this.msgType + ", msgValue='" + this.msgValue + "', code=" + this.code + '}';
    }
}
