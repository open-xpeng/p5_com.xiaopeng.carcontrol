package com.xiaopeng.speech.vui.event;

import android.view.View;
import com.xiaopeng.vui.commons.model.VuiElement;

/* loaded from: classes2.dex */
public interface IVuiEvent {
    <T extends View> T run(T t, VuiElement vuiElement);
}
