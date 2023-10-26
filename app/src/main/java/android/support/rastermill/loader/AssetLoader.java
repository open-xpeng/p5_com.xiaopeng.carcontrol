package android.support.rastermill.loader;

import android.content.Context;
import android.text.TextUtils;

/* loaded from: classes.dex */
public class AssetLoader extends AbsLoader {
    private String mAsset;

    @Override // android.support.rastermill.loader.ILoader
    public int getType() {
        return 1;
    }

    public AssetLoader(Context context, String str) {
        super(context);
        this.mAsset = str;
        this.mKey = str;
    }

    @Override // android.support.rastermill.loader.ILoader
    public boolean exists() {
        return !TextUtils.isEmpty(this.mAsset);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0026, code lost:
        if (r1 == null) goto L7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0029, code lost:
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:5:0x0011, code lost:
        if (r1 != null) goto L9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x0013, code lost:
        r1.close();
     */
    @Override // android.support.rastermill.loader.AbsLoader
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.support.rastermill.FrameSequence decode() {
        /*
            r5 = this;
            r0 = 0
            android.content.Context r1 = r5.mContext     // Catch: java.lang.Throwable -> L19
            android.content.res.AssetManager r1 = r1.getAssets()     // Catch: java.lang.Throwable -> L19
            java.lang.String r2 = r5.mAsset     // Catch: java.lang.Throwable -> L19
            java.io.InputStream r1 = r1.open(r2)     // Catch: java.lang.Throwable -> L19
            android.support.rastermill.FrameSequence r0 = android.support.rastermill.FrameSequence.decodeStream(r1)     // Catch: java.lang.Throwable -> L17
            if (r1 == 0) goto L29
        L13:
            r1.close()     // Catch: java.io.IOException -> L29
            goto L29
        L17:
            r2 = move-exception
            goto L1b
        L19:
            r2 = move-exception
            r1 = r0
        L1b:
            java.lang.Class<android.support.rastermill.FrameSequenceUtil> r3 = android.support.rastermill.FrameSequenceUtil.class
            java.lang.String r3 = r3.toString()     // Catch: java.lang.Throwable -> L2a
            java.lang.String r4 = "decodeAsset"
            android.util.Log.e(r3, r4, r2)     // Catch: java.lang.Throwable -> L2a
            if (r1 == 0) goto L29
            goto L13
        L29:
            return r0
        L2a:
            r0 = move-exception
            if (r1 == 0) goto L30
            r1.close()     // Catch: java.io.IOException -> L30
        L30:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.rastermill.loader.AssetLoader.decode():android.support.rastermill.FrameSequence");
    }
}
