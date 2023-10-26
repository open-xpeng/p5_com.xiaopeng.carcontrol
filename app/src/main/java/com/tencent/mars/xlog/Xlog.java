package com.tencent.mars.xlog;

import com.tencent.mars.xlog.Log;

/* loaded from: classes.dex */
public class Xlog implements Log.LogImp {
    public static final int AppednerModeAsync = 0;
    public static final int AppednerModeSync = 1;
    public static final int LEVEL_ALL = 0;
    public static final int LEVEL_DEBUG = 1;
    public static final int LEVEL_ERROR = 4;
    public static final int LEVEL_FATAL = 5;
    public static final int LEVEL_INFO = 2;
    public static final int LEVEL_NONE = 6;
    public static final int LEVEL_VERBOSE = 0;
    public static final int LEVEL_WARNING = 3;

    public static native void appenderOpen(int i, int i2, String str, String str2, String str3, int i3, String str4);

    private static String decryptTag(String str) {
        return str;
    }

    public static native void logWrite(XLoggerInfo xLoggerInfo, String str);

    public static native void logWrite2(int i, String str, String str2, String str3, int i2, int i3, long j, long j2, String str4);

    public static native void setAppenderMode(int i);

    public static native void setConsoleLogOpen(boolean z);

    public static native void setErrLogOpen(boolean z);

    public static native void setLogLevel(int i);

    public static native void setMaxAliveTime(long j);

    public static native void setMaxFileSize(long j);

    @Override // com.tencent.mars.xlog.Log.LogImp
    public native void appenderClose();

    @Override // com.tencent.mars.xlog.Log.LogImp
    public native void appenderFlush(boolean z);

    @Override // com.tencent.mars.xlog.Log.LogImp
    public native int getLogLevel();

    /* loaded from: classes.dex */
    static class XLoggerInfo {
        public String filename;
        public String funcname;
        public int level;
        public int line;
        public long maintid;
        public long pid;
        public String tag;
        public long tid;

        XLoggerInfo() {
        }
    }

    public static void open(boolean z, int i, int i2, String str, String str2, String str3, String str4) {
        if (z) {
            System.loadLibrary("c++_shared");
            System.loadLibrary("marsxlog");
        }
        appenderOpen(i, i2, str, str2, str3, 0, str4);
    }

    @Override // com.tencent.mars.xlog.Log.LogImp
    public void logV(String str, String str2, String str3, int i, int i2, long j, long j2, String str4) {
        logWrite2(0, decryptTag(str), str2, str3, i, i2, j, j2, str4);
    }

    @Override // com.tencent.mars.xlog.Log.LogImp
    public void logD(String str, String str2, String str3, int i, int i2, long j, long j2, String str4) {
        logWrite2(1, decryptTag(str), str2, str3, i, i2, j, j2, str4);
    }

    @Override // com.tencent.mars.xlog.Log.LogImp
    public void logI(String str, String str2, String str3, int i, int i2, long j, long j2, String str4) {
        logWrite2(2, decryptTag(str), str2, str3, i, i2, j, j2, str4);
    }

    @Override // com.tencent.mars.xlog.Log.LogImp
    public void logW(String str, String str2, String str3, int i, int i2, long j, long j2, String str4) {
        logWrite2(3, decryptTag(str), str2, str3, i, i2, j, j2, str4);
    }

    @Override // com.tencent.mars.xlog.Log.LogImp
    public void logE(String str, String str2, String str3, int i, int i2, long j, long j2, String str4) {
        logWrite2(4, decryptTag(str), str2, str3, i, i2, j, j2, str4);
    }

    @Override // com.tencent.mars.xlog.Log.LogImp
    public void logF(String str, String str2, String str3, int i, int i2, long j, long j2, String str4) {
        logWrite2(5, decryptTag(str), str2, str3, i, i2, j, j2, str4);
    }
}
