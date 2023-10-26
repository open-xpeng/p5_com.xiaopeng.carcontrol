package com.xiaopeng.carcontrol.download.listener;

import com.xiaopeng.carcontrol.download.DownloadInfo;
import com.xiaopeng.carcontrol.download.RequestInfo;

/* loaded from: classes2.dex */
public interface DownloadListener {
    void onDataChanged(DownloadInfo info);

    void onPause(long id, int result);

    void onResume(long id, int result);

    void onStart(RequestInfo info, int result);
}
