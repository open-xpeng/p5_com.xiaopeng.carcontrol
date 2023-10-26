package com.xiaopeng.speech.common.util;

import android.app.ActivityThread;
import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* loaded from: classes2.dex */
public class SPConfig {
    private SharedPreferences mPreferences;
    private static Map<String, SPConfig> mConfigMaps = new HashMap();
    private static String NAME = null;

    public static synchronized SPConfig getInstance() {
        SPConfig sPConfig;
        synchronized (SPConfig.class) {
            sPConfig = getInstance(ActivityThread.currentApplication());
        }
        return sPConfig;
    }

    public static synchronized SPConfig getInstance(Context context) {
        SPConfig config;
        synchronized (SPConfig.class) {
            if (NAME == null) {
                NAME = ActivityThread.currentActivityThread().getProcessName() + ".configuration";
            }
            config = getConfig(context, NAME);
        }
        return config;
    }

    private static SPConfig getConfig(Context context, String str) {
        SPConfig sPConfig = mConfigMaps.containsKey(str) ? mConfigMaps.get(str) : null;
        if (sPConfig == null) {
            SPConfig sPConfig2 = new SPConfig(context, str);
            mConfigMaps.put(str, sPConfig2);
            return sPConfig2;
        }
        return sPConfig;
    }

    public static synchronized SPConfig getInstance(Context context, String str) {
        SPConfig config;
        synchronized (SPConfig.class) {
            if (str == null) {
                str = "";
            }
            config = getConfig(context, str);
        }
        return config;
    }

    private SPConfig(Context context, String str) {
        this.mPreferences = context.getSharedPreferences(str + ".configuration", 0);
    }

    public void setOnChangeListenner(SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        if (onSharedPreferenceChangeListener != null) {
            this.mPreferences.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
        }
    }

    public synchronized boolean setString(String str, String str2) {
        return doApply(this.mPreferences.edit().putString(str, str2));
    }

    public synchronized boolean setStringSync(String str, String str2) {
        return doCommit(this.mPreferences.edit().putString(str, str2));
    }

    public synchronized boolean setInt(String str, int i) {
        return doApply(this.mPreferences.edit().putInt(str, i));
    }

    public synchronized boolean setIntSync(String str, int i) {
        return doCommit(this.mPreferences.edit().putInt(str, i));
    }

    public synchronized boolean setLong(String str, long j) {
        return doApply(this.mPreferences.edit().putLong(str, j));
    }

    public synchronized boolean setLongSync(String str, long j) {
        return doCommit(this.mPreferences.edit().putLong(str, j));
    }

    public synchronized boolean setBoolean(String str, boolean z) {
        return doApply(this.mPreferences.edit().putBoolean(str, z));
    }

    public synchronized boolean setBooleanSync(String str, boolean z) {
        return doCommit(this.mPreferences.edit().putBoolean(str, z));
    }

    public synchronized boolean setFloat(String str, float f) {
        return doApply(this.mPreferences.edit().putFloat(str, f));
    }

    public synchronized boolean setFloatSync(String str, float f) {
        return doCommit(this.mPreferences.edit().putFloat(str, f));
    }

    public synchronized boolean setStringSet(String str, Set<String> set) {
        return doApply(this.mPreferences.edit().putStringSet(str, set));
    }

    public synchronized boolean setStringSetSync(String str, Set<String> set) {
        return doCommit(this.mPreferences.edit().putStringSet(str, set));
    }

    public synchronized String getString(String str, String str2) {
        return this.mPreferences.getString(str, str2);
    }

    public synchronized int getInt(String str, int i) {
        return this.mPreferences.getInt(str, i);
    }

    public synchronized long getLong(String str, long j) {
        return this.mPreferences.getLong(str, j);
    }

    public synchronized boolean getBoolean(String str, boolean z) {
        return this.mPreferences.getBoolean(str, z);
    }

    public synchronized float getFloat(String str, float f) {
        return this.mPreferences.getFloat(str, f);
    }

    public synchronized Set<String> getStringSet(String str, Set<String> set) {
        return this.mPreferences.getStringSet(str, set);
    }

    public synchronized void clearAllSync() {
        doCommit(this.mPreferences.edit().clear());
    }

    public synchronized void clearAllAsync() {
        this.mPreferences.edit().clear().apply();
    }

    private boolean doCommit(SharedPreferences.Editor editor) {
        try {
            return editor.commit();
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    private boolean doApply(SharedPreferences.Editor editor) {
        try {
            editor.apply();
            return true;
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }
}
