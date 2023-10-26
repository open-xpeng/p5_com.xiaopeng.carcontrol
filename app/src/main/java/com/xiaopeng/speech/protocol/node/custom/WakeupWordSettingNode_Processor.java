package com.xiaopeng.speech.protocol.node.custom;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.protocol.event.WakeupTestEvent;

/* loaded from: classes2.dex */
public class WakeupWordSettingNode_Processor implements ICommandProcessor {
    private WakeupWordSettingNode mTarget;

    public WakeupWordSettingNode_Processor(WakeupWordSettingNode wakeupWordSettingNode) {
        this.mTarget = wakeupWordSettingNode;
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String str, String str2) {
        str.hashCode();
        if (str.equals(WakeupTestEvent.WAKEUP_WORD_SETTING_DONE)) {
            this.mTarget.onSettingDone(str, str2);
        } else if (str.equals(WakeupTestEvent.WAKEUP_WORD_MANUAL_INPUT)) {
            this.mTarget.onManualInput(str, str2);
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{WakeupTestEvent.WAKEUP_WORD_MANUAL_INPUT, WakeupTestEvent.WAKEUP_WORD_SETTING_DONE};
    }
}
