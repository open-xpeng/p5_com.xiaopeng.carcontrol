package com.xiaopeng.libconfig.ipc.bean;

/* loaded from: classes2.dex */
public class MqttMsgBase<T> {
    public static final int CONTROL_MSG = 2;
    public static final int HEART_BEAT_MSG = 4;
    public static final int OPERATION_FEEDBACK_MSG = 1;
    public static final int SERVER_MSG = 3;
    public static final int UNREAD_MSG = 0;
    private T msg_content;
    private String msg_id;
    private String msg_ref;
    private int msg_type;
    private int service_type = -1;

    public MqttMsgBase(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(20).append(i).append(System.currentTimeMillis()).append((int) (Math.random() * 10.0d)).append((int) (Math.random() * 10.0d));
        this.msg_id = sb.toString();
    }

    public MqttMsgBase(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(20).append(str).append(System.currentTimeMillis()).append((int) (Math.random() * 10.0d)).append((int) (Math.random() * 10.0d));
        this.msg_id = sb.toString();
    }

    public void setMsgId(String str) {
        this.msg_id = str;
    }

    public String getMsgId() {
        return this.msg_id;
    }

    public String getMsgRef() {
        return this.msg_ref;
    }

    public void setMsgRef(String str) {
        this.msg_ref = str;
    }

    public int getMsgType() {
        return this.msg_type;
    }

    public void setMsgType(int i) {
        this.msg_type = i;
    }

    public int getServiceType() {
        return this.service_type;
    }

    public void setServiceType(int i) {
        this.service_type = i;
    }

    public T getMsgContent() {
        return this.msg_content;
    }

    public void setMsgContent(T t) {
        this.msg_content = t;
    }

    public String toString() {
        return "MqttMsgBase{msg_id='" + this.msg_id + "', msg_ref='" + this.msg_ref + "', msg_type=" + this.msg_type + ", service_type=" + this.service_type + ", msg_content=" + this.msg_content + '}';
    }
}
