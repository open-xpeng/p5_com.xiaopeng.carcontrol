package com.xiaopeng.speech.vui.event;

import android.view.View;
import com.xiaopeng.speech.vui.utils.LogUtils;
import com.xiaopeng.vui.commons.IVuiElement;
import com.xiaopeng.vui.commons.model.VuiElement;

/* loaded from: classes2.dex */
public class ClickEvent extends BaseEvent {
    @Override // com.xiaopeng.speech.vui.event.IVuiEvent
    public <T extends View> T run(T t, VuiElement vuiElement) {
        if (t != null) {
            boolean z = t instanceof IVuiElement;
            if (z) {
                ((IVuiElement) t).setPerformVuiAction(true);
            }
            boolean performClick = t.performClick();
            LogUtils.i("ClickEvent run :" + performClick);
            if (!performClick && z) {
                ((IVuiElement) t).setPerformVuiAction(false);
            }
        }
        return t;
    }
}
