package com.xiaopeng.carcontrol.view.speech;

import com.xiaopeng.vui.commons.VuiElementType;
import com.xiaopeng.vui.commons.model.VuiElement;

/* loaded from: classes2.dex */
public class SwitchFictitiousElementsConstructor extends FictitiousElementsConstructor<Boolean> {
    @Override // com.xiaopeng.carcontrol.view.speech.IFictitiousElementConstructor
    public VuiElement generateElement(String id, Boolean currState, String[] values, String... vuiLable) {
        return null;
    }

    @Override // com.xiaopeng.carcontrol.view.speech.IFictitiousElementConstructor
    public VuiElement generateElement(String id, Boolean currState, String... vuiLable) {
        if (vuiLable == null || vuiLable.length != 1) {
            return null;
        }
        return new VuiElement.Builder().type(VuiElementType.SWITCH.getType()).id(id).value(generateElementValueJSON(VuiActions.SETCHECK, currState)).action(VuiActions.SETCHECK).label(vuiLable[0]).build();
    }
}
