package com.xiaopeng.carcontrol.carmanager.controller;

import com.xiaopeng.carcontrol.carmanager.IBaseCallback;
import com.xiaopeng.carcontrol.carmanager.IBaseCarController;

/* loaded from: classes.dex */
public interface IAtlController extends IBaseCarController<Callback> {
    public static final int ATL_ACTIVE = 1;
    public static final int ATL_DUAL_COLOR_MAX = 6;
    public static final int ATL_DUAL_COLOR_MIN = 0;
    public static final String ATL_EFFECT_BREATH = "gentle_breathing";
    public static final String ATL_EFFECT_MUSIC = "music";
    public static final String ATL_EFFECT_NONE = "stable_effect";
    public static final String ATL_EFFECT_SPEED = "follow_speed";
    public static final int ATL_SINGLE_COLOR_MAX = 20;
    public static final int ATL_SINGLE_COLOR_MIN = 1;
    public static final int ATL_SPEAKER_SW_CLOSE = 0;
    public static final int ATL_SPEAKER_SW_DEFAULT = 0;
    public static final int ATL_SPEAKER_SW_OPEN = 1;
    public static final String ATL_SW_KEY = "isAmbientLightOpen";

    /* loaded from: classes.dex */
    public interface Callback extends IBaseCallback {
        void onAtlAutoBrightSwChanged(boolean enabled);

        void onAtlDoubleColorChanged(String effectType, int first_color, int second_color);

        void onAtlDoubleColorEnableChanged(String effectType, boolean enable);

        void onAtlEffectTypeChanged(String type);

        void onAtlManualBrightChanged(int brightness);

        void onAtlMonoColorChanged(String effectType, int color);

        void onAtlSpeakerSwChanged(boolean enable);

        void onAtlSwChanged(boolean enabled);
    }

    int getAtlBrightness();

    int getAtlDualFirstColor(String effect);

    int getAtlDualSecondColor(String effect);

    String getAtlEffect();

    int getAtlSingleColor(String effect);

    boolean isAtlAutoBrightEnabled();

    boolean isAtlDualColor(String effect);

    boolean isAtlSpeakerSwEnabled();

    boolean isAtlSwEnabled();

    boolean isSupportDoubleThemeColor(String effectType);

    void setAtlAutoBrightEnable(boolean enable);

    void setAtlBrightness(int brightness, boolean force);

    void setAtlDualColor(String effect, int color1, int color2, boolean force);

    void setAtlDualColor(String effect, int color1, int color2, boolean force, boolean needSave);

    void setAtlDualColor(String effect, boolean dualColor, boolean force);

    void setAtlEffect(String effect, boolean force);

    void setAtlEffect(String effect, boolean force, boolean needSave);

    void setAtlSingleColor(String effect, int color, boolean force);

    void setAtlSingleColor(String effect, int color, boolean force, boolean needSave);

    void setAtlSpeakerSwEnable(boolean enable);

    void setAtlSwEnable(boolean enable);
}
