package com.xiaopeng.speech.protocol.node.xpu;

import android.text.TextUtils;
import com.xiaopeng.speech.SpeechNode;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class XpuNode extends SpeechNode<IXpuListener> {
    /* JADX INFO: Access modifiers changed from: protected */
    public void laneChange(String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject(str2);
            if (jSONObject.has(VuiConstants.EVENT_VALUE_DIRECTION)) {
                String optString = jSONObject.optString(VuiConstants.EVENT_VALUE_DIRECTION);
                if (TextUtils.isEmpty(optString)) {
                    return;
                }
                int parseInt = Integer.parseInt(optString);
                Object[] collectCallbacks = this.mListenerList.collectCallbacks();
                if (collectCallbacks != null) {
                    for (Object obj : collectCallbacks) {
                        ((IXpuListener) obj).laneChange(parseInt);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
