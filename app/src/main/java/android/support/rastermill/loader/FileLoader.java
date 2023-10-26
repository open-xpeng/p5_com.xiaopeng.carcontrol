package android.support.rastermill.loader;

import android.content.Context;
import java.io.File;

/* loaded from: classes.dex */
public class FileLoader extends AbsLoader {
    private File mFile;

    @Override // android.support.rastermill.loader.ILoader
    public int getType() {
        return 2;
    }

    public FileLoader(Context context, File file) {
        super(context);
        this.mFile = file;
        this.mKey = file.getAbsolutePath();
    }

    @Override // android.support.rastermill.loader.ILoader
    public boolean exists() {
        File file = this.mFile;
        return file != null && file.exists();
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x001f, code lost:
        if (r1 == null) goto L11;
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
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L12
            java.io.File r2 = r5.mFile     // Catch: java.lang.Throwable -> L12
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L12
            android.support.rastermill.FrameSequence r0 = android.support.rastermill.FrameSequence.decodeStream(r1)     // Catch: java.lang.Throwable -> L10
        Lc:
            r1.close()     // Catch: java.io.IOException -> L22
            goto L22
        L10:
            r2 = move-exception
            goto L14
        L12:
            r2 = move-exception
            r1 = r0
        L14:
            java.lang.Class<android.support.rastermill.FrameSequenceUtil> r3 = android.support.rastermill.FrameSequenceUtil.class
            java.lang.String r3 = r3.toString()     // Catch: java.lang.Throwable -> L23
            java.lang.String r4 = "decodeFile"
            android.util.Log.e(r3, r4, r2)     // Catch: java.lang.Throwable -> L23
            if (r1 == 0) goto L22
            goto Lc
        L22:
            return r0
        L23:
            r0 = move-exception
            if (r1 == 0) goto L29
            r1.close()     // Catch: java.io.IOException -> L29
        L29:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.rastermill.loader.FileLoader.decode():android.support.rastermill.FrameSequence");
    }
}
