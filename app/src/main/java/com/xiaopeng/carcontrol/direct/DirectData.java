package com.xiaopeng.carcontrol.direct;

import com.xiaopeng.carcontrol.R;
import com.xiaopeng.carcontrol.bean.xpilot.XPilotItem;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.direct.action.DoorKeySettingPageAction;
import com.xiaopeng.carcontrol.direct.action.HvacPanelAction;
import com.xiaopeng.carcontrol.direct.action.LluDancePageAction;
import com.xiaopeng.carcontrol.direct.action.NgpSettingPageAction;
import com.xiaopeng.carcontrol.direct.action.NormalPageAction;
import com.xiaopeng.carcontrol.direct.action.SpaceCapsuleAction;
import com.xiaopeng.carcontrol.direct.action.SunShadeControlPageAction;
import com.xiaopeng.carcontrol.direct.action.WindowControlPageAction;
import com.xiaopeng.carcontrol.direct.action.XKeySettingPageAction;
import com.xiaopeng.carcontrol.direct.support.AtlCheckAction;
import com.xiaopeng.carcontrol.direct.support.BootSoundCheckAction;
import com.xiaopeng.carcontrol.direct.support.CiuCheckAction;
import com.xiaopeng.carcontrol.direct.support.CloseTrunkCheckAction;
import com.xiaopeng.carcontrol.direct.support.CwcCheckAction;
import com.xiaopeng.carcontrol.direct.support.DhcCheckAction;
import com.xiaopeng.carcontrol.direct.support.DoorKeyCheckAction;
import com.xiaopeng.carcontrol.direct.support.DsmCameraCheckAction;
import com.xiaopeng.carcontrol.direct.support.EsbCheckAction;
import com.xiaopeng.carcontrol.direct.support.IhbCheckAction;
import com.xiaopeng.carcontrol.direct.support.LluCheckAction;
import com.xiaopeng.carcontrol.direct.support.LluDanceCheckAction;
import com.xiaopeng.carcontrol.direct.support.MeterSettingCheckAction;
import com.xiaopeng.carcontrol.direct.support.MirrorFoldCheckAction;
import com.xiaopeng.carcontrol.direct.support.RearMirrorDownCheckAction;
import com.xiaopeng.carcontrol.direct.support.RearMirrorReverseCheckAction;
import com.xiaopeng.carcontrol.direct.support.SeatCheckAction;
import com.xiaopeng.carcontrol.direct.support.SeatHeatVentCheckAction;
import com.xiaopeng.carcontrol.direct.support.SpaceCapsuleCheckAction;
import com.xiaopeng.carcontrol.direct.support.SunShadeCheckAction;
import com.xiaopeng.carcontrol.direct.support.SupportCheckAction;
import com.xiaopeng.carcontrol.direct.support.VipSeatCheckAction;
import com.xiaopeng.carcontrol.direct.support.XPedalCheckAction;
import com.xiaopeng.carcontrol.direct.support.XPilot3CheckAction;
import com.xiaopeng.carcontrol.direct.support.XPilotActiveSafeCheckAction;
import com.xiaopeng.carcontrol.direct.support.XPilotElkCheckAction;
import com.xiaopeng.carcontrol.direct.support.XPilotIslaCheckAction;
import com.xiaopeng.carcontrol.direct.support.XPilotLdwCheckAction;
import com.xiaopeng.carcontrol.direct.support.XPilotLidarCheckAction;
import com.xiaopeng.carcontrol.direct.support.XPilotLkaCheckAction;
import com.xiaopeng.carcontrol.direct.support.XPilotScuCheckAction;
import com.xiaopeng.carcontrol.direct.support.XPilotXpuCheckAction;
import com.xiaopeng.carcontrol.direct.support.XPilotXpuNedcCheckAction;
import com.xiaopeng.carcontrol.provider.CarControl;
import com.xiaopeng.carcontrol.view.HvacActivity;
import com.xiaopeng.carcontrol.view.SpaceCapsuleActivity;
import com.xiaopeng.carcontrol.view.fragment.CiuControlFragment;
import com.xiaopeng.carcontrol.view.fragment.DriveExperienceFragment;
import com.xiaopeng.carcontrol.view.fragment.LluFragment;
import com.xiaopeng.carcontrol.view.fragment.MainControlFragment;
import com.xiaopeng.carcontrol.view.fragment.impl.SettingsFragment;
import com.xiaopeng.carcontrol.view.fragment.impl.SituationFragment;
import com.xiaopeng.carcontrol.view.fragment.xpilot.XPilotFragment;
import com.xiaopeng.lludancemanager.view.LluDanceActivityNew;
import java.util.HashMap;

/* loaded from: classes2.dex */
class DirectData {
    public static final String AUTHORITY = "carcontrol";
    public static final String DRV_SIDE = "drv_side";
    public static final String PSN_SIDE = "psn_side";
    public static final String SCHEME = "xiaopeng";

    DirectData() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static HashMap<String, PageDirectItem> loadPageData() {
        HashMap<String, PageDirectItem> hashMap = new HashMap<>();
        CarBaseConfig carBaseConfig = CarBaseConfig.getInstance();
        NormalPageAction normalPageAction = new NormalPageAction();
        LluCheckAction lluCheckAction = new LluCheckAction();
        hashMap.put("/maincontrol", new PageDirectItem(MainControlFragment.class.getName(), normalPageAction));
        hashMap.put("/lampcontrol", new PageDirectItem(LluFragment.class.getName(), normalPageAction, lluCheckAction));
        hashMap.put("/drivecontrol", new PageDirectItem(DriveExperienceFragment.class.getName(), normalPageAction));
        hashMap.put("/carsettings", new PageDirectItem(SettingsFragment.class.getName(), normalPageAction));
        hashMap.put("/xpilotcontrol", new PageDirectItem(XPilotFragment.class.getName(), normalPageAction));
        hashMap.put("/ciucontrol", new PageDirectItem(CiuControlFragment.class.getName(), normalPageAction, new CiuCheckAction()));
        hashMap.put("/carsituation", new PageDirectItem(SituationFragment.class.getName(), normalPageAction));
        hashMap.put("/ac", new PageDirectItem(HvacActivity.class.getName(), new HvacPanelAction()));
        hashMap.put("/lludance", new PageDirectItem(LluDanceActivityNew.class.getName(), new LluDancePageAction(), new LluDanceCheckAction()));
        hashMap.put("/space_capsule", new PageDirectItem(SpaceCapsuleActivity.class.getName(), new SpaceCapsuleAction(), new SpaceCapsuleCheckAction()));
        hashMap.put("/space_capsule_sleep", new PageDirectItem(SpaceCapsuleActivity.class.getName(), new SpaceCapsuleAction(), new SpaceCapsuleCheckAction()));
        hashMap.put("/space_capsule_cinema", new PageDirectItem(SpaceCapsuleActivity.class.getName(), new SpaceCapsuleAction(), new SpaceCapsuleCheckAction()));
        if (carBaseConfig.isSupportWindowPos()) {
            WindowControlPageAction windowControlPageAction = new WindowControlPageAction();
            hashMap.put("/windowcontrol", new PageDirectItem(DRV_SIDE, windowControlPageAction));
            hashMap.put("/windowcontrol/drv_side", new PageDirectItem("/windowcontrol/drv_side", DRV_SIDE, windowControlPageAction));
            hashMap.put("/windowcontrol/psn_side", new PageDirectItem("/windowcontrol/psn_side", PSN_SIDE, windowControlPageAction));
        }
        hashMap.put("/sunshade", new PageDirectItem((Object) null, new SunShadeControlPageAction(), new SunShadeCheckAction()));
        hashMap.put("/xkeysetting", new PageDirectItem(new XKeySettingPageAction()));
        hashMap.put("/doorkeysetting", new PageDirectItem((Object) null, new DoorKeySettingPageAction(), new DoorKeyCheckAction()));
        hashMap.put("/ngp_setting", new PageDirectItem((Object) null, new NgpSettingPageAction(), new XPilot3CheckAction()));
        hashMap.put("/lampcontrol/lampsetting", new PageDirectItem("/lampcontrol/lampsetting", LluFragment.class.getName(), normalPageAction, lluCheckAction));
        hashMap.put("/lampcontrol/sayhicontrol", new PageDirectItem("/lampcontrol/sayhicontrol", LluFragment.class.getName(), normalPageAction, lluCheckAction));
        String name = XPilotFragment.class.getName();
        XPilotActiveSafeCheckAction xPilotActiveSafeCheckAction = new XPilotActiveSafeCheckAction();
        XPilotScuCheckAction xPilotScuCheckAction = new XPilotScuCheckAction();
        XPilotXpuCheckAction xPilotXpuCheckAction = new XPilotXpuCheckAction();
        hashMap.put("/carsettings/tip/ihb", new PageDirectItem("/carsettings/tip/ihb", SettingsFragment.class.getName(), normalPageAction, new IhbCheckAction()));
        hashMap.put("/xpilotcontrol/tip/fcw", new PageDirectItem("/xpilotcontrol/tip/fcw", name, normalPageAction, xPilotActiveSafeCheckAction));
        hashMap.put("/xpilotcontrol/tip/bsd", new PageDirectItem("/xpilotcontrol/tip/bsd", name, normalPageAction, xPilotActiveSafeCheckAction));
        if (carBaseConfig.isSupportDow()) {
            hashMap.put("/xpilotcontrol/tip/dow", new PageDirectItem("/xpilotcontrol/tip/dow", name, normalPageAction, xPilotScuCheckAction));
        }
        hashMap.put("/xpilotcontrol/tip/rcta", new PageDirectItem("/xpilotcontrol/tip/rcta", name, normalPageAction, xPilotActiveSafeCheckAction));
        hashMap.put("/xpilotcontrol/tip/lss", new PageDirectItem("/xpilotcontrol/tip/lss", name, normalPageAction, new XPilotLkaCheckAction()));
        hashMap.put("/xpilotcontrol/tip/ldw", new PageDirectItem("/xpilotcontrol/tip/ldw", name, normalPageAction, new XPilotLdwCheckAction()));
        hashMap.put("/xpilotcontrol/tip/elk", new PageDirectItem("/xpilotcontrol/tip/elk", name, normalPageAction, new XPilotElkCheckAction()));
        if (carBaseConfig.isSupportRcw()) {
            hashMap.put("/xpilotcontrol/tip/rcw", new PageDirectItem("/xpilotcontrol/tip/rcw", name, normalPageAction, null));
        }
        if (carBaseConfig.isSupportIslcInActive()) {
            hashMap.put("/xpilotcontrol/tip/islc", new PageDirectItem("/xpilotcontrol/tip/islc", name, normalPageAction, null));
        }
        hashMap.put("/xpilotcontrol/tip/isla", new PageDirectItem("/xpilotcontrol/tip/isla", name, normalPageAction, new XPilotIslaCheckAction()));
        if (carBaseConfig.isSupportShowAutoPark()) {
            hashMap.put("/xpilotcontrol/tip/auto_park", new PageDirectItem("/xpilotcontrol/tip/auto_park", name, normalPageAction, null));
            if (carBaseConfig.isSupportMemPark()) {
                hashMap.put("/xpilotcontrol/tip/mem_park", new PageDirectItem("/xpilotcontrol/tip/mem_park", name, normalPageAction, null));
            }
        }
        if (carBaseConfig.isSupportLcc()) {
            hashMap.put("/xpilotcontrol/tip/lcc", new PageDirectItem("/xpilotcontrol/tip/lcc", name, normalPageAction, null));
            hashMap.put("/xpilotcontrol/tip/alc", new PageDirectItem("/xpilotcontrol/tip/alc", name, normalPageAction, null));
        }
        hashMap.put("/xpilotcontrol/tip/ngp", new PageDirectItem("/xpilotcontrol/tip/ngp", name, normalPageAction, xPilotXpuCheckAction));
        hashMap.put("/xpilotcontrol/tip/cngp", new PageDirectItem("/xpilotcontrol/tip/cngp", name, normalPageAction, new XPilotLidarCheckAction()));
        if (carBaseConfig.isSupportAuxiliaryFunc()) {
            hashMap.put("/xpilotcontrol/tip/hdc", new PageDirectItem("/xpilotcontrol/tip/hdc", name, normalPageAction, null));
            hashMap.put("/xpilotcontrol/tip/esp", new PageDirectItem("/xpilotcontrol/tip/esp", name, normalPageAction, null));
            hashMap.put("/xpilotcontrol/tip/avh", new PageDirectItem("/xpilotcontrol/tip/avh", name, normalPageAction, null));
            if (carBaseConfig.isSupportShowEbw()) {
                hashMap.put("/xpilotcontrol/tip/ebw", new PageDirectItem("/xpilotcontrol/tip/ebw", name, normalPageAction, null));
            }
        }
        if (carBaseConfig.isSupportDrvSeatHeat()) {
            if (BaseFeatureOption.getInstance().isSeatHeatVentGather()) {
                hashMap.put("/ac/seat_heat_vent", new PageDirectItem("/ac/seat_heat_vent", HvacActivity.class.getName(), new HvacPanelAction(), new SeatHeatVentCheckAction()));
            } else {
                hashMap.put("/ac/seat_heat_vent", new PageDirectItem(HvacActivity.class.getName(), new HvacPanelAction()));
            }
        }
        return hashMap;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static HashMap<String, ElementDirectItem> loadItemData() {
        HashMap<String, ElementDirectItem> hashMap = new HashMap<>();
        CarBaseConfig carBaseConfig = CarBaseConfig.getInstance();
        hashMap.put("/maincontrol/trunk", new ElementDirectItem("trunk", R.id.open_trunk_btn));
        hashMap.put("/maincontrol/close_trunk", new ElementDirectItem("close_trunk", (int) R.id.close_trunk_btn, new CloseTrunkCheckAction()));
        hashMap.put("/maincontrol/head_lamp", new ElementDirectItem("head_lamp", R.id.head_lamp_tab));
        hashMap.put("/maincontrol/fog_lamp", new ElementDirectItem("fog_lamp", R.id.btn_fog_light));
        if (carBaseConfig.isSupportLightMeHomeNew()) {
            hashMap.put("/carsettings/light_me_home", new ElementDirectItem("light_me_home", R.id.light_me_home_tab_item));
        } else {
            hashMap.put("/maincontrol/light_me_home", new ElementDirectItem("light_me_home", R.id.light_me_home_btn));
        }
        hashMap.put("/maincontrol/unfold", new ElementDirectItem("unfold", (int) R.id.mirror_unfold_btn, new MirrorFoldCheckAction()));
        hashMap.put("/maincontrol/fold", new ElementDirectItem("fold", (int) R.id.mirror_fold_btn, new MirrorFoldCheckAction()));
        hashMap.put("/maincontrol/win_open_all", new ElementDirectItem("win_open_all", R.id.open_window_btn));
        hashMap.put("/maincontrol/win_close_all", new ElementDirectItem("win_close_all", R.id.close_window_btn));
        hashMap.put("/maincontrol/win_vent", new ElementDirectItem("win_vent", R.id.vent_window_btn));
        hashMap.put("/maincontrol/seat_control", new ElementDirectItem("seat_control", (int) R.id.drv_seat_view, new SeatCheckAction()));
        hashMap.put("/maincontrol/vip_seat_control", new ElementDirectItem("vip_seat_control", (int) R.id.vip_seat_btn, new VipSeatCheckAction()));
        LluCheckAction lluCheckAction = new LluCheckAction();
        hashMap.put("/lampcontrol/llu_switch", new ElementDirectItem("llu_switch", (int) R.id.llu_sw_item, lluCheckAction));
        hashMap.put("/lampcontrol/llu_unlock", new ElementDirectItem("llu_unlock", (int) R.id.btn_llu_awake, lluCheckAction));
        hashMap.put("/lampcontrol/llu_lock", new ElementDirectItem("llu_lock", (int) R.id.btn_llu_sleep, lluCheckAction));
        hashMap.put("/lampcontrol/llu_dc", new ElementDirectItem("llu_dc", (int) R.id.btn_llu_dc_charge, lluCheckAction));
        hashMap.put("/lampcontrol/llu_ac", new ElementDirectItem("llu_ac", (int) R.id.btn_llu_ac_charge, lluCheckAction));
        hashMap.put("/lampcontrol/llu_find_car", new ElementDirectItem("llu_find_car", (int) R.id.btn_llu_find_car, lluCheckAction));
        AtlCheckAction atlCheckAction = new AtlCheckAction();
        hashMap.put("/lampcontrol/atl_sw", new ElementDirectItem(CarControl.PrivateControl.ATL_SW, (int) R.id.atl_sw_item, atlCheckAction));
        hashMap.put("/lampcontrol/atl_color", new ElementDirectItem("atl_color", (int) R.id.atl_color_view, atlCheckAction));
        hashMap.put("/lampcontrol/park_lamp_mode", new ElementDirectItem("park_lamp_mode", (int) R.id.position_lamp_mode_item, lluCheckAction));
        hashMap.put("/drivecontrol/drive_mode", new ElementDirectItem(CarControl.System.DRIVE_MODE, R.id.drive_mode_tab_item));
        hashMap.put("/drivecontrol/recycle", new ElementDirectItem("recycle", R.id.power_recycle_tab_item));
        hashMap.put("/drivecontrol/x_pedal", new ElementDirectItem("x_pedal", (int) R.id.x_pedal_sw_item, new XPedalCheckAction()));
        hashMap.put("/drivecontrol/antisickmode", new ElementDirectItem("antisickmode", R.id.anti_carsick_sw_item));
        hashMap.put("/drivecontrol/steering_eps", new ElementDirectItem("steering_eps", R.id.eps_item));
        if (carBaseConfig.isNewAvasArch()) {
            hashMap.put("/carsettings/avas", new ElementDirectItem("avas", R.id.low_avas_tab_item));
        } else {
            hashMap.put("/carsettings/avas", new ElementDirectItem("avas", R.id.low_avas_sw_item));
        }
        hashMap.put("/carsettings/wiper_level", new ElementDirectItem("wiper_level", R.id.wipe_interval_tab_item));
        hashMap.put("/carsettings/unlock_response", new ElementDirectItem(CarControl.PrivateControl.UNLOCK_RESPONSE, R.id.unlock_response_item));
        hashMap.put("/carsettings/dhc", new ElementDirectItem(CarControl.PrivateControl.DHC, (int) R.id.dhc_sw_item, new DhcCheckAction()));
        hashMap.put("/carsettings/drive_auto_lock", new ElementDirectItem("drive_auto_lock", R.id.driving_auto_lock_sw_item));
        hashMap.put("/carsettings/park_auto_unlock", new ElementDirectItem(CarControl.PrivateControl.PARK_AUTO_UNLOCK, R.id.park_auto_unlock_sw_item));
        if (carBaseConfig.isSupportAutoCloseWin()) {
            hashMap.put("/carsettings/auto_window_lock", new ElementDirectItem("auto_window_lock", R.id.auto_leave_window_lock_sw));
        }
        MeterSettingCheckAction meterSettingCheckAction = new MeterSettingCheckAction();
        hashMap.put("/carsettings/meter_menu_setting", new ElementDirectItem("meter_menu_setting", (int) R.id.meter_menu_item, meterSettingCheckAction));
        hashMap.put("/carsettings/meter_spd_limit_sw", new ElementDirectItem("meter_spd_limit_sw", (int) R.id.speed_limit_sw_item, meterSettingCheckAction));
        hashMap.put("/carsettings/meter_spd_limit_value", new ElementDirectItem("meter_spd_limit_value", (int) R.id.speed_limit_sd, meterSettingCheckAction));
        hashMap.put("/carsettings/seat_welcome", new ElementDirectItem(CarControl.PrivateControl.SEAT_WELCOME_SW, R.id.seat_welcome_sw));
        hashMap.put("/carsettings/boot_sound_effect", new ElementDirectItem("boot_sound_effect", (int) R.id.boot_sound_sw, new BootSoundCheckAction()));
        hashMap.put("/carsettings/esb_sw", new ElementDirectItem("esb_sw", (int) R.id.esb_sw_item, new EsbCheckAction()));
        hashMap.put("/carsettings/rsb_warning", new ElementDirectItem("rsb_warning", R.id.rear_belt_sw_item));
        hashMap.put("/carsettings/ihb", new ElementDirectItem("ihb", (int) R.id.ihb_sw_item, new IhbCheckAction()));
        hashMap.put("/carsettings/cwc_sw", new ElementDirectItem("cwc_sw", (int) R.id.cwc_sw, new CwcCheckAction()));
        hashMap.put("/carsettings/dsm_camera", new ElementDirectItem("dsm_camera", (int) R.id.dsm_camera_sw, new DsmCameraCheckAction()));
        hashMap.put("/carsettings/high_speed_close_win", new ElementDirectItem("high_speed_close_win", R.id.high_speed_sw_item));
        hashMap.put("/carsettings/reverse_rm", new ElementDirectItem("reverse_rm", (int) R.id.mirror_auto_reverse_sw_item, new RearMirrorDownCheckAction()));
        RearMirrorReverseCheckAction rearMirrorReverseCheckAction = new RearMirrorReverseCheckAction();
        hashMap.put("/carsettings/reverse_left", new ElementDirectItem("reverse_left", (int) R.id.left_rm_reverse_sw, rearMirrorReverseCheckAction));
        hashMap.put("/carsettings/reverse_right", new ElementDirectItem("reverse_right", (int) R.id.right_rm_reverse_sw, rearMirrorReverseCheckAction));
        XPilotActiveSafeCheckAction xPilotActiveSafeCheckAction = new XPilotActiveSafeCheckAction();
        new XPilotScuCheckAction();
        XPilotXpuCheckAction xPilotXpuCheckAction = new XPilotXpuCheckAction();
        hashMap.put("/xpilotcontrol/fcw", new ElementDirectItem(XPilotItem.KEY_FCW, 0, xPilotActiveSafeCheckAction));
        hashMap.put("/xpilotcontrol/lss", new ElementDirectItem(XPilotItem.KEY_LSS, 0, new XPilotLkaCheckAction()));
        hashMap.put("/xpilotcontrol/ldw", new ElementDirectItem(XPilotItem.KEY_LDW, 0, new XPilotLdwCheckAction()));
        hashMap.put("/xpilotcontrol/elk", new ElementDirectItem(XPilotItem.KEY_ELK, 0, new XPilotElkCheckAction()));
        hashMap.put("/xpilotcontrol/bsd", new ElementDirectItem(XPilotItem.KEY_BSD, 0, xPilotActiveSafeCheckAction));
        hashMap.put("/xpilotcontrol/rcta", new ElementDirectItem(XPilotItem.KEY_RCTA, 0, xPilotActiveSafeCheckAction));
        if (carBaseConfig.isSupportDow()) {
            hashMap.put("/xpilotcontrol/dow", new ElementDirectItem(XPilotItem.KEY_DOW, 0, (SupportCheckAction) null));
        }
        if (carBaseConfig.isSupportRcw()) {
            hashMap.put("/xpilotcontrol/rcw", new ElementDirectItem(XPilotItem.KEY_RCW, 0, (SupportCheckAction) null));
        }
        if (carBaseConfig.isSupportIslcInActive()) {
            hashMap.put("/xpilotcontrol/islc", new ElementDirectItem(XPilotItem.KEY_ISLC, 0, (SupportCheckAction) null));
        }
        hashMap.put("/xpilotcontrol/isla", new ElementDirectItem(XPilotItem.KEY_ISLA, 0, new XPilotIslaCheckAction()));
        if (carBaseConfig.isSupportShowAutoPark()) {
            hashMap.put("/xpilotcontrol/auto_park", new ElementDirectItem("auto_park", 0, (SupportCheckAction) null));
            if (carBaseConfig.isSupportMemPark()) {
                hashMap.put("/xpilotcontrol/mem_park", new ElementDirectItem(XPilotItem.KEY_MEM_PARK, 0, (SupportCheckAction) null));
            }
        }
        if (carBaseConfig.isSupportLcc()) {
            hashMap.put("/xpilotcontrol/lcc", new ElementDirectItem(XPilotItem.KEY_LCC, 0, (SupportCheckAction) null));
            hashMap.put("/xpilotcontrol/alc", new ElementDirectItem(XPilotItem.KEY_ALC, 0, (SupportCheckAction) null));
        }
        hashMap.put("/xpilotcontrol/ngp", new ElementDirectItem(XPilotItem.KEY_NGP, 0, xPilotXpuCheckAction));
        hashMap.put("/xpilotcontrol/cngp", new ElementDirectItem(XPilotItem.KEY_CNGP, 0, new XPilotLidarCheckAction()));
        hashMap.put("/xpilotcontrol/ngp_setting", new ElementDirectItem(XPilotItem.KEY_NGP_SETTING, 0, xPilotXpuCheckAction));
        XPilot3CheckAction xPilot3CheckAction = new XPilot3CheckAction();
        hashMap.put("/ngp_setting/truck_offset", new ElementDirectItem("truck_offset", (int) R.id.ngp_truck_offset_sw, xPilot3CheckAction));
        hashMap.put("/ngp_setting/tip_window", new ElementDirectItem("tip_window", (int) R.id.ngp_tip_window_sw, xPilot3CheckAction));
        hashMap.put("/ngp_setting/voice_change_lane", new ElementDirectItem("voice_change_lane", (int) R.id.ngp_voice_change_lane_sw, xPilot3CheckAction));
        hashMap.put("/ngp_setting/remind_mode", new ElementDirectItem("remind_mode", (int) R.id.ngp_remind_mode_layout, xPilot3CheckAction));
        hashMap.put("/xpilotcontrol/xpu_low_power", new ElementDirectItem(XPilotItem.KEY_XPU_LOW_POWER, 0, new XPilotXpuNedcCheckAction()));
        if (carBaseConfig.isSupportAuxiliaryFunc()) {
            hashMap.put("/xpilotcontrol/hdc", new ElementDirectItem(XPilotItem.KEY_HDC, 0));
            hashMap.put("/xpilotcontrol/esp", new ElementDirectItem(XPilotItem.KEY_ESP, 0));
            hashMap.put("/xpilotcontrol/avh", new ElementDirectItem(XPilotItem.KEY_AVH, 0));
            if (carBaseConfig.isSupportShowEbw()) {
                hashMap.put("/xpilotcontrol/ebw", new ElementDirectItem(XPilotItem.KEY_EBW, 0));
            }
        }
        hashMap.put("/carsituation/mileage_reset_a", new ElementDirectItem("mileage_reset_a", R.id.xls_mileage_a));
        hashMap.put("/carsituation/mileage_reset_b", new ElementDirectItem("mileage_reset_b", R.id.xls_mileage_b));
        hashMap.put("/carsituation/tyre_calibrate", new ElementDirectItem("tyre_calibrate", R.id.tyre_calibrate_btn));
        hashMap.put("/carsituation/rescue_call", new ElementDirectItem("rescue_call", R.id.btn_rescue_phone_1));
        hashMap.put("/carsituation/rescue_call_2", new ElementDirectItem("rescue_call_2", R.id.btn_rescue_phone_2));
        if (carBaseConfig.isSupportWiperRepair()) {
            hashMap.put("/carsituation/wiper_repair", new ElementDirectItem("wiper_repair", R.id.wiper_repair_sw));
        }
        return hashMap;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static HashMap<String, String> loadPathMapData() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("head_lamp", "/maincontrol");
        hashMap.put("fog_lamp", "/maincontrol");
        if (CarBaseConfig.getInstance().isSupportLightMeHomeNew()) {
            hashMap.put("light_me_home", "/carsettings");
        } else {
            hashMap.put("light_me_home", "/maincontrol");
        }
        hashMap.put("fold", "/maincontrol");
        hashMap.put("unfold", "/maincontrol");
        hashMap.put("llu_switch", "/lampcontrol");
        hashMap.put("atl_color", "/lampcontrol");
        hashMap.put("park_lamp_mode", "/lampcontrol");
        hashMap.put("ihb", "/carsettings");
        hashMap.put("/lampsetting/tip/ihb", "/carsettings/tip/ihb");
        hashMap.put(CarControl.System.DRIVE_MODE, "/drivecontrol");
        hashMap.put("recycle", "/drivecontrol");
        hashMap.put("x_pedal", "/drivecontrol");
        hashMap.put("antisickmode", "/drivecontrol");
        hashMap.put("steering_eps", "/drivecontrol");
        hashMap.put("reverse_left", "/carsettings");
        hashMap.put("reverse_right", "/carsettings");
        hashMap.put(XPilotItem.KEY_ESP, "/xpilotcontrol");
        hashMap.put("/xpilotcontrol/tip/esp", "/xpilotcontrol/tip/esp");
        hashMap.put(XPilotItem.KEY_AVH, "/xpilotcontrol");
        hashMap.put("/xpilotcontrol/tip/avh", "/xpilotcontrol/tip/avh");
        return hashMap;
    }
}
