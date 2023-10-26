package com.xiaopeng.carcontrol.model;

import android.content.SharedPreferences;
import com.xiaopeng.carcontrol.App;

/* loaded from: classes2.dex */
public class ApaExamModel {
    private static final String APA_EXAM_UPDATED = "apa_exam_updated";
    private static final String APA_SP_NAME = "apa";
    private final SharedPreferences mSp;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class LazyHolder {
        private static final ApaExamModel sInstance = new ApaExamModel();

        private LazyHolder() {
        }
    }

    public static ApaExamModel getInstance() {
        return LazyHolder.sInstance;
    }

    private ApaExamModel() {
        this.mSp = App.getInstance().getSharedPreferences(APA_SP_NAME, 0);
    }

    public boolean getApaExamResult(String uid) {
        return this.mSp.getBoolean(uid, false);
    }

    public void setApaExamResult(String uid, boolean result) {
        this.mSp.edit().putBoolean(uid, result).apply();
    }

    public void setApaExamUpdate() {
        this.mSp.edit().putBoolean(APA_EXAM_UPDATED, true).apply();
    }

    public boolean isApaExamUpdated() {
        return this.mSp.getBoolean(APA_EXAM_UPDATED, false);
    }
}
