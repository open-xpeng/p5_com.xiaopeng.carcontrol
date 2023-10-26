package com.alibaba.sdk.android.oss.model;

/* loaded from: classes.dex */
public class HeadObjectResult extends OSSResult {
    private ObjectMetadata metadata = new ObjectMetadata();

    public ObjectMetadata getMetadata() {
        return this.metadata;
    }

    public void setMetadata(ObjectMetadata objectMetadata) {
        this.metadata = objectMetadata;
    }

    @Override // com.alibaba.sdk.android.oss.model.OSSResult
    public String toString() {
        return String.format("HeadObjectResult<%s>:\n metadata:%s", super.toString(), this.metadata.toString());
    }
}
