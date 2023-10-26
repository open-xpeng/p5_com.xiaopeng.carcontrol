package com.alibaba.mtl.appmonitor.c;

import com.alibaba.mtl.appmonitor.c.b;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;

/* compiled from: ReuseItemPool.java */
/* loaded from: classes.dex */
public class c<T extends b> {
    private static AtomicLong c = new AtomicLong(0);
    private static AtomicLong d = new AtomicLong(0);
    private final int m = 20;
    private Integer b = null;

    /* renamed from: a  reason: collision with other field name */
    private AtomicLong f46a = new AtomicLong(0);

    /* renamed from: b  reason: collision with other field name */
    private AtomicLong f48b = new AtomicLong(0);
    private ConcurrentLinkedQueue<T> a = new ConcurrentLinkedQueue<>();

    /* renamed from: b  reason: collision with other field name */
    private Set<Integer> f47b = new HashSet();

    public T a() {
        c.getAndIncrement();
        this.f46a.getAndIncrement();
        T poll = this.a.poll();
        if (poll != null) {
            this.f47b.remove(Integer.valueOf(System.identityHashCode(poll)));
            this.f48b.getAndIncrement();
            d.getAndIncrement();
        }
        return poll;
    }

    public void a(T t) {
        t.clean();
        if (this.a.size() < 20) {
            synchronized (this.f47b) {
                int identityHashCode = System.identityHashCode(t);
                if (!this.f47b.contains(Integer.valueOf(identityHashCode))) {
                    this.f47b.add(Integer.valueOf(identityHashCode));
                    this.a.offer(t);
                }
            }
        }
    }
}
