package com.xiaopeng.lib.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import java.util.Map;

/* loaded from: classes2.dex */
public class SharedPreferencesUtils {
    private static final String SP_NAME = "shared_pref";
    private static final String TAG = "SharedPreferencesUtils";
    private static int initCount;
    private static volatile SharedPreferencesUtils instance;
    private SharedPreferences.Editor editor;
    private SharedPreferences sp;

    private SharedPreferencesUtils(Context context, String str, int i) {
        str = TextUtils.isEmpty(str) ? context.getPackageName() : str;
        str = TextUtils.isEmpty(str) ? SP_NAME : str;
        Log.v(TAG, "spName=" + str);
        SharedPreferences sharedPreferences = context.getSharedPreferences(str, 0);
        this.sp = sharedPreferences;
        SharedPreferences.Editor edit = sharedPreferences.edit();
        this.editor = edit;
        edit.apply();
    }

    private static void init(Context context) {
        init(context, null);
    }

    private static void init(Context context, String str) {
        if (instance == null) {
            synchronized (SharedPreferencesUtils.class) {
                if (instance == null) {
                    instance = new SharedPreferencesUtils(context.getApplicationContext(), str, 0);
                    initCount++;
                }
            }
        }
    }

    public static SharedPreferencesUtils getInstance(Context context) {
        init(context);
        return instance;
    }

    public static SharedPreferencesUtils getInstance(Context context, String str) {
        init(context, str);
        return instance;
    }

    @Deprecated
    public static int getInitCount() {
        return initCount;
    }

    public void putString(String str, String str2) {
        this.editor.putString(str, str2).commit();
    }

    public String getString(String str) {
        return getString(str, null);
    }

    public String getString(String str, String str2) {
        return this.sp.getString(str, str2);
    }

    public void putInt(String str, int i) {
        this.editor.putInt(str, i).commit();
    }

    public int getInt(String str) {
        return getInt(str, -1);
    }

    public int getInt(String str, int i) {
        return this.sp.getInt(str, i);
    }

    public void putLong(String str, long j) {
        this.editor.putLong(str, j).commit();
    }

    public long getLong(String str) {
        return getLong(str, -1L);
    }

    public long getLong(String str, long j) {
        return this.sp.getLong(str, j);
    }

    public void putFloat(String str, float f) {
        this.editor.putFloat(str, f).commit();
    }

    public float getFloat(String str) {
        return getFloat(str, -1.0f);
    }

    public float getFloat(String str, float f) {
        return this.sp.getFloat(str, f);
    }

    public void putBoolean(String str, boolean z) {
        this.editor.putBoolean(str, z).commit();
    }

    public boolean getBoolean(String str) {
        return getBoolean(str, false);
    }

    public boolean getBoolean(String str, boolean z) {
        return this.sp.getBoolean(str, z);
    }

    public Map<String, ?> getAll() {
        return this.sp.getAll();
    }

    public void remove(String str) {
        this.editor.remove(str).commit();
    }

    public boolean contains(String str) {
        return this.sp.contains(str);
    }

    public void clear() {
        this.editor.clear().commit();
    }
}
