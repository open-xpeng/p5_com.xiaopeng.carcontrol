package com.alibaba.mtl.log.a;

import com.alibaba.mtl.log.e.e;
import com.alibaba.mtl.log.e.i;
import com.alibaba.mtl.log.e.l;
import com.alibaba.mtl.log.e.r;
import com.alibaba.mtl.log.e.t;
import java.util.HashMap;

/* compiled from: GcConfigChannelMgr.java */
/* loaded from: classes.dex */
public class b {
    private static b a = new b();
    private static String P = "adashxgc.ut.taobao.com";

    static /* synthetic */ String j() {
        return i();
    }

    private static String i() {
        return "https://" + P + "/rest/gc2";
    }

    public static b a() {
        return a;
    }

    public void q() {
        r.a().b(new a());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: GcConfigChannelMgr.java */
    /* loaded from: classes.dex */
    public class a implements Runnable {
        a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (l.isConnected()) {
                for (int i = 0; i < 8; i++) {
                    HashMap hashMap = new HashMap();
                    String m22b = com.alibaba.mtl.log.a.a.m22b("b01n15");
                    String m22b2 = com.alibaba.mtl.log.a.a.m22b("b01na");
                    hashMap.put("_b01n15", m22b);
                    hashMap.put("_b01na", m22b2);
                    try {
                        String b = t.b(b.j(), hashMap, null);
                        i.a("ConfigMgr", "config:" + b);
                        e.a a = com.alibaba.mtl.log.e.e.a(1, b, null, false);
                        if (a.e != null) {
                            com.alibaba.mtl.log.a.a.h(new String(a.e, 0, a.e.length));
                            com.alibaba.mtl.log.a.a.p();
                            return;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(10000L);
                    } catch (Exception unused) {
                    }
                }
            }
        }
    }
}
