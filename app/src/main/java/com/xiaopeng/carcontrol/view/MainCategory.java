package com.xiaopeng.carcontrol.view;

import com.xiaopeng.carcontrol.R;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.model.DebugFuncModel;
import com.xiaopeng.carcontrol.speech.VuiManager;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.view.fragment.CiuControlFragment;
import com.xiaopeng.carcontrol.view.fragment.DebugFragment;
import com.xiaopeng.carcontrol.view.fragment.DriveExperienceFragment;
import com.xiaopeng.carcontrol.view.fragment.LluFragment;
import com.xiaopeng.carcontrol.view.fragment.MainControlFragment;
import com.xiaopeng.carcontrol.view.fragment.impl.SettingsFragment;
import com.xiaopeng.carcontrol.view.fragment.impl.SituationFragment;
import com.xiaopeng.carcontrol.view.fragment.xpilot.XPilotFragment;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
final class MainCategory {
    static final int CAR_CIU = 5;
    static final int CAR_SETTING = 3;
    static final int CAR_STATUS = 6;
    static final String CATEGORY_INDEX = "category_index";
    static final int DEBUG_TAB = 7;
    static final int DEFAULT_INDEX = 0;
    static final int DRIVE_CONTROL = 2;
    static final int INDEX_MAX = 7;
    static final int LLU_CONTROL = 1;
    static final int MAIN_CONTROL = 0;
    private static final String TAG = "MainCategory";
    static final int X_PILOT = 4;
    private static final int[] ITEM_INDEX = {0, 1, 2, 3, 4, 5, 6, 7};
    private static final int[] ITEM_TITLE = {R.string.title_control, R.string.title_llu_control, R.string.title_drive_control, R.string.title_car_settings, R.string.title_x_pilot, R.string.title_car_ciu, R.string.title_car_status, R.string.title_debug};
    private static final int[] ITEM_ICON = {R.drawable.ic_mid_quick_control, R.drawable.ic_mid_lighting, R.drawable.ic_mid_drive_control, R.drawable.ic_mid_car_setting, R.drawable.ic_mid_xpilot, R.drawable.ic_mid_intelligent_vision_system, R.drawable.ic_mid_car_status, R.drawable.ic_mid_quick_control};
    private static final Class[] ITEM_FRAGMENT = {MainControlFragment.class, LluFragment.class, DriveExperienceFragment.class, SettingsFragment.class, XPilotFragment.class, CiuControlFragment.class, SituationFragment.class, DebugFragment.class};
    private static final String[] ITEM_VUI_SCENE_ID = {VuiManager.SCENE_MAIN_CONTROL, VuiManager.SCENE_LAMP_EFFECT, VuiManager.SCENE_DRIVE, VuiManager.SCENE_CAR_SETTING, VuiManager.SCENE_X_PILOT, VuiManager.SCENE_CIU_SETTING, VuiManager.SCENE_CAR_SITUATION, null};

    MainCategory() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static List<MainCategoryItem> getCategoryItems() {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        int i2 = 0;
        while (true) {
            int[] iArr = ITEM_INDEX;
            if (i >= iArr.length) {
                return arrayList;
            }
            int i3 = iArr[i];
            if (i3 == 1 && !CarBaseConfig.getInstance().isSupportLlu() && !CarBaseConfig.getInstance().isSupportAtl()) {
                LogUtils.w(TAG, "Current car not support LLU and ATL feature", false);
            } else if (i3 == 5 && !CarBaseConfig.getInstance().isSupportCiuConfig()) {
                LogUtils.w(TAG, "Current car not support CIU feature", false);
            } else if (i3 == 7 && (!CarBaseConfig.isSupportShowDebug() || !DebugFuncModel.getInstance().isDebugTabEnabled())) {
                LogUtils.w(TAG, "debug tab is not support", false);
            } else {
                arrayList.add(createCategoryItem(i2, i3, i));
                i2++;
            }
            i++;
        }
    }

    private static MainCategoryItem createCategoryItem(int position, int index, int itemIndex) {
        return new MainCategoryItem(position, index, ITEM_TITLE[itemIndex], ITEM_ICON[itemIndex], ITEM_FRAGMENT[itemIndex], ITEM_VUI_SCENE_ID[itemIndex]);
    }

    public static int getItemIdxByClassName(Object data) {
        if (data instanceof String) {
            int length = ITEM_FRAGMENT.length;
            for (int i = 0; i < length; i++) {
                if (ITEM_FRAGMENT[i].getName().equals(data)) {
                    return ITEM_INDEX[i];
                }
            }
        }
        return 0;
    }
}
