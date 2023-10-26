package com.xiaopeng.carcontrol.model;

import android.content.SharedPreferences;
import com.xiaopeng.carcontrol.App;

/* loaded from: classes2.dex */
public class ChildModeModel {
    private static final String CHILD_MODE_OPENED = "child_mode_opened";
    private static final String CHILD_MODE_SP_NAME = "child_mode";
    private final SharedPreferences mSp;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class LazyHolder {
        private static final ChildModeModel sInstance = new ChildModeModel();

        private LazyHolder() {
        }
    }

    public static ChildModeModel getInstance() {
        return LazyHolder.sInstance;
    }

    private ChildModeModel() {
        this.mSp = App.getInstance().getSharedPreferences(CHILD_MODE_SP_NAME, 0);
    }

    public void setChildModeOpened() {
        this.mSp.edit().putBoolean(CHILD_MODE_OPENED, true).apply();
    }

    public boolean isChildModeOpened() {
        return this.mSp.getBoolean(CHILD_MODE_OPENED, false);
    }
}
