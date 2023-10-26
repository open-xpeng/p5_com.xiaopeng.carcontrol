package com.xiaopeng.speech.protocol.node.speech.carcontrol;

import com.xiaopeng.speech.SpeechNode;

/* loaded from: classes2.dex */
public class SpeechCarControlNode extends SpeechNode<SpeechCarControlListener> {
    /* JADX INFO: Access modifiers changed from: protected */
    public void onCloseSoc(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SpeechCarControlListener) obj).onCloseDriveMileIncrease();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onOpenSoc(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SpeechCarControlListener) obj).onOpenDriveMileIncrease();
            }
        }
    }

    protected void onRiseSpeaker(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SpeechCarControlListener) obj).onOpenLoudspeaker();
            }
        }
    }
}
