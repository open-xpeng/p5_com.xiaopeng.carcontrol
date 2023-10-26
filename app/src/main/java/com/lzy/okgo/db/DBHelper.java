package com.lzy.okgo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cookie.SerializableCookie;
import com.lzy.okgo.model.Progress;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes.dex */
class DBHelper extends SQLiteOpenHelper {
    private static final String DB_CACHE_NAME = "okgo.db";
    private static final int DB_CACHE_VERSION = 1;
    static final String TABLE_CACHE = "cache";
    static final String TABLE_COOKIE = "cookie";
    static final String TABLE_DOWNLOAD = "download";
    static final String TABLE_UPLOAD = "upload";
    static final Lock lock = new ReentrantLock();
    private TableEntity cacheTableEntity;
    private TableEntity cookieTableEntity;
    private TableEntity downloadTableEntity;
    private TableEntity uploadTableEntity;

    /* JADX INFO: Access modifiers changed from: package-private */
    public DBHelper() {
        this(OkGo.getInstance().getContext());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public DBHelper(Context context) {
        super(context, DB_CACHE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
        this.cacheTableEntity = new TableEntity("cache");
        this.cookieTableEntity = new TableEntity("cookie");
        this.downloadTableEntity = new TableEntity(TABLE_DOWNLOAD);
        this.uploadTableEntity = new TableEntity(TABLE_UPLOAD);
        this.cacheTableEntity.addColumn(new ColumnEntity("key", "VARCHAR", true, true)).addColumn(new ColumnEntity(CacheEntity.LOCAL_EXPIRE, "INTEGER")).addColumn(new ColumnEntity(CacheEntity.HEAD, "BLOB")).addColumn(new ColumnEntity("data", "BLOB"));
        this.cookieTableEntity.addColumn(new ColumnEntity(SerializableCookie.HOST, "VARCHAR")).addColumn(new ColumnEntity("name", "VARCHAR")).addColumn(new ColumnEntity(SerializableCookie.DOMAIN, "VARCHAR")).addColumn(new ColumnEntity("cookie", "BLOB")).addColumn(new ColumnEntity(SerializableCookie.HOST, "name", SerializableCookie.DOMAIN));
        this.downloadTableEntity.addColumn(new ColumnEntity(Progress.TAG, "VARCHAR", true, true)).addColumn(new ColumnEntity("url", "VARCHAR")).addColumn(new ColumnEntity(Progress.FOLDER, "VARCHAR")).addColumn(new ColumnEntity(Progress.FILE_PATH, "VARCHAR")).addColumn(new ColumnEntity(Progress.FILE_NAME, "VARCHAR")).addColumn(new ColumnEntity(Progress.FRACTION, "VARCHAR")).addColumn(new ColumnEntity(Progress.TOTAL_SIZE, "INTEGER")).addColumn(new ColumnEntity(Progress.CURRENT_SIZE, "INTEGER")).addColumn(new ColumnEntity("status", "INTEGER")).addColumn(new ColumnEntity("priority", "INTEGER")).addColumn(new ColumnEntity(Progress.DATE, "INTEGER")).addColumn(new ColumnEntity(Progress.REQUEST, "BLOB")).addColumn(new ColumnEntity(Progress.EXTRA1, "BLOB")).addColumn(new ColumnEntity(Progress.EXTRA2, "BLOB")).addColumn(new ColumnEntity(Progress.EXTRA3, "BLOB"));
        this.uploadTableEntity.addColumn(new ColumnEntity(Progress.TAG, "VARCHAR", true, true)).addColumn(new ColumnEntity("url", "VARCHAR")).addColumn(new ColumnEntity(Progress.FOLDER, "VARCHAR")).addColumn(new ColumnEntity(Progress.FILE_PATH, "VARCHAR")).addColumn(new ColumnEntity(Progress.FILE_NAME, "VARCHAR")).addColumn(new ColumnEntity(Progress.FRACTION, "VARCHAR")).addColumn(new ColumnEntity(Progress.TOTAL_SIZE, "INTEGER")).addColumn(new ColumnEntity(Progress.CURRENT_SIZE, "INTEGER")).addColumn(new ColumnEntity("status", "INTEGER")).addColumn(new ColumnEntity("priority", "INTEGER")).addColumn(new ColumnEntity(Progress.DATE, "INTEGER")).addColumn(new ColumnEntity(Progress.REQUEST, "BLOB")).addColumn(new ColumnEntity(Progress.EXTRA1, "BLOB")).addColumn(new ColumnEntity(Progress.EXTRA2, "BLOB")).addColumn(new ColumnEntity(Progress.EXTRA3, "BLOB"));
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(this.cacheTableEntity.buildTableString());
        sQLiteDatabase.execSQL(this.cookieTableEntity.buildTableString());
        sQLiteDatabase.execSQL(this.downloadTableEntity.buildTableString());
        sQLiteDatabase.execSQL(this.uploadTableEntity.buildTableString());
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (DBUtils.isNeedUpgradeTable(sQLiteDatabase, this.cacheTableEntity)) {
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS cache");
        }
        if (DBUtils.isNeedUpgradeTable(sQLiteDatabase, this.cookieTableEntity)) {
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS cookie");
        }
        if (DBUtils.isNeedUpgradeTable(sQLiteDatabase, this.downloadTableEntity)) {
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS download");
        }
        if (DBUtils.isNeedUpgradeTable(sQLiteDatabase, this.uploadTableEntity)) {
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS upload");
        }
        onCreate(sQLiteDatabase);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        onUpgrade(sQLiteDatabase, i, i2);
    }
}
