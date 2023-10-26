package com.xiaopeng.speech.protocol.query.speech.hardware.bean;

import com.xiaopeng.speech.vui.constants.VuiConstants;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class StreamType {
    public static final String MEDIA = "media";
    public static final String NAVI = "navi";
    public static final String PHONE = "phone";
    public static final String SPEECH = "speech";
    public static final String SYSTEM = "system";
    public String type;

    public static StreamType fromJson(String str) {
        StreamType streamType = new StreamType();
        try {
            streamType.type = new JSONObject(str).optString(VuiConstants.ELEMENT_TYPE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return streamType;
    }
}
