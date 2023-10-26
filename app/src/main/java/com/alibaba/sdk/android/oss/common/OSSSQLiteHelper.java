package com.alibaba.sdk.android.oss.common;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/* loaded from: classes.dex */
public class OSSSQLiteHelper extends SQLiteOpenHelper {
    private static final String CREATE_TABLE_PART_INFO = "create table if not exists part_info(id INTEGER primary key,upload_id VARCHAR(255),num INTEGER,crc64 INTEGER,size INTEGER,etag VARCHAR(255))";
    public static final String TABLE_NAME_PART_INFO = "part_info";

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    public OSSSQLiteHelper(Context context) {
        this(context, "oss_android_sdk.db", null, 1);
    }

    public OSSSQLiteHelper(Context context, String str, SQLiteDatabase.CursorFactory cursorFactory, int i) {
        super(context, str, cursorFactory, i);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(CREATE_TABLE_PART_INFO);
    }
}
