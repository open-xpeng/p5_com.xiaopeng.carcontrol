package com.xiaopeng.carcontrol.lang;

import com.xiaopeng.carcontrol.util.ThreadUtils;

/* loaded from: classes2.dex */
public class TriggerJob {
    private long mInterval;
    private long mLastTriggerTime;
    private Runnable mPendingJob;
    private Runnable mRunnable;

    public TriggerJob() {
        this.mInterval = 1000L;
    }

    public TriggerJob(long interval, Runnable runnable) {
        this.mInterval = interval;
        this.mRunnable = runnable;
    }

    public final void untrigger() {
        synchronized (this) {
            this.mPendingJob = null;
            this.mLastTriggerTime = 0L;
        }
    }

    public final void trigger() {
        long j;
        boolean z;
        if (this.mPendingJob != null) {
            synchronized (this) {
                if (this.mPendingJob != null) {
                    return;
                }
            }
        }
        long currentTimeMillis = System.currentTimeMillis();
        synchronized (this) {
            j = this.mLastTriggerTime + this.mInterval;
            z = currentTimeMillis < j;
        }
        Runnable newJob = newJob();
        if (z) {
            synchronized (this) {
                this.mPendingJob = newJob;
            }
            ThreadUtils.postDelayed(new Runnable() { // from class: com.xiaopeng.carcontrol.lang.-$$Lambda$TriggerJob$4F5DFyfcWdiIXHb5WTEdu3bSOWE
                @Override // java.lang.Runnable
                public final void run() {
                    TriggerJob.this.lambda$trigger$0$TriggerJob();
                }
            }, j - currentTimeMillis);
            return;
        }
        synchronized (this) {
            this.mLastTriggerTime = currentTimeMillis;
        }
        newJob.run();
    }

    public /* synthetic */ void lambda$trigger$0$TriggerJob() {
        Runnable runnable;
        synchronized (this) {
            runnable = this.mPendingJob;
            this.mPendingJob = null;
            this.mLastTriggerTime = System.currentTimeMillis();
        }
        if (runnable != null) {
            runnable.run();
        }
    }

    private Runnable newJob() {
        return new Runnable() { // from class: com.xiaopeng.carcontrol.lang.-$$Lambda$Yrx6ckA48-Ezb5r0fWoYRFnZkBU
            @Override // java.lang.Runnable
            public final void run() {
                TriggerJob.this.onTrigger();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTrigger() {
        Runnable runnable = this.mRunnable;
        if (runnable != null) {
            runnable.run();
        }
    }
}
