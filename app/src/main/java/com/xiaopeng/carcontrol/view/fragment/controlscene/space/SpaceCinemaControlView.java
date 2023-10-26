package com.xiaopeng.carcontrol.view.fragment.controlscene.space;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Handler;
import android.support.rastermill.FrameSequenceUtil;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import com.airbnb.lottie.LottieAnimationView;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.R;
import com.xiaopeng.carcontrol.carmanager.XuiClientWrapper;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.helper.SpeechHelper;
import com.xiaopeng.carcontrol.statistic.BtnEnum;
import com.xiaopeng.carcontrol.statistic.PageEnum;
import com.xiaopeng.carcontrol.statistic.StatisticUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.view.fragment.spacecapsule.ISpaceCapsuleSlpCinInterface;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorViewModel;
import com.xiaopeng.carcontrol.viewmodel.hvac.AcHeatNatureMode;
import com.xiaopeng.carcontrol.viewmodel.hvac.HvacCirculationMode;
import com.xiaopeng.carcontrol.viewmodel.hvac.HvacViewModel;
import com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel;
import com.xiaopeng.carcontrol.viewmodel.space.ISpaceCapsuleViewModel;
import com.xiaopeng.carcontrol.viewmodel.space.SpaceCapsuleViewModel;
import com.xiaopeng.xui.theme.XThemeManager;
import com.xiaopeng.xui.utils.XuiUtils;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XLinearLayout;
import com.xiaopeng.xui.widget.XTextView;
import com.xiaopeng.xui.widget.toggle.XToggleLayout;
import com.xiaopeng.xui.widget.toggle.XToggleText;
import java.util.Arrays;
import org.eclipse.paho.client.mqttv3.internal.ClientDefaults;

/* loaded from: classes2.dex */
public class SpaceCinemaControlView extends AbsSpaceModeView {
    private static final int SOURCE_TIMEOUT = 2000;
    private static final String TAG = "SpaceCinemaControl";
    private XTextView bluetoothSubTitle;
    private FragmentActivity mActivity;
    private View mBluetoothTipsLayout;
    private XLinearLayout mBluetoothTipsLoading;
    private XTextView mBluetoothTipsTv;
    private XTextView mBluetoothTipsTvDone;
    private LottieAnimationView mCinemaIcon;
    private XImageView mCinemaScreenIcon;
    private Context mContext;
    private View mHvacAcView;
    private View mHvacAutoView;
    private View mHvacCentiGrade;
    private XImageView mHvacCircleView;
    private View mHvacOff;
    private HvacViewModel mHvacViewModel;
    private ISpaceCapsuleSlpCinInterface mInterface;
    private View mRootView;
    private XTextView mTvTemperature;
    private SpaceCapsuleViewModel mViewModel;
    private WindowDoorViewModel mWinDoorViewModel;
    private XToggleText mWindowControlBtn;
    private boolean mIsPlayTts = false;
    private final Handler mHandler = new Handler();
    private final Runnable mTtsPlayHandler = new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.space.-$$Lambda$SpaceCinemaControlView$ZoEYPbD8Q2ep9qPrR-LXSJotyP4
        @Override // java.lang.Runnable
        public final void run() {
            SpaceCinemaControlView.this.playTts();
        }
    };
    private boolean mIsRegister = false;
    private final Handler mSourceHandler = new Handler();
    private final Runnable mSourceRunnable = new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.space.-$$Lambda$SpaceCinemaControlView$0Vqwu-skdR27ml6i9uFVpTUDHlc
        @Override // java.lang.Runnable
        public final void run() {
            SpaceCinemaControlView.this.sourceDone();
        }
    };
    private final Runnable mBlueGoneRunnable = new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.space.-$$Lambda$SpaceCinemaControlView$0r9I9xfT0AGkcSixvLk1W-wbV2c
        @Override // java.lang.Runnable
        public final void run() {
            SpaceCinemaControlView.this.blueGone();
        }
    };
    private int mLastBtStatus = -1;

    @Override // com.xiaopeng.carcontrol.view.fragment.controlscene.space.AbsSpaceModeView
    public int getLayoutRes() {
        return R.layout.space_cinema_fragment;
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.controlscene.space.AbsSpaceModeView
    public void onPause() {
    }

    public SpaceCinemaControlView(Context context, FragmentActivity activity, ISpaceCapsuleSlpCinInterface slpCinInterface) {
        this.mContext = context;
        this.mActivity = activity;
        this.mInterface = slpCinInterface;
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.controlscene.space.AbsSpaceModeView
    public View onCreateView(LayoutInflater inflater) {
        View onCreateView = super.onCreateView(inflater);
        this.mRootView = onCreateView;
        initViews(onCreateView);
        return this.mRootView;
    }

    private void initViews(View view) {
        this.mCinemaIcon = (LottieAnimationView) view.findViewById(R.id.space_cinema_animator);
        this.mCinemaScreenIcon = (XImageView) view.findViewById(R.id.space_cinema_screen_animator);
        View findViewById = view.findViewById(R.id.space_capsule_cinema_bluetooth_btn);
        ((XImageView) findViewById.findViewById(R.id.space_capsule_item_icon)).setImageResource(R.drawable.space_capsule_bluetooth);
        ((XTextView) findViewById.findViewById(R.id.space_capsule_item_txt)).setText(R.string.space_cinema_bluetooth_tips);
        XTextView xTextView = (XTextView) findViewById.findViewById(R.id.space_capsule_item_subtxt);
        this.bluetoothSubTitle = xTextView;
        xTextView.setSelected(true);
        findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.space.-$$Lambda$SpaceCinemaControlView$gbBvBPhIBlk99hIROT7AbhTW888
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                SpaceCinemaControlView.this.lambda$initViews$0$SpaceCinemaControlView(view2);
            }
        });
        View findViewById2 = view.findViewById(R.id.space_capsule_cinema_airconditiner_btn);
        View findViewById3 = findViewById2.findViewById(R.id.space_capsule_container_ac);
        this.mHvacAcView = findViewById3;
        findViewById3.setEnabled(false);
        View findViewById4 = findViewById2.findViewById(R.id.space_capsule_container_auto);
        this.mHvacAutoView = findViewById4;
        findViewById4.setEnabled(false);
        this.mHvacCircleView = (XImageView) findViewById2.findViewById(R.id.space_capsule_container_airmode);
        XToggleText xToggleText = (XToggleText) findViewById2.findViewById(R.id.space_capsule_window_air_btn);
        this.mWindowControlBtn = xToggleText;
        xToggleText.setOnCheckedChangeListener(new XToggleLayout.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.space.SpaceCinemaControlView.1
            @Override // com.xiaopeng.xui.widget.toggle.XToggleLayout.OnCheckedChangeListener
            public void onCheckedChanged(XToggleLayout xToggleLayout, boolean b) {
            }

            @Override // com.xiaopeng.xui.widget.toggle.XToggleLayout.OnCheckedChangeListener
            public boolean onInterceptClickCheck(XToggleLayout xToggleLayout) {
                if (xToggleLayout.isPressed() && SpaceCinemaControlView.this.mWinDoorViewModel != null) {
                    if (!SpaceCinemaControlView.this.mWinDoorViewModel.isWindowLockActive()) {
                        if (SpaceCinemaControlView.this.mWinDoorViewModel.getWinMoveStateData().getValue() == null || SpaceCinemaControlView.this.mWinDoorViewModel.getWinMoveStateData().getValue().intValue() != 1) {
                            if (SpaceCinemaControlView.this.mWinDoorViewModel.isWindowInitFailed(4)) {
                                return true;
                            }
                            if (!SpaceCinemaControlView.this.mWinDoorViewModel.isWindowOpened()) {
                                SpaceCinemaControlView.this.mWinDoorViewModel.controlWindowVent();
                                StatisticUtils.sendSpaceCapsuleStatistic(PageEnum.SPACE_CAPSULE_PAGE, BtnEnum.SPACE_CAPSULE_PAGE_CONTROL_WINDOW, 1, 2);
                            } else {
                                StatisticUtils.sendSpaceCapsuleStatistic(PageEnum.SPACE_CAPSULE_PAGE, BtnEnum.SPACE_CAPSULE_PAGE_CONTROL_WINDOW, 2, 2);
                                SpaceCinemaControlView.this.mWinDoorViewModel.controlWindowClose();
                            }
                        } else {
                            LogUtils.i(SpaceCinemaControlView.TAG, "Window is moving");
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
        this.mHvacOff = findViewById2.findViewById(R.id.space_capsule_container_off);
        this.mTvTemperature = (XTextView) findViewById2.findViewById(R.id.space_capsule_container_temperature);
        this.mHvacCentiGrade = findViewById2.findViewById(R.id.space_capsule_container_temperature_unit);
        findViewById2.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.space.-$$Lambda$SpaceCinemaControlView$dKnL2IX5LjvVe7yYzEfCh2Ju4vI
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                SpaceCinemaControlView.this.lambda$initViews$1$SpaceCinemaControlView(view2);
            }
        });
        View findViewById5 = view.findViewById(R.id.space_capsule_cinema_fragrance_btn);
        if (CarBaseConfig.getInstance().isSupportSfs()) {
            findViewById5.setVisibility(0);
            XImageView xImageView = (XImageView) findViewById5.findViewById(R.id.space_capsule_item_icon);
            xImageView.setVisibility(0);
            xImageView.setImageResource(R.drawable.space_capsule_fragrance);
            ((XTextView) findViewById5.findViewById(R.id.space_capsule_item_txt)).setText(R.string.space_capsule_fragrance);
            findViewById5.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.space.-$$Lambda$SpaceCinemaControlView$VM92F9X-ik9eemQEm1rlMBWjUMc
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    SpaceCinemaControlView.this.lambda$initViews$2$SpaceCinemaControlView(view2);
                }
            });
            setViewLayoutParams(findViewById, XuiUtils.dip2px(this.mContext.getResources().getDimension(R.dimen.space_capsule_item_width_small)));
            setViewLayoutParams(findViewById2, XuiUtils.dip2px(this.mContext.getResources().getDimension(R.dimen.space_capsule_item_width_middle)));
            setViewLayoutParams(findViewById5, XuiUtils.dip2px(this.mContext.getResources().getDimension(R.dimen.space_capsule_item_width_small)));
        } else {
            view.findViewById(R.id.space_capsule_cinema_blank2).setVisibility(8);
            findViewById5.setVisibility(8);
            setViewLayoutParams(findViewById, XuiUtils.dip2px(this.mContext.getResources().getDimension(R.dimen.space_capsule_item_width_large)));
            setViewLayoutParams(findViewById2, XuiUtils.dip2px(this.mContext.getResources().getDimension(R.dimen.space_capsule_item_width_large)));
        }
        this.mBluetoothTipsLayout = view.findViewById(R.id.space_capsule_cinema_bluetooth_layout);
        this.mBluetoothTipsLoading = (XLinearLayout) view.findViewById(R.id.space_capsule_cinema_bluetooth_loading);
        this.mBluetoothTipsTv = (XTextView) view.findViewById(R.id.space_capsule_cinema_bluetooth_tips);
        this.mBluetoothTipsTvDone = (XTextView) view.findViewById(R.id.space_capsule_cinema_bluetooth_tips2);
        setBluetoothTips();
    }

    public /* synthetic */ void lambda$initViews$0$SpaceCinemaControlView(View v) {
        startBluetoothPopup();
    }

    public /* synthetic */ void lambda$initViews$1$SpaceCinemaControlView(View v) {
        this.mInterface.startHvac(this.mContext);
    }

    public /* synthetic */ void lambda$initViews$2$SpaceCinemaControlView(View v) {
        this.mInterface.startFragrance(this.mContext);
    }

    private void setBluetoothTips() {
        String string = this.mContext.getString(R.string.space_capsule_cinema_bluetooth_tips);
        SpannableString spannableString = new SpannableString(string);
        spannableString.setSpan(new ClickableSpan() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.space.SpaceCinemaControlView.2
            @Override // android.text.style.ClickableSpan
            public void onClick(View widget) {
                SpaceCinemaControlView.this.switchToBluetooth();
            }

            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.parseColor("#5E91F3"));
                ds.setUnderlineText(false);
            }
        }, string.length() - 4, string.length(), 33);
        this.mBluetoothTipsTv.setMovementMethod(LinkMovementMethod.getInstance());
        this.mBluetoothTipsTv.setText(spannableString);
        this.mBluetoothTipsTv.setHighlightColor(0);
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

    private void resetBluetoothTips() {
        this.mBluetoothTipsLoading.setVisibility(8);
        this.mBluetoothTipsTvDone.setVisibility(8);
        this.mBluetoothTipsTv.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void switchToBluetooth() {
        this.mViewModel.switchBluetoothSource();
        this.mBluetoothTipsLoading.setVisibility(0);
        this.mBluetoothTipsTv.setVisibility(8);
        this.mBluetoothTipsTvDone.setVisibility(8);
        this.mSourceHandler.removeCallbacks(this.mSourceRunnable);
        this.mSourceHandler.removeCallbacks(this.mBlueGoneRunnable);
        this.mSourceHandler.postDelayed(this.mSourceRunnable, 2000L);
        StatisticUtils.sendSpaceCapsuleStatistic(PageEnum.SPACE_CAPSULE_CINEMA_PAGE, BtnEnum.SPACE_CAPSULE_CINEMA_PAGE_BT_AUDIO_SOURCE, new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sourceDone() {
        this.mBluetoothTipsLoading.setVisibility(8);
        this.mBluetoothTipsTv.setVisibility(8);
        this.mBluetoothTipsTvDone.setVisibility(0);
        this.mSourceHandler.removeCallbacks(this.mSourceRunnable);
        this.mSourceHandler.removeCallbacks(this.mBlueGoneRunnable);
        this.mSourceHandler.postDelayed(this.mBlueGoneRunnable, 2000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void blueGone() {
        this.mBluetoothTipsLayout.setVisibility(8);
    }

    private void setBtDeviceView(String name) {
        if (this.bluetoothSubTitle != null) {
            if (!TextUtils.isEmpty(name)) {
                this.bluetoothSubTitle.setText(ResUtils.getString(R.string.space_cinema_bluetooth_connected, name));
            } else {
                this.bluetoothSubTitle.setText(ResUtils.getString(R.string.space_cinema_bluetooth_no_connected));
            }
        }
    }

    public void init(LifecycleOwner lifecycleOwner) {
        this.mViewModel = (SpaceCapsuleViewModel) ViewModelManager.getInstance().getViewModelImpl(ISpaceCapsuleViewModel.class);
        this.mHvacViewModel = (HvacViewModel) ViewModelManager.getInstance().getViewModelImpl(IHvacViewModel.class);
        this.mWinDoorViewModel = (WindowDoorViewModel) ViewModelManager.getInstance().getViewModelImpl(IWindowDoorViewModel.class);
        this.mViewModel.getA2dpConnectLiveData().setValue(-1);
        this.mViewModel.getA2dpConnectLiveData().observe(lifecycleOwner, new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.space.-$$Lambda$SpaceCinemaControlView$SSx9gdFEEky0aFRGc2LNOHhWlJk
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SpaceCinemaControlView.this.lambda$init$3$SpaceCinemaControlView((Integer) obj);
            }
        });
        this.mViewModel.getBtDeviceNameData().observe(lifecycleOwner, new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.space.-$$Lambda$SpaceCinemaControlView$6LiRvK084WsF9dVvvkfG6ECrvTA
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SpaceCinemaControlView.this.lambda$init$4$SpaceCinemaControlView((String) obj);
            }
        });
        webpDisplay();
        refreshHvac();
        refreshHvacCircle();
        refreshWindowBtn();
        this.mHvacViewModel.getHvacPowerData().observe(this.mActivity, new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.space.-$$Lambda$SpaceCinemaControlView$zWSc6o5UYQhD1fEHIg9RlGaJghc
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SpaceCinemaControlView.this.lambda$init$5$SpaceCinemaControlView((Boolean) obj);
            }
        });
        this.mHvacViewModel.getHvacTempAcData().observe(this.mActivity, new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.space.-$$Lambda$SpaceCinemaControlView$AABmjj4r3u4hjzrsV7Ii5fpnfNE
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SpaceCinemaControlView.this.lambda$init$6$SpaceCinemaControlView((AcHeatNatureMode) obj);
            }
        });
        this.mHvacViewModel.getHvacTempDriverData().observe(this.mActivity, new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.space.-$$Lambda$SpaceCinemaControlView$pRux4ATGkBwMLc9aVYCm2Oo72L4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SpaceCinemaControlView.this.lambda$init$7$SpaceCinemaControlView((Float) obj);
            }
        });
        this.mHvacViewModel.getHvacAutoData().observe(this.mActivity, new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.space.-$$Lambda$SpaceCinemaControlView$YXKRLtcmdfPLvUbdP4NltNgWtvg
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SpaceCinemaControlView.this.lambda$init$8$SpaceCinemaControlView((Boolean) obj);
            }
        });
        this.mHvacViewModel.getHvacCirculationData().observe(this.mActivity, new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.space.-$$Lambda$SpaceCinemaControlView$FAeVOdLLc_tR00ixV0GTibGASho
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SpaceCinemaControlView.this.lambda$init$9$SpaceCinemaControlView((HvacCirculationMode) obj);
            }
        });
        this.mWinDoorViewModel.getWinMoveStateData().observe(this.mActivity, new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.space.-$$Lambda$SpaceCinemaControlView$gcFot0TGY_QDWid-EGMm-kvc2Eg
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SpaceCinemaControlView.this.lambda$init$10$SpaceCinemaControlView((Integer) obj);
            }
        });
        this.mHandler.removeCallbacks(this.mTtsPlayHandler);
        this.mHandler.postDelayed(this.mTtsPlayHandler, 2000L);
    }

    public /* synthetic */ void lambda$init$3$SpaceCinemaControlView(Integer status) {
        LogUtils.d(TAG, "observer a2dp connect status:" + status + " mLastBtStatus:" + this.mLastBtStatus);
        if (status.intValue() == -1) {
            return;
        }
        if (this.mViewModel.isSourceInBt(this.mLastBtStatus) && this.mViewModel.isA2dpConnected()) {
            if (this.mSourceHandler.hasCallbacks(this.mSourceRunnable) || this.mSourceHandler.hasCallbacks(this.mBlueGoneRunnable)) {
                this.mSourceHandler.removeCallbacks(this.mSourceRunnable);
                this.mSourceHandler.removeCallbacks(this.mBlueGoneRunnable);
            }
            resetBluetoothTips();
            this.mBluetoothTipsLayout.setVisibility(0);
        } else if (this.mViewModel.isBtSource() || this.mViewModel.isA2dpConnected()) {
            if (this.mLastBtStatus < 5 && this.mViewModel.isA2dpConnected()) {
                this.mViewModel.switchBluetoothSource();
            } else if (this.mViewModel.isBtSource()) {
                this.mViewModel.requestAudioFocus();
                this.mViewModel.setBtVolume(1);
            }
            if (this.mSourceHandler.hasCallbacks(this.mSourceRunnable) || this.mSourceHandler.hasCallbacks(this.mBlueGoneRunnable)) {
                this.mBluetoothTipsLayout.setVisibility(0);
            } else if (this.mViewModel.isBtSource()) {
                this.mBluetoothTipsLayout.setVisibility(8);
            } else if (this.mViewModel.isA2dpConnected()) {
                this.mBluetoothTipsLayout.setVisibility(0);
            }
        } else {
            this.mBluetoothTipsLayout.setVisibility(8);
            resetBluetoothTips();
        }
        this.mLastBtStatus = status.intValue();
    }

    public /* synthetic */ void lambda$init$4$SpaceCinemaControlView(String name) {
        LogUtils.d(TAG, "observer bt connect status, name : " + name, false);
        setBtDeviceView(name);
    }

    public /* synthetic */ void lambda$init$5$SpaceCinemaControlView(Boolean aBoolean) {
        refreshHvac();
    }

    public /* synthetic */ void lambda$init$6$SpaceCinemaControlView(AcHeatNatureMode acHeatNatureMode) {
        refreshHvac();
    }

    public /* synthetic */ void lambda$init$7$SpaceCinemaControlView(Float aFloat) {
        refreshHvac();
    }

    public /* synthetic */ void lambda$init$8$SpaceCinemaControlView(Boolean aBoolean) {
        refreshHvac();
    }

    public /* synthetic */ void lambda$init$9$SpaceCinemaControlView(HvacCirculationMode hvacCirculationMode) {
        refreshHvacCircle();
    }

    public /* synthetic */ void lambda$init$10$SpaceCinemaControlView(Integer windowState) {
        refreshWindowBtn();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playTts() {
        if (this.mIsPlayTts) {
            return;
        }
        SpeechHelper.getInstance().speak(ResUtils.getString(R.string.space_capsule_enter_cinema_tips));
        this.mIsPlayTts = true;
    }

    private void webpDisplay() {
        try {
            FrameSequenceUtil.destroy(this.mCinemaScreenIcon);
            FrameSequenceUtil.with(this.mCinemaScreenIcon).resourceId(R.drawable.space_cinema_anim).decodingThreadId(0).loopBehavior(2).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mCinemaIcon.setAnimation("capsule_anim_cinema.json");
        this.mCinemaIcon.setRepeatCount(-1);
        this.mCinemaIcon.playAnimation();
    }

    private void startBluetoothPopup() {
        Intent intent = new Intent(GlobalConstant.ACTION.ACTION_BLUETOOTH_POPUP);
        intent.setFlags(ClientDefaults.MAX_MSG_SIZE);
        this.mContext.startActivity(intent);
        StatisticUtils.sendSpaceCapsuleStatistic(PageEnum.SPACE_CAPSULE_CINEMA_PAGE, BtnEnum.SPACE_CAPSULE_CINEMA_PAGE_BT, new Object[0]);
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.controlscene.space.AbsSpaceModeView
    public void onConfigurationChanged(Configuration newConfig) {
        if (isActive() && XThemeManager.isThemeChanged(newConfig)) {
            webpDisplay();
        }
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.controlscene.space.AbsSpaceModeView
    public void enter(LifecycleOwner lifecycleOwner) {
        super.enter(lifecycleOwner);
        init(lifecycleOwner);
        enterCinema();
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.controlscene.space.AbsSpaceModeView
    public void exit() {
        super.exit();
        if (isActive()) {
            leaveCinema();
        }
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.controlscene.space.AbsSpaceModeView
    public void onResume() {
        super.onResume();
        SpaceCapsuleViewModel spaceCapsuleViewModel = this.mViewModel;
        if (spaceCapsuleViewModel != null) {
            setBtDeviceView(spaceCapsuleViewModel.getConnectedBtDeviceName());
        }
    }

    private void enterCinema() {
        this.mViewModel.setCurrentSubMode(2);
        ThreadUtils.postDelayed(0, new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.space.-$$Lambda$SpaceCinemaControlView$gTD1LERfwvlXP8iv0hacZk6s020
            @Override // java.lang.Runnable
            public final void run() {
                SpaceCinemaControlView.this.lambda$enterCinema$11$SpaceCinemaControlView();
            }
        });
        this.mViewModel.registerBtStatusListener();
        this.mViewModel.registerBtDeviceNameObserver();
        this.mBluetoothTipsLayout.setVisibility(8);
        if (this.mViewModel.isBtSource()) {
            this.mViewModel.requestAudioFocus();
            this.mViewModel.setBtVolume(1);
        } else if (this.mViewModel.isA2dpConnected()) {
            this.mBluetoothTipsLayout.setVisibility(0);
        }
        setBtDeviceView(this.mViewModel.getConnectedBtDeviceName());
        setActive(true);
        LogUtils.i(TAG, "enter capsule cinema CurrentBtStatus:" + XuiClientWrapper.getInstance().getCurrentBtStatus());
    }

    public /* synthetic */ void lambda$enterCinema$11$SpaceCinemaControlView() {
        this.mViewModel.setSoundFieldToMiddle(true);
        this.mViewModel.changeMediaVolume(true);
        this.mViewModel.changeSoundEffectStyleCinema(true);
    }

    private void leaveCinema() {
        LogUtils.i(TAG, "leave capsule cinema");
        ThreadUtils.postDelayed(0, new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.space.-$$Lambda$SpaceCinemaControlView$q8UtFx2AvctF7i_B624BtAYtFhs
            @Override // java.lang.Runnable
            public final void run() {
                SpaceCinemaControlView.this.lambda$leaveCinema$12$SpaceCinemaControlView();
            }
        });
        this.mViewModel.unregisterBtStatusListener();
        this.mViewModel.unRegisterBtDeviceNameObserver();
        setActive(false);
        this.mViewModel.abandonAudioFocus();
        this.mHandler.removeCallbacks(this.mTtsPlayHandler);
        try {
            XImageView xImageView = this.mCinemaScreenIcon;
            if (xImageView != null) {
                FrameSequenceUtil.destroy(xImageView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        LottieAnimationView lottieAnimationView = this.mCinemaIcon;
        if (lottieAnimationView == null || !lottieAnimationView.isAnimating()) {
            return;
        }
        this.mCinemaIcon.cancelAnimation();
    }

    public /* synthetic */ void lambda$leaveCinema$12$SpaceCinemaControlView() {
        this.mViewModel.setSoundFieldToMiddle(false);
        this.mViewModel.changeSoundEffectStyleCinema(false);
        this.mViewModel.changeMediaVolume(false);
    }
}
