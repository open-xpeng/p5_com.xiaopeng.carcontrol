package com.xiaopeng.xvs.xid.sync;

import android.app.Application;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import com.xiaopeng.xvs.xid.XId;
import com.xiaopeng.xvs.xid.base.ICallback;
import com.xiaopeng.xvs.xid.base.IException;
import com.xiaopeng.xvs.xid.sync.api.SyncException;
import com.xiaopeng.xvs.xid.sync.api.SyncType;
import com.xiaopeng.xvs.xid.utils.L;

/* loaded from: classes2.dex */
class SyncProviderModule {
    private static final String TAG = "SyncProviderModule";
    private final SyncValueTable mSyncValueTable;
    private final Handler mWorkHandler;

    /* JADX INFO: Access modifiers changed from: package-private */
    public SyncProviderModule(Application application) {
        HandlerThread handlerThread = new HandlerThread("xvs-sync-provider-thread");
        handlerThread.start();
        this.mWorkHandler = new Handler(handlerThread.getLooper());
        this.mSyncValueTable = new SyncValueTable(application, XId.getAppId());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void init(final ICallback<SyncType, IException> iCallback) {
        L.d(TAG, "init: ");
        try {
            this.mSyncValueTable.registerContentObserver(new ContentObserver(this.mWorkHandler) { // from class: com.xiaopeng.xvs.xid.sync.SyncProviderModule.1
                @Override // android.database.ContentObserver
                public void onChange(boolean z, Uri uri) {
                    String lastPathSegment = uri.getLastPathSegment();
                    SyncType syncType = SyncType.ACCOUNT_CHANGED;
                    try {
                        syncType = SyncType.valueOf(lastPathSegment);
                    } catch (Exception e) {
                        L.d(SyncProviderModule.TAG, "onChange: e=" + e.getMessage());
                    }
                    L.d(SyncProviderModule.TAG, "onChange: selfChange = [" + z + "], uri = [" + uri + "], segment = [" + lastPathSegment + "], type = [" + syncType + "]");
                    iCallback.onSuccess(syncType);
                }
            });
        } catch (Exception e) {
            iCallback.onFail(new SyncException(SyncException.ERROR_CODE_SYNC_CHANNEL_FATAL));
            L.e(TAG, "init fatal: e=" + e);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public String get(String str) {
        return this.mSyncValueTable.get(str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void put(String str, String str2) {
        L.d(TAG, "put: key = [" + str + "], value = [" + str2 + "]");
        this.mSyncValueTable.put(str, str2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void put(int i, String str, String str2) {
        L.d(TAG, "put: key = [" + str + "], value = [" + str2 + "]");
        this.mSyncValueTable.putIndex(i, str, str2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isInit() {
        String isInit = this.mSyncValueTable.isInit();
        L.d(TAG, "isInit: value=" + isInit);
        return "1".equals(isInit);
    }
}
