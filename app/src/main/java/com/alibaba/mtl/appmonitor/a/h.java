package com.alibaba.mtl.appmonitor.a;

import java.util.HashMap;
import java.util.Map;

/* compiled from: UTEvent.java */
/* loaded from: classes.dex */
public class h implements com.alibaba.mtl.appmonitor.c.b {
    public int e;
    public Map<String, String> m;
    public String u;
    public String v;
    public String w;
    public String x;

    @Override // com.alibaba.mtl.appmonitor.c.b
    public void clean() {
        this.u = null;
        this.e = 0;
        this.v = null;
        this.w = null;
        this.x = null;
        Map<String, String> map = this.m;
        if (map != null) {
            map.clear();
        }
    }

    @Override // com.alibaba.mtl.appmonitor.c.b
    public void fill(Object... objArr) {
        if (this.m == null) {
            this.m = new HashMap();
        }
    }
}
