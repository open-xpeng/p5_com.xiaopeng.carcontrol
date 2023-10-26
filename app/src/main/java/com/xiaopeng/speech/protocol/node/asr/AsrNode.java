package com.xiaopeng.speech.protocol.node.asr;

import android.text.TextUtils;
import com.xiaopeng.speech.SpeechNode;
import com.xiaopeng.speech.jarvisproto.AsrEvent;
import com.xiaopeng.speech.protocol.node.app.AppListener;

/* loaded from: classes2.dex */
public class AsrNode extends SpeechNode<AppListener> {
    /* JADX INFO: Access modifiers changed from: protected */
    public void onAsrEvent(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        AsrEvent fromJson = AsrEvent.fromJson(str2);
        if (collectCallbacks != null) {
            for (int i = 0; i < collectCallbacks.length; i++) {
                ((AsrListener) collectCallbacks[i]).onAsrEvent(fromJson.mEvent);
                ((AsrListener) collectCallbacks[i]).onAsrEvent(fromJson);
            }
        }
    }
}
