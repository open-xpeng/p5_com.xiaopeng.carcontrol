package com.xiaopeng.lib.framework.netchannelmodule.common;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import com.xiaopeng.lib.http.HttpsUtils;
import com.xiaopeng.lib.utils.NetUtils;
import java.io.File;
import javax.net.ssl.SSLSocketFactory;

/* loaded from: classes2.dex */
public final class GlobalConfig {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String DOT = ".";
    public static final String EVENT_NAME_FAIL = "data_oss_fail";
    public static final String EVENT_NAME_SUCCESS = "data_oss_success";
    public static final String MOLE_OSS_FAILED_BUTTON_ID = "B007";
    public static final String MOLE_OSS_SUCCEED_BUTTON_ID = "B006";
    public static final String MOLE_PAGE_ID = "P00013";
    public static final String PACKAGE_IDENTIFIER = "XmartPackageId";
    private static Application sApplication;
    private static String sApplicationName;
    private static String sSimplePackageName;
    private static final String DEBUG_FLAG_FILE = "/NETCHANNEL_DEBUG.xp";
    private static boolean sDebuggable = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + DEBUG_FLAG_FILE).exists();

    public static void setApplicationContext(Application application) {
        sApplication = application;
    }

    public static Context getApplicationContext() {
        return sApplication;
    }

    public static SSLSocketFactory getSocketFactory() {
        return HttpsUtils.getTLS2SocketFactory(sApplication);
    }

    public static String getApplicationName() {
        Application application = sApplication;
        if (application == null) {
            return "";
        }
        if (sApplicationName == null) {
            sApplicationName = application.getPackageName();
        }
        return sApplicationName;
    }

    public static String getApplicationSimpleName() {
        Application application = sApplication;
        if (application == null) {
            return "";
        }
        if (sSimplePackageName == null) {
            String packageName = application.getPackageName();
            sSimplePackageName = packageName;
            if (packageName.lastIndexOf(DOT) > 0) {
                String str = sSimplePackageName;
                sSimplePackageName = str.substring(str.lastIndexOf(46) + 1);
            }
        }
        return sSimplePackageName;
    }

    public static int getNetworkType() {
        return NetUtils.getNetworkType(sApplication);
    }

    public static boolean debuggable() {
        return sDebuggable;
    }
}
