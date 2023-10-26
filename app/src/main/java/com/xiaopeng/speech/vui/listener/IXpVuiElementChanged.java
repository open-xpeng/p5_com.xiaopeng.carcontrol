package com.xiaopeng.speech.vui.listener;

import android.view.View;
import com.xiaopeng.vui.commons.model.VuiElement;
import java.util.List;

/* loaded from: classes2.dex */
public interface IXpVuiElementChanged {
    default void onVuiElementChanged(String str, View view) {
    }

    default void onVuiElementChanged(String str, View view, List<VuiElement> list) {
    }

    default void onVuiElementChanged(String str, View view, String[] strArr, int i) {
    }
}
