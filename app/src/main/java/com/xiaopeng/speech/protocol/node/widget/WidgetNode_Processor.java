package com.xiaopeng.speech.protocol.node.widget;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.protocol.event.WidgetEvent;

/* loaded from: classes2.dex */
public class WidgetNode_Processor implements ICommandProcessor {
    private WidgetNode mTarget;

    public WidgetNode_Processor(WidgetNode widgetNode) {
        this.mTarget = widgetNode;
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String str, String str2) {
        str.hashCode();
        if (str.equals(WidgetEvent.AC_WIDGET_ON)) {
            this.mTarget.onAcWidgetOn(str, str2);
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{WidgetEvent.AC_WIDGET_ON};
    }
}
