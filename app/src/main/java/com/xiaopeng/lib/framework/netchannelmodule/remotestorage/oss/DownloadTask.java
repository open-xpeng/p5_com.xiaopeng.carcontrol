package com.xiaopeng.lib.framework.netchannelmodule.remotestorage.oss;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.model.GetObjectRequest;
import com.alibaba.sdk.android.oss.model.GetObjectResult;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.StorageException;
import com.xiaopeng.lib.framework.netchannelmodule.remotestorage.exception.StorageExceptionImpl;
import com.xiaopeng.lib.framework.netchannelmodule.remotestorage.statistic.StorageCounter;
import com.xiaopeng.lib.framework.netchannelmodule.remotestorage.token.Token;
import com.xiaopeng.lib.framework.netchannelmodule.remotestorage.token.TokenRetriever;
import com.xiaopeng.lib.utils.FileUtils;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.ThreadUtils;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes2.dex */
public class DownloadTask extends BaseOssTask {
    private final int SUCCESS_CODE;
    private final String TAG;
    private final int UNKNOWN_LENGTH;

    public DownloadTask(Bucket bucket) {
        super(bucket);
        this.TAG = "NetChannel-DownloadTask";
        this.SUCCESS_CODE = 0;
        this.UNKNOWN_LENGTH = -1;
    }

    @Override // com.xiaopeng.lib.framework.netchannelmodule.remotestorage.oss.BaseOssTask
    public DownloadTask build() throws StorageException {
        super.build();
        this.mLocalFileSize = 0L;
        return this;
    }

    @Override // com.xiaopeng.lib.framework.netchannelmodule.remotestorage.oss.BaseOssTask
    public void performRealTask() {
        TokenRetriever.getInstance().getTokenWithCallback(new FutureTaskCallback());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void downloadFile(OSS oss) {
        oss.asyncGetObject(new GetObjectRequest(bucketRootName(), this.mRemoteObjectKey), new OSSCompletedCallback<GetObjectRequest, GetObjectResult>() { // from class: com.xiaopeng.lib.framework.netchannelmodule.remotestorage.oss.DownloadTask.1
            @Override // com.alibaba.sdk.android.oss.callback.OSSCompletedCallback
            public void onSuccess(GetObjectRequest getObjectRequest, GetObjectResult getObjectResult) {
                String str;
                int i;
                try {
                    DownloadTask.this.createFileAsNeeded();
                    i = DownloadTask.this.writeContentToFile(getObjectResult);
                    str = "";
                } catch (Exception e) {
                    String str2 = "Failed to write file:" + DownloadTask.this.mLocalFilePath + ", \n" + e;
                    LogUtils.e("NetChannel-DownloadTask", str2);
                    str = str2;
                    i = 515;
                }
                if (i == 0) {
                    LogUtils.e("Succeed to download file to :" + DownloadTask.this.mLocalFilePath + ", size:" + DownloadTask.this.mLocalFileSize);
                    DownloadTask.this.doSuccess();
                    StorageCounter.getInstance().increaseSucceedWithSize(DownloadTask.this.mLocalFileSize);
                    return;
                }
                FileUtils.deleteFile(DownloadTask.this.mLocalFilePath);
                DownloadTask.this.doFailure(new StorageExceptionImpl(i, str));
            }

            @Override // com.alibaba.sdk.android.oss.callback.OSSCompletedCallback
            public void onFailure(GetObjectRequest getObjectRequest, ClientException clientException, ServiceException serviceException) {
                DownloadTask.this.doFailure(new StorageExceptionImpl(serviceException != null ? serviceException.getStatusCode() : StorageException.REASON_DOWNLOAD_ERROR, "clientException:" + clientException + " serviceException:" + serviceException));
                StorageCounter.getInstance().increaseFailureWithCode(serviceException.getErrorCode(), 0L);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int writeContentToFile(GetObjectResult getObjectResult) {
        Closeable closeable;
        File file = new File(this.mLocalFilePath);
        Closeable closeable2 = null;
        int i = 0;
        try {
            byte[] bArr = new byte[4096];
            InputStream objectContent = getObjectResult.getObjectContent();
            try {
                closeable = new FileOutputStream(file, false);
                while (true) {
                    try {
                        int read = objectContent.read(bArr, 0, 4096);
                        if (read <= 0) {
                            break;
                        }
                        closeable.write(bArr, 0, read);
                        this.mLocalFileSize += read;
                    } catch (IOException e) {
                        e = e;
                        closeable2 = objectContent;
                        i = StorageException.REASON_DOWNLOAD_ERROR;
                        try {
                            LogUtils.e("NetChannel-DownloadTask", "Failed to download file:" + e);
                            closeStream(closeable2);
                            closeStream(closeable);
                            return getObjectResult.getContentLength() == -1 ? i : i;
                        } catch (Throwable th) {
                            th = th;
                            closeStream(closeable2);
                            closeStream(closeable);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        closeable2 = objectContent;
                        closeStream(closeable2);
                        closeStream(closeable);
                        throw th;
                    }
                }
                closeable.flush();
                closeStream(objectContent);
            } catch (IOException e2) {
                e = e2;
                closeable = null;
            } catch (Throwable th3) {
                th = th3;
                closeable = null;
            }
        } catch (IOException e3) {
            e = e3;
            closeable = null;
        } catch (Throwable th4) {
            th = th4;
            closeable = null;
        }
        closeStream(closeable);
        if (getObjectResult.getContentLength() == -1 && this.mLocalFileSize != getObjectResult.getContentLength()) {
            LogUtils.d("NetChannel-DownloadTask", "Not download completed. Expected:" + getObjectResult.getContentLength() + ", but actual:" + this.mLocalFileSize);
            return StorageException.REASON_DOWNLOAD_INCOMPLETE;
        }
    }

    private void closeStream(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                LogUtils.e("NetChannel-DownloadTask", "Failed to close stream: " + e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createFileAsNeeded() throws IOException {
        File file = new File(this.mLocalFilePath);
        if (file.exists()) {
            FileUtils.deleteFile(this.mLocalFilePath);
        }
        File parentFile = file.getParentFile();
        if (!parentFile.exists() && !parentFile.mkdirs()) {
            throw new IllegalStateException("Couldn't create dir: " + parentFile);
        }
        file.createNewFile();
    }

    /* loaded from: classes2.dex */
    private class FutureTaskCallback implements TokenRetriever.IRetrievingCallback {
        private FutureTaskCallback() {
        }

        @Override // com.xiaopeng.lib.framework.netchannelmodule.remotestorage.token.TokenRetriever.IRetrievingCallback
        public void onFailure(StorageException storageException) {
            DownloadTask.this.doFailure(storageException);
            StorageCounter.getInstance().increaseFailureWithCode(String.valueOf((int) StorageException.REASON_GET_TOKEN_ERROR), 0L);
        }

        @Override // com.xiaopeng.lib.framework.netchannelmodule.remotestorage.token.TokenRetriever.IRetrievingCallback
        public void onSuccess(Token token) {
            final OSS createOssClient = DownloadTask.this.createOssClient(token.accessKeyId(), token.acessKeySecret(), token.securityToken());
            ThreadUtils.postBackground(new Runnable() { // from class: com.xiaopeng.lib.framework.netchannelmodule.remotestorage.oss.DownloadTask.FutureTaskCallback.1
                @Override // java.lang.Runnable
                public void run() {
                    DownloadTask.this.downloadFile(createOssClient);
                }
            });
        }
    }
}
