package com.xiaopeng.speech.vui.constants;

import android.app.ActivityThread;
import android.content.Context;

/* loaded from: classes2.dex */
public class Foo {
    private static Context sContext;

    public static void setContext(Context context) {
        sContext = context;
    }

    public static Context getContext() {
        return ActivityThread.currentApplication();
    }
}
