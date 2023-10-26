package com.xiaopeng.speech.protocol.bean.skill;

import com.xiaopeng.speech.common.util.DontProguardClass;
import java.util.List;

@DontProguardClass
/* loaded from: classes2.dex */
public class SkillData {
    private String aiSubTitle;
    private String aiTitle;
    private List<DataBean> data;
    private String subtitle;
    private int switchSkill;
    private String title;

    public List<DataBean> getData() {
        return this.data;
    }

    public void setData(List<DataBean> list) {
        this.data = list;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getSubTitle() {
        return this.subtitle;
    }

    public void setSubtitle(String str) {
        this.subtitle = str;
    }

    public int getSwitchSkill() {
        return this.switchSkill;
    }

    public void setSwitchSkill(int i) {
        this.switchSkill = i;
    }

    public String getAiTitle() {
        return this.aiTitle;
    }

    public void setAiTitle(String str) {
        this.aiTitle = str;
    }

    public String getAiSubTitle() {
        return this.aiSubTitle;
    }

    public void setAiSubTitle(String str) {
        this.aiSubTitle = str;
    }
}
