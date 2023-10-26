package com.alibaba.sdk.android.oss.model;

import android.net.Uri;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;

/* loaded from: classes.dex */
public class AppendObjectRequest extends OSSRequest {
    private String bucketName;
    private Long initCRC64;
    private ObjectMetadata metadata;
    private String objectKey;
    private long position;
    private OSSProgressCallback<AppendObjectRequest> progressCallback;
    private byte[] uploadData;
    private String uploadFilePath;
    private Uri uploadUri;

    public AppendObjectRequest(String str, String str2, String str3) {
        this(str, str2, str3, (ObjectMetadata) null);
    }

    public AppendObjectRequest(String str, String str2, String str3, ObjectMetadata objectMetadata) {
        setBucketName(str);
        setObjectKey(str2);
        setUploadFilePath(str3);
        setMetadata(objectMetadata);
    }

    public AppendObjectRequest(String str, String str2, byte[] bArr) {
        this(str, str2, bArr, (ObjectMetadata) null);
    }

    public AppendObjectRequest(String str, String str2, byte[] bArr, ObjectMetadata objectMetadata) {
        setBucketName(str);
        setObjectKey(str2);
        setUploadData(bArr);
        setMetadata(objectMetadata);
    }

    public AppendObjectRequest(String str, String str2, Uri uri) {
        this(str, str2, uri, (ObjectMetadata) null);
    }

    public AppendObjectRequest(String str, String str2, Uri uri, ObjectMetadata objectMetadata) {
        setBucketName(str);
        setObjectKey(str2);
        setUploadUri(uri);
        setMetadata(objectMetadata);
    }

    public long getPosition() {
        return this.position;
    }

    public void setPosition(long j) {
        this.position = j;
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

    public Uri getUploadUri() {
        return this.uploadUri;
    }

    public void setUploadUri(Uri uri) {
        this.uploadUri = uri;
    }

    public ObjectMetadata getMetadata() {
        return this.metadata;
    }

    public void setMetadata(ObjectMetadata objectMetadata) {
        this.metadata = objectMetadata;
    }

    public OSSProgressCallback<AppendObjectRequest> getProgressCallback() {
        return this.progressCallback;
    }

    public void setProgressCallback(OSSProgressCallback<AppendObjectRequest> oSSProgressCallback) {
        this.progressCallback = oSSProgressCallback;
    }

    public Long getInitCRC64() {
        return this.initCRC64;
    }

    public void setInitCRC64(Long l) {
        this.initCRC64 = l;
    }
}
