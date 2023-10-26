package com.xiaopeng.carcontrol.statistic;

import com.xiaopeng.lib.framework.netchannelmodule.common.GlobalConfig;

/* loaded from: classes2.dex */
public enum PageEnum {
    MAIN_PAGE("P00001", "主页面"),
    CONTROL_PAGE("P00002", "车辆控制"),
    DRIVE_PAGE("P00003", "驾驶体验"),
    SETTING_PAGE("P00004", "车辆设置"),
    XPILOT_PAGE("P00005", "辅助驾驶"),
    STATUS_PAGE("P00006", "车辆状况"),
    STATUS_E28_PAGE("P10007", "车辆状况"),
    USERGUIDE_PAGE("P00007", "用户手册"),
    HVAC_PAGE("P00008", "空调控制"),
    LAMP_PAGE("P00011", "灯语/照明页面"),
    SETTING_OTHER_PAGE("P00022", "设置-其它"),
    SETTING_HVAC_PAGE("P00020", "设置-空调"),
    SETTING_WIN_LOCK_PAGE("P00021", "设置-门窗锁"),
    SETTING_CUSTOM_KEY_PAGE("P00023", "设置-智能/摄像头"),
    SETTING_PSN_DOOR_PAGE("P00018", "设置-门板按键"),
    SETTING_X_KEY_PAGE("P00016", "设置-X按键"),
    DRIVE_SETTING_PAGE("P00014", "驾驶页面"),
    SEAT_SAVE_SMALL_PAGE("P00024", "座椅保存小弹窗"),
    SEAT_ADJUST_DIALOG_PAGE("P00025", "座椅位置保存"),
    WINDOW_ADJUST_DIALOG_PAGE("P00017", "车窗调节弹窗"),
    MIRROR_ADJUST_DIALOG_PAGE("P00019", "后视镜调节弹窗"),
    MAIN_CONTROL_PAGE("P00010", "常用页面"),
    HVAC_PANEL_PAGE("P00001", "空调面板"),
    ATL_PAGE("P00012", "氛围灯页面"),
    XPILOT_E28_SETTING_PAGE(GlobalConfig.MOLE_PAGE_ID, "辅助驾驶页面"),
    KEY_EVENT_PAGE("P00006", "硬按键页面"),
    SETTING_E38_PAGE("P10232", "车辆设置界面"),
    SETTING_E38_CHILD_PAGE("P10293", "儿童安全模式界面"),
    MAIN_CONTROL_PAGE_NEW("P10290", "新车型常用页面"),
    LAMP_NEW_PAGE("P10231", "新车外灯界面"),
    SPACE_CAPSULE_PAGE("P10138", "太空舱页面"),
    SPACE_CAPSULE_SLEEP_PAGE("P10139", "睡眠空间"),
    SPACE_CAPSULE_CINEMA_PAGE("P10140", "观影空间"),
    SPACE_CAPSULE_VIP_SEAT_PAGE("P10086", "座椅放平"),
    CAR_LIFE_PAGE("P10133", "百变智能空间"),
    MEDITATION_PAGE("P10421", "冥想空间"),
    MEDITATION_MUSIC_PAGE("P10422", "冥想空间音乐"),
    HVAC_NEW_PAGE("P10244", "空调面板"),
    ATL_NEW_PAGE("P11116", "氛围灯页面"),
    SETTING_NEW_PAGE("P10232", "车控_车辆设置"),
    XPILOT_E38_SETTING_PAGE("P10298", "辅助驾驶页面"),
    XPILOT_E38_NGP_PAGE("P10300", "自定义NGP页面"),
    CAR_KEY_PAGE("P10247", "钥匙失效弹框"),
    ASHEIGHT_SETTING_PAGE_E38("P10303", "悬架调节页面"),
    WINDOW_SETTING_PAGE_E38("P10297", "车窗调节页面"),
    SEAT_ADJUSTMENT_PAGE_E38("P10292", "座椅调节页面"),
    SPACE_CAPSULE_E38_SLEEP_PAGE("P10863", "睡眠空间"),
    SPACE_CAPSULE_E38_CINEMA_PAGE("P10864", "观影空间"),
    SPACE_CAPSULE_E38_SLEEP_CINEMA_PAGE("P10865", "睡眠/观影空间"),
    AIRBED_CAPSULE_E38_SLEEP_PAGE("P10866", "睡眠空间/床垫"),
    AIRBED_E38_PAGE("P10867", "智能床垫");
    
    private String mPageId;
    private String mPageName;

    PageEnum(String pageId, String pageName) {
        this.mPageId = pageId;
        this.mPageName = pageName;
    }

    public String getPageId() {
        return this.mPageId;
    }

    public void setPageId(String pageId) {
        this.mPageId = pageId;
    }

    public String getPageName() {
        return this.mPageName;
    }

    public void setPageName(String pageName) {
        this.mPageName = pageName;
    }
}
