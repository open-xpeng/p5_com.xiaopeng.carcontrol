package com.xiaopeng.speech.common.bean;

import android.os.IBinder;

/* loaded from: classes2.dex */
public class DisableInfoBean extends BaseBean {
    private IBinder binder;
    private String byWho;
    private String info;
    private int notifyType;
    private int type;

    public DisableInfoBean(IBinder iBinder, int i, String str, String str2, int i2) {
        this.binder = iBinder;
        this.type = i;
        this.byWho = str;
        this.info = str2;
        this.notifyType = i2;
    }

    public IBinder getBinder() {
        return this.binder;
    }

    public void setBinder(IBinder iBinder) {
        this.binder = iBinder;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public String getByWho() {
        return this.byWho;
    }

    public void setByWho(String str) {
        this.byWho = str;
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String str) {
        this.info = str;
    }

    public int getNotifyType() {
        return this.notifyType;
    }

    public void setNotifyType(int i) {
        this.notifyType = i;
    }

    public String toString() {
        return "DisableInfoBean{binder=" + this.binder + ", type=" + this.type + ", byWho='" + this.byWho + "', info='" + this.info + "', notifyType=" + this.notifyType + '}';
    }
}
