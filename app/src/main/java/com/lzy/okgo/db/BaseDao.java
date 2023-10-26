package com.lzy.okgo.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Pair;
import com.lzy.okgo.utils.OkLogger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

/* loaded from: classes.dex */
public abstract class BaseDao<T> {
    protected static String TAG;
    protected SQLiteDatabase database;
    protected SQLiteOpenHelper helper;
    protected Lock lock;

    /* loaded from: classes.dex */
    public interface Action {
        void call(SQLiteDatabase sQLiteDatabase);
    }

    public abstract ContentValues getContentValues(T t);

    public abstract String getTableName();

    public abstract T parseCursorToBean(Cursor cursor);

    public abstract void unInit();

    public BaseDao(SQLiteOpenHelper sQLiteOpenHelper) {
        TAG = getClass().getSimpleName();
        this.lock = DBHelper.lock;
        this.helper = sQLiteOpenHelper;
        this.database = openWriter();
    }

    public SQLiteDatabase openReader() {
        return this.helper.getReadableDatabase();
    }

    public SQLiteDatabase openWriter() {
        return this.helper.getWritableDatabase();
    }

    protected final void closeDatabase(SQLiteDatabase sQLiteDatabase, Cursor cursor) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        if (sQLiteDatabase == null || !sQLiteDatabase.isOpen()) {
            return;
        }
        sQLiteDatabase.close();
    }

    public boolean insert(T t) {
        if (t == null) {
            return false;
        }
        long currentTimeMillis = System.currentTimeMillis();
        this.lock.lock();
        try {
            this.database.beginTransaction();
            this.database.insert(getTableName(), null, getContentValues(t));
            this.database.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            OkLogger.printStackTrace(e);
            return false;
        } finally {
            this.database.endTransaction();
            this.lock.unlock();
            OkLogger.v(TAG, (System.currentTimeMillis() - currentTimeMillis) + " insertT");
        }
    }

    public long insert(SQLiteDatabase sQLiteDatabase, T t) {
        return sQLiteDatabase.insert(getTableName(), null, getContentValues(t));
    }

    public boolean insert(List<T> list) {
        if (list == null) {
            return false;
        }
        long currentTimeMillis = System.currentTimeMillis();
        this.lock.lock();
        try {
            this.database.beginTransaction();
            for (T t : list) {
                this.database.insert(getTableName(), null, getContentValues(t));
            }
            this.database.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            OkLogger.printStackTrace(e);
            return false;
        } finally {
            this.database.endTransaction();
            this.lock.unlock();
            OkLogger.v(TAG, (System.currentTimeMillis() - currentTimeMillis) + " insertList");
        }
    }

    public boolean insert(SQLiteDatabase sQLiteDatabase, List<T> list) {
        try {
            for (T t : list) {
                sQLiteDatabase.insert(getTableName(), null, getContentValues(t));
            }
            return true;
        } catch (Exception e) {
            OkLogger.printStackTrace(e);
            return false;
        }
    }

    public boolean deleteAll() {
        return delete(null, null);
    }

    public long deleteAll(SQLiteDatabase sQLiteDatabase) {
        return delete(sQLiteDatabase, null, null);
    }

    public boolean delete(String str, String[] strArr) {
        long currentTimeMillis = System.currentTimeMillis();
        this.lock.lock();
        try {
            try {
                this.database.beginTransaction();
                this.database.delete(getTableName(), str, strArr);
                this.database.setTransactionSuccessful();
                return true;
            } catch (Exception e) {
                OkLogger.printStackTrace(e);
                this.database.endTransaction();
                this.lock.unlock();
                OkLogger.v(TAG, (System.currentTimeMillis() - currentTimeMillis) + " delete");
                return false;
            }
        } finally {
            this.database.endTransaction();
            this.lock.unlock();
            OkLogger.v(TAG, (System.currentTimeMillis() - currentTimeMillis) + " delete");
        }
    }

    public long delete(SQLiteDatabase sQLiteDatabase, String str, String[] strArr) {
        return sQLiteDatabase.delete(getTableName(), str, strArr);
    }

    public boolean deleteList(List<Pair<String, String[]>> list) {
        long currentTimeMillis = System.currentTimeMillis();
        this.lock.lock();
        try {
            try {
                this.database.beginTransaction();
                for (Pair<String, String[]> pair : list) {
                    this.database.delete(getTableName(), (String) pair.first, (String[]) pair.second);
                }
                this.database.setTransactionSuccessful();
                return true;
            } catch (Exception e) {
                OkLogger.printStackTrace(e);
                this.database.endTransaction();
                this.lock.unlock();
                OkLogger.v(TAG, (System.currentTimeMillis() - currentTimeMillis) + " deleteList");
                return false;
            }
        } finally {
            this.database.endTransaction();
            this.lock.unlock();
            OkLogger.v(TAG, (System.currentTimeMillis() - currentTimeMillis) + " deleteList");
        }
    }

    public boolean replace(T t) {
        if (t == null) {
            return false;
        }
        long currentTimeMillis = System.currentTimeMillis();
        this.lock.lock();
        try {
            this.database.beginTransaction();
            this.database.replace(getTableName(), null, getContentValues(t));
            this.database.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            OkLogger.printStackTrace(e);
            return false;
        } finally {
            this.database.endTransaction();
            this.lock.unlock();
            OkLogger.v(TAG, (System.currentTimeMillis() - currentTimeMillis) + " replaceT");
        }
    }

    public long replace(SQLiteDatabase sQLiteDatabase, T t) {
        return sQLiteDatabase.replace(getTableName(), null, getContentValues(t));
    }

    public boolean replace(ContentValues contentValues) {
        long currentTimeMillis = System.currentTimeMillis();
        this.lock.lock();
        try {
            try {
                this.database.beginTransaction();
                this.database.replace(getTableName(), null, contentValues);
                this.database.setTransactionSuccessful();
                return true;
            } catch (Exception e) {
                OkLogger.printStackTrace(e);
                this.database.endTransaction();
                this.lock.unlock();
                OkLogger.v(TAG, (System.currentTimeMillis() - currentTimeMillis) + " replaceContentValues");
                return false;
            }
        } finally {
            this.database.endTransaction();
            this.lock.unlock();
            OkLogger.v(TAG, (System.currentTimeMillis() - currentTimeMillis) + " replaceContentValues");
        }
    }

    public long replace(SQLiteDatabase sQLiteDatabase, ContentValues contentValues) {
        return sQLiteDatabase.replace(getTableName(), null, contentValues);
    }

    public boolean replace(List<T> list) {
        if (list == null) {
            return false;
        }
        long currentTimeMillis = System.currentTimeMillis();
        this.lock.lock();
        try {
            this.database.beginTransaction();
            for (T t : list) {
                this.database.replace(getTableName(), null, getContentValues(t));
            }
            this.database.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            OkLogger.printStackTrace(e);
            return false;
        } finally {
            this.database.endTransaction();
            this.lock.unlock();
            OkLogger.v(TAG, (System.currentTimeMillis() - currentTimeMillis) + " replaceList");
        }
    }

    public boolean replace(SQLiteDatabase sQLiteDatabase, List<T> list) {
        try {
            for (T t : list) {
                sQLiteDatabase.replace(getTableName(), null, getContentValues(t));
            }
            return true;
        } catch (Exception e) {
            OkLogger.printStackTrace(e);
            return false;
        }
    }

    public boolean update(T t, String str, String[] strArr) {
        if (t == null) {
            return false;
        }
        long currentTimeMillis = System.currentTimeMillis();
        this.lock.lock();
        try {
            this.database.beginTransaction();
            this.database.update(getTableName(), getContentValues(t), str, strArr);
            this.database.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            OkLogger.printStackTrace(e);
            return false;
        } finally {
            this.database.endTransaction();
            this.lock.unlock();
            OkLogger.v(TAG, (System.currentTimeMillis() - currentTimeMillis) + " updateT");
        }
    }

    public long update(SQLiteDatabase sQLiteDatabase, T t, String str, String[] strArr) {
        return sQLiteDatabase.update(getTableName(), getContentValues(t), str, strArr);
    }

    public boolean update(ContentValues contentValues, String str, String[] strArr) {
        long currentTimeMillis = System.currentTimeMillis();
        this.lock.lock();
        try {
            try {
                this.database.beginTransaction();
                this.database.update(getTableName(), contentValues, str, strArr);
                this.database.setTransactionSuccessful();
                return true;
            } catch (Exception e) {
                OkLogger.printStackTrace(e);
                this.database.endTransaction();
                this.lock.unlock();
                OkLogger.v(TAG, (System.currentTimeMillis() - currentTimeMillis) + " updateContentValues");
                return false;
            }
        } finally {
            this.database.endTransaction();
            this.lock.unlock();
            OkLogger.v(TAG, (System.currentTimeMillis() - currentTimeMillis) + " updateContentValues");
        }
    }

    public long update(SQLiteDatabase sQLiteDatabase, ContentValues contentValues, String str, String[] strArr) {
        return sQLiteDatabase.update(getTableName(), contentValues, str, strArr);
    }

    public List<T> queryAll(SQLiteDatabase sQLiteDatabase) {
        return query(sQLiteDatabase, null, null);
    }

    public List<T> query(SQLiteDatabase sQLiteDatabase, String str, String[] strArr) {
        return query(sQLiteDatabase, null, str, strArr, null, null, null, null);
    }

    public T queryOne(SQLiteDatabase sQLiteDatabase, String str, String[] strArr) {
        List<T> query = query(sQLiteDatabase, null, str, strArr, null, null, null, "1");
        if (query.size() > 0) {
            return query.get(0);
        }
        return null;
    }

    public List<T> query(SQLiteDatabase sQLiteDatabase, String[] strArr, String str, String[] strArr2, String str2, String str3, String str4, String str5) {
        Cursor cursor;
        ArrayList arrayList = new ArrayList();
        try {
            try {
                cursor = sQLiteDatabase.query(getTableName(), strArr, str, strArr2, str2, str3, str4, str5);
                while (!cursor.isClosed() && cursor.moveToNext()) {
                    try {
                        arrayList.add(parseCursorToBean(cursor));
                    } catch (Exception e) {
                        e = e;
                        OkLogger.printStackTrace(e);
                        closeDatabase(null, cursor);
                        return arrayList;
                    }
                }
            } catch (Exception e2) {
                e = e2;
                cursor = null;
            } catch (Throwable th) {
                th = th;
                closeDatabase(null, null);
                throw th;
            }
            closeDatabase(null, cursor);
            return arrayList;
        } catch (Throwable th2) {
            th = th2;
            closeDatabase(null, null);
            throw th;
        }
    }

    public List<T> queryAll() {
        return query(null, null);
    }

    public List<T> query(String str, String[] strArr) {
        return query(null, str, strArr, null, null, null, null);
    }

    public T queryOne(String str, String[] strArr) {
        long currentTimeMillis = System.currentTimeMillis();
        List<T> query = query(null, str, strArr, null, null, null, "1");
        OkLogger.v(TAG, (System.currentTimeMillis() - currentTimeMillis) + " queryOne");
        if (query.size() > 0) {
            return query.get(0);
        }
        return null;
    }

    public List<T> query(String[] strArr, String str, String[] strArr2, String str2, String str3, String str4, String str5) {
        Cursor cursor;
        String str6;
        StringBuilder sb;
        long currentTimeMillis = System.currentTimeMillis();
        this.lock.lock();
        ArrayList arrayList = new ArrayList();
        try {
            try {
                this.database.beginTransaction();
                cursor = this.database.query(getTableName(), strArr, str, strArr2, str2, str3, str4, str5);
                while (!cursor.isClosed() && cursor.moveToNext()) {
                    try {
                        arrayList.add(parseCursorToBean(cursor));
                    } catch (Exception e) {
                        e = e;
                        OkLogger.printStackTrace(e);
                        closeDatabase(null, cursor);
                        this.database.endTransaction();
                        this.lock.unlock();
                        str6 = TAG;
                        sb = new StringBuilder();
                        OkLogger.v(str6, sb.append(System.currentTimeMillis() - currentTimeMillis).append(" query").toString());
                        return arrayList;
                    }
                }
                this.database.setTransactionSuccessful();
                closeDatabase(null, cursor);
                this.database.endTransaction();
                this.lock.unlock();
                str6 = TAG;
                sb = new StringBuilder();
            } catch (Exception e2) {
                e = e2;
                cursor = null;
            } catch (Throwable th) {
                th = th;
                closeDatabase(null, null);
                this.database.endTransaction();
                this.lock.unlock();
                OkLogger.v(TAG, (System.currentTimeMillis() - currentTimeMillis) + " query");
                throw th;
            }
            OkLogger.v(str6, sb.append(System.currentTimeMillis() - currentTimeMillis).append(" query").toString());
            return arrayList;
        } catch (Throwable th2) {
            th = th2;
            closeDatabase(null, null);
            this.database.endTransaction();
            this.lock.unlock();
            OkLogger.v(TAG, (System.currentTimeMillis() - currentTimeMillis) + " query");
            throw th;
        }
    }

    public void startTransaction(Action action) {
        this.lock.lock();
        try {
            try {
                this.database.beginTransaction();
                action.call(this.database);
                this.database.setTransactionSuccessful();
            } catch (Exception e) {
                OkLogger.printStackTrace(e);
            }
        } finally {
            this.database.endTransaction();
            this.lock.unlock();
        }
    }
}
