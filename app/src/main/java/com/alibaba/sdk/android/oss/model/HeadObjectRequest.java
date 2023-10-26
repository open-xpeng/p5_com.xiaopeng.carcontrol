package com.alibaba.sdk.android.oss.model;

/* loaded from: classes.dex */
public class HeadObjectRequest extends OSSRequest {
    private String bucketName;
    private String objectKey;

    public HeadObjectRequest(String str, String str2) {
        setBucketName(str);
        setObjectKey(str2);
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String str) {
        this.bucketName = str;
    }

    public String getObjectKey() {
        return this.objectKey;
    }

    public void setObjectKey(String str) {
        this.objectKey = str;
    }
}
