package com.ta.utdid2.device;

import android.content.Context;
import com.ta.utdid2.b.a.g;
import com.ta.utdid2.b.a.i;
import java.util.zip.Adler32;

/* compiled from: DeviceInfo.java */
/* loaded from: classes.dex */
public class b {
    private static a a = null;
    static final Object e = new Object();
    static String k = "d6fc3a4a06adbde89223bvefedc24fecde188aaa9161";

    static long a(a aVar) {
        if (aVar != null) {
            String format = String.format("%s%s%s%s%s", aVar.f(), aVar.getDeviceId(), Long.valueOf(aVar.a()), aVar.e(), aVar.d());
            if (i.m74a(format)) {
                return 0L;
            }
            Adler32 adler32 = new Adler32();
            adler32.reset();
            adler32.update(format.getBytes());
            return adler32.getValue();
        }
        return 0L;
    }

    private static a a(Context context) {
        if (context != null) {
            new a();
            synchronized (e) {
                String value = c.a(context).getValue();
                if (i.m74a(value)) {
                    return null;
                }
                if (value.endsWith("\n")) {
                    value = value.substring(0, value.length() - 1);
                }
                a aVar = new a();
                long currentTimeMillis = System.currentTimeMillis();
                String a2 = g.a(context);
                String b = g.b(context);
                aVar.d(a2);
                aVar.b(a2);
                aVar.b(currentTimeMillis);
                aVar.c(b);
                aVar.e(value);
                aVar.a(a(aVar));
                return aVar;
            }
        }
        return null;
    }

    public static synchronized a b(Context context) {
        synchronized (b.class) {
            a aVar = a;
            if (aVar != null) {
                return aVar;
            }
            if (context != null) {
                a a2 = a(context);
                a = a2;
                return a2;
            }
            return null;
        }
    }
}
