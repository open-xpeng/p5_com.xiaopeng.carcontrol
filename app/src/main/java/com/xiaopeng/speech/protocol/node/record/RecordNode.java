package com.xiaopeng.speech.protocol.node.record;

import com.xiaopeng.speech.SpeechNode;
import com.xiaopeng.speech.protocol.node.record.bean.AsrResult;
import com.xiaopeng.speech.protocol.node.record.bean.RecordErrReason;
import com.xiaopeng.speech.protocol.node.record.bean.Volume;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class RecordNode extends SpeechNode<RecordListener> {
    public void onAsrResult(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        AsrResult fromJson = AsrResult.fromJson(str2);
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((RecordListener) obj).onAsrResult(fromJson);
            }
        }
    }

    public void onRecordBegin(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((RecordListener) obj).onRecordBegin();
            }
        }
    }

    public void onRecordEnd(String str, String str2) {
        boolean z;
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        try {
            z = new JSONObject(str2).optBoolean("isStopRecord");
        } catch (JSONException e) {
            e.printStackTrace();
            z = false;
        }
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((RecordListener) obj).onRecordEnd(z);
            }
        }
    }

    public void onRecordError(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        RecordErrReason fromJson = RecordErrReason.fromJson(str2);
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((RecordListener) obj).onRecordError(fromJson);
            }
        }
    }

    public void onSpeechBegin(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((RecordListener) obj).onSpeechBegin();
            }
        }
    }

    public void onSpeechEnd(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((RecordListener) obj).onSpeechEnd();
            }
        }
    }

    public void onSpeechVolume(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        Volume fromJson = Volume.fromJson(str2);
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((RecordListener) obj).onSpeechVolume(fromJson);
            }
        }
    }

    public void onRecordMaxLength(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((RecordListener) obj).onRecordMaxLength();
            }
        }
    }
}
