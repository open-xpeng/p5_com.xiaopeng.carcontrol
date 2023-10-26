package com.alibaba.sdk.android.oss.model;

import java.util.Date;

/* loaded from: classes.dex */
public class PartSummary {
    private String eTag;
    private Date lastModified;
    private int partNumber;
    private long size;

    public int getPartNumber() {
        return this.partNumber;
    }

    public void setPartNumber(int i) {
        this.partNumber = i;
    }

    public Date getLastModified() {
        return this.lastModified;
    }

    public void setLastModified(Date date) {
        this.lastModified = date;
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
}
