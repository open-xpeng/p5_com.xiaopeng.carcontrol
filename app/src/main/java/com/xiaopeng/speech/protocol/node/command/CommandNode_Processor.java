package com.xiaopeng.speech.protocol.node.command;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.protocol.event.CommandEvent;

/* loaded from: classes2.dex */
public class CommandNode_Processor implements ICommandProcessor {
    private CommandNode mTarget;

    public CommandNode_Processor(CommandNode commandNode) {
        this.mTarget = commandNode;
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String str, String str2) {
        str.hashCode();
        if (str.equals(CommandEvent.COMMAND_JOSN_POST)) {
            this.mTarget.onJsonPost(str, str2);
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{CommandEvent.COMMAND_JOSN_POST};
    }
}
