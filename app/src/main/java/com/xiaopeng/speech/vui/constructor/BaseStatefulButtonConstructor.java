package com.xiaopeng.speech.vui.constructor;

import com.xiaopeng.speech.vui.constants.VuiConstants;
import com.xiaopeng.speech.vui.utils.LogUtils;
import com.xiaopeng.vui.commons.IVuiElement;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public abstract class BaseStatefulButtonConstructor implements IStatefulButtonConstructor {
    private String TAG = getClass().getSimpleName();
    private String[] stateNames;

    public void setStatefulButtonData(IVuiElement iVuiElement, int i, String... strArr) {
        if (iVuiElement == null) {
            LogUtils.e(this.TAG, "vuiFriendly is null");
        } else if (strArr == null || strArr.length == 0) {
            LogUtils.e(this.TAG, "vuilabels  is empty");
        } else if (i < 0 || i > strArr.length - 1) {
            LogUtils.e(this.TAG, "currIndex 超过vuilabels数组边界");
        } else {
            JSONObject jSONObject = new JSONObject();
            JSONArray jSONArray = new JSONArray();
            try {
                this.stateNames = new String[strArr.length];
                int i2 = 0;
                while (i2 < strArr.length) {
                    JSONObject jSONObject2 = new JSONObject();
                    int i3 = i2 + 1;
                    String str = "state_" + i3;
                    this.stateNames[i2] = str;
                    jSONObject2.put(str, strArr[i2]);
                    jSONArray.put(jSONObject2);
                    i2 = i3;
                }
                jSONObject.put("states", jSONArray);
                jSONObject.put("curState", this.stateNames[i]);
                iVuiElement.setVuiProps(jSONObject);
            } catch (JSONException e) {
                e.printStackTrace();
                LogUtils.e(this.TAG, "e:" + e.getMessage());
            }
        }
    }

    public void setStatefulButtonData(IVuiElement iVuiElement, int i, int i2, int i3, float f, String... strArr) {
        if (iVuiElement == null) {
            LogUtils.e(this.TAG, "vuiFriendly is null");
        } else if (strArr == null || strArr.length == 0) {
            LogUtils.e(this.TAG, "vuilabels  is empty");
        } else if (i < 0 || i > strArr.length - 1) {
            LogUtils.e(this.TAG, "currIndex 超过vuilabels数组边界");
        } else {
            JSONObject jSONObject = new JSONObject();
            JSONArray jSONArray = new JSONArray();
            try {
                this.stateNames = new String[strArr.length];
                int i4 = 0;
                while (i4 < strArr.length) {
                    JSONObject jSONObject2 = new JSONObject();
                    int i5 = i4 + 1;
                    String str = "state_" + i5;
                    this.stateNames[i4] = str;
                    jSONObject2.put(str, strArr[i4]);
                    jSONArray.put(jSONObject2);
                    i4 = i5;
                }
                jSONObject.put("minValue", i2);
                jSONObject.put("maxValue", i3);
                jSONObject.put(VuiConstants.PROPS_INTERVAL, f);
                jSONObject.put("states", jSONArray);
                jSONObject.put("curState", this.stateNames[i]);
                iVuiElement.setVuiProps(jSONObject);
            } catch (JSONException e) {
                e.printStackTrace();
                LogUtils.e(this.TAG, "e:" + e.getMessage());
            }
        }
    }
}
