package com.alibaba.sdk.android.oss.common;

import com.xiaopeng.lib.bughunter.anr.UILooperObserver;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class LogThreadPoolManager {
    private static final int PERIOD_TASK_QOS = 1000;
    private static final int SIZE_CACHE_QUEUE = 200;
    private static final int SIZE_CORE_POOL = 1;
    private static final int SIZE_MAX_POOL = 1;
    private static final int SIZE_WORK_QUEUE = 500;
    private static final int TIME_KEEP_ALIVE = 5000;
    private static LogThreadPoolManager sThreadPoolManager = new LogThreadPoolManager();
    private final Runnable mAccessBufferThread;
    private final RejectedExecutionHandler mHandler;
    protected final ScheduledFuture<?> mTaskHandler;
    private final Queue<Runnable> mTaskQueue = new LinkedList();
    private final ThreadPoolExecutor mThreadPool;
    private final ScheduledExecutorService scheduler;

    private LogThreadPoolManager() {
        RejectedExecutionHandler rejectedExecutionHandler = new RejectedExecutionHandler() { // from class: com.alibaba.sdk.android.oss.common.LogThreadPoolManager.1
            @Override // java.util.concurrent.RejectedExecutionHandler
            public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
                if (LogThreadPoolManager.this.mTaskQueue.size() >= 200) {
                    LogThreadPoolManager.this.mTaskQueue.poll();
                }
                LogThreadPoolManager.this.mTaskQueue.offer(runnable);
            }
        };
        this.mHandler = rejectedExecutionHandler;
        ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(1);
        this.scheduler = newScheduledThreadPool;
        this.mThreadPool = new ThreadPoolExecutor(1, 1, UILooperObserver.ANR_TRIGGER_TIME, TimeUnit.MILLISECONDS, new ArrayBlockingQueue(500), new ThreadFactory() { // from class: com.alibaba.sdk.android.oss.common.LogThreadPoolManager.2
            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable, "oss-android-log-thread");
            }
        }, rejectedExecutionHandler);
        Runnable runnable = new Runnable() { // from class: com.alibaba.sdk.android.oss.common.LogThreadPoolManager.3
            @Override // java.lang.Runnable
            public void run() {
                if (LogThreadPoolManager.this.hasMoreAcquire()) {
                    LogThreadPoolManager.this.mThreadPool.execute((Runnable) LogThreadPoolManager.this.mTaskQueue.poll());
                }
            }
        };
        this.mAccessBufferThread = runnable;
        this.mTaskHandler = newScheduledThreadPool.scheduleAtFixedRate(runnable, 0L, 1000L, TimeUnit.MILLISECONDS);
    }

    public static LogThreadPoolManager newInstance() {
        if (sThreadPoolManager == null) {
            sThreadPoolManager = new LogThreadPoolManager();
        }
        return sThreadPoolManager;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasMoreAcquire() {
        return !this.mTaskQueue.isEmpty();
    }

    public void addExecuteTask(Runnable runnable) {
        if (runnable != null) {
            this.mThreadPool.execute(runnable);
        }
    }
}
