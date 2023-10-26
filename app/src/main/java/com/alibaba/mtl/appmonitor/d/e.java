package com.alibaba.mtl.appmonitor.d;

import java.util.Map;
import org.json.JSONObject;

/* compiled from: AlarmMonitorPointSampling.java */
/* loaded from: classes.dex */
public class e extends i {
    private int o;
    private int p;

    @Override // com.alibaba.mtl.appmonitor.d.i
    public /* bridge */ /* synthetic */ boolean a(int i, Map map) {
        return super.a(i, map);
    }

    public e(String str, int i, int i2) {
        super(str, 0);
        this.o = i;
        this.p = i2;
    }

    public boolean a(int i, Boolean bool, Map<String, String> map) {
        com.alibaba.mtl.log.e.i.a("AlarmMonitorPointSampling", "samplingSeed:", Integer.valueOf(i), "isSuccess:", bool, "successSampling:", Integer.valueOf(this.o), "failSampling:", Integer.valueOf(this.p));
        if (this.e != null && map != null) {
            for (c cVar : this.e) {
                Boolean a = cVar.a(i, map);
                if (a != null) {
                    return a.booleanValue();
                }
            }
        }
        return a(i, bool.booleanValue());
    }

    protected boolean a(int i, boolean z) {
        return z ? i < this.o : i < this.p;
    }

    @Override // com.alibaba.mtl.appmonitor.d.i
    public void b(JSONObject jSONObject) {
        super.b(jSONObject);
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
            com.alibaba.mtl.log.e.i.a("AlarmMonitorPointSampling", "[updateSelfSampling]", jSONObject, "successSampling:", valueOf, "failSampling", valueOf2);
        } catch (Exception unused) {
        }
    }
}
