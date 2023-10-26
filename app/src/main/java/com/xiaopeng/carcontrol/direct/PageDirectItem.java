package com.xiaopeng.carcontrol.direct;

import com.xiaopeng.carcontrol.direct.action.PageAction;
import com.xiaopeng.carcontrol.direct.support.SupportCheckAction;

/* loaded from: classes2.dex */
public class PageDirectItem {
    private Object mData;
    private PageAction mPageAction;
    private String mSecondName;
    private SupportCheckAction mSupportAction;

    public PageDirectItem(PageAction pageAction) {
        this.mPageAction = pageAction;
    }

    public PageDirectItem(Object data, PageAction pageAction) {
        this.mData = data;
        this.mPageAction = pageAction;
    }

    public PageDirectItem(String secondName, Object data, PageAction pageAction) {
        this.mData = data;
        this.mPageAction = pageAction;
        this.mSecondName = secondName;
    }

    public PageDirectItem(Object data, PageAction pageAction, SupportCheckAction supportAction) {
        this.mData = data;
        this.mPageAction = pageAction;
        this.mSupportAction = supportAction;
    }

    public PageDirectItem(String secondName, Object data, PageAction pageAction, SupportCheckAction supportAction) {
        this.mData = data;
        this.mPageAction = pageAction;
        this.mSupportAction = supportAction;
        this.mSecondName = secondName;
    }

    public PageDirectItem(Object data, SupportCheckAction supportAction) {
        this.mData = data;
        this.mSupportAction = supportAction;
    }

    public SupportCheckAction getSupportAction() {
        return this.mSupportAction;
    }

    public void setSupportAction(SupportCheckAction supportAction) {
        this.mSupportAction = supportAction;
    }

    public Object getData() {
        return this.mData;
    }

    public void setData(Object data) {
        this.mData = data;
    }

    public PageAction getPageAction() {
        return this.mPageAction;
    }

    public void setPageAction(PageAction pageAction) {
        this.mPageAction = pageAction;
    }

    public String getSecondName() {
        return this.mSecondName;
    }

    public void setSecondName(String secondName) {
        this.mSecondName = secondName;
    }

    public String toString() {
        return "PageDirectItem{data=" + this.mData + ", mPageAction=" + this.mPageAction + ", mSupportAction=" + this.mSupportAction + ", secondName='" + this.mSecondName + "'}";
    }
}
