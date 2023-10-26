package com.xiaopeng.speech.protocol.node.aiassistant;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.protocol.event.AiAssistantEvent;

/* loaded from: classes2.dex */
public class AiAssistantNode_Processor implements ICommandProcessor {
    private AiAssistantNode mTarget;

    public AiAssistantNode_Processor(AiAssistantNode aiAssistantNode) {
        this.mTarget = aiAssistantNode;
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String str, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -1808972678:
                if (str.equals(AiAssistantEvent.MESSAGE_CLOSE)) {
                    c = 0;
                    break;
                }
                break;
            case -874525820:
                if (str.equals(AiAssistantEvent.PLAY_VIDEO)) {
                    c = 1;
                    break;
                }
                break;
            case -473634936:
                if (str.equals(AiAssistantEvent.MESSAGE_OPEN)) {
                    c = 2;
                    break;
                }
                break;
            case 67809134:
                if (str.equals(AiAssistantEvent.XIAOP_DANCE)) {
                    c = 3;
                    break;
                }
                break;
            case 422503758:
                if (str.equals(AiAssistantEvent.GUI_OPEN_VIDEO)) {
                    c = 4;
                    break;
                }
                break;
            case 1345769982:
                if (str.equals(AiAssistantEvent.PLAY_VIDEO_TTSEND)) {
                    c = 5;
                    break;
                }
                break;
            case 1715275603:
                if (str.equals(AiAssistantEvent.XIAOP_CHANGE_CLOTHE)) {
                    c = 6;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                this.mTarget.onMessageClose(str, str2);
                return;
            case 1:
                this.mTarget.onPlayVideo(str, str2);
                return;
            case 2:
                this.mTarget.onMessageOpen(str, str2);
                return;
            case 3:
                this.mTarget.onXiaoPDance(str, str2);
                return;
            case 4:
                this.mTarget.onOpenVideo(str, str2);
                return;
            case 5:
                this.mTarget.onPlayVideoTtsend(str, str2);
                return;
            case 6:
                this.mTarget.onXiaoPChangeClothe(str, str2);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{AiAssistantEvent.PLAY_VIDEO, AiAssistantEvent.PLAY_VIDEO_TTSEND, AiAssistantEvent.MESSAGE_OPEN, AiAssistantEvent.MESSAGE_CLOSE, AiAssistantEvent.GUI_OPEN_VIDEO, AiAssistantEvent.XIAOP_DANCE, AiAssistantEvent.XIAOP_CHANGE_CLOTHE};
    }
}
