package com.xiaopeng.carcontrol.util;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Process;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/* loaded from: classes2.dex */
public final class ThreadUtils {
    private static final String TAG = "ThreadUtils";
    public static final int THREAD_BACKGROUND = 0;
    public static final int THREAD_NORMAL = 2;
    public static final int THREAD_UI = 1;
    private static Handler sBackgroundHandler;
    private static HandlerThread sBackgroundThread;
    private static Handler sMainThreadHandler;
    private static Handler sNormalHandler;
    private static HandlerThread sNormalThread;
    private static final ExecutorService sThreadPool = Executors.newFixedThreadPool(8);
    private static final HashMap<Object, RunnableMap> sRunnableCache = new HashMap<>();

    public static void execute(final Runnable runnable, final Runnable callback, final int priority) {
        ExecutorService executorService = sThreadPool;
        if (executorService.isShutdown()) {
            return;
        }
        if (callback == null && priority == 5) {
            executorService.execute(runnable);
            return;
        }
        try {
            executorService.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.util.-$$Lambda$ThreadUtils$vB8lqCYJHdyZocF7URQrtzrRm2g
                @Override // java.lang.Runnable
                public final void run() {
                    ThreadUtils.lambda$execute$0(priority, runnable, callback);
                }
            });
        } catch (Exception e) {
            LogUtils.e(TAG, (String) null, e);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$execute$0(final int priority, final Runnable runnable, final Runnable callback) {
        Process.setThreadPriority(priority);
        runnable.run();
        if (callback != null) {
            new Handler(Looper.myLooper()).post(callback);
        }
    }

    public static boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    public static void runOnMainThread(Runnable runner) {
        runOnMainThreadDelay(runner, 0L);
    }

    public static void runOnMainThreadDelay(Runnable runner, long delayMs) {
        if (sMainThreadHandler == null) {
            createMainThread();
        }
        sMainThreadHandler.postDelayed(runner, delayMs);
    }

    public static void execute(Runnable runnable) {
        if (runnable != null) {
            execute(runnable, null, 5);
        }
    }

    public static Future<?> submit(final Runnable runnable) {
        try {
            ExecutorService executorService = sThreadPool;
            if (!executorService.isShutdown()) {
                return executorService.submit(runnable);
            }
        } catch (Exception e) {
            LogUtils.e(TAG, (String) null, e);
        }
        return null;
    }

    public static void postDelayed(int threadType, Runnable task) {
        postDelayed(threadType, task, 0L);
    }

    public static void postDelayed(int threadType, Runnable task, long delayMillis) {
        doPost(threadType, task, delayMillis);
    }

    public static void postDelayed(Runnable task, long delayMillis) {
        doPost(5, task, delayMillis);
    }

    public static Handler getHandler(int threadType) {
        if (threadType == 0) {
            if (sBackgroundHandler == null) {
                createBackgroundThread();
            }
            return sBackgroundHandler;
        } else if (threadType == 2) {
            if (sNormalHandler == null) {
                createNormalThread();
            }
            return sNormalHandler;
        } else {
            if (sMainThreadHandler == null) {
                createMainThread();
            }
            return sMainThreadHandler;
        }
    }

    private static void doPost(int threadType, final Runnable task, long delayMillis) {
        Handler handler;
        if (sMainThreadHandler == null) {
            createMainThread();
        }
        if (task != null) {
            if (threadType == 0) {
                if (sBackgroundHandler == null) {
                    createBackgroundThread();
                }
                handler = sBackgroundHandler;
            } else if (threadType == 1) {
                if (sMainThreadHandler == null) {
                    createMainThread();
                }
                handler = sMainThreadHandler;
            } else if (threadType == 2) {
                if (sNormalHandler == null) {
                    createNormalThread();
                }
                handler = sNormalHandler;
            } else {
                handler = sMainThreadHandler;
            }
            Runnable runnable = new Runnable() { // from class: com.xiaopeng.carcontrol.util.-$$Lambda$ThreadUtils$iXNRQ_8rOxQMlxB4LVpirJG7UXQ
                @Override // java.lang.Runnable
                public final void run() {
                    ThreadUtils.lambda$doPost$1(task);
                }
            };
            HashMap<Object, RunnableMap> hashMap = sRunnableCache;
            synchronized (hashMap) {
                hashMap.put(task, new RunnableMap(runnable, Integer.valueOf(threadType)));
            }
            handler.postDelayed(runnable, delayMillis);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$doPost$1(final Runnable task) {
        try {
            task.run();
        } catch (Throwable th) {
            LogUtils.e(TAG, (String) null, th);
        }
        try {
            sRunnableCache.remove(task);
        } catch (Throwable th2) {
            LogUtils.e(TAG, (String) null, th2);
        }
    }

    public static void removeRunnable(Runnable task) {
        HashMap<Object, RunnableMap> hashMap;
        RunnableMap runnableMap;
        Runnable runnable;
        Handler handler;
        if (task == null || (runnableMap = (hashMap = sRunnableCache).get(task)) == null || (runnable = runnableMap.getRunnable()) == null) {
            return;
        }
        int type = runnableMap.getType();
        if (type == 0) {
            Handler handler2 = sBackgroundHandler;
            if (handler2 != null) {
                handler2.removeCallbacks(runnable);
            }
        } else if (type == 1) {
            Handler handler3 = sMainThreadHandler;
            if (handler3 != null) {
                handler3.removeCallbacks(runnable);
            }
        } else if (type == 2 && (handler = sNormalHandler) != null) {
            handler.removeCallbacks(runnable);
        }
        try {
            hashMap.remove(task);
        } catch (Throwable th) {
            LogUtils.e(TAG, (String) null, th);
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
                HandlerThread handlerThread = new HandlerThread("BackgroundHandler", 10);
                sBackgroundThread = handlerThread;
                handlerThread.start();
            }
            if (sBackgroundHandler == null) {
                sBackgroundHandler = new Handler(sBackgroundThread.getLooper());
            }
        }
    }

    private static synchronized void createNormalThread() {
        synchronized (ThreadUtils.class) {
            if (sNormalThread == null) {
                HandlerThread handlerThread = new HandlerThread("NormalHandler", 0);
                sNormalThread = handlerThread;
                handlerThread.start();
            }
            if (sNormalHandler == null) {
                sNormalHandler = new Handler(sNormalThread.getLooper());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class RunnableMap {
        private final Runnable mRunnable;
        private final Integer mType;

        RunnableMap(Runnable runnable, Integer type) {
            this.mRunnable = runnable;
            this.mType = type;
        }

        Runnable getRunnable() {
            return this.mRunnable;
        }

        public int getType() {
            return this.mType.intValue();
        }
    }
}
