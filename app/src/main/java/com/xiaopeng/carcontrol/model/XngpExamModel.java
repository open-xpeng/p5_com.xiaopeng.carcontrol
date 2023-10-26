package com.xiaopeng.carcontrol.model;

import android.content.SharedPreferences;
import com.xiaopeng.carcontrol.App;

/* loaded from: classes2.dex */
public class XngpExamModel {
    private static final String XNGP_EXAM_UPDATED = "xngp_exam_updated";
    private static final String XNGP_SP_NAME = "xngp";
    private final SharedPreferences mSp;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class LazyHolder {
        private static final XngpExamModel sInstance = new XngpExamModel();

        private LazyHolder() {
        }
    }

    public static XngpExamModel getInstance() {
        return LazyHolder.sInstance;
    }

    private XngpExamModel() {
        this.mSp = App.getInstance().getSharedPreferences(XNGP_SP_NAME, 0);
    }

    public boolean getXngpExamResult(String uid) {
        return this.mSp.getBoolean(uid, false);
    }

    public void setXngpExamResult(String uid, boolean result) {
        this.mSp.edit().putBoolean(uid, result).apply();
    }

    public void setXngpExamUpdate() {
        this.mSp.edit().putBoolean(XNGP_EXAM_UPDATED, true).apply();
    }

    public boolean isXngpExamUpdated() {
        return this.mSp.getBoolean(XNGP_EXAM_UPDATED, false);
    }
}
