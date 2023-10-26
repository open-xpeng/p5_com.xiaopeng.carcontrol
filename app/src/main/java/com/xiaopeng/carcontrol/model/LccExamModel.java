package com.xiaopeng.carcontrol.model;

import android.content.SharedPreferences;
import com.xiaopeng.carcontrol.App;

/* loaded from: classes2.dex */
public class LccExamModel {
    private static final String LCC_EXAM_UPDATED = "lcc_exam_updated";
    private static final String LCC_SP_NAME = "lcc";
    private final SharedPreferences mSp;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class LazyHolder {
        private static final LccExamModel sInstance = new LccExamModel();

        private LazyHolder() {
        }
    }

    public static LccExamModel getInstance() {
        return LazyHolder.sInstance;
    }

    private LccExamModel() {
        this.mSp = App.getInstance().getSharedPreferences("lcc", 0);
    }

    public boolean getLccExamResult(String uid) {
        return this.mSp.getBoolean(uid, false);
    }

    public void setLccExamResult(String uid, boolean result) {
        this.mSp.edit().putBoolean(uid, result).apply();
    }

    public void setLccExamUpdate() {
        this.mSp.edit().putBoolean(LCC_EXAM_UPDATED, true).apply();
    }

    public boolean isLccExamUpdated() {
        return this.mSp.getBoolean(LCC_EXAM_UPDATED, false);
    }
}
