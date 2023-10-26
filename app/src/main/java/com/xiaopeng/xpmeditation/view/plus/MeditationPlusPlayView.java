package com.xiaopeng.xpmeditation.view.plus;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingConstants;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.viewmodel.hvac.HvacCirculationMode;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.lludancemanager.util.ResUtil;
import com.xiaopeng.xpmeditation.model.MeditationBean;
import com.xiaopeng.xpmeditation.model.MeditationItemBeanPlus;
import com.xiaopeng.xpmeditation.util.TimeUtil;
import com.xiaopeng.xpmeditation.view.adapter.BaseRecyclerViewAdapter;
import com.xiaopeng.xpmeditation.view.adapter.HorizatalLinearItemDecoration;
import com.xiaopeng.xpmeditation.view.adapter.MeditationPlusMusicAdapter;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XConstraintLayout;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XRelativeLayout;
import com.xiaopeng.xui.widget.XTextView;
import java.util.List;

/* loaded from: classes2.dex */
public class MeditationPlusPlayView extends XRelativeLayout {
    private static final String ACTION_HVAC = "com.xiaopeng.carcontrol.intent.action.SHOW_HVAC_PANEL";
    private static final String TAG = "MeditationPlusPlayView";
    private boolean isVideoPrepared;
    private XTextView mAcTemp;
    private MeditationPlusMusicAdapter mAdapter;
    private XTextView mAirTv;
    private MeditationStatusListener mControllerListener;
    protected XImageView mControllerView;
    private XDialog mDialog;
    private View mExtraInfoView;
    private XImageView mHvacAcModeView;
    private XImageView mHvacAutoModeView;
    private XImageView mHvacCirculationView;
    private View mImmerseIndicateView;
    private BaseRecyclerViewAdapter.ItemClickListener mItemClickListener;
    private XTextView mLeftTimeTv;
    private XRelativeLayout mMainView;
    private VideoView mMeditationPlaySv;
    private XTextView mPsnControlBtn;
    private RecyclerView mRecyclerView;
    private View mSceneInfoView;
    private XConstraintLayout mSecondaryView;
    private XButton mShowDetailBtn;
    private XTextView mThemeInfoTv;
    private XTextView mTvSeatInfo;
    private XTextView mTvfragranceInfo;
    private int mVisiblity;

    /* loaded from: classes2.dex */
    public interface MeditationStatusListener {
        void onControl();

        void onPsnControl();
    }

    public MeditationPlusPlayView(Context context) {
        super(context);
        this.mVisiblity = -1;
        this.isVideoPrepared = false;
        init();
    }

    public MeditationPlusPlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mVisiblity = -1;
        this.isVideoPrepared = false;
        init();
    }

    public MeditationPlusPlayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mVisiblity = -1;
        this.isVideoPrepared = false;
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_meditation_play_plus, this);
        initView();
    }

    protected void initView() {
        initVideoPlayView();
        initInfo();
        initRecycler();
        initController();
    }

    private void initVideoPlayView() {
        LogUtils.d(TAG, "initVideoPlayView");
        VideoView videoView = (VideoView) findViewById(R.id.meditation_play_surface);
        this.mMeditationPlaySv = videoView;
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.xiaopeng.xpmeditation.view.plus.-$$Lambda$MeditationPlusPlayView$aM735a1A9PzFV4FNGxPhIW3kLmI
            @Override // android.media.MediaPlayer.OnPreparedListener
            public final void onPrepared(MediaPlayer mediaPlayer) {
                MeditationPlusPlayView.this.lambda$initVideoPlayView$0$MeditationPlusPlayView(mediaPlayer);
            }
        });
        this.mMeditationPlaySv.setOnErrorListener(new MediaPlayer.OnErrorListener() { // from class: com.xiaopeng.xpmeditation.view.plus.-$$Lambda$MeditationPlusPlayView$SYO6dTj7B57gu1llv53dK6Zn9Ys
            @Override // android.media.MediaPlayer.OnErrorListener
            public final boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                return MeditationPlusPlayView.this.lambda$initVideoPlayView$1$MeditationPlusPlayView(mediaPlayer, i, i2);
            }
        });
        this.mMeditationPlaySv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: com.xiaopeng.xpmeditation.view.plus.MeditationPlusPlayView.1
            @Override // android.media.MediaPlayer.OnCompletionListener
            public void onCompletion(MediaPlayer mp) {
                MeditationPlusPlayView.this.mMeditationPlaySv.start();
            }
        });
    }

    public /* synthetic */ void lambda$initVideoPlayView$0$MeditationPlusPlayView(MediaPlayer mp) {
        LogUtils.d(TAG, "video view prepared");
        this.isVideoPrepared = true;
    }

    public /* synthetic */ boolean lambda$initVideoPlayView$1$MeditationPlusPlayView(MediaPlayer mp, int what, int extra) {
        LogUtils.d(TAG, "video error... Error info: what - " + what + "     extra - " + extra);
        this.mMeditationPlaySv.setVisibility(8);
        this.mMeditationPlaySv.stopPlayback();
        this.isVideoPrepared = false;
        return true;
    }

    private boolean playVideo() {
        LogUtils.d(TAG, "playVideo");
        if (this.isVideoPrepared) {
            if (this.mMeditationPlaySv.isPlaying()) {
                this.mMeditationPlaySv.seekTo(0);
                LogUtils.d(TAG, "seek to start.");
            }
            this.mMeditationPlaySv.start();
        } else {
            this.mMeditationPlaySv.start();
            LogUtils.d(TAG, "no prepared, video will play later by VideoView targetState!");
        }
        return this.isVideoPrepared;
    }

    private void resumeVideo() {
        LogUtils.d(TAG, "resumeVideo");
        this.mMeditationPlaySv.start();
    }

    private void pauseVideo() {
        LogUtils.d(TAG, "pauseVideo");
        if (this.mMeditationPlaySv.isPlaying()) {
            this.mMeditationPlaySv.pause();
        }
    }

    private void initInfo() {
        this.mMainView = (XRelativeLayout) findViewById(R.id.play_main);
        XConstraintLayout xConstraintLayout = (XConstraintLayout) findViewById(R.id.play_secondary);
        this.mSecondaryView = xConstraintLayout;
        this.mLeftTimeTv = (XTextView) xConstraintLayout.findViewById(R.id.txt_left_time_value);
        this.mExtraInfoView = this.mMainView.findViewById(R.id.extra_info);
        this.mSceneInfoView = this.mMainView.findViewById(R.id.scene_info);
        this.mThemeInfoTv = (XTextView) this.mMainView.findViewById(R.id.scene_info_title);
        this.mImmerseIndicateView = this.mMainView.findViewById(R.id.exitImmersionStateBtn);
        XButton xButton = (XButton) this.mMainView.findViewById(R.id.show_scene_detail_btn);
        this.mShowDetailBtn = xButton;
        xButton.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.xpmeditation.view.plus.MeditationPlusPlayView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (MeditationPlusPlayView.this.mDialog == null) {
                    return;
                }
                MeditationPlusPlayView.this.mDialog.show();
            }
        });
        this.mAcTemp = (XTextView) findViewById(R.id.tv_ac_temp);
        this.mHvacAcModeView = (XImageView) findViewById(R.id.hvacAcMode);
        this.mHvacAutoModeView = (XImageView) findViewById(R.id.hvacAutoMode);
        this.mHvacCirculationView = (XImageView) findViewById(R.id.hvacCirculation);
        findViewById(R.id.ac_control_container).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.xpmeditation.view.plus.-$$Lambda$MeditationPlusPlayView$JcWe5cal9TKYrZ4sjGmX1uvmODQ
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MeditationPlusPlayView.this.lambda$initInfo$2$MeditationPlusPlayView(view);
            }
        });
        this.mTvSeatInfo = (XTextView) findViewById(R.id.tv_seat_control);
        if (CarBaseConfig.getInstance().isSupportSeatMassage() || CarBaseConfig.getInstance().isSupportSeatRhythm()) {
            this.mTvSeatInfo.setVisibility(0);
            this.mTvSeatInfo.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.xpmeditation.view.plus.-$$Lambda$MeditationPlusPlayView$g0jHxpjyTMobtlW2yXELOlB0tJs
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MeditationPlusPlayView.this.lambda$initInfo$3$MeditationPlusPlayView(view);
                }
            });
        } else {
            this.mTvSeatInfo.setVisibility(8);
        }
        this.mTvfragranceInfo = (XTextView) findViewById(R.id.tv_fragrance);
        if (CarBaseConfig.getInstance().isSupportSfs()) {
            this.mTvfragranceInfo.setVisibility(0);
        } else {
            this.mTvfragranceInfo.setVisibility(8);
        }
        this.mTvfragranceInfo.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.xpmeditation.view.plus.-$$Lambda$MeditationPlusPlayView$MS4M3Hq_DL_E0zgBRjg_jqxMnng
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MeditationPlusPlayView.this.lambda$initInfo$4$MeditationPlusPlayView(view);
            }
        });
        int i = ResUtils.getInt(R.integer.meditation_psn_visibility);
        XTextView xTextView = (XTextView) findViewById(R.id.tv_psn_control_btn);
        if (i == 0) {
            this.mPsnControlBtn = (XTextView) findViewById(R.id.psn_control_btn);
            xTextView.setVisibility(8);
        } else {
            this.mPsnControlBtn = xTextView;
        }
        this.mPsnControlBtn.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.xpmeditation.view.plus.-$$Lambda$MeditationPlusPlayView$6L9kHTifig0O1G7PAbK1owcDT1o
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MeditationPlusPlayView.this.lambda$initInfo$5$MeditationPlusPlayView(view);
            }
        });
        updatePsnControlBtnText(false);
    }

    public /* synthetic */ void lambda$initInfo$2$MeditationPlusPlayView(View v) {
        Intent intent = new Intent();
        intent.setAction("com.xiaopeng.carcontrol.intent.action.SHOW_HVAC_PANEL");
        intent.addFlags(1024);
        getContext().startActivity(intent);
    }

    public /* synthetic */ void lambda$initInfo$3$MeditationPlusPlayView(View v) {
        Intent intent = new Intent();
        intent.setAction(GlobalConstant.ACTION.ACTION_SEAT_COMFORT);
        intent.putExtra(GlobalConstant.EXTRA.KEY_SEAT_COMFORT_TYPE, 0);
        intent.addFlags(1024);
        getContext().startActivity(intent);
    }

    public /* synthetic */ void lambda$initInfo$4$MeditationPlusPlayView(View v) {
        Intent intent = new Intent();
        intent.addFlags(268468224);
        intent.addFlags(1024);
        intent.setData(Uri.parse("xiaopeng://aiot/device/detail?type=fragrance&from=meditation_plus"));
        getContext().startActivity(intent);
    }

    public /* synthetic */ void lambda$initInfo$5$MeditationPlusPlayView(View v) {
        MeditationStatusListener meditationStatusListener = this.mControllerListener;
        if (meditationStatusListener != null) {
            meditationStatusListener.onPsnControl();
        }
    }

    private void updateDialog(String title, String msg, String url) {
        if (this.mDialog == null) {
            this.mDialog = new XDialog(getContext(), R.style.XDialogView_XLarge);
        }
        this.mDialog.setTitle(title);
        this.mDialog.setMessage(msg);
        this.mDialog.setCloseVisibility(true);
        this.mDialog.getContentView().setBackgroundResource(App.getInstance().getResources().getIdentifier(url, ResUtil.DRAWABLE, App.getInstance().getPackageName()));
    }

    public boolean isDialogShowing() {
        XDialog xDialog = this.mDialog;
        return xDialog != null && xDialog.isShowing();
    }

    public void updatePsnControlBtnText(boolean isProcess) {
        this.mPsnControlBtn.setText(ResUtils.getString(!isProcess ? R.string.meditation_start_process_text : R.string.meditation_in_process_text));
    }

    private void initRecycler() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_music);
        this.mRecyclerView = recyclerView;
        recyclerView.addItemDecoration(getItemDecoration());
        this.mRecyclerView.setLayoutManager(getLayoutManager());
        MeditationPlusMusicAdapter meditationPlusMusicAdapter = new MeditationPlusMusicAdapter();
        this.mAdapter = meditationPlusMusicAdapter;
        this.mRecyclerView.setAdapter(meditationPlusMusicAdapter);
        this.mAdapter.setItemClickListener(new BaseRecyclerViewAdapter.ItemClickListener() { // from class: com.xiaopeng.xpmeditation.view.plus.-$$Lambda$MeditationPlusPlayView$SUUxIyK1J6iltRsEEoTSPhDXKBE
            @Override // com.xiaopeng.xpmeditation.view.adapter.BaseRecyclerViewAdapter.ItemClickListener
            public final void click(int i, int i2) {
                MeditationPlusPlayView.this.lambda$initRecycler$6$MeditationPlusPlayView(i, i2);
            }
        });
    }

    public /* synthetic */ void lambda$initRecycler$6$MeditationPlusPlayView(int type, int position) {
        BaseRecyclerViewAdapter.ItemClickListener itemClickListener = this.mItemClickListener;
        if (itemClickListener != null) {
            itemClickListener.click(type, position);
        }
    }

    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new HorizatalLinearItemDecoration(43);
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext(), 0, false);
    }

    private void initController() {
        XImageView xImageView = (XImageView) findViewById(R.id.btn_control);
        this.mControllerView = xImageView;
        xImageView.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.xpmeditation.view.plus.MeditationPlusPlayView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (MeditationPlusPlayView.this.mControllerListener != null) {
                    MeditationPlusPlayView.this.mControllerListener.onControl();
                }
            }
        });
    }

    public void refreshMeditationList(List<MeditationItemBeanPlus> list) {
        this.mAdapter.setData(list);
    }

    public void refreshInfo(MeditationItemBeanPlus bean) {
        LogUtils.d(TAG, "refreshInfo, bean = " + bean);
        if (bean.getData() == null) {
            return;
        }
        MeditationBean.DataBean.ListBeanPlus data = bean.getData();
        this.mMeditationPlaySv.setVideoPath("file://" + data.getVideoUrl());
        this.mThemeInfoTv.setText(ResUtil.getStringByIdentity(data.getInfo()));
        updateDialog(ResUtil.getStringByIdentity(data.getTitle()), ResUtil.getStringByIdentity(data.getDetail()), data.getMidThumbnailUrl());
    }

    public void refreshStatus(int status) {
        LogUtils.i(TAG, "refreshStatus status= " + status);
        if (status == 3 || status == 4) {
            this.mControllerView.setImageResource(R.drawable.ic_120_play);
            pauseVideo();
            return;
        }
        this.mControllerView.setImageResource(R.drawable.ic_120_suspend);
        resumeVideo();
    }

    @Override // com.xiaopeng.xui.widget.XRelativeLayout, android.view.View
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (this.mVisiblity != visibility && visibility == 0) {
            this.mMeditationPlaySv.setVisibility(0);
            playVideo();
        }
        this.mVisiblity = visibility;
    }

    public void scrollToIndex(final int index) {
        this.mRecyclerView.post(new Runnable() { // from class: com.xiaopeng.xpmeditation.view.plus.-$$Lambda$MeditationPlusPlayView$3lNA8MA28fYzKDczss22CPf6L1c
            @Override // java.lang.Runnable
            public final void run() {
                MeditationPlusPlayView.this.lambda$scrollToIndex$7$MeditationPlusPlayView(index);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: realScrollToIndex */
    public void lambda$scrollToIndex$7$MeditationPlusPlayView(int index) {
        int i;
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) this.mRecyclerView.getLayoutManager();
        if (linearLayoutManager != null) {
            int findFirstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
            int findLastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
            if (linearLayoutManager.getItemCount() <= 0) {
                return;
            }
            int round = Math.round((findLastVisibleItemPosition - findFirstVisibleItemPosition) / 2.0f);
            int i2 = 0;
            if (index < findFirstVisibleItemPosition) {
                i = index - round;
                if (i <= 0) {
                    i = 0;
                }
                linearLayoutManager.scrollToPositionWithOffset(index, 0);
            } else {
                if (index > findLastVisibleItemPosition) {
                    i = index + round;
                    if (i > linearLayoutManager.getItemCount() - 1) {
                        i = linearLayoutManager.getItemCount() - 1;
                    }
                    linearLayoutManager.scrollToPositionWithOffset(index, 0);
                }
                LogUtils.i(TAG, "current=" + index + " top=" + findFirstVisibleItemPosition + " bottom=" + findLastVisibleItemPosition + " visibleNum=" + round + " pos = " + i2);
            }
            i2 = i;
            LogUtils.i(TAG, "current=" + index + " top=" + findFirstVisibleItemPosition + " bottom=" + findLastVisibleItemPosition + " visibleNum=" + round + " pos = " + i2);
        }
    }

    public void setOnItemClick(BaseRecyclerViewAdapter.ItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public void refreshTimeGroup(String[] timeArray) {
        String[] split = timeArray[2].split(QuickSettingConstants.JOINER);
        ((TextView) findViewById(R.id.txt_time_hour)).setText(split[0]);
        ((TextView) findViewById(R.id.txt_time_min)).setText(split[1]);
        ((TextView) findViewById(R.id.txt_date)).setText(timeArray[1]);
        ((TextView) findViewById(R.id.txt_week)).setText(timeArray[0]);
    }

    public void refreshCountDownTime(long millisUntilFinished) {
        XTextView xTextView = this.mLeftTimeTv;
        if (xTextView != null) {
            xTextView.setText(TimeUtil.getMSFormatTime(millisUntilFinished));
        }
    }

    public void updateTemp(float temp) {
        this.mAcTemp.setText(String.valueOf(temp));
    }

    public void updateAutoData(boolean on) {
        this.mHvacAutoModeView.setSelected(on);
    }

    public void updateAcData(boolean on) {
        this.mHvacAcModeView.setSelected(on);
    }

    public void updateHvacCirculationMode(HvacCirculationMode mode) {
        this.mHvacCirculationView.setImageLevel(mode != null ? mode.ordinal() + 1 : 2);
    }

    public void updateSfsStatus(boolean isEnable) {
        this.mTvfragranceInfo.setSelected(isEnable);
    }

    public void setOnControllerListener(MeditationStatusListener listener) {
        this.mControllerListener = listener;
    }

    public void enterImmerse() {
        this.mRecyclerView.setVisibility(8);
        this.mExtraInfoView.setVisibility(8);
        this.mSceneInfoView.setVisibility(8);
        this.mControllerView.setVisibility(8);
        this.mImmerseIndicateView.setVisibility(0);
    }

    public void exitImmerse() {
        this.mRecyclerView.setVisibility(0);
        this.mExtraInfoView.setVisibility(0);
        this.mControllerView.setVisibility(0);
        this.mImmerseIndicateView.setVisibility(8);
    }
}
