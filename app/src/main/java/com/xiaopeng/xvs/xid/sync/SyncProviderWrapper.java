package com.xiaopeng.xvs.xid.sync;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.xiaopeng.xvs.xid.XId;
import com.xiaopeng.xvs.xid.base.ICallback;
import com.xiaopeng.xvs.xid.base.IException;
import com.xiaopeng.xvs.xid.base.IWrapper;
import com.xiaopeng.xvs.xid.sync.SyncProviderWrapper;
import com.xiaopeng.xvs.xid.sync.api.ISync;
import com.xiaopeng.xvs.xid.sync.api.OnSyncChangedListener;
import com.xiaopeng.xvs.xid.sync.api.SyncException;
import com.xiaopeng.xvs.xid.sync.api.SyncType;
import com.xiaopeng.xvs.xid.utils.L;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class SyncProviderWrapper implements IWrapper {
    private static final String TAG = "SyncProviderWrapper";
    private OnSyncChangedListener mListener;
    private final Handler mMainHandler = new Handler(Looper.getMainLooper());
    private final SyncProviderModule mSyncModule;

    /* JADX INFO: Access modifiers changed from: package-private */
    public SyncProviderWrapper(Application application) {
        this.mSyncModule = new SyncProviderModule(application);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.xvs.xid.sync.SyncProviderWrapper$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    public class AnonymousClass1 implements ICallback<SyncType, IException> {
        @Override // com.xiaopeng.xvs.xid.base.ICallback
        public void onFail(IException iException) {
        }

        AnonymousClass1() {
        }

        @Override // com.xiaopeng.xvs.xid.base.ICallback
        public void onSuccess(final SyncType syncType) {
            L.d(SyncProviderWrapper.TAG, "init: end type=" + syncType);
            if (SyncProviderWrapper.this.mListener != null) {
                SyncProviderWrapper.this.mMainHandler.post(new Runnable() { // from class: com.xiaopeng.xvs.xid.sync.-$$Lambda$SyncProviderWrapper$1$2CoO8G-zH020-wt08l49YB67z-g
                    @Override // java.lang.Runnable
                    public final void run() {
                        SyncProviderWrapper.AnonymousClass1.this.lambda$onSuccess$0$SyncProviderWrapper$1(syncType);
                    }
                });
            }
        }

        public /* synthetic */ void lambda$onSuccess$0$SyncProviderWrapper$1(SyncType syncType) {
            SyncProviderWrapper.this.mListener.OnSyncChanged(XId.getAccountApi().getUid(), syncType);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void init() {
        this.mSyncModule.init(new AnonymousClass1());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setSyncChangedListener(OnSyncChangedListener onSyncChangedListener, boolean z) {
        L.d(TAG, "setSyncChangedListener: callImmediately = [" + z + "]");
        this.mListener = onSyncChangedListener;
        if (z && this.mSyncModule.isInit()) {
            L.d(TAG, "setSyncChangedListener: server init completed, now callImmediately");
            this.mMainHandler.post(new Runnable() { // from class: com.xiaopeng.xvs.xid.sync.-$$Lambda$SyncProviderWrapper$mDv1pCZYhZ0KimBJ3FJ697mH7LE
                @Override // java.lang.Runnable
                public final void run() {
                    SyncProviderWrapper.this.lambda$setSyncChangedListener$0$SyncProviderWrapper();
                }
            });
        }
    }

    public /* synthetic */ void lambda$setSyncChangedListener$0$SyncProviderWrapper() {
        this.mListener.OnSyncChanged(XId.getAccountApi().getUid(), SyncType.ACCOUNT_CHANGED);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void removeSyncChangedListener() {
        L.d(TAG, "removeSyncChangedListener: ");
        this.mListener = null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void put(String str, String str2) {
        if (str.contains(ISync.EXTRA_SYNC_GROUP_KEY_SEPARATOR)) {
            throw new IllegalArgumentException(new SyncException(SyncException.ERROR_CODE_SYNC_ILLEGAL_KEY).getMessage());
        }
        this.mSyncModule.put(str, str2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void put(int i, String str, String str2) {
        if (str.contains(ISync.EXTRA_SYNC_GROUP_KEY_SEPARATOR)) {
            throw new IllegalArgumentException(new SyncException(SyncException.ERROR_CODE_SYNC_ILLEGAL_KEY).getMessage());
        }
        this.mSyncModule.put(i, str, str2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public String get(String str, String str2) {
        String str3 = this.mSyncModule.get(str);
        L.d(TAG, "get() called with: key = [" + str + "], result = [" + str3 + "], defaultValue = [" + str2 + "]");
        return TextUtils.isEmpty(str3) ? str2 : str3;
    }
}
