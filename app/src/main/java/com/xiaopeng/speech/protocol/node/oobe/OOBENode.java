package com.xiaopeng.speech.protocol.node.oobe;

import com.xiaopeng.speech.SpeechNode;

/* loaded from: classes2.dex */
public class OOBENode extends SpeechNode<OOBEListener> {
    private static final String TAG = "OOBENode";

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRecordResult(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((OOBEListener) obj).onRecordResult(str2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRecordInput(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((OOBEListener) obj).onRecordInput(str2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onAddressSet(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((OOBEListener) obj).onAddressSet(str2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSkip(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((OOBEListener) obj).onSkip();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSearchError(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((OOBEListener) obj).onSearchError();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onNetError(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((OOBEListener) obj).onASRError();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onASRError(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((OOBEListener) obj).onASRError();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onError(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((OOBEListener) obj).onError(str2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onVolumeChange(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((OOBEListener) obj).onVolumeChange(str2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onNetWorkError(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((OOBEListener) obj).onNetWorkError();
            }
        }
    }
}
