package com.xiaopeng.speech.protocol.node.tts;

import com.xiaopeng.speech.SpeechNode;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class TtsNode extends SpeechNode<TtsListener> {
    /* JADX INFO: Access modifiers changed from: protected */
    public void onTtsStart(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((TtsListener) obj).ttsStart(str2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTtsEnd(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((TtsListener) obj).ttsEnd(str2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTtsTimbreSetting(String str, String str2) {
        int i;
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        try {
            i = new JSONObject(str2).optInt("timbre_type");
        } catch (JSONException e) {
            e.printStackTrace();
            i = 1;
        }
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((TtsListener) obj).onTtsTimbreSetting(i);
            }
        }
    }
}
