package com.alibaba.mtl.appmonitor.a;

import com.alibaba.mtl.appmonitor.model.DimensionValueSet;
import com.alibaba.mtl.appmonitor.model.Measure;
import com.alibaba.mtl.appmonitor.model.MeasureValue;
import com.alibaba.mtl.appmonitor.model.MeasureValueSet;
import com.alibaba.mtl.appmonitor.model.Metric;
import com.alibaba.mtl.appmonitor.model.MetricRepo;
import com.alibaba.mtl.log.e.i;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: DurationEvent.java */
/* loaded from: classes.dex */
public class c extends d {
    private static final Long a = 300000L;

    /* renamed from: a  reason: collision with other field name */
    private Metric f40a;
    private DimensionValueSet b;

    /* renamed from: b  reason: collision with other field name */
    private MeasureValueSet f41b;

    /* renamed from: b  reason: collision with other field name */
    private Long f42b;
    private Map<String, MeasureValue> i;

    public boolean c() {
        long currentTimeMillis = System.currentTimeMillis();
        List<Measure> measures = this.f40a.getMeasureSet().getMeasures();
        if (measures != null) {
            int size = measures.size();
            for (int i = 0; i < size; i++) {
                Measure measure = measures.get(i);
                if (measure != null) {
                    double doubleValue = measure.getMax() != null ? measure.getMax().doubleValue() : a.longValue();
                    MeasureValue measureValue = this.i.get(measure.getName());
                    if (measureValue != null && !measureValue.isFinish() && currentTimeMillis - measureValue.getValue() > doubleValue) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void a(String str) {
        long currentTimeMillis = System.currentTimeMillis();
        if (this.i.isEmpty()) {
            this.f42b = Long.valueOf(currentTimeMillis);
        }
        this.i.put(str, (MeasureValue) com.alibaba.mtl.appmonitor.c.a.a().a(MeasureValue.class, Double.valueOf(currentTimeMillis), Double.valueOf(currentTimeMillis - this.f42b.longValue())));
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m15a(String str) {
        MeasureValue measureValue = this.i.get(str);
        if (measureValue != null) {
            double currentTimeMillis = System.currentTimeMillis();
            i.a("DurationEvent", "statEvent consumeTime. module:", this.o, " monitorPoint:", this.p, " measureName:", str, " time:", Double.valueOf(currentTimeMillis - measureValue.getValue()));
            measureValue.setValue(currentTimeMillis - measureValue.getValue());
            measureValue.setFinish(true);
            this.f41b.setValue(str, measureValue);
            if (this.f40a.getMeasureSet().valid(this.f41b)) {
                return true;
            }
        }
        return false;
    }

    public void a(DimensionValueSet dimensionValueSet) {
        DimensionValueSet dimensionValueSet2 = this.b;
        if (dimensionValueSet2 == null) {
            this.b = dimensionValueSet;
        } else {
            dimensionValueSet2.addValues(dimensionValueSet);
        }
    }

    /* renamed from: a  reason: collision with other method in class */
    public MeasureValueSet m14a() {
        return this.f41b;
    }

    public DimensionValueSet a() {
        return this.b;
    }

    @Override // com.alibaba.mtl.appmonitor.a.d, com.alibaba.mtl.appmonitor.c.b
    public void clean() {
        super.clean();
        this.f40a = null;
        this.f42b = null;
        for (MeasureValue measureValue : this.i.values()) {
            com.alibaba.mtl.appmonitor.c.a.a().a((com.alibaba.mtl.appmonitor.c.a) measureValue);
        }
        this.i.clear();
        if (this.f41b != null) {
            com.alibaba.mtl.appmonitor.c.a.a().a((com.alibaba.mtl.appmonitor.c.a) this.f41b);
            this.f41b = null;
        }
        if (this.b != null) {
            com.alibaba.mtl.appmonitor.c.a.a().a((com.alibaba.mtl.appmonitor.c.a) this.b);
            this.b = null;
        }
    }

    @Override // com.alibaba.mtl.appmonitor.a.d, com.alibaba.mtl.appmonitor.c.b
    public void fill(Object... objArr) {
        super.fill(objArr);
        if (this.i == null) {
            this.i = new HashMap();
        }
        Metric metric = MetricRepo.getRepo().getMetric(this.o, this.p);
        this.f40a = metric;
        if (metric.getDimensionSet() != null) {
            this.b = (DimensionValueSet) com.alibaba.mtl.appmonitor.c.a.a().a(DimensionValueSet.class, new Object[0]);
            this.f40a.getDimensionSet().setConstantValue(this.b);
        }
        this.f41b = (MeasureValueSet) com.alibaba.mtl.appmonitor.c.a.a().a(MeasureValueSet.class, new Object[0]);
    }
}
