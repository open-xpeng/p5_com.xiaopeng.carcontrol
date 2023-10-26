package com.xiaopeng.carcontrol.view.speech;

import android.content.Context;
import com.google.gson.JsonObject;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import com.xiaopeng.vui.commons.model.VuiElement;

/* loaded from: classes2.dex */
public class ValuePickerFictitiousElementsConstructor {
    private IFictitiousElementConstructor constructor;

    public ValuePickerFictitiousElementsConstructor(Context context, boolean isDrive) {
        String string;
        if (context != null) {
            if (isDrive) {
                string = context.getResources().getString(R.string.hvac_driver_temp_vui_label);
            } else {
                string = context.getResources().getString(R.string.hvac_psn_temp_vui_label);
            }
            this.constructor = new StateFulButtonFictitiousElementsConstructor(string, VuiActions.SETVALUE, 18, 32, 0.5f);
        }
    }

    public VuiElement getValuePickerElement(String id, float currState, String... values) {
        VuiElement generateElement = this.constructor.generateElement(id, Float.valueOf(currState), values);
        if (generateElement == null) {
            return null;
        }
        JsonObject props = generateElement.getProps();
        props.addProperty(VuiConstants.PROPS_DEFAULTACTION, "");
        props.addProperty(VuiConstants.PROPS_UNIT, "Centigrade");
        generateElement.setProps(props);
        return generateElement;
    }
}
