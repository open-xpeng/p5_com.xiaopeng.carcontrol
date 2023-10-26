package com.xiaopeng.xvs.tools.tranfer;

import java.util.List;

/* loaded from: classes2.dex */
public interface SyncDataDao {
    void erase(long j);

    List<SyncDataEntity> query(long j);

    List<SyncDataEntity> queryAll();

    List<Long> queryUid();

    void save(List<SyncDataEntity> list);
}
