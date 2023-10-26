package com.alibaba.sdk.android.oss.model;

import java.util.Date;

/* loaded from: classes.dex */
public class CopyObjectResult extends OSSResult {
    private String etag;
    private Date lastModified;

    public String getETag() {
        return this.etag;
    }

    public void setEtag(String str) {
        this.etag = str;
    }

    public Date getLastModified() {
        return this.lastModified;
    }

    public void setLastModified(Date date) {
        this.lastModified = date;
    }
}
