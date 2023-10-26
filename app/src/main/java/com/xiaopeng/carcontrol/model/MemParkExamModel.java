package com.xiaopeng.carcontrol.model;

import android.content.SharedPreferences;
import com.xiaopeng.carcontrol.App;

/* loaded from: classes2.dex */
public class MemParkExamModel {
    private static final String MEM_PARK_CHECKED = "mem_park_checked";
    private static final String MEM_PARK_SP_NAME = "mem_park";
    private final SharedPreferences mSp;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class LazyHolder {
        private static final MemParkExamModel sInstance = new MemParkExamModel();

        private LazyHolder() {
        }
    }

    public static MemParkExamModel getInstance() {
        return LazyHolder.sInstance;
    }

    private MemParkExamModel() {
        this.mSp = App.getInstance().getSharedPreferences("mem_park", 0);
    }

    public boolean isMemParkChecked() {
        return this.mSp.getBoolean(MEM_PARK_CHECKED, false);
    }

    public void setMemParkChecked() {
        this.mSp.edit().putBoolean(MEM_PARK_CHECKED, true).apply();
    }

    public boolean getMemParkExamResult(String uid) {
        return this.mSp.getBoolean(uid, false);
    }

    public void setMemParkExamResult(String uid, boolean result) {
        this.mSp.edit().putBoolean(uid, result).apply();
    }
}
