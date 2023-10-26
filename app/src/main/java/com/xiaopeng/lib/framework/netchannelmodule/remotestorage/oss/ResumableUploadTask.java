package com.xiaopeng.lib.framework.netchannelmodule.remotestorage.oss;

import android.os.Environment;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.alibaba.sdk.android.oss.model.ResumableUploadRequest;
import com.alibaba.sdk.android.oss.model.ResumableUploadResult;
import com.xiaopeng.datalog.DataLogModuleEntry;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IDataLog;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.StorageException;
import com.xiaopeng.lib.framework.netchannelmodule.common.GlobalConfig;
import com.xiaopeng.lib.framework.netchannelmodule.remotestorage.exception.StorageExceptionImpl;
import com.xiaopeng.lib.framework.netchannelmodule.remotestorage.statistic.StorageCounter;
import com.xiaopeng.lib.framework.netchannelmodule.remotestorage.token.Token;
import com.xiaopeng.lib.framework.netchannelmodule.remotestorage.token.TokenRetriever;
import com.xiaopeng.lib.framework.netchannelmodule.remotestorage.util.LogFileCleaner;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.ThreadUtils;
import java.io.File;

/* loaded from: classes2.dex */
public class ResumableUploadTask extends BaseOssTask {
    private static final String TAG = "NetChannel-ResumableUploadTask";
    private final String mOssTaskLogFolder;
    private long mUploadedSize;

    public ResumableUploadTask(Bucket bucket) {
        super(bucket);
        this.mUploadedSize = 0L;
        String initRecordDir = initRecordDir();
        this.mOssTaskLogFolder = initRecordDir;
        LogFileCleaner.getInstance().setLogFileFolder(initRecordDir);
    }

    @Override // com.xiaopeng.lib.framework.netchannelmodule.remotestorage.oss.BaseOssTask
    public void performRealTask() {
        TokenRetriever.getInstance().getTokenWithCallback(new FutureTaskCallback());
    }

    @Override // com.xiaopeng.lib.framework.netchannelmodule.remotestorage.oss.BaseOssTask
    public ResumableUploadTask build() throws StorageException {
        super.build();
        File file = new File(this.mLocalFilePath);
        if (!file.exists()) {
            throw new StorageExceptionImpl(513);
        }
        this.mLocalFileSize = file.length();
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void executeUploadTask(Token token) throws StorageException {
        final OSS createOssClient = createOssClient(token.accessKeyId(), token.acessKeySecret(), token.securityToken());
        ThreadUtils.postBackground(new Runnable() { // from class: com.xiaopeng.lib.framework.netchannelmodule.remotestorage.oss.ResumableUploadTask.1
            @Override // java.lang.Runnable
            public void run() {
                ResumableUploadTask.this.upload2OssByResumable(createOssClient);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void upload2OssByResumable(OSS oss) {
        ResumableUploadRequest resumableUploadRequest = new ResumableUploadRequest(bucketRootName(), this.mRemoteObjectKey, this.mLocalFilePath, initRecordDir());
        if (this.mCallbackParams != null) {
            resumableUploadRequest.setCallbackParam(this.mCallbackParams);
        }
        resumableUploadRequest.setProgressCallback(new OSSProgressCallback<ResumableUploadRequest>() { // from class: com.xiaopeng.lib.framework.netchannelmodule.remotestorage.oss.ResumableUploadTask.2
            @Override // com.alibaba.sdk.android.oss.callback.OSSProgressCallback
            public void onProgress(ResumableUploadRequest resumableUploadRequest2, long j, long j2) {
                ResumableUploadTask.this.mUploadedSize += j;
                LogUtils.d(ResumableUploadTask.TAG, "Uploading " + resumableUploadRequest2.getUploadFilePath() + ", current size " + j);
            }
        });
        oss.asyncResumableUpload(resumableUploadRequest, new OSSCompletedCallback<ResumableUploadRequest, ResumableUploadResult>() { // from class: com.xiaopeng.lib.framework.netchannelmodule.remotestorage.oss.ResumableUploadTask.3
            @Override // com.alibaba.sdk.android.oss.callback.OSSCompletedCallback
            public void onSuccess(ResumableUploadRequest resumableUploadRequest2, ResumableUploadResult resumableUploadResult) {
                LogUtils.d(ResumableUploadTask.TAG, "upload " + ResumableUploadTask.this.mLocalFilePath + " success!");
                if (!StorageCounter.isInternationVersion()) {
                    IDataLog iDataLog = (IDataLog) Module.get(DataLogModuleEntry.class).get(IDataLog.class);
                    iDataLog.sendStatData(iDataLog.buildStat().setEventName(GlobalConfig.EVENT_NAME_SUCCESS).setProperty("pack", GlobalConfig.getApplicationSimpleName()).setProperty("method", "resumable").setProperty("localPath", ResumableUploadTask.this.mLocalFilePath).setProperty("localSize", Long.valueOf(ResumableUploadTask.this.mLocalFileSize)).setProperty("uploadPath", ResumableUploadTask.this.mRemoteObjectKey).setProperty("uploadSize", Long.valueOf(ResumableUploadTask.this.mUploadedSize)).setProperty(RequestParameters.UPLOAD_ID, resumableUploadRequest2.getUploadId()).setProperty("requestId", resumableUploadResult.getRequestId()).build());
                }
                ResumableUploadTask.this.doSuccess();
                StorageCounter.getInstance().increaseSucceedWithSize(ResumableUploadTask.this.mLocalFileSize);
            }

            @Override // com.alibaba.sdk.android.oss.callback.OSSCompletedCallback
            public void onFailure(ResumableUploadRequest resumableUploadRequest2, ClientException clientException, ServiceException serviceException) {
                String str = "clientException:" + clientException + " serviceException:" + serviceException;
                if (!StorageCounter.isInternationVersion()) {
                    IDataLog iDataLog = (IDataLog) Module.get(DataLogModuleEntry.class).get(IDataLog.class);
                    iDataLog.sendStatData(iDataLog.buildStat().setEventName(GlobalConfig.EVENT_NAME_FAIL).setProperty("pack", GlobalConfig.getApplicationSimpleName()).setProperty("method", "resumable").setProperty("localPath", ResumableUploadTask.this.mLocalFilePath).setProperty("localSize", Long.valueOf(ResumableUploadTask.this.mLocalFileSize)).setProperty("uploadPath", ResumableUploadTask.this.mRemoteObjectKey).setProperty("uploadSize", Long.valueOf(ResumableUploadTask.this.mUploadedSize)).setProperty("failReason", str).setProperty(RequestParameters.UPLOAD_ID, resumableUploadRequest2.getUploadId()).build());
                }
                if (clientException != null) {
                    ResumableUploadTask.this.doFailure(new StorageExceptionImpl(1025, str));
                    StorageCounter.getInstance().increaseFailureWithCode(String.valueOf(1025), ResumableUploadTask.this.mUploadedSize);
                } else if (serviceException != null) {
                    ResumableUploadTask.this.doFailure(new StorageExceptionImpl(serviceException.getStatusCode(), str));
                    StorageCounter.getInstance().increaseFailureWithCode(serviceException.getErrorCode(), ResumableUploadTask.this.mUploadedSize);
                }
                LogUtils.d(ResumableUploadTask.TAG, "Uploaded " + ResumableUploadTask.this.mUploadedSize);
                LogFileCleaner.getInstance().cleanLogAsNeeded();
            }
        });
    }

    private String initRecordDir() {
        String str = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Log/oss_record/";
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
        }
        return str;
    }

    /* loaded from: classes2.dex */
    private class FutureTaskCallback implements TokenRetriever.IRetrievingCallback {
        private FutureTaskCallback() {
        }

        @Override // com.xiaopeng.lib.framework.netchannelmodule.remotestorage.token.TokenRetriever.IRetrievingCallback
        public void onFailure(StorageException storageException) {
            ResumableUploadTask.this.doFailure(storageException);
            StorageCounter.getInstance().increaseFailureWithCode(String.valueOf((int) StorageException.REASON_GET_TOKEN_ERROR), 0L);
        }

        @Override // com.xiaopeng.lib.framework.netchannelmodule.remotestorage.token.TokenRetriever.IRetrievingCallback
        public void onSuccess(Token token) {
            try {
                ResumableUploadTask.this.executeUploadTask(token);
            } catch (StorageException e) {
                ResumableUploadTask.this.doFailure(e);
                StorageCounter.getInstance().increaseFailureWithCode(String.valueOf(e.getReasonCode()), 0L);
            }
        }
    }
}
