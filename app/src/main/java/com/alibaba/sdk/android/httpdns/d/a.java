package com.alibaba.sdk.android.httpdns.d;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.sdk.android.utils.AlicloudTracker;
import com.alibaba.sdk.android.utils.AlicloudTrackerManager;
import com.alibaba.sdk.android.utils.crashdefend.SDKMessageCallback;
import com.lzy.okgo.cookie.SerializableCookie;
import com.xiaopeng.carcontrol.carmanager.controller.IScenarioController;
import com.xiaopeng.lib.bughunter.anr.UILooperObserver;
import java.util.HashMap;

/* loaded from: classes.dex */
public class a {
    private static a b;
    private AlicloudTracker a;

    /* renamed from: a  reason: collision with other field name */
    private AlicloudTrackerManager f87a;
    private boolean i = true;

    private a(Context context) {
        this.a = null;
        this.f87a = null;
        if (context == null || !(context.getApplicationContext() instanceof Application)) {
            return;
        }
        AlicloudTrackerManager alicloudTrackerManager = AlicloudTrackerManager.getInstance((Application) context.getApplicationContext());
        this.f87a = alicloudTrackerManager;
        if (alicloudTrackerManager != null) {
            this.a = alicloudTrackerManager.getTracker("httpdns", "1.2.3");
        }
    }

    public static a a() {
        return b;
    }

    public static a a(Context context) {
        if (b == null) {
            synchronized (a.class) {
                if (b == null) {
                    b = new a(context);
                }
            }
        }
        return b;
    }

    public void a(String str, int i, int i2, int i3) {
        String str2;
        if (!this.i) {
            str2 = "report is disabled";
        } else if (this.a == null) {
            Log.e("HttpDns:ReportManager", "report http dns succes failed due to tacker is null");
            return;
        } else if (!TextUtils.isEmpty(str) && ((i == 0 || i == 1) && ((i2 == 0 || i2 == 1) && (i3 == 0 || i3 == 1)))) {
            HashMap hashMap = new HashMap();
            hashMap.put(SerializableCookie.HOST, str);
            hashMap.put(IScenarioController.RET_SUCCESS, String.valueOf(i));
            hashMap.put("ipv6", String.valueOf(i2));
            hashMap.put("cacheOpen", String.valueOf(i3));
            this.a.sendCustomHit("perf_getip", hashMap);
            return;
        } else {
            str2 = "report http dns success failed due to invalid params";
        }
        Log.e("HttpDns:ReportManager", str2);
    }

    public void a(String str, long j, int i) {
        String str2;
        if (!this.i) {
            str2 = "report is disabled";
        } else if (this.a == null) {
            Log.e("HttpDns:ReportManager", "report sc request time cost failed due to tacker is null");
            return;
        } else if (!TextUtils.isEmpty(str) && j > 0 && (i == 0 || i == 1)) {
            if (j > 30000) {
                j = 30000;
            }
            HashMap hashMap = new HashMap();
            hashMap.put("scAddr", str);
            hashMap.put("cost", String.valueOf(j));
            hashMap.put("ipv6", String.valueOf(i));
            this.a.sendCustomHit("perf_sc", hashMap);
            return;
        } else {
            str2 = "report sc request time cost failed due to invalid params";
        }
        Log.e("HttpDns:ReportManager", str2);
    }

    public void a(String str, String str2, String str3) {
        String str4;
        if (!this.i) {
            str4 = "report is disabled";
        } else if (this.a == null) {
            Log.e("HttpDns:ReportManager", "report sniffer failed due to tracker is null");
            return;
        } else if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3)) {
            HashMap hashMap = new HashMap();
            hashMap.put(SerializableCookie.HOST, str);
            hashMap.put("scAddr", str2);
            hashMap.put("srvAddr", str3);
            this.a.sendCustomHit("biz_sniffer", hashMap);
            return;
        } else {
            str4 = "report sniffer failed due to missing params";
        }
        Log.e("HttpDns:ReportManager", str4);
    }

    public void a(String str, String str2, String str3, int i) {
        String str4;
        if (!this.i) {
            str4 = "report is disabled";
        } else if (this.a == null) {
            Log.e("HttpDns:ReportManager", "report error sc failed due to tacker is null");
            return;
        } else if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3) && (i == 0 || i == 1)) {
            HashMap hashMap = new HashMap();
            hashMap.put("scAddr", str);
            hashMap.put("errCode", str2);
            hashMap.put("errMsg", str3);
            hashMap.put("ipv6", String.valueOf(i));
            this.a.sendCustomHit("err_sc", hashMap);
            return;
        } else {
            str4 = "report error sc failed, due to invalid params";
        }
        Log.e("HttpDns:ReportManager", str4);
    }

    public void a(String str, String str2, String str3, long j, long j2, int i) {
        String str4;
        if (!this.i) {
            str4 = "report is disabled";
        } else if (this.a == null) {
            Log.e("HttpDns:ReportManager", "report ip selection failed due to tacker is null");
            return;
        } else if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3) && i > 0) {
            if (j > UILooperObserver.ANR_TRIGGER_TIME) {
                j = 5000;
            }
            if (j2 > UILooperObserver.ANR_TRIGGER_TIME) {
                j2 = 5000;
            }
            HashMap hashMap = new HashMap();
            hashMap.put(SerializableCookie.HOST, str);
            hashMap.put("defaultIp", str2);
            hashMap.put("selectedIp", str3);
            hashMap.put("defaultIpCost", String.valueOf(j));
            hashMap.put("selectedIpCost", String.valueOf(j2));
            hashMap.put("ipCount", String.valueOf(i));
            this.a.sendCustomHit("perf_ipselection", hashMap);
            return;
        } else {
            str4 = "report ip selection failed due to invalid params";
        }
        Log.e("HttpDns:ReportManager", str4);
    }

    public boolean a(SDKMessageCallback sDKMessageCallback) {
        AlicloudTrackerManager alicloudTrackerManager = this.f87a;
        if (alicloudTrackerManager != null) {
            return alicloudTrackerManager.registerCrashDefend("httpdns", "1.2.3", 10, 5, sDKMessageCallback);
        }
        return false;
    }

    public void b(String str, int i, int i2, int i3) {
        String str2;
        if (!this.i) {
            str2 = "report is disabled";
        } else if (this.a == null) {
            Log.e("HttpDns:ReportManager", "report http dns succes failed due to tacker is null");
            return;
        } else if (!TextUtils.isEmpty(str) && ((i == 0 || i == 1) && ((i2 == 0 || i2 == 1) && (i3 == 0 || i3 == 1)))) {
            HashMap hashMap = new HashMap();
            hashMap.put(SerializableCookie.HOST, str);
            hashMap.put(IScenarioController.RET_SUCCESS, String.valueOf(i));
            hashMap.put("ipv6", String.valueOf(i2));
            hashMap.put("cacheOpen", String.valueOf(i3));
            this.a.sendCustomHit("perf_user_getip", hashMap);
            return;
        } else {
            str2 = "report http dns success failed due to invalid params";
        }
        Log.e("HttpDns:ReportManager", str2);
    }

    public void b(String str, long j, int i) {
        String str2;
        if (!this.i) {
            str2 = "report is disabled";
        } else if (this.a == null) {
            Log.e("HttpDns:ReportManager", "report http dns request time cost failed due to tacker is null");
            return;
        } else if (!TextUtils.isEmpty(str) && j > 0 && (i == 0 || i == 1)) {
            if (j > 30000) {
                j = 30000;
            }
            HashMap hashMap = new HashMap();
            hashMap.put("srvAddr", str);
            hashMap.put("cost", String.valueOf(j));
            hashMap.put("ipv6", String.valueOf(i));
            this.a.sendCustomHit("perf_srv", hashMap);
            return;
        } else {
            str2 = "report http dns request time cost failed due to invalid param";
        }
        Log.e("HttpDns:ReportManager", str2);
    }

    public void b(String str, String str2, String str3) {
        String str4;
        if (!this.i) {
            str4 = "report is disabled";
        } else if (this.a == null) {
            Log.e("HttpDns:ReportManager", "report local disable failed due to tracker is null");
            return;
        } else if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3)) {
            HashMap hashMap = new HashMap();
            hashMap.put(SerializableCookie.HOST, str);
            hashMap.put("scAddr", str2);
            hashMap.put("srvAddr", str3);
            this.a.sendCustomHit("biz_local_disable", hashMap);
            return;
        } else {
            str4 = "report local disable failed due to missing params";
        }
        Log.e("HttpDns:ReportManager", str4);
    }

    public void b(String str, String str2, String str3, int i) {
        String str4;
        if (!this.i) {
            str4 = "report is disabled";
        } else if (this.a == null) {
            Log.e("HttpDns:ReportManager", "report error http dns request failed due to tacker is null");
            return;
        } else if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3) && (i == 0 || i == 1)) {
            HashMap hashMap = new HashMap();
            hashMap.put("srvAddr", str);
            hashMap.put("errCode", str2);
            hashMap.put("errMsg", str3);
            hashMap.put("ipv6", String.valueOf(i));
            this.a.sendCustomHit("err_srv", hashMap);
            return;
        } else {
            str4 = "report error http dns request failed, due to invalid params";
        }
        Log.e("HttpDns:ReportManager", str4);
    }

    public void c(int i) {
        String str;
        if (!this.i) {
            str = "report is disabled";
        } else if (this.a == null) {
            Log.e("HttpDns:ReportManager", "report cache failed due to tracker is null");
            return;
        } else if (i == 0 || i == 1) {
            HashMap hashMap = new HashMap();
            hashMap.put("enable", String.valueOf(i));
            this.a.sendCustomHit("biz_cache", hashMap);
            return;
        } else {
            str = "report cache failed, due to invalid param enable, enable can only be 0 or 1";
        }
        Log.e("HttpDns:ReportManager", str);
    }

    public void d(int i) {
        String str;
        if (!this.i) {
            str = "report is disabled";
        } else if (this.a == null) {
            Log.e("HttpDns:ReportManager", "report set expired ip enabled failed due to tracker is null");
            return;
        } else if (i == 0 || i == 1) {
            HashMap hashMap = new HashMap();
            hashMap.put("enable", String.valueOf(i));
            this.a.sendCustomHit("biz_expired_ip", hashMap);
            return;
        } else {
            str = "report set expired ip enabled failed, due to invalid param enable, enable can only be 0 or 1";
        }
        Log.e("HttpDns:ReportManager", str);
    }

    public void d(boolean z) {
        synchronized (a.class) {
            this.i = z;
        }
    }

    public void i() {
        if (!this.i) {
            Log.e("HttpDns:ReportManager", "report is disabled");
            return;
        }
        AlicloudTracker alicloudTracker = this.a;
        if (alicloudTracker != null) {
            alicloudTracker.sendCustomHit("biz_active", null);
        } else {
            Log.e("HttpDns:ReportManager", "report sdk start failed due to tracker is null");
        }
    }

    public void k(String str) {
        String str2;
        if (!this.i) {
            str2 = "report is disabled";
        } else if (this.a == null) {
            Log.e("HttpDns:ReportManager", "report uncaught exception failed due to tacker is null");
            return;
        } else if (!TextUtils.isEmpty(str)) {
            HashMap hashMap = new HashMap();
            hashMap.put("exception", str);
            this.a.sendCustomHit("err_uncaught_exception", hashMap);
            return;
        } else {
            str2 = "report uncaught exception failed due to exception msg is null";
        }
        Log.e("HttpDns:ReportManager", str2);
    }

    public void setAccountId(String str) {
        AlicloudTracker alicloudTracker = this.a;
        if (alicloudTracker != null) {
            alicloudTracker.setGlobalProperty("accountId", str);
        } else {
            Log.e("HttpDns:ReportManager", "tracker null, set global properties failed");
        }
    }
}
