package com.alibaba.sdk.android.oss.model;

import java.util.ArrayList;

/* loaded from: classes.dex */
public class PutBucketLifecycleRequest extends OSSRequest {
    ArrayList<BucketLifecycleRule> lifecycleRules;
    private String mBucketName;

    public String getBucketName() {
        return this.mBucketName;
    }

    public void setBucketName(String str) {
        this.mBucketName = str;
    }

    public ArrayList<BucketLifecycleRule> getLifecycleRules() {
        return this.lifecycleRules;
    }

    public void setLifecycleRules(ArrayList<BucketLifecycleRule> arrayList) {
        this.lifecycleRules = arrayList;
    }
}
