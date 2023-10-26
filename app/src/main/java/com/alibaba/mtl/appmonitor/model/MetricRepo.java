package com.alibaba.mtl.appmonitor.model;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class MetricRepo {
    private static MetricRepo a;
    public List<Metric> metrics;

    public static MetricRepo getRepo() {
        if (a == null) {
            a = new MetricRepo(3);
        }
        return a;
    }

    public static MetricRepo getRepo(int i) {
        return new MetricRepo(i);
    }

    private MetricRepo(int i) {
        this.metrics = new ArrayList(i);
    }

    public void add(Metric metric) {
        if (this.metrics.contains(metric)) {
            return;
        }
        this.metrics.add(metric);
    }

    public boolean remove(Metric metric) {
        if (this.metrics.contains(metric)) {
            return this.metrics.remove(metric);
        }
        return true;
    }

    public Metric getMetric(String str, String str2) {
        List<Metric> list;
        if (str != null && str2 != null && (list = this.metrics) != null) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                Metric metric = this.metrics.get(i);
                if (metric != null && metric.getModule().equals(str) && metric.getMonitorPoint().equals(str2)) {
                    return metric;
                }
            }
        }
        return null;
    }
}
