package com.xiaopeng.speech.protocol.node.skill;

import com.xiaopeng.speech.INodeListener;

/* loaded from: classes2.dex */
public interface SkillDialogListener extends INodeListener {
    void onCloseWindow(String str);

    void onForwardScreenEvent(String str);

    void onOpenWindow(String str);

    void onRefreshSkillData(String str);

    void onShowDoubleGuide(String str);

    void onShowKeyGuide(String str);
}
