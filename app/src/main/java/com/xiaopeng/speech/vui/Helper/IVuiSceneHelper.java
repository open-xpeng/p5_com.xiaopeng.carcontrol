package com.xiaopeng.speech.vui.Helper;

import android.text.TextUtils;
import android.view.View;
import com.xiaopeng.speech.vui.VuiEngine;
import com.xiaopeng.speech.vui.constants.Foo;
import com.xiaopeng.vui.commons.IVuiSceneListener;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public interface IVuiSceneHelper extends IVuiSceneListener {
    List<View> getBuildViews();

    String getSceneId();

    default boolean isCustomBuildScene() {
        return false;
    }

    default boolean isMainScene() {
        return true;
    }

    @Override // com.xiaopeng.vui.commons.IVuiSceneListener
    default void onBuildScene() {
        if (isCustomBuildScene()) {
            return;
        }
        buildScene();
    }

    default List<String> getSubSceneList() {
        return new ArrayList();
    }

    default void buildScene() {
        if (TextUtils.isEmpty(getSceneId())) {
            return;
        }
        VuiEngine.getInstance(Foo.getContext()).buildScene(getSceneId(), getBuildViews(), getSubSceneList(), isMainScene());
    }
}
