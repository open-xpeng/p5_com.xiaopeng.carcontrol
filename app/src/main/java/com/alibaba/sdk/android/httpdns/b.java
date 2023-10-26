package com.alibaba.sdk.android.httpdns;

import android.content.Context;
import android.content.SharedPreferences;

/* loaded from: classes.dex */
public class b {
    private static SharedPreferences a = null;

    /* renamed from: a  reason: collision with other field name */
    private static boolean f79a = true;

    public static void a(boolean z) {
        f79a = z;
        SharedPreferences sharedPreferences = a;
        if (sharedPreferences != null) {
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putBoolean("key_enable", z);
            edit.apply();
        }
        i.d("[EnableManager] enable: " + z);
    }

    public static boolean a() {
        return f79a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void init(Context context) {
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("httpdns_config_enable", 0);
            a = sharedPreferences;
            if (sharedPreferences != null) {
                f79a = sharedPreferences.getBoolean("key_enable", true);
            }
            i.d("[EnableManager] init enable: " + f79a);
        }
    }
}
