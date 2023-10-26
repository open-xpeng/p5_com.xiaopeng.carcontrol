package com.xiaopeng.speech.protocol.node.avatar;

import com.xiaopeng.speech.SpeechNode;
import com.xiaopeng.speech.jarvisproto.DMListening;
import com.xiaopeng.speech.jarvisproto.DMRecognized;

/* loaded from: classes2.dex */
public class AvatarNode extends SpeechNode<AvatarListener> {
    /* JADX INFO: Access modifiers changed from: protected */
    public void onSilence(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        DMRecognized fromJson = DMRecognized.fromJson(str2);
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((AvatarListener) obj).onSilence(fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onListening(String str, String str2) {
        DMListening fromJson = DMListening.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((AvatarListener) obj).onListening(fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSpeaking(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((AvatarListener) obj).onSpeaking();
            }
        }
    }

    protected void onStandby(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((AvatarListener) obj).onStandby();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onUnderstanding(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((AvatarListener) obj).onUnderstanding();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onAvatarExpression(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((AvatarListener) obj).onAvatarExpression(str2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWidgetCustom(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((AvatarListener) obj).onWidgetCustom(str2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWidgetText(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((AvatarListener) obj).onWidgetText(str2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWidgetList(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((AvatarListener) obj).onWidgetList(str2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWidgetMedia(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((AvatarListener) obj).onWidgetMedia(str2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWidgetCard(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((AvatarListener) obj).onWidgetCard(str2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWidgetSearch(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((AvatarListener) obj).onWidgetSearch(str2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onAvatarWakerupEnable(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((AvatarListener) obj).onAvatarWakerupEnable(str2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onAvatarWakerupDisable(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((AvatarListener) obj).onAvatarWakerupDisable(str2);
            }
        }
    }
}
