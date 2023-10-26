package com.xiaopeng.carcontrol.viewmodel.selfcheck;

import com.xiaopeng.carcontrol.bean.selfcheck.CheckCategoryItem;
import com.xiaopeng.carcontrol.bean.selfcheck.CheckDetailItem;
import com.xiaopeng.carcontrol.bean.selfcheck.CheckItemEcu;
import com.xiaopeng.carcontrol.bean.selfcheck.CheckItemKey;
import com.xiaopeng.carcontrol.config.DxCarConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.ciu.CiuViewModel;
import com.xiaopeng.carcontrol.viewmodel.ciu.ICiuViewModel;
import com.xiaopeng.carcontrolmodule.R;

/* loaded from: classes2.dex */
public class D2CheckCarMode extends CheckCarMode {
    @Override // com.xiaopeng.carcontrol.viewmodel.selfcheck.CheckCarMode
    protected boolean isNeedMock() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.selfcheck.CheckCarMode
    protected void generateData() {
        this.mCheckList.clear();
        this.mCheckedTotal = 0;
        CheckCategoryItem build = new CheckCategoryItem.Builder().category(getString(R.string.selfcheck_category_chassis_sys)).ecu("ABS").title(getString(R.string.selfcheck_category_abs)).addItem(new CheckDetailItem(CheckItemKey.ID_ESP_ABS_FAULT, 3, getString(R.string.esp_abs_fault))).build();
        this.mCheckList.add(build);
        this.mCheckedTotal += build.getCheckSize();
        CheckCategoryItem build2 = new CheckCategoryItem.Builder().category(getString(R.string.selfcheck_category_chassis_sys)).ecu("HDC").title(getString(R.string.selfcheck_category_hdc)).addItem(new CheckDetailItem(CheckItemKey.ID_ESP_HDC_FAULT, 1, getString(R.string.esp_hdc_fault))).build();
        this.mCheckList.add(build2);
        this.mCheckedTotal += build2.getCheckSize();
        CheckCategoryItem build3 = new CheckCategoryItem.Builder().category(getString(R.string.selfcheck_category_chassis_sys)).ecu(CheckItemEcu.KEY_TPMS).title(getString(R.string.selfcheck_category_tpms)).addItem(new CheckDetailItem("ID_TPMS_SYSFAULTWARN", 2, getString(R.string.tpms_warning_system_fault))).addItem(new CheckDetailItem(CheckItemKey.ID_TPMS_WARNING_TIRE_TEMPERATURE_ALL, 1, getString(R.string.tpms_warning_tire_temp_all))).addItem(new CheckDetailItem(CheckItemKey.ID_TPMS_TIRE_PRESSURE_SENSOR_STATUS_ALL, 2, getString(R.string.tpms_tire_pressure_sensor_fault))).build();
        this.mCheckList.add(build3);
        this.mCheckedTotal += build3.getCheckSize();
        CheckCategoryItem build4 = new CheckCategoryItem.Builder().category(getString(R.string.selfcheck_category_battery_sys)).ecu("BMS").title(getString(R.string.selfcheck_category_bms)).addItem(new CheckDetailItem(CheckItemKey.ID_VCU_WARNING_BATTERY_OVERHEATING, 1, getString(R.string.vcu_warning_battery_overheating))).build();
        this.mCheckList.add(build4);
        this.mCheckedTotal += build4.getCheckSize();
        CheckCategoryItem build5 = new CheckCategoryItem.Builder().category(getString(R.string.selfcheck_category_battery_sys)).ecu("CCS").title(getString(R.string.selfcheck_category_css)).addItem(new CheckDetailItem(CheckItemKey.ID_VCU_CCS_WORK_ERROR, 1, getString(R.string.vcu_ccs_work_error))).build();
        this.mCheckList.add(build5);
        this.mCheckedTotal += build5.getCheckSize();
        CheckCategoryItem build6 = new CheckCategoryItem.Builder().category(getString(R.string.selfcheck_category_battery_sys)).ecu("DCDC").title(getString(R.string.selfcheck_category_dcdc)).addItem(new CheckDetailItem(CheckItemKey.ID_VCU_DCDC_ERROR, 1, getString(R.string.vcu_dcdc_error))).addItem(new CheckDetailItem(CheckItemKey.ID_VCU_BATTERY_VOLTAGE_LOW, 1, getString(R.string.vcu_battery_voltage_low))).build();
        this.mCheckList.add(build6);
        this.mCheckedTotal += build6.getCheckSize();
        CheckCategoryItem build7 = new CheckCategoryItem.Builder().category(getString(R.string.selfcheck_category_moto_sys)).ecu("IPU").title(getString(R.string.selfcheck_category_ipu)).addItem(new CheckDetailItem(CheckItemKey.ID_VCU_ELECTRIC_MOTOR_SYSTEM_OVERHEATING, 1, getString(R.string.vcu_moto_sys_overheating))).addItem(new CheckDetailItem(CheckItemKey.ID_IPU_FAIL_ST, 3, getString(R.string.ipu_fault_st))).build();
        this.mCheckList.add(build7);
        this.mCheckedTotal += build7.getCheckSize();
        CheckCategoryItem build8 = new CheckCategoryItem.Builder().category(getString(R.string.selfcheck_category_moto_sys)).ecu(CheckItemEcu.KEY_VCU).title(getString(R.string.selfcheck_category_multelec_vcu)).addItem(new CheckDetailItem(CheckItemKey.ID_VCU_ELECTRIC_VACUUM_PUMP_ERROR, 3, getString(R.string.vcu_elec_vacuum_pump_error))).addItem(new CheckDetailItem(CheckItemKey.ID_VCU_GEAR_WARNING, 1, getString(R.string.vcu_gear_warning))).addItem(new CheckDetailItem(CheckItemKey.ID_VCU_HVRLY_ADHESION_ST, 3, getString(R.string.vcu_contactor_sticky))).build();
        this.mCheckList.add(build8);
        this.mCheckedTotal += build8.getCheckSize();
        CheckCategoryItem build9 = new CheckCategoryItem.Builder().category(getString(R.string.selfcheck_category_moto_sys)).ecu("AGS").title(getString(R.string.selfcheck_category_ags)).addItem(new CheckDetailItem(CheckItemKey.ID_VCU_AGS_ERROR, 3, getString(R.string.vcu_ags_error))).build();
        this.mCheckList.add(build9);
        this.mCheckedTotal += build9.getCheckSize();
        CheckCategoryItem build10 = new CheckCategoryItem.Builder().category(getString(R.string.selfcheck_category_body_elec_sys)).ecu(CheckItemEcu.KEY_BCM).title(getString(R.string.selfcheck_category_ecu_elec)).addItem(new CheckDetailItem(CheckItemKey.ID_BCM_HIGH_BEAM_FAIL, 1, getString(R.string.bcm_high_beam_fail))).addItem(new CheckDetailItem(CheckItemKey.ID_BCM_LOW_BEAM_FAIL, 3, getString(R.string.bcm_low_beam_fail))).addItem(new CheckDetailItem(CheckItemKey.ID_BCM_LEFT_TRUN_LAMP_FAIL, 3, getString(R.string.bcm_left_turn_lamp_fail))).addItem(new CheckDetailItem(CheckItemKey.ID_BCM_RIGHT_TRUN_LAMP_FAIL, 3, getString(R.string.bcm_right_turn_lamp_fail))).build();
        this.mCheckList.add(build10);
        this.mCheckedTotal += build10.getCheckSize();
        CheckCategoryItem build11 = new CheckCategoryItem.Builder().category(getString(R.string.selfcheck_category_body_elec_sys)).ecu("PEPS").title(getString(R.string.selfcheck_category_peps)).addItem(new CheckDetailItem(CheckItemKey.ID_BCM_SYSTEM_ERROR, 3, getString(R.string.bcm_system_error))).build();
        this.mCheckList.add(build11);
        this.mCheckedTotal += build11.getCheckSize();
        CheckCategoryItem build12 = new CheckCategoryItem.Builder().category(getString(R.string.selfcheck_category_body_elec_sys)).ecu("MSM").title(getString(R.string.selfcheck_category_msm)).addItem(new CheckDetailItem(CheckItemKey.ID_BCM_MSM_ERROR_INFO, 2, getString(R.string.bcm_msm_error))).build();
        this.mCheckList.add(build12);
        this.mCheckedTotal += build12.getCheckSize();
        if (((CiuViewModel) ViewModelManager.getInstance().getViewModelImpl(ICiuViewModel.class)).isCiuExist()) {
            CheckCategoryItem build13 = new CheckCategoryItem.Builder().category(getString(R.string.selfcheck_category_intell_vision_sys)).ecu("DVR").title(getString(R.string.selfcheck_category_dvr)).addItem(new CheckDetailItem(CheckItemKey.ID_CIU_DVR_STATUS, 1, getString(R.string.ciu_dvr_fault))).addItem(new CheckDetailItem(CheckItemKey.ID_CIU_SD_ST, 1, getString(R.string.ciu_dvr_sd_fault))).build();
            this.mCheckList.add(build13);
            this.mCheckedTotal += build13.getCheckSize();
        }
        if (BaseFeatureOption.getInstance().isSupportIntellDriveSysSelfCheck() && (DxCarConfig.getInstance().isHighConfig() || DxCarConfig.getInstance().isMiddleConfig())) {
            CheckCategoryItem build14 = new CheckCategoryItem.Builder().category(getString(R.string.selfcheck_category_intell_drive_sys)).ecu(CheckItemEcu.KEY_SCU).title(getString(R.string.selfcheck_category_scu)).addItem(new CheckDetailItem("ID_SCU_ISLC_SW", 1, getString(R.string.scu_front_camera_fault))).build();
            this.mCheckList.add(build14);
            this.mCheckedTotal += build14.getCheckSize();
        }
        CheckCategoryItem build15 = new CheckCategoryItem.Builder().category(getString(R.string.selfcheck_category_hvac_sys)).ecu("PTC").title(getString(R.string.selfcheck_category_ptc)).addItem(new CheckDetailItem(CheckItemKey.ID_HVAC_PTC_ERROR, 1, getString(R.string.hvac_ptc))).build();
        this.mCheckList.add(build15);
        this.mCheckedTotal += build15.getCheckSize();
        CheckCategoryItem build16 = new CheckCategoryItem.Builder().category(getString(R.string.selfcheck_category_hvac_sys)).ecu("ACP").title(getString(R.string.selfcheck_category_acp)).addItem(new CheckDetailItem(CheckItemKey.ID_HVAC_COMPRESSOR_ERROR_INFO, 1, getString(R.string.hvac_compressor_error))).build();
        this.mCheckList.add(build16);
        this.mCheckedTotal += build16.getCheckSize();
        CheckCategoryItem build17 = new CheckCategoryItem.Builder().category(getString(R.string.selfcheck_category_hvac_sys)).ecu("PTC 025").title(getString(R.string.selfcheck_category_ptc_battery)).addItem(new CheckDetailItem(CheckItemKey.ID_VCU_BATTERY_PTC_ERROR_INFO, 1, getString(R.string.vcu_battery_ptc_fault))).build();
        this.mCheckList.add(build17);
        this.mCheckedTotal += build17.getCheckSize();
        for (CheckCategoryItem checkCategoryItem : this.mCheckList) {
            for (CheckDetailItem checkDetailItem : checkCategoryItem.getItems()) {
                checkDetailItem.setCategory(checkCategoryItem);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.selfcheck.CheckCarMode, com.xiaopeng.carcontrol.viewmodel.selfcheck.ICheckCarMode
    public String generateJsonForUi(int status, boolean needDetail) {
        return generateResultJsonForCloud();
    }
}
