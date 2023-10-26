package com.xiaopeng.carcontrol.quicksetting;

import java.util.List;

/* loaded from: classes2.dex */
interface IQuickSettingHandler {

    /* loaded from: classes2.dex */
    public interface IQuickSettingCallback {
        void onHandleCallback(String key, int value);

        void onHandleCallbackForString(String key, String result);
    }

    List<String> getKeyList();

    boolean handleCommand(String key, int command);

    void initData();

    void initViewModel();

    <T> boolean onSignalCallback(String key, T value);
}
