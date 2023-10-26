package com.xiaopeng.speech.protocol.bean;

import android.text.TextUtils;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class ChangeValue {
    private boolean isPercent = false;
    private boolean isScale = false;
    private int value;

    public static ChangeValue fromJson(String str) {
        ChangeValue changeValue = new ChangeValue();
        try {
            JSONObject jSONObject = new JSONObject(str);
            String optString = jSONObject.optString(TypedValues.Attributes.S_TARGET);
            String optString2 = jSONObject.optString("scale");
            int i = 0;
            if (!TextUtils.isEmpty(optString)) {
                changeValue.isScale = false;
                i = Integer.parseInt(optString);
            } else if (!TextUtils.isEmpty(optString2)) {
                changeValue.isScale = true;
                i = Integer.parseInt(optString2);
            }
            String optString3 = jSONObject.optString("percent");
            if (!TextUtils.isEmpty(optString3)) {
                changeValue.isPercent = true;
                i = Integer.parseInt(optString3);
            }
            changeValue.value = i;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return changeValue;
    }

    public boolean isPercent() {
        return this.isPercent;
    }

    public boolean isScale() {
        return this.isScale;
    }

    public int getValue() {
        return this.value;
    }
}
