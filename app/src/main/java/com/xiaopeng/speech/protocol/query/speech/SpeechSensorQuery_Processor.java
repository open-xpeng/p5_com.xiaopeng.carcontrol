package com.xiaopeng.speech.protocol.query.speech;

import com.xiaopeng.speech.annotation.IQueryProcessor;
import com.xiaopeng.speech.protocol.event.query.speech.SpeechQueryEvent;

/* loaded from: classes2.dex */
public class SpeechSensorQuery_Processor implements IQueryProcessor {
    private SpeechSensorQuery mTarget;

    public SpeechSensorQuery_Processor(SpeechSensorQuery speechSensorQuery) {
        this.mTarget = speechSensorQuery;
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public Object querySensor(String str, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -1914865600:
                if (str.equals(SpeechQueryEvent.SOUND_LOCATION)) {
                    c = 0;
                    break;
                }
                break;
            case -1584155906:
                if (str.equals(SpeechQueryEvent.GET_SCENE_SWITCH_STATUS)) {
                    c = 1;
                    break;
                }
                break;
            case -1563492784:
                if (str.equals(SpeechQueryEvent.IS_USEREXPRESSION_OPENED)) {
                    c = 2;
                    break;
                }
                break;
            case -414885244:
                if (str.equals(SpeechQueryEvent.GET_CURRENT_MODE)) {
                    c = 3;
                    break;
                }
                break;
            case -323965442:
                if (str.equals(SpeechQueryEvent.IS_WAKEUP_ENABLE)) {
                    c = 4;
                    break;
                }
                break;
            case 386767985:
                if (str.equals(SpeechQueryEvent.GET_CAR_PLATFORM)) {
                    c = 5;
                    break;
                }
                break;
            case 907076707:
                if (str.equals(SpeechQueryEvent.CURRENT_TTS_ENGINE)) {
                    c = 6;
                    break;
                }
                break;
            case 1205362780:
                if (str.equals(SpeechQueryEvent.IS_ACCOUNT_LOGIN)) {
                    c = 7;
                    break;
                }
                break;
            case 1352584367:
                if (str.equals(SpeechQueryEvent.IS_APP_FOREGROUND)) {
                    c = '\b';
                    break;
                }
                break;
            case 1717410403:
                if (str.equals(SpeechQueryEvent.APP_IS_INSTALLED)) {
                    c = '\t';
                    break;
                }
                break;
            case 1990811260:
                if (str.equals(SpeechQueryEvent.GET_FIRST_SPEECH_STATUS)) {
                    c = '\n';
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return Integer.valueOf(this.mTarget.getSoundLocation(str, str2));
            case 1:
                return Integer.valueOf(this.mTarget.getVuiSceneSwitchStatus(str, str2));
            case 2:
                return Boolean.valueOf(this.mTarget.isUserExpressionOpened(str, str2));
            case 3:
                return Integer.valueOf(this.mTarget.getCurrentMode(str, str2));
            case 4:
                return Boolean.valueOf(this.mTarget.isEnableGlobalWakeup(str, str2));
            case 5:
                return this.mTarget.getCarPlatform(str, str2);
            case 6:
                return Integer.valueOf(this.mTarget.getCurrentTtsEngine(str, str2));
            case 7:
                return Boolean.valueOf(this.mTarget.isAccountLogin(str, str2));
            case '\b':
                return Boolean.valueOf(this.mTarget.isAppForeground(str, str2));
            case '\t':
                return Boolean.valueOf(this.mTarget.appIsInstalled(str, str2));
            case '\n':
                return Integer.valueOf(this.mTarget.getFirstSpeechState(str, str2));
            default:
                return null;
        }
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public String[] getQueryEvents() {
        return new String[]{SpeechQueryEvent.SOUND_LOCATION, SpeechQueryEvent.IS_APP_FOREGROUND, SpeechQueryEvent.IS_ACCOUNT_LOGIN, SpeechQueryEvent.IS_WAKEUP_ENABLE, SpeechQueryEvent.GET_CURRENT_MODE, SpeechQueryEvent.GET_CAR_PLATFORM, SpeechQueryEvent.GET_SCENE_SWITCH_STATUS, SpeechQueryEvent.GET_FIRST_SPEECH_STATUS, SpeechQueryEvent.CURRENT_TTS_ENGINE, SpeechQueryEvent.APP_IS_INSTALLED, SpeechQueryEvent.IS_USEREXPRESSION_OPENED};
    }
}
