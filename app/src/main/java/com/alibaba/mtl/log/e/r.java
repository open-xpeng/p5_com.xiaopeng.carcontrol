package com.alibaba.mtl.log.e;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import com.alibaba.mtl.appmonitor.AppMonitor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: TaskExecutor.java */
/* loaded from: classes.dex */
public class r {
    private static int F = 1;
    private static int G = 2;
    private static int H = 10;
    private static int I = 60;
    public static r a;

    /* renamed from: a  reason: collision with other field name */
    private static ThreadPoolExecutor f65a;
    private static final AtomicInteger f = new AtomicInteger();
    private HandlerThread b;
    private Handler mHandler;

    static /* synthetic */ ThreadPoolExecutor b() {
        return m31a();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: TaskExecutor.java */
    /* loaded from: classes.dex */
    public static class a implements ThreadFactory {
        private int priority;

        public a(int i) {
            this.priority = i;
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable, "AppMonitor:" + r.f.getAndIncrement());
            thread.setPriority(this.priority);
            return thread;
        }
    }

    private static ThreadPoolExecutor a(int i, int i2, int i3, int i4, int i5) {
        LinkedBlockingQueue linkedBlockingQueue;
        if (i5 > 0) {
            linkedBlockingQueue = new LinkedBlockingQueue(i5);
        } else {
            linkedBlockingQueue = new LinkedBlockingQueue();
        }
        a aVar = new a(i);
        return new ThreadPoolExecutor(i2, i3, i4, TimeUnit.SECONDS, linkedBlockingQueue, aVar, new ThreadPoolExecutor.DiscardOldestPolicy());
    }

    /* renamed from: a  reason: collision with other method in class */
    private static synchronized ThreadPoolExecutor m31a() {
        ThreadPoolExecutor threadPoolExecutor;
        synchronized (r.class) {
            if (f65a == null) {
                f65a = a(F, G, H, I, 500);
            }
            threadPoolExecutor = f65a;
        }
        return threadPoolExecutor;
    }

    public static synchronized r a() {
        r rVar;
        synchronized (r.class) {
            if (a == null) {
                a = new r();
            }
            rVar = a;
        }
        return rVar;
    }

    private r() {
        HandlerThread handlerThread = new HandlerThread(AppMonitor.TAG);
        this.b = handlerThread;
        handlerThread.start();
        this.mHandler = new Handler(this.b.getLooper()) { // from class: com.alibaba.mtl.log.e.r.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                super.handleMessage(message);
                try {
                    if (message.obj == null || !(message.obj instanceof Runnable)) {
                        return;
                    }
                    r.b().submit((Runnable) message.obj);
                } catch (Throwable unused) {
                }
            }
        };
    }

    public final void a(int i, Runnable runnable, long j) {
        try {
            Message obtain = Message.obtain(this.mHandler, i);
            obtain.obj = runnable;
            this.mHandler.sendMessageDelayed(obtain, j);
        } catch (Exception e) {
            com.alibaba.mtl.appmonitor.b.b.m20a((Throwable) e);
        }
    }

    public final void f(int i) {
        this.mHandler.removeMessages(i);
    }

    public final boolean b(int i) {
        return this.mHandler.hasMessages(i);
    }

    public void b(Runnable runnable) {
        try {
            m31a().submit(runnable);
        } catch (Throwable unused) {
        }
    }
}
