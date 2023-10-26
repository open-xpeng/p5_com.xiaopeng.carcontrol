package com.xiaopeng.speech.protocol.node.bugreport;

import com.xiaopeng.speech.SpeechNode;

/* loaded from: classes2.dex */
public class BugReportNode extends SpeechNode<BugReportListener> {
    /* JADX INFO: Access modifiers changed from: protected */
    public void onBugReportBegin(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((BugReportListener) obj).onBugReportBegin();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onBugReportEnd(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        BugReportEndValue fromJson = BugReportEndValue.fromJson(str2);
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((BugReportListener) obj).onBugReportEnd(fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onBugReportVolume(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((BugReportListener) obj).onBugReportVolume(str2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onBugReportEndtts(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((BugReportListener) obj).onBugReportEndtts();
            }
        }
    }
}
