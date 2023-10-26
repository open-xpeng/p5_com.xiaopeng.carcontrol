package com.unity3d.player;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class c {
    protected f b;
    protected String e;
    protected o a = null;
    protected Context c = null;
    protected String d = null;

    /* JADX INFO: Access modifiers changed from: package-private */
    public c(String str, f fVar) {
        this.b = null;
        this.e = "";
        this.e = str;
        this.b = fVar;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void reportError(String str) {
        f fVar = this.b;
        if (fVar != null) {
            fVar.reportError(this.e + " Error [" + this.d + "]", str);
        } else {
            g.Log(6, this.e + " Error [" + this.d + "]: " + str);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void runOnUiThread(Runnable runnable) {
        Context context = this.c;
        if (context instanceof Activity) {
            ((Activity) context).runOnUiThread(runnable);
        } else {
            g.Log(5, "Not running " + this.e + " from an Activity; Ignoring execution request...");
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean runOnUiThreadWithSync(final Runnable runnable) {
        boolean z = true;
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            runnable.run();
            return true;
        }
        final Semaphore semaphore = new Semaphore(0);
        runOnUiThread(new Runnable() { // from class: com.unity3d.player.c.1
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    try {
                        runnable.run();
                    } catch (Exception e) {
                        c.this.reportError("Exception unloading Google VR on UI Thread. " + e.getLocalizedMessage());
                    }
                } finally {
                    semaphore.release();
                }
            }
        });
        try {
            if (!semaphore.tryAcquire(4L, TimeUnit.SECONDS)) {
                reportError("Timeout waiting for vr state change!");
                z = false;
            }
            return z;
        } catch (InterruptedException e) {
            reportError("Interrupted while trying to acquire sync lock. " + e.getLocalizedMessage());
            return false;
        }
    }
}
