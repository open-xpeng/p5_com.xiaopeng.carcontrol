package com.alibaba.sdk.android.utils.crashdefend;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import com.xiaopeng.carcontrol.carmanager.controller.IScenarioController;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: CrashDefendUtils.java */
/* loaded from: classes.dex */
public class f {
    public static void a(Context context, b bVar, List<d> list) {
        if (context == null) {
            return;
        }
        FileOutputStream fileOutputStream = null;
        try {
            try {
                try {
                    JSONObject jSONObject = new JSONObject();
                    if (bVar != null) {
                        jSONObject.put("startSerialNumber", bVar.a);
                    }
                    synchronized (list) {
                        if (list != null) {
                            try {
                                JSONArray jSONArray = new JSONArray();
                                for (d dVar : list) {
                                    if (dVar != null) {
                                        JSONObject jSONObject2 = new JSONObject();
                                        jSONObject2.put("sdkId", dVar.f129a);
                                        jSONObject2.put("sdkVersion", dVar.f131b);
                                        jSONObject2.put("crashLimit", dVar.a);
                                        jSONObject2.put("crashCount", dVar.crashCount);
                                        jSONObject2.put("waitTime", dVar.b);
                                        jSONObject2.put("beaconStatus", dVar.c);
                                        jSONObject2.put("registerSerialNumber", dVar.f130b);
                                        jSONObject2.put("startSerialNumber", dVar.f127a);
                                        jSONObject2.put("restoreSerialNumber", dVar.f132c);
                                        jSONObject2.put("restoreCount", dVar.d);
                                        jSONArray.put(jSONObject2);
                                    }
                                }
                                jSONObject.put("sdkList", jSONArray);
                            } catch (JSONException e) {
                                Log.e("CrashUtils", "save sdk json fail:", e);
                            }
                        }
                    }
                    String jSONObject3 = jSONObject.toString();
                    FileOutputStream openFileOutput = context.openFileOutput("com_alibaba_aliyun_crash_defend_sdk_info", 0);
                    openFileOutput.write(jSONObject3.getBytes());
                    if (openFileOutput != null) {
                        openFileOutput.close();
                    }
                } catch (IOException e2) {
                    Log.e("CrashUtils", "save sdk io fail:", e2);
                    if (0 != 0) {
                        fileOutputStream.close();
                    }
                } catch (Exception e3) {
                    Log.e("CrashUtils", "save sdk exception:", e3);
                    if (0 != 0) {
                        fileOutputStream.close();
                    }
                }
            } catch (IOException e4) {
                Log.e("CrashUtils", "save sdk io fail:", e4);
            }
        } catch (Throwable th) {
            if (0 != 0) {
                try {
                    fileOutputStream.close();
                } catch (IOException e5) {
                    Log.e("CrashUtils", "save sdk io fail:", e5);
                }
            }
            throw th;
        }
    }

    /* renamed from: a  reason: collision with other method in class */
    public static boolean m61a(Context context, b bVar, List<d> list) {
        if (context == null) {
            return false;
        }
        FileInputStream fileInputStream = null;
        StringBuilder sb = new StringBuilder();
        try {
            try {
                try {
                    try {
                        try {
                            fileInputStream = context.openFileInput("com_alibaba_aliyun_crash_defend_sdk_info");
                            byte[] bArr = new byte[512];
                            while (true) {
                                int read = fileInputStream.read(bArr);
                                if (read == -1) {
                                    break;
                                }
                                sb.append(new String(bArr, 0, read));
                            }
                            if (fileInputStream != null) {
                                fileInputStream.close();
                            }
                        } catch (FileNotFoundException e) {
                            Log.e("CrashUtils", "load sdk file fail:", e);
                            if (fileInputStream != null) {
                                fileInputStream.close();
                            }
                        }
                    } catch (IOException e2) {
                        Log.e("CrashUtils", "load sdk io fail:", e2);
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                    }
                } catch (Exception e3) {
                    Log.e("CrashUtils", "load sdk exception:", e3);
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                }
            } catch (IOException e4) {
                Log.e("CrashUtils", "load sdk io fail:", e4);
            }
            if (sb.length() == 0) {
                return false;
            }
            try {
                JSONObject jSONObject = new JSONObject(sb.toString());
                bVar.a = jSONObject.optLong("startSerialNumber", 1L);
                JSONArray jSONArray = jSONObject.getJSONArray("sdkList");
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                    if (jSONObject2 != null) {
                        d dVar = new d();
                        dVar.f129a = jSONObject2.optString("sdkId", "");
                        dVar.f131b = jSONObject2.optString("sdkVersion", "");
                        dVar.a = jSONObject2.optInt("crashLimit", -1);
                        dVar.crashCount = jSONObject2.optInt("crashCount", 0);
                        dVar.b = jSONObject2.optInt("waitTime", 0);
                        dVar.c = jSONObject2.optInt("beaconStatus", 0);
                        dVar.f130b = jSONObject2.optLong("registerSerialNumber", 0L);
                        dVar.f127a = jSONObject2.optLong("startSerialNumber", 0L);
                        dVar.f132c = jSONObject2.optLong("restoreSerialNumber", 0L);
                        dVar.d = jSONObject2.optInt("restoreCount", 0);
                        if (!TextUtils.isEmpty(dVar.f129a)) {
                            list.add(dVar);
                        }
                    }
                }
                return true;
            } catch (JSONException e5) {
                Log.e("CrashUtils", "load sdk json fail:", e5);
                return true;
            } catch (Exception e6) {
                Log.e("CrashUtils", "load sdk exception:", e6);
                return true;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                try {
                    fileInputStream.close();
                } catch (IOException e7) {
                    Log.e("CrashUtils", "load sdk io fail:", e7);
                }
            }
            throw th;
        }
    }

    public static boolean a(Context context) {
        int myPid = Process.myPid();
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService(IScenarioController.SPACE_CAPSULE_SOURCE_ACTIVITY)).getRunningAppProcesses();
        if (runningAppProcesses == null) {
            return false;
        }
        String str = null;
        Iterator<ActivityManager.RunningAppProcessInfo> it = runningAppProcesses.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            ActivityManager.RunningAppProcessInfo next = it.next();
            if (next.pid == myPid) {
                str = next.processName;
                break;
            }
        }
        return context.getPackageName().equalsIgnoreCase(str);
    }
}
