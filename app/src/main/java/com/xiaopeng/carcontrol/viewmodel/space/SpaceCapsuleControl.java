package com.xiaopeng.carcontrol.viewmodel.space;

import android.content.Intent;
import android.media.AudioConfig.AudioConfig;
import android.media.AudioConfig.SoundFieldData;
import android.media.AudioManager;
import android.provider.Settings;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.speech.speechwidget.ListWidget;

/* loaded from: classes2.dex */
public class SpaceCapsuleControl {
    public static final int CAPSULE_ALARM_VOLUME = 11;
    public static final int CAPSULE_BRIGHTNESS_LOW = 1;
    public static final int CAPSULE_VOLUME_LOW = 10;
    public static final int SOUND_STYLE_CINEMA = 4;
    public static final int SOUND_STYLE_SLEEP = 6;
    private static final String TAG = "SpaceCapsuleControl";
    private AudioConfig mAudioConfig;
    private AudioManager mAudioManager;
    public boolean mIsBrightnessSetLow;
    private boolean mIsChangeEffectType;
    private int mMediaVolume;
    private int mMediaVolumeBeforeAlarm;
    private int mSoundEffectMode;
    private int mSoundEffectStyle;
    private SoundFieldData mSoundFieldData;

    public void changeAlarmVolume(boolean bEnter) {
    }

    /* loaded from: classes2.dex */
    private static class SingleHolder {
        private static final SpaceCapsuleControl INSTANCE = new SpaceCapsuleControl();

        private SingleHolder() {
        }
    }

    private SpaceCapsuleControl() {
        this.mSoundEffectMode = -1;
        this.mMediaVolume = -1;
        this.mMediaVolumeBeforeAlarm = -1;
        this.mIsBrightnessSetLow = false;
        this.mSoundEffectStyle = -1;
        this.mIsChangeEffectType = false;
        this.mAudioConfig = new AudioConfig(App.getInstance().getApplicationContext());
        this.mAudioManager = (AudioManager) App.getInstance().getSystemService(ListWidget.EXTRA_TYPE_AUDIO);
    }

    public static SpaceCapsuleControl getInstance() {
        return SingleHolder.INSTANCE;
    }

    public synchronized void setSoundFieldToMiddle(boolean isEnter) {
        if (isEnter) {
            int soundEffectMode = this.mAudioConfig.getSoundEffectMode();
            this.mSoundEffectMode = soundEffectMode;
            this.mSoundFieldData = this.mAudioConfig.getSoundField(soundEffectMode);
            LogUtils.i(TAG, "current sound effect mode:" + this.mSoundEffectMode + " field:" + this.mSoundFieldData.x + "," + this.mSoundFieldData.y);
            this.mAudioConfig.setSoundField(this.mSoundEffectMode, 50, 50);
        } else if (this.mSoundFieldData != null && this.mSoundEffectMode != -1) {
            AudioConfig audioConfig = this.mAudioConfig;
            SoundFieldData soundField = audioConfig.getSoundField(audioConfig.getSoundEffectMode());
            if (soundField.x == 50 && soundField.y == 50) {
                this.mAudioConfig.setSoundField(this.mSoundEffectMode, this.mSoundFieldData.x, this.mSoundFieldData.y);
                LogUtils.i(TAG, "restore sound effect mode:" + this.mSoundEffectMode + " field:" + this.mSoundFieldData.x + "," + this.mSoundFieldData.y);
            }
        }
    }

    public synchronized void changeMediaVolume(boolean bEnter) {
        boolean z = true;
        if (bEnter) {
            int streamVolume = this.mAudioManager.getStreamVolume(3);
            if (streamVolume < 10) {
                this.mMediaVolume = streamVolume;
                LogUtils.i(TAG, "change media volume:" + streamVolume + " and change to 10");
                AudioManager audioManager = this.mAudioManager;
                if (bEnter) {
                    z = false;
                }
                audioManager.temporaryChangeVolumeDown(3, 10, 256, z);
            }
        } else if (this.mMediaVolume != -1) {
            LogUtils.i(TAG, "restore media volume to:" + this.mMediaVolume);
            AudioManager audioManager2 = this.mAudioManager;
            int i = this.mMediaVolume;
            if (bEnter) {
                z = false;
            }
            audioManager2.temporaryChangeVolumeDown(3, i, 256, z);
            this.mMediaVolume = -1;
        }
    }

    public void setCapsuleBrightnessLow() {
        if (this.mIsBrightnessSetLow) {
            return;
        }
        changeBrightness(true);
        this.mIsBrightnessSetLow = true;
    }

    public void restoreCapsuleBrightness() {
        if (this.mIsBrightnessSetLow) {
            changeBrightness(false);
            this.mIsBrightnessSetLow = false;
        }
    }

    private void changeBrightness(boolean bEnter) {
        LogUtils.i(TAG, "set capsule brightness:" + bEnter);
        Settings.Secure.putString(App.getInstance().getApplicationContext().getContentResolver(), "panel_brightness", "7#1#" + (bEnter ? 1 : 0) + "#scenario");
    }

    public synchronized void changeSoundEffectStyleCinema(boolean isEnter) {
        if (isEnter) {
            if (!this.mIsChangeEffectType) {
                AudioConfig audioConfig = this.mAudioConfig;
                this.mSoundEffectStyle = audioConfig.getSoundEffectType(audioConfig.getSoundEffectMode());
                LogUtils.i(TAG, "setSoundEffectStyle current:" + this.mSoundEffectStyle);
                AudioConfig audioConfig2 = this.mAudioConfig;
                audioConfig2.setSoundEffectType(audioConfig2.getSoundEffectMode(), 4);
                this.mIsChangeEffectType = true;
            }
        } else if (this.mIsChangeEffectType) {
            LogUtils.i(TAG, "restoreSoundEffectStyle to:" + this.mSoundEffectStyle);
            AudioConfig audioConfig3 = this.mAudioConfig;
            if (audioConfig3.getSoundEffectType(audioConfig3.getSoundEffectMode()) == 4) {
                AudioConfig audioConfig4 = this.mAudioConfig;
                audioConfig4.setSoundEffectType(audioConfig4.getSoundEffectMode(), this.mSoundEffectStyle);
            }
            this.mIsChangeEffectType = false;
        }
    }

    public void reqSfsOpen() {
        LogUtils.i(TAG, "reqSfsOpen sendBroadcast");
        Intent intent = new Intent("com.xiaopeng.intent.action.AIOT_NOTIFICATION");
        intent.putExtra("bd_event_type", "type_event_stat_notify");
        intent.putExtra("key_event_stat_notify", "notify_fragrance_into_sleep");
        intent.setFlags(16777216);
        App.getInstance().getApplicationContext().sendBroadcast(intent);
    }

    public void reqSfsForSleep(boolean enter) {
        LogUtils.i(TAG, "reqSfsForSleep sendBroadcast:" + enter);
        Intent intent = new Intent("com.xiaopeng.intent.action.AIOT_NOTIFICATION");
        intent.putExtra("bd_event_type", "type_event_stat_notify");
        intent.putExtra("key_event_stat_notify", enter ? "notify_fragrance_into_sleep" : "notify_fragrance_out_sleep");
        intent.setFlags(16777216);
        App.getInstance().getApplicationContext().sendBroadcast(intent);
    }

    public void switchBluetoothSource() {
        LogUtils.i(TAG, "carcontrol switchBluetoothSource sendBroadcast");
        Intent intent = new Intent("com.xiaopeng.intent.action.SWITCH_TO_BLUETOOTH_SOURCE");
        intent.setFlags(16777216);
        App.getInstance().getApplicationContext().sendBroadcast(intent);
    }
}
