package com.xiaopeng.lib.framework.netchannelmodule.remotestorage.oss;

import android.app.Application;
import android.text.TextUtils;
import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.Callback;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.StorageException;
import com.xiaopeng.lib.framework.netchannelmodule.remotestorage.exception.StorageExceptionImpl;
import com.xiaopeng.lib.framework.netchannelmodule.remotestorage.statistic.StorageCounter;
import com.xiaopeng.lib.framework.netchannelmodule.remotestorage.token.TokenRetriever;
import com.xiaopeng.lib.utils.LogUtils;
import java.util.Map;

/* loaded from: classes2.dex */
public abstract class BaseOssTask implements Runnable {
    private static final int CONNECTION_TIMEOUT = 60000;
    private static final int ERROR_FORBIDDEN = 403;
    private static final int MAX_CONCURRENT_REQUEST = 2;
    private static final int MAX_ERROR_RETRY = 3;
    private static final int SOCKET_TIMEOUT = 60000;
    private static final String TAG = "NetChannel-BaseOssTask";
    private static OSS sOssClient;
    private Application mApplication;
    private Bucket mBucket;
    private Callback mCallback;
    protected Map<String, String> mCallbackParams;
    protected String mLocalFilePath;
    protected long mLocalFileSize;
    private String mModuleName;
    private String mRemoteFolder;
    protected String mRemoteObjectKey;
    protected String mRemoteUrl;

    abstract void performRealTask();

    public BaseOssTask(Bucket bucket) {
        this.mBucket = bucket;
    }

    public BaseOssTask application(Application application) {
        this.mApplication = application;
        return this;
    }

    public BaseOssTask module(String str) throws IllegalArgumentException {
        if (!TextUtils.isEmpty(this.mRemoteFolder)) {
            throw new IllegalArgumentException("Remote folder has been assigned.");
        }
        this.mModuleName = str;
        return this;
    }

    public BaseOssTask remoteFolder(String str) throws IllegalArgumentException {
        if (!TextUtils.isEmpty(this.mModuleName)) {
            throw new IllegalArgumentException("Module name has been assigned.");
        }
        this.mRemoteFolder = str;
        return this;
    }

    public BaseOssTask filePath(String str) {
        this.mLocalFilePath = str;
        return this;
    }

    public BaseOssTask callback(Callback callback) {
        this.mCallback = callback;
        return this;
    }

    public BaseOssTask remoteCallbackParams(Map<String, String> map) {
        this.mCallbackParams = map;
        return this;
    }

    public BaseOssTask build() throws StorageException {
        if (TextUtils.isEmpty(this.mRemoteFolder) && TextUtils.isEmpty(this.mModuleName)) {
            throw new StorageExceptionImpl(3);
        }
        String str = this.mRemoteFolder;
        if (str == null) {
            this.mRemoteObjectKey = this.mBucket.generateObjectKey(this.mModuleName);
        } else {
            this.mRemoteObjectKey = str;
        }
        this.mRemoteUrl = this.mBucket.getUrl() + this.mRemoteObjectKey;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void doSuccess() {
        Callback callback = this.mCallback;
        if (callback != null) {
            callback.onSuccess(this.mRemoteUrl, this.mLocalFilePath);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void doFailure(StorageException storageException) {
        LogUtils.d(TAG, "Failed! Reason is-->" + storageException.getMessage());
        Callback callback = this.mCallback;
        if (callback != null) {
            callback.onFailure(this.mRemoteUrl, this.mLocalFilePath, storageException);
        }
        if (storageException.getReasonCode() == 403) {
            TokenRetriever.getInstance().clearToken();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String bucketRootName() {
        return this.mBucket.getRootName();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public long bucketMaxObjectSize() {
        return this.mBucket.getMaxObjectSize();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public synchronized OSS createOssClient(String str, String str2, String str3) {
        OSSStsTokenCredentialProvider oSSStsTokenCredentialProvider = new OSSStsTokenCredentialProvider(str, str2, str3);
        OSS oss = sOssClient;
        if (oss == null) {
            ClientConfiguration clientConfiguration = new ClientConfiguration();
            clientConfiguration.setConnectionTimeout(60000);
            clientConfiguration.setSocketTimeout(60000);
            clientConfiguration.setMaxConcurrentRequest(2);
            clientConfiguration.setMaxErrorRetry(3);
            sOssClient = new OSSClient(this.mApplication, Bucket.END_POINT, oSSStsTokenCredentialProvider, clientConfiguration);
        } else {
            oss.updateCredentialProvider(oSSStsTokenCredentialProvider);
        }
        return sOssClient;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.mCallback.onStart(this.mRemoteUrl, this.mLocalFilePath);
        performRealTask();
        StorageCounter.getInstance().increaseRequestCount();
    }
}
