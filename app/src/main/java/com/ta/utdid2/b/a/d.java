package com.ta.utdid2.b.a;

import com.xiaopeng.xvs.xid.sync.api.ISync;
import java.lang.reflect.Method;

/* compiled from: DebugUtils.java */
/* loaded from: classes.dex */
public class d {
    private static Class<?> a;

    /* renamed from: a  reason: collision with other field name */
    private static Method f140a;
    private static Method b;
    public static boolean e;

    public static int getInt(String str, int i) {
        a();
        try {
            return ((Integer) b.invoke(a, str, Integer.valueOf(i))).intValue();
        } catch (Exception e2) {
            e2.printStackTrace();
            return i;
        }
    }

    static {
        e = getInt("alidebug", 0) == 1;
        a = null;
        f140a = null;
        b = null;
    }

    private static void a() {
        try {
            if (a == null) {
                Class<?> cls = Class.forName("android.os.SystemProperties");
                a = cls;
                f140a = cls.getDeclaredMethod(ISync.SYNC_CALL_METHOD_GET, String.class);
                b = a.getDeclaredMethod("getInt", String.class, Integer.TYPE);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
