package com.xiaopeng.speech.protocol.node.internal;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.protocol.event.InternalEvent;

/* loaded from: classes2.dex */
public class InternalNode_Processor implements ICommandProcessor {
    private InternalNode mTarget;

    public InternalNode_Processor(InternalNode internalNode) {
        this.mTarget = internalNode;
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String str, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -1771481023:
                if (str.equals(InternalEvent.REBOOT_COMPLETE)) {
                    c = 0;
                    break;
                }
                break;
            case 735086539:
                if (str.equals(InternalEvent.INPUT_DM_DATA)) {
                    c = 1;
                    break;
                }
                break;
            case 1211476952:
                if (str.equals(InternalEvent.LOCAL_WAKEUP_RESULT)) {
                    c = 2;
                    break;
                }
                break;
            case 1445372987:
                if (str.equals(InternalEvent.RESOURCE_UPDATE_FINISH)) {
                    c = 3;
                    break;
                }
                break;
            case 1795896838:
                if (str.equals(InternalEvent.DM_OUTPUT)) {
                    c = 4;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                this.mTarget.onRebootComplete(str, str2);
                return;
            case 1:
                this.mTarget.onInputDmData(str, str2);
                return;
            case 2:
                this.mTarget.onLocalWakeupResult(str, str2);
                return;
            case 3:
                this.mTarget.onResourceUpdateFinish(str, str2);
                return;
            case 4:
                this.mTarget.onDmOutput(str, str2);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{InternalEvent.DM_OUTPUT, InternalEvent.INPUT_DM_DATA, InternalEvent.LOCAL_WAKEUP_RESULT, InternalEvent.RESOURCE_UPDATE_FINISH, InternalEvent.REBOOT_COMPLETE};
    }
}
