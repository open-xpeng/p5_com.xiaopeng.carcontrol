package com.xiaopeng.carcontrol.bean.xpilot;

import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.R;
import com.xiaopeng.carcontrol.bean.xpilot.category.IXPilotCategory;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class XPilotCategory implements IXPilotCategory {
    private final List<XPilotItem> mCategoryList;
    private final Map<String, XPilotItem> mKeyItemMap;

    /* loaded from: classes.dex */
    private static class SingleHolder {
        private static final XPilotCategory sInstance = new XPilotCategory();

        private SingleHolder() {
        }
    }

    public static XPilotCategory getInstance() {
        return SingleHolder.sInstance;
    }

    private XPilotCategory() {
        this.mCategoryList = new ArrayList();
        this.mKeyItemMap = new HashMap();
        initXPilotCategory();
    }

    @Override // com.xiaopeng.carcontrol.bean.xpilot.category.IXPilotCategory
    public List<XPilotItem> getXPilotList() {
        return this.mCategoryList;
    }

    private void initXPilotCategory() {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        this.mCategoryList.clear();
        CarBaseConfig carBaseConfig = CarBaseConfig.getInstance();
        int i9 = 0;
        if (carBaseConfig.isSupportActiveSafety()) {
            this.mCategoryList.add(new XPilotCategoryItem(0, XPilotItem.KEY_CATE_SAFE, R.string.active_safe_module_title));
            this.mCategoryList.add(new XPilotCardItem(1, XPilotItem.KEY_FCW, (int) R.string.fcw_feature_title, (int) R.string.fcw_feature_desc, (int) R.drawable.img_x_pilot_fcw, 0, (int) R.drawable.img_xpilot_fcw_1, (int) R.string.fcw_feature_help_1, (int) R.drawable.img_xpilot_fcw_2, (int) R.string.fcw_feature_help_2, (int) R.string.fcw_feature_keyword));
            if (carBaseConfig.isSupportLka()) {
                i = 2;
            } else {
                this.mCategoryList.add(new XPilotCardItem(2, XPilotItem.KEY_LDW, (int) R.string.ldw_feature_title, (int) R.string.ldw_feature_desc, (int) R.drawable.img_x_pilot_ldw, 1, (int) R.drawable.img_xpilot_ldw_1, (int) R.string.ldw_feature_help_1, (int) R.drawable.img_xpilot_ldw_2, (int) R.string.ldw_feature_help_2, (int) R.string.ldw_feature_keyword));
                i = 3;
            }
            if (carBaseConfig.isSupportElk()) {
                this.mCategoryList.add(new XPilotCardItem(i, XPilotItem.KEY_ELK, (int) R.string.elk_feature_title, (int) R.string.elk_feature_desc, (int) R.drawable.img_x_pilot_ldw, 15, (int) R.drawable.img_xpilot_elk_1, (int) R.string.elk_feature_help_1, 0, 0, (int) R.string.elk_feature_keyword));
                i2 = i + 1;
            } else {
                i2 = i;
            }
            int i10 = i2 + 1;
            this.mCategoryList.add(new XPilotCardItem(i2, XPilotItem.KEY_BSD, (int) R.string.bsd_feature_title, (int) R.string.bsd_feature_desc, (int) R.drawable.img_x_pilot_bsd, 2, (int) R.drawable.img_xpilot_bsd_1, (int) R.string.bsd_feature_help_1, 0, 0, (int) R.string.bsd_feature_keyword));
            int i11 = i10 + 1;
            this.mCategoryList.add(new XPilotCardItem(i10, XPilotItem.KEY_RCTA, (int) R.string.rcta_feature_title, (int) R.string.rcta_feature_desc, (int) R.drawable.img_x_pilot_rcta, 3, (int) R.drawable.img_xpilot_rcta_1, (int) R.string.rcta_feature_help_1, 0, 0, (int) R.string.rcta_feature_keyword));
            if (carBaseConfig.isSupportDow()) {
                this.mCategoryList.add(new XPilotCardItem(i11, XPilotItem.KEY_DOW, (int) R.string.dow_feature_title, (int) R.string.dow_feature_desc, (int) R.drawable.img_x_pilot_dow, 12, (int) R.drawable.img_xpilot_bsd_1, (int) R.string.dow_feature_help_1, 0, 0, (int) R.string.dow_feature_keyword));
                i3 = i11 + 1;
            } else {
                i3 = i11;
            }
            if (carBaseConfig.isSupportRcw()) {
                this.mCategoryList.add(new XPilotCardItem(i3, XPilotItem.KEY_RCW, (int) R.string.rcw_feature_title, (int) R.string.rcw_feature_desc, (int) R.drawable.img_x_pilot_rcw, 13, (int) R.drawable.img_xpilot_rcw_1, (int) R.string.rcw_feature_help_1, 0, 0, (int) R.string.rcw_feature_keyword));
                i4 = i3 + 1;
            } else {
                i4 = i3;
            }
            if (carBaseConfig.isSupportNra()) {
                this.mCategoryList.add(new XPilotTabItem(i4, XPilotItem.KEY_NRA, R.string.nra_feature_title, R.string.nra_feature_desc, ResUtils.getStringArray(R.array.xpilot_nra_type)));
                i5 = i4 + 1;
            } else {
                i5 = i4;
            }
            if (carBaseConfig.isSupportIslc() && carBaseConfig.isSupportIslcInActive()) {
                this.mCategoryList.add(new XPilotCardItem(i5, XPilotItem.KEY_ISLC, (int) R.string.islc_feature_title, (int) R.string.islc_feature_desc, (int) R.drawable.img_x_pilot_islc, 4, (int) R.drawable.img_xpilot_islc_1, (int) R.string.islc_feature_help_1, 0, 0, (int) R.string.islc_feature_keyword));
                i6 = i5 + 1;
            } else {
                i6 = i5;
            }
            if (carBaseConfig.isSupportLka()) {
                this.mCategoryList.add(new XPilotTabItem(i6, XPilotItem.KEY_LSS, R.string.lka_feature_title, R.string.lka_feature_desc, ResUtils.getStringArray(R.array.scu_lss_type), 14, R.drawable.img_xpilot_elk_1, R.string.lka_feature_help_1, 0, 0, R.string.lka_feature_keyword));
                i7 = i6 + 1;
            } else {
                i7 = i6;
            }
            if (carBaseConfig.isSupportIsla()) {
                int i12 = i7 + 1;
                this.mCategoryList.add(new XPilotTabItem(i7, XPilotItem.KEY_ISLA, R.string.isla_feature_title, R.string.isla_feature_desc, ResUtils.getStringArray(R.array.scu_isla_type), 30, R.drawable.img_xpilot_islc_1, R.string.isla_feature_help_1, R.drawable.img_xpilot_laa_2, R.string.isla_feature_help_2, R.string.isla_feature_keyword));
                int i13 = i12 + 1;
                this.mCategoryList.add(new XPilotCardItem(i12, XPilotItem.KEY_ISLA_SETTING, R.string.isla_setting_title, R.string.isla_setting_desc, R.drawable.img_xpilot_islc_setting, -1, 0, 0, 0, 0, 0, R.string.isla_setting_button, false));
                this.mCategoryList.add(new XPilotEmptyCardItem(i13, XPilotItem.KEY_EMPTY, 0, 0, 0, 0));
                i7 = i13 + 1;
            }
            if (carBaseConfig.isSupportShowAutoPark()) {
                int i14 = i7 + 1;
                this.mCategoryList.add(new XPilotCategoryItem(i7, XPilotItem.KEY_CATE_PARK, R.string.auto_park_module_title));
                int i15 = i14 + 1;
                this.mCategoryList.add(new XPilotCardItem(i14, "auto_park", R.string.auto_park_sw, 0, R.drawable.img_x_pilot_park, 5, 0, R.string.auto_park_feature_help_1, 0, 0, R.string.auto_park_keyword, carBaseConfig.isSupportXPilotSafeExam() ? R.string.laa_study_button : 0, true));
                if (carBaseConfig.isSupportMemPark()) {
                    i7 = i15 + 1;
                    this.mCategoryList.add(new XPilotCardItem(i15, XPilotItem.KEY_MEM_PARK, (int) R.string.mem_park_feature_title, (int) R.string.mem_park_feature_desc, (int) R.drawable.img_x_pilot_memory_park, 19, 0, (int) R.string.mem_park_feature_help_1, 0, (int) R.string.mem_park_feature_help_2, (int) R.string.mem_park_keyword));
                } else {
                    i7 = i15;
                }
            }
            if (carBaseConfig.isSupportLcc()) {
                int i16 = i7 + 1;
                this.mCategoryList.add(new XPilotCategoryItem(i7, XPilotItem.KEY_CATE_DRIVE, R.string.auto_drive_module_title));
                i7 = i16 + 1;
                this.mCategoryList.add(new XPilotCardItem(i16, XPilotItem.KEY_LCC, (int) R.string.laa_feature_title, (int) R.string.laa_feature_desc, (int) R.drawable.img_x_pilot_laa, 6, (int) R.drawable.img_xpilot_laa_1, (int) R.string.laa_feature_help_1, (int) R.drawable.img_xpilot_laa_2, (int) R.string.laa_feature_help_2, (int) R.string.laa_feature_keyword, carBaseConfig.isSupportXPilotSafeExam() ? R.string.laa_study_button : 0, true, (int) R.string.laa_feature_vui_label));
            }
            int i17 = i7;
            if (carBaseConfig.isSupportAlc()) {
                this.mCategoryList.add(new XPilotCardItem(i17, XPilotItem.KEY_ALC, (int) R.string.lca_feature_title, (int) R.string.lca_feature_desc, (int) R.drawable.img_x_pilot_lca, 7, (int) R.drawable.img_xpilot_alc_1, ResUtils.getString(R.string.lca_feature_help_1, Integer.valueOf(BaseFeatureOption.getInstance().getAlcSpeed())), (int) R.drawable.img_xpilot_alc_2, ResUtils.getString(R.string.lca_feature_help_2), (int) R.string.lca_feature_keyword, 0, true, (int) R.string.lca_feature_vui_label));
                i8 = i17 + 1;
            } else {
                i8 = i17;
            }
            if (carBaseConfig.isSupportNgp()) {
                int i18 = i8 + 1;
                this.mCategoryList.add(new XPilotCardItem(i8, XPilotItem.KEY_NGP, (int) R.string.ngp_feature_title, (int) R.string.ngp_feature_desc, (int) R.drawable.img_x_pilot_ngp_not_activated, 20, 0, (int) R.string.ngp_feature_help_1, (int) R.drawable.img_xpilot_ngp_2, (int) R.string.ngp_feature_help_2, (int) R.string.ngp_feature_keyword));
                if (carBaseConfig.isSupportCNgp()) {
                    int i19 = i18 + 1;
                    this.mCategoryList.add(new XPilotCardItem(i18, XPilotItem.KEY_CNGP, (int) R.string.cngp_feature_title, (int) R.string.cngp_feature_desc, (int) R.drawable.img_x_pilot_ngp_not_activated, 33, 0, (int) R.string.cngp_feature_help_1, (int) R.drawable.img_xpilot_ngp_2, (int) R.string.cngp_feature_help_2, (int) R.string.cngp_feature_keyword));
                    i18 = i19 + 1;
                    this.mCategoryList.add(new XPilotCardItem(i19, XPilotItem.KEY_CNGP_MAP, R.string.cngp_map_title, R.string.cngp_map_desc, R.drawable.img_x_pilot_ngp_setting, -1, 0, 0, 0, 0, 0, R.string.ngp_setting_button, false));
                }
                int i20 = i18;
                i8 = i20 + 1;
                this.mCategoryList.add(new XPilotCardItem(i20, XPilotItem.KEY_NGP_SETTING, CarBaseConfig.getInstance().isSupportCrossBarriers() ? R.string.ngp_lcc_setting_title : R.string.ngp_setting_title, R.string.ngp_setting_desc, R.drawable.img_x_pilot_ngp_setting, -1, 0, 0, 0, 0, 0, R.string.ngp_setting_button, false));
            }
            int i21 = i8;
            if (CarBaseConfig.getInstance().isSupportXPilotTtsCfg()) {
                this.mCategoryList.add(new XPilotTabItem(i21, "tts", R.string.laboratory_tts_broadcast_type, R.string.laboratory_tts_broadcast_tips, App.getInstance().getResources().getStringArray(R.array.laboratory_tts_broadcast_type)));
                i9 = i21 + 1;
            } else {
                i9 = i21;
            }
            if (carBaseConfig.isSupportXpuNedc()) {
                int i22 = i9 + 1;
                this.mCategoryList.add(new XPilotCategoryItem(i9, XPilotItem.KEY_CATE_OTHER_SETTINGS, R.string.xpilot_other_settings_module_title));
                this.mCategoryList.add(new XPilotCardItem(i22, XPilotItem.KEY_XPU_LOW_POWER, R.string.xpu_low_power_feature_title, R.string.xpu_low_power_feature_desc, R.drawable.img_x_pilot_eco, -1));
                i9 = i22 + 1;
            }
        }
        if (carBaseConfig.isSupportAuxiliaryFunc()) {
            int i23 = i9 + 1;
            this.mCategoryList.add(new XPilotCategoryItem(i9, XPilotItem.KEY_CATE_AUXILIARY_SAFE, R.string.auxiliary_safe_module_title));
            int i24 = i23 + 1;
            this.mCategoryList.add(new XPilotCardItem(i23, XPilotItem.KEY_HDC, (int) R.string.hdc_feature_title, (int) R.string.hdc_feature_desc, (int) R.drawable.img_x_pilot_hdc, 8, (int) R.drawable.xpilot_hdc_1, (int) R.string.hdc_feature_help_1, 0, 0, (int) R.string.hdc_feature_keyword));
            int i25 = i24 + 1;
            this.mCategoryList.add(new XPilotCardItem(i24, XPilotItem.KEY_ESP, (int) R.string.esp_feature_title, (int) R.string.esp_feature_desc, (int) R.drawable.img_x_pilot_esp, 9, (int) R.drawable.xpilot_esp_1, (int) R.string.esp_feature_help_1, 0, 0, (int) R.string.esp_feature_keyword));
            int i26 = i25 + 1;
            this.mCategoryList.add(new XPilotCardItem(i25, XPilotItem.KEY_AVH, (int) R.string.avh_feature_title, (int) R.string.avh_feature_desc, (int) R.drawable.img_x_pilot_avh, 10, (int) R.drawable.xpilot_avh_1, (int) R.string.avh_feature_help_1, 0, 0, (int) R.string.avh_feature_keyword));
            if (carBaseConfig.isSupportShowEbw()) {
                i9 = i26 + 1;
                this.mCategoryList.add(new XPilotCardItem(i26, XPilotItem.KEY_EBW, (int) R.string.ebw_feature_title, (int) R.string.ebw_feature_desc, (int) R.drawable.img_x_pilot_ebw, 11, (int) R.drawable.xpilot_ebw_1, (int) R.string.ebw_feature_help_1, 0, 0, (int) R.string.ebw_feature_keyword));
            } else {
                i9 = i26;
            }
        }
        LogUtils.d("XPilot", "found " + i9 + " items");
    }

    @Override // com.xiaopeng.carcontrol.bean.xpilot.category.IXPilotCategory
    public XPilotItem getItemByKey(String key) {
        XPilotItem xPilotItem = this.mKeyItemMap.get(key);
        if (xPilotItem == null) {
            for (XPilotItem xPilotItem2 : this.mCategoryList) {
                if (key.equals(xPilotItem2.getKey())) {
                    this.mKeyItemMap.put(key, xPilotItem2);
                    return xPilotItem2;
                }
            }
            return xPilotItem;
        }
        return xPilotItem;
    }

    @Override // com.xiaopeng.carcontrol.bean.xpilot.category.IXPilotCategory
    public List<String> getXPilotSrrRelatedList() {
        ArrayList arrayList = new ArrayList();
        if (CarBaseConfig.getInstance().isSupportXpu()) {
            arrayList.add(XPilotItem.KEY_FCW);
            arrayList.add(XPilotItem.KEY_BSD);
            arrayList.add(XPilotItem.KEY_DOW);
            arrayList.add(XPilotItem.KEY_RCW);
            arrayList.add(XPilotItem.KEY_RCTA);
            arrayList.add(XPilotItem.KEY_ELK);
            arrayList.add(XPilotItem.KEY_LSS);
            arrayList.add(XPilotItem.KEY_MEM_PARK);
            arrayList.add(XPilotItem.KEY_LCC);
            arrayList.add(XPilotItem.KEY_ALC);
            arrayList.add(XPilotItem.KEY_NGP);
            arrayList.add(XPilotItem.KEY_NGP_SETTING);
        }
        return arrayList;
    }
}
