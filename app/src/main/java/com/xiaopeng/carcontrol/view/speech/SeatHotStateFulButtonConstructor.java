package com.xiaopeng.carcontrol.view.speech;

import android.content.Context;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import com.xiaopeng.speech.vui.constructor.BaseStatefulButtonConstructor;
import com.xiaopeng.speech.vui.utils.VuiUtils;
import com.xiaopeng.vui.commons.IVuiElement;
import com.xiaopeng.vui.commons.VuiAction;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class SeatHotStateFulButtonConstructor extends BaseStatefulButtonConstructor {
    private int level;
    private String[] vuiLabels;

    public SeatHotStateFulButtonConstructor(Context mContext, int level) {
        this.level = level;
        if (mContext != null) {
            this.vuiLabels = mContext.getResources().getStringArray(R.array.hvac_seat_air_vui_labels);
        }
    }

    @Override // com.xiaopeng.speech.vui.constructor.IStatefulButtonConstructor
    public void construct(IVuiElement vuiFriendly) {
        if (vuiFriendly == null) {
            return;
        }
        int i = this.level;
        if (i <= 0) {
            this.level = 0;
        } else if (i > 3) {
            this.level = 3;
        }
        int i2 = this.level;
        setStatefulButtonData(vuiFriendly, i2 != 0 ? i2 - 1 : 0, 1, 3, 1.0f, this.vuiLabels);
        JSONObject vuiProps = vuiFriendly.getVuiProps();
        if (vuiProps == null) {
            return;
        }
        try {
            vuiProps.put(VuiConstants.PROPS_DEFAULTACTION, getVuiLabelAction());
            vuiProps.put(VuiConstants.PROPS_UNIT, "Gear");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        vuiFriendly.setVuiProps(vuiProps);
    }

    private JSONObject getVuiLabelAction() {
        JSONObject jSONObject = new JSONObject();
        VuiUtils.generateElementValueJSON(jSONObject, VuiAction.SETCHECK.getName(), true);
        return jSONObject;
    }
}
