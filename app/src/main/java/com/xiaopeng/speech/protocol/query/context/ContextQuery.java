package com.xiaopeng.speech.protocol.query.context;

import com.xiaopeng.speech.SpeechQuery;

/* loaded from: classes2.dex */
public class ContextQuery extends SpeechQuery<IContextCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public int getWidgetListSize(String str, String str2) {
        return ((IContextCaller) this.mQueryCaller).getWidgetListSize();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getWidgetPageSize(String str, String str2) {
        return ((IContextCaller) this.mQueryCaller).getWidgetPageSize();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getWidgetCurrLocation(String str, String str2) {
        return ((IContextCaller) this.mQueryCaller).getWidgetCurrLocation();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getWidgetId(String str, String str2) {
        return ((IContextCaller) this.mQueryCaller).getWidgetId();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getWidgetType(String str, String str2) {
        return ((IContextCaller) this.mQueryCaller).getWidgetType();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getWidgetInfo(String str, String str2) {
        return ((IContextCaller) this.mQueryCaller).getWidgetInfo();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getInfoFlowScrollToTop(String str, String str2) {
        return ((IContextCaller) this.mQueryCaller).getInfoFlowScrollToTop();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getInfoFlowScrollToBottom(String str, String str2) {
        return ((IContextCaller) this.mQueryCaller).getInfoFlowScrollToBottom();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getInfoFlowOnePage(String str, String str2) {
        return ((IContextCaller) this.mQueryCaller).getInfoFlowOnePage();
    }
}
