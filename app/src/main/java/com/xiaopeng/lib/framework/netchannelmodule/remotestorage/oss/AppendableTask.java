package com.xiaopeng.lib.framework.netchannelmodule.remotestorage.oss;

import android.os.Looper;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.alibaba.sdk.android.oss.model.AppendObjectRequest;
import com.alibaba.sdk.android.oss.model.AppendObjectResult;
import com.alibaba.sdk.android.oss.model.HeadObjectRequest;
import com.alibaba.sdk.android.oss.model.HeadObjectResult;
import com.xiaopeng.datalog.DataLogModuleEntry;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IDataLog;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.StorageException;
import com.xiaopeng.lib.framework.netchannelmodule.common.GlobalConfig;
import com.xiaopeng.lib.framework.netchannelmodule.remotestorage.exception.StorageExceptionImpl;
import com.xiaopeng.lib.framework.netchannelmodule.remotestorage.statistic.StorageCounter;
import com.xiaopeng.lib.framework.netchannelmodule.remotestorage.token.Token;
import com.xiaopeng.lib.framework.netchannelmodule.remotestorage.token.TokenRetriever;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.ThreadUtils;
import com.xiaopeng.lib.utils.info.BuildInfoUtils;
import com.xiaopeng.lib.utils.info.DeviceInfoUtils;

/* loaded from: classes2.dex */
public class AppendableTask extends BaseOssTask {
    private static int STATUS_OK = 200;
    private static final String TAG = "NetChannel-AppendableTask";
    private byte[] mUploadContent;

    public AppendableTask(Bucket bucket) {
        super(bucket);
    }

    @Override // com.xiaopeng.lib.framework.netchannelmodule.remotestorage.oss.BaseOssTask
    public void performRealTask() {
        TokenRetriever.getInstance().getTokenWithCallback(new FutureTaskCallback());
    }

    public AppendableTask append(byte[] bArr) throws StorageException {
        if (bArr == null || bArr.length == 0) {
            throw new StorageExceptionImpl(4);
        }
        this.mUploadContent = bArr;
        return this;
    }

    @Override // com.xiaopeng.lib.framework.netchannelmodule.remotestorage.oss.BaseOssTask
    public BaseOssTask build() throws StorageException {
        byte[] bArr = this.mUploadContent;
        if (bArr == null || bArr.length == 0) {
            throw new StorageExceptionImpl(4);
        }
        return super.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void executeAppendTask(Token token) {
        final OSS createOssClient = createOssClient(token.accessKeyId(), token.acessKeySecret(), token.securityToken());
        ThreadUtils.postBackground(new Runnable() { // from class: com.xiaopeng.lib.framework.netchannelmodule.remotestorage.oss.AppendableTask.1
            @Override // java.lang.Runnable
            public void run() {
                AppendableTask.this.appendToOssObject(createOssClient);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void appendToOssObject(OSS oss) {
        String str;
        if (BuildInfoUtils.isDebuggableVersion() && Thread.currentThread() == Looper.getMainLooper().getThread()) {
            throw new RuntimeException("Not allow to run in main thread.");
        }
        try {
            long tryToGetExistingObjectLength = tryToGetExistingObjectLength(oss);
            try {
                AppendObjectRequest appendObjectRequest = new AppendObjectRequest(bucketRootName(), this.mRemoteObjectKey, this.mUploadContent);
                appendObjectRequest.setPosition(tryToGetExistingObjectLength);
                AppendObjectResult appendObject = oss.appendObject(appendObjectRequest);
                if (STATUS_OK == appendObject.getStatusCode()) {
                    if (!DeviceInfoUtils.isInternationalVer()) {
                        IDataLog iDataLog = (IDataLog) Module.get(DataLogModuleEntry.class).get(IDataLog.class);
                        iDataLog.sendStatData(iDataLog.buildStat().setEventName(GlobalConfig.EVENT_NAME_SUCCESS).setProperty("pack", GlobalConfig.getApplicationSimpleName()).setProperty("method", RequestParameters.SUBRESOURCE_APPEND).setProperty("localSize", Integer.valueOf(this.mUploadContent.length)).setProperty("uploadPath", this.mRemoteObjectKey).setProperty("requestId", appendObject.getRequestId()).build());
                    }
                    doSuccess();
                    StorageCounter.getInstance().increaseSucceedWithSize(this.mUploadContent.length);
                    return;
                }
                String valueOf = String.valueOf(appendObject.getStatusCode());
                if (!DeviceInfoUtils.isInternationalVer()) {
                    IDataLog iDataLog2 = (IDataLog) Module.get(DataLogModuleEntry.class).get(IDataLog.class);
                    iDataLog2.sendStatData(iDataLog2.buildStat().setEventName(GlobalConfig.EVENT_NAME_FAIL).setProperty("pack", GlobalConfig.getApplicationSimpleName()).setProperty("method", RequestParameters.SUBRESOURCE_APPEND).setProperty("localSize", Integer.valueOf(this.mUploadContent.length)).setProperty("uploadPath", this.mRemoteObjectKey).setProperty("failReason", valueOf).setProperty(RequestParameters.UPLOAD_ID, (String) null).build());
                }
                str = "append error!";
                try {
                    LogUtils.e(TAG, str, valueOf);
                    doFailure(new StorageExceptionImpl(1025, valueOf));
                    StorageCounter.getInstance().increaseFailureWithCode(valueOf, 0L);
                } catch (Exception e) {
                    e = e;
                    if (!StorageCounter.isInternationVersion()) {
                        IDataLog iDataLog3 = (IDataLog) Module.get(DataLogModuleEntry.class).get(IDataLog.class);
                        iDataLog3.sendStatData(iDataLog3.buildStat().setEventName(GlobalConfig.EVENT_NAME_FAIL).setProperty("pack", GlobalConfig.getApplicationSimpleName()).setProperty("method", RequestParameters.SUBRESOURCE_APPEND).setProperty("localPath", this.mLocalFilePath).setProperty("localSize", Integer.valueOf(this.mUploadContent.length)).setProperty("uploadPath", this.mRemoteObjectKey).setProperty("failReason", e.toString()).setProperty(RequestParameters.UPLOAD_ID, (String) null).build());
                    }
                    LogUtils.e(TAG, str, e);
                    doFailure(new StorageExceptionImpl(1025, e.toString()));
                    StorageCounter.getInstance().increaseFailureWithCode(e.getMessage(), 0L);
                }
            } catch (Exception e2) {
                e = e2;
                str = "append error!";
            }
        } catch (Exception e3) {
            e = e3;
            str = "append error!";
        }
    }

    private long tryToGetExistingObjectLength(OSS oss) {
        try {
            HeadObjectResult headObject = oss.headObject(new HeadObjectRequest(bucketRootName(), this.mRemoteObjectKey));
            if (STATUS_OK == headObject.getStatusCode()) {
                return headObject.getMetadata().getContentLength();
            }
            return 0L;
        } catch (Exception unused) {
            return 0L;
        }
    }

    /* loaded from: classes2.dex */
    private class FutureTaskCallback implements TokenRetriever.IRetrievingCallback {
        private FutureTaskCallback() {
        }

        @Override // com.xiaopeng.lib.framework.netchannelmodule.remotestorage.token.TokenRetriever.IRetrievingCallback
        public void onFailure(StorageException storageException) {
            AppendableTask.this.doFailure(storageException);
            StorageCounter.getInstance().increaseFailureWithCode(String.valueOf((int) StorageException.REASON_GET_TOKEN_ERROR), 0L);
        }

        @Override // com.xiaopeng.lib.framework.netchannelmodule.remotestorage.token.TokenRetriever.IRetrievingCallback
        public void onSuccess(Token token) {
            AppendableTask.this.executeAppendTask(token);
        }
    }
}
