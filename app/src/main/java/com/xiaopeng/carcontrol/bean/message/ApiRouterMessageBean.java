package com.xiaopeng.carcontrol.bean.message;

/* loaded from: classes.dex */
public class ApiRouterMessageBean<T> {
    private String receiverPackageName;
    private String senderPackageName;
    private T string_msg;

    public void setSenderPackageName(String senderPackageName) {
        this.senderPackageName = senderPackageName;
    }

    public String getSenderPackageName() {
        return this.senderPackageName;
    }

    public void setReceiverPackageName(String receiverPackageName) {
        this.receiverPackageName = receiverPackageName;
    }

    public String getReceiverPackageName() {
        return this.receiverPackageName;
    }

    public void setString_msg(T string_msg) {
        this.string_msg = string_msg;
    }

    public T getString_msg() {
        return this.string_msg;
    }
}
