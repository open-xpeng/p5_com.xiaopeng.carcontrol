package com.alibaba.sdk.android.oss.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

/* loaded from: classes.dex */
public class OSSSharedPreferences {
    private static OSSSharedPreferences sInstance;
    private SharedPreferences mSp;

    private OSSSharedPreferences(Context context) {
        this.mSp = context.getSharedPreferences("oss_android_sdk_sp", 0);
    }

    public static OSSSharedPreferences instance(Context context) {
        if (sInstance == null) {
            synchronized (OSSSharedPreferences.class) {
                if (sInstance == null) {
                    sInstance = new OSSSharedPreferences(context);
                }
            }
        }
        return sInstance;
    }

    public void setStringValue(String str, String str2) {
        SharedPreferences.Editor edit = this.mSp.edit();
        edit.putString(str, str2);
        edit.commit();
    }

    public String getStringValue(String str) {
        return this.mSp.getString(str, "");
    }

    public void removeKey(String str) {
        SharedPreferences.Editor edit = this.mSp.edit();
        edit.remove(str);
        edit.commit();
    }

    public boolean contains(String str) {
        return this.mSp.contains(str);
    }
}
