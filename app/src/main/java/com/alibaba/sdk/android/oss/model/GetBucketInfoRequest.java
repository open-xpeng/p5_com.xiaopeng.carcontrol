package com.alibaba.sdk.android.oss.model;

/* loaded from: classes.dex */
public class GetBucketInfoRequest extends OSSRequest {
    private String bucketName;

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String str) {
        this.bucketName = str;
    }

    public GetBucketInfoRequest(String str) {
        this.bucketName = str;
    }
}
