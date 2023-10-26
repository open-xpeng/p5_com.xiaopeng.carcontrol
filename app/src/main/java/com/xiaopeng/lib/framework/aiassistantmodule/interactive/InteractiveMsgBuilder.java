package com.xiaopeng.lib.framework.aiassistantmodule.interactive;

import com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.IInteractiveMsg;
import com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.IInteractiveMsgBuilder;

/* loaded from: classes2.dex */
public class InteractiveMsgBuilder implements IInteractiveMsgBuilder {
    private String mData;
    private int mMsgId;

    @Override // com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.IInteractiveMsgBuilder
    public IInteractiveMsgBuilder data(String str) {
        this.mData = str;
        return this;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.IInteractiveMsgBuilder
    public IInteractiveMsgBuilder msgId(int i) {
        this.mMsgId = i;
        return this;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.IInteractiveMsgBuilder
    public IInteractiveMsg build() {
        return new InteractiveMsg(this.mMsgId, this.mData);
    }
}
