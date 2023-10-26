package com.xiaopeng.speech.protocol.bean.config;

import com.xiaopeng.speech.common.util.DontProguardClass;

@DontProguardClass
/* loaded from: classes2.dex */
public class GuideConentBean {
    private String content;
    private String des;
    private String img;
    public Object props;

    public String getContent() {
        return this.content;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public String getDes() {
        return this.des;
    }

    public void setDes(String str) {
        this.des = str;
    }

    public String getImg() {
        return this.img;
    }

    public void setImg(String str) {
        this.img = str;
    }

    public Object getProps() {
        return this.props;
    }

    public void setProps(Object obj) {
        this.props = obj;
    }
}
