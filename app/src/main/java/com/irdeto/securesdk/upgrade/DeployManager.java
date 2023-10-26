package com.irdeto.securesdk.upgrade;

import android.content.Context;
import java.io.File;
import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes.dex */
public class DeployManager {
    private static final int ERROR = 1;
    private static final int SUCCESS = 0;
    private static final String bak_dir = ".irmsdk_bak";
    private static final String flag_file = "IRDETO";
    private static final String tmp_dir = ".irmsdk_tmp";

    /* JADX WARN: Removed duplicated region for block: B:34:0x0058 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void createFlagFile(android.content.Context r5) {
        /*
            r4 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.io.File r5 = r5.getFilesDir()
            java.lang.String r5 = r5.getPath()
            java.lang.String r5 = java.lang.String.valueOf(r5)
            r0.<init>(r5)
            java.lang.String r5 = "/"
            java.lang.StringBuilder r5 = r0.append(r5)
            java.lang.String r0 = "IRDETO"
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r5 = r5.toString()
            java.io.File r0 = new java.io.File
            r0.<init>(r5)
            r5 = 0
            java.io.BufferedWriter r1 = new java.io.BufferedWriter     // Catch: java.lang.Throwable -> L40 java.io.IOException -> L44
            java.io.FileWriter r2 = new java.io.FileWriter     // Catch: java.lang.Throwable -> L40 java.io.IOException -> L44
            r3 = 0
            r2.<init>(r0, r3)     // Catch: java.lang.Throwable -> L40 java.io.IOException -> L44
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L40 java.io.IOException -> L44
            java.lang.String r5 = "load success !"
            r1.write(r5)     // Catch: java.io.IOException -> L3e java.lang.Throwable -> L55
            r1.flush()     // Catch: java.io.IOException -> L3e java.lang.Throwable -> L55
            r1.close()     // Catch: java.io.IOException -> L50
            goto L54
        L3e:
            r5 = move-exception
            goto L47
        L40:
            r0 = move-exception
            r1 = r5
            r5 = r0
            goto L56
        L44:
            r0 = move-exception
            r1 = r5
            r5 = r0
        L47:
            r5.printStackTrace()     // Catch: java.lang.Throwable -> L55
            if (r1 == 0) goto L54
            r1.close()     // Catch: java.io.IOException -> L50
            goto L54
        L50:
            r5 = move-exception
            r5.printStackTrace()
        L54:
            return
        L55:
            r5 = move-exception
        L56:
            if (r1 == 0) goto L60
            r1.close()     // Catch: java.io.IOException -> L5c
            goto L60
        L5c:
            r0 = move-exception
            r0.printStackTrace()
        L60:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.irdeto.securesdk.upgrade.DeployManager.createFlagFile(android.content.Context):void");
    }

    private void deleteFlagFile(Context context) {
        File file = new File(String.valueOf(context.getFilesDir().getPath()) + MqttTopic.TOPIC_LEVEL_SEPARATOR + flag_file);
        if (file.exists()) {
            file.delete();
        }
    }

    private int init(Context context) {
        String path = context.getFilesDir().getPath();
        if (new File(String.valueOf(path) + MqttTopic.TOPIC_LEVEL_SEPARATOR + flag_file).exists()) {
            return 0;
        }
        O00000Oo.O00000Oo(path);
        File file = new File(String.valueOf(path) + MqttTopic.TOPIC_LEVEL_SEPARATOR + bak_dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        O000000o O00000o0 = O00000Oo.O00000o0(file.getPath());
        if (O00000o0 == null) {
            O00000Oo.O000000o(path, context);
            O00000Oo.O00000Oo(file.getPath());
            O00000Oo.O000000o(file.getPath(), context);
        } else {
            O00000Oo.O000000o(O00000o0, path);
            O00000Oo.O00000Oo(file.getPath());
        }
        if (irmsdk_verify(path) != 0) {
            System.exit(0);
        }
        return 0;
    }

    private int upgrade(Context context, O000000o o000000o) {
        int i;
        String path = context.getFilesDir().getPath();
        String str = String.valueOf(path) + MqttTopic.TOPIC_LEVEL_SEPARATOR + tmp_dir;
        String str2 = String.valueOf(path) + MqttTopic.TOPIC_LEVEL_SEPARATOR + bak_dir;
        String O000000o = O00000Oo.O000000o(o000000o.O000000o.getPath());
        O000000o O00000o0 = O00000Oo.O00000o0(path);
        String O000000o2 = O00000Oo.O000000o(O00000o0.O000000o.getPath());
        boolean z = false;
        int i2 = 1;
        if (O00000o0 == null || O00000Oo.O00000Oo(O000000o2, O000000o) <= 0) {
            i = 0;
            z = true;
        } else {
            i = 1;
        }
        if (!z || irmsdk_verify(str) == 0) {
            i2 = i;
        }
        O00000Oo.O00000Oo(str2);
        if (O00000o0 != null) {
            O00000Oo.O000000o(O00000o0, str2);
        }
        O00000Oo.O00000Oo(path);
        O00000Oo.O000000o(o000000o, path);
        O00000Oo.O00000Oo(str);
        return i2;
    }

    public int irmsdk_upgradeInit(Context context) {
        int i;
        boolean z = false;
        if (context == null) {
            i = 1;
        } else {
            i = 0;
            z = true;
        }
        if (z) {
            String str = "/data/data/" + context.getApplicationInfo().packageName;
            String str2 = String.valueOf(str) + "/files";
            String str3 = String.valueOf(str) + "/files_tmp";
            synchronized (this) {
                if (!new File(str3).exists()) {
                    try {
                        Runtime.getRuntime().exec("ln -s " + str2 + " " + str3).waitFor();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }
                }
            }
            O000000o O00000o0 = O00000Oo.O00000o0(String.valueOf(str2) + MqttTopic.TOPIC_LEVEL_SEPARATOR + tmp_dir);
            if (O00000o0 == null) {
                init(context);
            } else {
                upgrade(context, O00000o0);
            }
            deleteFlagFile(context);
            try {
                System.load(O00000Oo.O00000o0(str3).O000000o.getPath());
                createFlagFile(context);
            } catch (Throwable th) {
                th.printStackTrace();
                return 1;
            }
        }
        return i;
    }

    public int irmsdk_verify(String str) {
        O000000o O00000o0;
        if (str == null || (O00000o0 = O00000Oo.O00000o0(str)) == null) {
            return 1;
        }
        if (O00000Oo.O000000o(O00000o0.O000000o.getPath(), O00000o0.O00000Oo.getPath()) && O00000Oo.O000000o(O00000o0.O00000o0.getPath(), O00000o0.O00000o.getPath())) {
            return 0;
        }
        O00000Oo.O00000Oo(str);
        return 1;
    }
}
