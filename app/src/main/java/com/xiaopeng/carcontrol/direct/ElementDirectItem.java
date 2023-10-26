package com.xiaopeng.carcontrol.direct;

import com.xiaopeng.carcontrol.direct.support.SupportCheckAction;

/* loaded from: classes2.dex */
public class ElementDirectItem {
    private int id;
    private String key;
    private SupportCheckAction mSupportAction;
    private int parentId;

    public ElementDirectItem(String key, int id) {
        this.key = key;
        this.id = id;
    }

    public ElementDirectItem(String key, int id, int parentId) {
        this.key = key;
        this.id = id;
        this.parentId = parentId;
    }

    public ElementDirectItem(String key, int id, SupportCheckAction supportAction) {
        this.key = key;
        this.id = id;
        this.mSupportAction = supportAction;
    }

    public ElementDirectItem(String key, int id, int parentId, SupportCheckAction supportAction) {
        this.key = key;
        this.id = id;
        this.parentId = parentId;
        this.mSupportAction = supportAction;
    }

    public ElementDirectItem(SupportCheckAction supportCheckAction) {
        this.mSupportAction = supportCheckAction;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return this.parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public SupportCheckAction getSupportAction() {
        return this.mSupportAction;
    }

    public void setSupportAction(SupportCheckAction supportAction) {
        this.mSupportAction = supportAction;
    }

    public String toString() {
        return "ElementDirectItem{key='" + this.key + "', id=" + this.id + ", parentId=" + this.parentId + ", mSupportAction=" + this.mSupportAction + '}';
    }
}
