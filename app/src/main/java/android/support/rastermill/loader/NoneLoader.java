package android.support.rastermill.loader;

import android.content.Context;
import android.support.rastermill.FrameSequence;

/* loaded from: classes.dex */
public class NoneLoader extends AbsLoader {
    @Override // android.support.rastermill.loader.AbsLoader
    public FrameSequence decode() {
        return null;
    }

    @Override // android.support.rastermill.loader.ILoader
    public boolean exists() {
        return false;
    }

    @Override // android.support.rastermill.loader.ILoader
    public int getType() {
        return 0;
    }

    public NoneLoader(Context context) {
        super(context);
    }
}
