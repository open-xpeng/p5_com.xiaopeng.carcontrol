package com.xiaopeng.xui.vui;

import com.xiaopeng.vui.commons.IVuiElementListener;
import com.xiaopeng.vui.commons.IVuiEngine;
import com.xiaopeng.vui.commons.IVuiSceneListener;
import java.util.List;

/* loaded from: classes2.dex */
public interface IVuiViewScene {
    default int getVuiDisplayLocation() {
        return 0;
    }

    default void initVuiScene(String str, IVuiEngine iVuiEngine) {
    }

    default void initVuiScene(String str, IVuiEngine iVuiEngine, IVuiSceneListener iVuiSceneListener) {
    }

    default void setCustomViewIdList(List<Integer> list) {
    }

    default void setVuiElementListener(IVuiElementListener iVuiElementListener) {
    }

    default void setVuiEngine(IVuiEngine iVuiEngine) {
    }

    default void setVuiSceneId(String str) {
    }
}
