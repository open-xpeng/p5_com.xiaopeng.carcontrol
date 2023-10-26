package com.xiaopeng.speech.protocol.query.speech.combo;

import com.xiaopeng.speech.IQueryCaller;
import com.xiaopeng.speech.jarvisproto.DMEnd;

/* loaded from: classes2.dex */
public interface IComboQueryCaller extends IQueryCaller {
    default String checkEnterUserMode(String str) {
        return DMEnd.REASON_NORMAL;
    }

    default String enterUserMode(String str) {
        return DMEnd.REASON_NORMAL;
    }

    default String exitUserMode(String str) {
        return DMEnd.REASON_NORMAL;
    }

    default String getCurrentUserMode() {
        return DMEnd.REASON_NORMAL;
    }
}
