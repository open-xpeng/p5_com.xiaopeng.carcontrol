package com.alibaba.sdk.android.oss.model;

/* loaded from: classes.dex */
public class InitiateMultipartUploadResult extends OSSResult {
    private String bucketName;
    private String objectKey;
    private String uploadId;

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

    public String getUploadId() {
        return this.uploadId;
    }

    public void setUploadId(String str) {
        this.uploadId = str;
    }
}
