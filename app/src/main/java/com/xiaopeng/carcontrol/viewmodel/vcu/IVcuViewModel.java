package com.xiaopeng.carcontrol.viewmodel.vcu;

import com.xiaopeng.carcontrol.viewmodel.IBaseViewModel;

/* loaded from: classes2.dex */
public interface IVcuViewModel extends IBaseViewModel {
    void controlExhibitionMode(boolean enter);

    void enterXSportDriveModeByType(int driveMode, boolean showTips);

    void enterXSportGeekSettings();

    int getAsDriveModeStatus();

    int getAvailableMileage();

    int getAvailableMileage(boolean byMileageMode);

    int getAwdSetting();

    boolean getBrakeLightOnOff();

    float getCarSpeed();

    int getChargeStatus();

    int getCreepVehSpd();

    int getDriveMode();

    int getDriveModeByUser();

    String getDriveModeSubItemInfo(int driveMode);

    int getElectricityPercent();

    int getEnergyDisableString();

    int getEnergyRecycleGrade();

    int getEnergyRecycleGradeByUser();

    int getGearLevel();

    int getMileageMode();

    boolean getNGearWarningSwitchStatus();

    boolean getNewDriveArchXPedalMode();

    boolean getParkDropdownMenuEnable();

    int getPowerResponseMode();

    boolean getSnowMode();

    boolean getSsaSw();

    boolean getTrailerModeStatus();

    boolean getVMCRwsSwitchState();

    boolean getVMCSystemState();

    boolean getVMCZWalkModeState();

    int getXSportDriveMode();

    boolean isAbsFault();

    boolean isAutoDriveModeEnabled();

    boolean isAutoEasyLoadingMode();

    boolean isBreakPedalPressed();

    boolean isCruiseActive();

    boolean isEVSysReady();

    boolean isEnergyEnable(boolean showToast);

    boolean isEvHighVolOn();

    boolean isExhibitionMode();

    boolean isExhibitionModeOn();

    boolean isQuickControlVisible();

    void setAutoDriveMode(boolean enable);

    void setAutoEasyLoadMode(boolean enable);

    void setAwdSetting(int mode);

    void setDriveMode(int driveMode);

    void setDriveMode(int driveMode, boolean audioEffect);

    void setDriveMode(int driveMode, boolean audioEffect, boolean storeEnable);

    void setDriveModeByUser(DriveMode driveMode);

    void setDriveModeByUser(DriveMode driveMode, boolean storeEnable);

    void setDriveModeSp(int driveMode);

    void setDriveModeSubItemByUser(int vcuCode);

    void setDriveModeSubItemByUser(int vcuCode, boolean customFlag);

    void setDriveModeSubItemByUser(int vcuCode, boolean customFlag, boolean igOn);

    void setDriveModeSubItemForAsMode(int vcuCode, boolean customerModeFlag);

    void setEnergyRecycleGrade(int grade);

    void setEnergyRecycleGrade(int grade, boolean storeEnable);

    void setNGearWarningSwitch(boolean enable);

    void setNewDriveArchXPedalMode(boolean enable);

    void setNewDriveArchXPedalMode(boolean enable, boolean storeEnable);

    void setParkDropdownMenuEnable(boolean enable, boolean fromNapa);

    void setPowerResponseMode(int mode);

    void setQuickControlVisible(boolean visible);

    void setSnowMode(boolean enable);

    void setSsaSwEnable(boolean enable);

    void setTrailerMode(boolean enable);

    void setVMCRwsSwitch(boolean enable);

    void setVMCZWalkModeSwitch(boolean enable);

    void setXSportDriveMode(int mode);
}
