package com.xiaopeng.xvs.tools.tranfer;

/* loaded from: classes2.dex */
public class SyncDataEntity {
    public static final String COLUMN_NAME_APPID = "appID";
    public static final String COLUMN_NAME_KEY = "key";
    public static final String COLUMN_NAME_UID = "uid";
    public static final String COLUMN_NAME_VALUE = "value";
    public String appID;
    public String key;
    public long uid;
    public String value;

    public static SyncData toSyncData(SyncDataEntity syncDataEntity) {
        SyncData syncData = new SyncData();
        syncData.key = syncDataEntity.key;
        syncData.value = syncDataEntity.value;
        return syncData;
    }

    public static SyncDataEntity fromSyncData(String str, long j, SyncData syncData) {
        SyncDataEntity syncDataEntity = new SyncDataEntity();
        syncDataEntity.appID = str;
        syncDataEntity.uid = j;
        syncDataEntity.key = syncData.key;
        syncDataEntity.value = syncData.value;
        return syncDataEntity;
    }

    public String toString() {
        return "SyncDataEntity{appID='" + this.appID + "', uid=" + this.uid + ", key='" + this.key + "', value='" + this.value + "'}";
    }
}
