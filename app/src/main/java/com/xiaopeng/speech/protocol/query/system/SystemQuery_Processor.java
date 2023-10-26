package com.xiaopeng.speech.protocol.query.system;

import com.xiaopeng.speech.annotation.IQueryProcessor;
import com.xiaopeng.speech.protocol.event.query.QuerySystemEvent;

/* loaded from: classes2.dex */
public class SystemQuery_Processor implements IQueryProcessor {
    private SystemQuery mTarget;

    public SystemQuery_Processor(SystemQuery systemQuery) {
        this.mTarget = systemQuery;
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public Object querySensor(String str, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -2146663530:
                if (str.equals(QuerySystemEvent.GET_CUR_SCREEN_BRIGHTNESS)) {
                    c = 0;
                    break;
                }
                break;
            case -2111346675:
                if (str.equals(QuerySystemEvent.GET_MAX_SCREEN_BRIGHTNESS_VALUE)) {
                    c = 1;
                    break;
                }
                break;
            case -1976339039:
                if (str.equals(QuerySystemEvent.ISUPPORT_SOUND_POSTION)) {
                    c = 2;
                    break;
                }
                break;
            case -1613537166:
                if (str.equals(QuerySystemEvent.GET_MAX_VOLUME_VALUE)) {
                    c = 3;
                    break;
                }
                break;
            case -1541034698:
                if (str.equals(QuerySystemEvent.GET_CURRENT_NEDC_STATUS)) {
                    c = 4;
                    break;
                }
                break;
            case -1469262005:
                if (str.equals(QuerySystemEvent.ISSUPPORT_SOUND_SCENE)) {
                    c = 5;
                    break;
                }
                break;
            case -1423951429:
                if (str.equals(QuerySystemEvent.GET_PAGE_OPEN_STATUS)) {
                    c = 6;
                    break;
                }
                break;
            case -1338868101:
                if (str.equals(QuerySystemEvent.GET_MIN_SCREEN_BRIGHTNESS_VALUE)) {
                    c = 7;
                    break;
                }
                break;
            case -965726840:
                if (str.equals(QuerySystemEvent.GET_CURRENT_SOUND_POSITON)) {
                    c = '\b';
                    break;
                }
                break;
            case -884498389:
                if (str.equals(QuerySystemEvent.ISSUPPORT_HEADREST_MODE)) {
                    c = '\t';
                    break;
                }
                break;
            case -853478350:
                if (str.equals(QuerySystemEvent.GET_CURRENT_SOUND_SCENE)) {
                    c = '\n';
                    break;
                }
                break;
            case -841058592:
                if (str.equals(QuerySystemEvent.GET_MIN_VOLUME_VALUE)) {
                    c = 11;
                    break;
                }
                break;
            case -813486830:
                if (str.equals(QuerySystemEvent.GET_CURRENT_HEADREST_MODE)) {
                    c = '\f';
                    break;
                }
                break;
            case -701436316:
                if (str.equals(QuerySystemEvent.GET_MAX_ICM_BRIGHTNESS_VALUE)) {
                    c = '\r';
                    break;
                }
                break;
            case -668448824:
                if (str.equals(QuerySystemEvent.GET_INTELLIGENT_DARK_LIGHT_ADAPT_STATUS)) {
                    c = 14;
                    break;
                }
                break;
            case -540074940:
                if (str.equals(QuerySystemEvent.GET_CUR_VOLUME)) {
                    c = 15;
                    break;
                }
                break;
            case -288490975:
                if (str.equals(QuerySystemEvent.GET_AUTO_SCREEN_STATUS)) {
                    c = 16;
                    break;
                }
                break;
            case -183670776:
                if (str.equals(QuerySystemEvent.GET_CURRENT_SOUND_EFFECT_STYLE)) {
                    c = 17;
                    break;
                }
                break;
            case 71042258:
                if (str.equals(QuerySystemEvent.GET_MIN_ICM_BRIGHTNESS_VALUE)) {
                    c = 18;
                    break;
                }
                break;
            case 148702545:
                if (str.equals(QuerySystemEvent.GET_TIPS_VOLUME)) {
                    c = 19;
                    break;
                }
                break;
            case 1473884338:
                if (str.equals(QuerySystemEvent.GET_SYSTEM_MUSIC_ISPLAYING)) {
                    c = 20;
                    break;
                }
                break;
            case 1521446945:
                if (str.equals(QuerySystemEvent.ISSUPPORT_SOUND_EFFECT_STYLE)) {
                    c = 21;
                    break;
                }
                break;
            case 1587476973:
                if (str.equals(QuerySystemEvent.GET_SOUND_EFFECT_MODEL)) {
                    c = 22;
                    break;
                }
                break;
            case 1606123190:
                if (str.equals(QuerySystemEvent.GET_CUR_ICM_BRIGHTNESS)) {
                    c = 23;
                    break;
                }
                break;
            case 1832379462:
                if (str.equals(QuerySystemEvent.ISSUPPORT_SOUND_EFFECT_DYNAUDIO)) {
                    c = 24;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return Integer.valueOf(this.mTarget.getCurScreenBrightness(str, str2));
            case 1:
                return Integer.valueOf(this.mTarget.getMaxScreenBrightnessValue(str, str2));
            case 2:
                return Integer.valueOf(this.mTarget.isSupportSoundEffectField(str, str2));
            case 3:
                return Integer.valueOf(this.mTarget.getMaxVolumeValue(str, str2));
            case 4:
                return Integer.valueOf(this.mTarget.getCurrentNedcStatus(str, str2));
            case 5:
                return Integer.valueOf(this.mTarget.isSupportSoundEffectScene(str, str2));
            case 6:
                return Integer.valueOf(this.mTarget.getGuiPageOpenState(str, str2));
            case 7:
                return Integer.valueOf(this.mTarget.getMinScreenBrightnessValue(str, str2));
            case '\b':
                return Integer.valueOf(this.mTarget.getCurrentSoundEffectField(str, str2));
            case '\t':
                return Integer.valueOf(this.mTarget.isSupportHeadsetMode(str, str2));
            case '\n':
                return Integer.valueOf(this.mTarget.getCurrentSoundEffectScene(str, str2));
            case 11:
                return Integer.valueOf(this.mTarget.getMinVolumeValue(str, str2));
            case '\f':
                return Integer.valueOf(this.mTarget.getCurrentHeadsetMode(str, str2));
            case '\r':
                return Integer.valueOf(this.mTarget.getMaxIcmBrightnessValue(str, str2));
            case 14:
                return Integer.valueOf(this.mTarget.getIntelligentDarkLightAdaptStatus(str, str2));
            case 15:
                return Integer.valueOf(this.mTarget.getCurVolume(str, str2));
            case 16:
                return Integer.valueOf(this.mTarget.getAutoScreenStatus(str, str2));
            case 17:
                return Integer.valueOf(this.mTarget.getCurrentSoundEffectStyle(str, str2));
            case 18:
                return Integer.valueOf(this.mTarget.getMinIcmBrightnessValue(str, str2));
            case 19:
                return Integer.valueOf(this.mTarget.getTipsVolume(str, str2));
            case 20:
                return Integer.valueOf(this.mTarget.getCurrentMusicActive(str, str2));
            case 21:
                return Integer.valueOf(this.mTarget.isSupportSoundEffectStyle(str, str2));
            case 22:
                return Integer.valueOf(this.mTarget.getCurrentSoundEffectMode(str, str2));
            case 23:
                return Integer.valueOf(this.mTarget.getCurIcmBrightness(str, str2));
            case 24:
                return Integer.valueOf(this.mTarget.isSupportSoundEffectMode(str, str2));
            default:
                return null;
        }
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public String[] getQueryEvents() {
        return new String[]{QuerySystemEvent.GET_CUR_SCREEN_BRIGHTNESS, QuerySystemEvent.GET_MAX_SCREEN_BRIGHTNESS_VALUE, QuerySystemEvent.GET_MIN_SCREEN_BRIGHTNESS_VALUE, QuerySystemEvent.GET_CUR_ICM_BRIGHTNESS, QuerySystemEvent.GET_MAX_ICM_BRIGHTNESS_VALUE, QuerySystemEvent.GET_MIN_ICM_BRIGHTNESS_VALUE, QuerySystemEvent.GET_CUR_VOLUME, QuerySystemEvent.GET_MAX_VOLUME_VALUE, QuerySystemEvent.GET_MIN_VOLUME_VALUE, QuerySystemEvent.GET_TIPS_VOLUME, QuerySystemEvent.GET_PAGE_OPEN_STATUS, QuerySystemEvent.GET_SYSTEM_MUSIC_ISPLAYING, QuerySystemEvent.GET_SOUND_EFFECT_MODEL, QuerySystemEvent.ISSUPPORT_SOUND_EFFECT_DYNAUDIO, QuerySystemEvent.ISSUPPORT_SOUND_EFFECT_STYLE, QuerySystemEvent.GET_CURRENT_SOUND_EFFECT_STYLE, QuerySystemEvent.ISSUPPORT_SOUND_SCENE, QuerySystemEvent.GET_CURRENT_SOUND_SCENE, QuerySystemEvent.ISSUPPORT_HEADREST_MODE, QuerySystemEvent.GET_CURRENT_HEADREST_MODE, QuerySystemEvent.ISUPPORT_SOUND_POSTION, QuerySystemEvent.GET_CURRENT_SOUND_POSITON, QuerySystemEvent.GET_AUTO_SCREEN_STATUS, QuerySystemEvent.GET_CURRENT_NEDC_STATUS, QuerySystemEvent.GET_INTELLIGENT_DARK_LIGHT_ADAPT_STATUS};
    }
}
