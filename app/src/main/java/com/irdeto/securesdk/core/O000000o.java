package com.irdeto.securesdk.core;

import android.content.Context;
import android.os.Build;
import android.util.Base64;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

/* compiled from: Utils.java */
/* loaded from: classes.dex */
public class O000000o {
    public static final String O000000o = "securesdk";
    public static String O00000Oo = "a0332f8c-adcd-4c3b-92e4-75e9d7ffccd2";
    public static final String O00000o = "(libirmsdk_)(\\d+\\.\\d+\\.\\d+)(_.+)*(\\.so)";
    public static String O00000o0 = "a2e431ba0332f88.lock";
    public static final String O00000oO = "(libirmsdk_)(\\d+\\.\\d+\\.\\d+)(_.+)*(\\.so\\.sig)";
    public static final String O00000oo = "(acv_)(\\d+\\.\\d+\\.\\d+)(_.+)*(\\.dat)";
    public static final String O0000O0o = "(acv_)(\\d+\\.\\d+\\.\\d+)(_.+)*(\\.dat\\.sig)";
    private static final String O0000OOo = "securesdk";
    private static final String O0000Oo = "SHA1withRSA";
    private static final String O0000Oo0 = "RSA";
    private static final String O0000OoO = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuF2CS9xW+NihhIkXtcAgcOYqJNPqFvXG1QMG1rIBs80LwgQ5W92lnDyO0s+OiMZIUDHesxdtJhMXGyuutOGgJpWt/yWKwrb0PhIgPjrVk0rtaMLu7QDVrPdUiyhr1BwkpIWzR8+ohXt3k0K6003Sg8XI+1RbhzdoTHMMYXIMXOuy/eJsERWxoBEAnT6WiHEeCS1iHS5wmriEHqSZGFJZFoXU0ccho+Hzj0ioHf2LfQRwRtX3wG1JGQUzxihOB9lk1eQ981Yef8ZzG80gZwSK0XqB8DfEnaOOWz4TSyCpfxWkQshq05aj3+SXz0uv0yfx0D0WeZsa6DPvnQTv7W570QIDAQAB";

    public static String O000000o() {
        return "/data/data/org.zxq.teleri/files/" + O00000o0;
    }

    public static String O000000o(String str, final String str2) {
        String[] list = new File(str).list(new FilenameFilter() { // from class: com.irdeto.securesdk.core.O000000o.1
            @Override // java.io.FilenameFilter
            public boolean accept(File file, String str3) {
                return str3.matches(str2);
            }
        });
        return (list == null || list.length <= 0) ? "" : list[0];
    }

    public static String O000000o(byte[] bArr) {
        char[] cArr = new char[bArr.length * 2];
        char[] cArr2 = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        int i = 0;
        for (int i2 = 0; i2 < bArr.length; i2++) {
            int i3 = i + 1;
            cArr[i] = cArr2[(bArr[i2] >>> 4) & 15];
            i = i3 + 1;
            cArr[i3] = cArr2[bArr[i2] & 15];
        }
        return new String(cArr);
    }

    public static Key O000000o(KeyPair keyPair, byte[] bArr) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(4, keyPair.getPrivate());
            return cipher.unwrap(bArr, "AES", 3);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
            return null;
        } catch (NoSuchPaddingException e3) {
            e3.printStackTrace();
            return null;
        }
    }

    public static void O000000o(String str, byte[] bArr) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(str));
            fileOutputStream.write(bArr);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean O000000o(Context context) {
        return false;
    }

    private static boolean O000000o(byte[] bArr, String str, byte[] bArr2) throws Exception {
        PublicKey generatePublic = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decode(str.getBytes(), 0)));
        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initVerify(generatePublic);
        signature.update(bArr);
        return signature.verify(bArr2);
    }

    public static byte[] O000000o(String str) {
        File file = new File(str);
        byte[] bArr = new byte[(int) file.length()];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(bArr);
            fileInputStream.close();
            return bArr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] O000000o(KeyPair keyPair, Key key) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(3, keyPair.getPublic());
            return cipher.wrap(key);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
            return null;
        } catch (IllegalBlockSizeException e3) {
            e3.printStackTrace();
            return null;
        } catch (NoSuchPaddingException e4) {
            e4.printStackTrace();
            return null;
        }
    }

    public static byte[] O000000o(byte[] bArr, Key key) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(1, key);
            return cipher.doFinal(bArr);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
            return null;
        } catch (BadPaddingException e3) {
            e3.printStackTrace();
            return null;
        } catch (IllegalBlockSizeException e4) {
            e4.printStackTrace();
            return null;
        } catch (NoSuchPaddingException e5) {
            e5.printStackTrace();
            return null;
        }
    }

    private static byte[] O000000o(byte[] bArr, byte[] bArr2) {
        byte[] copyOf = Arrays.copyOf(bArr, bArr.length + bArr2.length);
        System.arraycopy(bArr2, 0, copyOf, bArr.length, bArr2.length);
        return copyOf;
    }

    public static Key O00000Oo() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean O00000Oo(String str) {
        return new File(str).exists();
    }

    public static boolean O00000Oo(String str, String str2) {
        byte[] O00000o2 = O00000o(str);
        byte[] O00000o3 = O00000o(str2);
        if (O00000o2 != null && O00000o3 != null) {
            try {
                return O000000o(O000000o(new File(str).getName().getBytes(), O00000o2), O0000OoO, O00000o3);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static byte[] O00000Oo(byte[] bArr, Key key) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(2, key);
            return cipher.doFinal(bArr);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
            return null;
        } catch (BadPaddingException e3) {
            e3.printStackTrace();
            return null;
        } catch (IllegalBlockSizeException e4) {
            e4.printStackTrace();
            return null;
        } catch (NoSuchPaddingException e5) {
            e5.printStackTrace();
            return null;
        }
    }

    public static Key O00000o() {
        return O000000o(O00000o0(), O000000o(O000000o()));
    }

    /* JADX WARN: Removed duplicated region for block: B:57:0x005f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0069 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:19:0x0037 -> B:52:0x005a). Please submit an issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static byte[] O00000o(java.lang.String r5) {
        /*
            boolean r0 = android.text.TextUtils.isEmpty(r5)
            r1 = 0
            if (r0 == 0) goto L8
            return r1
        L8:
            r0 = 1024(0x400, float:1.435E-42)
            byte[] r0 = new byte[r0]
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L45
            java.io.File r3 = new java.io.File     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L45
            r3.<init>(r5)     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L45
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L45
            java.io.ByteArrayOutputStream r5 = new java.io.ByteArrayOutputStream     // Catch: java.lang.Throwable -> L3d java.lang.Exception -> L3f
            r5.<init>()     // Catch: java.lang.Throwable -> L3d java.lang.Exception -> L3f
        L1b:
            int r3 = r2.read(r0)     // Catch: java.lang.Exception -> L3b java.lang.Throwable -> L5b
            if (r3 <= 0) goto L26
            r4 = 0
            r5.write(r0, r4, r3)     // Catch: java.lang.Exception -> L3b java.lang.Throwable -> L5b
            goto L1b
        L26:
            byte[] r1 = r5.toByteArray()     // Catch: java.lang.Exception -> L3b java.lang.Throwable -> L5b
            r5.close()     // Catch: java.io.IOException -> L2e
            goto L32
        L2e:
            r5 = move-exception
            r5.printStackTrace()
        L32:
            r2.close()     // Catch: java.io.IOException -> L36
            goto L5a
        L36:
            r5 = move-exception
            r5.printStackTrace()
            goto L5a
        L3b:
            r0 = move-exception
            goto L48
        L3d:
            r0 = move-exception
            goto L5d
        L3f:
            r0 = move-exception
            r5 = r1
            goto L48
        L42:
            r0 = move-exception
            r2 = r1
            goto L5d
        L45:
            r0 = move-exception
            r5 = r1
            r2 = r5
        L48:
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L5b
            if (r5 == 0) goto L55
            r5.close()     // Catch: java.io.IOException -> L51
            goto L55
        L51:
            r5 = move-exception
            r5.printStackTrace()
        L55:
            if (r2 == 0) goto L5a
            r2.close()     // Catch: java.io.IOException -> L36
        L5a:
            return r1
        L5b:
            r0 = move-exception
            r1 = r5
        L5d:
            if (r1 == 0) goto L67
            r1.close()     // Catch: java.io.IOException -> L63
            goto L67
        L63:
            r5 = move-exception
            r5.printStackTrace()
        L67:
            if (r2 == 0) goto L71
            r2.close()     // Catch: java.io.IOException -> L6d
            goto L71
        L6d:
            r5 = move-exception
            r5.printStackTrace()
        L71:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.irdeto.securesdk.core.O000000o.O00000o(java.lang.String):byte[]");
    }

    public static KeyPair O00000o0() {
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            if (Build.VERSION.SDK_INT >= 23) {
                return new KeyPair(keyStore.getCertificate(O00000Oo).getPublicKey(), (PrivateKey) keyStore.getKey(O00000Oo, null));
            }
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(O00000Oo, null);
            return new KeyPair(privateKeyEntry.getCertificate().getPublicKey(), privateKeyEntry.getPrivateKey());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (KeyStoreException e2) {
            e2.printStackTrace();
            return null;
        } catch (NoSuchAlgorithmException e3) {
            e3.printStackTrace();
            return null;
        } catch (UnrecoverableEntryException e4) {
            e4.printStackTrace();
            return null;
        } catch (CertificateException e5) {
            e5.printStackTrace();
            return null;
        }
    }

    public static void O00000o0(String str, String str2) {
    }

    public static byte[] O00000o0(String str) {
        byte[] bArr = new byte[str.length() / 2];
        char[] charArray = str.toCharArray();
        int i = 0;
        int i2 = 0;
        while (i < charArray.length) {
            bArr[i2] = (byte) Integer.parseInt(new String(charArray, i, 2), 16);
            i += 2;
            i2++;
        }
        return bArr;
    }
}
