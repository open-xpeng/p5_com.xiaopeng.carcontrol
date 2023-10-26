package com.alibaba.sdk.android.httpdns.probe;

/* loaded from: classes.dex */
class c {
    private String[] a;
    private String hostName;
    private long j;
    private long k;
    private String o;
    private String p;

    /* JADX INFO: Access modifiers changed from: package-private */
    public c(String str, String[] strArr, String str2, String str3, long j, long j2) {
        this.hostName = str;
        this.a = strArr;
        this.o = str2;
        this.p = str3;
        this.j = j;
        this.k = j2;
    }

    public String[] a() {
        return this.a;
    }

    public long c() {
        return this.j;
    }

    public long d() {
        return this.k;
    }

    public String getHostName() {
        return this.hostName;
    }

    public String i() {
        return this.o;
    }

    public String j() {
        return this.p;
    }
}
