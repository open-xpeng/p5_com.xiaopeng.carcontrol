package com.alibaba.mtl.log.c;

import com.alibaba.mtl.log.e.i;
import com.alibaba.mtl.log.e.r;
import com.xiaopeng.lib.bughunter.anr.UILooperObserver;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: LogStoreMgr.java */
/* loaded from: classes.dex */
public class c {
    private static c a;
    private List<com.alibaba.mtl.log.model.a> l = new CopyOnWriteArrayList();
    private Runnable b = new Runnable() { // from class: com.alibaba.mtl.log.c.c.1
        @Override // java.lang.Runnable
        public void run() {
            c.this.F();
        }
    };

    /* renamed from: a  reason: collision with other field name */
    private com.alibaba.mtl.log.c.a f58a = new b(com.alibaba.mtl.log.a.getContext());

    private c() {
        com.alibaba.mtl.log.d.a.a().start();
        r.a().b(new a());
    }

    public static synchronized c a() {
        c cVar;
        synchronized (c.class) {
            if (a == null) {
                a = new c();
            }
            cVar = a;
        }
        return cVar;
    }

    public void a(com.alibaba.mtl.log.model.a aVar) {
        i.a("LogStoreMgr", "[add] :", aVar.X);
        com.alibaba.mtl.log.b.a.m(aVar.T);
        this.l.add(aVar);
        if (this.l.size() >= 100) {
            r.a().f(1);
            r.a().a(1, this.b, 0L);
        } else if (r.a().b(1)) {
        } else {
            r.a().a(1, this.b, UILooperObserver.ANR_TRIGGER_TIME);
        }
    }

    public int a(List<com.alibaba.mtl.log.model.a> list) {
        i.a("LogStoreMgr", list);
        return this.f58a.a(list);
    }

    public List<com.alibaba.mtl.log.model.a> a(String str, int i) {
        List<com.alibaba.mtl.log.model.a> a2 = this.f58a.a(str, i);
        i.a("LogStoreMgr", "[get]", a2);
        return a2;
    }

    public synchronized void F() {
        i.a("LogStoreMgr", "[store]");
        ArrayList arrayList = null;
        try {
            synchronized (this.l) {
                if (this.l.size() > 0) {
                    arrayList = new ArrayList(this.l);
                    this.l.clear();
                }
            }
            if (arrayList != null && arrayList.size() > 0) {
                this.f58a.mo25a((List<com.alibaba.mtl.log.model.a>) arrayList);
            }
        } catch (Throwable unused) {
        }
    }

    public void clear() {
        i.a("LogStoreMgr", "[clear]");
        this.f58a.clear();
        this.l.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void G() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(5, -3);
        this.f58a.c("time", String.valueOf(calendar.getTimeInMillis()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void H() {
        this.f58a.e(1000);
    }

    /* compiled from: LogStoreMgr.java */
    /* loaded from: classes.dex */
    class a implements Runnable {
        a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            c.this.G();
            if (c.this.f58a.g() > 9000) {
                c.this.H();
            }
        }
    }
}
