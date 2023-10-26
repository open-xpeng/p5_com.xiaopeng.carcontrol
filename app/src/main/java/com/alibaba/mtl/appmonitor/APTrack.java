package com.alibaba.mtl.appmonitor;

import android.os.RemoteException;
import com.alibaba.mtl.appmonitor.model.DimensionValueSet;
import com.alibaba.mtl.appmonitor.model.MeasureValueSet;
import com.alibaba.mtl.log.e.i;
import com.alibaba.mtl.log.model.LogField;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class APTrack {
    private Map<String, String> a;

    /* JADX INFO: Access modifiers changed from: protected */
    public APTrack(String str) {
        HashMap hashMap = new HashMap();
        this.a = hashMap;
        hashMap.put(LogField.APPKEY.toString(), str);
    }

    public void commitSuccess(final String str, final String str2) {
        if (AppMonitor.checkInit()) {
            AppMonitor.f17a.a(new Runnable() { // from class: com.alibaba.mtl.appmonitor.APTrack.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        AppMonitor.f18a.alarm_commitSuccess1(str, str2, APTrack.this.a);
                    } catch (Throwable th) {
                        i.a("APTrack", null, th);
                    }
                }
            });
        }
    }

    public void commitSuccess(final String str, final String str2, final String str3) {
        if (AppMonitor.checkInit()) {
            AppMonitor.f17a.a(new Runnable() { // from class: com.alibaba.mtl.appmonitor.APTrack.2
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        AppMonitor.f18a.alarm_commitSuccess2(str, str2, str3, APTrack.this.a);
                    } catch (Throwable th) {
                        i.a("APTrack", null, th);
                    }
                }
            });
        }
    }

    public void commitFail(final String str, final String str2, final String str3, final String str4) {
        if (AppMonitor.checkInit()) {
            AppMonitor.f17a.a(new Runnable() { // from class: com.alibaba.mtl.appmonitor.APTrack.3
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        AppMonitor.f18a.alarm_commitFail1(str, str2, str3, str4, APTrack.this.a);
                    } catch (Throwable th) {
                        i.a("APTrack", null, th);
                    }
                }
            });
        }
    }

    public void commitFail(final String str, final String str2, final String str3, final String str4, final String str5) {
        if (AppMonitor.checkInit()) {
            AppMonitor.f17a.a(new Runnable() { // from class: com.alibaba.mtl.appmonitor.APTrack.4
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        AppMonitor.f18a.alarm_commitFail2(str, str2, str3, str4, str5, APTrack.this.a);
                    } catch (Throwable th) {
                        i.a("APTrack", null, th);
                    }
                }
            });
        }
    }

    public void commit(final String str, final String str2, final double d) {
        if (AppMonitor.checkInit()) {
            AppMonitor.f17a.a(new Runnable() { // from class: com.alibaba.mtl.appmonitor.APTrack.5
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        AppMonitor.f18a.counter_commit1(str, str2, d, APTrack.this.a);
                    } catch (RemoteException e) {
                        i.a("APTrack", null, e);
                    }
                }
            });
        }
    }

    public void commit(final String str, final String str2, final String str3, final double d) {
        if (AppMonitor.checkInit()) {
            AppMonitor.f17a.a(new Runnable() { // from class: com.alibaba.mtl.appmonitor.APTrack.6
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        AppMonitor.f18a.counter_commit2(str, str2, str3, d, APTrack.this.a);
                    } catch (Throwable th) {
                        i.a("APTrack", null, th);
                    }
                }
            });
        }
    }

    public void commit(final String str, final String str2, final DimensionValueSet dimensionValueSet, final double d) {
        if (AppMonitor.checkInit()) {
            AppMonitor.f17a.a(new Runnable() { // from class: com.alibaba.mtl.appmonitor.APTrack.7
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        AppMonitor.f18a.stat_commit2(str, str2, dimensionValueSet, d, APTrack.this.a);
                    } catch (RemoteException unused) {
                    }
                }
            });
        }
    }

    public void commit(final String str, final String str2, final DimensionValueSet dimensionValueSet, final MeasureValueSet measureValueSet) {
        if (AppMonitor.checkInit()) {
            AppMonitor.f17a.a(new Runnable() { // from class: com.alibaba.mtl.appmonitor.APTrack.8
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        AppMonitor.f18a.stat_commit3(str, str2, dimensionValueSet, measureValueSet, APTrack.this.a);
                    } catch (RemoteException unused) {
                    }
                }
            });
        }
    }
}
