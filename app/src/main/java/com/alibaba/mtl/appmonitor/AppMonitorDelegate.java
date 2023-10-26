package com.alibaba.mtl.appmonitor;

import android.app.Application;
import android.text.TextUtils;
import com.alibaba.mtl.appmonitor.a.e;
import com.alibaba.mtl.appmonitor.a.f;
import com.alibaba.mtl.appmonitor.d.j;
import com.alibaba.mtl.appmonitor.model.DimensionSet;
import com.alibaba.mtl.appmonitor.model.DimensionValueSet;
import com.alibaba.mtl.appmonitor.model.Measure;
import com.alibaba.mtl.appmonitor.model.MeasureSet;
import com.alibaba.mtl.appmonitor.model.MeasureValueSet;
import com.alibaba.mtl.appmonitor.model.Metric;
import com.alibaba.mtl.appmonitor.model.MetricRepo;
import com.alibaba.mtl.log.e.i;
import com.alibaba.mtl.log.e.l;
import com.alibaba.mtl.log.sign.BaseRequestAuth;
import com.alibaba.mtl.log.sign.IRequestAuth;
import com.alibaba.mtl.log.sign.SecurityRequestAuth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class AppMonitorDelegate {
    public static final String DEFAULT_VALUE = "defaultValue";
    public static boolean IS_DEBUG = false;
    public static final String MAX_VALUE = "maxValue";
    public static final String MIN_VALUE = "minValue";
    public static final String TAG = "AppMonitorDelegate";
    private static Application b = null;
    static volatile boolean i = false;

    public static synchronized void init(Application application) {
        synchronized (AppMonitorDelegate.class) {
            i.a(TAG, "start init");
            if (!i) {
                b = application;
                com.alibaba.mtl.log.a.a(application.getApplicationContext());
                b.init();
                c.init();
                a.init(application);
                l.b(application.getApplicationContext());
                i = true;
            }
        }
    }

    public static synchronized void destroy() {
        synchronized (AppMonitorDelegate.class) {
            try {
                i.a(TAG, "start destory");
                if (i) {
                    c.d();
                    c.destroy();
                    b.destroy();
                    Application application = b;
                    if (application != null) {
                        l.c(application.getApplicationContext());
                    }
                    i = false;
                }
            } finally {
            }
        }
    }

    public static synchronized void triggerUpload() {
        synchronized (AppMonitorDelegate.class) {
            try {
                i.a(TAG, "triggerUpload");
                if (i && com.alibaba.mtl.log.a.a.f()) {
                    c.d();
                }
            } finally {
            }
        }
    }

    public static void setStatisticsInterval(int i2) {
        f[] values;
        for (f fVar : f.values()) {
            fVar.setStatisticsInterval(i2);
            setStatisticsInterval(fVar, i2);
        }
    }

    public static void setSampling(int i2) {
        f[] values;
        i.a(TAG, "[setSampling]");
        for (f fVar : f.values()) {
            fVar.c(i2);
            j.a().a(fVar, i2);
        }
    }

    public static void enableLog(boolean z) {
        i.a(TAG, "[enableLog]");
        i.d(z);
    }

    public static void register(String str, String str2, MeasureSet measureSet) {
        register(str, str2, measureSet, (DimensionSet) null);
    }

    public static void register(String str, String str2, MeasureSet measureSet, boolean z) {
        register(str, str2, measureSet, null, z);
    }

    public static void register(String str, String str2, MeasureSet measureSet, DimensionSet dimensionSet) {
        register(str, str2, measureSet, dimensionSet, false);
    }

    public static void register(String str, String str2, MeasureSet measureSet, DimensionSet dimensionSet, boolean z) {
        try {
            if (i) {
                if (!com.alibaba.mtl.appmonitor.f.b.d(str) && !com.alibaba.mtl.appmonitor.f.b.d(str2)) {
                    MetricRepo.getRepo().add(new Metric(str, str2, measureSet, dimensionSet, z));
                    return;
                }
                i.a(TAG, "register stat event. module: ", str, " monitorPoint: ", str2);
                if (IS_DEBUG) {
                    throw new com.alibaba.mtl.appmonitor.b.a("register error. module and monitorPoint can't be null");
                }
            }
        } catch (Throwable th) {
            com.alibaba.mtl.appmonitor.b.b.m20a(th);
        }
    }

    public static void updateMeasure(String str, String str2, String str3, double d, double d2, double d3) {
        Metric metric;
        i.a(TAG, "[updateMeasure]");
        try {
            if (!i || com.alibaba.mtl.appmonitor.f.b.d(str) || com.alibaba.mtl.appmonitor.f.b.d(str2) || (metric = MetricRepo.getRepo().getMetric(str, str2)) == null || metric.getMeasureSet() == null) {
                return;
            }
            metric.getMeasureSet().upateMeasure(new Measure(str3, Double.valueOf(d3), Double.valueOf(d), Double.valueOf(d2)));
        } catch (Exception unused) {
        }
    }

    /* loaded from: classes.dex */
    public static class Alarm {
        public static void setStatisticsInterval(int i) {
            f.ALARM.setStatisticsInterval(i);
            AppMonitorDelegate.setStatisticsInterval(f.ALARM, i);
        }

        public static void setSampling(int i) {
            j.a().a(f.ALARM, i);
        }

        @Deprecated
        public static boolean checkSampled(String str, String str2) {
            return j.a(f.ALARM, str, str2);
        }

        public static void commitSuccess(String str, String str2, Map<String, String> map) {
            commitSuccess(str, str2, null, map);
        }

        public static void commitSuccess(String str, String str2, String str3, Map<String, String> map) {
            try {
                if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                    com.alibaba.mtl.log.b.a.A();
                    if (AppMonitorDelegate.i && com.alibaba.mtl.log.a.a.f() && f.ALARM.isOpen() && (AppMonitorDelegate.IS_DEBUG || j.a(str, str2, (Boolean) true, (Map<String, String>) null))) {
                        i.a(AppMonitorDelegate.TAG, "commitSuccess module:", str, " monitorPoint:", str2);
                        com.alibaba.mtl.log.b.a.B();
                        e.a().a(f.ALARM.a(), str, str2, str3, map);
                        return;
                    }
                    i.a("log discard !", "");
                    return;
                }
                i.a(AppMonitorDelegate.TAG, "module & monitorPoint must not null");
            } catch (Throwable th) {
                com.alibaba.mtl.appmonitor.b.b.m20a(th);
            }
        }

        public static void commitFail(String str, String str2, String str3, String str4, Map<String, String> map) {
            commitFail(str, str2, null, str3, str4, map);
        }

        public static void commitFail(String str, String str2, String str3, String str4, String str5, Map<String, String> map) {
            try {
                if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                    com.alibaba.mtl.log.b.a.A();
                    HashMap hashMap = new HashMap();
                    hashMap.put("_status", "0");
                    if (AppMonitorDelegate.i && com.alibaba.mtl.log.a.a.f() && f.ALARM.isOpen() && (AppMonitorDelegate.IS_DEBUG || j.a(str, str2, (Boolean) false, (Map<String, String>) hashMap))) {
                        i.a(AppMonitorDelegate.TAG, "commitFail module:", str, " monitorPoint:", str2, " errorCode:", str4, "errorMsg:", str5);
                        com.alibaba.mtl.log.b.a.B();
                        e.a().a(f.ALARM.a(), str, str2, str3, str4, str5, map);
                        return;
                    }
                    i.a("log discard !", "");
                    return;
                }
                i.a(AppMonitorDelegate.TAG, "module & monitorPoint must not null");
            } catch (Throwable th) {
                com.alibaba.mtl.appmonitor.b.b.m20a(th);
            }
        }
    }

    /* loaded from: classes.dex */
    public static class Counter {
        public static void setStatisticsInterval(int i) {
            f.COUNTER.setStatisticsInterval(i);
            AppMonitorDelegate.setStatisticsInterval(f.COUNTER, i);
        }

        public static void setSampling(int i) {
            j.a().a(f.COUNTER, i);
        }

        @Deprecated
        public static boolean checkSampled(String str, String str2) {
            return j.a(f.COUNTER, str, str2);
        }

        public static void commit(String str, String str2, double d, Map<String, String> map) {
            commit(str, str2, null, d, map);
        }

        public static void commit(String str, String str2, String str3, double d, Map<String, String> map) {
            try {
                if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                    com.alibaba.mtl.log.b.a.y();
                    if (AppMonitorDelegate.i && com.alibaba.mtl.log.a.a.f() && f.COUNTER.isOpen()) {
                        if (AppMonitorDelegate.IS_DEBUG || j.a(f.COUNTER, str, str2)) {
                            i.a(AppMonitorDelegate.TAG, "commitCount module: ", str, " monitorPoint: ", str2, " value: ", Double.valueOf(d));
                            com.alibaba.mtl.log.b.a.z();
                            e.a().a(f.COUNTER.a(), str, str2, str3, d, map);
                            return;
                        }
                        return;
                    }
                    return;
                }
                i.a(AppMonitorDelegate.TAG, "module & monitorPoint must not null");
            } catch (Throwable th) {
                com.alibaba.mtl.appmonitor.b.b.m20a(th);
            }
        }
    }

    /* loaded from: classes.dex */
    public static class OffLineCounter {
        public static void setStatisticsInterval(int i) {
            f.OFFLINE_COUNTER.setStatisticsInterval(i);
            AppMonitorDelegate.setStatisticsInterval(f.OFFLINE_COUNTER, i);
        }

        public static void setSampling(int i) {
            j.a().a(f.OFFLINE_COUNTER, i);
        }

        @Deprecated
        public static boolean checkSampled(String str, String str2) {
            return j.a(f.OFFLINE_COUNTER, str, str2);
        }

        public static void commit(String str, String str2, double d) {
            try {
                if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                    com.alibaba.mtl.log.b.a.w();
                    if (AppMonitorDelegate.i && com.alibaba.mtl.log.a.a.f() && f.OFFLINE_COUNTER.isOpen()) {
                        if (AppMonitorDelegate.IS_DEBUG || j.a(f.OFFLINE_COUNTER, str, str2)) {
                            i.a(AppMonitorDelegate.TAG, "commitOffLineCount module: ", str, " monitorPoint: ", str2, " value: ", Double.valueOf(d));
                            com.alibaba.mtl.log.b.a.x();
                            e.a().a(f.OFFLINE_COUNTER.a(), str, str2, (String) null, d, (Map<String, String>) null);
                            return;
                        }
                        return;
                    }
                    return;
                }
                i.a(AppMonitorDelegate.TAG, "module & monitorPoint must not null");
            } catch (Throwable th) {
                com.alibaba.mtl.appmonitor.b.b.m20a(th);
            }
        }
    }

    /* loaded from: classes.dex */
    public static class Stat {
        public static void setStatisticsInterval(int i) {
            f.STAT.setStatisticsInterval(i);
            AppMonitorDelegate.setStatisticsInterval(f.STAT, i);
        }

        public static void setSampling(int i) {
            j.a().a(f.STAT, i);
        }

        @Deprecated
        public static boolean checkSampled(String str, String str2) {
            return j.a(f.STAT, str, str2);
        }

        public static void begin(String str, String str2, String str3) {
            try {
                if (AppMonitorDelegate.i && com.alibaba.mtl.log.a.a.f() && f.STAT.isOpen()) {
                    if (AppMonitorDelegate.IS_DEBUG || j.a(f.STAT, str, str2)) {
                        i.a(AppMonitorDelegate.TAG, "statEvent begin. module: ", str, " monitorPoint: ", str2, " measureName: ", str3);
                        e.a().a(Integer.valueOf(f.STAT.a()), str, str2, str3);
                    }
                }
            } catch (Throwable th) {
                com.alibaba.mtl.appmonitor.b.b.m20a(th);
            }
        }

        public static void end(String str, String str2, String str3) {
            try {
                if (AppMonitorDelegate.i && com.alibaba.mtl.log.a.a.f() && f.STAT.isOpen()) {
                    if (AppMonitorDelegate.IS_DEBUG || j.a(f.STAT, str, str2)) {
                        i.a(AppMonitorDelegate.TAG, "statEvent end. module: ", str, " monitorPoint: ", str2, " measureName: ", str3);
                        e.a().a(str, str2, str3);
                    }
                }
            } catch (Throwable th) {
                com.alibaba.mtl.appmonitor.b.b.m20a(th);
            }
        }

        public static Transaction createTransaction(String str, String str2) {
            return createTransaction(str, str2, null);
        }

        public static Transaction createTransaction(String str, String str2, DimensionValueSet dimensionValueSet) {
            return new Transaction(Integer.valueOf(f.STAT.a()), str, str2, dimensionValueSet);
        }

        public static void commit(String str, String str2, double d, Map<String, String> map) {
            commit(str, str2, (DimensionValueSet) null, d, map);
        }

        public static void commit(String str, String str2, DimensionValueSet dimensionValueSet, double d, Map<String, String> map) {
            try {
                if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                    com.alibaba.mtl.log.b.a.u();
                    if (AppMonitorDelegate.i && com.alibaba.mtl.log.a.a.f() && f.STAT.isOpen()) {
                        if (AppMonitorDelegate.IS_DEBUG || j.a(f.STAT, str, str2)) {
                            i.a(AppMonitorDelegate.TAG, "statEvent commit. module: ", str, " monitorPoint: ", str2);
                            Metric metric = MetricRepo.getRepo().getMetric(str, str2);
                            com.alibaba.mtl.log.b.a.v();
                            if (metric != null) {
                                List<Measure> measures = metric.getMeasureSet().getMeasures();
                                if (measures.size() == 1) {
                                    commit(str, str2, dimensionValueSet, ((MeasureValueSet) com.alibaba.mtl.appmonitor.c.a.a().a(MeasureValueSet.class, new Object[0])).setValue(measures.get(0).getName(), d), map);
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    return;
                }
                i.a(AppMonitorDelegate.TAG, "module & monitorPoint must not null");
            } catch (Throwable th) {
                com.alibaba.mtl.appmonitor.b.b.m20a(th);
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:21:0x003c, code lost:
            if (com.alibaba.mtl.appmonitor.d.j.a(com.alibaba.mtl.appmonitor.a.f.STAT, r10, r11, r12 != null ? r12.getMap() : null) != false) goto L22;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static void commit(java.lang.String r10, java.lang.String r11, com.alibaba.mtl.appmonitor.model.DimensionValueSet r12, com.alibaba.mtl.appmonitor.model.MeasureValueSet r13, java.util.Map<java.lang.String, java.lang.String> r14) {
            /*
                boolean r1 = android.text.TextUtils.isEmpty(r10)     // Catch: java.lang.Throwable -> L82
                if (r1 != 0) goto L7a
                boolean r1 = android.text.TextUtils.isEmpty(r11)     // Catch: java.lang.Throwable -> L82
                if (r1 == 0) goto Le
                goto L7a
            Le:
                com.alibaba.mtl.log.b.a.u()     // Catch: java.lang.Throwable -> L82
                boolean r1 = com.alibaba.mtl.appmonitor.AppMonitorDelegate.i     // Catch: java.lang.Throwable -> L82
                r2 = 3
                java.lang.String r3 = " monitorPoint: "
                r5 = 2
                r6 = 1
                r7 = 0
                r8 = 4
                if (r1 == 0) goto L67
                boolean r1 = com.alibaba.mtl.log.a.a.f()     // Catch: java.lang.Throwable -> L82
                if (r1 == 0) goto L67
                com.alibaba.mtl.appmonitor.a.f r1 = com.alibaba.mtl.appmonitor.a.f.STAT     // Catch: java.lang.Throwable -> L82
                boolean r1 = r1.isOpen()     // Catch: java.lang.Throwable -> L82
                if (r1 == 0) goto L67
                boolean r1 = com.alibaba.mtl.appmonitor.AppMonitorDelegate.IS_DEBUG     // Catch: java.lang.Throwable -> L82
                if (r1 != 0) goto L3e
                com.alibaba.mtl.appmonitor.a.f r1 = com.alibaba.mtl.appmonitor.a.f.STAT     // Catch: java.lang.Throwable -> L82
                if (r12 == 0) goto L37
                java.util.Map r9 = r12.getMap()     // Catch: java.lang.Throwable -> L82
                goto L38
            L37:
                r9 = 0
            L38:
                boolean r1 = com.alibaba.mtl.appmonitor.d.j.a(r1, r10, r11, r9)     // Catch: java.lang.Throwable -> L82
                if (r1 == 0) goto L67
            L3e:
                java.lang.String r1 = "statEvent commit success"
                java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch: java.lang.Throwable -> L82
                java.lang.String r9 = "statEvent commit. module: "
                r8[r7] = r9     // Catch: java.lang.Throwable -> L82
                r8[r6] = r10     // Catch: java.lang.Throwable -> L82
                r8[r5] = r3     // Catch: java.lang.Throwable -> L82
                r8[r2] = r11     // Catch: java.lang.Throwable -> L82
                com.alibaba.mtl.log.e.i.a(r1, r8)     // Catch: java.lang.Throwable -> L82
                com.alibaba.mtl.log.b.a.v()     // Catch: java.lang.Throwable -> L82
                com.alibaba.mtl.appmonitor.a.e r1 = com.alibaba.mtl.appmonitor.a.e.a()     // Catch: java.lang.Throwable -> L82
                com.alibaba.mtl.appmonitor.a.f r2 = com.alibaba.mtl.appmonitor.a.f.STAT     // Catch: java.lang.Throwable -> L82
                int r2 = r2.a()     // Catch: java.lang.Throwable -> L82
                r3 = r10
                r4 = r11
                r5 = r13
                r6 = r12
                r7 = r14
                r1.a(r2, r3, r4, r5, r6, r7)     // Catch: java.lang.Throwable -> L82
                goto L86
            L67:
                java.lang.String r1 = "statEvent commit failed,log discard"
                java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch: java.lang.Throwable -> L82
                java.lang.String r9 = " ,. module: "
                r8[r7] = r9     // Catch: java.lang.Throwable -> L82
                r8[r6] = r10     // Catch: java.lang.Throwable -> L82
                r8[r5] = r3     // Catch: java.lang.Throwable -> L82
                r8[r2] = r11     // Catch: java.lang.Throwable -> L82
                com.alibaba.mtl.log.e.i.a(r1, r8)     // Catch: java.lang.Throwable -> L82
                goto L86
            L7a:
                java.lang.String r0 = "AppMonitorDelegate"
                java.lang.String r1 = "module & monitorPoint must not null"
                com.alibaba.mtl.log.e.i.a(r0, r1)     // Catch: java.lang.Throwable -> L82
                return
            L82:
                r0 = move-exception
                com.alibaba.mtl.appmonitor.b.b.m20a(r0)
            L86:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.alibaba.mtl.appmonitor.AppMonitorDelegate.Stat.commit(java.lang.String, java.lang.String, com.alibaba.mtl.appmonitor.model.DimensionValueSet, com.alibaba.mtl.appmonitor.model.MeasureValueSet, java.util.Map):void");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setStatisticsInterval(f fVar, int i2) {
        try {
            if (i && fVar != null) {
                c.a(fVar.a(), i2);
                if (i2 > 0) {
                    fVar.b(true);
                } else {
                    fVar.b(false);
                }
            }
        } catch (Throwable th) {
            com.alibaba.mtl.appmonitor.b.b.m20a(th);
        }
    }

    public static void setRequestAuthInfo(boolean z, String str, String str2, String str3) {
        IRequestAuth baseRequestAuth;
        if (z) {
            baseRequestAuth = new SecurityRequestAuth(str, str3);
        } else {
            baseRequestAuth = new BaseRequestAuth(str, str2, "1".equalsIgnoreCase(str3));
        }
        com.alibaba.mtl.log.a.a(baseRequestAuth);
        com.alibaba.mtl.log.a.a.a(b);
    }

    public static void setChannel(String str) {
        com.alibaba.mtl.log.a.setChannel(str);
    }

    public static void turnOnRealTimeDebug(Map<String, String> map) {
        com.alibaba.mtl.log.a.a.turnOnRealTimeDebug(map);
    }

    public static void turnOffRealTimeDebug() {
        i.a(TAG, "[turnOffRealTimeDebug]");
    }
}
