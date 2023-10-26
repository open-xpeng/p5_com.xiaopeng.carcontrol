package com.xiaopeng.datalog.helper;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Map;

/* loaded from: classes2.dex */
public class SharedPreferenceHelper {
    private static final String LOCAL_DATA_FILE = "local_data";
    private static volatile SharedPreferenceHelper mInstance;
    private SharedPreferences.Editor editor;
    private SharedPreferences sp;

    private SharedPreferenceHelper(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(LOCAL_DATA_FILE, 0);
        this.sp = sharedPreferences;
        SharedPreferences.Editor edit = sharedPreferences.edit();
        this.editor = edit;
        edit.apply();
    }

    public static SharedPreferenceHelper getInstance(Context context) {
        if (mInstance == null) {
            synchronized (SharedPreferenceHelper.class) {
                if (mInstance == null) {
                    mInstance = new SharedPreferenceHelper(context);
                }
            }
        }
        return mInstance;
    }

    public void putString(String str, String str2) {
        this.editor.putString(str, str2).apply();
    }

    public String getString(String str) {
        return getString(str, null);
    }

    public String getString(String str, String str2) {
        return this.sp.getString(str, str2);
    }

    public void putInt(String str, int i) {
        this.editor.putInt(str, i).apply();
    }

    public int getInt(String str) {
        return getInt(str, -1);
    }

    public int getInt(String str, int i) {
        return this.sp.getInt(str, i);
    }

    public void putLong(String str, long j) {
        this.editor.putLong(str, j).apply();
    }

    public long getLong(String str) {
        return getLong(str, -1L);
    }

    public long getLong(String str, long j) {
        return this.sp.getLong(str, j);
    }

    public void putFloat(String str, float f) {
        this.editor.putFloat(str, f).apply();
    }

    public float getFloat(String str) {
        return getFloat(str, -1.0f);
    }

    public float getFloat(String str, float f) {
        return this.sp.getFloat(str, f);
    }

    public void putBoolean(String str, boolean z) {
        this.editor.putBoolean(str, z).apply();
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
        this.editor.remove(str).apply();
    }

    public boolean contains(String str) {
        return this.sp.contains(str);
    }

    public void clear() {
        this.editor.clear().apply();
    }
}
