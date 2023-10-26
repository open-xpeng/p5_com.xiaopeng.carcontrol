package com.alibaba.sdk.android.httpdns;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class c {
    private static final ExecutorService a;

    /* renamed from: a  reason: collision with other field name */
    private static final ThreadFactory f82a;

    /* renamed from: a  reason: collision with other field name */
    private static final TimeUnit f83a;

    static {
        TimeUnit timeUnit = TimeUnit.SECONDS;
        f83a = timeUnit;
        ThreadFactory threadFactory = new ThreadFactory() { // from class: com.alibaba.sdk.android.httpdns.c.1
            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable);
                thread.setName("httpdns worker");
                thread.setDaemon(false);
                thread.setUncaughtExceptionHandler(new j());
                return thread;
            }
        };
        f82a = threadFactory;
        a = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 1L, timeUnit, new SynchronousQueue(), threadFactory);
    }

    public static ExecutorService a() {
        return a;
    }
}
