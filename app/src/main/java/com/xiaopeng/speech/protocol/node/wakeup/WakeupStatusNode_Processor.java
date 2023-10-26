package com.xiaopeng.speech.protocol.node.wakeup;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.jarvisproto.WakeupStatus;

/* loaded from: classes2.dex */
public class WakeupStatusNode_Processor implements ICommandProcessor {
    private WakeupStatusNode mTarget;

    public WakeupStatusNode_Processor(WakeupStatusNode wakeupStatusNode) {
        this.mTarget = wakeupStatusNode;
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String str, String str2) {
        str.hashCode();
        if (str.equals(WakeupStatus.EVENT)) {
            this.mTarget.onWakeupStatusChanged(str, str2);
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{WakeupStatus.EVENT};
    }
}
