package com.alibaba.sdk.android.oss.model;

/* loaded from: classes.dex */
public class GetObjectACLResult extends OSSResult {
    private CannedAccessControlList objectACL;
    private Owner objectOwner = new Owner();

    public Owner getOwner() {
        return this.objectOwner;
    }

    public String getObjectOwner() {
        return this.objectOwner.getDisplayName();
    }

    public void setObjectOwner(String str) {
        this.objectOwner.setDisplayName(str);
    }

    public String getObjectOwnerID() {
        return this.objectOwner.getId();
    }

    public void setObjectOwnerID(String str) {
        this.objectOwner.setId(str);
    }

    public String getObjectACL() {
        CannedAccessControlList cannedAccessControlList = this.objectACL;
        if (cannedAccessControlList != null) {
            return cannedAccessControlList.toString();
        }
        return null;
    }

    public void setObjectACL(String str) {
        this.objectACL = CannedAccessControlList.parseACL(str);
    }
}
