package com.xiaopeng.carcontrol.model;

import android.content.SharedPreferences;
import com.xiaopeng.carcontrol.App;

/* loaded from: classes2.dex */
public class CngpExamModel {
    private static final String CNGP_SP_NAME = "cngp";
    private final SharedPreferences mSp;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class LazyHolder {
        private static final CngpExamModel sInstance = new CngpExamModel();

        private LazyHolder() {
        }
    }

    public static CngpExamModel getInstance() {
        return LazyHolder.sInstance;
    }

    private CngpExamModel() {
        this.mSp = App.getInstance().getSharedPreferences("cngp", 0);
    }

    public boolean getCngpExamResult(String uid) {
        return this.mSp.getBoolean(uid, false);
    }

    public void setCngpExamResult(String uid, boolean result) {
        this.mSp.edit().putBoolean(uid, result).apply();
    }
}
