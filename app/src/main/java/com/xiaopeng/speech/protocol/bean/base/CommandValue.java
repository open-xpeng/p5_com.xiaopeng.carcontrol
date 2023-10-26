package com.xiaopeng.speech.protocol.bean.base;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.xiaopeng.speech.common.util.DontProguardClass;
import org.json.JSONObject;

@DontProguardClass
/* loaded from: classes2.dex */
public class CommandValue {
    private int color;
    private int number;
    private int percent;

    public int getPercent() {
        return this.percent;
    }

    public void setPercent(int i) {
        this.percent = i;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int i) {
        this.number = i;
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int i) {
        this.color = i;
    }

    public CommandValue(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has(TypedValues.Custom.S_COLOR)) {
                this.color = Integer.valueOf(jSONObject.getString(TypedValues.Custom.S_COLOR)).intValue();
            }
            if (jSONObject.has("number")) {
                this.number = jSONObject.getInt("number");
            }
            if (jSONObject.has("percent")) {
                this.percent = jSONObject.getInt("percent");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
