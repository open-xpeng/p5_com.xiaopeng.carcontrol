package android.support.rastermill.loader;

import android.content.Context;
import android.support.rastermill.FrameSequence;

/* loaded from: classes.dex */
public abstract class AbsLoader implements ILoader {
    protected Context mContext;
    protected String mKey;

    public abstract FrameSequence decode();

    @Override // android.support.rastermill.loader.ILoader
    public boolean isCacheRequired() {
        return false;
    }

    @Override // android.support.rastermill.loader.ILoader
    public void release() {
    }

    public AbsLoader(Context context) {
        this.mContext = context;
    }

    @Override // android.support.rastermill.loader.ILoader
    public String getKey() {
        return this.mKey;
    }

    @Override // android.support.rastermill.loader.ILoader
    public FrameSequence getFrameSequence() {
        if (exists()) {
            FrameSequence decode = decode();
            if (decode != null) {
                decode.addFrameSequenceKey(getKey());
            }
            return decode;
        }
        return null;
    }
}
