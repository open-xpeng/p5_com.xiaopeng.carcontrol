package com.xiaopeng.speech.protocol.query.appstore;

import com.xiaopeng.speech.annotation.IQueryProcessor;
import com.xiaopeng.speech.protocol.event.query.QueryAppStoreEvent;

/* loaded from: classes2.dex */
public class AppStoreQuery_Processor implements IQueryProcessor {
    private AppStoreQuery mTarget;

    public AppStoreQuery_Processor(AppStoreQuery appStoreQuery) {
        this.mTarget = appStoreQuery;
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public Object querySensor(String str, String str2) {
        str.hashCode();
        if (str.equals(QueryAppStoreEvent.GET_OPEN_STATUS)) {
            return Integer.valueOf(this.mTarget.getOpenStatus(str, str2));
        }
        return null;
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public String[] getQueryEvents() {
        return new String[]{QueryAppStoreEvent.GET_OPEN_STATUS};
    }
}
