package com.xiaopeng.lib.utils.log;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.support.v4.media.session.PlaybackStateCompat;
import com.tencent.mars.xlog.Xlog;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.ThreadUtils;
import com.xiaopeng.lib.utils.info.BuildInfoUtils;
import com.xiaopeng.lib.utils.log.XLoggerFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes2.dex */
public class XLogger implements LogUtils.Logger, Handler.Callback {
    private static final int CACHE_DAYS = 0;
    private static final int MAX_INCREASE_SIZE_ONE_MIN = 131072;
    private static final int MESSAGE_FLUSH = 1;
    private static final String PUBLIC_KEY = "43b835098ac59e6a265edfd3b4aa53ba18e67c9503e88f74253d433f36cf854e3a6f77a92d25454284bcf57913bd91abadfa062520cb8662f6740eaeca23b573";
    private static final String TAG = "XLogger";
    private List<String> mAlreadyChmodList;
    private final String mFilePath;
    private final String mFilePrefix;
    private final int mFlushInterval;
    private Handler mHandler = new Handler(ThreadUtils.getLooper(0), this);
    private final int mLevel;
    private long mLogLengthPerMin;
    private long mMainThreadId;
    private final long mMaxSingleSize;
    private final long mMaxTotalSize;
    private final int mMode;
    private final String mPackageName;
    private int mPid;
    private Xlog mXlog;

    /* JADX INFO: Access modifiers changed from: package-private */
    public XLogger(XLoggerFactory.XLoggerBuilder xLoggerBuilder) throws XLogLoadLibraryException {
        this.mPackageName = xLoggerBuilder.context.getPackageName();
        this.mFilePath = xLoggerBuilder.filePath;
        this.mFilePrefix = xLoggerBuilder.filePrefix;
        this.mMode = xLoggerBuilder.mode;
        this.mLevel = xLoggerBuilder.level;
        this.mFlushInterval = xLoggerBuilder.flushInterval;
        this.mMaxTotalSize = xLoggerBuilder.maxTotalSize;
        this.mMaxSingleSize = xLoggerBuilder.maxSingleSize;
        init(xLoggerBuilder.context);
    }

    private void init(Context context) throws XLogLoadLibraryException {
        try {
            System.loadLibrary("c++_shared");
            System.loadLibrary("marsxlog");
            this.mMainThreadId = context.getMainLooper().getThread().getId();
            this.mPid = Process.myPid();
            Xlog.appenderOpen(this.mLevel, this.mMode, context.getFilesDir() + "/mXlog", this.mFilePath, this.mFilePrefix, 0, PUBLIC_KEY);
            Xlog.setConsoleLogOpen(true);
            Xlog.setMaxFileSize(this.mMaxSingleSize);
            this.mXlog = new Xlog();
            this.mHandler.sendEmptyMessageDelayed(1, this.mFlushInterval);
        } catch (Throwable th) {
            throw new XLogLoadLibraryException(th);
        }
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        if (message.what == 1) {
            this.mHandler.sendEmptyMessageDelayed(1, this.mFlushInterval);
            long j = this.mLogLengthPerMin;
            this.mLogLengthPerMin = 0L;
            if (BuildInfoUtils.isDebuggableVersion()) {
                BuildInfoUtils.getFullSystemVersion().endsWith("DEV");
            }
            this.mXlog.appenderFlush(true);
            cleanLogPath();
            if (j > PlaybackStateCompat.ACTION_PREPARE_FROM_URI) {
                LogUtils.e(TAG, "##Error##[LogExplosion]## increaseLength > 128k, packageName:" + this.mPackageName);
            }
        }
        return true;
    }

    private void cleanLogPath() {
        File file = new File(this.mFilePath);
        if (!file.exists() || !file.isDirectory()) {
            LogUtils.d(TAG, "!logDir.exists() || !logDir.isDirectory(), return!");
            return;
        }
        if (this.mAlreadyChmodList == null) {
            this.mAlreadyChmodList = new ArrayList();
            LogUtils.d(TAG, "logDir.setReadable(true, false), path:" + file.getPath());
            file.setReadable(true, false);
            file.setWritable(true, false);
            file.setExecutable(true, false);
        }
        File[] listFiles = file.listFiles();
        if (listFiles == null || listFiles.length == 0) {
            LogUtils.d(TAG, "logFiles == null || logFiles.length == 0, return!");
            return;
        }
        long j = 0;
        for (File file2 : listFiles) {
            j += file2.length();
            if (!this.mAlreadyChmodList.contains(file2.getName())) {
                LogUtils.d(TAG, "logDir.setReadable(true, false), path:" + file2.getPath() + " chmodResult:" + (file2.setReadable(true, false) & file2.setWritable(true, false) & file2.setExecutable(true, false)));
                this.mAlreadyChmodList.add(file2.getName());
            }
        }
        if (this.mAlreadyChmodList.size() > 0 && this.mAlreadyChmodList.size() > listFiles.length) {
            LogUtils.d(TAG, "delete first mAlreadyChmodList element:" + this.mAlreadyChmodList.get(0));
            this.mAlreadyChmodList.remove(0);
        }
        if (j < this.mMaxTotalSize) {
            return;
        }
        Collections.sort(Arrays.asList(listFiles), new Comparator<File>() { // from class: com.xiaopeng.lib.utils.log.XLogger.1
            @Override // java.util.Comparator
            public int compare(File file3, File file4) {
                return (int) (file3.lastModified() - file4.lastModified());
            }
        });
        for (int i = 0; j > this.mMaxTotalSize && i <= listFiles.length; i++) {
            LogUtils.d(TAG, "fileSizeSum > " + this.mMaxTotalSize + ", delete " + listFiles[i].getPath());
            j -= listFiles[i].length();
            boolean delete = listFiles[i].delete();
            LogUtils.d(TAG, "delete result:" + delete);
            if (!delete) {
                LogUtils.e(TAG, "delete file fail, reset to DefaultLogger");
                LogUtils.setLogger(new LogUtils.DefaultLogger());
                return;
            }
        }
    }

    @Override // com.xiaopeng.lib.utils.LogUtils.Logger
    public void logByLevel(int i, String str, String str2, String str3) {
        this.mLogLengthPerMin += str.length();
        long id = Thread.currentThread().getId();
        if (i == 2) {
            this.mXlog.logV(str2, str3, null, 0, this.mPid, id, this.mMainThreadId, str);
        } else if (i == 3) {
            this.mXlog.logD(str2, str3, null, 0, this.mPid, id, this.mMainThreadId, str);
        } else if (i == 4) {
            this.mXlog.logI(str2, str3, null, 0, this.mPid, id, this.mMainThreadId, str);
        } else if (i == 5) {
            this.mXlog.logW(str2, str3, null, 0, this.mPid, id, this.mMainThreadId, str);
        } else if (i != 6) {
        } else {
            this.mXlog.logE(str2, str3, null, 0, this.mPid, id, this.mMainThreadId, str);
        }
    }
}
