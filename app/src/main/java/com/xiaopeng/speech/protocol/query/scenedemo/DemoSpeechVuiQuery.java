package com.xiaopeng.speech.protocol.query.scenedemo;

import android.text.TextUtils;
import com.xiaopeng.carcontrol.view.speech.VuiActions;
import com.xiaopeng.lib.framework.aiassistantmodule.interactive.Constants;
import com.xiaopeng.speech.SpeechQuery;
import com.xiaopeng.speech.protocol.query.speech.vui.ISpeechVuiElementStateQueryCaller;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class DemoSpeechVuiQuery extends SpeechQuery<ISpeechVuiElementStateQueryCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSwitchChecked(String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject(str2);
            String optString = jSONObject.optString("elementId", "");
            String vuiElementState = ((ISpeechVuiElementStateQueryCaller) this.mQueryCaller).getVuiElementState(jSONObject.optString(VuiConstants.SCENE_ID, ""), optString);
            if (!TextUtils.isEmpty(vuiElementState)) {
                JSONObject jSONObject2 = new JSONObject(vuiElementState);
                if (jSONObject2.has("value")) {
                    return jSONObject2.optJSONObject("value").optJSONObject(VuiActions.SETCHECK).optBoolean("value", false);
                }
                if (jSONObject2.has("values")) {
                    return jSONObject2.optJSONObject("values").optJSONObject(VuiActions.SETCHECK).optBoolean("value", false);
                }
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isTableLayoutSelected(String str, String str2) {
        int optInt;
        try {
            JSONObject jSONObject = new JSONObject(str2);
            int optInt2 = jSONObject.optInt(Constants.INDEX, 0);
            String vuiElementState = ((ISpeechVuiElementStateQueryCaller) this.mQueryCaller).getVuiElementState(jSONObject.optString(VuiConstants.SCENE_ID, ""), jSONObject.optString("elementId", ""));
            if (!TextUtils.isEmpty(vuiElementState)) {
                JSONObject jSONObject2 = new JSONObject(vuiElementState);
                JSONObject jSONObject3 = null;
                if (jSONObject2.has("value")) {
                    jSONObject3 = jSONObject2.optJSONObject("value");
                } else if (jSONObject2.has("values")) {
                    jSONObject3 = jSONObject2.optJSONObject("values");
                }
                if (jSONObject3 != null) {
                    if (jSONObject3.has(VuiActions.SETVALUE)) {
                        optInt = jSONObject3.optJSONObject(VuiActions.SETVALUE).optInt("value", 0);
                    } else {
                        optInt = jSONObject3.has("SelectTab") ? jSONObject3.optJSONObject("SelectTab").optInt("value", 0) : 0;
                    }
                    return optInt == optInt2;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getXSliderIndex(String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject(str2);
            String optString = jSONObject.optString("elementId", "");
            String vuiElementState = ((ISpeechVuiElementStateQueryCaller) this.mQueryCaller).getVuiElementState(jSONObject.optString(VuiConstants.SCENE_ID, ""), optString);
            if (TextUtils.isEmpty(vuiElementState)) {
                return 0;
            }
            JSONObject jSONObject2 = new JSONObject(vuiElementState);
            double d = 0.0d;
            if (jSONObject2.has("value")) {
                d = jSONObject2.optJSONObject("value").optJSONObject(VuiActions.SETVALUE).optDouble("value", 0.0d);
            } else if (jSONObject2.has("values")) {
                d = jSONObject2.optJSONObject("values").optJSONObject(VuiActions.SETVALUE).optDouble("value", 0.0d);
            }
            return (int) d;
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isStatefulButtonChecked(String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject(str2);
            String optString = jSONObject.optString("elementId", "");
            String vuiElementState = ((ISpeechVuiElementStateQueryCaller) this.mQueryCaller).getVuiElementState(jSONObject.optString(VuiConstants.SCENE_ID, ""), optString);
            if (!TextUtils.isEmpty(vuiElementState)) {
                JSONObject jSONObject2 = new JSONObject(vuiElementState);
                if (jSONObject2.has("value")) {
                    return jSONObject2.optJSONObject("value").optJSONObject(VuiActions.SETCHECK).optBoolean("value", false);
                }
                if (jSONObject2.has("values")) {
                    return jSONObject2.optJSONObject("values").optJSONObject(VuiActions.SETCHECK).optBoolean("value", false);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getStatefulButtonValue(String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject(str2);
            String optString = jSONObject.optString("elementId", "");
            String vuiElementState = ((ISpeechVuiElementStateQueryCaller) this.mQueryCaller).getVuiElementState(jSONObject.optString(VuiConstants.SCENE_ID, ""), optString);
            if (!TextUtils.isEmpty(vuiElementState)) {
                JSONObject jSONObject2 = new JSONObject(vuiElementState);
                if (jSONObject2.has("value")) {
                    return jSONObject2.optJSONObject("value").optJSONObject(VuiActions.SETVALUE).optString("value", null);
                }
                if (jSONObject2.has("values")) {
                    return jSONObject2.optJSONObject("values").optJSONObject(VuiActions.SETVALUE).optString("value", null);
                }
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isElementEnabled(String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject(str2);
            String optString = jSONObject.optString("elementId", "");
            String vuiElementState = ((ISpeechVuiElementStateQueryCaller) this.mQueryCaller).getVuiElementState(jSONObject.optString(VuiConstants.SCENE_ID, ""), optString);
            if (TextUtils.isEmpty(vuiElementState)) {
                return false;
            }
            return new JSONObject(vuiElementState).optBoolean(VuiConstants.ELEMENT_ENABLED, true);
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0073, code lost:
        if (r2 == 1) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0075, code lost:
        if (r2 == 2) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0077, code lost:
        if (r2 == 3) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x007a, code lost:
        r10 = com.xiaopeng.speech.vui.constants.VuiConstants.PROPS_SCROLLDOWN;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x007d, code lost:
        r10 = com.xiaopeng.speech.vui.constants.VuiConstants.PROPS_SCROLLRIGHT;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0080, code lost:
        r10 = com.xiaopeng.speech.vui.constants.VuiConstants.PROPS_SCROLLUP;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean isElementCanScrolled(java.lang.String r9, java.lang.String r10) {
        /*
            r8 = this;
            java.lang.String r9 = ""
            r0 = 0
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch: org.json.JSONException -> L8a
            r1.<init>(r10)     // Catch: org.json.JSONException -> L8a
            java.lang.String r10 = "elementId"
            java.lang.String r10 = r1.optString(r10, r9)     // Catch: org.json.JSONException -> L8a
            java.lang.String r2 = "sceneId"
            java.lang.String r2 = r1.optString(r2, r9)     // Catch: org.json.JSONException -> L8a
            java.lang.String r3 = "direction"
            java.lang.String r9 = r1.optString(r3, r9)     // Catch: org.json.JSONException -> L8a
            T r1 = r8.mQueryCaller     // Catch: org.json.JSONException -> L8a
            com.xiaopeng.speech.protocol.query.speech.vui.ISpeechVuiElementStateQueryCaller r1 = (com.xiaopeng.speech.protocol.query.speech.vui.ISpeechVuiElementStateQueryCaller) r1     // Catch: org.json.JSONException -> L8a
            java.lang.String r10 = r1.getVuiElementState(r2, r10)     // Catch: org.json.JSONException -> L8a
            boolean r1 = android.text.TextUtils.isEmpty(r10)     // Catch: org.json.JSONException -> L8a
            if (r1 != 0) goto L8e
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch: org.json.JSONException -> L8a
            r1.<init>(r10)     // Catch: org.json.JSONException -> L8a
            r10 = 0
            r2 = -1
            int r3 = r9.hashCode()     // Catch: org.json.JSONException -> L8a
            r4 = 3739(0xe9b, float:5.24E-42)
            r5 = 3
            r6 = 2
            r7 = 1
            if (r3 == r4) goto L68
            r4 = 3089570(0x2f24a2, float:4.32941E-39)
            if (r3 == r4) goto L5e
            r4 = 3317767(0x32a007, float:4.649182E-39)
            if (r3 == r4) goto L54
            r4 = 108511772(0x677c21c, float:4.6598146E-35)
            if (r3 == r4) goto L4a
            goto L71
        L4a:
            java.lang.String r3 = "right"
            boolean r9 = r9.equals(r3)     // Catch: org.json.JSONException -> L8a
            if (r9 == 0) goto L71
            r2 = r6
            goto L71
        L54:
            java.lang.String r3 = "left"
            boolean r9 = r9.equals(r3)     // Catch: org.json.JSONException -> L8a
            if (r9 == 0) goto L71
            r2 = r0
            goto L71
        L5e:
            java.lang.String r3 = "down"
            boolean r9 = r9.equals(r3)     // Catch: org.json.JSONException -> L8a
            if (r9 == 0) goto L71
            r2 = r5
            goto L71
        L68:
            java.lang.String r3 = "up"
            boolean r9 = r9.equals(r3)     // Catch: org.json.JSONException -> L8a
            if (r9 == 0) goto L71
            r2 = r7
        L71:
            if (r2 == 0) goto L83
            if (r2 == r7) goto L80
            if (r2 == r6) goto L7d
            if (r2 == r5) goto L7a
            goto L85
        L7a:
            java.lang.String r10 = "canScrollDown"
            goto L85
        L7d:
            java.lang.String r10 = "canScrollRight"
            goto L85
        L80:
            java.lang.String r10 = "canScrollUp"
            goto L85
        L83:
            java.lang.String r10 = "canScrollLeft"
        L85:
            boolean r9 = r1.optBoolean(r10, r7)     // Catch: org.json.JSONException -> L8a
            return r9
        L8a:
            r9 = move-exception
            r9.printStackTrace()
        L8e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.speech.protocol.query.scenedemo.DemoSpeechVuiQuery.isElementCanScrolled(java.lang.String, java.lang.String):boolean");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isCheckBoxChecked(String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject(str2);
            String optString = jSONObject.optString("elementId", "");
            String vuiElementState = ((ISpeechVuiElementStateQueryCaller) this.mQueryCaller).getVuiElementState(jSONObject.optString(VuiConstants.SCENE_ID, ""), optString);
            if (!TextUtils.isEmpty(vuiElementState)) {
                JSONObject jSONObject2 = new JSONObject(vuiElementState);
                if (jSONObject2.has("value")) {
                    return jSONObject2.optJSONObject("value").optJSONObject(VuiActions.SETCHECK).optBoolean("value", false);
                }
                if (jSONObject2.has("values")) {
                    return jSONObject2.optJSONObject("values").optJSONObject(VuiActions.SETCHECK).optBoolean("value", false);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isRadiobuttonChecked(String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject(str2);
            String optString = jSONObject.optString("elementId", "");
            String vuiElementState = ((ISpeechVuiElementStateQueryCaller) this.mQueryCaller).getVuiElementState(jSONObject.optString(VuiConstants.SCENE_ID, ""), optString);
            if (!TextUtils.isEmpty(vuiElementState)) {
                JSONObject jSONObject2 = new JSONObject(vuiElementState);
                if (jSONObject2.has("value")) {
                    return jSONObject2.optJSONObject("value").optJSONObject(VuiActions.SETCHECK).optBoolean("value", false);
                }
                if (jSONObject2.has("values")) {
                    return jSONObject2.optJSONObject("values").optJSONObject(VuiActions.SETCHECK).optBoolean("value", false);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}
