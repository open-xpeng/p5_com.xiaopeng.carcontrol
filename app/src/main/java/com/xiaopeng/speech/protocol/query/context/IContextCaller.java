package com.xiaopeng.speech.protocol.query.context;

import com.xiaopeng.speech.IQueryCaller;

/* loaded from: classes2.dex */
public interface IContextCaller extends IQueryCaller {
    default int getInfoFlowOnePage() {
        return -1;
    }

    default int getInfoFlowScrollToBottom() {
        return -1;
    }

    default int getInfoFlowScrollToTop() {
        return -1;
    }

    int getWidgetCurrLocation();

    String getWidgetId();

    String getWidgetInfo();

    int getWidgetListSize();

    int getWidgetPageSize();

    String getWidgetType();
}
