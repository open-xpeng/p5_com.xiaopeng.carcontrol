package com.xiaopeng.xpmeditation.viewModel.plus;

import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.speech.speechwidget.ListWidget;
import com.xiaopeng.xpmeditation.viewModel.plus.MediaPlayerController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public class MediaPlayerController {
    private static final String TAG = "MediaPlayerController";
    private final AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener;
    private AudioManager mAudioManager;
    AudioFocusRequest mFocusRequest;
    AudioAttributes mPlaybackAttributes;
    private MediaPlayer mPlayer = null;
    String mDataSource = null;
    List<AudioStatusListener> mListeners = new ArrayList();
    private boolean mLoop = true;

    /* loaded from: classes2.dex */
    public interface AudioStatusListener {
        void onStatusChange(int status);
    }

    public MediaPlayerController() {
        $$Lambda$MediaPlayerController$SB6nVIbYMeFWadP8s64wP6eQUD4 __lambda_mediaplayercontroller_sb6nvibymefwadp8s64wp6equd4 = new AudioManager.OnAudioFocusChangeListener() { // from class: com.xiaopeng.xpmeditation.viewModel.plus.-$$Lambda$MediaPlayerController$SB6nVIbYMeFWadP8s64wP6eQUD4
            @Override // android.media.AudioManager.OnAudioFocusChangeListener
            public final void onAudioFocusChange(int i) {
                LogUtils.d(MediaPlayerController.TAG, "audio focus change, focus change type: " + i, false);
            }
        };
        this.mAudioFocusChangeListener = __lambda_mediaplayercontroller_sb6nvibymefwadp8s64wp6equd4;
        this.mAudioManager = (AudioManager) App.getInstance().getApplicationContext().getSystemService(ListWidget.EXTRA_TYPE_AUDIO);
        this.mPlaybackAttributes = new AudioAttributes.Builder().setUsage(1).setContentType(2).build();
        this.mFocusRequest = new AudioFocusRequest.Builder(1).setAudioAttributes(this.mPlaybackAttributes).setAcceptsDelayedFocusGain(true).setOnAudioFocusChangeListener(__lambda_mediaplayercontroller_sb6nvibymefwadp8s64wp6equd4).build();
    }

    public void setIsLoop(boolean isLoop) {
        this.mLoop = isLoop;
        this.mPlayer.setLooping(isLoop);
    }

    public void initPlayer() {
        LogUtils.i(TAG, "initPlayer");
        MediaPlayer mediaPlayer = new MediaPlayer();
        this.mPlayer = mediaPlayer;
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() { // from class: com.xiaopeng.xpmeditation.viewModel.plus.-$$Lambda$MediaPlayerController$0wjIAdnpr7gHwymi98OFI7DNeNw
            @Override // android.media.MediaPlayer.OnErrorListener
            public final boolean onError(MediaPlayer mediaPlayer2, int i, int i2) {
                return MediaPlayerController.lambda$initPlayer$1(mediaPlayer2, i, i2);
            }
        });
        this.mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.xiaopeng.xpmeditation.viewModel.plus.-$$Lambda$MediaPlayerController$hba_vLvYDMB1S8FUXZOm5MlXYGY
            @Override // android.media.MediaPlayer.OnPreparedListener
            public final void onPrepared(MediaPlayer mediaPlayer2) {
                MediaPlayerController.lambda$initPlayer$2(mediaPlayer2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean lambda$initPlayer$1(MediaPlayer mp, int what, int extra) {
        LogUtils.w(TAG, "mediaPlayer on error what: " + what + ", extra: " + extra);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$initPlayer$2(MediaPlayer mp) {
        if (mp != null) {
            mp.start();
        }
    }

    private boolean requestFocus() {
        int requestAudioFocus = this.mAudioManager.requestAudioFocus(this.mFocusRequest);
        LogUtils.i(TAG, "request audio focus: " + requestAudioFocus);
        return requestAudioFocus != 0;
    }

    public boolean isPlaying() {
        MediaPlayer mediaPlayer = this.mPlayer;
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }

    public void play(String dataSource) {
        play(dataSource, true, true);
    }

    public void play(String dataSource, boolean isNotify, boolean isLoop) {
        this.mDataSource = dataSource;
        playInner(dataSource, isNotify, isLoop);
    }

    public void play() {
        playInner(this.mDataSource);
    }

    public void playOrPause() {
        if (isPlaying()) {
            pause();
        } else {
            play();
        }
    }

    private void playInner(String dataSource) {
        playInner(dataSource, true, true);
    }

    private void playInner(String dataSource, boolean isNotify, boolean isLoop) {
        LogUtils.i(TAG, "start to play media source: " + dataSource);
        if (dataSource == null) {
            LogUtils.w(TAG, "data source is empty!");
            return;
        }
        if (this.mPlayer == null) {
            initPlayer();
        } else {
            resetPlayer();
        }
        this.mPlayer.setLooping(isLoop);
        try {
            this.mPlayer.setDataSource(dataSource);
        } catch (IOException e) {
            LogUtils.w(TAG, "set data source occurs an exception,play failure");
            e.printStackTrace();
            this.mPlayer.reset();
        }
        this.mPlayer.prepareAsync();
        if (isNotify) {
            notifyAudioStatusChanged(2);
        }
    }

    private void resume() {
        StringBuilder append = new StringBuilder().append("resume, mPlayer ");
        MediaPlayer mediaPlayer = this.mPlayer;
        LogUtils.i(TAG, append.append(mediaPlayer != null && mediaPlayer.isPlaying()).toString());
        MediaPlayer mediaPlayer2 = this.mPlayer;
        if (mediaPlayer2 != null) {
            if (mediaPlayer2.isPlaying()) {
                return;
            }
            this.mPlayer.start();
            notifyAudioStatusChanged(2);
            return;
        }
        play();
    }

    public void pause() {
        pauseInner();
        AudioManager audioManager = this.mAudioManager;
        if (audioManager != null) {
            audioManager.abandonAudioFocusRequest(this.mFocusRequest);
        }
    }

    private void pauseInner() {
        LogUtils.i(TAG, "pause");
        MediaPlayer mediaPlayer = this.mPlayer;
        if (mediaPlayer == null || !mediaPlayer.isPlaying()) {
            return;
        }
        this.mPlayer.pause();
        notifyAudioStatusChanged(3);
    }

    public void release() {
        LogUtils.i(TAG, "release: mPlayer is null " + (this.mPlayer == null));
        if (this.mPlayer == null) {
            return;
        }
        pauseInner();
        if (this.mPlayer.isPlaying()) {
            this.mPlayer.stop();
        }
        this.mPlayer.release();
        this.mPlayer = null;
        this.mAudioManager.abandonAudioFocusRequest(this.mFocusRequest);
        notifyAudioStatusChanged(-1);
        this.mDataSource = null;
        this.mLoop = false;
    }

    private void resetPlayer() {
        if (this.mPlayer.isPlaying()) {
            this.mPlayer.stop();
        }
        this.mPlayer.reset();
    }

    public void addListener(AudioStatusListener l) {
        if (this.mListeners.contains(l)) {
            return;
        }
        this.mListeners.add(l);
    }

    public void removeListener(AudioStatusListener l) {
        if (this.mListeners.contains(l)) {
            return;
        }
        this.mListeners.remove(l);
    }

    private void notifyAudioStatusChanged(final int status) {
        this.mListeners.forEach(new Consumer() { // from class: com.xiaopeng.xpmeditation.viewModel.plus.-$$Lambda$MediaPlayerController$qQHJ_bfx9GyZB5i46a-4vvYv5B8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((MediaPlayerController.AudioStatusListener) obj).onStatusChange(status);
            }
        });
    }
}
