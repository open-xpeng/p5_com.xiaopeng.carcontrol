package com.alibaba.sdk.android.httpdns.probe;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
class a implements Runnable {
    private f a;

    /* renamed from: a  reason: collision with other field name */
    private ConcurrentHashMap<String, Long> f102a = new ConcurrentHashMap<>();

    /* renamed from: a  reason: collision with other field name */
    private String[] f103a;
    private String h;
    private long i;
    private int port;

    public a(long j, String str, String[] strArr, int i, f fVar) {
        this.a = null;
        this.i = j;
        this.h = str;
        this.f103a = strArr;
        this.port = i;
        this.a = fVar;
    }

    private c a(String[] strArr) {
        String[] strArr2 = this.f103a;
        if (strArr2 == null || strArr2.length == 0 || strArr == null || strArr.length == 0) {
            return null;
        }
        String str = strArr2[0];
        String str2 = strArr[0];
        return new c(this.h, strArr, str, str2, this.f102a.containsKey(str) ? this.f102a.get(str).longValue() : 2147483647L, this.f102a.containsKey(str2) ? this.f102a.get(str2).longValue() : 2147483647L);
    }

    private String[] a(ConcurrentHashMap<String, Long> concurrentHashMap) {
        if (concurrentHashMap == null) {
            return null;
        }
        int size = concurrentHashMap.size();
        String[] strArr = new String[size];
        int i = 0;
        for (String str : concurrentHashMap.keySet()) {
            strArr[i] = new String(str);
            i++;
        }
        for (int i2 = 0; i2 < size - 1; i2++) {
            int i3 = 0;
            while (i3 < (size - i2) - 1) {
                int i4 = i3 + 1;
                if (concurrentHashMap.get(strArr[i3]).longValue() > concurrentHashMap.get(strArr[i4]).longValue()) {
                    String str2 = strArr[i3];
                    strArr[i3] = strArr[i4];
                    strArr[i4] = str2;
                }
                i3 = i4;
            }
        }
        return strArr;
    }

    @Override // java.lang.Runnable
    public void run() {
        String[] a;
        String[] strArr = this.f103a;
        if (strArr == null || strArr.length == 0) {
            return;
        }
        CountDownLatch countDownLatch = new CountDownLatch(this.f103a.length);
        for (int i = 0; i < this.f103a.length; i++) {
            com.alibaba.sdk.android.httpdns.c.a().execute(new g(this.f103a[i], this.port, countDownLatch, this.f102a));
        }
        try {
            countDownLatch.await(10000L, TimeUnit.MILLISECONDS);
            if (this.a == null || (a = a(this.f102a)) == null || a.length == 0) {
                return;
            }
            this.a.a(this.i, a(a));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
