package com.alibaba.mtl.appmonitor;

import com.alibaba.mtl.appmonitor.a.e;
import com.alibaba.mtl.log.e.i;
import com.alibaba.mtl.log.e.r;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: CleanTask.java */
/* loaded from: classes.dex */
public class b implements Runnable {
    private static long a = 300000;

    /* renamed from: a  reason: collision with other field name */
    private static b f45a = null;
    private static boolean j = false;

    private b() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void init() {
        if (j) {
            return;
        }
        i.a("CleanTask", "init TimeoutEventManager");
        f45a = new b();
        r.a().a(5, f45a, a);
        j = true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void destroy() {
        r.a().f(5);
        j = false;
        f45a = null;
    }

    @Override // java.lang.Runnable
    public void run() {
        i.a("CleanTask", "clean TimeoutEvent");
        e.a().g();
        r.a().a(5, f45a, a);
    }
}
