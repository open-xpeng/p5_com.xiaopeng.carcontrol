package com.xiaopeng.carcontrol.carmanager.controller;

import com.xiaopeng.carcontrol.carmanager.IBaseCallback;
import com.xiaopeng.carcontrol.carmanager.IBaseCarController;
import com.xiaopeng.carcontrol.util.CarStatusUtils;

/* loaded from: classes.dex */
public interface IVcuController extends IBaseCarController<Callback> {
    public static final int AS_DRVIVING_MODE_COMFORT_STATUS = 1;
    public static final int AS_DRVIVING_MODE_CUSTOMER_MODE_STATUS = 4;
    public static final int AS_DRVIVING_MODE_ECO_STATUS = 0;
    public static final int AS_DRVIVING_MODE_NORMAL_STATUS = 2;
    public static final int AS_DRVIVING_MODE_NOT_VALID_STATUS = 6;
    public static final int AS_DRVIVING_MODE_OFF_ROAD_STATUS = 5;
    public static final int AS_DRVIVING_MODE_SPORT_STATUS = 3;
    public static final int AWDSETTING_MODE_FIVE_FIVE = 2;
    public static final int AWDSETTING_MODE_SEVEN_THREE = 1;
    public static final int AWDSETTING_MODE_TEN_ZERO = 0;
    public static final int AWDSETTING_MODE_THREE_SEVEN = 3;
    public static final int AWDSETTING_MODE_ZERO_TEN = 4;
    public static final int BREAK_PEDAL_NOT_PRESSED = 0;
    public static final int BREAK_PEDAL_PRESSED = 1;
    public static final int CHARGE_GUN_AC_AND_DC_LINK = 3;
    public static final int CHARGE_GUN_AC_LINK = 1;
    public static final int CHARGE_GUN_DC_LINK = 2;
    public static final int CHARGE_GUN_INVALID = 7;
    public static final int CHARGE_GUN_NO_LINK = 0;
    public static final int CHARGE_GUN_V2L_LINK = 4;
    public static final int CHARGE_GUN_V2L_LINK_LOW_POWER_OBC = 5;
    public static final int CHARGE_STATUS_APPOINTMENT = 1;
    public static final int CHARGE_STATUS_CHARGE_DONE = 4;
    public static final int CHARGE_STATUS_CHARGE_ERROR = 3;
    public static final int CHARGE_STATUS_CHARGING = 2;
    public static final int CHARGE_STATUS_DISCHARGE_DONE = 6;
    public static final int CHARGE_STATUS_DISCHARGE_ERROR = 7;
    public static final int CHARGE_STATUS_DISCHARGING = 5;
    public static final int CHARGE_STATUS_ERROR_D21 = 20;
    public static final int CHARGE_STATUS_FULLY_CHARGED_D21 = 16;
    public static final int CHARGE_STATUS_PREPARE = 0;
    public static final int CHARGE_STATUS_REMOVE_CHARGER_D21 = 17;
    public static final int CHARGE_STATUS_STOPPING_D21 = 19;
    public static final int CHARGE_STATUS_WRONG_OP_D21 = 18;
    public static final int CLTC_MODE = 2;
    public static final float CONSUMPTION_CLTC = 11.4f;
    public static final float CONSUMPTION_NEDC = 11.8f;
    public static final float CONSUMPTION_WLTP = 13.7f;
    public static final int DRIVE_MODE_ADAPTIVE = 14;
    public static final int DRIVE_MODE_ANTI_SICKNESS_OFF = 8;
    public static final int DRIVE_MODE_ANTI_SICKNESS_ON = 7;
    public static final int DRIVE_MODE_COMFORT = 0;
    public static final int DRIVE_MODE_ECO = 1;
    public static final int DRIVE_MODE_ECO_PLUS_OFF = 6;
    public static final int DRIVE_MODE_ECO_PLUS_ON = 5;
    public static final int DRIVE_MODE_GEEK = 1001;
    public static final int DRIVE_MODE_INVALID = -1;
    public static final int DRIVE_MODE_NORMAL = 10;
    public static final int DRIVE_MODE_NO_COMMAND = 12;
    public static final int DRIVE_MODE_OFFROAD = 16;
    public static final int DRIVE_MODE_SHOW_MODE;
    public static final int DRIVE_MODE_SPORT = 2;
    public static final int DRIVE_MODE_USER_DEFINE = 11;
    public static final int DRIVING_MODE_STATUS_COMFORT_V2 = 7;
    public static final int GEAR_LEVEL_D = 1;
    public static final int GEAR_LEVEL_INVALID = 0;
    public static final int GEAR_LEVEL_N = 2;
    public static final int GEAR_LEVEL_P = 4;
    public static final int GEAR_LEVEL_R = 3;
    public static final int MOTOR_POWER_NORMAL_MODE = 1;
    public static final int MOTOR_POWER_XPOWER_MODE = 2;
    public static final int NEDC_MODE = 0;
    public static final int POWER_RESPONCE_FAST_MODE = 3;
    public static final int POWER_RESPONCE_INTELLIGENT_MODE = 6;
    public static final int POWER_RESPONCE_NO_COMMAND = 1;
    public static final int POWER_RESPONCE_SLOW_MODE = 2;
    public static final int POWER_RESPONCE_STANDARD_MODE = 1;
    public static final int POWER_RESPONCE_SUPERFAST_MODE = 5;
    public static final int POWER_RESPONCE_SUPERSLOW_MODE = 4;
    public static final int SPECIAL_DRIVING_GEEK_MODE = 5;
    public static final int SPECIAL_DRIVING_MUD_MODE = 1;
    public static final int SPECIAL_DRIVING_NO_COMMAND = 0;
    public static final int SPECIAL_DRIVING_XPOWER_MODE = 2;
    public static final int TRAILER_MODE_STATUS_OFF = 0;
    public static final int TRAILER_MODE_STATUS_ON = 1;
    public static final int VCU_ENERGY_RECOVERY_AUTO = 5;
    public static final int VCU_ENERGY_RECOVERY_HIGH = 3;
    public static final int VCU_ENERGY_RECOVERY_INVALID = -1;
    public static final int VCU_ENERGY_RECOVERY_LOW = 1;
    public static final int VCU_ENERGY_RECOVERY_MIDDLE = 2;
    public static final int VCU_EV_HIGH_VOL_ON = 1;
    public static final int VCU_EV_NOT_READY = 0;
    public static final int VCU_EV_READY = 2;
    public static final int VCU_SNOWMODE_OFF = 0;
    public static final int VCU_SNOWMODE_ON = 1;
    public static final int WLTP_MODE = 1;
    public static final int XPEDAL_MODE_STATUS_OFF = 0;
    public static final int XPEDAL_MODE_STATUS_ON = 1;
    public static final int XSPORT_MODE_ENTER_STATUS_FULL = 0;
    public static final int XSPORT_MODE_ENTER_STATUS_N0_TIPS = 1;
    public static final int XSPORT_MODE_STATUS_AI = 2;
    public static final int XSPORT_MODE_STATUS_BOOST = 3;
    public static final int XSPORT_MODE_STATUS_NO_COMMAND = 0;
    public static final int XSPORT_MODE_STATUS_RACER = 4;
    public static final int XSPORT_MODE_STATUS_TUNNING = 1;

    /* loaded from: classes.dex */
    public interface Callback extends IBaseCallback {
        default void onAccPedalStatusChanged(int value) {
        }

        default void onAutoEasyLoadModeChanged(boolean mode) {
        }

        default void onAvailableMileageChanged(int mileage) {
        }

        default void onAwdSettingChanged(int mode) {
        }

        default void onBrakeLightChanged(boolean on) {
        }

        default void onChargeGunStatusChanged(int status) {
        }

        default void onChargeStatusChanged(int status) {
        }

        default void onCruiseStateChanged(boolean isActive) {
        }

        default void onDriveModeChanged(int mode) {
        }

        default void onElecPercentChanged(int percent) {
        }

        default void onEvSysReady(int state) {
        }

        default void onGearChanged(int gear) {
        }

        default void onNGearWarningSwChanged(boolean enable) {
        }

        default void onPowerResponseModeChanged(int mode) {
        }

        default void onRawCarSpeedChanged(float speed) {
        }

        default void onRecycleGradeChanged(int grade) {
        }

        default void onRwsModeChanged(boolean ison) {
        }

        default void onShowModeChanged(boolean on) {
        }

        default void onSnowModeChanged(boolean enable) {
        }

        default void onSsaSwChanged(boolean enabled) {
        }

        default void onTrailerModeSwitchChanged(boolean on) {
        }

        default void onVMCSystemStateChanged(boolean isfailed) {
        }

        default void onXPedalModeSwitchChanged(boolean on) {
        }

        default void onXSportDriveModeChanged(int mode) {
        }

        default void onZwalkModeChanged(boolean ison) {
        }
    }

    void controlExhibitionMode(boolean enter);

    void enterXSportDriveModeByType(int mode, int type);

    void exitXSportDriveMode();

    int getAccPedalStatus();

    int getAsDriveModeStatus();

    boolean getAutoEasyLoadMode();

    int getAvailableMileage();

    int getAvailableMileage(boolean byMileageMode);

    int getAwdSetting();

    boolean getBrakeLightOnOff();

    boolean getBreakPedalStatus();

    float getCarSpeed();

    boolean getCarStationaryStatus();

    int getChargeGunStatus();

    int getChargeStatus();

    float getConsumptionPer100Km();

    int getCreepVehSpd();

    String getCurrentUserDefineDriveModeInfo();

    int getDriveMode();

    int getDriveModeByUser();

    String getDriveModeSubItemInfo(int driveMoode);

    int getElecPercent();

    int getEnergyRecycleGrade();

    int getEnergyRecycleGradeByUser();

    boolean getFirstEnterXpowerFlag();

    int getGearLevel();

    int getMileageMode();

    int getMotorPowerMode();

    boolean getNGearWarningSwitchStatus();

    boolean getNewDriveArchXPedalMode();

    boolean getNewDriveArchXPedalModeSp();

    boolean getParkDropdownMenuEnable();

    int getPowerResponseMode();

    boolean getSnowMode();

    boolean getSsaSw();

    boolean getTrailerModeStatus();

    boolean getVMCRwsSwitchState();

    boolean getVMCSystemState();

    boolean getVMCZWalkModeState();

    int getXSportDrivingMode();

    boolean isAutoDriveModeEnabled();

    boolean isCruiseActive();

    boolean isEvHighVolReady();

    boolean isEvSysReady();

    boolean isExhibitionMode();

    boolean isExhibitionModeOn();

    int onModesDrivingXSport(int mode);

    void saveCurrentUserDefineDriveModeInfo(String info);

    void setAutoDriveMode(boolean enable);

    void setAutoEasyLoadMode(boolean enable);

    void setAwdSetting(int mode);

    void setDriveMode(int driveMode);

    void setDriveMode(int driveMode, boolean storeEnable);

    void setDriveModeSp(int driveMode);

    void setEnergyRecycleGrade(int grade);

    void setEnergyRecycleGrade(int grade, boolean needSave);

    void setMotorPowerMode(int mode);

    void setNGearWarningSwitch(boolean enable);

    void setNewDriveArchXPedalMode(boolean enable, boolean storeEnable);

    void setParkDropdownMenuEnable(boolean enable);

    void setPowerResponseMode(int mode);

    void setSnowMode(boolean enable);

    void setSsaSwEnable(boolean enable);

    void setTrailerMode(boolean enable);

    void setVMCRwsSwitch(boolean on);

    void setVMCZWalkModeSwitch(boolean on);

    void setXSportDrivingMode(int mode);

    static {
        DRIVE_MODE_SHOW_MODE = CarStatusUtils.isD20CarType() ? 5 : 3;
    }
}
