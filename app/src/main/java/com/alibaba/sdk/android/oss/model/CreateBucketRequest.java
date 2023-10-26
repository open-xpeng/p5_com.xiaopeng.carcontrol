package com.alibaba.sdk.android.oss.model;

/* loaded from: classes.dex */
public class CreateBucketRequest extends OSSRequest {
    public static final String TAB_LOCATIONCONSTRAINT = "LocationConstraint";
    public static final String TAB_STORAGECLASS = "StorageClass";
    private CannedAccessControlList bucketACL;
    private String bucketName;
    private StorageClass bucketStorageClass = StorageClass.Standard;
    private String locationConstraint;

    public CreateBucketRequest(String str) {
        setBucketName(str);
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String str) {
        this.bucketName = str;
    }

    @Deprecated
    public String getLocationConstraint() {
        return this.locationConstraint;
    }

    @Deprecated
    public void setLocationConstraint(String str) {
        this.locationConstraint = str;
    }

    public CannedAccessControlList getBucketACL() {
        return this.bucketACL;
    }

    public void setBucketACL(CannedAccessControlList cannedAccessControlList) {
        this.bucketACL = cannedAccessControlList;
    }

    public StorageClass getBucketStorageClass() {
        return this.bucketStorageClass;
    }

    public void setBucketStorageClass(StorageClass storageClass) {
        this.bucketStorageClass = storageClass;
    }
}
