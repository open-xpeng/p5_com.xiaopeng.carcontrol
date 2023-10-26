package com.xiaopeng.speech.protocol.node.aiassistant;

import android.text.TextUtils;
import com.xiaopeng.speech.SpeechNode;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class AiAssistantNode extends SpeechNode<AiAssistantListener> {
    /* JADX INFO: Access modifiers changed from: protected */
    public void onPlayVideo(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((AiAssistantListener) obj).onPlayVideo();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPlayVideoTtsend(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((AiAssistantListener) obj).onPlayVideoTtsend();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onMessageOpen(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((AiAssistantListener) obj).onMessageOpen();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onMessageClose(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((AiAssistantListener) obj).onMessageClose();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onOpenVideo(String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject(str2);
            String optString = jSONObject.optString("video_name");
            String optString2 = jSONObject.optString("video_tag");
            String optString3 = jSONObject.optString("video_category");
            Object[] collectCallbacks = this.mListenerList.collectCallbacks();
            if (collectCallbacks != null) {
                for (Object obj : collectCallbacks) {
                    ((AiAssistantListener) obj).onOpenVideo(optString, optString2, optString3);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onXiaoPDance(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((AiAssistantListener) obj).onXiaoPDance(0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onXiaoPChangeClothe(String str, String str2) {
        try {
            int optInt = !TextUtils.isEmpty(str2) ? new JSONObject(str2).optInt("skin") : 0;
            Object[] collectCallbacks = this.mListenerList.collectCallbacks();
            if (collectCallbacks != null) {
                for (Object obj : collectCallbacks) {
                    ((AiAssistantListener) obj).onXiaoPChangeClothe(optInt);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
