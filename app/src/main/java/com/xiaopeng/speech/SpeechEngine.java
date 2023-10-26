package com.xiaopeng.speech;

import android.content.Context;
import com.xiaopeng.speech.overall.OverallManager;
import com.xiaopeng.speech.overall.listener.IXpRecordListener;

/* loaded from: classes2.dex */
public class SpeechEngine {
    public static void subscribeOverall(Context context, String str, String[] strArr, String[] strArr2) {
        if (strArr == null && strArr2 == null) {
            throw new IllegalArgumentException("no events and querys");
        }
        String[] strArr3 = new String[(strArr == null ? 0 : strArr.length) + (strArr2 == null ? 0 : strArr2.length)];
        if (strArr != null) {
            System.arraycopy(strArr, 0, strArr3, 0, strArr.length);
        }
        if (strArr2 != null) {
            System.arraycopy(strArr2, 0, strArr3, strArr == null ? 0 : strArr.length, strArr2.length);
        }
        OverallManager.instance().subscribe(context, str, strArr3);
    }

    public static void unsubscribeOverall(String str) {
        OverallManager.instance().unsubscribe(str);
    }

    public static void triggerIntent(String str, String str2, String str3, String str4) {
        OverallManager.instance().triggerIntent(str, str2, str3, str4);
    }

    public void triggerEvent(String str, String str2) {
        OverallManager.instance().triggerEvent(str, str2);
    }

    public void stopDialog() {
        OverallManager.instance().stopDialog();
    }

    public void sendEvent(String str, String str2) {
        OverallManager.instance().sendEvent(str, str2);
    }

    public void startDialogFrom(String str) {
        OverallManager.instance().startDialogFrom(str);
    }

    public void feedbackResult(String str, String str2) {
        OverallManager.instance().feedbackResult(str, str2);
    }

    public void replySupport(String str, boolean z, String str2) {
        OverallManager.instance().replySupport(str, z, str2);
    }

    public void replySupport(String str, boolean z) {
        replySupport(str, z, "");
    }

    public static void speak(String str) {
        OverallManager.instance().speak(str);
    }

    public static void initRecord(Context context, String str, IXpRecordListener iXpRecordListener) {
        OverallManager.instance().initRecord(context, str, iXpRecordListener);
    }

    public static void initRecord(Context context, IXpRecordListener iXpRecordListener) {
        OverallManager.instance().initRecord(context, null, iXpRecordListener);
    }

    public static void startRecord(String str) {
        OverallManager.instance().startRecord(str);
    }

    public static void starRecord() {
        OverallManager.instance().startRecord(null);
    }

    public static void stopRecord() {
        OverallManager.instance().stopRecord();
    }

    public static void destroyRecord(IXpRecordListener iXpRecordListener) {
        OverallManager.instance().destroyRecord(iXpRecordListener);
    }

    public static boolean isSupportRecord() {
        return OverallManager.instance().isSupportRecord();
    }
}
