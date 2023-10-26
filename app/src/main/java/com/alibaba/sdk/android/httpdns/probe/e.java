package com.alibaba.sdk.android.httpdns.probe;

import com.alibaba.sdk.android.httpdns.i;
import com.alibaba.sdk.android.httpdns.probe.IPProbeService;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes.dex */
public class e implements IPProbeService {

    /* renamed from: a  reason: collision with other field name */
    private AtomicLong f104a = new AtomicLong(0);

    /* renamed from: b  reason: collision with other field name */
    private ConcurrentHashMap<String, Long> f105b = new ConcurrentHashMap<>();
    private b a = null;
    private f b = new f() { // from class: com.alibaba.sdk.android.httpdns.probe.e.1
        @Override // com.alibaba.sdk.android.httpdns.probe.f
        public void a(long j, c cVar) {
            if (cVar != null) {
                if (!e.this.f105b.containsKey(cVar.getHostName()) || ((Long) e.this.f105b.get(cVar.getHostName())).longValue() != j) {
                    i.d("corresponding tasknumber not exists, drop the result");
                } else if (cVar == null || cVar.a() == null || cVar.i() == null || cVar.j() == null || cVar.getHostName() == null) {
                } else {
                    i.e("defultId:" + cVar.i() + ", selectedIp:" + cVar.j() + ", promote:" + (cVar.c() - cVar.d()));
                    e.this.a(cVar.getHostName(), cVar.i(), cVar.j(), cVar.c(), cVar.d(), cVar.a().length);
                    e.this.a.a(cVar.getHostName(), cVar.a());
                    e.this.f105b.remove(cVar.getHostName());
                }
            }
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str, String str2, String str3, long j, long j2, int i) {
        com.alibaba.sdk.android.httpdns.d.a a = com.alibaba.sdk.android.httpdns.d.a.a();
        if (a != null) {
            a.a(str, str2, str3, j, j2, i);
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.probe.IPProbeService
    public IPProbeService.a getProbeStatus(String str) {
        return this.f105b.containsKey(str) ? IPProbeService.a.PROBING : IPProbeService.a.NO_PROBING;
    }

    @Override // com.alibaba.sdk.android.httpdns.probe.IPProbeService
    public void launchIPProbeTask(String str, int i, String[] strArr) {
        if (!com.alibaba.sdk.android.httpdns.a.a.a().f()) {
            i.f("ip probe is forbidden");
        } else if (getProbeStatus(str) != IPProbeService.a.NO_PROBING) {
            i.f("already launch the same task, drop the task");
        } else {
            long addAndGet = this.f104a.addAndGet(1L);
            this.f105b.put(str, Long.valueOf(addAndGet));
            com.alibaba.sdk.android.httpdns.c.a().execute(new a(addAndGet, str, strArr, i, this.b));
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.probe.IPProbeService
    public void setIPListUpdateCallback(b bVar) {
        this.a = bVar;
    }

    @Override // com.alibaba.sdk.android.httpdns.probe.IPProbeService
    public boolean stopIPProbeTask(String str) {
        if (this.f105b.containsKey(str)) {
            i.d("stop ip probe task for host:" + str);
            this.f105b.remove(str);
            return true;
        }
        return false;
    }
}
