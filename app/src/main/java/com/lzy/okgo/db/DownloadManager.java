package com.lzy.okgo.db;

import android.content.ContentValues;
import android.database.Cursor;
import com.lzy.okgo.model.Progress;
import com.xiaopeng.lib.utils.info.BuildInfoUtils;
import java.util.List;

/* loaded from: classes.dex */
public class DownloadManager extends BaseDao<Progress> {
    @Override // com.lzy.okgo.db.BaseDao
    public String getTableName() {
        return "download";
    }

    @Override // com.lzy.okgo.db.BaseDao
    public void unInit() {
    }

    private DownloadManager() {
        super(new DBHelper());
    }

    public static DownloadManager getInstance() {
        return DownloadManagerHolder.instance;
    }

    /* loaded from: classes.dex */
    private static class DownloadManagerHolder {
        private static final DownloadManager instance = new DownloadManager();

        private DownloadManagerHolder() {
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.lzy.okgo.db.BaseDao
    public Progress parseCursorToBean(Cursor cursor) {
        return Progress.parseCursorToBean(cursor);
    }

    @Override // com.lzy.okgo.db.BaseDao
    public ContentValues getContentValues(Progress progress) {
        return Progress.buildContentValues(progress);
    }

    public Progress get(String str) {
        return queryOne("tag=?", new String[]{str});
    }

    public void delete(String str) {
        delete("tag=?", new String[]{str});
    }

    public boolean update(Progress progress) {
        return update((DownloadManager) progress, "tag=?", new String[]{progress.tag});
    }

    public boolean update(ContentValues contentValues, String str) {
        return update(contentValues, "tag=?", new String[]{str});
    }

    public List<Progress> getAll() {
        return query(null, null, null, null, null, "date ASC", null);
    }

    public List<Progress> getFinished() {
        return query(null, "status=?", new String[]{BuildInfoUtils.BID_PT_SPECIAL_1}, null, null, "date ASC", null);
    }

    public List<Progress> getDownloading() {
        return query(null, "status not in(?)", new String[]{BuildInfoUtils.BID_PT_SPECIAL_1}, null, null, "date ASC", null);
    }

    public boolean clear() {
        return deleteAll();
    }
}
