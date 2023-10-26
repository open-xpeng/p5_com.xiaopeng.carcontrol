package com.xiaopeng.lib.framework.netchannelmodule.remotestorage.util;

import com.xiaopeng.lib.utils.FileUtils;
import com.xiaopeng.lib.utils.ThreadUtils;
import java.io.File;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes2.dex */
public class LogFileCleaner {
    private static final int CLEANING_THRESHOLD = 512;
    private static final long FILE_EXPIRED_TIME = 604800000;
    private static final int MAX_DELETED_FILES_EACH_TIME = 20;
    private static final long ONE_DAY_MILLS = 86400000;
    private CleaningTask mCleaningTask;
    private AtomicLong mLastCleaningDate;
    private String mLogFileFolder;

    public static LogFileCleaner getInstance() {
        return Holder.INSTANCE;
    }

    private LogFileCleaner() {
        this.mLastCleaningDate = new AtomicLong(0L);
    }

    public void cleanLogAsNeeded() {
        if (this.mLogFileFolder == null) {
            return;
        }
        long currentTimeMillis = System.currentTimeMillis() / 86400000;
        if (this.mLastCleaningDate.get() > currentTimeMillis) {
            return;
        }
        if (this.mCleaningTask == null) {
            this.mCleaningTask = new CleaningTask();
        }
        ThreadUtils.postBackground(this.mCleaningTask);
        this.mLastCleaningDate.set(currentTimeMillis);
    }

    public void setLogFileFolder(String str) {
        this.mLogFileFolder = str;
    }

    /* loaded from: classes2.dex */
    private final class CleaningTask implements Runnable {
        private CleaningTask() {
        }

        @Override // java.lang.Runnable
        public void run() {
            File[] listFiles;
            File file = new File(LogFileCleaner.this.mLogFileFolder);
            if (!file.exists() || !file.isDirectory() || (listFiles = file.listFiles()) == null || listFiles.length <= 512) {
                return;
            }
            long currentTimeMillis = System.currentTimeMillis();
            int i = 0;
            for (File file2 : listFiles) {
                if (currentTimeMillis - file2.lastModified() > LogFileCleaner.FILE_EXPIRED_TIME) {
                    FileUtils.deleteFile(file2.getAbsolutePath());
                    i++;
                }
                if (i > 20) {
                    return;
                }
            }
        }
    }

    /* loaded from: classes2.dex */
    private static final class Holder {
        private static final LogFileCleaner INSTANCE = new LogFileCleaner();

        private Holder() {
        }
    }
}
