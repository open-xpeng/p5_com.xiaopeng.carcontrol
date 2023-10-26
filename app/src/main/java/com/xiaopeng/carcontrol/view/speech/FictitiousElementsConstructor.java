package com.xiaopeng.carcontrol.view.speech;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.xiaopeng.carcontrol.util.LogUtils;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public abstract class FictitiousElementsConstructor<T> implements IFictitiousElementConstructor<T> {
    private String TAG = "FictitiousElementsConstructor";

    public JsonObject generateElementValueJSON(String action, Object... value) {
        try {
            if (TextUtils.isEmpty(action)) {
                return new JsonObject();
            }
            if (value != null && value.length != 0) {
                String[] split = action.split("\\|");
                if (split == null) {
                    return new JsonObject();
                }
                if (value.length < split.length) {
                    return new JsonObject();
                }
                JSONObject jSONObject = new JSONObject();
                for (int i = 0; i < split.length; i++) {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("value", value[i]);
                    jSONObject.put(action, jSONObject2);
                }
                return (JsonObject) new Gson().fromJson(jSONObject.toString(), (Class<Object>) JsonObject.class);
            }
            return new JsonObject();
        } catch (Exception e) {
            LogUtils.e(this.TAG, e.getMessage());
            return null;
        }
    }
}
