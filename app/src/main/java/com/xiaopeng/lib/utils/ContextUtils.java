package com.xiaopeng.lib.utils;

import android.app.ActivityManager;
import android.content.Context;
import com.xiaopeng.carcontrol.carmanager.controller.IScenarioController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class ContextUtils {
    private static final String TAG = "ContextUtils";

    public static boolean isTopActivity(Context context, Class cls) {
        List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) context.getSystemService(IScenarioController.SPACE_CAPSULE_SOURCE_ACTIVITY)).getRunningTasks(1);
        return runningTasks != null && runningTasks.size() > 0 && runningTasks.get(0).topActivity.getClassName().equals(cls.getName());
    }

    public static boolean isServiceWorked(Context context, Class cls) {
        ArrayList arrayList = (ArrayList) ((ActivityManager) context.getSystemService(IScenarioController.SPACE_CAPSULE_SOURCE_ACTIVITY)).getRunningServices(100);
        for (int i = 0; i < arrayList.size(); i++) {
            if (((ActivityManager.RunningServiceInfo) arrayList.get(i)).service.getClassName().toString().equals(cls.getName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isRunningForeground(Context context) {
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) context.getSystemService(IScenarioController.SPACE_CAPSULE_SOURCE_ACTIVITY)).getRunningAppProcesses()) {
            if (runningAppProcessInfo.importance == 100 && runningAppProcessInfo.processName.equals(context.getApplicationInfo().processName)) {
                return true;
            }
        }
        return false;
    }

    public static void moveAppToForegroundOrStartNew(Context context) {
        boolean z;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(IScenarioController.SPACE_CAPSULE_SOURCE_ACTIVITY);
        Iterator<ActivityManager.RunningTaskInfo> it = activityManager.getRunningTasks(100).iterator();
        while (true) {
            z = false;
            if (!it.hasNext()) {
                break;
            }
            ActivityManager.RunningTaskInfo next = it.next();
            if (next.topActivity.getPackageName().equals(context.getPackageName())) {
                activityManager.moveTaskToFront(next.id, 0);
                z = true;
                break;
            }
        }
        if (z) {
            return;
        }
        LogUtils.i(TAG, "start brand new application");
        context.startActivity(context.getPackageManager().getLaunchIntentForPackage(context.getPackageName()));
    }
}
