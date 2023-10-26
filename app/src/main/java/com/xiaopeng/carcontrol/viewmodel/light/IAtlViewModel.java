package com.xiaopeng.carcontrol.viewmodel.light;

import com.xiaopeng.carcontrol.viewmodel.IBaseViewModel;

/* loaded from: classes2.dex */
public interface IAtlViewModel extends IBaseViewModel {
    void enterMeditationState();

    void exitMeditationState();

    int getAtlBrightnessValue();

    int getAtlDualFirstColor();

    int getAtlDualSecondColor();

    String getAtlEffect();

    int getAtlSingleColor();

    boolean getAtlThemeColorMode();

    boolean isAtlAutoBrightness();

    boolean isAtlEnabled();

    boolean isAtlSpeakerSwEnabled();

    boolean isSupportDoubleThemeColor(String effectType);

    void setAtlAutoBrightness(boolean enable);

    void setAtlBrightnessValue(int brightness);

    void setAtlDualColor(int color1, int color2);

    void setAtlDualColor(int color1, int color2, boolean needSave);

    void setAtlEffect(String effect);

    void setAtlSingleColor(int color);

    void setAtlSingleColor(int color, boolean force);

    void setAtlSingleColor(int color, boolean force, boolean needSave);

    void setAtlSpeakerSwEnabled(boolean enable);

    void setAtlSwitch(boolean enable);

    void setAtlThemeColorMode(boolean isDualColor);
}
