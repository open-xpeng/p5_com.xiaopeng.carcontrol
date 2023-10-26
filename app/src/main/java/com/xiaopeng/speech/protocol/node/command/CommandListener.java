package com.xiaopeng.speech.protocol.node.command;

import com.xiaopeng.speech.INodeListener;
import com.xiaopeng.speech.protocol.node.command.bean.CommandBean;

/* loaded from: classes2.dex */
public interface CommandListener extends INodeListener {
    void postCommand(CommandBean commandBean);
}
