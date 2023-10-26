package com.xiaopeng.carcontrol.speech;

import com.google.gson.annotations.SerializedName;

/* loaded from: classes2.dex */
public class SpeechParams {
    @SerializedName("name")
    public String name;
    @SerializedName("value_type")
    public String type;
    @SerializedName("value")
    public String value;

    public String toString() {
        return "Params{type='" + this.type + "', value='" + this.value + "', name='" + this.name + "'}";
    }
}
