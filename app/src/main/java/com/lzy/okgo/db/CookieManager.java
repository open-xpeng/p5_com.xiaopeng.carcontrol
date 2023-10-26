package com.lzy.okgo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.lzy.okgo.cookie.SerializableCookie;

/* loaded from: classes.dex */
public class CookieManager extends BaseDao<SerializableCookie> {
    private static Context context;
    private static volatile CookieManager instance;

    @Override // com.lzy.okgo.db.BaseDao
    public String getTableName() {
        return SerializableCookie.COOKIE;
    }

    @Override // com.lzy.okgo.db.BaseDao
    public void unInit() {
    }

    public static CookieManager getInstance() {
        if (instance == null) {
            synchronized (CookieManager.class) {
                if (instance == null) {
                    instance = new CookieManager();
                }
            }
        }
        return instance;
    }

    private CookieManager() {
        super(new DBHelper(context));
    }

    public static void init(Context context2) {
        context = context2;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.lzy.okgo.db.BaseDao
    public SerializableCookie parseCursorToBean(Cursor cursor) {
        return SerializableCookie.parseCursorToBean(cursor);
    }

    @Override // com.lzy.okgo.db.BaseDao
    public ContentValues getContentValues(SerializableCookie serializableCookie) {
        return SerializableCookie.getContentValues(serializableCookie);
    }
}
