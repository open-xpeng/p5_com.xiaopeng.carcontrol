package com.xiaopeng.xpmeditation.model;

import com.xiaopeng.xpmeditation.model.MeditationBean;
import java.io.Serializable;

/* loaded from: classes2.dex */
public class MeditationItemBeanPlus implements Serializable {
    private MeditationBean.DataBean.ListBeanPlus mData;
    private boolean mIsSelected;

    public void setData(MeditationBean.DataBean.ListBeanPlus bean) {
        this.mData = bean;
    }

    public MeditationBean.DataBean.ListBeanPlus getData() {
        return this.mData;
    }

    public void setSelected(boolean isSelected) {
        this.mIsSelected = isSelected;
    }

    public boolean isSelected() {
        return this.mIsSelected;
    }

    public String toString() {
        return "MeditationItemBeanPlus{mData=" + this.mData + ", mIsSelected=" + this.mIsSelected + '}';
    }
}
