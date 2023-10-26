package com.alibaba.sdk.android.oss.model;

import com.alibaba.sdk.android.oss.internal.CheckCRC64DownloadInputStream;
import java.io.InputStream;

/* loaded from: classes.dex */
public class GetObjectResult extends OSSResult {
    private long contentLength;
    private ObjectMetadata metadata = new ObjectMetadata();
    private InputStream objectContent;

    public ObjectMetadata getMetadata() {
        return this.metadata;
    }

    public void setMetadata(ObjectMetadata objectMetadata) {
        this.metadata = objectMetadata;
    }

    public InputStream getObjectContent() {
        return this.objectContent;
    }

    public void setObjectContent(InputStream inputStream) {
        this.objectContent = inputStream;
    }

    public long getContentLength() {
        return this.contentLength;
    }

    public void setContentLength(long j) {
        this.contentLength = j;
    }

    @Override // com.alibaba.sdk.android.oss.model.OSSResult
    public Long getClientCRC() {
        InputStream inputStream = this.objectContent;
        if (inputStream != null && (inputStream instanceof CheckCRC64DownloadInputStream)) {
            return Long.valueOf(((CheckCRC64DownloadInputStream) inputStream).getClientCRC64());
        }
        return super.getClientCRC();
    }
}
