package com.xiaopeng.xvs.tools.tranfer;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import java.util.Map;

/* loaded from: classes2.dex */
class ToolsSPUtils {
    private static volatile ToolsSPUtils sInstance;
    private SharedPreferences mPrefs;

    private ToolsSPUtils() {
    }

    public static ToolsSPUtils getInstance() {
        if (sInstance == null) {
            synchronized (ToolsSPUtils.class) {
                if (sInstance == null) {
                    sInstance = new ToolsSPUtils();
                }
            }
        }
        return sInstance;
    }

    public void init(Context context) {
        this.mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public SharedPreferences getPrefs() {
        return this.mPrefs;
    }

    public SharedPreferences.Editor getEditor() {
        return this.mPrefs.edit();
    }

    public void putString(String str, String str2) {
        getEditor().putString(str, str2).commit();
    }

    public String getString(String str) {
        return getString(str, null);
    }

    public String getString(String str, String str2) {
        return getPrefs().getString(str, str2);
    }

    public void putInt(String str, int i) {
        getEditor().putInt(str, i).commit();
    }

    public int getInt(String str) {
        return getInt(str, -1);
    }

    public int getInt(String str, int i) {
        return getPrefs().getInt(str, i);
    }

    public void putLong(String str, long j) {
        getEditor().putLong(str, j).commit();
    }

    public long getLong(String str) {
        return getLong(str, -1L);
    }

    public long getLong(String str, long j) {
        return getPrefs().getLong(str, j);
    }

    public void putFloat(String str, float f) {
        getEditor().putFloat(str, f).commit();
    }

    public float getFloat(String str) {
        return getFloat(str, -1.0f);
    }

    public float getFloat(String str, float f) {
        return getPrefs().getFloat(str, f);
    }

    public void putBoolean(String str, boolean z) {
        getEditor().putBoolean(str, z).commit();
    }

    public boolean getBoolean(String str) {
        return getBoolean(str, false);
    }

    public boolean getBoolean(String str, boolean z) {
        return getPrefs().getBoolean(str, z);
    }

    public Map<String, ?> getAll() {
        return getPrefs().getAll();
    }

    public void remove(String str) {
        getEditor().remove(str).commit();
    }

    public boolean contains(String str) {
        return getPrefs().contains(str);
    }

    public void clear() {
        getEditor().clear().commit();
    }
}
