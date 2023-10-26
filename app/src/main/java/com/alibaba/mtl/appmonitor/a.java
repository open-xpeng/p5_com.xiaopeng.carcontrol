package com.alibaba.mtl.appmonitor;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.mtl.appmonitor.a.f;
import com.alibaba.mtl.appmonitor.d.j;
import com.alibaba.mtl.log.e.i;
import com.alibaba.mtl.log.e.r;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingConstants;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: BackgroundTrigger.java */
/* loaded from: classes.dex */
public class a implements Runnable {
    private static boolean j = false;
    private static boolean l = false;
    private Application b;
    private boolean k = true;

    public static void init(Application application) {
        if (j) {
            return;
        }
        i.a("BackgroundTrigger", "init BackgroundTrigger");
        l = a(application.getApplicationContext());
        a aVar = new a(application);
        if (l) {
            r.a().a(4, aVar, 60000L);
        } else if (Build.VERSION.SDK_INT >= 14) {
            application.registerActivityLifecycleCallbacks(new C0007a(aVar));
        }
        j = true;
    }

    public a(Application application) {
        this.b = application;
    }

    @Override // java.lang.Runnable
    public void run() {
        int i = 0;
        i.a("BackgroundTrigger", "[bg check]");
        boolean b = com.alibaba.mtl.log.e.b.b(this.b.getApplicationContext());
        if (this.k != b) {
            this.k = b;
            if (b) {
                j.a().j();
                f[] values = f.values();
                int length = values.length;
                while (i < length) {
                    f fVar = values[i];
                    AppMonitorDelegate.setStatisticsInterval(fVar, fVar.c());
                    i++;
                }
                com.alibaba.mtl.log.a.l();
            } else {
                f[] values2 = f.values();
                int length2 = values2.length;
                while (i < length2) {
                    f fVar2 = values2[i];
                    AppMonitorDelegate.setStatisticsInterval(fVar2, fVar2.d());
                    i++;
                }
                AppMonitorDelegate.triggerUpload();
                com.alibaba.mtl.log.a.k();
            }
        }
        if (l) {
            r.a().a(4, this, 60000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: BackgroundTrigger.java */
    /* renamed from: com.alibaba.mtl.appmonitor.a$a  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    public class C0007a implements Application.ActivityLifecycleCallbacks {

        /* renamed from: a  reason: collision with other field name */
        private Runnable f39a;

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        C0007a(Runnable runnable) {
            this.f39a = runnable;
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(Activity activity) {
            r.a().f(4);
            r.a().a(4, this.f39a, 60000L);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStopped(Activity activity) {
            r.a().f(4);
            r.a().a(4, this.f39a, 60000L);
        }
    }

    private static boolean a(Context context) {
        String a = com.alibaba.mtl.log.e.b.a(context);
        i.a("BackgroundTrigger", "[checkRuningProcess]:", a);
        return (TextUtils.isEmpty(a) || a.indexOf(QuickSettingConstants.JOINER) == -1) ? false : true;
    }
}
