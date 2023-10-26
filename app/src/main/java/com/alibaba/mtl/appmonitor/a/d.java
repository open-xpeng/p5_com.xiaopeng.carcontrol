package com.alibaba.mtl.appmonitor.a;

import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: Event.java */
/* loaded from: classes.dex */
public abstract class d implements com.alibaba.mtl.appmonitor.c.b {
    public int e;
    public String o;
    public String p;
    public String s;

    public JSONObject a() {
        JSONObject jSONObject = (JSONObject) com.alibaba.mtl.appmonitor.c.a.a().a(com.alibaba.mtl.appmonitor.c.e.class, new Object[0]);
        try {
            jSONObject.put("page", this.o);
            jSONObject.put("monitorPoint", this.p);
            String str = this.s;
            if (str != null) {
                jSONObject.put("arg", str);
            }
        } catch (JSONException unused) {
        }
        return jSONObject;
    }

    @Override // com.alibaba.mtl.appmonitor.c.b
    public void clean() {
        this.e = 0;
        this.o = null;
        this.p = null;
        this.s = null;
    }

    @Override // com.alibaba.mtl.appmonitor.c.b
    public void fill(Object... objArr) {
        this.e = ((Integer) objArr[0]).intValue();
        this.o = (String) objArr[1];
        this.p = (String) objArr[2];
        if (objArr.length <= 3 || objArr[3] == null) {
            return;
        }
        this.s = (String) objArr[3];
    }
}
