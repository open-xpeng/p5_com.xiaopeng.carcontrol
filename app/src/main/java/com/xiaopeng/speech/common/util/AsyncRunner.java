package com.xiaopeng.speech.common.util;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/* loaded from: classes2.dex */
public abstract class AsyncRunner<IFunc> {
    protected String TAG = "AsyncRunner";
    private Queue<IFunc> mFuncQueue = new ArrayBlockingQueue(100);
    protected WorkerHandler mWorkerHandler;

    protected abstract boolean canRun();

    protected abstract <R> R realRun(IFunc ifunc);

    public AsyncRunner(String str) {
        this.TAG += "-" + str;
    }

    public void setWorkerHandler(WorkerHandler workerHandler) {
        this.mWorkerHandler = workerHandler;
    }

    public void runFunc(IFunc ifunc) {
        runFunc(ifunc, null);
    }

    public synchronized <R> R runFunc(IFunc ifunc, R r) {
        if (canRun()) {
            if (this.mFuncQueue.size() > 0) {
                this.mFuncQueue.offer(ifunc);
                fetchAll();
                LogUtils.i(this.TAG, "can run offer fun");
            } else {
                return (R) realRun(ifunc);
            }
        } else {
            this.mFuncQueue.offer(ifunc);
            LogUtils.i(this.TAG, "offer fun");
        }
        return r;
    }

    public void fetchAll() {
        LogUtils.i(this.TAG, "fetchAll fun:%d", Integer.valueOf(this.mFuncQueue.size()));
        WorkerHandler workerHandler = this.mWorkerHandler;
        if (workerHandler != null) {
            workerHandler.post(new Runnable() { // from class: com.xiaopeng.speech.common.util.AsyncRunner.1
                @Override // java.lang.Runnable
                public void run() {
                    AsyncRunner.this.doFetchAll();
                }
            });
        } else {
            doFetchAll();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void doFetchAll() {
        while (this.mFuncQueue.size() > 0) {
            IFunc poll = this.mFuncQueue.poll();
            if (canRun() && poll != null) {
                realRun(poll);
            }
        }
    }

    public synchronized void clearAll() {
        LogUtils.i(this.TAG, "clear all fun:%d", Integer.valueOf(this.mFuncQueue.size()));
        this.mFuncQueue.clear();
    }
}
