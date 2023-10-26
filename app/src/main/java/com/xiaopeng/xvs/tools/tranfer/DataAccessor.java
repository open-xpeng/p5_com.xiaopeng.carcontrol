package com.xiaopeng.xvs.tools.tranfer;

import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class DataAccessor {
    private static final String TAG = "DataAccessor";
    private SyncDataDao mDao;

    public DataAccessor(SyncDataDao syncDataDao) {
        this.mDao = syncDataDao;
    }

    public List<SyncDataEntity> queryUid(long j) {
        return this.mDao.query(j);
    }

    public List<SyncDataEntity> queryAll() {
        return this.mDao.queryAll();
    }

    public void erase(long j) {
        this.mDao.erase(j);
    }

    public List<Long> queryUid() {
        return this.mDao.queryUid();
    }

    public void saveData(List<SyncDataEntity> list) {
        this.mDao.save(list);
    }
}
