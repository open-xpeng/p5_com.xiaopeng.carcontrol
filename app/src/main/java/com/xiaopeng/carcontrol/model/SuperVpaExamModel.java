package com.xiaopeng.carcontrol.model;

import android.content.SharedPreferences;
import com.xiaopeng.carcontrol.App;

/* loaded from: classes2.dex */
public class SuperVpaExamModel {
    private static final String SUPER_VPA_SP_NAME = "super_vpa";
    private static final String SUPER_VPA_UPDATED = "super_vpa_updated";
    private final SharedPreferences mSp;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class LazyHolder {
        private static final SuperVpaExamModel sInstance = new SuperVpaExamModel();

        private LazyHolder() {
        }
    }

    public static SuperVpaExamModel getInstance() {
        return LazyHolder.sInstance;
    }

    private SuperVpaExamModel() {
        this.mSp = App.getInstance().getSharedPreferences(SUPER_VPA_SP_NAME, 0);
    }

    public boolean isSuperVpaUpdated() {
        return this.mSp.getBoolean(SUPER_VPA_UPDATED, false);
    }

    public void setSuperVpaUpdated() {
        this.mSp.edit().putBoolean(SUPER_VPA_UPDATED, true).apply();
    }

    public boolean getSuperVpaExamResult(String uid) {
        return this.mSp.getBoolean(uid, false);
    }

    public void setSuperVpaExamResult(String uid, boolean result) {
        this.mSp.edit().putBoolean(uid, result).apply();
    }
}
