package com.alibaba.sdk.android.oss.model;

import java.util.ArrayList;

/* loaded from: classes.dex */
public class GetBucketRefererResult extends OSSResult {
    private String mAllowEmpty;
    private ArrayList<String> mReferers;

    public String getAllowEmpty() {
        return this.mAllowEmpty;
    }

    public void setAllowEmpty(String str) {
        this.mAllowEmpty = str;
    }

    public ArrayList<String> getReferers() {
        return this.mReferers;
    }

    public void setReferers(ArrayList<String> arrayList) {
        this.mReferers = arrayList;
    }

    public void addReferer(String str) {
        if (this.mReferers == null) {
            this.mReferers = new ArrayList<>();
        }
        this.mReferers.add(str);
    }
}
