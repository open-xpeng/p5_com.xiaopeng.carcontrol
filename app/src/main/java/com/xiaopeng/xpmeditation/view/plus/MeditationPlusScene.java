package com.xiaopeng.xpmeditation.view.plus;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.helper.SpeechHelper;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorViewModel;
import com.xiaopeng.carcontrol.viewmodel.hvac.AcHeatNatureMode;
import com.xiaopeng.carcontrol.viewmodel.hvac.HvacCirculationMode;
import com.xiaopeng.carcontrol.viewmodel.hvac.HvacViewModel;
import com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel;
import com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel;
import com.xiaopeng.carcontrol.viewmodel.lamp.LampMode;
import com.xiaopeng.carcontrol.viewmodel.lamp.LampViewModel;
import com.xiaopeng.carcontrol.viewmodel.light.AtlViewModel;
import com.xiaopeng.carcontrol.viewmodel.light.IAtlViewModel;
import com.xiaopeng.carcontrol.viewmodel.scenario.IScenarioViewModel;
import com.xiaopeng.carcontrol.viewmodel.scenario.ScenarioMode;
import com.xiaopeng.carcontrol.viewmodel.scenario.ScenarioViewModel;
import com.xiaopeng.carcontrol.viewmodel.sfs.ISfsViewModel;
import com.xiaopeng.carcontrol.viewmodel.sfs.SfsViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.GearLevel;
import com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.VcuViewModel;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.lib.utils.view.UIUtils;
import com.xiaopeng.lludancemanager.util.ResUtil;
import com.xiaopeng.xpmeditation.model.MeditationItemBeanPlus;
import com.xiaopeng.xpmeditation.util.XpCountDownTimer;
import com.xiaopeng.xpmeditation.view.BaseMeditationScene;
import com.xiaopeng.xpmeditation.view.adapter.BaseRecyclerViewAdapter;
import com.xiaopeng.xpmeditation.view.plus.MeditationPlusPlayView;
import com.xiaopeng.xpmeditation.viewModel.IMeditationViewModel;
import com.xiaopeng.xpmeditation.viewModel.plus.MeditationPlusViewModel;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.widget.XImageButton;
import com.xiaopeng.xui.widget.XTextView;
import com.xiaopeng.xvs.xid.utils.ResourceUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class MeditationPlusScene extends BaseMeditationScene {
    private static final long COUNT_DOWN_INTERVAL = 1000;
    private static final boolean DEBUG = false;
    private static final String DEFAULT_MASSAGER_EFFECT = "wave";
    private static final int DEFAULT_RHY_EFFECT = 0;
    private static final long IMMERSE_CHECK_INTERVAL = 1000;
    private static final long IMMERSE_COUNT_DOWN_TIME = 15000;
    private static final long MEDITATION_COUNT_DOWN_TIME = 900000;
    private static final int MSG_IMMERSE_CHECK = 1;
    private static final int MSG_IMMERSE_ENTER = 0;
    private static final String START_MUSIC_PATH = "/system/media/audio/xiaopeng/cdu/wav/CDU_relax_start.wav";
    private static final String START_VIDEO_PATH = "/system/media/video/xiaopeng/cdu/mp4/CDU_relax_start.mp4";
    private static final String TAG = "MeditationPlusScene";
    private boolean isSupportSfs;
    private boolean isVideoPrepared;
    private AtlViewModel mAtlVm;
    private XImageButton mCloseBtn;
    private XDialog mDialog;
    private HvacViewModel mHvacViewModel;
    final Handler mImmerseHandler;
    private boolean mIsShowPlay;
    private LampViewModel mLampViewModel;
    private int mLastPsnTiltPos;
    private List<String> mMassagerEffectIDs;
    private XpCountDownTimer mMeditationCountDownTimer;
    private ViewStub mPlayStub;
    private MeditationPlusPlayView mPlayView;
    private XTextView mPrepareTxt;
    private int[] mRhythmModes;
    private ScenarioViewModel mScenarioViewModel;
    private SeatViewModel mSeatViewModel;
    private SfsViewModel mSfsViewModel;
    private VideoView mStartVideoView;
    private XTextView mTitleTv;
    private VcuViewModel mVcuViewModel;
    private ViewGroup mView;
    private MeditationPlusViewModel mViewModel;
    private WindowDoorViewModel mWindowDoorViewModel;

    public MeditationPlusScene(View parent, LifecycleOwner owner) {
        super(parent, owner);
        this.mMassagerEffectIDs = new ArrayList();
        this.mRhythmModes = new int[]{0, 1, 2};
        this.isVideoPrepared = false;
        this.mMeditationCountDownTimer = new XpCountDownTimer(MEDITATION_COUNT_DOWN_TIME, 1000L, "meditation") { // from class: com.xiaopeng.xpmeditation.view.plus.MeditationPlusScene.1
            @Override // com.xiaopeng.xpmeditation.util.XpCountDownTimer
            public void onCancel() {
            }

            @Override // com.xiaopeng.xpmeditation.util.XpCountDownTimer
            public void onTick(long millisUntilFinished) {
                if (MeditationPlusScene.this.mPlayView == null || !MeditationPlusScene.this.mIsShowPlay) {
                    return;
                }
                MeditationPlusScene.this.mPlayView.refreshCountDownTime(millisUntilFinished);
            }

            @Override // com.xiaopeng.xpmeditation.util.XpCountDownTimer
            public void onFinish() {
                LogUtils.i(MeditationPlusScene.TAG, "meditation time up");
                MeditationPlusScene.this.pauseMeditationInScene();
                MeditationPlusScene.this.showExitConfirmDialog(false);
            }
        };
        this.mImmerseHandler = new Handler() { // from class: com.xiaopeng.xpmeditation.view.plus.MeditationPlusScene.2
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                int i = msg.what;
                if (i != 0) {
                    if (i == 1 && !MeditationPlusScene.this.checkIfStartImmerseCountDown()) {
                        sendEmptyMessageDelayed(1, 1000L);
                        return;
                    }
                    return;
                }
                MeditationPlusScene.this.enterImmerse();
            }
        };
    }

    protected void initViewModels() {
        this.mScenarioViewModel = (ScenarioViewModel) ViewModelManager.getInstance().getViewModelImpl(IScenarioViewModel.class);
        this.mHvacViewModel = (HvacViewModel) ViewModelManager.getInstance().getViewModelImpl(IHvacViewModel.class);
        this.mSfsViewModel = (SfsViewModel) ViewModelManager.getInstance().getViewModelImpl(ISfsViewModel.class);
        this.mVcuViewModel = (VcuViewModel) ViewModelManager.getInstance().getViewModelImpl(IVcuViewModel.class);
        this.isSupportSfs = CarBaseConfig.getInstance().isSupportSfs();
        this.mWindowDoorViewModel = (WindowDoorViewModel) ViewModelManager.getInstance().getViewModelImpl(IWindowDoorViewModel.class);
        this.mSeatViewModel = (SeatViewModel) ViewModelManager.getInstance().getViewModelImpl(ISeatViewModel.class);
        this.mLampViewModel = (LampViewModel) ViewModelManager.getInstance().getViewModelImpl(ILampViewModel.class);
        this.mAtlVm = (AtlViewModel) ViewModelManager.getInstance().getViewModelImpl(IAtlViewModel.class);
        this.mMassagerEffectIDs = this.mSeatViewModel.getMassagerEffectIDs();
        MeditationPlusViewModel meditationPlusViewModel = (MeditationPlusViewModel) ViewModelManager.getInstance().getViewModelImpl(IMeditationViewModel.class);
        this.mViewModel = meditationPlusViewModel;
        setLdo(meditationPlusViewModel.getPlayIndexData(), new Observer() { // from class: com.xiaopeng.xpmeditation.view.plus.-$$Lambda$MeditationPlusScene$rTYW4arwrKtYiwlCS57YBpkrGJ8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MeditationPlusScene.this.lambda$initViewModels$0$MeditationPlusScene((Integer) obj);
            }
        });
        setLdo(this.mViewModel.getPsnStatusData(), new Observer() { // from class: com.xiaopeng.xpmeditation.view.plus.-$$Lambda$MeditationPlusScene$NIWC5gjJou1T-aW_Utyukkw7qcQ
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MeditationPlusScene.this.lambda$initViewModels$1$MeditationPlusScene((Boolean) obj);
            }
        });
        setLdo(this.mViewModel.getPlayStateData(), new Observer() { // from class: com.xiaopeng.xpmeditation.view.plus.-$$Lambda$MeditationPlusScene$Gyxuw3CtBI-i-Lmr-AOUChbuX4I
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MeditationPlusScene.this.lambda$initViewModels$2$MeditationPlusScene((Integer) obj);
            }
        });
        setLdo(this.mHvacViewModel.getHvacTempDriverData(), new Observer() { // from class: com.xiaopeng.xpmeditation.view.plus.-$$Lambda$MeditationPlusScene$R49Ia6j6mA16anJ_ZRlczes_Oww
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MeditationPlusScene.this.lambda$initViewModels$3$MeditationPlusScene((Float) obj);
            }
        });
        setLdo(this.mHvacViewModel.getHvacAutoData(), new Observer() { // from class: com.xiaopeng.xpmeditation.view.plus.-$$Lambda$MeditationPlusScene$my7PqsUsBn10tXkalKRIYLPTm4s
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MeditationPlusScene.this.lambda$initViewModels$4$MeditationPlusScene((Boolean) obj);
            }
        });
        setLdo(this.mHvacViewModel.getHvacTempAcData(), new Observer() { // from class: com.xiaopeng.xpmeditation.view.plus.-$$Lambda$MeditationPlusScene$X4ZyFj84X69cW46o0ySDe8hElU0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MeditationPlusScene.this.lambda$initViewModels$5$MeditationPlusScene((AcHeatNatureMode) obj);
            }
        });
        setLdo(this.mHvacViewModel.getHvacCirculationData(), new Observer() { // from class: com.xiaopeng.xpmeditation.view.plus.-$$Lambda$MeditationPlusScene$8xPEC-yV1AMYisGGJd8U3rrrUAE
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MeditationPlusScene.this.lambda$initViewModels$6$MeditationPlusScene((HvacCirculationMode) obj);
            }
        });
        setLdo(this.mVcuViewModel.getGearLevelData(), new Observer() { // from class: com.xiaopeng.xpmeditation.view.plus.-$$Lambda$MeditationPlusScene$My0m6ligbPkVQS4-_rMLqiOt71o
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MeditationPlusScene.this.lambda$initViewModels$7$MeditationPlusScene((GearLevel) obj);
            }
        });
    }

    public /* synthetic */ void lambda$initViewModels$0$MeditationPlusScene(Integer index) {
        LogUtils.d(TAG, "play index change to : " + index);
        if (index == null || index.intValue() <= -1 || this.mView == null) {
            return;
        }
        updatePlayIndex(index.intValue());
    }

    public /* synthetic */ void lambda$initViewModels$1$MeditationPlusScene(Boolean enable) {
        MeditationPlusPlayView meditationPlusPlayView;
        LogUtils.d(TAG, "mViewModel.getPsnStatusData(): " + enable);
        if (this.mView == null || !this.mIsShowPlay || (meditationPlusPlayView = this.mPlayView) == null) {
            return;
        }
        meditationPlusPlayView.updatePsnControlBtnText(enable.booleanValue());
        setPsnMeditationMode(enable.booleanValue());
    }

    public /* synthetic */ void lambda$initViewModels$2$MeditationPlusScene(Integer state) {
        LogUtils.i(TAG, "play state changed to: " + state);
        if (state == null || this.mView == null) {
            return;
        }
        updatePlayState(state.intValue());
    }

    public /* synthetic */ void lambda$initViewModels$3$MeditationPlusScene(Float temp) {
        updateTemp(temp.floatValue());
    }

    public /* synthetic */ void lambda$initViewModels$4$MeditationPlusScene(Boolean isAuto) {
        updateAutoData(isAuto.booleanValue());
    }

    public /* synthetic */ void lambda$initViewModels$5$MeditationPlusScene(AcHeatNatureMode data) {
        updateAcData(data == AcHeatNatureMode.HVAC_AC_ON);
    }

    public /* synthetic */ void lambda$initViewModels$7$MeditationPlusScene(GearLevel level) {
        if (GearLevel.P.equals(level)) {
            return;
        }
        this.mScenarioViewModel.startScenario(false, ScenarioMode.Meditation);
        NotificationHelper.getInstance().showToast(R.string.meditaion_plus_exit_msg);
    }

    protected void initViews() {
        ViewGroup viewGroup = (ViewGroup) getContent().findViewById(R.id.meditation_content);
        this.mView = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.layout_meditation_plus, viewGroup);
        this.mPlayStub = (ViewStub) getContent().findViewById(R.id.play_stub);
        XImageButton xImageButton = (XImageButton) getContent().findViewById(R.id.btn_close);
        this.mCloseBtn = xImageButton;
        xImageButton.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.xpmeditation.view.plus.-$$Lambda$MeditationPlusScene$_g15-6gWFwKM2GI9nIfDYvwYocg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MeditationPlusScene.this.lambda$initViews$8$MeditationPlusScene(view);
            }
        });
        this.mTitleTv = (XTextView) getContent().findViewById(R.id.meditation_title);
        this.mStartVideoView = (VideoView) viewGroup.findViewById(R.id.meditation_start_surface);
        initPrepareView();
    }

    public /* synthetic */ void lambda$initViews$8$MeditationPlusScene(View v) {
        showExitConfirmDialog(true);
    }

    @Override // com.xiaopeng.xpmeditation.view.BaseMeditationScene
    public void preEnterMeditation() {
        LogUtils.i(TAG, "preEnterMeditation: ");
        this.mLampViewModel.setHeadLampMode(LampMode.Off);
        this.mWindowDoorViewModel.controlWindowClose();
        if (!this.mHvacViewModel.isHvacPowerModeOn()) {
            this.mHvacViewModel.setHvacPowerMode(true);
        }
        if (!this.mHvacViewModel.isHvacAutoModeOn()) {
            this.mHvacViewModel.setHvacAutoMode(true);
        }
        this.mSeatViewModel.enterMeditationState(CarBaseConfig.getInstance().getMeditationSeatPos());
        if (!CarBaseConfig.getInstance().isSupportSfs() || this.mSfsViewModel.isSwEnabled()) {
            return;
        }
        reqSfs(true);
    }

    private void enterMeditation() {
        enterMeditationTheme(this.mViewModel.getLastPlayIndex());
    }

    private void enterMeditationTheme(int index) {
        LogUtils.i(TAG, "enterMeditationTheme: " + index);
        this.mAtlVm.enterMeditationState();
        if (CarBaseConfig.getInstance().isSupportSeatMassage()) {
            this.mSeatViewModel.startMassager(0, (index >= this.mMassagerEffectIDs.size() || index <= -1) ? DEFAULT_MASSAGER_EFFECT : this.mMassagerEffectIDs.get(index));
        }
        if (CarBaseConfig.getInstance().isSupportSeatRhythm()) {
            SeatViewModel seatViewModel = this.mSeatViewModel;
            int[] iArr = this.mRhythmModes;
            seatViewModel.startRhythm(0, (index >= iArr.length || index <= -1) ? 0 : iArr[index]);
        }
        this.mViewModel.setLastPlayIndex(index);
        this.mViewModel.play(index);
    }

    private void exitMeditationMode() {
        LogUtils.i(TAG, "exitMeditationMode: ");
        this.mSeatViewModel.exitMeditationState();
        this.mAtlVm.exitMeditationState();
        if (CarBaseConfig.getInstance().isSupportSeatMassage()) {
            this.mSeatViewModel.stopMassager(0);
        }
        if (CarBaseConfig.getInstance().isSupportSeatRhythm()) {
            this.mSeatViewModel.stopRhythm(0);
        }
        setPsnMeditationMode(false);
        this.mViewModel.release();
        this.mScenarioViewModel.startScenario(false, ScenarioMode.Meditation);
    }

    public void pauseMeditationInScene() {
        LogUtils.i(TAG, "pauseMeditationInScene: ");
        this.mAtlVm.setAtlSwitch(false);
        if (CarBaseConfig.getInstance().isSupportSeatMassage()) {
            this.mSeatViewModel.stopMassager(0);
        }
        if (CarBaseConfig.getInstance().isSupportSeatRhythm()) {
            this.mSeatViewModel.stopRhythm(0);
        }
        this.mViewModel.pause();
    }

    private void setPsnMeditationMode(boolean isEnable) {
        if (isEnable) {
            int lastPlayIndex = this.mViewModel.getLastPlayIndex();
            if (CarBaseConfig.getInstance().isSupportSeatMassage()) {
                this.mSeatViewModel.startMassager(1, (lastPlayIndex >= this.mMassagerEffectIDs.size() || lastPlayIndex <= -1) ? DEFAULT_MASSAGER_EFFECT : this.mMassagerEffectIDs.get(lastPlayIndex));
            }
            if (CarBaseConfig.getInstance().isSupportSeatRhythm()) {
                SeatViewModel seatViewModel = this.mSeatViewModel;
                int[] iArr = this.mRhythmModes;
                seatViewModel.startRhythm(1, (lastPlayIndex >= iArr.length || lastPlayIndex <= -1) ? 0 : iArr[lastPlayIndex]);
            }
            int meditationSeatPos = CarBaseConfig.getInstance().getMeditationSeatPos();
            int pSeatTiltPos = this.mSeatViewModel.getPSeatTiltPos();
            this.mLastPsnTiltPos = pSeatTiltPos;
            if (Math.abs(meditationSeatPos - pSeatTiltPos) < 2 || meditationSeatPos == -1) {
                return;
            }
            LogUtils.i(TAG, "start to move psn seat tilt position");
            this.mSeatViewModel.setPSeatTiltPos(meditationSeatPos);
            return;
        }
        if (CarBaseConfig.getInstance().isSupportSeatMassage()) {
            this.mSeatViewModel.stopMassager(1);
        }
        if (CarBaseConfig.getInstance().isSupportSeatRhythm()) {
            this.mSeatViewModel.stopRhythm(1);
        }
        if (Math.abs(this.mLastPsnTiltPos - this.mSeatViewModel.getPSeatTiltPos()) < 2 || this.mLastPsnTiltPos == -1) {
            return;
        }
        LogUtils.i(TAG, "resume psn seat tilt position");
        this.mSeatViewModel.setPSeatTiltPos(this.mLastPsnTiltPos);
    }

    private void reqSfs(boolean enter) {
        LogUtils.i(TAG, "reqSfs sendBroadcast:" + enter);
        Intent intent = new Intent("com.xiaopeng.intent.action.AIOT_NOTIFICATION");
        intent.putExtra("bd_event_type", "type_event_stat_notify");
        intent.putExtra("key_event_stat_notify", enter ? "notify_fragrance_into_meditation" : "notify_fragrance_out_meditation");
        intent.setFlags(16777216);
        App.getInstance().getApplicationContext().sendBroadcast(intent);
    }

    @Override // com.xiaopeng.xpmeditation.view.BaseMeditationScene
    public void refreshTime(long time) {
        MeditationPlusPlayView meditationPlusPlayView = this.mPlayView;
        if (meditationPlusPlayView != null) {
            meditationPlusPlayView.refreshTimeGroup(MeditationPlusViewModel.parseTime(time));
        }
    }

    private void release() {
        LogUtils.d(TAG, "release = ");
        if (CarBaseConfig.getInstance().isSupportSfs()) {
            reqSfs(false);
        }
        exitMeditationMode();
        this.mStartVideoView.stopPlayback();
        XDialog xDialog = this.mDialog;
        if (xDialog != null) {
            xDialog.dismiss();
            this.mDialog = null;
        }
        this.mImmerseHandler.removeCallbacksAndMessages(null);
        this.mMeditationCountDownTimer.cancel();
    }

    @Override // com.xiaopeng.xpmeditation.view.BaseMeditationScene
    public void playNext() {
        this.mViewModel.playNext();
    }

    @Override // com.xiaopeng.xpmeditation.view.BaseMeditationScene
    public void playPre() {
        this.mViewModel.playPre();
    }

    @Override // com.xiaopeng.xpmeditation.view.BaseMeditationScene
    public void onClose() {
        showExitConfirmDialog(true);
    }

    @Override // com.xiaopeng.xpmeditation.view.BaseMeditationScene
    public boolean onTouch(MotionEvent event) {
        exitAndStartImmerseCheck();
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkIfStartImmerseCountDown() {
        MeditationPlusPlayView meditationPlusPlayView;
        XDialog xDialog = this.mDialog;
        if ((xDialog == null || !xDialog.isShowing()) && this.mViewModel.isPlaying() && (meditationPlusPlayView = this.mPlayView) != null && this.mIsShowPlay && !meditationPlusPlayView.isDialogShowing()) {
            return startImmerseCountDown();
        }
        return false;
    }

    private boolean startImmerseCountDown() {
        LogUtils.d(TAG, "startImmerseCountDown, mIsShowPlay: " + this.mIsShowPlay);
        if (this.mPlayView == null || !this.mIsShowPlay) {
            return false;
        }
        this.mImmerseHandler.removeCallbacksAndMessages(null);
        this.mImmerseHandler.sendEmptyMessageDelayed(0, IMMERSE_COUNT_DOWN_TIME);
        return true;
    }

    private void exitAndStartImmerseCheck() {
        exitImmerse();
        this.mImmerseHandler.sendEmptyMessageDelayed(1, 1000L);
    }

    private void exitImmerse() {
        XImageButton xImageButton;
        this.mImmerseHandler.removeCallbacksAndMessages(null);
        if (this.mPlayView == null || !this.mIsShowPlay || (xImageButton = this.mCloseBtn) == null || this.mTitleTv == null) {
            return;
        }
        xImageButton.setVisibility(0);
        this.mTitleTv.setVisibility(0);
        this.mPlayView.exitImmerse();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enterImmerse() {
        XImageButton xImageButton;
        LogUtils.d(TAG, "enterImmerse, mIsShowPlay: " + this.mIsShowPlay);
        if (this.mPlayView == null || !this.mIsShowPlay || (xImageButton = this.mCloseBtn) == null || this.mTitleTv == null) {
            return;
        }
        xImageButton.setVisibility(8);
        this.mTitleTv.setVisibility(8);
        this.mPlayView.enterImmerse();
    }

    private void initPrepareView() {
        LogUtils.d(TAG, "initPrepareView: ");
        prepareStartMedia();
        this.mPrepareTxt = (XTextView) getContent().findViewById(R.id.txt_prepare);
        if (CarBaseConfig.getInstance().isSupportMsmD()) {
            this.mPrepareTxt.setText(R.string.meditation_prepareing_tip_plus);
            SpeechHelper.getInstance().speak(ResUtil.getString(R.string.meditation_prepareing_tip_plus));
            return;
        }
        this.mPrepareTxt.setText(R.string.meditation_prepareing_tip_plus_low);
        SpeechHelper.getInstance().speak(ResUtil.getString(R.string.meditation_prepareing_tip_plus_low));
    }

    private void prepareStartMedia() {
        LogUtils.d(TAG, "prepareStartMedia:/system/media/video/xiaopeng/cdu/mp4/CDU_relax_start.mp4");
        this.mStartVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.xiaopeng.xpmeditation.view.plus.-$$Lambda$MeditationPlusScene$wwFTAv4Pt4bdzlcGQ6vdXHb8Dqc
            @Override // android.media.MediaPlayer.OnPreparedListener
            public final void onPrepared(MediaPlayer mediaPlayer) {
                MeditationPlusScene.this.lambda$prepareStartMedia$10$MeditationPlusScene(mediaPlayer);
            }
        });
        this.mStartVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() { // from class: com.xiaopeng.xpmeditation.view.plus.-$$Lambda$MeditationPlusScene$BXYtPNb2OwOPG9QwPyhiz9uEpmQ
            @Override // android.media.MediaPlayer.OnErrorListener
            public final boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                return MeditationPlusScene.this.lambda$prepareStartMedia$11$MeditationPlusScene(mediaPlayer, i, i2);
            }
        });
        this.mStartVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: com.xiaopeng.xpmeditation.view.plus.MeditationPlusScene.3
            @Override // android.media.MediaPlayer.OnCompletionListener
            public void onCompletion(MediaPlayer mp) {
                LogUtils.i(MeditationPlusScene.TAG, "start video completed");
                MeditationPlusScene.this.hidePrepareView();
                MeditationPlusScene.this.showAndPlay();
            }
        });
        if (new File(START_VIDEO_PATH).exists()) {
            this.mStartVideoView.setVideoPath("file:///system/media/video/xiaopeng/cdu/mp4/CDU_relax_start.mp4");
        } else {
            LogUtils.w(TAG, "video path not found:/system/media/video/xiaopeng/cdu/mp4/CDU_relax_start.mp4");
        }
    }

    public /* synthetic */ void lambda$prepareStartMedia$10$MeditationPlusScene(MediaPlayer mp) {
        this.isVideoPrepared = true;
        long duration = this.mStartVideoView.getDuration() - 2000;
        if (duration <= 0) {
            duration = 7000;
        }
        LogUtils.d(TAG, "startPrepareCountDown, delay: " + duration);
        ThreadUtils.postDelayed(1, new Runnable() { // from class: com.xiaopeng.xpmeditation.view.plus.-$$Lambda$MeditationPlusScene$ZV6JveUiMtRe6Uk-4khK0yZ7eyQ
            @Override // java.lang.Runnable
            public final void run() {
                MeditationPlusScene.this.lambda$null$9$MeditationPlusScene();
            }
        }, duration);
    }

    public /* synthetic */ void lambda$null$9$MeditationPlusScene() {
        if (this.mPlayView == null) {
            preloadPlayView();
        }
    }

    public /* synthetic */ boolean lambda$prepareStartMedia$11$MeditationPlusScene(MediaPlayer mp, int what, int extra) {
        LogUtils.d(TAG, "video error... Error info: what - " + what + "     extra - " + extra);
        this.mStartVideoView.setVisibility(8);
        this.mStartVideoView.stopPlayback();
        this.isVideoPrepared = false;
        return true;
    }

    private boolean playStartVideoAndMusic() {
        LogUtils.d(TAG, "play start video & music");
        if (this.isVideoPrepared) {
            if (this.mStartVideoView.isPlaying()) {
                this.mStartVideoView.seekTo(0);
                LogUtils.d(TAG, "seek to start.");
            }
            this.mStartVideoView.start();
        } else {
            this.mStartVideoView.start();
            LogUtils.d(TAG, "no prepared, video will play later by VideoView targetState!");
        }
        this.mViewModel.playStartMusic(START_MUSIC_PATH);
        return this.isVideoPrepared;
    }

    private void preloadPlayView() {
        LogUtils.i(TAG, "preloadPlayView");
        createPlayView();
        updatePlayViewInfo();
        this.mPlayView.setVisibility(0);
        this.mIsShowPlay = true;
        refreshTimeGroup(MeditationPlusViewModel.parseTime(System.currentTimeMillis()));
    }

    private void updatePlayViewInfo() {
        MeditationPlusPlayView meditationPlusPlayView = this.mPlayView;
        if (meditationPlusPlayView != null) {
            meditationPlusPlayView.refreshMeditationList(this.mViewModel.getMusicList());
            if (this.mIsShowPlay) {
                this.mPlayView.setVisibility(0);
            }
        }
        updatePlayIndex(this.mViewModel.getLastPlayIndex());
        updateTemp(this.mHvacViewModel.getHvacDriverTemp());
        updateAutoData(this.mHvacViewModel.isHvacAutoModeOn());
        updateAcData(this.mHvacViewModel.isHvacAcModeOn());
        lambda$initViewModels$6$MeditationPlusScene(this.mHvacViewModel.getCirculationMode());
        if (this.isSupportSfs) {
            this.mPlayView.updateSfsStatus(this.mSfsViewModel.isSwEnabled());
        }
        this.mPlayView.updatePsnControlBtnText(this.mViewModel.getPsnProcessStatus());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hidePrepareView() {
        this.mPrepareTxt.setVisibility(8);
        this.mStartVideoView.stopPlayback();
        ThreadUtils.postDelayed(1, new Runnable() { // from class: com.xiaopeng.xpmeditation.view.plus.MeditationPlusScene.4
            @Override // java.lang.Runnable
            public void run() {
                MeditationPlusScene.this.mStartVideoView.setVisibility(8);
            }
        }, 2000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showAndPlay() {
        LogUtils.i(TAG, "showPlayView, is null " + (this.mPlayView == null));
        MeditationPlusPlayView meditationPlusPlayView = this.mPlayView;
        if (meditationPlusPlayView == null) {
            preloadPlayView();
            return;
        }
        meditationPlusPlayView.setVisibility(0);
        this.mIsShowPlay = true;
        exitAndStartImmerseCheck();
        refreshTimeGroup(MeditationPlusViewModel.parseTime(System.currentTimeMillis()));
        enterMeditation();
        this.mMeditationCountDownTimer.start();
    }

    private void createPlayView() {
        LogUtils.i(TAG, "createPlayView");
        if (this.mPlayView != null) {
            return;
        }
        ViewStub viewStub = this.mPlayStub;
        if (viewStub != null && viewStub.getParent() != null) {
            this.mPlayView = (MeditationPlusPlayView) this.mPlayStub.inflate();
        }
        MeditationPlusPlayView meditationPlusPlayView = this.mPlayView;
        if (meditationPlusPlayView == null) {
            return;
        }
        meditationPlusPlayView.setOnControllerListener(new MeditationPlusPlayView.MeditationStatusListener() { // from class: com.xiaopeng.xpmeditation.view.plus.MeditationPlusScene.5
            @Override // com.xiaopeng.xpmeditation.view.plus.MeditationPlusPlayView.MeditationStatusListener
            public void onControl() {
                if (MeditationPlusScene.this.mPlayView == null || !MeditationPlusScene.this.mIsShowPlay) {
                    return;
                }
                MeditationPlusScene.this.mViewModel.playOrPause();
            }

            @Override // com.xiaopeng.xpmeditation.view.plus.MeditationPlusPlayView.MeditationStatusListener
            public void onPsnControl() {
                MeditationPlusScene.this.mViewModel.pauseOrContinuePsnMeditation();
            }
        });
        this.mPlayView.setOnItemClick(new BaseRecyclerViewAdapter.ItemClickListener() { // from class: com.xiaopeng.xpmeditation.view.plus.-$$Lambda$MeditationPlusScene$F243yl3Qxus2lny33iAjOpzyhsI
            @Override // com.xiaopeng.xpmeditation.view.adapter.BaseRecyclerViewAdapter.ItemClickListener
            public final void click(int i, int i2) {
                MeditationPlusScene.this.lambda$createPlayView$12$MeditationPlusScene(i, i2);
            }
        });
        this.mPlayView.setVisibility(8);
    }

    public /* synthetic */ void lambda$createPlayView$12$MeditationPlusScene(int type, int position) {
        enterMeditationTheme(position);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showExitConfirmDialog(final boolean fromUser) {
        if (this.mDialog == null) {
            XDialog negativeButton = new XDialog(getContext()).setTitle(fromUser ? R.string.meditaion_plus_exit_confirm_title : R.string.meditaion_plus_finish_confirm_title).setMessage(fromUser ? R.string.meditaion_plus_exit_confirm_content : R.string.meditaion_plus_finish_confirm_content).setPositiveButton(ResourceUtils.getResources().getString(fromUser ? R.string.meditation_sure : R.string.msg_exit), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.xpmeditation.view.plus.-$$Lambda$MeditationPlusScene$preQ2XIwpOWvwfn_LO_fJcaBFbs
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i) {
                    MeditationPlusScene.this.lambda$showExitConfirmDialog$13$MeditationPlusScene(xDialog, i);
                }
            }).setNegativeButton(ResourceUtils.getResources().getString(fromUser ? R.string.meditation_cancel : R.string.meditaion_plus_once_more), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.xpmeditation.view.plus.-$$Lambda$MeditationPlusScene$m6b9XbHAhOfUYOV9QP_cBdaoTII
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i) {
                    MeditationPlusScene.this.lambda$showExitConfirmDialog$14$MeditationPlusScene(fromUser, xDialog, i);
                }
            });
            this.mDialog = negativeButton;
            Window window = negativeButton.getDialog().getWindow();
            if (window != null) {
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.y = -UIUtils.dip2px(App.getInstance().getApplicationContext(), ResourceUtils.getResources().getDimensionPixelOffset(R.dimen.meditation_dialog_margin_top));
                window.setAttributes(attributes);
            }
            if (this.mDialog.getDialog() != null && this.mDialog.getDialog().getWindow() != null) {
                WindowManager.LayoutParams attributes2 = this.mDialog.getDialog().getWindow().getAttributes();
                attributes2.flags |= 1024;
                attributes2.systemUiVisibility |= 1024;
                attributes2.systemUiVisibility |= 2;
                attributes2.systemUiVisibility |= 512;
                this.mDialog.getDialog().getWindow().setAttributes(attributes2);
            }
        }
        this.mDialog.show();
    }

    public /* synthetic */ void lambda$showExitConfirmDialog$13$MeditationPlusScene(XDialog xDialog, int i) {
        this.mScenarioViewModel.startScenario(false, ScenarioMode.Meditation);
    }

    public /* synthetic */ void lambda$showExitConfirmDialog$14$MeditationPlusScene(final boolean fromUser, XDialog xDialog, int i) {
        this.mDialog = null;
        if (fromUser) {
            return;
        }
        this.mMeditationCountDownTimer.seek(0);
        this.mMeditationCountDownTimer.resume();
        enterMeditation();
    }

    private void refreshTimeGroup(String[] timeArray) {
        MeditationPlusPlayView meditationPlusPlayView;
        if (timeArray == null || timeArray.length < 3 || (meditationPlusPlayView = this.mPlayView) == null || !this.mIsShowPlay) {
            return;
        }
        meditationPlusPlayView.refreshTimeGroup(timeArray);
    }

    private void updatePlayIndex(int index) {
        if (this.mPlayView != null) {
            List<MeditationItemBeanPlus> musicList = this.mViewModel.getMusicList();
            if (musicList != null) {
                this.mPlayView.refreshMeditationList(musicList);
                this.mPlayView.refreshInfo(musicList.get(index));
                this.mPlayView.scrollToIndex(index);
            }
            if (this.mIsShowPlay) {
                this.mPlayView.setVisibility(0);
            }
        }
    }

    private void updatePlayState(int state) {
        MeditationPlusPlayView meditationPlusPlayView = this.mPlayView;
        if (meditationPlusPlayView != null) {
            if (this.mIsShowPlay) {
                meditationPlusPlayView.setVisibility(0);
            }
            this.mPlayView.refreshStatus(state);
            if (state == 2) {
                this.mMeditationCountDownTimer.resume();
                return;
            }
            this.mMeditationCountDownTimer.pause();
            exitAndStartImmerseCheck();
        }
    }

    private void updateTemp(float temp) {
        MeditationPlusPlayView meditationPlusPlayView = this.mPlayView;
        if (meditationPlusPlayView != null) {
            meditationPlusPlayView.updateTemp(temp);
        }
    }

    private void updateAutoData(boolean on) {
        MeditationPlusPlayView meditationPlusPlayView = this.mPlayView;
        if (meditationPlusPlayView != null) {
            meditationPlusPlayView.updateAutoData(on);
        }
    }

    private void updateAcData(boolean on) {
        MeditationPlusPlayView meditationPlusPlayView = this.mPlayView;
        if (meditationPlusPlayView != null) {
            meditationPlusPlayView.updateAcData(on);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: updateHvacCirculationMode */
    public void lambda$initViewModels$6$MeditationPlusScene(HvacCirculationMode mode) {
        MeditationPlusPlayView meditationPlusPlayView = this.mPlayView;
        if (meditationPlusPlayView != null) {
            meditationPlusPlayView.updateHvacCirculationMode(mode);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xpmeditation.view.BaseMeditationScene
    public void onCreate() {
        super.onCreate();
        initViewModels();
        initViews();
        this.mViewModel.init();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xpmeditation.view.BaseMeditationScene
    public void onStart() {
        super.onStart();
        playStartVideoAndMusic();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xpmeditation.view.BaseMeditationScene
    public void onResume() {
        super.onResume();
        checkIfStartImmerseCountDown();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xpmeditation.view.BaseMeditationScene
    public void onPause() {
        super.onPause();
        exitImmerse();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xpmeditation.view.BaseMeditationScene
    public void onStop() {
        super.onStop();
        release();
    }
}
