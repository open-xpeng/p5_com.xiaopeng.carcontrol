package com.alibaba.mtl.appmonitor.a;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: AlarmEvent.java */
/* loaded from: classes.dex */
public class a extends d {
    public int f = 0;
    public int g = 0;

    /* renamed from: g  reason: collision with other field name */
    public Map<String, String> f38g;
    public Map<String, Integer> h;

    public synchronized void e() {
        this.f++;
    }

    public synchronized void f() {
        this.g++;
    }

    public synchronized void a(String str, String str2) {
        if (com.alibaba.mtl.appmonitor.f.b.d(str)) {
            return;
        }
        if (this.f38g == null) {
            this.f38g = new HashMap();
        }
        if (this.h == null) {
            this.h = new HashMap();
        }
        if (com.alibaba.mtl.appmonitor.f.b.c(str2)) {
            int i = 100;
            if (str2.length() <= 100) {
                i = str2.length();
            }
            this.f38g.put(str, str2.substring(0, i));
        }
        if (!this.h.containsKey(str)) {
            this.h.put(str, 1);
        } else {
            Map<String, Integer> map = this.h;
            map.put(str, Integer.valueOf(map.get(str).intValue() + 1));
        }
    }

    @Override // com.alibaba.mtl.appmonitor.a.d
    public synchronized JSONObject a() {
        JSONObject a;
        a = super.a();
        try {
            a.put("successCount", this.f);
            a.put("failCount", this.g);
            if (this.h != null) {
                JSONArray jSONArray = (JSONArray) com.alibaba.mtl.appmonitor.c.a.a().a(com.alibaba.mtl.appmonitor.c.d.class, new Object[0]);
                for (Map.Entry<String, Integer> entry : this.h.entrySet()) {
                    JSONObject jSONObject = (JSONObject) com.alibaba.mtl.appmonitor.c.a.a().a(com.alibaba.mtl.appmonitor.c.e.class, new Object[0]);
                    String key = entry.getKey();
                    jSONObject.put("errorCode", key);
                    jSONObject.put("errorCount", entry.getValue());
                    if (this.f38g.containsKey(key)) {
                        jSONObject.put("errorMsg", this.f38g.get(key));
                    }
                    jSONArray.put(jSONObject);
                }
                a.put("errors", jSONArray);
            }
        } catch (Exception unused) {
        }
        return a;
    }

    @Override // com.alibaba.mtl.appmonitor.a.d, com.alibaba.mtl.appmonitor.c.b
    public synchronized void clean() {
        super.clean();
        this.f = 0;
        this.g = 0;
        Map<String, String> map = this.f38g;
        if (map != null) {
            map.clear();
        }
        Map<String, Integer> map2 = this.h;
        if (map2 != null) {
            map2.clear();
        }
    }
}
