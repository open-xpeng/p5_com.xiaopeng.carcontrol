package com.unity3d.player;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import com.unity3d.player.GoogleVrVideo;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicLong;
import org.eclipse.paho.client.mqttv3.internal.ClientDefaults;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class GoogleVrProxy extends c implements GoogleVrVideo {
    private boolean f;
    private boolean g;
    private Runnable h;
    private Vector i;
    private SurfaceView j;
    private a k;
    private Thread l;
    private Handler m;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class a {
        public boolean a = false;
        public boolean b = false;
        public boolean c = false;
        public boolean d = false;
        public boolean e = true;
        public boolean f = false;

        a() {
        }

        public final boolean a() {
            return this.a && this.b;
        }

        public final void b() {
            this.a = false;
            this.b = false;
            this.d = false;
            this.e = true;
            this.f = false;
        }
    }

    public GoogleVrProxy(f fVar) {
        super("Google VR", fVar);
        this.f = false;
        this.g = false;
        this.h = null;
        this.i = new Vector();
        this.j = null;
        this.k = new a();
        this.l = null;
        this.m = new Handler(Looper.getMainLooper()) { // from class: com.unity3d.player.GoogleVrProxy.1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                if (message.what != 135711) {
                    super.handleMessage(message);
                    return;
                }
                switch (message.arg1) {
                    case 2147483645:
                        Iterator it = GoogleVrProxy.this.i.iterator();
                        while (it.hasNext()) {
                            ((GoogleVrVideo.GoogleVrVideoCallbacks) it.next()).onFrameAvailable();
                        }
                        return;
                    case 2147483646:
                        Surface surface = (Surface) message.obj;
                        Iterator it2 = GoogleVrProxy.this.i.iterator();
                        while (it2.hasNext()) {
                            ((GoogleVrVideo.GoogleVrVideoCallbacks) it2.next()).onSurfaceAvailable(surface);
                        }
                        return;
                    default:
                        super.handleMessage(message);
                        return;
                }
            }
        };
        initVrJni();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(boolean z) {
        this.k.d = z;
    }

    private static boolean a(int i) {
        return Build.VERSION.SDK_INT >= i;
    }

    private boolean a(ClassLoader classLoader) {
        try {
            Class<?> loadClass = classLoader.loadClass("com.unity3d.unitygvr.GoogleVR");
            o oVar = new o(loadClass, loadClass.getConstructor(new Class[0]).newInstance(new Object[0]));
            oVar.a("initialize", new Class[]{Activity.class, Context.class, SurfaceView.class, Boolean.TYPE, Handler.class});
            oVar.a("deinitialize", new Class[0]);
            oVar.a("load", new Class[]{Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Runnable.class});
            oVar.a("enable", new Class[]{Boolean.TYPE});
            oVar.a("unload", new Class[0]);
            oVar.a("pause", new Class[0]);
            oVar.a("resume", new Class[0]);
            oVar.a("getGvrLayout", new Class[0]);
            oVar.a("getVideoSurfaceId", new Class[0]);
            oVar.a("getVideoSurface", new Class[0]);
            this.a = oVar;
            return true;
        } catch (Exception e) {
            reportError("Exception initializing GoogleVR from Unity library. " + e.getLocalizedMessage());
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean d() {
        return this.k.d;
    }

    private void e() {
        Activity activity = (Activity) this.c;
        if (!this.g || this.k.f || activity == null) {
            return;
        }
        this.k.f = true;
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        intent.setFlags(ClientDefaults.MAX_MSG_SIZE);
        activity.startActivity(intent);
    }

    private final native void initVrJni();

    private final native boolean isQuiting();

    private final native void setVrVideoTransform(float[][] fArr);

    public final void a(Intent intent) {
        if (intent == null || !intent.getBooleanExtra("android.intent.extra.VR_LAUNCH", false)) {
            return;
        }
        this.g = true;
    }

    public final boolean a() {
        return this.k.a;
    }

    public final boolean a(Activity activity, Context context, SurfaceView surfaceView, Runnable runnable) {
        String str;
        boolean z;
        if (activity == null || context == null || surfaceView == null || runnable == null) {
            str = "Invalid parameters passed to Google VR initiialization.";
        } else {
            this.k.b();
            this.c = context;
            this.h = runnable;
            if (!a(19)) {
                str = "Google VR requires a device that supports an api version of 19 (KitKat) or better.";
            } else if (this.g && !a(24)) {
                str = "Daydream requires a device that supports an api version of 24 (Nougat) or better.";
            } else if (!a(UnityPlayer.class.getClassLoader())) {
                return false;
            } else {
                try {
                    z = ((Boolean) this.a.a("initialize", activity, context, surfaceView, Boolean.valueOf(this.g), this.m)).booleanValue();
                } catch (Exception e) {
                    reportError("Exception while trying to intialize Unity Google VR Library. " + e.getLocalizedMessage());
                    z = false;
                }
                if (z) {
                    this.j = surfaceView;
                    this.k.a = true;
                    this.d = "";
                    return true;
                }
                str = "Unable to initialize GoogleVR library.";
            }
        }
        reportError(str);
        return false;
    }

    public final void b() {
        resumeGvrLayout();
    }

    public final void c() {
        SurfaceView surfaceView = this.j;
        if (surfaceView != null) {
            surfaceView.getHolder().setSizeFromLayout();
        }
    }

    @Override // com.unity3d.player.GoogleVrVideo
    public void deregisterGoogleVrVideoListener(GoogleVrVideo.GoogleVrVideoCallbacks googleVrVideoCallbacks) {
        if (this.i.contains(googleVrVideoCallbacks)) {
            googleVrVideoCallbacks.onSurfaceUnavailable();
            this.i.remove(googleVrVideoCallbacks);
        }
    }

    protected Object getVideoSurface() {
        if (d() && !this.k.e) {
            try {
                return this.a.a("getVideoSurface", new Object[0]);
            } catch (Exception e) {
                reportError("Exception caught while Getting GoogleVR Video Surface. " + e.getLocalizedMessage());
            }
        }
        return null;
    }

    protected int getVideoSurfaceId() {
        if (d() && !this.k.e) {
            try {
                return ((Integer) this.a.a("getVideoSurfaceId", new Object[0])).intValue();
            } catch (Exception e) {
                reportError("Exception caught while getting Video Surface ID from GoogleVR. " + e.getLocalizedMessage());
            }
        }
        return -1;
    }

    protected long loadGoogleVr(final boolean z, final boolean z2, final boolean z3, final boolean z4, final boolean z5) {
        if (this.k.a) {
            final AtomicLong atomicLong = new AtomicLong(0L);
            this.d = (z || z2) ? "Daydream" : "Cardboard";
            if (!runOnUiThreadWithSync(new Runnable() { // from class: com.unity3d.player.GoogleVrProxy.2
                @Override // java.lang.Runnable
                public final void run() {
                    try {
                        atomicLong.set(((Long) GoogleVrProxy.this.a.a("load", Boolean.valueOf(z), Boolean.valueOf(z2), Boolean.valueOf(z3), Boolean.valueOf(z4), Boolean.valueOf(z5), GoogleVrProxy.this.h)).longValue());
                        GoogleVrProxy.this.k.b = true;
                    } catch (Exception e) {
                        GoogleVrProxy.this.reportError("Exception caught while loading GoogleVR. " + e.getLocalizedMessage());
                        atomicLong.set(0L);
                    }
                }
            }) || atomicLong.longValue() == 0) {
                reportError("Google VR had a fatal issue while loading. VR will not be available.");
            }
            return atomicLong.longValue();
        }
        return 0L;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void pauseGvrLayout() {
        if (this.k.a() && !this.k.e) {
            if (d()) {
                Iterator it = this.i.iterator();
                while (it.hasNext()) {
                    ((GoogleVrVideo.GoogleVrVideoCallbacks) it.next()).onSurfaceUnavailable();
                }
            }
            if (this.a != null) {
                this.a.a("pause", new Object[0]);
            }
            this.k.e = true;
        }
    }

    @Override // com.unity3d.player.GoogleVrVideo
    public void registerGoogleVrVideoListener(GoogleVrVideo.GoogleVrVideoCallbacks googleVrVideoCallbacks) {
        if (this.i.contains(googleVrVideoCallbacks)) {
            return;
        }
        this.i.add(googleVrVideoCallbacks);
        Surface surface = (Surface) getVideoSurface();
        if (surface != null) {
            googleVrVideoCallbacks.onSurfaceAvailable(surface);
        }
    }

    protected void resumeGvrLayout() {
        if (this.k.a() && this.k.e) {
            if (this.a != null) {
                this.a.a("resume", new Object[0]);
            }
            this.k.e = false;
        }
    }

    protected void setGoogleVrModeEnabled(final boolean z) {
        if (!this.k.a() || this.b == null || this.c == null) {
            return;
        }
        if (!z && isQuiting()) {
            e();
        }
        runOnUiThread(new Runnable() { // from class: com.unity3d.player.GoogleVrProxy.3
            @Override // java.lang.Runnable
            public final void run() {
                if (z == GoogleVrProxy.this.d()) {
                    return;
                }
                try {
                    if (z && !GoogleVrProxy.this.d()) {
                        if (GoogleVrProxy.this.a != null && GoogleVrProxy.this.b != null && !GoogleVrProxy.this.b.addViewToPlayer((View) GoogleVrProxy.this.a.a("getGvrLayout", new Object[0]), true)) {
                            GoogleVrProxy.this.reportError("Unable to add Google VR to view hierarchy.");
                            return;
                        }
                        if (GoogleVrProxy.this.a != null) {
                            GoogleVrProxy.this.a.a("enable", true);
                        }
                        GoogleVrProxy.this.a(true);
                    } else if (z || !GoogleVrProxy.this.d()) {
                    } else {
                        GoogleVrProxy.this.a(false);
                        if (GoogleVrProxy.this.a != null) {
                            GoogleVrProxy.this.a.a("enable", false);
                        }
                        if (GoogleVrProxy.this.a == null || GoogleVrProxy.this.b == null) {
                            return;
                        }
                        GoogleVrProxy.this.b.removeViewFromPlayer((View) GoogleVrProxy.this.a.a("getGvrLayout", new Object[0]));
                    }
                } catch (Exception e) {
                    GoogleVrProxy.this.reportError("Exception enabling Google VR on UI Thread. " + e.getLocalizedMessage());
                }
            }
        });
    }

    @Override // com.unity3d.player.GoogleVrVideo
    public void setVideoLocationTransform(float[] fArr) {
        float[][] fArr2 = (float[][]) Array.newInstance(float.class, 4, 4);
        for (int i = 0; i < 4; i++) {
            for (int i2 = 0; i2 < 4; i2++) {
                fArr2[i][i2] = fArr[(i * 4) + i2];
            }
        }
        setVrVideoTransform(fArr2);
    }

    protected void unloadGoogleVr() {
        if (this.k.d) {
            setGoogleVrModeEnabled(false);
        }
        if (this.k.c) {
            this.k.c = false;
        }
        this.j = null;
        runOnUiThread(new Runnable() { // from class: com.unity3d.player.GoogleVrProxy.4
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    if (GoogleVrProxy.this.a != null) {
                        GoogleVrProxy.this.a.a("unload", new Object[0]);
                        GoogleVrProxy.this.a.a("deinitialize", new Object[0]);
                        GoogleVrProxy.this.a = null;
                    }
                    GoogleVrProxy.this.k.b = false;
                } catch (Exception e) {
                    GoogleVrProxy.this.reportError("Exception unloading Google VR on UI Thread. " + e.getLocalizedMessage());
                }
            }
        });
    }
}
