package com.xiaopeng.carcontrol.model;

import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.xvs.xid.sync.api.ICarControlSync;

/* loaded from: classes2.dex */
public class D55DataSyncModel extends DataSyncModel {
    private static final String TAG = "D55DataSyncModel";

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public void setDriveAutoLock(boolean enable) {
        LogUtils.i(TAG, "setDriveAutoLock: " + enable);
        this.mSyncDataValue.setDriveAutoLock(enable);
        save(ICarControlSync.DRIVE_AUTO_LOCK, String.valueOf(enable));
        SharedPreferenceUtil.setDriveAutoLock(enable);
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel
    public boolean getDriveAutoLock() {
        return this.mSyncDataValue.isDriveAutoLock();
    }
}
