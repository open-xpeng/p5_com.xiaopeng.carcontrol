package com.xiaopeng.speech.protocol.node.floater;

import com.xiaopeng.speech.SpeechNode;
import com.xiaopeng.speech.protocol.bean.WindowAnimState;

/* loaded from: classes2.dex */
public class FloaterSpeechNode extends SpeechNode<FloaterSpeechListener> {
    /* JADX INFO: Access modifiers changed from: protected */
    public void onCloseWindow(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        int parseInt = Integer.parseInt(str2);
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((FloaterSpeechListener) obj).onCloseWindow(parseInt);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSetAnimState(String str, String str2) {
        WindowAnimState fromJson = WindowAnimState.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((FloaterSpeechListener) obj).onSetAnimState(fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onXiaopExpression(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((FloaterSpeechListener) obj).onXiaopExpression(str2);
            }
        }
    }
}
