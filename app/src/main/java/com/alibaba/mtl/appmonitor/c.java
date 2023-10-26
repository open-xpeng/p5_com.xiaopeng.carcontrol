package com.alibaba.mtl.appmonitor;

import com.alibaba.mtl.appmonitor.a.e;
import com.alibaba.mtl.appmonitor.a.f;
import com.alibaba.mtl.log.e.i;
import com.alibaba.mtl.log.e.r;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: UploadTask.java */
/* loaded from: classes.dex */
public class c implements Runnable {
    private static Map<Integer, c> f = null;
    private static boolean j = false;
    private int d;
    private int e;
    private long startTime = System.currentTimeMillis();

    private static int a(int i) {
        if (i != 65133) {
            switch (i) {
                case 65501:
                    return 6;
                case 65502:
                    return 9;
                case 65503:
                    return 10;
                default:
                    return 0;
            }
        }
        return 11;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void init() {
        f[] values;
        if (j) {
            return;
        }
        i.a("CommitTask", "init StatisticsAlarmEvent");
        f = new ConcurrentHashMap();
        for (f fVar : f.values()) {
            if (fVar.isOpen()) {
                int a = fVar.a();
                c cVar = new c(a, fVar.c() * 1000);
                f.put(Integer.valueOf(a), cVar);
                r.a().a(a(a), cVar, cVar.d);
            }
        }
        j = true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void destroy() {
        for (f fVar : f.values()) {
            r.a().f(a(fVar.a()));
        }
        j = false;
        f = null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(int i, int i2) {
        i.a("CommitTask", "[setStatisticsInterval] eventId" + i + " statisticsInterval:" + i2);
        synchronized (f) {
            c cVar = f.get(Integer.valueOf(i));
            if (cVar == null) {
                if (i2 > 0) {
                    c cVar2 = new c(i, i2 * 1000);
                    f.put(Integer.valueOf(i), cVar2);
                    i.a("CommitTask", "post next eventId" + i + ": uploadTask.interval " + cVar2.d);
                    r.a().a(a(i), cVar2, cVar2.d);
                }
            } else if (i2 > 0) {
                int i3 = i2 * 1000;
                if (cVar.d != i3) {
                    r.a().f(a(i));
                    cVar.d = i3;
                    long currentTimeMillis = System.currentTimeMillis();
                    long j2 = cVar.d - (currentTimeMillis - cVar.startTime);
                    if (j2 < 0) {
                        j2 = 0;
                    }
                    i.a("CommitTask", cVar + "post next eventId" + i + " next:" + j2 + "  uploadTask.interval: " + cVar.d);
                    r.a().a(a(i), cVar, j2);
                    cVar.startTime = currentTimeMillis;
                }
            } else {
                i.a("CommitTask", "uploadTasks.size:" + f.size());
                f.remove(Integer.valueOf(i));
                i.a("CommitTask", "uploadTasks.size:" + f.size());
            }
        }
    }

    private c(int i, int i2) {
        this.d = 180000;
        this.e = i;
        this.d = i2;
    }

    @Override // java.lang.Runnable
    public void run() {
        i.a("CommitTask", "check&commit event:", Integer.valueOf(this.e));
        e.a().m16a(this.e);
        if (f.containsValue(this)) {
            this.startTime = System.currentTimeMillis();
            i.a("CommitTask", "next:" + this.e);
            r.a().a(a(this.e), this, this.d);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void d() {
        for (f fVar : f.values()) {
            e.a().m16a(fVar.a());
        }
    }
}
