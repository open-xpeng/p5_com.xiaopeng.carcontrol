package com.xiaopeng.xvs.xid.sync;

import com.xiaopeng.xvs.xid.sync.api.ICarDvrSync;

/* loaded from: classes2.dex */
class CarDvrSyncImpl implements ICarDvrSync {
    SyncProviderWrapper mWrapper;

    /* JADX INFO: Access modifiers changed from: package-private */
    public CarDvrSyncImpl(SyncProviderWrapper syncProviderWrapper) {
        this.mWrapper = syncProviderWrapper;
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarDvrSync
    public String getDvrBtSt(String str) {
        return this.mWrapper.get(ICarDvrSync.KEY_DVR_BT_ST, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarDvrSync
    public void putDvrBtSt(String str) {
        this.mWrapper.put(ICarDvrSync.KEY_DVR_BT_ST, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarDvrSync
    public String getDvrType(String str) {
        return this.mWrapper.get(ICarDvrSync.KEY_DVR_TYPE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarDvrSync
    public void putDvrType(String str) {
        this.mWrapper.put(ICarDvrSync.KEY_DVR_TYPE, str);
    }
}
