package com.xiaopeng.xvs.xid.sync;

import com.xiaopeng.xvs.xid.sync.api.ICarCameraSync;

/* loaded from: classes2.dex */
class CarCameraSyncImpl implements ICarCameraSync {
    SyncProviderWrapper mWrapper;

    /* JADX INFO: Access modifiers changed from: package-private */
    public CarCameraSyncImpl(SyncProviderWrapper syncProviderWrapper) {
        this.mWrapper = syncProviderWrapper;
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarCameraSync
    public String getTransChassis(String str) {
        return this.mWrapper.get(ICarCameraSync.KEY_TRANS_CHASSIS, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarCameraSync
    public void putTransChassis(String str) {
        this.mWrapper.put(ICarCameraSync.KEY_TRANS_CHASSIS, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarCameraSync
    public String getTransBody(String str) {
        return this.mWrapper.get(ICarCameraSync.KEY_TRANS_BODY, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarCameraSync
    public void putTransBody(String str) {
        this.mWrapper.put(ICarCameraSync.KEY_TRANS_BODY, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarCameraSync
    public String getBevMode(String str) {
        return this.mWrapper.get(ICarCameraSync.KEY_BEV_MODE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarCameraSync
    public void putBevMode(String str) {
        this.mWrapper.put(ICarCameraSync.KEY_BEV_MODE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarCameraSync
    public String getTurnAssistantSW(String str) {
        return this.mWrapper.get(ICarCameraSync.KEY_TURN_ASSISTANT_SW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarCameraSync
    public void putTurnAssistantSW(String str) {
        this.mWrapper.put(ICarCameraSync.KEY_TURN_ASSISTANT_SW, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarCameraSync
    public String getCarDrivingSceneNRALevel(String str) {
        return this.mWrapper.get(ICarCameraSync.KEY_CAR_DRIVING_SCENE_NRA_LEVEL, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarCameraSync
    public void setCarDrivingSceneNRALevel(String str) {
        this.mWrapper.put(ICarCameraSync.KEY_CAR_DRIVING_SCENE_NRA_LEVEL, str);
    }
}
