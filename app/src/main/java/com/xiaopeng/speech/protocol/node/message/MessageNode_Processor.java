package com.xiaopeng.speech.protocol.node.message;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.protocol.event.MessageEvent;

/* loaded from: classes2.dex */
public class MessageNode_Processor implements ICommandProcessor {
    private MessageNode mTarget;

    public MessageNode_Processor(MessageNode messageNode) {
        this.mTarget = messageNode;
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String str, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -1618710173:
                if (str.equals(MessageEvent.PATH_CHANGE)) {
                    c = 0;
                    break;
                }
                break;
            case -1486791704:
                if (str.equals(MessageEvent.PARKING_SELECT)) {
                    c = 1;
                    break;
                }
                break;
            case -1370113131:
                if (str.equals(MessageEvent.COMMON_MESSAGE_CANCEL)) {
                    c = 2;
                    break;
                }
                break;
            case -893924045:
                if (str.equals(MessageEvent.COMMON_MESSAGE_SUBMIT)) {
                    c = 3;
                    break;
                }
                break;
            case 738039525:
                if (str.equals(MessageEvent.MESSAGE_AI_TO_SPEECH)) {
                    c = 4;
                    break;
                }
                break;
            case 963627594:
                if (str.equals(MessageEvent.COMMON_MESSAGE_AI_TO_SPEECH)) {
                    c = 5;
                    break;
                }
                break;
            case 1482412654:
                if (str.equals(MessageNode.EVENT)) {
                    c = 6;
                    break;
                }
                break;
            case 1609816176:
                if (str.equals(MessageEvent.CANCEL)) {
                    c = 7;
                    break;
                }
                break;
            case 1843785962:
                if (str.equals(MessageEvent.AI_MESSAGE_DISABLE)) {
                    c = '\b';
                    break;
                }
                break;
            case 1887912220:
                if (str.equals(MessageEvent.AI_MESSAGE_DISABLE_SEVEN_DAYS)) {
                    c = '\t';
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                this.mTarget.onPathChanged(str, str2);
                return;
            case 1:
                this.mTarget.onParkingSelected(str, str2);
                return;
            case 2:
                this.mTarget.onCommonCancel(str, str2);
                return;
            case 3:
                this.mTarget.onCommonSubmit(str, str2);
                return;
            case 4:
                this.mTarget.onAIMessage(str, str2);
                return;
            case 5:
                this.mTarget.onCommonAIMessage(str, str2);
                return;
            case 6:
                this.mTarget.onHotWordEngineOK(str, str2);
                return;
            case 7:
                this.mTarget.onCancel(str, str2);
                return;
            case '\b':
                this.mTarget.onAIMessageDisable(str, str2);
                return;
            case '\t':
                this.mTarget.onAIMessageDisableSevenDays(str, str2);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{MessageEvent.CANCEL, MessageEvent.PARKING_SELECT, MessageEvent.PATH_CHANGE, MessageEvent.MESSAGE_AI_TO_SPEECH, MessageEvent.COMMON_MESSAGE_AI_TO_SPEECH, MessageEvent.COMMON_MESSAGE_SUBMIT, MessageEvent.COMMON_MESSAGE_CANCEL, MessageEvent.AI_MESSAGE_DISABLE, MessageEvent.AI_MESSAGE_DISABLE_SEVEN_DAYS, MessageNode.EVENT};
    }
}
