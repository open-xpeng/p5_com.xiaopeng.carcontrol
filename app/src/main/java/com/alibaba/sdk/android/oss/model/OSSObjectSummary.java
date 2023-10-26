package com.alibaba.sdk.android.oss.model;

import java.util.Date;

/* loaded from: classes.dex */
public class OSSObjectSummary {
    private String bucketName;
    private String eTag;
    private String key;
    private Date lastModified;
    private Owner owner;
    private long size;
    private String storageClass;
    private String type;

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String str) {
        this.bucketName = str;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String str) {
        this.key = str;
    }

    public String getETag() {
        return this.eTag;
    }

    public void setETag(String str) {
        this.eTag = str;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long j) {
        this.size = j;
    }

    public Date getLastModified() {
        return this.lastModified;
    }

    public void setLastModified(Date date) {
        this.lastModified = date;
    }

    public String getStorageClass() {
        return this.storageClass;
    }

    public void setStorageClass(String str) {
        this.storageClass = str;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public Owner getOwner() {
        return this.owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
