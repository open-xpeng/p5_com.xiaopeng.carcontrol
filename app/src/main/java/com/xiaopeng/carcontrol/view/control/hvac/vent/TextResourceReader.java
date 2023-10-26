package com.xiaopeng.carcontrol.view.control.hvac.vent;

import android.content.Context;
import com.xiaopeng.carcontrol.util.LogUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/* loaded from: classes2.dex */
public class TextResourceReader {
    private static final String TAG = "TextResourceReader";

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Not initialized variable reg: 3, insn: 0x0076: MOVE  (r2 I:??[OBJECT, ARRAY]) = (r3 I:??[OBJECT, ARRAY]), block:B:42:0x0076 */
    /* JADX WARN: Type inference failed for: r5v0, types: [android.content.Context] */
    /* JADX WARN: Type inference failed for: r5v10, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r5v12, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r5v4 */
    /* JADX WARN: Type inference failed for: r5v6 */
    /* JADX WARN: Type inference failed for: r5v7, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r6v0, types: [int] */
    /* JADX WARN: Type inference failed for: r6v1 */
    /* JADX WARN: Type inference failed for: r6v2 */
    /* JADX WARN: Type inference failed for: r6v3, types: [java.io.InputStreamReader] */
    /* JADX WARN: Type inference failed for: r6v4, types: [java.io.InputStreamReader] */
    /* JADX WARN: Type inference failed for: r6v5 */
    /* JADX WARN: Type inference failed for: r6v7 */
    /* JADX WARN: Type inference failed for: r6v8, types: [java.io.Reader, java.io.InputStreamReader] */
    public static String readTextFromFile(Context context, int resourceId) {
        BufferedReader bufferedReader;
        Exception e;
        BufferedReader bufferedReader2;
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader3 = null;
        try {
        } catch (IOException e2) {
            LogUtils.e(TAG, e2.toString());
        }
        try {
            try {
                context = context.getResources().openRawResource(resourceId);
                try {
                    resourceId = new InputStreamReader(context);
                } catch (Exception e3) {
                    bufferedReader = null;
                    e = e3;
                    resourceId = 0;
                } catch (Throwable th) {
                    th = th;
                    resourceId = 0;
                }
                try {
                    bufferedReader = new BufferedReader(resourceId);
                    while (true) {
                        try {
                            String readLine = bufferedReader.readLine();
                            if (readLine == null) {
                                break;
                            }
                            sb.append(readLine);
                            sb.append("\n");
                        } catch (Exception e4) {
                            e = e4;
                            LogUtils.e(TAG, e.toString());
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            if (resourceId != 0) {
                                resourceId.close();
                            }
                            if (context != 0) {
                                context.close();
                            }
                            return sb.toString();
                        }
                    }
                    bufferedReader.close();
                    resourceId.close();
                    if (context != 0) {
                        context.close();
                    }
                } catch (Exception e5) {
                    bufferedReader = null;
                    e = e5;
                } catch (Throwable th2) {
                    th = th2;
                    if (bufferedReader3 != null) {
                        try {
                            bufferedReader3.close();
                        } catch (IOException e6) {
                            LogUtils.e(TAG, e6.toString());
                            throw th;
                        }
                    }
                    if (resourceId != 0) {
                        resourceId.close();
                    }
                    if (context != 0) {
                        context.close();
                    }
                    throw th;
                }
            } catch (Exception e7) {
                resourceId = 0;
                bufferedReader = null;
                e = e7;
                context = 0;
            } catch (Throwable th3) {
                th = th3;
                context = 0;
                resourceId = 0;
            }
            return sb.toString();
        } catch (Throwable th4) {
            th = th4;
            bufferedReader3 = bufferedReader2;
        }
    }
}
