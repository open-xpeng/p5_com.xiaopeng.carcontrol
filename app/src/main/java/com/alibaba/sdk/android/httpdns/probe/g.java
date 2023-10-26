package com.alibaba.sdk.android.httpdns.probe;

import com.alibaba.sdk.android.httpdns.i;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/* loaded from: classes.dex */
class g implements Runnable {
    private ConcurrentHashMap<String, Long> a;

    /* renamed from: a  reason: collision with other field name */
    private CountDownLatch f106a;
    private String k;
    private int port;

    public g(String str, int i, CountDownLatch countDownLatch, ConcurrentHashMap<String, Long> concurrentHashMap) {
        this.f106a = null;
        this.k = str;
        this.port = i;
        this.f106a = countDownLatch;
        this.a = concurrentHashMap;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0083 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0084  */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v4, types: [int] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private long a(java.lang.String r8, int r9) {
        /*
            r7 = this;
            java.lang.String r0 = "socket close failed:"
            long r1 = java.lang.System.currentTimeMillis()
            r3 = 2147483647(0x7fffffff, double:1.060997895E-314)
            r5 = 0
            java.net.Socket r6 = new java.net.Socket     // Catch: java.lang.Throwable -> L42 java.io.IOException -> L44
            r6.<init>()     // Catch: java.lang.Throwable -> L42 java.io.IOException -> L44
            java.net.InetSocketAddress r5 = new java.net.InetSocketAddress     // Catch: java.lang.Throwable -> L3c java.io.IOException -> L3f
            r5.<init>(r8, r9)     // Catch: java.lang.Throwable -> L3c java.io.IOException -> L3f
            r8 = 5000(0x1388, float:7.006E-42)
            r6.connect(r5, r8)     // Catch: java.lang.Throwable -> L3c java.io.IOException -> L3f
            long r8 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> L3c java.io.IOException -> L3f
            r6.close()     // Catch: java.io.IOException -> L22
            goto L7f
        L22:
            r5 = move-exception
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.StringBuilder r0 = r6.append(r0)
            java.lang.String r5 = r5.toString()
            java.lang.StringBuilder r0 = r0.append(r5)
            java.lang.String r0 = r0.toString()
            com.alibaba.sdk.android.httpdns.i.f(r0)
            goto L7f
        L3c:
            r8 = move-exception
            r5 = r6
            goto L86
        L3f:
            r8 = move-exception
            r5 = r6
            goto L45
        L42:
            r8 = move-exception
            goto L86
        L44:
            r8 = move-exception
        L45:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L42
            r9.<init>()     // Catch: java.lang.Throwable -> L42
            java.lang.String r6 = "connect failed:"
            java.lang.StringBuilder r9 = r9.append(r6)     // Catch: java.lang.Throwable -> L42
            java.lang.String r8 = r8.toString()     // Catch: java.lang.Throwable -> L42
            java.lang.StringBuilder r8 = r9.append(r8)     // Catch: java.lang.Throwable -> L42
            java.lang.String r8 = r8.toString()     // Catch: java.lang.Throwable -> L42
            com.alibaba.sdk.android.httpdns.i.f(r8)     // Catch: java.lang.Throwable -> L42
            if (r5 == 0) goto L7e
            r5.close()     // Catch: java.io.IOException -> L65
            goto L7e
        L65:
            r8 = move-exception
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.StringBuilder r9 = r9.append(r0)
            java.lang.String r8 = r8.toString()
            java.lang.StringBuilder r8 = r9.append(r8)
            java.lang.String r8 = r8.toString()
            com.alibaba.sdk.android.httpdns.i.f(r8)
        L7e:
            r8 = r3
        L7f:
            int r0 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1))
            if (r0 != 0) goto L84
            return r3
        L84:
            long r8 = r8 - r1
            return r8
        L86:
            if (r5 == 0) goto La5
            r5.close()     // Catch: java.io.IOException -> L8c
            goto La5
        L8c:
            r9 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.StringBuilder r0 = r1.append(r0)
            java.lang.String r9 = r9.toString()
            java.lang.StringBuilder r9 = r0.append(r9)
            java.lang.String r9 = r9.toString()
            com.alibaba.sdk.android.httpdns.i.f(r9)
        La5:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.httpdns.probe.g.a(java.lang.String, int):long");
    }

    private boolean a(int i) {
        return i >= 1 && i <= 65535;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.k == null || !a(this.port)) {
            i.f("invalid params, give up");
        } else {
            long a = a(this.k, this.port);
            i.d("connect cost for ip:" + this.k + " is " + a);
            ConcurrentHashMap<String, Long> concurrentHashMap = this.a;
            if (concurrentHashMap != null) {
                concurrentHashMap.put(this.k, Long.valueOf(a));
            }
        }
        CountDownLatch countDownLatch = this.f106a;
        if (countDownLatch != null) {
            countDownLatch.countDown();
        }
    }
}
