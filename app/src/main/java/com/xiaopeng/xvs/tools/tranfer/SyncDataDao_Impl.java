package com.xiaopeng.xvs.tools.tranfer;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class SyncDataDao_Impl implements SyncDataDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter __insertionAdapterOfSyncDataEntity;
    private final SharedSQLiteStatement __preparedStmtOfErase;

    public SyncDataDao_Impl(RoomDatabase roomDatabase) {
        this.__db = roomDatabase;
        this.__insertionAdapterOfSyncDataEntity = new EntityInsertionAdapter<SyncDataEntity>(roomDatabase) { // from class: com.xiaopeng.xvs.tools.tranfer.SyncDataDao_Impl.1
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "INSERT OR REPLACE INTO `sync_data`(`appID`,`uid`,`key`,`value`) VALUES (?,?,?,?)";
            }

            @Override // androidx.room.EntityInsertionAdapter
            public void bind(SupportSQLiteStatement supportSQLiteStatement, SyncDataEntity syncDataEntity) {
                if (syncDataEntity.appID == null) {
                    supportSQLiteStatement.bindNull(1);
                } else {
                    supportSQLiteStatement.bindString(1, syncDataEntity.appID);
                }
                supportSQLiteStatement.bindLong(2, syncDataEntity.uid);
                if (syncDataEntity.key == null) {
                    supportSQLiteStatement.bindNull(3);
                } else {
                    supportSQLiteStatement.bindString(3, syncDataEntity.key);
                }
                if (syncDataEntity.value == null) {
                    supportSQLiteStatement.bindNull(4);
                } else {
                    supportSQLiteStatement.bindString(4, syncDataEntity.value);
                }
            }
        };
        this.__preparedStmtOfErase = new SharedSQLiteStatement(roomDatabase) { // from class: com.xiaopeng.xvs.tools.tranfer.SyncDataDao_Impl.2
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM sync_data WHERE uid = ?";
            }
        };
    }

    @Override // com.xiaopeng.xvs.tools.tranfer.SyncDataDao
    public void save(List<SyncDataEntity> list) {
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfSyncDataEntity.insert((Iterable) list);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.xiaopeng.xvs.tools.tranfer.SyncDataDao
    public void erase(long j) {
        SupportSQLiteStatement acquire = this.__preparedStmtOfErase.acquire();
        this.__db.beginTransaction();
        try {
            acquire.bindLong(1, j);
            acquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfErase.release(acquire);
        }
    }

    @Override // com.xiaopeng.xvs.tools.tranfer.SyncDataDao
    public List<SyncDataEntity> query(long j) {
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT * FROM sync_data WHERE uid = ?", 1);
        acquire.bindLong(1, j);
        Cursor query = this.__db.query(acquire);
        try {
            int columnIndexOrThrow = query.getColumnIndexOrThrow(SyncDataEntity.COLUMN_NAME_APPID);
            int columnIndexOrThrow2 = query.getColumnIndexOrThrow("uid");
            int columnIndexOrThrow3 = query.getColumnIndexOrThrow("key");
            int columnIndexOrThrow4 = query.getColumnIndexOrThrow("value");
            ArrayList arrayList = new ArrayList(query.getCount());
            while (query.moveToNext()) {
                SyncDataEntity syncDataEntity = new SyncDataEntity();
                syncDataEntity.appID = query.getString(columnIndexOrThrow);
                syncDataEntity.uid = query.getLong(columnIndexOrThrow2);
                syncDataEntity.key = query.getString(columnIndexOrThrow3);
                syncDataEntity.value = query.getString(columnIndexOrThrow4);
                arrayList.add(syncDataEntity);
            }
            return arrayList;
        } finally {
            query.close();
            acquire.release();
        }
    }

    @Override // com.xiaopeng.xvs.tools.tranfer.SyncDataDao
    public List<SyncDataEntity> queryAll() {
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT * FROM sync_data", 0);
        Cursor query = this.__db.query(acquire);
        try {
            int columnIndexOrThrow = query.getColumnIndexOrThrow(SyncDataEntity.COLUMN_NAME_APPID);
            int columnIndexOrThrow2 = query.getColumnIndexOrThrow("uid");
            int columnIndexOrThrow3 = query.getColumnIndexOrThrow("key");
            int columnIndexOrThrow4 = query.getColumnIndexOrThrow("value");
            ArrayList arrayList = new ArrayList(query.getCount());
            while (query.moveToNext()) {
                SyncDataEntity syncDataEntity = new SyncDataEntity();
                syncDataEntity.appID = query.getString(columnIndexOrThrow);
                syncDataEntity.uid = query.getLong(columnIndexOrThrow2);
                syncDataEntity.key = query.getString(columnIndexOrThrow3);
                syncDataEntity.value = query.getString(columnIndexOrThrow4);
                arrayList.add(syncDataEntity);
            }
            return arrayList;
        } finally {
            query.close();
            acquire.release();
        }
    }

    @Override // com.xiaopeng.xvs.tools.tranfer.SyncDataDao
    public List<Long> queryUid() {
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT uid FROM sync_data group by uid", 0);
        Cursor query = this.__db.query(acquire);
        try {
            ArrayList arrayList = new ArrayList(query.getCount());
            while (query.moveToNext()) {
                arrayList.add(query.isNull(0) ? null : Long.valueOf(query.getLong(0)));
            }
            return arrayList;
        } finally {
            query.close();
            acquire.release();
        }
    }
}
