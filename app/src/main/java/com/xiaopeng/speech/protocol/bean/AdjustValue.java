package com.xiaopeng.speech.protocol.bean;

import android.text.TextUtils;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class AdjustValue {
    private boolean isPercent = false;
    private int value;

    public static AdjustValue fromJson(String str) {
        AdjustValue adjustValue = new AdjustValue();
        try {
            JSONObject jSONObject = new JSONObject(str);
            int i = 0;
            String optString = jSONObject.optString(TypedValues.Attributes.S_TARGET);
            String optString2 = jSONObject.optString("scale");
            String optString3 = jSONObject.optString("number");
            if (!TextUtils.isEmpty(optString)) {
                i = Integer.parseInt(optString);
            } else if (!TextUtils.isEmpty(optString2)) {
                i = Integer.parseInt(optString2);
            } else if (!TextUtils.isEmpty(optString3)) {
                i = Integer.parseInt(optString3);
            }
            String optString4 = jSONObject.optString("percent");
            if (!TextUtils.isEmpty(optString4)) {
                adjustValue.isPercent = true;
                i = Integer.parseInt(optString4);
            }
            adjustValue.value = i;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return adjustValue;
    }

    public boolean isPercent() {
        return this.isPercent;
    }

    public int getValue() {
        return this.value;
    }
}
