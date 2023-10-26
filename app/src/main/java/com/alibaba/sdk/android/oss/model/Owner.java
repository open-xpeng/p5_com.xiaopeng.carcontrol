package com.alibaba.sdk.android.oss.model;

import java.io.Serializable;

/* loaded from: classes.dex */
public class Owner implements Serializable {
    private static final long serialVersionUID = -1942759024112448066L;
    private String displayName;
    private String id;

    public Owner() {
        this(null, null);
    }

    public Owner(String str, String str2) {
        this.id = str;
        this.displayName = str2;
    }

    public String toString() {
        return "Owner [name=" + getDisplayName() + ",id=" + getId() + "]";
    }

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String str) {
        this.displayName = str;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Owner) {
            Owner owner = (Owner) obj;
            String id = owner.getId();
            String displayName = owner.getDisplayName();
            String id2 = getId();
            String displayName2 = getDisplayName();
            if (id == null) {
                id = "";
            }
            if (displayName == null) {
                displayName = "";
            }
            if (id2 == null) {
                id2 = "";
            }
            if (displayName2 == null) {
                displayName2 = "";
            }
            return id.equals(id2) && displayName.equals(displayName2);
        }
        return false;
    }

    public int hashCode() {
        String str = this.id;
        if (str != null) {
            return str.hashCode();
        }
        return 0;
    }
}
