package com.alibaba.sdk.android.oss.model;

/* loaded from: classes.dex */
public class GetBucketACLResult extends OSSResult {
    private CannedAccessControlList bucketACL;
    private Owner bucketOwner = new Owner();

    public Owner getOwner() {
        return this.bucketOwner;
    }

    public String getBucketOwner() {
        return this.bucketOwner.getDisplayName();
    }

    public void setBucketOwner(String str) {
        this.bucketOwner.setDisplayName(str);
    }

    public String getBucketOwnerID() {
        return this.bucketOwner.getId();
    }

    public void setBucketOwnerID(String str) {
        this.bucketOwner.setId(str);
    }

    public String getBucketACL() {
        CannedAccessControlList cannedAccessControlList = this.bucketACL;
        if (cannedAccessControlList != null) {
            return cannedAccessControlList.toString();
        }
        return null;
    }

    public void setBucketACL(String str) {
        this.bucketACL = CannedAccessControlList.parseACL(str);
    }
}
