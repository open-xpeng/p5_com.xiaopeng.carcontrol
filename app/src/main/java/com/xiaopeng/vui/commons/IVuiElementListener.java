package com.xiaopeng.vui.commons;

import android.view.View;
import com.xiaopeng.vui.commons.model.VuiElement;
import com.xiaopeng.vui.commons.model.VuiEvent;

/* loaded from: classes2.dex */
public interface IVuiElementListener extends IVuiElement {
    default VuiElement onBuildVuiElement(String str, IVuiElementBuilder iVuiElementBuilder) {
        return null;
    }

    default boolean onVuiElementEvent(View view, VuiEvent vuiEvent) {
        return false;
    }
}
