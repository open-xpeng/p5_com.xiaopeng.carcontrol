package com.xiaopeng.speech.protocol.node.fm;

import com.xiaopeng.speech.SpeechNode;

/* loaded from: classes2.dex */
public class FMNode extends SpeechNode<FMListener> {
    /* JADX INFO: Access modifiers changed from: protected */
    public void onFmLocalOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((FMListener) obj).onFmLocalOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onFmLocalOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((FMListener) obj).onFmLocalOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onFmNetworkOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((FMListener) obj).onFmNetworkOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onFmNetworkOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((FMListener) obj).onFmNetworkOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onFmPlayChannel(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((FMListener) obj).onFmPlayChannel(str2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onFmPlayChannelName(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((FMListener) obj).onFmPlayChannelName(str, str2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPlayCollectFM(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((FMListener) obj).onPlayCollectFM();
            }
        }
    }
}
