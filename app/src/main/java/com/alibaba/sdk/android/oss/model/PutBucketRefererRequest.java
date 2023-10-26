package com.alibaba.sdk.android.oss.model;

import java.util.ArrayList;

/* loaded from: classes.dex */
public class PutBucketRefererRequest extends OSSRequest {
    private boolean mAllowEmpty;
    private String mBucketName;
    private ArrayList<String> mReferers;

    public String getBucketName() {
        return this.mBucketName;
    }

    public void setBucketName(String str) {
        this.mBucketName = str;
    }

    public boolean isAllowEmpty() {
        return this.mAllowEmpty;
    }

    public void setAllowEmpty(boolean z) {
        this.mAllowEmpty = z;
    }

    public ArrayList<String> getReferers() {
        return this.mReferers;
    }

    public void setReferers(ArrayList<String> arrayList) {
        this.mReferers = arrayList;
    }
}
