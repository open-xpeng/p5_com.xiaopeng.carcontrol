package com.xiaopeng.lib.framework.netchannelmodule.remotestorage;

import android.app.Application;
import com.xiaopeng.datalog.DataLogModuleEntry;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.Callback;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.IRemoteStorage;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.StorageException;
import com.xiaopeng.lib.framework.netchannelmodule.common.GlobalConfig;
import com.xiaopeng.lib.framework.netchannelmodule.remotestorage.aws.BaseAwsTask;
import com.xiaopeng.lib.framework.netchannelmodule.remotestorage.aws.SimpleAwsUploadTask;
import com.xiaopeng.lib.framework.netchannelmodule.remotestorage.exception.StorageExceptionImpl;
import com.xiaopeng.lib.framework.netchannelmodule.remotestorage.oss.Bucket;
import com.xiaopeng.lib.framework.netchannelmodule.remotestorage.statistic.StorageCounter;
import com.xiaopeng.lib.utils.ThreadUtils;
import java.util.Map;

/* loaded from: classes2.dex */
public class RemoteAwsStorageImpl implements IRemoteStorage {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private Application mApplication;

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.IRemoteStorage
    public void initWithCategoryAndContext(Application application) {
        initWithContext(application);
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.IRemoteStorage
    public void initWithContext(Application application) {
        if (this.mApplication != null) {
            return;
        }
        this.mApplication = application;
        GlobalConfig.setApplicationContext(application);
        StorageCounter.getInstance().init(this.mApplication);
        Module.register(DataLogModuleEntry.class, new DataLogModuleEntry(this.mApplication));
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.IRemoteStorage
    public void uploadWithCallback(IRemoteStorage.CATEGORY category, String str, String str2, Callback callback) throws Exception {
        doPreCheck();
        ThreadUtils.postBackground(createUploadTask(Bucket.get(category).getRootName()).module(str).filePath(str2).callback(callback).build());
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.IRemoteStorage
    public void uploadWithCallback(IRemoteStorage.CATEGORY category, String str, String str2, Callback callback, Map<String, String> map) throws Exception {
        doPreCheck();
        ThreadUtils.postBackground(createUploadTask(Bucket.get(category).getRootName()).module(str).filePath(str2).callback(callback).remoteCallbackParams(map).build());
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.IRemoteStorage
    public void uploadWithPathAndCallback(String str, String str2, String str3, Callback callback) throws Exception {
        doPreCheck();
        ThreadUtils.postBackground(createUploadTask(str).remoteFolder(str2).filePath(str3).callback(callback).build());
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.IRemoteStorage
    public void uploadWithPathAndCallback(String str, String str2, String str3, Callback callback, Map<String, String> map) throws Exception {
        doPreCheck();
        ThreadUtils.postBackground(createUploadTask(str).remoteFolder(str2).filePath(str3).callback(callback).remoteCallbackParams(map).build());
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.IRemoteStorage
    public void appendWithPathAndCallback(String str, String str2, byte[] bArr, Callback callback) throws Exception {
        callback.onFailure(str, str2, new StorageExceptionImpl(1025));
    }

    private BaseAwsTask createUploadTask(String str) {
        return new SimpleAwsUploadTask(str);
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.IRemoteStorage
    public void downloadWithPathAndCallback(String str, String str2, String str3, Callback callback) {
        callback.onFailure(str, str2, new StorageExceptionImpl(StorageException.REASON_DOWNLOAD_ERROR));
    }

    private void doPreCheck() throws StorageException {
        if (this.mApplication == null) {
            throw new StorageExceptionImpl(1);
        }
        if (StorageCounter.getInstance().isExceedTrafficQuota()) {
            StorageCounter.getInstance().increaseRejectCount();
            throw new StorageExceptionImpl(StorageException.REASON_EXCEED_TRAFFIC_QUOTA);
        }
    }
}
