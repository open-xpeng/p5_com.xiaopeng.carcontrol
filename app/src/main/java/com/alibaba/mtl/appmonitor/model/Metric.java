package com.alibaba.mtl.appmonitor.model;

import android.text.TextUtils;
import com.alibaba.mtl.appmonitor.c.b;
import java.util.List;
import java.util.UUID;

/* loaded from: classes.dex */
public class Metric implements b {
    private DimensionSet b;

    /* renamed from: b  reason: collision with other field name */
    private MeasureSet f50b;
    private boolean g;
    private String o;
    private String p;
    private String r;
    private String s;
    private String z;

    @Deprecated
    public Metric() {
        this.z = null;
    }

    public Metric(String str, String str2, MeasureSet measureSet, DimensionSet dimensionSet, boolean z) {
        this.z = null;
        this.o = str;
        this.p = str2;
        this.b = dimensionSet;
        this.f50b = measureSet;
        this.s = null;
        this.g = z;
    }

    public synchronized String getTransactionId() {
        if (this.r == null) {
            this.r = UUID.randomUUID().toString() + "$" + this.o + "$" + this.p;
        }
        return this.r;
    }

    public void resetTransactionId() {
        this.r = null;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x006a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean valid(com.alibaba.mtl.appmonitor.model.DimensionValueSet r6, com.alibaba.mtl.appmonitor.model.MeasureValueSet r7) {
        /*
            r5 = this;
            com.alibaba.mtl.appmonitor.model.DimensionSet r0 = r5.b
            r1 = 1
            if (r0 == 0) goto La
            boolean r6 = r0.valid(r6)
            goto Lb
        La:
            r6 = r1
        Lb:
            com.alibaba.mtl.appmonitor.model.MetricRepo r0 = com.alibaba.mtl.appmonitor.model.MetricRepo.getRepo()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "config_prefix"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r4 = r5.o
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.String r2 = r2.toString()
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.StringBuilder r3 = r4.append(r3)
            java.lang.String r4 = r5.p
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            com.alibaba.mtl.appmonitor.model.Metric r0 = r0.getMetric(r2, r3)
            r2 = 0
            if (r0 == 0) goto L8e
            com.alibaba.mtl.appmonitor.model.MeasureSet r3 = r0.getMeasureSet()
            if (r3 == 0) goto L8e
            if (r7 == 0) goto L8e
            java.util.Map r3 = r7.getMap()
            if (r3 == 0) goto L8e
            com.alibaba.mtl.appmonitor.model.MeasureSet r3 = r5.f50b
            if (r3 == 0) goto L8e
            com.alibaba.mtl.appmonitor.model.MeasureSet r0 = r0.getMeasureSet()
            java.util.List r0 = r0.getMeasures()
            java.util.Map r1 = r7.getMap()
            java.util.Set r1 = r1.keySet()
            java.util.Iterator r1 = r1.iterator()
        L64:
            boolean r3 = r1.hasNext()
            if (r3 == 0) goto L8d
            java.lang.Object r3 = r1.next()
            java.lang.String r3 = (java.lang.String) r3
            com.alibaba.mtl.appmonitor.model.Measure r4 = r5.a(r3, r0)
            if (r4 != 0) goto L80
            com.alibaba.mtl.appmonitor.model.MeasureSet r4 = r5.f50b
            java.util.List r4 = r4.getMeasures()
            com.alibaba.mtl.appmonitor.model.Measure r4 = r5.a(r3, r4)
        L80:
            if (r4 == 0) goto L8c
            com.alibaba.mtl.appmonitor.model.MeasureValue r3 = r7.getValue(r3)
            boolean r3 = r4.valid(r3)
            if (r3 != 0) goto L64
        L8c:
            return r2
        L8d:
            return r6
        L8e:
            com.alibaba.mtl.appmonitor.model.MeasureSet r0 = r5.f50b
            if (r0 == 0) goto L9d
            if (r6 == 0) goto L9b
            boolean r6 = r0.valid(r7)
            if (r6 == 0) goto L9b
            goto L9c
        L9b:
            r1 = r2
        L9c:
            r6 = r1
        L9d:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.mtl.appmonitor.model.Metric.valid(com.alibaba.mtl.appmonitor.model.DimensionValueSet, com.alibaba.mtl.appmonitor.model.MeasureValueSet):boolean");
    }

    private Measure a(String str, List<Measure> list) {
        if (list != null) {
            for (Measure measure : list) {
                if (TextUtils.equals(str, measure.name)) {
                    return measure;
                }
            }
            return null;
        }
        return null;
    }

    public String getModule() {
        return this.o;
    }

    public String getMonitorPoint() {
        return this.p;
    }

    public DimensionSet getDimensionSet() {
        return this.b;
    }

    public MeasureSet getMeasureSet() {
        return this.f50b;
    }

    public synchronized boolean isCommitDetail() {
        if ("1".equalsIgnoreCase(this.z)) {
            return true;
        }
        if ("0".equalsIgnoreCase(this.z)) {
            return false;
        }
        return this.g;
    }

    public int hashCode() {
        String str = this.s;
        int hashCode = ((str == null ? 0 : str.hashCode()) + 31) * 31;
        String str2 = this.o;
        int hashCode2 = (hashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.p;
        return hashCode2 + (str3 != null ? str3.hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            Metric metric = (Metric) obj;
            String str = this.s;
            if (str == null) {
                if (metric.s != null) {
                    return false;
                }
            } else if (!str.equals(metric.s)) {
                return false;
            }
            String str2 = this.o;
            if (str2 == null) {
                if (metric.o != null) {
                    return false;
                }
            } else if (!str2.equals(metric.o)) {
                return false;
            }
            String str3 = this.p;
            if (str3 == null) {
                if (metric.p != null) {
                    return false;
                }
            } else if (!str3.equals(metric.p)) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override // com.alibaba.mtl.appmonitor.c.b
    public void clean() {
        this.o = null;
        this.p = null;
        this.s = null;
        this.g = false;
        this.b = null;
        this.f50b = null;
        this.r = null;
    }

    @Override // com.alibaba.mtl.appmonitor.c.b
    public void fill(Object... objArr) {
        this.o = (String) objArr[0];
        this.p = (String) objArr[1];
        if (objArr.length > 2) {
            this.s = (String) objArr[2];
        }
    }

    public synchronized void setCommitDetailFromConfig(String str) {
        this.z = str;
    }
}
