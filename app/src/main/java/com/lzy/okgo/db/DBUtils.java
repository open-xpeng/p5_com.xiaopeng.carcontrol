package com.lzy.okgo.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.lzy.okgo.utils.OkLogger;

/* loaded from: classes.dex */
public class DBUtils {
    public static boolean isNeedUpgradeTable(SQLiteDatabase sQLiteDatabase, TableEntity tableEntity) {
        if (isTableExists(sQLiteDatabase, tableEntity.tableName)) {
            Cursor rawQuery = sQLiteDatabase.rawQuery("select * from " + tableEntity.tableName, null);
            if (rawQuery == null) {
                return false;
            }
            try {
                int columnCount = tableEntity.getColumnCount();
                if (columnCount == rawQuery.getColumnCount()) {
                    for (int i = 0; i < columnCount; i++) {
                        if (tableEntity.getColumnIndex(rawQuery.getColumnName(i)) == -1) {
                            return true;
                        }
                    }
                    return false;
                }
                return true;
            } finally {
                rawQuery.close();
            }
        }
        return true;
    }

    public static boolean isTableExists(SQLiteDatabase sQLiteDatabase, String str) {
        int i;
        if (str == null || sQLiteDatabase == null || !sQLiteDatabase.isOpen()) {
            return false;
        }
        Cursor cursor = null;
        try {
            try {
                cursor = sQLiteDatabase.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[]{"table", str});
            } catch (Exception e) {
                OkLogger.printStackTrace(e);
                if (cursor != null) {
                    cursor.close();
                }
                i = 0;
            }
            if (!cursor.moveToFirst()) {
                if (cursor != null) {
                    cursor.close();
                }
                return false;
            }
            i = cursor.getInt(0);
            if (cursor != null) {
                cursor.close();
            }
            return i > 0;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    public static boolean isFieldExists(SQLiteDatabase sQLiteDatabase, String str, String str2) {
        boolean z = false;
        if (str == null || sQLiteDatabase == null || str2 == null || !sQLiteDatabase.isOpen()) {
            return false;
        }
        Cursor cursor = null;
        try {
            try {
                cursor = sQLiteDatabase.rawQuery("SELECT * FROM " + str + " LIMIT 0", null);
                if (cursor != null) {
                    if (cursor.getColumnIndex(str2) != -1) {
                        z = true;
                    }
                }
                if (cursor != null) {
                    cursor.close();
                }
                return z;
            } catch (Exception e) {
                OkLogger.printStackTrace(e);
                if (cursor != null) {
                    cursor.close();
                }
                return false;
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }
}
