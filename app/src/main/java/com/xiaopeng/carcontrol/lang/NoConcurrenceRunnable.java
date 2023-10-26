package com.xiaopeng.carcontrol.lang;

import java.lang.ref.WeakReference;

/* loaded from: classes2.dex */
public class NoConcurrenceRunnable {
    private Runnable mRun;
    private int mSession;
    private WeakReference<SessionRunnable> mSessionRunnable;
    private boolean mStrict;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public abstract class SessionRunnable implements Runnable {
        boolean mRepeatable = false;
        int mSession;

        SessionRunnable() {
        }
    }

    public NoConcurrenceRunnable() {
    }

    public NoConcurrenceRunnable(Runnable runnable) {
        this.mRun = runnable;
    }

    public NoConcurrenceRunnable strict() {
        this.mStrict = true;
        return this;
    }

    protected void run() {
        Runnable runnable = this.mRun;
        if (runnable != null) {
            runnable.run();
        }
    }

    public void cancel() {
        synchronized (this) {
            this.mSession++;
        }
    }

    public int getSession() {
        return this.mSession;
    }

    public void trigger(int session) {
        trigger(session, true);
    }

    public void trigger(int session, boolean updateSession) {
        boolean z;
        synchronized (this) {
            WeakReference<SessionRunnable> weakReference = this.mSessionRunnable;
            SessionRunnable sessionRunnable = weakReference != null ? weakReference.get() : null;
            z = sessionRunnable != null && sessionRunnable.mSession == session;
            if (updateSession) {
                this.mSession++;
            }
        }
        if (z) {
            run();
        }
    }

    public Runnable newSession() {
        return newSession(false);
    }

    public Runnable newSession(boolean repeatable) {
        SessionRunnable sessionRunnable = new SessionRunnable() { // from class: com.xiaopeng.carcontrol.lang.NoConcurrenceRunnable.1
            @Override // java.lang.Runnable
            public void run() {
                boolean z;
                if (NoConcurrenceRunnable.this.mStrict) {
                    synchronized (NoConcurrenceRunnable.this) {
                        if (this.mSession == NoConcurrenceRunnable.this.mSession) {
                            NoConcurrenceRunnable.this.run();
                            if (!this.mRepeatable) {
                                this.mSession--;
                            }
                        }
                    }
                    return;
                }
                synchronized (NoConcurrenceRunnable.this) {
                    z = this.mSession == NoConcurrenceRunnable.this.mSession;
                    if (!this.mRepeatable) {
                        this.mSession--;
                    }
                }
                if (z) {
                    NoConcurrenceRunnable.this.run();
                }
            }
        };
        synchronized (this) {
            int i = this.mSession + 1;
            this.mSession = i;
            sessionRunnable.mSession = i;
            sessionRunnable.mRepeatable = repeatable;
            this.mSessionRunnable = new WeakReference<>(sessionRunnable);
        }
        return sessionRunnable;
    }
}
