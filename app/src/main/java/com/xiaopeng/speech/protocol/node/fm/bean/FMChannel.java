package com.xiaopeng.speech.protocol.node.fm.bean;

import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class FMChannel {
    private String mFMName;
    private float mFMRadio;

    public static final FMChannel fromJson(String str) {
        FMChannel fMChannel = new FMChannel();
        try {
            JSONObject jSONObject = new JSONObject(str);
            fMChannel.setFMName(jSONObject.optString("name"));
            if (jSONObject.has("fm")) {
                fMChannel.setFMRadio(Float.valueOf(jSONObject.optString("fm")).floatValue());
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return fMChannel;
    }

    public float getFMRadio() {
        return this.mFMRadio;
    }

    public void setFMRadio(float f) {
        this.mFMRadio = f;
    }

    public void setFMName(String str) {
        this.mFMName = str;
    }

    public String getFMName() {
        return this.mFMName;
    }

    public String toString() {
        return "FMChannel{FMRadio='" + this.mFMRadio + "'}";
    }
}
