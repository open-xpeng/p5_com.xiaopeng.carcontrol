package com.irdeto.securesdk;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Process;
import android.text.TextUtils;
import com.irdeto.securesdk.core.SSUtils;
import com.xiaopeng.carcontrol.carmanager.controller.IScenarioController;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes.dex */
public class SecureSDKManager {
    private static final String EXTERNAL_READ_PHONE_STATE = "android.permission.READ_PHONE_STATE";
    private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";
    private static final String TAG = "securesdk";
    private static Context ctx = null;
    private static int mAgentInitRes = -1;
    private static String mPackageName = null;
    private static SdkStatusListener mStatusListener = null;
    private static Object sInitLock = new Object();
    private static boolean sInitialized = false;

    /* loaded from: classes.dex */
    public interface SdkStatusListener {
        void onStatus(int i);
    }

    private static int _isfCleanup() {
        return api.isfCleanup();
    }

    private static int _isfInitialize() {
        return api.isfInitialize();
    }

    private boolean checkSSExist() {
        StringBuilder append;
        String str = Build.CPU_ABI;
        isX86();
        ctx.getFilesDir().getAbsolutePath();
        String str2 = "/data/data/" + mPackageName + "/files";
        String str3 = str2 + MqttTopic.TOPIC_LEVEL_SEPARATOR + "irss0.dat";
        String str4 = str2 + MqttTopic.TOPIC_LEVEL_SEPARATOR + "irss1.dat";
        if (!new File(str3).exists()) {
            append = new StringBuilder().append(str3);
        } else if (new File(str4).exists()) {
            return true;
        } else {
            append = new StringBuilder().append(str4);
        }
        com.irdeto.securesdk.core.O000000o.O00000o0("securesdk", append.append("not exits!!").toString());
        return false;
    }

    private void copySS(Context context, File file, String str, String str2) throws Exception {
        InputStream inputStream;
        AssetManager assets = context.getAssets();
        try {
            try {
                inputStream = assets.open(str + MqttTopic.TOPIC_LEVEL_SEPARATOR + str2);
            } catch (Exception unused) {
                inputStream = null;
            }
        } catch (Exception unused2) {
            inputStream = assets.open(str2);
        }
        if (inputStream == null) {
            return;
        }
        migrateDescriptor(inputStream, file);
    }

    private String findSpecificFileName(String str, String str2) {
        String[] list = new File("/data/data/" + mPackageName + "/files").list();
        if (list == null) {
            return null;
        }
        for (String str3 : list) {
            if (str3.startsWith(str) && str3.endsWith(str2)) {
                return str3;
            }
        }
        return null;
    }

    private static String getProcessName(Context context) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService(IScenarioController.SPACE_CAPSULE_SOURCE_ACTIVITY)).getRunningAppProcesses();
        if (runningAppProcesses == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.pid == Process.myPid() && runningAppProcessInfo.processName != null) {
                return runningAppProcessInfo.processName;
            }
        }
        return null;
    }

    private static boolean isApkDebugable(Context context) {
        try {
            return (context.getApplicationInfo().flags & 2) != 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean isX86() {
        boolean z = false;
        try {
            if (Build.VERSION.SDK_INT < 21) {
                z = ((String) Class.forName("android.os.Build").getField("CPU_ABI").get(null)).contains("x86");
            } else {
                for (String str : (String[]) Class.forName("android.os.Build").getField("SUPPORTED_ABIS").get(null)) {
                    if (str.contains("x86")) {
                        z = true;
                        break;
                    }
                }
            }
        } catch (Exception unused) {
        }
        return z;
    }

    public static byte[] isfMsgPack(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        return api.isfMsgPack(bArr);
    }

    private static boolean loadLibSo(Context context, String str) {
        String O000000o = com.irdeto.securesdk.core.O000000o.O000000o(str, "(libirmsdk_)(\\d+\\.\\d+\\.\\d+)(_.+)*(\\.so)");
        if (TextUtils.isEmpty(O000000o)) {
            return false;
        }
        File file = new File(str, O000000o);
        if (file.exists()) {
            try {
                System.load(file.getPath());
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    private void migrateDescriptor(InputStream inputStream, File file) throws Exception {
        FileOutputStream fileOutputStream = null;
        try {
            try {
                byte[] bArr = new byte[TarConstants.DEFAULT_BLKSIZE];
                FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                while (true) {
                    try {
                        int read = inputStream.read(bArr);
                        if (read == -1) {
                            break;
                        }
                        fileOutputStream2.write(bArr, 0, read);
                    } catch (Exception e) {
                        e = e;
                        throw new Exception(e);
                    } catch (Throwable th) {
                        th = th;
                        fileOutputStream = fileOutputStream2;
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        throw th;
                    }
                }
                fileOutputStream2.close();
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private static int setIndivStorePath(String str) {
        return api.isfSetIndivStorePath(str);
    }

    private static boolean verifyPermissions(Context context) {
        return context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION) == 0 && context.checkCallingOrSelfPermission(EXTERNAL_READ_PHONE_STATE) == 0;
    }

    public int cleanAllDataInUserStore(Context context) throws Exception {
        String str = Build.CPU_ABI;
        String str2 = isX86() ? "x86" : "armeabi";
        context.getFilesDir().getAbsolutePath();
        copySS(context, new File(("/data/data/" + mPackageName + "/files") + MqttTopic.TOPIC_LEVEL_SEPARATOR + "irss3.dat"), str2, "irss3.dat");
        SSUtils.O000000o();
        return 0;
    }

    public int isfCleanup() {
        sInitialized = false;
        return _isfCleanup();
    }

    public int isfInitialize(Context context, String str) throws ISFException {
        synchronized (sInitLock) {
            int i = 0;
            if (sInitialized) {
                return 0;
            }
            ctx = context;
            mPackageName = context.getPackageName();
            if (verifyPermissions(context)) {
                if (str == null || str.length() == 0) {
                    throw new ISFException(O000000o.ISF_MGR_INVALID_PARAMETER);
                }
                if (isApkDebugable(context)) {
                    try {
                        setupSecureStore(context);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (checkSSExist()) {
                        String sharedSoPath = api.getSharedSoPath(context);
                        if (TextUtils.isEmpty(sharedSoPath)) {
                            throw new ISFException(O000000o.ISF_MGR_SECURE_SDK_INITIALIZE_FAILED, "shared lib path is null");
                        }
                        if (loadLibSo(context, sharedSoPath)) {
                            setIndivStorePath(str);
                            int _isfInitialize = _isfInitialize();
                            if (_isfInitialize != 1047) {
                                if (_isfInitialize == 4353) {
                                    com.irdeto.securesdk.core.O000000o.O00000o0("zxq", "throw shared secure store invalid exception:" + _isfInitialize);
                                    throw new ISFException(O000000o.ISF_MGR_SHARED_SECURE_STORE_INVALID);
                                }
                                if (_isfInitialize == 4354 || _isfInitialize == 4355) {
                                    try {
                                        setupSecureStore(context);
                                    } catch (Exception e2) {
                                        e2.printStackTrace();
                                    }
                                }
                                sInitialized = true;
                                if (_isfInitialize == 4355) {
                                    com.irdeto.securesdk.core.O000000o.O00000o0("securesdk", "throw root exception: " + _isfInitialize);
                                    throw new ISFException(O000000o.ISF_MGR_ROOTDETECTED);
                                }
                                if (_isfInitialize != 4354) {
                                    i = _isfInitialize;
                                }
                                return i;
                            }
                            throw new ISFException(O000000o.ISF_MGR_ROOTDETECTED);
                        }
                        throw new ISFException(O000000o.ISF_MGR_SECURE_SDK_INITIALIZE_FAILED);
                    }
                    throw new ISFException(O000000o.ISF_MGR_LOCAL_SECURE_STORE_INVALID);
                }
                throw new ISFException(O000000o.ISF_MGR_GE_APP_ENV_ERROR);
            }
            throw new ISFException(O000000o.ISF_MGR_NO_PERMISSION);
        }
    }

    public void setOnSdkStatusListener(SdkStatusListener sdkStatusListener) {
        mStatusListener = sdkStatusListener;
        if (mAgentInitRes == 0) {
            sdkStatusListener.onStatus(0);
        }
    }

    public boolean setupSecureStore(Context context) throws Exception {
        String str = Build.CPU_ABI;
        String str2 = isX86() ? "x86" : "armeabi";
        context.getFilesDir().getAbsolutePath();
        String str3 = "/data/data/" + mPackageName + "/files";
        String str4 = str3 + MqttTopic.TOPIC_LEVEL_SEPARATOR + "acv_1.0.0.dat";
        if (findSpecificFileName("acv_", ".dat") == null) {
            copySS(context, new File(str4), str2, "acv_1.0.0.dat");
        }
        File file = new File(str3 + MqttTopic.TOPIC_LEVEL_SEPARATOR + "surface.png");
        if (!file.exists()) {
            copySS(context, file, str2, "surface.png");
        }
        File file2 = new File(str3 + MqttTopic.TOPIC_LEVEL_SEPARATOR + "irss0.dat");
        if (!file2.exists()) {
            copySS(context, file2, str2, "irss0.dat");
        }
        File file3 = new File(str3 + MqttTopic.TOPIC_LEVEL_SEPARATOR + "irss3.dat");
        if (!file3.exists()) {
            copySS(context, file3, str2, "irss3.dat");
        }
        File file4 = new File(str3 + MqttTopic.TOPIC_LEVEL_SEPARATOR + "irss1.dat");
        if (file4.exists()) {
            return true;
        }
        copySS(context, file4, str2, "irss1.dat");
        return true;
    }
}
