package com.xiaopeng.speech.protocol.node.skill;

import com.xiaopeng.speech.SpeechNode;

/* loaded from: classes2.dex */
public class SkillDialogNode extends SpeechNode<SkillDialogListener> {
    /* JADX INFO: Access modifiers changed from: protected */
    public void onCloseWindow(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SkillDialogListener) obj).onCloseWindow(str2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onOpenWindow(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SkillDialogListener) obj).onOpenWindow(str2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onForwardScreenEvent(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SkillDialogListener) obj).onForwardScreenEvent(str2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRefreshSkillData(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SkillDialogListener) obj).onRefreshSkillData(str2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onShowDoubleGuide(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SkillDialogListener) obj).onShowDoubleGuide(str2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onShowKeyGuide(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SkillDialogListener) obj).onShowKeyGuide(str2);
            }
        }
    }
}
