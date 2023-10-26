package com.xiaopeng.speech.protocol.node.widget;

import com.xiaopeng.speech.SpeechNode;

/* loaded from: classes2.dex */
public class WidgetNode extends SpeechNode<WidgetListener> {
    /* JADX INFO: Access modifiers changed from: protected */
    public void onAcWidgetOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((WidgetListener) obj).onAcWidgetOn();
            }
        }
    }
}
