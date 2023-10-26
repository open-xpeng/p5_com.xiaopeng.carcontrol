package com.xiaopeng.speech.protocol.bean.config;

import com.xiaopeng.speech.common.util.DontProguardClass;
import java.util.List;

@DontProguardClass
/* loaded from: classes2.dex */
public class GuideDataBean {
    private List<GuideConentBean> content;
    private String isChange;
    private boolean isOpened;
    private String name;
    private int type;

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getIsChange() {
        return this.isChange;
    }

    public void setIsChange(String str) {
        this.isChange = str;
    }

    public boolean isOpened() {
        return this.isOpened;
    }

    public void setOpened(boolean z) {
        this.isOpened = z;
    }

    public List<GuideConentBean> getContent() {
        return this.content;
    }

    public void setContent(List<GuideConentBean> list) {
        this.content = list;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }
}
