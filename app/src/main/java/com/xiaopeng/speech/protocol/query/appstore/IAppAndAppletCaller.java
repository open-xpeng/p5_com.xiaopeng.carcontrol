package com.xiaopeng.speech.protocol.query.appstore;

import com.xiaopeng.speech.IQueryCaller;

/* loaded from: classes2.dex */
public interface IAppAndAppletCaller extends IQueryCaller {
    int getAppCloseStatus(String str);

    int getAppOpenStatus(String str);

    int getAppletCloseStatus(String str);

    int getAppletOpenStatus(String str);

    int getCurrentCloseStatus(String str);

    boolean hasDialogCloseByHand();

    int onCloseAppStoreMainPage();

    int onCloseAppletMainPage();

    int onCloseAppshopPage();

    int onOpenAppStoreMainPage();

    int onOpenAppletMainPage();

    int onOpenAppshopPage();
}
