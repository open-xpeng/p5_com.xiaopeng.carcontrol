package com.xiaopeng.speech.vui.actor;

import android.os.Handler;
import android.os.Looper;

/* loaded from: classes2.dex */
public class VuiEventHandler extends Handler {
    private static volatile VuiEventHandler instance;

    private VuiEventHandler() {
        super(Looper.getMainLooper());
    }

    public static VuiEventHandler getInstance() {
        if (instance == null) {
            synchronized (VuiEventHandler.class) {
                if (instance == null) {
                    instance = new VuiEventHandler();
                }
            }
        }
        return instance;
    }

    public void runMain(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        instance.post(runnable);
    }
}
