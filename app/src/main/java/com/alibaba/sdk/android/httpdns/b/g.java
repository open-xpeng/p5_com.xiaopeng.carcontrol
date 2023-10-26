package com.alibaba.sdk.android.httpdns.b;

/* loaded from: classes.dex */
public class g {
    public long h;
    public long id;
    public String k;
    public String l;

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("[IpRecord] ");
        sb.append("id:");
        sb.append(this.id);
        sb.append("|");
        sb.append("host_id:");
        sb.append(this.h);
        sb.append("|");
        sb.append("ip:");
        sb.append(this.k);
        sb.append("|");
        sb.append("ttl:");
        sb.append(this.l);
        sb.append("|");
        return sb.toString();
    }
}
