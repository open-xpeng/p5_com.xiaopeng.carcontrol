package com.xiaopeng.lib.framework.netchannelmodule.remotestorage.oss;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
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
import com.xiaopeng.speech.jarvisproto.DMEnd;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes2.dex */
public class SimpleUploadTask extends BaseOssTask {
    private static final int EACH_PARTITION_SIZE = 5242880;
    private static final String TAG = "NetChannel-BaseOssTask-SimpleUploadTask";

    public SimpleUploadTask(Bucket bucket) {
        super(bucket);
    }

    @Override // com.xiaopeng.lib.framework.netchannelmodule.remotestorage.oss.BaseOssTask
    public void performRealTask() {
        TokenRetriever.getInstance().getTokenWithCallback(new FutureTaskCallback());
    }

    @Override // com.xiaopeng.lib.framework.netchannelmodule.remotestorage.oss.BaseOssTask
    public SimpleUploadTask build() throws StorageException {
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
        ThreadUtils.postBackground(new Runnable() { // from class: com.xiaopeng.lib.framework.netchannelmodule.remotestorage.oss.SimpleUploadTask.1
            @Override // java.lang.Runnable
            public void run() {
                if (SimpleUploadTask.this.mLocalFileSize > SimpleUploadTask.this.bucketMaxObjectSize()) {
                    SimpleUploadTask.this.upload2OssByPartition(createOssClient);
                } else {
                    SimpleUploadTask.this.upload2OSSByNormal(createOssClient);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void upload2OSSByNormal(OSS oss) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketRootName(), this.mRemoteObjectKey, this.mLocalFilePath);
        if (this.mCallbackParams != null) {
            putObjectRequest.setCallbackParam(this.mCallbackParams);
        }
        oss.asyncPutObject(putObjectRequest, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() { // from class: com.xiaopeng.lib.framework.netchannelmodule.remotestorage.oss.SimpleUploadTask.2
            @Override // com.alibaba.sdk.android.oss.callback.OSSCompletedCallback
            public void onSuccess(PutObjectRequest putObjectRequest2, PutObjectResult putObjectResult) {
                if (!StorageCounter.isInternationVersion()) {
                    IDataLog iDataLog = (IDataLog) Module.get(DataLogModuleEntry.class).get(IDataLog.class);
                    iDataLog.sendStatData(iDataLog.buildMoleEvent().setEvent(GlobalConfig.EVENT_NAME_SUCCESS).setPageId(GlobalConfig.MOLE_PAGE_ID).setButtonId(GlobalConfig.MOLE_OSS_SUCCEED_BUTTON_ID).setProperty("pack", GlobalConfig.getApplicationSimpleName()).setProperty("method", DMEnd.REASON_NORMAL).setProperty("localPath", SimpleUploadTask.this.mLocalFilePath).setProperty("localSize", Long.valueOf(SimpleUploadTask.this.mLocalFileSize)).setProperty("uploadPath", SimpleUploadTask.this.mRemoteObjectKey).build());
                }
                SimpleUploadTask.this.doSuccess();
                StorageCounter.getInstance().increaseSucceedWithSize(SimpleUploadTask.this.mLocalFileSize);
            }

            @Override // com.alibaba.sdk.android.oss.callback.OSSCompletedCallback
            public void onFailure(PutObjectRequest putObjectRequest2, ClientException clientException, ServiceException serviceException) {
                String str = "clientException:" + clientException + " serviceException:" + serviceException;
                if (!StorageCounter.isInternationVersion()) {
                    IDataLog iDataLog = (IDataLog) Module.get(DataLogModuleEntry.class).get(IDataLog.class);
                    iDataLog.sendStatData(iDataLog.buildMoleEvent().setEvent(GlobalConfig.EVENT_NAME_FAIL).setPageId(GlobalConfig.MOLE_PAGE_ID).setButtonId(GlobalConfig.MOLE_OSS_FAILED_BUTTON_ID).setProperty("pack", GlobalConfig.getApplicationSimpleName()).setProperty("method", DMEnd.REASON_NORMAL).setProperty("localPath", SimpleUploadTask.this.mLocalFilePath).setProperty("localSize", Long.valueOf(SimpleUploadTask.this.mLocalFileSize)).setProperty("uploadPath", SimpleUploadTask.this.mRemoteObjectKey).setProperty("failReason", str).build());
                }
                if (clientException != null) {
                    SimpleUploadTask.this.doFailure(new StorageExceptionImpl(1025, str));
                    StorageCounter.getInstance().increaseFailureWithCode(String.valueOf(1025), 0L);
                } else if (serviceException != null) {
                    SimpleUploadTask.this.doFailure(new StorageExceptionImpl(serviceException.getStatusCode(), str));
                    StorageCounter.getInstance().increaseFailureWithCode(serviceException.getErrorCode(), 0L);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:47:0x015e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void upload2OssByPartition(com.alibaba.sdk.android.oss.OSS r25) {
        /*
            Method dump skipped, instructions count: 474
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.lib.framework.netchannelmodule.remotestorage.oss.SimpleUploadTask.upload2OssByPartition(com.alibaba.sdk.android.oss.OSS):void");
    }

    private void closeFileStream(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                LogUtils.e(TAG, "Failed to close file: " + e);
            }
        }
    }

    /* loaded from: classes2.dex */
    private class FutureTaskCallback implements TokenRetriever.IRetrievingCallback {
        private FutureTaskCallback() {
        }

        @Override // com.xiaopeng.lib.framework.netchannelmodule.remotestorage.token.TokenRetriever.IRetrievingCallback
        public void onFailure(StorageException storageException) {
            if (!StorageCounter.isInternationVersion()) {
                IDataLog iDataLog = (IDataLog) Module.get(DataLogModuleEntry.class).get(IDataLog.class);
                iDataLog.sendStatData(iDataLog.buildMoleEvent().setEvent(GlobalConfig.EVENT_NAME_FAIL).setPageId(GlobalConfig.MOLE_PAGE_ID).setButtonId(GlobalConfig.MOLE_OSS_FAILED_BUTTON_ID).setProperty("pack", GlobalConfig.getApplicationSimpleName()).setProperty("method", "token").setProperty("localPath", SimpleUploadTask.this.mLocalFilePath).setProperty("localSize", Long.valueOf(SimpleUploadTask.this.mLocalFileSize)).setProperty("uploadPath", SimpleUploadTask.this.mRemoteObjectKey).setProperty("failReason", storageException.toString()).build());
            }
            SimpleUploadTask.this.doFailure(storageException);
            StorageCounter.getInstance().increaseFailureWithCode(String.valueOf((int) StorageException.REASON_GET_TOKEN_ERROR), 0L);
        }

        @Override // com.xiaopeng.lib.framework.netchannelmodule.remotestorage.token.TokenRetriever.IRetrievingCallback
        public void onSuccess(Token token) {
            try {
                SimpleUploadTask.this.executeUploadTask(token);
            } catch (StorageException e) {
                if (!StorageCounter.isInternationVersion()) {
                    IDataLog iDataLog = (IDataLog) Module.get(DataLogModuleEntry.class).get(IDataLog.class);
                    iDataLog.sendStatData(iDataLog.buildMoleEvent().setEvent(GlobalConfig.EVENT_NAME_FAIL).setPageId(GlobalConfig.MOLE_PAGE_ID).setButtonId(GlobalConfig.MOLE_OSS_FAILED_BUTTON_ID).setProperty("pack", GlobalConfig.getApplicationSimpleName()).setProperty("method", "token").setProperty("localPath", SimpleUploadTask.this.mLocalFilePath).setProperty("localSize", Long.valueOf(SimpleUploadTask.this.mLocalFileSize)).setProperty("uploadPath", SimpleUploadTask.this.mRemoteObjectKey).setProperty("failReason", e.toString()).build());
                }
                SimpleUploadTask.this.doFailure(e);
                StorageCounter.getInstance().increaseFailureWithCode(String.valueOf(e.getReasonCode()), 0L);
            }
        }
    }
}
