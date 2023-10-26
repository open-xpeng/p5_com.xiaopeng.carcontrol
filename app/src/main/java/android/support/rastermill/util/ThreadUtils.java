package android.support.rastermill.util;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

/* loaded from: classes.dex */
public class ThreadUtils {
    protected static Handler sMainHandler = new Handler(Looper.getMainLooper());
    private static Handler sWorkerHandler;
    private static HandlerThread sWorkerThread;

    static {
        HandlerThread handlerThread = new HandlerThread("lib_anim_webpWorker");
        sWorkerThread = handlerThread;
        handlerThread.start();
        sWorkerHandler = new Handler(sWorkerThread.getLooper());
    }

    public static void postWorker(Runnable runnable) {
        if (runnable != null) {
            sWorkerHandler.post(runnable);
        }
    }

    public static void removeWorker(Runnable runnable) {
        if (runnable != null) {
            sWorkerHandler.removeCallbacks(runnable);
        }
    }

    public static void postMain(Runnable runnable) {
        if (runnable != null) {
            sMainHandler.post(runnable);
        }
    }

    public static void removeMain(Runnable runnable) {
        if (runnable != null) {
            sMainHandler.removeCallbacks(runnable);
        }
    }
}
