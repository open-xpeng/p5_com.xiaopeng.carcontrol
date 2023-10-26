package com.xiaopeng.speech.vui.task;

/* loaded from: classes2.dex */
public class ThreadPoolProxyFactory {
    static ThreadPoolProxy mThreadPool;

    public static ThreadPoolProxy getThreadPool() {
        if (mThreadPool == null) {
            synchronized (ThreadPoolProxyFactory.class) {
                if (mThreadPool == null) {
                    mThreadPool = new ThreadPoolProxy(1, 1);
                }
            }
        }
        return mThreadPool;
    }
}
