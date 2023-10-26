package com.xiaopeng.speech.protocol.query.aiassistant;

import com.xiaopeng.speech.annotation.IQueryProcessor;
import com.xiaopeng.speech.protocol.event.query.speech.QueryAiAssistantEvent;

/* loaded from: classes2.dex */
public class AiAssistantQuery_Processor implements IQueryProcessor {
    private AiAssistantQuery mTarget;

    public AiAssistantQuery_Processor(AiAssistantQuery aiAssistantQuery) {
        this.mTarget = aiAssistantQuery;
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public Object querySensor(String str, String str2) {
        str.hashCode();
        if (str.equals(QueryAiAssistantEvent.GUI_AI_VIDEO_OPEN)) {
            return Integer.valueOf(this.mTarget.getAiVideoOpenStatus(str, str2));
        }
        return null;
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public String[] getQueryEvents() {
        return new String[]{QueryAiAssistantEvent.GUI_AI_VIDEO_OPEN};
    }
}
