package com.xiaopeng.speech.vui.event;

import android.view.View;
import android.widget.Checkable;
import android.widget.CompoundButton;
import com.google.gson.internal.LinkedTreeMap;
import com.xiaopeng.speech.vui.utils.LogUtils;
import com.xiaopeng.vui.commons.IVuiElement;
import com.xiaopeng.vui.commons.model.VuiElement;
import java.util.Map;

/* loaded from: classes2.dex */
public class SetCheckEvent extends BaseEvent {
    @Override // com.xiaopeng.speech.vui.event.IVuiEvent
    public <T extends View> T run(T t, VuiElement vuiElement) {
        Boolean bool;
        if (t == null) {
            return null;
        }
        if (vuiElement != null && vuiElement.getResultActions() != null && !vuiElement.getResultActions().isEmpty() && (bool = (Boolean) getSetCheck(vuiElement)) != null) {
            if (t instanceof CompoundButton) {
                CompoundButton compoundButton = (CompoundButton) t;
                if ((!bool.booleanValue()) == compoundButton.isChecked()) {
                    LogUtils.d("SetCheckEvent run on CompoundButton");
                    boolean z = t instanceof IVuiElement;
                    if (z) {
                        ((IVuiElement) t).setPerformVuiAction(true);
                    }
                    compoundButton.setChecked(bool.booleanValue());
                    if (z) {
                        ((IVuiElement) t).setPerformVuiAction(false);
                    }
                }
            } else if (t instanceof Checkable) {
                if ((!bool.booleanValue()) == ((Checkable) t).isChecked()) {
                    LogUtils.d("SetCheckEvent run on Checkable view");
                    boolean z2 = t instanceof IVuiElement;
                    if (z2) {
                        ((IVuiElement) t).setPerformVuiAction(true);
                    }
                    t.performClick();
                    if (z2) {
                        ((IVuiElement) t).setPerformVuiAction(false);
                    }
                }
            } else if ((!bool.booleanValue()) == t.isSelected()) {
                LogUtils.d("SetCheckEvent run on setSelected view");
                boolean z3 = t instanceof IVuiElement;
                if (z3) {
                    ((IVuiElement) t).setPerformVuiAction(true);
                }
                t.performClick();
                if (z3) {
                    ((IVuiElement) t).setPerformVuiAction(false);
                }
            }
        }
        return t;
    }

    private <T> T getSetCheck(VuiElement vuiElement) {
        Map map;
        if (vuiElement != null && vuiElement.getResultActions() != null && !vuiElement.getResultActions().isEmpty()) {
            Object obj = (String) vuiElement.getResultActions().get(0);
            if ((vuiElement.getValues() instanceof LinkedTreeMap) && (map = (Map) vuiElement.getValues()) != null) {
                if (map.get(obj) instanceof LinkedTreeMap) {
                    Map map2 = (Map) map.get(obj);
                    if (map2 == null || map2.get("value") == null) {
                        return null;
                    }
                    return (T) map2.get("value");
                } else if (map.get(map) != null) {
                    return (T) map.get("value");
                }
            }
        }
        return null;
    }
}
