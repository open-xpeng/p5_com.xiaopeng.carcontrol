package com.xiaopeng.lib.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;
import android.os.Process;
import android.text.TextUtils;
import com.xiaopeng.carcontrol.carmanager.controller.IScenarioController;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/* loaded from: classes2.dex */
public class ProcessUtils {
    private static final String TAG = "ProcUtils";

    private void getRunningAppProcessInfo() {
    }

    public static long getAppMemoryUsed(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(IScenarioController.SPACE_CAPSULE_SOURCE_ACTIVITY);
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : activityManager.getRunningAppProcesses()) {
            if (!TextUtils.isEmpty(runningAppProcessInfo.processName) && runningAppProcessInfo.processName.equals(context.getPackageName())) {
                Debug.MemoryInfo[] processMemoryInfo = activityManager.getProcessMemoryInfo(new int[]{runningAppProcessInfo.pid});
                return (processMemoryInfo[0].dalvikSharedDirty + processMemoryInfo[0].dalvikPrivateDirty) * 1024;
            }
        }
        return 0L;
    }

    public static long getAvailableMemory(Context context) {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ((ActivityManager) context.getSystemService(IScenarioController.SPACE_CAPSULE_SOURCE_ACTIVITY)).getMemoryInfo(memoryInfo);
        return memoryInfo.availMem;
    }

    public static long getTotalMemory(Context context) {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ((ActivityManager) context.getSystemService(IScenarioController.SPACE_CAPSULE_SOURCE_ACTIVITY)).getMemoryInfo(memoryInfo);
        return memoryInfo.totalMem;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:74:0x00ce A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x00c4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x00ba A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v17 */
    /* JADX WARN: Type inference failed for: r3v18 */
    /* JADX WARN: Type inference failed for: r3v2 */
    /* JADX WARN: Type inference failed for: r3v20 */
    /* JADX WARN: Type inference failed for: r3v21 */
    /* JADX WARN: Type inference failed for: r3v22, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r3v3, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r3v5 */
    /* JADX WARN: Type inference failed for: r3v6, types: [java.io.BufferedReader] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:20:0x0050 -> B:76:0x008c). Please submit an issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static long getAppCpuTime() {
        /*
            Method dump skipped, instructions count: 215
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.lib.utils.ProcessUtils.getAppCpuTime():long");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0070 A[Catch: IOException -> 0x0034, TRY_ENTER, TRY_LEAVE, TryCatch #3 {IOException -> 0x0034, blocks: (B:17:0x0030, B:48:0x0070), top: B:75:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0066 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x005c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:97:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:20:0x0035 -> B:74:0x0073). Please submit an issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static long getTotalCpuTime() {
        /*
            Method dump skipped, instructions count: 211
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.lib.utils.ProcessUtils.getTotalCpuTime():long");
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x002e, code lost:
        com.xiaopeng.lib.utils.LogUtils.d("ProcessCpuRate", "Result-->" + r1);
        r1 = r1.split("%");
        r8 = r1[0].split("User");
        r1 = r1[1].split("System");
        r0[0] = java.lang.Integer.parseInt(r8[1].trim());
        r0[1] = java.lang.Integer.parseInt(r1[1].trim());
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x008f, code lost:
        if (r2 == null) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00da, code lost:
        if (r2 == null) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00dc, code lost:
        r2.destroy();
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00df, code lost:
        return r0;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Not initialized variable reg: 5, insn: 0x00e1: MOVE  (r1 I:??[OBJECT, ARRAY]) = (r5 I:??[OBJECT, ARRAY]), block:B:67:0x00e1 */
    /* JADX WARN: Removed duplicated region for block: B:105:0x00f8 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x00ee A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:99:0x00e4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int[] getProcessCpuRate() {
        /*
            Method dump skipped, instructions count: 262
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.lib.utils.ProcessUtils.getProcessCpuRate():int[]");
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0060, code lost:
        if (r7 == 0) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0062, code lost:
        r7.destroy();
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00a4, code lost:
        if (r7 == 0) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x00a7, code lost:
        return r0;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Not initialized variable reg: 3, insn: 0x00a9: MOVE  (r0 I:??[OBJECT, ARRAY]) = (r3 I:??[OBJECT, ARRAY]), block:B:68:0x00a9 */
    /* JADX WARN: Removed duplicated region for block: B:100:0x00c0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:104:0x009c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x00ca  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x00b6 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0092 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:94:0x00ac A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0088 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v3, types: [java.io.InputStreamReader] */
    /* JADX WARN: Type inference failed for: r2v4, types: [java.io.InputStreamReader] */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r2v7, types: [java.io.Reader, java.io.InputStreamReader] */
    /* JADX WARN: Type inference failed for: r7v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r7v1 */
    /* JADX WARN: Type inference failed for: r7v10 */
    /* JADX WARN: Type inference failed for: r7v11 */
    /* JADX WARN: Type inference failed for: r7v13 */
    /* JADX WARN: Type inference failed for: r7v3 */
    /* JADX WARN: Type inference failed for: r7v5 */
    /* JADX WARN: Type inference failed for: r7v6, types: [java.lang.Process] */
    /* JADX WARN: Type inference failed for: r7v7 */
    /* JADX WARN: Type inference failed for: r7v8, types: [java.lang.Process] */
    /* JADX WARN: Type inference failed for: r7v9, types: [java.lang.Process] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String execProcess(java.lang.String r7, int r8) {
        /*
            Method dump skipped, instructions count: 206
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.lib.utils.ProcessUtils.execProcess(java.lang.String, int):java.lang.String");
    }

    public static String getProcessName(Context context, int i) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService(IScenarioController.SPACE_CAPSULE_SOURCE_ACTIVITY)).getRunningAppProcesses();
        if (runningAppProcesses == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.pid == i) {
                return runningAppProcessInfo.processName;
            }
        }
        return null;
    }

    public static String getCurProcessName() {
        Throwable th;
        FileReader fileReader;
        FileReader fileReader2 = null;
        try {
            try {
                fileReader = new FileReader(new File("/proc/" + Process.myPid() + "/cmdline"));
            } catch (Exception e) {
                e = e;
                fileReader = null;
            } catch (Throwable th2) {
                th = th2;
                try {
                    fileReader2.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                throw th;
            }
            try {
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String trim = bufferedReader.readLine().trim();
                bufferedReader.close();
                fileReader.close();
                try {
                    fileReader.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
                return trim;
            } catch (Exception e4) {
                e = e4;
                e.printStackTrace();
                try {
                    fileReader.close();
                } catch (IOException e5) {
                    e5.printStackTrace();
                }
                return null;
            }
        } catch (Throwable th3) {
            th = th3;
            fileReader2.close();
            throw th;
        }
    }
}
