package com.xiaopeng.vui.commons;

import android.view.View;
import com.xiaopeng.vui.commons.model.VuiElement;
import java.util.List;

/* loaded from: classes2.dex */
public interface IVuiElementBuilder {
    default List<VuiElement> build(int i, View view) {
        return null;
    }

    default List<VuiElement> build(int i, List<View> list) {
        return null;
    }
}
