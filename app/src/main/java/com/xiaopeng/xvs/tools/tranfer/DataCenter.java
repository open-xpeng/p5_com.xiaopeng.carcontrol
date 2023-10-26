package com.xiaopeng.xvs.tools.tranfer;

import android.app.Application;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public final class DataCenter {
    static final String DATABASE_NAME = "sync_db_v1.db";
    static final int DB_VERSION = 1;
    private static final int DB_VERSION_BASE = 1;
    static final String TABLE_SYNC_DATA = "sync_data";
    private static final String TAG = "DataCenter";
    private SyncDataDB mDatabase;
    private final AtomicBoolean mInitFlag = new AtomicBoolean(false);

    public void init(Application application) {
        if (this.mInitFlag.getAndSet(true)) {
            return;
        }
        RoomDatabase.Builder databaseBuilder = Room.databaseBuilder(application, SyncDataDB.class, DATABASE_NAME);
        Migration[] migrations = getMigrations();
        if (migrations.length > 0) {
            databaseBuilder.addMigrations(migrations);
        }
        this.mDatabase = (SyncDataDB) databaseBuilder.build();
    }

    public void close() {
        SyncDataDB syncDataDB;
        if (!this.mInitFlag.get() || (syncDataDB = this.mDatabase) == null) {
            return;
        }
        syncDataDB.close();
    }

    public SyncDataDao getSyncDataDao() {
        return this.mDatabase.getSyncDataDao();
    }

    private Migration[] getMigrations() {
        return (Migration[]) new ArrayList().toArray(new Migration[0]);
    }
}
