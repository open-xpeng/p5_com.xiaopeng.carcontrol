package com.xiaopeng.smartcontrol.utils;

import android.os.Handler;
import android.os.HandlerThread;

/* loaded from: classes2.dex */
public class HandlerThreadHelper {
    private Handler mHandler;
    private HandlerThread mHandlerThread;

    public HandlerThreadHelper(String str) {
        HandlerThread handlerThread = new HandlerThread(str);
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mHandler = new Handler(this.mHandlerThread.getLooper());
    }

    public void post(Runnable runnable) {
        this.mHandler.post(runnable);
    }

    public void remove(Runnable runnable) {
        this.mHandler.removeCallbacks(runnable);
    }

    public void postDelayed(Runnable runnable, long j) {
        this.mHandler.postDelayed(runnable, j);
    }

    public void destroy() {
        this.mHandlerThread.quit();
    }
}
