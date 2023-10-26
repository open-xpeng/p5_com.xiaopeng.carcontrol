package com.alibaba.mtl.log.b;

import android.text.TextUtils;
import com.alibaba.mtl.log.e.i;
import com.alibaba.mtl.log.e.l;
import java.util.List;

/* compiled from: CoreStatics.java */
/* loaded from: classes.dex */
public class a {
    private static StringBuilder a = new StringBuilder();
    private static volatile long e;
    private static long f;
    private static long g;
    private static long h;
    private static long i;
    private static long j;
    private static long k;
    private static long l;
    private static long m;
    private static long n;
    private static long o;
    private static long p;
    private static long q;
    private static long r;
    private static long s;
    private static long t;
    private static int u;

    /* renamed from: u  reason: collision with other field name */
    private static long f54u;
    private static int v;

    /* renamed from: v  reason: collision with other field name */
    private static long f55v;
    private static int w;

    /* renamed from: w  reason: collision with other field name */
    private static long f56w;
    private static long x;
    private static long y;

    public static synchronized void l(String str) {
        synchronized (a.class) {
            if (e(str)) {
                return;
            }
            if ("65501".equalsIgnoreCase(str)) {
                y++;
            } else if ("65133".equalsIgnoreCase(str)) {
                f56w++;
            } else if ("65502".equalsIgnoreCase(str)) {
                x++;
            } else if ("65503".equalsIgnoreCase(str)) {
                f55v++;
            }
            e++;
        }
    }

    public static synchronized void m(String str) {
        synchronized (a.class) {
            if (e(str)) {
                return;
            }
            f++;
            C();
        }
    }

    public static synchronized void a(List<com.alibaba.mtl.log.model.a> list, int i2) {
        synchronized (a.class) {
            if (list == null) {
                return;
            }
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                com.alibaba.mtl.log.model.a aVar = list.get(i4);
                if (aVar != null) {
                    if (!"6005".equalsIgnoreCase(aVar.T)) {
                        i3++;
                    }
                    a.append(aVar.X);
                    if (i4 != list.size() - 1) {
                        a.append(",");
                    }
                }
            }
            i.a("CoreStatics", "[uploadInc]:", Long.valueOf(g), "count:", Integer.valueOf(i2));
            long j2 = g + i2;
            g = j2;
            i.a("CoreStatics", "[uploadInc]:", Long.valueOf(j2));
            if (i3 != i2) {
                i.a("CoreStatics", "Mutil Process Upload Error");
            }
        }
    }

    public static synchronized void d(int i2) {
        synchronized (a.class) {
            u += i2;
        }
    }

    public static synchronized void s() {
        synchronized (a.class) {
            h++;
        }
    }

    public static synchronized void t() {
        synchronized (a.class) {
            i++;
        }
    }

    public static synchronized void u() {
        synchronized (a.class) {
            n++;
        }
    }

    public static synchronized void v() {
        synchronized (a.class) {
            o++;
        }
    }

    public static synchronized void w() {
        synchronized (a.class) {
            p++;
        }
    }

    public static synchronized void x() {
        synchronized (a.class) {
            q++;
        }
    }

    public static synchronized void y() {
        synchronized (a.class) {
            r++;
        }
    }

    public static synchronized void z() {
        synchronized (a.class) {
            s++;
        }
    }

    public static synchronized void A() {
        synchronized (a.class) {
            t++;
        }
    }

    public static synchronized void B() {
        synchronized (a.class) {
            f54u++;
        }
    }

    public static synchronized void c(boolean z) {
        synchronized (a.class) {
        }
    }

    private static void C() {
        String w2 = l.w();
        if ("wifi".equalsIgnoreCase(w2)) {
            m++;
        } else if ("3G".equalsIgnoreCase(w2)) {
            k++;
        } else if ("4G".equalsIgnoreCase(w2)) {
            l++;
        } else if ("2G".equalsIgnoreCase(w2)) {
            j++;
        } else {
            v++;
        }
    }

    public static synchronized void D() {
        synchronized (a.class) {
            w++;
            if (e == 0 && g == 0) {
                return;
            }
            if (com.alibaba.mtl.log.a.o || w >= 6) {
                c(true);
            }
        }
    }

    private static boolean e(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return "6005".equalsIgnoreCase(str.trim());
    }
}
