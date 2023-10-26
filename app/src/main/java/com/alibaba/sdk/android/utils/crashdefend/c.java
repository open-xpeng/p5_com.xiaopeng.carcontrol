package com.alibaba.sdk.android.utils.crashdefend;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.sdk.android.utils.crashdefend.a;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

/* compiled from: CrashDefendManager.java */
/* loaded from: classes.dex */
public class c {
    private static c b;
    private Context a;

    /* renamed from: a  reason: collision with other field name */
    private com.alibaba.sdk.android.utils.c f121a;

    /* renamed from: b  reason: collision with other field name */
    private d f124b;
    private boolean d;

    /* renamed from: a  reason: collision with other field name */
    private com.alibaba.sdk.android.utils.crashdefend.b f122a = new com.alibaba.sdk.android.utils.crashdefend.b();

    /* renamed from: b  reason: collision with other field name */
    private List<d> f125b = new ArrayList();

    /* renamed from: b  reason: collision with other field name */
    private ExecutorService f126b = null;

    /* renamed from: b  reason: collision with other field name */
    private com.alibaba.sdk.android.utils.crashdefend.a f123b = null;
    private Map<String, String> e = new HashMap();

    public void d(String str, String str2) {
    }

    public static synchronized c a(Context context, com.alibaba.sdk.android.utils.c cVar) {
        c cVar2;
        synchronized (c.class) {
            if (b == null) {
                b = new c(context, cVar);
            }
            cVar2 = b;
        }
        return cVar2;
    }

    private c(Context context, com.alibaba.sdk.android.utils.c cVar) {
        this.d = true;
        this.a = context;
        this.d = f.a(context);
        this.f121a = cVar;
        this.e.put("sdkId", "utils");
        this.e.put("sdkVersion", "1.1.3");
        if (this.d) {
            a();
            b();
            c();
        }
    }

    private void a() {
        if (f.m61a(this.a, this.f122a, this.f125b)) {
            this.f122a.a++;
            return;
        }
        this.f122a.a = 1L;
    }

    private void b() {
        this.f123b = com.alibaba.sdk.android.utils.crashdefend.a.a(this.a);
        for (d dVar : this.f125b) {
            if (dVar != null) {
                this.f123b.a(dVar, new b());
            }
        }
    }

    private void c() {
        this.f124b = null;
        ArrayList<d> arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (d dVar : this.f125b) {
            if (dVar.c == 0) {
                if (dVar.crashCount >= dVar.a) {
                    arrayList.add(dVar);
                } else {
                    arrayList2.add(dVar);
                }
            }
        }
        int[] iArr = new int[5];
        for (int i = 0; i < 5; i++) {
            iArr[i] = (i * 5) + 5;
        }
        for (d dVar2 : arrayList) {
            if (dVar2.d >= 5) {
                Log.i("UtilsSDK", "SDK " + dVar2.f129a + " has been closed");
            } else {
                long j = this.f122a.a - iArr[dVar2.d];
                if (dVar2.f127a < j && dVar2.f132c < j) {
                    if (this.f124b == null) {
                        this.f124b = dVar2;
                    } else if (dVar2.f127a < this.f124b.f127a) {
                        this.f124b = dVar2;
                    } else if (dVar2.f127a == this.f124b.f127a && this.f124b.crashCount - this.f124b.a < dVar2.crashCount - dVar2.a) {
                        this.f124b = dVar2;
                    }
                }
            }
        }
        if (this.f124b == null) {
            Log.i("UtilsSDK", "NO SDK restore");
            return;
        }
        Iterator it = arrayList2.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            d dVar3 = (d) it.next();
            if (dVar3.crashCount > 0 && dVar3.f130b >= this.f124b.f130b) {
                this.f124b = null;
                break;
            }
        }
        d dVar4 = this.f124b;
        if (dVar4 == null) {
            Log.i("UtilsSDK", "NO SDK restore");
            return;
        }
        dVar4.d++;
        Log.i("UtilsSDK", this.f124b.f129a + " will restore --- startSerialNumber:" + this.f124b.f127a + "   crashCount:" + this.f124b.crashCount);
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m60a(d dVar, SDKMessageCallback sDKMessageCallback) {
        d a2;
        if (!this.d) {
            Log.i("UtilsSDK", "NO Crash Defend Service");
            return false;
        } else if (dVar == null || sDKMessageCallback == null || TextUtils.isEmpty(dVar.f131b) || TextUtils.isEmpty(dVar.f129a) || (a2 = a(dVar, sDKMessageCallback)) == null) {
            return false;
        } else {
            boolean m59a = m59a(a2);
            if (a2.crashCount == a2.a) {
                a(a2.f129a, a2.f131b, a2.crashCount, a2.a);
            }
            a2.crashCount++;
            f.a(this.a, this.f122a, this.f125b);
            if (m59a) {
                a(a2);
                Log.i("UtilsSDK", "START:" + a2.f129a + " --- limit:" + a2.a + "  count:" + (a2.crashCount - 1) + "  restore:" + a2.d + "  startSerialNumber:" + a2.f127a + "  restoreSerialNumber:" + a2.f132c + "  registerSerialNumber:" + a2.f130b);
            } else {
                sDKMessageCallback.crashDefendMessage(a2.a, a2.crashCount - 1);
                Log.i("UtilsSDK", "STOP:" + a2.f129a + " --- limit:" + a2.a + "  count:" + (a2.crashCount - 1) + "  restore:" + a2.d + "  startSerialNumber:" + a2.f127a + "  restoreSerialNumber:" + a2.f132c + "  registerSerialNumber:" + a2.f130b);
            }
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        boolean z;
        synchronized (this.f125b) {
            Iterator<d> it = this.f125b.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = true;
                    break;
                }
                d next = it.next();
                if (next != null && next.f) {
                    z = false;
                    break;
                }
            }
        }
        if (z) {
            synchronized (this) {
                ExecutorService executorService = this.f126b;
                if (executorService != null && !executorService.isShutdown()) {
                    this.f126b.shutdown();
                    Log.i("UtilsSDK", "Thread Pool is close");
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0087 A[Catch: all -> 0x00ab, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x000a, B:8:0x0010, B:9:0x0016, B:11:0x001c, B:13:0x0024, B:15:0x002e, B:17:0x0038, B:18:0x0053, B:20:0x0057, B:21:0x0077, B:23:0x0079, B:26:0x0087, B:27:0x00a9), top: B:32:0x0003 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.alibaba.sdk.android.utils.crashdefend.d a(com.alibaba.sdk.android.utils.crashdefend.d r9, com.alibaba.sdk.android.utils.crashdefend.SDKMessageCallback r10) {
        /*
            r8 = this;
            java.util.List<com.alibaba.sdk.android.utils.crashdefend.d> r0 = r8.f125b
            monitor-enter(r0)
            java.util.List<com.alibaba.sdk.android.utils.crashdefend.d> r1 = r8.f125b     // Catch: java.lang.Throwable -> Lab
            r2 = 0
            r3 = 1
            r4 = 0
            if (r1 == 0) goto L84
            int r1 = r1.size()     // Catch: java.lang.Throwable -> Lab
            if (r1 <= 0) goto L84
            java.util.List<com.alibaba.sdk.android.utils.crashdefend.d> r1 = r8.f125b     // Catch: java.lang.Throwable -> Lab
            java.util.Iterator r1 = r1.iterator()     // Catch: java.lang.Throwable -> Lab
        L16:
            boolean r5 = r1.hasNext()     // Catch: java.lang.Throwable -> Lab
            if (r5 == 0) goto L84
            java.lang.Object r5 = r1.next()     // Catch: java.lang.Throwable -> Lab
            com.alibaba.sdk.android.utils.crashdefend.d r5 = (com.alibaba.sdk.android.utils.crashdefend.d) r5     // Catch: java.lang.Throwable -> Lab
            if (r5 == 0) goto L16
            java.lang.String r6 = r5.f129a     // Catch: java.lang.Throwable -> Lab
            java.lang.String r7 = r9.f129a     // Catch: java.lang.Throwable -> Lab
            boolean r6 = r6.equals(r7)     // Catch: java.lang.Throwable -> Lab
            if (r6 == 0) goto L16
            java.lang.String r1 = r5.f131b     // Catch: java.lang.Throwable -> Lab
            java.lang.String r6 = r9.f131b     // Catch: java.lang.Throwable -> Lab
            boolean r1 = r1.equals(r6)     // Catch: java.lang.Throwable -> Lab
            if (r1 != 0) goto L53
            java.lang.String r1 = r9.f131b     // Catch: java.lang.Throwable -> Lab
            r5.f131b = r1     // Catch: java.lang.Throwable -> Lab
            int r1 = r9.a     // Catch: java.lang.Throwable -> Lab
            r5.a = r1     // Catch: java.lang.Throwable -> Lab
            int r1 = r9.b     // Catch: java.lang.Throwable -> Lab
            r5.b = r1     // Catch: java.lang.Throwable -> Lab
            r5.c = r2     // Catch: java.lang.Throwable -> Lab
            r8.e()     // Catch: java.lang.Throwable -> Lab
            com.alibaba.sdk.android.utils.crashdefend.a r1 = r8.f123b     // Catch: java.lang.Throwable -> Lab
            com.alibaba.sdk.android.utils.crashdefend.c$b r6 = new com.alibaba.sdk.android.utils.crashdefend.c$b     // Catch: java.lang.Throwable -> Lab
            r6.<init>()     // Catch: java.lang.Throwable -> Lab
            r1.a(r5, r6)     // Catch: java.lang.Throwable -> Lab
        L53:
            boolean r1 = r5.e     // Catch: java.lang.Throwable -> Lab
            if (r1 == 0) goto L79
            java.lang.String r10 = "UtilsSDK"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lab
            r1.<init>()     // Catch: java.lang.Throwable -> Lab
            java.lang.String r2 = "SDK "
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch: java.lang.Throwable -> Lab
            java.lang.String r9 = r9.f129a     // Catch: java.lang.Throwable -> Lab
            java.lang.StringBuilder r9 = r1.append(r9)     // Catch: java.lang.Throwable -> Lab
            java.lang.String r1 = " has been registered"
            java.lang.StringBuilder r9 = r9.append(r1)     // Catch: java.lang.Throwable -> Lab
            java.lang.String r9 = r9.toString()     // Catch: java.lang.Throwable -> Lab
            android.util.Log.i(r10, r9)     // Catch: java.lang.Throwable -> Lab
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lab
            return r4
        L79:
            r5.e = r3     // Catch: java.lang.Throwable -> Lab
            r5.f128a = r10     // Catch: java.lang.Throwable -> Lab
            com.alibaba.sdk.android.utils.crashdefend.b r1 = r8.f122a     // Catch: java.lang.Throwable -> Lab
            long r6 = r1.a     // Catch: java.lang.Throwable -> Lab
            r5.f130b = r6     // Catch: java.lang.Throwable -> Lab
            goto L85
        L84:
            r5 = r4
        L85:
            if (r5 != 0) goto La9
            java.lang.Object r9 = r9.clone()     // Catch: java.lang.Throwable -> Lab
            r5 = r9
            com.alibaba.sdk.android.utils.crashdefend.d r5 = (com.alibaba.sdk.android.utils.crashdefend.d) r5     // Catch: java.lang.Throwable -> Lab
            r5.e = r3     // Catch: java.lang.Throwable -> Lab
            r5.f128a = r10     // Catch: java.lang.Throwable -> Lab
            r5.crashCount = r2     // Catch: java.lang.Throwable -> Lab
            com.alibaba.sdk.android.utils.crashdefend.b r9 = r8.f122a     // Catch: java.lang.Throwable -> Lab
            long r9 = r9.a     // Catch: java.lang.Throwable -> Lab
            r5.f130b = r9     // Catch: java.lang.Throwable -> Lab
            java.util.List<com.alibaba.sdk.android.utils.crashdefend.d> r9 = r8.f125b     // Catch: java.lang.Throwable -> Lab
            r9.add(r5)     // Catch: java.lang.Throwable -> Lab
            com.alibaba.sdk.android.utils.crashdefend.a r9 = r8.f123b     // Catch: java.lang.Throwable -> Lab
            com.alibaba.sdk.android.utils.crashdefend.c$b r10 = new com.alibaba.sdk.android.utils.crashdefend.c$b     // Catch: java.lang.Throwable -> Lab
            r10.<init>()     // Catch: java.lang.Throwable -> Lab
            r9.a(r5, r10)     // Catch: java.lang.Throwable -> Lab
        La9:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lab
            return r5
        Lab:
            r9 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lab
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.utils.crashdefend.c.a(com.alibaba.sdk.android.utils.crashdefend.d, com.alibaba.sdk.android.utils.crashdefend.SDKMessageCallback):com.alibaba.sdk.android.utils.crashdefend.d");
    }

    private void e() {
        for (d dVar : this.f125b) {
            dVar.crashCount = 0;
            dVar.d = 0;
            dVar.f132c = 0L;
        }
    }

    /* renamed from: a  reason: collision with other method in class */
    private boolean m59a(d dVar) {
        if (dVar.c == 1) {
            dVar.crashCount = 0;
            dVar.f127a = dVar.f130b;
            return true;
        } else if (dVar.c == 2) {
            dVar.crashCount = dVar.a;
            return false;
        } else if (dVar.crashCount < dVar.a) {
            dVar.f127a = dVar.f130b;
            return true;
        } else {
            d dVar2 = this.f124b;
            if (dVar2 == null || !dVar2.f129a.equals(dVar.f129a)) {
                return false;
            }
            dVar.crashCount = dVar.a - 1;
            dVar.f127a = dVar.f130b;
            dVar.f132c = dVar.f130b;
            return true;
        }
    }

    private void a(d dVar) {
        if (dVar == null) {
            return;
        }
        if (dVar.f128a != null) {
            dVar.f128a.crashDefendMessage(dVar.a, dVar.crashCount - 1);
        }
        e eVar = new e();
        eVar.a = dVar;
        eVar.e = dVar.b;
        eVar.a.f = true;
        a(eVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(d dVar) {
        if (dVar == null) {
            return;
        }
        if (dVar.d > 0) {
            b(dVar.f129a, dVar.f131b, dVar.d, 5);
        }
        dVar.crashCount = 0;
        dVar.d = 0;
        dVar.f = false;
    }

    private void a(e eVar) {
        if (eVar == null || eVar.a == null) {
            return;
        }
        synchronized (this) {
            if (this.f126b == null) {
                this.f126b = Executors.newCachedThreadPool();
            }
            if (this.f126b.isShutdown()) {
                this.f126b = Executors.newCachedThreadPool();
                Log.i("UtilsSDK", "Thread Pool is restart");
            }
            if (!this.f126b.isShutdown()) {
                try {
                    this.f126b.execute(new a(eVar));
                } catch (RejectedExecutionException e) {
                    Log.e("UtilsSDK", "add task fail:", e);
                }
            }
        }
    }

    private void a(String str, String str2, int i, int i2) {
        if (this.f121a == null) {
            return;
        }
        HashMap hashMap = new HashMap();
        hashMap.putAll(this.e);
        hashMap.put("crashSdkId", str);
        hashMap.put("crashSdkVer", str2);
        hashMap.put("curCrashCount", String.valueOf(i));
        hashMap.put("crashThreshold", String.valueOf(i2));
        this.f121a.sendCustomHit("utils_biz_crash", 0L, hashMap);
    }

    private void b(String str, String str2, int i, int i2) {
        if (this.f121a == null) {
            return;
        }
        HashMap hashMap = new HashMap();
        hashMap.putAll(this.e);
        hashMap.put("crashSdkId", str);
        hashMap.put("crashSdkVer", str2);
        hashMap.put("recoverCount", String.valueOf(i));
        hashMap.put("recoverThreshold", String.valueOf(i2));
        this.f121a.sendCustomHit("utils_biz_recover", 0L, hashMap);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: CrashDefendManager.java */
    /* loaded from: classes.dex */
    public class a implements Runnable {
        private e a;

        public a(e eVar) {
            this.a = eVar;
        }

        @Override // java.lang.Runnable
        public void run() {
            do {
                try {
                    Thread.sleep(1000L);
                    e eVar = this.a;
                    eVar.e--;
                } catch (InterruptedException unused) {
                } catch (Throwable th) {
                    c.this.d();
                    throw th;
                }
            } while (this.a.e > 0);
            if (this.a.e <= 0) {
                c.this.b(this.a.a);
                f.a(c.this.a, c.this.f122a, (List<d>) c.this.f125b);
            }
            c.this.d();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: CrashDefendManager.java */
    /* loaded from: classes.dex */
    public class b implements a.InterfaceC0013a {
        private b() {
        }

        @Override // com.alibaba.sdk.android.utils.crashdefend.a.InterfaceC0013a
        public void update() {
            f.a(c.this.a, c.this.f122a, (List<d>) c.this.f125b);
        }
    }
}
