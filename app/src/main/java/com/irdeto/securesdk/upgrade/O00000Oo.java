package com.irdeto.securesdk.upgrade;

import android.content.Context;
import android.os.Build;
import android.util.Base64;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* compiled from: Utils.java */
/* loaded from: classes.dex */
public class O00000Oo {
    public static final String O000000o = "RSA";
    public static final String O00000Oo = "SHA1withRSA";
    public static final String O00000o = "(libirmsdk_)(\\d+\\.\\d+\\.\\d+)(_.+)*(\\.so\\.sig)";
    public static final String O00000o0 = "(libirmsdk_)(\\d+\\.\\d+\\.\\d+)(_.+)*(\\.so)";
    public static final String O00000oO = "(acv_)(\\d+\\.\\d+\\.\\d+)(_.+)*(\\.dat)";
    public static final String O00000oo = "(acv_)(\\d+\\.\\d+\\.\\d+)(_.+)*(\\.dat\\.sig)";
    public static final String O0000O0o = "(libirmsdk_)(%s)(_.+)*(\\.so)";
    public static final String O0000OOo = "(libirmsdk_)(%s)(_.+)*(\\.so\\.sig)";
    public static final String O0000Oo = "(acv_)(%s)(_.+)*(\\.dat\\.sig)";
    public static final String O0000Oo0 = "(acv_)(%s)(_.+)*(\\.dat)";

    private static File O000000o(File file, String str) {
        File[] listFiles;
        for (File file2 : file.listFiles()) {
            if (file2.isFile() && file2.getName().matches(str)) {
                return file2;
            }
        }
        return null;
    }

    public static String O000000o(String str) {
        Matcher matcher = Pattern.compile("(libirmsdk_)(\\d+\\.\\d+\\.\\d+)(_.+)*(\\.so)").matcher(str);
        if (matcher.find()) {
            return matcher.group(2);
        }
        return null;
    }

    public static void O000000o(O000000o o000000o, String str) {
        if (o000000o == null || str == null) {
            return;
        }
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
        }
        O00000o0(o000000o.O000000o.getPath(), String.valueOf(str) + MqttTopic.TOPIC_LEVEL_SEPARATOR + o000000o.O000000o.getName());
        O00000o0(o000000o.O00000Oo.getPath(), String.valueOf(str) + MqttTopic.TOPIC_LEVEL_SEPARATOR + o000000o.O00000Oo.getName());
        O00000o0(o000000o.O00000o0.getPath(), String.valueOf(str) + MqttTopic.TOPIC_LEVEL_SEPARATOR + o000000o.O00000o0.getName());
        O00000o0(o000000o.O00000o.getPath(), String.valueOf(str) + MqttTopic.TOPIC_LEVEL_SEPARATOR + o000000o.O00000o.getName());
    }

    public static void O000000o(String str, Context context) {
        String[] strArr;
        String str2 = O000000o() ? "x86" : "armeabi";
        try {
            strArr = context.getResources().getAssets().list(str2);
        } catch (IOException e) {
            e.printStackTrace();
            strArr = null;
        }
        for (String str3 : strArr) {
            if (O00000oO(str3)) {
                O000000o(String.format("%s/%s", str2, str3), str, context);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:65:0x00a2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0098 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:47:0x0091 -> B:69:0x0094). Please submit an issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void O000000o(java.lang.String r2, java.lang.String r3, android.content.Context r4) {
        /*
            java.io.File r0 = new java.io.File
            r0.<init>(r3)
            boolean r1 = r0.exists()
            if (r1 != 0) goto Le
            r0.mkdirs()
        Le:
            r0 = 0
            android.content.res.Resources r4 = r4.getResources()     // Catch: java.lang.Throwable -> L78 java.lang.Exception -> L7b
            android.content.res.AssetManager r4 = r4.getAssets()     // Catch: java.lang.Throwable -> L78 java.lang.Exception -> L7b
            java.io.InputStream r4 = r4.open(r2)     // Catch: java.lang.Throwable -> L78 java.lang.Exception -> L7b
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L70 java.lang.Exception -> L74
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch: java.lang.Throwable -> L70 java.lang.Exception -> L74
            r1.<init>(r3)     // Catch: java.lang.Throwable -> L70 java.lang.Exception -> L74
            java.lang.String r3 = "/"
            java.lang.StringBuilder r3 = r1.append(r3)     // Catch: java.lang.Throwable -> L70 java.lang.Exception -> L74
            r1 = 47
            int r1 = r2.lastIndexOf(r1)     // Catch: java.lang.Throwable -> L70 java.lang.Exception -> L74
            int r1 = r1 + 1
            java.lang.String r2 = r2.substring(r1)     // Catch: java.lang.Throwable -> L70 java.lang.Exception -> L74
            java.lang.StringBuilder r2 = r3.append(r2)     // Catch: java.lang.Throwable -> L70 java.lang.Exception -> L74
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> L70 java.lang.Exception -> L74
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L70 java.lang.Exception -> L74
            java.io.File r1 = new java.io.File     // Catch: java.lang.Throwable -> L70 java.lang.Exception -> L74
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L70 java.lang.Exception -> L74
            r3.<init>(r1)     // Catch: java.lang.Throwable -> L70 java.lang.Exception -> L74
            r2 = 1024(0x400, float:1.435E-42)
            byte[] r2 = new byte[r2]     // Catch: java.lang.Throwable -> L6c java.lang.Exception -> L6e
        L4c:
            int r0 = r4.read(r2)     // Catch: java.lang.Throwable -> L6c java.lang.Exception -> L6e
            r1 = -1
            if (r0 != r1) goto L67
            r4.close()     // Catch: java.lang.Throwable -> L6c java.lang.Exception -> L6e
            r3.close()     // Catch: java.lang.Throwable -> L6c java.lang.Exception -> L6e
            if (r4 == 0) goto L63
            r4.close()     // Catch: java.io.IOException -> L5f
            goto L63
        L5f:
            r2 = move-exception
            r2.printStackTrace()
        L63:
            r3.close()     // Catch: java.io.IOException -> L90
            goto L94
        L67:
            r1 = 0
            r3.write(r2, r1, r0)     // Catch: java.lang.Throwable -> L6c java.lang.Exception -> L6e
            goto L4c
        L6c:
            r2 = move-exception
            goto L72
        L6e:
            r2 = move-exception
            goto L76
        L70:
            r2 = move-exception
            r3 = r0
        L72:
            r0 = r4
            goto L96
        L74:
            r2 = move-exception
            r3 = r0
        L76:
            r0 = r4
            goto L7d
        L78:
            r2 = move-exception
            r3 = r0
            goto L96
        L7b:
            r2 = move-exception
            r3 = r0
        L7d:
            r2.printStackTrace()     // Catch: java.lang.Throwable -> L95
            if (r0 == 0) goto L8a
            r0.close()     // Catch: java.io.IOException -> L86
            goto L8a
        L86:
            r2 = move-exception
            r2.printStackTrace()
        L8a:
            if (r3 == 0) goto L94
            r3.close()     // Catch: java.io.IOException -> L90
            goto L94
        L90:
            r2 = move-exception
            r2.printStackTrace()
        L94:
            return
        L95:
            r2 = move-exception
        L96:
            if (r0 == 0) goto La0
            r0.close()     // Catch: java.io.IOException -> L9c
            goto La0
        L9c:
            r4 = move-exception
            r4.printStackTrace()
        La0:
            if (r3 == 0) goto Laa
            r3.close()     // Catch: java.io.IOException -> La6
            goto Laa
        La6:
            r3 = move-exception
            r3.printStackTrace()
        Laa:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.irdeto.securesdk.upgrade.O00000Oo.O000000o(java.lang.String, java.lang.String, android.content.Context):void");
    }

    public static boolean O000000o() {
        boolean z = false;
        try {
            if (Build.VERSION.SDK_INT >= 21) {
                for (String str : (String[]) Class.forName("android.os.Build").getField("SUPPORTED_ABIS").get(null)) {
                    if (!str.contains("x86")) {
                    }
                }
                return false;
            } else if (!((String) Class.forName("android.os.Build").getField("CPU_ABI").get(null)).contains("x86")) {
                return false;
            }
            z = true;
            return true;
        } catch (Exception unused) {
            return z;
        }
    }

    public static boolean O000000o(String str, String str2) {
        byte[] O00000o2 = O00000o(str);
        byte[] O00000o3 = O00000o(str2);
        if (O00000o2 == null || O00000o3 == null) {
            return false;
        }
        try {
            return O000000o(O000000o(new File(str).getName().getBytes(), O00000o2), "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuF2CS9xW+NihhIkXtcAgcOYqJNPqFvXG1QMG1rIBs80LwgQ5W92lnDyO0s+OiMZIUDHesxdtJhMXGyuutOGgJpWt/yWKwrb0PhIgPjrVk0rtaMLu7QDVrPdUiyhr1BwkpIWzR8+ohXt3k0K6003Sg8XI+1RbhzdoTHMMYXIMXOuy/eJsERWxoBEAnT6WiHEeCS1iHS5wmriEHqSZGFJZFoXU0ccho+Hzj0ioHf2LfQRwRtX3wG1JGQUzxihOB9lk1eQ981Yef8ZzG80gZwSK0XqB8DfEnaOOWz4TSyCpfxWkQshq05aj3+SXz0uv0yfx0D0WeZsa6DPvnQTv7W570QIDAQAB", O00000o3);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean O000000o(byte[] bArr, String str, byte[] bArr2) throws Exception {
        PublicKey generatePublic = KeyFactory.getInstance(O000000o).generatePublic(new X509EncodedKeySpec(Base64.decode(str.getBytes(), 0)));
        Signature signature = Signature.getInstance(O00000Oo);
        signature.initVerify(generatePublic);
        signature.update(bArr);
        return signature.verify(bArr2);
    }

    public static byte[] O000000o(byte[] bArr, String str) throws Exception {
        PrivateKey generatePrivate = KeyFactory.getInstance(O000000o).generatePrivate(new PKCS8EncodedKeySpec(Base64.decode(str.getBytes(), 0)));
        Signature signature = Signature.getInstance(O00000Oo);
        signature.initSign(generatePrivate);
        signature.update(bArr);
        return signature.sign();
    }

    public static byte[] O000000o(byte[] bArr, byte[] bArr2) {
        byte[] copyOf = Arrays.copyOf(bArr, bArr.length + bArr2.length);
        System.arraycopy(bArr2, 0, copyOf, bArr.length, bArr2.length);
        return copyOf;
    }

    public static int O00000Oo(String str, String str2) {
        if (str == null || str2 == null || str.equals(str2)) {
            return 0;
        }
        String[] split = str.split("\\.");
        String[] split2 = str2.split("\\.");
        if (split.length == 3 && split2.length == 3) {
            if (Integer.valueOf(split[0]).intValue() > Integer.valueOf(split2[0]).intValue()) {
                return 1;
            }
            if (Integer.valueOf(split[0]).intValue() < Integer.valueOf(split2[0]).intValue()) {
                return -1;
            }
            if (Integer.valueOf(split[1]).intValue() > Integer.valueOf(split2[1]).intValue()) {
                return 1;
            }
            if (Integer.valueOf(split[1]).intValue() < Integer.valueOf(split2[1]).intValue()) {
                return -1;
            }
            if (Integer.valueOf(split[2]).intValue() > Integer.valueOf(split2[2]).intValue()) {
                return 1;
            }
            if (Integer.valueOf(split[2]).intValue() < Integer.valueOf(split2[2]).intValue()) {
                return -1;
            }
        }
        return 0;
    }

    public static void O00000Oo(String str) {
        if (str == null) {
            return;
        }
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
            return;
        }
        File[] listFiles = file.listFiles();
        for (File file2 : listFiles) {
            if (!file2.isDirectory() && O00000oO(file2.getName())) {
                file2.delete();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:55:0x005f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0069 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:18:0x002e -> B:63:0x005b). Please submit an issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static byte[] O00000o(java.lang.String r6) {
        /*
            r0 = 0
            if (r6 != 0) goto L4
            return r0
        L4:
            r1 = 1024(0x400, float:1.435E-42)
            byte[] r1 = new byte[r1]
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L41 java.lang.Exception -> L46
            java.io.File r3 = new java.io.File     // Catch: java.lang.Throwable -> L41 java.lang.Exception -> L46
            r3.<init>(r6)     // Catch: java.lang.Throwable -> L41 java.lang.Exception -> L46
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L41 java.lang.Exception -> L46
            java.io.ByteArrayOutputStream r6 = new java.io.ByteArrayOutputStream     // Catch: java.lang.Throwable -> L39 java.lang.Exception -> L3e
            r6.<init>()     // Catch: java.lang.Throwable -> L39 java.lang.Exception -> L3e
        L17:
            int r3 = r2.read(r1)     // Catch: java.lang.Exception -> L37 java.lang.Throwable -> L5c
            if (r3 > 0) goto L32
            byte[] r0 = r6.toByteArray()     // Catch: java.lang.Exception -> L37 java.lang.Throwable -> L5c
            r6.close()     // Catch: java.io.IOException -> L25
            goto L29
        L25:
            r6 = move-exception
            r6.printStackTrace()
        L29:
            r2.close()     // Catch: java.io.IOException -> L2d
            goto L5b
        L2d:
            r6 = move-exception
            r6.printStackTrace()
            goto L5b
        L32:
            r4 = 0
            r6.write(r1, r4, r3)     // Catch: java.lang.Exception -> L37 java.lang.Throwable -> L5c
            goto L17
        L37:
            r1 = move-exception
            goto L49
        L39:
            r6 = move-exception
            r5 = r0
            r0 = r6
            r6 = r5
            goto L5d
        L3e:
            r1 = move-exception
            r6 = r0
            goto L49
        L41:
            r6 = move-exception
            r2 = r0
            r0 = r6
            r6 = r2
            goto L5d
        L46:
            r1 = move-exception
            r6 = r0
            r2 = r6
        L49:
            r1.printStackTrace()     // Catch: java.lang.Throwable -> L5c
            if (r6 == 0) goto L56
            r6.close()     // Catch: java.io.IOException -> L52
            goto L56
        L52:
            r6 = move-exception
            r6.printStackTrace()
        L56:
            if (r2 == 0) goto L5b
            r2.close()     // Catch: java.io.IOException -> L2d
        L5b:
            return r0
        L5c:
            r0 = move-exception
        L5d:
            if (r6 == 0) goto L67
            r6.close()     // Catch: java.io.IOException -> L63
            goto L67
        L63:
            r6 = move-exception
            r6.printStackTrace()
        L67:
            if (r2 == 0) goto L71
            r2.close()     // Catch: java.io.IOException -> L6d
            goto L71
        L6d:
            r6 = move-exception
            r6.printStackTrace()
        L71:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.irdeto.securesdk.upgrade.O00000Oo.O00000o(java.lang.String):byte[]");
    }

    public static O000000o O00000o0(String str) {
        String[] strArr;
        File file;
        boolean z = str != null;
        if (z) {
            file = new File(str);
            strArr = file.list(new FilenameFilter() { // from class: com.irdeto.securesdk.upgrade.O00000Oo.1
                @Override // java.io.FilenameFilter
                public boolean accept(File file2, String str2) {
                    return str2.matches("(libirmsdk_)(\\d+\\.\\d+\\.\\d+)(_.+)*(\\.so)");
                }
            });
            if (strArr == null || strArr.length == 0) {
                z = false;
            }
        } else {
            strArr = null;
            file = null;
        }
        if (z) {
            String O000000o2 = O000000o(strArr[0]);
            File O000000o3 = O000000o(file, String.format(O0000O0o, O000000o2));
            File O000000o4 = O000000o(file, String.format(O0000OOo, O000000o2));
            File O000000o5 = O000000o(file, String.format(O0000Oo0, O000000o2));
            File O000000o6 = O000000o(file, String.format(O0000Oo, O000000o2));
            if (O000000o3 == null || O000000o4 == null || O000000o5 == null || O000000o6 == null) {
                return null;
            }
            return new O000000o(O000000o3, O000000o4, O000000o5, O000000o6);
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0055 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x004b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:82:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r3v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v10 */
    /* JADX WARN: Type inference failed for: r3v11 */
    /* JADX WARN: Type inference failed for: r3v12 */
    /* JADX WARN: Type inference failed for: r3v13 */
    /* JADX WARN: Type inference failed for: r3v14 */
    /* JADX WARN: Type inference failed for: r3v15 */
    /* JADX WARN: Type inference failed for: r3v16, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r3v17, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r3v19 */
    /* JADX WARN: Type inference failed for: r3v2, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r3v21 */
    /* JADX WARN: Type inference failed for: r3v22 */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void O00000o0(java.lang.String r3, java.lang.String r4) {
        /*
            r0 = 0
            java.io.File r1 = new java.io.File     // Catch: java.lang.Throwable -> L5e
            r1.<init>(r3)     // Catch: java.lang.Throwable -> L5e
            boolean r1 = r1.exists()     // Catch: java.lang.Throwable -> L5e
            if (r1 == 0) goto L48
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch: java.io.IOException -> L3a java.io.FileNotFoundException -> L40 java.lang.Throwable -> L5e
            r1.<init>(r3)     // Catch: java.io.IOException -> L3a java.io.FileNotFoundException -> L40 java.lang.Throwable -> L5e
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L2e java.io.IOException -> L32 java.io.FileNotFoundException -> L36
            r3.<init>(r4)     // Catch: java.lang.Throwable -> L2e java.io.IOException -> L32 java.io.FileNotFoundException -> L36
            r4 = 1444(0x5a4, float:2.023E-42)
            byte[] r4 = new byte[r4]     // Catch: java.lang.Throwable -> L28 java.io.IOException -> L2a java.io.FileNotFoundException -> L2c
        L1a:
            int r0 = r1.read(r4)     // Catch: java.lang.Throwable -> L28 java.io.IOException -> L2a java.io.FileNotFoundException -> L2c
            r2 = -1
            if (r0 != r2) goto L23
            r0 = r1
            goto L49
        L23:
            r2 = 0
            r3.write(r4, r2, r0)     // Catch: java.lang.Throwable -> L28 java.io.IOException -> L2a java.io.FileNotFoundException -> L2c
            goto L1a
        L28:
            r4 = move-exception
            goto L30
        L2a:
            r4 = move-exception
            goto L34
        L2c:
            r4 = move-exception
            goto L38
        L2e:
            r4 = move-exception
            r3 = r0
        L30:
            r0 = r1
            goto L60
        L32:
            r4 = move-exception
            r3 = r0
        L34:
            r0 = r1
            goto L3c
        L36:
            r4 = move-exception
            r3 = r0
        L38:
            r0 = r1
            goto L42
        L3a:
            r4 = move-exception
            r3 = r0
        L3c:
            r4.printStackTrace()     // Catch: java.lang.Throwable -> L46
            goto L49
        L40:
            r4 = move-exception
            r3 = r0
        L42:
            r4.printStackTrace()     // Catch: java.lang.Throwable -> L46
            goto L49
        L46:
            r4 = move-exception
            goto L60
        L48:
            r3 = r0
        L49:
            if (r0 == 0) goto L53
            r0.close()     // Catch: java.io.IOException -> L4f
            goto L53
        L4f:
            r4 = move-exception
            r4.printStackTrace()
        L53:
            if (r3 == 0) goto L5d
            r3.close()     // Catch: java.io.IOException -> L59
            goto L5d
        L59:
            r3 = move-exception
            r3.printStackTrace()
        L5d:
            return
        L5e:
            r4 = move-exception
            r3 = r0
        L60:
            if (r0 == 0) goto L6a
            r0.close()     // Catch: java.io.IOException -> L66
            goto L6a
        L66:
            r0 = move-exception
            r0.printStackTrace()
        L6a:
            if (r3 == 0) goto L74
            r3.close()     // Catch: java.io.IOException -> L70
            goto L74
        L70:
            r3 = move-exception
            r3.printStackTrace()
        L74:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.irdeto.securesdk.upgrade.O00000Oo.O00000o0(java.lang.String, java.lang.String):void");
    }

    private static boolean O00000oO(String str) {
        if (str == null) {
            return false;
        }
        return str.matches("(libirmsdk_)(\\d+\\.\\d+\\.\\d+)(_.+)*(\\.so)") || str.matches("(acv_)(\\d+\\.\\d+\\.\\d+)(_.+)*(\\.dat)") || str.matches("(libirmsdk_)(\\d+\\.\\d+\\.\\d+)(_.+)*(\\.so\\.sig)") || str.matches("(acv_)(\\d+\\.\\d+\\.\\d+)(_.+)*(\\.dat\\.sig)");
    }
}
