package com.alibaba.mtl.log.e;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import com.alibaba.mtl.appmonitor.SdkMeta;
import com.alibaba.mtl.log.model.LogField;
import com.ut.device.UTDevice;
import com.xiaopeng.speech.protocol.event.OOBEEvent;
import com.xiaopeng.xvs.xid.sync.api.ISync;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* compiled from: DeviceUtil.java */
/* loaded from: classes.dex */
public class d {
    private static Map<String, String> v;

    public static synchronized Map<String, String> a(Context context) {
        synchronized (d.class) {
            Map<String, String> map = v;
            if (map != null) {
                map.put(LogField.CHANNEL.toString(), b.o());
                v.put(LogField.APPKEY.toString(), b.getAppkey());
                String imei = m.getImei(context);
                String imsi = m.getImsi(context);
                if (TextUtils.isEmpty(imei) || TextUtils.isEmpty(imsi)) {
                    imei = "";
                    imsi = "";
                }
                v.put(LogField.IMEI.toString(), imei);
                v.put(LogField.IMSI.toString(), imsi);
                a(v, context);
                return v;
            }
            HashMap hashMap = new HashMap();
            v = hashMap;
            if (context != null) {
                if (hashMap != null) {
                    try {
                        String imei2 = m.getImei(context);
                        String imsi2 = m.getImsi(context);
                        if (TextUtils.isEmpty(imei2) || TextUtils.isEmpty(imsi2)) {
                            imei2 = "";
                            imsi2 = "";
                        }
                        v.put(LogField.IMEI.toString(), imei2);
                        v.put(LogField.IMSI.toString(), imsi2);
                        v.put(LogField.BRAND.toString(), Build.BRAND);
                        v.put(LogField.DEVICE_MODEL.toString(), Build.MODEL);
                        v.put(LogField.RESOLUTION.toString(), c(context));
                        v.put(LogField.CHANNEL.toString(), b.o());
                        v.put(LogField.APPKEY.toString(), b.getAppkey());
                        v.put(LogField.APPVERSION.toString(), d(context));
                        v.put(LogField.LANGUAGE.toString(), b(context));
                        v.put(LogField.OS.toString(), s());
                        v.put(LogField.OSVERSION.toString(), r());
                        v.put(LogField.SDKVERSION.toString(), SdkMeta.SDK_VERSION);
                        v.put(LogField.SDKTYPE.toString(), "mini");
                        try {
                            v.put(LogField.UTDID.toString(), UTDevice.getUtdid(context));
                        } catch (Throwable th) {
                            Log.e("DeviceUtil", "utdid4all jar doesn't exist, please copy the libs folder.");
                            th.printStackTrace();
                        }
                        a(v, context);
                    } catch (Exception unused) {
                        return null;
                    }
                }
                return v;
            }
            return null;
        }
    }

    private static String r() {
        String str = Build.VERSION.RELEASE;
        if (i()) {
            String property = System.getProperty("ro.yunos.version");
            if (!TextUtils.isEmpty(property)) {
                return property;
            }
            str = v();
            if (!TextUtils.isEmpty(str)) {
            }
        }
        return str;
    }

    private static String s() {
        return (!i() || j()) ? "a" : "y";
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v1 */
    /* JADX WARN: Type inference failed for: r6v4, types: [android.telephony.TelephonyManager] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:8:0x0033 -> B:21:0x0045). Please submit an issue!!! */
    private static void a(Map<String, String> map, Context context) {
        Context context2;
        String str = "Unknown";
        try {
            String[] networkState = l.getNetworkState(context);
            map.put(LogField.ACCESS.toString(), networkState[0]);
            if (networkState[0].equals("2G/3G")) {
                map.put(LogField.ACCESS_SUBTYPE.toString(), networkState[1]);
                context2 = context;
            } else {
                map.put(LogField.ACCESS_SUBTYPE.toString(), "Unknown");
                context2 = context;
            }
        } catch (Exception unused) {
            map.put(LogField.ACCESS.toString(), "Unknown");
            map.put(LogField.ACCESS_SUBTYPE.toString(), "Unknown");
            context2 = context;
        }
        try {
            context = (TelephonyManager) context2.getSystemService("phone");
            String str2 = "";
            if (context != 0 && context.getSimState() == 5) {
                str2 = context.getNetworkOperatorName();
            }
            if (!TextUtils.isEmpty(str2)) {
                str = str2;
            }
            map.put(LogField.CARRIER.toString(), str);
        } catch (Exception unused2) {
        }
    }

    private static String b(Context context) {
        try {
            return Locale.getDefault().getLanguage();
        } catch (Throwable unused) {
            return "Unknown";
        }
    }

    private static String c(Context context) {
        try {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            int i = displayMetrics.widthPixels;
            int i2 = displayMetrics.heightPixels;
            if (i > i2) {
                int i3 = i ^ i2;
                i2 ^= i3;
                i = i3 ^ i2;
            }
            return i2 + "*" + i;
        } catch (Exception unused) {
            return "Unknown";
        }
    }

    public static String d(Context context) {
        String f = com.alibaba.mtl.log.b.a().f();
        if (TextUtils.isEmpty(f)) {
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                if (packageInfo != null) {
                    v.put(LogField.APPVERSION.toString(), packageInfo.versionName);
                    return packageInfo.versionName;
                }
                return "Unknown";
            } catch (Throwable unused) {
                return "Unknown";
            }
        }
        return f;
    }

    public static boolean i() {
        try {
            if ((System.getProperty("java.vm.name") == null || !System.getProperty("java.vm.name").toLowerCase().contains("lemur")) && System.getProperty("ro.yunos.version") == null && TextUtils.isEmpty(q.get("ro.yunos.build.version"))) {
                return j();
            }
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    private static boolean j() {
        return (TextUtils.isEmpty(c("ro.yunos.product.chip")) && TextUtils.isEmpty(c("ro.yunos.hardware"))) ? false : true;
    }

    public static String c(String str) {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod(ISync.SYNC_CALL_METHOD_GET, String.class).invoke(cls.newInstance(), str);
        } catch (Exception unused) {
            return null;
        }
    }

    public static String t() {
        String str = q.get("ro.aliyun.clouduuid", OOBEEvent.STRING_FALSE);
        if (OOBEEvent.STRING_FALSE.equals(str)) {
            str = q.get("ro.sys.aliyun.clouduuid", OOBEEvent.STRING_FALSE);
        }
        return TextUtils.isEmpty(str) ? u() : str;
    }

    private static String u() {
        try {
            return (String) Class.forName("com.yunos.baseservice.clouduuid.CloudUUID").getMethod("getCloudUUID", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception unused) {
            return null;
        }
    }

    private static String v() {
        try {
            Field declaredField = Build.class.getDeclaredField("YUNOS_BUILD_VERSION");
            if (declaredField != null) {
                declaredField.setAccessible(true);
                return (String) declaredField.get(new String());
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }
}
