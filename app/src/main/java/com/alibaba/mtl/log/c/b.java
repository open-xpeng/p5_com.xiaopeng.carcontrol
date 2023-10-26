package com.alibaba.mtl.log.c;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import com.alibaba.mtl.log.e.i;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: LogSqliteStore.java */
/* loaded from: classes.dex */
public class b implements com.alibaba.mtl.log.c.a {
    a a;
    String aa = "SELECT * FROM %s ORDER BY %s ASC LIMIT %s";
    String ab = "SELECT count(*) FROM %s";
    String ac = "DELETE FROM log where _id in ( select _id from log  ORDER BY _id ASC LIMIT %d )";

    /* JADX INFO: Access modifiers changed from: protected */
    public b(Context context) {
        this.a = new a(context);
    }

    @Override // com.alibaba.mtl.log.c.a
    /* renamed from: a */
    public synchronized boolean mo25a(List<com.alibaba.mtl.log.model.a> list) {
        boolean z = true;
        if (list != null) {
            if (list.size() != 0) {
                SQLiteDatabase sQLiteDatabase = null;
                boolean z2 = false;
                try {
                    sQLiteDatabase = this.a.getWritableDatabase();
                    if (sQLiteDatabase != null) {
                        sQLiteDatabase.beginTransaction();
                        int i = 0;
                        while (true) {
                            try {
                                if (i >= list.size()) {
                                    break;
                                }
                                com.alibaba.mtl.log.model.a aVar = list.get(i);
                                if (aVar != null) {
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("eventId", aVar.T);
                                    contentValues.put("priority", aVar.U);
                                    contentValues.put("content", aVar.l());
                                    contentValues.put("time", aVar.W);
                                    contentValues.put("_index", aVar.X);
                                    long insert = sQLiteDatabase.insert("log", "", contentValues);
                                    if (insert == -1) {
                                        z = false;
                                        break;
                                    }
                                    i.a("UTSqliteLogStore", "[insert] ", aVar.X, " isSuccess:", true, "ret", Long.valueOf(insert));
                                }
                                i++;
                            } catch (Throwable th) {
                                th = th;
                                i.a("UTSqliteLogStore", "insert error", th);
                                com.alibaba.mtl.appmonitor.b.b.m20a(th);
                                if (sQLiteDatabase != null) {
                                    try {
                                        sQLiteDatabase.setTransactionSuccessful();
                                    } catch (Throwable unused) {
                                    }
                                    try {
                                        sQLiteDatabase.endTransaction();
                                    } catch (Throwable unused2) {
                                    }
                                }
                                this.a.a(sQLiteDatabase);
                                z2 = z;
                                return z2;
                            }
                        }
                        z2 = z;
                    } else {
                        i.a("UTSqliteLogStore", "db is null");
                    }
                    if (sQLiteDatabase != null) {
                        try {
                            sQLiteDatabase.setTransactionSuccessful();
                        } catch (Throwable unused3) {
                        }
                        try {
                            sQLiteDatabase.endTransaction();
                        } catch (Throwable unused4) {
                        }
                    }
                    this.a.a(sQLiteDatabase);
                } catch (Throwable th2) {
                    th = th2;
                    z = false;
                }
                return z2;
            }
        }
        return true;
    }

    @Override // com.alibaba.mtl.log.c.a
    public synchronized int a(List<com.alibaba.mtl.log.model.a> list) {
        boolean z;
        int i;
        if (list != null) {
            if (list.size() != 0) {
                SQLiteDatabase writableDatabase = this.a.getWritableDatabase();
                if (writableDatabase != null) {
                    writableDatabase.beginTransaction();
                    i = 0;
                    z = true;
                    for (int i2 = 0; i2 < list.size(); i2++) {
                        long delete = writableDatabase.delete("log", "_id=?", new String[]{list.get(i2).id + ""});
                        if (delete <= 0) {
                            i.a("UTSqliteLogStore", "[delete]  ", Integer.valueOf(list.get(i2).id), " ret:", Long.valueOf(delete));
                            z = false;
                        } else if (!"6005".equalsIgnoreCase(list.get(i2).T)) {
                            i++;
                        }
                    }
                    try {
                        writableDatabase.setTransactionSuccessful();
                    } catch (Throwable unused) {
                    }
                    try {
                        writableDatabase.endTransaction();
                    } catch (Throwable unused2) {
                    }
                    this.a.a(writableDatabase);
                } else {
                    i.a("UTSqliteLogStore", "db is null");
                    z = false;
                    i = 0;
                }
                i.a("UTSqliteLogStore", "delete ", Integer.valueOf(list.size()), " isSuccess:", Boolean.valueOf(z));
                return i;
            }
        }
        return 0;
    }

    @Override // com.alibaba.mtl.log.c.a
    public synchronized ArrayList<com.alibaba.mtl.log.model.a> a(String str, int i) {
        ArrayList<com.alibaba.mtl.log.model.a> arrayList = null;
        if (i <= 0) {
            return (ArrayList) Collections.EMPTY_LIST;
        }
        ArrayList<com.alibaba.mtl.log.model.a> arrayList2 = new ArrayList<>(i);
        try {
            SQLiteDatabase writableDatabase = this.a.getWritableDatabase();
            if (writableDatabase != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("SELECT * FROM ").append("log");
                if (!TextUtils.isEmpty(str)) {
                    sb.append(" WHERE ").append(str);
                }
                sb.append(" ORDER BY ").append("time").append(" ASC ");
                sb.append(" LIMIT ").append(i + "");
                String sb2 = sb.toString();
                i.a("UTSqliteLogStore", "sql:" + sb2);
                Cursor rawQuery = writableDatabase.rawQuery(sb2, null);
                while (rawQuery != null && rawQuery.moveToNext()) {
                    com.alibaba.mtl.log.model.a aVar = new com.alibaba.mtl.log.model.a();
                    i.a("UTSqliteLogStore", "pos", Integer.valueOf(rawQuery.getPosition()), "count", Integer.valueOf(rawQuery.getCount()));
                    aVar.id = rawQuery.getInt(rawQuery.getColumnIndex("_id"));
                    aVar.T = rawQuery.getString(rawQuery.getColumnIndex("eventId"));
                    aVar.U = rawQuery.getString(rawQuery.getColumnIndex("priority"));
                    aVar.k(rawQuery.getString(rawQuery.getColumnIndex("content")));
                    aVar.W = rawQuery.getString(rawQuery.getColumnIndex("time"));
                    try {
                        aVar.X = rawQuery.getString(rawQuery.getColumnIndex("_index"));
                    } catch (Throwable unused) {
                    }
                    arrayList2.add(aVar);
                }
                a(rawQuery);
                this.a.a(writableDatabase);
            } else {
                i.a("UTSqliteLogStore", "db is null");
            }
        } catch (Throwable unused2) {
            arrayList = arrayList2;
            arrayList2 = arrayList;
            return arrayList2;
        }
        return arrayList2;
    }

    @Override // com.alibaba.mtl.log.c.a
    public synchronized int g() {
        int i;
        SQLiteDatabase writableDatabase = this.a.getWritableDatabase();
        i = 0;
        if (writableDatabase != null) {
            Cursor rawQuery = writableDatabase.rawQuery(String.format(this.ab, "log"), null);
            if (rawQuery != null) {
                rawQuery.moveToFirst();
                i = rawQuery.getInt(0);
            }
            a(rawQuery);
            this.a.a(writableDatabase);
        } else {
            i.a("UTSqliteLogStore", "db is null");
        }
        return i;
    }

    @Override // com.alibaba.mtl.log.c.a
    public synchronized void clear() {
        SQLiteDatabase writableDatabase = this.a.getWritableDatabase();
        if (writableDatabase != null) {
            writableDatabase.delete("log", null, null);
            this.a.a(writableDatabase);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: LogSqliteStore.java */
    /* loaded from: classes.dex */
    public class a extends SQLiteOpenHelper {
        private SQLiteDatabase a;
        private AtomicInteger e;

        a(Context context) {
            super(context, "ut.db", (SQLiteDatabase.CursorFactory) null, 2);
            this.e = new AtomicInteger();
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onOpen(SQLiteDatabase sQLiteDatabase) {
            Cursor cursor = null;
            try {
                cursor = sQLiteDatabase.rawQuery("PRAGMA journal_mode=DELETE", null);
            } catch (Throwable unused) {
            }
            b.this.a(cursor);
            super.onOpen(sQLiteDatabase);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS log (_id INTEGER PRIMARY KEY AUTOINCREMENT, eventId TEXT,priority TEXT, streamId TEXT, time TEXT, content TEXT, _index TEXT )");
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            if (i == 1 && i2 == 2) {
                try {
                    sQLiteDatabase.execSQL("ALTER TABLE log ADD COLUMN _index TEXT ");
                } catch (Throwable th) {
                    i.a("UTSqliteLogStore", "DB Upgrade Error", th);
                }
            }
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public synchronized SQLiteDatabase getWritableDatabase() {
            try {
                if (this.e.incrementAndGet() == 1) {
                    this.a = super.getWritableDatabase();
                }
            }
            return this.a;
        }

        public synchronized void a(SQLiteDatabase sQLiteDatabase) {
            SQLiteDatabase sQLiteDatabase2;
            if (sQLiteDatabase == null) {
                return;
            }
            try {
                if (this.e.decrementAndGet() == 0 && (sQLiteDatabase2 = this.a) != null) {
                    sQLiteDatabase2.close();
                    this.a = null;
                }
            } catch (Throwable unused) {
            }
        }
    }

    @Override // com.alibaba.mtl.log.c.a
    public synchronized void c(String str, String str2) {
        SQLiteDatabase writableDatabase = this.a.getWritableDatabase();
        if (writableDatabase != null) {
            writableDatabase.delete("log", str + " < ?", new String[]{String.valueOf(str2)});
            this.a.a(writableDatabase);
        } else {
            i.a("UTSqliteLogStore", "db is null");
        }
    }

    @Override // com.alibaba.mtl.log.c.a
    public void e(int i) {
        if (i <= 0) {
            return;
        }
        SQLiteDatabase writableDatabase = this.a.getWritableDatabase();
        if (writableDatabase != null) {
            try {
                writableDatabase.execSQL(String.format(this.ac, Integer.valueOf(i)));
            } catch (Throwable unused) {
            }
            this.a.a(writableDatabase);
            return;
        }
        i.a("UTSqliteLogStore", "db is null");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Cursor cursor) {
        if (cursor != null) {
            try {
                cursor.close();
            } catch (Throwable unused) {
            }
        }
    }
}
