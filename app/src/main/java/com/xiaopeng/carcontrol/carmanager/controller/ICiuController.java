package com.xiaopeng.carcontrol.carmanager.controller;

import com.xiaopeng.carcontrol.carmanager.IBaseCallback;
import com.xiaopeng.carcontrol.carmanager.IBaseCarController;

/* loaded from: classes.dex */
public interface ICiuController extends IBaseCarController<Callback> {
    public static final int CIU_RAIN_SW_AUTO = 0;
    public static final int CIU_RAIN_SW_LEVEL1 = 1;
    public static final int CIU_RAIN_SW_LEVEL2 = 2;
    public static final int CIU_RAIN_SW_LEVEL3 = 3;
    public static final int CIU_RAIN_SW_LEVEL4 = 4;
    public static final int DISTRACTION_OFF = 0;
    public static final int DISTRACTION_ON = 1;
    public static final int DISTRACT_STATUS_OFF = 2;
    public static final int DISTRACT_STATUS_ON = 1;
    public static final int DMS_OFF = 0;
    public static final int DMS_ON = 1;
    public static final int DMS_STATUS_OFF = 1;
    public static final int DMS_STATUS_ON = 2;
    public static final int FACE_SW_STATUS_OFF = 0;
    public static final int FACE_SW_STATUS_ON = 1;
    public static final int FATIGUE_OFF = 0;
    public static final int FATIGUE_ON = 1;
    public static final int FATIGUE_STATUS_OFF = 2;
    public static final int FATIGUE_STATUS_ON = 1;

    /* loaded from: classes.dex */
    public interface Callback extends IBaseCallback {
        default void onCiuWiperAutoSwitchChanged(boolean enable) {
        }

        default void onCiuWiperIntervalChanged(int level) {
        }

        default void onDistractSwChanged(boolean enabled) {
        }

        default void onDmsSwChanged(boolean enabled) {
        }

        default void onFaceIdSwChanged(boolean enabled) {
        }

        default void onFatigueSwChanged(boolean enabled) {
        }
    }

    void controlDistractSwitch(boolean enable, boolean needSave);

    void controlDmsSwitch(boolean enable);

    void controlFaceIdSwitch(boolean enable, boolean needSave);

    void controlFatigueSwitch(boolean enable, boolean needSave);

    int getCiuWiperLevel();

    boolean getDistractSw();

    boolean getDmsSw();

    boolean getFaceIdSw();

    boolean getFatigueSw();

    boolean isCiuRainEnable();

    boolean isCiuValid();

    void setCiuRainEnable(boolean enable);

    void setCiuWiperLevel(int level);

    void setDistractSw(boolean enable);

    void setDmsSw(boolean enable);

    void setFaceIdSw(boolean enable);

    void setFatigueSw(boolean enable);

    void setMultiDms(boolean dmsSw, boolean faceIdSw, boolean fatigueSw, boolean distractSw);
}
