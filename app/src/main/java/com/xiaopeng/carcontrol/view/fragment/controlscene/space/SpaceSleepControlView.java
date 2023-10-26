package com.xiaopeng.carcontrol.view.fragment.controlscene.space;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.airbnb.lottie.LottieAnimationView;
import com.xiaopeng.carcontrol.CapsuleDialogService;
import com.xiaopeng.carcontrol.CapsuleMusicService;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.R;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.helper.SpeechHelper;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingConstants;
import com.xiaopeng.carcontrol.statistic.BtnEnum;
import com.xiaopeng.carcontrol.statistic.PageEnum;
import com.xiaopeng.carcontrol.statistic.StatisticUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.view.fragment.spacecapsule.ISpaceCapsuleSlpCinInterface;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorViewModel;
import com.xiaopeng.carcontrol.viewmodel.endurance.EnduranceViewModel;
import com.xiaopeng.carcontrol.viewmodel.endurance.IEnduranceViewModel;
import com.xiaopeng.carcontrol.viewmodel.hvac.AcHeatNatureMode;
import com.xiaopeng.carcontrol.viewmodel.hvac.HvacCirculationMode;
import com.xiaopeng.carcontrol.viewmodel.hvac.HvacViewModel;
import com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel;
import com.xiaopeng.carcontrol.viewmodel.sfs.ISfsViewModel;
import com.xiaopeng.carcontrol.viewmodel.sfs.SfsViewModel;
import com.xiaopeng.carcontrol.viewmodel.space.ISpaceCapsuleViewModel;
import com.xiaopeng.carcontrol.viewmodel.space.SpaceCapsuleViewModel;
import com.xiaopeng.spacecapsule.util.ScreenOnUtil;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.theme.XThemeManager;
import com.xiaopeng.xui.utils.XuiUtils;
import com.xiaopeng.xui.widget.XImageButton;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XTabLayout;
import com.xiaopeng.xui.widget.XTextView;
import com.xiaopeng.xui.widget.timepicker.XTimePicker;
import com.xiaopeng.xui.widget.toggle.XToggleLayout;
import com.xiaopeng.xui.widget.toggle.XToggleText;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class SpaceSleepControlView extends AbsSpaceModeView implements CapsuleMusicService.CapsuleMusicListener {
    private static final String ALARM_PATH = "/system/media/audio/xiaopeng/cdu/wav/CDU_CAPSULE_ALARM.wav";
    private static final int ENDURANCE_SHORTAGE_MILE = 20;
    private static final int ENDURANCE_UPDATE_TIME_INTERVAL = 300000;
    private static final int ENDURANCE_WARNING_MILE = 60;
    private static final String MEDIA_PATH1 = "/system/media/audio/xiaopeng/cdu/ogg/CDU_CAPSULE_MUSIC1.ogg";
    private static final String MEDIA_PATH2 = "/system/media/audio/xiaopeng/cdu/ogg/CDU_CAPSULE_MUSIC2.ogg";
    private static final String MEDIA_PATH3 = "/system/media/audio/xiaopeng/cdu/ogg/CDU_CAPSULE_MUSIC3.ogg";
    private static final String MEDIA_PATH4 = "/system/media/audio/xiaopeng/cdu/ogg/CDU_CAPSULE_MUSIC4.ogg";
    private static final String MEDIA_PATH5 = "/system/media/audio/xiaopeng/cdu/ogg/CDU_CAPSULE_MUSIC5.ogg";
    private static final String MEDIA_PATH6 = "/system/media/audio/xiaopeng/cdu/ogg/CDU_CAPSULE_MUSIC6.ogg";
    private static final String MEDIA_PATH7 = "/system/media/audio/xiaopeng/cdu/ogg/CDU_CAPSULE_MUSIC7.ogg";
    private static final String MEDIA_PATH8 = "/system/media/audio/xiaopeng/cdu/ogg/CDU_CAPSULE_MUSIC8.ogg";
    private static final long MUSIC_PLAY_TIME1 = 900000;
    private static final long MUSIC_PLAY_TIME2 = 1800000;
    private static final long MUSIC_PLAY_TIME3 = 3600000;
    private static final String TAG = "SpaceSleepControlView";
    private static final int TIME_INVALID = -1;
    private static final ArrayList<MusicResource> sMusicResources;
    private FragmentActivity mActivity;
    private XTextView mAlarmEnduranceTitleTv;
    private Uri mAlarmUri;
    private Context mContext;
    private EnduranceViewModel mEnduranceViewModel;
    private View mHvacAcView;
    private View mHvacAutoView;
    private View mHvacCentiGrade;
    private XImageView mHvacCircleView;
    private View mHvacOff;
    private HvacViewModel mHvacViewModel;
    private ISpaceCapsuleSlpCinInterface mInterface;
    private XImageButton mMusicControlBtn;
    private XImageView mMusicIcon;
    private MusicIconViewHolder mMusicIconViewHolder;
    private XTabLayout mMusicTimeTabLayout;
    private XTextView mMusicTimeTv;
    private View mRootView;
    private MusicResource mSelectResource;
    private SfsViewModel mSfsViewModel;
    private XDialog mSleepAlarmDialog;
    private XTextView mSleepAlarmSubTipsTv;
    private XTextView mSleepAlarmTipsTv;
    private XTextView mSleepEnduranceTv;
    private LottieAnimationView mSleepIcon;
    private XDialog mSleepMusicDialog;
    private XTextView mSleepMusicTipsTv;
    private XDialog mSleepProtectDialog;
    private int mSleepTargetHour;
    private int mSleepTargetMin;
    private String mSleepTargetTime;
    private XTextView mStopTargetTimeTv;
    private XTextView mTargetTimeTv;
    private XTimePicker mTimePicker;
    private XTextView mTvTemperature;
    private SpaceCapsuleViewModel mViewModel;
    private WindowDoorViewModel mWinDoorViewModel;
    private XToggleText mWindowControlBtn;
    private final OnTimeTabLayoutChangeListener mOnTimeTabLayoutChangeListener = new OnTimeTabLayoutChangeListener();
    private final MusicClickListener mMusicClickListener = new MusicClickListener();
    private final List<MusicIconViewHolder> mMusicViews = new ArrayList();
    private long mCurrentPlayTime = -1;
    private boolean mIsPlayTts = false;
    private boolean mIsMusicPlaying = false;
    private boolean mIsSupportRemainingMileage = true;
    private final Handler mHandler = new Handler();
    private final Runnable mTtsPlayHandler = new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.space.-$$Lambda$SpaceSleepControlView$r-sPLFB7XspLgbotK6zh__h4Ydk
        @Override // java.lang.Runnable
        public final void run() {
            SpaceSleepControlView.this.playTts();
        }
    };
    private final Handler mAlarmEnduranceHandler = new Handler();
    private final Handler mEnduranceHandler = new Handler();
    private int mBedType = -1;
    private String mIsPlayingSource = "";
    private CountDownTimeListener mCountDownTimeListener = new CountDownTimeListener();
    StopAlarmReceiver mAlarmReceiver = new StopAlarmReceiver();
    IntentFilter mAlarmIntentFilter = new IntentFilter(GlobalConstant.ACTION.ACTION_CAPSULE_SLEEP_ALARM_STOP_DISMISS);
    private final Runnable mAlarEnduranceRunnable = new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.space.-$$Lambda$SpaceSleepControlView$kN9lVi08yo5zMSPZelApvibc_2k
        @Override // java.lang.Runnable
        public final void run() {
            SpaceSleepControlView.this.lambda$new$15$SpaceSleepControlView();
        }
    };
    private final Runnable mEnduranceRunnable = new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.space.SpaceSleepControlView.4
        @Override // java.lang.Runnable
        public void run() {
            if (SpaceSleepControlView.this.mIsSupportRemainingMileage) {
                LogUtils.i(SpaceSleepControlView.TAG, "mEnduranceRunnable hour: " + SpaceSleepControlView.this.mSleepTargetHour + ", min: " + SpaceSleepControlView.this.mSleepTargetMin);
                if (SpaceSleepControlView.this.mSleepTargetHour == -1) {
                    return;
                }
                Calendar calendar = Calendar.getInstance(SpaceSleepControlView.this.mContext.getResources().getConfiguration().getLocales().get(0));
                Date date = new Date();
                date.setHours(SpaceSleepControlView.this.mSleepTargetHour);
                date.setMinutes(SpaceSleepControlView.this.mSleepTargetMin);
                calendar.setTime(date);
                if (System.currentTimeMillis() > date.getTime()) {
                    calendar.add(5, 1);
                }
                calendar.set(11, SpaceSleepControlView.this.mSleepTargetHour);
                calendar.set(12, SpaceSleepControlView.this.mSleepTargetMin);
                calendar.set(13, 0);
                calendar.set(14, 0);
                SpaceSleepControlView spaceSleepControlView = SpaceSleepControlView.this;
                spaceSleepControlView.updateEnduranceView(spaceSleepControlView.mEnduranceViewModel.getEstimateEndurance(calendar.getTimeInMillis()));
                SpaceSleepControlView.this.mEnduranceHandler.postDelayed(this, 300000L);
            }
        }
    };

    @Override // com.xiaopeng.carcontrol.view.fragment.controlscene.space.AbsSpaceModeView
    protected int getLayoutRes() {
        return R.layout.space_sleep_fragment;
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.controlscene.space.AbsSpaceModeView
    public void onResume() {
    }

    static {
        ArrayList<MusicResource> arrayList = new ArrayList<>();
        sMusicResources = arrayList;
        arrayList.add(new MusicResource(MEDIA_PATH1, R.string.space_sleep_music_classic1, R.drawable.space_sleep_music_icon1));
        arrayList.add(new MusicResource(MEDIA_PATH2, R.string.space_sleep_music_classic2, R.drawable.space_sleep_music_icon2));
        arrayList.add(new MusicResource(MEDIA_PATH3, R.string.space_sleep_music_classic3, R.drawable.space_sleep_music_icon3));
        arrayList.add(new MusicResource(MEDIA_PATH4, R.string.space_sleep_music_classic4, R.drawable.space_sleep_music_icon4));
        arrayList.add(new MusicResource(MEDIA_PATH5, R.string.space_sleep_music_classic5, R.drawable.space_sleep_music_icon5));
        arrayList.add(new MusicResource(MEDIA_PATH6, R.string.space_sleep_music_classic6, R.drawable.space_sleep_music_icon6));
        arrayList.add(new MusicResource(MEDIA_PATH7, R.string.space_sleep_music_classic7, R.drawable.space_sleep_music_icon7));
        arrayList.add(new MusicResource(MEDIA_PATH8, R.string.space_sleep_music_classic8, R.drawable.space_sleep_music_icon8));
    }

    public SpaceSleepControlView(Context context, FragmentActivity activity, ISpaceCapsuleSlpCinInterface slpCinInterface) {
        this.mContext = context;
        this.mActivity = activity;
        this.mInterface = slpCinInterface;
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.controlscene.space.AbsSpaceModeView
    public View onCreateView(LayoutInflater inflater) {
        View onCreateView = super.onCreateView(inflater);
        initViews(onCreateView);
        return onCreateView;
    }

    private void initViews(View view) {
        this.mRootView = view.findViewById(R.id.space_sleep_root_view);
        this.mSleepIcon = (LottieAnimationView) view.findViewById(R.id.space_sleep_animator);
        View findViewById = view.findViewById(R.id.space_capsule_sleep_music_btn);
        XImageView xImageView = (XImageView) findViewById.findViewById(R.id.space_capsule_item_icon);
        this.mMusicIcon = xImageView;
        xImageView.setBackgroundResource(R.drawable.space_capsule_sleep_music);
        ((XTextView) findViewById.findViewById(R.id.space_capsule_item_txt)).setText(R.string.space_sleep_music);
        XTextView xTextView = (XTextView) findViewById.findViewById(R.id.space_capsule_item_subtxt);
        this.mSleepMusicTipsTv = xTextView;
        xTextView.setVisibility(0);
        findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.space.-$$Lambda$SpaceSleepControlView$qHc1yAhYveRieWIfus2mkSjl8VY
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                SpaceSleepControlView.this.lambda$initViews$0$SpaceSleepControlView(view2);
            }
        });
        this.mMusicControlBtn = (XImageButton) findViewById.findViewById(R.id.space_capsule_control_music_btn);
        View findViewById2 = view.findViewById(R.id.space_capsule_sleep_alarm_btn);
        ((XImageView) findViewById2.findViewById(R.id.space_capsule_item_icon)).setImageResource(R.drawable.space_capsule_sleep_alarm);
        XTextView xTextView2 = (XTextView) findViewById2.findViewById(R.id.space_capsule_item_txt);
        this.mSleepAlarmTipsTv = xTextView2;
        xTextView2.setText(this.mContext.getString(R.string.space_sleep_alarm, ""));
        XTextView xTextView3 = (XTextView) findViewById2.findViewById(R.id.space_capsule_item_subtxt);
        this.mSleepAlarmSubTipsTv = xTextView3;
        xTextView3.setVisibility(0);
        this.mSleepAlarmSubTipsTv.setText(R.string.space_sleep_no_alarm);
        XTextView xTextView4 = (XTextView) findViewById2.findViewById(R.id.space_capsule_item_endurance);
        this.mSleepEnduranceTv = xTextView4;
        xTextView4.setVisibility(this.mIsSupportRemainingMileage ? 0 : 8);
        findViewById2.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.space.-$$Lambda$SpaceSleepControlView$Tdx9SER9QCAuNma1BiK1jinykhs
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                SpaceSleepControlView.this.lambda$initViews$1$SpaceSleepControlView(view2);
            }
        });
        View findViewById3 = view.findViewById(R.id.space_capsule_sleep_airconditiner_btn);
        View findViewById4 = findViewById3.findViewById(R.id.space_capsule_container_ac);
        this.mHvacAcView = findViewById4;
        findViewById4.setEnabled(false);
        View findViewById5 = findViewById3.findViewById(R.id.space_capsule_container_auto);
        this.mHvacAutoView = findViewById5;
        findViewById5.setEnabled(false);
        this.mHvacCircleView = (XImageView) findViewById3.findViewById(R.id.space_capsule_container_airmode);
        XToggleText xToggleText = (XToggleText) findViewById3.findViewById(R.id.space_capsule_window_air_btn);
        this.mWindowControlBtn = xToggleText;
        xToggleText.setOnCheckedChangeListener(new XToggleLayout.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.space.SpaceSleepControlView.1
            @Override // com.xiaopeng.xui.widget.toggle.XToggleLayout.OnCheckedChangeListener
            public void onCheckedChanged(XToggleLayout xToggleLayout, boolean b) {
            }

            @Override // com.xiaopeng.xui.widget.toggle.XToggleLayout.OnCheckedChangeListener
            public boolean onInterceptClickCheck(XToggleLayout xToggleLayout) {
                if (xToggleLayout.isPressed() && SpaceSleepControlView.this.mWinDoorViewModel != null) {
                    if (!SpaceSleepControlView.this.mWinDoorViewModel.isWindowLockActive()) {
                        if (SpaceSleepControlView.this.mWinDoorViewModel.getWinMoveStateData().getValue() == null || SpaceSleepControlView.this.mWinDoorViewModel.getWinMoveStateData().getValue().intValue() != 1) {
                            if (SpaceSleepControlView.this.mWinDoorViewModel.isWindowInitFailed(4)) {
                                return true;
                            }
                            if (!SpaceSleepControlView.this.mWinDoorViewModel.isWindowOpened()) {
                                SpaceSleepControlView.this.mWinDoorViewModel.controlWindowVent();
                                StatisticUtils.sendSpaceCapsuleStatistic(PageEnum.SPACE_CAPSULE_PAGE, BtnEnum.SPACE_CAPSULE_PAGE_CONTROL_WINDOW, 1, 1);
                            } else {
                                StatisticUtils.sendSpaceCapsuleStatistic(PageEnum.SPACE_CAPSULE_PAGE, BtnEnum.SPACE_CAPSULE_PAGE_CONTROL_WINDOW, 2, 1);
                                SpaceSleepControlView.this.mWinDoorViewModel.controlWindowClose();
                            }
                        } else {
                            LogUtils.i(SpaceSleepControlView.TAG, "Window is moving");
                            return true;
                        }
                    } else {
                        NotificationHelper.getInstance().showToast(R.string.win_lock_is_active);
                        return true;
                    }
                }
                return false;
            }
        });
        this.mHvacOff = findViewById3.findViewById(R.id.space_capsule_container_off);
        this.mTvTemperature = (XTextView) findViewById3.findViewById(R.id.space_capsule_container_temperature);
        this.mHvacCentiGrade = findViewById3.findViewById(R.id.space_capsule_container_temperature_unit);
        findViewById3.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.space.-$$Lambda$SpaceSleepControlView$OjqCDBPe41LZXOY0nJKDreKQxZo
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                SpaceSleepControlView.this.lambda$initViews$2$SpaceSleepControlView(view2);
            }
        });
        View findViewById6 = view.findViewById(R.id.space_capsule_sleep_fragrance_btn);
        if (CarBaseConfig.getInstance().isSupportSfs()) {
            findViewById6.setVisibility(0);
            ((XImageView) findViewById6.findViewById(R.id.space_capsule_item_icon)).setImageResource(R.drawable.space_capsule_fragrance);
            ((XTextView) findViewById6.findViewById(R.id.space_capsule_item_txt)).setText(R.string.space_capsule_fragrance);
            findViewById6.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.space.-$$Lambda$SpaceSleepControlView$66oy1fQ3r5MfJsprlIPjegr8UOc
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    SpaceSleepControlView.this.lambda$initViews$3$SpaceSleepControlView(view2);
                }
            });
            setViewLayoutParams(findViewById6, XuiUtils.dip2px(this.mContext.getResources().getDimension(R.dimen.space_capsule_item_width_little)));
            setViewLayoutParams(findViewById, XuiUtils.dip2px(this.mContext.getResources().getDimension(R.dimen.space_capsule_item_width_little)));
            setViewLayoutParams(findViewById2, XuiUtils.dip2px(this.mContext.getResources().getDimension(R.dimen.space_capsule_item_width_little)));
            setViewLayoutParams(findViewById3, XuiUtils.dip2px(this.mContext.getResources().getDimension(R.dimen.space_capsule_item_width_big)));
        } else {
            view.findViewById(R.id.space_capsule_blank3).setVisibility(8);
            findViewById6.setVisibility(8);
            setViewLayoutParams(findViewById, XuiUtils.dip2px(this.mContext.getResources().getDimension(R.dimen.space_capsule_item_width_small)));
            setViewLayoutParams(findViewById2, XuiUtils.dip2px(this.mContext.getResources().getDimension(R.dimen.space_capsule_item_width_small)));
            setViewLayoutParams(findViewById3, XuiUtils.dip2px(this.mContext.getResources().getDimension(R.dimen.space_capsule_item_width_middle)));
        }
        View findViewById7 = view.findViewById(R.id.space_capsule_protect_tip);
        if (findViewById7 != null) {
            findViewById7.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.space.-$$Lambda$SpaceSleepControlView$BXk5fwhNHIT1TeVFFkzqs8mOfiA
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    SpaceSleepControlView.this.lambda$initViews$4$SpaceSleepControlView(view2);
                }
            });
        }
    }

    public /* synthetic */ void lambda$initViews$0$SpaceSleepControlView(View v) {
        showSleepMusicDialog();
    }

    public /* synthetic */ void lambda$initViews$1$SpaceSleepControlView(View v) {
        if (TextUtils.isEmpty(this.mSleepTargetTime)) {
            showSleepAlarmDialog(true);
        } else {
            showSleepAlarmDialog(false);
        }
    }

    public /* synthetic */ void lambda$initViews$2$SpaceSleepControlView(View v) {
        this.mInterface.startHvac(this.mContext);
    }

    public /* synthetic */ void lambda$initViews$3$SpaceSleepControlView(View v) {
        this.mInterface.startFragrance(this.mContext);
    }

    public /* synthetic */ void lambda$initViews$4$SpaceSleepControlView(View v) {
        showSafeProtectDialog();
    }

    private void showSafeProtectDialog() {
        if (this.mSleepProtectDialog == null) {
            XDialog xDialog = new XDialog(this.mContext, (int) R.style.XDialogView);
            xDialog.setCustomView(LayoutInflater.from(this.mContext).inflate(R.layout.space_sleep_protect_dialog, xDialog.getContentView(), false));
            xDialog.setTitle(R.string.space_capsule_sleep_protect_dialog_title);
            xDialog.setCloseVisibility(true);
            xDialog.setCanceledOnTouchOutside(true);
            this.mSleepProtectDialog = xDialog;
        }
        this.mSleepProtectDialog.show();
    }

    private void refreshHvac() {
        HvacViewModel hvacViewModel;
        if (this.mHvacOff == null || (hvacViewModel = this.mHvacViewModel) == null) {
            return;
        }
        if (hvacViewModel.isHvacPowerModeOn()) {
            this.mHvacOff.setVisibility(8);
            this.mTvTemperature.setVisibility(0);
            this.mHvacCentiGrade.setVisibility(0);
            this.mTvTemperature.setText(this.mHvacViewModel.getHvacDriverTemp() + "");
            this.mHvacAcView.setEnabled(this.mHvacViewModel.isHvacAcModeOn());
            this.mHvacAutoView.setEnabled(this.mHvacViewModel.isHvacAutoModeOn());
            return;
        }
        this.mHvacOff.setVisibility(0);
        this.mTvTemperature.setVisibility(8);
        this.mHvacCentiGrade.setVisibility(8);
        this.mTvTemperature.setText("");
        this.mHvacAcView.setEnabled(false);
        this.mHvacAutoView.setEnabled(false);
    }

    private void refreshWindowBtn() {
        WindowDoorViewModel windowDoorViewModel;
        if (this.mWindowControlBtn == null || (windowDoorViewModel = this.mWinDoorViewModel) == null) {
            return;
        }
        float[] windowsPos = windowDoorViewModel.getWindowsPos();
        Integer value = this.mWinDoorViewModel.getWinMoveStateData().getValue();
        LogUtils.i(TAG, "windowState:" + Arrays.toString(windowsPos) + ",moveState:" + value);
        if (value != null && 1 == value.intValue()) {
            this.mWindowControlBtn.setLoading(true);
            return;
        }
        this.mWindowControlBtn.setLoading(false);
        if (this.mWinDoorViewModel.isWindowOpened()) {
            this.mWindowControlBtn.setText(this.mActivity.getString(R.string.space_capsule_window_off));
        } else {
            this.mWindowControlBtn.setText(this.mActivity.getString(R.string.space_cinema_window_air));
        }
    }

    private void refreshHvacCircle() {
        HvacViewModel hvacViewModel;
        if (this.mHvacCircleView == null || (hvacViewModel = this.mHvacViewModel) == null) {
            return;
        }
        int hvacCirculationMode = hvacViewModel.getHvacCirculationMode();
        if (hvacCirculationMode == 1) {
            this.mHvacCircleView.setImageResource(R.drawable.space_capsule_container_air);
        } else if (hvacCirculationMode == 6) {
            this.mHvacCircleView.setImageResource(R.drawable.space_capsule_container_air_a);
        } else {
            this.mHvacCircleView.setImageResource(R.drawable.space_capsule_container_air_f);
        }
    }

    public void init(LifecycleOwner lifecycleOwner) {
        this.mIsSupportRemainingMileage = BaseFeatureOption.getInstance().isSupportRemainingMileageInSleepMode();
        this.mViewModel = (SpaceCapsuleViewModel) ViewModelManager.getInstance().getViewModelImpl(ISpaceCapsuleViewModel.class);
        this.mSfsViewModel = (SfsViewModel) ViewModelManager.getInstance().getViewModelImpl(ISfsViewModel.class);
        this.mHvacViewModel = (HvacViewModel) ViewModelManager.getInstance().getViewModelImpl(IHvacViewModel.class);
        this.mWinDoorViewModel = (WindowDoorViewModel) ViewModelManager.getInstance().getViewModelImpl(IWindowDoorViewModel.class);
        this.mEnduranceViewModel = (EnduranceViewModel) ViewModelManager.getInstance().getViewModelImpl(IEnduranceViewModel.class);
        this.mViewModel.setMusicListener(this);
        LocalBroadcastManager.getInstance(this.mContext).registerReceiver(this.mAlarmReceiver, this.mAlarmIntentFilter);
        String spaceCapsuleSleepMusic = this.mViewModel.getSpaceCapsuleSleepMusic();
        LogUtils.i(TAG, "init mSleepMusic:" + spaceCapsuleSleepMusic);
        if (TextUtils.isEmpty(spaceCapsuleSleepMusic)) {
            spaceCapsuleSleepMusic = sMusicResources.get(0).path;
        }
        Iterator<MusicResource> it = sMusicResources.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            MusicResource next = it.next();
            if (spaceCapsuleSleepMusic.equals(next.path)) {
                this.mSelectResource = next;
                break;
            }
        }
        if (this.mSelectResource == null) {
            this.mSelectResource = sMusicResources.get(0);
        }
        LogUtils.i(TAG, "init mSelectResource:" + this.mSelectResource);
        webpDisplay();
        this.mSleepMusicTipsTv.setText(this.mSelectResource.nameRes);
        this.mMusicControlBtn.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.space.-$$Lambda$SpaceSleepControlView$8zCQj0o8CkuLl2wUr1zvvf5BNv4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SpaceSleepControlView.this.lambda$init$5$SpaceSleepControlView(view);
            }
        });
        this.mMusicControlBtn.setVisibility(0);
        if (this.mViewModel.isSpaceCapsuleSleepBgmOpen()) {
            playPauseMusic(this.mSelectResource);
        }
        if (CarBaseConfig.getInstance().isSupportSfs()) {
            this.mViewModel.reqSfsForSleep(true);
        }
        refreshHvac();
        refreshHvacCircle();
        refreshWindowBtn();
        this.mHvacViewModel.getHvacPowerData().observe(this.mActivity, new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.space.-$$Lambda$SpaceSleepControlView$TN4Prb-14ViK87PVHCAOdMvM2X0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SpaceSleepControlView.this.lambda$init$6$SpaceSleepControlView((Boolean) obj);
            }
        });
        this.mHvacViewModel.getHvacTempAcData().observe(this.mActivity, new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.space.-$$Lambda$SpaceSleepControlView$lAEMd1GsgX7C5rYebf0cOY-_TAU
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SpaceSleepControlView.this.lambda$init$7$SpaceSleepControlView((AcHeatNatureMode) obj);
            }
        });
        this.mHvacViewModel.getHvacTempDriverData().observe(this.mActivity, new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.space.-$$Lambda$SpaceSleepControlView$1u-ws_Zqmj6LiKFsKcu5440-q_M
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SpaceSleepControlView.this.lambda$init$8$SpaceSleepControlView((Float) obj);
            }
        });
        this.mHvacViewModel.getHvacAutoData().observe(this.mActivity, new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.space.-$$Lambda$SpaceSleepControlView$ks6nIUrT5AhaA_qCUi2Gn5AtXOg
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SpaceSleepControlView.this.lambda$init$9$SpaceSleepControlView((Boolean) obj);
            }
        });
        this.mHvacViewModel.getHvacCirculationData().observe(this.mActivity, new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.space.-$$Lambda$SpaceSleepControlView$Bnxk_E2hEssGEKvToXAO66ufEMc
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SpaceSleepControlView.this.lambda$init$10$SpaceSleepControlView((HvacCirculationMode) obj);
            }
        });
        this.mWinDoorViewModel.getWinMoveStateData().observe(this.mActivity, new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.space.-$$Lambda$SpaceSleepControlView$5KH-fNI8cK27NQA0IKqjH7u5Y7s
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SpaceSleepControlView.this.lambda$init$11$SpaceSleepControlView((Integer) obj);
            }
        });
        this.mHandler.removeCallbacks(this.mTtsPlayHandler);
        this.mHandler.postDelayed(this.mTtsPlayHandler, 2000L);
    }

    public /* synthetic */ void lambda$init$5$SpaceSleepControlView(View v) {
        playPauseMusic(this.mSelectResource);
    }

    public /* synthetic */ void lambda$init$6$SpaceSleepControlView(Boolean aBoolean) {
        refreshHvac();
    }

    public /* synthetic */ void lambda$init$7$SpaceSleepControlView(AcHeatNatureMode acHeatNatureMode) {
        refreshHvac();
    }

    public /* synthetic */ void lambda$init$8$SpaceSleepControlView(Float aFloat) {
        refreshHvac();
    }

    public /* synthetic */ void lambda$init$9$SpaceSleepControlView(Boolean aBoolean) {
        refreshHvac();
    }

    public /* synthetic */ void lambda$init$10$SpaceSleepControlView(HvacCirculationMode hvacCirculationMode) {
        refreshHvacCircle();
    }

    public /* synthetic */ void lambda$init$11$SpaceSleepControlView(Integer windowState) {
        refreshWindowBtn();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playTts() {
        if (this.mIsPlayTts) {
            return;
        }
        SpeechHelper.getInstance().speak(ResUtils.getString(R.string.space_capsule_enter_sleep_tips_new));
        this.mIsPlayTts = true;
    }

    private boolean isMusicPlaying(String source) {
        return this.mIsPlayingSource.equals(source) && this.mIsMusicPlaying;
    }

    private void musicControl(boolean play) {
        MusicResource musicResource = this.mSelectResource;
        if (musicResource != null) {
            this.mIsMusicPlaying = play;
            if (play) {
                this.mIsPlayingSource = musicResource.path;
                this.mViewModel.playMusic(this.mContext, this.mSelectResource.path, SpaceCapsuleViewModel.SLEEP_CAPSULE_PACKAGE);
                return;
            }
            this.mViewModel.pauseMusic(this.mContext, musicResource.path, SpaceCapsuleViewModel.SLEEP_CAPSULE_PACKAGE);
        }
    }

    private void musicUI(boolean play) {
        this.mIsMusicPlaying = play;
        if (!play) {
            this.mMusicIcon.setBackgroundResource(R.drawable.space_capsule_sleep_music);
            MusicIconViewHolder musicIconViewHolder = this.mMusicIconViewHolder;
            if (musicIconViewHolder != null) {
                musicIconViewHolder.playingIcon.setVisibility(8);
                return;
            }
            return;
        }
        this.mMusicIcon.setBackgroundResource(R.drawable.space_capsule_sleep_music_playing);
        MusicIconViewHolder musicIconViewHolder2 = this.mMusicIconViewHolder;
        if (musicIconViewHolder2 != null) {
            musicIconViewHolder2.playingIcon.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playPauseMusic(MusicResource resource) {
        if (isMusicPlaying(resource.path)) {
            musicControl(false);
            musicUI(false);
            this.mViewModel.setSpaceCapsuleSleepBgmOpen(false);
            return;
        }
        musicControl(true);
        musicUI(true);
        this.mViewModel.setSpaceCapsuleSleepBgmOpen(true);
    }

    private MusicIconViewHolder getMusicIconViewHolder(View parent, int resLayout, int imgRes, int textRes, String uriPath) {
        parent.findViewById(resLayout);
        MusicIconViewHolder musicIconViewHolder = new MusicIconViewHolder();
        musicIconViewHolder.layout = (ViewGroup) parent.findViewById(resLayout);
        musicIconViewHolder.musicIcon = (XImageView) musicIconViewHolder.layout.findViewById(R.id.space_sleep_music_image);
        musicIconViewHolder.musicLayout = (ViewGroup) musicIconViewHolder.layout.findViewById(R.id.space_sleep_music_icon_layout);
        musicIconViewHolder.musicIcon.setImageResource(imgRes);
        musicIconViewHolder.playingIcon = musicIconViewHolder.layout.findViewById(R.id.space_sleep_music_playing);
        musicIconViewHolder.musicText = (XTextView) musicIconViewHolder.layout.findViewById(R.id.space_sleep_music_text);
        musicIconViewHolder.musicText.setText(textRes);
        musicIconViewHolder.uriPath = uriPath;
        return musicIconViewHolder;
    }

    private void showSleepMusicDialog() {
        if (this.mSleepMusicDialog == null) {
            this.mSleepMusicDialog = new XDialog(this.mContext, 2131886921);
            View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.space_sleep_music_dialog, this.mSleepMusicDialog.getContentView(), false);
            int[] iArr = {R.id.space_sleep_music1, R.id.space_sleep_music2, R.id.space_sleep_music3, R.id.space_sleep_music4, R.id.space_sleep_music5, R.id.space_sleep_music6, R.id.space_sleep_music7, R.id.space_sleep_music8};
            this.mMusicViews.clear();
            int i = 0;
            while (true) {
                ArrayList<MusicResource> arrayList = sMusicResources;
                if (i >= arrayList.size()) {
                    break;
                }
                MusicIconViewHolder musicIconViewHolder = getMusicIconViewHolder(inflate, iArr[i], arrayList.get(i).iconRes, arrayList.get(i).nameRes, arrayList.get(i).path);
                musicIconViewHolder.layout.setOnClickListener(this.mMusicClickListener);
                this.mMusicViews.add(musicIconViewHolder);
                i++;
            }
            this.mMusicTimeTv = (XTextView) inflate.findViewById(R.id.space_sleep_music_time_tips);
            XTabLayout xTabLayout = (XTabLayout) inflate.findViewById(R.id.space_sleep_music_time_tab);
            this.mMusicTimeTabLayout = xTabLayout;
            xTabLayout.setOnTabChangeListener(this.mOnTimeTabLayoutChangeListener);
            this.mSleepMusicDialog.setCustomView(inflate);
            this.mSleepMusicDialog.setTitle(R.string.space_sleep_music_set);
            this.mSleepMusicDialog.setCloseVisibility(true);
            this.mSleepMusicDialog.getContentView().setBackgroundResource(R.drawable.space_capsule_dialog_bg);
        }
        int i2 = 0;
        while (true) {
            ArrayList<MusicResource> arrayList2 = sMusicResources;
            if (i2 >= arrayList2.size()) {
                i2 = 0;
                break;
            } else if (this.mSelectResource == arrayList2.get(i2)) {
                break;
            } else {
                i2++;
            }
        }
        this.mMusicIconViewHolder = this.mMusicViews.get(i2);
        musicSelected(this.mMusicViews.get(i2), true);
        if (isMusicPlaying(this.mSelectResource.path)) {
            this.mMusicIconViewHolder.playingIcon.setVisibility(0);
        } else {
            this.mMusicIconViewHolder.playingIcon.setVisibility(8);
        }
        this.mSleepMusicDialog.show();
        StatisticUtils.sendSpaceCapsuleStatistic(PageEnum.SPACE_CAPSULE_SLEEP_PAGE, BtnEnum.SPACE_CAPSULE_SLEEP_PAGE_MUSIC, new Object[0]);
    }

    private void showSleepAlarmDialog(boolean toSet) {
        if (this.mSleepAlarmDialog == null) {
            this.mSleepAlarmDialog = new XDialog(this.mContext, 2131886921);
        }
        if (toSet) {
            View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.space_sleep_time_confirm_dialog, this.mSleepAlarmDialog.getContentView(), false);
            this.mTimePicker = (XTimePicker) inflate.findViewById(R.id.space_sleep_time_picker);
            XTextView xTextView = (XTextView) inflate.findViewById(R.id.alarm_estimate_endurance_title);
            this.mAlarmEnduranceTitleTv = xTextView;
            xTextView.setVisibility(this.mIsSupportRemainingMileage ? 0 : 8);
            this.mSleepAlarmDialog.setPositiveButtonListener(new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.space.SpaceSleepControlView.2
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public void onClick(XDialog xDialog, int i) {
                    SpaceSleepControlView spaceSleepControlView = SpaceSleepControlView.this;
                    spaceSleepControlView.mSleepTargetHour = spaceSleepControlView.mTimePicker.getHour();
                    SpaceSleepControlView spaceSleepControlView2 = SpaceSleepControlView.this;
                    spaceSleepControlView2.mSleepTargetMin = spaceSleepControlView2.mTimePicker.getMinute();
                    SpaceSleepControlView spaceSleepControlView3 = SpaceSleepControlView.this;
                    StringBuilder sb = new StringBuilder();
                    SpaceSleepControlView spaceSleepControlView4 = SpaceSleepControlView.this;
                    StringBuilder append = sb.append(spaceSleepControlView4.fillZeroForTime(spaceSleepControlView4.mSleepTargetHour)).append(QuickSettingConstants.JOINER);
                    SpaceSleepControlView spaceSleepControlView5 = SpaceSleepControlView.this;
                    spaceSleepControlView3.mSleepTargetTime = append.append(spaceSleepControlView5.fillZeroForTime(spaceSleepControlView5.mSleepTargetMin)).toString();
                    LogUtils.d(SpaceSleepControlView.TAG, "onTimeChanged target:" + SpaceSleepControlView.this.mSleepTargetTime);
                    SpaceSleepControlView.this.mSleepAlarmSubTipsTv.setText(SpaceSleepControlView.this.mSleepTargetTime);
                    NotificationHelper notificationHelper = NotificationHelper.getInstance();
                    Context context = SpaceSleepControlView.this.mContext;
                    SpaceSleepControlView spaceSleepControlView6 = SpaceSleepControlView.this;
                    SpaceSleepControlView spaceSleepControlView7 = SpaceSleepControlView.this;
                    notificationHelper.showToast(context.getString(R.string.space_sleep_alarm_toast, spaceSleepControlView6.fillZeroForTime(spaceSleepControlView6.mTimePicker.getHour()), spaceSleepControlView7.fillZeroForTime(spaceSleepControlView7.mTimePicker.getMinute())));
                    SpaceSleepControlView.this.mViewModel.setAlarmTime(SpaceSleepControlView.this.mContext, SpaceSleepControlView.ALARM_PATH, SpaceSleepControlView.getAlarmTimestamp(SpaceSleepControlView.this.mTimePicker.getHour(), SpaceSleepControlView.this.mTimePicker.getMinute()));
                    SpaceSleepControlView.this.mEnduranceHandler.removeCallbacks(SpaceSleepControlView.this.mEnduranceRunnable);
                    SpaceSleepControlView.this.mEnduranceHandler.postDelayed(SpaceSleepControlView.this.mEnduranceRunnable, 500L);
                    StatisticUtils.sendSpaceCapsuleStatistic(PageEnum.SPACE_CAPSULE_SLEEP_PAGE, BtnEnum.SPACE_CAPSULE_SLEEP_PAGE_ALARM_TIME, SpaceSleepControlView.this.mSleepTargetTime);
                }
            });
            this.mSleepAlarmDialog.setCustomView(inflate);
            this.mSleepAlarmDialog.setTitle(R.string.space_sleep_music_alarm_time);
            this.mSleepAlarmDialog.setPositiveButton(R.string.btn_confirm);
            this.mSleepAlarmDialog.setNegativeButton(R.string.btn_cancel);
            this.mSleepAlarmDialog.setCloseVisibility(true);
            this.mSleepAlarmDialog.setNegativeButtonInterceptDismiss(false);
            this.mSleepAlarmDialog.setNegativeButtonListener(null);
            this.mSleepAlarmDialog.getContentView().setBackgroundResource(R.drawable.space_capsule_dialog_bg);
            this.mSleepAlarmDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.space.-$$Lambda$SpaceSleepControlView$Z4x-xTofgpFGDHIMjZfYaCwCxSQ
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    SpaceSleepControlView.this.lambda$showSleepAlarmDialog$12$SpaceSleepControlView(dialogInterface);
                }
            });
            Calendar calendar = Calendar.getInstance(this.mContext.getResources().getConfiguration().getLocales().get(0));
            this.mTimePicker.setHour(calendar.get(11));
            this.mTimePicker.setMinute(calendar.get(12));
            this.mTimePicker.setOnTimeChangedListener(new XTimePicker.OnTimeChangedListener() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.space.-$$Lambda$SpaceSleepControlView$pqYeHAEwN0-MvonNKv78y6ahyLo
                @Override // com.xiaopeng.xui.widget.timepicker.XTimePicker.OnTimeChangedListener
                public final void onTimeChanged(XTimePicker xTimePicker, int i, int i2) {
                    SpaceSleepControlView.this.lambda$showSleepAlarmDialog$13$SpaceSleepControlView(xTimePicker, i, i2);
                }
            });
        } else {
            View inflate2 = LayoutInflater.from(this.mContext).inflate(R.layout.space_sleep_time_set_dialog, this.mSleepAlarmDialog.getContentView(), false);
            this.mTargetTimeTv = (XTextView) inflate2.findViewById(R.id.space_sleep_alarm_time);
            this.mSleepAlarmDialog.setCustomView(inflate2);
            this.mSleepAlarmDialog.setTitle(R.string.space_sleep_music_alarm_time);
            this.mSleepAlarmDialog.setPositiveButton(R.string.space_sleep_music_alarm_close);
            this.mSleepAlarmDialog.setNegativeButton(R.string.space_sleep_music_alarm_change);
            this.mSleepAlarmDialog.setPositiveButtonListener(new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.space.SpaceSleepControlView.3
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public void onClick(XDialog xDialog, int i) {
                    StatisticUtils.sendSpaceCapsuleStatistic(PageEnum.SPACE_CAPSULE_SLEEP_PAGE, BtnEnum.SPACE_CAPSULE_SLEEP_PAGE_ALARM_CANCEL, SpaceSleepControlView.this.mSleepTargetTime);
                    SpaceSleepControlView.this.mSleepTargetTime = "";
                    SpaceSleepControlView.this.mSleepTargetHour = -1;
                    SpaceSleepControlView.this.mSleepTargetMin = -1;
                    SpaceSleepControlView.this.mSleepAlarmSubTipsTv.setText(R.string.space_sleep_no_alarm);
                    SpaceSleepControlView.this.mSleepEnduranceTv.setVisibility(8);
                    SpaceSleepControlView.this.mEnduranceHandler.removeCallbacks(SpaceSleepControlView.this.mEnduranceRunnable);
                    SpaceSleepControlView.this.stopAlarmForUI(false);
                }
            });
            this.mSleepAlarmDialog.setCloseVisibility(true);
            this.mSleepAlarmDialog.setNegativeButtonListener(new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.space.-$$Lambda$SpaceSleepControlView$jcnd6_TPVN5UbixpNUkqXJgiMSw
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i) {
                    SpaceSleepControlView.this.lambda$showSleepAlarmDialog$14$SpaceSleepControlView(xDialog, i);
                }
            });
            this.mSleepAlarmDialog.setNegativeButtonInterceptDismiss(true);
            this.mSleepAlarmDialog.getContentView().setBackgroundResource(R.drawable.space_capsule_dialog_bg);
            this.mSleepAlarmDialog.setOnDismissListener(null);
            this.mTargetTimeTv.setText(this.mSleepTargetTime);
        }
        this.mSleepAlarmDialog.show();
        StatisticUtils.sendSpaceCapsuleStatistic(PageEnum.SPACE_CAPSULE_SLEEP_PAGE, BtnEnum.SPACE_CAPSULE_SLEEP_PAGE_ALARM, new Object[0]);
    }

    public /* synthetic */ void lambda$showSleepAlarmDialog$12$SpaceSleepControlView(DialogInterface dialog) {
        this.mAlarmEnduranceHandler.removeCallbacks(this.mAlarEnduranceRunnable);
    }

    public /* synthetic */ void lambda$showSleepAlarmDialog$13$SpaceSleepControlView(XTimePicker xTimePicker, int hour, int minute) {
        this.mAlarmEnduranceHandler.removeCallbacks(this.mAlarEnduranceRunnable);
        this.mAlarmEnduranceHandler.postDelayed(this.mAlarEnduranceRunnable, 500L);
    }

    public /* synthetic */ void lambda$showSleepAlarmDialog$14$SpaceSleepControlView(XDialog xDialog, int i) {
        showSleepAlarmDialog(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String fillZeroForTime(int time) {
        return time < 10 ? "0" + time : String.valueOf(time);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopAlarmForUI(boolean isRestoreVolume) {
        this.mViewModel.stopAlarm(this.mContext, null);
        this.mViewModel.cancelAlarm(this.mContext);
        LogUtils.d(TAG, "sleep alarm stop ");
        this.mSleepTargetTime = "";
        this.mSleepTargetHour = -1;
        this.mSleepTargetMin = -1;
        this.mSleepEnduranceTv.setVisibility(8);
        this.mEnduranceHandler.removeCallbacks(this.mEnduranceRunnable);
        this.mSleepAlarmSubTipsTv.setText(R.string.space_sleep_no_alarm);
        if (isRestoreVolume) {
            this.mViewModel.changeAlarmVolume(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void musicSelected(MusicIconViewHolder viewHolder, boolean selected) {
        if (selected) {
            setMusicSelected(viewHolder.layout, true);
            viewHolder.playingIcon.setVisibility(0);
            viewHolder.musicLayout.setBackground(this.mContext.getDrawable(R.drawable.space_sleep_music_icon_bg));
            return;
        }
        setMusicSelected(viewHolder.layout, false);
        viewHolder.playingIcon.setVisibility(8);
        viewHolder.musicLayout.setBackground(null);
    }

    private void setMusicSelected(View v, boolean selected) {
        if (v instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) v;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View childAt = viewGroup.getChildAt(i);
                if (childAt instanceof ViewGroup) {
                    setMusicSelected((ViewGroup) childAt, selected);
                }
                childAt.setSelected(selected);
            }
            return;
        }
        v.setSelected(selected);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getPlayTimeTips(long playTime) {
        if (playTime == MUSIC_PLAY_TIME1) {
            return this.mContext.getString(R.string.space_sleep_music_timer_15);
        }
        if (playTime == MUSIC_PLAY_TIME2) {
            return this.mContext.getString(R.string.space_sleep_music_timer_30);
        }
        return playTime == 3600000 ? this.mContext.getString(R.string.space_sleep_music_timer_60) : this.mContext.getString(R.string.space_sleep_music_timer_custom, Integer.valueOf((int) Math.ceil(playTime / 60000.0d)));
    }

    @Override // com.xiaopeng.carcontrol.CapsuleMusicService.CapsuleMusicListener
    public void showAlarmDialog() {
        LogUtils.i(TAG, "sleep alarm ringing!!!");
        ScreenOnUtil.setXpIcmScreenOnOrOff(true);
        ScreenOnUtil.setDriverScreenOnOrOff(true);
        Intent intent = new Intent(this.mContext, CapsuleDialogService.class);
        intent.setAction(GlobalConstant.ACTION.ACTION_CAPSULE_SLEEP_ALARM_STOP_SHOW);
        intent.putExtra(CapsuleDialogService.SLEEP_ALARM_TIME, this.mSleepTargetTime);
        this.mContext.startService(intent);
        stopMusic();
        this.mViewModel.changeAlarmVolume(true);
    }

    @Override // com.xiaopeng.carcontrol.CapsuleMusicService.CapsuleMusicListener
    public void pauseByLossAudioFocus() {
        LogUtils.i(TAG, "music status:pause");
        musicUI(false);
    }

    @Override // com.xiaopeng.carcontrol.CapsuleMusicService.CapsuleMusicListener
    public void resumeByGainAudioFocus() {
        LogUtils.i(TAG, "music status:resume");
        musicUI(true);
    }

    @Override // com.xiaopeng.carcontrol.CapsuleMusicService.CapsuleMusicListener
    public void onPauseMusic() {
        this.mMusicControlBtn.setBackgroundResource(R.drawable.x_ic_small_play);
    }

    @Override // com.xiaopeng.carcontrol.CapsuleMusicService.CapsuleMusicListener
    public void onStartMusic() {
        this.mMusicControlBtn.setBackgroundResource(R.drawable.x_ic_small_suspend);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class OnTimeTabLayoutChangeListener implements XTabLayout.OnTabChangeListener {
        @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
        public boolean onInterceptTabChange(XTabLayout xTabLayout, int i, boolean b, boolean b1) {
            return false;
        }

        @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
        public void onTabChangeStart(XTabLayout xTabLayout, int i, boolean b, boolean b1) {
        }

        private OnTimeTabLayoutChangeListener() {
        }

        @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
        public void onTabChangeEnd(XTabLayout xTabLayout, int i, boolean b, boolean b1) {
            if (SpaceSleepControlView.this.mMusicTimeTabLayout == xTabLayout) {
                if (i == 1) {
                    SpaceSleepControlView.this.mCurrentPlayTime = SpaceSleepControlView.MUSIC_PLAY_TIME1;
                } else if (i == 2) {
                    SpaceSleepControlView.this.mCurrentPlayTime = SpaceSleepControlView.MUSIC_PLAY_TIME2;
                } else if (i != 3) {
                    SpaceSleepControlView.this.mCurrentPlayTime = -1L;
                } else {
                    SpaceSleepControlView.this.mCurrentPlayTime = 3600000L;
                }
                if (SpaceSleepControlView.this.mCurrentPlayTime == -1) {
                    SpaceSleepControlView.this.mMusicTimeTv.setVisibility(8);
                    if (SpaceSleepControlView.this.mMusicIconViewHolder != null) {
                        SpaceSleepControlView.this.mSleepMusicTipsTv.setText(SpaceSleepControlView.this.mMusicIconViewHolder.musicText.getText());
                    }
                    SpaceSleepControlView.this.mViewModel.stopMusicTimer();
                } else {
                    XTextView xTextView = SpaceSleepControlView.this.mSleepMusicTipsTv;
                    Context context = SpaceSleepControlView.this.mContext;
                    SpaceSleepControlView spaceSleepControlView = SpaceSleepControlView.this;
                    xTextView.setText(context.getString(R.string.space_sleep_music_sub_tips, spaceSleepControlView.getPlayTimeTips(spaceSleepControlView.mCurrentPlayTime)));
                    XTextView xTextView2 = SpaceSleepControlView.this.mMusicTimeTv;
                    Context context2 = SpaceSleepControlView.this.mContext;
                    SpaceSleepControlView spaceSleepControlView2 = SpaceSleepControlView.this;
                    xTextView2.setText(context2.getString(R.string.space_sleep_music_stop_time, spaceSleepControlView2.getPlayTimeTips(spaceSleepControlView2.mCurrentPlayTime)));
                    SpaceSleepControlView.this.mMusicTimeTv.setVisibility(0);
                    SpaceSleepControlView.this.mViewModel.startMusicTimer(SpaceSleepControlView.this.mCurrentPlayTime, SpaceSleepControlView.this.mCountDownTimeListener);
                }
                if (i != 0) {
                    StatisticUtils.sendSpaceCapsuleStatistic(PageEnum.SPACE_CAPSULE_SLEEP_PAGE, BtnEnum.SPACE_CAPSULE_SLEEP_PAGE_MUSIC_STOP_PLAY, Integer.valueOf(i));
                }
            }
        }
    }

    /* loaded from: classes2.dex */
    public class CountDownTimeListener implements SpaceCapsuleViewModel.ICountdownTimeListener {
        public CountDownTimeListener() {
        }

        @Override // com.xiaopeng.carcontrol.viewmodel.space.SpaceCapsuleViewModel.ICountdownTimeListener
        public void onTimeStop() {
            SpaceSleepControlView.this.stopMusic();
        }

        @Override // com.xiaopeng.carcontrol.viewmodel.space.SpaceCapsuleViewModel.ICountdownTimeListener
        public void onUpdateTime(long remainingTime) {
            SpaceSleepControlView.this.mSleepMusicTipsTv.setText(SpaceSleepControlView.this.mContext.getString(R.string.space_sleep_music_sub_tips, SpaceSleepControlView.this.getPlayTimeTips(remainingTime)));
            SpaceSleepControlView.this.mMusicTimeTv.setText(SpaceSleepControlView.this.mContext.getString(R.string.space_sleep_music_stop_time, SpaceSleepControlView.this.getPlayTimeTips(remainingTime)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class MusicClickListener implements View.OnClickListener {
        private MusicClickListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            if (v != SpaceSleepControlView.this.mMusicIconViewHolder.layout) {
                int i = 0;
                for (int i2 = 0; i2 < SpaceSleepControlView.this.mMusicViews.size(); i2++) {
                    SpaceSleepControlView spaceSleepControlView = SpaceSleepControlView.this;
                    spaceSleepControlView.musicSelected((MusicIconViewHolder) spaceSleepControlView.mMusicViews.get(i2), false);
                    if (v == ((MusicIconViewHolder) SpaceSleepControlView.this.mMusicViews.get(i2)).layout) {
                        SpaceSleepControlView spaceSleepControlView2 = SpaceSleepControlView.this;
                        spaceSleepControlView2.mMusicIconViewHolder = (MusicIconViewHolder) spaceSleepControlView2.mMusicViews.get(i2);
                        i = i2;
                    }
                }
                SpaceSleepControlView.this.mSelectResource = (MusicResource) SpaceSleepControlView.sMusicResources.get(i);
                if (SpaceSleepControlView.this.mMusicIconViewHolder != null) {
                    SpaceSleepControlView spaceSleepControlView3 = SpaceSleepControlView.this;
                    spaceSleepControlView3.musicSelected(spaceSleepControlView3.mMusicIconViewHolder, true);
                    SpaceSleepControlView.this.mSleepMusicTipsTv.setText(SpaceSleepControlView.this.mMusicIconViewHolder.musicText.getText());
                    if (SpaceSleepControlView.this.mCurrentPlayTime != -1) {
                        XTextView xTextView = SpaceSleepControlView.this.mSleepMusicTipsTv;
                        Context context = SpaceSleepControlView.this.mContext;
                        SpaceSleepControlView spaceSleepControlView4 = SpaceSleepControlView.this;
                        xTextView.setText(context.getString(R.string.space_sleep_music_sub_tips, spaceSleepControlView4.getPlayTimeTips(spaceSleepControlView4.mCurrentPlayTime)));
                    }
                }
                SpaceSleepControlView.this.mViewModel.setSpaceCapsuleSleepMusic(SpaceSleepControlView.this.mSelectResource.path);
                SpaceSleepControlView spaceSleepControlView5 = SpaceSleepControlView.this;
                spaceSleepControlView5.playPauseMusic(spaceSleepControlView5.mSelectResource);
                StatisticUtils.sendSpaceCapsuleStatistic(PageEnum.SPACE_CAPSULE_SLEEP_PAGE, BtnEnum.SPACE_CAPSULE_SLEEP_PAGE_SELECT_MUSIC, ResUtils.getString(SpaceSleepControlView.this.mSelectResource.nameRes));
                return;
            }
            SpaceSleepControlView spaceSleepControlView6 = SpaceSleepControlView.this;
            spaceSleepControlView6.playPauseMusic(spaceSleepControlView6.mSelectResource);
        }
    }

    private void webpDisplay() {
        this.mSleepIcon.setAnimation("capsule_anim_sleep.json");
        this.mSleepIcon.setRepeatCount(-1);
        this.mSleepIcon.playAnimation();
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.controlscene.space.AbsSpaceModeView
    public void onConfigurationChanged(Configuration newConfig) {
        if (isActive() && XThemeManager.isThemeChanged(newConfig)) {
            webpDisplay();
            refreshBg();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopMusic() {
        musicControl(false);
        this.mMusicIcon.setBackgroundResource(R.drawable.space_capsule_sleep_music);
        XTabLayout xTabLayout = this.mMusicTimeTabLayout;
        if (xTabLayout != null) {
            xTabLayout.selectTab(0, false);
        }
        MusicIconViewHolder musicIconViewHolder = this.mMusicIconViewHolder;
        if (musicIconViewHolder != null) {
            musicIconViewHolder.playingIcon.setVisibility(8);
        }
        this.mViewModel.stopMusicTimer();
    }

    private void leaveSleep() {
        LogUtils.i(TAG, "leave capsule sleep");
        if (!TextUtils.isEmpty(this.mSleepTargetTime)) {
            NotificationHelper.getInstance().showToast(R.string.space_sleep_alarm_invalid);
        }
        stopMusic();
        if (CarBaseConfig.getInstance().isSupportSfs()) {
            this.mViewModel.reqSfsForSleep(false);
        }
        this.mSleepTargetTime = "";
        this.mSleepTargetHour = -1;
        this.mSleepTargetMin = -1;
        this.mEnduranceHandler.removeCallbacks(this.mEnduranceRunnable);
        this.mSleepAlarmSubTipsTv.setText(R.string.space_sleep_no_alarm);
        this.mViewModel.stopAlarm(this.mContext, null);
        this.mViewModel.cancelAlarm(this.mContext);
        this.mViewModel.changeAlarmVolume(false);
        Intent intent = new Intent(this.mContext, CapsuleDialogService.class);
        intent.setAction(GlobalConstant.ACTION.ACTION_CAPSULE_SLEEP_ALARM_STOP_DISMISS_DIALOG);
        this.mContext.startService(intent);
        this.mViewModel.restoreCapsuleBrightness();
        this.mViewModel.requestMediaButton(false);
        dismissDialog();
        this.mViewModel.unBindService(this.mContext);
        this.mHandler.removeCallbacks(this.mTtsPlayHandler);
        LocalBroadcastManager.getInstance(this.mContext).unregisterReceiver(this.mAlarmReceiver);
        LottieAnimationView lottieAnimationView = this.mSleepIcon;
        if (lottieAnimationView == null || !lottieAnimationView.isAnimating()) {
            return;
        }
        this.mSleepIcon.cancelAnimation();
    }

    private void dismissDialog() {
        XDialog xDialog = this.mSleepMusicDialog;
        if (xDialog != null) {
            xDialog.dismiss();
            this.mSleepMusicDialog = null;
        }
        XDialog xDialog2 = this.mSleepAlarmDialog;
        if (xDialog2 != null) {
            xDialog2.dismiss();
            this.mSleepAlarmDialog = null;
        }
    }

    public void enter(LifecycleOwner lifecycleOwner, int sleepBedType) {
        super.enter(lifecycleOwner);
        this.mBedType = sleepBedType;
        refreshBg();
        init(lifecycleOwner);
        this.mViewModel.bindService(this.mContext, SpaceCapsuleViewModel.SLEEP_CAPSULE_PACKAGE);
        this.mViewModel.setCurrentSubMode(1);
        setActive(true);
        this.mViewModel.sendCapsuleSleepOverTimeRemind();
        this.mViewModel.requestMediaButton(true);
        LogUtils.i(TAG, "enter capsule sleep " + this.mBedType);
    }

    private void refreshBg() {
        if (this.mBedType == 2) {
            this.mRootView.setBackgroundResource(R.drawable.space_capsule_sleep_bg);
        } else {
            this.mRootView.setBackgroundResource(R.drawable.space_capsule_single_sleep_bg);
        }
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.controlscene.space.AbsSpaceModeView
    public void onPause() {
        super.onPause();
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.controlscene.space.AbsSpaceModeView
    public void exit() {
        super.exit();
        if (isActive()) {
            leaveSleep();
            setActive(false);
            this.mViewModel.cancelCapsuleSleepOverTimeRemind();
        }
    }

    public static long getAlarmTimestamp(int hour, int minutes) {
        Date date = new Date();
        date.setHours(hour);
        date.setMinutes(minutes);
        String str = TAG;
        LogUtils.d(str, "sleep alarm time:" + date.getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (System.currentTimeMillis() > date.getTime()) {
            calendar.add(5, 1);
        } else {
            calendar.add(5, 0);
        }
        calendar.set(11, hour);
        calendar.set(12, minutes);
        calendar.set(13, 0);
        Date time = calendar.getTime();
        LogUtils.d(str, "after sleep alarm time:" + time.getTime());
        return time.getTime();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class MusicIconViewHolder {
        public ViewGroup layout;
        public XImageView musicIcon;
        public ViewGroup musicLayout;
        public XTextView musicText;
        public View playingIcon;
        public String uriPath;

        private MusicIconViewHolder() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class MusicResource {
        int iconRes;
        int nameRes;
        String path;

        public MusicResource(String path, int nameRes, int iconRes) {
            this.path = path;
            this.nameRes = nameRes;
            this.iconRes = iconRes;
        }

        public String toString() {
            return "MusicResource{path='" + this.path + "', nameRes=" + this.nameRes + ", iconRes=" + this.iconRes + '}';
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public class StopAlarmReceiver extends BroadcastReceiver {
        StopAlarmReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            intent.getAction();
            LogUtils.i(SpaceSleepControlView.TAG, "StopAlarm Receiver");
            SpaceSleepControlView.this.stopAlarmForUI(true);
        }
    }

    public /* synthetic */ void lambda$new$15$SpaceSleepControlView() {
        if (this.mIsSupportRemainingMileage) {
            int hour = this.mTimePicker.getHour();
            int minute = this.mTimePicker.getMinute();
            LogUtils.i(TAG, "mAlarEnduranceRunnable hour: " + hour + ", min: " + minute);
            Calendar calendar = Calendar.getInstance(this.mContext.getResources().getConfiguration().getLocales().get(0));
            Date date = new Date();
            date.setHours(hour);
            date.setMinutes(minute);
            calendar.setTime(date);
            if (System.currentTimeMillis() > date.getTime()) {
                calendar.add(5, 1);
            }
            calendar.set(11, hour);
            calendar.set(12, minute);
            calendar.set(13, 0);
            calendar.set(14, 0);
            updateAlarmEnduranceView(this.mEnduranceViewModel.getEstimateEndurance(calendar.getTimeInMillis()));
        }
    }

    private void updateAlarmEnduranceView(int endurance) {
        this.mAlarmEnduranceTitleTv.setText(endurance > 20 ? ResUtils.getString(R.string.space_sleep_music_alarm_estimate_endurance_title, Integer.valueOf(endurance)) : ResUtils.getString(R.string.space_sleep_music_alarm_estimate_endurance_low));
        this.mAlarmEnduranceTitleTv.setTextColor(ResUtils.getColor(endurance > 60 ? R.color.x_theme_text_01_night : R.color.space_sleep_estimate_endurance_warning_text_color));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateEnduranceView(int endurance) {
        this.mSleepEnduranceTv.setVisibility(0);
        this.mSleepEnduranceTv.setText(endurance > 20 ? ResUtils.getString(R.string.space_sleep_endurance, Integer.valueOf(endurance)) : ResUtils.getString(R.string.space_sleep_music_alarm_estimate_endurance_low));
        this.mSleepEnduranceTv.setTextColor(ResUtils.getColor(endurance > 60 ? R.color.space_sleep_estimate_endurance_text_color : R.color.space_sleep_estimate_endurance_warning_text_color));
    }
}
