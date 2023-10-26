package com.alibaba.sdk.android.oss.model;

/* loaded from: classes.dex */
public class InitiateMultipartUploadRequest extends OSSRequest {
    private String bucketName;
    public boolean isSequential;
    private ObjectMetadata metadata;
    private String objectKey;

    public InitiateMultipartUploadRequest(String str, String str2) {
        this(str, str2, null);
    }

    public InitiateMultipartUploadRequest(String str, String str2, ObjectMetadata objectMetadata) {
        setBucketName(str);
        setObjectKey(str2);
        setMetadata(objectMetadata);
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

    public ObjectMetadata getMetadata() {
        return this.metadata;
    }

    public void setMetadata(ObjectMetadata objectMetadata) {
        this.metadata = objectMetadata;
    }
}
