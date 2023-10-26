package com.xiaopeng.carcontrol.view.speech;

import com.google.gson.JsonObject;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import com.xiaopeng.vui.commons.VuiElementType;
import com.xiaopeng.vui.commons.model.VuiElement;

/* loaded from: classes2.dex */
public class SliderFictitiousElementsConstructor extends FictitiousElementsConstructor<Integer> {
    private int interval;
    private int maxValue;
    private int minValue;

    @Override // com.xiaopeng.carcontrol.view.speech.IFictitiousElementConstructor
    public VuiElement generateElement(String id, Integer currState, String[] values, String... vuiLable) {
        return null;
    }

    public SliderFictitiousElementsConstructor(int minValue, int maxValue, float interval) {
        this.maxValue = maxValue;
        this.minValue = minValue;
        this.interval = (int) interval;
    }

    @Override // com.xiaopeng.carcontrol.view.speech.IFictitiousElementConstructor
    public VuiElement generateElement(String id, Integer currState, String... vuiLable) {
        if (vuiLable == null || vuiLable.length == 0) {
            return null;
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("minValue", Integer.valueOf(this.minValue));
        jsonObject.addProperty("maxValue", Integer.valueOf(this.maxValue));
        jsonObject.addProperty(VuiConstants.PROPS_INTERVAL, Integer.valueOf(this.interval));
        jsonObject.addProperty(VuiConstants.PROPS_UNIT, "Gear");
        return new VuiElement.Builder().label(vuiLable[0]).id(id).action(VuiActions.SETVALUE).type(VuiElementType.XSLIDER.getType()).props(jsonObject).value(generateElementValueJSON(VuiActions.SETVALUE, currState)).build();
    }
}
