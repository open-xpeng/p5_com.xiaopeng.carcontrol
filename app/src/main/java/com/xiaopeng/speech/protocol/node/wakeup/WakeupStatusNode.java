package com.xiaopeng.speech.protocol.node.wakeup;

import com.xiaopeng.speech.SpeechNode;
import com.xiaopeng.speech.jarvisproto.WakeupStatus;

/* loaded from: classes2.dex */
public class WakeupStatusNode extends SpeechNode<WakeupStatusListener> {
    /* JADX INFO: Access modifiers changed from: protected */
    public void onWakeupStatusChanged(String str, String str2) {
        WakeupStatus fromJson = WakeupStatus.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((WakeupStatusListener) obj).onWakeupStatusChanged(fromJson.mStatus, fromJson.mType, fromJson.mInfo);
            }
        }
    }
}
