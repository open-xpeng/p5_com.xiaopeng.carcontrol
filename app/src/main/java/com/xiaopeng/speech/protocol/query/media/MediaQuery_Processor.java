package com.xiaopeng.speech.protocol.query.media;

import com.xiaopeng.speech.annotation.IQueryProcessor;
import com.xiaopeng.speech.protocol.event.query.QueryMediaEvent;

/* loaded from: classes2.dex */
public class MediaQuery_Processor implements IQueryProcessor {
    private MediaQuery mTarget;

    public MediaQuery_Processor(MediaQuery mediaQuery) {
        this.mTarget = mediaQuery;
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public Object querySensor(String str, String str2) {
        str.hashCode();
        if (str.equals(QueryMediaEvent.GET_INFO_QUERY)) {
            return this.mTarget.getMediaInfo(str, str2);
        }
        return null;
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public String[] getQueryEvents() {
        return new String[]{QueryMediaEvent.GET_INFO_QUERY};
    }
}
