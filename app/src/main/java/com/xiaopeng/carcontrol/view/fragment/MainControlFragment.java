package com.xiaopeng.carcontrol.view.fragment;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.CarControlService;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.R;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.helper.ClickHelper;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.statistic.BtnEnum;
import com.xiaopeng.carcontrol.statistic.PageEnum;
import com.xiaopeng.carcontrol.statistic.StatisticUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.view.fragment.controlscene.MainScrollView;
import com.xiaopeng.carcontrol.view.fragment.controlscene.SeatControlScene;
import com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.MirrorFoldState;
import com.xiaopeng.carcontrol.viewmodel.cabin.MirrorViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.TrunkState;
import com.xiaopeng.carcontrol.viewmodel.cabin.TrunkType;
import com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.WindowState;
import com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyViewModel;
import com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel;
import com.xiaopeng.carcontrol.viewmodel.carsettings.WiperInterval;
import com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel;
import com.xiaopeng.carcontrol.viewmodel.lamp.LampMode;
import com.xiaopeng.carcontrol.viewmodel.lamp.LampViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.DriveMode;
import com.xiaopeng.carcontrol.viewmodel.vcu.GearLevel;
import com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.VcuViewModel;
import com.xiaopeng.speech.vui.utils.VuiUtils;
import com.xiaopeng.vui.commons.VuiElementType;
import com.xiaopeng.vui.commons.VuiMode;
import com.xiaopeng.vui.commons.model.VuiEvent;
import com.xiaopeng.xui.vui.VuiView;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XListMultiple;
import com.xiaopeng.xui.widget.XTabLayout;
import com.xiaopeng.xui.widget.toggle.XToggleLayout;
import com.xiaopeng.xui.widget.toggle.XToggleText;
import java.util.HashMap;

/* loaded from: classes2.dex */
public class MainControlFragment extends BaseFragment {
    private CarBodyViewModel mCarBodyVm;
    private XButton mCloseTrunkBtn;
    private XToggleLayout mCloseWinBtn;
    private XTabLayout mDriveModeTab;
    private XListMultiple mDriveModeTabItem;
    private XToggleText mFogLampBtn;
    private XTabLayout mHeadLampBtn;
    private LampViewModel mLampVm;
    private MirrorViewModel mMirrorVm;
    private XButton mOpenTrunkBtn;
    private XToggleLayout mOpenWinBtn;
    private VcuViewModel mVcuVm;
    private XToggleLayout mVentBtn;
    private WindowDoorViewModel mWindowDoorVm;
    private XTabLayout mWiperIntervalTab;

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment
    protected int getPreLoadLayoutId() {
        return R.layout.layout_main_control_stub;
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.VuiFragment
    protected int getRootLayoutId() {
        return R.layout.fragment_main_control;
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment
    protected boolean needPreLoadLayout() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment
    protected void initViewModels() {
        this.mVcuVm = (VcuViewModel) getViewModel(IVcuViewModel.class);
        this.mCarBodyVm = (CarBodyViewModel) getViewModel(ICarBodyViewModel.class);
        this.mWindowDoorVm = (WindowDoorViewModel) getViewModel(IWindowDoorViewModel.class);
        this.mMirrorVm = (MirrorViewModel) getViewModel(IMirrorViewModel.class);
        this.mLampVm = (LampViewModel) getViewModel(ILampViewModel.class);
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment
    protected void initViews() {
        initMainControlLayout();
    }

    private void initMainControlLayout() {
        ((XImageView) this.mPreloadLayout.findViewById(R.id.img_top_car)).setImageResource(App.getMainControlPageCarBodyImg());
        initDriveMode(this.mPreloadLayout);
        initHeadLamp(this.mPreloadLayout);
        initMirror(this.mPreloadLayout);
        initWindow(this.mPreloadLayout);
        initTrunk(this.mPreloadLayout);
        initShade(this.mPreloadLayout);
        initWiperInterval(this.mPreloadLayout);
        addScene(new SeatControlScene(getSceneId(), this.mPreloadLayout, this));
        if (!CarBaseConfig.getInstance().isSupportSeatCtrl()) {
            ((MainScrollView) this.mRootContainer.findViewById(R.id.main_control_scroll)).setVerticalScrollBarEnabled(false);
            this.mPreloadLayout.findViewById(R.id.divider2).setVisibility(8);
        }
        updateSeatView(CarBaseConfig.getInstance().isSupportSeatCtrl());
    }

    private void initDriveMode(View parent) {
        XListMultiple xListMultiple = (XListMultiple) parent.findViewById(R.id.drive_mode_tab_item);
        this.mDriveModeTabItem = xListMultiple;
        XTabLayout xTabLayout = (XTabLayout) xListMultiple.findViewById(R.id.drive_mode_tab);
        this.mDriveModeTab = xTabLayout;
        xTabLayout.setOnTabChangeListener(new XTabLayout.OnTabChangeListenerAdapter() { // from class: com.xiaopeng.carcontrol.view.fragment.MainControlFragment.1
            @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListenerAdapter, com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
            public boolean onInterceptTabChange(XTabLayout tabLayout, int index, boolean tabChange, boolean fromUser) {
                if (fromUser && ClickHelper.isFastClick(R.id.drive_mode_tab, 1000L, false)) {
                    NotificationHelper.getInstance().showToast(R.string.operation_fast_tips);
                    return true;
                }
                return false;
            }

            @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
            public void onTabChangeEnd(XTabLayout xTabLayout2, int index, boolean tabChange, boolean fromUser) {
                if (fromUser) {
                    MainControlFragment.this.setDriveMode(index);
                    PageEnum pageEnum = PageEnum.DRIVE_PAGE;
                    BtnEnum btnEnum = BtnEnum.DRIVE_MODE_BTN;
                    int i = 1;
                    Object[] objArr = new Object[1];
                    if (index == 0) {
                        i = 2;
                    } else if (index != 2) {
                        i = 3;
                    }
                    objArr[0] = Integer.valueOf(i);
                    StatisticUtils.sendStatistic(pageEnum, btnEnum, objArr);
                }
            }
        });
        setLiveDataObserver(this.mVcuVm.getDriveModeData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$MainControlFragment$ATgbVEDO1z0o7t4Opol_uk4mLTg
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainControlFragment.this.updateDriveMode((DriveMode) obj);
            }
        });
        setLiveDataObserver(this.mVcuVm.getExhibitionModeData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$MainControlFragment$fX_S8e5xZiQrEPK5Q4hm2PvzUTM
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainControlFragment.this.lambda$initDriveMode$0$MainControlFragment((Boolean) obj);
            }
        });
        updateExhibitionMode(this.mVcuVm.isExhibitionMode());
    }

    public /* synthetic */ void lambda$initDriveMode$0$MainControlFragment(Boolean mode) {
        if (mode != null) {
            updateExhibitionMode(mode.booleanValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDriveMode(int index) {
        DriveMode driveMode;
        if (index == 0) {
            driveMode = DriveMode.Normal;
        } else if (index == 1) {
            driveMode = DriveMode.Sport;
        } else if (index == 2) {
            driveMode = DriveMode.Eco;
        } else {
            LogUtils.d(this.TAG, "error drive mode index=" + index);
            driveMode = null;
        }
        if (driveMode != null) {
            this.mVcuVm.setDriveModeByUser(driveMode);
        }
    }

    private void updateExhibitionMode(boolean exhibitionMode) {
        this.mDriveModeTab.setEnabled(!exhibitionMode);
        if (exhibitionMode) {
            return;
        }
        updateDriveMode(this.mVcuVm.getDriveModeData().getValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDriveMode(DriveMode driveMode) {
        LogUtils.d(this.TAG, "updateDriveMode:driveMode=" + driveMode);
        if (driveMode == null) {
            return;
        }
        if (this.mDriveModeTabItem == null || this.mDriveModeTab == null) {
            LogUtils.d(this.TAG, "inflateUiLayout not finish");
        } else if (DriveMode.Comfort == driveMode) {
            this.mDriveModeTab.selectedNoneTab(false, true);
            this.mDriveModeTabItem.setTextSub(getStringById(R.string.drive_mode_anti_carsick_tips));
        } else if (DriveMode.EcoPlus == driveMode) {
            this.mDriveModeTab.selectedNoneTab(false, true);
            this.mDriveModeTabItem.setTextSub(getStringById(R.string.drive_mode_xpdeal_tips));
        } else {
            int i = AnonymousClass9.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$DriveMode[driveMode.ordinal()];
            if (i == 1) {
                updateXTabLayout(this.mDriveModeTab, 0);
                this.mDriveModeTabItem.setTextSub(getStringById(R.string.drive_mode_standard_summary));
            } else if (i == 2) {
                updateXTabLayout(this.mDriveModeTab, 2);
                this.mDriveModeTabItem.setTextSub(getStringById(R.string.drive_mode_eco_summary));
            } else if (i != 3) {
            } else {
                updateXTabLayout(this.mDriveModeTab, 1);
                this.mDriveModeTabItem.setTextSub(getStringById(R.string.drive_mode_sport_summary));
            }
        }
    }

    private void initHeadLamp(View parent) {
        this.mHeadLampBtn = (XTabLayout) parent.findViewById(R.id.head_lamp_tab);
        updateHeadLamp(this.mLampVm.getHeadLampMode(LampMode.Auto));
        setLiveDataObserver(this.mLampVm.getHeadLampData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$MainControlFragment$rpal8cS_CY0CGPnNza-Khfq-Qg4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainControlFragment.this.updateHeadLamp((LampMode) obj);
            }
        });
        this.mHeadLampBtn.setOnTabChangeListener(new XTabLayout.OnTabChangeListenerAdapter() { // from class: com.xiaopeng.carcontrol.view.fragment.MainControlFragment.2
            @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListenerAdapter, com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
            public boolean onInterceptTabChange(XTabLayout tabLayout, int index, boolean tabChange, boolean fromUser) {
                if (fromUser && ClickHelper.isFastClick(R.id.head_lamp_tab, 500L, false)) {
                    LogUtils.i(MainControlFragment.this.TAG, "Intercept head lamp tab layout for fast click");
                    return true;
                }
                return false;
            }

            @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
            public void onTabChangeEnd(XTabLayout xTabLayout, int index, boolean tabChange, boolean fromUser) {
                if (fromUser) {
                    MainControlFragment.this.setHeadLampMode(index);
                }
            }
        });
        XToggleText xToggleText = (XToggleText) parent.findViewById(R.id.btn_fog_light);
        this.mFogLampBtn = xToggleText;
        xToggleText.setChecked(this.mLampVm.getRearFogLampState());
        setLiveDataObserver(this.mLampVm.getRearFogLampData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$MainControlFragment$gn9f9RuuamFNOmiGHeaFlG5Y8NI
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainControlFragment.this.lambda$initHeadLamp$1$MainControlFragment((Boolean) obj);
            }
        });
        this.mFogLampBtn.setOnCheckedChangeListener(new XToggleLayout.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.MainControlFragment.3
            @Override // com.xiaopeng.xui.widget.toggle.XToggleLayout.OnCheckedChangeListener
            public void onCheckedChanged(XToggleLayout buttonView, boolean isChecked) {
            }

            @Override // com.xiaopeng.xui.widget.toggle.XToggleLayout.OnCheckedChangeListener
            public boolean onInterceptClickCheck(XToggleLayout xToggleLayout) {
                if (!ClickHelper.isFastClick(R.id.head_lamp_tab, 500L, false)) {
                    MainControlFragment.this.mLampVm.setRearFogLamp(!xToggleLayout.isChecked());
                    StatisticUtils.sendStatistic(PageEnum.CONTROL_PAGE, BtnEnum.REARFOG_LAMP_BTN, new Object[0]);
                    return true;
                }
                LogUtils.i(MainControlFragment.this.TAG, "Intercept rear fog button for fast click");
                return true;
            }
        });
        if (CarBaseConfig.getInstance().isSupportLightMeHomeNew()) {
            return;
        }
        parent.findViewById(R.id.light_me_home_desc).setVisibility(0);
        final XToggleText xToggleText2 = (XToggleText) parent.findViewById(R.id.light_me_home_btn);
        xToggleText2.setVisibility(0);
        xToggleText2.setChecked(this.mLampVm.isLightMeHomeEnable());
        setLiveDataObserver(this.mLampVm.getLightMeHomeData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$MainControlFragment$o1nlY7J3GIcvPnd7YVNURJ2n2E8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainControlFragment.lambda$initHeadLamp$2(XToggleText.this, (Boolean) obj);
            }
        });
        xToggleText2.setOnCheckedChangeListener(new XToggleLayout.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.MainControlFragment.4
            @Override // com.xiaopeng.xui.widget.toggle.XToggleLayout.OnCheckedChangeListener
            public boolean onInterceptClickCheck(XToggleLayout xToggleLayout) {
                return false;
            }

            @Override // com.xiaopeng.xui.widget.toggle.XToggleLayout.OnCheckedChangeListener
            public void onCheckedChanged(XToggleLayout buttonView, boolean isChecked) {
                if (buttonView.isPressed() || MainControlFragment.this.isVuiAction(buttonView)) {
                    MainControlFragment.this.mLampVm.setLightMeHome(isChecked);
                    StatisticUtils.sendStatistic(PageEnum.CONTROL_PAGE, BtnEnum.LIGHT_ME_HOME_BTN, new Object[0]);
                }
            }
        });
    }

    public /* synthetic */ void lambda$initHeadLamp$1$MainControlFragment(Boolean on) {
        if (on != null) {
            this.mFogLampBtn.setChecked(on.booleanValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$initHeadLamp$2(final XToggleText mLightMeHomeBtn, Boolean enable) {
        if (enable != null) {
            mLightMeHomeBtn.setChecked(enable.booleanValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateHeadLamp(LampMode state) {
        if (state != null) {
            int i = AnonymousClass9.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LampMode[state.ordinal()];
            int i2 = 3;
            if (i == 1) {
                i2 = 0;
            } else if (i == 2) {
                i2 = 1;
            } else if (i == 3) {
                i2 = 2;
            }
            updateXTabLayout(this.mHeadLampBtn, i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setHeadLampMode(int index) {
        LampMode lampMode = LampMode.Auto;
        if (index == 0) {
            lampMode = LampMode.Off;
        } else if (index == 1) {
            lampMode = LampMode.Position;
        } else if (index == 2) {
            lampMode = LampMode.LowBeam;
        } else if (index == 3) {
            lampMode = LampMode.Auto;
        }
        this.mLampVm.setHeadLampMode(lampMode);
    }

    private void initMirror(View parent) {
        if (!CarBaseConfig.getInstance().isSupportMirrorFold()) {
            parent.findViewById(R.id.mirror_fold_tv).setVisibility(8);
            parent.findViewById(R.id.mirror_unfold_btn).setVisibility(8);
            parent.findViewById(R.id.mirror_fold_btn).setVisibility(8);
            parent.findViewById(R.id.divider_mirror_trunk).setVisibility(8);
            return;
        }
        final XButton xButton = (XButton) parent.findViewById(R.id.mirror_unfold_btn);
        VuiUtils.addHasFeedbackProp(xButton);
        xButton.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$MainControlFragment$81FmOtCL6eFjQfOh6-zhLzG5mO4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainControlFragment.this.lambda$initMirror$3$MainControlFragment(view);
            }
        });
        final XButton xButton2 = (XButton) parent.findViewById(R.id.mirror_fold_btn);
        VuiUtils.addHasFeedbackProp(xButton2);
        xButton2.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$MainControlFragment$G6TdXxnJPIl6Pb0qFPJDGmNpFLs
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainControlFragment.this.lambda$initMirror$4$MainControlFragment(view);
            }
        });
        setLiveDataObserver(this.mMirrorVm.getMirrorFoldData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$MainControlFragment$o7OUUlRvXaGa25rEjjpRYOd5rmY
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainControlFragment.lambda$initMirror$5(XButton.this, xButton, (MirrorFoldState) obj);
            }
        });
        boolean z = this.mMirrorVm.getMirrorFoldState() != MirrorFoldState.Middle;
        xButton2.setEnabled(z);
        xButton.setEnabled(z);
    }

    public /* synthetic */ void lambda$initMirror$3$MainControlFragment(View v) {
        this.mMirrorVm.controlMirror(false);
        StatisticUtils.sendStatistic(PageEnum.CONTROL_PAGE, BtnEnum.REAR_MIRROR_EXTEND_BTN, new Object[0]);
    }

    public /* synthetic */ void lambda$initMirror$4$MainControlFragment(View v) {
        if ((v instanceof VuiView) && ((VuiView) v).isPerformVuiAction() && this.mVcuVm.getCarSpeed() >= 3.0f) {
            vuiFeedbackClick(R.string.mirror_state_unable_with_running, v);
        }
        this.mMirrorVm.controlMirror(true);
        StatisticUtils.sendStatistic(PageEnum.CONTROL_PAGE, BtnEnum.REAR_MIRROR_FOLD_BTN, new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$initMirror$5(final XButton foldBtn, final XButton unFoldBtn, MirrorFoldState mirrorFoldState) {
        boolean z = mirrorFoldState != MirrorFoldState.Middle;
        foldBtn.setEnabled(z);
        unFoldBtn.setEnabled(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkWindowLocked(XToggleLayout xToggleLayout) {
        LogUtils.d(this.TAG, "checkWindowLocked1");
        if (this.mWindowDoorVm.isWindowLockActive()) {
            LogUtils.d(this.TAG, "checkWindowLocked2");
            if (isVuiAction(xToggleLayout)) {
                LogUtils.d(this.TAG, "checkWindowLocked3");
                vuiFeedback(R.string.win_lock_is_active, xToggleLayout);
            }
            NotificationHelper.getInstance().showToast(R.string.win_lock_is_active);
            return true;
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.VuiFragment, com.xiaopeng.vui.commons.IVuiSceneListener
    public boolean onInterceptVuiEvent(View view, VuiEvent vuiEvent) {
        int id = view.getId();
        if (id == R.id.open_window_btn || id == R.id.close_window_btn || id == R.id.vent_window_btn) {
            XToggleLayout xToggleLayout = (XToggleLayout) view;
            xToggleLayout.setPerformVuiAction(true);
            if (checkWindowLocked(xToggleLayout)) {
                return true;
            }
            if (this.mWindowDoorVm.isWindowInitFailed(4, false)) {
                vuiFeedback(R.string.window_init_failed_feedback, view);
                return true;
            }
        }
        return super.onInterceptVuiEvent(view, vuiEvent);
    }

    private void initWindow(View parent) {
        XToggleLayout xToggleLayout = (XToggleLayout) parent.findViewById(R.id.open_window_btn);
        this.mOpenWinBtn = xToggleLayout;
        xToggleLayout.setVuiElementType(VuiElementType.BUTTON);
        VuiUtils.addHasFeedbackProp(this.mOpenWinBtn);
        this.mOpenWinBtn.setOnCheckedChangeListener(new XToggleLayout.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.MainControlFragment.5
            @Override // com.xiaopeng.xui.widget.toggle.XToggleLayout.OnCheckedChangeListener
            public void onCheckedChanged(XToggleLayout xToggleLayout2, boolean b) {
            }

            @Override // com.xiaopeng.xui.widget.toggle.XToggleLayout.OnCheckedChangeListener
            public boolean onInterceptClickCheck(XToggleLayout xToggleLayout2) {
                if (MainControlFragment.this.checkWindowLocked(xToggleLayout2)) {
                    return true;
                }
                MainControlFragment.this.mWindowDoorVm.controlWindowOpen();
                StatisticUtils.sendStatistic(PageEnum.CONTROL_PAGE, BtnEnum.WINDOW_ALLOPEN_BTN, new Object[0]);
                return true;
            }
        });
        XToggleLayout xToggleLayout2 = (XToggleLayout) parent.findViewById(R.id.close_window_btn);
        this.mCloseWinBtn = xToggleLayout2;
        xToggleLayout2.setVuiElementType(VuiElementType.BUTTON);
        VuiUtils.addHasFeedbackProp(this.mCloseWinBtn);
        this.mCloseWinBtn.setOnCheckedChangeListener(new XToggleLayout.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.MainControlFragment.6
            @Override // com.xiaopeng.xui.widget.toggle.XToggleLayout.OnCheckedChangeListener
            public void onCheckedChanged(XToggleLayout xToggleLayout3, boolean b) {
            }

            @Override // com.xiaopeng.xui.widget.toggle.XToggleLayout.OnCheckedChangeListener
            public boolean onInterceptClickCheck(XToggleLayout xToggleLayout3) {
                if (MainControlFragment.this.checkWindowLocked(xToggleLayout3)) {
                    return true;
                }
                MainControlFragment.this.mWindowDoorVm.controlWindowClose();
                StatisticUtils.sendStatistic(PageEnum.CONTROL_PAGE, BtnEnum.WINDOW_ALLCLOSE_BTN, new Object[0]);
                return true;
            }
        });
        XToggleLayout xToggleLayout3 = (XToggleLayout) parent.findViewById(R.id.vent_window_btn);
        this.mVentBtn = xToggleLayout3;
        xToggleLayout3.setVuiElementType(VuiElementType.BUTTON);
        VuiUtils.addHasFeedbackProp(this.mVentBtn);
        this.mVentBtn.setOnCheckedChangeListener(new XToggleLayout.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.MainControlFragment.7
            @Override // com.xiaopeng.xui.widget.toggle.XToggleLayout.OnCheckedChangeListener
            public void onCheckedChanged(XToggleLayout xToggleLayout4, boolean b) {
            }

            @Override // com.xiaopeng.xui.widget.toggle.XToggleLayout.OnCheckedChangeListener
            public boolean onInterceptClickCheck(XToggleLayout xToggleLayout4) {
                if (MainControlFragment.this.checkWindowLocked(xToggleLayout4)) {
                    return true;
                }
                MainControlFragment.this.mWindowDoorVm.controlWindowVent();
                StatisticUtils.sendStatistic(PageEnum.CONTROL_PAGE, BtnEnum.VENTILATE_BTN, new Object[0]);
                return true;
            }
        });
        setLiveDataObserver(this.mWindowDoorVm.getWindowStateData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$MainControlFragment$wUwXarMsGCLPjjCUbBTfMbE5fAU
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainControlFragment.this.updateWindows((WindowState) obj);
            }
        });
        if (CarBaseConfig.getInstance().isSupportWindowPos()) {
            XButton xButton = (XButton) parent.findViewById(R.id.adjust_window_btn);
            xButton.setVisibility(0);
            xButton.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$MainControlFragment$zRfbcyuflu8N2wlPogiVa55bWFc
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MainControlFragment.this.lambda$initWindow$6$MainControlFragment(view);
                }
            });
        }
    }

    public /* synthetic */ void lambda$initWindow$6$MainControlFragment(View v) {
        showPanel(GlobalConstant.ACTION.ACTION_SHOW_WINDOW_CONTROL_PANEL);
    }

    private void showWinBtnLoading(XToggleLayout loadingBtn, XToggleLayout disableBtn1, XToggleLayout disableBtn2) {
        loadingBtn.setLoading(true);
        disableBtn1.setLoading(false);
        disableBtn1.setEnabled(false);
        disableBtn1.getChildAt(0).setEnabled(false);
        disableBtn2.setLoading(false);
        disableBtn2.setEnabled(false);
        disableBtn2.getChildAt(0).setEnabled(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateWindows(WindowState state) {
        if (state == null) {
            return;
        }
        if (BaseFeatureOption.getInstance().isSupportAllWindowState()) {
            switch (AnonymousClass9.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$WindowState[state.ordinal()]) {
                case 1:
                    this.mOpenWinBtn.setLoading(false);
                    this.mOpenWinBtn.setEnabled(false);
                    this.mOpenWinBtn.getChildAt(0).setEnabled(false);
                    this.mCloseWinBtn.setLoading(false);
                    this.mCloseWinBtn.setEnabled(true);
                    this.mCloseWinBtn.getChildAt(0).setEnabled(true);
                    this.mVentBtn.setLoading(false);
                    this.mVentBtn.setEnabled(false);
                    this.mVentBtn.getChildAt(0).setEnabled(false);
                    return;
                case 2:
                    this.mCloseWinBtn.setLoading(false);
                    this.mCloseWinBtn.setEnabled(false);
                    this.mCloseWinBtn.getChildAt(0).setEnabled(false);
                    this.mOpenWinBtn.setLoading(false);
                    this.mOpenWinBtn.setEnabled(true);
                    this.mOpenWinBtn.getChildAt(0).setEnabled(true);
                    this.mVentBtn.setLoading(false);
                    this.mVentBtn.setEnabled(true);
                    this.mVentBtn.getChildAt(0).setEnabled(true);
                    return;
                case 3:
                    showWinBtnLoading(this.mOpenWinBtn, this.mCloseWinBtn, this.mVentBtn);
                    return;
                case 4:
                    showWinBtnLoading(this.mCloseWinBtn, this.mOpenWinBtn, this.mVentBtn);
                    return;
                case 5:
                    this.mOpenWinBtn.setLoading(false);
                    this.mOpenWinBtn.setEnabled(true);
                    this.mOpenWinBtn.getChildAt(0).setEnabled(true);
                    this.mCloseWinBtn.setLoading(false);
                    this.mCloseWinBtn.setEnabled(true);
                    this.mCloseWinBtn.getChildAt(0).setEnabled(true);
                    this.mVentBtn.setLoading(false);
                    this.mVentBtn.setEnabled(false);
                    this.mVentBtn.getChildAt(0).setEnabled(false);
                    return;
                case 6:
                    showWinBtnLoading(this.mVentBtn, this.mOpenWinBtn, this.mCloseWinBtn);
                    return;
                default:
                    return;
            }
        }
        int i = AnonymousClass9.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$WindowState[state.ordinal()];
        if (i == 3) {
            showWinBtnLoading(this.mOpenWinBtn, this.mCloseWinBtn, this.mVentBtn);
        } else if (i == 4) {
            showWinBtnLoading(this.mCloseWinBtn, this.mOpenWinBtn, this.mVentBtn);
        } else if (i == 6) {
            showWinBtnLoading(this.mVentBtn, this.mOpenWinBtn, this.mCloseWinBtn);
        } else {
            this.mOpenWinBtn.setLoading(false);
            this.mOpenWinBtn.setEnabled(true);
            this.mOpenWinBtn.getChildAt(0).setEnabled(true);
            this.mCloseWinBtn.setLoading(false);
            this.mCloseWinBtn.setEnabled(true);
            this.mCloseWinBtn.getChildAt(0).setEnabled(true);
            this.mVentBtn.setLoading(false);
            this.mVentBtn.setEnabled(true);
            this.mVentBtn.getChildAt(0).setEnabled(true);
        }
    }

    private void initTrunk(View parent) {
        if (CarBaseConfig.getInstance().isSupportElcTrunk()) {
            XButton xButton = (XButton) parent.findViewById(R.id.open_trunk_btn);
            this.mOpenTrunkBtn = xButton;
            VuiUtils.addHasFeedbackProp(xButton);
            VuiUtils.setStatefulButtonAttr(this.mOpenTrunkBtn, 1, new String[]{ResUtils.getString(R.string.open_trunk_idle_label), ResUtils.getString(R.string.trunk_running_label)}, "SetValue|Click");
            this.mOpenTrunkBtn.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$MainControlFragment$b43sMi0aOsz_gk00SiOxVlIq2k4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MainControlFragment.this.lambda$initTrunk$7$MainControlFragment(view);
                }
            });
            XButton xButton2 = (XButton) parent.findViewById(R.id.close_trunk_btn);
            this.mCloseTrunkBtn = xButton2;
            xButton2.setVisibility(0);
            this.mCloseTrunkBtn.setVuiMode(VuiMode.NORMAL);
            VuiUtils.setStatefulButtonAttr(this.mCloseTrunkBtn, 1, new String[]{ResUtils.getString(R.string.close_trunk_idle_label), ResUtils.getString(R.string.trunk_running_label)}, "SetValue|Click");
            this.mCloseTrunkBtn.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$MainControlFragment$uFLCb3TMOOW1WUDvV3GXpMsh4F8
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MainControlFragment.this.lambda$initTrunk$8$MainControlFragment(view);
                }
            });
            HashMap hashMap = new HashMap();
            hashMap.put("hasFeedback", true);
            hashMap.put("skipMultipleAlready", true);
            VuiUtils.addProps(this.mOpenTrunkBtn, hashMap);
            VuiUtils.addProps(this.mCloseTrunkBtn, hashMap);
            setVuiLabelUnSupportText(this.mOpenTrunkBtn);
            setVuiLabelUnSupportText(this.mCloseTrunkBtn);
            setLiveDataObserver(this.mWindowDoorVm.getRearTrunkData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$MainControlFragment$QUxQD1tv3unBLxXA3XndNBurmDQ
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    MainControlFragment.this.lambda$initTrunk$9$MainControlFragment((TrunkState) obj);
                }
            });
            updateTrunkBtn(this.mWindowDoorVm.getRearTrunkStateValue(), false);
            return;
        }
        XButton xButton3 = (XButton) parent.findViewById(R.id.open_trunk_btn);
        ViewGroup.LayoutParams layoutParams = xButton3.getLayoutParams();
        layoutParams.width = ResUtils.dp2px(314);
        xButton3.setLayoutParams(layoutParams);
        xButton3.setText(R.string.unlock_trunk);
        VuiUtils.addHasFeedbackProp(xButton3);
        xButton3.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$MainControlFragment$mQv6wx8R8pRLr-fEGgndY7Vgnnw
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainControlFragment.this.lambda$initTrunk$10$MainControlFragment(view);
            }
        });
    }

    public /* synthetic */ void lambda$initTrunk$7$MainControlFragment(View v) {
        controlTrunk(v);
        StatisticUtils.sendStatistic(PageEnum.CONTROL_PAGE, BtnEnum.OPEN_TRUNK_BTN, new Object[0]);
    }

    public /* synthetic */ void lambda$initTrunk$8$MainControlFragment(View v) {
        controlTrunk(v);
        StatisticUtils.sendStatistic(PageEnum.CONTROL_PAGE, BtnEnum.CLOSE_TRUNK_BTN, new Object[0]);
    }

    public /* synthetic */ void lambda$initTrunk$9$MainControlFragment(TrunkState d2TrunkState) {
        updateTrunkBtn(d2TrunkState, false);
    }

    public /* synthetic */ void lambda$initTrunk$10$MainControlFragment(View v) {
        if (this.mVcuVm.getGearLevelValue() != GearLevel.P) {
            vuiFeedbackClick(R.string.trunk_unable_with_none_park_gear, v);
        }
        this.mWindowDoorVm.openRearTrunk();
        StatisticUtils.sendStatistic(PageEnum.CONTROL_PAGE, BtnEnum.OPEN_TRUNK_BTN, new Object[0]);
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.VuiFragment, com.xiaopeng.vui.commons.IVuiSceneListener
    public void onVuiEvent(View view, VuiEvent vuiEvent) {
        super.onVuiEvent(view, vuiEvent);
        int id = view.getId();
        if (R.id.open_trunk_btn == id || R.id.close_trunk_btn == id) {
            ((VuiView) view).setPerformVuiAction(true);
            view.performClick();
        }
    }

    private void controlTrunk(View trunkBtn) {
        if (ClickHelper.isFastClick(1000L, false)) {
            LogUtils.d(this.TAG, "Control trunk too fast!!!");
        } else if (trunkBtn == this.mOpenTrunkBtn && this.mVcuVm.getCarSpeed() >= 3.0f) {
            NotificationHelper.getInstance().showToast(R.string.trunk_unable_with_running);
            vuiFeedbackClick(R.string.trunk_unable_with_running, trunkBtn);
        } else {
            TrunkState rearTrunkStateValue = this.mWindowDoorVm.getRearTrunkStateValue();
            if (trunkBtn == this.mOpenTrunkBtn) {
                if (rearTrunkStateValue == TrunkState.Opening) {
                    this.mWindowDoorVm.controlRearTrunk(TrunkType.Pause);
                    updateTrunkBtn(TrunkState.Pause_Opening, true);
                    return;
                }
                this.mWindowDoorVm.controlRearTrunk(TrunkType.Open);
            } else if (rearTrunkStateValue == TrunkState.Closing) {
                this.mWindowDoorVm.controlRearTrunk(TrunkType.Pause);
                updateTrunkBtn(TrunkState.Pause_Closing, true);
            } else {
                this.mWindowDoorVm.controlRearTrunk(TrunkType.Close);
            }
        }
    }

    private void updateTrunkBtn(TrunkState state, boolean isMock) {
        LogUtils.d(this.TAG, "updateTrunkBtn:state=" + state + ", isMock=" + isMock);
        if (state == null) {
            return;
        }
        switch (AnonymousClass9.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$TrunkState[state.ordinal()]) {
            case 1:
                this.mOpenTrunkBtn.setEnabled(false);
                this.mOpenTrunkBtn.setText(R.string.open_trunk);
                VuiUtils.setStatefulButtonValue(this.mOpenTrunkBtn, 1);
                this.mCloseTrunkBtn.setEnabled(true);
                this.mCloseTrunkBtn.setText(R.string.close_trunk);
                VuiUtils.setStatefulButtonValue(this.mCloseTrunkBtn, 1);
                return;
            case 2:
                this.mOpenTrunkBtn.setEnabled(true);
                this.mOpenTrunkBtn.setText(R.string.open_trunk);
                VuiUtils.setStatefulButtonValue(this.mOpenTrunkBtn, 1);
                this.mCloseTrunkBtn.setEnabled(false);
                this.mCloseTrunkBtn.setText(R.string.close_trunk);
                VuiUtils.setStatefulButtonValue(this.mCloseTrunkBtn, 1);
                return;
            case 3:
                this.mOpenTrunkBtn.setEnabled(true);
                this.mOpenTrunkBtn.setText(R.string.pause_trunk);
                VuiUtils.setStatefulButtonValue(this.mOpenTrunkBtn, 0);
                this.mCloseTrunkBtn.setEnabled(false);
                this.mCloseTrunkBtn.setText(R.string.close_trunk);
                VuiUtils.setStatefulButtonValue(this.mCloseTrunkBtn, 1);
                return;
            case 4:
                this.mOpenTrunkBtn.setEnabled(false);
                this.mOpenTrunkBtn.setText(R.string.open_trunk);
                VuiUtils.setStatefulButtonValue(this.mOpenTrunkBtn, 1);
                this.mCloseTrunkBtn.setEnabled(true);
                this.mCloseTrunkBtn.setText(R.string.pause_trunk);
                VuiUtils.setStatefulButtonValue(this.mCloseTrunkBtn, 0);
                return;
            case 5:
            case 6:
                this.mOpenTrunkBtn.setEnabled(true);
                this.mOpenTrunkBtn.setText(R.string.open_trunk);
                VuiUtils.setStatefulButtonValue(this.mOpenTrunkBtn, 1);
                this.mCloseTrunkBtn.setEnabled(true);
                this.mCloseTrunkBtn.setText(R.string.close_trunk);
                VuiUtils.setStatefulButtonValue(this.mCloseTrunkBtn, 1);
                return;
            case 7:
                this.mOpenTrunkBtn.setEnabled(false);
                this.mCloseTrunkBtn.setEnabled(false);
                return;
            default:
                return;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.carcontrol.view.fragment.MainControlFragment$9  reason: invalid class name */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass9 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$TrunkState;
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$WindowState;
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LampMode;
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$DriveMode;

        static {
            int[] iArr = new int[TrunkState.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$TrunkState = iArr;
            try {
                iArr[TrunkState.Opened.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$TrunkState[TrunkState.Closed.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$TrunkState[TrunkState.Opening.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$TrunkState[TrunkState.Closing.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$TrunkState[TrunkState.Pause_Closing.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$TrunkState[TrunkState.Pause_Opening.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$TrunkState[TrunkState.Preparing.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            int[] iArr2 = new int[WindowState.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$WindowState = iArr2;
            try {
                iArr2[WindowState.Opened.ordinal()] = 1;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$WindowState[WindowState.Closed.ordinal()] = 2;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$WindowState[WindowState.Opening.ordinal()] = 3;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$WindowState[WindowState.Closing.ordinal()] = 4;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$WindowState[WindowState.HalfOpened.ordinal()] = 5;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$WindowState[WindowState.Ventting.ordinal()] = 6;
            } catch (NoSuchFieldError unused13) {
            }
            int[] iArr3 = new int[LampMode.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LampMode = iArr3;
            try {
                iArr3[LampMode.Off.ordinal()] = 1;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LampMode[LampMode.Position.ordinal()] = 2;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LampMode[LampMode.LowBeam.ordinal()] = 3;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LampMode[LampMode.Auto.ordinal()] = 4;
            } catch (NoSuchFieldError unused17) {
            }
            int[] iArr4 = new int[DriveMode.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$DriveMode = iArr4;
            try {
                iArr4[DriveMode.Normal.ordinal()] = 1;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$DriveMode[DriveMode.Eco.ordinal()] = 2;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$DriveMode[DriveMode.Sport.ordinal()] = 3;
            } catch (NoSuchFieldError unused20) {
            }
        }
    }

    private void initShade(View parent) {
        if (CarBaseConfig.getInstance().isSupportSunShade()) {
            parent.findViewById(R.id.sun_shade_tv).setVisibility(0);
            XButton xButton = (XButton) parent.findViewById(R.id.sun_shade_btn);
            xButton.setVisibility(0);
            xButton.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$MainControlFragment$R2EqM-rxMAhIvNUQk9DoD3vqv-E
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MainControlFragment.this.lambda$initShade$11$MainControlFragment(view);
                }
            });
        }
    }

    public /* synthetic */ void lambda$initShade$11$MainControlFragment(View v) {
        showPanel(GlobalConstant.ACTION.ACTION_SHOW_SUNSHADE_CONTROL_PANEL);
    }

    private void showPanel(String panelKey) {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            Intent intent = new Intent(activity, CarControlService.class);
            intent.setAction(panelKey);
            activity.startService(intent);
        }
    }

    private void initWiperInterval(View parent) {
        if (CarBaseConfig.getInstance().isSupportMainWiperInterval()) {
            XListMultiple xListMultiple = (XListMultiple) parent.findViewById(R.id.wiper_gear_tab_item);
            xListMultiple.setVisibility(0);
            XTabLayout xTabLayout = (XTabLayout) xListMultiple.findViewById(R.id.wiper_interval_tab);
            this.mWiperIntervalTab = xTabLayout;
            xTabLayout.setOnTabChangeListener(new XTabLayout.OnTabChangeListenerAdapter() { // from class: com.xiaopeng.carcontrol.view.fragment.MainControlFragment.8
                @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
                public void onTabChangeEnd(XTabLayout xTabLayout2, int index, boolean tabChange, boolean fromUser) {
                    if (fromUser) {
                        MainControlFragment.this.mCarBodyVm.setWiperInterval(WiperInterval.getIndexValue(index));
                        StatisticUtils.sendStatistic(PageEnum.SETTING_PAGE, BtnEnum.WIPER_SHIFT, Integer.valueOf(index + 1));
                    }
                }
            });
            WiperInterval wiperIntervalValue = this.mCarBodyVm.getWiperIntervalValue();
            if (wiperIntervalValue != null) {
                this.mWiperIntervalTab.selectTab(wiperIntervalValue.ordinal());
            }
            setLiveDataObserver(this.mCarBodyVm.getWiperIntervalData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$MainControlFragment$GP7ReI7nan8AkiRWB5duzdlvXOY
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    MainControlFragment.this.lambda$initWiperInterval$12$MainControlFragment((WiperInterval) obj);
                }
            });
        }
    }

    public /* synthetic */ void lambda$initWiperInterval$12$MainControlFragment(WiperInterval wiperInterval) {
        if (wiperInterval != null) {
            this.mWiperIntervalTab.selectTab(wiperInterval.ordinal());
        }
    }

    private void updateSeatView(boolean show) {
        this.mPreloadLayout.findViewById(R.id.seat_control_tv).setVisibility(show ? 0 : 8);
        this.mPreloadLayout.findViewById(R.id.account_group_tip).setVisibility(show ? 0 : 8);
        this.mPreloadLayout.findViewById(R.id.drv_seat_view).setVisibility(show ? 0 : 8);
        this.mPreloadLayout.findViewById(R.id.psn_seat_view).setAlpha(0.0f);
        this.mPreloadLayout.findViewById(R.id.psn_seat_view).setVisibility(show ? 0 : 8);
        this.mPreloadLayout.findViewById(R.id.seat_drv_tip).setVisibility(show ? 0 : 8);
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment, androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean hidden) {
        VcuViewModel vcuViewModel;
        CarBodyViewModel carBodyViewModel;
        WiperInterval wiperIntervalValue;
        super.onHiddenChanged(hidden);
        if (!hidden && this.mWiperIntervalTab != null && (carBodyViewModel = this.mCarBodyVm) != null && (wiperIntervalValue = carBodyViewModel.getWiperIntervalValue()) != null) {
            this.mWiperIntervalTab.selectTab(wiperIntervalValue.ordinal());
        }
        if (hidden || this.mDriveModeTab == null || (vcuViewModel = this.mVcuVm) == null) {
            return;
        }
        updateDriveMode(vcuViewModel.getDriveModeData().getValue());
    }
}
