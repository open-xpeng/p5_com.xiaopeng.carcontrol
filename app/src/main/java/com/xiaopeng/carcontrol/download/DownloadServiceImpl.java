package com.xiaopeng.carcontrol.download;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import com.xiaopeng.carcontrol.download.listener.DownloadListener;
import com.xiaopeng.carcontrol.download.listener.UriObserverListener;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class DownloadServiceImpl extends Binder implements UriObserverListener {
    private static final String TAG = "DownloadServiceImpl";
    private Context context;
    private BroadcastReceiver downloadReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.carcontrol.download.DownloadServiceImpl.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            LogUtils.d(DownloadServiceImpl.TAG, "action : " + action);
            if ("android.intent.action.DOWNLOAD_COMPLETE".equals(action)) {
                long longExtra = intent.getLongExtra("extra_download_id", -1L);
                if (longExtra != -1) {
                    DownloadServiceImpl.this.query(longExtra);
                }
            }
        }
    };
    private List<DownloadListener> listeners;

    /* JADX INFO: Access modifiers changed from: package-private */
    public DownloadServiceImpl(Context context) {
        this.context = context;
        DownloadProcessor.getInstance().init(this);
    }

    public void onCreate() {
        Context context = this.context;
        if (context != null) {
            context.registerReceiver(this.downloadReceiver, new IntentFilter("android.intent.action.DOWNLOAD_COMPLETE"));
        }
    }

    public void onDestroy() {
        Context context = this.context;
        if (context != null) {
            context.unregisterReceiver(this.downloadReceiver);
            DownloadProcessor.getInstance().release();
        }
        List<DownloadListener> list = this.listeners;
        if (list != null) {
            list.clear();
        }
    }

    public void setDownloadListener(DownloadListener listener) {
        if (this.listeners == null) {
            this.listeners = new ArrayList();
        }
        if (this.listeners.contains(listener)) {
            return;
        }
        this.listeners.add(listener);
    }

    public void start(final RequestInfo requestInfo) {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.download.-$$Lambda$DownloadServiceImpl$DXd5MWJQgBMBlL6x-BW_KyiCn1s
            @Override // java.lang.Runnable
            public final void run() {
                DownloadServiceImpl.this.lambda$start$0$DownloadServiceImpl(requestInfo);
            }
        });
    }

    public /* synthetic */ void lambda$start$0$DownloadServiceImpl(final RequestInfo requestInfoF) {
        int start = DownloadProcessor.getInstance().start(requestInfoF);
        List<DownloadListener> list = this.listeners;
        if (list != null) {
            for (DownloadListener downloadListener : list) {
                downloadListener.onStart(requestInfoF, start);
            }
        }
        LogUtils.d(TAG, "start result = " + start);
    }

    public void pause(final long id) {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.download.-$$Lambda$DownloadServiceImpl$9nt4VhEydwShYqk9xh-mpdj070g
            @Override // java.lang.Runnable
            public final void run() {
                DownloadServiceImpl.this.lambda$pause$1$DownloadServiceImpl(id);
            }
        });
    }

    public /* synthetic */ void lambda$pause$1$DownloadServiceImpl(final long id) {
        int pause = DownloadProcessor.getInstance().pause(id);
        List<DownloadListener> list = this.listeners;
        if (list != null) {
            for (DownloadListener downloadListener : list) {
                downloadListener.onPause(id, pause);
            }
        }
        LogUtils.d(TAG, "pause result = " + pause + ", id = " + id);
    }

    public void resume(final long id) {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.download.-$$Lambda$DownloadServiceImpl$fKfWuDnucGK2wvn9QNSV3iTAXkI
            @Override // java.lang.Runnable
            public final void run() {
                DownloadServiceImpl.this.lambda$resume$2$DownloadServiceImpl(id);
            }
        });
    }

    public /* synthetic */ void lambda$resume$2$DownloadServiceImpl(final long id) {
        int resume = DownloadProcessor.getInstance().resume(id);
        List<DownloadListener> list = this.listeners;
        if (list != null) {
            for (DownloadListener downloadListener : list) {
                downloadListener.onResume(id, resume);
            }
        }
        LogUtils.d(TAG, "resume result = " + resume + ", id = " + id);
    }

    public void query(final long id) {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.download.-$$Lambda$DownloadServiceImpl$j3my6sZ9PehAlUGZYVHrKxDTUt4
            @Override // java.lang.Runnable
            public final void run() {
                DownloadServiceImpl.this.lambda$query$3$DownloadServiceImpl(id);
            }
        });
    }

    public /* synthetic */ void lambda$query$3$DownloadServiceImpl(final long id) {
        DownloadInfo query = DownloadProcessor.getInstance().query(id);
        List<DownloadListener> list = this.listeners;
        if (list != null) {
            for (DownloadListener downloadListener : list) {
                downloadListener.onDataChanged(query);
            }
        }
        LogUtils.d(TAG, "downloadInfo = " + query.toString());
    }

    @Override // com.xiaopeng.carcontrol.download.listener.UriObserverListener
    public void onUriChanged(long id) {
        query(id);
    }
}
