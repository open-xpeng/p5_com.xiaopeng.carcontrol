package com.alibaba.sdk.android.oss.model;

/* loaded from: classes.dex */
public class PutBucketLoggingRequest extends OSSRequest {
    private String mBucketName;
    private String mTargetBucketName;
    private String mTargetPrefix;

    public String getBucketName() {
        return this.mBucketName;
    }

    public void setBucketName(String str) {
        this.mBucketName = str;
    }

    public String getTargetBucketName() {
        return this.mTargetBucketName;
    }

    public void setTargetBucketName(String str) {
        this.mTargetBucketName = str;
    }

    public String getTargetPrefix() {
        return this.mTargetPrefix;
    }

    public void setTargetPrefix(String str) {
        this.mTargetPrefix = str;
    }
}
