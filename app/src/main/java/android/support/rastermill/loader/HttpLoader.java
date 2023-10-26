package android.support.rastermill.loader;

import android.content.Context;
import android.support.rastermill.CacheEngine;
import java.io.File;

/* loaded from: classes.dex */
public class HttpLoader extends AbsLoader {
    private File mFile;

    @Override // android.support.rastermill.loader.ILoader
    public int getType() {
        return 5;
    }

    @Override // android.support.rastermill.loader.AbsLoader, android.support.rastermill.loader.ILoader
    public boolean isCacheRequired() {
        return true;
    }

    public HttpLoader(Context context, String str) {
        super(context);
        this.mKey = str;
    }

    @Override // android.support.rastermill.loader.ILoader
    public boolean exists() {
        if (this.mFile == null) {
            this.mFile = CacheEngine.getInstance().getDiskCache().get(this.mKey);
        }
        File file = this.mFile;
        return file != null && file.exists();
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x002d, code lost:
        if (r1 != null) goto L40;
     */
    @Override // android.support.rastermill.loader.AbsLoader
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.support.rastermill.FrameSequence decode() {
        /*
            r5 = this;
            java.io.File r0 = r5.mFile
            if (r0 != 0) goto L14
            android.support.rastermill.CacheEngine r0 = android.support.rastermill.CacheEngine.getInstance()
            android.support.rastermill.cache.DiskCache r0 = r0.getDiskCache()
            java.lang.String r1 = r5.mKey
            java.io.File r0 = r0.get(r1)
            r5.mFile = r0
        L14:
            java.io.File r0 = r5.mFile
            r1 = 0
            if (r0 == 0) goto L66
            boolean r0 = r0.exists()
            if (r0 == 0) goto L66
            java.io.FileInputStream r0 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L3f
            java.io.File r2 = r5.mFile     // Catch: java.lang.Throwable -> L3f
            r0.<init>(r2)     // Catch: java.lang.Throwable -> L3f
            android.support.rastermill.FrameSequence r1 = android.support.rastermill.FrameSequence.decodeStream(r0)     // Catch: java.lang.Throwable -> L3d
            r0.close()     // Catch: java.io.IOException -> L2d
        L2d:
            if (r1 != 0) goto L66
        L2f:
            android.support.rastermill.CacheEngine r0 = android.support.rastermill.CacheEngine.getInstance()
            android.support.rastermill.cache.DiskCache r0 = r0.getDiskCache()
            java.lang.String r2 = r5.mKey
            r0.delete(r2)
            goto L66
        L3d:
            r2 = move-exception
            goto L41
        L3f:
            r2 = move-exception
            r0 = r1
        L41:
            java.lang.Class<android.support.rastermill.FrameSequenceUtil> r3 = android.support.rastermill.FrameSequenceUtil.class
            java.lang.String r3 = r3.toString()     // Catch: java.lang.Throwable -> L52
            java.lang.String r4 = "decodeFile"
            android.util.Log.e(r3, r4, r2)     // Catch: java.lang.Throwable -> L52
            if (r0 == 0) goto L2f
            r0.close()     // Catch: java.io.IOException -> L2f
            goto L2f
        L52:
            r1 = move-exception
            if (r0 == 0) goto L58
            r0.close()     // Catch: java.io.IOException -> L58
        L58:
            android.support.rastermill.CacheEngine r0 = android.support.rastermill.CacheEngine.getInstance()
            android.support.rastermill.cache.DiskCache r0 = r0.getDiskCache()
            java.lang.String r2 = r5.mKey
            r0.delete(r2)
            throw r1
        L66:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.rastermill.loader.HttpLoader.decode():android.support.rastermill.FrameSequence");
    }
}
