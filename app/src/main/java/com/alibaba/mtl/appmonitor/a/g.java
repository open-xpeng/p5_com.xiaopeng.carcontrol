package com.alibaba.mtl.appmonitor.a;

import com.alibaba.mtl.appmonitor.model.DimensionValueSet;
import com.alibaba.mtl.appmonitor.model.Measure;
import com.alibaba.mtl.appmonitor.model.MeasureValue;
import com.alibaba.mtl.appmonitor.model.MeasureValueSet;
import com.alibaba.mtl.appmonitor.model.Metric;
import com.alibaba.mtl.appmonitor.model.MetricRepo;
import com.alibaba.mtl.log.e.i;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: StatEvent.java */
/* loaded from: classes.dex */
public class g extends d {
    private Metric a;
    private Map<DimensionValueSet, a> values;

    public synchronized void a(DimensionValueSet dimensionValueSet, MeasureValueSet measureValueSet) {
        a aVar;
        if (dimensionValueSet == null) {
            DimensionValueSet dimensionValueSet2 = (DimensionValueSet) com.alibaba.mtl.appmonitor.c.a.a().a(DimensionValueSet.class, new Object[0]);
            dimensionValueSet2.addValues(dimensionValueSet);
            dimensionValueSet = dimensionValueSet2;
        }
        if (this.values.containsKey(dimensionValueSet)) {
            aVar = this.values.get(dimensionValueSet);
        } else {
            DimensionValueSet dimensionValueSet3 = (DimensionValueSet) com.alibaba.mtl.appmonitor.c.a.a().a(DimensionValueSet.class, new Object[0]);
            dimensionValueSet3.addValues(dimensionValueSet);
            a aVar2 = new a();
            this.values.put(dimensionValueSet3, aVar2);
            aVar = aVar2;
        }
        Metric metric = this.a;
        if (metric != null ? metric.valid(dimensionValueSet, measureValueSet) : false) {
            aVar.h();
            aVar.m18a(measureValueSet);
        } else {
            aVar.i();
            if (this.a.isCommitDetail()) {
                aVar.m18a(measureValueSet);
            }
        }
        i.a("StatEvent", "entity  count:", Integer.valueOf(aVar.count), " noise:", Integer.valueOf(aVar.l));
    }

    @Override // com.alibaba.mtl.appmonitor.a.d
    public synchronized JSONObject a() {
        JSONObject a2;
        Set<String> keySet;
        a2 = super.a();
        try {
            Metric metric = this.a;
            if (metric != null) {
                a2.put("isCommitDetail", String.valueOf(metric.isCommitDetail()));
            }
            JSONArray jSONArray = (JSONArray) com.alibaba.mtl.appmonitor.c.a.a().a(com.alibaba.mtl.appmonitor.c.d.class, new Object[0]);
            Map<DimensionValueSet, a> map = this.values;
            if (map != null) {
                for (Map.Entry<DimensionValueSet, a> entry : map.entrySet()) {
                    JSONObject jSONObject = (JSONObject) com.alibaba.mtl.appmonitor.c.a.a().a(com.alibaba.mtl.appmonitor.c.e.class, new Object[0]);
                    DimensionValueSet key = entry.getKey();
                    a value = entry.getValue();
                    Object valueOf = Integer.valueOf(value.count);
                    Object valueOf2 = Integer.valueOf(value.l);
                    jSONObject.put("count", valueOf);
                    jSONObject.put("noise", valueOf2);
                    jSONObject.put("dimensions", key != null ? new JSONObject(key.getMap()) : "");
                    List<Map<String, Map<String, Double>>> a3 = value.a();
                    JSONArray jSONArray2 = new JSONArray();
                    for (int i = 0; i < a3.size(); i++) {
                        JSONObject jSONObject2 = new JSONObject();
                        Map<String, Map<String, Double>> map2 = a3.get(i);
                        if (map2 != null && (keySet = map2.keySet()) != null) {
                            for (String str : keySet) {
                                if (map2.get(str) != null) {
                                    jSONObject2.put(str, new JSONObject(map2.get(str)));
                                } else {
                                    jSONObject2.put(str, "");
                                }
                            }
                        }
                        jSONArray2.put(jSONObject2);
                    }
                    jSONObject.put("measures", jSONArray2);
                    jSONArray.put(jSONObject);
                }
            }
            a2.put("values", jSONArray);
        } catch (Exception unused) {
        }
        return a2;
    }

    @Override // com.alibaba.mtl.appmonitor.a.d, com.alibaba.mtl.appmonitor.c.b
    public synchronized void clean() {
        super.clean();
        this.a = null;
        for (DimensionValueSet dimensionValueSet : this.values.keySet()) {
            com.alibaba.mtl.appmonitor.c.a.a().a((com.alibaba.mtl.appmonitor.c.a) dimensionValueSet);
        }
        this.values.clear();
    }

    @Override // com.alibaba.mtl.appmonitor.a.d, com.alibaba.mtl.appmonitor.c.b
    public void fill(Object... objArr) {
        super.fill(objArr);
        if (this.values == null) {
            this.values = new HashMap();
        }
        this.a = MetricRepo.getRepo().getMetric(this.o, this.p);
    }

    /* compiled from: StatEvent.java */
    /* loaded from: classes.dex */
    public class a {
        private int count = 0;
        private int l = 0;
        private List<MeasureValueSet> b = new ArrayList();

        public a() {
        }

        /* renamed from: a  reason: collision with other method in class */
        public void m18a(MeasureValueSet measureValueSet) {
            if (measureValueSet != null) {
                if (g.this.a != null && g.this.a.isCommitDetail()) {
                    this.b.add(a(measureValueSet));
                } else if (this.b.isEmpty()) {
                    this.b.add(a(measureValueSet));
                } else {
                    this.b.get(0).merge(measureValueSet);
                }
            }
        }

        private MeasureValueSet a(MeasureValueSet measureValueSet) {
            List<Measure> measures;
            MeasureValueSet measureValueSet2 = (MeasureValueSet) com.alibaba.mtl.appmonitor.c.a.a().a(MeasureValueSet.class, new Object[0]);
            if (g.this.a != null && g.this.a.getMeasureSet() != null && (measures = g.this.a.getMeasureSet().getMeasures()) != null) {
                int size = measures.size();
                for (int i = 0; i < size; i++) {
                    Measure measure = measures.get(i);
                    if (measure != null) {
                        MeasureValue measureValue = (MeasureValue) com.alibaba.mtl.appmonitor.c.a.a().a(MeasureValue.class, new Object[0]);
                        MeasureValue value = measureValueSet.getValue(measure.getName());
                        if (value.getOffset() != null) {
                            measureValue.setOffset(value.getOffset().doubleValue());
                        }
                        measureValue.setValue(value.getValue());
                        measureValueSet2.setValue(measure.getName(), measureValue);
                    }
                }
            }
            return measureValueSet2;
        }

        public List<Map<String, Map<String, Double>>> a() {
            Map<String, MeasureValue> map;
            List<MeasureValueSet> list = this.b;
            if (list == null || list.isEmpty()) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            int size = this.b.size();
            for (int i = 0; i < size; i++) {
                MeasureValueSet measureValueSet = this.b.get(i);
                if (measureValueSet != null && (map = measureValueSet.getMap()) != null && !map.isEmpty()) {
                    HashMap hashMap = new HashMap();
                    for (Map.Entry<String, MeasureValue> entry : map.entrySet()) {
                        HashMap hashMap2 = new HashMap();
                        String key = entry.getKey();
                        MeasureValue value = entry.getValue();
                        hashMap2.put("value", Double.valueOf(value.getValue()));
                        if (value.getOffset() != null) {
                            hashMap2.put("offset", value.getOffset());
                        }
                        hashMap.put(key, hashMap2);
                    }
                    arrayList.add(hashMap);
                }
            }
            return arrayList;
        }

        public void h() {
            this.count++;
        }

        public void i() {
            this.l++;
        }
    }
}
