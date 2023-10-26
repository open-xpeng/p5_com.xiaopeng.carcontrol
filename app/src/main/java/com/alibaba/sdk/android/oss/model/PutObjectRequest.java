package com.alibaba.sdk.android.oss.model;

import android.net.Uri;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.callback.OSSRetryCallback;
import java.util.Map;

/* loaded from: classes.dex */
public class PutObjectRequest extends OSSRequest {
    private String bucketName;
    private Map<String, String> callbackParam;
    private Map<String, String> callbackVars;
    private ObjectMetadata metadata;
    private String objectKey;
    private OSSProgressCallback<PutObjectRequest> progressCallback;
    private OSSRetryCallback retryCallback;
    private byte[] uploadData;
    private String uploadFilePath;
    private Uri uploadUri;

    public PutObjectRequest(String str, String str2, String str3) {
        this(str, str2, str3, (ObjectMetadata) null);
    }

    public PutObjectRequest(String str, String str2, String str3, ObjectMetadata objectMetadata) {
        setBucketName(str);
        setObjectKey(str2);
        setUploadFilePath(str3);
        setMetadata(objectMetadata);
    }

    public PutObjectRequest(String str, String str2, byte[] bArr) {
        this(str, str2, bArr, (ObjectMetadata) null);
    }

    public PutObjectRequest(String str, String str2, byte[] bArr, ObjectMetadata objectMetadata) {
        setBucketName(str);
        setObjectKey(str2);
        setUploadData(bArr);
        setMetadata(objectMetadata);
    }

    public PutObjectRequest(String str, String str2, Uri uri) {
        this(str, str2, uri, (ObjectMetadata) null);
    }

    public PutObjectRequest(String str, String str2, Uri uri, ObjectMetadata objectMetadata) {
        setBucketName(str);
        setObjectKey(str2);
        setUploadUri(uri);
        setMetadata(objectMetadata);
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

    public String getUploadFilePath() {
        return this.uploadFilePath;
    }

    public void setUploadFilePath(String str) {
        this.uploadFilePath = str;
    }

    public byte[] getUploadData() {
        return this.uploadData;
    }

    public void setUploadData(byte[] bArr) {
        this.uploadData = bArr;
    }

    public ObjectMetadata getMetadata() {
        return this.metadata;
    }

    public Uri getUploadUri() {
        return this.uploadUri;
    }

    public void setUploadUri(Uri uri) {
        this.uploadUri = uri;
    }

    public void setMetadata(ObjectMetadata objectMetadata) {
        this.metadata = objectMetadata;
    }

    public OSSProgressCallback<PutObjectRequest> getProgressCallback() {
        return this.progressCallback;
    }

    public void setProgressCallback(OSSProgressCallback<PutObjectRequest> oSSProgressCallback) {
        this.progressCallback = oSSProgressCallback;
    }

    public OSSRetryCallback getRetryCallback() {
        return this.retryCallback;
    }

    public void setRetryCallback(OSSRetryCallback oSSRetryCallback) {
        this.retryCallback = oSSRetryCallback;
    }

    public Map<String, String> getCallbackParam() {
        return this.callbackParam;
    }

    public void setCallbackParam(Map<String, String> map) {
        this.callbackParam = map;
    }

    public Map<String, String> getCallbackVars() {
        return this.callbackVars;
    }

    public void setCallbackVars(Map<String, String> map) {
        this.callbackVars = map;
    }
}
