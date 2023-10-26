package com.xiaopeng.speech.protocol.node.internal.bean;

import android.text.TextUtils;
import com.xiaopeng.speech.common.SpeechEvent;
import com.xiaopeng.speech.jarvisproto.WakeupResult;
import com.xiaopeng.speech.speechwidget.SpeechWidget;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class DMResult {
    private String event;
    private String intent;
    private boolean isNative;
    private String nlg;
    private JSONObject param;
    private String rawData;
    private String runSequence;
    private String sessionId;
    private boolean shouldEndSession;
    private String task;
    private JSONObject widget;

    public static DMResult fromJson(String str) {
        DMResult dMResult = new DMResult();
        dMResult.rawData = str;
        try {
            JSONObject jSONObject = new JSONObject(str);
            dMResult.sessionId = jSONObject.optString("sessionId");
            JSONObject optJSONObject = jSONObject.optJSONObject("dm");
            if (optJSONObject.optString("dataFrom", "").equals("native")) {
                String optString = optJSONObject.optString(SpeechWidget.DATA_SOURCE_API);
                if (!TextUtils.isEmpty(optString)) {
                    dMResult.event = SpeechEvent.NATIVE_API_SCHEME + optString;
                }
            }
            JSONObject optJSONObject2 = optJSONObject.optJSONObject(WakeupResult.REASON_COMMAND);
            if (optJSONObject2 != null) {
                String optString2 = optJSONObject2.optString(SpeechWidget.DATA_SOURCE_API);
                if (!TextUtils.isEmpty(optString2)) {
                    dMResult.event = SpeechEvent.COMMAND_SCHEME + optString2;
                }
            }
            dMResult.intent = optJSONObject.optString("intentName");
            dMResult.runSequence = optJSONObject.optString("runSequence");
            dMResult.nlg = optJSONObject.optString("nlg");
            dMResult.task = optJSONObject.optString("task");
            dMResult.shouldEndSession = optJSONObject.optBoolean("shouldEndSession");
            dMResult.isNative = !TextUtils.isEmpty(optJSONObject.optString("dataFrom"));
            dMResult.widget = optJSONObject.optJSONObject("widget");
            dMResult.param = optJSONObject.optJSONObject("param");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dMResult;
    }

    public String getRawData() {
        return this.rawData;
    }

    public JSONObject getParam() {
        return this.param;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public String getIntent() {
        return this.intent;
    }

    public void setIntent(String str) {
        this.intent = str;
    }

    public String getRunSequence() {
        return this.runSequence;
    }

    public void setRunSequence(String str) {
        this.runSequence = str;
    }

    public String getEvent() {
        return this.event;
    }

    public void setEvent(String str) {
        this.event = str;
    }

    public String getNlg() {
        return this.nlg;
    }

    public void setNlg(String str) {
        this.nlg = str;
    }

    public String getTask() {
        return this.task;
    }

    public void setTask(String str) {
        this.task = str;
    }

    public boolean isShouldEndSession() {
        return this.shouldEndSession;
    }

    public void setShouldEndSession(boolean z) {
        this.shouldEndSession = z;
    }

    public boolean isNative() {
        return this.isNative;
    }

    public void setNative(boolean z) {
        this.isNative = z;
    }

    public boolean isListWidget() {
        return this.widget.optString(VuiConstants.ELEMENT_TYPE).equals(SpeechWidget.TYPE_LIST);
    }
}
