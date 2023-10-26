package android.support.rastermill.loader;

import android.support.rastermill.FrameSequence;

/* loaded from: classes.dex */
public interface ILoader {
    public static final int TYPE_ASSET = 1;
    public static final int TYPE_FILE = 2;
    public static final int TYPE_HTTP = 5;
    public static final int TYPE_INPUT_STREAM = 4;
    public static final int TYPE_NONE = 0;
    public static final int TYPE_RESOURCE = 3;

    boolean exists();

    FrameSequence getFrameSequence();

    String getKey();

    int getType();

    boolean isCacheRequired();

    void release();
}
