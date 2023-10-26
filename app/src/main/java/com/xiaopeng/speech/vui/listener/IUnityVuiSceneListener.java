package com.xiaopeng.speech.vui.listener;

import com.xiaopeng.vui.commons.IVuiSceneListener;

/* loaded from: classes2.dex */
public interface IUnityVuiSceneListener extends IVuiSceneListener {
    void checkSubElementsIsInsight(String str);

    void getScrollViewState(String str, String str2);

    @Override // com.xiaopeng.vui.commons.IVuiSceneListener
    void onBuildScene();

    void onVuiEvent(String str);
}
