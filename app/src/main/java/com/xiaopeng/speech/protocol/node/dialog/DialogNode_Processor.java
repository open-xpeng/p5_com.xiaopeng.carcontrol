package com.xiaopeng.speech.protocol.node.dialog;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.jarvisproto.DMWait;
import com.xiaopeng.speech.protocol.event.DialogEvent;

/* loaded from: classes2.dex */
public class DialogNode_Processor implements ICommandProcessor {
    private DialogNode mTarget;

    public DialogNode_Processor(DialogNode dialogNode) {
        this.mTarget = dialogNode;
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String str, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -1434480195:
                if (str.equals(DialogEvent.DIALOG_STATUS_CHANGED)) {
                    c = 0;
                    break;
                }
                break;
            case -1411139995:
                if (str.equals(DialogEvent.VAD_END)) {
                    c = 1;
                    break;
                }
                break;
            case -1293262773:
                if (str.equals(DialogEvent.WAKEUP_RESULT)) {
                    c = 2;
                    break;
                }
                break;
            case -931531412:
                if (str.equals(DialogEvent.DIALOG_CONTINUE)) {
                    c = 3;
                    break;
                }
                break;
            case -771523855:
                if (str.equals(DMWait.EVENT)) {
                    c = 4;
                    break;
                }
                break;
            case -153794717:
                if (str.equals(DialogEvent.DIALOG_ERROR)) {
                    c = 5;
                    break;
                }
                break;
            case 1083473887:
                if (str.equals("jarvis.dm.end")) {
                    c = 6;
                    break;
                }
                break;
            case 1101097907:
                if (str.equals(DialogEvent.VAD_BEGIN)) {
                    c = 7;
                    break;
                }
                break;
            case 1849428582:
                if (str.equals("jarvis.dm.start")) {
                    c = '\b';
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                this.mTarget.onDialogStatusChanged(str, str2);
                return;
            case 1:
                this.mTarget.onVadEnd(str, str2);
                return;
            case 2:
                this.mTarget.onWakeupResult(str, str2);
                return;
            case 3:
                this.mTarget.onDialogContinue(str, str2);
                return;
            case 4:
                this.mTarget.onDialogWait(str, str2);
                return;
            case 5:
                this.mTarget.onDialogError(str, str2);
                return;
            case 6:
                this.mTarget.onDialogEnd(str, str2);
                return;
            case 7:
                this.mTarget.onVadBegin(str, str2);
                return;
            case '\b':
                this.mTarget.onDialogStart(str, str2);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{"jarvis.dm.start", DialogEvent.DIALOG_ERROR, "jarvis.dm.end", DMWait.EVENT, DialogEvent.DIALOG_CONTINUE, DialogEvent.WAKEUP_RESULT, DialogEvent.VAD_BEGIN, DialogEvent.VAD_END, DialogEvent.DIALOG_STATUS_CHANGED};
    }
}
