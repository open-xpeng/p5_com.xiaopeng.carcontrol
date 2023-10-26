package com.xiaopeng.speech.overall;

import androidx.core.app.NotificationCompat;
import com.xiaopeng.speech.protocol.bean.recommend.RecommendBean;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class SpeechResult {
    public static final int FLOAT_ARRAY_TYPE = 2;
    public static final int INT_ARRAY_TYPE = 1;
    private int classType = 0;
    private String event;
    private Object result;

    public SpeechResult(String str, Object obj) {
        this.event = str;
        this.result = initValue(obj);
    }

    public String toString() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(NotificationCompat.CATEGORY_EVENT, this.event);
            jSONObject.put(RecommendBean.SHOW_TIME_RESULT, this.result);
            int i = this.classType;
            if (i > 0) {
                jSONObject.put("classType", i);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    private Object initValue(Object obj) {
        if (obj == null) {
            return obj;
        }
        try {
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (obj instanceof int[]) {
            this.classType = 1;
            return new JSONArray(obj);
        }
        if (obj instanceof float[]) {
            this.classType = 2;
            return new JSONArray(obj);
        }
        return obj;
    }
}
