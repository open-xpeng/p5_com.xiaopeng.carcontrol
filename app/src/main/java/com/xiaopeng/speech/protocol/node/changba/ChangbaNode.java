package com.xiaopeng.speech.protocol.node.changba;

import com.xiaopeng.speech.SpeechNode;
import com.xiaopeng.speech.protocol.node.changba.bean.ChangbaBean;

/* loaded from: classes2.dex */
public class ChangbaNode extends SpeechNode<ChangbaListener> {
    /* JADX INFO: Access modifiers changed from: protected */
    public void onMusicChangbaSearch(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        ChangbaBean fromJson = ChangbaBean.fromJson(str2);
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ChangbaListener) obj).onMusicChangbaSearch(fromJson);
            }
        }
    }
}
