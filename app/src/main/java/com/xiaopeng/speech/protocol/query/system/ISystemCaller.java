package com.xiaopeng.speech.protocol.query.system;

import com.xiaopeng.speech.IQueryCaller;

/* loaded from: classes2.dex */
public interface ISystemCaller extends IQueryCaller {
    int getAutoScreenStatus();

    int getCurIcmBrightness();

    int getCurScreenBrightness();

    int getCurVolume(int i);

    int getCurrentHeadsetMode();

    int getCurrentMusicActive();

    int getCurrentNedcStatus();

    int getCurrentSoundEffectField();

    int getCurrentSoundEffectMode();

    int getCurrentSoundEffectScene();

    int getCurrentSoundEffectStyle();

    int getGuiPageOpenState(String str);

    int getIntelligentDarkLightAdaptStatus();

    int getMaxIcmBrightnessValue();

    int getMaxScreenBrightnessValue();

    int getMaxVolumeValue(int i);

    int getMinIcmBrightnessValue();

    int getMinScreenBrightnessValue();

    int getMinVolumeValue(int i);

    int getTipsVolume();

    int isSupportHeadsetMode();

    int isSupportSoundEffectField(int i);

    int isSupportSoundEffectMode(int i);

    int isSupportSoundEffectScene(int i);

    int isSupportSoundEffectStyle(int i);
}
