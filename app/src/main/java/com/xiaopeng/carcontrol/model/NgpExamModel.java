package com.xiaopeng.carcontrol.model;

import android.content.SharedPreferences;
import com.xiaopeng.carcontrol.App;

/* loaded from: classes2.dex */
public class NgpExamModel {
    private static final String NGP_SP_NAME = "ngp";
    private final SharedPreferences mSp;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class LazyHolder {
        private static final NgpExamModel sInstance = new NgpExamModel();

        private LazyHolder() {
        }
    }

    public static NgpExamModel getInstance() {
        return LazyHolder.sInstance;
    }

    private NgpExamModel() {
        this.mSp = App.getInstance().getSharedPreferences("ngp", 0);
    }

    public boolean getNgpExamResult(String uid) {
        return this.mSp.getBoolean(uid, false);
    }

    public void setNgpExamResult(String uid, boolean result) {
        this.mSp.edit().putBoolean(uid, result).apply();
    }
}
