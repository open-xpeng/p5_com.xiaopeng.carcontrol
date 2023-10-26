package com.xiaopeng.xvs.xid.sync;

import com.xiaopeng.xvs.xid.sync.api.ICarDemoSync;

/* loaded from: classes2.dex */
class CarDemoSyncImpl implements ICarDemoSync {
    SyncProviderWrapper mWrapper;

    /* JADX INFO: Access modifiers changed from: package-private */
    public CarDemoSyncImpl(SyncProviderWrapper syncProviderWrapper) {
        this.mWrapper = syncProviderWrapper;
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarDemoSync
    public String getKEY1(String str) {
        return this.mWrapper.get(ICarDemoSync.KEY1, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarDemoSync
    public void putKEY1(String str) {
        this.mWrapper.put(ICarDemoSync.KEY1, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarDemoSync
    public String getKEY2(String str) {
        return this.mWrapper.get(ICarDemoSync.KEY2, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarDemoSync
    public void putKEY2(String str) {
        this.mWrapper.put(ICarDemoSync.KEY2, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarDemoSync
    public String getKEY3(String str) {
        return this.mWrapper.get(ICarDemoSync.KEY3, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarDemoSync
    public void putKEY3(String str) {
        this.mWrapper.put(ICarDemoSync.KEY3, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarDemoSync
    public String getKEY4(String str) {
        return this.mWrapper.get(ICarDemoSync.KEY4, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarDemoSync
    public void putKEY4(String str) {
        this.mWrapper.put(ICarDemoSync.KEY4, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarDemoSync
    public String getKEY5(String str) {
        return this.mWrapper.get(ICarDemoSync.KEY5, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarDemoSync
    public void putKEY5(String str) {
        this.mWrapper.put(ICarDemoSync.KEY5, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarDemoSync
    public String getKeyString(String str) {
        return this.mWrapper.get(ICarDemoSync.KEY_STRING, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarDemoSync
    public void putKeyString(String str) {
        this.mWrapper.put(ICarDemoSync.KEY_STRING, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarDemoSync
    public String getKeyBool(String str) {
        return this.mWrapper.get(ICarDemoSync.KEY_BOOL, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarDemoSync
    public void putKeyBool(String str) {
        this.mWrapper.put(ICarDemoSync.KEY_BOOL, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarDemoSync
    public String getKeyInt(String str) {
        return this.mWrapper.get(ICarDemoSync.KEY_INT, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarDemoSync
    public void putKeyInt(String str) {
        this.mWrapper.put(ICarDemoSync.KEY_INT, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarDemoSync
    public String getKeyLong(String str) {
        return this.mWrapper.get(ICarDemoSync.KEY_LONG, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarDemoSync
    public void putKeyLong(String str) {
        this.mWrapper.put(ICarDemoSync.KEY_LONG, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarDemoSync
    public String getKeyFloat(String str) {
        return this.mWrapper.get(ICarDemoSync.KEY_FLOAT, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarDemoSync
    public void putKeyFloat(String str) {
        this.mWrapper.put(ICarDemoSync.KEY_FLOAT, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarDemoSync
    public String getKeyMap(String str) {
        return this.mWrapper.get(ICarDemoSync.KEY_MAP, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarDemoSync
    public void putKeyMap(String str) {
        this.mWrapper.put(ICarDemoSync.KEY_MAP, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarDemoSync
    public String getKeyJson(String str) {
        return this.mWrapper.get(ICarDemoSync.KEY_JSON, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.ICarDemoSync
    public void putKeyJson(String str) {
        this.mWrapper.put(ICarDemoSync.KEY_JSON, str);
    }
}
