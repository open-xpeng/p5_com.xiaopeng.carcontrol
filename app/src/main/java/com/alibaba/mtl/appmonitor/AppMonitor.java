package com.alibaba.mtl.appmonitor;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.mtl.appmonitor.IMonitor;
import com.alibaba.mtl.appmonitor.a.f;
import com.alibaba.mtl.appmonitor.model.DimensionSet;
import com.alibaba.mtl.appmonitor.model.DimensionValueSet;
import com.alibaba.mtl.appmonitor.model.MeasureSet;
import com.alibaba.mtl.appmonitor.model.MeasureValueSet;
import com.alibaba.mtl.log.e.i;
import com.xiaopeng.lib.bughunter.anr.UILooperObserver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;

/* loaded from: classes.dex */
public final class AppMonitor {
    public static final String TAG = "AppMonitor";
    private static Application a;

    /* renamed from: a  reason: collision with other field name */
    private static ServiceConnection f14a;

    /* renamed from: a  reason: collision with other field name */
    private static HandlerThread f15a;

    /* renamed from: a  reason: collision with other field name */
    protected static c f17a;

    /* renamed from: a  reason: collision with other field name */
    protected static IMonitor f18a;

    /* renamed from: a  reason: collision with other field name */
    private static volatile boolean f21a;
    private static Map<String, APTrack> b;
    private static boolean c;
    private static String f;
    private static String g;
    private static String h;
    private static String i;
    private static Context mContext;

    /* renamed from: a  reason: collision with other field name */
    private static Object f19a = new Object();

    /* renamed from: a  reason: collision with other field name */
    private static List<a> f20a = Collections.synchronizedList(new ArrayList());

    /* renamed from: b  reason: collision with other field name */
    private static boolean f22b = false;

    /* renamed from: a  reason: collision with other field name */
    private static b f16a = b.Local;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public enum b {
        Local,
        Service
    }

    static {
        try {
            System.loadLibrary("ut_c_api");
            Log.i(TAG, "load ut_c_api.so success");
        } catch (Throwable unused) {
            Log.w(TAG, "load ut_c_api.so failed");
        }
        f14a = new ServiceConnection() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.5
            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                if (b.Service == AppMonitor.f16a) {
                    AppMonitor.f18a = IMonitor.Stub.asInterface(iBinder);
                    if (AppMonitor.f22b && AppMonitor.f17a != null) {
                        AppMonitor.f17a.postAtFrontOfQueue(new Runnable() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.5.1
                            @Override // java.lang.Runnable
                            public void run() {
                                AppMonitor.restart();
                            }
                        });
                    }
                }
                synchronized (AppMonitor.f19a) {
                    AppMonitor.f19a.notifyAll();
                }
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                i.a(AppMonitor.TAG, "[onServiceDisconnected]");
                synchronized (AppMonitor.f19a) {
                    AppMonitor.f19a.notifyAll();
                }
                boolean unused2 = AppMonitor.f22b = true;
            }
        };
        b = Collections.synchronizedMap(new HashMap());
    }

    public static synchronized void init(Application application) {
        synchronized (AppMonitor.class) {
            i.a(TAG, "[init]");
            try {
                if (!f21a) {
                    a = application;
                    if (application != null) {
                        mContext = application.getApplicationContext();
                    }
                    HandlerThread handlerThread = new HandlerThread("AppMonitor_Client");
                    f15a = handlerThread;
                    handlerThread.start();
                    f17a = new c(f15a.getLooper());
                    if (f16a == b.Local) {
                        m10a();
                    } else if (m12a()) {
                        f17a.a(true);
                    }
                    m9a().run();
                    f21a = true;
                }
            } catch (Throwable unused) {
            }
        }
    }

    @Deprecated
    public static synchronized void destroy() {
        synchronized (AppMonitor.class) {
            if (checkInit()) {
                f17a.a(new Runnable() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            AppMonitor.f18a.destroy();
                        } catch (RemoteException e) {
                            AppMonitor.a(e);
                        }
                    }
                });
            }
        }
    }

    @Deprecated
    public static synchronized void triggerUpload() {
        synchronized (AppMonitor.class) {
            if (f21a) {
                f17a.a(new Runnable() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.10
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            AppMonitor.f18a.triggerUpload();
                        } catch (RemoteException e) {
                            AppMonitor.a(e);
                        }
                    }
                });
            }
        }
    }

    public static void setStatisticsInterval(final int i2) {
        if (checkInit()) {
            f17a.a(new Runnable() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.11
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        AppMonitor.f18a.setStatisticsInterval1(i2);
                    } catch (RemoteException e) {
                        AppMonitor.a(e);
                    }
                }
            });
        }
    }

    public static void setSampling(final int i2) {
        if (checkInit()) {
            f17a.a(new Runnable() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.12
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        AppMonitor.f18a.setSampling(i2);
                    } catch (RemoteException e) {
                        AppMonitor.a(e);
                    }
                }
            });
        }
    }

    public static void enableLog(final boolean z) {
        if (checkInit()) {
            f17a.a(new Runnable() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.13
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        AppMonitor.f18a.enableLog(z);
                    } catch (RemoteException e) {
                        AppMonitor.a(e);
                    }
                }
            });
        }
    }

    public static void register(final String str, final String str2, final MeasureSet measureSet) {
        if (checkInit()) {
            f17a.a(new Runnable() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.14
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        AppMonitor.f18a.register1(str, str2, measureSet);
                    } catch (RemoteException e) {
                        AppMonitor.a(e);
                    }
                }
            });
            m11a(str, str2, measureSet, (DimensionSet) null, false);
        }
    }

    public static void register(final String str, final String str2, final MeasureSet measureSet, final boolean z) {
        if (checkInit()) {
            f17a.a(new Runnable() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.15
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        AppMonitor.f18a.register2(str, str2, measureSet, z);
                    } catch (RemoteException e) {
                        AppMonitor.a(e);
                    }
                }
            });
            m11a(str, str2, measureSet, (DimensionSet) null, z);
        }
    }

    public static void register(final String str, final String str2, final MeasureSet measureSet, final DimensionSet dimensionSet) {
        if (checkInit()) {
            f17a.a(new Runnable() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.16
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        i.a(AppMonitor.TAG, "[register]:", AppMonitor.f18a);
                        AppMonitor.f18a.register3(str, str2, measureSet, dimensionSet);
                    } catch (RemoteException e) {
                        AppMonitor.a(e);
                    }
                }
            });
            m11a(str, str2, measureSet, dimensionSet, false);
        }
    }

    public static void register(String str, String str2, MeasureSet measureSet, DimensionSet dimensionSet, boolean z) {
        if (checkInit()) {
            registerInternal(str, str2, measureSet, dimensionSet, z, false);
        }
    }

    public static void register(String str, String str2, String[] strArr, String[] strArr2, boolean z) {
        Object[] objArr = new Object[9];
        objArr[0] = "[register]";
        objArr[1] = "module:";
        objArr[2] = str;
        objArr[3] = "measures:";
        objArr[4] = strArr == null ? "null" : new JSONArray((Collection) Arrays.asList(strArr)).toString();
        objArr[5] = "dimensions:";
        objArr[6] = strArr2 != null ? new JSONArray((Collection) Arrays.asList(strArr2)).toString() : "null";
        objArr[7] = "isCommitDetail:";
        objArr[8] = Boolean.valueOf(z);
        i.a(TAG, objArr);
        if (strArr != null) {
            MeasureSet create = MeasureSet.create();
            for (String str3 : strArr) {
                create.addMeasure(str3);
            }
            DimensionSet dimensionSet = null;
            if (strArr2 != null) {
                dimensionSet = DimensionSet.create();
                for (String str4 : strArr2) {
                    dimensionSet.addDimension(str4);
                }
            }
            register(str, str2, create, dimensionSet, z);
            return;
        }
        i.a(TAG, "register failed:no mearsure");
    }

    public static void updateMeasure(final String str, final String str2, final String str3, final double d, final double d2, final double d3) {
        i.a(TAG, "[updateMeasure]");
        if (checkInit()) {
            f17a.post(new Runnable() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.17
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        AppMonitor.f18a.updateMeasure(str, str2, str3, d, d2, d3);
                    } catch (RemoteException e) {
                        AppMonitor.a(e);
                    }
                }
            });
        }
    }

    public static void registerInternal(String str, String str2, MeasureSet measureSet, DimensionSet dimensionSet, boolean z, boolean z2) {
        if (checkInit()) {
            i.a(TAG, "[registerInternal] : module:", str, "monitorPoint:", str2, "measures:", measureSet, "dimensions:", dimensionSet, "isCommitDetail:", Boolean.valueOf(z), "isInternal:", Boolean.valueOf(z2));
            if (!z2) {
                m11a(str, str2, measureSet, dimensionSet, z);
            }
            f17a.a(a(str, str2, measureSet, dimensionSet, z));
        }
    }

    /* renamed from: a  reason: collision with other method in class */
    private static void m11a(String str, String str2, MeasureSet measureSet, DimensionSet dimensionSet, boolean z) {
        try {
            a aVar = new a();
            aVar.o = str;
            aVar.p = str2;
            aVar.f37b = measureSet;
            aVar.b = dimensionSet;
            aVar.g = z;
            f20a.add(aVar);
        } catch (Throwable unused) {
        }
    }

    /* loaded from: classes.dex */
    public static class Alarm {
        public static void setStatisticsInterval(final int i) {
            if (AppMonitor.checkInit()) {
                AppMonitor.f17a.a(new Runnable() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.Alarm.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            AppMonitor.f18a.alarm_setStatisticsInterval(i);
                        } catch (RemoteException e) {
                            AppMonitor.a(e);
                        }
                    }
                });
            }
        }

        public static void setSampling(final int i) {
            if (AppMonitor.checkInit()) {
                AppMonitor.f17a.a(new Runnable() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.Alarm.2
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            AppMonitor.f18a.alarm_setSampling(i);
                        } catch (RemoteException e) {
                            AppMonitor.a(e);
                        }
                    }
                });
            }
        }

        @Deprecated
        public static boolean checkSampled(String str, String str2) {
            if (AppMonitor.f18a == null) {
                return false;
            }
            try {
                return AppMonitor.f18a.alarm_checkSampled(str, str2);
            } catch (RemoteException e) {
                AppMonitor.a(e);
                return false;
            }
        }

        public static void commitSuccess(final String str, final String str2) {
            if (AppMonitor.checkInit()) {
                AppMonitor.f17a.a(new Runnable() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.Alarm.3
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            AppMonitor.f18a.alarm_commitSuccess1(str, str2, null);
                        } catch (RemoteException e) {
                            AppMonitor.a(e);
                        }
                    }
                });
            }
        }

        public static void commitSuccess(final String str, final String str2, final String str3) {
            if (AppMonitor.checkInit()) {
                AppMonitor.f17a.a(new Runnable() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.Alarm.4
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            AppMonitor.f18a.alarm_commitSuccess2(str, str2, str3, null);
                        } catch (RemoteException e) {
                            AppMonitor.a(e);
                        }
                    }
                });
            }
        }

        public static void commitFail(final String str, final String str2, final String str3, final String str4) {
            if (AppMonitor.checkInit()) {
                AppMonitor.f17a.a(new Runnable() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.Alarm.5
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            AppMonitor.f18a.alarm_commitFail1(str, str2, str3, str4, null);
                        } catch (RemoteException e) {
                            AppMonitor.a(e);
                        }
                    }
                });
            }
        }

        public static void commitFail(final String str, final String str2, final String str3, final String str4, final String str5) {
            if (AppMonitor.checkInit()) {
                AppMonitor.f17a.a(new Runnable() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.Alarm.6
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            AppMonitor.f18a.alarm_commitFail2(str, str2, str3, str4, str5, null);
                        } catch (RemoteException e) {
                            AppMonitor.a(e);
                        }
                    }
                });
            }
        }
    }

    /* loaded from: classes.dex */
    public static class Counter {
        public static void setStatisticsInterval(final int i) {
            if (AppMonitor.checkInit()) {
                AppMonitor.f17a.a(new Runnable() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.Counter.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            AppMonitor.f18a.counter_setStatisticsInterval(i);
                        } catch (RemoteException e) {
                            AppMonitor.a(e);
                        }
                    }
                });
            }
        }

        public static void setSampling(final int i) {
            if (AppMonitor.checkInit()) {
                AppMonitor.f17a.a(new Runnable() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.Counter.2
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            AppMonitor.f18a.counter_setSampling(i);
                        } catch (RemoteException e) {
                            AppMonitor.a(e);
                        }
                    }
                });
            }
        }

        @Deprecated
        public static boolean checkSampled(String str, String str2) {
            if (AppMonitor.f18a == null) {
                return false;
            }
            try {
                return AppMonitor.f18a.counter_checkSampled(str, str2);
            } catch (RemoteException e) {
                AppMonitor.a(e);
                return false;
            }
        }

        public static void commit(final String str, final String str2, final double d) {
            if (AppMonitor.checkInit()) {
                AppMonitor.f17a.a(new Runnable() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.Counter.3
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            AppMonitor.f18a.counter_commit1(str, str2, d, null);
                        } catch (RemoteException e) {
                            AppMonitor.a(e);
                        }
                    }
                });
            }
        }

        public static void commit(final String str, final String str2, final String str3, final double d) {
            if (AppMonitor.checkInit()) {
                AppMonitor.f17a.a(new Runnable() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.Counter.4
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            AppMonitor.f18a.counter_commit2(str, str2, str3, d, null);
                        } catch (RemoteException e) {
                            AppMonitor.a(e);
                        }
                    }
                });
            }
        }
    }

    /* loaded from: classes.dex */
    public static class OffLineCounter {
        public static void setStatisticsInterval(final int i) {
            if (AppMonitor.checkInit()) {
                AppMonitor.f17a.a(new Runnable() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.OffLineCounter.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            AppMonitor.f18a.offlinecounter_setStatisticsInterval(i);
                        } catch (RemoteException e) {
                            AppMonitor.a(e);
                        }
                    }
                });
            }
        }

        public static void setSampling(final int i) {
            if (AppMonitor.checkInit()) {
                AppMonitor.f17a.a(new Runnable() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.OffLineCounter.2
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            AppMonitor.f18a.offlinecounter_setSampling(i);
                        } catch (RemoteException e) {
                            AppMonitor.a(e);
                        }
                    }
                });
            }
        }

        @Deprecated
        public static boolean checkSampled(String str, String str2) {
            if (AppMonitor.f18a == null) {
                return false;
            }
            try {
                return AppMonitor.f18a.offlinecounter_checkSampled(str, str2);
            } catch (RemoteException e) {
                AppMonitor.a(e);
                return false;
            }
        }

        public static void commit(final String str, final String str2, final double d) {
            if (AppMonitor.checkInit()) {
                AppMonitor.f17a.a(new Runnable() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.OffLineCounter.3
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            AppMonitor.f18a.offlinecounter_commit(str, str2, d);
                        } catch (RemoteException e) {
                            AppMonitor.a(e);
                        }
                    }
                });
            }
        }
    }

    /* loaded from: classes.dex */
    public static class Stat {
        public static void setStatisticsInterval(final int i) {
            if (AppMonitor.checkInit()) {
                AppMonitor.f17a.a(new Runnable() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.Stat.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            AppMonitor.f18a.stat_setStatisticsInterval(i);
                        } catch (RemoteException e) {
                            AppMonitor.a(e);
                        }
                    }
                });
            }
        }

        public static void setSampling(final int i) {
            if (AppMonitor.checkInit()) {
                AppMonitor.f17a.a(new Runnable() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.Stat.2
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            AppMonitor.f18a.stat_setSampling(i);
                        } catch (RemoteException e) {
                            AppMonitor.a(e);
                        }
                    }
                });
            }
        }

        public static boolean checkSampled(String str, String str2) {
            if (AppMonitor.f18a == null) {
                return false;
            }
            try {
                return AppMonitor.f18a.stat_checkSampled(str, str2);
            } catch (RemoteException e) {
                AppMonitor.a(e);
                return false;
            }
        }

        public static void begin(final String str, final String str2, final String str3) {
            if (AppMonitor.checkInit()) {
                AppMonitor.f17a.a(new Runnable() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.Stat.3
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            AppMonitor.f18a.stat_begin(str, str2, str3);
                        } catch (RemoteException e) {
                            AppMonitor.a(e);
                        }
                    }
                });
            }
        }

        public static void end(final String str, final String str2, final String str3) {
            if (AppMonitor.checkInit()) {
                AppMonitor.f17a.a(new Runnable() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.Stat.4
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            AppMonitor.f18a.stat_end(str, str2, str3);
                        } catch (RemoteException e) {
                            AppMonitor.a(e);
                        }
                    }
                });
            }
        }

        public static Transaction createTransaction(String str, String str2) {
            return createTransaction(str, str2, null);
        }

        public static Transaction createTransaction(String str, String str2, DimensionValueSet dimensionValueSet) {
            return new Transaction(Integer.valueOf(f.STAT.a()), str, str2, dimensionValueSet);
        }

        public static Transaction createTransaction(String str, String str2, DimensionValueSet dimensionValueSet, String str3) {
            return new Transaction(Integer.valueOf(f.STAT.a()), str, str2, dimensionValueSet, str3);
        }

        public static void commit(String str, String str2, double d) {
            commit(str, str2, (DimensionValueSet) null, d);
        }

        public static void commit(final String str, final String str2, final DimensionValueSet dimensionValueSet, final double d) {
            if (AppMonitor.checkInit()) {
                AppMonitor.f17a.a(new Runnable() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.Stat.5
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            AppMonitor.f18a.stat_commit2(str, str2, dimensionValueSet, d, null);
                        } catch (RemoteException e) {
                            AppMonitor.a(e);
                        }
                    }
                });
            }
        }

        public static void commit(String str, String str2, String[] strArr, String[] strArr2, String[] strArr3, String[] strArr4) {
            DimensionValueSet dimensionValueSet;
            i.a(AppMonitor.TAG, "[commit from jni]");
            MeasureValueSet measureValueSet = null;
            if (strArr == null || strArr2 == null || strArr.length != strArr2.length) {
                dimensionValueSet = null;
            } else {
                dimensionValueSet = DimensionValueSet.create();
                for (int i = 0; i < strArr2.length; i++) {
                    dimensionValueSet.setValue(strArr[i], strArr2[i]);
                }
            }
            if (strArr3 != null && strArr4 != null && strArr3.length == strArr4.length) {
                measureValueSet = MeasureValueSet.create();
                for (int i2 = 0; i2 < strArr4.length; i2++) {
                    double d = 0.0d;
                    if (!TextUtils.isEmpty(strArr4[i2])) {
                        try {
                            d = Double.valueOf(strArr4[i2]).doubleValue();
                        } catch (Exception unused) {
                            i.a(AppMonitor.TAG, "measure's value cannot convert to double. measurevalue:" + strArr4[i2]);
                        }
                    }
                    measureValueSet.setValue(strArr3[i2], d);
                }
            } else {
                i.a(AppMonitor.TAG, "measure is null ,or lenght not match");
            }
            commit(str, str2, dimensionValueSet, measureValueSet);
        }

        public static void commit(final String str, final String str2, final DimensionValueSet dimensionValueSet, final MeasureValueSet measureValueSet) {
            if (AppMonitor.checkInit()) {
                AppMonitor.f17a.a(new Runnable() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.Stat.6
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            AppMonitor.f18a.stat_commit3(str, str2, dimensionValueSet, measureValueSet, null);
                        } catch (RemoteException e) {
                            AppMonitor.a(e);
                        }
                    }
                });
            }
        }
    }

    public static void setStatisticsInterval(f fVar, final int i2) {
        if (checkInit()) {
            final int a2 = a(fVar);
            f17a.a(new Runnable() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.2
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        AppMonitor.f18a.setStatisticsInterval2(a2, i2);
                    } catch (RemoteException e) {
                        AppMonitor.a(e);
                    }
                }
            });
        }
    }

    private static int a(f fVar) {
        return fVar.a();
    }

    public static void setRequestAuthInfo(boolean z, String str, String str2, String str3) {
        if (checkInit()) {
            f17a.a(a(z, str, str2, str3));
            c = z;
            g = str;
            h = str2;
            i = str3;
        }
    }

    public static void setChannel(String str) {
        if (checkInit()) {
            f17a.a(a(str));
            f = str;
        }
    }

    public static void turnOnRealTimeDebug(final Map<String, String> map) {
        if (checkInit()) {
            f17a.a(new Runnable() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.3
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        AppMonitor.f18a.turnOnRealTimeDebug(map);
                    } catch (RemoteException e) {
                        AppMonitor.a(e);
                    }
                }
            });
        }
    }

    public static void turnOffRealTimeDebug() {
        if (checkInit()) {
            f17a.a(new Runnable() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.4
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        AppMonitor.f18a.turnOffRealTimeDebug();
                    } catch (RemoteException e) {
                        AppMonitor.a(e);
                    }
                }
            });
        }
    }

    /* renamed from: a  reason: collision with other method in class */
    private static boolean m12a() {
        Application application = a;
        if (application == null) {
            return false;
        }
        boolean bindService = application.getApplicationContext().bindService(new Intent(a.getApplicationContext(), AppMonitorService.class), f14a, 1);
        if (!bindService) {
            m10a();
        }
        i.a(TAG, "bindsuccess:", Boolean.valueOf(bindService));
        return bindService;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class c extends Handler {
        private boolean h;

        public c(Looper looper) {
            super(looper);
            this.h = false;
        }

        public void a(Runnable runnable) {
            if (runnable == null) {
                return;
            }
            try {
                Message obtain = Message.obtain();
                obtain.what = 1;
                obtain.obj = runnable;
                sendMessage(obtain);
            } catch (Throwable unused) {
            }
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            try {
                if (this.h) {
                    this.h = false;
                    synchronized (AppMonitor.f19a) {
                        try {
                            AppMonitor.f19a.wait(UILooperObserver.ANR_TRIGGER_TIME);
                        } catch (InterruptedException unused) {
                            AppMonitor.m10a();
                        }
                    }
                }
                if (message.obj != null && (message.obj instanceof Runnable)) {
                    ((Runnable) message.obj).run();
                }
            } catch (Throwable unused2) {
            }
            super.handleMessage(message);
        }

        public void a(boolean z) {
            this.h = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(Exception exc) {
        i.a(TAG, "", exc);
        if (exc instanceof DeadObjectException) {
            restart();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static synchronized void restart() {
        synchronized (AppMonitor.class) {
            i.a(TAG, "[restart]");
            try {
                if (f22b) {
                    f22b = false;
                    m10a();
                    m9a().run();
                    a(c, g, h, i).run();
                    a(f).run();
                    synchronized (f20a) {
                        for (int i2 = 0; i2 < f20a.size(); i2++) {
                            a aVar = f20a.get(i2);
                            if (aVar != null) {
                                try {
                                    a(aVar.o, aVar.p, aVar.f37b, aVar.b, aVar.g).run();
                                } catch (Throwable unused) {
                                }
                            }
                        }
                    }
                }
            } catch (Throwable unused2) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a  reason: collision with other method in class */
    public static void m10a() {
        f18a = new Monitor(a);
        f16a = b.Local;
        i.a(TAG, "Start AppMonitor Service failed,AppMonitor run in local Mode...");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class a {
        public DimensionSet b;

        /* renamed from: b  reason: collision with other field name */
        public MeasureSet f37b;
        public boolean g;
        public String o;
        public String p;

        a() {
        }
    }

    public static boolean checkInit() {
        if (!f21a) {
            i.a(TAG, "Please call UTAnalytics.getInstance().setAppApplicationInstance()||.setAppApplicationInstance4sdk() before call other method");
        }
        return f21a;
    }

    /* renamed from: a  reason: collision with other method in class */
    private static Runnable m9a() {
        return new Runnable() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.6
            @Override // java.lang.Runnable
            public void run() {
                try {
                    AppMonitor.f18a.init();
                } catch (RemoteException unused) {
                    AppMonitor.m10a();
                    try {
                        AppMonitor.f18a.init();
                    } catch (Throwable unused2) {
                    }
                }
            }
        };
    }

    private static Runnable a(final boolean z, final String str, final String str2, final String str3) {
        return new Runnable() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.7
            @Override // java.lang.Runnable
            public void run() {
                try {
                    AppMonitor.f18a.setRequestAuthInfo(z, str, str2, str3);
                } catch (Throwable unused) {
                }
            }
        };
    }

    private static Runnable a(final String str) {
        return new Runnable() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.8
            @Override // java.lang.Runnable
            public void run() {
                try {
                    AppMonitor.f18a.setChannel(str);
                } catch (Throwable unused) {
                }
            }
        };
    }

    private static Runnable a(final String str, final String str2, final MeasureSet measureSet, final DimensionSet dimensionSet, final boolean z) {
        return new Runnable() { // from class: com.alibaba.mtl.appmonitor.AppMonitor.9
            @Override // java.lang.Runnable
            public void run() {
                try {
                    i.a(AppMonitor.TAG, "register stat event. module: ", str, " monitorPoint: ", str2);
                    AppMonitor.f18a.register4(str, str2, measureSet, dimensionSet, z);
                } catch (RemoteException e) {
                    AppMonitor.a(e);
                }
            }
        };
    }

    public static APTrack getTrackByAppkey(String str) {
        if (checkInit()) {
            if (!b.containsKey(str)) {
                b.put(str, new APTrack(str));
            }
            return b.get(str);
        }
        return null;
    }
}
