package com.xiaopeng.lib.bughunter;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.xiaopeng.carcontrol.carmanager.controller.IScenarioController;
import com.xiaopeng.datalog.DataLogModuleEntry;
import com.xiaopeng.lib.bughunter.anr.Caton;
import com.xiaopeng.lib.bughunter.anr.Config;
import com.xiaopeng.lib.bughunter.utils.BugHunterUtils;
import com.xiaopeng.lib.framework.module.IModuleEntry;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IDataLog;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IMoleEventBuilder;
import com.xiaopeng.lib.utils.info.BuildInfoUtils;
import com.xiaopeng.lib.utils.info.DeviceInfoUtils;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class BugHunter {
    private static final String CATON_EVENT = "perf_caton";
    private static final String KEY_ANR_FLAG = "anr";
    private static final String KEY_APP_NAME = "appName";
    private static final String KEY_APP_VER = "appVer";
    private static final String KEY_EVENT = "_event";
    private static final String KEY_EVENT_TIME = "_time";
    private static final String KEY_MCU_VER = "_mcuver";
    private static final String KEY_MEM_INFO = "memInfo";
    private static final String KEY_MODULE = "_module";
    private static final String KEY_MODULE_VER = "_module_version";
    private static final String KEY_NETWORK = "_network";
    private static final String KEY_STACK_INFO = "stackInfo";
    private static final String KEY_STACK_MD5 = "md5";
    private static final String KEY_STUCK_TIME = "elapseTime";
    private static final String KEY_SYSTEM_BOOT_TIME = "_st_time";
    private static final String SEPARATOR = "#";
    private static final String TAG = "libBugHunter";
    private static boolean dumpToSdCardFlag;

    public static void init(Context context) {
        init(context, false, true, false);
    }

    public static void init(Context context, boolean z, boolean z2, boolean z3) {
        try {
            initCaton(context.getApplicationContext(), z, z2, z3);
            Module.register(DataLogModuleEntry.class, new DataLogModuleEntry(context));
            Module.get(DataLogModuleEntry.class).get(IDataLog.class);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private static void initCaton(final Context context, boolean z, boolean z2, boolean z3) {
        long j = 400;
        long j2 = 100;
        if (BuildInfoUtils.isUserVersion()) {
            j = 60000;
            j2 = 1000;
        } else if (z || new File("/sdcard/Log/catonflag").exists()) {
            j = 100;
        } else {
            j2 = 400;
        }
        if (z3) {
            dumpToSdCardFlag = true;
        } else if (new File("/sdcard/Log/catondumpflag").exists()) {
            dumpToSdCardFlag = true;
        }
        Caton.initialize(new Caton.Builder(context.getApplicationContext()).monitorMode(Caton.MonitorMode.LOOPER).loggingEnabled(z2).collectInterval(j).thresholdTime(j2).callback(new Caton.Callback() { // from class: com.xiaopeng.lib.bughunter.BugHunter.1
            @Override // com.xiaopeng.lib.bughunter.anr.Caton.Callback
            public void onBlockOccurs(String[] strArr, boolean z4, long... jArr) {
                String packageName = context.getPackageName();
                StringBuilder sb = new StringBuilder();
                int length = strArr.length;
                String calcStackTraceMd5 = BugHunter.calcStackTraceMd5(strArr[0]);
                int i = length - 1;
                int i2 = 0;
                while (true) {
                    if (i < 0) {
                        break;
                    }
                    i2++;
                    if (i2 == length) {
                        sb.append(strArr[i]);
                        break;
                    } else {
                        sb.append(strArr[i]).append("#");
                        i--;
                    }
                }
                long j3 = jArr[0];
                String printStackTrace = Config.LOG_ENABLED ? BugHunter.printStackTrace(calcStackTraceMd5, context, packageName, strArr, j3, jArr[1]) : "";
                IDataLog iDataLog = null;
                IModuleEntry iModuleEntry = Module.get(DataLogModuleEntry.class);
                if (iModuleEntry != null) {
                    try {
                        iDataLog = (IDataLog) iModuleEntry.get(IDataLog.class);
                    } catch (Throwable th) {
                        th.printStackTrace();
                        Log.e(BugHunter.TAG, "<<<< error, get IDataLog module occurred an exception, " + th.getMessage());
                    }
                } else {
                    Log.e(BugHunter.TAG, "<<<< error, can not get DataLogModuleEntry");
                }
                if (iDataLog != null) {
                    if (!DeviceInfoUtils.isInternationalVer()) {
                        IMoleEventBuilder buildMoleEvent = iDataLog.buildMoleEvent();
                        buildMoleEvent.setEvent(BugHunter.CATON_EVENT).setPageId("P00010").setButtonId("B001").setProperty(BugHunter.KEY_APP_NAME, packageName).setProperty(BugHunter.KEY_APP_VER, BugHunter.getVersionName(context, packageName)).setProperty(BugHunter.KEY_ANR_FLAG, z4).setProperty(BugHunter.KEY_STACK_MD5, calcStackTraceMd5).setProperty(BugHunter.KEY_STUCK_TIME, Long.valueOf(j3)).setProperty(BugHunter.KEY_STACK_INFO, sb.toString()).setProperty(BugHunter.KEY_MEM_INFO, printStackTrace);
                        iDataLog.sendStatData(buildMoleEvent.build());
                    }
                } else {
                    Log.e(BugHunter.TAG, "<<<< upload caton log fail, can not get IDataLog module!");
                }
                if (BugHunter.dumpToSdCardFlag) {
                    Context context2 = context;
                    byte[] bytes = BugHunter.getJsonStuckLog(context2, packageName, BugHunter.getVersionName(context2, packageName), j3, calcStackTraceMd5, sb.toString()).getBytes();
                    if (BugHunter.dumpToSdCardFlag) {
                        BugHunter.dumpCatonInfo("/sdcard/Log/caton", packageName, bytes);
                    }
                }
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String calcStackTraceMd5(String str) {
        int indexOf = str.indexOf("\n");
        if (indexOf > 0) {
            str = str.substring(indexOf + 1);
        }
        return MD5Utils.getMD5(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getJsonStuckLog(Context context, String str, String str2, long j, String str3, String str4) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.putOpt("_event", CATON_EVENT);
            jSONObject.putOpt("_module", "perf");
            jSONObject.putOpt("_mcuver", BugHunterUtils.getMCUVer());
            jSONObject.putOpt("_module_version", str2);
            jSONObject.putOpt("_st_time", Long.valueOf(SystemClock.uptimeMillis() / 1000));
            jSONObject.putOpt("_time", Long.valueOf(System.currentTimeMillis()));
            jSONObject.putOpt("_network", BugHunterUtils.getNetworkType(context));
            jSONObject.putOpt(KEY_APP_NAME, str);
            jSONObject.putOpt(KEY_APP_VER, str2);
            jSONObject.putOpt(KEY_ANR_FLAG, false);
            jSONObject.putOpt(KEY_STUCK_TIME, Long.valueOf(j));
            jSONObject.putOpt(KEY_STACK_MD5, str3);
            jSONObject.putOpt(KEY_STACK_INFO, str4);
            return jSONObject.toString();
        } catch (Throwable th) {
            Log.e(TAG, "error in function getJsonCatonLog, " + th.getMessage());
            return "";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String printStackTrace(String str, Context context, String str2, String[] strArr, long j, long j2) {
        long j3;
        long j4;
        JSONObject jSONObject = new JSONObject();
        StringBuffer stringBuffer = new StringBuffer("");
        long j5 = 0;
        if (context != null) {
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            ((ActivityManager) context.getSystemService(IScenarioController.SPACE_CAPSULE_SOURCE_ACTIVITY)).getMemoryInfo(memoryInfo);
            long j6 = memoryInfo.availMem / 1048576;
            j4 = memoryInfo.totalMem / 1048576;
            j3 = memoryInfo.threshold / 1048576;
            stringBuffer.append("availMem:").append(j6);
            stringBuffer.append("totalMem:").append(j4);
            stringBuffer.append("threshold:").append(j3);
            j5 = j6;
        } else {
            j3 = 0;
            j4 = 0;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\n----------------caton log [ ");
        if (!TextUtils.isEmpty(str2)) {
            sb.append(str2);
        }
        sb.append(" ]");
        for (int length = strArr.length - 1; length >= 0; length--) {
            String str3 = strArr[length];
            sb.append("\n");
            sb.append(str3);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
        try {
            jSONObject.put(KEY_STACK_MD5, str);
            jSONObject.put("pkgName", str2);
            jSONObject.put("time", simpleDateFormat.format(Calendar.getInstance().getTime()));
            jSONObject.put("ElapseTime", j);
            jSONObject.put("threadElapseTime", j2);
            jSONObject.put("availMem", j5);
            jSONObject.put("totalMem", j4);
            jSONObject.put("threshold", j3);
            jSONObject.put("catonLog", sb.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Config.log(TAG, jSONObject.toString());
        return stringBuffer.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00aa A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r8v1, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r8v11 */
    /* JADX WARN: Type inference failed for: r8v12 */
    /* JADX WARN: Type inference failed for: r8v9, types: [java.io.RandomAccessFile] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void dumpCatonInfo(java.lang.String r7, java.lang.String r8, byte[] r9) {
        /*
            java.io.File r0 = new java.io.File
            r0.<init>(r7)
            boolean r1 = r0.exists()
            if (r1 != 0) goto L51
            boolean r1 = r0.mkdirs()
            java.lang.String r2 = "libBugHunter"
            if (r1 == 0) goto L4c
            r1 = 1
            r3 = 0
            boolean r4 = r0.setReadable(r1, r3)
            boolean r5 = r0.setWritable(r1, r3)
            boolean r0 = r0.setExecutable(r1, r3)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = "caton LogDir setReadable: "
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.StringBuilder r1 = r1.append(r4)
            java.lang.String r3 = "; setWritable: "
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.StringBuilder r1 = r1.append(r5)
            java.lang.String r3 = "; setExecutable: "
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.StringBuilder r0 = r1.append(r0)
            java.lang.String r0 = r0.toString()
            com.xiaopeng.lib.utils.LogUtils.d(r2, r0)
            goto L51
        L4c:
            java.lang.String r0 = "make caton LogDir failed"
            com.xiaopeng.lib.utils.LogUtils.w(r2, r0)
        L51:
            java.io.File r0 = new java.io.File
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.StringBuilder r7 = r1.append(r7)
            java.lang.String r1 = "/"
            java.lang.StringBuilder r7 = r7.append(r1)
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r8 = ".log"
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r7 = r7.toString()
            r0.<init>(r7)
            r7 = 0
            java.io.RandomAccessFile r8 = new java.io.RandomAccessFile     // Catch: java.lang.Throwable -> L97 java.lang.Exception -> L9c
            java.lang.String r1 = "rw"
            r8.<init>(r0, r1)     // Catch: java.lang.Throwable -> L97 java.lang.Exception -> L9c
            long r0 = r8.length()     // Catch: java.lang.Exception -> L95 java.lang.Throwable -> La7
            r8.seek(r0)     // Catch: java.lang.Exception -> L95 java.lang.Throwable -> La7
            r8.write(r9)     // Catch: java.lang.Exception -> L95 java.lang.Throwable -> La7
            java.lang.String r7 = "\n\n"
            r8.writeBytes(r7)     // Catch: java.lang.Exception -> L95 java.lang.Throwable -> La7
            java.io.FileDescriptor r7 = r8.getFD()     // Catch: java.lang.Exception -> L95 java.lang.Throwable -> La7
            r7.sync()     // Catch: java.lang.Exception -> L95 java.lang.Throwable -> La7
        L91:
            r8.close()     // Catch: java.lang.Exception -> La6
            goto La6
        L95:
            r7 = move-exception
            goto La0
        L97:
            r8 = move-exception
            r6 = r8
            r8 = r7
            r7 = r6
            goto La8
        L9c:
            r8 = move-exception
            r6 = r8
            r8 = r7
            r7 = r6
        La0:
            r7.printStackTrace()     // Catch: java.lang.Throwable -> La7
            if (r8 == 0) goto La6
            goto L91
        La6:
            return
        La7:
            r7 = move-exception
        La8:
            if (r8 == 0) goto Lad
            r8.close()     // Catch: java.lang.Exception -> Lad
        Lad:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.lib.bughunter.BugHunter.dumpCatonInfo(java.lang.String, java.lang.String, byte[]):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getVersionName(Context context, String str) {
        PackageInfo packageInfo = getPackageInfo(context, str);
        return packageInfo == null ? "" : packageInfo.versionName;
    }

    private static PackageInfo getPackageInfo(Context context, String str) {
        try {
            PackageManager packageManager = context.getPackageManager();
            if (str == null) {
                str = context.getPackageName();
            }
            return packageManager.getPackageInfo(str, 16384);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
