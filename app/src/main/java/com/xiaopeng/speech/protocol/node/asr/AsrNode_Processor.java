package com.xiaopeng.speech.protocol.node.asr;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.jarvisproto.AsrEvent;

/* loaded from: classes2.dex */
public class AsrNode_Processor implements ICommandProcessor {
    private AsrNode mTarget;

    public AsrNode_Processor(AsrNode asrNode) {
        this.mTarget = asrNode;
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String str, String str2) {
        str.hashCode();
        if (str.equals(AsrEvent.EVENT)) {
            this.mTarget.onAsrEvent(str, str2);
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{AsrEvent.EVENT};
    }
}
