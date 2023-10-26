package com.alibaba.sdk.android.httpdns;

import com.alibaba.sdk.android.httpdns.probe.IPProbeItem;
import com.alibaba.sdk.android.httpdns.probe.IPProbeService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class d {
    private static d a = new d();

    /* renamed from: a  reason: collision with other field name */
    private static IPProbeService f84a = com.alibaba.sdk.android.httpdns.probe.d.a(new com.alibaba.sdk.android.httpdns.probe.b() { // from class: com.alibaba.sdk.android.httpdns.d.1
        @Override // com.alibaba.sdk.android.httpdns.probe.b
        public void a(String str, String[] strArr) {
            e eVar;
            if (str == null || strArr == null || strArr.length == 0 || (eVar = (e) d.f85a.get(str)) == null) {
                return;
            }
            e eVar2 = new e(str, strArr, eVar.a(), eVar.b());
            d.f85a.put(str, eVar2);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < eVar2.m51a().length; i++) {
                sb.append(eVar2.m51a()[i] + ",");
            }
            i.f("optimized host:" + str + ", ip:" + sb.toString());
        }
    });

    /* renamed from: a  reason: collision with other field name */
    private static ConcurrentMap<String, e> f85a;

    /* renamed from: a  reason: collision with other field name */
    private static ConcurrentSkipListSet<String> f86a;

    private d() {
        f85a = new ConcurrentHashMap();
        f86a = new ConcurrentSkipListSet<>();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static d a() {
        return a;
    }

    private IPProbeItem a(String str) {
        List<IPProbeItem> list = f.f89a;
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                if (str.equals(list.get(i).getHostName())) {
                    return list.get(i);
                }
            }
            return null;
        }
        return null;
    }

    private boolean a(com.alibaba.sdk.android.httpdns.b.e eVar) {
        return (System.currentTimeMillis() / 1000) - com.alibaba.sdk.android.httpdns.b.c.a(eVar.j) > 604800;
    }

    private boolean a(String str, e eVar) {
        IPProbeItem a2;
        if (eVar == null || eVar.m51a() == null || eVar.m51a().length <= 1 || f84a == null || (a2 = a(str)) == null) {
            return false;
        }
        if (f84a.getProbeStatus(str) == IPProbeService.a.PROBING) {
            f84a.stopIPProbeTask(str);
        }
        i.f("START PROBE");
        f84a.launchIPProbeTask(str, a2.getPort(), eVar.m51a());
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        List<com.alibaba.sdk.android.httpdns.b.e> a2 = com.alibaba.sdk.android.httpdns.b.b.a();
        String g = com.alibaba.sdk.android.httpdns.b.b.g();
        for (com.alibaba.sdk.android.httpdns.b.e eVar : a2) {
            if (a(eVar)) {
                com.alibaba.sdk.android.httpdns.b.b.b(eVar);
            } else if (g.equals(eVar.i)) {
                eVar.j = String.valueOf(System.currentTimeMillis() / 1000);
                e eVar2 = new e(eVar);
                f85a.put(eVar.h, eVar2);
                com.alibaba.sdk.android.httpdns.b.b.b(eVar);
                a(eVar.h, eVar2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: a  reason: collision with other method in class */
    public int m42a() {
        return f85a.size();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: a  reason: collision with other method in class */
    public e m43a(String str) {
        return f85a.get(str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: a  reason: collision with other method in class */
    public ArrayList<String> m44a() {
        return new ArrayList<>(f85a.keySet());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: a  reason: collision with other method in class */
    public void m45a() {
        if (com.alibaba.sdk.android.httpdns.b.b.m34a()) {
            c.a().submit(new Runnable() { // from class: com.alibaba.sdk.android.httpdns.d.2
                @Override // java.lang.Runnable
                public void run() {
                    d.this.b();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: a  reason: collision with other method in class */
    public void m46a(String str) {
        f86a.add(str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: a  reason: collision with other method in class */
    public void m47a(String str, e eVar) {
        f85a.put(str, eVar);
        if (com.alibaba.sdk.android.httpdns.b.b.m34a()) {
            com.alibaba.sdk.android.httpdns.b.e m50a = eVar.m50a();
            if (m50a.a == null || m50a.a.size() <= 0) {
                com.alibaba.sdk.android.httpdns.b.b.b(m50a);
            } else {
                com.alibaba.sdk.android.httpdns.b.b.a(m50a);
            }
        }
        a(str, eVar);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: a  reason: collision with other method in class */
    public boolean m48a(String str) {
        return f86a.contains(str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(String str) {
        f86a.remove(str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void clear() {
        f85a.clear();
        f86a.clear();
    }
}
