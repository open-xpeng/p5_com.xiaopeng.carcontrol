package com.xiaopeng.speech.protocol.bean.skill;

import com.xiaopeng.speech.common.util.DontProguardClass;
import java.util.List;

@DontProguardClass
/* loaded from: classes2.dex */
public class SkillBean {
    private String function;
    private boolean isChange = true;
    private List<TipsBean> tips;

    public String getFunction() {
        return this.function;
    }

    public void setFunction(String str) {
        this.function = str;
    }

    public List<TipsBean> getTips() {
        return this.tips;
    }

    public void setTips(List<TipsBean> list) {
        this.tips = list;
    }

    public boolean isChange() {
        return this.isChange;
    }

    public void setChange(boolean z) {
        this.isChange = z;
    }
}
