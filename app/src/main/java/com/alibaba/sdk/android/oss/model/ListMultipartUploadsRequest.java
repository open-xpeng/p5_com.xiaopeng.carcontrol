package com.alibaba.sdk.android.oss.model;

/* loaded from: classes.dex */
public class ListMultipartUploadsRequest extends OSSRequest {
    private String bucketName;
    private String delimiter;
    private String encodingType;
    private String keyMarker;
    private Integer maxUploads;
    private String prefix;
    private String uploadIdMarker;

    public ListMultipartUploadsRequest(String str) {
        this.bucketName = str;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public Integer getMaxUploads() {
        return this.maxUploads;
    }

    public void setMaxUploads(Integer num) {
        this.maxUploads = num;
    }

    public String getKeyMarker() {
        return this.keyMarker;
    }

    public void setKeyMarker(String str) {
        this.keyMarker = str;
    }

    public String getUploadIdMarker() {
        return this.uploadIdMarker;
    }

    public void setUploadIdMarker(String str) {
        this.uploadIdMarker = str;
    }

    public String getDelimiter() {
        return this.delimiter;
    }

    public void setDelimiter(String str) {
        this.delimiter = str;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String str) {
        this.prefix = str;
    }

    public String getEncodingType() {
        return this.encodingType;
    }

    public void setEncodingType(String str) {
        this.encodingType = str;
    }
}
