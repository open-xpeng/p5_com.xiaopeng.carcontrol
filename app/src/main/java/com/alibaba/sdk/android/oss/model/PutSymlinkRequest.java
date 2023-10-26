package com.alibaba.sdk.android.oss.model;

/* loaded from: classes.dex */
public class PutSymlinkRequest extends OSSRequest {
    private String bucketName;
    private ObjectMetadata metadata;
    private String objectKey;
    private String targetObjectName;

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

    public String getTargetObjectName() {
        return this.targetObjectName;
    }

    public void setTargetObjectName(String str) {
        this.targetObjectName = str;
    }

    public ObjectMetadata getMetadata() {
        return this.metadata;
    }

    public void setMetadata(ObjectMetadata objectMetadata) {
        this.metadata = objectMetadata;
    }
}
