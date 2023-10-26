package com.xiaopeng.carcontrol.util;

import android.content.Context;
import java.lang.ref.WeakReference;

/* loaded from: classes2.dex */
public class ContextUtils {
    private static WeakReference<Context> sContext;

    public static void init(Context context) {
        sContext = new WeakReference<>(context.getApplicationContext());
    }

    public static Context getContext() {
        WeakReference<Context> weakReference = sContext;
        if (weakReference != null) {
            return weakReference.get();
        }
        throw new RuntimeException("please init first!!!");
    }
}
