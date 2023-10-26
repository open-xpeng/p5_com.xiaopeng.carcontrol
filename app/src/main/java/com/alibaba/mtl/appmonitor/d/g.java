package com.alibaba.mtl.appmonitor.d;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: EventTypeSampling.java */
/* loaded from: classes.dex */
class g extends a<JSONObject> {
    private com.alibaba.mtl.appmonitor.a.f e;
    protected int q;

    /* renamed from: q  reason: collision with other field name */
    protected Map<String, h> f49q;

    public g(com.alibaba.mtl.appmonitor.a.f fVar, int i) {
        super(i);
        this.q = -1;
        this.e = fVar;
        this.f49q = Collections.synchronizedMap(new HashMap());
    }

    public boolean a(int i, String str, String str2, Map<String, String> map) {
        h hVar;
        Map<String, h> map2 = this.f49q;
        if (map2 == null || (hVar = map2.get(str)) == null) {
            return i < this.n;
        }
        return hVar.a(i, str2, map);
    }

    public void b(JSONObject jSONObject) {
        a((g) jSONObject);
        c(jSONObject);
        this.f49q.clear();
        try {
            JSONArray optJSONArray = jSONObject.optJSONArray("metrics");
            if (optJSONArray != null) {
                for (int i = 0; i < optJSONArray.length(); i++) {
                    JSONObject jSONObject2 = optJSONArray.getJSONObject(i);
                    String optString = jSONObject2.optString("module");
                    if (com.alibaba.mtl.appmonitor.f.b.c(optString)) {
                        h hVar = this.f49q.get(optString);
                        if (hVar == null) {
                            hVar = new h(optString, this.n);
                            this.f49q.put(optString, hVar);
                        }
                        hVar.b(jSONObject2);
                    }
                }
            }
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void c(JSONObject jSONObject) {
        com.alibaba.mtl.appmonitor.a.f fVar;
        com.alibaba.mtl.log.e.i.a("EventTypeSampling", "[updateEventTypeTriggerCount]", this, jSONObject);
        if (jSONObject == null) {
            return;
        }
        try {
            int optInt = jSONObject.optInt("cacheCount");
            if (optInt <= 0 || (fVar = this.e) == null) {
                return;
            }
            fVar.b(optInt);
        } catch (Throwable th) {
            com.alibaba.mtl.log.e.i.a("EventTypeSampling", "updateTriggerCount", th);
        }
    }

    public void setSampling(int i) {
        this.n = i;
    }
}
