package com.alibaba.mtl.log.d;

import com.alibaba.mtl.log.e.a;
import com.alibaba.mtl.log.e.e;
import com.alibaba.mtl.log.e.i;
import com.alibaba.mtl.log.e.l;
import com.alibaba.mtl.log.e.n;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

/* compiled from: UploadTask.java */
/* loaded from: classes.dex */
public abstract class b implements Runnable {
    static int A = 0;
    private static volatile boolean F = false;
    private static boolean G = false;
    int B = -1;
    float a = 200.0f;
    int C = 0;

    public abstract void J();

    public abstract void K();

    @Override // java.lang.Runnable
    public void run() {
        try {
            L();
            J();
        } catch (Throwable unused) {
        }
    }

    public static boolean isRunning() {
        return F;
    }

    /* JADX WARN: Code restructure failed: missing block: B:66:0x0171, code lost:
        com.alibaba.mtl.log.e.k.release();
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x01ef, code lost:
        com.alibaba.mtl.log.d.b.F = false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void L() {
        /*
            Method dump skipped, instructions count: 503
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.mtl.log.d.b.L():void");
    }

    private int b(List<com.alibaba.mtl.log.model.a> list) {
        if (list == null) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < list.size(); i2++) {
            String str = list.get(i2).T;
            if (str != null && "6005".equalsIgnoreCase(str.toString())) {
                i++;
            }
        }
        return i;
    }

    private int h() {
        if (this.B == -1) {
            String w = l.w();
            if ("wifi".equalsIgnoreCase(w)) {
                this.B = 20;
            } else if ("4G".equalsIgnoreCase(w)) {
                this.B = 16;
            } else if ("3G".equalsIgnoreCase(w)) {
                this.B = 12;
            } else {
                this.B = 8;
            }
        }
        return this.B;
    }

    private a.C0008a a(String str, Map<String, Object> map) {
        if (str != null) {
            byte[] bArr = e.a(2, str, map, false).e;
            i.a("UploadTask", "url:", str);
            if (bArr != null) {
                String str2 = null;
                try {
                    str2 = new String(bArr, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (str2 != null) {
                    i.a("UploadTask", "result:", str2);
                    return com.alibaba.mtl.log.e.a.a(str2);
                }
            }
        }
        return a.C0008a.a;
    }

    private int a(Boolean bool, long j) {
        if (j < 0) {
            return this.B;
        }
        float f = this.C / ((float) j);
        if (!bool.booleanValue()) {
            this.B /= 2;
            A++;
        } else if (j > 45000) {
            return this.B;
        } else {
            this.B = (int) (((f * 45000.0f) / this.a) - A);
        }
        int i = this.B;
        if (i < 1) {
            this.B = 1;
            A = 0;
        } else if (i > 350) {
            this.B = 350;
        }
        i.a("UploadTask", "winsize:", Integer.valueOf(this.B));
        return this.B;
    }

    private Map<String, Object> a(List<com.alibaba.mtl.log.model.a> list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        HashMap hashMap = new HashMap();
        for (int i = 0; i < list.size(); i++) {
            List<String> a = a(list.get(i));
            if (a != null) {
                for (int i2 = 0; i2 < a.size(); i2++) {
                    StringBuilder sb = (StringBuilder) hashMap.get(a.get(i2));
                    if (sb == null) {
                        sb = new StringBuilder();
                        hashMap.put(a.get(i2), sb);
                    } else {
                        sb.append("\n");
                    }
                    sb.append(list.get(i).k());
                }
            }
        }
        HashMap hashMap2 = new HashMap();
        this.C = 0;
        for (String str : hashMap.keySet()) {
            byte[] a2 = a(((StringBuilder) hashMap.get(str)).toString());
            hashMap2.put(str, a2);
            this.C += a2.length;
        }
        float size = this.C / list.size();
        this.a = size;
        i.a("UploadTask", "averagePackageSize:", Float.valueOf(size));
        return hashMap2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v8, types: [java.lang.String] */
    private byte[] a(String str) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPOutputStream gZIPOutputStream = null;
        try {
            try {
                GZIPOutputStream gZIPOutputStream2 = new GZIPOutputStream(byteArrayOutputStream);
                try {
                    ?? r1 = "UTF-8";
                    gZIPOutputStream2.write(str.getBytes("UTF-8"));
                    gZIPOutputStream2.flush();
                    gZIPOutputStream2.close();
                    gZIPOutputStream = r1;
                } catch (IOException e) {
                    e = e;
                    gZIPOutputStream = gZIPOutputStream2;
                    e.printStackTrace();
                    gZIPOutputStream = gZIPOutputStream;
                    if (gZIPOutputStream != null) {
                        gZIPOutputStream.close();
                        gZIPOutputStream = gZIPOutputStream;
                    }
                    byte[] a = n.a(byteArrayOutputStream.toByteArray(), "QrMgt8GGYI6T52ZY5AnhtxkLzb8egpFn3j5JELI8H6wtACbUnZ5cc3aYTsTRbmkAkRJeYbtx92LPBWm7nBO9UIl7y5i5MQNmUZNf5QENurR5tGyo7yJ2G0MBjWvy6iAtlAbacKP0SwOUeUWx5dsBdyhxa7Id1APtybSdDgicBDuNjI0mlZFUzZSS9dmN8lBD0WTVOMz0pRZbR3cysomRXOO1ghqjJdTcyDIxzpNAEszN8RMGjrzyU7Hjbmwi6YNK");
                    byteArrayOutputStream.close();
                    return a;
                } catch (Throwable th) {
                    th = th;
                    gZIPOutputStream = gZIPOutputStream2;
                    if (gZIPOutputStream != null) {
                        try {
                            gZIPOutputStream.close();
                        } catch (Exception unused) {
                        }
                    }
                    throw th;
                }
            } catch (IOException e2) {
                e = e2;
            }
            byte[] a2 = n.a(byteArrayOutputStream.toByteArray(), "QrMgt8GGYI6T52ZY5AnhtxkLzb8egpFn3j5JELI8H6wtACbUnZ5cc3aYTsTRbmkAkRJeYbtx92LPBWm7nBO9UIl7y5i5MQNmUZNf5QENurR5tGyo7yJ2G0MBjWvy6iAtlAbacKP0SwOUeUWx5dsBdyhxa7Id1APtybSdDgicBDuNjI0mlZFUzZSS9dmN8lBD0WTVOMz0pRZbR3cysomRXOO1ghqjJdTcyDIxzpNAEszN8RMGjrzyU7Hjbmwi6YNK");
            try {
                byteArrayOutputStream.close();
            } catch (Exception unused2) {
            }
            return a2;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private List<String> a(com.alibaba.mtl.log.model.a aVar) {
        return com.alibaba.mtl.log.a.a.m21a(aVar.T);
    }
}
