package com.xiaopeng.lib.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Process;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes2.dex */
public class ThreadUtils {
    private static final String TAG = "ThreadUtils";
    public static final int THREAD_BACKGROUND = 0;
    public static final int THREAD_NORMAL = 2;
    private static final int THREAD_POOL_SIZE = 4;
    public static final int THREAD_UI = 1;
    private static Handler sBackgroundHandler;
    private static volatile HandlerThread sBackgroundThread;
    private static Handler sMainThreadHandler;
    private static Handler sNormalHandler;
    private static volatile HandlerThread sNormalThread;
    private static final ExecutorService sThreadPool = Executors.newFixedThreadPool(4, new ThreadFactoryBuilder().setNameFormat("order-%d").setDaemon(false).build());
    private static ConcurrentHashMap<Object, RunnableMap> sRunnableCache = new ConcurrentHashMap<>();

    private ThreadUtils() {
    }

    public static Looper getLooper(int i) {
        if (i == 0) {
            createBackgroundThread();
            return sBackgroundThread.getLooper();
        } else if (i == 1) {
            createMainThread();
            return sMainThreadHandler.getLooper();
        } else if (i == 2) {
            createNormalThread();
            return sNormalHandler.getLooper();
        } else {
            throw new IllegalArgumentException("invalid threadType:" + i);
        }
    }

    public static void runOnMainThread(Runnable runnable) {
        post(1, runnable);
    }

    public static void runOnMainThreadDelay(Runnable runnable, long j) {
        postDelayed(1, runnable, j);
    }

    public static void execute(Runnable runnable) {
        execute(runnable, null, 10);
    }

    public static void execute(Runnable runnable, Runnable runnable2) {
        execute(runnable, runnable2, 10);
    }

    public static void execute(final Runnable runnable, final Runnable runnable2, final int i) {
        try {
            ExecutorService executorService = sThreadPool;
            if (executorService.isShutdown()) {
                return;
            }
            final Handler handler = runnable2 != null ? new Handler(Looper.myLooper()) : null;
            executorService.execute(new Runnable() { // from class: com.xiaopeng.lib.utils.ThreadUtils.1
                @Override // java.lang.Runnable
                public void run() {
                    Runnable runnable3;
                    Process.setThreadPriority(i);
                    runnable.run();
                    Handler handler2 = handler;
                    if (handler2 == null || (runnable3 = runnable2) == null) {
                        return;
                    }
                    handler2.post(runnable3);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void post(int i, Runnable runnable, Runnable runnable2, Runnable runnable3, boolean z) {
        doPost(i, runnable, runnable2, runnable3, z, 0L);
    }

    public static void post(int i, Runnable runnable, Runnable runnable2, boolean z) {
        doPost(i, null, runnable, runnable2, z, 0L);
    }

    public static void post(int i, Runnable runnable) {
        doPost(i, null, runnable, null, false, 0L);
    }

    public static void postDelayed(int i, Runnable runnable, long j) {
        doPost(i, null, runnable, null, false, j);
    }

    public static void postMainThread(Runnable runnable) {
        doPost(1, null, runnable, null, false, 0L);
    }

    public static void postMainThread(Runnable runnable, long j) {
        doPost(1, null, runnable, null, false, j);
    }

    public static void postNormal(Runnable runnable) {
        doPost(2, null, runnable, null, false, 0L);
    }

    public static void postNormal(Runnable runnable, long j) {
        doPost(2, null, runnable, null, false, j);
    }

    public static void postBackground(Runnable runnable) {
        doPost(0, null, runnable, null, false, 0L);
    }

    public static void postBackground(Runnable runnable, long j) {
        doPost(0, null, runnable, null, false, j);
    }

    private static void doPost(int i, final Runnable runnable, final Runnable runnable2, final Runnable runnable3, final boolean z, long j) {
        Handler handler;
        if (runnable2 == null) {
            return;
        }
        if (sMainThreadHandler == null) {
            createMainThread();
        }
        if (i == 0) {
            if (sBackgroundThread == null) {
                createBackgroundThread();
            }
            handler = sBackgroundHandler;
        } else if (i == 1) {
            handler = sMainThreadHandler;
        } else if (i == 2) {
            if (sNormalThread == null) {
                createNormalThread();
            }
            handler = sNormalHandler;
        } else {
            handler = sMainThreadHandler;
        }
        if (handler == null) {
            handler = sMainThreadHandler;
        }
        Looper looper = null;
        if (!z && (looper = Looper.myLooper()) == null) {
            looper = sMainThreadHandler.getLooper();
        }
        final Looper looper2 = looper;
        final Runnable runnable4 = new Runnable() { // from class: com.xiaopeng.lib.utils.ThreadUtils.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    runnable2.run();
                } catch (Throwable th) {
                    th.printStackTrace();
                }
                if (runnable3 != null) {
                    if (z || looper2 == ThreadUtils.sMainThreadHandler.getLooper()) {
                        ThreadUtils.sMainThreadHandler.post(runnable3);
                    } else {
                        new Handler(looper2).post(runnable3);
                    }
                }
                LogUtils.d(ThreadUtils.TAG, "remove task: " + runnable2);
                ThreadUtils.sRunnableCache.remove(runnable2);
            }
        };
        final Handler handler2 = handler;
        Runnable runnable5 = new Runnable() { // from class: com.xiaopeng.lib.utils.ThreadUtils.3
            @Override // java.lang.Runnable
            public void run() {
                if (runnable != null) {
                    if (z || looper2 == ThreadUtils.sMainThreadHandler.getLooper()) {
                        ThreadUtils.sMainThreadHandler.post(new Runnable() { // from class: com.xiaopeng.lib.utils.ThreadUtils.3.1
                            @Override // java.lang.Runnable
                            public void run() {
                                runnable.run();
                                handler2.post(runnable4);
                            }
                        });
                        return;
                    } else {
                        new Handler(looper2).post(new Runnable() { // from class: com.xiaopeng.lib.utils.ThreadUtils.3.2
                            @Override // java.lang.Runnable
                            public void run() {
                                runnable.run();
                                handler2.post(runnable4);
                            }
                        });
                        return;
                    }
                }
                runnable4.run();
            }
        };
        if (runnable == null) {
            sRunnableCache.put(runnable2, new RunnableMap(runnable5, Integer.valueOf(i)));
        } else {
            sRunnableCache.put(runnable2, new RunnableMap(runnable4, Integer.valueOf(i)));
        }
        LogUtils.d(TAG, "put task: " + runnable2);
        handler.postDelayed(runnable5, j);
    }

    public static void removeRunnable(Runnable runnable) {
        RunnableMap remove;
        Runnable runnable2;
        Handler handler;
        if (runnable == null || (remove = sRunnableCache.remove(runnable)) == null || (runnable2 = remove.getRunnable()) == null) {
            return;
        }
        int type = remove.getType();
        if (type == 0) {
            Handler handler2 = sBackgroundHandler;
            if (handler2 != null) {
                handler2.removeCallbacks(runnable2);
            }
        } else if (type != 1) {
            if (type == 2 && (handler = sNormalHandler) != null) {
                handler.removeCallbacks(runnable2);
            }
        } else {
            Handler handler3 = sMainThreadHandler;
            if (handler3 != null) {
                handler3.removeCallbacks(runnable2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class RunnableMap {
        private Runnable mRunnable;
        private Integer mType;

        public RunnableMap(Runnable runnable, Integer num) {
            this.mRunnable = runnable;
            this.mType = num;
        }

        public Runnable getRunnable() {
            return this.mRunnable;
        }

        public int getType() {
            return this.mType.intValue();
        }
    }

    private static synchronized void createMainThread() {
        synchronized (ThreadUtils.class) {
            if (sMainThreadHandler == null) {
                sMainThreadHandler = new Handler(Looper.getMainLooper());
            }
        }
    }

    private static synchronized void createBackgroundThread() {
        synchronized (ThreadUtils.class) {
            if (sBackgroundThread == null) {
                sBackgroundThread = new HandlerThread("BackgroundHandler", 10);
                sBackgroundThread.start();
                sBackgroundHandler = new Handler(sBackgroundThread.getLooper());
            }
        }
    }

    private static synchronized void createNormalThread() {
        synchronized (ThreadUtils.class) {
            if (sNormalThread == null) {
                sNormalThread = new HandlerThread("NormalHandler", 0);
                sNormalThread.start();
                sNormalHandler = new Handler(sNormalThread.getLooper());
            }
        }
    }

    public static synchronized void destroy() {
        synchronized (ThreadUtils.class) {
            if (sBackgroundThread != null) {
                sBackgroundThread.quit();
                sBackgroundThread.interrupt();
                sBackgroundThread = null;
            }
            if (sNormalThread != null) {
                sNormalThread.quit();
                sNormalThread.interrupt();
                sNormalThread = null;
            }
        }
    }

    public static boolean hasCallbacksOnBackground(Runnable runnable) {
        Handler handler = sBackgroundHandler;
        return handler != null && handler.hasCallbacks(runnable);
    }

    public static boolean hasMessagesOnBackground(int i, Object obj) {
        Handler handler = sBackgroundHandler;
        return handler != null && handler.hasMessages(i, obj);
    }

    public static boolean hasMessagesOnBackground(int i) {
        Handler handler = sBackgroundHandler;
        return handler != null && handler.hasMessages(i);
    }

    public static boolean hasCallbacksOnMain(Runnable runnable) {
        Handler handler = sMainThreadHandler;
        return handler != null && handler.hasCallbacks(runnable);
    }

    public static boolean hasMessagesOnMain(int i, Object obj) {
        Handler handler = sMainThreadHandler;
        return handler != null && handler.hasMessages(i, obj);
    }

    public static boolean hasMessagesOnMain(int i) {
        Handler handler = sMainThreadHandler;
        return handler != null && handler.hasMessages(i);
    }

    public static boolean hasCallbacksOnNormal(Runnable runnable) {
        Handler handler = sNormalHandler;
        return handler != null && handler.hasCallbacks(runnable);
    }

    public static boolean hasMessagesOnNormal(int i, Object obj) {
        Handler handler = sNormalHandler;
        return handler != null && handler.hasMessages(i, obj);
    }

    public static boolean hasMessagesOnNormal(int i) {
        Handler handler = sNormalHandler;
        return handler != null && handler.hasMessages(i);
    }

    /* loaded from: classes2.dex */
    private static class ThreadFactoryBuilder {
        private static final boolean DAEMON_VALUE = false;
        private static final String NAME = "newFixedThreadPool";
        private boolean mDaemon;
        private String mName;
        private String mNameFormat;
        private ThreadFactory mThreadFactory;

        private ThreadFactoryBuilder() {
            this.mName = NAME;
            this.mDaemon = false;
        }

        public ThreadFactory getThreadFactory() {
            return this.mThreadFactory;
        }

        public ThreadFactoryBuilder setThreadFactory(ThreadFactory threadFactory) {
            this.mThreadFactory = threadFactory;
            return this;
        }

        public String getNameFormat() {
            return this.mNameFormat;
        }

        ThreadFactoryBuilder setNameFormat(String str) {
            this.mNameFormat = str;
            return this;
        }

        public String getName() {
            return this.mName;
        }

        public boolean ismDaemon() {
            return this.mDaemon;
        }

        ThreadFactoryBuilder setDaemon(boolean z) {
            this.mDaemon = z;
            return this;
        }

        ThreadFactory build() {
            return new ThreadFactory() { // from class: com.xiaopeng.lib.utils.ThreadUtils.ThreadFactoryBuilder.1
                @Override // java.util.concurrent.ThreadFactory
                public Thread newThread(Runnable runnable) {
                    Thread newThread = (ThreadFactoryBuilder.this.mThreadFactory != null ? ThreadFactoryBuilder.this.mThreadFactory : Executors.defaultThreadFactory()).newThread(runnable);
                    AtomicLong atomicLong = ThreadFactoryBuilder.this.mNameFormat != null ? new AtomicLong(0L) : null;
                    if (ThreadFactoryBuilder.this.mNameFormat != null) {
                        newThread.setName(String.format(Locale.ROOT, ThreadFactoryBuilder.this.mNameFormat, Long.valueOf(atomicLong.getAndIncrement())));
                    }
                    newThread.setDaemon(ThreadFactoryBuilder.this.mDaemon);
                    return newThread;
                }
            };
        }
    }
}
