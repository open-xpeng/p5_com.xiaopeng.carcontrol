package com.xiaopeng.datalog.helper;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xiaopeng.datalog.DataLogModuleEntry;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IDataLog;
import com.xiaopeng.lib.utils.DateUtils;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.MD5Utils;
import com.xiaopeng.lib.utils.SystemPropertyUtil;
import com.xiaopeng.lib.utils.ThreadUtils;
import java.io.File;
import java.io.FilenameFilter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes2.dex */
public class DataBackupHelper {
    public static final String CAN_TAG = "candata";
    public static final String CDU_TAG = "cdudata";
    private static final String DATA_FILE_DIR = "/sdcard/Log/";
    private static final long EXPIRE_TIME_THRESHOLD = 604800000;
    private static final long MAX_FILE_LEN = 10485760;
    private static final int MAX_RETRY_COUNT = 5;
    private static final int OK_CODE = 200;
    private static final String TAG = "DataBackupHelper";
    public static final int TYPE_CAN = 1;
    public static final int TYPE_CDU = 2;
    private static volatile DataBackupHelper mInstance;

    private DataBackupHelper() {
    }

    public static DataBackupHelper getInstance() {
        if (mInstance == null) {
            synchronized (DataBackupHelper.class) {
                if (mInstance == null) {
                    mInstance = new DataBackupHelper();
                }
            }
        }
        return mInstance;
    }

    public void updateFile(final String str, final String str2, final Context context) {
        ThreadUtils.post(0, new Runnable() { // from class: com.xiaopeng.datalog.helper.DataBackupHelper.1
            @Override // java.lang.Runnable
            public void run() {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("vehicleId", String.valueOf(SystemPropertyUtil.getVehicleId()));
                jsonObject.addProperty("message", str2);
                jsonObject.addProperty("md5", MD5Utils.getMD5(str2));
                byte[] bytes = new Gson().toJson((JsonElement) jsonObject).getBytes();
                DataBackupHelper.this.initDir(str, context);
                DataBackupHelper.this.deleteExpiredFile(str, context);
                DataBackupHelper.this.writeFile(str, bytes, context);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initDir(String str, Context context) {
        File file = new File(DATA_FILE_DIR + str + MqttTopic.TOPIC_LEVEL_SEPARATOR + context.getPackageName());
        if (file.exists()) {
            return;
        }
        file.mkdirs();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteExpiredFile(final String str, Context context) {
        File file = new File(DATA_FILE_DIR + str + MqttTopic.TOPIC_LEVEL_SEPARATOR + context.getPackageName());
        if (!file.exists()) {
            file.mkdirs();
        }
        String[] list = file.list(new FilenameFilter() { // from class: com.xiaopeng.datalog.helper.DataBackupHelper.2
            @Override // java.io.FilenameFilter
            public boolean accept(File file2, String str2) {
                return str2.contains(str) && str2.endsWith(".log");
            }
        });
        if (list == null) {
            return;
        }
        Arrays.sort(list);
        ArrayList arrayList = new ArrayList();
        for (String str2 : list) {
            if (isExpiredFile(str2)) {
                arrayList.add(str2);
                LogUtils.d(TAG, "file: " + str2 + " is expired, deleted " + new File(DATA_FILE_DIR + str + MqttTopic.TOPIC_LEVEL_SEPARATOR + context.getPackageName() + MqttTopic.TOPIC_LEVEL_SEPARATOR + str2).delete());
            }
        }
        if (arrayList.size() > 0) {
            sendExpireInfo(arrayList, list);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void writeFile(String str, byte[] bArr, Context context) {
        File currentFile = getCurrentFile(str, context);
        File file = new File(generateZipFilePath(currentFile.getPath()));
        if (file.exists()) {
            LogUtils.i(TAG, "zipFile exists, delete .zip and write .log");
            file.delete();
        }
        RandomAccessFile randomAccessFile = null;
        try {
            try {
                try {
                    RandomAccessFile randomAccessFile2 = new RandomAccessFile(currentFile, "rw");
                    try {
                        randomAccessFile2.seek(randomAccessFile2.length());
                        randomAccessFile2.write(bArr);
                        randomAccessFile2.writeBytes("\n");
                        randomAccessFile2.getFD().sync();
                        LogUtils.i(TAG, "write " + currentFile + " success, data.length:" + bArr.length);
                        randomAccessFile2.close();
                    } catch (Exception e) {
                        e = e;
                        randomAccessFile = randomAccessFile2;
                        LogUtils.w(TAG, "write file fail!", e);
                        if (randomAccessFile != null) {
                            randomAccessFile.close();
                        }
                    } catch (Throwable th) {
                        th = th;
                        randomAccessFile = randomAccessFile2;
                        if (randomAccessFile != null) {
                            try {
                                randomAccessFile.close();
                            } catch (Exception unused) {
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Exception unused2) {
        }
    }

    private boolean isExpiredFile(String str) {
        int lastIndexOf = str.lastIndexOf("_");
        if (DateUtils.dateToStamp(DateUtils.formatDate10(System.currentTimeMillis()).substring(0, 8) + "_000000") - DateUtils.dateToStamp(str.substring(lastIndexOf - 8, lastIndexOf) + "_000000") > EXPIRE_TIME_THRESHOLD) {
            LogUtils.d(TAG, str + " EXPIRED!");
            return true;
        }
        return false;
    }

    private File getCurrentFile(final String str, Context context) {
        File[] listFiles = new File(DATA_FILE_DIR + str + MqttTopic.TOPIC_LEVEL_SEPARATOR + context.getPackageName()).listFiles(new FilenameFilter() { // from class: com.xiaopeng.datalog.helper.DataBackupHelper.3
            @Override // java.io.FilenameFilter
            public boolean accept(File file, String str2) {
                return str2.contains(str) && str2.contains(".log");
            }
        });
        if (listFiles == null || listFiles.length == 0) {
            LogUtils.d(TAG, "create a new File");
            return createNewFile(str, context);
        }
        Arrays.sort(listFiles);
        File file = listFiles[listFiles.length - 1];
        return (!hasTodayFile(file) || file.length() >= 10485760) ? createNewFile(str, context) : file;
    }

    private boolean hasTodayFile(File file) {
        String name = file.getName();
        String formatDate10 = DateUtils.formatDate10(System.currentTimeMillis());
        return name.contains(formatDate10.substring(0, formatDate10.indexOf("_")));
    }

    private File createNewFile(String str, Context context) {
        return new File(DATA_FILE_DIR + str + MqttTopic.TOPIC_LEVEL_SEPARATOR + context.getPackageName() + MqttTopic.TOPIC_LEVEL_SEPARATOR + str + "_" + DateUtils.formatDate10(System.currentTimeMillis()) + ".log");
    }

    private String generateZipFilePath(String str) {
        return str.replace(".log", ".zip");
    }

    private void sendExpireInfo(List<String> list, String[] strArr) {
        String timeFromFile;
        int size = list.size();
        String timeFromFile2 = getTimeFromFile(list.get(0));
        if (strArr.length > size) {
            timeFromFile = getTimeFromFile(strArr[size]);
        } else {
            timeFromFile = getTimeFromFile(list.get(size - 1));
        }
        IDataLog iDataLog = (IDataLog) Module.get(DataLogModuleEntry.class).get(IDataLog.class);
        iDataLog.sendStatData(iDataLog.buildStat().setEventName("data_expire").setProperty("startTime", timeFromFile2).setProperty("endTime", timeFromFile).build());
    }

    private String getTimeFromFile(String str) {
        return str.substring(str.lastIndexOf("_") - 8, str.lastIndexOf("."));
    }
}
