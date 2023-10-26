package com.xiaopeng.lib.bughunter;

import android.content.Context;
import android.util.Log;
import com.xiaopeng.datalog.DataLogModuleEntry;
import com.xiaopeng.lib.bughunter.utils.BugHunterUtils;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IDataLog;
import com.xiaopeng.lib.utils.info.DeviceInfoUtils;

/* loaded from: classes2.dex */
public class StartPerfUtils {
    private static final String PERFORM_EVENT = "perf_start";
    private static final String TAG = "StartPerfUtils";
    private static long appCreateBeginStamp = 0;
    private static long appCreateEndStamp = 0;
    private static long appCreateTime = 0;
    private static long appFirstFrameTime = 0;
    private static long appMainActTime = 0;
    private static long firstFrameEndStamp = 0;
    private static volatile boolean mIsColdStart = true;
    private static long mainActCreateBeginStamp;
    private static long mainActCreateEndStamp;
    private static long mainActCreateTime;
    private static long mainActFirstFrameTime;
    private static long mainActRestartBeginStamp;
    private static long mainActRestartEndStamp;
    private static long mainActRestartTime;
    private static long mainActResumeBeginStamp;
    private static long mainActResumeEndStamp;
    private static long mainActResumeTime;
    private static long mainActStartBeginStamp;
    private static long mainActStartEndStamp;
    private static long mainActStartTime;

    public static void appOnCreateBegin() {
        appCreateBeginStamp = System.currentTimeMillis();
    }

    public static void appOnCreateEnd() {
        long currentTimeMillis = System.currentTimeMillis();
        appCreateEndStamp = currentTimeMillis;
        appCreateTime = currentTimeMillis - appCreateBeginStamp;
    }

    public static void onCreateBegin() {
        long currentTimeMillis = System.currentTimeMillis();
        mainActCreateBeginStamp = currentTimeMillis;
        appMainActTime = currentTimeMillis - appCreateEndStamp;
    }

    public static void onCreateEnd() {
        long currentTimeMillis = System.currentTimeMillis();
        mainActCreateEndStamp = currentTimeMillis;
        mainActCreateTime = currentTimeMillis - mainActCreateBeginStamp;
    }

    public static void firstFrameEnd(Context context) {
        long currentTimeMillis = System.currentTimeMillis();
        firstFrameEndStamp = currentTimeMillis;
        mainActFirstFrameTime = currentTimeMillis - mainActCreateBeginStamp;
        appFirstFrameTime = currentTimeMillis - appCreateBeginStamp;
        if (mIsColdStart) {
            mIsColdStart = false;
        } else {
            appCreateTime = -1L;
        }
        sendPerformData(context);
    }

    private static void sendPerformData(Context context) {
        IDataLog iDataLog;
        String packageName = context.getPackageName();
        try {
            iDataLog = (IDataLog) Module.get(DataLogModuleEntry.class).get(IDataLog.class);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "<<<< error, get IDataLog module occurred an exception, " + e.getMessage());
            iDataLog = null;
        }
        if (iDataLog == null || DeviceInfoUtils.isInternationalVer()) {
            return;
        }
        iDataLog.sendStatData(iDataLog.buildStat().setProperty("appName", packageName).setProperty("appVer", BugHunterUtils.getVersionName(context, packageName)).setProperty("appstep0", Long.valueOf(appCreateTime)).setProperty("appstep1", Long.valueOf(appMainActTime)).setProperty("appstep2", Long.valueOf(mainActCreateTime)).setProperty("appstep3", Long.valueOf(mainActFirstFrameTime)).setProperty("appstep4", Long.valueOf(mainActStartTime)).setProperty("appstepend", Long.valueOf(appFirstFrameTime)).setProperty("apprestart", Long.valueOf(mainActRestartTime)).setProperty("appresume0", Long.valueOf(mainActResumeTime)).setEventName(PERFORM_EVENT).build());
    }

    public static void onStartBegin() {
        mainActStartBeginStamp = System.currentTimeMillis();
    }

    public static void onStartEnd() {
        long currentTimeMillis = System.currentTimeMillis();
        mainActStartEndStamp = currentTimeMillis;
        mainActStartTime = currentTimeMillis - mainActStartBeginStamp;
    }

    public static void onReStartBegin() {
        mainActRestartBeginStamp = System.currentTimeMillis();
    }

    public static void onReStartEnd() {
        long currentTimeMillis = System.currentTimeMillis();
        mainActRestartEndStamp = currentTimeMillis;
        mainActRestartTime = currentTimeMillis - mainActRestartBeginStamp;
    }

    public static void onResumeBegin() {
        mainActResumeBeginStamp = System.currentTimeMillis();
    }

    public static void onResumeEnd() {
        long currentTimeMillis = System.currentTimeMillis();
        mainActResumeEndStamp = currentTimeMillis;
        mainActResumeTime = currentTimeMillis - mainActResumeBeginStamp;
    }
}
