package com.xiaopeng.speech.protocol.node.app;

import com.xiaopeng.speech.INodeListener;

/* loaded from: classes2.dex */
public interface AppListener extends INodeListener {
    void onAiHomepageClose();

    void onAiHomepageOpen();

    void onAppActive();

    void onAppLauncherExit();

    void onAppOpen(String str, String str2);

    void onAppPageOpen(String str);

    void onAppStoreOpen(String str, String str2);

    void onDebugOpen();

    void onGuiSpeechDebugPage();

    void onKeyeventBack();

    void onOpenYoukuSearch(String str);

    void onQuery(String str, String str2);

    void onStartPage(String str, String str2, String str3);

    void onTriggerIntent(String str, String str2, String str3, String str4);
}
