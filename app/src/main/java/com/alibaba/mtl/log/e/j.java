package com.alibaba.mtl.log.e;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* compiled from: MD5Utils.java */
/* loaded from: classes.dex */
public class j {
    public static char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String a(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (int i = 0; i < bArr.length; i++) {
            sb.append(a[(bArr[i] & 240) >>> 4]);
            sb.append(a[bArr[i] & 15]);
        }
        return sb.toString();
    }

    public static String b(byte[] bArr) {
        byte[] m29a = m29a(bArr);
        return m29a != null ? a(m29a) : "0000000000000000";
    }

    /* renamed from: a  reason: collision with other method in class */
    public static byte[] m29a(byte[] bArr) {
        if (bArr != null) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                messageDigest.update(bArr);
                return messageDigest.digest();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
}
