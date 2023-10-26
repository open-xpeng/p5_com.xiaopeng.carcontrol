package com.xiaopeng.carcontrol.view.fragment.impl;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import com.xiaopeng.carcontrol.BusinessConstant;
import com.xiaopeng.carcontrol.bean.MaintainInfo;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.helper.ClickHelper;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.speech.VuiManager;
import com.xiaopeng.carcontrol.statistic.BtnEnum;
import com.xiaopeng.carcontrol.statistic.PageEnum;
import com.xiaopeng.carcontrol.statistic.StatisticUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.view.fragment.BaseFragment;
import com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyViewModel;
import com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel;
import com.xiaopeng.carcontrol.viewmodel.chassis.ChassisViewModel;
import com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel;
import com.xiaopeng.carcontrol.viewmodel.chassis.TpmsCalibrationStatus;
import com.xiaopeng.carcontrol.viewmodel.chassis.TpmsWarningState;
import com.xiaopeng.carcontrol.viewmodel.locale.ILocaleViewModel;
import com.xiaopeng.carcontrol.viewmodel.locale.LocaleViewModel;
import com.xiaopeng.carcontrol.viewmodel.meter.IMeterViewModel;
import com.xiaopeng.carcontrol.viewmodel.meter.MeterViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.GearLevel;
import com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.VcuViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.XSportDriveMode;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.speech.vui.utils.VuiUtils;
import com.xiaopeng.vui.commons.model.VuiEvent;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XFrameLayout;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XListSingle;
import com.xiaopeng.xui.widget.XSwitch;
import com.xiaopeng.xui.widget.XTextView;
import com.xiaopeng.xui.widget.toggle.XTextToggle;
import com.xiaopeng.xui.widget.toggle.XToggleLayout;

/* loaded from: classes2.dex */
public class SituationFragment extends BaseFragment {
    private XButton mCallBtn1;
    private XButton mCallBtn2;
    private CarBodyViewModel mCarBodyViewModel;
    private ChassisViewModel mChassisVm;
    private XTextView mFlValueText;
    private XTextView mFlValueUnit;
    private XTextView mFrValueText;
    private XTextView mFrValueUnit;
    private XImageView mLeftFrontImg;
    private XImageView mLeftRearImg;
    private LocaleViewModel mLocaleViewModel;
    private MeterViewModel mMeterVm;
    private XTextView mRescueTv;
    private XImageView mRightFrontImg;
    private XImageView mRightRearImg;
    private XTextView mRlValueText;
    private XTextView mRlValueUnit;
    private XTextView mRrValueText;
    private XTextView mRrValueUnit;
    private XTextToggle mTpmsBtn;
    private VcuViewModel mVcuViewModel;
    private XSwitch rearWiperRepairSw;
    private XSwitch wiperRepairSw;
    private boolean mClickCalibrate = false;
    private boolean canUpdateWiperSw = true;
    private boolean canUpdateRearWiperSw = true;
    private boolean mRegisterBusiness = false;

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment
    protected boolean needPreLoadLayout() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment
    protected void initViewModels() {
        this.mChassisVm = (ChassisViewModel) getViewModel(IChassisViewModel.class);
        this.mMeterVm = (MeterViewModel) getViewModel(IMeterViewModel.class);
        this.mVcuViewModel = (VcuViewModel) getViewModel(IVcuViewModel.class);
        this.mCarBodyViewModel = (CarBodyViewModel) getViewModel(ICarBodyViewModel.class);
        this.mLocaleViewModel = (LocaleViewModel) getViewModel(ILocaleViewModel.class);
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.VuiFragment
    protected int getRootLayoutId() {
        return R.layout.fragment_situation;
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment
    protected int getPreLoadLayoutId() {
        return R.layout.fragment_situation_stub;
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment
    protected void initViews() {
        initContentLayout();
    }

    private void initContentLayout() {
        initTyre(this.mPreloadLayout);
        initMileage(this.mPreloadLayout);
        initMaintenanceInfo(this.mPreloadLayout);
        initRescueCall(this.mPreloadLayout);
        initRepairMode(this.mPreloadLayout);
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment, com.xiaopeng.carcontrol.view.fragment.VuiFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        this.mRegisterBusiness = false;
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment
    protected void resumeViews() {
        ChassisViewModel chassisViewModel;
        if (!BaseFeatureOption.getInstance().isSignalRegisterDynamically() || this.mRegisterBusiness || (chassisViewModel = this.mChassisVm) == null || this.mMeterVm == null) {
            return;
        }
        chassisViewModel.registerBusiness(BusinessConstant.KEY_TIRE_CONDITION);
        this.mMeterVm.registerBusiness(BusinessConstant.KEY_MILEAGE_DATA);
        this.mRegisterBusiness = true;
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment, com.xiaopeng.carcontrol.view.fragment.VuiFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        if (BaseFeatureOption.getInstance().isSignalRegisterDynamically() && this.mRegisterBusiness) {
            this.mChassisVm.unregisterBusiness(BusinessConstant.KEY_TIRE_CONDITION);
            this.mMeterVm.unregisterBusiness(BusinessConstant.KEY_MILEAGE_DATA);
            this.mRegisterBusiness = false;
        }
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment, androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean hidden) {
        MeterViewModel meterViewModel;
        super.onHiddenChanged(hidden);
        if (hidden || !CarBaseConfig.getInstance().isSupportMaintenanceInfo() || (meterViewModel = this.mMeterVm) == null) {
            return;
        }
        meterViewModel.requestMaintainData();
    }

    private void initTyre(View view) {
        this.mFlValueText = (XTextView) view.findViewById(R.id.tyre_fl_value);
        this.mFrValueText = (XTextView) view.findViewById(R.id.tyre_fr_value);
        this.mRlValueText = (XTextView) view.findViewById(R.id.tyre_rl_value);
        this.mRrValueText = (XTextView) view.findViewById(R.id.tyre_rr_value);
        this.mLeftFrontImg = (XImageView) view.findViewById(R.id.left_front_image);
        this.mLeftRearImg = (XImageView) view.findViewById(R.id.left_rear_image);
        this.mRightFrontImg = (XImageView) view.findViewById(R.id.right_front_image);
        this.mRightRearImg = (XImageView) view.findViewById(R.id.right_rear_image);
        this.mFlValueUnit = (XTextView) view.findViewById(R.id.tyre_fl_unit);
        this.mFrValueUnit = (XTextView) view.findViewById(R.id.tyre_fr_unit);
        this.mRlValueUnit = (XTextView) view.findViewById(R.id.tyre_rl_unit);
        this.mRrValueUnit = (XTextView) view.findViewById(R.id.tyre_rr_unit);
        setLiveDataObserver(this.mChassisVm.getTyrePressureData(1), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SituationFragment$D1L_tOh6nIoxYDlm997vNR-9nGs
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SituationFragment.this.lambda$initTyre$0$SituationFragment((String) obj);
            }
        });
        if (BaseFeatureOption.getInstance().isSupportTpmsWarning()) {
            setLiveDataObserver(this.mChassisVm.getTpmsWarningData(1), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SituationFragment$J7T2eV-azS2azDiCs5VRphO1ryo
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SituationFragment.this.lambda$initTyre$1$SituationFragment((TpmsWarningState) obj);
                }
            });
        }
        setLiveDataObserver(this.mChassisVm.getTyrePressureData(2), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SituationFragment$dn4VL7Azww56Qsy1BxZ40_aTSIU
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SituationFragment.this.lambda$initTyre$2$SituationFragment((String) obj);
            }
        });
        if (BaseFeatureOption.getInstance().isSupportTpmsWarning()) {
            setLiveDataObserver(this.mChassisVm.getTpmsWarningData(2), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SituationFragment$-gnLzJeyYgjTUzddK7RidKZMAVo
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SituationFragment.this.lambda$initTyre$3$SituationFragment((TpmsWarningState) obj);
                }
            });
        }
        setLiveDataObserver(this.mChassisVm.getTyrePressureData(3), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SituationFragment$IWAmuICp6uz7YnxIZsAp3MCrky0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SituationFragment.this.lambda$initTyre$4$SituationFragment((String) obj);
            }
        });
        if (BaseFeatureOption.getInstance().isSupportTpmsWarning()) {
            setLiveDataObserver(this.mChassisVm.getTpmsWarningData(3), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SituationFragment$L0nGHuiqrQvaTmAKXLHJ-BIZkwo
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SituationFragment.this.lambda$initTyre$5$SituationFragment((TpmsWarningState) obj);
                }
            });
        }
        setLiveDataObserver(this.mChassisVm.getTyrePressureData(4), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SituationFragment$U4l9U_vnB3nBlqtaIE_v7A_vMpQ
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SituationFragment.this.lambda$initTyre$6$SituationFragment((String) obj);
            }
        });
        if (BaseFeatureOption.getInstance().isSupportTpmsWarning()) {
            setLiveDataObserver(this.mChassisVm.getTpmsWarningData(4), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SituationFragment$9x6kmuZOCzAPHQk0Jg159bMnbYI
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SituationFragment.this.lambda$initTyre$7$SituationFragment((TpmsWarningState) obj);
                }
            });
            TpmsWarningState[] tpmsWarningStates = this.mChassisVm.getTpmsWarningStates();
            if (tpmsWarningStates != null) {
                updateTyreWarningState(1, tpmsWarningStates[0]);
                updateTyreWarningState(2, tpmsWarningStates[1]);
                updateTyreWarningState(3, tpmsWarningStates[2]);
                updateTyreWarningState(4, tpmsWarningStates[3]);
            }
        }
        this.mTpmsBtn = (XTextToggle) view.findViewById(R.id.tyre_calibrate_btn);
        View findViewById = view.findViewById(R.id.tyre_calibrate_summary);
        if (CarBaseConfig.getInstance().isSupportTpmsCalibrate()) {
            if (CarBaseConfig.getInstance().isSupportManualTpmsCalibrate()) {
                VuiUtils.addHasFeedbackProp(this.mTpmsBtn);
                this.mTpmsBtn.setOnCheckedChangeListener(new XToggleLayout.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.SituationFragment.1
                    @Override // com.xiaopeng.xui.widget.toggle.XToggleLayout.OnCheckedChangeListener
                    public void onCheckedChanged(XToggleLayout xToggleLayout, boolean b) {
                    }

                    @Override // com.xiaopeng.xui.widget.toggle.XToggleLayout.OnCheckedChangeListener
                    public boolean onInterceptClickCheck(XToggleLayout xToggleLayout) {
                        SituationFragment.this.mClickCalibrate = true;
                        SituationFragment.this.mTpmsBtn.setLoading(true);
                        SituationFragment.this.mChassisVm.calibrateTyrePressure();
                        StatisticUtils.sendStatistic(PageEnum.STATUS_E28_PAGE, BtnEnum.STATUS_E28_PAGE_TYRE_BTN, new Object[0]);
                        return true;
                    }
                });
                updateTpmsUI(this.mChassisVm.getTpmsCalibrationStatus());
            }
            if (CarBaseConfig.getInstance().isSupportAutoTpmsCalibrate()) {
                this.mTpmsBtn.setVisibility(8);
            }
        } else {
            this.mTpmsBtn.setVisibility(8);
            if (findViewById != null) {
                findViewById.setVisibility(8);
            }
        }
        setLiveDataObserver(this.mChassisVm.getTpmsCalibrationStatusData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SituationFragment$-TvPuE0ITw3mXnOPYUcUZKwkaDA
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SituationFragment.this.updateTpmsUI((TpmsCalibrationStatus) obj);
            }
        });
    }

    public /* synthetic */ void lambda$initTyre$0$SituationFragment(String value) {
        if (value != null) {
            this.mFlValueText.setText(value);
            XTextView xTextView = this.mFlValueUnit;
            if (xTextView != null) {
                xTextView.setVisibility(value.equals(ChassisViewModel.TPMS_DEFAULT_PRESSURE) ? 8 : 0);
            }
        }
    }

    public /* synthetic */ void lambda$initTyre$1$SituationFragment(TpmsWarningState tpmsWarningState) {
        if (tpmsWarningState != null) {
            updateTyreWarningState(1, tpmsWarningState);
        }
    }

    public /* synthetic */ void lambda$initTyre$2$SituationFragment(String value) {
        if (value != null) {
            this.mFrValueText.setText(value);
            XTextView xTextView = this.mFrValueUnit;
            if (xTextView != null) {
                xTextView.setVisibility(value.equals(ChassisViewModel.TPMS_DEFAULT_PRESSURE) ? 8 : 0);
            }
        }
    }

    public /* synthetic */ void lambda$initTyre$3$SituationFragment(TpmsWarningState tpmsWarningState) {
        if (tpmsWarningState != null) {
            updateTyreWarningState(2, tpmsWarningState);
        }
    }

    public /* synthetic */ void lambda$initTyre$4$SituationFragment(String value) {
        if (value != null) {
            this.mRlValueText.setText(value);
            XTextView xTextView = this.mRlValueUnit;
            if (xTextView != null) {
                xTextView.setVisibility(value.equals(ChassisViewModel.TPMS_DEFAULT_PRESSURE) ? 8 : 0);
            }
        }
    }

    public /* synthetic */ void lambda$initTyre$5$SituationFragment(TpmsWarningState tpmsWarningState) {
        if (tpmsWarningState != null) {
            updateTyreWarningState(3, tpmsWarningState);
        }
    }

    public /* synthetic */ void lambda$initTyre$6$SituationFragment(String value) {
        if (value != null) {
            this.mRrValueText.setText(value);
            XTextView xTextView = this.mRrValueUnit;
            if (xTextView != null) {
                xTextView.setVisibility(value.equals(ChassisViewModel.TPMS_DEFAULT_PRESSURE) ? 8 : 0);
            }
        }
    }

    public /* synthetic */ void lambda$initTyre$7$SituationFragment(TpmsWarningState tpmsWarningState) {
        if (tpmsWarningState != null) {
            updateTyreWarningState(4, tpmsWarningState);
        }
    }

    private void initMileage(View view) {
        final XTextView xTextView = (XTextView) view.findViewById(R.id.tv_xls_total_mile);
        if (xTextView != null) {
            setLiveDataObserver(this.mMeterVm.getTotalMileageData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SituationFragment$rT34JDjiqEAHPw7UuU6AK40-L_8
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SituationFragment.this.lambda$initMileage$8$SituationFragment(xTextView, (String) obj);
                }
            });
            xTextView.setText(formatMileageText(this.mMeterVm.getMileageTotal()));
        }
        final XTextView xTextView2 = (XTextView) view.findViewById(R.id.tv_xls_since_charge_mile);
        if (xTextView2 != null) {
            setLiveDataObserver(this.mMeterVm.getMileageDataSinceCharge(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SituationFragment$TgrrIQNp8HVrBlXcIMVT1dDkFbY
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SituationFragment.this.lambda$initMileage$9$SituationFragment(xTextView2, (String) obj);
                }
            });
            xTextView2.setText(formatMileageText(this.mMeterVm.getMileageSinceLastCharge()));
        }
        final XTextView xTextView3 = (XTextView) view.findViewById(R.id.tv_xls_since_start_mile);
        if (xTextView3 != null) {
            setLiveDataObserver(this.mMeterVm.getMileageDataSinceStart(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SituationFragment$f6k3xQVvhdIS03PjuyqcsYcOchs
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SituationFragment.this.lambda$initMileage$10$SituationFragment(xTextView3, (String) obj);
                }
            });
            xTextView3.setText(formatMileageText(this.mMeterVm.getMileageSinceStartUp()));
        }
        XListSingle xListSingle = (XListSingle) view.findViewById(R.id.xls_mileage_a);
        final XTextView xTextView4 = (XTextView) xListSingle.findViewById(R.id.tv_ab_mileage);
        if (xTextView4 != null) {
            setLiveDataObserver(this.mMeterVm.getMileageAData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SituationFragment$ahDBTdB-MW02fJbIXBJjUe-oWWk
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SituationFragment.this.lambda$initMileage$11$SituationFragment(xTextView4, (String) obj);
                }
            });
            xTextView4.setText(formatMileageText(this.mMeterVm.getMileageA()));
        }
        XButton xButton = (XButton) xListSingle.findViewById(R.id.rest_ab_mileage_btn);
        xButton.setVuiLabel(getStringById(R.string.drive_mileage_reset_a_vui_label));
        xButton.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SituationFragment$LC4HbZvHoxbsxhSGlmi3jswjXkw
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                SituationFragment.this.lambda$initMileage$12$SituationFragment(view2);
            }
        });
        XListSingle xListSingle2 = (XListSingle) view.findViewById(R.id.xls_mileage_b);
        final XTextView xTextView5 = (XTextView) xListSingle2.findViewById(R.id.tv_ab_mileage);
        if (xTextView5 != null) {
            setLiveDataObserver(this.mMeterVm.getMileageBData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SituationFragment$Ba0ssyanwlE9ZJKCzWJp9LtFM_I
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SituationFragment.this.lambda$initMileage$13$SituationFragment(xTextView5, (String) obj);
                }
            });
            xTextView5.setText(formatMileageText(this.mMeterVm.getMileageB()));
        }
        XButton xButton2 = (XButton) xListSingle2.findViewById(R.id.rest_ab_mileage_btn);
        xButton2.setVuiLabel(getStringById(R.string.drive_mileage_reset_b_vui_label));
        xButton2.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SituationFragment$eXIQQA65GP6yfnYFdDdo3fqwX9c
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                SituationFragment.this.lambda$initMileage$14$SituationFragment(view2);
            }
        });
        setVuiLabelUnSupportText(xButton, xButton2);
    }

    public /* synthetic */ void lambda$initMileage$8$SituationFragment(final XTextView mTototalMileTv, String mileage) {
        if (mileage != null) {
            mTototalMileTv.setText(formatMileageText(mileage));
        }
    }

    public /* synthetic */ void lambda$initMileage$9$SituationFragment(final XTextView mChargeMileTv, String mileage) {
        if (mileage != null) {
            mChargeMileTv.setText(formatMileageText(mileage));
        }
    }

    public /* synthetic */ void lambda$initMileage$10$SituationFragment(final XTextView mStartMileTv, String mileage) {
        if (mileage != null) {
            mStartMileTv.setText(formatMileageText(mileage));
        }
    }

    public /* synthetic */ void lambda$initMileage$11$SituationFragment(final XTextView mMileageATv, String mileage) {
        if (mileage != null) {
            mMileageATv.setText(formatMileageText(mileage));
        }
    }

    public /* synthetic */ void lambda$initMileage$12$SituationFragment(View v) {
        this.mMeterVm.resetMeterMileageA();
        StatisticUtils.sendStatistic(PageEnum.STATUS_E28_PAGE, BtnEnum.STATUS_E28_PAGE_RESET_A_BTN, new Object[0]);
    }

    public /* synthetic */ void lambda$initMileage$13$SituationFragment(final XTextView mileageBTv, String mileage) {
        if (mileage != null) {
            mileageBTv.setText(formatMileageText(mileage));
        }
    }

    public /* synthetic */ void lambda$initMileage$14$SituationFragment(View v) {
        this.mMeterVm.resetMeterMileageB();
        StatisticUtils.sendStatistic(PageEnum.STATUS_E28_PAGE, BtnEnum.STATUS_E28_PAGE_RESET_B_BTN, new Object[0]);
    }

    private void initRescueCall(View view) {
        MeterViewModel meterViewModel;
        if (BaseFeatureOption.getInstance().isSupportECall() && (meterViewModel = this.mMeterVm) != null && meterViewModel.isEcallAvailable()) {
            ((XTextView) view.findViewById(R.id.ecall_tv_title)).setVisibility(0);
            XButton xButton = (XButton) view.findViewById(R.id.btn_ecall);
            xButton.setVisibility(0);
            xButton.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SituationFragment$ndWYxE64GSxcKc7Pt3tb5OqA-yk
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    SituationFragment.this.lambda$initRescueCall$15$SituationFragment(view2);
                }
            });
            XListSingle xListSingle = (XListSingle) view.findViewById(R.id.show_ecall_sw);
            xListSingle.setVisibility(0);
            XSwitch xSwitch = (XSwitch) xListSingle.findViewById(R.id.x_list_sw);
            xSwitch.setVuiLabel(getStringById(R.string.ecall_btn_title));
            xSwitch.setChecked(this.mMeterVm.shouldShowEcallOnStatus());
            xSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SituationFragment$3XQRHEVk8sjAf002BnX7sTvba70
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    SituationFragment.this.lambda$initRescueCall$16$SituationFragment(compoundButton, z);
                }
            });
            setLiveDataObserver(this.mMeterVm.getEcallAvailableData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SituationFragment$i5v85fO3cyNaqf1y7Y8BLY9VCJM
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SituationFragment.this.lambda$initRescueCall$17$SituationFragment((Boolean) obj);
                }
            });
        }
        this.mRescueTv = (XTextView) view.findViewById(R.id.rescue_tv_title);
        this.mCallBtn1 = (XButton) view.findViewById(R.id.btn_rescue_phone_1);
        this.mCallBtn2 = (XButton) view.findViewById(R.id.btn_rescue_phone_2);
        this.mCallBtn1.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SituationFragment$G5OITGaejIyvMStn3fS49WkLHtk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                SituationFragment.this.lambda$initRescueCall$18$SituationFragment(view2);
            }
        });
        this.mCallBtn2.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SituationFragment$AXou9QKP2BkeQMpa8VeNfnjHp6Q
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                SituationFragment.this.lambda$initRescueCall$19$SituationFragment(view2);
            }
        });
        setLiveDataObserver(this.mLocaleViewModel.getRecuseCallsData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SituationFragment$Et4bfKCWQ_LHTqO8RIQKXMkM6Z8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SituationFragment.this.lambda$initRescueCall$20$SituationFragment((String[]) obj);
            }
        });
        lambda$initRescueCall$20$SituationFragment(this.mLocaleViewModel.getRescueCalls());
    }

    public /* synthetic */ void lambda$initRescueCall$15$SituationFragment(View v) {
        startECallProcedure();
    }

    public /* synthetic */ void lambda$initRescueCall$16$SituationFragment(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed() || isVuiAction(buttonView)) {
            this.mMeterVm.setShowEcallOnStatus(isChecked);
        }
    }

    public /* synthetic */ void lambda$initRescueCall$17$SituationFragment(Boolean available) {
        if (available != null) {
            showOrHideEcall(available.booleanValue());
        }
    }

    public /* synthetic */ void lambda$initRescueCall$18$SituationFragment(View v) {
        callRescue(this.mLocaleViewModel.getRescueCalls()[0]);
        StatisticUtils.sendStatistic(PageEnum.STATUS_E28_PAGE, BtnEnum.STATUS_E28_PAGE_PHONE_BTN, new Object[0]);
    }

    public /* synthetic */ void lambda$initRescueCall$19$SituationFragment(View v) {
        callRescue(this.mLocaleViewModel.getRescueCalls()[1]);
        StatisticUtils.sendStatistic(PageEnum.STATUS_E28_PAGE, BtnEnum.STATUS_E28_PAGE_PHONE_BTN_2, new Object[0]);
    }

    private void showOrHideEcall(boolean available) {
        if (this.mPreloadLayout != null) {
            View findViewById = this.mPreloadLayout.findViewById(R.id.ecall_tv_title);
            if (findViewById != null) {
                findViewById.setVisibility(available ? 0 : 8);
            }
            View findViewById2 = this.mPreloadLayout.findViewById(R.id.btn_ecall);
            if (findViewById2 != null) {
                findViewById2.setVisibility(available ? 0 : 8);
            }
            View findViewById3 = this.mPreloadLayout.findViewById(R.id.show_ecall_sw);
            if (findViewById3 != null) {
                findViewById3.setVisibility(available ? 0 : 8);
            }
        }
    }

    private String formatMileageText(String rawMileage) {
        return getStringById(R.string.drive_mileage_value, rawMileage);
    }

    private void initMaintenanceInfo(View view) {
        if (CarBaseConfig.getInstance().isSupportMaintenanceInfo()) {
            view.findViewById(R.id.car_maintenance_title_tv).setVisibility(0);
            XFrameLayout xFrameLayout = (XFrameLayout) view.findViewById(R.id.xls_last_mileage);
            xFrameLayout.setVisibility(0);
            final XTextView xTextView = (XTextView) xFrameLayout.findViewById(R.id.tv_mileage);
            XFrameLayout xFrameLayout2 = (XFrameLayout) view.findViewById(R.id.xls_last_date);
            xFrameLayout2.setVisibility(0);
            final XTextView xTextView2 = (XTextView) xFrameLayout2.findViewById(R.id.tv_mileage);
            setLiveDataObserver(this.mMeterVm.getLastMaintenanceData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SituationFragment$rQTnr2Ux4hhMRAk3SDBTCsP6CJ4
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SituationFragment.lambda$initMaintenanceInfo$21(XTextView.this, xTextView2, (MaintainInfo) obj);
                }
            });
            XFrameLayout xFrameLayout3 = (XFrameLayout) view.findViewById(R.id.xls_next_mileage);
            xFrameLayout3.setVisibility(0);
            final XTextView xTextView3 = (XTextView) xFrameLayout3.findViewById(R.id.tv_mileage);
            XFrameLayout xFrameLayout4 = (XFrameLayout) view.findViewById(R.id.xls_next_date);
            xFrameLayout4.setVisibility(0);
            final XTextView xTextView4 = (XTextView) xFrameLayout4.findViewById(R.id.tv_mileage);
            setLiveDataObserver(this.mMeterVm.getNextMaintenanceData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SituationFragment$8yz6TQ6u-WX5vslaJcTbJUq4PJA
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SituationFragment.lambda$initMaintenanceInfo$22(XTextView.this, xTextView4, (MaintainInfo) obj);
                }
            });
            this.mMeterVm.requestMaintainData();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$initMaintenanceInfo$21(final XTextView mTvLastMileage, final XTextView mTvLastDate, MaintainInfo info) {
        if (info != null) {
            if (info.mMileage != null) {
                mTvLastMileage.setText(ResUtils.getString(R.string.maintenance_mileage_format, info.mMileage));
            } else {
                mTvLastMileage.setText(R.string.maintenance_mileage_invalid);
            }
            if (info.mDate != null) {
                mTvLastDate.setText(info.mDate);
                return;
            } else {
                mTvLastDate.setText(R.string.maintenance_date_invalid);
                return;
            }
        }
        mTvLastMileage.setText(R.string.maintenance_mileage_invalid);
        mTvLastDate.setText(R.string.maintenance_date_invalid);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$initMaintenanceInfo$22(final XTextView mTvNextMileage, final XTextView mTvNextDate, MaintainInfo info) {
        if (info != null) {
            if (info.mMileage != null) {
                mTvNextMileage.setText(ResUtils.getString(R.string.maintenance_mileage_format, info.mMileage));
            } else {
                mTvNextMileage.setText(R.string.maintenance_mileage_invalid);
            }
            if (info.mDate != null) {
                mTvNextDate.setText(info.mDate);
                return;
            } else {
                mTvNextDate.setText(R.string.maintenance_date_invalid);
                return;
            }
        }
        mTvNextMileage.setText(R.string.maintenance_mileage_invalid);
        mTvNextDate.setText(R.string.maintenance_date_invalid);
    }

    private void initRepairMode(View view) {
        if (CarBaseConfig.getInstance().isSupportWiperRepair()) {
            view.findViewById(R.id.wiper_repair_title).setVisibility(0);
            view.findViewById(R.id.wiper_repair_sw).setVisibility(0);
            XSwitch xSwitch = (XSwitch) view.findViewById(R.id.wiper_repair_sw).findViewById(R.id.x_list_sw);
            this.wiperRepairSw = xSwitch;
            xSwitch.setVuiLabel(getStringById(R.string.wiper_repair_mode));
            VuiUtils.addHasFeedbackProp(this.wiperRepairSw);
            this.wiperRepairSw.setChecked(this.mCarBodyViewModel.getWiperRepairMode());
            this.wiperRepairSw.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SituationFragment$Vo5_LiBMu3KpedhgygK4CyefF5A
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view2, MotionEvent motionEvent) {
                    return SituationFragment.this.lambda$initRepairMode$23$SituationFragment(view2, motionEvent);
                }
            });
            this.wiperRepairSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SituationFragment$cxKKm0KWssKu8sPgdRqo5FbgYjQ
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    SituationFragment.this.lambda$initRepairMode$24$SituationFragment(compoundButton, z);
                }
            });
            setLiveDataObserver(this.mCarBodyViewModel.getWiperRepairModeData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SituationFragment$nR7Oev1T3xtnFrFvWawT84GecsM
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SituationFragment.this.lambda$initRepairMode$25$SituationFragment((Boolean) obj);
                }
            });
            if (CarBaseConfig.getInstance().isSupportRearWiper()) {
                view.findViewById(R.id.rear_wiper_repair_sw).setVisibility(0);
                XSwitch xSwitch2 = (XSwitch) view.findViewById(R.id.rear_wiper_repair_sw).findViewById(R.id.x_list_sw);
                this.rearWiperRepairSw = xSwitch2;
                xSwitch2.setChecked(this.mCarBodyViewModel.getRearWiperRepairMode());
                this.rearWiperRepairSw.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SituationFragment$Aa7GaWPzbpm9-UX9gEpDKR9cwpY
                    @Override // android.view.View.OnTouchListener
                    public final boolean onTouch(View view2, MotionEvent motionEvent) {
                        return SituationFragment.this.lambda$initRepairMode$26$SituationFragment(view2, motionEvent);
                    }
                });
                this.rearWiperRepairSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SituationFragment$Hp5-A6udQs6HyRJLg_UCy50zMWQ
                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
                    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                        SituationFragment.this.lambda$initRepairMode$27$SituationFragment(compoundButton, z);
                    }
                });
                setLiveDataObserver(this.mCarBodyViewModel.getRearWiperRepairModeData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SituationFragment$L0jNQwtQkGyHgMOqW0I3X5tDFUY
                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        SituationFragment.this.lambda$initRepairMode$28$SituationFragment((Boolean) obj);
                    }
                });
            }
        }
        if (CarBaseConfig.getInstance().isSupportAirSuspension()) {
            view.findViewById(R.id.wiper_repair_sw).setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.sub_item_top_bg));
            view.findViewById(R.id.as_repair_sw).setVisibility(0);
            final XSwitch xSwitch3 = (XSwitch) view.findViewById(R.id.as_repair_sw).findViewById(R.id.x_list_sw);
            xSwitch3.setVuiLabel(getStringById(R.string.as_repair_title));
            xSwitch3.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SituationFragment$LtDu-BA8r32Mqg1wFAy3vBJazZc
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view2, MotionEvent motionEvent) {
                    return SituationFragment.this.lambda$initRepairMode$29$SituationFragment(xSwitch3, view2, motionEvent);
                }
            });
            xSwitch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SituationFragment$D5ekdTpRSUHaU5TYrP6uHXEFKxk
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    SituationFragment.this.lambda$initRepairMode$30$SituationFragment(compoundButton, z);
                }
            });
            setLiveDataObserver(this.mChassisVm.getAsRepairModeData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SituationFragment$DPT5CIH-acz1jBKbsAvbEZMaqJ8
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SituationFragment.lambda$initRepairMode$31(XSwitch.this, (Boolean) obj);
                }
            });
            xSwitch3.setChecked(this.mChassisVm.getAirSuspensionRepairMode());
        }
    }

    public /* synthetic */ boolean lambda$initRepairMode$23$SituationFragment(View v, MotionEvent event) {
        if (event.getAction() == 0) {
            LogUtils.d(this.TAG, "onTouchEvent:canUpdateWiperSw=" + this.canUpdateWiperSw);
            if (ClickHelper.isFastClick(R.id.wiper_repair_sw, 1200L, true)) {
                return true;
            }
            if (CarBaseConfig.getInstance().isSupportWiperFault() && this.mCarBodyViewModel.isWiperFault()) {
                NotificationHelper.getInstance().showToast(R.string.wiper_fault_tip);
                return true;
            } else if (!this.canUpdateWiperSw || this.wiperRepairSw.isChecked()) {
                return false;
            } else {
                if (this.mVcuViewModel.getGearLevelValue() == GearLevel.P && this.mCarBodyViewModel.isWiperSpeedSwitchOff()) {
                    return false;
                }
                NotificationHelper.getInstance().showToast(R.string.wiper_unable_without_p_gear);
                return true;
            }
        }
        return false;
    }

    public /* synthetic */ void lambda$initRepairMode$24$SituationFragment(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed() || isVuiAction(buttonView)) {
            this.mCarBodyViewModel.setWiperRepairMode(isChecked);
            StatisticUtils.sendStatistic(PageEnum.STATUS_E28_PAGE, BtnEnum.STATUS_E28_PAGE_WIPER_REPAIR_BTN, Integer.valueOf(StatisticUtils.getSwitchOnOff(isChecked)));
        }
    }

    public /* synthetic */ void lambda$initRepairMode$25$SituationFragment(Boolean enable) {
        if (enable != null) {
            this.canUpdateWiperSw = false;
            this.wiperRepairSw.setChecked(this.mCarBodyViewModel.getWiperRepairMode());
            this.canUpdateWiperSw = true;
        }
    }

    public /* synthetic */ boolean lambda$initRepairMode$26$SituationFragment(View v, MotionEvent event) {
        if (event.getAction() == 0) {
            if (ClickHelper.isFastClick(R.id.wiper_repair_sw, 1200L, true)) {
                return true;
            }
            if (CarBaseConfig.getInstance().isSupportWiperFault() && this.mCarBodyViewModel.isRearWiperFault()) {
                NotificationHelper.getInstance().showToast(R.string.wiper_fault_tip);
                return true;
            } else if (!this.canUpdateRearWiperSw || this.rearWiperRepairSw.isChecked()) {
                return false;
            } else {
                if (this.mVcuViewModel.getGearLevelValue() == GearLevel.P && this.mCarBodyViewModel.isRearWiperSpeedSwitchOff()) {
                    return false;
                }
                NotificationHelper.getInstance().showToast(R.string.wiper_unable_without_p_gear);
                return true;
            }
        }
        return false;
    }

    public /* synthetic */ void lambda$initRepairMode$27$SituationFragment(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed()) {
            this.mCarBodyViewModel.setRearWiperRepairMode(isChecked);
        }
    }

    public /* synthetic */ void lambda$initRepairMode$28$SituationFragment(Boolean enable) {
        if (enable != null) {
            this.canUpdateRearWiperSw = false;
            this.rearWiperRepairSw.setChecked(this.mCarBodyViewModel.getRearWiperRepairMode());
            this.canUpdateRearWiperSw = true;
        }
    }

    public /* synthetic */ boolean lambda$initRepairMode$29$SituationFragment(final XSwitch mAsRepairSw, View v, MotionEvent event) {
        if (event.getAction() == 0) {
            if (ClickHelper.isFastClick(R.id.as_repair_sw, 1200L, true)) {
                return true;
            }
            if (mAsRepairSw.isChecked()) {
                return false;
            }
            if (this.mChassisVm.getTrailerModeStatus() || this.mVcuViewModel.getTrailerModeStatus()) {
                NotificationHelper.getInstance().showToast(R.string.as_repair_open_with_trailer_mode);
                return true;
            } else if (CarBaseConfig.getInstance().isSupportXSport() && XSportDriveMode.XPower == this.mVcuViewModel.getXSportDriveModeValue()) {
                NotificationHelper.getInstance().showToast(R.string.exit_with_drive_mode_xpower_on);
                return true;
            } else if (this.mVcuViewModel.getGearLevelValue() != GearLevel.P) {
                NotificationHelper.getInstance().showToast(R.string.as_repair_open_without_p_gear);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public /* synthetic */ void lambda$initRepairMode$30$SituationFragment(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed() || isVuiAction(buttonView)) {
            this.mChassisVm.setAirSuspensionRepairMode(isChecked);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$initRepairMode$31(final XSwitch mAsRepairSw, Boolean enable) {
        if (enable != null) {
            mAsRepairSw.setChecked(enable.booleanValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: updateRescueCalls */
    public void lambda$initRescueCall$20$SituationFragment(String[] rescueCalls) {
        if (rescueCalls != null && rescueCalls.length > 0) {
            this.mRescueTv.setVisibility(0);
            this.mCallBtn1.setVisibility(0);
            this.mCallBtn1.setText(rescueCalls[0]);
            if (rescueCalls.length >= 2) {
                this.mCallBtn2.setVisibility(0);
                this.mCallBtn2.setText(rescueCalls[1]);
                return;
            }
            this.mCallBtn2.setVisibility(8);
            return;
        }
        this.mRescueTv.setVisibility(8);
        this.mCallBtn1.setVisibility(8);
        this.mCallBtn2.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.carcontrol.view.fragment.impl.SituationFragment$2  reason: invalid class name */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$chassis$TpmsCalibrationStatus;

        static {
            int[] iArr = new int[TpmsCalibrationStatus.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$chassis$TpmsCalibrationStatus = iArr;
            try {
                iArr[TpmsCalibrationStatus.Fixing.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$chassis$TpmsCalibrationStatus[TpmsCalibrationStatus.Fixed.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$chassis$TpmsCalibrationStatus[TpmsCalibrationStatus.Fail.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$chassis$TpmsCalibrationStatus[TpmsCalibrationStatus.NotFix.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTpmsUI(TpmsCalibrationStatus tpmsStatus) {
        if (tpmsStatus != null) {
            int i = AnonymousClass2.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$chassis$TpmsCalibrationStatus[tpmsStatus.ordinal()];
            if (i == 1) {
                this.mTpmsBtn.setLoading(true);
                addVuiProp(this.mTpmsBtn, VuiManager.VUI_PROP_SPECIAL_STATUS, VuiManager.SPECIAL_STATUS_LOADING);
            } else if (i == 2 || i == 3) {
                if (this.mClickCalibrate) {
                    this.mClickCalibrate = false;
                    NotificationHelper.getInstance().showToast(tpmsStatus == TpmsCalibrationStatus.Fixed ? R.string.calibrate_tyre_pressure_done : R.string.calibrate_tyre_pressure_fail);
                }
                this.mTpmsBtn.setLoading(false);
                removeVuiProps(this.mTpmsBtn, VuiManager.VUI_PROP_SPECIAL_STATUS);
            } else {
                this.mTpmsBtn.setLoading(false);
                removeVuiProps(this.mTpmsBtn, VuiManager.VUI_PROP_SPECIAL_STATUS);
            }
        }
    }

    private void updateTyreWarningState(int position, TpmsWarningState state) {
        XTextView xTextView;
        XTextView xTextView2;
        XImageView xImageView;
        int i;
        boolean z = state == TpmsWarningState.NORMAL;
        if (position == 1) {
            xTextView = this.mFlValueText;
            xTextView2 = this.mFlValueUnit;
            xImageView = this.mLeftFrontImg;
            i = z ? R.drawable.img_tile_normal : R.drawable.img_tile_warning;
        } else if (position == 2) {
            xTextView = this.mFrValueText;
            xTextView2 = this.mFrValueUnit;
            xImageView = this.mRightFrontImg;
            i = z ? R.drawable.img_tile_normal : R.drawable.img_tile_warning;
        } else if (position == 3) {
            xTextView = this.mRlValueText;
            xTextView2 = this.mRlValueUnit;
            xImageView = this.mLeftRearImg;
            i = z ? R.drawable.img_tile_normal : R.drawable.img_tile_warning;
        } else if (position != 4) {
            i = -1;
            xTextView = null;
            xTextView2 = null;
            xImageView = null;
        } else {
            xTextView = this.mRrValueText;
            xTextView2 = this.mRrValueUnit;
            xImageView = this.mRightRearImg;
            i = z ? R.drawable.img_tile_normal : R.drawable.img_tile_warning;
        }
        String tyrePressure = this.mChassisVm.getTyrePressure(position);
        if (xTextView != null) {
            xTextView.setText(tyrePressure);
            xTextView.setTextColor(getResourcesById().getColor(z ? R.color.x_list_text_color_selector : R.color.tile_warning_color, null));
        }
        if (xTextView2 != null) {
            xTextView2.setVisibility(tyrePressure.equals(ChassisViewModel.TPMS_DEFAULT_PRESSURE) ? 8 : 0);
            xTextView2.setTextColor(getResourcesById().getColor(z ? R.color.x_list_text_color_selector : R.color.tile_warning_color, null));
        }
        if (xImageView != null) {
            xImageView.setImageResource(i);
        }
    }

    private void callRescue(String telNum) {
        Uri parse = Uri.parse("tel:" + telNum.trim());
        try {
            startActivity(new Intent("android.intent.action.CALL", parse));
        } catch (Exception e) {
            e.printStackTrace();
            startActivity(new Intent("android.intent.action.DIAL", parse));
        }
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.VuiFragment, com.xiaopeng.vui.commons.IVuiSceneListener
    public void onVuiEvent(View view, VuiEvent vuiEvent) {
        if (view.getId() == R.id.btn_rescue_phone_1 && view.getId() == R.id.btn_rescue_phone_2) {
            return;
        }
        super.onVuiEvent(view, vuiEvent);
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.VuiFragment, com.xiaopeng.vui.commons.IVuiSceneListener
    public boolean onInterceptVuiEvent(View view, VuiEvent vuiEvent) {
        if (view != null && R.id.tyre_calibrate_btn == view.getId() && this.mChassisVm.getTpmsCalibrationStatus() == TpmsCalibrationStatus.Fixing) {
            vuiFeedback(R.string.calibrate_tyre_pressure_running, view);
            return true;
        } else if (view != null && view.getId() == R.id.x_list_sw && !this.wiperRepairSw.isChecked() && (this.mVcuViewModel.getGearLevelValue() != GearLevel.P || !this.mCarBodyViewModel.isWiperSpeedSwitchOff())) {
            vuiFeedback(R.string.wiper_unable_without_p_gear, view);
            NotificationHelper.getInstance().showToast(R.string.wiper_unable_without_p_gear);
            return true;
        } else {
            return super.onInterceptVuiEvent(view, vuiEvent);
        }
    }

    private void confirmDialECall() {
        showDialog(R.string.ecall_confirm_title, R.string.ecall_confirm_msg, getStringById(R.string.btn_cancel), (XDialogInterface.OnClickListener) null, getStringById(R.string.btn_ok), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SituationFragment$6eZTqAK1rpq0QJ84_AhhZI_UoZA
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                SituationFragment.this.lambda$confirmDialECall$32$SituationFragment(xDialog, i);
            }
        }, VuiManager.SCENE_ECALL_CONFIRM);
    }

    public /* synthetic */ void lambda$confirmDialECall$32$SituationFragment(XDialog xDialog, int i) {
        startECallProcedure();
    }

    private void startECallProcedure() {
        try {
            startActivity(new Intent("com.xiaopeng.ecall.callout"));
        } catch (ActivityNotFoundException unused) {
            LogUtils.w(this.TAG, "ECALL activity not found", false);
        }
    }
}
