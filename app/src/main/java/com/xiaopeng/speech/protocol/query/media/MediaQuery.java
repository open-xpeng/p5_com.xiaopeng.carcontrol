package com.xiaopeng.speech.protocol.query.media;

import com.xiaopeng.speech.SpeechQuery;

/* loaded from: classes2.dex */
public class MediaQuery extends SpeechQuery<IMediaQueryCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public String getMediaInfo(String str, String str2) {
        return ((IMediaQueryCaller) this.mQueryCaller).getMediaInfo();
    }
}
