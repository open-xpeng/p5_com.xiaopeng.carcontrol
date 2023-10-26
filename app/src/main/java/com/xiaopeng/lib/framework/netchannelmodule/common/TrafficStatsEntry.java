package com.xiaopeng.lib.framework.netchannelmodule.common;

import android.content.Context;
import android.net.TrafficStats;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import com.lzy.okgo.model.Progress;
import com.xiaopeng.libconfig.ipc.IpcConfig;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class TrafficStatsEntry {
    public static final int FIRST_NETWORK_UID = 100000;
    public static final int LAST_NETWORK_UID = 110000;
    public static final String TAG = "TrafficStatsEntry";
    public static final int TAG_APP_AUTOPILOT = -16449537;
    public static final int TAG_APP_AUTO_SHOW = -14483457;
    public static final int TAG_APP_AVATAR_SERVICE = -15663105;
    public static final int TAG_APP_BLUETOOTH_PHONE = -16121857;
    public static final int TAG_APP_BUG_HUNTER = -14548993;
    public static final int TAG_APP_CAR_ACCOUNT = -15335425;
    public static final int TAG_APP_CAR_CAMERA = -15532033;
    public static final int TAG_APP_CAR_CONTROL = -15269889;
    public static final int TAG_APP_CAR_DIAGNOSIS = -14352385;
    public static final int TAG_APP_CAR_GALLERY = -15466497;
    public static final int TAG_APP_CAR_REMOTE_CONTROL = -15073281;
    public static final int TAG_APP_CAR_SERVICE = -15400961;
    public static final int TAG_APP_CAR_SETTINGS = -15204353;
    public static final int TAG_APP_CAR_SPEECH_SERVICE = -15138817;
    public static final int TAG_APP_DATA_COLLECTOR = -16252929;
    public static final int TAG_APP_DATA_UPLOADER = -16187393;
    public static final int TAG_APP_DEVICE_COMMUNICATION = -15597569;
    public static final int TAG_APP_DEV_TOOLS = -16384001;
    public static final int TAG_APP_ENGINE = -16515073;
    public static final int TAG_APP_FACTORY = -14417921;
    public static final int TAG_APP_IPC = -14614529;
    public static final int TAG_APP_NETWORK_MONITOR = -14286849;
    public static final int TAG_APP_OOBE = -16580609;
    public static final int TAG_APP_OTA = -16646145;
    public static final int TAG_APP_PSO_SERVICE = -14221313;
    public static final int TAG_APP_XUI_SERVICE = -16318465;
    private static final HashMap<String, Entry> sEntry;

    static {
        HashMap<String, Entry> hashMap = new HashMap<>();
        sEntry = hashMap;
        hashMap.clear();
        hashMap.put(IpcConfig.App.CAR_OTA, new Entry(100000, TAG_APP_OTA));
        hashMap.put("com.xiaopeng.oobe", new Entry(100001, TAG_APP_OOBE));
        hashMap.put(IpcConfig.App.APP_ENGINE, new Entry(100002, TAG_APP_ENGINE));
        hashMap.put(IpcConfig.App.CAR_AUTOPILOT, new Entry(100003, TAG_APP_AUTOPILOT));
        hashMap.put(IpcConfig.App.CAR_DEVTOOLS, new Entry(IpcConfig.CarInfoUploadConfig.TYPE_IMMEDIATELY_GET_CARINFO_DATA_REQUEST, TAG_APP_DEV_TOOLS));
        hashMap.put(IpcConfig.App.APP_XUI_SERVICE, new Entry(100005, TAG_APP_XUI_SERVICE));
        hashMap.put(IpcConfig.App.DATA_COLLECTOR, new Entry(100006, TAG_APP_DATA_COLLECTOR));
        hashMap.put("com.xiaopeng.data.uploader", new Entry(100007, TAG_APP_DATA_UPLOADER));
        hashMap.put(IpcConfig.App.CAR_BT_PHONE, new Entry(100008, TAG_APP_BLUETOOTH_PHONE));
        hashMap.put("com.xiaopeng.aiavatarservice", new Entry(100009, TAG_APP_AVATAR_SERVICE));
        hashMap.put(IpcConfig.App.DEVICE_COMMUNICATION, new Entry(100010, TAG_APP_DEVICE_COMMUNICATION));
        hashMap.put(IpcConfig.App.CAR_CAMERA, new Entry(IpcConfig.XUIConfig.MSG_ID, TAG_APP_CAR_CAMERA));
        hashMap.put(IpcConfig.App.CAR_GALLERY, new Entry(100012, TAG_APP_CAR_GALLERY));
        hashMap.put("com.android.car", new Entry(100013, TAG_APP_CAR_SERVICE));
        hashMap.put("com.xiaopeng.caraccount", new Entry(100014, TAG_APP_CAR_ACCOUNT));
        hashMap.put("com.xiaopeng.carcontrol", new Entry(100015, TAG_APP_CAR_CONTROL));
        hashMap.put("com.xiaopeng.car.settings", new Entry(100016, TAG_APP_CAR_SETTINGS));
        hashMap.put("com.xiaopeng.xpspeechservice", new Entry(100017, TAG_APP_CAR_SPEECH_SERVICE));
        hashMap.put("com.xpeng.xpcarremotecontrol", new Entry(100018, TAG_APP_CAR_REMOTE_CONTROL));
        hashMap.put("com.xiaopeng.ipc", new Entry(100019, TAG_APP_IPC));
        hashMap.put(IpcConfig.App.APP_BUGHUNTER, new Entry(100020, TAG_APP_BUG_HUNTER));
        hashMap.put(IpcConfig.App.AUTO_SHOW, new Entry(100021, TAG_APP_AUTO_SHOW));
        hashMap.put("com.xiaopeng.factory", new Entry(100022, TAG_APP_FACTORY));
        hashMap.put(IpcConfig.App.APP_CAR_DIAGNOSIS, new Entry(100023, TAG_APP_CAR_DIAGNOSIS));
        hashMap.put("com.xiaopeng.networkmonitor", new Entry(100024, TAG_APP_NETWORK_MONITOR));
        hashMap.put("android.E28psoService", new Entry(100025, TAG_APP_PSO_SERVICE));
    }

    public static int getTag(String str) {
        Entry entry = getEntry(str);
        if (entry != null) {
            return entry.tag;
        }
        return -1;
    }

    public static int getUid(String str) {
        Entry entry = getEntry(str);
        if (entry != null) {
            return entry.uid;
        }
        return -1;
    }

    public static String getPackageName(int i, int i2) {
        HashMap<String, Entry> hashMap = sEntry;
        if (hashMap != null) {
            for (String str : hashMap.keySet()) {
                Entry entry = sEntry.get(str);
                if (entry != null && (entry.tag == i || entry.uid == i2)) {
                    return str;
                }
            }
            return null;
        }
        return null;
    }

    public static boolean isEntryTag(int i) {
        HashMap<String, Entry> hashMap = sEntry;
        if (hashMap != null) {
            for (String str : hashMap.keySet()) {
                Entry entry = sEntry.get(str);
                if (entry != null && entry.tag == i) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public static boolean isEntryUid(int i) {
        HashMap<String, Entry> hashMap = sEntry;
        if (hashMap != null) {
            for (String str : hashMap.keySet()) {
                Entry entry = sEntry.get(str);
                if (entry != null && entry.uid == i) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    private static Entry getEntry(String str) {
        HashMap<String, Entry> hashMap;
        if (TextUtils.isEmpty(str) || (hashMap = sEntry) == null || !hashMap.containsKey(str)) {
            return null;
        }
        return hashMap.get(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class Entry {
        public int tag;
        public int uid;

        public Entry(int i, int i2) {
            this.uid = i;
            this.tag = i2;
        }
    }

    private static void set(int i, int i2) {
        TrafficStats.getAndSetThreadStatsTag(i2);
        TrafficStats.setThreadStatsUid(i);
    }

    public static List<HashMap<String, Object>> getTrafficInfo() {
        ArrayList arrayList = new ArrayList();
        try {
            File file = new File("/system/etc/xp_traffic_stats_maps.json");
            if (file.exists()) {
                StringBuilder sb = new StringBuilder();
                InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file));
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    sb.append(readLine);
                }
                bufferedReader.close();
                inputStreamReader.close();
                JSONArray jSONArray = new JSONObject(sb.toString()).getJSONArray("data");
                if (jSONArray != null) {
                    int length = jSONArray.length();
                    for (int i = 0; i < length; i++) {
                        JSONObject jSONObject = jSONArray.getJSONObject(i);
                        HashMap hashMap = new HashMap();
                        if (jSONObject.has(VuiConstants.SCENE_PACKAGE_NAME)) {
                            hashMap.put(VuiConstants.SCENE_PACKAGE_NAME, jSONObject.getString(VuiConstants.SCENE_PACKAGE_NAME));
                        }
                        if (jSONObject.has("uid")) {
                            hashMap.put("uid", Integer.valueOf(jSONObject.getInt("uid")));
                        }
                        if (jSONObject.has(Progress.TAG)) {
                            hashMap.put(Progress.TAG, jSONObject.getString(Progress.TAG));
                        }
                        arrayList.add(hashMap);
                    }
                }
            }
        } catch (Exception unused) {
        }
        return arrayList;
    }

    public static void setTraficInfo() {
        Context applicationContext;
        if (Build.VERSION.SDK_INT >= 26 && (applicationContext = ContextNetStatusProvider.getApplicationContext()) != null) {
            String packageName = applicationContext.getPackageName();
            int tag = getTag(packageName);
            int uid = getUid(packageName);
            Log.i(TAG, "setTraficInfo:\t" + packageName + "\ttag:" + tag + "\tuid:" + uid);
            set(uid, tag);
        }
    }
}
