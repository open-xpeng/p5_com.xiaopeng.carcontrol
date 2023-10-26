package com.xiaopeng.speech.protocol.node.bugreport;

import android.text.TextUtils;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class BugReportEndValue {
    private String reason;
    private boolean suc;

    public boolean isSuc() {
        return this.suc;
    }

    public void setSuc(boolean z) {
        this.suc = z;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String str) {
        this.reason = str;
    }

    public static BugReportEndValue fromJson(String str) {
        BugReportEndValue bugReportEndValue = new BugReportEndValue();
        try {
            JSONObject jSONObject = new JSONObject(str);
            boolean optBoolean = jSONObject.optBoolean("suc");
            String optString = jSONObject.optString("reason");
            if (!TextUtils.isEmpty(optString)) {
                bugReportEndValue.reason = optString;
            }
            bugReportEndValue.suc = optBoolean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bugReportEndValue;
    }
}
