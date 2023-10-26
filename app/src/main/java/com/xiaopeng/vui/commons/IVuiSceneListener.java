package com.xiaopeng.vui.commons;

import android.view.View;
import com.xiaopeng.vui.commons.model.VuiEvent;

/* loaded from: classes2.dex */
public interface IVuiSceneListener {
    default void onBuildScene() {
    }

    default boolean onInterceptVuiEvent(View view, VuiEvent vuiEvent) {
        return false;
    }

    default void onVuiEvent(View view, VuiEvent vuiEvent) {
    }

    default void onVuiEvent(VuiEvent vuiEvent) {
    }

    default void onVuiEventExecutioned() {
    }

    default void onVuiStateChanged() {
    }
}
