package com.xiaopeng.speech.protocol.bean;

import com.xiaopeng.speech.protocol.utils.StringUtil;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class VolumeValue {
    private static final String STREAM_MUSIC = "3";
    private AdjustValue adjustValue;
    private int streamType;

    public static VolumeValue fromJson(String str) {
        VolumeValue volumeValue = new VolumeValue();
        try {
            JSONObject jSONObject = new JSONObject(str);
            String optString = jSONObject.optString("stream_type", "3");
            volumeValue.streamType = Integer.valueOf(StringUtil.isDecimalNumber(optString) ? optString : "3").intValue();
            volumeValue.adjustValue = AdjustValue.fromJson(jSONObject.optString("change_value"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return volumeValue;
    }

    public int getStreamType() {
        return this.streamType;
    }

    public void setStreamType(int i) {
        this.streamType = i;
    }

    public AdjustValue getAdjustValue() {
        return this.adjustValue;
    }

    public void setAdjustValue(AdjustValue adjustValue) {
        this.adjustValue = adjustValue;
    }

    public String toString() {
        return "VolumeValue{streamType=" + this.streamType + ", adjustValue=" + this.adjustValue + '}';
    }
}
