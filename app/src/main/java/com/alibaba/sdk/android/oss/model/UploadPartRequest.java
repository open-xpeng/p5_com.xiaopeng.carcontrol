package com.alibaba.sdk.android.oss.model;

import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;

/* loaded from: classes.dex */
public class UploadPartRequest extends OSSRequest {
    private String bucketName;
    private String md5Digest;
    private String objectKey;
    private byte[] partContent;
    private int partNumber;
    private OSSProgressCallback<UploadPartRequest> progressCallback;
    private String uploadId;

    public UploadPartRequest() {
    }

    public UploadPartRequest(String str, String str2, String str3, int i) {
        this.bucketName = str;
        this.objectKey = str2;
        this.uploadId = str3;
        this.partNumber = i;
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

    public String getUploadId() {
        return this.uploadId;
    }

    public void setUploadId(String str) {
        this.uploadId = str;
    }

    public int getPartNumber() {
        return this.partNumber;
    }

    public void setPartNumber(int i) {
        this.partNumber = i;
    }

    public String getMd5Digest() {
        return this.md5Digest;
    }

    public void setMd5Digest(String str) {
        this.md5Digest = str;
    }

    public OSSProgressCallback<UploadPartRequest> getProgressCallback() {
        return this.progressCallback;
    }

    public void setProgressCallback(OSSProgressCallback<UploadPartRequest> oSSProgressCallback) {
        this.progressCallback = oSSProgressCallback;
    }

    public byte[] getPartContent() {
        return this.partContent;
    }

    public void setPartContent(byte[] bArr) {
        this.partContent = bArr;
    }
}
