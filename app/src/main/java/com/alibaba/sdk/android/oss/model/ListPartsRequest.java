package com.alibaba.sdk.android.oss.model;

/* loaded from: classes.dex */
public class ListPartsRequest extends OSSRequest {
    private String bucketName;
    private Integer maxParts;
    private String objectKey;
    private Integer partNumberMarker;
    private String uploadId;

    public ListPartsRequest(String str, String str2, String str3) {
        setBucketName(str);
        setObjectKey(str2);
        setUploadId(str3);
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

    public Integer getMaxParts() {
        return this.maxParts;
    }

    public void setMaxParts(int i) {
        this.maxParts = Integer.valueOf(i);
    }

    public Integer getPartNumberMarker() {
        return this.partNumberMarker;
    }

    public void setPartNumberMarker(Integer num) {
        this.partNumberMarker = num;
    }
}
