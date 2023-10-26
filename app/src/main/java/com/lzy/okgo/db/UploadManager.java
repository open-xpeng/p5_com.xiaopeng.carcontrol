package com.lzy.okgo.db;

import android.content.ContentValues;
import android.database.Cursor;
import com.lzy.okgo.model.Progress;
import com.xiaopeng.lib.utils.info.BuildInfoUtils;
import java.util.List;

/* loaded from: classes.dex */
public class UploadManager extends BaseDao<Progress> {
    @Override // com.lzy.okgo.db.BaseDao
    public String getTableName() {
        return "upload";
    }

    @Override // com.lzy.okgo.db.BaseDao
    public void unInit() {
    }

    private UploadManager() {
        super(new DBHelper());
    }

    public static UploadManager getInstance() {
        return UploadManagerHolder.instance;
    }

    /* loaded from: classes.dex */
    private static class UploadManagerHolder {
        private static final UploadManager instance = new UploadManager();

        private UploadManagerHolder() {
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
        return update((UploadManager) progress, "tag=?", new String[]{progress.tag});
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

    public List<Progress> getUploading() {
        return query(null, "status not in(?)", new String[]{BuildInfoUtils.BID_PT_SPECIAL_1}, null, null, "date ASC", null);
    }

    public boolean clear() {
        return deleteAll();
    }
}
