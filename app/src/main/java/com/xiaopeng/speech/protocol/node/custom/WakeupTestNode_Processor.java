package com.xiaopeng.speech.protocol.node.custom;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.protocol.event.WakeupTestEvent;

/* loaded from: classes2.dex */
public class WakeupTestNode_Processor implements ICommandProcessor {
    private WakeupTestNode mTarget;

    public WakeupTestNode_Processor(WakeupTestNode wakeupTestNode) {
        this.mTarget = wakeupTestNode;
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String str, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -876394041:
                if (str.equals(WakeupTestEvent.VAD_BEGIN)) {
                    c = 0;
                    break;
                }
                break;
            case -654737660:
                if (str.equals(WakeupTestEvent.WAKEUP_SUCC)) {
                    c = 1;
                    break;
                }
                break;
            case 1733167481:
                if (str.equals(WakeupTestEvent.VAD_END)) {
                    c = 2;
                    break;
                }
                break;
            case 1766842495:
                if (str.equals(WakeupTestEvent.WAKEUP_FAILED)) {
                    c = 3;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                this.mTarget.onVADBeginSpeech(str, str2);
                return;
            case 1:
                this.mTarget.onWakeupSucced(str, str2);
                return;
            case 2:
                this.mTarget.onVADEndSpeech(str, str2);
                return;
            case 3:
                this.mTarget.onWakeupFailed(str, str2);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{WakeupTestEvent.VAD_BEGIN, WakeupTestEvent.VAD_END, WakeupTestEvent.WAKEUP_SUCC, WakeupTestEvent.WAKEUP_FAILED};
    }
}
