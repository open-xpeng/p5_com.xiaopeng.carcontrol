package com.xiaopeng.lludancemanager.vui;

import android.content.Context;
import com.xiaopeng.lludancemanager.R;
import com.xiaopeng.speech.vui.constructor.BaseStatefulButtonConstructor;
import com.xiaopeng.vui.commons.IVuiElement;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class LluStatefulButtonConstructor extends BaseStatefulButtonConstructor {
    private final int mValue;
    private String[] mVuiLabels;

    public LluStatefulButtonConstructor(Context context, int mValue) {
        if (context != null) {
            this.mVuiLabels = context.getResources().getStringArray(R.array.llu_state_array);
        }
        this.mValue = mValue;
    }

    @Override // com.xiaopeng.speech.vui.constructor.IStatefulButtonConstructor
    public void construct(IVuiElement iVuiElement) {
        setStatefulButtonData(iVuiElement, this.mValue, this.mVuiLabels);
        JSONObject vuiProps = iVuiElement.getVuiProps();
        if (vuiProps == null) {
            return;
        }
        try {
            vuiProps.put("hasFeedback", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        iVuiElement.setVuiProps(vuiProps);
    }
}
