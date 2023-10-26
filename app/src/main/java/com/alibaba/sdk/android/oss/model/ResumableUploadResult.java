package com.alibaba.sdk.android.oss.model;

/* loaded from: classes.dex */
public class ResumableUploadResult extends CompleteMultipartUploadResult {
    public ResumableUploadResult(CompleteMultipartUploadResult completeMultipartUploadResult) {
        setRequestId(completeMultipartUploadResult.getRequestId());
        setResponseHeader(completeMultipartUploadResult.getResponseHeader());
        setStatusCode(completeMultipartUploadResult.getStatusCode());
        setClientCRC(completeMultipartUploadResult.getClientCRC());
        setServerCRC(completeMultipartUploadResult.getServerCRC());
        setBucketName(completeMultipartUploadResult.getBucketName());
        setObjectKey(completeMultipartUploadResult.getObjectKey());
        setETag(completeMultipartUploadResult.getETag());
        setLocation(completeMultipartUploadResult.getLocation());
        setServerCallbackReturnBody(completeMultipartUploadResult.getServerCallbackReturnBody());
    }
}
