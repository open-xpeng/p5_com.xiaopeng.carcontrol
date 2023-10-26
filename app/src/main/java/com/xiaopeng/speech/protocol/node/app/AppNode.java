package com.xiaopeng.speech.protocol.node.app;

import android.text.TextUtils;
import com.xiaopeng.speech.SpeechNode;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class AppNode extends SpeechNode<AppListener> {
    /* JADX INFO: Access modifiers changed from: protected */
    public void onQuery(String str, String str2) {
        String str3;
        try {
            str3 = new JSONObject(str2).optString("appName");
        } catch (JSONException e) {
            e.printStackTrace();
            str3 = "";
        }
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((AppListener) obj).onQuery(str, str3);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onAppOpen(String str, String str2) {
        String str3;
        try {
            str3 = new JSONObject(str2).optString("appName");
        } catch (JSONException e) {
            e.printStackTrace();
            str3 = "";
        }
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((AppListener) obj).onAppOpen(str, str3);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Removed duplicated region for block: B:19:0x003b A[LOOP:0: B:19:0x003b->B:21:0x003e, LOOP_START, PHI: r1 
      PHI: (r1v1 int) = (r1v0 int), (r1v2 int) binds: [B:18:0x0039, B:21:0x003e] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x004c A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onAppPageOpen(java.lang.String r5, java.lang.String r6) {
        /*
            r4 = this;
            java.lang.String r5 = "pageUrl"
            com.xiaopeng.speech.common.util.SimpleCallbackList<T> r0 = r4.mListenerList
            java.lang.Object[] r0 = r0.collectCallbacks()
            r1 = 0
            r2 = 0
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch: java.io.UnsupportedEncodingException -> L2f org.json.JSONException -> L34
            r3.<init>(r6)     // Catch: java.io.UnsupportedEncodingException -> L2f org.json.JSONException -> L34
            java.lang.String r6 = r3.optString(r5)     // Catch: java.io.UnsupportedEncodingException -> L29 org.json.JSONException -> L2c
            java.lang.String r2 = "isDui"
            boolean r2 = r3.optBoolean(r2, r1)     // Catch: java.io.UnsupportedEncodingException -> L29 org.json.JSONException -> L2c
            if (r2 == 0) goto L25
            java.lang.String r6 = r4.replaceAllstr(r6)     // Catch: java.io.UnsupportedEncodingException -> L29 org.json.JSONException -> L2c
            java.lang.String r2 = "utf-8"
            java.lang.String r6 = java.net.URLDecoder.decode(r6, r2)     // Catch: java.io.UnsupportedEncodingException -> L29 org.json.JSONException -> L2c
        L25:
            r3.putOpt(r5, r6)     // Catch: java.io.UnsupportedEncodingException -> L29 org.json.JSONException -> L2c
            goto L39
        L29:
            r5 = move-exception
            r2 = r3
            goto L30
        L2c:
            r5 = move-exception
            r2 = r3
            goto L35
        L2f:
            r5 = move-exception
        L30:
            r5.printStackTrace()
            goto L38
        L34:
            r5 = move-exception
        L35:
            r5.printStackTrace()
        L38:
            r3 = r2
        L39:
            if (r0 == 0) goto L4c
        L3b:
            int r5 = r0.length
            if (r1 >= r5) goto L4c
            r5 = r0[r1]
            com.xiaopeng.speech.protocol.node.app.AppListener r5 = (com.xiaopeng.speech.protocol.node.app.AppListener) r5
            java.lang.String r6 = r3.toString()
            r5.onAppPageOpen(r6)
            int r1 = r1 + 1
            goto L3b
        L4c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.speech.protocol.node.app.AppNode.onAppPageOpen(java.lang.String, java.lang.String):void");
    }

    private String replaceAllstr(String str) {
        if (TextUtils.isEmpty(str) || !str.contains("$")) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < str.length()) {
            int i2 = i + 1;
            String substring = str.substring(i, i2);
            if (substring.equals("$")) {
                sb.append("&");
            } else {
                sb.append(substring);
            }
            i = i2;
        }
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onKeyeventBack(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((AppListener) obj).onKeyeventBack();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onAppStoreOpen(String str, String str2) {
        String str3;
        try {
            str3 = new JSONObject(str2).optString("appName");
        } catch (JSONException e) {
            e.printStackTrace();
            str3 = "";
        }
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((AppListener) obj).onAppStoreOpen(str, str3);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0045 A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onTriggerIntent(java.lang.String r6, java.lang.String r7) {
        /*
            r5 = this;
            java.lang.String r6 = ""
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch: org.json.JSONException -> L28
            r0.<init>(r7)     // Catch: org.json.JSONException -> L28
            java.lang.String r7 = "skill"
            java.lang.String r7 = r0.optString(r7)     // Catch: org.json.JSONException -> L28
            java.lang.String r1 = "task"
            java.lang.String r1 = r0.optString(r1)     // Catch: org.json.JSONException -> L25
            java.lang.String r2 = "intent"
            java.lang.String r2 = r0.optString(r2)     // Catch: org.json.JSONException -> L22
            java.lang.String r3 = "slots"
            java.lang.String r6 = r0.optString(r3)     // Catch: org.json.JSONException -> L20
            goto L2f
        L20:
            r0 = move-exception
            goto L2c
        L22:
            r0 = move-exception
            r2 = r6
            goto L2c
        L25:
            r0 = move-exception
            r1 = r6
            goto L2b
        L28:
            r0 = move-exception
            r7 = r6
            r1 = r7
        L2b:
            r2 = r1
        L2c:
            r0.printStackTrace()
        L2f:
            com.xiaopeng.speech.common.util.SimpleCallbackList<T> r0 = r5.mListenerList
            java.lang.Object[] r0 = r0.collectCallbacks()
            if (r0 == 0) goto L45
            r3 = 0
        L38:
            int r4 = r0.length
            if (r3 >= r4) goto L45
            r4 = r0[r3]
            com.xiaopeng.speech.protocol.node.app.AppListener r4 = (com.xiaopeng.speech.protocol.node.app.AppListener) r4
            r4.onTriggerIntent(r7, r1, r2, r6)
            int r3 = r3 + 1
            goto L38
        L45:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.speech.protocol.node.app.AppNode.onTriggerIntent(java.lang.String, java.lang.String):void");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onDebugOpen(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((AppListener) obj).onDebugOpen();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onAppActive(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((AppListener) obj).onAppActive();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onAiHomepageOpen(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((AppListener) obj).onAiHomepageOpen();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onAiHomepageClose(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((AppListener) obj).onAiHomepageClose();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onAppLauncherExit(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((AppListener) obj).onAppLauncherExit();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Removed duplicated region for block: B:15:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x003b A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onStartPage(java.lang.String r5, java.lang.String r6) {
        /*
            r4 = this;
            java.lang.String r5 = ""
            com.xiaopeng.speech.common.util.SimpleCallbackList<T> r0 = r4.mListenerList
            java.lang.Object[] r0 = r0.collectCallbacks()
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch: org.json.JSONException -> L25
            r1.<init>(r6)     // Catch: org.json.JSONException -> L25
            java.lang.String r6 = "packageName"
            java.lang.String r6 = r1.optString(r6)     // Catch: org.json.JSONException -> L25
            java.lang.String r2 = "action"
            java.lang.String r2 = r1.optString(r2)     // Catch: org.json.JSONException -> L22
            java.lang.String r3 = "extraData"
            java.lang.String r5 = r1.optString(r3)     // Catch: org.json.JSONException -> L20
            goto L2b
        L20:
            r1 = move-exception
            goto L28
        L22:
            r1 = move-exception
            r2 = r5
            goto L28
        L25:
            r1 = move-exception
            r6 = r5
            r2 = r6
        L28:
            r1.printStackTrace()
        L2b:
            if (r0 == 0) goto L3b
            r1 = 0
        L2e:
            int r3 = r0.length
            if (r1 >= r3) goto L3b
            r3 = r0[r1]
            com.xiaopeng.speech.protocol.node.app.AppListener r3 = (com.xiaopeng.speech.protocol.node.app.AppListener) r3
            r3.onStartPage(r6, r2, r5)
            int r1 = r1 + 1
            goto L2e
        L3b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.speech.protocol.node.app.AppNode.onStartPage(java.lang.String, java.lang.String):void");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onGuiSpeechDebugPage(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((AppListener) obj).onGuiSpeechDebugPage();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onOpenYoukuSearch(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((AppListener) obj).onOpenYoukuSearch(str2);
            }
        }
    }
}
