package com.xiaopeng.carcontrol.util;

import android.content.Context;
import android.util.Log;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public final class LogUtils {
    private static final int BUFFER_LIMIT = 20;
    private static final int FILE_KEEP_COUNT = 8;
    private static final String LOG_FILE_NAME = "my_log_";
    private static final int LOG_LEVEL_DEBUG = 2;
    private static final int LOG_LEVEL_RELEASE = 1;
    private static StringBuffer sBuffer = null;
    private static Context sContext = null;
    private static final int sLogLevel = 2;
    private static String sProcessName;
    private static ExecutorService sSingleExecutor;
    private static BufferedWriter sWriter;
    private static final SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
    private static int sCount = 0;
    private static boolean sIsLogBackUp = false;

    public static void init(Context context) {
        sContext = context.getApplicationContext();
    }

    public static void setProcessName(boolean isMainProcess) {
        sProcessName = isMainProcess ? "c_c " : "c_c:unity ";
    }

    public static void i(String tag, String message) {
        i(tag, message, 2);
    }

    public static void i(String tag, String message, boolean debug) {
        if (debug) {
            i(tag, message, 2);
        } else {
            i(tag, message, 1);
        }
    }

    private static void i(String tag, String message, int level) {
        if (level <= 2) {
            Log.i(sProcessName + tag, message);
            if (sIsLogBackUp) {
                writeLogToFile("I", tag, message, null);
            }
        }
    }

    public static void d(String tag, String message) {
        d(tag, message, 2);
    }

    public static void d(String tag, String message, boolean debug) {
        if (debug) {
            d(tag, message, 2);
        } else {
            d(tag, message, 1);
        }
    }

    private static void d(String tag, String message, int level) {
        if (level <= 2) {
            Log.i(sProcessName + tag, message);
            if (sIsLogBackUp) {
                writeLogToFile("I", tag, message, null);
            }
        }
    }

    public static void debug(String tag, String message) {
        debug(tag, message, 2);
    }

    public static void debug(String tag, String message, boolean debug) {
        if (debug) {
            debug(tag, message, 2);
        } else {
            debug(tag, message, 1);
        }
    }

    private static void debug(String tag, String message, int level) {
        if (level <= 2) {
            Log.d(sProcessName + tag, message);
            if (sIsLogBackUp) {
                writeLogToFile("D", tag, message, null);
            }
        }
    }

    public static void w(String tag, String message) {
        w(tag, message, 2);
    }

    public static void w(String tag, String message, boolean debug) {
        if (debug) {
            w(tag, message, 2);
        } else {
            w(tag, message, 1);
        }
    }

    private static void w(String tag, String message, int level) {
        if (level <= 2) {
            Log.w(sProcessName + tag, message);
            if (sIsLogBackUp) {
                writeLogToFile("W", tag, message, null);
            }
        }
    }

    public static void e(String tag, String message) {
        e(tag, message, 2);
    }

    public static void e(String tag, String message, boolean debug) {
        if (debug) {
            e(tag, message, 2);
        } else {
            e(tag, message, 1);
        }
    }

    private static void e(String tag, String message, int level) {
        if (level <= 2) {
            Log.e(sProcessName + tag, message);
            if (sIsLogBackUp) {
                writeLogToFile("E", tag, message, null);
            }
        }
    }

    public static void e(String tag, String message, Throwable tr) {
        e(tag, message, tr, 2);
    }

    public static void e(String tag, String message, Throwable tr, boolean debug) {
        if (debug) {
            e(tag, message, tr, 2);
        } else {
            e(tag, message, tr, 1);
        }
    }

    private static void e(String tag, String message, Throwable tr, int level) {
        if (level <= 2) {
            Log.e(sProcessName + tag, message, tr);
            if (sIsLogBackUp) {
                writeLogToFile("E", tag, message, tr);
            }
        }
    }

    public static boolean isLogBackUp() {
        return sIsLogBackUp;
    }

    public static void setLogBackUp(boolean needBackUp) {
        d(sProcessName, "setLogBackUp: " + needBackUp);
        FileOutputStream fileOutputStream = null;
        if (needBackUp) {
            sSingleExecutor = Executors.newSingleThreadExecutor();
            sBuffer = new StringBuffer();
            renameFiles();
            try {
                try {
                    fileOutputStream = sContext.openFileOutput("my_log_0", 32768);
                    sWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                closeSilently(fileOutputStream);
                sIsLogBackUp = true;
                return;
            } catch (Throwable th) {
                closeSilently(fileOutputStream);
                throw th;
            }
        }
        sIsLogBackUp = false;
        ExecutorService executorService = sSingleExecutor;
        if (executorService != null) {
            executorService.shutdownNow();
            try {
                sSingleExecutor.awaitTermination(100L, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
            sSingleExecutor = null;
        }
        if (sBuffer != null) {
            sBuffer = null;
        }
        BufferedWriter bufferedWriter = sWriter;
        try {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
                sWriter = null;
            }
        } finally {
            closeSilently(sWriter);
        }
    }

    private static void writeLogToFile(final String level, final String tag, final String message, final Throwable tr) {
        ExecutorService executorService = sSingleExecutor;
        if (executorService == null || sBuffer == null || sWriter == null) {
            return;
        }
        executorService.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.util.-$$Lambda$LogUtils$sYvoO8xmdnt90vmi-SOO6bn8hEI
            @Override // java.lang.Runnable
            public final void run() {
                LogUtils.lambda$writeLogToFile$0(level, tag, message, tr);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$writeLogToFile$0(final String level, final String tag, final String message, final Throwable tr) {
        StringBuilder sb = new StringBuilder();
        sb.append(sFormat.format(Long.valueOf(System.currentTimeMillis())));
        sb.append(" ").append(level).append(" ").append(sProcessName).append(" ").append(tag).append(": ");
        if (message != null) {
            for (String str : message.split("\n")) {
                sBuffer.append((CharSequence) sb).append(str).append('\n');
                sCount++;
            }
        } else {
            sBuffer.append((CharSequence) sb).append("null").append('\n');
            sCount++;
        }
        if (tr != null) {
            sBuffer.append((CharSequence) sb).append(tr.getClass().getName()).append(": ").append(tr.getMessage()).append("\n");
            sCount++;
            for (StackTraceElement stackTraceElement : tr.getStackTrace()) {
                sBuffer.append((CharSequence) sb).append("at ").append(stackTraceElement).append("\n");
                sCount++;
            }
        }
        if (sCount >= 20) {
            try {
                sWriter.write(sBuffer.toString());
                sWriter.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
            sCount = 0;
            sBuffer = new StringBuffer();
        }
    }

    private static void renameFiles() {
        File filesDir = sContext.getFilesDir();
        File file = new File(filesDir, "my_log_7");
        if (file.exists()) {
            file.delete();
        }
        for (int i = 6; i >= 0; i--) {
            File file2 = new File(filesDir, LOG_FILE_NAME + i);
            if (file2.exists()) {
                file2.renameTo(new File(filesDir, LOG_FILE_NAME + (i + 1)));
            }
        }
    }

    public static void closeSilently(Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
