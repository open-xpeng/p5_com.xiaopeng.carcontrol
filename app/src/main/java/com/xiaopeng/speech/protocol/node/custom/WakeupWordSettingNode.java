package com.xiaopeng.speech.protocol.node.custom;

import com.xiaopeng.speech.SpeechNode;

/* loaded from: classes2.dex */
public class WakeupWordSettingNode extends SpeechNode<WakeupWordSettingListener> {
    /* JADX INFO: Access modifiers changed from: protected */
    public void onManualInput(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((WakeupWordSettingListener) obj).onManualInput(str2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSettingDone(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((WakeupWordSettingListener) obj).onSettingDone(str2);
            }
        }
    }
}
