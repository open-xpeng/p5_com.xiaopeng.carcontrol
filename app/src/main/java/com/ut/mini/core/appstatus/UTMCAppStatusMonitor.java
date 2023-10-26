package com.ut.mini.core.appstatus;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import com.alibaba.mtl.log.e.r;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

/* loaded from: classes.dex */
public class UTMCAppStatusMonitor implements Application.ActivityLifecycleCallbacks {
    private static UTMCAppStatusMonitor a;
    private int J = 0;
    private boolean S = false;

    /* renamed from: a  reason: collision with other field name */
    private ScheduledFuture<?> f160a = null;
    private Object d = new Object();
    private List<UTMCAppStatusCallbacks> m = new LinkedList();
    private Object e = new Object();

    private UTMCAppStatusMonitor() {
    }

    public static synchronized UTMCAppStatusMonitor getInstance() {
        UTMCAppStatusMonitor uTMCAppStatusMonitor;
        synchronized (UTMCAppStatusMonitor.class) {
            if (a == null) {
                a = new UTMCAppStatusMonitor();
            }
            uTMCAppStatusMonitor = a;
        }
        return uTMCAppStatusMonitor;
    }

    public void registerAppStatusCallbacks(UTMCAppStatusCallbacks uTMCAppStatusCallbacks) {
        if (uTMCAppStatusCallbacks != null) {
            synchronized (this.e) {
                this.m.add(uTMCAppStatusCallbacks);
            }
        }
    }

    public void unregisterAppStatusCallbacks(UTMCAppStatusCallbacks uTMCAppStatusCallbacks) {
        if (uTMCAppStatusCallbacks != null) {
            synchronized (this.e) {
                this.m.remove(uTMCAppStatusCallbacks);
            }
        }
    }

    private void M() {
        synchronized (this.d) {
            r.a().f(11);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
        synchronized (this.e) {
            for (UTMCAppStatusCallbacks uTMCAppStatusCallbacks : this.m) {
                uTMCAppStatusCallbacks.onActivityCreated(activity, bundle);
            }
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
        synchronized (this.e) {
            for (UTMCAppStatusCallbacks uTMCAppStatusCallbacks : this.m) {
                uTMCAppStatusCallbacks.onActivityDestroyed(activity);
            }
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
        synchronized (this.e) {
            for (UTMCAppStatusCallbacks uTMCAppStatusCallbacks : this.m) {
                uTMCAppStatusCallbacks.onActivityPaused(activity);
            }
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        synchronized (this.e) {
            for (UTMCAppStatusCallbacks uTMCAppStatusCallbacks : this.m) {
                uTMCAppStatusCallbacks.onActivityResumed(activity);
            }
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        synchronized (this.e) {
            for (UTMCAppStatusCallbacks uTMCAppStatusCallbacks : this.m) {
                uTMCAppStatusCallbacks.onActivitySaveInstanceState(activity, bundle);
            }
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
        M();
        this.J++;
        if (!this.S) {
            synchronized (this.e) {
                for (UTMCAppStatusCallbacks uTMCAppStatusCallbacks : this.m) {
                    uTMCAppStatusCallbacks.onSwitchForeground();
                }
            }
        }
        this.S = true;
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
        int i = this.J - 1;
        this.J = i;
        if (i == 0) {
            M();
            r.a().a(11, new a(), 1000L);
        }
    }

    /* loaded from: classes.dex */
    private class a implements Runnable {
        private a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            UTMCAppStatusMonitor.this.S = false;
            synchronized (UTMCAppStatusMonitor.this.e) {
                for (UTMCAppStatusCallbacks uTMCAppStatusCallbacks : UTMCAppStatusMonitor.this.m) {
                    uTMCAppStatusCallbacks.onSwitchBackground();
                }
            }
        }
    }
}
