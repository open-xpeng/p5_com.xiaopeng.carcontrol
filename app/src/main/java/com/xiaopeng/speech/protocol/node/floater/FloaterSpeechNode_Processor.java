package com.xiaopeng.speech.protocol.node.floater;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.protocol.event.FloaterSpeechEvent;

/* loaded from: classes2.dex */
public class FloaterSpeechNode_Processor implements ICommandProcessor {
    private FloaterSpeechNode mTarget;

    public FloaterSpeechNode_Processor(FloaterSpeechNode floaterSpeechNode) {
        this.mTarget = floaterSpeechNode;
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String str, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -1829252700:
                if (str.equals(FloaterSpeechEvent.XIAOP_EXPRESSION)) {
                    c = 0;
                    break;
                }
                break;
            case 1493310260:
                if (str.equals(FloaterSpeechEvent.SET_ANIM_STATE)) {
                    c = 1;
                    break;
                }
                break;
            case 1920721242:
                if (str.equals(FloaterSpeechEvent.CLOSE_WINDOW)) {
                    c = 2;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                this.mTarget.onXiaopExpression(str, str2);
                return;
            case 1:
                this.mTarget.onSetAnimState(str, str2);
                return;
            case 2:
                this.mTarget.onCloseWindow(str, str2);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{FloaterSpeechEvent.CLOSE_WINDOW, FloaterSpeechEvent.SET_ANIM_STATE, FloaterSpeechEvent.XIAOP_EXPRESSION};
    }
}
