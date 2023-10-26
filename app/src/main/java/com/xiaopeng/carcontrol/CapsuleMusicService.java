package com.xiaopeng.carcontrol;

import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.text.TextUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.speech.speechwidget.ListWidget;
import java.io.IOException;

/* loaded from: classes.dex */
public class CapsuleMusicService extends Service {
    public static final int ALARM_REQUEST_CODE = 657920;
    public static final int COMMAND_PAUSE = 1;
    public static final int COMMAND_PLAY = 0;
    public static final int COMMAND_RESUME = 2;
    public static final String EXTRA_LOOP_MODE = "extra_loop_mode";
    public static final String EXTRA_MEDIA_COMMAND = "extra_media_command";
    public static final String EXTRA_MEDIA_SCREEN_ID = "extra_media_screen_id";
    public static final String EXTRA_MEDIA_SOURCE = "extra_media_source";
    public static final String EXTRA_MEDIA_TYPE = "extra_media_type";
    public static final int LOOP_MODE_ALWAYS = 0;
    public static final int LOOP_MODE_CUSTOM = 2;
    public static final int LOOP_MODE_ONCE = 1;
    public static final int MEDIA_ALARM_TYPE = 1;
    public static final int MEDIA_MUSIC_SINGLE_TYPE = 2;
    public static final int MEDIA_MUSIC_TYPE = 0;
    private static final String TAG = "CapsuleMusicService";
    private String mAlarmMediaSource;
    private MediaPlayer mAlarmPlayer;
    private AudioManager mAudioManager;
    private String mChoseMediaSource;
    private CapsuleMusicListener mListener;
    private MediaPlayer mPlayer;
    private final int mDefaultCmd = -1;
    private final int mDefaultType = -1;
    private final CapsuleMusicServiceBinder mBinder = new CapsuleMusicServiceBinder();
    int mDuckVolume = 0;
    private int mMediaType = -1;
    private int mLoopMode = 0;
    private final AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() { // from class: com.xiaopeng.carcontrol.-$$Lambda$CapsuleMusicService$4MIbH1Js9KDGquaKTkrbk7kLExE
        @Override // android.media.AudioManager.OnAudioFocusChangeListener
        public final void onAudioFocusChange(int i) {
            CapsuleMusicService.this.lambda$new$0$CapsuleMusicService(i);
        }
    };
    private int mPlayerType = -1;
    private int mLoopCount = 1;

    /* loaded from: classes.dex */
    public interface CapsuleMusicListener {
        default void onCompletion() {
        }

        default void onPauseMusic() {
        }

        default void onStartMusic() {
        }

        void pauseByLossAudioFocus();

        void resumeByGainAudioFocus();

        void showAlarmDialog();
    }

    public /* synthetic */ void lambda$new$0$CapsuleMusicService(int focusChange) {
        LogUtils.d(TAG, "audio focus change...  focus change type: " + focusChange, false);
        if (focusChange == -3) {
            downVolume();
        } else if (focusChange == -2 || focusChange == -1) {
            pauseByLossFocus();
        } else if (focusChange != 1) {
        } else {
            resumeVolume();
            resumeByGainFocus();
        }
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.i(TAG, "onStartCommand " + intent);
        if (intent == null) {
            return super.onStartCommand(intent, flags, startId);
        }
        int intExtra = intent.getIntExtra(EXTRA_MEDIA_COMMAND, -1);
        this.mMediaType = intent.getIntExtra(EXTRA_MEDIA_TYPE, -1);
        String stringExtra = intent.getStringExtra(EXTRA_MEDIA_SOURCE);
        this.mLoopMode = intent.getIntExtra(EXTRA_LOOP_MODE, 0);
        if (this.mMediaType == 2) {
            this.mLoopMode = 1;
        }
        if (this.mLoopMode < 0) {
            LogUtils.w(TAG, "Loop mode not support:" + this.mLoopMode);
            this.mLoopMode = 0;
        }
        LogUtils.d(TAG, "mediaCmd: " + intExtra + ", mediaType: " + this.mMediaType + ", mediaSource: " + stringExtra + ", loopMode:" + this.mLoopMode);
        LogUtils.i(TAG, "mChoseMediaSource:" + this.mChoseMediaSource);
        int i = this.mMediaType;
        if (i == 0) {
            this.mChoseMediaSource = stringExtra;
            this.mPlayerType = i;
            if (intExtra == 0) {
                playMusic();
            } else if (intExtra == 2) {
                resumeMusic();
            } else {
                pauseMusic();
            }
        } else if (i == 2) {
            this.mChoseMediaSource = stringExtra;
            this.mPlayerType = i;
            if (intExtra == 0) {
                playMusic();
            } else if (intExtra == 2) {
                resumeMusic();
            } else {
                pauseMusic();
            }
        } else {
            this.mAlarmMediaSource = stringExtra;
            if (intExtra == 0) {
                triggerAlarm();
                this.mPlayerType = this.mMediaType;
            } else {
                stopAlarm();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mAudioManager = (AudioManager) getSystemService(ListWidget.EXTRA_TYPE_AUDIO);
    }

    @Override // android.app.Service
    public void onDestroy() {
        stopAndRelease();
        super.onDestroy();
    }

    private void initMediaPlayer() {
        this.mPlayer = new MediaPlayer();
        this.mPlayer.setAudioAttributes(new AudioAttributes.Builder().setLegacyStreamType(3).setFlags(1048576).build());
        this.mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() { // from class: com.xiaopeng.carcontrol.-$$Lambda$CapsuleMusicService$tw5EBxoiZYfTG1MtyLBlTLYghi0
            @Override // android.media.MediaPlayer.OnErrorListener
            public final boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                return CapsuleMusicService.lambda$initMediaPlayer$1(mediaPlayer, i, i2);
            }
        });
        this.mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.xiaopeng.carcontrol.-$$Lambda$CapsuleMusicService$n8UprEyR32jmVj8plJ8axGVWQ3M
            @Override // android.media.MediaPlayer.OnPreparedListener
            public final void onPrepared(MediaPlayer mediaPlayer) {
                CapsuleMusicService.this.lambda$initMediaPlayer$2$CapsuleMusicService(mediaPlayer);
            }
        });
        this.mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: com.xiaopeng.carcontrol.-$$Lambda$CapsuleMusicService$zClQ5QOirwzK1nLcmPe9lq6zFjo
            @Override // android.media.MediaPlayer.OnCompletionListener
            public final void onCompletion(MediaPlayer mediaPlayer) {
                CapsuleMusicService.this.lambda$initMediaPlayer$3$CapsuleMusicService(mediaPlayer);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean lambda$initMediaPlayer$1(MediaPlayer mp, int what, int extra) {
        LogUtils.w(TAG, "mediaPlayer on error what: " + what + ", extra: " + extra);
        return false;
    }

    public /* synthetic */ void lambda$initMediaPlayer$2$CapsuleMusicService(MediaPlayer mp) {
        mp.start();
        LogUtils.d(TAG, " onStartMusic");
        if (this.mListener != null) {
            LogUtils.d(TAG, " onStartMusic listener not null");
            this.mListener.onStartMusic();
        }
        requestMusicFocus();
    }

    public /* synthetic */ void lambda$initMediaPlayer$3$CapsuleMusicService(MediaPlayer mediaPlayer) {
        int i = this.mLoopMode;
        if (i >= 2 && this.mLoopCount < i) {
            LogUtils.i(TAG, "onCompletion, Replay, currentCount:" + this.mLoopCount + ", loopMode:" + this.mLoopMode);
            this.mLoopCount++;
            mediaPlayer.seekTo(0);
            mediaPlayer.start();
            return;
        }
        LogUtils.i(TAG, "onCompletion, currentCount:" + this.mLoopCount + ", loopMode:" + this.mLoopMode);
        if (this.mListener != null) {
            LogUtils.d(TAG, " onCompletion listener not null");
            this.mListener.onCompletion();
        }
    }

    private void resetMediaPlayer() {
        if (this.mPlayer.isPlaying()) {
            this.mPlayer.stop();
        }
        this.mPlayer.reset();
    }

    private void initAlarmPlayer() {
        this.mAlarmPlayer = new MediaPlayer();
        this.mAlarmPlayer.setAudioAttributes(new AudioAttributes.Builder().setLegacyStreamType(1).build());
        this.mAlarmPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() { // from class: com.xiaopeng.carcontrol.-$$Lambda$CapsuleMusicService$Lwn7m2sxJN6r2JdfVOudteWg4Rg
            @Override // android.media.MediaPlayer.OnErrorListener
            public final boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                return CapsuleMusicService.lambda$initAlarmPlayer$4(mediaPlayer, i, i2);
            }
        });
        this.mAlarmPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.xiaopeng.carcontrol.-$$Lambda$CapsuleMusicService$UZ2T3A_dIzS5fIPMR9CFNdksXl8
            @Override // android.media.MediaPlayer.OnPreparedListener
            public final void onPrepared(MediaPlayer mediaPlayer) {
                CapsuleMusicService.this.lambda$initAlarmPlayer$5$CapsuleMusicService(mediaPlayer);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean lambda$initAlarmPlayer$4(MediaPlayer mp, int what, int extra) {
        LogUtils.w(TAG, "mediaPlayer on error what: " + what + ", extra: " + extra);
        return false;
    }

    public /* synthetic */ void lambda$initAlarmPlayer$5$CapsuleMusicService(MediaPlayer mp) {
        mp.start();
        requestAlarmFocus();
    }

    private void playMusic() {
        if (!TextUtils.isEmpty(this.mChoseMediaSource)) {
            play(this.mChoseMediaSource);
        } else {
            LogUtils.w(TAG, "mChoseMediaSource is empty!");
        }
    }

    private void play(String dataSource) {
        LogUtils.d(TAG, " play");
        this.mLoopCount = 1;
        if (this.mPlayer == null) {
            LogUtils.d(TAG, " initMediaPlayer");
            initMediaPlayer();
        } else {
            resetMediaPlayer();
        }
        this.mPlayer.setLooping(this.mLoopMode == 0);
        try {
            this.mPlayer.setDataSource(dataSource);
            this.mPlayer.prepareAsync();
        } catch (IOException e) {
            LogUtils.w(TAG, "set data source occurs an exception,play failure");
            e.printStackTrace();
            this.mPlayer.reset();
        }
    }

    private void resumeMusic() {
        MediaPlayer mediaPlayer = this.mPlayer;
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                return;
            }
            this.mPlayer.start();
            requestMusicFocus();
            return;
        }
        playMusic();
    }

    private void pauseMusic() {
        MediaPlayer mediaPlayer = this.mPlayer;
        if (mediaPlayer == null || !mediaPlayer.isPlaying()) {
            return;
        }
        this.mPlayer.pause();
        abandonAudioFocus();
        CapsuleMusicListener capsuleMusicListener = this.mListener;
        if (capsuleMusicListener != null) {
            capsuleMusicListener.onPauseMusic();
        }
    }

    public void stopAndRelease() {
        LogUtils.i(TAG, "stop and release media.");
        MediaPlayer mediaPlayer = this.mPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            this.mPlayer.release();
            this.mPlayer = null;
        }
        stopAlarm();
    }

    private void triggerAlarm() {
        CapsuleMusicListener capsuleMusicListener = this.mListener;
        if (capsuleMusicListener != null) {
            capsuleMusicListener.showAlarmDialog();
        }
        if (!TextUtils.isEmpty(this.mAlarmMediaSource)) {
            pauseMusic();
            initAlarmPlayer();
            this.mAlarmPlayer.setLooping(true);
            try {
                this.mAlarmPlayer.setDataSource(this.mAlarmMediaSource);
                this.mAlarmPlayer.prepareAsync();
                return;
            } catch (IOException e) {
                LogUtils.w(TAG, "set data source occurs an exception,play failure");
                e.printStackTrace();
                this.mAlarmPlayer.reset();
                return;
            }
        }
        LogUtils.w(TAG, "mAlarmMediaSource is empty!");
    }

    private void stopAlarm() {
        MediaPlayer mediaPlayer = this.mAlarmPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            this.mAlarmPlayer.release();
            this.mAlarmPlayer = null;
        }
        abandonAudioFocus();
    }

    private void pauseByLossFocus() {
        LogUtils.i(TAG, "Pause music due to loss audio focus! mediaType: " + this.mPlayerType, false);
        int i = this.mPlayerType;
        if (i == -1) {
            return;
        }
        if (i == 0) {
            MediaPlayer mediaPlayer = this.mPlayer;
            if (mediaPlayer == null || !mediaPlayer.isPlaying()) {
                return;
            }
            this.mPlayer.pause();
            CapsuleMusicListener capsuleMusicListener = this.mListener;
            if (capsuleMusicListener != null) {
                capsuleMusicListener.pauseByLossAudioFocus();
                return;
            }
            return;
        }
        MediaPlayer mediaPlayer2 = this.mAlarmPlayer;
        if (mediaPlayer2 == null || !mediaPlayer2.isPlaying()) {
            return;
        }
        this.mAlarmPlayer.pause();
    }

    private void resumeByGainFocus() {
        LogUtils.i(TAG, "Resume music due to gain audio focus again! mediaType: " + this.mPlayerType, false);
        int i = this.mPlayerType;
        if (i == -1) {
            return;
        }
        if (i == 0) {
            MediaPlayer mediaPlayer = this.mPlayer;
            if (mediaPlayer == null || mediaPlayer.isPlaying()) {
                return;
            }
            this.mPlayer.start();
            CapsuleMusicListener capsuleMusicListener = this.mListener;
            if (capsuleMusicListener != null) {
                capsuleMusicListener.resumeByGainAudioFocus();
                return;
            }
            return;
        }
        MediaPlayer mediaPlayer2 = this.mAlarmPlayer;
        if (mediaPlayer2 == null || mediaPlayer2.isPlaying()) {
            return;
        }
        this.mAlarmPlayer.start();
    }

    private void resumeVolume() {
        LogUtils.d(TAG, "resumeVolume... ");
        MediaPlayer mediaPlayer = this.mPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(1.0f, 1.0f);
        }
        MediaPlayer mediaPlayer2 = this.mAlarmPlayer;
        if (mediaPlayer2 != null) {
            mediaPlayer2.setVolume(1.0f, 1.0f);
        }
    }

    private void downVolume() {
        LogUtils.d(TAG, "downVolume... ");
        MediaPlayer mediaPlayer = this.mPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(0.5f, 0.5f);
        }
        MediaPlayer mediaPlayer2 = this.mAlarmPlayer;
        if (mediaPlayer2 != null) {
            mediaPlayer2.setVolume(0.5f, 0.5f);
        }
    }

    private void requestAlarmFocus() {
        this.mAudioManager.requestAudioFocus(this.mAudioFocusChangeListener, 4, 1);
    }

    private void requestMusicFocus() {
        this.mAudioManager.requestAudioFocus(this.mAudioFocusChangeListener, 3, 1);
    }

    private void abandonAudioFocus() {
        AudioManager audioManager = this.mAudioManager;
        if (audioManager != null) {
            audioManager.abandonAudioFocus(this.mAudioFocusChangeListener);
        }
        resumeVolume();
    }

    public void setMusicListener(CapsuleMusicListener listener) {
        this.mListener = listener;
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        stopAndRelease();
        return super.onUnbind(intent);
    }

    /* loaded from: classes.dex */
    public class CapsuleMusicServiceBinder extends Binder {
        public CapsuleMusicServiceBinder() {
        }

        public CapsuleMusicService getService() {
            return CapsuleMusicService.this;
        }
    }
}
