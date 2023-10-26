package com.alibaba.sdk.android.oss.common;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.xiaopeng.speech.speechwidget.SpeechWidget;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

/* loaded from: classes.dex */
public class OSSLogToFileUtils {
    private static final String LOG_DIR_NAME = "OSSLog";
    private static OSSLogToFileUtils instance;
    private static Context sContext;
    private static File sLogFile;
    private static Uri sLogUri;
    private boolean useSdCard = true;
    private static LogThreadPoolManager logService = LogThreadPoolManager.newInstance();
    private static SimpleDateFormat sLogSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static long LOG_MAX_SIZE = 5242880;

    private OSSLogToFileUtils() {
    }

    public static void init(Context context, ClientConfiguration clientConfiguration) {
        File file;
        OSSLog.logDebug("init ...", false);
        if (clientConfiguration != null) {
            LOG_MAX_SIZE = clientConfiguration.getMaxLogSize();
        }
        if (sContext == null || instance == null || (file = sLogFile) == null || !file.exists()) {
            sContext = context.getApplicationContext();
            instance = getInstance();
            logService.addExecuteTask(new Runnable() { // from class: com.alibaba.sdk.android.oss.common.OSSLogToFileUtils.1
                @Override // java.lang.Runnable
                public void run() {
                    File unused = OSSLogToFileUtils.sLogFile = OSSLogToFileUtils.instance.getLogFile();
                    if (OSSLogToFileUtils.sLogFile != null) {
                        OSSLog.logInfo("LogFilePath is: " + OSSLogToFileUtils.sLogFile.getPath(), false);
                        if (OSSLogToFileUtils.LOG_MAX_SIZE < OSSLogToFileUtils.getLogFileSize(OSSLogToFileUtils.sLogFile)) {
                            OSSLog.logInfo("init reset log file", false);
                            OSSLogToFileUtils.instance.resetLogFile();
                        }
                    }
                }
            });
            return;
        }
        OSSLog.logDebug("LogToFileUtils has been init ...", false);
    }

    public static OSSLogToFileUtils getInstance() {
        if (instance == null) {
            synchronized (OSSLogToFileUtils.class) {
                if (instance == null) {
                    instance = new OSSLogToFileUtils();
                }
            }
        }
        return instance;
    }

    public static void reset() {
        sContext = null;
        instance = null;
        sLogFile = null;
    }

    public static long getLogFileSize(File file) {
        if (file == null || !file.exists()) {
            return 0L;
        }
        return file.length();
    }

    public static long getLocalLogFileSize() {
        return getLogFileSize(sLogFile);
    }

    private long readSDCardSpace() {
        long availableBlocks;
        long j = 0;
        if ("mounted".equals(Environment.getExternalStorageState())) {
            try {
                StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
                long blockSize = statFs.getBlockSize();
                if (Build.VERSION.SDK_INT >= 18) {
                    availableBlocks = statFs.getAvailableBlocksLong();
                } else {
                    availableBlocks = statFs.getAvailableBlocks();
                }
                j = availableBlocks * blockSize;
            } catch (Exception unused) {
            }
        }
        OSSLog.logDebug("sd卡存储空间:" + String.valueOf(j) + "kb", false);
        return j;
    }

    private long readSystemSpace() {
        long j;
        long availableBlocks;
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            long blockSize = statFs.getBlockSize();
            if (Build.VERSION.SDK_INT >= 18) {
                availableBlocks = statFs.getAvailableBlocksLong();
            } else {
                availableBlocks = statFs.getAvailableBlocks();
            }
            j = (availableBlocks * blockSize) / 1024;
        } catch (Exception unused) {
            j = 0;
        }
        OSSLog.logDebug("内部存储空间:" + String.valueOf(j) + "kb", false);
        return j;
    }

    public void setUseSdCard(boolean z) {
        this.useSdCard = z;
    }

    public void resetLogFile() {
        OSSLog.logDebug("Reset Log File ... ", false);
        if (!sLogFile.getParentFile().exists()) {
            OSSLog.logDebug("Reset Log make File dir ... ", false);
            sLogFile.getParentFile().mkdir();
        }
        File file = new File(sLogFile.getParent() + "/logs.csv");
        if (file.exists()) {
            file.delete();
        }
        createNewFile(file);
    }

    public void deleteLogFile() {
        File file = new File(sLogFile.getParent() + "/logs.csv");
        if (file.exists()) {
            OSSLog.logDebug("delete Log File ... ", false);
            file.delete();
        }
    }

    public void deleteLogFileDir() {
        deleteLogFile();
        File file = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + LOG_DIR_NAME);
        if (file.exists()) {
            OSSLog.logDebug("delete Log FileDir ... ", false);
            file.delete();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public File getLogFile() {
        File file;
        File file2 = null;
        boolean z = false;
        try {
            boolean z2 = true;
            if (this.useSdCard && Environment.getExternalStorageState().equals("mounted") && Build.VERSION.SDK_INT < 29) {
                if (readSDCardSpace() <= LOG_MAX_SIZE / 1024) {
                    z2 = false;
                }
                file = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + LOG_DIR_NAME);
            } else {
                if (readSystemSpace() <= LOG_MAX_SIZE / 1024) {
                    z2 = false;
                }
                file = new File(sContext.getFilesDir().getPath() + File.separator + LOG_DIR_NAME);
            }
            z = z2;
        } catch (Exception unused) {
            file = null;
        }
        if (z) {
            if (!file.exists()) {
                file.mkdirs();
            }
            file2 = new File(file.getPath() + "/logs.csv");
            if (!file2.exists()) {
                createNewFile(file2);
            }
        }
        return file2;
    }

    private Uri getLogUri() {
        ContentResolver contentResolver = sContext.getContentResolver();
        Uri queryLogUri = queryLogUri();
        if (queryLogUri == null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("_display_name", "logs.csv");
            contentValues.put("mime_type", "file/csv");
            contentValues.put(SpeechWidget.WIDGET_TITLE, "logs.csv");
            contentValues.put("relative_path", "Documents/OSSLog");
            queryLogUri = contentResolver.insert(MediaStore.Files.getContentUri("external"), contentValues);
            try {
                contentResolver.openFileDescriptor(queryLogUri, "w");
            } catch (Exception unused) {
                return null;
            }
        }
        return queryLogUri;
    }

    private Uri queryLogUri() {
        ContentResolver contentResolver = sContext.getContentResolver();
        Uri contentUri = MediaStore.Files.getContentUri("external");
        Cursor query = contentResolver.query(contentUri, new String[]{"_id"}, "relative_path like ? AND _display_name=?", new String[]{"Documents/OSSLog%", "logs.csv"}, null);
        if (query == null || !query.moveToFirst()) {
            return null;
        }
        Uri withAppendedId = ContentUris.withAppendedId(contentUri, query.getLong(0));
        query.close();
        return withAppendedId;
    }

    public void createNewFile(File file) {
        try {
            file.createNewFile();
        } catch (Exception e) {
            OSSLog.logError("Create log file failure !!! " + e.toString(), false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getFunctionInfo(StackTraceElement[] stackTraceElementArr) {
        if (stackTraceElementArr == null) {
            return "[" + sLogSDF.format(new Date()) + "]";
        }
        return null;
    }

    public synchronized void write(Object obj) {
        File file;
        if (OSSLog.isEnableLog()) {
            if (sContext != null && instance != null && (file = sLogFile) != null) {
                if (!file.exists()) {
                    resetLogFile();
                }
                logService.addExecuteTask(new WriteCall(obj));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class WriteCall implements Runnable {
        private Object mStr;

        public WriteCall(Object obj) {
            this.mStr = obj;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (OSSLogToFileUtils.sLogFile != null) {
                OSSLogToFileUtils.getInstance();
                if (OSSLogToFileUtils.getLogFileSize(OSSLogToFileUtils.sLogFile) > OSSLogToFileUtils.LOG_MAX_SIZE) {
                    OSSLogToFileUtils.getInstance().resetLogFile();
                }
                try {
                    PrintWriter printWriter = new PrintWriter((Writer) new FileWriter(OSSLogToFileUtils.sLogFile, true), true);
                    if (!(this.mStr instanceof Throwable)) {
                        printWriter.println(OSSLogToFileUtils.getInstance().getFunctionInfo(null) + " - " + this.mStr.toString());
                    } else {
                        printEx(printWriter);
                    }
                    printWriter.println("------>end of log");
                    printWriter.println();
                    printWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private PrintWriter printEx(PrintWriter printWriter) {
            printWriter.println("crash_time：" + OSSLogToFileUtils.sLogSDF.format(new Date()));
            ((Throwable) this.mStr).printStackTrace(printWriter);
            return printWriter;
        }
    }
}
