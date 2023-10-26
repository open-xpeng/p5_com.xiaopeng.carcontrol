package com.xiaopeng.xpmeditation.model;

import com.xiaopeng.xpmeditation.model.MeditationBean;
import java.io.Serializable;

/* loaded from: classes2.dex */
public class MeditationItemBean implements Serializable {
    private MeditationBean.DataBean.ListBean mData;
    private boolean mIsSelected;

    public void setData(MeditationBean.DataBean.ListBean bean) {
        this.mData = bean;
    }

    public MeditationBean.DataBean.ListBean getData() {
        return this.mData;
    }

    public void setSelected(boolean isSelected) {
        this.mIsSelected = isSelected;
    }

    public boolean isSelected() {
        return this.mIsSelected;
    }

    public String toString() {
        return "MeditationItemBean{mData=" + this.mData + ", mIsSelected=" + this.mIsSelected + '}';
    }
}
