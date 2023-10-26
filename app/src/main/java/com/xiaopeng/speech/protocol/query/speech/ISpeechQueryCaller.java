package com.xiaopeng.speech.protocol.query.speech;

import com.xiaopeng.speech.IQueryCaller;

/* loaded from: classes2.dex */
public interface ISpeechQueryCaller extends IQueryCaller {
    default boolean appIsInstalled(String str) {
        return false;
    }

    String getCarPlatForm();

    int getCurrentMode();

    default int getCurrentTtsEngine() {
        return 1;
    }

    default int getFirstSpeechState() {
        return -1;
    }

    int getSoundLocation();

    default int getVuiSceneSwitchStatus() {
        return -1;
    }

    boolean isAccountLogin();

    boolean isAppForeground(String str);

    boolean isEnableGlobalWakeup();

    default boolean isUserExpressionOpened() {
        return false;
    }
}
