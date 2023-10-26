package com.xiaopeng.lib.framework.netchannelmodule.common;

import android.os.Handler;
import android.os.HandlerThread;

/* loaded from: classes2.dex */
public class BackgroundHandler {
    private static final String DEFAULT_THREAD_NAME = "BackgroundHandler";
    private volatile Handler mHandler;
    private HandlerThread mHandlerThread;
    private final String mThreadName;

    public BackgroundHandler(String str) {
        this.mThreadName = str;
        start();
    }

    private BackgroundHandler() {
        this.mThreadName = DEFAULT_THREAD_NAME;
    }

    public synchronized void start() {
        if (this.mHandlerThread != null) {
            return;
        }
        HandlerThread handlerThread = new HandlerThread(this.mThreadName);
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mHandler = new Handler(this.mHandlerThread.getLooper());
    }

    public synchronized void stop() {
        clearRunnables();
        this.mHandler = null;
        HandlerThread handlerThread = this.mHandlerThread;
        if (handlerThread != null) {
            handlerThread.quitSafely();
            this.mHandlerThread = null;
        }
    }

    public synchronized boolean post(Runnable runnable) {
        if (this.mHandler != null) {
            return this.mHandler.post(runnable);
        }
        return false;
    }

    public synchronized boolean postAtFront(Runnable runnable) {
        if (this.mHandler != null) {
            return this.mHandler.postAtFrontOfQueue(runnable);
        }
        return false;
    }

    public synchronized boolean postDelayed(Runnable runnable, long j) {
        if (this.mHandler != null) {
            return this.mHandler.postDelayed(runnable, j);
        }
        return false;
    }

    public synchronized void clearRunnables() throws RuntimeException {
        if (this.mHandler != null) {
            this.mHandler.removeCallbacksAndMessages(null);
        }
    }

    public synchronized void removeCallbacks(Runnable runnable) throws RuntimeException {
        if (this.mHandler != null) {
            this.mHandler.removeCallbacks(runnable);
        }
    }

    public synchronized boolean hasCallbacks(Runnable runnable) {
        if (this.mHandler == null) {
            return false;
        }
        return this.mHandler.hasCallbacks(runnable);
    }
}
