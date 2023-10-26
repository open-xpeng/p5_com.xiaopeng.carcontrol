package com.xiaopeng.carcontrol.view.fragment.impl;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.CompoundButton;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.CarControlService;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.helper.ClickHelper;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.helper.SoundHelper;
import com.xiaopeng.carcontrol.speech.VuiManager;
import com.xiaopeng.carcontrol.statistic.BtnEnum;
import com.xiaopeng.carcontrol.statistic.PageEnum;
import com.xiaopeng.carcontrol.statistic.StatisticUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.view.dialog.DialogInfoHelper;
import com.xiaopeng.carcontrol.view.fragment.BaseFragment;
import com.xiaopeng.carcontrol.view.scene.AvasControlScene;
import com.xiaopeng.carcontrol.view.scene.WiperControlScene;
import com.xiaopeng.carcontrol.view.widget.CustomXSwitch;
import com.xiaopeng.carcontrol.view.widget.MeterMenuDialog;
import com.xiaopeng.carcontrol.viewmodel.audio.AudioViewModel;
import com.xiaopeng.carcontrol.viewmodel.audio.IAudioViewModel;
import com.xiaopeng.carcontrol.viewmodel.avas.AvasViewModel;
import com.xiaopeng.carcontrol.viewmodel.avas.BootSoundEffect;
import com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.MirrorReverseMode;
import com.xiaopeng.carcontrol.viewmodel.cabin.MirrorViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.TrunkState;
import com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorViewModel;
import com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyViewModel;
import com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel;
import com.xiaopeng.carcontrol.viewmodel.carsettings.UnlockResponse;
import com.xiaopeng.carcontrol.viewmodel.chassis.ChassisViewModel;
import com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel;
import com.xiaopeng.carcontrol.viewmodel.chassis.SteeringEpsMode;
import com.xiaopeng.carcontrol.viewmodel.chassis.TrailerHitchStatus;
import com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel;
import com.xiaopeng.carcontrol.viewmodel.lamp.LampViewModel;
import com.xiaopeng.carcontrol.viewmodel.lamp.LightMeHomeTime;
import com.xiaopeng.carcontrol.viewmodel.meter.DoorKeyForCustomer;
import com.xiaopeng.carcontrol.viewmodel.meter.IMeterViewModel;
import com.xiaopeng.carcontrol.viewmodel.meter.MeterViewModel;
import com.xiaopeng.carcontrol.viewmodel.meter.XKeyForCustomer;
import com.xiaopeng.carcontrol.viewmodel.scu.DsmState;
import com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel;
import com.xiaopeng.carcontrol.viewmodel.scu.ScuResponse;
import com.xiaopeng.carcontrol.viewmodel.scu.ScuViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.DriveMode;
import com.xiaopeng.carcontrol.viewmodel.vcu.GearLevel;
import com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.VcuViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.XSportDriveMode;
import com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel;
import com.xiaopeng.carcontrol.viewmodel.xpu.NedcState;
import com.xiaopeng.carcontrol.viewmodel.xpu.XpuViewModel;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import com.xiaopeng.speech.vui.utils.VuiUtils;
import com.xiaopeng.vui.commons.model.VuiEvent;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.theme.XThemeManager;
import com.xiaopeng.xui.utils.XViewLocationUtils;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XFrameLayout;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XListMultiple;
import com.xiaopeng.xui.widget.XListSingle;
import com.xiaopeng.xui.widget.XListTwo;
import com.xiaopeng.xui.widget.XLoading;
import com.xiaopeng.xui.widget.XSwitch;
import com.xiaopeng.xui.widget.XTabLayout;
import com.xiaopeng.xui.widget.XTextView;
import com.xiaopeng.xui.widget.slider.XSlider;
import org.eclipse.paho.client.mqttv3.internal.ClientDefaults;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class SettingsFragment extends BaseFragment {
    private static final int MAX_ANGLE = 100;
    private static final int MIN_ANGLE = 40;
    private static final int SETTING_TYPE_BELT = 6;
    private static final int SETTING_TYPE_CHILD_LOCK = 3;
    private static final int SETTING_TYPE_DRIVE = 5;
    private static final int SETTING_TYPE_KEY = 9;
    private static final int SETTING_TYPE_LOCK = 1;
    private static final int SETTING_TYPE_WELCOME = 4;
    private static final int SETTING_TYPE_WHEEL = 7;
    private static final int SETTING_TYPE_WINDOW = 2;
    private static final int SETTING_TYPE_WIPER = 8;
    private static final int SPEED_LIMIT_MAX = 120;
    private static final int SPEED_LIMIT_MIN = 40;
    private static final int SPEED_LIMIT_STEP = 10;
    private static final int STEP_ANGLE = 5;
    private static final String[] sElementDirectSupport = {"/carsettings/tip/ihb"};
    private ObjectAnimator fadeIn;
    private ObjectAnimator fadeOut;
    private XSwitch leftChildLockTB;
    private Animator.AnimatorListener mAnimListener;
    private AnimatorSet mAnimSet;
    private XTabLayout mAsSoftTab;
    private AudioViewModel mAudioVm;
    private AvasControlScene mAvasControlScene;
    private AvasViewModel mAvasViewModel;
    private XSwitch mBootEffectSw;
    private XTabLayout mBootEffectTab;
    private CarBodyViewModel mCarBodyViewModel;
    private XImageView mCarImg;
    private ChassisViewModel mChassisVm;
    private XTextView mChildModeDesTv;
    private XFrameLayout mChildModeLayout;
    private XImageView mChildModeTTv;
    private XTextView mChildModeTitleTv;
    private XTextView mCustomDoorKeyTv;
    private XTextView mCustomXkeyTv;
    private XSlider mDoorAngleSlider;
    private XListTwo mDrvEsbView;
    private XSwitch mDsmSw;
    private XTextView mEpbDescTv;
    private XFrameLayout mEpbLayout;
    private XLoading mEpbLoading;
    private XTextView mEpbTitleTv;
    private XButton mIhbBtn;
    private XSwitch mIhbSw;
    private XListSingle mIhbSwItem;
    private XDialog mInductionLockDialog;
    private XSwitch mInductionLockSwitch;
    private boolean mIsDrvEsbConfirmed;
    private LampViewModel mLampViewModel;
    private XTabLayout mLightMeHomeTab;
    private MeterMenuDialog mMeterDialog;
    private MeterViewModel mMeterViewModel;
    private XSwitch mMicrophoneSw;
    private MirrorViewModel mMirrorViewModel;
    private XDialog mNfcKeyConfirmDialog;
    private XSwitch mNfcKeySw;
    private XSwitch mPsnSeatWelcomeSw;
    private XDialog mPsnSrsConfirmDialog;
    private XSwitch mPsnSrsSw;
    private XSwitch mRearSeatWelcomeSw;
    private XListTwo mRsbWarningView;
    private XDialog mSafeBeltDialog;
    private ScuViewModel mScuViewModel;
    private SeatViewModel mSeatViewModel;
    private XSwitch mSeatWelcomeSw;
    private ViewGroup mSettingScrollLayout;
    private volatile View mSettingSubPanelView;
    private XSlider mSpdLimitSd;
    private int mTopCarResId;
    private XTextView mTrailerContent;
    private XFrameLayout mTrailerLayout;
    private XDialog mTrailerModeOpenConfirmDialog;
    private VcuViewModel mVcuViewModel;
    private XSwitch mWheelProtectSw;
    private WindowDoorViewModel mWindowDoorViewModel;
    private WiperControlScene mWiperControlScene;
    private XpuViewModel mXpuVm;
    private XSwitch rightChildLockTB;
    private XSwitch switchDrvEsb;
    private int operationType = 1;
    private Runnable mUpdateEpbViewRun = new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$TqupV01WQ6SBGG1gZ3a7Cye2syU
        @Override // java.lang.Runnable
        public final void run() {
            SettingsFragment.this.lambda$new$0$SettingsFragment();
        }
    };

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment
    protected boolean needPreLoadLayout() {
        return true;
    }

    public /* synthetic */ void lambda$new$0$SettingsFragment() {
        VcuViewModel vcuViewModel = this.mVcuViewModel;
        if (vcuViewModel == null || this.mChassisVm == null) {
            return;
        }
        updateEpbView(vcuViewModel.getGearLevelValue() != GearLevel.P, this.mChassisVm.getApbSystemStatus());
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment
    protected void initViewModels() {
        this.mSeatViewModel = (SeatViewModel) getViewModel(ISeatViewModel.class);
        this.mCarBodyViewModel = (CarBodyViewModel) getViewModel(ICarBodyViewModel.class);
        this.mWindowDoorViewModel = (WindowDoorViewModel) getViewModel(IWindowDoorViewModel.class);
        this.mMeterViewModel = (MeterViewModel) getViewModel(IMeterViewModel.class);
        this.mAvasViewModel = (AvasViewModel) getViewModel(IAvasViewModel.class);
        this.mChassisVm = (ChassisViewModel) getViewModel(IChassisViewModel.class);
        this.mVcuViewModel = (VcuViewModel) getViewModel(IVcuViewModel.class);
        this.mScuViewModel = (ScuViewModel) getViewModel(IScuViewModel.class);
        if (CarBaseConfig.getInstance().isSupportXpu()) {
            this.mXpuVm = (XpuViewModel) getViewModel(IXpuViewModel.class);
        }
        if (BaseFeatureOption.getInstance().isSupportLightMeHomeInSettings()) {
            this.mLampViewModel = (LampViewModel) getViewModel(ILampViewModel.class);
        }
        this.mMirrorViewModel = (MirrorViewModel) getViewModel(IMirrorViewModel.class);
        this.mAudioVm = (AudioViewModel) getViewModelSync(IAudioViewModel.class);
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment, com.xiaopeng.carcontrol.view.fragment.VuiFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment
    protected void resumeViews() {
        XSwitch xSwitch;
        XSwitch xSwitch2;
        XSwitch xSwitch3;
        XSwitch xSwitch4;
        BootSoundEffect bootSoundEffectValue = this.mAvasViewModel.getBootSoundEffectValue();
        if (bootSoundEffectValue != null) {
            initBootEffectControl(bootSoundEffectValue.ordinal());
        }
        if (this.mCustomXkeyTv != null) {
            XKeyForCustomer xKeyForCustomerValue = this.mMeterViewModel.getXKeyForCustomerValue();
            if (xKeyForCustomerValue != null) {
                this.mCustomXkeyTv.setText(getStringById(R.string.car_setting_x_key_tv_format, xKeyForCustomerValue.getTitle()));
            } else {
                this.mCustomXkeyTv.setText((CharSequence) null);
            }
        }
        if (this.mCustomDoorKeyTv != null) {
            DoorKeyForCustomer doorKeyForCustomerValue = this.mMeterViewModel.getDoorKeyForCustomerValue();
            if (doorKeyForCustomerValue != null) {
                this.mCustomDoorKeyTv.setText(getStringById(R.string.car_setting_x_key_tv_format, doorKeyForCustomerValue.getTitle()));
            } else {
                this.mCustomDoorKeyTv.setText((CharSequence) null);
            }
        }
        if (CarBaseConfig.getInstance().isSupportSdc() && this.mDoorAngleSlider != null) {
            updateDoorAngle(Integer.valueOf(this.mWindowDoorViewModel.getSdcMaxAutoDoorOpeningAngle()));
            this.mDoorAngleSlider.setEnabled(!this.mWindowDoorViewModel.isSdcRunning());
        }
        if (this.mSeatWelcomeSw != null) {
            boolean isWelcomeModeEnabled = this.mSeatViewModel.isWelcomeModeEnabled();
            LogUtils.d(this.TAG, "onResume, WelcomeModeEnabled : " + isWelcomeModeEnabled, false);
            this.mSeatWelcomeSw.setChecked(isWelcomeModeEnabled);
        }
        XSlider xSlider = this.mSpdLimitSd;
        if (xSlider != null) {
            xSlider.post(new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$5hrtpEyzkxdw11P3dDwHFRr9qjM
                @Override // java.lang.Runnable
                public final void run() {
                    SettingsFragment.this.lambda$resumeViews$1$SettingsFragment();
                }
            });
        }
        if (CarBaseConfig.getInstance().isSupportWheelKeyProtect() && (xSwitch4 = this.mWheelProtectSw) != null) {
            xSwitch4.post(new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$fpz0rIRjhOrZkYE3MhSpIdRsALw
                @Override // java.lang.Runnable
                public final void run() {
                    SettingsFragment.this.lambda$resumeViews$2$SettingsFragment();
                }
            });
        }
        if (CarBaseConfig.getInstance().isSupportChildLock() && (xSwitch3 = this.leftChildLockTB) != null && this.rightChildLockTB != null) {
            xSwitch3.setChecked(this.mCarBodyViewModel.isLeftChildLocked());
            this.rightChildLockTB.setChecked(this.mCarBodyViewModel.isRightChildLocked());
        }
        if (CarBaseConfig.getInstance().isSupportEsb() && (xSwitch2 = this.switchDrvEsb) != null) {
            xSwitch2.setChecked(this.mSeatViewModel.getEsbMode());
        }
        if (!CarBaseConfig.getInstance().isSupportRearSeatWelcomeMode() || (xSwitch = this.mRearSeatWelcomeSw) == null) {
            return;
        }
        xSwitch.post(new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$2swi6Zw9krFouxZk0nnc2pJUWKQ
            @Override // java.lang.Runnable
            public final void run() {
                SettingsFragment.this.lambda$resumeViews$3$SettingsFragment();
            }
        });
    }

    public /* synthetic */ void lambda$resumeViews$1$SettingsFragment() {
        updateSpdLimitValue(Integer.valueOf(this.mMeterViewModel.getSpeedWarningValue()));
    }

    public /* synthetic */ void lambda$resumeViews$2$SettingsFragment() {
        this.mWheelProtectSw.setChecked(this.mMeterViewModel.isWheelKeyProtectEnabled());
    }

    public /* synthetic */ void lambda$resumeViews$3$SettingsFragment() {
        this.mRearSeatWelcomeSw.setChecked(this.mSeatViewModel.isRearSeatWelcomeEnabled());
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment, com.xiaopeng.carcontrol.view.fragment.VuiFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        SoundHelper.stopAllSound();
        dismissAllDialogs();
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment, com.xiaopeng.carcontrol.view.fragment.VuiFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        ThreadUtils.removeRunnable(this.mUpdateEpbViewRun);
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.VuiFragment
    protected int getRootLayoutId() {
        return R.layout.fragment_settings;
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment
    protected int getPreLoadLayoutId() {
        return R.layout.fragment_settings_stub;
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment
    protected void initViews() {
        initSettingsLayout();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment
    public void showLayout(ViewGroup parent, View view) {
        super.showLayout(parent, view);
        if (parent == null || view == null || view.getParent() != parent) {
            return;
        }
        this.mCarImg = (XImageView) parent.findViewById(R.id.settings_car_view);
        if (BaseFeatureOption.getInstance().isSupportLeftCarImgChange()) {
            return;
        }
        this.mCarImg.setImageResource(App.getMainControlPageCarBodyImg());
    }

    private void initSettingsLayout() {
        ViewGroup viewGroup = (ViewGroup) this.mPreloadLayout.findViewById(R.id.settings_scroll_layout);
        this.mSettingScrollLayout = viewGroup;
        initLock(viewGroup);
        initWindow(this.mSettingScrollLayout);
        initChildLock(this.mSettingScrollLayout);
        if (BaseFeatureOption.getInstance().isSupportInflateTwice()) {
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$q5OEL2ALd9vCpf0Wan77loKRQ44
                @Override // java.lang.Runnable
                public final void run() {
                    SettingsFragment.this.inflateSubUiLayout();
                }
            });
        } else {
            initSettingsView();
        }
    }

    public void inflateSubUiLayout() {
        if (this.mSettingSubPanelView == null) {
            this.mSettingSubPanelView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_settings_stub_others, (ViewGroup) null, false);
        } else {
            LogUtils.d(this.TAG, "mSettingSubPanelView is not null");
        }
        ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$JJybjwsLFUGfgcqBiPIOcKJb-mY
            @Override // java.lang.Runnable
            public final void run() {
                SettingsFragment.this.lambda$inflateSubUiLayout$4$SettingsFragment();
            }
        });
    }

    public /* synthetic */ void lambda$inflateSubUiLayout$4$SettingsFragment() {
        if (this.mSettingSubPanelView != null) {
            LogUtils.w(this.TAG, "mSettingSubPanelView has been inflated yet");
            this.mSettingScrollLayout.addView(this.mSettingSubPanelView);
        }
        initSettingsView();
        updateVuiScene(this.mRootContainer);
    }

    private void initSettingsView() {
        initWelcome(this.mSettingScrollLayout);
        initDrive(this.mSettingScrollLayout);
        initAvas(this.mSettingScrollLayout);
        initAvasSpeaker(this.mSettingScrollLayout);
        initAirSuspension(this.mSettingScrollLayout);
        initBelt(this.mSettingScrollLayout);
        initTrunk(this.mSettingScrollLayout);
        initWheel(this.mSettingScrollLayout);
        initWiper(this.mSettingScrollLayout);
        initCwc(this.mSettingScrollLayout);
        initMeterAndKeys(this.mSettingScrollLayout);
        initOthers(this.mSettingScrollLayout);
        initIhbSwLayout(this.mSettingScrollLayout);
        initDsmSwLayout(this.mSettingScrollLayout);
        initMichophoneSwLayout(this.mSettingScrollLayout);
        initAsSoft(this.mSettingScrollLayout);
    }

    private void initIhbSwLayout(View parent) {
        if (BaseFeatureOption.getInstance().isSupportIhbInSettings()) {
            View findViewById = parent.findViewById(R.id.ihb_title_container);
            XImageView xImageView = (XImageView) parent.findViewById(R.id.ihb_info_iv);
            this.mIhbSwItem = (XListSingle) parent.findViewById(R.id.ihb_sw_item);
            findViewById.setVisibility(0);
            xImageView.setVisibility(0);
            this.mIhbSwItem.setVisibility(0);
            xImageView.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$r6irUszKLqj-otb_I_5O0gZmtk0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SettingsFragment.this.lambda$initIhbSwLayout$5$SettingsFragment(view);
                }
            });
            XButton xButton = (XButton) this.mIhbSwItem.findViewById(R.id.x_list_button);
            this.mIhbBtn = xButton;
            VuiUtils.addHasFeedbackProp(xButton);
            VuiUtils.addCanVoiceControlProp(this.mIhbBtn);
            this.mIhbBtn.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$bRfncKuTZJ_WE2ssYOFdmEhawdE
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SettingsFragment.this.lambda$initIhbSwLayout$6$SettingsFragment(view);
                }
            });
            XSwitch xSwitch = (XSwitch) this.mIhbSwItem.findViewById(R.id.x_list_sw);
            this.mIhbSw = xSwitch;
            VuiUtils.addHasFeedbackProp(xSwitch);
            VuiUtils.addCanVoiceControlProp(this.mIhbSw);
            this.mIhbSw.setVuiLabel(ResUtils.getString(R.string.ihb_title));
            this.mIhbSw.setOnInterceptListener(new XSwitch.OnInterceptListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$qPWzTSVNxSpiOEYq2AevXB1G0R8
                @Override // com.xiaopeng.xui.widget.XSwitch.OnInterceptListener
                public final boolean onInterceptCheck(View view, boolean z) {
                    return SettingsFragment.this.lambda$initIhbSwLayout$7$SettingsFragment(view, z);
                }
            });
            this.mIhbSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$2WufbFt2nf2k7S1UUP2eJ3PYR2A
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    SettingsFragment.this.lambda$initIhbSwLayout$8$SettingsFragment(compoundButton, z);
                }
            });
            if (!CarBaseConfig.getInstance().isSupportSrrMiss()) {
                updateIhbSwLayout(this.mScuViewModel.getXpuXpilotState());
                setLiveDataObserver(this.mScuViewModel.getXpuXPilotActiveData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$nB0ty5k0BYvCWD0yAOLH3c6NcuU
                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        SettingsFragment.this.lambda$initIhbSwLayout$9$SettingsFragment((Integer) obj);
                    }
                });
                setLiveDataObserver(this.mScuViewModel.getIhbData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$-Yb2XmT9Gl-rxWpilNCXG9nztWk
                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        SettingsFragment.this.lambda$initIhbSwLayout$10$SettingsFragment((ScuResponse) obj);
                    }
                });
                setLiveDataObserver(this.mXpuVm.getNedcStateData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$bNnzHnND5lILbwijIXo1LNzc5Fk
                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        SettingsFragment.this.lambda$initIhbSwLayout$11$SettingsFragment((NedcState) obj);
                    }
                });
                return;
            }
            this.mIhbSw.setChecked(false);
            updateIhbSwLayout(2);
        }
    }

    public /* synthetic */ void lambda$initIhbSwLayout$5$SettingsFragment(View v) {
        showIhbHelpInfo();
    }

    public /* synthetic */ void lambda$initIhbSwLayout$6$SettingsFragment(View v) {
        if (CarBaseConfig.getInstance().isSupportSrrMiss()) {
            NotificationHelper.getInstance().showToast(R.string.xpilot_srr_missed_open_fail);
            vuiFeedbackClick(R.string.xpilot_srr_missed_open_fail, this.mIhbBtn);
            return;
        }
        showIhbPurchaseHelpInfo();
    }

    public /* synthetic */ boolean lambda$initIhbSwLayout$7$SettingsFragment(View view, boolean b) {
        boolean z;
        if (view.isPressed()) {
            z = false;
        } else {
            z = isVuiAction(view, false);
            if (!z) {
                return false;
            }
        }
        if (ClickHelper.isFastClick(500L, false)) {
            return true;
        }
        if (CarBaseConfig.getInstance().isSupportSrrMiss()) {
            NotificationHelper.getInstance().showToast(R.string.xpilot_srr_missed_open_fail);
            vuiFeedbackClick(R.string.xpilot_srr_missed_open_fail, this.mIhbSw);
            return true;
        }
        return !confirmIhbFunc(view, z);
    }

    public /* synthetic */ void lambda$initIhbSwLayout$8$SettingsFragment(CompoundButton view, boolean isChecked) {
        if (view.isPressed() || isVuiAction(view)) {
            this.mScuViewModel.setIhbEnable(isChecked, false);
        }
    }

    public /* synthetic */ void lambda$initIhbSwLayout$9$SettingsFragment(Integer activeState) {
        if (activeState != null) {
            updateIhbSwLayout(activeState.intValue());
        }
    }

    public /* synthetic */ void lambda$initIhbSwLayout$10$SettingsFragment(ScuResponse state) {
        if (state != null) {
            updateIhbSwLayout(this.mScuViewModel.getXpuXpilotState());
        }
    }

    public /* synthetic */ void lambda$initIhbSwLayout$11$SettingsFragment(NedcState state) {
        updateIhbSwLayout(this.mScuViewModel.getXpuXpilotState());
    }

    private void showIhbHelpInfo() {
        if (ClickHelper.isFastClick(1000L)) {
            return;
        }
        ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$a3x5HgQud5MNq2GdNsMy8m7YAMo
            @Override // java.lang.Runnable
            public final void run() {
                SettingsFragment.this.lambda$showIhbHelpInfo$12$SettingsFragment();
            }
        });
    }

    public /* synthetic */ void lambda$showIhbHelpInfo$12$SettingsFragment() {
        DialogInfoHelper.getInstance().showXPilotInfoPanel(this.mContext, 17, (DialogInterface.OnShowListener) null, (DialogInterface.OnDismissListener) null);
    }

    private void showIhbPurchaseHelpInfo() {
        if (ClickHelper.isFastClick(1000L)) {
            return;
        }
        ThreadUtils.runOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$eFnUX77nnmQg4-_1mgyRIOEfYrA
            @Override // java.lang.Runnable
            public final void run() {
                DialogInfoHelper.getInstance().showInfoPanel(App.getInstance().getApplicationContext(), 29, null, null);
            }
        }, 150L);
    }

    private void updateIhbSwLayout(int xpilot3St) {
        boolean z = false;
        if (BaseFeatureOption.getInstance().isIhbDependOnXPilot() && xpilot3St == 2) {
            this.mIhbBtn.setVisibility(0);
            this.mIhbSw.setVisibility(8);
        } else {
            this.mIhbBtn.setVisibility(8);
            this.mIhbSw.setVisibility(0);
        }
        XSwitch xSwitch = this.mIhbSw;
        if (this.mXpuVm.getNedcState() != NedcState.On && this.mScuViewModel.getIhbState() == ScuResponse.ON) {
            z = true;
        }
        xSwitch.setChecked(z);
    }

    private boolean confirmIhbFunc(View view, boolean isVuiAction) {
        if (view.getVisibility() != 0 && isVuiAction) {
            vuiFeedback(R.string.ihb_unable_feedback, view);
            return false;
        }
        int xpuXpilotState = this.mScuViewModel.getXpuXpilotState();
        if (xpuXpilotState == 0) {
            NotificationHelper.getInstance().showToast(R.string.ihb_xpilot_st_init);
            if (isVuiAction) {
                vuiFeedback(R.string.ihb_xpilot_st_init, view);
            }
            return false;
        } else if (xpuXpilotState == 3) {
            NotificationHelper.getInstance().showToast(R.string.ihb_xpilot_st_not_activated);
            if (isVuiAction) {
                vuiFeedback(R.string.ihb_xpilot_st_not_activated, view);
            }
            return false;
        } else if (this.mScuViewModel.getIhbState() != ScuResponse.FAIL) {
            return checkIfXpuAvailable(view, isVuiAction);
        } else {
            NotificationHelper.getInstance().showToast(R.string.xpilot_open_fail);
            if (isVuiAction) {
                vuiFeedback(R.string.xpilot_open_fail, view);
            }
            return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x0030  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x003d A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean checkIfXpuAvailable(android.view.View r5, boolean r6) {
        /*
            r4 = this;
            java.lang.String r0 = "persist.sys.xiaopeng.XPU"
            boolean r0 = com.xiaopeng.carcontrol.config.CarBaseConfig.hasFeature(r0)
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L2d
            com.xiaopeng.carcontrol.viewmodel.xpu.XpuViewModel r0 = r4.mXpuVm
            com.xiaopeng.carcontrol.viewmodel.xpu.NedcState r0 = r0.getNedcState()
            if (r0 != 0) goto L13
            return r2
        L13:
            int[] r3 = com.xiaopeng.carcontrol.view.fragment.impl.SettingsFragment.AnonymousClass12.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$xpu$NedcState
            int r0 = r0.ordinal()
            r0 = r3[r0]
            if (r0 == r2) goto L2a
            r3 = 2
            if (r0 == r3) goto L2a
            r3 = 3
            if (r0 == r3) goto L2a
            r3 = 4
            if (r0 == r3) goto L27
            goto L2d
        L27:
            int r0 = com.xiaopeng.carcontrolmodule.R.string.xpilot_open_fail_xpu_turning_on
            goto L2e
        L2a:
            int r0 = com.xiaopeng.carcontrolmodule.R.string.xpilot_open_fail_xpu_off
            goto L2e
        L2d:
            r0 = r1
        L2e:
            if (r0 == 0) goto L3d
            com.xiaopeng.carcontrol.helper.NotificationHelper r2 = com.xiaopeng.carcontrol.helper.NotificationHelper.getInstance()
            r2.showToast(r0)
            if (r6 == 0) goto L3c
            r4.vuiFeedback(r0, r5)
        L3c:
            return r1
        L3d:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.view.fragment.impl.SettingsFragment.checkIfXpuAvailable(android.view.View, boolean):boolean");
    }

    private void initDsmSwLayout(ViewGroup parent) {
        if (CarBaseConfig.getInstance().isSupportDsmCamera() || CarBaseConfig.getInstance().isSupportRemoteCamera()) {
            parent.findViewById(R.id.dsm_camera_title_tv).setVisibility(0);
        }
        if (CarBaseConfig.getInstance().isSupportDsmCamera()) {
            XListTwo xListTwo = (XListTwo) parent.findViewById(R.id.dsm_camera_sw);
            if (BaseFeatureOption.getInstance().isSupportDsmRegulation()) {
                xListTwo.setText(ResUtils.getString(R.string.fatigue_reminder_title_tv));
                xListTwo.setTextSub(ResUtils.getString(R.string.fatigue_reminder_content_tv));
            }
            xListTwo.setVisibility(0);
            XSwitch xSwitch = (XSwitch) xListTwo.findViewById(R.id.x_list_sw);
            this.mDsmSw = xSwitch;
            VuiUtils.addHasFeedbackProp(xSwitch);
            VuiUtils.addCanVoiceControlProp(this.mDsmSw);
            this.mDsmSw.setVuiLabel(ResUtils.getString(R.string.ciu_inner_camera_title_tv));
            this.mDsmSw.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$mQAKITl64auWdkktY-ooXSolUa4
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return SettingsFragment.this.lambda$initDsmSwLayout$15$SettingsFragment(view, motionEvent);
                }
            });
            if (CarBaseConfig.getInstance().isSupportSrrMiss()) {
                this.mDsmSw.setChecked(false);
            } else if (CarBaseConfig.getInstance().isVpmNotReady()) {
                this.mDsmSw.setChecked(false);
            } else {
                updateDsmCameraSt(this.mScuViewModel.getDsmState());
                setLiveDataObserver(this.mScuViewModel.getDsmStateData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$ypRknNZpgQG9DzllAJ3ZAnxquKc
                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        SettingsFragment.this.updateDsmCameraSt((DsmState) obj);
                    }
                });
            }
        }
        if (CarBaseConfig.getInstance().isSupportRemoteCamera()) {
            XListTwo xListTwo2 = (XListTwo) parent.findViewById(R.id.remote_camera_sw);
            xListTwo2.setVisibility(0);
            XSwitch xSwitch2 = (XSwitch) xListTwo2.findViewById(R.id.x_list_sw);
            xSwitch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$Xw6BKl6_Hsk2rRCWtUwynl-QIfk
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    SettingsFragment.this.lambda$initDsmSwLayout$16$SettingsFragment(compoundButton, z);
                }
            });
            xSwitch2.setChecked(this.mScuViewModel.getRemoteCameraSw());
        }
    }

    public /* synthetic */ boolean lambda$initDsmSwLayout$15$SettingsFragment(View v, MotionEvent event) {
        if (event.getAction() != 1) {
            return false;
        }
        if (ClickHelper.isFastClick(500L, false)) {
            LogUtils.i(this.TAG, "Click too fast", false);
        } else if (CarBaseConfig.getInstance().isSupportSrrMiss()) {
            NotificationHelper.getInstance().showToast(R.string.xpilot_srr_missed_open_fail);
            vuiFeedbackClick(R.string.xpilot_srr_missed_open_fail, this.mDsmSw);
        } else if (CarBaseConfig.getInstance().isVpmNotReady()) {
            NotificationHelper.getInstance().showToast(R.string.xpilot_vpm_not_ready_open_fail);
            vuiFeedbackClick(R.string.xpilot_vpm_not_ready_open_fail, this.mDsmSw);
        } else {
            int i = AnonymousClass12.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$DsmState[this.mScuViewModel.getDsmState().ordinal()];
            if (i == 1) {
                NotificationHelper.getInstance().showToast(R.string.ciu_inner_camera_failure);
                vuiFeedbackClick(R.string.ciu_inner_camera_failure, this.mDsmSw);
                return true;
            } else if (i == 2) {
                NotificationHelper.getInstance().showToast(R.string.ciu_inner_camera_turning_on);
                vuiFeedbackClick(R.string.ciu_inner_camera_turning_on, this.mDsmSw);
                return true;
            } else if (!this.mDsmSw.isChecked()) {
                this.mScuViewModel.setDsmSw(true);
            } else {
                showDialog(BaseFeatureOption.getInstance().isSupportDsmRegulation() ? R.string.fatigue_reminder_title_tv : R.string.ciu_inner_camera_title_tv, BaseFeatureOption.getInstance().isSupportDsmRegulation() ? R.string.fatigue_reminder_confirm : R.string.ciu_inner_camera_feature_feedback_new, getStringById(R.string.btn_cancel), (XDialogInterface.OnClickListener) null, getStringById(R.string.btn_ok), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$gXXE2oc7g-JL8NHwF1oqpPy0DjY
                    @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                    public final void onClick(XDialog xDialog, int i2) {
                        SettingsFragment.this.lambda$null$14$SettingsFragment(xDialog, i2);
                    }
                }, VuiManager.SCENE_DSM_SETTING);
            }
        }
        return true;
    }

    public /* synthetic */ void lambda$null$14$SettingsFragment(XDialog xDialog, int i) {
        this.mScuViewModel.setDsmSw(false);
        this.mDsmSw.setChecked(false);
    }

    public /* synthetic */ void lambda$initDsmSwLayout$16$SettingsFragment(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed()) {
            this.mScuViewModel.setRemoteCameraSw(isChecked);
        }
    }

    public void updateDsmCameraSt(DsmState dsmState) {
        XSwitch xSwitch;
        if (dsmState == null || (xSwitch = this.mDsmSw) == null) {
            return;
        }
        xSwitch.setChecked(dsmState == DsmState.On || dsmState == DsmState.TurnOning);
        if (dsmState == DsmState.Failure) {
            NotificationHelper.getInstance().showToast(R.string.ciu_inner_camera_failure);
        }
    }

    private void initTrunk(View parent) {
        if (CarBaseConfig.getInstance().isSupportSensorTrunk() || CarBaseConfig.getInstance().isSupportTrunkSetPosition()) {
            boolean z = false;
            parent.findViewById(R.id.settings_trunk_tv).setVisibility(0);
            if (CarBaseConfig.getInstance().isSupportSensorTrunk()) {
                View findViewById = parent.findViewById(R.id.trunk_sensor_item);
                findViewById.setVisibility(0);
                XSwitch xSwitch = (XSwitch) findViewById.findViewById(R.id.x_list_sw);
                xSwitch.setChecked(this.mWindowDoorViewModel.isTrunkSensorEnable());
                xSwitch.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$ajLTZodiYQOrwKnfrJfLAf6XfVc
                    @Override // android.view.View.OnTouchListener
                    public final boolean onTouch(View view, MotionEvent motionEvent) {
                        return SettingsFragment.lambda$initTrunk$17(view, motionEvent);
                    }
                });
                xSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$3VKLIXvoT2Nni1BcnJsx5LWY7Oc
                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
                    public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                        SettingsFragment.this.lambda$initTrunk$18$SettingsFragment(compoundButton, z2);
                    }
                });
                LiveData<Boolean> trunkSensorData = this.mWindowDoorViewModel.getTrunkSensorData();
                xSwitch.getClass();
                setLiveDataObserver(trunkSensorData, new $$Lambda$SettingsFragment$aeIiqtRaGva9_dxYD21QFFdegA(xSwitch));
                if (!CarBaseConfig.getInstance().isSupportTrunkSetPosition()) {
                    findViewById.setBackground(ResUtils.getDrawable(R.drawable.sub_item_bg));
                }
            }
            if (CarBaseConfig.getInstance().isSupportTrunkSetPosition()) {
                View findViewById2 = parent.findViewById(R.id.trunk_angle_setting_item);
                findViewById2.setVisibility(0);
                final XSlider xSlider = (XSlider) findViewById2.findViewById(R.id.trunk_angle_slider);
                xSlider.setCurrentIndex(this.mWindowDoorViewModel.getTrunkFullOpenPos() / 10);
                xSlider.setSliderProgressListener(new XSlider.SliderProgressListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.SettingsFragment.1
                    @Override // com.xiaopeng.xui.widget.slider.XSlider.SliderProgressListener
                    public void onStartTrackingTouch(XSlider xSlider2) {
                    }

                    @Override // com.xiaopeng.xui.widget.slider.XSlider.SliderProgressListener
                    public void onStopTrackingTouch(XSlider xSlider2) {
                    }

                    {
                        SettingsFragment.this = this;
                    }

                    @Override // com.xiaopeng.xui.widget.slider.XSlider.SliderProgressListener
                    public void onProgressChanged(XSlider xSlider2, float progress, String unit) {
                        SettingsFragment.this.mWindowDoorViewModel.setTrunkFullOpenPos(((int) progress) * 10);
                    }
                });
                TrunkState rearTrunkStateValue = this.mWindowDoorViewModel.getRearTrunkStateValue();
                if (rearTrunkStateValue != TrunkState.Closing && rearTrunkStateValue != TrunkState.Opening) {
                    z = true;
                }
                xSlider.setEnabled(z);
                setLiveDataObserver(this.mWindowDoorViewModel.getTrunkFullOpenPosData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$3K1ACFLUuArMNmCV-ys1f5m1P_o
                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        XSlider.this.setCurrentIndex(((Integer) obj).intValue() / 10);
                    }
                });
                setLiveDataObserver(this.mWindowDoorViewModel.getRearTrunkData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$hC-xqWYjJ0wizSc7iO0nQJ_x7oc
                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        TrunkState trunkState = (TrunkState) obj;
                        XSlider.this.setEnabled((trunkState == TrunkState.Closing || trunkState == TrunkState.Opening) ? false : true);
                    }
                });
                if (CarBaseConfig.getInstance().isSupportSensorTrunk()) {
                    return;
                }
                findViewById2.setBackground(ResUtils.getDrawable(R.drawable.sub_item_bg));
            }
        }
    }

    public static /* synthetic */ boolean lambda$initTrunk$17(View v, MotionEvent event) {
        return event.getAction() == 0 && ClickHelper.isFastClick(v.getId(), 1000L, false);
    }

    public /* synthetic */ void lambda$initTrunk$18$SettingsFragment(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed() || isVuiAction(buttonView)) {
            this.mWindowDoorViewModel.setTrunkSensorEnable(isChecked);
        }
    }

    private void initMichophoneSwLayout(ViewGroup parent) {
        XSwitch xSwitch = (XSwitch) ((XListTwo) parent.findViewById(R.id.settings_microphone_sw)).findViewById(R.id.x_list_sw);
        this.mMicrophoneSw = xSwitch;
        xSwitch.setVuiLabel(ResUtils.getString(R.string.settings_microphone_title));
        this.mMicrophoneSw.setOnInterceptListener(new XSwitch.OnInterceptListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$I1wrgnlMTYyNayIAKgMGIePGrgE
            @Override // com.xiaopeng.xui.widget.XSwitch.OnInterceptListener
            public final boolean onInterceptCheck(View view, boolean z) {
                return SettingsFragment.this.lambda$initMichophoneSwLayout$21$SettingsFragment(view, z);
            }
        });
        updateMicrophoneSw(this.mAudioVm.isMicrophoneMute());
        setLiveDataObserver(this.mAudioVm.getMicrophoneMuteData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$11UkyXuq_P3ml9dON0FHlvD-U28
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SettingsFragment.this.updateMicrophoneSw(((Boolean) obj).booleanValue());
            }
        });
    }

    public /* synthetic */ boolean lambda$initMichophoneSwLayout$21$SettingsFragment(View view, boolean isChecked) {
        if (view.isPressed() || isVuiAction(view)) {
            if (ClickHelper.isFastClick(500L, false)) {
                LogUtils.i(this.TAG, "Click too fast", false);
                return true;
            } else if (isChecked) {
                this.mAudioVm.setMicrophoneMute(false);
                return false;
            } else {
                this.mAudioVm.confirmMuteMicrophone();
                return true;
            }
        }
        return false;
    }

    public void updateMicrophoneSw(boolean isMute) {
        XSwitch xSwitch = this.mMicrophoneSw;
        if (xSwitch != null) {
            xSwitch.setChecked(!isMute);
        }
    }

    private void initAsSoft(View parent) {
        if (CarBaseConfig.getInstance().isSupportCdcControl()) {
            parent.findViewById(R.id.as_soft_item).setVisibility(0);
            XTabLayout xTabLayout = (XTabLayout) parent.findViewById(R.id.as_soft_tab);
            this.mAsSoftTab = xTabLayout;
            xTabLayout.setOnTabChangeListener(new XTabLayout.OnTabChangeListenerAdapter() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.SettingsFragment.2
                {
                    SettingsFragment.this = this;
                }

                @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListenerAdapter, com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
                public boolean onInterceptTabChange(XTabLayout tabLayout, int index, boolean tabChange, boolean fromUser) {
                    if (fromUser && ClickHelper.isFastClick(tabLayout.getId(), 1000L, false)) {
                        NotificationHelper.getInstance().showToast(R.string.operation_fast_tips);
                        return true;
                    }
                    return false;
                }

                @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
                public void onTabChangeEnd(XTabLayout xTabLayout2, int index, boolean tabChange, boolean fromUser) {
                    if (fromUser) {
                        if (index == 0) {
                            SettingsFragment.this.mChassisVm.setCdcMode(2);
                        } else if (index == 1) {
                            SettingsFragment.this.mChassisVm.setCdcMode(1);
                        } else if (index != 2) {
                        } else {
                            SettingsFragment.this.mChassisVm.setCdcMode(3);
                        }
                    }
                }
            });
            updateSoftTab(this.mChassisVm.getCdcMode());
            setLiveDataObserver(this.mChassisVm.getCdcModeData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$1FqsElj29vAnZnF7HmPEr2GJRNs
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SettingsFragment.this.updateSoftTab(((Integer) obj).intValue());
                }
            });
        }
    }

    public void updateSoftTab(int soft) {
        int i = 2;
        if (soft == 1) {
            i = 1;
        } else if (soft == 2 || soft != 3) {
            i = 0;
        }
        updateXTabLayout(this.mAsSoftTab, i);
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment
    protected String[] supportSecondPageForElementDirect() {
        return sElementDirectSupport;
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment, com.xiaopeng.carcontrol.direct.OnPageDirectShowListener
    public void onPageDirectShow(String name) {
        if (sElementDirectSupport[0].equals(name)) {
            XListSingle xListSingle = this.mIhbSwItem;
            if (xListSingle != null) {
                XViewLocationUtils.scrollByLocation(xListSingle, new XViewLocationUtils.OnCorrectionLocationListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$YU4A0Yk5Pxr6D3pNpb4-ZwmxWfc
                    @Override // com.xiaopeng.xui.utils.XViewLocationUtils.OnCorrectionLocationListener
                    public final void onCorrectionLocationEnd(View view) {
                        SettingsFragment.this.lambda$onPageDirectShow$22$SettingsFragment(view);
                    }
                });
            } else {
                showIhbHelpInfo();
            }
        }
    }

    public /* synthetic */ void lambda$onPageDirectShow$22$SettingsFragment(View view) {
        showIhbHelpInfo();
    }

    private void initLock(View container) {
        final XTabLayout xTabLayout = (XTabLayout) container.findViewById(R.id.unlock_response_item).findViewById(R.id.unlock_response_tab);
        if (!CarBaseConfig.getInstance().isNewAvasArch()) {
            xTabLayout.removeTab(2);
        }
        xTabLayout.setOnTabChangeListener(new XTabLayout.OnTabChangeListenerAdapter() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.SettingsFragment.3
            {
                SettingsFragment.this = this;
            }

            @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListenerAdapter, com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
            public boolean onInterceptTabChange(XTabLayout tabLayout, int index, boolean tabChange, boolean fromUser) {
                return fromUser && ClickHelper.isFastClick(tabLayout.getId(), 500L, false);
            }

            @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
            public void onTabChangeEnd(XTabLayout xTabLayout2, int index, boolean tabChange, boolean fromUser) {
                if (fromUser) {
                    if (index == 0) {
                        SettingsFragment.this.mCarBodyViewModel.setUnlockResponseMode(UnlockResponse.Light);
                    } else if (index == 1) {
                        SettingsFragment.this.mCarBodyViewModel.setUnlockResponseMode(UnlockResponse.LightAndHorn);
                    } else if (index == 2) {
                        SettingsFragment.this.mCarBodyViewModel.setUnlockResponseMode(UnlockResponse.LightAndAvas);
                    }
                    SettingsFragment.this.updateCarImgView(1);
                    StatisticUtils.sendStatistic(PageEnum.SETTING_WIN_LOCK_PAGE, BtnEnum.SETTING_WIN_LOCK_PAGE_LOCK_RESPONSE_BTN, Integer.valueOf(index + 1));
                }
            }
        });
        UnlockResponse unlockResponseMode = this.mCarBodyViewModel.getUnlockResponseMode();
        if (unlockResponseMode != null) {
            updateXTabLayout(xTabLayout, unlockResponseMode.ordinal());
        }
        setLiveDataObserver(this.mCarBodyViewModel.getUnlockResponseData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$TlgZuCVpKJO9v3gGzGXtT9Cnbzw
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SettingsFragment.updateXTabLayout(XTabLayout.this, ((UnlockResponse) obj).ordinal());
            }
        });
        XListTwo xListTwo = (XListTwo) container.findViewById(R.id.dhc_sw_item);
        if (CarBaseConfig.getInstance().isSupportDhc()) {
            xListTwo.setVisibility(0);
            XSwitch xSwitch = (XSwitch) xListTwo.findViewById(R.id.x_list_sw);
            xSwitch.setVuiLabel(getStringById(R.string.auto_door_handle_title));
            xSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$wYjFLH7RWbGmZID8auNW5qPOrhU
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    SettingsFragment.this.lambda$initLock$24$SettingsFragment(compoundButton, z);
                }
            });
            xSwitch.setChecked(this.mWindowDoorViewModel.isAutoDoorHandleEnabled());
            LiveData<Boolean> doorHandle = this.mWindowDoorViewModel.getDoorHandle();
            xSwitch.getClass();
            setLiveDataObserver(doorHandle, new $$Lambda$SettingsFragment$aeIiqtRaGva9_dxYD21QFFdegA(xSwitch));
        } else {
            xListTwo.setVisibility(8);
        }
        XSwitch xSwitch2 = (XSwitch) container.findViewById(R.id.park_auto_unlock_sw_item).findViewById(R.id.x_list_sw);
        xSwitch2.setVuiLabel(getStringById(R.string.park_auto_unlock_title));
        xSwitch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$XpZhB6L9IPPMg8avzRvPODWb6OE
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                SettingsFragment.this.lambda$initLock$25$SettingsFragment(compoundButton, z);
            }
        });
        xSwitch2.setChecked(this.mCarBodyViewModel.getParkingAutoUnlock());
        LiveData<Boolean> parkingAutoUnlockData = this.mCarBodyViewModel.getParkingAutoUnlockData();
        xSwitch2.getClass();
        setLiveDataObserver(parkingAutoUnlockData, new $$Lambda$SettingsFragment$aeIiqtRaGva9_dxYD21QFFdegA(xSwitch2));
        XListTwo xListTwo2 = (XListTwo) container.findViewById(R.id.nfc_key_sw_item);
        if (xListTwo2 != null) {
            if (CarBaseConfig.getInstance().isSupportNfc()) {
                XSwitch xSwitch3 = (XSwitch) xListTwo2.findViewById(R.id.x_list_sw);
                this.mNfcKeySw = xSwitch3;
                xSwitch3.setVuiLabel(getStringById(R.string.nfc_key_title));
                this.mNfcKeySw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$jqrhge3lQ17aCaahHPfF1nnDJ8o
                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
                    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                        SettingsFragment.this.lambda$initLock$26$SettingsFragment(compoundButton, z);
                    }
                });
                this.mNfcKeySw.setOnInterceptListener(new XSwitch.OnInterceptListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$1pHcGlB0_SkQlOH6Sg4WzEN3DFg
                    @Override // com.xiaopeng.xui.widget.XSwitch.OnInterceptListener
                    public final boolean onInterceptCheck(View view, boolean z) {
                        return SettingsFragment.this.lambda$initLock$27$SettingsFragment(view, z);
                    }
                });
                this.mNfcKeySw.setChecked(this.mCarBodyViewModel.getStopNfcCardSw());
                LiveData<Boolean> stopNfcSwData = this.mCarBodyViewModel.getStopNfcSwData();
                XSwitch xSwitch4 = this.mNfcKeySw;
                xSwitch4.getClass();
                setLiveDataObserver(stopNfcSwData, new $$Lambda$SettingsFragment$aeIiqtRaGva9_dxYD21QFFdegA(xSwitch4));
            } else {
                xListTwo2.setVisibility(8);
            }
        }
        if (BaseFeatureOption.getInstance().isSupportDriveAutoLock()) {
            XSwitch xSwitch5 = (XSwitch) container.findViewById(R.id.driving_auto_lock_sw_item).findViewById(R.id.x_list_sw);
            xSwitch5.setVuiLabel(getStringById(R.string.driving_auto_lock_title));
            xSwitch5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$be5YDGLzm2mQ-wRW7GpzCQBODE0
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    SettingsFragment.this.lambda$initLock$28$SettingsFragment(compoundButton, z);
                }
            });
            xSwitch5.setChecked(this.mCarBodyViewModel.getDriveAutoLock());
            LiveData<Boolean> driveAutoLockData = this.mCarBodyViewModel.getDriveAutoLockData();
            xSwitch5.getClass();
            setLiveDataObserver(driveAutoLockData, new $$Lambda$SettingsFragment$aeIiqtRaGva9_dxYD21QFFdegA(xSwitch5));
        }
        if (CarBaseConfig.getInstance().isSupportSdc()) {
            container.findViewById(R.id.park_auto_unlock_sw_item).setBackground(ResUtils.getDrawable(R.drawable.sub_item_mid_bg));
            container.findViewById(R.id.double_click_open_layout).setVisibility(0);
            final XTabLayout xTabLayout2 = (XTabLayout) container.findViewById(R.id.double_click_open_tab);
            xTabLayout2.setOnTabChangeListener(new XTabLayout.OnTabChangeListenerAdapter() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.SettingsFragment.4
                {
                    SettingsFragment.this = this;
                }

                @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
                public void onTabChangeEnd(XTabLayout xTabLayout3, int index, boolean tabChange, boolean fromUser) {
                    if (fromUser) {
                        SettingsFragment.this.mWindowDoorViewModel.setSdcKeyCfg(index == 0 ? 1 : 0);
                        SettingsFragment.this.updateCarImgView(1);
                    }
                }
            });
            updateXTabLayout(xTabLayout2, this.mWindowDoorViewModel.getSdcKeyCfg() == 1 ? 0 : 1);
            setLiveDataObserver(this.mWindowDoorViewModel.getSdcKeyCfgData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$_alDHi9iYZlaWvAeEAaxD0jYjt4
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    Integer num = (Integer) obj;
                    SettingsFragment.updateXTabLayout(XTabLayout.this, cfg.intValue() == 1 ? 0 : 1);
                }
            });
            View findViewById = container.findViewById(R.id.sdc_brake_close_door_layout);
            findViewById.setVisibility(0);
            final XTabLayout xTabLayout3 = (XTabLayout) findViewById.findViewById(R.id.sdc_brake_close_door_tab);
            xTabLayout3.setOnTabChangeListener(new XTabLayout.OnTabChangeListenerAdapter() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.SettingsFragment.5
                {
                    SettingsFragment.this = this;
                }

                @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListenerAdapter, com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
                public boolean onInterceptTabChange(XTabLayout tabLayout, int index, boolean tabChange, boolean fromUser) {
                    if (fromUser) {
                        if (!ClickHelper.isFastClick(R.id.sdc_brake_close_door_tab, 500L, false)) {
                            SettingsFragment.this.updateCarImgView(1);
                            if (index != 0) {
                                SettingsFragment.this.showSdcBrakeCloseDoorSafeDialog(index);
                                return true;
                            }
                        } else {
                            NotificationHelper.getInstance().showToast(R.string.operation_fast_tips);
                            SettingsFragment.this.vuiFeedbackClick(R.string.operation_fast_tips, tabLayout);
                            return true;
                        }
                    }
                    return false;
                }

                @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
                public void onTabChangeEnd(XTabLayout xTabLayout4, int index, boolean tabChange, boolean fromUser) {
                    if (fromUser && index == 0) {
                        SettingsFragment.this.mWindowDoorViewModel.setSdcBrakeCloseDoorCfg(index);
                        StatisticUtils.sendStatistic(PageEnum.SETTING_NEW_PAGE, BtnEnum.SETTING_SDC_BRAKE_CLOSE_DOOR_BTN, Integer.valueOf(index + 1));
                    }
                }
            });
            updateXTabLayout(xTabLayout3, this.mWindowDoorViewModel.getSdcBrakeCloseDoorCfg());
            setLiveDataObserver(this.mWindowDoorViewModel.getSdcBrakeCloseDoorCfgData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$1yUmkYIoGBaZe9pjP1wTDRJnQHo
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SettingsFragment.lambda$initLock$30(XTabLayout.this, (Integer) obj);
                }
            });
            container.findViewById(R.id.door_angle_layout).setVisibility(0);
            XSlider xSlider = (XSlider) container.findViewById(R.id.door_angle_slider);
            this.mDoorAngleSlider = xSlider;
            xSlider.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$8VVnZDsJpKABowuYey3saxMdr3c
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public final void onGlobalLayout() {
                    SettingsFragment.this.lambda$initLock$31$SettingsFragment();
                }
            });
            this.mDoorAngleSlider.setStartIndex(8);
            this.mDoorAngleSlider.setEndIndex(20);
            this.mDoorAngleSlider.setSliderProgressListener(new XSlider.SliderProgressListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.SettingsFragment.6
                @Override // com.xiaopeng.xui.widget.slider.XSlider.SliderProgressListener
                public void onProgressChanged(XSlider xSlider2, float progress, String unit) {
                }

                @Override // com.xiaopeng.xui.widget.slider.XSlider.SliderProgressListener
                public void onStartTrackingTouch(XSlider xSlider2) {
                }

                {
                    SettingsFragment.this = this;
                }

                @Override // com.xiaopeng.xui.widget.slider.XSlider.SliderProgressListener
                public void onStopTrackingTouch(XSlider xSlider2) {
                    SettingsFragment.this.onSdcAngleSliderChange();
                }
            });
            this.mDoorAngleSlider.setProgressChangeListener(new XSlider.ProgressChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$RyrWayM7zyFYnDyye07__q91zuA
                @Override // com.xiaopeng.xui.widget.slider.XSlider.ProgressChangeListener
                public final void onProgressChanged(XSlider xSlider2, float f, String str, boolean z) {
                    SettingsFragment.this.lambda$initLock$32$SettingsFragment(xSlider2, f, str, z);
                }
            });
            setLiveDataObserver(this.mWindowDoorViewModel.getSdcMaxAutoDoorOpeningAngleData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$7Z4CGeVu_bUya8LzYbTAa7skgN4
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SettingsFragment.this.updateDoorAngle((Integer) obj);
                }
            });
            setLiveDataObserver(this.mWindowDoorViewModel.getLeftSdcSysRunningStateData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$gu9QwFQbMfex3MknEFdMRLiMRrk
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SettingsFragment.this.lambda$initLock$33$SettingsFragment((Integer) obj);
                }
            });
            setLiveDataObserver(this.mWindowDoorViewModel.getRightSdcSysRunningStateData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$lAFs6Hpi3ly762aIIto3wQNj1N0
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SettingsFragment.this.lambda$initLock$34$SettingsFragment((Integer) obj);
                }
            });
            updateDoorAngle(Integer.valueOf(this.mWindowDoorViewModel.getSdcMaxAutoDoorOpeningAngle()));
            if (this.mWindowDoorViewModel.isSdcRunning()) {
                this.mDoorAngleSlider.setEnabled(false);
            }
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("minValue", 0);
                jSONObject.put("maxValue", 100);
                jSONObject.put("startValue", 40);
                jSONObject.put(VuiConstants.PROPS_SETPROPS, true);
                jSONObject.put(VuiConstants.PROPS_INTERVAL, 5);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            this.mDoorAngleSlider.setVuiProps(jSONObject);
        }
        if (CarBaseConfig.getInstance().isSupportInductionLock()) {
            XListTwo xListTwo3 = (XListTwo) container.findViewById(R.id.d21_induction_lock);
            xListTwo3.setVisibility(0);
            XSwitch xSwitch6 = (XSwitch) xListTwo3.findViewById(R.id.x_list_sw);
            this.mInductionLockSwitch = xSwitch6;
            xSwitch6.setVuiLabel(getStringById(R.string.laboratory_induction_lock));
            this.mInductionLockSwitch.setChecked(this.mCarBodyViewModel.getInductionLock());
            this.mInductionLockSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$eClHJNDeglQXbvtau2dQExwF8sI
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    SettingsFragment.this.lambda$initLock$35$SettingsFragment(compoundButton, z);
                }
            });
            this.mInductionLockSwitch.setOnInterceptListener(new XSwitch.OnInterceptListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$gYzToWlAIAgN1X6zpjXF6uSEziY
                @Override // com.xiaopeng.xui.widget.XSwitch.OnInterceptListener
                public final boolean onInterceptCheck(View view, boolean z) {
                    return SettingsFragment.this.lambda$initLock$36$SettingsFragment(view, z);
                }
            });
            MutableLiveData<Boolean> inductionLockData = this.mCarBodyViewModel.getInductionLockData();
            XSwitch xSwitch7 = this.mInductionLockSwitch;
            xSwitch7.getClass();
            setLiveDataObserver(inductionLockData, new $$Lambda$SettingsFragment$aeIiqtRaGva9_dxYD21QFFdegA(xSwitch7));
        }
        if (CarBaseConfig.getInstance().isSupportPollingLock()) {
            View findViewById2 = container.findViewById(R.id.laboratory_near_unlock);
            findViewById2.setVisibility(0);
            XSwitch xSwitch8 = (XSwitch) findViewById2.findViewById(R.id.x_list_sw);
            xSwitch8.setVuiLabel(getStringById(R.string.laboratory_near_unlocking_title));
            xSwitch8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$wgTf8t6azfJEBA2o_N9USsXF33I
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    SettingsFragment.this.lambda$initLock$37$SettingsFragment(compoundButton, z);
                }
            });
            LiveData<Boolean> nearAutoUnlockData = this.mCarBodyViewModel.getNearAutoUnlockData();
            xSwitch8.getClass();
            setLiveDataObserver(nearAutoUnlockData, new $$Lambda$SettingsFragment$aeIiqtRaGva9_dxYD21QFFdegA(xSwitch8));
            xSwitch8.setChecked(this.mCarBodyViewModel.getNearUnlock());
            View findViewById3 = container.findViewById(R.id.laboratory_leave_lock);
            findViewById3.setVisibility(0);
            XSwitch xSwitch9 = (XSwitch) findViewById3.findViewById(R.id.x_list_sw);
            xSwitch9.setVuiLabel(getStringById(R.string.laboratory_leave_locking_title));
            xSwitch9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$lcYjsAiPEAxgyYQ0QpA9QiESet8
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    SettingsFragment.this.lambda$initLock$38$SettingsFragment(compoundButton, z);
                }
            });
            LiveData<Boolean> leaveAutoLockData = this.mCarBodyViewModel.getLeaveAutoLockData();
            xSwitch9.getClass();
            setLiveDataObserver(leaveAutoLockData, new $$Lambda$SettingsFragment$aeIiqtRaGva9_dxYD21QFFdegA(xSwitch9));
            xSwitch9.setChecked(this.mCarBodyViewModel.getLeaveAutoLock());
        }
    }

    public /* synthetic */ void lambda$initLock$24$SettingsFragment(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed() || isVuiAction(buttonView)) {
            this.mWindowDoorViewModel.setAutoDoorHandleEnable(isChecked);
            updateCarImgView(1);
            StatisticUtils.sendStatistic(PageEnum.SETTING_WIN_LOCK_PAGE, BtnEnum.SETTING_WIN_LOCK_PAGE_DOOR_HANDLE_BTN, Integer.valueOf(StatisticUtils.getSwitchOnOff(isChecked)));
        }
    }

    public /* synthetic */ void lambda$initLock$25$SettingsFragment(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed() || isVuiAction(buttonView)) {
            this.mCarBodyViewModel.setParkingAutoUnlock(isChecked);
            updateCarImgView(1);
            StatisticUtils.sendStatistic(PageEnum.SETTING_WIN_LOCK_PAGE, BtnEnum.SETTING_WIN_LOCK_PAGE_PAKING_UNLOCK_BTN, Integer.valueOf(StatisticUtils.getSwitchOnOff(isChecked)));
        }
    }

    public /* synthetic */ void lambda$initLock$26$SettingsFragment(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed()) {
            this.mCarBodyViewModel.setStopNfcCardSw(isChecked);
        }
    }

    public /* synthetic */ boolean lambda$initLock$27$SettingsFragment(View buttonView, boolean isChecked) {
        if (!buttonView.isPressed() || isChecked) {
            return false;
        }
        showNfcKeyConfirmDialog();
        return true;
    }

    public /* synthetic */ void lambda$initLock$28$SettingsFragment(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed() || isVuiAction(buttonView)) {
            this.mCarBodyViewModel.setDriveAutoLock(isChecked);
            StatisticUtils.sendStatistic(PageEnum.SETTING_PAGE, BtnEnum.RUNNINT_LOCK_SWITCH, Integer.valueOf(StatisticUtils.getSwitchOnOff(isChecked)));
        }
    }

    public static /* synthetic */ void lambda$initLock$30(final XTabLayout tabSdcBrakeCloseDoorCfg, Integer cfg) {
        if (cfg != null) {
            updateXTabLayout(tabSdcBrakeCloseDoorCfg, cfg.intValue());
        }
    }

    public /* synthetic */ void lambda$initLock$31$SettingsFragment() {
        int measuredWidth = this.mDoorAngleSlider.getMeasuredWidth();
        LogUtils.d(this.TAG, "DoorAngleSlider onGlobalLayout, mSliderWidth: " + measuredWidth + ", mSliderHeight: " + this.mDoorAngleSlider.getMeasuredHeight(), false);
    }

    public /* synthetic */ void lambda$initLock$32$SettingsFragment(XSlider xSlider, float v, String s, boolean b) {
        onSdcAngleSliderChange();
    }

    public /* synthetic */ void lambda$initLock$33$SettingsFragment(Integer state) {
        setAngleSliderEnable(true, state.intValue());
    }

    public /* synthetic */ void lambda$initLock$34$SettingsFragment(Integer state) {
        setAngleSliderEnable(false, state.intValue());
    }

    public /* synthetic */ void lambda$initLock$35$SettingsFragment(CompoundButton buttonView, boolean isChecked) {
        this.mCarBodyViewModel.setInductionLock(isChecked);
    }

    public /* synthetic */ boolean lambda$initLock$36$SettingsFragment(View buttonView, boolean checked) {
        if (checked) {
            if (buttonView.isPressed() || isVuiAction(buttonView)) {
                showInductionLockDialog();
                return true;
            }
            return false;
        }
        return false;
    }

    public /* synthetic */ void lambda$initLock$37$SettingsFragment(CompoundButton buttonView, boolean checked) {
        this.mCarBodyViewModel.setNearUnlock(checked);
    }

    public /* synthetic */ void lambda$initLock$38$SettingsFragment(CompoundButton buttonView, boolean checked) {
        this.mCarBodyViewModel.setLeaveAutoLock(checked);
    }

    public void showSdcBrakeCloseDoorSafeDialog(final int index) {
        if (getActivity() == null) {
            return;
        }
        XDialog xDialog = new XDialog(getContext(), R.style.XDialogView);
        xDialog.setTitle(R.string.sdc_brake_close_door_title);
        xDialog.setMessage(R.string.sdc_brake_close_door_confirm_msg);
        xDialog.setPositiveButton(getStringById(R.string.btn_confirm), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$i42eE2IrIK_nWoTW-X0DACJWVqE
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog2, int i) {
                SettingsFragment.this.lambda$showSdcBrakeCloseDoorSafeDialog$39$SettingsFragment(index, xDialog2, i);
            }
        });
        xDialog.setNegativeButton(getStringById(R.string.btn_cancel));
        xDialog.setSystemDialog(2048);
        VuiManager.instance().initVuiDialog(xDialog, App.getInstance(), VuiManager.SCENE_DIALOG_SDC_BRAKE_DOOR);
        xDialog.show();
    }

    public /* synthetic */ void lambda$showSdcBrakeCloseDoorSafeDialog$39$SettingsFragment(final int index, XDialog xDialog, int i) {
        this.mWindowDoorViewModel.setSdcBrakeCloseDoorCfg(index);
        StatisticUtils.sendStatistic(PageEnum.SETTING_NEW_PAGE, BtnEnum.SETTING_SDC_BRAKE_CLOSE_DOOR_BTN, Integer.valueOf(index + 1));
    }

    public void onSdcAngleSliderChange() {
        float indicatorValue = this.mDoorAngleSlider.getIndicatorValue();
        int round = Math.round(indicatorValue / 5.0f) * 5;
        LogUtils.d(this.TAG, "SdcAngle Slider slider progress:" + indicatorValue + " invert to angle = " + round);
        this.mWindowDoorViewModel.setSdcMaxAutoDoorOpeningAngle(round);
    }

    private void showInductionLockDialog() {
        if (getActivity() == null) {
            return;
        }
        if (this.mInductionLockDialog == null) {
            XDialog xDialog = new XDialog(getContext(), R.style.XDialogView_Large_AppLimit);
            this.mInductionLockDialog = xDialog;
            xDialog.setTitle(R.string.laboratory_induction_title);
            this.mInductionLockDialog.setMessage(R.string.laboratory_induction_lock_pop);
            this.mInductionLockDialog.setPositiveButton(getStringById(R.string.confirm_open), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$jwth2ktp-IQBCZjKeCFOiBnYExw
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog2, int i) {
                    SettingsFragment.this.lambda$showInductionLockDialog$40$SettingsFragment(xDialog2, i);
                }
            });
            this.mInductionLockDialog.setNegativeButton(getStringById(R.string.btn_cancel));
        }
        this.mInductionLockDialog.show();
    }

    public /* synthetic */ void lambda$showInductionLockDialog$40$SettingsFragment(XDialog xDialog, int i) {
        this.mInductionLockSwitch.setChecked(true);
        this.mCarBodyViewModel.setInductionLock(true);
    }

    private void showNfcKeyConfirmDialog() {
        if (getActivity() == null) {
            return;
        }
        if (this.mNfcKeyConfirmDialog == null) {
            XDialog xDialog = new XDialog(getContext(), R.style.XDialogView);
            this.mNfcKeyConfirmDialog = xDialog;
            xDialog.setTitle(R.string.nfc_key_title);
            this.mNfcKeyConfirmDialog.setMessage(R.string.nfc_key_dialog_content);
            this.mNfcKeyConfirmDialog.setPositiveButton(getStringById(R.string.btn_confirm), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$PAfBASh7KOZ915bzZkazTLjKZLM
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog2, int i) {
                    SettingsFragment.this.lambda$showNfcKeyConfirmDialog$41$SettingsFragment(xDialog2, i);
                }
            });
            this.mNfcKeyConfirmDialog.setNegativeButton(getStringById(R.string.btn_cancel));
            this.mNfcKeyConfirmDialog.setSystemDialog(2048);
        }
        this.mNfcKeyConfirmDialog.show();
    }

    public /* synthetic */ void lambda$showNfcKeyConfirmDialog$41$SettingsFragment(XDialog xDialog, int i) {
        this.mNfcKeySw.setChecked(false);
        this.mCarBodyViewModel.setStopNfcCardSw(false);
    }

    private void showPsnSrsCloseConfirmDialog() {
        if (getActivity() == null) {
            return;
        }
        if (this.mPsnSrsConfirmDialog == null) {
            XDialog xDialog = new XDialog(getContext(), R.style.XDialogView);
            this.mPsnSrsConfirmDialog = xDialog;
            xDialog.setTitle(R.string.psn_srs_tv);
            this.mPsnSrsConfirmDialog.setMessage(R.string.psn_srs_dialog_content);
            this.mPsnSrsConfirmDialog.setPositiveButton(getStringById(R.string.btn_confirm), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$6PsNxr0GEjkQU-Um_A_0T7eQMgA
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog2, int i) {
                    SettingsFragment.this.lambda$showPsnSrsCloseConfirmDialog$42$SettingsFragment(xDialog2, i);
                }
            });
            this.mPsnSrsConfirmDialog.setNegativeButton(getStringById(R.string.btn_cancel));
            this.mPsnSrsConfirmDialog.setSystemDialog(2048);
        }
        this.mPsnSrsConfirmDialog.show();
    }

    public /* synthetic */ void lambda$showPsnSrsCloseConfirmDialog$42$SettingsFragment(XDialog xDialog, int i) {
        this.mSeatViewModel.setPsnSrsEnable(false);
    }

    private void initWindow(View container) {
        View findViewById = container.findViewById(R.id.auto_leave_window_lock_sw);
        if (CarBaseConfig.getInstance().isSupportAutoCloseWin()) {
            final XSwitch xSwitch = (XSwitch) findViewById.findViewById(R.id.x_list_sw);
            xSwitch.setVuiLabel(getStringById(R.string.win_auto_close_by_lock_title));
            xSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$ixAUWe2hm7YG7BFWoqoE265TJzw
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    SettingsFragment.this.lambda$initWindow$43$SettingsFragment(compoundButton, z);
                }
            });
            xSwitch.setChecked(this.mWindowDoorViewModel.getAutoWindowLockSw());
            setLiveDataObserver(this.mWindowDoorViewModel.getAutoWindowLockSwData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$kCXqXv_6-IXY88vgu9feP8NaW-E
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SettingsFragment.lambda$initWindow$44(XSwitch.this, (Boolean) obj);
                }
            });
        } else {
            findViewById.setVisibility(8);
        }
        final XSwitch xSwitch2 = (XSwitch) container.findViewById(R.id.high_speed_sw_item).findViewById(R.id.x_list_sw);
        xSwitch2.setVuiLabel(getStringById(R.string.high_speed_tv));
        xSwitch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$lfBFqZori1RKBWljoQN0yYTcZgc
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                SettingsFragment.this.lambda$initWindow$45$SettingsFragment(compoundButton, z);
            }
        });
        xSwitch2.setChecked(this.mWindowDoorViewModel.isHighSpdCloseWinEnabled());
        setLiveDataObserver(this.mWindowDoorViewModel.getHighSpdCloseWinSwData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$juvyfm1t00TwJgm9ngLmcjgzA8g
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SettingsFragment.lambda$initWindow$46(XSwitch.this, (Boolean) obj);
            }
        });
        if (CarBaseConfig.getInstance().isSupportSdc()) {
            container.findViewById(R.id.high_speed_sw_item).setBackground(ResUtils.getDrawable(R.drawable.sub_item_mid_bg));
            container.findViewById(R.id.open_sdc_with_win_sw).setVisibility(0);
            final XSwitch xSwitch3 = (XSwitch) container.findViewById(R.id.open_sdc_with_win_sw).findViewById(R.id.x_list_sw);
            xSwitch3.setVuiLabel(getStringById(R.string.open_sdc_with_win_sw_title));
            xSwitch3.setOnInterceptListener(new XSwitch.OnInterceptListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$3ZowMIZmc2GVrtTDi9d9nQH2jSE
                @Override // com.xiaopeng.xui.widget.XSwitch.OnInterceptListener
                public final boolean onInterceptCheck(View view, boolean z) {
                    return SettingsFragment.lambda$initWindow$47(view, z);
                }
            });
            xSwitch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$veE7dOA9uvr4el7wDrKw6XLg3zE
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    SettingsFragment.this.lambda$initWindow$48$SettingsFragment(compoundButton, z);
                }
            });
            xSwitch3.setChecked(this.mWindowDoorViewModel.getSdcWinAutoDown());
            setLiveDataObserver(this.mWindowDoorViewModel.getSdcWinAutoDownData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$g4KFuzfqZnDbi-eJDDi8eF_H7zY
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SettingsFragment.lambda$initWindow$49(XSwitch.this, (Boolean) obj);
                }
            });
        }
    }

    public /* synthetic */ void lambda$initWindow$43$SettingsFragment(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed() || isVuiAction(buttonView)) {
            this.mWindowDoorViewModel.setAutoWindowLockSw(isChecked);
            updateCarImgView(2);
            StatisticUtils.sendStatistic(PageEnum.SETTING_WIN_LOCK_PAGE, BtnEnum.SETTING_WIN_LOCK_PAGE_LEAVE_LOCK_BTN, Integer.valueOf(StatisticUtils.getSwitchOnOff(isChecked)));
        }
    }

    public static /* synthetic */ void lambda$initWindow$44(final XSwitch switchLockAutoClose, Boolean enable) {
        if (enable != null) {
            switchLockAutoClose.setChecked(enable.booleanValue());
        }
    }

    public /* synthetic */ void lambda$initWindow$45$SettingsFragment(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed() || isVuiAction(buttonView)) {
            this.mWindowDoorViewModel.setHighSpdCloseWin(isChecked);
            updateCarImgView(2);
            StatisticUtils.sendStatistic(PageEnum.SETTING_WIN_LOCK_PAGE, BtnEnum.SETTING_WIN_LOCK_PAGE_HIGH_SPEED_CLOSE_BTN, Integer.valueOf(StatisticUtils.getSwitchOnOff(isChecked)));
        }
    }

    public static /* synthetic */ void lambda$initWindow$46(final XSwitch switchHighSpdCloseWin, Boolean enable) {
        if (enable != null) {
            switchHighSpdCloseWin.setChecked(enable.booleanValue());
        }
    }

    public static /* synthetic */ boolean lambda$initWindow$47(View buttonView, boolean isChecked) {
        if (buttonView.isPressed()) {
            return ClickHelper.isFastClick(500L);
        }
        return false;
    }

    public /* synthetic */ void lambda$initWindow$48$SettingsFragment(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed() || isVuiAction(buttonView)) {
            this.mWindowDoorViewModel.setSdcWinAutoDown(isChecked);
            updateCarImgView(2);
        }
    }

    public static /* synthetic */ void lambda$initWindow$49(final XSwitch mSwitchOpenSdcWithWin, Boolean enable) {
        if (enable != null) {
            mSwitchOpenSdcWithWin.setChecked(enable.booleanValue());
        }
    }

    public void updateDoorAngle(Integer angle) {
        if (angle == null || angle.intValue() < 40 || angle.intValue() > 100) {
            return;
        }
        final int round = Math.round(angle.floatValue() / 5.0f);
        this.mDoorAngleSlider.postDelayed(new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$lN7fZABSdJ4kPzMKxnFpEV6lww8
            @Override // java.lang.Runnable
            public final void run() {
                SettingsFragment.this.lambda$updateDoorAngle$50$SettingsFragment(round);
            }
        }, 200L);
    }

    public /* synthetic */ void lambda$updateDoorAngle$50$SettingsFragment(final int slideIndex) {
        this.mDoorAngleSlider.setCurrentIndex(slideIndex);
    }

    private void setAngleSliderEnable(boolean left, int state) {
        int leftSdcSysRunningState = left ? state : this.mWindowDoorViewModel.getLeftSdcSysRunningState();
        if (left) {
            state = this.mWindowDoorViewModel.getRightSdcSysRunningState();
        }
        LogUtils.d(this.TAG, "setAngleSliderEnable, leftState: " + leftSdcSysRunningState + ", rightState: " + state);
        if (leftSdcSysRunningState == 2 || leftSdcSysRunningState == 3 || state == 2 || state == 3) {
            this.mDoorAngleSlider.setEnabled(false);
            return;
        }
        this.mDoorAngleSlider.setEnabled(true);
        updateDoorAngle(Integer.valueOf(this.mWindowDoorViewModel.getSdcMaxAutoDoorOpeningAngle()));
    }

    private void initChildLock(View container) {
        boolean isSupportChildMode = CarBaseConfig.getInstance().isSupportChildMode();
        if (!CarBaseConfig.getInstance().isSupportChildLock() && !isSupportChildMode) {
            LogUtils.i(this.TAG, "Not support child mode,and not support child lock!");
            return;
        }
        XTextView xTextView = (XTextView) container.findViewById(R.id.child_lock_title_tv);
        xTextView.setVisibility(0);
        if (isSupportChildMode) {
            xTextView.setText(R.string.child_mode_title);
            this.mChildModeLayout = (XFrameLayout) container.findViewById(R.id.child_mode_layout);
            this.mChildModeDesTv = (XTextView) container.findViewById(R.id.child_mode_content);
            this.mChildModeTitleTv = (XTextView) container.findViewById(R.id.child_mode_title);
            this.mChildModeTTv = (XImageView) container.findViewById(R.id.child_mode_triangle);
            this.mChildModeLayout.setVisibility(0);
            this.mChildModeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$h8ig7IEY19m4Ww35nfHl04HLK-s
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SettingsFragment.this.lambda$initChildLock$51$SettingsFragment(view);
                }
            });
            updateChildModeView(this.mCarBodyViewModel.isChildModeEnable());
            setLiveDataObserver(this.mCarBodyViewModel.getChildMode(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$TPN6q5kZSG_sC0KNddqxOL6SqS4
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SettingsFragment.this.lambda$initChildLock$52$SettingsFragment((Boolean) obj);
                }
            });
            return;
        }
        xTextView.setText(R.string.child_lock_title);
        View findViewById = container.findViewById(R.id.left_child_door_lock_sw);
        this.leftChildLockTB = (XSwitch) findViewById.findViewById(R.id.x_list_sw);
        findViewById.setVisibility(0);
        this.leftChildLockTB.setVuiLabel(getStringById(R.string.child_lock_rl_title));
        this.leftChildLockTB.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$6Tbr0BKqt1ZvS_Y3YVxmgN5mMMk
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return SettingsFragment.lambda$initChildLock$53(view, motionEvent);
            }
        });
        this.leftChildLockTB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$45rqTkwxYp58H5sNukZLDpEmXi4
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                SettingsFragment.this.lambda$initChildLock$54$SettingsFragment(compoundButton, z);
            }
        });
        this.leftChildLockTB.setChecked(this.mCarBodyViewModel.isLeftChildLocked());
        LiveData<Boolean> leftChildLockData = this.mCarBodyViewModel.getLeftChildLockData();
        XSwitch xSwitch = this.leftChildLockTB;
        xSwitch.getClass();
        setLiveDataObserver(leftChildLockData, new $$Lambda$SettingsFragment$aeIiqtRaGva9_dxYD21QFFdegA(xSwitch));
        View findViewById2 = container.findViewById(R.id.right_child_door_lock_sw);
        this.rightChildLockTB = (XSwitch) findViewById2.findViewById(R.id.x_list_sw);
        findViewById2.setVisibility(0);
        this.rightChildLockTB.setVuiLabel(getStringById(R.string.child_lock_rr_title));
        this.rightChildLockTB.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$SxXiB3kVUbHZLkwtfDQbe5-uvmY
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return SettingsFragment.lambda$initChildLock$55(view, motionEvent);
            }
        });
        this.rightChildLockTB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$RmT_sJim6ncSYcQ58ql1wxtCBB0
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                SettingsFragment.this.lambda$initChildLock$56$SettingsFragment(compoundButton, z);
            }
        });
        this.rightChildLockTB.setChecked(this.mCarBodyViewModel.isRightChildLocked());
        LiveData<Boolean> rightChildLockData = this.mCarBodyViewModel.getRightChildLockData();
        XSwitch xSwitch2 = this.rightChildLockTB;
        xSwitch2.getClass();
        setLiveDataObserver(rightChildLockData, new $$Lambda$SettingsFragment$aeIiqtRaGva9_dxYD21QFFdegA(xSwitch2));
    }

    public /* synthetic */ void lambda$initChildLock$51$SettingsFragment(View v) {
        showChildModePanel();
    }

    public /* synthetic */ void lambda$initChildLock$52$SettingsFragment(Boolean enable) {
        updateChildModeView(enable.booleanValue());
    }

    public static /* synthetic */ boolean lambda$initChildLock$53(View v, MotionEvent event) {
        return event.getAction() == 0 && ClickHelper.isFastClick(500L);
    }

    public /* synthetic */ void lambda$initChildLock$54$SettingsFragment(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed() || isVuiAction(buttonView)) {
            this.mCarBodyViewModel.setChildLock(true, isChecked);
            int i = 3;
            updateCarImgView(3);
            if (this.mCarBodyViewModel.isRightChildLocked()) {
                i = isChecked ? 2 : 4;
            } else if (!isChecked) {
                i = 1;
            }
            StatisticUtils.sendStatistic(PageEnum.SETTING_WIN_LOCK_PAGE, BtnEnum.SETTING_WIN_LOCK_PAGE_CHILD_LOCK_BTN, Integer.valueOf(i));
        }
    }

    public static /* synthetic */ boolean lambda$initChildLock$55(View v, MotionEvent event) {
        return event.getAction() == 0 && ClickHelper.isFastClick(500L);
    }

    public /* synthetic */ void lambda$initChildLock$56$SettingsFragment(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed() || isVuiAction(buttonView)) {
            this.mCarBodyViewModel.setChildLock(false, isChecked);
            int i = 3;
            updateCarImgView(3);
            if (!this.mCarBodyViewModel.isLeftChildLocked()) {
                i = isChecked ? 4 : 1;
            } else if (isChecked) {
                i = 2;
            }
            StatisticUtils.sendStatistic(PageEnum.SETTING_WIN_LOCK_PAGE, BtnEnum.SETTING_WIN_LOCK_PAGE_CHILD_LOCK_BTN, Integer.valueOf(i));
        }
    }

    private void initWelcome(View container) {
        XListTwo xListTwo = (XListTwo) container.findViewById(R.id.seat_welcome_sw);
        if (CarBaseConfig.getInstance().isSupportWelcomeMode()) {
            XSwitch xSwitch = (XSwitch) xListTwo.findViewById(R.id.x_list_sw);
            this.mSeatWelcomeSw = xSwitch;
            xSwitch.setVuiLabel(getStringById(R.string.welcome_mode_tv));
            if (CarBaseConfig.getInstance().isSupportAirSuspension()) {
                xListTwo.setTextSub(ResUtils.getString(R.string.welcome_mode_as_desc));
            } else if (CarBaseConfig.getInstance().isSupportControlSteer()) {
                xListTwo.setTextSub(ResUtils.getString(R.string.welcome_mode_steer_desc));
            } else {
                xListTwo.setTextSub(ResUtils.getString(R.string.welcome_mode_desc));
            }
            boolean isWelcomeModeEnabled = this.mSeatViewModel.isWelcomeModeEnabled();
            LogUtils.d(this.TAG, "initWelcome, WelcomeModeEnabled : " + isWelcomeModeEnabled, false);
            this.mSeatWelcomeSw.setChecked(isWelcomeModeEnabled);
            this.mSeatWelcomeSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$BRGc4qK7UuUnK41J1l-TJHdqVZ4
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    SettingsFragment.this.lambda$initWelcome$57$SettingsFragment(compoundButton, z);
                }
            });
            LiveData<Boolean> welcomeModeData = this.mSeatViewModel.getWelcomeModeData();
            XSwitch xSwitch2 = this.mSeatWelcomeSw;
            xSwitch2.getClass();
            setLiveDataObserver(welcomeModeData, new $$Lambda$SettingsFragment$aeIiqtRaGva9_dxYD21QFFdegA(xSwitch2));
        } else {
            xListTwo.setVisibility(8);
            container.findViewById(R.id.boot_sound_sw).setBackground(ResUtils.getDrawable(R.drawable.sub_item_top_bg));
        }
        if (CarBaseConfig.getInstance().isSupportPsnWelcomeMode()) {
            XListTwo xListTwo2 = (XListTwo) container.findViewById(R.id.psn_seat_welcome_sw);
            xListTwo2.setVisibility(0);
            XSwitch xSwitch3 = (XSwitch) xListTwo2.findViewById(R.id.x_list_sw);
            this.mPsnSeatWelcomeSw = xSwitch3;
            xSwitch3.setVuiLabel(getStringById(R.string.psn_welcome_mode_tv));
            boolean isPsnWelcomeEnabled = this.mSeatViewModel.isPsnWelcomeEnabled();
            LogUtils.d(this.TAG, "initPsnWelcome, WelcomeModeEnabled : " + isPsnWelcomeEnabled, false);
            this.mPsnSeatWelcomeSw.setChecked(isPsnWelcomeEnabled);
            this.mPsnSeatWelcomeSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$E8umDeuzt3rY3_1aKlxE-5TuAT0
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    SettingsFragment.this.lambda$initWelcome$58$SettingsFragment(compoundButton, z);
                }
            });
            LiveData<Boolean> psnSeatWelcomeMode = this.mSeatViewModel.getPsnSeatWelcomeMode();
            XSwitch xSwitch4 = this.mPsnSeatWelcomeSw;
            xSwitch4.getClass();
            setLiveDataObserver(psnSeatWelcomeMode, new $$Lambda$SettingsFragment$aeIiqtRaGva9_dxYD21QFFdegA(xSwitch4));
        }
        if (CarBaseConfig.getInstance().isSupportRearSeatWelcomeMode()) {
            XListTwo xListTwo3 = (XListTwo) container.findViewById(R.id.rear_seat_welcome_sw);
            xListTwo3.setVisibility(0);
            XSwitch xSwitch5 = (XSwitch) xListTwo3.findViewById(R.id.x_list_sw);
            this.mRearSeatWelcomeSw = xSwitch5;
            xSwitch5.setVuiLabel(getStringById(R.string.rear_seat_welcome_mode_tv));
            this.mRearSeatWelcomeSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$TwL9mxbSY85x_a0LNmG8BpzRWPU
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    SettingsFragment.this.lambda$initWelcome$59$SettingsFragment(compoundButton, z);
                }
            });
        }
        if (CarBaseConfig.getInstance().isSupportBootSoundEffect()) {
            container.findViewById(R.id.boot_sound_sw).setVisibility(0);
            container.findViewById(R.id.boot_sound_tab_item).setVisibility(0);
            XSwitch xSwitch6 = (XSwitch) container.findViewById(R.id.boot_sound_sw).findViewById(R.id.x_list_sw);
            this.mBootEffectSw = xSwitch6;
            xSwitch6.setVuiLabel(getStringById(R.string.boot_sound_effect_title));
            this.mBootEffectSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$Md285zM45CprPAbzCyUAjKsZ8JQ
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    SettingsFragment.this.lambda$initWelcome$60$SettingsFragment(compoundButton, z);
                }
            });
            XTabLayout xTabLayout = (XTabLayout) container.findViewById(R.id.boot_sound_tab_item).findViewById(R.id.boot_sound_tab);
            this.mBootEffectTab = xTabLayout;
            xTabLayout.setSoundEffectsEnabled(false);
            this.mBootEffectTab.setOnTabChangeListener(new XTabLayout.OnTabChangeListenerAdapter() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.SettingsFragment.7
                {
                    SettingsFragment.this = this;
                }

                @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
                public void onTabChangeEnd(XTabLayout xTabLayout2, int index, boolean tabChange, boolean fromUser) {
                    if (fromUser) {
                        LogUtils.d(SettingsFragment.this.TAG, "mBootEffectTab onTabChangeEnd: " + index);
                        if (index == 0) {
                            SoundHelper.play(SoundHelper.PATH_BOOT_1, true, false);
                        } else if (index == 1) {
                            SoundHelper.play(SoundHelper.PATH_BOOT_2, true, false);
                        } else if (index == 2) {
                            SoundHelper.play(SoundHelper.PATH_BOOT_3, true, false);
                        } else if (index == 3) {
                            SoundHelper.play(SoundHelper.PATH_BOOT_4, true, false);
                        } else {
                            SoundHelper.play("", false, false);
                        }
                        SettingsFragment.this.mAvasViewModel.setBootSoundEffectValue(BootSoundEffect.values()[index + 1]);
                        SettingsFragment.this.updateCarImgView(4);
                        StatisticUtils.sendStatistic(PageEnum.SETTING_OTHER_PAGE, BtnEnum.SETTING_OTHER_PAGE_BOOT_SOUND_BTN, Integer.valueOf(index + 2));
                    }
                }
            });
            VuiUtils.addHasFeedbackProp(this.mBootEffectTab);
            BootSoundEffect bootSoundEffectValue = this.mAvasViewModel.getBootSoundEffectValue();
            if (bootSoundEffectValue != null) {
                initBootEffectControl(bootSoundEffectValue.ordinal());
            }
            setLiveDataObserver(this.mAvasViewModel.getAvasBootEffectData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$_3ioR3uTOqtkrjHyfY3X0vAdBfk
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SettingsFragment.this.initBootEffectControl(((Integer) obj).intValue());
                }
            });
            return;
        }
        xListTwo.setBackground(ResUtils.getDrawable(R.drawable.sub_item_bg));
        container.findViewById(R.id.boot_sound_sw).setVisibility(8);
        container.findViewById(R.id.boot_sound_tab_item).setVisibility(8);
    }

    public /* synthetic */ void lambda$initWelcome$57$SettingsFragment(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed() || isVuiAction(buttonView)) {
            this.mSeatViewModel.setWelcomeMode(isChecked);
            updateCarImgView(4);
            StatisticUtils.sendStatistic(PageEnum.SEAT_ADJUST_DIALOG_PAGE, BtnEnum.SEAT_ADJUST_DIALOG_PAGE_WELCOME_BTN, Integer.valueOf(StatisticUtils.getSwitchOnOff(isChecked)));
        }
    }

    public /* synthetic */ void lambda$initWelcome$58$SettingsFragment(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed() || isVuiAction(buttonView)) {
            this.mSeatViewModel.setPsnWelcomeMode(isChecked);
            updateCarImgView(4);
        }
    }

    public /* synthetic */ void lambda$initWelcome$59$SettingsFragment(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed() || isVuiAction(buttonView)) {
            this.mSeatViewModel.setRearSeatWelcomeMode(isChecked);
            updateCarImgView(4);
        }
    }

    public /* synthetic */ void lambda$initWelcome$60$SettingsFragment(CompoundButton buttonView, boolean isChecked) {
        int i;
        if (buttonView.isPressed() || isVuiAction(buttonView)) {
            this.mBootEffectTab.setEnabled(isChecked);
            if (isChecked) {
                i = this.mAvasViewModel.getBootEffectBeforeSw();
                this.mAvasViewModel.setBootSoundEffectValue(BootSoundEffect.fromXuiCmd(i));
                this.mBootEffectTab.selectTab(i - 1, false);
            } else {
                this.mAvasViewModel.setBootSoundEffectValue(BootSoundEffect.values()[0]);
                this.mBootEffectTab.selectedNoneTab(false, false);
                SoundHelper.play("", false, false);
                i = 0;
            }
            updateCarImgView(4);
            PageEnum pageEnum = PageEnum.SETTING_OTHER_PAGE;
            BtnEnum btnEnum = BtnEnum.SETTING_OTHER_PAGE_BOOT_SOUND_BTN;
            Object[] objArr = new Object[1];
            objArr[0] = Integer.valueOf(isChecked ? 1 + i : 1);
            StatisticUtils.sendStatistic(pageEnum, btnEnum, objArr);
        }
    }

    public void initBootEffectControl(int effectValue) {
        XSwitch xSwitch = this.mBootEffectSw;
        if (xSwitch != null) {
            xSwitch.setChecked(effectValue != 0);
        }
        XTabLayout xTabLayout = this.mBootEffectTab;
        if (xTabLayout != null) {
            if (effectValue == 0) {
                xTabLayout.selectedNoneTab(false, false);
                this.mBootEffectTab.setEnabled(false);
                return;
            }
            xTabLayout.selectTab(effectValue - 1);
            this.mBootEffectTab.setEnabled(true);
        }
    }

    private void initDrive(View view) {
        if (BaseFeatureOption.getInstance().isSupportDriveInSettingsPage()) {
            if (CarBaseConfig.getInstance().isSupportShowEps()) {
                if (CarBaseConfig.getInstance().isSupportAwd()) {
                    ((XTextView) view.findViewById(R.id.tv_drive_experience_turn)).setText(R.string.steering_eps_awd_title);
                }
                if (CarBaseConfig.getInstance().isSupportXSport()) {
                    XTextView xTextView = (XTextView) view.findViewById(R.id.tv_drive_experience_turn_summary);
                    if (XSportDriveMode.XPower == this.mVcuViewModel.getXSportDriveModeValue()) {
                        xTextView.setText(R.string.steering_eps_desc_xpower);
                    } else if (XSportDriveMode.Geek == this.mVcuViewModel.getXSportDriveModeValue()) {
                        xTextView.setText(R.string.steering_eps_desc_geek);
                    }
                }
                final XTabLayout xTabLayout = (XTabLayout) view.findViewById(R.id.tab_drive_experience_turn);
                VuiUtils.addHasFeedbackProp(xTabLayout);
                xTabLayout.setOnTabChangeListener(new XTabLayout.OnTabChangeListenerAdapter() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.SettingsFragment.8
                    {
                        SettingsFragment.this = this;
                    }

                    @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListenerAdapter, com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
                    public boolean onInterceptTabChange(XTabLayout tabLayout, int index, boolean tabChange, boolean fromUser) {
                        if (fromUser) {
                            if (CarBaseConfig.getInstance().isSupportXSport()) {
                                if (XSportDriveMode.XPower != SettingsFragment.this.mVcuViewModel.getXSportDriveModeValue()) {
                                    if (XSportDriveMode.Geek == SettingsFragment.this.mVcuViewModel.getXSportDriveModeValue()) {
                                        NotificationHelper.getInstance().showToast(R.string.exit_with_drive_mode_geek_on);
                                    }
                                    return true;
                                }
                                NotificationHelper.getInstance().showToast(R.string.exit_with_drive_mode_xpower_on);
                                return true;
                            } else if (!ClickHelper.isFastClick(tabLayout.getId(), 1000L, false)) {
                                if (SettingsFragment.this.mVcuViewModel.getCarSpeed() > CarBaseConfig.getInstance().getEpsSpdThreshold() || Math.abs(SettingsFragment.this.mChassisVm.getTorsionBarTorque()) > CarBaseConfig.getInstance().getEpsTorsionBarThreshold()) {
                                    NotificationHelper.getInstance().showToast(R.string.eps_unable_with_running);
                                    SettingsFragment.this.vuiFeedbackClick(R.string.eps_unable_with_running, tabLayout);
                                    StatisticUtils.sendStatistic(PageEnum.DRIVE_SETTING_PAGE, BtnEnum.DRIVE_SETTING_PAGE_EPS_BTN, Integer.valueOf(index + 1));
                                    return true;
                                } else if ((CarBaseConfig.getInstance().isSupportNgp() && SettingsFragment.this.mScuViewModel.isNgpRunning()) || (CarBaseConfig.getInstance().isSupportLcc() && SettingsFragment.this.mScuViewModel.getLccWorkSt() == ScuResponse.ON)) {
                                    NotificationHelper.getInstance().showToast(R.string.eps_unable_with_xpilot);
                                    SettingsFragment.this.vuiFeedbackClick(R.string.eps_unable_with_xpilot, tabLayout);
                                    StatisticUtils.sendStatistic(PageEnum.DRIVE_SETTING_PAGE, BtnEnum.DRIVE_SETTING_PAGE_EPS_BTN, Integer.valueOf(index + 1));
                                    return true;
                                }
                            } else {
                                NotificationHelper.getInstance().showToast(R.string.operation_fast_tips);
                                SettingsFragment.this.vuiFeedbackClick(R.string.operation_fast_tips, tabLayout);
                                return true;
                            }
                        }
                        return false;
                    }

                    @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
                    public void onTabChangeEnd(XTabLayout xTabLayout2, int index, boolean tabChange, boolean fromUser) {
                        if (fromUser) {
                            if (index == 0) {
                                SettingsFragment.this.mChassisVm.setSteeringEps(SteeringEpsMode.Soft);
                            } else if (index == 1) {
                                SettingsFragment.this.mChassisVm.setSteeringEps(SteeringEpsMode.Standard);
                            } else if (index == 2) {
                                SettingsFragment.this.mChassisVm.setSteeringEps(SteeringEpsMode.Sport);
                            }
                            SettingsFragment.this.updateCarImgView(5);
                            StatisticUtils.sendStatistic(PageEnum.DRIVE_SETTING_PAGE, BtnEnum.DRIVE_SETTING_PAGE_EPS_BTN, Integer.valueOf(index + 1));
                        }
                    }
                });
                setLiveDataObserver(this.mChassisVm.getSteeringEpsData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$_LcuRz0LV-YTs8m6RWIvBzPvEZo
                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        SettingsFragment.this.lambda$initDrive$61$SettingsFragment(xTabLayout, (SteeringEpsMode) obj);
                    }
                });
                SteeringEpsMode steeringEps = this.mChassisVm.getSteeringEps(SteeringEpsMode.Standard);
                if (steeringEps != null) {
                    if (CarBaseConfig.getInstance().isSupportXSport() && (XSportDriveMode.XPower == this.mVcuViewModel.getXSportDriveModeValue() || XSportDriveMode.Geek == this.mVcuViewModel.getXSportDriveModeValue())) {
                        xTabLayout.selectedNoneTab(false, false);
                    } else {
                        xTabLayout.selectTab(steeringEps.ordinal());
                    }
                }
            } else {
                view.findViewById(R.id.drive_experience_turn_layout).setVisibility(8);
                view.findViewById(R.id.listtwo_drive_experience_avh).setBackground(ResUtils.getDrawable(R.drawable.sub_item_top_bg));
            }
            boolean isSupportEpbSetting = CarBaseConfig.getInstance().isSupportEpbSetting();
            boolean isSupportTrailerMode = CarBaseConfig.getInstance().isSupportTrailerMode();
            boolean z = true;
            if (isSupportEpbSetting || isSupportTrailerMode) {
                view.findViewById(R.id.epb_and_trailer_layout).setVisibility(0);
                if (isSupportEpbSetting) {
                    view.findViewById(R.id.epb_layout).setVisibility(0);
                    this.mEpbLayout = (XFrameLayout) view.findViewById(R.id.epb_layout);
                    this.mEpbTitleTv = (XTextView) view.findViewById(R.id.epb_title);
                    this.mEpbDescTv = (XTextView) view.findViewById(R.id.epb_content);
                    this.mEpbLoading = (XLoading) view.findViewById(R.id.epb_loading);
                    this.mEpbLayout.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$e04X-KC36yexrsusG8ZMKyhuFvA
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            SettingsFragment.this.lambda$initDrive$62$SettingsFragment(view2);
                        }
                    });
                    setLiveDataObserver(this.mVcuViewModel.getGearLevelData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$J8o4NOzL0__37tVfXHSBW88Pt2M
                        @Override // androidx.lifecycle.Observer
                        public final void onChanged(Object obj) {
                            SettingsFragment.this.lambda$initDrive$63$SettingsFragment((GearLevel) obj);
                        }
                    });
                    setLiveDataObserver(this.mChassisVm.getApbStatusData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$F_mgG6ASWexkWO41nCccwc4nc5o
                        @Override // androidx.lifecycle.Observer
                        public final void onChanged(Object obj) {
                            SettingsFragment.this.lambda$initDrive$64$SettingsFragment((Integer) obj);
                        }
                    });
                    updateEpbView(this.mVcuViewModel.getGearLevelValue() != GearLevel.P, this.mChassisVm.getApbSystemStatus());
                }
                if (isSupportTrailerMode) {
                    view.findViewById(R.id.trailer_mode_layout).setVisibility(0);
                    view.findViewById(R.id.trailer_mode_layout).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$irVVJ6Vr2JAjjeqxkP_fdKFdDR4
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            SettingsFragment.this.lambda$initDrive$65$SettingsFragment(view2);
                        }
                    });
                }
            }
            if (CarBaseConfig.getInstance().isSupportTrailerHook()) {
                view.findViewById(R.id.trailer_layout).setVisibility(0);
                XFrameLayout xFrameLayout = (XFrameLayout) view.findViewById(R.id.trailer_mode);
                this.mTrailerLayout = xFrameLayout;
                xFrameLayout.setVisibility(0);
                this.mTrailerContent = (XTextView) view.findViewById(R.id.trailer_content);
                setLiveDataObserver(this.mChassisVm.getTtmSwitchData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$RC__p-ioNxP83-2Emi6t8cc02ak
                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        SettingsFragment.this.lambda$initDrive$66$SettingsFragment((TrailerHitchStatus) obj);
                    }
                });
                setLiveDataObserver(this.mChassisVm.getTrailerModeData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$j2C_53RnMxgiRGUsMLHg4JtUl8Y
                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        SettingsFragment.this.lambda$initDrive$67$SettingsFragment((Boolean) obj);
                    }
                });
                this.mTrailerLayout.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$Y9V85hcw8RmOhDAtmOvJO6EEFW0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        SettingsFragment.this.lambda$initDrive$68$SettingsFragment(view2);
                    }
                });
            }
            if (CarBaseConfig.getInstance().isSupportAirSuspension()) {
                View findViewById = view.findViewById(R.id.as_easy_loading_item);
                findViewById.setVisibility(0);
                XSwitch xSwitch = (XSwitch) findViewById.findViewById(R.id.x_list_sw);
                xSwitch.setOnInterceptListener(new XSwitch.OnInterceptListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$K4WKK9Ay6UK49CzpLcia67QHSIM
                    @Override // com.xiaopeng.xui.widget.XSwitch.OnInterceptListener
                    public final boolean onInterceptCheck(View view2, boolean z2) {
                        return SettingsFragment.this.lambda$initDrive$69$SettingsFragment(view2, z2);
                    }
                });
                xSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$_kpulEzkPJOceF7IFO8B_tumTU8
                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
                    public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                        SettingsFragment.this.lambda$initDrive$70$SettingsFragment(compoundButton, z2);
                    }
                });
                MutableLiveData<Boolean> asEasyLoadingMode = this.mChassisVm.getAsEasyLoadingMode();
                xSwitch.getClass();
                setLiveDataObserver(asEasyLoadingMode, new $$Lambda$SettingsFragment$aeIiqtRaGva9_dxYD21QFFdegA(xSwitch));
                xSwitch.setChecked(this.mChassisVm.isEasyLoadingEnable());
            }
            final XSwitch xSwitch2 = (XSwitch) view.findViewById(R.id.listtwo_drive_experience_avh).findViewById(R.id.x_list_sw);
            xSwitch2.setVuiLabel(getStringById(R.string.avh_vui_label));
            VuiUtils.addHasFeedbackProp(xSwitch2);
            xSwitch2.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$KNKfKYcDmH6suUGi9Jzhwh2M-bI
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view2, MotionEvent motionEvent) {
                    return SettingsFragment.this.lambda$initDrive$71$SettingsFragment(view2, motionEvent);
                }
            });
            xSwitch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$-ujTyYFhZ605EmPpGOHDsbUaA5E
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                    SettingsFragment.this.lambda$initDrive$72$SettingsFragment(compoundButton, z2);
                }
            });
            LiveData<Boolean> avhSwData = this.mChassisVm.getAvhSwData();
            xSwitch2.getClass();
            setLiveDataObserver(avhSwData, new $$Lambda$SettingsFragment$aeIiqtRaGva9_dxYD21QFFdegA(xSwitch2));
            setLiveDataObserver(this.mChassisVm.getAvhFaultData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$I29AaD3e7YNObuswNTQYKbbfiHM
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SettingsFragment.this.lambda$initDrive$73$SettingsFragment(xSwitch2, (Boolean) obj);
                }
            });
            xSwitch2.setChecked(this.mChassisVm.getAvhForUI());
            if (CarBaseConfig.getInstance().isSupportHdc()) {
                view.findViewById(R.id.listtwo_drive_experience_hdc).setVisibility(0);
                final XSwitch xSwitch3 = (XSwitch) view.findViewById(R.id.listtwo_drive_experience_hdc).findViewById(R.id.x_list_sw);
                xSwitch3.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$2bklGsmFwnW8S-FWf3v2QDhShRs
                    @Override // android.view.View.OnTouchListener
                    public final boolean onTouch(View view2, MotionEvent motionEvent) {
                        return SettingsFragment.this.lambda$initDrive$74$SettingsFragment(xSwitch3, view2, motionEvent);
                    }
                });
                setLiveDataObserver(this.mChassisVm.getHdcData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$XddFJy6wJuvHjli-G9p0AnOtQXA
                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        SettingsFragment.lambda$initDrive$75(XSwitch.this, (Boolean) obj);
                    }
                });
                setLiveDataObserver(this.mChassisVm.getHdcFaultData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$2XTYqV0oNFFtEXrPUGfhGjgo-Ks
                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        SettingsFragment.this.lambda$initDrive$76$SettingsFragment(xSwitch3, (Boolean) obj);
                    }
                });
                if (this.mChassisVm.getHdcFault() || !this.mChassisVm.getHdc()) {
                    z = false;
                }
                xSwitch3.setChecked(z);
            }
            View findViewById2 = view.findViewById(R.id.listtwo_drive_experience_esp);
            final XSwitch xSwitch4 = (XSwitch) findViewById2.findViewById(R.id.x_list_sw);
            xSwitch4.setVuiLabel(getStringById(R.string.esp_title));
            VuiUtils.addHasFeedbackProp(xSwitch4);
            xSwitch4.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$E08VblBldkFKmkMR3RfICa3eJXM
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view2, MotionEvent motionEvent) {
                    return SettingsFragment.this.lambda$initDrive$77$SettingsFragment(view2, motionEvent);
                }
            });
            xSwitch4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$CYGvQDf6MOVdz9Dlk6IGcxdHwNY
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                    SettingsFragment.this.lambda$initDrive$79$SettingsFragment(xSwitch4, compoundButton, z2);
                }
            });
            setLiveDataObserver(this.mChassisVm.getEspModeData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$Vi1Syr5Lwh1izwEkqaFeH4iy1eg
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SettingsFragment.this.lambda$initDrive$80$SettingsFragment(xSwitch4, (Boolean) obj);
                }
            });
            setLiveDataObserver(this.mChassisVm.getEspFaultData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$z7C_2nlXH0A4SIILnYwjItNt9a4
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SettingsFragment.this.lambda$initDrive$81$SettingsFragment(xSwitch4, (Boolean) obj);
                }
            });
            xSwitch4.setChecked(this.mChassisVm.getEspForUI());
            if (CarBaseConfig.getInstance().isSupportTrailerRv() && !CarBaseConfig.getInstance().isSupportTrailerHook()) {
                View findViewById3 = view.findViewById(R.id.listtwo_trailer_mode_sw);
                findViewById3.setVisibility(0);
                XSwitch xSwitch5 = (XSwitch) findViewById3.findViewById(R.id.x_list_sw);
                xSwitch5.setChecked(this.mChassisVm.getTrailerModeStatus());
                xSwitch5.setOnInterceptListener(new XSwitch.OnInterceptListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$3-bFOy5hDVSOiB0VEBD5kFV3YJ0
                    @Override // com.xiaopeng.xui.widget.XSwitch.OnInterceptListener
                    public final boolean onInterceptCheck(View view2, boolean z2) {
                        return SettingsFragment.this.lambda$initDrive$82$SettingsFragment(view2, z2);
                    }
                });
                xSwitch5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$jS3OARQqgE6a9pGL6ZiVnqoO2Xs
                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
                    public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                        SettingsFragment.this.lambda$initDrive$83$SettingsFragment(compoundButton, z2);
                    }
                });
            }
            XListTwo xListTwo = (XListTwo) view.findViewById(R.id.listtwo_drive_experience_snow);
            if (CarBaseConfig.getInstance().isSupportSnowMode()) {
                final XSwitch xSwitch6 = (XSwitch) xListTwo.findViewById(R.id.x_list_sw);
                xSwitch6.setVuiLabel(getStringById(R.string.snow_title));
                VuiUtils.addHasFeedbackProp(xSwitch6);
                xSwitch6.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$K-TfDz5-17L6no81qWWpaG_1EEU
                    @Override // android.view.View.OnTouchListener
                    public final boolean onTouch(View view2, MotionEvent motionEvent) {
                        return SettingsFragment.this.lambda$initDrive$84$SettingsFragment(view2, motionEvent);
                    }
                });
                xSwitch6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$gsDc-5DgJis797tbrntQ-O7Sv4k
                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
                    public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                        SettingsFragment.this.lambda$initDrive$85$SettingsFragment(compoundButton, z2);
                    }
                });
                setLiveDataObserver(this.mVcuViewModel.getSnowModeData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$Or_7AxrPvgGtua7v2uRMTvXRlXM
                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        SettingsFragment.lambda$initDrive$86(XSwitch.this, (Boolean) obj);
                    }
                });
                xSwitch6.setChecked(this.mVcuViewModel.getSnowMode());
                return;
            }
            xListTwo.setVisibility(8);
            findViewById2.setBackground(ResUtils.getDrawable(R.drawable.sub_item_bottom_bg));
        }
    }

    public /* synthetic */ void lambda$initDrive$61$SettingsFragment(final XTabLayout epsTab, SteeringEpsMode mode) {
        if (mode != null) {
            if (CarBaseConfig.getInstance().isSupportXSport() && (XSportDriveMode.XPower == this.mVcuViewModel.getXSportDriveModeValue() || XSportDriveMode.Geek == this.mVcuViewModel.getXSportDriveModeValue())) {
                epsTab.selectedNoneTab(false, false);
            } else {
                epsTab.selectTab(mode.ordinal());
            }
        }
    }

    public /* synthetic */ void lambda$initDrive$62$SettingsFragment(View v) {
        this.mChassisVm.controlEpb();
    }

    public /* synthetic */ void lambda$initDrive$63$SettingsFragment(GearLevel gearLevel) {
        if (gearLevel != null) {
            updateEpbView(gearLevel != GearLevel.P, this.mChassisVm.getApbSystemStatus());
        }
    }

    public /* synthetic */ void lambda$initDrive$64$SettingsFragment(Integer status) {
        if (status != null) {
            updateEpbView(this.mVcuViewModel.getGearLevelValue() != GearLevel.P, status.intValue());
        }
    }

    public /* synthetic */ void lambda$initDrive$65$SettingsFragment(View v) {
        if (this.mChassisVm.getAsFaultStatus()) {
            NotificationHelper.getInstance().showToast(R.string.trailer_mode_error);
        } else if (CarBaseConfig.getInstance().isSupportXSport() && XSportDriveMode.XPower == this.mVcuViewModel.getXSportDriveModeValue()) {
            NotificationHelper.getInstance().showToast(R.string.exit_with_drive_mode_xpower_on);
        } else {
            Intent intent = new Intent(GlobalConstant.ACTION.ACTION_TRAILER_MODE);
            intent.setFlags(ClientDefaults.MAX_MSG_SIZE);
            intent.addFlags(1024);
            App.getInstance().startActivity(intent);
        }
    }

    public /* synthetic */ void lambda$initDrive$66$SettingsFragment(TrailerHitchStatus status) {
        updateTrailerContentView(status, this.mChassisVm.getTrailerModeStatus());
    }

    public /* synthetic */ void lambda$initDrive$67$SettingsFragment(Boolean enable) {
        updateTrailerContentView(this.mChassisVm.getTrailerHitchStatus(), enable.booleanValue());
    }

    public /* synthetic */ void lambda$initDrive$68$SettingsFragment(View v) {
        showTrailerModePanel();
    }

    public /* synthetic */ boolean lambda$initDrive$69$SettingsFragment(View buttonView, boolean isChecked) {
        if (buttonView.isPressed() && isChecked) {
            if (this.mVcuViewModel.getCarSpeed() >= 3.0f) {
                NotificationHelper.getInstance().showToast(R.string.as_easy_loading_open_failed_for_moving);
                return true;
            } else if (this.mChassisVm.getAirSuspensionRepairMode() || this.mChassisVm.getTrailerModeStatus()) {
                NotificationHelper.getInstance().showToast(R.string.as_easy_loading_open_failed_for_trailer_mode);
                return true;
            } else if (CarBaseConfig.getInstance().isSupportXSport() && XSportDriveMode.XPower == this.mVcuViewModel.getXSportDriveModeValue()) {
                NotificationHelper.getInstance().showToast(R.string.exit_with_drive_mode_xpower_on);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public /* synthetic */ void lambda$initDrive$70$SettingsFragment(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed()) {
            this.mChassisVm.setEasyLoadingSwitch(isChecked);
        }
    }

    public /* synthetic */ boolean lambda$initDrive$71$SettingsFragment(View v, MotionEvent event) {
        if (event.getAction() == 1) {
            if (ClickHelper.isFastClick(R.id.listtwo_drive_experience_avh, 500L, false)) {
                return true;
            }
            if (this.mChassisVm.getAvhFault()) {
                NotificationHelper.getInstance().showToast(R.string.avh_open_fail);
                return true;
            } else if (CarBaseConfig.getInstance().isSupportDriveModeNewArch() && this.mVcuViewModel.getNewDriveArchXPedalMode()) {
                NotificationHelper.getInstance().showToast(R.string.exit_with_x_pedal_on);
                return true;
            }
        }
        return false;
    }

    public /* synthetic */ void lambda$initDrive$72$SettingsFragment(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed() || isVuiAction(buttonView)) {
            this.mChassisVm.setAvhSw(isChecked);
            updateCarImgView(5);
            StatisticUtils.sendStatistic(PageEnum.DRIVE_SETTING_PAGE, BtnEnum.DRIVE_SETTING_PAGE_AVH_BTN, Integer.valueOf(StatisticUtils.getSwitchOnOff(isChecked)));
        }
    }

    public /* synthetic */ void lambda$initDrive$73$SettingsFragment(final XSwitch mAvhSw, Boolean isFault) {
        if (isFault.booleanValue()) {
            mAvhSw.setChecked(false);
        } else {
            mAvhSw.setChecked(this.mChassisVm.getAvhForUI());
        }
    }

    public /* synthetic */ boolean lambda$initDrive$74$SettingsFragment(final XSwitch mHdcSw, View v, MotionEvent event) {
        if (event.getAction() != 1) {
            return false;
        }
        this.mChassisVm.setHdc(!mHdcSw.isChecked());
        return true;
    }

    public static /* synthetic */ void lambda$initDrive$75(final XSwitch mHdcSw, Boolean enabled) {
        if (enabled != null) {
            mHdcSw.setChecked(enabled.booleanValue());
        }
    }

    public /* synthetic */ void lambda$initDrive$76$SettingsFragment(final XSwitch mHdcSw, Boolean isFault) {
        if (isFault != null) {
            if (isFault.booleanValue()) {
                mHdcSw.setChecked(false);
            } else {
                mHdcSw.setChecked(this.mChassisVm.getHdc());
            }
        }
    }

    public /* synthetic */ boolean lambda$initDrive$77$SettingsFragment(View v, MotionEvent event) {
        if (event.getAction() == 1) {
            if (CarBaseConfig.getInstance().isSupportXSport()) {
                if (XSportDriveMode.Geek == this.mVcuViewModel.getXSportDriveModeValue()) {
                    NotificationHelper.getInstance().showToast(R.string.eps_unable_with_geek);
                    return true;
                } else if (XSportDriveMode.XPower == this.mVcuViewModel.getXSportDriveModeValue()) {
                    NotificationHelper.getInstance().showToast(R.string.eps_unable_with_xpower);
                    return true;
                }
            }
            if (!CarBaseConfig.getInstance().isSupportDriveModeNewArch()) {
                if (ClickHelper.isFastClick(R.id.listtwo_drive_experience_esp, R.id.tab_drive_experience_turn, 500L, false)) {
                    return true;
                }
            } else if (ClickHelper.isFastClick(R.id.listtwo_drive_experience_esp, 500L, false)) {
                return true;
            }
            if (this.mChassisVm.getEspFault()) {
                NotificationHelper.getInstance().showToast(R.string.esp_open_fail);
                return true;
            } else if (CarBaseConfig.getInstance().isSupportDriveModeNewArch() && this.mVcuViewModel.getDriveModeValue() == DriveMode.OffRoad) {
                NotificationHelper.getInstance().showToast(R.string.exit_with_drive_mode_mud_on);
                return true;
            }
        }
        return false;
    }

    public /* synthetic */ void lambda$initDrive$79$SettingsFragment(final XSwitch mEspSw, CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed() || isVuiAction(buttonView)) {
            if (this.mChassisVm.getEspSw() && this.mVcuViewModel.getCarSpeed() > 80.0f) {
                if (getActivity() != null) {
                    NotificationHelper.getInstance().showToast(R.string.esp_unable_with_running);
                }
                ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$ApwlBUTovI3dHe-F1v2recRkjGg
                    @Override // java.lang.Runnable
                    public final void run() {
                        XSwitch.this.setChecked(true);
                    }
                });
                return;
            }
            this.mChassisVm.setEspSw();
            updateCarImgView(5);
            StatisticUtils.sendStatistic(PageEnum.DRIVE_SETTING_PAGE, BtnEnum.DRIVE_SETTING_PAGE_ESP_BTN, Integer.valueOf(StatisticUtils.getSwitchOnOff(isChecked)));
        }
    }

    public /* synthetic */ void lambda$initDrive$80$SettingsFragment(final XSwitch mEspSw, Boolean enable) {
        mEspSw.setChecked(this.mChassisVm.getEspForUI());
    }

    public /* synthetic */ void lambda$initDrive$81$SettingsFragment(final XSwitch mEspSw, Boolean fault) {
        mEspSw.setChecked(this.mChassisVm.getEspForUI());
    }

    public /* synthetic */ boolean lambda$initDrive$82$SettingsFragment(View buttonView, boolean isChecked) {
        if (buttonView.isPressed() && isChecked) {
            showTrailerModeOpenConfirmDialog();
            return true;
        }
        return false;
    }

    public /* synthetic */ void lambda$initDrive$83$SettingsFragment(CompoundButton buttonView, boolean isChecked) {
        if ((buttonView.isPressed() || isVuiAction(buttonView)) && !isChecked) {
            this.mChassisVm.setTrailerModeStatus(false);
        }
    }

    public /* synthetic */ boolean lambda$initDrive$84$SettingsFragment(View v, MotionEvent event) {
        if (event.getAction() != 1) {
            return false;
        }
        if (DriveMode.EcoPlus == this.mVcuViewModel.getDriveModeValue()) {
            NotificationHelper.getInstance().showToast(R.string.drive_mode_eco_plus_with_open_snow_mode);
            return true;
        } else if (DriveMode.Comfort == this.mVcuViewModel.getDriveModeValue()) {
            NotificationHelper.getInstance().showToast(R.string.drive_mode_anti_sickness_with_open_snow_mode);
            return true;
        } else {
            return false;
        }
    }

    public /* synthetic */ void lambda$initDrive$85$SettingsFragment(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed() || isVuiAction(buttonView)) {
            this.mVcuViewModel.setSnowMode(isChecked);
            updateCarImgView(5);
            StatisticUtils.sendStatistic(PageEnum.DRIVE_SETTING_PAGE, BtnEnum.DRIVE_SETTING_PAGE_SNOW_BTN, Integer.valueOf(StatisticUtils.getSwitchOnOff(isChecked)));
        }
    }

    public static /* synthetic */ void lambda$initDrive$86(final XSwitch mSnowSw, Boolean enable) {
        if (enable != null) {
            mSnowSw.setChecked(enable.booleanValue());
        }
    }

    private void updateChildModeView(boolean enable) {
        if (this.mChildModeLayout == null) {
            return;
        }
        if (enable) {
            this.mChildModeDesTv.setText(R.string.child_mode_desc_on);
            this.mChildModeLayout.setBackground(ResUtils.getDrawable(R.drawable.sub_item_on_bg));
            this.mChildModeTitleTv.setTextColor(ResUtils.getColor(R.color.epb_on_disable_content_text_color));
            this.mChildModeDesTv.setTextColor(ResUtils.getColor(R.color.epb_on_content_color));
            this.mChildModeTTv.setImageResource(R.drawable.ic_btn_triangle_active);
            return;
        }
        this.mChildModeDesTv.setText(R.string.child_mode_desc_off);
        this.mChildModeLayout.setBackground(ResUtils.getDrawable(R.drawable.sub_item_off_bg));
        this.mChildModeTitleTv.setTextColor(ResUtils.getColor(R.color.epb_off_normal_content_text_color));
        this.mChildModeDesTv.setTextColor(ResUtils.getColor(R.color.epb_off_content_color));
        this.mChildModeTTv.setImageResource(R.drawable.ic_btn_triangle);
    }

    private void updateEpbView(boolean enable, int epbStatus) {
        XFrameLayout xFrameLayout = this.mEpbLayout;
        if (xFrameLayout == null) {
            return;
        }
        xFrameLayout.setEnabled(enable);
        if (epbStatus == 1) {
            this.mEpbTitleTv.setVisibility(0);
            this.mEpbDescTv.setVisibility(0);
            this.mEpbDescTv.setText(R.string.epb_settings_off_desc);
            this.mEpbTitleTv.setTextColor(ResUtils.getColor(enable ? R.color.epb_off_normal_content_text_color : R.color.epb_off_disable_content_text_color));
            this.mEpbLoading.setVisibility(8);
            this.mEpbLayout.setBackground(ResUtils.getDrawable(R.drawable.sub_item_off_bg));
            this.mEpbTitleTv.setTextColor(ResUtils.getColor(R.color.epb_off_title_text_color));
            this.mEpbDescTv.setTextColor(ResUtils.getColor(R.color.epb_off_content_color));
        } else if (epbStatus != 2) {
            if (epbStatus == 3 || epbStatus == 4) {
                this.mEpbLoading.setVisibility(0);
                this.mEpbTitleTv.setVisibility(8);
                this.mEpbDescTv.setVisibility(8);
            }
        } else {
            this.mEpbTitleTv.setVisibility(0);
            this.mEpbDescTv.setVisibility(0);
            this.mEpbDescTv.setText(R.string.epb_settings_on_desc);
            this.mEpbLoading.setVisibility(8);
            this.mEpbLayout.setBackground(ResUtils.getDrawable(R.drawable.sub_item_on_bg));
            this.mEpbTitleTv.setTextColor(ResUtils.getColor(R.color.epb_on_title_text_color));
            this.mEpbDescTv.setTextColor(ResUtils.getColor(R.color.epb_on_content_color));
        }
    }

    private void showTrailerModeOpenConfirmDialog() {
        if (this.mTrailerModeOpenConfirmDialog == null && getContext() != null) {
            XDialog positiveButton = new XDialog(getContext()).setTitle(R.string.trailer_mode_sw).setMessage(R.string.trailer_mode_sw_open_confirm_without_ttm_dialog_content).setPositiveButton(R.string.btn_cancel).setPositiveButton(R.string.btn_confirm_open, new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$YBVA72Kfw5dHOHQ7w5YH7dbmhjg
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i) {
                    SettingsFragment.this.lambda$showTrailerModeOpenConfirmDialog$87$SettingsFragment(xDialog, i);
                }
            });
            this.mTrailerModeOpenConfirmDialog = positiveButton;
            positiveButton.setSystemDialog(2048);
        }
        if (this.mTrailerModeOpenConfirmDialog.isShowing()) {
            return;
        }
        this.mTrailerModeOpenConfirmDialog.show();
    }

    public /* synthetic */ void lambda$showTrailerModeOpenConfirmDialog$87$SettingsFragment(XDialog xDialog, int i) {
        if (this.mVcuViewModel.getGearLevelValue() != GearLevel.P) {
            NotificationHelper.getInstance().showToast(R.string.trailer_hook_without_p_gear);
        } else if (this.mVcuViewModel.getNewDriveArchXPedalMode()) {
            NotificationHelper.getInstance().showToast(R.string.trailer_hook_open_with_xpedal);
        } else {
            this.mChassisVm.setTrailerModeStatus(true);
        }
    }

    private void updateTrailerContentView(TrailerHitchStatus status, boolean enable) {
        if (status == TrailerHitchStatus.Open && enable) {
            this.mTrailerContent.setText(R.string.trailer_mode_sw_open);
        } else if (status == TrailerHitchStatus.Open) {
            this.mTrailerContent.setText(R.string.trailer_hook_sw_open);
        } else {
            this.mTrailerContent.setText(R.string.trailer_hook_sw_close);
        }
    }

    private void showChildModePanel() {
        Intent intent = new Intent(this.mContext, CarControlService.class);
        intent.setAction(GlobalConstant.ACTION.ACTION_SHOW_CHILD_MODE_CONTROL_PANEL);
        App.getInstance().startService(intent);
    }

    private void showTrailerModePanel() {
        Intent intent = new Intent(this.mContext, CarControlService.class);
        intent.setAction(GlobalConstant.ACTION.ACTION_SHOW_TRAILER_MODE_CONTROL_PANEL);
        App.getInstance().startService(intent);
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (XThemeManager.isThemeChanged(newConfig)) {
            this.mAvasControlScene.onConfigurationChanged(newConfig);
            this.mWiperControlScene.onConfigurationChanged(newConfig);
            if (CarBaseConfig.getInstance().isSupportEpbSetting()) {
                ThreadUtils.runOnMainThreadDelay(this.mUpdateEpbViewRun, 200L);
            }
        }
    }

    private void initAvas(View container) {
        AvasControlScene avasControlScene = new AvasControlScene(getSceneId(), container, this);
        this.mAvasControlScene = avasControlScene;
        addScene(avasControlScene);
    }

    private void initAvasSpeaker(View view) {
        if (CarBaseConfig.getInstance().isSupportAvasLoudSpeaker()) {
            view.findViewById(R.id.settings_loud_speaker_tv).setVisibility(0);
            view.findViewById(R.id.multimedia_item).setVisibility(0);
            XSwitch xSwitch = (XSwitch) view.findViewById(R.id.multimedia_item).findViewById(R.id.x_list_sw);
            xSwitch.setChecked(this.mAvasViewModel.getAvasSpeakerSw());
            xSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$4dtLqklrzqtum35_c0zLojmZBVQ
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    SettingsFragment.this.lambda$initAvasSpeaker$88$SettingsFragment(compoundButton, z);
                }
            });
        }
    }

    public /* synthetic */ void lambda$initAvasSpeaker$88$SettingsFragment(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed() || isVuiAction(buttonView)) {
            this.mAvasViewModel.setAvasSpeakerSw(isChecked);
        }
    }

    private void initAirSuspension(View container) {
        if (CarBaseConfig.getInstance().isSupportAsWelcomeMode()) {
            View findViewById = container.findViewById(R.id.as_welcome_item);
            findViewById.setVisibility(0);
            XSwitch xSwitch = (XSwitch) findViewById.findViewById(R.id.x_list_sw);
            xSwitch.setVuiLabel(getStringById(R.string.as_welcome_title));
            xSwitch.setChecked(this.mChassisVm.isAirSuspensionWelcomeEnable());
            xSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$zke51cKpfoYwRH7_p_uavDi8B84
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    SettingsFragment.this.lambda$initAirSuspension$89$SettingsFragment(compoundButton, z);
                }
            });
            MutableLiveData<Boolean> asWelcomeMode = this.mChassisVm.getAsWelcomeMode();
            xSwitch.getClass();
            setLiveDataObserver(asWelcomeMode, new $$Lambda$SettingsFragment$aeIiqtRaGva9_dxYD21QFFdegA(xSwitch));
        }
    }

    public /* synthetic */ void lambda$initAirSuspension$89$SettingsFragment(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed() || isVuiAction(buttonView)) {
            this.mChassisVm.setAirSuspensionWelcome(isChecked);
        }
    }

    private void initBelt(View container) {
        this.mDrvEsbView = (XListTwo) container.findViewById(R.id.esb_sw_item);
        this.mRsbWarningView = (XListTwo) container.findViewById(R.id.rear_belt_sw_item);
        boolean z = true;
        boolean z2 = false;
        if (CarBaseConfig.getInstance().isSupportEsb()) {
            this.mDrvEsbView.setVisibility(0);
            XSwitch xSwitch = (XSwitch) this.mDrvEsbView.findViewById(R.id.x_list_sw);
            this.switchDrvEsb = xSwitch;
            xSwitch.setVuiLabel(getStringById(R.string.drv_belt_tv));
            this.switchDrvEsb.setVuiLayoutLoadable(true);
            VuiUtils.addHasFeedbackProp(this.switchDrvEsb);
            this.switchDrvEsb.setOnInterceptListener(new XSwitch.OnInterceptListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$glJ_fH3D63TgCpk6fiIftZjiyx8
                @Override // com.xiaopeng.xui.widget.XSwitch.OnInterceptListener
                public final boolean onInterceptCheck(View view, boolean z3) {
                    return SettingsFragment.this.lambda$initBelt$90$SettingsFragment(view, z3);
                }
            });
            this.switchDrvEsb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$MgGwDGn5YEsrFcx7gXwA8g2soMY
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z3) {
                    SettingsFragment.this.lambda$initBelt$91$SettingsFragment(compoundButton, z3);
                }
            });
            this.switchDrvEsb.setChecked(this.mSeatViewModel.getEsbMode());
            setLiveDataObserver(this.mSeatViewModel.getEsbModeData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$We_MpvjuOMs9M452RrcSAJ-vUf4
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SettingsFragment.this.lambda$initBelt$92$SettingsFragment((Boolean) obj);
                }
            });
            updateDrvEsbConfigMode(this.mSeatViewModel.getEsbConfigMode());
            setLiveDataObserver(this.mSeatViewModel.getEsbConfigModeData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$e6LzokNpEYOwttdyS7Tfldvj7W4
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SettingsFragment.this.lambda$initBelt$93$SettingsFragment((String) obj);
                }
            });
            if (this.mDrvEsbView.getVisibility() != 8) {
                z = false;
            }
        } else {
            this.mDrvEsbView.setVisibility(8);
            this.mRsbWarningView.setBackground(ResUtils.getDrawable(R.drawable.sub_item_bg));
        }
        if (CarBaseConfig.getInstance().isSupportRearBeltWarningSwitch()) {
            XSwitch xSwitch2 = (XSwitch) this.mRsbWarningView.findViewById(R.id.x_list_sw);
            xSwitch2.setVuiLabel(getStringById(R.string.rear_belt_tv));
            xSwitch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$UTLRw9xMPAl9MVez5PEpx7h3KcI
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z3) {
                    SettingsFragment.this.lambda$initBelt$94$SettingsFragment(compoundButton, z3);
                }
            });
            xSwitch2.setChecked(this.mSeatViewModel.getBackBeltSw());
            LiveData<Boolean> backBeltWaringData = this.mSeatViewModel.getBackBeltWaringData();
            xSwitch2.getClass();
            setLiveDataObserver(backBeltWaringData, new $$Lambda$SettingsFragment$aeIiqtRaGva9_dxYD21QFFdegA(xSwitch2));
            z = false;
        } else {
            this.mRsbWarningView.setVisibility(8);
        }
        if (CarBaseConfig.getInstance().isSupportPsnSrsSwitch()) {
            XListSingle xListSingle = (XListSingle) container.findViewById(R.id.psn_srs_sw_item);
            xListSingle.setVisibility(0);
            XSwitch xSwitch3 = (XSwitch) xListSingle.findViewById(R.id.x_list_sw);
            this.mPsnSrsSw = xSwitch3;
            xSwitch3.setVuiLabel(getStringById(R.string.psn_srs_tv));
            this.mPsnSrsSw.setOnInterceptListener(new XSwitch.OnInterceptListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$Cmbgbf8qnaVA8jVxBG5Z6hT0LJw
                @Override // com.xiaopeng.xui.widget.XSwitch.OnInterceptListener
                public final boolean onInterceptCheck(View view, boolean z3) {
                    return SettingsFragment.this.lambda$initBelt$95$SettingsFragment(view, z3);
                }
            });
            this.mPsnSrsSw.setChecked(this.mSeatViewModel.getPsnSrsEnable());
            LiveData<Boolean> psnSrsEnableData = this.mSeatViewModel.getPsnSrsEnableData();
            XSwitch xSwitch4 = this.mPsnSrsSw;
            xSwitch4.getClass();
            setLiveDataObserver(psnSrsEnableData, new $$Lambda$SettingsFragment$aeIiqtRaGva9_dxYD21QFFdegA(xSwitch4));
        } else {
            z2 = z;
        }
        if (z2) {
            container.findViewById(R.id.seat_and_belt_main_tv).setVisibility(8);
        }
    }

    public /* synthetic */ boolean lambda$initBelt$90$SettingsFragment(View buttonView, boolean isChecked) {
        if (!buttonView.isPressed() || isChecked) {
            return false;
        }
        showCloseSafeBeltDialog();
        updateCarImgView(6);
        StatisticUtils.sendStatistic(PageEnum.SETTING_OTHER_PAGE, BtnEnum.SETTING_OTHER_PAGE_DRVESB_BTN, Integer.valueOf(StatisticUtils.getSwitchOnOff(false)));
        return true;
    }

    public /* synthetic */ void lambda$initBelt$91$SettingsFragment(CompoundButton buttonView, boolean isChecked) {
        if ((buttonView.isPressed() || isVuiAction(buttonView)) && isChecked) {
            this.mSeatViewModel.setEsbMode(true);
            updateCarImgView(6);
            StatisticUtils.sendStatistic(PageEnum.SETTING_OTHER_PAGE, BtnEnum.SETTING_OTHER_PAGE_DRVESB_BTN, Integer.valueOf(StatisticUtils.getSwitchOnOff(true)));
        }
    }

    public /* synthetic */ void lambda$initBelt$92$SettingsFragment(Boolean enabled) {
        LogUtils.i(this.TAG, "update drv esb sw : " + enabled);
        this.switchDrvEsb.setChecked(enabled.booleanValue());
    }

    public /* synthetic */ void lambda$initBelt$93$SettingsFragment(String configMode) {
        XDialog xDialog = this.mSafeBeltDialog;
        if (xDialog != null) {
            xDialog.dismiss();
            this.mSafeBeltDialog = null;
        }
        updateDrvEsbConfigMode(configMode);
    }

    public /* synthetic */ void lambda$initBelt$94$SettingsFragment(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed() || isVuiAction(buttonView)) {
            this.mSeatViewModel.setBackBeltSw(isChecked);
            updateCarImgView(6);
            StatisticUtils.sendStatistic(PageEnum.SETTING_OTHER_PAGE, BtnEnum.SETTING_OTHER_PAGE_NO_BELT_WARN_BTN, Integer.valueOf(StatisticUtils.getSwitchOnOff(isChecked)));
        }
    }

    public /* synthetic */ boolean lambda$initBelt$95$SettingsFragment(View buttonView, boolean isChecked) {
        boolean z = false;
        if (buttonView.isPressed()) {
            z = true;
            if (ClickHelper.isFastClick(1000L, false)) {
                return true;
            }
            if (!isChecked) {
                showPsnSrsCloseConfirmDialog();
            } else {
                this.mSeatViewModel.setPsnSrsEnable(true);
            }
        }
        return z;
    }

    private void showCloseSafeBeltDialog() {
        int i;
        int i2;
        if (this.mSafeBeltDialog == null && getActivity() != null) {
            String esbConfigMode = this.mSeatViewModel.getEsbConfigMode();
            char c = 65535;
            int hashCode = esbConfigMode.hashCode();
            if (hashCode != 49) {
                if (hashCode == 50 && esbConfigMode.equals("2")) {
                    c = 0;
                }
            } else if (esbConfigMode.equals("1")) {
                c = 1;
            }
            if (c == 0) {
                i = R.string.drv_belt_tv_new;
                i2 = R.string.electronic_seat_belt_off_msg_new;
            } else {
                i = R.string.drv_belt_tv;
                i2 = R.string.electronic_seat_belt_off_msg;
            }
            XDialog onDismissListener = new XDialog(getActivity(), R.style.show_close_safe_belt_dialog).setTitle(i).setMessage(i2).setNegativeButton(getStringById(R.string.btn_cancel), (XDialogInterface.OnClickListener) null).setPositiveButton(getStringById(R.string.btn_confirm), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$4PcKMEZBzMQqAWANoUaUhHmm100
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i3) {
                    SettingsFragment.this.lambda$showCloseSafeBeltDialog$96$SettingsFragment(xDialog, i3);
                }
            }).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$YvAgkK1oHai0MpjH3QS3iBvTdIU
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    SettingsFragment.this.lambda$showCloseSafeBeltDialog$97$SettingsFragment(dialogInterface);
                }
            });
            this.mSafeBeltDialog = onDismissListener;
            onDismissListener.setSystemDialog(2048);
        }
        if (this.mSafeBeltDialog.isShowing()) {
            return;
        }
        this.mSafeBeltDialog.show();
        VuiManager.instance().initVuiDialog(this.mSafeBeltDialog, getContext(), VuiManager.SCENE_SAFE_BELT_SETTING);
    }

    public /* synthetic */ void lambda$showCloseSafeBeltDialog$96$SettingsFragment(XDialog xDialog, int i) {
        this.mSeatViewModel.setEsbMode(false);
        this.mIsDrvEsbConfirmed = true;
    }

    public /* synthetic */ void lambda$showCloseSafeBeltDialog$97$SettingsFragment(DialogInterface dialog) {
        if (!this.mIsDrvEsbConfirmed) {
            this.switchDrvEsb.setChecked(true);
        } else {
            this.mIsDrvEsbConfirmed = false;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x005b, code lost:
        if (r6.equals("2") == false) goto L23;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void updateDrvEsbConfigMode(java.lang.String r6) {
        /*
            r5 = this;
            java.lang.String r0 = r5.TAG
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "updateDrvEsbConfigMode with configMode : "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r6)
            java.lang.String r1 = r1.toString()
            com.xiaopeng.carcontrol.util.LogUtils.i(r0, r1)
            if (r6 != 0) goto L22
            java.lang.String r6 = r5.TAG
            java.lang.String r0 = "updateDrvEsbText failed configMode is null"
            com.xiaopeng.carcontrol.util.LogUtils.w(r6, r0)
            return
        L22:
            com.xiaopeng.carcontrol.config.CarBaseConfig r0 = com.xiaopeng.carcontrol.config.CarBaseConfig.getInstance()
            boolean r0 = r0.isSupportEsbForLdw()
            java.lang.String r1 = "2"
            if (r0 != 0) goto L43
            boolean r0 = r1.equals(r6)
            if (r0 == 0) goto L43
            com.xiaopeng.xui.widget.XListTwo r6 = r5.mDrvEsbView
            r0 = 8
            r6.setVisibility(r0)
            com.xiaopeng.xui.widget.XListTwo r6 = r5.mRsbWarningView
            int r0 = com.xiaopeng.carcontrolmodule.R.drawable.sub_item_bg
            r6.setBackgroundResource(r0)
            goto Lae
        L43:
            com.xiaopeng.xui.widget.XListTwo r0 = r5.mDrvEsbView
            r2 = 0
            r0.setVisibility(r2)
            r0 = -1
            int r3 = r6.hashCode()
            r4 = 49
            if (r3 == r4) goto L5e
            r4 = 50
            if (r3 == r4) goto L57
            goto L68
        L57:
            boolean r6 = r6.equals(r1)
            if (r6 == 0) goto L68
            goto L69
        L5e:
            java.lang.String r1 = "1"
            boolean r6 = r6.equals(r1)
            if (r6 == 0) goto L68
            r2 = 1
            goto L69
        L68:
            r2 = r0
        L69:
            if (r2 == 0) goto L8d
            com.xiaopeng.xui.widget.XListTwo r6 = r5.mDrvEsbView
            int r0 = com.xiaopeng.carcontrolmodule.R.string.drv_belt_tv
            java.lang.String r0 = com.xiaopeng.carcontrol.util.ResUtils.getString(r0)
            r6.setText(r0)
            com.xiaopeng.xui.widget.XListTwo r6 = r5.mDrvEsbView
            int r0 = com.xiaopeng.carcontrolmodule.R.string.drv_belt_desc
            java.lang.String r0 = com.xiaopeng.carcontrol.util.ResUtils.getString(r0)
            r6.setTextSub(r0)
            com.xiaopeng.xui.widget.XSwitch r6 = r5.switchDrvEsb
            int r0 = com.xiaopeng.carcontrolmodule.R.string.drv_belt_tv
            java.lang.String r0 = r5.getStringById(r0)
            r6.setVuiLabel(r0)
            goto Lae
        L8d:
            com.xiaopeng.xui.widget.XListTwo r6 = r5.mDrvEsbView
            int r0 = com.xiaopeng.carcontrolmodule.R.string.drv_belt_tv_new
            java.lang.String r0 = com.xiaopeng.carcontrol.util.ResUtils.getString(r0)
            r6.setText(r0)
            com.xiaopeng.xui.widget.XListTwo r6 = r5.mDrvEsbView
            int r0 = com.xiaopeng.carcontrolmodule.R.string.drv_belt_desc_new
            java.lang.String r0 = com.xiaopeng.carcontrol.util.ResUtils.getString(r0)
            r6.setTextSub(r0)
            com.xiaopeng.xui.widget.XSwitch r6 = r5.switchDrvEsb
            int r0 = com.xiaopeng.carcontrolmodule.R.string.drv_belt_tv_new
            java.lang.String r0 = r5.getStringById(r0)
            r6.setVuiLabel(r0)
        Lae:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.view.fragment.impl.SettingsFragment.updateDrvEsbConfigMode(java.lang.String):void");
    }

    private void initWheel(View container) {
        if (CarBaseConfig.getInstance().isSupportWheelSetting()) {
            View findViewById = container.findViewById(R.id.settings_new_wheel_title_tv);
            if (findViewById != null) {
                findViewById.setVisibility(0);
            }
            View findViewById2 = container.findViewById(R.id.wheel_key_protect_sw);
            View findViewById3 = container.findViewById(R.id.n_stall_sw);
            if (CarBaseConfig.getInstance().isSupportWheelKeyProtect()) {
                findViewById2.setVisibility(0);
                XSwitch xSwitch = (XSwitch) findViewById2.findViewById(R.id.x_list_sw);
                this.mWheelProtectSw = xSwitch;
                xSwitch.setVuiLabel(getStringById(R.string.steering_wheel_protect_title));
                this.mWheelProtectSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$3_LycDgJjHxPUvC1FBFoQRKBQ2o
                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
                    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                        SettingsFragment.this.lambda$initWheel$98$SettingsFragment(compoundButton, z);
                    }
                });
                this.mWheelProtectSw.setChecked(this.mMeterViewModel.isWheelKeyProtectEnabled());
            } else {
                findViewById2.setVisibility(8);
                findViewById3.setBackground(ResUtils.getDrawable(R.drawable.sub_item_bg));
            }
            if (CarBaseConfig.getInstance().isSupportNeutralGearProtect()) {
                findViewById3.setVisibility(0);
                final XSwitch xSwitch2 = (XSwitch) findViewById3.findViewById(R.id.x_list_sw);
                xSwitch2.setVuiLabel(getStringById(R.string.n_stall_protect_title));
                xSwitch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$OAeq3ARLF2cEt011IChm0YkH178
                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
                    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                        SettingsFragment.this.lambda$initWheel$99$SettingsFragment(compoundButton, z);
                    }
                });
                xSwitch2.setChecked(this.mVcuViewModel.getNGearWarningSwitchStatus());
                setLiveDataObserver(this.mVcuViewModel.getNGearWarningSwData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$C4nBTNK2roAHpECPo0uu8tfIOrg
                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        SettingsFragment.lambda$initWheel$100(XSwitch.this, (Boolean) obj);
                    }
                });
                return;
            }
            findViewById3.setVisibility(8);
        }
    }

    public /* synthetic */ void lambda$initWheel$98$SettingsFragment(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed() || isVuiAction(buttonView)) {
            MeterViewModel meterViewModel = this.mMeterViewModel;
            if (meterViewModel != null) {
                meterViewModel.setWheelKeyProtectSw(isChecked);
            }
            updateCarImgView(7);
            StatisticUtils.sendStatistic(PageEnum.SETTING_CUSTOM_KEY_PAGE, BtnEnum.SETTING_CUSTOM_KEY_PAGE_WHELL_BTN, Integer.valueOf(StatisticUtils.getSwitchOnOff(isChecked)));
        }
    }

    public /* synthetic */ void lambda$initWheel$99$SettingsFragment(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed() || isVuiAction(buttonView)) {
            this.mVcuViewModel.setNGearWarningSwitch(isChecked);
            updateCarImgView(7);
            StatisticUtils.sendStatistic(PageEnum.SETTING_CUSTOM_KEY_PAGE, BtnEnum.SETTING_CUSTOM_KEY_PAGE_N_STALL_BTN, Integer.valueOf(StatisticUtils.getSwitchOnOff(isChecked)));
        }
    }

    public static /* synthetic */ void lambda$initWheel$100(final XSwitch mNStallSw, Boolean enable) {
        if (enable != null) {
            mNStallSw.setChecked(enable.booleanValue());
        }
    }

    private void initWiper(View container) {
        WiperControlScene wiperControlScene = new WiperControlScene(getSceneId(), container, this);
        this.mWiperControlScene = wiperControlScene;
        addScene(wiperControlScene);
    }

    private void initCwc(View container) {
        if (CarBaseConfig.getInstance().isSupportCwc()) {
            container.findViewById(R.id.cwc_title_tv).setVisibility(0);
            View findViewById = container.findViewById(R.id.cwc_sw);
            findViewById.setVisibility(0);
            final CustomXSwitch customXSwitch = (CustomXSwitch) findViewById.findViewById(R.id.x_list_sw);
            customXSwitch.setVuiLabel(getStringById(R.string.wireless_charge_title));
            customXSwitch.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$kvOo26v5uG4FpN6Ja3Ky0pfhRz8
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return SettingsFragment.this.lambda$initCwc$101$SettingsFragment(customXSwitch, view, motionEvent);
                }
            });
            customXSwitch.setChecked(this.mCarBodyViewModel.isCwcSwEnable());
            setLiveDataObserver(this.mCarBodyViewModel.getCwcSwData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$FGhpsf9Q52JjSagGH5XcOpk3a54
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SettingsFragment.lambda$initCwc$102(CustomXSwitch.this, (Boolean) obj);
                }
            });
        }
    }

    public /* synthetic */ boolean lambda$initCwc$101$SettingsFragment(final CustomXSwitch cwcSw, View v, MotionEvent event) {
        if (event.getAction() != 1) {
            return false;
        }
        if (ClickHelper.isFastClick(cwcSw.getId(), 500L, false)) {
            return true;
        }
        this.mCarBodyViewModel.setCwcSwEnable(!cwcSw.isChecked());
        StatisticUtils.sendStatistic(PageEnum.SETTING_NEW_PAGE, BtnEnum.CWC_SWITCH, new Object[0]);
        return true;
    }

    public static /* synthetic */ void lambda$initCwc$102(final CustomXSwitch cwcSw, Boolean enable) {
        if (enable != null) {
            cwcSw.setChecked(enable.booleanValue());
        }
    }

    private void initMeterAndKeys(View container) {
        boolean isSupportMeterSetting = CarBaseConfig.getInstance().isSupportMeterSetting();
        if (!isSupportMeterSetting) {
            ((XTextView) container.findViewById(R.id.meter_and_steer_main_tv)).setText(getStringById(R.string.steer_feature_tv));
        }
        if (isSupportMeterSetting) {
            XListTwo xListTwo = (XListTwo) container.findViewById(R.id.meter_menu_item);
            xListTwo.setVisibility(0);
            XButton xButton = (XButton) xListTwo.findViewById(R.id.x_list_two_button);
            xButton.setVuiLabel(getStringById(R.string.meter_menu_setting_vui_lable));
            xButton.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$NdQb6l1WDjVqyVei230iwW2ci50
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SettingsFragment.this.lambda$initMeterAndKeys$103$SettingsFragment(view);
                }
            });
        }
        if (isSupportMeterSetting) {
            XListSingle xListSingle = (XListSingle) container.findViewById(R.id.speed_limit_sw_item);
            xListSingle.setVisibility(0);
            container.findViewById(R.id.speed_limit_slider_item).setVisibility(0);
            this.mSpdLimitSd = (XSlider) container.findViewById(R.id.speed_limit_sd);
            final XSwitch xSwitch = (XSwitch) xListSingle.findViewById(R.id.x_list_sw);
            xSwitch.setVuiLabel(getStringById(R.string.speed_limit_tv));
            this.mSpdLimitSd.setSliderProgressListener(new XSlider.SliderProgressListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.SettingsFragment.9
                @Override // com.xiaopeng.xui.widget.slider.XSlider.SliderProgressListener
                public void onProgressChanged(XSlider xSlider, float progress, String unit) {
                }

                @Override // com.xiaopeng.xui.widget.slider.XSlider.SliderProgressListener
                public void onStartTrackingTouch(XSlider xSlider) {
                }

                {
                    SettingsFragment.this = this;
                }

                @Override // com.xiaopeng.xui.widget.slider.XSlider.SliderProgressListener
                public void onStopTrackingTouch(XSlider xSlider) {
                    SettingsFragment.this.onSpeedSliderChange();
                }
            });
            this.mSpdLimitSd.setProgressChangeListener(new XSlider.ProgressChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$TOwIw6I9HWHuhuiyQl0EIonQ2ew
                @Override // com.xiaopeng.xui.widget.slider.XSlider.ProgressChangeListener
                public final void onProgressChanged(XSlider xSlider, float f, String str, boolean z) {
                    SettingsFragment.this.lambda$initMeterAndKeys$104$SettingsFragment(xSlider, f, str, z);
                }
            });
            xSwitch.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$22ptRa4BueE5DRHbT-v4SP_g71o
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return SettingsFragment.lambda$initMeterAndKeys$105(view, motionEvent);
                }
            });
            xSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$kXuIXMDQ92Qso9oOIiUALaBabbk
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    SettingsFragment.this.lambda$initMeterAndKeys$106$SettingsFragment(compoundButton, z);
                }
            });
            setLiveDataObserver(this.mMeterViewModel.getSpdWarningSwData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$UDQiaYe5petBJGNUUV0et4SCk_k
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SettingsFragment.this.lambda$initMeterAndKeys$107$SettingsFragment(xSwitch, (Boolean) obj);
                }
            });
            this.mSpdLimitSd.postDelayed(new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$xinPAG91KGr6llEubyK1HAxJZKA
                @Override // java.lang.Runnable
                public final void run() {
                    SettingsFragment.this.lambda$initMeterAndKeys$108$SettingsFragment(xSwitch);
                }
            }, 200L);
            setLiveDataObserver(this.mMeterViewModel.getSpdWarningValueData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$zxFy_9wDHoptpVB88Mer_MkdNsA
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SettingsFragment.this.updateSpdLimitValue((Integer) obj);
                }
            });
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("minValue", 0);
                jSONObject.put("maxValue", 120);
                jSONObject.put("startValue", 40);
                jSONObject.put(VuiConstants.PROPS_SETPROPS, true);
                jSONObject.put(VuiConstants.PROPS_INTERVAL, 10);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            this.mSpdLimitSd.setVuiProps(jSONObject);
            VuiUtils.addHasFeedbackProp(this.mSpdLimitSd);
        }
        View findViewById = container.findViewById(R.id.settings_x_key_layout);
        XButton xButton2 = (XButton) findViewById.findViewById(R.id.x_key_modify_btn);
        xButton2.setVuiLabel(getStringById(R.string.x_key_setting_vui_label));
        xButton2.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$qaufEC_20mY8gT9syu383tFhMrQ
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SettingsFragment.this.lambda$initMeterAndKeys$109$SettingsFragment(view);
            }
        });
        ((XTextView) findViewById.findViewById(R.id.x_key_title)).setText(R.string.car_setting_x_key_title);
        XTextView xTextView = (XTextView) findViewById.findViewById(R.id.x_key_desc);
        if (xTextView != null) {
            xTextView.setText(R.string.custom_x_key_desc);
        }
        this.mCustomXkeyTv = (XTextView) findViewById.findViewById(R.id.x_key_content);
        XKeyForCustomer xKeyForCustomerValue = this.mMeterViewModel.getXKeyForCustomerValue();
        if (xKeyForCustomerValue != null) {
            this.mCustomXkeyTv.setText(getStringById(R.string.car_setting_x_key_tv_format, xKeyForCustomerValue.getTitle()));
        } else {
            this.mCustomXkeyTv.setText((CharSequence) null);
        }
        setLiveDataObserver(this.mMeterViewModel.getXKeyData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$Agf29hr5R5oH8q2l9XGJNSCupZs
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SettingsFragment.this.lambda$initMeterAndKeys$110$SettingsFragment((XKeyForCustomer) obj);
            }
        });
        View findViewById2 = container.findViewById(R.id.settings_door_key_layout);
        if (CarBaseConfig.getInstance().isSupportDoorKeySetting()) {
            XButton xButton3 = (XButton) findViewById2.findViewById(R.id.x_key_modify_btn);
            xButton3.setVuiLabel(getStringById(R.string.door_key_setting_vui_label));
            xButton3.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$Wo2ZJI2ehkLbt5NzsEtAO6T5oRE
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SettingsFragment.this.lambda$initMeterAndKeys$111$SettingsFragment(view);
                }
            });
            ((XTextView) findViewById2.findViewById(R.id.x_key_title)).setText(R.string.car_setting_door_key_title);
            this.mCustomDoorKeyTv = (XTextView) findViewById2.findViewById(R.id.x_key_content);
            DoorKeyForCustomer doorKeyForCustomerValue = this.mMeterViewModel.getDoorKeyForCustomerValue();
            if (doorKeyForCustomerValue != null) {
                this.mCustomDoorKeyTv.setText(getStringById(R.string.car_setting_door_key_tv_format, doorKeyForCustomerValue.getTitle()));
            } else {
                this.mCustomDoorKeyTv.setText((CharSequence) null);
            }
            setLiveDataObserver(this.mMeterViewModel.getDoorKeyData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$hgRA751Pe6E64_BBhmqlu8ciVjY
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SettingsFragment.this.lambda$initMeterAndKeys$112$SettingsFragment((DoorKeyForCustomer) obj);
                }
            });
            return;
        }
        findViewById2.setVisibility(8);
        findViewById.setBackground(ResUtils.getDrawable(R.drawable.sub_item_bg));
    }

    public /* synthetic */ void lambda$initMeterAndKeys$103$SettingsFragment(View v) {
        showMeterMenuDialog();
        StatisticUtils.sendStatistic(PageEnum.SETTING_PAGE, BtnEnum.INSTRMENT_CUSTOM_MENU_BTN, new Object[0]);
    }

    public /* synthetic */ void lambda$initMeterAndKeys$104$SettingsFragment(XSlider xSlider, float v, String s, boolean b) {
        onSpeedSliderChange();
    }

    public static /* synthetic */ boolean lambda$initMeterAndKeys$105(View v, MotionEvent event) {
        return event.getAction() == 0 && ClickHelper.isFastClick(v.getId(), 500L, false);
    }

    public /* synthetic */ void lambda$initMeterAndKeys$106$SettingsFragment(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed() || isVuiAction(buttonView)) {
            this.mMeterViewModel.setSpeedWarningSw(isChecked);
            this.mSpdLimitSd.setEnabled(isChecked);
            if (isChecked) {
                updateSpdLimitValue(Integer.valueOf(this.mMeterViewModel.getSpeedWarningValue()));
            }
            StatisticUtils.sendStatistic(PageEnum.SETTING_PAGE, BtnEnum.SPEED_LIMIT_WARNINIT_SWITCH, Integer.valueOf(StatisticUtils.getSwitchOnOff(isChecked)));
        }
    }

    public /* synthetic */ void lambda$initMeterAndKeys$107$SettingsFragment(final XSwitch spdWaringSw, Boolean enable) {
        if (enable != null) {
            spdWaringSw.setChecked(enable.booleanValue());
            this.mSpdLimitSd.setEnabled(enable.booleanValue());
            if (enable.booleanValue()) {
                updateSpdLimitValue(Integer.valueOf(this.mMeterViewModel.getSpeedWarningValue()));
            }
            updateSpdLimitValue(Integer.valueOf(this.mMeterViewModel.getSpeedWarningValue()));
        }
    }

    public /* synthetic */ void lambda$initMeterAndKeys$108$SettingsFragment(final XSwitch spdWaringSw) {
        int speedWarningValue = this.mMeterViewModel.getSpeedWarningValue();
        LogUtils.i(this.TAG, "update speed limit origin data: " + speedWarningValue);
        if (speedWarningValue >= 40 && speedWarningValue <= 120) {
            this.mSpdLimitSd.setCurrentIndex(Math.round(speedWarningValue / 10.0f));
        }
        boolean speedWarningSw = this.mMeterViewModel.getSpeedWarningSw();
        spdWaringSw.setChecked(speedWarningSw);
        this.mSpdLimitSd.setEnabled(speedWarningSw);
    }

    public /* synthetic */ void lambda$initMeterAndKeys$109$SettingsFragment(View v) {
        Intent intent = new Intent(this.mContext, CarControlService.class);
        intent.setAction(GlobalConstant.ACTION.ACTION_SHOW_CUSTOM_X_KEY_PANEL);
        App.getInstance().startService(intent);
        StatisticUtils.sendStatistic(PageEnum.SETTING_CUSTOM_KEY_PAGE, BtnEnum.SETTING_CUSTOM_KEY_PAGE_X_KEY_SETTING_SHOW_BTN, new Object[0]);
    }

    public /* synthetic */ void lambda$initMeterAndKeys$110$SettingsFragment(XKeyForCustomer xKeyForCustomer) {
        if (xKeyForCustomer != null) {
            this.mCustomXkeyTv.setText(getStringById(R.string.car_setting_x_key_tv_format, xKeyForCustomer.getTitle()));
        }
    }

    public /* synthetic */ void lambda$initMeterAndKeys$111$SettingsFragment(View v) {
        Intent intent = new Intent(this.mContext, CarControlService.class);
        intent.setAction(GlobalConstant.ACTION.ACTION_SHOW_CUSTOM_DOOR_KEY_PANEL);
        App.getInstance().startService(intent);
        StatisticUtils.sendStatistic(PageEnum.SETTING_CUSTOM_KEY_PAGE, BtnEnum.SETTING_CUSTOM_KEY_PAGE_DOOR_KEY_SETTING_SHOW_BTN, new Object[0]);
    }

    public /* synthetic */ void lambda$initMeterAndKeys$112$SettingsFragment(DoorKeyForCustomer doorKeyForCustomer) {
        if (doorKeyForCustomer != null) {
            this.mCustomDoorKeyTv.setText(getStringById(R.string.car_setting_x_key_tv_format, doorKeyForCustomer.getTitle()));
        }
    }

    public void onSpeedSliderChange() {
        float indicatorValue = this.mSpdLimitSd.getIndicatorValue();
        int round = Math.round(indicatorValue / 10.0f) * 10;
        LogUtils.d(this.TAG, "slider progress:" + indicatorValue + " invert to speed limit = " + round);
        this.mMeterViewModel.setSpeedWarningValue(round);
    }

    public void updateSpdLimitValue(Integer limit) {
        LogUtils.i(this.TAG, "update speed limit origin data: " + limit);
        if (limit == null || limit.intValue() < 40 || limit.intValue() > 120 || !this.mSpdLimitSd.isEnabled()) {
            return;
        }
        final int round = Math.round(limit.floatValue() / 10.0f);
        this.mSpdLimitSd.post(new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$JpNY0YPkIe6bIQrwhZLK-jEGwHc
            @Override // java.lang.Runnable
            public final void run() {
                SettingsFragment.this.lambda$updateSpdLimitValue$113$SettingsFragment(round);
            }
        });
    }

    public /* synthetic */ void lambda$updateSpdLimitValue$113$SettingsFragment(final int slideIndex) {
        this.mSpdLimitSd.setCurrentIndex(slideIndex);
    }

    private void showMeterMenuDialog() {
        if (getActivity() != null) {
            if (this.mMeterDialog == null) {
                MeterMenuDialog meterMenuDialog = new MeterMenuDialog(getActivity());
                this.mMeterDialog = meterMenuDialog;
                meterMenuDialog.setClickListener(new MeterMenuDialog.IMeterDialogClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$HgoA1fz7vh4qNakRNhOeFz-UCmI
                    @Override // com.xiaopeng.carcontrol.view.widget.MeterMenuDialog.IMeterDialogClickListener
                    public final void onMeterItemClick(int i, boolean z) {
                        SettingsFragment.this.handleMeterMenuChanged(i, z);
                    }
                });
            }
            SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
            sparseBooleanArray.put(0, this.mMeterViewModel.getTempSw());
            sparseBooleanArray.put(1, this.mMeterViewModel.getWindPowerSw());
            sparseBooleanArray.put(4, this.mMeterViewModel.getWindModeSw());
            if (CarBaseConfig.getInstance().isSupportSwitchMedia()) {
                sparseBooleanArray.put(3, this.mMeterViewModel.getMediaSw());
            }
            sparseBooleanArray.put(2, this.mMeterViewModel.getBrightSw());
            LogUtils.d(this.TAG, "Show meter menu with value: " + sparseBooleanArray.toString());
            this.mMeterDialog.initData(sparseBooleanArray);
            Window window = this.mMeterDialog.getDialog().getWindow();
            if (window != null) {
                window.setElevation(0.0f);
            }
            this.mMeterDialog.show();
            this.mMeterDialog.getDialog().getWindow().setBackgroundDrawableResource(17170445);
            VuiManager.instance().initVuiDialog(this.mMeterDialog, getContext(), VuiManager.SCENE_METER_MENU_SETTING);
        }
    }

    public void handleMeterMenuChanged(int index, boolean value) {
        if (index == 0) {
            this.mMeterViewModel.setTempSw(value);
        } else if (index == 1) {
            this.mMeterViewModel.setWindPowerSw(value);
        } else if (index == 2) {
            this.mMeterViewModel.setBrightSw(value);
        } else if (index == 3) {
            this.mMeterViewModel.setMediaSw(value);
        } else if (index == 4) {
            this.mMeterViewModel.setWindModeSw(value);
        }
        StatisticUtils.sendStatistic(PageEnum.SETTING_PAGE, BtnEnum.INSTRUMENT_CUSTOME_MENU_DATA, Integer.valueOf(index + 1));
    }

    private void initOthers(View container) {
        if (BaseFeatureOption.getInstance().isSupportLightMeHomeInSettings()) {
            XListMultiple xListMultiple = (XListMultiple) container.findViewById(R.id.light_me_home_tab_item);
            xListMultiple.setVisibility(0);
            XTabLayout xTabLayout = (XTabLayout) xListMultiple.findViewById(R.id.light_me_home_tab);
            this.mLightMeHomeTab = xTabLayout;
            xTabLayout.setOnTabChangeListener(new XTabLayout.OnTabChangeListenerAdapter() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.SettingsFragment.10
                {
                    SettingsFragment.this = this;
                }

                @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListenerAdapter, com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
                public boolean onInterceptTabChange(XTabLayout tabLayout, int index, boolean tabChange, boolean fromUser) {
                    return fromUser && ClickHelper.isFastClick(tabLayout.getId(), 500L, false);
                }

                @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
                public void onTabChangeEnd(XTabLayout xTabLayout2, int index, boolean tabChange, boolean fromUser) {
                    if (fromUser) {
                        if (index == 0) {
                            SettingsFragment.this.mLampViewModel.setLightMeHome(false);
                        } else if (index == 1) {
                            SettingsFragment.this.mLampViewModel.setLightMeHomeTime(LightMeHomeTime.Time15s);
                        } else if (index == 2) {
                            SettingsFragment.this.mLampViewModel.setLightMeHomeTime(LightMeHomeTime.Time30s);
                        } else if (index == 3) {
                            SettingsFragment.this.mLampViewModel.setLightMeHomeTime(LightMeHomeTime.Time60s);
                        }
                        StatisticUtils.sendStatistic(PageEnum.LAMP_PAGE, BtnEnum.LAMP_PAGE_HOME_LAMP_BTN, Integer.valueOf(index + 1));
                    }
                }
            });
            setLiveDataObserver(this.mLampViewModel.getLightMeHomeTimeData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$4fL8KOON3I5RttCIIl9ujaPCAIg
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SettingsFragment.this.lambda$initOthers$114$SettingsFragment((LightMeHomeTime) obj);
                }
            });
            updateLightMeHome(this.mLampViewModel.getLightMeTime());
        }
        if (CarBaseConfig.getInstance().isSupportMirrorDown()) {
            View findViewById = container.findViewById(R.id.mirror_auto_reverse_sw_item);
            findViewById.setVisibility(0);
            final XSwitch xSwitch = (XSwitch) findViewById.findViewById(R.id.x_list_sw);
            xSwitch.setVuiLabel(getStringById(R.string.mirror_auto_reverse_tv));
            xSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$ARa5pPWlDa7OaJhICCP007BZFAw
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    SettingsFragment.this.lambda$initOthers$115$SettingsFragment(compoundButton, z);
                }
            });
            xSwitch.setChecked(this.mMirrorViewModel.isMirrorAutoDown());
            setLiveDataObserver(this.mMirrorViewModel.getMirrorAutoDownData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$f478yWK4cvyKgVA4fiNfMzRCRCE
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SettingsFragment.lambda$initOthers$116(XSwitch.this, (Boolean) obj);
                }
            });
        }
        if (BaseFeatureOption.getInstance().isSupportMirrorMemoryInSettings()) {
            XListSingle xListSingle = (XListSingle) container.findViewById(R.id.right_rm_reverse_sw);
            XListSingle xListSingle2 = (XListSingle) container.findViewById(R.id.left_rm_reverse_sw);
            xListSingle2.setVisibility(0);
            final XSwitch xSwitch2 = (XSwitch) xListSingle2.findViewById(R.id.x_list_sw);
            xSwitch2.setVuiLabel(getStringById(R.string.mirror_reverse_mode_left));
            xListSingle.setVisibility(0);
            final XSwitch xSwitch3 = (XSwitch) xListSingle.findViewById(R.id.x_list_sw);
            xSwitch3.setVuiLabel(getStringById(R.string.mirror_reverse_mode_right));
            xSwitch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$tD-yeOzE-QuedZDSGmFyQTVN9fY
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    SettingsFragment.this.lambda$initOthers$117$SettingsFragment(compoundButton, z);
                }
            });
            xSwitch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$gc69VauJirckweVYVN36mM-aiqU
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    SettingsFragment.this.lambda$initOthers$118$SettingsFragment(compoundButton, z);
                }
            });
            xSwitch2.setChecked(this.mMirrorViewModel.getLMReverseSw());
            xSwitch3.setChecked(this.mMirrorViewModel.getRMReverseSw());
            setLiveDataObserver(this.mMirrorViewModel.getMirrorReverseData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$m4-G05VSQFG-5bqyYTPZflQRPcY
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SettingsFragment.this.lambda$initOthers$119$SettingsFragment(xSwitch2, xSwitch3, (MirrorReverseMode) obj);
                }
            });
        }
        if (CarBaseConfig.getInstance().isSupportAutoPowerOff()) {
            XSwitch xSwitch4 = (XSwitch) container.findViewById(R.id.auto_power_off).findViewById(R.id.x_list_action_switch);
            xSwitch4.setVuiLabel(getStringById(R.string.laboratory_auto_power_off));
            xSwitch4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$Va7EDkIV58rKSbSUuQYOZZrT0dU
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    SettingsFragment.this.lambda$initOthers$120$SettingsFragment(compoundButton, z);
                }
            });
            LiveData<Boolean> autoPowerOffData = this.mCarBodyViewModel.getAutoPowerOffData();
            xSwitch4.getClass();
            setLiveDataObserver(autoPowerOffData, new $$Lambda$SettingsFragment$aeIiqtRaGva9_dxYD21QFFdegA(xSwitch4));
            xSwitch4.setChecked(this.mCarBodyViewModel.getAutoPowerOffConfig());
        }
    }

    public /* synthetic */ void lambda$initOthers$114$SettingsFragment(LightMeHomeTime lightMeHomeTime) {
        if (lightMeHomeTime != null) {
            updateLightMeHome(lightMeHomeTime);
        }
    }

    public /* synthetic */ void lambda$initOthers$115$SettingsFragment(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed() || isVuiAction(buttonView)) {
            this.mMirrorViewModel.setMirrorAutoDown(isChecked);
        }
    }

    public static /* synthetic */ void lambda$initOthers$116(final XSwitch mirrorAutoDownSw, Boolean enabled) {
        if (enabled != null) {
            mirrorAutoDownSw.setChecked(enabled.booleanValue());
        }
    }

    public /* synthetic */ void lambda$initOthers$117$SettingsFragment(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed() || isVuiAction(buttonView)) {
            this.mMirrorViewModel.setLMReverseSw(isChecked);
        }
    }

    public /* synthetic */ void lambda$initOthers$118$SettingsFragment(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed() || isVuiAction(buttonView)) {
            this.mMirrorViewModel.setRMReverseSw(isChecked);
        }
    }

    public /* synthetic */ void lambda$initOthers$119$SettingsFragment(final XSwitch leftRmSw, final XSwitch rightRmSw, MirrorReverseMode mirrorReverseMode) {
        if (mirrorReverseMode != null) {
            leftRmSw.setChecked(this.mMirrorViewModel.getLMReverseSw());
            rightRmSw.setChecked(this.mMirrorViewModel.getRMReverseSw());
        }
    }

    public /* synthetic */ void lambda$initOthers$120$SettingsFragment(CompoundButton buttonView, boolean checked) {
        if (buttonView.isPressed() || isVuiAction(buttonView)) {
            this.mCarBodyViewModel.setAutoPowerOffConfig(checked);
        }
    }

    private void updateLightMeHome(LightMeHomeTime time) {
        int i = AnonymousClass12.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LightMeHomeTime[time.ordinal()];
        int i2 = 3;
        if (i == 1) {
            i2 = 0;
        } else if (i == 2) {
            i2 = 2;
        } else if (i != 3) {
            i2 = 1;
        }
        updateXTabLayout(this.mLightMeHomeTab, i2);
    }

    private void dismissAllDialogs() {
        XDialog xDialog = this.mSafeBeltDialog;
        if (xDialog == null || !xDialog.isShowing()) {
            return;
        }
        this.mSafeBeltDialog.dismiss();
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.VuiFragment, com.xiaopeng.vui.commons.IVuiSceneListener
    public boolean onInterceptVuiEvent(View view, VuiEvent vuiEvent) {
        LogUtils.d(this.TAG, "onInterceptVuiEvent, vuiEvent： " + vuiEvent.getHitVuiElement());
        if (vuiEvent.getHitVuiElement().getId().contains(String.valueOf(R.id.listtwo_drive_experience_avh))) {
            if (this.mChassisVm.getAvhFault()) {
                vuiFeedback(R.string.avh_open_fail, view);
                return true;
            }
        } else if (vuiEvent.getHitVuiElement().getId().contains(String.valueOf(R.id.listtwo_drive_experience_esp))) {
            if (this.mChassisVm.getEspFault()) {
                vuiFeedback(R.string.esp_open_fail, view);
                NotificationHelper.getInstance().showToast(R.string.esp_open_fail);
                return true;
            }
        } else if (vuiEvent.getHitVuiElement().getId().contains(String.valueOf(R.id.listtwo_drive_experience_snow))) {
            if ((view instanceof XSwitch) && !((XSwitch) view).isChecked()) {
                if (DriveMode.EcoPlus == this.mVcuViewModel.getDriveModeValue()) {
                    vuiFeedback(R.string.drive_mode_eco_plus_with_open_snow_mode, view);
                    return true;
                } else if (DriveMode.Comfort == this.mVcuViewModel.getDriveModeValue()) {
                    vuiFeedback(R.string.drive_mode_anti_sickness_with_open_snow_mode, view);
                    return true;
                }
            }
        } else if (vuiEvent.getHitVuiElement().id.equals(String.valueOf(R.id.boot_sound_tab))) {
            if (this.mAvasViewModel.getBootSoundEffect() == 0) {
                vuiFeedback(R.string.vui_event_intercept_boot, view);
                return true;
            }
        } else if (vuiEvent.getHitVuiElement().id.equals(String.valueOf(R.id.tab_drive_experience_turn))) {
            if (this.mVcuViewModel.getCarSpeed() > 100.0f || Math.abs(this.mChassisVm.getTorsionBarTorque()) > 0.5f) {
                vuiFeedback(R.string.eps_unable_with_running, view);
                return true;
            } else if ((CarBaseConfig.getInstance().isSupportNgp() && this.mScuViewModel.isNgpRunning()) || (CarBaseConfig.getInstance().isSupportLcc() && this.mScuViewModel.getLccWorkSt() == ScuResponse.ON)) {
                vuiFeedback(R.string.eps_unable_with_xpilot, view);
                return true;
            }
        } else if (vuiEvent.getHitVuiElement().getId().contains(String.valueOf(R.id.esb_sw_item))) {
            if ((view instanceof XSwitch) && ((XSwitch) view).isChecked()) {
                showCloseSafeBeltDialog();
                return true;
            }
        } else if (vuiEvent.getHitVuiElement().getLabel().equals(ResUtils.getString(R.string.ihb_title))) {
            if (CarBaseConfig.getInstance().isSupportSrrMiss()) {
                vuiFeedback(R.string.xpilot_srr_missed_open_fail, view);
                return true;
            }
            return !confirmIhbFunc(view, true);
        } else if (vuiEvent.getHitVuiElement().getLabel().equals(ResUtils.getString(R.string.ihb_title_vui_label))) {
            if (CarBaseConfig.getInstance().isSupportSrrMiss()) {
                vuiFeedback(R.string.xpilot_srr_missed_open_fail, view);
                return true;
            }
            return false;
        } else if (vuiEvent.getHitVuiElement().getLabel().equals(ResUtils.getString(R.string.ciu_inner_camera_title_tv))) {
            if (CarBaseConfig.getInstance().isSupportSrrMiss()) {
                vuiFeedback(R.string.xpilot_srr_missed_open_fail, view);
            } else if (CarBaseConfig.getInstance().isVpmNotReady()) {
                vuiFeedback(R.string.xpilot_vpm_not_ready_open_fail, view);
            } else {
                int i = AnonymousClass12.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$DsmState[this.mScuViewModel.getDsmState().ordinal()];
                if (i == 1) {
                    vuiFeedback(R.string.ciu_inner_camera_failure, this.mDsmSw);
                    return true;
                } else if (i == 2) {
                    vuiFeedback(R.string.ciu_inner_camera_turning_on, this.mDsmSw);
                    return true;
                } else if (!((XSwitch) view).isChecked()) {
                    this.mScuViewModel.setDsmSw(true);
                } else {
                    showDialog(R.string.ciu_inner_camera_title_tv, R.string.ciu_inner_camera_feature_feedback_new, getStringById(R.string.btn_cancel), (XDialogInterface.OnClickListener) null, getStringById(R.string.btn_ok), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$ICN4DF9VnVl0PIdbzGwB7_07cI0
                        @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                        public final void onClick(XDialog xDialog, int i2) {
                            SettingsFragment.this.lambda$onInterceptVuiEvent$121$SettingsFragment(xDialog, i2);
                        }
                    }, VuiManager.SCENE_DSM_SETTING);
                }
            }
            return true;
        } else if (vuiEvent.getHitVuiElement().getLabel().equals(ResUtils.getString(R.string.wireless_charge_title))) {
            this.mCarBodyViewModel.setCwcSwEnable(!((XSwitch) view).isChecked());
            StatisticUtils.sendStatistic(PageEnum.SETTING_NEW_PAGE, BtnEnum.CWC_SWITCH, new Object[0]);
            return true;
        }
        return super.onInterceptVuiEvent(view, vuiEvent);
    }

    /* renamed from: com.xiaopeng.carcontrol.view.fragment.impl.SettingsFragment$12 */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass12 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LightMeHomeTime;
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$DsmState;
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$xpu$NedcState;

        static {
            int[] iArr = new int[DsmState.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$DsmState = iArr;
            try {
                iArr[DsmState.Failure.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$DsmState[DsmState.TurnOning.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            int[] iArr2 = new int[LightMeHomeTime.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LightMeHomeTime = iArr2;
            try {
                iArr2[LightMeHomeTime.Off.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LightMeHomeTime[LightMeHomeTime.Time30s.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LightMeHomeTime[LightMeHomeTime.Time60s.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LightMeHomeTime[LightMeHomeTime.Time15s.ordinal()] = 4;
            } catch (NoSuchFieldError unused6) {
            }
            int[] iArr3 = new int[NedcState.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$xpu$NedcState = iArr3;
            try {
                iArr3[NedcState.On.ordinal()] = 1;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$xpu$NedcState[NedcState.TurnOning.ordinal()] = 2;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$xpu$NedcState[NedcState.TurnOnFailure.ordinal()] = 3;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$xpu$NedcState[NedcState.TurnOffing.ordinal()] = 4;
            } catch (NoSuchFieldError unused10) {
            }
        }
    }

    public /* synthetic */ void lambda$onInterceptVuiEvent$121$SettingsFragment(XDialog xDialog, int i) {
        this.mScuViewModel.setDsmSw(false);
        this.mDsmSw.setChecked(false);
    }

    public void updateCarImgView(int type) {
        if (BaseFeatureOption.getInstance().isSupportLeftCarImgChange() && this.operationType != type) {
            LogUtils.d(this.TAG, "updateCarImgView, type: " + type);
            this.operationType = type;
            switch (type) {
                case 1:
                    this.mTopCarResId = R.drawable.img_car_set_lock;
                    break;
                case 2:
                    this.mTopCarResId = R.drawable.img_car_set_window;
                    break;
                case 3:
                    this.mTopCarResId = R.drawable.img_car_set_child_lock;
                    break;
                case 4:
                    this.mTopCarResId = R.drawable.img_car_set_welcome;
                    break;
                case 5:
                    this.mTopCarResId = R.drawable.img_car_set_drive;
                    break;
                case 6:
                    this.mTopCarResId = R.drawable.img_car_set_belt;
                    break;
                case 7:
                    this.mTopCarResId = R.drawable.img_car_set_wheel;
                    break;
                case 8:
                    this.mTopCarResId = R.drawable.img_car_set_wiper;
                    break;
            }
            if (this.mTopCarResId != 0) {
                if (this.fadeIn == null) {
                    this.fadeIn = ObjectAnimator.ofFloat(this.mCarImg, "alpha", 1.0f, 0.0f);
                }
                if (this.fadeOut == null) {
                    this.fadeOut = ObjectAnimator.ofFloat(this.mCarImg, "alpha", 0.0f, 1.0f);
                }
                if (this.mAnimListener == null) {
                    AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.SettingsFragment.11
                        {
                            SettingsFragment.this = this;
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            SettingsFragment.this.mCarImg.setImageResource(SettingsFragment.this.mTopCarResId);
                        }
                    };
                    this.mAnimListener = animatorListenerAdapter;
                    this.fadeOut.addListener(animatorListenerAdapter);
                }
                if (this.mAnimSet == null) {
                    AnimatorSet animatorSet = new AnimatorSet();
                    this.mAnimSet = animatorSet;
                    animatorSet.setDuration(300L);
                    this.mAnimSet.play(this.fadeIn).before(this.fadeOut);
                }
                this.mAnimSet.start();
            }
        }
    }
}
