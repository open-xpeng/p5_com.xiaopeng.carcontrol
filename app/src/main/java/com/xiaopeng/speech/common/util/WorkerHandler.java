package com.xiaopeng.speech.common.util;

import android.os.Handler;
import android.os.Looper;

/* loaded from: classes2.dex */
public class WorkerHandler extends Handler {
    public WorkerHandler(Looper looper) {
        super(looper);
    }

    public void optPost(Runnable runnable) {
        if (getLooper() == Looper.myLooper()) {
            runnable.run();
        } else {
            super.post(runnable);
        }
    }

    public void optPostDelay(Runnable runnable, long j) {
        if (getLooper() == Looper.myLooper() && j == 0) {
            runnable.run();
        } else {
            super.postDelayed(runnable, j);
        }
    }
}
