package com.alibaba.sdk.android.oss.model;

/* loaded from: classes.dex */
public class GetBucketLoggingResult extends OSSResult {
    private boolean mLoggingEnabled = false;
    private String mTargetBucketName;
    private String mTargetPrefix;

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

    public boolean loggingEnabled() {
        return this.mLoggingEnabled;
    }

    public void setLoggingEnabled(boolean z) {
        this.mLoggingEnabled = z;
    }
}
