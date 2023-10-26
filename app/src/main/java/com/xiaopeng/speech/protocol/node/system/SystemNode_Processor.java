package com.xiaopeng.speech.protocol.node.system;

import com.alibaba.fastjson.parser.JSONLexer;
import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.protocol.event.SystemEvent;

/* loaded from: classes2.dex */
public class SystemNode_Processor implements ICommandProcessor {
    private SystemNode mTarget;

    public SystemNode_Processor(SystemNode systemNode) {
        this.mTarget = systemNode;
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String str, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -1919368988:
                if (str.equals(SystemEvent.VOLUME_NOTIFICATION_MEDIUM)) {
                    c = 0;
                    break;
                }
                break;
            case -1597026812:
                if (str.equals(SystemEvent.ICM_BRIGHTNESS_UP_PERCENT)) {
                    c = 1;
                    break;
                }
                break;
            case -1344595949:
                if (str.equals(SystemEvent.SCREEN_BRIGHTNESS_AUTO_ON)) {
                    c = 2;
                    break;
                }
                break;
            case -1017521433:
                if (str.equals(SystemEvent.ICM_BRIGHTNESS_SET_PERCENT)) {
                    c = 3;
                    break;
                }
                break;
            case -1008260193:
                if (str.equals(SystemEvent.BLUETOOTH_OFF)) {
                    c = 4;
                    break;
                }
                break;
            case -931863087:
                if (str.equals(SystemEvent.VOLUME_NOTIFICATION_DOWN)) {
                    c = 5;
                    break;
                }
                break;
            case -601542604:
                if (str.equals(SystemEvent.ICM_BRIGHTNESS_DOWN)) {
                    c = 6;
                    break;
                }
                break;
            case -537146671:
                if (str.equals(SystemEvent.WIFI_SETTING_OPEN)) {
                    c = 7;
                    break;
                }
                break;
            case -453867971:
                if (str.equals(SystemEvent.BRIGHTNESS_UP_PERCENT)) {
                    c = '\b';
                    break;
                }
                break;
            case -147779855:
                if (str.equals(SystemEvent.VOLUME_RESUME)) {
                    c = '\t';
                    break;
                }
                break;
            case -95607791:
                if (str.equals(SystemEvent.SCREEN_ON)) {
                    c = '\n';
                    break;
                }
                break;
            case -70327096:
                if (str.equals(SystemEvent.WIFI_ON)) {
                    c = 11;
                    break;
                }
                break;
            case -53759242:
                if (str.equals(SystemEvent.VOLUME_UNMUTE)) {
                    c = '\f';
                    break;
                }
                break;
            case 60664270:
                if (str.equals(SystemEvent.BRIGHTNESS_SET_PERCENT)) {
                    c = '\r';
                    break;
                }
                break;
            case 108495445:
                if (str.equals(SystemEvent.VOLUME_NOTIFICATION_MAX)) {
                    c = 14;
                    break;
                }
                break;
            case 108495683:
                if (str.equals(SystemEvent.VOLUME_NOTIFICATION_MIN)) {
                    c = 15;
                    break;
                }
                break;
            case 195917088:
                if (str.equals(SystemEvent.VOLUME_MAX)) {
                    c = 16;
                    break;
                }
                break;
            case 195917326:
                if (str.equals(SystemEvent.VOLUME_MIN)) {
                    c = 17;
                    break;
                }
                break;
            case 195922974:
                if (str.equals(SystemEvent.VOLUME_SET)) {
                    c = 18;
                    break;
                }
                break;
            case 448747494:
                if (str.equals(SystemEvent.SCREEN_BRIGHTNESS_UP)) {
                    c = 19;
                    break;
                }
                break;
            case 483297957:
                if (str.equals(SystemEvent.THEME_MODE_AUTO)) {
                    c = 20;
                    break;
                }
                break;
            case 546271995:
                if (str.equals(SystemEvent.SOUND_EFFECT_DIRECTION_SET)) {
                    c = 21;
                    break;
                }
                break;
            case 605044356:
                if (str.equals(SystemEvent.BRIGHTNESS_DOWN_PERCENT)) {
                    c = 22;
                    break;
                }
                break;
            case 660212143:
                if (str.equals(SystemEvent.BLUETOOTH_ON)) {
                    c = 23;
                    break;
                }
                break;
            case 662217111:
                if (str.equals(SystemEvent.SETTING_PAGE_OPEN)) {
                    c = 24;
                    break;
                }
                break;
            case 759114577:
                if (str.equals(SystemEvent.SOUND_EFFECT_SCENE_SET)) {
                    c = 25;
                    break;
                }
                break;
            case 968795136:
                if (str.equals(SystemEvent.SOUND_EFFECT_STYlE_SET)) {
                    c = JSONLexer.EOI;
                    break;
                }
                break;
            case 979070195:
                if (str.equals(SystemEvent.HEADREST_MODE_SET)) {
                    c = 27;
                    break;
                }
                break;
            case 985423846:
                if (str.equals(SystemEvent.THEME_MODE_DAY)) {
                    c = 28;
                    break;
                }
                break;
            case 1026262393:
                if (str.equals(SystemEvent.SCREEN_BRIGHTNESS_MAX)) {
                    c = 29;
                    break;
                }
                break;
            case 1026262631:
                if (str.equals(SystemEvent.SCREEN_BRIGHTNESS_MIN)) {
                    c = 30;
                    break;
                }
                break;
            case 1026268279:
                if (str.equals(SystemEvent.SCREEN_BRIGHTNESS_SET)) {
                    c = 31;
                    break;
                }
                break;
            case 1209375344:
                if (str.equals(SystemEvent.SCREEN_MODE_CLEAN)) {
                    c = ' ';
                    break;
                }
                break;
            case 1267198395:
                if (str.equals(SystemEvent.SCREEN_BRIGHTNESS_AUTO_OFF)) {
                    c = '!';
                    break;
                }
                break;
            case 1331125629:
                if (str.equals(SystemEvent.SCREEN_OFF)) {
                    c = '\"';
                    break;
                }
                break;
            case 1447822712:
                if (str.equals(SystemEvent.SOUND_EFFECT_MODE_SET)) {
                    c = '#';
                    break;
                }
                break;
            case 1541025931:
                if (str.equals(SystemEvent.ICM_BRIGHTNESS_DOWN_PERCENT)) {
                    c = '$';
                    break;
                }
                break;
            case 1749108525:
                if (str.equals(SystemEvent.SCREEN_BRIGHTNESS_DOWN)) {
                    c = '%';
                    break;
                }
                break;
            case 1749559949:
                if (str.equals(SystemEvent.SCREEN_BRIGHTNESS_STOP)) {
                    c = '&';
                    break;
                }
                break;
            case 1778207846:
                if (str.equals(SystemEvent.VOLUME_DOWN)) {
                    c = '\'';
                    break;
                }
                break;
            case 1778481629:
                if (str.equals(SystemEvent.VOLUME_MUTE)) {
                    c = '(';
                    break;
                }
                break;
            case 1781718930:
                if (str.equals(SystemEvent.ICM_BRIGHTNESS_MAX)) {
                    c = ')';
                    break;
                }
                break;
            case 1781719168:
                if (str.equals(SystemEvent.ICM_BRIGHTNESS_MIN)) {
                    c = '*';
                    break;
                }
                break;
            case 1781724816:
                if (str.equals(SystemEvent.ICM_BRIGHTNESS_SET)) {
                    c = '+';
                    break;
                }
                break;
            case 1858590381:
                if (str.equals(SystemEvent.ICM_BRIGHTNESS_UP)) {
                    c = ',';
                    break;
                }
                break;
            case 1943162762:
                if (str.equals(SystemEvent.VOLUME_NOTIFICATION_UP)) {
                    c = '-';
                    break;
                }
                break;
            case 1945982815:
                if (str.equals(SystemEvent.VOLUME_UP)) {
                    c = '.';
                    break;
                }
                break;
            case 2108970466:
                if (str.equals(SystemEvent.THEME_MODE_NIGHT)) {
                    c = '/';
                    break;
                }
                break;
            case 2114827174:
                if (str.equals(SystemEvent.WIFI_OFF)) {
                    c = '0';
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                this.mTarget.onVolumeNotificationMedium(str, str2);
                return;
            case 1:
                this.mTarget.onIcmBrightnessUpPercent(str, str2);
                return;
            case 2:
                this.mTarget.onScreenBrightnessAutoOn(str, str2);
                return;
            case 3:
                this.mTarget.onIcmBrightnessSetPercent(str, str2);
                return;
            case 4:
                this.mTarget.onBluetoothOff(str, str2);
                return;
            case 5:
                this.mTarget.onVolumeNotificationDown(str, str2);
                return;
            case 6:
                this.mTarget.onIcmBrightnessDown(str, str2);
                return;
            case 7:
                this.mTarget.onOpenWifiPage(str, str2);
                return;
            case '\b':
                this.mTarget.onBrightnessUpPercent(str, str2);
                return;
            case '\t':
                this.mTarget.onVolumeResume(str, str2);
                return;
            case '\n':
                this.mTarget.onScreenOn(str, str2);
                return;
            case 11:
                this.mTarget.onWifiOn(str, str2);
                return;
            case '\f':
                this.mTarget.onVolumeUnmute(str, str2);
                return;
            case '\r':
                this.mTarget.onScreenBrightnessSetPercent(str, str2);
                return;
            case 14:
                this.mTarget.onVolumeNotificationMax(str, str2);
                return;
            case 15:
                this.mTarget.onVolumeNotificationMin(str, str2);
                return;
            case 16:
                this.mTarget.onVolumeMax(str, str2);
                return;
            case 17:
                this.mTarget.onVolumeMin(str, str2);
                return;
            case 18:
                this.mTarget.onVolumeSet(str, str2);
                return;
            case 19:
                this.mTarget.onScreenBrightnessUp(str, str2);
                return;
            case 20:
                this.mTarget.onThemeModeAuto(str, str2);
                return;
            case 21:
                this.mTarget.onSoundEffectField(str, str2);
                return;
            case 22:
                this.mTarget.onBrightnessDownPercent(str, str2);
                return;
            case 23:
                this.mTarget.onBluetoothOn(str, str2);
                return;
            case 24:
                this.mTarget.onOpenSettingPage(str, str2);
                return;
            case 25:
                this.mTarget.onSoundEffectScene(str, str2);
                return;
            case 26:
                this.mTarget.onSoundEffectStyle(str, str2);
                return;
            case 27:
                this.mTarget.onHeadsetMode(str, str2);
                return;
            case 28:
                this.mTarget.onThemeModeDay(str, str2);
                return;
            case 29:
                this.mTarget.onScreenBrightnessMax(str, str2);
                return;
            case 30:
                this.mTarget.onScreenBrightnessMin(str, str2);
                return;
            case 31:
                this.mTarget.onBrightnessSet(str, str2);
                return;
            case ' ':
                this.mTarget.onScreenModeClean(str, str2);
                return;
            case '!':
                this.mTarget.onScreenBrightnessAutoOff(str, str2);
                return;
            case '\"':
                this.mTarget.onScreenOff(str, str2);
                return;
            case '#':
                this.mTarget.onSoundEffectMode(str, str2);
                return;
            case '$':
                this.mTarget.onIcmBrightnessDownPercent(str, str2);
                return;
            case '%':
                this.mTarget.onScreenBrightnessDown(str, str2);
                return;
            case '&':
                this.mTarget.onScreenBrightnessStop(str, str2);
                return;
            case '\'':
                this.mTarget.onVolumeDown(str, str2);
                return;
            case '(':
                this.mTarget.onVolumeMute(str, str2);
                return;
            case ')':
                this.mTarget.onIcmBrightnessMax(str, str2);
                return;
            case '*':
                this.mTarget.onIcmBrightnessMin(str, str2);
                return;
            case '+':
                this.mTarget.onIcmBrightnessSet(str, str2);
                return;
            case ',':
                this.mTarget.onIcmBrightnessUp(str, str2);
                return;
            case '-':
                this.mTarget.onVolumeNotificationUp(str, str2);
                return;
            case '.':
                this.mTarget.onVolumeUp(str, str2);
                return;
            case '/':
                this.mTarget.onThemeModeNight(str, str2);
                return;
            case '0':
                this.mTarget.onWifiOff(str, str2);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{SystemEvent.SCREEN_BRIGHTNESS_UP, SystemEvent.SCREEN_BRIGHTNESS_MAX, SystemEvent.SCREEN_BRIGHTNESS_DOWN, SystemEvent.SCREEN_BRIGHTNESS_MIN, SystemEvent.THEME_MODE_AUTO, SystemEvent.THEME_MODE_DAY, SystemEvent.THEME_MODE_NIGHT, SystemEvent.SCREEN_MODE_CLEAN, SystemEvent.WIFI_OFF, SystemEvent.WIFI_ON, SystemEvent.BLUETOOTH_OFF, SystemEvent.BLUETOOTH_ON, SystemEvent.VOLUME_UP, SystemEvent.VOLUME_SET, SystemEvent.VOLUME_DOWN, SystemEvent.VOLUME_MAX, SystemEvent.VOLUME_MIN, SystemEvent.VOLUME_MUTE, SystemEvent.VOLUME_UNMUTE, SystemEvent.VOLUME_RESUME, SystemEvent.BRIGHTNESS_UP_PERCENT, SystemEvent.BRIGHTNESS_SET_PERCENT, SystemEvent.SCREEN_BRIGHTNESS_SET, SystemEvent.ICM_BRIGHTNESS_SET, SystemEvent.ICM_BRIGHTNESS_UP, SystemEvent.ICM_BRIGHTNESS_MAX, SystemEvent.ICM_BRIGHTNESS_DOWN, SystemEvent.ICM_BRIGHTNESS_MIN, SystemEvent.SETTING_PAGE_OPEN, SystemEvent.WIFI_SETTING_OPEN, SystemEvent.BRIGHTNESS_DOWN_PERCENT, SystemEvent.ICM_BRIGHTNESS_UP_PERCENT, SystemEvent.ICM_BRIGHTNESS_SET_PERCENT, SystemEvent.ICM_BRIGHTNESS_DOWN_PERCENT, SystemEvent.SCREEN_OFF, SystemEvent.SCREEN_ON, SystemEvent.SCREEN_BRIGHTNESS_STOP, SystemEvent.SCREEN_BRIGHTNESS_AUTO_ON, SystemEvent.SCREEN_BRIGHTNESS_AUTO_OFF, SystemEvent.VOLUME_NOTIFICATION_MAX, SystemEvent.VOLUME_NOTIFICATION_MIN, SystemEvent.VOLUME_NOTIFICATION_MEDIUM, SystemEvent.VOLUME_NOTIFICATION_UP, SystemEvent.VOLUME_NOTIFICATION_DOWN, SystemEvent.SOUND_EFFECT_DIRECTION_SET, SystemEvent.SOUND_EFFECT_MODE_SET, SystemEvent.SOUND_EFFECT_SCENE_SET, SystemEvent.SOUND_EFFECT_STYlE_SET, SystemEvent.HEADREST_MODE_SET};
    }
}
