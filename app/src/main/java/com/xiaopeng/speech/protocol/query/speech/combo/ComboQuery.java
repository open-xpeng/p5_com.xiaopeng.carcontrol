package com.xiaopeng.speech.protocol.query.speech.combo;

import android.text.TextUtils;
import com.xiaopeng.speech.SpeechQuery;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class ComboQuery extends SpeechQuery<IComboQueryCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public String enterMode(String str, String str2) {
        return ((IComboQueryCaller) this.mQueryCaller).enterUserMode(getModeFromJson(str2));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String exitMode(String str, String str2) {
        return ((IComboQueryCaller) this.mQueryCaller).exitUserMode(getModeFromJson(str2));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String checkEnterUserMode(String str, String str2) {
        return ((IComboQueryCaller) this.mQueryCaller).checkEnterUserMode(getModeFromJson(str2));
    }

    private String getModeFromJson(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            return jSONObject.has("mode") ? jSONObject.optString("mode") : "";
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getCurrentUserMode(String str, String str2) {
        return ((IComboQueryCaller) this.mQueryCaller).getCurrentUserMode();
    }
}
