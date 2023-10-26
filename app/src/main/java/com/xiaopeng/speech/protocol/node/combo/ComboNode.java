package com.xiaopeng.speech.protocol.node.combo;

import android.text.TextUtils;
import com.xiaopeng.speech.SpeechClient;
import com.xiaopeng.speech.SpeechNode;
import com.xiaopeng.speech.protocol.event.ComboEvent;
import com.xiaopeng.speech.protocol.event.OOBEEvent;
import com.xiaopeng.speech.protocol.event.query.speech.SpeechCarStatusEvent;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class ComboNode extends SpeechNode<ComboListener> {
    private static final String CLOSE_MEDITATION_INTENT = "关闭冥想模式";
    private static final String CLOSE_WAIT_INTENT = "关闭等人模式";
    private static final String COMBO_SKILL = "离线系统组合命令词";
    private static final String COMBO_TASK = "组合命令词";
    private static final String OPEN_MEDITATION_INTENT = "冥想模式";
    private static final String OPEN_WAIT_INTENT = "等人模式";

    /* JADX INFO: Access modifiers changed from: protected */
    public void onModeBio(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ComboListener) obj).onModeBio();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onModeVentilate(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ComboListener) obj).onModeVentilate();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onModeInvisible(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ComboListener) obj).onModeInvisible();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onModeInvisibleOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ComboListener) obj).onModeInvisibleOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onModeFridge(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ComboListener) obj).onModeFridge();
            }
        }
    }

    public void onDataModeInvisibleTts(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ComboListener) obj).onDataModeInvisibleTts();
            }
        }
    }

    public void onDataModeMeditationTts(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ComboListener) obj).onDataModeMeditationTts();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onFastCloseModeInvisible(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ComboListener) obj).onFastCloseModeInvisible();
            }
        }
    }

    public void onDataModeBioTts(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ComboListener) obj).onDataModeBioTts();
            }
        }
    }

    public void onDataModeVentilateTts(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ComboListener) obj).onDataModeVentilateTts();
            }
        }
    }

    public void onDataModeFridgeTts(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ComboListener) obj).onDataModeFridgeTts();
            }
        }
    }

    public void onModeBioOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ComboListener) obj).onModeBioOff();
            }
        }
    }

    public void onModeVentilateOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ComboListener) obj).onModeVentilateOff();
            }
        }
    }

    public void onModeFridgeOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ComboListener) obj).onModeFridgeOff();
            }
        }
    }

    public void onDataModeWaitTts(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ComboListener) obj).onDataModeWaitTts();
            }
        }
    }

    public void onModeWait(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ComboListener) obj).onModeWait();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onModeWaitOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ComboListener) obj).onModeWaitOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void enterUserMode(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ComboListener) obj).enterUserMode(getModeFromJson(str2));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void exitUserModel(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ComboListener) obj).exitUserModel(getModeFromJson(str2));
            }
        }
    }

    private String getModeFromJson(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            return jSONObject.has("mode") ? jSONObject.optString("mode") : "";
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    public void openMeditationMode(String str) {
        String str2;
        SpeechClient.instance().getWakeupEngine().stopDialog();
        try {
            str2 = new JSONObject().put("tts", str).put("isAsrModeOffline", true).toString();
        } catch (JSONException e) {
            e.printStackTrace();
            str2 = "";
        }
        SpeechClient.instance().getAgent().triggerIntent(COMBO_SKILL, COMBO_TASK, OPEN_MEDITATION_INTENT, str2);
    }

    public void closeMeditationMode(String str) {
        closeMeditationMode(str, false);
    }

    public void closeMeditationMode(String str, boolean z) {
        String str2;
        SpeechClient.instance().getWakeupEngine().stopDialog();
        try {
            str2 = new JSONObject().put("tts", str).put("isAsrModeOffline", true).put("needTTS", z ? OOBEEvent.STRING_TRUE : OOBEEvent.STRING_FALSE).toString();
        } catch (JSONException e) {
            e.printStackTrace();
            str2 = "";
        }
        SpeechClient.instance().getAgent().triggerIntent(COMBO_SKILL, COMBO_TASK, CLOSE_MEDITATION_INTENT, str2);
    }

    public void openWaitMode(boolean z) {
        String str;
        try {
            str = new JSONObject().put("isAsrModeOffline", true).put("needTTS", z ? OOBEEvent.STRING_TRUE : OOBEEvent.STRING_FALSE).toString();
        } catch (JSONException e) {
            e.printStackTrace();
            str = "";
        }
        SpeechClient.instance().getAgent().triggerIntent(COMBO_SKILL, COMBO_TASK, OPEN_WAIT_INTENT, str);
    }

    public void closeWaitMode(boolean z) {
        SpeechClient.instance().getAgent().sendEvent(ComboEvent.MODE_WAIT_OFF, "");
    }

    public int getCurrentMode() {
        return SpeechClient.instance().getQueryInjector().queryData(SpeechCarStatusEvent.STATUS_CUR_MODE, "").getInteger().intValue();
    }
}
