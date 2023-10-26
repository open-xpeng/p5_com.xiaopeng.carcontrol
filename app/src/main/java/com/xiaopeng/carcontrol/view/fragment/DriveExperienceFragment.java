package com.xiaopeng.carcontrol.view.fragment;

import android.support.rastermill.FrameSequenceUtil;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import androidx.lifecycle.Observer;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.R;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.helper.ClickHelper;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.speech.VuiManager;
import com.xiaopeng.carcontrol.statistic.BtnEnum;
import com.xiaopeng.carcontrol.statistic.PageEnum;
import com.xiaopeng.carcontrol.statistic.StatisticUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.viewmodel.chassis.ChassisViewModel;
import com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel;
import com.xiaopeng.carcontrol.viewmodel.chassis.SteeringEpsMode;
import com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel;
import com.xiaopeng.carcontrol.viewmodel.scu.ScuResponse;
import com.xiaopeng.carcontrol.viewmodel.scu.ScuViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.DriveMode;
import com.xiaopeng.carcontrol.viewmodel.vcu.EnergyRecycleGrade;
import com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.VcuViewModel;
import com.xiaopeng.speech.vui.utils.VuiUtils;
import com.xiaopeng.vui.commons.model.VuiEvent;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.widget.XListMultiple;
import com.xiaopeng.xui.widget.XSwitch;
import com.xiaopeng.xui.widget.XTabLayout;

/* loaded from: classes2.dex */
public class DriveExperienceFragment extends BaseFragment {
    private XDialog mAntiCarSickDialog;
    private XSwitch mAntiCarSickSw;
    private ImageView mCarBodyImg;
    private ChassisViewModel mChassisVm;
    private DriveMode mCurrentDriveMode;
    private ImageView mDriveModeImg;
    private XTabLayout mDriveModeTab;
    private XListMultiple mDriveModeTabItem;
    private XTabLayout mEnergyGradeTab;
    private XListMultiple mEnergyGradeTabItem;
    private XTabLayout mEpsTab;
    private ScuViewModel mScuVm;
    private VcuViewModel mVcuVm;
    private XDialog mXPedalDialog;
    private XSwitch mXPedalSw;

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment
    protected int getPreLoadLayoutId() {
        return R.layout.layout_drive_experience_stub;
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.VuiFragment
    protected int getRootLayoutId() {
        return R.layout.fragment_drive_experience;
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment
    protected boolean needPreLoadLayout() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment
    protected void initViewModels() {
        this.mVcuVm = (VcuViewModel) getViewModel(IVcuViewModel.class);
        this.mChassisVm = (ChassisViewModel) getViewModel(IChassisViewModel.class);
        this.mScuVm = (ScuViewModel) getViewModel(IScuViewModel.class);
    }

    private void findViews() {
        this.mDriveModeImg = (ImageView) this.mPreloadLayout.findViewById(R.id.drive_mode_img);
        this.mCarBodyImg = (ImageView) this.mPreloadLayout.findViewById(R.id.drive_mode_car);
        XListMultiple xListMultiple = (XListMultiple) this.mPreloadLayout.findViewById(R.id.drive_mode_tab_item);
        this.mDriveModeTabItem = xListMultiple;
        XTabLayout xTabLayout = (XTabLayout) xListMultiple.findViewById(R.id.drive_mode_tab);
        this.mDriveModeTab = xTabLayout;
        xTabLayout.setVuiLabel(getStringById(R.string.drive_mode_tv));
        XListMultiple xListMultiple2 = (XListMultiple) this.mPreloadLayout.findViewById(R.id.power_recycle_tab_item);
        this.mEnergyGradeTabItem = xListMultiple2;
        XTabLayout xTabLayout2 = (XTabLayout) xListMultiple2.findViewById(R.id.power_recycle_tab);
        this.mEnergyGradeTab = xTabLayout2;
        xTabLayout2.setVuiLabel(getStringById(R.string.power_recycle_tv));
        XTabLayout xTabLayout3 = (XTabLayout) this.mPreloadLayout.findViewById(R.id.eps_item).findViewById(R.id.eps_tab);
        this.mEpsTab = xTabLayout3;
        xTabLayout3.setVuiLabel(getStringById(R.string.eps_tv));
        if (CarBaseConfig.getInstance().isSupportXPedal()) {
            View findViewById = this.mPreloadLayout.findViewById(R.id.x_pedal_sw_item);
            findViewById.setVisibility(0);
            XSwitch xSwitch = (XSwitch) findViewById.findViewById(R.id.x_list_sw);
            this.mXPedalSw = xSwitch;
            xSwitch.setVuiLabel(getStringById(R.string.drive_mode_eco_plus));
            this.mXPedalSw.setTag(Integer.valueOf((int) R.id.x_pedal_sw_item));
        }
        XSwitch xSwitch2 = (XSwitch) this.mPreloadLayout.findViewById(R.id.anti_carsick_sw_item).findViewById(R.id.x_list_sw);
        this.mAntiCarSickSw = xSwitch2;
        xSwitch2.setVuiLabel(getStringById(R.string.drive_mode_anti_carsick));
        this.mAntiCarSickSw.setTag(Integer.valueOf((int) R.id.anti_carsick_sw_item));
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment
    protected void initViews() {
        initContentLayout();
    }

    private void initContentLayout() {
        findViews();
        this.mCarBodyImg.setImageResource(App.getMainControlPageCarBodyImg());
        this.mDriveModeTab.setOnTabChangeListener(new XTabLayout.OnTabChangeListenerAdapter() { // from class: com.xiaopeng.carcontrol.view.fragment.DriveExperienceFragment.1
            @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListenerAdapter, com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
            public boolean onInterceptTabChange(XTabLayout tabLayout, int index, boolean tabChange, boolean fromUser) {
                if (fromUser && ClickHelper.isFastClick(R.id.drive_mode_tab, 1000L, false)) {
                    NotificationHelper.getInstance().showToast(R.string.operation_fast_tips);
                    return true;
                }
                return false;
            }

            @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
            public void onTabChangeEnd(XTabLayout xTabLayout, int index, boolean tabChange, boolean fromUser) {
                if (fromUser) {
                    DriveExperienceFragment.this.setDriveMode(index);
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
        XSwitch xSwitch = this.mXPedalSw;
        if (xSwitch != null) {
            VuiUtils.addHasFeedbackProp(xSwitch);
            this.mXPedalSw.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$DriveExperienceFragment$098RRhZTq-wQ9zfmrqfNfmLceEI
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return DriveExperienceFragment.this.lambda$initContentLayout$0$DriveExperienceFragment(view, motionEvent);
                }
            });
        }
        VuiUtils.addHasFeedbackProp(this.mAntiCarSickSw);
        this.mAntiCarSickSw.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$DriveExperienceFragment$mg-osYK2s3ssRRLfeuU3ZmZpogs
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return DriveExperienceFragment.this.lambda$initContentLayout$1$DriveExperienceFragment(view, motionEvent);
            }
        });
        this.mEnergyGradeTab.setOnTabChangeListener(new XTabLayout.OnTabChangeListenerAdapter() { // from class: com.xiaopeng.carcontrol.view.fragment.DriveExperienceFragment.2
            @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListenerAdapter, com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
            public boolean onInterceptTabChange(XTabLayout tabLayout, int index, boolean tabChange, boolean fromUser) {
                if (fromUser && ClickHelper.isFastClick(R.id.drive_mode_tab, 1000L, false)) {
                    NotificationHelper.getInstance().showToast(R.string.operation_fast_tips);
                    return true;
                }
                return false;
            }

            @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
            public void onTabChangeEnd(XTabLayout xTabLayout, int index, boolean tabChange, boolean fromUser) {
                if (fromUser) {
                    DriveExperienceFragment.this.setEnergyGrade(index);
                    StatisticUtils.sendStatistic(PageEnum.DRIVE_PAGE, BtnEnum.ENGER_RECYCLE_BTN, Integer.valueOf(3 - index));
                }
            }
        });
        updateExhibitionMode(this.mVcuVm.isExhibitionMode());
        updateDriveMode(this.mVcuVm.getDriveModeData().getValue());
        updateEnergyGrade(this.mVcuVm.getEnergyRecycle());
        setLiveDataObserver(this.mVcuVm.getExhibitionModeData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$DriveExperienceFragment$Zgh2gY_t5Ck7qeZwOB3AXtRFM3U
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DriveExperienceFragment.this.lambda$initContentLayout$2$DriveExperienceFragment((Boolean) obj);
            }
        });
        setLiveDataObserver(this.mVcuVm.getDriveModeData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$DriveExperienceFragment$eCbca2MSgmJxeoMBXd--G2xpuzg
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DriveExperienceFragment.this.updateDriveMode((DriveMode) obj);
            }
        });
        setLiveDataObserver(this.mVcuVm.getEnergyRecycleData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$DriveExperienceFragment$xcwSCcwy0EuggzxZRT4DW8vY0y8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DriveExperienceFragment.this.updateEnergyGrade((EnergyRecycleGrade) obj);
            }
        });
        this.mEpsTab.setOnTabChangeListener(new XTabLayout.OnTabChangeListenerAdapter() { // from class: com.xiaopeng.carcontrol.view.fragment.DriveExperienceFragment.3
            @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListenerAdapter, com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
            public boolean onInterceptTabChange(XTabLayout tabLayout, int index, boolean tabChange, boolean fromUser) {
                if (fromUser) {
                    if (!ClickHelper.isFastClick(tabLayout.getId(), 1000L, false)) {
                        if (DriveExperienceFragment.this.mVcuVm.getCarSpeed() > CarBaseConfig.getInstance().getEpsSpdThreshold() || Math.abs(DriveExperienceFragment.this.mChassisVm.getTorsionBarTorque()) > CarBaseConfig.getInstance().getEpsTorsionBarThreshold()) {
                            NotificationHelper.getInstance().showToast(R.string.eps_unable_with_running);
                            DriveExperienceFragment.this.vuiFeedbackClick(R.string.eps_unable_with_running, tabLayout);
                            StatisticUtils.sendStatistic(PageEnum.DRIVE_SETTING_PAGE, BtnEnum.DRIVE_SETTING_PAGE_EPS_BTN, Integer.valueOf(index + 1));
                            return true;
                        } else if ((CarBaseConfig.getInstance().isSupportNgp() && DriveExperienceFragment.this.mScuVm.isNgpRunning()) || ((CarBaseConfig.getInstance().isSupportLcc() && DriveExperienceFragment.this.mScuVm.getLccWorkSt() == ScuResponse.ON) || (CarBaseConfig.getInstance().isSupportCNgp() && DriveExperienceFragment.this.mScuVm.isCngpRunning()))) {
                            NotificationHelper.getInstance().showToast(R.string.eps_unable_with_xpilot);
                            DriveExperienceFragment.this.vuiFeedbackClick(R.string.eps_unable_with_xpilot, tabLayout);
                            StatisticUtils.sendStatistic(PageEnum.DRIVE_SETTING_PAGE, BtnEnum.DRIVE_SETTING_PAGE_EPS_BTN, Integer.valueOf(index + 1));
                            return true;
                        }
                    } else {
                        NotificationHelper.getInstance().showToast(R.string.operation_fast_tips);
                        DriveExperienceFragment.this.vuiFeedbackClick(R.string.operation_fast_tips, tabLayout);
                        return true;
                    }
                }
                return false;
            }

            @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
            public void onTabChangeEnd(XTabLayout xTabLayout, int index, boolean tabChange, boolean fromUser) {
                if (fromUser) {
                    if (index == 0) {
                        DriveExperienceFragment.this.mChassisVm.setSteeringEps(SteeringEpsMode.Soft);
                    } else if (index == 1) {
                        DriveExperienceFragment.this.mChassisVm.setSteeringEps(SteeringEpsMode.Standard);
                    } else if (index == 2) {
                        DriveExperienceFragment.this.mChassisVm.setSteeringEps(SteeringEpsMode.Sport);
                    }
                    StatisticUtils.sendStatistic(PageEnum.DRIVE_PAGE, BtnEnum.STEERING_ASSIS_BTN, Integer.valueOf(index + 1));
                }
            }
        });
        setLiveDataObserver(this.mChassisVm.getSteeringEpsData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$DriveExperienceFragment$U8lQFHYtUVfkiUrjINmhvE2Z1O8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DriveExperienceFragment.this.updateEpsTab((SteeringEpsMode) obj);
            }
        });
        updateEpsTab(this.mChassisVm.getSteeringEps(SteeringEpsMode.Standard));
    }

    public /* synthetic */ boolean lambda$initContentLayout$0$DriveExperienceFragment(View v, MotionEvent event) {
        if (event.getAction() != 0) {
            return false;
        }
        if (ClickHelper.isFastClick(R.id.drive_mode_tab, 1000L, false)) {
            NotificationHelper.getInstance().showToast(R.string.operation_fast_tips);
            return true;
        }
        if (!this.mXPedalSw.isChecked()) {
            showXPedalDialog();
        } else {
            this.mVcuVm.setDriveModeByUser(DriveMode.EcoPlusOff);
        }
        return true;
    }

    public /* synthetic */ boolean lambda$initContentLayout$1$DriveExperienceFragment(View v, MotionEvent event) {
        if (event.getAction() != 0) {
            return false;
        }
        if (ClickHelper.isFastClick(R.id.drive_mode_tab, 1000L, false)) {
            NotificationHelper.getInstance().showToast(R.string.operation_fast_tips);
            return true;
        }
        if (!this.mAntiCarSickSw.isChecked()) {
            showAntiSickDialog();
        } else {
            this.mVcuVm.setDriveModeByUser(DriveMode.ComfortOff);
        }
        return true;
    }

    public /* synthetic */ void lambda$initContentLayout$2$DriveExperienceFragment(Boolean mode) {
        if (mode != null) {
            updateExhibitionMode(mode.booleanValue());
        }
    }

    private void showDriveModeImg(DriveMode driveMode) {
        int i = AnonymousClass4.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$DriveMode[driveMode.ordinal()];
        int i2 = i != 1 ? i != 2 ? i != 3 ? i != 4 ? i != 5 ? 0 : R.drawable.img_anti_sick_mode : R.drawable.img_xpedal_mode : R.drawable.img_eco_mode : R.drawable.img_sport_mode : R.drawable.img_comfort_mode;
        if (i2 != 0) {
            try {
                FrameSequenceUtil.destroy(this.mDriveModeImg);
                FrameSequenceUtil.with(this.mDriveModeImg).resourceId(i2).decodingThreadId(0).loopBehavior(3).apply();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showXPedalDialog() {
        if (this.mXPedalDialog == null && getActivity() != null) {
            XDialog positiveButton = new XDialog(getActivity()).setTitle(R.string.drive_mode_eco_plus).setMessage(R.string.drive_mode_eco_plus_summary).setNegativeButton(getStringById(R.string.btn_cancel), (XDialogInterface.OnClickListener) null).setPositiveButton(getStringById(R.string.btn_confirm_open), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$DriveExperienceFragment$xp-63ltfp2VMDkacwfm0EX6wZbU
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i) {
                    DriveExperienceFragment.this.lambda$showXPedalDialog$3$DriveExperienceFragment(xDialog, i);
                }
            });
            this.mXPedalDialog = positiveButton;
            positiveButton.setSystemDialog(2048);
        }
        if (this.mXPedalDialog.isShowing()) {
            return;
        }
        this.mXPedalDialog.show();
        VuiManager.instance().initVuiDialog(this.mXPedalDialog, getContext(), VuiManager.SCENE_XPEDAL_SETTING);
    }

    public /* synthetic */ void lambda$showXPedalDialog$3$DriveExperienceFragment(XDialog xDialog, int i) {
        ClickHelper.setDefaultViewIdCurrentTime(System.currentTimeMillis());
        this.mVcuVm.setDriveModeByUser(DriveMode.EcoPlus);
    }

    private void showAntiSickDialog() {
        if (this.mAntiCarSickDialog == null && getActivity() != null) {
            XDialog positiveButton = new XDialog(getActivity()).setTitle(R.string.drive_mode_anti_carsick).setMessage(R.string.drive_mode_anti_carsick_summary).setNegativeButton(getStringById(R.string.btn_cancel), (XDialogInterface.OnClickListener) null).setPositiveButton(getStringById(R.string.btn_confirm_open), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$DriveExperienceFragment$zeLbhKTAOpbYHQL79KMj0emTM34
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i) {
                    DriveExperienceFragment.this.lambda$showAntiSickDialog$4$DriveExperienceFragment(xDialog, i);
                }
            });
            this.mAntiCarSickDialog = positiveButton;
            positiveButton.setSystemDialog(2048);
        }
        if (this.mAntiCarSickDialog.isShowing()) {
            return;
        }
        this.mAntiCarSickDialog.show();
        VuiManager.instance().initVuiDialog(this.mAntiCarSickDialog, getContext(), VuiManager.SCENE_ANTI_SICK_SETTING);
    }

    public /* synthetic */ void lambda$showAntiSickDialog$4$DriveExperienceFragment(XDialog xDialog, int i) {
        ClickHelper.setDefaultViewIdCurrentTime(System.currentTimeMillis());
        this.mVcuVm.setDriveModeByUser(DriveMode.Comfort);
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment, androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            return;
        }
        ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$DriveExperienceFragment$NwiPb-BVb7ru6De-6mYnoKB6l-c
            @Override // java.lang.Runnable
            public final void run() {
                DriveExperienceFragment.this.lambda$onHiddenChanged$5$DriveExperienceFragment();
            }
        });
    }

    public /* synthetic */ void lambda$onHiddenChanged$5$DriveExperienceFragment() {
        VcuViewModel vcuViewModel = this.mVcuVm;
        if (vcuViewModel == null || this.mDriveModeTab == null) {
            return;
        }
        updateDriveMode(vcuViewModel.getDriveModeData().getValue(), true);
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment, com.xiaopeng.carcontrol.view.fragment.VuiFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        try {
            FrameSequenceUtil.destroy(this.mDriveModeImg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateEpsTab(SteeringEpsMode eps) {
        if (eps != null) {
            updateXTabLayout(this.mEpsTab, eps.ordinal());
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

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDriveMode(DriveMode driveMode) {
        updateDriveMode(driveMode, false);
    }

    private void updateDriveMode(DriveMode driveMode, boolean forceFresh) {
        LogUtils.d(this.TAG, "updateDriveMode:driveMode: " + driveMode + ", CurrentDriveMode: " + this.mCurrentDriveMode + ", forceFresh: " + forceFresh);
        if (driveMode == null) {
            return;
        }
        if (this.mCurrentDriveMode != driveMode || forceFresh) {
            showDriveModeImg(driveMode);
        }
        if (DriveMode.Comfort == driveMode) {
            this.mDriveModeTabItem.setTextSub(getStringById(R.string.drive_mode_anti_carsick_tips));
            this.mEnergyGradeTabItem.setTextSub(getStringById(R.string.energy_anti_sick_desc));
            this.mAntiCarSickSw.setChecked(true);
            XSwitch xSwitch = this.mXPedalSw;
            if (xSwitch != null) {
                xSwitch.setChecked(false);
            }
            this.mDriveModeTab.selectedNoneTab(false, true);
            this.mEnergyGradeTab.selectedNoneTab(false, true);
        } else if (DriveMode.EcoPlus == driveMode) {
            this.mDriveModeTabItem.setTextSub(getStringById(R.string.drive_mode_xpdeal_tips));
            this.mEnergyGradeTabItem.setTextSub(getStringById(R.string.energy_xpedal_desc));
            this.mAntiCarSickSw.setChecked(false);
            XSwitch xSwitch2 = this.mXPedalSw;
            if (xSwitch2 != null) {
                xSwitch2.setChecked(true);
            }
            this.mDriveModeTab.selectedNoneTab(false, true);
            this.mEnergyGradeTab.selectedNoneTab(false, true);
        } else {
            this.mAntiCarSickSw.setChecked(false);
            XSwitch xSwitch3 = this.mXPedalSw;
            if (xSwitch3 != null) {
                xSwitch3.setChecked(false);
            }
            int i = AnonymousClass4.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$DriveMode[driveMode.ordinal()];
            if (i == 1) {
                this.mDriveModeTabItem.setTextSub(getStringById(R.string.drive_mode_standard_summary));
                updateXTabLayout(this.mDriveModeTab, 0);
            } else if (i == 2) {
                this.mDriveModeTabItem.setTextSub(getStringById(R.string.drive_mode_sport_summary));
                updateXTabLayout(this.mDriveModeTab, 1);
            } else if (i == 3) {
                this.mDriveModeTabItem.setTextSub(getStringById(R.string.drive_mode_eco_summary));
                updateXTabLayout(this.mDriveModeTab, 2);
            }
            if (((this.mCurrentDriveMode == DriveMode.Comfort || this.mCurrentDriveMode == DriveMode.EcoPlus) && driveMode != DriveMode.Comfort && driveMode != DriveMode.EcoPlus) || this.mEnergyGradeTab.getSelectedTabIndex() == -1) {
                updateEnergyGrade(this.mVcuVm.getEnergyRecycle());
            }
        }
        this.mCurrentDriveMode = driveMode;
    }

    private void updateExhibitionMode(boolean exhibitionMode) {
        this.mDriveModeTab.setEnabled(!exhibitionMode);
        if (exhibitionMode) {
            return;
        }
        updateDriveMode(this.mVcuVm.getDriveModeData().getValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setEnergyGrade(int index) {
        if (index == 0) {
            this.mVcuVm.setEnergyRecycleGrade(EnergyRecycleGrade.Low);
        } else if (index == 1) {
            this.mVcuVm.setEnergyRecycleGrade(EnergyRecycleGrade.Middle);
        } else if (index != 2) {
        } else {
            this.mVcuVm.setEnergyRecycleGrade(EnergyRecycleGrade.High);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateEnergyGrade(EnergyRecycleGrade grade) {
        if (grade == null) {
            return;
        }
        DriveMode driveModeValue = this.mVcuVm.getDriveModeValue();
        LogUtils.d(this.TAG, "updateEnergyGrade, grade: " + grade + ", currentDriveMode: " + driveModeValue);
        if (driveModeValue == DriveMode.Comfort || driveModeValue == DriveMode.EcoPlus) {
            return;
        }
        int i = AnonymousClass4.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$EnergyRecycleGrade[grade.ordinal()];
        if (i == 1) {
            this.mEnergyGradeTabItem.setTextSub(getStringById(R.string.energy_low_desc));
            updateXTabLayout(this.mEnergyGradeTab, 0);
        } else if (i == 2) {
            this.mEnergyGradeTabItem.setTextSub(getStringById(R.string.energy_middle_desc));
            updateXTabLayout(this.mEnergyGradeTab, 1);
        } else if (i != 3) {
        } else {
            this.mEnergyGradeTabItem.setTextSub(getStringById(R.string.energy_high_desc));
            updateXTabLayout(this.mEnergyGradeTab, 2);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.carcontrol.view.fragment.DriveExperienceFragment$4  reason: invalid class name */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$DriveMode;
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$EnergyRecycleGrade;

        static {
            int[] iArr = new int[EnergyRecycleGrade.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$EnergyRecycleGrade = iArr;
            try {
                iArr[EnergyRecycleGrade.Low.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$EnergyRecycleGrade[EnergyRecycleGrade.Middle.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$EnergyRecycleGrade[EnergyRecycleGrade.High.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            int[] iArr2 = new int[DriveMode.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$DriveMode = iArr2;
            try {
                iArr2[DriveMode.Normal.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$DriveMode[DriveMode.Sport.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$DriveMode[DriveMode.Eco.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$DriveMode[DriveMode.EcoPlus.ordinal()] = 4;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$DriveMode[DriveMode.Comfort.ordinal()] = 5;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.VuiFragment, com.xiaopeng.vui.commons.IVuiSceneListener
    public boolean onInterceptVuiEvent(View view, VuiEvent vuiEvent) {
        LogUtils.i(this.TAG, "onInterceptVuiEvent, vuiEvent： " + vuiEvent.getHitVuiElement(), false);
        if (R.id.x_pedal_sw_item == ((Integer) view.getTag()).intValue()) {
            if (view != null && (view instanceof XSwitch)) {
                if (!((XSwitch) view).isChecked()) {
                    showXPedalDialog();
                } else {
                    this.mVcuVm.setDriveModeByUser(DriveMode.EcoPlusOff);
                }
                return true;
            }
        } else if (R.id.anti_carsick_sw_item == ((Integer) view.getTag()).intValue()) {
            if (view instanceof XSwitch) {
                if (!((XSwitch) view).isChecked()) {
                    showAntiSickDialog();
                } else {
                    this.mVcuVm.setDriveModeByUser(DriveMode.ComfortOff);
                }
            }
            return true;
        }
        return super.onInterceptVuiEvent(view, vuiEvent);
    }
}
