package com.xiaopeng.speech.vui.task;

import com.xiaopeng.speech.vui.task.queue.DeDuplicationQueue;
import com.xiaopeng.speech.vui.task.queue.TaskWrapperQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class ThreadPoolProxy {
    private int mCorePoolSize;
    private ThreadPoolExecutor mExecutor;
    private int mMaximumPoolSize;
    private DeDuplicationQueue queue;

    public ThreadPoolProxy(int i, int i2) {
        this.mCorePoolSize = i;
        this.mMaximumPoolSize = i2;
    }

    private void initThreadPoolExecutor() {
        ThreadPoolExecutor threadPoolExecutor = this.mExecutor;
        if (threadPoolExecutor == null || threadPoolExecutor.isShutdown() || this.mExecutor.isTerminated()) {
            synchronized (ThreadPoolProxy.class) {
                ThreadPoolExecutor threadPoolExecutor2 = this.mExecutor;
                if (threadPoolExecutor2 == null || threadPoolExecutor2.isShutdown() || this.mExecutor.isTerminated()) {
                    TimeUnit timeUnit = TimeUnit.MILLISECONDS;
                    this.queue = new TaskWrapperQueue();
                    this.mExecutor = new ThreadPoolExecutor(this.mCorePoolSize, this.mMaximumPoolSize, 3000L, timeUnit, this.queue, Executors.defaultThreadFactory(), new ThreadPoolExecutor.DiscardPolicy());
                }
            }
        }
    }

    public void execute(Runnable runnable) {
        initThreadPoolExecutor();
        this.mExecutor.execute(runnable);
    }

    public Future submit(Runnable runnable) {
        initThreadPoolExecutor();
        return this.mExecutor.submit(runnable);
    }

    public void remove(Runnable runnable) {
        initThreadPoolExecutor();
    }

    public void removeTask(String str) {
        DeDuplicationQueue deDuplicationQueue = this.queue;
        if (deDuplicationQueue != null) {
            deDuplicationQueue.removeTask(str);
        }
    }
}
