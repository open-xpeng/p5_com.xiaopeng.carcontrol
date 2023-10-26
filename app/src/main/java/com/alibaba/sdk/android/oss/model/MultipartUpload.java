package com.alibaba.sdk.android.oss.model;

import java.util.Date;

/* loaded from: classes.dex */
public class MultipartUpload {
    private Date initiated;
    private String key;
    private String storageClass;
    private String uploadId;

    public String getKey() {
        return this.key;
    }

    public void setKey(String str) {
        this.key = str;
    }

    public String getUploadId() {
        return this.uploadId;
    }

    public void setUploadId(String str) {
        this.uploadId = str;
    }

    public String getStorageClass() {
        return this.storageClass;
    }

    public void setStorageClass(String str) {
        this.storageClass = str;
    }

    public Date getInitiated() {
        return this.initiated;
    }

    public void setInitiated(Date date) {
        this.initiated = date;
    }
}
