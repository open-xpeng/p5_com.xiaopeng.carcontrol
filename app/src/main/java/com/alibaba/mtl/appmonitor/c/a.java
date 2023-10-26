package com.alibaba.mtl.appmonitor.c;

import java.util.HashMap;
import java.util.Map;

/* compiled from: BalancedPool.java */
/* loaded from: classes.dex */
public class a {
    private static a a = new a();
    private Map<Class<? extends b>, c<? extends b>> o = new HashMap();

    public static a a() {
        return a;
    }

    private a() {
    }

    public <T extends b> T a(Class<T> cls, Object... objArr) {
        T a2 = a(cls).a();
        if (a2 == null) {
            try {
                a2 = cls.newInstance();
            } catch (Exception e) {
                com.alibaba.mtl.appmonitor.b.b.m20a((Throwable) e);
            }
        }
        if (a2 != null) {
            a2.fill(objArr);
        }
        return a2;
    }

    public <T extends b> void a(T t) {
        if (t == null || (t instanceof e) || (t instanceof d)) {
            return;
        }
        a(t.getClass()).a(t);
    }

    private synchronized <T extends b> c<T> a(Class<T> cls) {
        c<T> cVar;
        cVar = (c<T>) this.o.get(cls);
        if (cVar == null) {
            cVar = new c<>();
            this.o.put(cls, cVar);
        }
        return cVar;
    }
}
