package com.xiaopeng.carcontrol.helper;

import android.media.AudioAttributes;
import android.media.AudioConfig.AudioConfig;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.SystemProperties;
import com.xiaopeng.carcontrol.util.ContextUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.speech.speechwidget.ListWidget;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes2.dex */
public class SoundHelper {
    private static final int DEFAULT_INVALID_STREAM_ID = 0;
    public static final String PATH_AIR_PROTECT_1 = "/system/media/audio/xiaopeng/cdu/wav/CDU_air_protect_3.wav";
    public static final String PATH_AIR_PROTECT_2 = "/system/media/audio/xiaopeng/cdu/wav/CDU_air_protect_4.wav";
    public static final String PATH_AS_HEIGHT_HL0 = "/system/media/audio/xiaopeng/cdu/wav/CDU_as_height_hl0.wav";
    public static final String PATH_AS_HEIGHT_HL1 = "/system/media/audio/xiaopeng/cdu/wav/CDU_as_height_hl1.wav";
    public static final String PATH_AS_HEIGHT_HL2 = "/system/media/audio/xiaopeng/cdu/wav/CDU_as_height_hl2.wav";
    public static final String PATH_AS_HEIGHT_LL1 = "/system/media/audio/xiaopeng/cdu/wav/CDU_as_height_ll1.wav";
    public static final String PATH_AS_HEIGHT_LL2 = "/system/media/audio/xiaopeng/cdu/wav/CDU_as_height_ll2.wav";
    public static final String PATH_AS_HEIGHT_LL3 = "/system/media/audio/xiaopeng/cdu/wav/CDU_as_height_ll3.wav";
    public static final String PATH_AVAS_LOW_SPEED_1 = "/system/media/audio/xiaopeng/cdu/wav/CDU_avas_low_speed_1.wav";
    public static final String PATH_AVAS_LOW_SPEED_2 = "/system/media/audio/xiaopeng/cdu/wav/CDU_avas_low_speed_2.wav";
    public static final String PATH_AVAS_LOW_SPEED_3 = "/system/media/audio/xiaopeng/cdu/wav/CDU_avas_low_speed_3.wav";
    public static final String PATH_AVAS_LOW_SPEED_4 = "/system/media/audio/xiaopeng/cdu/wav/CDU_avas_low_speed_4.wav";
    public static final String PATH_AVAS_SAYHI_1 = "/system/media/audio/xiaopeng/cdu/wav/CDU_avas_sayhi_mode_1.wav";
    public static final String PATH_AVAS_SAYHI_2 = "/system/media/audio/xiaopeng/cdu/wav/CDU_avas_sayhi_mode_2.wav";
    public static final String PATH_AVAS_SAYHI_3 = "/system/media/audio/xiaopeng/cdu/wav/CDU_avas_sayhi_mode_3.wav";
    public static final String PATH_BOOT_1 = "/system/media/audio/xiaopeng/cdu/boot/CDU_boot_01_aether.wav";
    public static final String PATH_BOOT_2 = "/system/media/audio/xiaopeng/cdu/boot/CDU_boot_02_piano.wav";
    public static final String PATH_BOOT_3 = "/system/media/audio/xiaopeng/cdu/boot/CDU_boot_03_sci-Fi.wav";
    public static final String PATH_BOOT_4 = "/system/media/audio/xiaopeng/cdu/boot/CDU_boot_04_wave.wav";
    public static final String PATH_CLOSE_CHARGE = "/system/media/audio/xiaopeng/cdu/wav/CDU_chargeport_off.wav";
    public static final String PATH_DRIVE_MODE_ANTISICK = "/system/media/audio/xiaopeng/cdu/wav/CDU_drive_comfortable.wav";
    public static final String PATH_DRIVE_MODE_BOOST = "/system/media/audio/xiaopeng/cdu/wav/CDU_drive_boost.wav";
    public static final String PATH_DRIVE_MODE_ECO = "/system/media/audio/xiaopeng/cdu/wav/CDU_drive_eco.wav";
    public static final String PATH_DRIVE_MODE_MUD = "/system/media/audio/xiaopeng/cdu/wav/CDU_drive_mud.wav";
    public static final String PATH_DRIVE_MODE_NORMAL = "/system/media/audio/xiaopeng/cdu/wav/CDU_drive_normal.wav";
    public static final String PATH_DRIVE_MODE_NORMAL_NEW = "/system/media/audio/xiaopeng/cdu/wav/CDU_drive_normal_new.wav";
    public static final String PATH_DRIVE_MODE_RACER = "/system/media/audio/xiaopeng/cdu/wav/CDU_drive_racer.wav";
    public static final String PATH_DRIVE_MODE_SPORT = "/system/media/audio/xiaopeng/cdu/wav/CDU_drive_sport.wav";
    public static final String PATH_DRIVE_MODE_XPEDAL = "/system/media/audio/xiaopeng/cdu/wav/CDU_x_pedal.wav";
    public static final String PATH_FOLD_MIRROR = "/system/media/audio/xiaopeng/cdu/wav/CDU_mirror_fold.wav";
    public static final String PATH_INVALID = "";
    public static final String PATH_LAMP_HEIGHT_AUTO = "/system/media/audio/xiaopeng/cdu/wav/CDU_lamp_height_auto.wav";
    public static final String PATH_LAMP_HEIGHT_HIGH = "/system/media/audio/xiaopeng/cdu/wav/CDU_lamp_height_hight.wav";
    public static final String PATH_LAMP_HEIGHT_HIGHEST = "/system/media/audio/xiaopeng/cdu/wav/CDU_lamp_height_highest.wav";
    public static final String PATH_LAMP_HEIGHT_LOW = "/system/media/audio/xiaopeng/cdu/wav/CDU_lamp_height_low.wav";
    public static final String PATH_LAMP_HEIGHT_LOWEST = "/system/media/audio/xiaopeng/cdu/wav/CDU_lamp_height_lowest.wav";
    public static final String PATH_OPEN_CHARGE = "/system/media/audio/xiaopeng/cdu/wav/CDU_chargeport_on.wav";
    public static final String PATH_SEAT_ADJUST = "/system/media/audio/xiaopeng/cdu/wav/CDU_seat_adjust.wav";
    public static final String PATH_SEAT_RHYTHM_MODE_1 = "/system/media/audio/xiaopeng/cdu/wav/CDU_seat_rhythm_mode_1.wav";
    public static final String PATH_SEAT_RHYTHM_MODE_2 = "/system/media/audio/xiaopeng/cdu/wav/CDU_seat_rhythm_mode_2.wav";
    public static final String PATH_SPACE_CAPSULE_CINEMA = "/system/media/audio/xiaopeng/cdu/wav/CDU_drive_normal.wav";
    public static final String PATH_SPACE_CAPSULE_ENTER = "/system/media/audio/xiaopeng/cdu/ogg/enter_space_capsule.ogg";
    public static final String PATH_SPACE_CAPSULE_SLEEP = "/system/media/audio/xiaopeng/cdu/wav/CDU_drive_sport.wav";
    public static final String PATH_STEER_ADJUST = "/system/media/audio/xiaopeng/cdu/wav/CDU_steer_adjust.wav";
    public static final String PATH_SYS_READY = "/system/media/audio/xiaopeng/cdu/wav/CDU_ready.wav";
    public static final String PATH_TRUNK_OFF = "/system/media/audio/xiaopeng/cdu/wav/CDU_trunk_off.wav";
    public static final String PATH_TRUNK_ON = "/system/media/audio/xiaopeng/cdu/wav/CDU_trunk_on.wav";
    public static final String PATH_UNFOLD_MIRROR = "/system/media/audio/xiaopeng/cdu/wav/CDU_mirror_unfold.wav";
    public static final String PATH_WHEEL_SCROLL_1 = "/system/media/audio/xiaopeng/cdu/wav/CDU_wheel_scroll_1.wav";
    public static final String PATH_WHEEL_SCROLL_2 = "/system/media/audio/xiaopeng/cdu/wav/CDU_wheel_scroll_2.wav";
    public static final String PATH_WHEEL_TIP_1 = "/system/media/audio/xiaopeng/cdu/wav/CDU_wheel_tip_1.wav";
    public static final int STREAM_MUSIC = 3;
    public static final int STREAM_NOTIFICATION = 5;
    public static final int STREAM_SYSTEM = 1;
    private static final String SYSTEM_MEDIA_PLAYER_DISABLE = "void.xppolicy.system.notplay";
    private static final String TAG = "SoundHelper";
    private static AudioConfig mAudioConfig;
    private static AudioManager sAudioManager;
    private static AudioFocusRequest sFocusRequest;
    private static MediaPlayer sPlayer;
    private static Map<String, Integer> sSoundData;
    private static SoundPool sSoundPool;
    private static ExecutorService sThreadPool;
    public static final String PATH_WHEEL_SCROLL_7 = "/system/media/audio/xiaopeng/cdu/wav/CDU_wheel_scroll_7.wav";
    private static final String[] PATH_NOTIFICATION_ARRAY = {PATH_WHEEL_SCROLL_7};
    private static boolean isInit = false;
    private static int sStreamId = 0;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public interface IFadeOutCallback {
        void onFadeOutFinish();
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    @interface SoundPath {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    @interface StreamType {
    }

    public static void init() {
        String[] strArr;
        if (isInit) {
            return;
        }
        LogUtils.d(TAG, "init start");
        sAudioManager = (AudioManager) ContextUtils.getContext().getSystemService(ListWidget.EXTRA_TYPE_AUDIO);
        mAudioConfig = new AudioConfig(ContextUtils.getContext());
        sPlayer = new MediaPlayer();
        AudioAttributes.Builder builder = new AudioAttributes.Builder();
        builder.setLegacyStreamType(5);
        sSoundPool = new SoundPool.Builder().setAudioAttributes(builder.build()).setMaxStreams(10).build();
        sSoundData = new HashMap();
        for (String str : PATH_NOTIFICATION_ARRAY) {
            sSoundData.put(str, Integer.valueOf(sSoundPool.load(str, 1)));
        }
        sThreadPool = Executors.newFixedThreadPool(1);
        isInit = true;
        LogUtils.d(TAG, "init end");
    }

    public static void play(String path, boolean requestFocus, boolean isNotification) {
        play(path, requestFocus, isNotification ? 5 : 1);
    }

    public static void play(final String path, final boolean requestFocus, final int streamType) {
        AudioConfig audioConfig = mAudioConfig;
        if (audioConfig == null) {
            return;
        }
        boolean isSystemSoundEnabled = audioConfig.isSystemSoundEnabled();
        LogUtils.d(TAG, "play sound:" + path + ", requestFocus:" + requestFocus + ", streamType:" + streamType + ", isSystemSoundEnable:" + isSystemSoundEnabled, false);
        boolean z = streamType == 5;
        if (!z || isSystemSoundEnabled) {
            if (z) {
                stopNotification();
            }
            sThreadPool.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.helper.-$$Lambda$SoundHelper$0NSBADz7iwiBYjc-u0jTbIU3iGc
                @Override // java.lang.Runnable
                public final void run() {
                    SoundHelper.lambda$play$1(path, requestFocus, streamType);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$play$1(final String path, final boolean requestFocus, final int streamType) {
        if (sPlayer.isPlaying()) {
            fadeOut(new IFadeOutCallback() { // from class: com.xiaopeng.carcontrol.helper.-$$Lambda$SoundHelper$exIgnm4aL2LXSjFm6sl2d6yi5mM
                @Override // com.xiaopeng.carcontrol.helper.SoundHelper.IFadeOutCallback
                public final void onFadeOutFinish() {
                    SoundHelper.realPlay(path, requestFocus, streamType);
                }
            });
        } else {
            realPlay(path, requestFocus, streamType);
        }
    }

    public static void stopAllSound() {
        sThreadPool.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.helper.-$$Lambda$SoundHelper$LWplnmoldyae9GQ1jAac4D3Nq2w
            @Override // java.lang.Runnable
            public final void run() {
                SoundHelper.lambda$stopAllSound$2();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$stopAllSound$2() {
        MediaPlayer mediaPlayer = sPlayer;
        if (mediaPlayer == null || !mediaPlayer.isPlaying()) {
            return;
        }
        fadeOut(new IFadeOutCallback() { // from class: com.xiaopeng.carcontrol.helper.-$$Lambda$SoundHelper$vbRfRuyt6rLip5CHK926lg75T40
            @Override // com.xiaopeng.carcontrol.helper.SoundHelper.IFadeOutCallback
            public final void onFadeOutFinish() {
                SoundHelper.reset();
            }
        });
    }

    private static void fadeOut(IFadeOutCallback callback) {
        int i = 10;
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                break;
            }
            float f = i2 / 10.0f;
            sPlayer.setVolume(f, f);
            try {
                Thread.sleep(10L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i = i2;
        }
        sPlayer.stop();
        sPlayer.setVolume(1.0f, 1.0f);
        if (callback != null) {
            callback.onFadeOutFinish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void realPlay(String path, final boolean requestFocus, int type) {
        reset();
        if ("".equals(path)) {
            return;
        }
        AudioAttributes.Builder legacyStreamType = new AudioAttributes.Builder().setLegacyStreamType(type);
        if (3 == type) {
            legacyStreamType.setFlags(1048576);
        }
        final AudioAttributes build = legacyStreamType.build();
        sPlayer.setAudioAttributes(build);
        try {
            sPlayer.setDataSource(path);
            sPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.xiaopeng.carcontrol.helper.-$$Lambda$SoundHelper$fm2geiLaurLfAz8b8pNcER0-VkI
                @Override // android.media.MediaPlayer.OnPreparedListener
                public final void onPrepared(MediaPlayer mediaPlayer) {
                    SoundHelper.lambda$realPlay$3(requestFocus, build, mediaPlayer);
                }
            });
            sPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: com.xiaopeng.carcontrol.helper.-$$Lambda$SoundHelper$CpLn60J8pwb8dU9GE6gu97m6gUA
                @Override // android.media.MediaPlayer.OnCompletionListener
                public final void onCompletion(MediaPlayer mediaPlayer) {
                    SoundHelper.lambda$realPlay$4(requestFocus, mediaPlayer);
                }
            });
            try {
                sPlayer.prepareAsync();
            } catch (Exception e) {
                LogUtils.e(TAG, "prepareAsync", e);
            }
        } catch (Exception e2) {
            LogUtils.e(TAG, "setDataSource failed", e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$realPlay$3(final boolean requestFocus, final AudioAttributes audioAttributes, MediaPlayer mp) {
        if (requestFocus) {
            AudioFocusRequest build = new AudioFocusRequest.Builder(3).setAudioAttributes(audioAttributes).build();
            sFocusRequest = build;
            if (sAudioManager.requestAudioFocus(build) == 1) {
                sPlayer.start();
                return;
            } else {
                LogUtils.e(TAG, "request audio focus failed", false);
                return;
            }
        }
        sPlayer.start();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$realPlay$4(final boolean requestFocus, MediaPlayer mp) {
        AudioFocusRequest audioFocusRequest;
        if (!requestFocus || (audioFocusRequest = sFocusRequest) == null) {
            return;
        }
        sAudioManager.abandonAudioFocusRequest(audioFocusRequest);
        sFocusRequest = null;
    }

    public static boolean isMusicPlaying() {
        return sAudioManager.isMusicActive();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void reset() {
        sPlayer.reset();
        AudioFocusRequest audioFocusRequest = sFocusRequest;
        if (audioFocusRequest != null) {
            sAudioManager.abandonAudioFocusRequest(audioFocusRequest);
            sFocusRequest = null;
        }
    }

    private static boolean isPreviewSound(String path) {
        return PATH_BOOT_1.equals(path) || PATH_BOOT_2.equals(path) || PATH_BOOT_3.equals(path) || PATH_AVAS_SAYHI_1.equals(path) || PATH_AVAS_SAYHI_2.equals(path) || PATH_AVAS_SAYHI_3.equals(path) || PATH_AVAS_LOW_SPEED_1.equals(path) || PATH_AVAS_LOW_SPEED_2.equals(path) || PATH_AVAS_LOW_SPEED_3.equals(path) || PATH_AVAS_LOW_SPEED_4.equals(path);
    }

    public static void playNotification(String soundType) {
        LogUtils.d(TAG, "playNotification sound effect " + soundType);
        if (SystemProperties.getBoolean(SYSTEM_MEDIA_PLAYER_DISABLE, false)) {
            return;
        }
        stopNotification();
        Integer num = sSoundData.get(soundType);
        int intValue = num != null ? num.intValue() : 0;
        SoundPool soundPool = sSoundPool;
        if (soundPool == null || intValue == 0) {
            return;
        }
        sStreamId = soundPool.play(intValue, 1.0f, 1.0f, 0, 0, 1.0f);
        LogUtils.d(TAG, "playNotification sound effect sStreamId = " + sStreamId);
    }

    public static void stopNotification() {
        int i;
        LogUtils.d(TAG, "stopNotification last sound effect sStreamId = " + sStreamId);
        SoundPool soundPool = sSoundPool;
        if (soundPool == null || (i = sStreamId) == 0) {
            return;
        }
        soundPool.stop(i);
    }

    public static void pauseNotification() {
        int i;
        SoundPool soundPool = sSoundPool;
        if (soundPool == null || (i = sStreamId) == 0) {
            return;
        }
        soundPool.pause(i);
    }

    public static void resumeNotification() {
        int i;
        SoundPool soundPool = sSoundPool;
        if (soundPool == null || (i = sStreamId) == 0) {
            return;
        }
        soundPool.resume(i);
    }
}
