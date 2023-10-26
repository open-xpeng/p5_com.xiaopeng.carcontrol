package com.xiaopeng.speech.protocol.query.xpu;

import com.xiaopeng.speech.annotation.IQueryProcessor;
import com.xiaopeng.speech.protocol.event.query.speech.QueryXpuEvent;

/* loaded from: classes2.dex */
public class XpuQuery_Processor implements IQueryProcessor {
    private XpuQuery mTarget;

    public XpuQuery_Processor(XpuQuery xpuQuery) {
        this.mTarget = xpuQuery;
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public Object querySensor(String str, String str2) {
        str.hashCode();
        if (str.equals(QueryXpuEvent.XPU_IS_ON_AUTOPILOT)) {
            return Integer.valueOf(this.mTarget.getAutoPilotStatus(str, str2));
        }
        if (str.equals(QueryXpuEvent.XPU_IS_ON_ALC)) {
            return Integer.valueOf(this.mTarget.getALCStatus(str, str2));
        }
        return null;
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public String[] getQueryEvents() {
        return new String[]{QueryXpuEvent.XPU_IS_ON_AUTOPILOT, QueryXpuEvent.XPU_IS_ON_ALC};
    }
}
