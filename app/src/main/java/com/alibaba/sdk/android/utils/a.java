package com.alibaba.sdk.android.utils;

import java.util.concurrent.ThreadFactory;

/* compiled from: AlicloudThreadFactory.java */
/* loaded from: classes.dex */
public class a implements ThreadFactory {
    @Override // java.util.concurrent.ThreadFactory
    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.setUncaughtExceptionHandler(new b());
        return thread;
    }
}
