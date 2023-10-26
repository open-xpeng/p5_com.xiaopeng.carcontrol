package com.xiaopeng.speech.protocol.node.dialog.bean;

import androidx.core.app.NotificationCompat;
import com.xiaopeng.speech.jarvisproto.DMEnd;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class DialogEndReason {
    public String event;
    public String reason;
    public String sessionId;

    public DialogEndReason(String str, String str2, String str3) {
        this.reason = str;
        this.event = str2;
        this.sessionId = str3;
    }

    public static DialogEndReason fromJson(String str) {
        String str2;
        JSONObject jSONObject;
        String str3 = "";
        String str4 = DMEnd.REASON_NORMAL;
        try {
            jSONObject = new JSONObject(str);
            str4 = jSONObject.optString("reason");
            str2 = jSONObject.optString(NotificationCompat.CATEGORY_EVENT);
        } catch (JSONException e) {
            e = e;
            str2 = "";
        }
        try {
            str3 = jSONObject.optString("sessionId");
        } catch (JSONException e2) {
            e = e2;
            e.printStackTrace();
            return new DialogEndReason(str4, str2, str3);
        }
        return new DialogEndReason(str4, str2, str3);
    }
}
