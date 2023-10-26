package com.xiaopeng.carcontrol.model;

import android.content.SharedPreferences;
import com.xiaopeng.carcontrol.App;

/* loaded from: classes2.dex */
public class SuperLccExamModel {
    private static final String LCC_L_EXAM_UPDATED = "lcc_l_exam_updated";
    private static final String LCC_L_SP_NAME = "lcc_l";
    private final SharedPreferences mSp;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class LazyHolder {
        private static final SuperLccExamModel sInstance = new SuperLccExamModel();

        private LazyHolder() {
        }
    }

    public static SuperLccExamModel getInstance() {
        return LazyHolder.sInstance;
    }

    private SuperLccExamModel() {
        this.mSp = App.getInstance().getSharedPreferences(LCC_L_SP_NAME, 0);
    }

    public boolean getSuperLccExamResult(String uid) {
        return this.mSp.getBoolean(uid, false);
    }

    public void setSuperLccExamResult(String uid, boolean result) {
        this.mSp.edit().putBoolean(uid, result).apply();
    }

    public void setSuperLccExamUpdate() {
        this.mSp.edit().putBoolean(LCC_L_EXAM_UPDATED, true).apply();
    }

    public boolean isSuperLccExamUpdated() {
        return this.mSp.getBoolean(LCC_L_EXAM_UPDATED, false);
    }
}
