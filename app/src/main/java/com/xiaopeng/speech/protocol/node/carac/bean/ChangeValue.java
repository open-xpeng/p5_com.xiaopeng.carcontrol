package com.xiaopeng.speech.protocol.node.carac.bean;

import android.text.TextUtils;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class ChangeValue {
    private int decimal;
    private int value;

    public static ChangeValue fromJson(String str) {
        int parseInt;
        ChangeValue changeValue = new ChangeValue();
        try {
            JSONObject jSONObject = new JSONObject(str);
            String optString = jSONObject.optString("number");
            String optString2 = jSONObject.optString(TypedValues.Attributes.S_TARGET);
            String optString3 = jSONObject.optString("scale");
            String optString4 = jSONObject.optString("d");
            if (!TextUtils.isEmpty(optString2)) {
                parseInt = Integer.parseInt(optString2);
            } else if (!TextUtils.isEmpty(optString3)) {
                parseInt = Integer.parseInt(optString3);
            } else {
                parseInt = !TextUtils.isEmpty(optString) ? Integer.parseInt(optString) : 1;
            }
            if (!TextUtils.isEmpty(optString4)) {
                changeValue.decimal = Integer.parseInt(optString4);
            }
            changeValue.value = parseInt;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return changeValue;
    }

    public int getValue() {
        return this.value;
    }

    public ChangeValue setValue(int i) {
        this.value = i;
        return this;
    }

    public int getDecimal() {
        return this.decimal;
    }
}
