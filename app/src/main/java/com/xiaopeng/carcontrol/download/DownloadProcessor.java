package com.xiaopeng.carcontrol.download;

import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.download.listener.UriObserverListener;
import com.xiaopeng.carcontrol.util.LogUtils;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

/* loaded from: classes2.dex */
class DownloadProcessor {
    private static final Uri DOWNLOADS_URI = Uri.parse("content://downloads/my_downloads");
    private static final String TAG = "DownloadProcessor";
    private ContentResolver contentResolver;
    private DownloadManager downloadManager;
    private ContentObserver downloadObserver;
    private UriObserverListener uriObserverListener;
    private ArrayList<RequestInfo> downloadCaches = new ArrayList<>();
    private String defaultPath = Environment.getExternalStorageDirectory().getPath();

    DownloadProcessor() {
    }

    /* loaded from: classes2.dex */
    private static class SingletonHolder {
        static DownloadProcessor sInstance = new DownloadProcessor();

        private SingletonHolder() {
        }
    }

    public static DownloadProcessor getInstance() {
        return SingletonHolder.sInstance;
    }

    public void init(UriObserverListener listener) {
        if (this.downloadManager == null) {
            this.downloadManager = (DownloadManager) App.getInstance().getSystemService("download");
        }
        if (this.contentResolver == null) {
            this.contentResolver = App.getInstance().getContentResolver();
        }
        if (this.downloadObserver == null) {
            this.downloadObserver = new ContentObserver(null) { // from class: com.xiaopeng.carcontrol.download.DownloadProcessor.1
                @Override // android.database.ContentObserver
                public void onChange(boolean selfChange) {
                    super.onChange(selfChange);
                }

                @Override // android.database.ContentObserver
                public void onChange(boolean selfChange, Uri uri) {
                    LogUtils.d(DownloadProcessor.TAG, "uri : " + uri + " self: " + selfChange);
                    super.onChange(selfChange, uri);
                    try {
                        long parseLong = Long.parseLong(uri.getLastPathSegment());
                        if (DownloadProcessor.this.uriObserverListener != null) {
                            DownloadProcessor.this.uriObserverListener.onUriChanged(parseLong);
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            };
        }
        this.contentResolver.registerContentObserver(DOWNLOADS_URI, true, this.downloadObserver);
        this.uriObserverListener = listener;
    }

    public void release() {
        ContentObserver contentObserver;
        ContentResolver contentResolver = this.contentResolver;
        if (contentResolver != null && (contentObserver = this.downloadObserver) != null) {
            contentResolver.unregisterContentObserver(contentObserver);
        }
        this.uriObserverListener = null;
    }

    private void removeSameDownloadAndFile(DownloadManager dm, RequestInfo requestInfo) {
        String substring = requestInfo.name.substring(0, requestInfo.name.lastIndexOf("."));
        String path = Paths.get(requestInfo.localPath, substring).toString();
        HashSet<Long> hashSet = new HashSet();
        try {
            Cursor query = dm.query(new DownloadManager.Query());
            if (query != null) {
                while (query.moveToNext()) {
                    String string = query.getString(query.getColumnIndex("local_uri"));
                    if (string.contains(substring)) {
                        String path2 = Uri.parse(string).getPath();
                        if (path.equals(path2.substring(0, path2.lastIndexOf(substring) + substring.length()))) {
                            hashSet.add(Long.valueOf(getLong(query, "_id").longValue()));
                        }
                    } else {
                        LogUtils.d(TAG, "removeSameDownload, ignore this path:" + string);
                    }
                }
            } else {
                LogUtils.w(TAG, "removeSameDownload Finish. cursor is null.");
            }
            if (query != null) {
                query.close();
            }
        } catch (Exception e) {
            LogUtils.w(TAG, "removeSameDownload ex " + e);
        }
        if (!hashSet.isEmpty()) {
            for (Long l : hashSet) {
                LogUtils.w(TAG, "remove same download, id:" + l);
                dm.remove(l.longValue());
            }
        }
        File[] listFilesInDir = FileHelper.listFilesInDir(requestInfo.localPath);
        if (listFilesInDir != null) {
            for (File file : listFilesInDir) {
                if (file.getName().contains(substring)) {
                    LogUtils.w(TAG, "remove same file, path:" + file.getPath() + ", deleted:" + file.delete());
                }
            }
        }
    }

    private static Long getLong(Cursor cursor, String column) {
        long j;
        try {
            j = cursor.getLong(cursor.getColumnIndexOrThrow(column));
        } catch (IllegalArgumentException unused) {
            j = 0;
        }
        return Long.valueOf(j);
    }

    private static String getString(Cursor cursor, String column) {
        String str;
        try {
            str = cursor.getString(cursor.getColumnIndexOrThrow(column));
        } catch (IllegalArgumentException unused) {
            str = "";
        }
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return str;
    }

    public int start(RequestInfo requestInfo) {
        if (requestInfo == null || TextUtils.isEmpty(requestInfo.uri)) {
            return -1;
        }
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(requestInfo.uri));
        if (requestInfo.header != null) {
            for (Map.Entry<String, String> entry : requestInfo.header.entrySet()) {
                request.addRequestHeader(entry.getKey(), entry.getValue());
            }
        }
        if (TextUtils.isEmpty(requestInfo.localPath)) {
            return 1002;
        }
        if (TextUtils.isEmpty(requestInfo.name)) {
            return 1003;
        }
        if (this.downloadManager == null) {
            this.downloadManager = (DownloadManager) App.getInstance().getSystemService("download");
        }
        removeSameDownloadAndFile(this.downloadManager, requestInfo);
        LogUtils.d(TAG, "createOrExistsDir : " + FileHelper.createOrExistsDir(requestInfo.localPath) + ", path:" + requestInfo.localPath);
        request.setDestinationUri(Uri.fromFile(new File(requestInfo.localPath, requestInfo.name)));
        requestInfo.id = this.downloadManager.enqueue(request);
        LogUtils.d(TAG, "request done, " + requestInfo.toString());
        return 1011;
    }

    public int pause(long downloadId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("control", (Integer) 1);
        contentValues.put("status", (Integer) 193);
        return this.contentResolver.update(ContentUris.withAppendedId(DOWNLOADS_URI, downloadId), contentValues, null, null);
    }

    public int resume(long downloadId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("control", (Integer) 0);
        contentValues.put("status", (Integer) 2);
        return this.contentResolver.update(ContentUris.withAppendedId(DOWNLOADS_URI, downloadId), contentValues, null, null);
    }

    public int cancel(long downloadId) {
        return this.downloadManager.remove(downloadId);
    }

    public int restart(long downloadId) {
        this.downloadManager.restartDownload(new long[]{downloadId});
        return 1;
    }

    public DownloadInfo query(long downloadId) {
        Uri parse;
        DownloadInfo downloadInfo = new DownloadInfo();
        Cursor query = this.downloadManager.query(new DownloadManager.Query().setFilterById(downloadId));
        if (query != null && query.moveToFirst()) {
            downloadInfo.id = query.getInt(query.getColumnIndex("_id"));
            String string = query.getString(query.getColumnIndex("local_uri"));
            if (string != null && (parse = Uri.parse(string)) != null) {
                downloadInfo.name = parse.getLastPathSegment();
            }
            downloadInfo.status = query.getInt(query.getColumnIndex("status"));
            downloadInfo.downloadedSize = query.getInt(query.getColumnIndex("bytes_so_far"));
            downloadInfo.totalSize = query.getInt(query.getColumnIndex("total_size"));
            if (downloadInfo.totalSize > 0) {
                downloadInfo.percent = (int) ((downloadInfo.downloadedSize * 100) / downloadInfo.totalSize);
            }
        }
        if (query != null) {
            query.close();
        }
        return downloadInfo;
    }
}
