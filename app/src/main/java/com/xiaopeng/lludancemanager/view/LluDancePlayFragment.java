package com.xiaopeng.lludancemanager.view;

import android.media.AudioConfig.AudioConfig;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.VideoView;
import androidx.lifecycle.Observer;
import com.bumptech.glide.Glide;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.helper.SpeechHelper;
import com.xiaopeng.carcontrol.statistic.BtnEnum;
import com.xiaopeng.carcontrol.statistic.PageEnum;
import com.xiaopeng.carcontrol.statistic.StatisticUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.lludancemanager.LluDanceHelper;
import com.xiaopeng.lludancemanager.R;
import com.xiaopeng.lludancemanager.bean.LluDanceViewData;
import com.xiaopeng.lludancemanager.viewmodel.ILluDanceViewModel;
import com.xiaopeng.lludancemanager.viewmodel.LluDanceViewModel;
import com.xiaopeng.speech.SpeechClient;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XLinearLayout;
import com.xiaopeng.xui.widget.XRelativeLayout;
import com.xiaopeng.xui.widget.XTextView;

/* loaded from: classes2.dex */
public class LluDancePlayFragment extends LluBaseFragment implements View.OnClickListener {
    private static final int START_PLAY_DANCE_COUNT_DOWN_INTERVAL = 1000;
    private static final int START_PLAY_DANCE_COUNT_DOWN_IN_FEATURE = 15000;
    private static final String TAG = "LluDancePlayFragment";
    private boolean isVideoPrepared;
    private XRelativeLayout mCountDownControlContainer;
    private XTextView mCountDownSecond;
    private XTextView mDanceMusicAuthor;
    private XImageView mDanceMusicIcon;
    private XTextView mDanceMusicTitle;
    private XImageView mDancePlayFallback;
    private Observer<Boolean> mDanceStartObserver;
    private XButton mDanceStopPlay;
    private VideoView mDanceVideoView;
    private LluDanceViewData mPlayingDanceViewData;
    private CountDownTimer mStartPlayCountDownTimer;
    private XLinearLayout mStopCountDownContainerInCountDown;
    private String mTTSSpeakId;
    private XTextView mTipsText;
    private AudioConfig mAudioConfig = null;
    private boolean mOrderPlay = false;
    private boolean isInterruptByUser = false;
    private boolean isSupportLluVideo = BaseFeatureOption.getInstance().isSupportLluVideo();

    @Override // com.xiaopeng.lludancemanager.view.LluBaseFragment
    protected boolean supportVui() {
        return false;
    }

    public void setPlayingDanceViewData(LluDanceViewData playingDanceViewData) {
        this.mPlayingDanceViewData = playingDanceViewData;
    }

    public void setOrderPlay(boolean orderPlay) {
        this.mOrderPlay = orderPlay;
    }

    @Override // com.xiaopeng.lludancemanager.view.LluBaseFragment
    protected void initViewModelObserver() {
        this.mDanceStartObserver = new Observer() { // from class: com.xiaopeng.lludancemanager.view.-$$Lambda$LluDancePlayFragment$oXOIByug11rk8sizxMkwgdyr16M
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LluDancePlayFragment.this.lambda$initViewModelObserver$0$LluDancePlayFragment((Boolean) obj);
            }
        };
    }

    public /* synthetic */ void lambda$initViewModelObserver$0$LluDancePlayFragment(Boolean danceStartPlay) {
        if (danceStartPlay.booleanValue()) {
            this.mDanceViewModel.preliminaryWork();
            if (this.isSupportLluVideo) {
                this.mCountDownControlContainer.setVisibility(8);
                this.mDancePlayFallback.setVisibility(playVideo() ? 8 : 0);
                return;
            }
            return;
        }
        LogUtils.d(TAG, "llu dance playing is broken in framework, reset ui to dance list item page");
    }

    @Override // com.xiaopeng.lludancemanager.view.LluBaseFragment
    protected void initViewModel() {
        this.mDanceViewModel = (LluDanceViewModel) ViewModelManager.getInstance().getViewModelImpl(ILluDanceViewModel.class);
        setLiveDataObserver(this.mDanceViewModel.getLluDanceStartPlayData(), this.mDanceStartObserver);
    }

    @Override // com.xiaopeng.lludancemanager.view.LluBaseFragment, com.xiaopeng.carcontrol.view.fragment.VuiFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.d(TAG, "onCreate");
        this.mAudioConfig = new AudioConfig(getContext());
        this.isInterruptByUser = false;
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.VuiFragment
    protected int getRootLayoutId() {
        return R.layout.fragment_llu_dance_play_layout_root;
    }

    @Override // com.xiaopeng.lludancemanager.view.LluBaseFragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtils.d(TAG, "onViewCreated");
        VideoView videoView = (VideoView) view.findViewById(R.id.llu_activity_dance_video);
        this.mDanceVideoView = videoView;
        videoView.setAudioFocusRequest(0);
        this.mDanceMusicIcon = (XImageView) view.findViewById(R.id.llu_activity_dance_music_icon);
        this.mDanceMusicTitle = (XTextView) view.findViewById(R.id.llu_activity_dance_music_title);
        this.mDanceMusicAuthor = (XTextView) view.findViewById(R.id.llu_activity_dance_music_author);
        XButton xButton = (XButton) view.findViewById(R.id.llu_activity_dance_video_stop_play);
        this.mDanceStopPlay = xButton;
        xButton.setOnClickListener(this);
        this.mCountDownControlContainer = (XRelativeLayout) view.findViewById(R.id.llu_dance_fragment_count_down_control_container);
        this.mDancePlayFallback = (XImageView) view.findViewById(R.id.llu_activity_video_fail_fallback);
        XLinearLayout xLinearLayout = (XLinearLayout) view.findViewById(R.id.llu_play_fragment_dance_video_stop_count_down_container);
        this.mStopCountDownContainerInCountDown = xLinearLayout;
        xLinearLayout.setOnClickListener(this);
        this.mCountDownSecond = (XTextView) view.findViewById(R.id.llu_dance_play_fragment_count_down);
        this.mTipsText = (XTextView) view.findViewById(R.id.llu_dance_play_fragment_count_down_tips);
        fillDanceData();
        if (!this.mOrderPlay) {
            startCountDown();
            prepareForCountDown();
            playDanceTTS();
            return;
        }
        view.postDelayed(new Runnable() { // from class: com.xiaopeng.lludancemanager.view.-$$Lambda$LluDancePlayFragment$55M8JE9j1HT1bHa7tMMBXMBqP6I
            @Override // java.lang.Runnable
            public final void run() {
                LluDancePlayFragment.this.lambda$onViewCreated$1$LluDancePlayFragment();
            }
        }, 200L);
    }

    @Override // com.xiaopeng.lludancemanager.view.LluBaseFragment, com.xiaopeng.carcontrol.view.fragment.VuiFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        String str = TAG;
        LogUtils.d(str, "onResume");
        this.mAudioConfig.setBanVolumeChangeMode(3, 1);
        LogUtils.d(str, "onResume mAudioConfig.setBanVolumeChangeMode(AudioManager.STREAM_MUSIC, AudioConfig.BAN_VOLCONTROL_MODE_LEVEL_1)");
        resumeVideo();
        disableVoiceAssistant();
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.VuiFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        LogUtils.d(TAG, "onDestroyView");
        unRegisterLiveDataObserver();
    }

    @Override // com.xiaopeng.lludancemanager.view.LluBaseFragment, com.xiaopeng.carcontrol.view.fragment.VuiFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        suspendVideo();
        String str = TAG;
        LogUtils.d(str, "onPause");
        this.mAudioConfig.setBanVolumeChangeMode(3, 0);
        LogUtils.d(str, "onPause mAudioConfig.setBanVolumeChangeMode(AudioManager.STREAM_MUSIC, AudioConfig.BAN_VOLCONTROL_MODE_NO_ACTIVE);");
        breakDanceTTS();
        stopCountDown();
        this.mDanceViewModel.stopPlayLlu();
        enableVoiceAssistant();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.mDanceVideoView.stopPlayback();
        LogUtils.d(TAG, "onDestroy");
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.llu_activity_dance_video_stop_play) {
            this.isInterruptByUser = true;
            this.mDanceViewModel.stopPlayLlu();
            exitPlayFragment();
        } else if (id == R.id.llu_play_fragment_dance_video_stop_count_down_container) {
            stopCountDown();
            exitPlayFragment();
        }
    }

    public void stopDanceByMeditationMode() {
        LogUtils.i(TAG, "stop llu play by meditation.", false);
        stopCountDown();
        if (this.mDanceViewModel != null) {
            this.mDanceViewModel.stopPlayLlu();
        }
        if (isAdded()) {
            exitPlayFragment();
        }
    }

    private void prepareForCountDown() {
        this.mTipsText.setVisibility(0);
        this.mTipsText.setText(R.string.llu_dance_ready_to_show_prompt);
    }

    private void playDanceTTS() {
        this.mTTSSpeakId = SpeechHelper.getInstance().speak(getString(R.string.llu_dance_ready_to_show_prompt));
    }

    private void breakDanceTTS() {
        if (TextUtils.isEmpty(this.mTTSSpeakId)) {
            return;
        }
        SpeechHelper.getInstance().stop(this.mTTSSpeakId);
    }

    private void stopCountDown() {
        if (this.mStartPlayCountDownTimer != null) {
            Log.d(TAG, "mStartPlayCountDownTimer cancel");
            this.mStartPlayCountDownTimer.cancel();
        }
    }

    private void startCountDown() {
        if (this.mStartPlayCountDownTimer == null) {
            this.mStartPlayCountDownTimer = new CountDownTimer(15000L, 1000L) { // from class: com.xiaopeng.lludancemanager.view.LluDancePlayFragment.1
                @Override // android.os.CountDownTimer
                public void onTick(long millisUntilFinished) {
                    String valueOf = String.valueOf((millisUntilFinished / 1000) + 1);
                    LluDancePlayFragment.this.mCountDownSecond.setText(valueOf);
                    LogUtils.d(LluDancePlayFragment.TAG, "Karl log open time optimize start play count down on tick = " + valueOf);
                }

                @Override // android.os.CountDownTimer
                public void onFinish() {
                    LluDancePlayFragment.this.lambda$onViewCreated$1$LluDancePlayFragment();
                }
            };
        }
        LogUtils.d(TAG, "Karl log open time optimize start play mStartPlayCountDownTimer");
        this.mStartPlayCountDownTimer.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: executeDancePlay */
    public void lambda$onViewCreated$1$LluDancePlayFragment() {
        String str = TAG;
        LogUtils.d(str, "Karl log open time optimize start play dance id = " + this.mPlayingDanceViewData.getId() + " count down timer finished，is Interrupt by user: " + this.isInterruptByUser);
        if (this.isInterruptByUser) {
            return;
        }
        if (LluDanceHelper.checkLightDanceAvailable()) {
            if (this.isSupportLluVideo) {
                this.mTipsText.setVisibility(8);
            } else {
                this.mTipsText.setText(R.string.llu_dance_showing_prompt);
            }
            this.mCountDownSecond.setVisibility(8);
            int startPlay = this.mDanceViewModel.startPlay(this.mPlayingDanceViewData);
            LogUtils.d(str, "Karl log open time optimize start play result = " + startPlay);
            if (startPlay != 0) {
                exitPlayFragment();
                return;
            }
            return;
        }
        exitPlayFragment();
    }

    private void unRegisterLiveDataObserver() {
        removeLiveDataObserver(this.mDanceViewModel.getLluDanceStartPlayData(), this.mDanceStartObserver);
    }

    private void fillDanceData() {
        if (this.mPlayingDanceViewData != null) {
            Glide.with(this).load(this.mPlayingDanceViewData.getImageThumbnailUrl()).into(this.mDanceMusicIcon);
            prepareVideo(this.mPlayingDanceViewData.getVideoPath());
            this.mDanceMusicTitle.setText(this.mPlayingDanceViewData.getTitle());
            this.mDanceMusicAuthor.setText(this.mPlayingDanceViewData.getAuthor());
            StatisticUtils.sendStatistic(PageEnum.LAMP_PAGE, BtnEnum.LAMP_PAGE_DANCE_PLAY_BTN, this.mPlayingDanceViewData.getId() + "_" + this.mPlayingDanceViewData.getTitle());
        }
    }

    private void exitPlayFragment() {
        getParentFragmentManager().beginTransaction().remove(this).commitNow();
    }

    private void disableVoiceAssistant() {
        this.mDanceViewModel.disableWakeup();
        if (SpeechClient.instance().getSpeechState().isDMStarted()) {
            LogUtils.d(TAG, "Karl log now speech active , break it");
            SpeechClient.instance().getWakeupEngine().stopDialog();
        }
    }

    private void enableVoiceAssistant() {
        this.mDanceViewModel.enableWakeup();
    }

    private void prepareVideo(String videoPath) {
        if (!this.isSupportLluVideo) {
            LogUtils.i(TAG, "Not support llu video in prepareVideo");
            return;
        }
        LogUtils.d(TAG, "prepare video path:" + videoPath);
        this.mDanceVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.xiaopeng.lludancemanager.view.-$$Lambda$LluDancePlayFragment$tvIvufY-JUNtRVAXPPsE4LlJggw
            @Override // android.media.MediaPlayer.OnPreparedListener
            public final void onPrepared(MediaPlayer mediaPlayer) {
                LluDancePlayFragment.this.lambda$prepareVideo$2$LluDancePlayFragment(mediaPlayer);
            }
        });
        this.mDanceVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() { // from class: com.xiaopeng.lludancemanager.view.-$$Lambda$LluDancePlayFragment$7oMOrjljHqpgInyi9mFeFCKpwlE
            @Override // android.media.MediaPlayer.OnErrorListener
            public final boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                return LluDancePlayFragment.this.lambda$prepareVideo$3$LluDancePlayFragment(mediaPlayer, i, i2);
            }
        });
        this.mDanceVideoView.setVideoPath(videoPath);
    }

    public /* synthetic */ void lambda$prepareVideo$2$LluDancePlayFragment(MediaPlayer mp) {
        this.isVideoPrepared = true;
    }

    public /* synthetic */ boolean lambda$prepareVideo$3$LluDancePlayFragment(MediaPlayer mp, int what, int extra) {
        LogUtils.d(TAG, "video error... Error info: what - " + what + "     extra - " + extra);
        this.mDanceVideoView.setVisibility(8);
        this.mDanceVideoView.stopPlayback();
        this.isVideoPrepared = false;
        return true;
    }

    private boolean playVideo() {
        if (!this.isSupportLluVideo) {
            LogUtils.i(TAG, "Not support llu video in playVideo");
            return false;
        }
        String str = TAG;
        LogUtils.d(str, "start to play video.");
        if (this.isVideoPrepared) {
            if (this.mDanceVideoView.isPlaying()) {
                this.mDanceVideoView.seekTo(0);
                LogUtils.d(str, "seek to start.");
            }
            this.mDanceVideoView.start();
        } else {
            this.mDanceVideoView.start();
            LogUtils.d(str, "no prepared, video will play later by VideoView targetState!");
        }
        return this.isVideoPrepared;
    }

    private void suspendVideo() {
        if (!this.isSupportLluVideo) {
            LogUtils.i(TAG, "Not support llu video in suspendVideo");
        } else if (this.isVideoPrepared) {
            this.mDanceVideoView.suspend();
        } else {
            LogUtils.d(TAG, "no prepared,no suspend");
        }
    }

    private void resumeVideo() {
        if (!this.isSupportLluVideo) {
            LogUtils.i(TAG, "Not support llu video in resumeVideo");
        } else if (this.isVideoPrepared) {
            this.mDanceVideoView.resume();
        } else {
            LogUtils.d(TAG, "no prepared,no resume");
        }
    }
}
