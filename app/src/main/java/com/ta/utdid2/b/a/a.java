package com.ta.utdid2.b.a;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.compress.archivers.tar.TarConstants;

/* compiled from: AESUtils.java */
/* loaded from: classes.dex */
public class a {
    public static byte[] a = {TarConstants.LF_NORMAL, TarConstants.LF_NORMAL, TarConstants.LF_LINK, TarConstants.LF_CONTIG, 68, 67, TarConstants.LF_LINK, 66, 69, TarConstants.LF_SYMLINK, TarConstants.LF_SYMLINK, TarConstants.LF_DIR, 56, TarConstants.LF_DIR, TarConstants.LF_DIR, TarConstants.LF_BLK, 67, 70, TarConstants.LF_NORMAL, TarConstants.LF_SYMLINK, 67, TarConstants.LF_DIR, TarConstants.LF_CONTIG, 66, TarConstants.LF_CONTIG, 56, 69, TarConstants.LF_CONTIG, TarConstants.LF_BLK, TarConstants.LF_NORMAL, 65, TarConstants.LF_DIR};

    public static String d(String str, String str2) {
        byte[] bArr;
        try {
            bArr = a(m72a(str.getBytes()), str2.getBytes());
        } catch (Exception unused) {
            bArr = null;
        }
        if (bArr != null) {
            return a(bArr);
        }
        return null;
    }

    public static String e(String str, String str2) {
        try {
            return new String(b(m72a(str.getBytes()), a(str2)));
        } catch (Exception unused) {
            return null;
        }
    }

    /* renamed from: a  reason: collision with other method in class */
    private static byte[] m72a(byte[] bArr) throws Exception {
        return a(new String(a, 0, 32));
    }

    private static byte[] a(byte[] bArr, byte[] bArr2) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(1, secretKeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        return cipher.doFinal(bArr2);
    }

    private static byte[] b(byte[] bArr, byte[] bArr2) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(2, secretKeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        return cipher.doFinal(bArr2);
    }

    public static byte[] a(String str) {
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i] = Integer.valueOf(str.substring(i2, i2 + 2), 16).byteValue();
        }
        return bArr;
    }

    public static String a(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer(bArr.length * 2);
        for (byte b : bArr) {
            a(stringBuffer, b);
        }
        return stringBuffer.toString();
    }

    private static void a(StringBuffer stringBuffer, byte b) {
        stringBuffer.append("0123456789ABCDEF".charAt((b >> 4) & 15)).append("0123456789ABCDEF".charAt(b & 15));
    }
}
