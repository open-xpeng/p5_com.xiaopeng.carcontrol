package com.xiaopeng.speech.protocol.node.meter;

import com.xiaopeng.lib.framework.aiassistantmodule.interactive.Constants;
import com.xiaopeng.speech.SpeechNode;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class MeterNode extends SpeechNode<MeterListener> {
    /* JADX INFO: Access modifiers changed from: protected */
    public void setLeftCard(String str, String str2) {
        int i;
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        try {
            i = new JSONObject(str2).optInt(Constants.INDEX);
        } catch (JSONException e) {
            e.printStackTrace();
            i = -1;
        }
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((MeterListener) obj).setLeftCard(i);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setRightCard(String str, String str2) {
        int i;
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        try {
            i = new JSONObject(str2).optInt(Constants.INDEX);
        } catch (JSONException e) {
            e.printStackTrace();
            i = -1;
        }
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((MeterListener) obj).setRightCard(i);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onDashboardLightsStatus(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((MeterListener) obj).onDashboardLightsStatus();
            }
        }
    }
}
