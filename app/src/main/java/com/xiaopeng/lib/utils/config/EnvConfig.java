package com.xiaopeng.lib.utils.config;

import android.text.TextUtils;
import android.util.Log;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingConstants;
import com.xiaopeng.lib.utils.FileUtils;
import com.xiaopeng.lib.utils.info.BuildInfoUtils;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Properties;

/* loaded from: classes2.dex */
public final class EnvConfig {
    static final String KEY_EXPIRED_TIME = "expired_time";
    static final String KEY_MAIN_HOST = "main_host";
    static final String PER_ENV_FILE_PATH = "/sdcard/pre_env.ini";
    private static final String TAG = "EnvConfig";
    private static Properties sConfigs = new Properties();

    static {
        init();
    }

    private EnvConfig() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Properties cloneConfig() {
        Properties properties = new Properties();
        properties.putAll(sConfigs);
        return properties;
    }

    public static boolean hasKey(String str) {
        return sConfigs.containsKey(str);
    }

    public static boolean hasValidConfig() {
        return sConfigs.size() > 0 && hasKey(KEY_MAIN_HOST);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void removeKey(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        sConfigs.remove(str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setString(String str, String str2) {
        if (str2 == null) {
            str2 = "";
        }
        sConfigs.put(str, str2);
    }

    public static String getString(String str, String str2) {
        if (BuildInfoUtils.isDebuggableVersion()) {
            String property = sConfigs.getProperty(str);
            if (!TextUtils.isEmpty(property)) {
                return property;
            }
        }
        return str2;
    }

    public static String getHostInString(String str, String str2) {
        return getString(KEY_MAIN_HOST, str, str2);
    }

    public static String getString(String str, String str2, String str3) {
        if (BuildInfoUtils.isDebuggableVersion()) {
            String property = sConfigs.getProperty(str);
            if (!TextUtils.isEmpty(property)) {
                return property;
            }
        }
        return BuildInfoUtils.isLanVersion() ? str2 : str3;
    }

    public static int getInt(String str, int i) {
        if (BuildInfoUtils.isDebuggableVersion()) {
            String property = sConfigs.getProperty(str);
            if (TextUtils.isEmpty(property)) {
                return i;
            }
            try {
                return Integer.parseInt(property);
            } catch (Exception e) {
                e.printStackTrace();
                return i;
            }
        }
        return i;
    }

    public static int getInt(String str, int i, int i2) {
        if (BuildInfoUtils.isDebuggableVersion()) {
            String property = sConfigs.getProperty(str);
            if (!TextUtils.isEmpty(property)) {
                try {
                    return Integer.parseInt(property);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return BuildInfoUtils.isLanVersion() ? i : i2;
    }

    public static long getLong(String str, long j) {
        if (BuildInfoUtils.isDebuggableVersion()) {
            String property = sConfigs.getProperty(str);
            if (TextUtils.isEmpty(property)) {
                return j;
            }
            try {
                return Long.parseLong(property);
            } catch (Exception e) {
                e.printStackTrace();
                return j;
            }
        }
        return j;
    }

    public static long getLong(String str, long j, long j2) {
        if (BuildInfoUtils.isDebuggableVersion()) {
            String property = sConfigs.getProperty(str);
            if (!TextUtils.isEmpty(property)) {
                try {
                    return Long.parseLong(property);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return BuildInfoUtils.isLanVersion() ? j : j2;
    }

    public static double getDouble(String str, double d) {
        if (BuildInfoUtils.isDebuggableVersion()) {
            String property = sConfigs.getProperty(str);
            if (TextUtils.isEmpty(property)) {
                return d;
            }
            try {
                return Double.parseDouble(property);
            } catch (Exception e) {
                e.printStackTrace();
                return d;
            }
        }
        return d;
    }

    public static double getDouble(String str, double d, double d2) {
        if (BuildInfoUtils.isDebuggableVersion()) {
            String property = sConfigs.getProperty(str);
            if (!TextUtils.isEmpty(property)) {
                try {
                    return Double.parseDouble(property);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return BuildInfoUtils.isLanVersion() ? d : d2;
    }

    private static boolean strToBoolean(String str) {
        if ("0".equals(str)) {
            return false;
        }
        if ("1".equals(str)) {
            return true;
        }
        return Boolean.parseBoolean(str);
    }

    public static boolean getBoolean(String str, boolean z) {
        if (BuildInfoUtils.isDebuggableVersion()) {
            String property = sConfigs.getProperty(str);
            if (!TextUtils.isEmpty(property)) {
                return strToBoolean(property);
            }
        }
        return z;
    }

    public static boolean getBoolean(String str, boolean z, boolean z2) {
        if (BuildInfoUtils.isDebuggableVersion()) {
            String property = sConfigs.getProperty(str);
            if (!TextUtils.isEmpty(property)) {
                return strToBoolean(property);
            }
        }
        return BuildInfoUtils.isLanVersion() ? z : z2;
    }

    private static long convertDateStringToMillis(String str) {
        try {
            String str2 = "yyyyMMdd HH:mm:ss";
            if (str.indexOf(QuickSettingConstants.JOINER) < 0) {
                if (str.indexOf(" ") < 0) {
                    str2 = str.length() <= 8 ? "yyyyMMdd" : "yyyyMMddHHmmss";
                } else {
                    str2 = "yyyyMMdd HHmmss";
                }
            }
            return new SimpleDateFormat(str2).parse(str).getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return -1L;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v10, types: [int] */
    /* JADX WARN: Type inference failed for: r1v11, types: [int] */
    /* JADX WARN: Type inference failed for: r1v12, types: [java.lang.String] */
    private static void init() {
        FileInputStream fileInputStream;
        File file;
        if (!BuildInfoUtils.isDebuggableVersion()) {
            return;
        }
        BufferedInputStream bufferedInputStream = null;
        try {
            try {
                file = new File(PER_ENV_FILE_PATH);
            } catch (Exception e) {
                e = e;
                fileInputStream = null;
            } catch (Throwable th) {
                th = th;
                fileInputStream = null;
            }
            if (!file.exists()) {
                FileUtils.closeQuietly(null);
                FileUtils.closeQuietly(null);
                return;
            }
            fileInputStream = new FileInputStream(file);
            try {
                BufferedInputStream bufferedInputStream2 = new BufferedInputStream(fileInputStream);
                try {
                    sConfigs.load(bufferedInputStream2);
                    Log.w(TAG, "<<<< warning, load file: pre_env.ini !!!");
                    String property = sConfigs.getProperty(KEY_EXPIRED_TIME, null);
                    boolean isEmpty = TextUtils.isEmpty(property);
                    String str = property;
                    if (!isEmpty) {
                        long convertDateStringToMillis = convertDateStringToMillis(property);
                        ?? r1 = (convertDateStringToMillis > 0L ? 1 : (convertDateStringToMillis == 0L ? 0 : -1));
                        str = r1;
                        if (r1 > 0) {
                            ?? r12 = (System.currentTimeMillis() > convertDateStringToMillis ? 1 : (System.currentTimeMillis() == convertDateStringToMillis ? 0 : -1));
                            str = r12;
                            if (r12 >= 0) {
                                Log.w(TAG, "<<<< file pre_env.ini is expired!");
                                sConfigs.clear();
                                str = "<<<< file pre_env.ini is expired!";
                            }
                        }
                    }
                    FileUtils.closeQuietly(bufferedInputStream2);
                    bufferedInputStream = str;
                } catch (Exception e2) {
                    e = e2;
                    bufferedInputStream = bufferedInputStream2;
                    e.printStackTrace();
                    FileUtils.closeQuietly(bufferedInputStream);
                    bufferedInputStream = bufferedInputStream;
                    FileUtils.closeQuietly(fileInputStream);
                } catch (Throwable th2) {
                    th = th2;
                    bufferedInputStream = bufferedInputStream2;
                    FileUtils.closeQuietly(bufferedInputStream);
                    FileUtils.closeQuietly(fileInputStream);
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
            }
            FileUtils.closeQuietly(fileInputStream);
        } catch (Throwable th3) {
            th = th3;
        }
    }
}
