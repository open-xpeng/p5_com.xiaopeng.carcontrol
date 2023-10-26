package com.xiaopeng.carcontrol.view.fragment.spacecapsule;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.Observer;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.R;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.helper.SoundHelper;
import com.xiaopeng.carcontrol.statistic.BtnEnum;
import com.xiaopeng.carcontrol.statistic.PageEnum;
import com.xiaopeng.carcontrol.statistic.StatisticUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.view.fragment.controlscene.space.SpaceCinemaControlView;
import com.xiaopeng.carcontrol.view.fragment.controlscene.space.SpaceSleepControlView;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.sfs.ISfsViewModel;
import com.xiaopeng.carcontrol.viewmodel.sfs.SfsViewModel;
import com.xiaopeng.carcontrol.viewmodel.space.ISpaceCapsuleViewModel;
import com.xiaopeng.carcontrol.viewmodel.space.SpaceCapsuleViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.VcuViewModel;
import com.xiaopeng.spacecapsule.util.ScreenOnUtil;
import com.xiaopeng.xui.theme.XThemeManager;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XTextView;

/* loaded from: classes2.dex */
public class SpaceCapsuleFragment extends SpaceBaseFragment implements ISpaceCapsuleSlpCinInterface {
    private static final int ANIMA_TIMEOUT = 400;
    private static final int TIMEOUT_TIME = 10000;
    private XImageView mBatteryIcon;
    private ObjectAnimator mCinemaShowAnimator;
    private XTextView mEnduranceTitle;
    private ViewGroup mModeContainer;
    private int mModeIndex;
    private View mRootView;
    private SfsViewModel mSfsViewModel;
    private ObjectAnimator mSleepShowAnimator;
    private SpaceCinemaControlView mSpaceCinemaControlView;
    private View mSpaceCinemaRootView;
    private ISpaceCapsuleInterface mSpaceListener;
    private SpaceSleepControlView mSpaceSleepControlView;
    private View mSpaceSleepRootView;
    private SpaceCapsuleViewModel mSpaceViewModel;
    private XTextView mTimeTitle;
    private VcuViewModel mVcuViewModel;
    private int mSleepBedType = -1;
    private final Handler mHandler = new Handler();
    private final Runnable mTimeOutRunnable = new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.SpaceCapsuleFragment.1
        @Override // java.lang.Runnable
        public void run() {
            LogUtils.i(SpaceCapsuleFragment.this.TAG, "timeout mTimeOutRunnable start set brightness 1 ");
            if (SpaceCapsuleFragment.this.getActivity() == null || !SpaceCapsuleFragment.this.getActivity().hasWindowFocus()) {
                return;
            }
            SpaceCapsuleFragment.this.mSpaceViewModel.setCapsuleBrightnessLow();
        }
    };
    private final String TAG = SpaceCapsuleFragment.class.getSimpleName();

    @Override // com.xiaopeng.carcontrol.view.fragment.VuiFragment
    protected int getRootLayoutId() {
        return R.layout.fragment_space_capsule;
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.spacecapsule.SpaceBaseFragment
    protected void initViewModelObserver() {
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.spacecapsule.SpaceBaseFragment
    public void setClickSelected(int position) {
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.spacecapsule.SpaceBaseFragment
    protected boolean supportVui() {
        return false;
    }

    public static SpaceCapsuleFragment newInstance(int index, int sleepBed) {
        Bundle bundle = new Bundle();
        bundle.putInt(GlobalConstant.EXTRA.KEY_SPACE_SUB_MODE_INDEX, index);
        bundle.putInt(GlobalConstant.EXTRA.KEY_SPACE_CHOOSE_BED, sleepBed);
        SpaceCapsuleFragment spaceCapsuleFragment = new SpaceCapsuleFragment();
        spaceCapsuleFragment.setArguments(bundle);
        return spaceCapsuleFragment;
    }

    private SpaceCapsuleFragment() {
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.spacecapsule.SpaceBaseFragment, androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ISpaceCapsuleInterface) {
            this.mSpaceListener = (ISpaceCapsuleInterface) context;
        }
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.spacecapsule.SpaceBaseFragment, com.xiaopeng.carcontrol.view.fragment.VuiFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mSpaceCinemaControlView = new SpaceCinemaControlView(getContext(), getActivity(), this);
        this.mSpaceSleepControlView = new SpaceSleepControlView(getContext(), getActivity(), this);
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.VuiFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtils.i(this.TAG, "onCreateView");
        View onCreateView = super.onCreateView(inflater, container, savedInstanceState);
        this.mSpaceSleepRootView = this.mSpaceSleepControlView.onCreateView(inflater);
        this.mSpaceCinemaRootView = this.mSpaceCinemaControlView.onCreateView(inflater);
        initData();
        initViews(onCreateView);
        return onCreateView;
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.spacecapsule.SpaceBaseFragment
    protected void initViewModel() {
        this.mSpaceViewModel = (SpaceCapsuleViewModel) ViewModelManager.getInstance().getViewModelImpl(ISpaceCapsuleViewModel.class);
        this.mSfsViewModel = (SfsViewModel) ViewModelManager.getInstance().getViewModelImpl(ISfsViewModel.class);
        this.mVcuViewModel = (VcuViewModel) ViewModelManager.getInstance().getViewModelImpl(IVcuViewModel.class);
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.spacecapsule.SpaceBaseFragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void initData() {
        Bundle arguments = getArguments();
        this.mModeIndex = arguments.getInt(GlobalConstant.EXTRA.KEY_SPACE_SUB_MODE_INDEX, 1);
        this.mSleepBedType = arguments.getInt(GlobalConstant.EXTRA.KEY_SPACE_CHOOSE_BED, 2);
    }

    private void init() {
        setLiveDataObserver(this.mSpaceViewModel.getDatetimeModelMutableLiveData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$SpaceCapsuleFragment$wayW1uitRIDs9ru37rUD175GlKo
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SpaceCapsuleFragment.this.lambda$init$0$SpaceCapsuleFragment((String) obj);
            }
        });
        this.mSpaceViewModel.getSpeechStatus().setValue(-1);
        this.mSpaceViewModel.getSpeechStatus().observe(getActivity(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$SpaceCapsuleFragment$quu23SeoEkM43XDfI5Sry9PjrLw
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SpaceCapsuleFragment.this.lambda$init$1$SpaceCapsuleFragment((Integer) obj);
            }
        });
        this.mSpaceViewModel.onCreate();
        lambda$init$3$SpaceCapsuleFragment(Integer.valueOf(this.mVcuViewModel.getAvailableMileage()));
        refreshBattery(this.mVcuViewModel.getElectricityPercent());
        this.mVcuViewModel.getElectricityPercentData().observe(getActivity(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$SpaceCapsuleFragment$Su9ku0vD5l4ydKx_rrl7huiHx3g
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SpaceCapsuleFragment.this.lambda$init$2$SpaceCapsuleFragment((Integer) obj);
            }
        });
        this.mVcuViewModel.getAvailableMileageData().observe(getActivity(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$SpaceCapsuleFragment$1tGk4dqaKSKTGn4iAOXPPXbJGys
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SpaceCapsuleFragment.this.lambda$init$3$SpaceCapsuleFragment((Integer) obj);
            }
        });
        LogUtils.i(this.TAG, "current mode:" + this.mModeIndex);
        toSubMode(this.mModeIndex);
        PageEnum pageEnum = PageEnum.SPACE_CAPSULE_PAGE;
        BtnEnum btnEnum = BtnEnum.SPACE_CAPSULE_ENTER;
        Object[] objArr = new Object[1];
        objArr[0] = Integer.valueOf(this.mModeIndex != 1 ? 2 : 1);
        StatisticUtils.sendSpaceCapsuleStatistic(pageEnum, btnEnum, objArr);
    }

    public /* synthetic */ void lambda$init$0$SpaceCapsuleFragment(String time) {
        this.mTimeTitle.setText(time);
    }

    public /* synthetic */ void lambda$init$1$SpaceCapsuleFragment(Integer status) {
        LogUtils.i(this.TAG, "speech status:" + status);
        if (status.intValue() == 1) {
            cancelTimeOut("tts");
            ScreenOnUtil.setXpIcmScreenOnOrOff(true);
            ScreenOnUtil.setDriverScreenOnOrOff(true);
        } else if (status.intValue() == 2) {
            timeOut("tts");
        }
    }

    public /* synthetic */ void lambda$init$2$SpaceCapsuleFragment(Integer percent) {
        LogUtils.i(this.TAG, "getElectricityPercentData:" + percent);
        refreshBattery(percent.intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: refreshMileage */
    public void lambda$init$3$SpaceCapsuleFragment(Integer mileage) {
        XTextView xTextView = this.mEnduranceTitle;
        Object[] objArr = new Object[1];
        objArr[0] = Integer.valueOf(mileage == null ? this.mVcuViewModel.getAvailableMileage(true) : mileage.intValue());
        xTextView.setText(ResUtils.getString(R.string.drive_mileage_value, objArr));
    }

    private void refreshBattery(int percent) {
        if (percent < 0 || percent > 100) {
            return;
        }
        setBatteryImageResource(this.mSpaceViewModel.getElecPercent(percent));
    }

    private void setBatteryImageResource(int level) {
        int i;
        switch (level) {
            case 0:
            case 1:
                i = R.drawable.ic_sysbar_battery_10;
                break;
            case 2:
                i = R.drawable.ic_sysbar_battery_20;
                break;
            case 3:
                i = R.drawable.ic_sysbar_battery_30;
                break;
            case 4:
                i = R.drawable.ic_sysbar_battery_40;
                break;
            case 5:
                i = R.drawable.ic_sysbar_battery_50;
                break;
            case 6:
                i = R.drawable.ic_sysbar_battery_60;
                break;
            case 7:
                i = R.drawable.ic_sysbar_battery_70;
                break;
            case 8:
                i = R.drawable.ic_sysbar_battery_80;
                break;
            case 9:
                i = R.drawable.ic_sysbar_battery_90;
                break;
            case 10:
                i = R.drawable.ic_sysbar_battery_100;
                break;
            default:
                i = 0;
                break;
        }
        if (i != 0) {
            this.mBatteryIcon.setImageResource(i);
        }
    }

    private void initViews(View view) {
        this.mRootView = view.findViewById(R.id.root_view);
        this.mModeContainer = (ViewGroup) view.findViewById(R.id.space_capsule_mode_container);
        view.findViewById(R.id.space_capsule_exit_btn).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$SpaceCapsuleFragment$mNlrzqRRmtb0KDzz6MoMCUhGggQ
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                SpaceCapsuleFragment.this.lambda$initViews$4$SpaceCapsuleFragment(view2);
            }
        });
        this.mTimeTitle = (XTextView) view.findViewById(R.id.space_time_title);
        view.findViewById(R.id.close_iv).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$SpaceCapsuleFragment$VjqIyXxp6-Lt0uA9RuvnmMRI8GM
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                SpaceCapsuleFragment.this.lambda$initViews$5$SpaceCapsuleFragment(view2);
            }
        });
        this.mModeContainer.addView(this.mSpaceSleepRootView);
        this.mModeContainer.addView(this.mSpaceCinemaRootView);
        this.mSpaceSleepRootView.setVisibility(8);
        this.mSpaceCinemaRootView.setVisibility(8);
        XButton xButton = (XButton) view.findViewById(R.id.space_capsule_sleep_use_guide_btn);
        XButton xButton2 = (XButton) view.findViewById(R.id.space_capsule_screen_off_btn);
        XButton xButton3 = (XButton) view.findViewById(R.id.space_capsule_cinema_use_guide_btn);
        int i = this.mModeIndex;
        if (i == 1) {
            xButton.setVisibility(0);
            xButton2.setVisibility(0);
            xButton3.setVisibility(8);
            xButton.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$SpaceCapsuleFragment$ufoyJ4TTG4ekK5x5wfubK4dY6dM
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    SpaceCapsuleFragment.this.lambda$initViews$6$SpaceCapsuleFragment(view2);
                }
            });
            xButton2.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$SpaceCapsuleFragment$xyG-Kj-Kho-mZoqJR2I4w7Xm1L0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    SpaceCapsuleFragment.this.lambda$initViews$7$SpaceCapsuleFragment(view2);
                }
            });
        } else if (i == 2) {
            xButton.setVisibility(8);
            xButton2.setVisibility(8);
            xButton3.setVisibility(0);
            xButton3.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$SpaceCapsuleFragment$J1Nx1vJGXpBbSkEx7PCqFeP0-Dk
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    SpaceCapsuleFragment.this.lambda$initViews$8$SpaceCapsuleFragment(view2);
                }
            });
        }
        this.mEnduranceTitle = (XTextView) view.findViewById(R.id.space_endurance);
        this.mBatteryIcon = (XImageView) view.findViewById(R.id.space_battery);
    }

    public /* synthetic */ void lambda$initViews$4$SpaceCapsuleFragment(View v) {
        startToExitSpaceCapsule();
    }

    public /* synthetic */ void lambda$initViews$5$SpaceCapsuleFragment(View v) {
        startToExitSpaceCapsule();
    }

    public /* synthetic */ void lambda$initViews$6$SpaceCapsuleFragment(View v) {
        showUseGuide();
    }

    public /* synthetic */ void lambda$initViews$7$SpaceCapsuleFragment(View v) {
        screenOff();
    }

    public /* synthetic */ void lambda$initViews$8$SpaceCapsuleFragment(View v) {
        showUseGuide();
    }

    private void screenOff() {
        LogUtils.d(this.TAG, "screenOff");
        ScreenOnUtil.setXpIcmScreenOnOrOff(false);
        ScreenOnUtil.setDriverScreenOnOrOff(false);
    }

    private void showUseGuide() {
        int i = this.mModeIndex;
        if (i == 1) {
            startToUseGuide(false);
        } else if (i == 2) {
            startToUseGuide(true);
        }
    }

    private void toSubMode(int index) {
        LogUtils.d(this.TAG, "toSubMode index:" + index);
        if (index == this.mSpaceViewModel.getCurrentSubMode()) {
            return;
        }
        if (index == 1) {
            leaveCinema();
            switchToSleep();
        } else if (index == 2) {
            leaveSleep();
            switchToCinema();
        }
    }

    private void switchToSleep() {
        SoundHelper.play(SoundHelper.PATH_SPACE_CAPSULE_ENTER, true, false);
        this.mSpaceSleepRootView.setVisibility(0);
        startSleepAnimator();
        this.mSpaceSleepControlView.enter(this, this.mSleepBedType);
    }

    private void switchToCinema() {
        SoundHelper.play(SoundHelper.PATH_SPACE_CAPSULE_ENTER, true, false);
        this.mSpaceCinemaRootView.setVisibility(0);
        startCinemaAnimator();
        this.mSpaceCinemaControlView.enter(this);
    }

    private void leaveCinema() {
        reverseCinemaAnimator();
        this.mSpaceCinemaControlView.exit();
    }

    private void leaveSleep() {
        reverseSleepAnimator();
        this.mSpaceSleepControlView.exit();
    }

    private void startSleepAnimator() {
        if (this.mSleepShowAnimator == null) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mSpaceSleepRootView, "alpha", 0.0f, 1.0f);
            this.mSleepShowAnimator = ofFloat;
            ofFloat.setDuration(400L);
            this.mSleepShowAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.SpaceCapsuleFragment.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    LogUtils.d(SpaceCapsuleFragment.this.TAG, "mSleepShowAnimator onAnimationEnd sleep mIsSleepReversing:" + SpaceCapsuleFragment.this.mSpaceSleepRootView.getAlpha());
                    if (SpaceCapsuleFragment.this.mSpaceSleepRootView.getAlpha() == 1.0f) {
                        SpaceCapsuleFragment.this.mSpaceSleepRootView.setVisibility(0);
                        SpaceCapsuleFragment.this.mSpaceCinemaRootView.setVisibility(8);
                        return;
                    }
                    SpaceCapsuleFragment.this.mSpaceSleepRootView.setVisibility(8);
                    SpaceCapsuleFragment.this.mSpaceCinemaRootView.setVisibility(0);
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animation, boolean isReverse) {
                    LogUtils.i(SpaceCapsuleFragment.this.TAG, "mSleepShowAnimator onAnimationStart " + isReverse);
                }
            });
        }
        if (this.mSleepShowAnimator.isRunning()) {
            this.mSleepShowAnimator.reverse();
        } else {
            this.mSleepShowAnimator.start();
        }
    }

    private void startCinemaAnimator() {
        if (this.mCinemaShowAnimator == null) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mSpaceCinemaRootView, "alpha", 0.0f, 1.0f);
            this.mCinemaShowAnimator = ofFloat;
            ofFloat.setDuration(400L);
            this.mCinemaShowAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.SpaceCapsuleFragment.3
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    LogUtils.d(SpaceCapsuleFragment.this.TAG, "mCinemaShowAnimator onAnimationEnd cinema mIsCinemaReversing:" + SpaceCapsuleFragment.this.mSpaceCinemaRootView.getAlpha());
                    if (SpaceCapsuleFragment.this.mSpaceCinemaRootView.getAlpha() == 1.0f) {
                        SpaceCapsuleFragment.this.mSpaceSleepRootView.setVisibility(8);
                        SpaceCapsuleFragment.this.mSpaceCinemaRootView.setVisibility(0);
                        return;
                    }
                    SpaceCapsuleFragment.this.mSpaceSleepRootView.setVisibility(0);
                    SpaceCapsuleFragment.this.mSpaceCinemaRootView.setVisibility(8);
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animation, boolean isReverse) {
                    LogUtils.i(SpaceCapsuleFragment.this.TAG, "mCinemaShowAnimator onAnimationStart " + isReverse);
                }
            });
        }
        if (this.mCinemaShowAnimator.isRunning()) {
            this.mCinemaShowAnimator.reverse();
        } else {
            this.mCinemaShowAnimator.start();
        }
    }

    private void reverseSleepAnimator() {
        ObjectAnimator objectAnimator = this.mSleepShowAnimator;
        if (objectAnimator != null) {
            objectAnimator.reverse();
        }
    }

    private void reverseCinemaAnimator() {
        ObjectAnimator objectAnimator = this.mCinemaShowAnimator;
        if (objectAnimator != null) {
            objectAnimator.reverse();
        }
    }

    public void changeSubMode(int mode) {
        toSubMode(mode);
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.spacecapsule.SpaceBaseFragment, com.xiaopeng.carcontrol.view.fragment.VuiFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        timeOut("onResume");
        this.mSpaceSleepControlView.onResume();
        this.mSpaceCinemaControlView.onResume();
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.spacecapsule.SpaceBaseFragment, com.xiaopeng.carcontrol.view.fragment.VuiFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        this.mSpaceSleepControlView.onPause();
        this.mSpaceCinemaControlView.onPause();
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (XThemeManager.isThemeChanged(newConfig)) {
            this.mSpaceCinemaControlView.onConfigurationChanged(newConfig);
            this.mSpaceSleepControlView.onConfigurationChanged(newConfig);
        }
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.spacecapsule.ISpaceCapsuleSlpCinInterface
    public void startHvac(Context context) {
        try {
            Intent intent = new Intent(GlobalConstant.ACTION.ACTION_SHOW_HVAC_PANEL);
            intent.setFlags(270532608);
            if (!BaseFeatureOption.getInstance().isSupportFullscreenPanel()) {
                intent.addFlags(1024);
            }
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        PageEnum pageEnum = PageEnum.SPACE_CAPSULE_PAGE;
        BtnEnum btnEnum = BtnEnum.SPACE_CAPSULE_PAGE_ENTER_HVAC;
        Object[] objArr = new Object[1];
        objArr[0] = Integer.valueOf(this.mModeIndex != 1 ? 2 : 1);
        StatisticUtils.sendSpaceCapsuleStatistic(pageEnum, btnEnum, objArr);
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.spacecapsule.ISpaceCapsuleSlpCinInterface
    public void startFragrance(Context context) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.addFlags(268468224);
            intent.setData(Uri.parse("xiaopeng://aiot/device/detail?type=fragrance"));
            context.startActivity(intent);
        } catch (Exception unused) {
            LogUtils.e(this.TAG, "cant find fragrance app");
        }
        PageEnum pageEnum = PageEnum.SPACE_CAPSULE_PAGE;
        BtnEnum btnEnum = BtnEnum.SPACE_CAPSULE_PAGE_ENTER_SFS;
        Object[] objArr = new Object[1];
        objArr[0] = Integer.valueOf(this.mModeIndex != 1 ? 2 : 1);
        StatisticUtils.sendSpaceCapsuleStatistic(pageEnum, btnEnum, objArr);
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.spacecapsule.ISpaceCapsuleSlpCinInterface
    public void startToUseGuide(boolean toCinema) {
        Intent intent = new Intent();
        if (toCinema) {
            intent.addFlags(268468224);
            intent.setAction(GlobalConstant.ACTION.ACTION_START_CINEMA_USE_GUIDE);
            intent.putExtra(GlobalConstant.EXTRA.KEY_SPACE_CINEMA_GUIDE_FLAT, false);
            intent.addFlags(1024);
        } else {
            intent.putExtra(GlobalConstant.EXTRA.KEY_SPACE_SLEEP_GUIDE_FLAT, true);
            intent.putExtra(GlobalConstant.EXTRA.KEY_SPACE_SLEEP_GUIDE_AUTO, false);
            intent.putExtra(GlobalConstant.EXTRA.KEY_SPACE_CHOOSE_BED, this.mSleepBedType);
            intent.putExtra(GlobalConstant.EXTRA.KEY_USER_GUIDE_OPEN_FROM, "guideBtn");
            intent.setAction(GlobalConstant.ACTION.ACTION_SPACE_CAPSULE_GUIDE);
        }
        startActivity(intent);
    }

    private void startToExitSpaceCapsule() {
        ISpaceCapsuleInterface iSpaceCapsuleInterface = this.mSpaceListener;
        if (iSpaceCapsuleInterface != null) {
            iSpaceCapsuleInterface.onSpaceCapsuleRecover();
        }
    }

    protected void playSoundEffect(int index) {
        String str = index == 1 ? "/system/media/audio/xiaopeng/cdu/wav/CDU_drive_sport.wav" : index == 2 ? "/system/media/audio/xiaopeng/cdu/wav/CDU_drive_normal.wav" : "";
        if (TextUtils.isEmpty(str)) {
            return;
        }
        SoundHelper.play(str, true, false);
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.spacecapsule.SpaceBaseFragment, com.xiaopeng.carcontrol.view.fragment.VuiFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        cancelTimeOut("destroy");
        this.mSpaceCinemaControlView.exit();
        this.mSpaceSleepControlView.exit();
        this.mSpaceViewModel.onDestroy();
        if (this.mSpaceViewModel.isOpenSfs()) {
            LogUtils.i(this.TAG, "close sfs");
            this.mSfsViewModel.setSwEnable(false);
            this.mSpaceViewModel.setOpenSfs(false);
        }
        ObjectAnimator objectAnimator = this.mSleepShowAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        ObjectAnimator objectAnimator2 = this.mCinemaShowAnimator;
        if (objectAnimator2 != null) {
            objectAnimator2.cancel();
        }
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            timeOut("hasFocus");
        } else {
            cancelTimeOut("no focus");
        }
    }

    public void touchEvent(MotionEvent ev) {
        LogUtils.i(this.TAG, "touchEvent sleep mModeIndex:" + this.mModeIndex);
        if (ev.getAction() == 0) {
            cancelTimeOut("ACTION_DOWN");
        } else if (ev.getAction() == 1) {
            timeOut("ACTION_UP");
        }
    }

    private void cancelTimeOut(String from) {
        this.mHandler.removeCallbacks(this.mTimeOutRunnable);
        LogUtils.i(this.TAG, "cancel timeOut from:" + from);
        this.mSpaceViewModel.restoreCapsuleBrightness();
    }

    private void timeOut(String from) {
        this.mHandler.removeCallbacks(this.mTimeOutRunnable);
        this.mHandler.postDelayed(this.mTimeOutRunnable, 10000L);
        LogUtils.i(this.TAG, "timeOut start from:" + from);
    }
}
