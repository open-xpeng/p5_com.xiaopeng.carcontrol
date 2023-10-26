package com.xiaopeng.lib.framework.aiassistantmodule.interactive;

import android.os.Bundle;
import com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.IInteractiveMsg;

/* loaded from: classes2.dex */
public class InteractiveMsg implements IInteractiveMsg {
    private String mData;
    private int mMsgId;

    public InteractiveMsg(int i, String str) {
        this.mMsgId = i;
        this.mData = str;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.IInteractiveMsg
    public String getData() {
        return this.mData;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.IInteractiveMsg
    public int getMsgId() {
        return this.mMsgId;
    }

    public static InteractiveMsg from(Bundle bundle) {
        return new InteractiveMsg(bundle.getInt(Constants.MSG_ID, 0), bundle.getString("data"));
    }
}
