package com.bumptech.glide.request;

/* loaded from: classes.dex */
public interface Request {
    void begin();

    void clear();

    boolean isCancelled();

    boolean isComplete();

    boolean isEquivalentTo(Request request);

    boolean isFailed();

    boolean isPaused();

    boolean isResourceSet();

    boolean isRunning();

    void pause();

    void recycle();
}
