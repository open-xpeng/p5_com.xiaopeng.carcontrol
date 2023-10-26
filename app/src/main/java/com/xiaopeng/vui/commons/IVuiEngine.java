package com.xiaopeng.vui.commons;

import android.view.View;
import java.util.List;

/* loaded from: classes2.dex */
public interface IVuiEngine {
    void addVuiSceneListener(String str, View view, IVuiSceneListener iVuiSceneListener);

    void addVuiSceneListener(String str, View view, IVuiSceneListener iVuiSceneListener, IVuiElementChangedListener iVuiElementChangedListener);

    void buildScene(String str, View view);

    void buildScene(String str, View view, String str2);

    void buildScene(String str, View view, List<Integer> list, IVuiElementListener iVuiElementListener);

    void buildScene(String str, View view, List<Integer> list, IVuiElementListener iVuiElementListener, String str2);

    void buildScene(String str, View view, List<Integer> list, IVuiElementListener iVuiElementListener, String str2, IVuiSceneListener iVuiSceneListener);

    void buildScene(String str, List<View> list);

    void buildScene(String str, List<View> list, String str2);

    void buildScene(String str, List<View> list, List<Integer> list2, IVuiElementListener iVuiElementListener);

    void buildScene(String str, List<View> list, List<Integer> list2, IVuiElementListener iVuiElementListener, String str2);

    void buildScene(String str, List<View> list, List<Integer> list2, IVuiElementListener iVuiElementListener, String str2, IVuiSceneListener iVuiSceneListener);

    void enterScene(String str);

    void enterScene(String str, String str2);

    void exitScene(String str);

    void exitScene(String str, String str2);

    void exitScene(String str, String str2, IVuiSceneListener iVuiSceneListener);

    void removeVuiSceneListener(String str);

    void removeVuiSceneListener(String str, IVuiSceneListener iVuiSceneListener);

    void updateDisplayLocation(String str, int i);

    void updateElementAttribute(String str, View view);

    void updateScene(String str, View view);

    void updateScene(String str, View view, List<Integer> list, IVuiElementListener iVuiElementListener);

    void updateScene(String str, List<View> list);

    void updateScene(String str, List<View> list, List<Integer> list2, IVuiElementListener iVuiElementListener);
}
