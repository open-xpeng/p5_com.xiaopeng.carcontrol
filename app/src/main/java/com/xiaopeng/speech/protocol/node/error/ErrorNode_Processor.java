package com.xiaopeng.speech.protocol.node.error;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.protocol.event.ErrorEvent;

/* loaded from: classes2.dex */
public class ErrorNode_Processor implements ICommandProcessor {
    private ErrorNode mTarget;

    public ErrorNode_Processor(ErrorNode errorNode) {
        this.mTarget = errorNode;
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String str, String str2) {
        str.hashCode();
        if (str.equals(ErrorEvent.ERROR_ASR_RESULT)) {
            this.mTarget.onErrorAsrResult(str, str2);
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{ErrorEvent.ERROR_ASR_RESULT};
    }
}
