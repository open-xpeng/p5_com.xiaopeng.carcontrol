package com.xiaopeng.lludancemanager.util;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes2.dex */
public class FileUtil {
    /* JADX WARN: Removed duplicated region for block: B:38:0x0042 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String loadFromAssets(java.lang.String r3) {
        /*
            r0 = 0
            com.xiaopeng.carcontrol.App r1 = com.xiaopeng.carcontrol.App.getInstance()     // Catch: java.lang.Throwable -> L2a java.io.IOException -> L2f
            android.content.Context r1 = r1.getApplicationContext()     // Catch: java.lang.Throwable -> L2a java.io.IOException -> L2f
            android.content.res.AssetManager r1 = r1.getAssets()     // Catch: java.lang.Throwable -> L2a java.io.IOException -> L2f
            java.io.InputStream r3 = r1.open(r3)     // Catch: java.lang.Throwable -> L2a java.io.IOException -> L2f
            if (r3 == 0) goto L24
            java.lang.String r0 = inputStream2String(r3)     // Catch: java.io.IOException -> L22 java.lang.Throwable -> L3f
            if (r3 == 0) goto L21
            r3.close()     // Catch: java.io.IOException -> L1d
            goto L21
        L1d:
            r3 = move-exception
            r3.printStackTrace()
        L21:
            return r0
        L22:
            r1 = move-exception
            goto L31
        L24:
            if (r3 == 0) goto L3e
            r3.close()     // Catch: java.io.IOException -> L3a
            goto L3e
        L2a:
            r3 = move-exception
            r2 = r0
            r0 = r3
            r3 = r2
            goto L40
        L2f:
            r1 = move-exception
            r3 = r0
        L31:
            r1.printStackTrace()     // Catch: java.lang.Throwable -> L3f
            if (r3 == 0) goto L3e
            r3.close()     // Catch: java.io.IOException -> L3a
            goto L3e
        L3a:
            r3 = move-exception
            r3.printStackTrace()
        L3e:
            return r0
        L3f:
            r0 = move-exception
        L40:
            if (r3 == 0) goto L4a
            r3.close()     // Catch: java.io.IOException -> L46
            goto L4a
        L46:
            r3 = move-exception
            r3.printStackTrace()
        L4a:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.lludancemanager.util.FileUtil.loadFromAssets(java.lang.String):java.lang.String");
    }

    private static String inputStream2String(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        byte[] bArr = new byte[4096];
        while (true) {
            int read = in.read(bArr);
            if (read != -1) {
                sb.append(new String(bArr, 0, read));
            } else {
                return sb.toString();
            }
        }
    }
}
