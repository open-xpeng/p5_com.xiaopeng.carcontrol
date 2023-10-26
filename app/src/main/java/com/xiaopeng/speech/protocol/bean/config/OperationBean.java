package com.xiaopeng.speech.protocol.bean.config;

import com.xiaopeng.speech.common.util.DontProguardClass;

@DontProguardClass
/* loaded from: classes2.dex */
public class OperationBean {
    private String des;
    private String imgDisable;
    private String imgEnable;
    private boolean isOpened;
    private String tag;
    private String tips;
    private String title;
    private String type;

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public boolean isOpened() {
        return this.isOpened;
    }

    public void setOpened(boolean z) {
        this.isOpened = z;
    }

    public String getTips() {
        return this.tips;
    }

    public void setTips(String str) {
        this.tips = str;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getDes() {
        return this.des;
    }

    public void setDes(String str) {
        this.des = str;
    }

    public String getImgEnable() {
        return this.imgEnable;
    }

    public void setImgEnable(String str) {
        this.imgEnable = str;
    }

    public String getImgDisable() {
        return this.imgDisable;
    }

    public void setImgDisable(String str) {
        this.imgDisable = str;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String str) {
        this.tag = str;
    }
}
