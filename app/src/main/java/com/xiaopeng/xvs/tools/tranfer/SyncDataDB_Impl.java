package com.xiaopeng.xvs.tools.tranfer;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomMasterTable;
import androidx.room.RoomOpenHelper;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.util.HashMap;
import java.util.HashSet;

/* loaded from: classes2.dex */
public class SyncDataDB_Impl extends SyncDataDB {
    private volatile SyncDataDao _syncDataDao;

    @Override // androidx.room.RoomDatabase
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration databaseConfiguration) {
        return databaseConfiguration.sqliteOpenHelperFactory.create(SupportSQLiteOpenHelper.Configuration.builder(databaseConfiguration.context).name(databaseConfiguration.name).callback(new RoomOpenHelper(databaseConfiguration, new RoomOpenHelper.Delegate(1) { // from class: com.xiaopeng.xvs.tools.tranfer.SyncDataDB_Impl.1
            @Override // androidx.room.RoomOpenHelper.Delegate
            public void createAllTables(SupportSQLiteDatabase supportSQLiteDatabase) {
                supportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `sync_data` (`appID` TEXT NOT NULL, `uid` INTEGER NOT NULL, `key` TEXT NOT NULL, `value` TEXT, PRIMARY KEY(`appID`, `uid`, `key`))");
                supportSQLiteDatabase.execSQL(RoomMasterTable.CREATE_QUERY);
                supportSQLiteDatabase.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"a912e748d0879e5e89ecc70e00720dad\")");
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void dropAllTables(SupportSQLiteDatabase supportSQLiteDatabase) {
                supportSQLiteDatabase.execSQL("DROP TABLE IF EXISTS `sync_data`");
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            protected void onCreate(SupportSQLiteDatabase supportSQLiteDatabase) {
                if (SyncDataDB_Impl.this.mCallbacks != null) {
                    int size = SyncDataDB_Impl.this.mCallbacks.size();
                    for (int i = 0; i < size; i++) {
                        ((RoomDatabase.Callback) SyncDataDB_Impl.this.mCallbacks.get(i)).onCreate(supportSQLiteDatabase);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onOpen(SupportSQLiteDatabase supportSQLiteDatabase) {
                SyncDataDB_Impl.this.mDatabase = supportSQLiteDatabase;
                SyncDataDB_Impl.this.internalInitInvalidationTracker(supportSQLiteDatabase);
                if (SyncDataDB_Impl.this.mCallbacks != null) {
                    int size = SyncDataDB_Impl.this.mCallbacks.size();
                    for (int i = 0; i < size; i++) {
                        ((RoomDatabase.Callback) SyncDataDB_Impl.this.mCallbacks.get(i)).onOpen(supportSQLiteDatabase);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            protected void validateMigration(SupportSQLiteDatabase supportSQLiteDatabase) {
                HashMap hashMap = new HashMap(4);
                hashMap.put(SyncDataEntity.COLUMN_NAME_APPID, new TableInfo.Column(SyncDataEntity.COLUMN_NAME_APPID, "TEXT", true, 1));
                hashMap.put("uid", new TableInfo.Column("uid", "INTEGER", true, 2));
                hashMap.put("key", new TableInfo.Column("key", "TEXT", true, 3));
                hashMap.put("value", new TableInfo.Column("value", "TEXT", false, 0));
                TableInfo tableInfo = new TableInfo("sync_data", hashMap, new HashSet(0), new HashSet(0));
                TableInfo read = TableInfo.read(supportSQLiteDatabase, "sync_data");
                if (!tableInfo.equals(read)) {
                    throw new IllegalStateException("Migration didn't properly handle sync_data(com.xiaopeng.xvs.tools.tranfer.SyncDataEntity).\n Expected:\n" + tableInfo + "\n Found:\n" + read);
                }
            }
        }, "a912e748d0879e5e89ecc70e00720dad", "b930d3b859c81d90400cce86a4822336")).build());
    }

    @Override // androidx.room.RoomDatabase
    protected InvalidationTracker createInvalidationTracker() {
        return new InvalidationTracker(this, "sync_data");
    }

    @Override // androidx.room.RoomDatabase
    public void clearAllTables() {
        super.assertNotMainThread();
        SupportSQLiteDatabase writableDatabase = super.getOpenHelper().getWritableDatabase();
        try {
            super.beginTransaction();
            writableDatabase.execSQL("DELETE FROM `sync_data`");
            super.setTransactionSuccessful();
        } finally {
            super.endTransaction();
            writableDatabase.query("PRAGMA wal_checkpoint(FULL)").close();
            if (!writableDatabase.inTransaction()) {
                writableDatabase.execSQL("VACUUM");
            }
        }
    }

    @Override // com.xiaopeng.xvs.tools.tranfer.SyncDataDB
    public SyncDataDao getSyncDataDao() {
        SyncDataDao syncDataDao;
        if (this._syncDataDao != null) {
            return this._syncDataDao;
        }
        synchronized (this) {
            if (this._syncDataDao == null) {
                this._syncDataDao = new SyncDataDao_Impl(this);
            }
            syncDataDao = this._syncDataDao;
        }
        return syncDataDao;
    }
}
