package com.xiaopeng.speech.protocol.node.speech.carcontrol;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.protocol.event.SpeechCarControlCmdEvent;

/* loaded from: classes2.dex */
public class SpeechCarControlNode_Processor implements ICommandProcessor {
    private SpeechCarControlNode mTarget;

    public SpeechCarControlNode_Processor(SpeechCarControlNode speechCarControlNode) {
        this.mTarget = speechCarControlNode;
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String str, String str2) {
        str.hashCode();
        if (str.equals(SpeechCarControlCmdEvent.CLOSE_CAR_CONTROL_SOC)) {
            this.mTarget.onCloseSoc(str, str2);
        } else if (str.equals(SpeechCarControlCmdEvent.OPEN_CAR_CONTROL_SOC)) {
            this.mTarget.onOpenSoc(str, str2);
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{SpeechCarControlCmdEvent.CLOSE_CAR_CONTROL_SOC, SpeechCarControlCmdEvent.OPEN_CAR_CONTROL_SOC};
    }
}
