package com.alibaba.mtl.appmonitor;

import com.alibaba.mtl.appmonitor.AppMonitor;
import com.alibaba.mtl.appmonitor.model.DimensionSet;
import com.alibaba.mtl.appmonitor.model.DimensionValueSet;
import com.alibaba.mtl.appmonitor.model.MeasureSet;
import com.alibaba.mtl.appmonitor.model.MeasureValueSet;

/* loaded from: classes.dex */
public class AppMonitorStatTable {
    private String o;
    private String p;

    public AppMonitorStatTable(String str, String str2) {
        this.o = str;
        this.p = str2;
    }

    public AppMonitorStatTable registerRowAndColumn(DimensionSet dimensionSet, MeasureSet measureSet, boolean z) {
        AppMonitor.register(this.o, this.p, measureSet, dimensionSet, z);
        return this;
    }

    public AppMonitorStatTable update(DimensionValueSet dimensionValueSet, MeasureValueSet measureValueSet) {
        AppMonitor.Stat.commit(this.o, this.p, dimensionValueSet, measureValueSet);
        return this;
    }
}
