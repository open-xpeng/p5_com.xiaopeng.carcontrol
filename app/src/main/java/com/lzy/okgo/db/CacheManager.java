package com.lzy.okgo.db;

import android.content.ContentValues;
import android.database.Cursor;
import com.irdeto.securesdk.core.SSUtils;
import com.lzy.okgo.cache.CacheEntity;
import java.util.List;

/* loaded from: classes.dex */
public class CacheManager extends BaseDao<CacheEntity<?>> {
    @Override // com.lzy.okgo.db.BaseDao
    public String getTableName() {
        return SSUtils.O00000o0;
    }

    @Override // com.lzy.okgo.db.BaseDao
    public void unInit() {
    }

    public static CacheManager getInstance() {
        return CacheManagerHolder.instance;
    }

    /* loaded from: classes.dex */
    private static class CacheManagerHolder {
        private static final CacheManager instance = new CacheManager();

        private CacheManagerHolder() {
        }
    }

    private CacheManager() {
        super(new DBHelper());
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.lzy.okgo.db.BaseDao
    public CacheEntity<?> parseCursorToBean(Cursor cursor) {
        return CacheEntity.parseCursorToBean(cursor);
    }

    @Override // com.lzy.okgo.db.BaseDao
    public ContentValues getContentValues(CacheEntity<?> cacheEntity) {
        return CacheEntity.getContentValues(cacheEntity);
    }

    public CacheEntity<?> get(String str) {
        if (str == null) {
            return null;
        }
        List<CacheEntity<?>> query = query("key=?", new String[]{str});
        if (query.size() > 0) {
            return query.get(0);
        }
        return null;
    }

    public boolean remove(String str) {
        if (str == null) {
            return false;
        }
        return delete("key=?", new String[]{str});
    }

    public <T> CacheEntity<T> get(String str, Class<T> cls) {
        return (CacheEntity<T>) get(str);
    }

    public List<CacheEntity<?>> getAll() {
        return queryAll();
    }

    public <T> CacheEntity<T> replace(String str, CacheEntity<T> cacheEntity) {
        cacheEntity.setKey(str);
        replace((CacheManager) cacheEntity);
        return cacheEntity;
    }

    public boolean clear() {
        return deleteAll();
    }
}
