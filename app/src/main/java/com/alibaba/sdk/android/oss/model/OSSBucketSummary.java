package com.alibaba.sdk.android.oss.model;

import java.util.Date;

/* loaded from: classes.dex */
public class OSSBucketSummary {
    private CannedAccessControlList acl;
    public Date createDate;
    public String extranetEndpoint;
    public String intranetEndpoint;
    public String location;
    public String name;
    public Owner owner;
    public String storageClass;

    public String getAcl() {
        CannedAccessControlList cannedAccessControlList = this.acl;
        if (cannedAccessControlList != null) {
            return cannedAccessControlList.toString();
        }
        return null;
    }

    public void setAcl(String str) {
        this.acl = CannedAccessControlList.parseACL(str);
    }

    public String toString() {
        if (this.storageClass == null) {
            return "OSSBucket [name=" + this.name + ", creationDate=" + this.createDate + ", owner=" + this.owner.toString() + ", location=" + this.location + "]";
        }
        return "OSSBucket [name=" + this.name + ", creationDate=" + this.createDate + ", owner=" + this.owner.toString() + ", location=" + this.location + ", storageClass=" + this.storageClass + "]";
    }
}
