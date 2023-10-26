package com.alibaba.mtl.appmonitor.d;

import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: AlarmModuleSampling.java */
/* loaded from: classes.dex */
public class d extends h {
    private int o;
    private int p;

    @Override // com.alibaba.mtl.appmonitor.d.h
    public /* bridge */ /* synthetic */ boolean a(int i, String str, Map map) {
        return super.a(i, str, map);
    }

    public d(String str, int i, int i2) {
        super(str, 0);
        this.o = this.n;
        this.p = this.n;
    }

    public boolean a(int i, String str, Boolean bool, Map<String, String> map) {
        i iVar;
        com.alibaba.mtl.log.e.i.a("AlarmModuleSampling", "samplingSeed:", Integer.valueOf(i), "isSuccess:", bool, "successSampling:", Integer.valueOf(this.o), "failSampling:", Integer.valueOf(this.p));
        if (this.r != null && (iVar = this.r.get(str)) != null && (iVar instanceof e)) {
            return ((e) iVar).a(i, bool, map);
        }
        return a(i, bool.booleanValue());
    }

    @Override // com.alibaba.mtl.appmonitor.d.h
    public void b(JSONObject jSONObject) {
        a(jSONObject);
        try {
            JSONArray jSONArray = jSONObject.getJSONArray("monitorPoints");
            if (jSONArray != null) {
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                    String string = jSONObject2.getString("monitorPoint");
                    if (com.alibaba.mtl.appmonitor.f.b.c(string)) {
                        i iVar = this.r.get(string);
                        if (iVar == null) {
                            iVar = new e(string, this.o, this.p);
                            this.r.put(string, iVar);
                        }
                        iVar.b(jSONObject2);
                    }
                }
            }
        } catch (Exception unused) {
        }
    }

    protected boolean a(int i, boolean z) {
        return z ? i < this.o : i < this.p;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.alibaba.mtl.appmonitor.d.a
    public void a(JSONObject jSONObject) {
        super.a((d) jSONObject);
        this.o = this.n;
        this.p = this.n;
        try {
            Integer valueOf = Integer.valueOf(jSONObject.getInt("successSampling"));
            if (valueOf != null) {
                this.o = valueOf.intValue();
            }
            Integer valueOf2 = Integer.valueOf(jSONObject.getInt("failSampling"));
            if (valueOf2 != null) {
                this.p = valueOf2.intValue();
            }
            com.alibaba.mtl.log.e.i.a("AlarmModuleSampling", "[updateSelfSampling]", jSONObject, "successSampling:", valueOf, "failSampling");
        } catch (Exception unused) {
        }
    }
}
