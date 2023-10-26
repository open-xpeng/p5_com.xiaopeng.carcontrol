package com.ta.utdid2.b.a;

import java.util.Random;

/* compiled from: PhoneInfoUtils.java */
/* loaded from: classes.dex */
public class g {
    public static final String c() {
        int nextInt = new Random().nextInt();
        int nextInt2 = new Random().nextInt();
        byte[] bytes = e.getBytes((int) (System.currentTimeMillis() / 1000));
        byte[] bytes2 = e.getBytes((int) System.nanoTime());
        byte[] bytes3 = e.getBytes(nextInt);
        byte[] bytes4 = e.getBytes(nextInt2);
        byte[] bArr = new byte[16];
        System.arraycopy(bytes, 0, bArr, 0, 4);
        System.arraycopy(bytes2, 0, bArr, 4, 4);
        System.arraycopy(bytes3, 0, bArr, 8, 4);
        System.arraycopy(bytes4, 0, bArr, 12, 4);
        return b.encodeToString(bArr, 2);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0018  */
    /* JADX WARN: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String a(android.content.Context r1) {
        /*
            if (r1 == 0) goto L11
            java.lang.String r0 = "phone"
            java.lang.Object r1 = r1.getSystemService(r0)     // Catch: java.lang.Exception -> L11
            android.telephony.TelephonyManager r1 = (android.telephony.TelephonyManager) r1     // Catch: java.lang.Exception -> L11
            if (r1 == 0) goto L11
            java.lang.String r1 = r1.getDeviceId()     // Catch: java.lang.Exception -> L11
            goto L12
        L11:
            r1 = 0
        L12:
            boolean r0 = com.ta.utdid2.b.a.i.m74a(r1)
            if (r0 == 0) goto L1c
            java.lang.String r1 = c()
        L1c:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.utdid2.b.a.g.a(android.content.Context):java.lang.String");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0018  */
    /* JADX WARN: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String b(android.content.Context r1) {
        /*
            if (r1 == 0) goto L11
            java.lang.String r0 = "phone"
            java.lang.Object r1 = r1.getSystemService(r0)     // Catch: java.lang.Exception -> L11
            android.telephony.TelephonyManager r1 = (android.telephony.TelephonyManager) r1     // Catch: java.lang.Exception -> L11
            if (r1 == 0) goto L11
            java.lang.String r1 = r1.getSubscriberId()     // Catch: java.lang.Exception -> L11
            goto L12
        L11:
            r1 = 0
        L12:
            boolean r0 = com.ta.utdid2.b.a.i.m74a(r1)
            if (r0 == 0) goto L1c
            java.lang.String r1 = c()
        L1c:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.utdid2.b.a.g.b(android.content.Context):java.lang.String");
    }
}
