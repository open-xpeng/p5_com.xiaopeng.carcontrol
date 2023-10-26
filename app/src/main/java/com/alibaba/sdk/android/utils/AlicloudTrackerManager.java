package com.alibaba.sdk.android.utils;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.sdk.android.utils.crashdefend.SDKMessageCallback;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class AlicloudTrackerManager {
    private static AlicloudTrackerManager a;

    /* renamed from: a  reason: collision with other field name */
    private c f116a = new c();

    /* renamed from: a  reason: collision with other field name */
    private com.alibaba.sdk.android.utils.crashdefend.c f117a;
    private Map<String, AlicloudTracker> c;

    private AlicloudTrackerManager(Application application) {
        this.f117a = null;
        HashMap hashMap = new HashMap(4);
        hashMap.put("kVersion", "1.1.3");
        hashMap.put(VuiConstants.SCENE_PACKAGE_NAME, application.getPackageName());
        this.f116a.a(application, hashMap);
        this.c = new HashMap();
        this.f117a = com.alibaba.sdk.android.utils.crashdefend.c.a(application, this.f116a);
    }

    public static synchronized AlicloudTrackerManager getInstance(Application application) {
        synchronized (AlicloudTrackerManager.class) {
            if (application == null) {
                return null;
            }
            if (a == null) {
                a = new AlicloudTrackerManager(application);
            }
            return a;
        }
    }

    public AlicloudTracker getTracker(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            Log.e("AlicloudTrackerManager", "sdkId or sdkVersion is null");
            return null;
        }
        String str3 = str + str2;
        if (this.c.containsKey(str3)) {
            return this.c.get(str3);
        }
        AlicloudTracker alicloudTracker = new AlicloudTracker(this.f116a, str, str2);
        this.c.put(str3, alicloudTracker);
        return alicloudTracker;
    }

    public boolean registerCrashDefend(String str, String str2, int i, int i2, SDKMessageCallback sDKMessageCallback) {
        if (this.f117a != null) {
            com.alibaba.sdk.android.utils.crashdefend.d dVar = new com.alibaba.sdk.android.utils.crashdefend.d();
            dVar.f129a = str;
            dVar.f131b = str2;
            dVar.a = i;
            dVar.b = i2;
            return this.f117a.m60a(dVar, sDKMessageCallback);
        }
        return false;
    }

    public void unregisterCrashDefend(String str, String str2) {
        this.f117a.d(str, str2);
    }
}
