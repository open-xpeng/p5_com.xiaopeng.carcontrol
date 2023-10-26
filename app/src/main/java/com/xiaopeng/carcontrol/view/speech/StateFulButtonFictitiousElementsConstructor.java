package com.xiaopeng.carcontrol.view.speech;

import com.google.gson.JsonObject;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import com.xiaopeng.vui.commons.VuiElementType;
import com.xiaopeng.vui.commons.model.VuiElement;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public class StateFulButtonFictitiousElementsConstructor extends FictitiousElementsConstructor<Float> {
    private String action;
    private float interval;
    private String mainVuiLabel;
    private int maxValue;
    private int minValue;

    public StateFulButtonFictitiousElementsConstructor(String mainLabel, String action, int minValue, int maxValue, float interval) {
        this.mainVuiLabel = mainLabel;
        this.action = action;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.interval = interval;
    }

    @Override // com.xiaopeng.carcontrol.view.speech.IFictitiousElementConstructor
    public VuiElement generateElement(String id, Float currState, String... vuiLable) {
        return getVuiElement(id, currState, vuiLable, null);
    }

    private VuiElement getVuiElement(String id, Float currState, String[] vuiLable, String[] values) {
        if (vuiLable == null || vuiLable.length == 0) {
            return null;
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("minValue", Integer.valueOf(this.minValue));
        jsonObject.addProperty("maxValue", Integer.valueOf(this.maxValue));
        jsonObject.addProperty(VuiConstants.PROPS_INTERVAL, Float.valueOf(this.interval));
        VuiElement build = new VuiElement.Builder().id(id).type(VuiElementType.STATEFULBUTTON.getType()).label(this.mainVuiLabel).action(this.action).value(generateElementValueJSON(this.action, currState)).props(jsonObject).build();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < vuiLable.length; i++) {
            String str = vuiLable[i];
            if (values == null || values.length != vuiLable.length) {
                arrayList.add(new VuiElement.Builder().type("State").id(id + "_" + (i + 1)).value(str).label(str).build());
            } else {
                arrayList.add(new VuiElement.Builder().type("State").id(id + "_" + (i + 1)).value(values[i]).label(str).build());
            }
        }
        build.setElements(arrayList);
        return build;
    }

    @Override // com.xiaopeng.carcontrol.view.speech.IFictitiousElementConstructor
    public VuiElement generateElement(String id, Float currState, String[] values, String... vuiLable) {
        return getVuiElement(id, currState, vuiLable, values);
    }
}
