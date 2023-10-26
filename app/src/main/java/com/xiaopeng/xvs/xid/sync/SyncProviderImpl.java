package com.xiaopeng.xvs.xid.sync;

import android.content.ComponentName;
import android.content.Intent;
import com.xiaopeng.xvs.xid.XId;
import com.xiaopeng.xvs.xid.base.IClient;
import com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync;
import com.xiaopeng.xvs.xid.sync.api.ICarCameraSync;
import com.xiaopeng.xvs.xid.sync.api.ICarControlSync;
import com.xiaopeng.xvs.xid.sync.api.ICarDemoSync;
import com.xiaopeng.xvs.xid.sync.api.ICarDvrSync;
import com.xiaopeng.xvs.xid.sync.api.ICarSettingsSync;
import com.xiaopeng.xvs.xid.sync.api.ISync;
import com.xiaopeng.xvs.xid.sync.api.OnSyncChangedListener;
import com.xiaopeng.xvs.xid.sync.api.SyncException;
import com.xiaopeng.xvs.xid.utils.L;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class SyncProviderImpl extends IClient implements ISync {
    private static final String TAG = "SyncProviderImpl";
    private static volatile SyncProviderImpl mInstance;
    private final IAiAssistantSync mAiAssistantSync;
    private final ICarCameraSync mCarCameraSync;
    private final ICarControlSync mCarControlSync;
    private final ICarDemoSync mCarDemoSync;
    private final ICarDvrSync mCarDvrSync;
    private final ICarSettingsSync mCarSettingsSync;
    private final SyncProviderWrapper mContextWrapper;

    private SyncProviderImpl() {
        L.d(TAG, "SyncProviderImpl onCreate true");
        SyncProviderWrapper syncProviderWrapper = new SyncProviderWrapper(XId.getApplication());
        this.mContextWrapper = syncProviderWrapper;
        this.mCarControlSync = new CarControlSyncImpl(syncProviderWrapper);
        this.mAiAssistantSync = new AiAssistantSyncImpl(syncProviderWrapper);
        this.mCarCameraSync = new CarCameraSyncImpl(syncProviderWrapper);
        this.mCarDvrSync = new CarDvrSyncImpl(syncProviderWrapper);
        this.mCarSettingsSync = new CarSettingsSyncImpl(syncProviderWrapper);
        this.mCarDemoSync = new CarDemoSyncImpl(syncProviderWrapper);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static SyncProviderImpl getInstance() {
        if (mInstance == null) {
            synchronized (SyncProviderImpl.class) {
                if (mInstance == null) {
                    mInstance = new SyncProviderImpl();
                }
            }
        }
        return mInstance;
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ISync
    public void init() {
        this.mContextWrapper.init();
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ISync
    public void setSyncChangedListener(OnSyncChangedListener onSyncChangedListener, boolean z) {
        this.mContextWrapper.setSyncChangedListener(onSyncChangedListener, z);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ISync
    public void removeSyncChangedListener() {
        this.mContextWrapper.removeSyncChangedListener();
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ISync
    public ICarControlSync getCarControlSync() {
        if (!"xp_car_setting_car".equals(XId.getAppId())) {
            throw new IllegalArgumentException(new SyncException(SyncException.ERROR_CODE_SYNC_APP_ID_ERROR).getMessage());
        }
        return this.mCarControlSync;
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ISync
    public ICarSettingsSync getCarSettingsSync() {
        if (!XId.APP_ID_CAR_SETTINGS.equals(XId.getAppId()) && !XId.APP_ID_CAR_SETTINGS_D21.equals(XId.getAppId())) {
            throw new IllegalArgumentException(new SyncException(SyncException.ERROR_CODE_SYNC_APP_ID_ERROR).getMessage());
        }
        return this.mCarSettingsSync;
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ISync
    public IAiAssistantSync getAiAssistantSync() {
        if (!XId.APP_ID_AI_ASSISTANT.equals(XId.getAppId())) {
            throw new IllegalArgumentException(new SyncException(SyncException.ERROR_CODE_SYNC_APP_ID_ERROR).getMessage());
        }
        return this.mAiAssistantSync;
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ISync
    public ICarCameraSync getCarCameraSync() {
        if (!XId.APP_ID_CAR_CAMERA.equals(XId.getAppId())) {
            throw new IllegalArgumentException(new SyncException(SyncException.ERROR_CODE_SYNC_APP_ID_ERROR).getMessage());
        }
        return this.mCarCameraSync;
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ISync
    public ICarDvrSync getCarDvrSync() {
        if (!XId.APP_ID_CAR_DVR.equals(XId.getAppId())) {
            throw new IllegalArgumentException(new SyncException(SyncException.ERROR_CODE_SYNC_APP_ID_ERROR).getMessage());
        }
        return this.mCarDvrSync;
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ISync
    public ICarDemoSync getCarDemo() {
        if (!XId.APP_ID_CAR_DEMO.equals(XId.getAppId())) {
            throw new IllegalArgumentException(new SyncException(SyncException.ERROR_CODE_SYNC_APP_ID_ERROR).getMessage());
        }
        return this.mCarDemoSync;
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ISync
    public void requestCreateNewSyncGroup() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.xiaopeng.caraccount", "com.xiaopeng.caraccount.AccountService"));
        intent.setAction(ISync.ACTION_SERVICE_REQ_SYNC_GROUP_CREATE);
        XId.getApplication().startService(intent);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ISync
    public void requestSelectSyncGroupIndex(int i) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.xiaopeng.caraccount", "com.xiaopeng.caraccount.AccountService"));
        intent.setAction(ISync.ACTION_SERVICE_REQ_SYNC_GROUP_SELECT);
        intent.putExtra(ISync.EXTRA_NAME_GROUP_INDEX, i);
        XId.getApplication().startService(intent);
    }
}
