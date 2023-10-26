package com.alibaba.mtl.log.e;

/* compiled from: IntUtils.java */
/* loaded from: classes.dex */
public class f {
    public static byte[] getBytes(int i) {
        byte[] bArr = {(byte) ((r3 >> 8) % 256), (byte) (r3 % 256), (byte) (r3 % 256), (byte) (i % 256)};
        int i2 = i >> 8;
        int i3 = i2 >> 8;
        return bArr;
    }
}
