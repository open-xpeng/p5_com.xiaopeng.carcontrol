package com.alibaba.sdk.android.httpdns;

import com.lzy.okgo.cookie.SerializableCookie;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class e {
    private String[] a;
    private long b;
    private long c;
    private String hostName;

    /* JADX INFO: Access modifiers changed from: package-private */
    public e(com.alibaba.sdk.android.httpdns.b.e eVar) {
        int size;
        this.hostName = eVar.h;
        this.c = com.alibaba.sdk.android.httpdns.b.c.a(eVar.j);
        if (eVar.a == null || eVar.a.size() <= 0 || (size = eVar.a.size()) <= 0) {
            return;
        }
        this.b = -1000L;
        this.a = new String[size];
        for (int i = 0; i < size; i++) {
            this.a[i] = eVar.a.get(i).k;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public e(String str) {
        JSONObject jSONObject = new JSONObject(str);
        this.hostName = jSONObject.getString(SerializableCookie.HOST);
        JSONArray jSONArray = jSONObject.getJSONArray("ips");
        int length = jSONArray.length();
        this.a = new String[length];
        for (int i = 0; i < length; i++) {
            this.a[i] = jSONArray.getString(i);
        }
        this.b = jSONObject.getLong("ttl");
        this.c = System.currentTimeMillis() / 1000;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public e(String str, String[] strArr, long j, long j2) {
        this.hostName = str;
        this.a = strArr;
        this.b = j;
        this.c = j2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public long a() {
        return this.b;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: a  reason: collision with other method in class */
    public com.alibaba.sdk.android.httpdns.b.e m50a() {
        String[] strArr;
        com.alibaba.sdk.android.httpdns.b.e eVar = new com.alibaba.sdk.android.httpdns.b.e();
        eVar.h = this.hostName;
        eVar.j = String.valueOf(this.c);
        eVar.i = com.alibaba.sdk.android.httpdns.b.b.g();
        String[] strArr2 = this.a;
        if (strArr2 != null && strArr2.length > 0) {
            eVar.a = new ArrayList<>();
            for (String str : this.a) {
                com.alibaba.sdk.android.httpdns.b.g gVar = new com.alibaba.sdk.android.httpdns.b.g();
                gVar.k = str;
                gVar.l = String.valueOf(this.b);
                eVar.a.add(gVar);
            }
        }
        return eVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: a  reason: collision with other method in class */
    public String[] m51a() {
        return this.a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public long b() {
        return this.c;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: b  reason: collision with other method in class */
    public boolean m52b() {
        return b() + a() < System.currentTimeMillis() / 1000 || c();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean c() {
        return a() == -1000;
    }

    public String toString() {
        String str = "host: " + this.hostName + " ip cnt: " + this.a.length + " ttl: " + this.b;
        for (int i = 0; i < this.a.length; i++) {
            str = str + "\n ip: " + this.a[i];
        }
        return str;
    }
}
