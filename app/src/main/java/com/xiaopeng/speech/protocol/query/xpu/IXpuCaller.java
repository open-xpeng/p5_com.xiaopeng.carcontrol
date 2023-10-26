package com.xiaopeng.speech.protocol.query.xpu;

import com.xiaopeng.speech.IQueryCaller;

/* loaded from: classes2.dex */
public interface IXpuCaller extends IQueryCaller {
    default int getALCStatus() {
        return -1;
    }

    default int getAutoPilotStatus() {
        return -1;
    }
}
