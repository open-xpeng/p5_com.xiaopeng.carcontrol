package com.xiaopeng.carcontrol.model;

import android.content.SharedPreferences;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.GlobalConstant;

/* loaded from: classes2.dex */
public class DebugFuncModel {
    private static final String DEBUG_SP_NAME = "debug_sp";
    private static final boolean ENABLE_UNITY = true;
    private static final String KEY_DEBUG_TAB = "show_debug_tab";
    private static final String KEY_ENABLE_UNITY = "enable_unity";
    private static final boolean SHOW_DEBUG_TAB = false;
    private final SharedPreferences mDebugSp;

    /* loaded from: classes2.dex */
    private static class LazyHolder {
        private static final DebugFuncModel sInstance = new DebugFuncModel();

        private LazyHolder() {
        }
    }

    public static DebugFuncModel getInstance() {
        return LazyHolder.sInstance;
    }

    private DebugFuncModel() {
        this.mDebugSp = App.getInstance().getSharedPreferences(DEBUG_SP_NAME, 0);
    }

    public boolean isDebugTabEnabled() {
        return this.mDebugSp.getBoolean(KEY_DEBUG_TAB, false);
    }

    public void setDebugTabEnable(boolean enable) {
        this.mDebugSp.edit().putBoolean(KEY_DEBUG_TAB, enable).apply();
    }

    public boolean isUnityEnabled() {
        return this.mDebugSp.getBoolean(KEY_ENABLE_UNITY, true);
    }

    public void setUnityEnable(boolean enable) {
        this.mDebugSp.edit().putBoolean(KEY_ENABLE_UNITY, enable).apply();
    }

    public boolean isAvasExternalSwEnabled() {
        return this.mDebugSp.getBoolean(GlobalConstant.PREFS.PREF_AVAS_EXTERNAL_SW_BOOL, true);
    }

    public void setAvasExternalSw(boolean enable) {
        this.mDebugSp.edit().putBoolean(GlobalConstant.PREFS.PREF_AVAS_EXTERNAL_SW_BOOL, enable).apply();
    }

    public String getAirAutoProtectSound() {
        return this.mDebugSp.getString(GlobalConstant.PREFS.PREF_AIR_PROTECT_SOUND_STRING, "/system/media/audio/xiaopeng/cdu/wav/CDU_air_protect_3.wav");
    }

    public void setAirAutoProtectSound(String path) {
        this.mDebugSp.edit().putString(GlobalConstant.PREFS.PREF_AIR_PROTECT_SOUND_STRING, path).apply();
    }

    public int getLluAwakeMode() {
        return this.mDebugSp.getInt(GlobalConstant.PREFS.PREF_LLU_AWAKE_MODE_INT, 1);
    }

    public void setLluAwakeMode(int mode) {
        this.mDebugSp.edit().putInt(GlobalConstant.PREFS.PREF_LLU_AWAKE_MODE_INT, mode).apply();
    }

    public int getLluSleepMode() {
        return this.mDebugSp.getInt(GlobalConstant.PREFS.PREF_LLU_SLEEP_MODE_INT, 1);
    }

    public void setLluSleepMode(int mode) {
        this.mDebugSp.edit().putInt(GlobalConstant.PREFS.PREF_LLU_SLEEP_MODE_INT, mode).apply();
    }

    public boolean isLluLockUnlockEleEnabled() {
        return this.mDebugSp.getBoolean(GlobalConstant.PREFS.PREF_LLU_LOCK_UNLOCK_ELE_BOOL, false);
    }

    public void setLluLockUnlockEleSw(boolean enable) {
        this.mDebugSp.edit().putBoolean(GlobalConstant.PREFS.PREF_LLU_LOCK_UNLOCK_ELE_BOOL, enable).apply();
    }
}
