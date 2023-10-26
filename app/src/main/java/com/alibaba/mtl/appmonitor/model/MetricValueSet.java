package com.alibaba.mtl.appmonitor.model;

import com.alibaba.mtl.appmonitor.a.d;
import com.alibaba.mtl.appmonitor.a.f;
import com.alibaba.mtl.appmonitor.c.a;
import com.alibaba.mtl.appmonitor.c.b;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class MetricValueSet implements b {
    private Map<Metric, d> n = Collections.synchronizedMap(new HashMap());

    public List<d> getEvents() {
        return new ArrayList(this.n.values());
    }

    public d getEvent(Integer num, String str, String str2, String str3, Class<? extends d> cls) {
        Metric metric;
        boolean z;
        d dVar;
        boolean z2 = false;
        if (num.intValue() == f.STAT.a()) {
            metric = MetricRepo.getRepo().getMetric(str, str2);
            z = false;
        } else {
            metric = (Metric) a.a().a(Metric.class, str, str2, str3);
            z = true;
        }
        d dVar2 = null;
        if (metric != null) {
            if (this.n.containsKey(metric)) {
                dVar2 = this.n.get(metric);
                z2 = z;
            } else {
                synchronized (MetricValueSet.class) {
                    dVar = (d) a.a().a(cls, num, str, str2, str3);
                    this.n.put(metric, dVar);
                }
                dVar2 = dVar;
            }
            if (z2) {
                a.a().a((a) metric);
            }
        }
        return dVar2;
    }

    @Override // com.alibaba.mtl.appmonitor.c.b
    public void clean() {
        for (d dVar : this.n.values()) {
            a.a().a((a) dVar);
        }
        this.n.clear();
    }

    @Override // com.alibaba.mtl.appmonitor.c.b
    public void fill(Object... objArr) {
        if (this.n == null) {
            this.n = Collections.synchronizedMap(new HashMap());
        }
    }
}
