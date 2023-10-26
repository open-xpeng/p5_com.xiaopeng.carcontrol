package com.alibaba.mtl.appmonitor.c;

import org.json.JSONArray;

/* compiled from: ReuseJSONArray.java */
/* loaded from: classes.dex */
public class d extends JSONArray implements b {
    @Override // com.alibaba.mtl.appmonitor.c.b
    public void fill(Object... objArr) {
    }

    @Override // com.alibaba.mtl.appmonitor.c.b
    public void clean() {
        for (int i = 0; i < length(); i++) {
            Object opt = opt(i);
            if (opt != null && (opt instanceof b)) {
                a.a().a((a) ((b) opt));
            }
        }
    }
}
