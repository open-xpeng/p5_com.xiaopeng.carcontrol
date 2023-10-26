package com.xiaopeng.speech.protocol.node.changba;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.protocol.event.ChangbaEvent;

/* loaded from: classes2.dex */
public class ChangbaNode_Processor implements ICommandProcessor {
    private ChangbaNode mTarget;

    public ChangbaNode_Processor(ChangbaNode changbaNode) {
        this.mTarget = changbaNode;
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String str, String str2) {
        str.hashCode();
        if (str.equals(ChangbaEvent.MUSIC_CHANGBA_SEARCH)) {
            this.mTarget.onMusicChangbaSearch(str, str2);
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{ChangbaEvent.MUSIC_CHANGBA_SEARCH};
    }
}
