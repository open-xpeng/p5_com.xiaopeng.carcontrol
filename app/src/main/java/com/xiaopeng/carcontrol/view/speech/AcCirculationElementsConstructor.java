package com.xiaopeng.carcontrol.view.speech;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.viewmodel.hvac.HvacCirculationMode;
import com.xiaopeng.carcontrol.viewmodel.hvac.HvacSwitchStatus;
import com.xiaopeng.carcontrol.viewmodel.hvac.HvacViewModel;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import com.xiaopeng.speech.vui.utils.VuiUtils;
import com.xiaopeng.vui.commons.VuiAction;
import com.xiaopeng.vui.commons.VuiElementType;
import com.xiaopeng.vui.commons.model.VuiElement;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class AcCirculationElementsConstructor {
    public static final int AUTO = 3;
    public static final int INNER = 1;
    public static final int OUTSIDE = 2;
    private SwitchFictitiousElementsConstructor mSwitchConstructor = new SwitchFictitiousElementsConstructor();
    private HvacViewModel mViewModel;

    public VuiElement getAcCirculationElements(String id, HvacViewModel viewModel, String... vuiLabels) {
        this.mViewModel = viewModel;
        VuiElement build = new VuiElement.Builder().id(id).type(VuiElementType.CUSTOM.getType()).build();
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (i < vuiLabels.length) {
            int i2 = i + 1;
            VuiElement generateElement = this.mSwitchConstructor.generateElement(id + "_" + i2, Boolean.valueOf(getCurrState(i2)), vuiLabels[i]);
            generateElement.setProps(getVuiLabelAction());
            arrayList.add(generateElement);
            i = i2;
        }
        build.setProps(getVuiFeedbackJson());
        build.setElements(arrayList);
        return build;
    }

    public boolean getCurrState(int index) {
        if (!CarBaseConfig.getInstance().isSupportAqs()) {
            return index != 1 ? index != 2 ? index == 3 && this.mViewModel.getCirculationMode() == HvacCirculationMode.Auto : this.mViewModel.getCirculationMode() == HvacCirculationMode.Outside : this.mViewModel.getCirculationMode() == HvacCirculationMode.Inner;
        } else if (index == 1) {
            return this.mViewModel.getHvacAqsStatus() != HvacSwitchStatus.ON && this.mViewModel.getCirculationMode() == HvacCirculationMode.Inner;
        } else if (index == 2) {
            return this.mViewModel.getHvacAqsStatus() != HvacSwitchStatus.ON && this.mViewModel.getCirculationMode() == HvacCirculationMode.Outside;
        } else if (index == 3) {
            return this.mViewModel.getHvacAqsStatus() == HvacSwitchStatus.ON || this.mViewModel.getCirculationMode() == HvacCirculationMode.Auto;
        }
        return false;
    }

    private JsonObject getVuiLabelAction() {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        VuiUtils.generateElementValueJSON(jSONObject, VuiAction.SETCHECK.getName(), true);
        try {
            jSONObject2.put(VuiConstants.PROPS_DEFAULTACTION, jSONObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return (JsonObject) new Gson().fromJson(jSONObject2.toString(), (Class<Object>) JsonObject.class);
    }

    private JsonObject getVuiFeedbackJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("hasFeedback", true);
        } catch (Exception unused) {
        }
        return (JsonObject) new Gson().fromJson(jSONObject.toString(), (Class<Object>) JsonObject.class);
    }
}
