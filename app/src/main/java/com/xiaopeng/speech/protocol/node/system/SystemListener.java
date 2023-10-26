package com.xiaopeng.speech.protocol.node.system;

import com.xiaopeng.speech.INodeListener;
import com.xiaopeng.speech.protocol.bean.AdjustValue;
import com.xiaopeng.speech.protocol.bean.VolumeValue;

/* loaded from: classes2.dex */
public interface SystemListener extends INodeListener {
    void onBluetoothOff();

    void onBluetoothOn();

    void onBrightnessDownPercent(AdjustValue adjustValue);

    void onBrightnessSet(AdjustValue adjustValue);

    void onBrightnessSetPercent(AdjustValue adjustValue);

    void onBrightnessUpPercent(AdjustValue adjustValue);

    void onHeadsetMode(int i);

    void onIcmBrightnessDown();

    void onIcmBrightnessDownPercent(AdjustValue adjustValue);

    void onIcmBrightnessMax();

    void onIcmBrightnessMin();

    void onIcmBrightnessSet(AdjustValue adjustValue);

    void onIcmBrightnessSetPercent(AdjustValue adjustValue);

    void onIcmBrightnessUp();

    void onIcmBrightnessUpPercent(AdjustValue adjustValue);

    void onOpenSettingPage();

    void onOpenWifiPage();

    void onScreenBrightnessAutoOff();

    void onScreenBrightnessAutoOn();

    void onScreenBrightnessDown();

    void onScreenBrightnessMax();

    void onScreenBrightnessMin();

    void onScreenBrightnessStop();

    void onScreenBrightnessUp();

    void onScreenModeClean();

    void onScreenOff();

    void onScreenOn();

    @Deprecated
    void onSettingOpen();

    void onSoundEffectField(int i);

    void onSoundEffectMode(int i);

    void onSoundEffectScene(int i);

    void onSoundEffectStyle(int i);

    void onThemeModeAuto();

    void onThemeModeDay();

    void onThemeModeNight();

    void onVolumeDown(VolumeValue volumeValue);

    void onVolumeMax(int i);

    void onVolumeMin(int i);

    void onVolumeMute(int i);

    void onVolumeNotificationDown();

    void onVolumeNotificationMax();

    void onVolumeNotificationMedium();

    void onVolumeNotificationMin();

    void onVolumeNotificationUp();

    void onVolumeResume();

    void onVolumeSet(VolumeValue volumeValue);

    void onVolumeUnmute(int i);

    void onVolumeUp(VolumeValue volumeValue);

    void onWifiOff();

    void onWifiOn();
}
