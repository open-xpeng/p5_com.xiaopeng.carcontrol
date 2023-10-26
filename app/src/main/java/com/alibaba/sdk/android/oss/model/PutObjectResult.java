package com.alibaba.sdk.android.oss.model;

/* loaded from: classes.dex */
public class PutObjectResult extends OSSResult {
    private String eTag;
    private String serverCallbackReturnBody;

    public String getETag() {
        return this.eTag;
    }

    public void setETag(String str) {
        this.eTag = str;
    }

    public String getServerCallbackReturnBody() {
        return this.serverCallbackReturnBody;
    }

    public void setServerCallbackReturnBody(String str) {
        this.serverCallbackReturnBody = str;
    }
}
