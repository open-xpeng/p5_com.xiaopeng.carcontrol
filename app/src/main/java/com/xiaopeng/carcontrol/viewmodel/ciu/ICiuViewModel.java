package com.xiaopeng.carcontrol.viewmodel.ciu;

import com.xiaopeng.carcontrol.viewmodel.IBaseViewModel;

/* loaded from: classes2.dex */
public interface ICiuViewModel extends IBaseViewModel {
    public static final int SETTING_BRIGHTNESS_MODE_DISABLE = 0;
    public static final int SETTING_BRIGHTNESS_MODE_ENABLE = 1;
    public static final String SETTING_KEY_SCREEN_BRIGHTNESS_MODE = "screen_brightness_mode";

    boolean isCiuExist();

    boolean isDistractSwEnabled();

    boolean isDmsSwEnabled();

    boolean isFaceIdSwEnabled();

    boolean isFatigueSwEnabled();

    void setDistractSwEnable(boolean enable);

    void setDmsSwEnable(boolean enable);

    void setFaceIdSwEnable(boolean enable);

    void setFatigueSwEnable(boolean enable);
}
