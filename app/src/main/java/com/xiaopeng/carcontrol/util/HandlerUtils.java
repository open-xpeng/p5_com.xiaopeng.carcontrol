package com.xiaopeng.carcontrol.util;

import android.os.Handler;
import android.os.Looper;

/* loaded from: classes2.dex */
public class HandlerUtils {
    public static void removeCallbacks(final Handler handler, final Runnable callback) {
        if (handler.getLooper() == Looper.myLooper()) {
            handler.removeCallbacks(callback);
        } else {
            handler.post(new Runnable() { // from class: com.xiaopeng.carcontrol.util.-$$Lambda$HandlerUtils$ctdFLiIGjgzqfcrMDS7mzZqhcYM
                @Override // java.lang.Runnable
                public final void run() {
                    handler.removeCallbacks(callback);
                }
            });
        }
    }

    public static void removeMessages(final Handler handler, final int what) {
        removeMessages(handler, what, null);
    }

    public static void removeMessages(final Handler handler, final int what, final Object obj) {
        if (handler.getLooper() == Looper.myLooper()) {
            handler.removeMessages(what, obj);
        } else {
            handler.post(new Runnable() { // from class: com.xiaopeng.carcontrol.util.-$$Lambda$HandlerUtils$-aJKsg8JTRr3dGUkb5jl4mmncgU
                @Override // java.lang.Runnable
                public final void run() {
                    handler.removeMessages(what, obj);
                }
            });
        }
    }
}
