package com.xiaopeng.speech.protocol.node.social;

import com.xiaopeng.speech.SpeechClient;
import com.xiaopeng.speech.SpeechNode;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class SocialNode extends SpeechNode<SocialListener> {
    public static final String GROUP_MESSAGE_INTENT = "播放群内容";
    public static final String JOIN_GROUP_INTENT = "加入鹏窝";
    private static final String LBS_SOCAIL_TASK = "LBS社交";
    private static final String OFFLINE_SKILL = "命令词";
    public static final String QUERY_SEND_MESSAGE = "发送消息";
    public static final String QUERY_SET_VOICE_BUTTON = "设置方向盘按钮";

    public void onSocialMotorcadeOpen(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SocialListener) obj).onSocialMotorcadeOpen();
            }
        }
    }

    public void onSocialMotorcadeClose(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SocialListener) obj).onSocialMotorcadeClose();
            }
        }
    }

    public void onSocialGrabMic(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SocialListener) obj).onSocialGrabMic();
            }
        }
    }

    public void onSocialGrabMicCancel(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SocialListener) obj).onSocialGrabMicCancel();
            }
        }
    }

    public void onSocialCreateTopic(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SocialListener) obj).onSocialCreateTopic();
            }
        }
    }

    public void onSocialReplyTopic(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SocialListener) obj).onSocialReplyTopic();
            }
        }
    }

    public void onSocialQuitChat(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SocialListener) obj).onSocialQuitChat();
            }
        }
    }

    public void onSocialConfirm(String str, String str2) {
        String str3;
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        try {
            str3 = new JSONObject(str2).optString("intent");
        } catch (JSONException e) {
            e.printStackTrace();
            str3 = "";
        }
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SocialListener) obj).onSocialConfirm(str3);
            }
        }
    }

    public void onSocialCancel(String str, String str2) {
        String str3;
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        try {
            str3 = new JSONObject(str2).optString("intent");
        } catch (JSONException e) {
            e.printStackTrace();
            str3 = "";
        }
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SocialListener) obj).onSocialCancel(str3);
            }
        }
    }

    public void onVoiceButtonClick(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SocialListener) obj).onVoiceButtonClick();
            }
        }
    }

    public void onBackButtonClick(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SocialListener) obj).onBackButtonClick();
            }
        }
    }

    public void broadcastGroupMessage(String str) {
        String str2;
        try {
            str2 = new JSONObject().put("tts", str).put("intent", GROUP_MESSAGE_INTENT).toString();
        } catch (JSONException e) {
            e.printStackTrace();
            str2 = "";
        }
        SpeechClient.instance().getAgent().triggerIntent(OFFLINE_SKILL, LBS_SOCAIL_TASK, GROUP_MESSAGE_INTENT, str2);
    }

    public void joinGroup(String str) {
        String str2;
        try {
            str2 = new JSONObject().put("tts", str).put("intent", JOIN_GROUP_INTENT).toString();
        } catch (JSONException e) {
            e.printStackTrace();
            str2 = "";
        }
        SpeechClient.instance().getAgent().triggerIntent(OFFLINE_SKILL, LBS_SOCAIL_TASK, JOIN_GROUP_INTENT, str2);
    }

    public void querySendMessage(String str) {
        String str2;
        try {
            str2 = new JSONObject().put("tts", str).put("intent", QUERY_SEND_MESSAGE).toString();
        } catch (JSONException e) {
            e.printStackTrace();
            str2 = "";
        }
        SpeechClient.instance().getAgent().triggerIntent(OFFLINE_SKILL, LBS_SOCAIL_TASK, QUERY_SEND_MESSAGE, str2);
    }

    public void querySetVoiceButton(String str) {
        String str2;
        try {
            str2 = new JSONObject().put("tts", str).put("intent", QUERY_SET_VOICE_BUTTON).toString();
        } catch (JSONException e) {
            e.printStackTrace();
            str2 = "";
        }
        SpeechClient.instance().getAgent().triggerIntent(OFFLINE_SKILL, LBS_SOCAIL_TASK, QUERY_SET_VOICE_BUTTON, str2);
    }

    public void stopDialog() {
        SpeechClient.instance().getWakeupEngine().stopDialog();
    }
}
