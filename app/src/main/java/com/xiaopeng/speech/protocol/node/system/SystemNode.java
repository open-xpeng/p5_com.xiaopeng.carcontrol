package com.xiaopeng.speech.protocol.node.system;

import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.speech.SpeechNode;
import com.xiaopeng.speech.protocol.bean.AdjustValue;
import com.xiaopeng.speech.protocol.bean.VolumeValue;
import com.xiaopeng.speech.protocol.bean.stats.SceneSwitchStatisticsBean;
import com.xiaopeng.speech.protocol.utils.StringUtil;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class SystemNode extends SpeechNode<SystemListener> {
    public static final String NAVI_VOICE_DATA = "{\"stream_type\":9}";
    private static final String STREAM_MUSIC = "3";

    /* JADX INFO: Access modifiers changed from: protected */
    public void onScreenBrightnessUp(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onScreenBrightnessUp();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onScreenBrightnessMax(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onScreenBrightnessMax();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onScreenBrightnessDown(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onScreenBrightnessDown();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onScreenBrightnessMin(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onScreenBrightnessMin();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onThemeModeAuto(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onThemeModeAuto();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onThemeModeDay(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onThemeModeDay();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onThemeModeNight(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onThemeModeNight();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onScreenModeClean(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onScreenModeClean();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWifiOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onWifiOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWifiOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onWifiOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onBluetoothOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onBluetoothOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onBluetoothOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onBluetoothOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onVolumeUp(String str, String str2) {
        VolumeValue fromJson = VolumeValue.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onVolumeUp(fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onVolumeSet(String str, String str2) {
        VolumeValue fromJson = VolumeValue.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onVolumeSet(fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onVolumeDown(String str, String str2) {
        VolumeValue fromJson = VolumeValue.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onVolumeDown(fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onVolumeMax(String str, String str2) {
        String str3;
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        try {
            str3 = new JSONObject(str2).optString("stream_type", "3");
        } catch (Exception e) {
            e.printStackTrace();
            str3 = "3";
        }
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onVolumeMax(Integer.valueOf(StringUtil.isDecimalNumber(str3) ? str3 : "3").intValue());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onVolumeMin(String str, String str2) {
        String str3;
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        try {
            str3 = new JSONObject(str2).optString("stream_type", "3");
        } catch (Exception e) {
            e.printStackTrace();
            str3 = "3";
        }
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onVolumeMin(Integer.valueOf(Integer.valueOf(StringUtil.isDecimalNumber(str3) ? str3 : "3").intValue()).intValue());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onVolumeMute(String str, String str2) {
        String str3;
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        try {
            str3 = new JSONObject(str2).optString("stream_type", "3");
        } catch (Exception e) {
            e.printStackTrace();
            str3 = "3";
        }
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onVolumeMute(Integer.valueOf(StringUtil.isDecimalNumber(str3) ? str3 : "3").intValue());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onVolumeUnmute(String str, String str2) {
        String str3;
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        try {
            str3 = new JSONObject(str2).optString("stream_type", "3");
        } catch (Exception e) {
            e.printStackTrace();
            str3 = "3";
        }
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onVolumeUnmute(Integer.valueOf(StringUtil.isDecimalNumber(str3) ? str3 : "3").intValue());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onVolumeResume(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onVolumeResume();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onBrightnessUpPercent(String str, String str2) {
        AdjustValue fromJson = AdjustValue.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onBrightnessUpPercent(fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onScreenBrightnessSetPercent(String str, String str2) {
        AdjustValue fromJson = AdjustValue.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onBrightnessSetPercent(fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onBrightnessSet(String str, String str2) {
        AdjustValue fromJson = AdjustValue.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onBrightnessSet(fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onIcmBrightnessSet(String str, String str2) {
        AdjustValue fromJson = AdjustValue.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onIcmBrightnessSet(fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onIcmBrightnessUp(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onIcmBrightnessUp();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onIcmBrightnessMax(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onIcmBrightnessMax();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onIcmBrightnessDown(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onIcmBrightnessDown();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onIcmBrightnessMin(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onIcmBrightnessMin();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onOpenSettingPage(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onOpenSettingPage();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onOpenWifiPage(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onOpenWifiPage();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onBrightnessDownPercent(String str, String str2) {
        AdjustValue fromJson = AdjustValue.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onBrightnessDownPercent(fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onIcmBrightnessUpPercent(String str, String str2) {
        AdjustValue fromJson = AdjustValue.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onIcmBrightnessUpPercent(fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onIcmBrightnessSetPercent(String str, String str2) {
        AdjustValue fromJson = AdjustValue.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onIcmBrightnessSetPercent(fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onIcmBrightnessDownPercent(String str, String str2) {
        AdjustValue fromJson = AdjustValue.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onIcmBrightnessDownPercent(fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onScreenOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onScreenOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onScreenOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onScreenOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onScreenBrightnessStop(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onScreenBrightnessStop();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onScreenBrightnessAutoOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onScreenBrightnessAutoOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onScreenBrightnessAutoOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onScreenBrightnessAutoOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onVolumeNotificationMax(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onVolumeNotificationMax();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onVolumeNotificationMin(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onVolumeNotificationMin();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onVolumeNotificationMedium(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onVolumeNotificationMedium();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onVolumeNotificationUp(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onVolumeNotificationUp();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onVolumeNotificationDown(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onVolumeNotificationDown();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSoundEffectField(String str, String str2) {
        int i;
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        try {
            i = new JSONObject(str2).optInt(VuiConstants.EVENT_VALUE_DIRECTION);
        } catch (Exception e) {
            e.printStackTrace();
            i = 1;
        }
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onSoundEffectField(i);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSoundEffectMode(String str, String str2) {
        int i;
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        try {
            i = new JSONObject(str2).optInt("mode");
        } catch (Exception e) {
            e.printStackTrace();
            i = 1;
        }
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onSoundEffectMode(i);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSoundEffectScene(String str, String str2) {
        int i;
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        try {
            i = new JSONObject(str2).optInt(SceneSwitchStatisticsBean.NAME_SCENE);
        } catch (Exception e) {
            e.printStackTrace();
            i = 1;
        }
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onSoundEffectScene(i);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSoundEffectStyle(String str, String str2) {
        int i;
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        try {
            i = new JSONObject(str2).optInt(ThemeManager.AttributeSet.STYLE);
        } catch (Exception e) {
            e.printStackTrace();
            i = 1;
        }
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onSoundEffectStyle(i);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onHeadsetMode(String str, String str2) {
        int i;
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        try {
            i = new JSONObject(str2).optInt("mode");
        } catch (Exception e) {
            e.printStackTrace();
            i = 1;
        }
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((SystemListener) obj).onHeadsetMode(i);
            }
        }
    }
}
