package com.alibaba.mtl.log.e;

/* compiled from: RC4.java */
/* loaded from: classes.dex */
public class n {

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: RC4.java */
    /* loaded from: classes.dex */
    public static class a {
        public int[] d;
        public int x;
        public int y;

        private a() {
            this.d = new int[256];
        }
    }

    public static byte[] a(byte[] bArr, String str) {
        a a2;
        if (bArr == null || str == null || (a2 = a(str)) == null) {
            return null;
        }
        return a(bArr, a2);
    }

    private static a a(String str) {
        if (str != null) {
            a aVar = new a();
            for (int i = 0; i < 256; i++) {
                aVar.d[i] = i;
            }
            aVar.x = 0;
            aVar.y = 0;
            int i2 = 0;
            int i3 = 0;
            for (int i4 = 0; i4 < 256; i4++) {
                try {
                    i3 = ((str.charAt(i2) + aVar.d[i4]) + i3) % 256;
                    int i5 = aVar.d[i4];
                    aVar.d[i4] = aVar.d[i3];
                    aVar.d[i3] = i5;
                    i2 = (i2 + 1) % str.length();
                } catch (Exception unused) {
                    return null;
                }
            }
            return aVar;
        }
        return null;
    }

    private static byte[] a(byte[] bArr, a aVar) {
        if (bArr == null || aVar == null) {
            return null;
        }
        int i = aVar.x;
        int i2 = aVar.y;
        for (int i3 = 0; i3 < bArr.length; i3++) {
            i = (i + 1) % 256;
            i2 = (aVar.d[i] + i2) % 256;
            int i4 = aVar.d[i];
            aVar.d[i] = aVar.d[i2];
            aVar.d[i2] = i4;
            bArr[i3] = (byte) (aVar.d[(aVar.d[i] + aVar.d[i2]) % 256] ^ bArr[i3]);
        }
        aVar.x = i;
        aVar.y = i2;
        return bArr;
    }
}
