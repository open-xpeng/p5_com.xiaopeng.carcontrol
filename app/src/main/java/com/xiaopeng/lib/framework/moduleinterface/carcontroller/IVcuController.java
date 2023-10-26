package com.xiaopeng.lib.framework.moduleinterface.carcontroller;

/* loaded from: classes2.dex */
public interface IVcuController extends ILifeCycle {
    public static final int BATTERY_NORMAL = 0;
    public static final int BATTERY_TOO_COLD = 1;
    public static final int BATTERY_WARMING_START = 1;
    public static final int BATTERY_WARMING_STOP = 0;
    public static final int CHARGE_ERROR_GAIN = 1;
    public static final int CHARGE_ERROR_NONE = 0;
    public static final int CHARGE_GUN_AC_LINK = 1;
    public static final int CHARGE_GUN_DC_LINK = 2;
    public static final int CHARGE_GUN_NO_LINK = 0;
    public static final int CHARGE_SPEED_SLOW = 1;
    public static final int CHARGE_SPEED_SLOW_INVALID = 0;
    public static final int CHARGE_STATUS_APPOINTMENT = 1;
    public static final int CHARGE_STATUS_CHARGING = 2;
    public static final int CHARGE_STATUS_DONE = 8;
    public static final int CHARGE_STATUS_ERROR = 6;
    public static final int CHARGE_STATUS_FULLY_CHARGE = 3;
    public static final int CHARGE_STATUS_PREPARE = 0;
    public static final int CHARGE_STATUS_REMOVE_CHAGRE_CONNECTOR = 4;
    public static final int CHARGE_STATUS_STOPPING = 7;
    public static final int CHARGE_STATUS_WRONG_OPERATION = 5;
    public static final int DRIVE_MODE_FB_AUTO = 1;
    public static final int DRIVE_MODE_FB_MANUAL = 0;
    public static final int DRIVE_MODE_FB_NOT_MANUAL = 3;
    public static final int DRIVE_MODE_FB_REMOTE_CONTROL = 2;
    public static final int DRIVING_STATUS_MODE_ANTISICKNESS = 4;
    public static final int DRIVING_STATUS_MODE_COMFORT = 0;
    public static final int DRIVING_STATUS_MODE_ECO = 1;
    public static final int DRIVING_STATUS_MODE_SPORT = 2;
    public static final int ENERGY_STATUS_RECOVERY_HIGH = 2;
    public static final int ENERGY_STATUS_RECOVERY_LOW = 0;
    public static final int ENERGY_STATUS_RECOVERY_MIDDLE = 1;
    public static final int GEAR_LEVEL_D = 1;
    public static final int GEAR_LEVEL_INVALID = 0;
    public static final int GEAR_LEVEL_N = 2;
    public static final int GEAR_LEVEL_P = 4;
    public static final int GEAR_LEVEL_R = 3;
    public static final int VCU_ENERGY_RECOVERY_HIGH = 2;
    public static final int VCU_ENERGY_RECOVERY_LOW = 0;
    public static final int VCU_ENERGY_RECOVERY_MIDDLE = 1;

    /* loaded from: classes2.dex */
    public static class AcChargeCurrentEvengMsg extends AbstractEventMsg<Float> {
    }

    /* loaded from: classes2.dex */
    public static class AcChargeVoltEvengMsg extends AbstractEventMsg<Float> {
    }

    /* loaded from: classes2.dex */
    public static class AcInputStatusEvnetMsg extends AbstractEventMsg<Boolean> {
    }

    /* loaded from: classes2.dex */
    public static class AvailableDrivingDistanceEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class AverageVehConusme100kmEventMsg extends AbstractEventMsg<Float> {
    }

    /* loaded from: classes2.dex */
    public static class AverageVehConusmeEventMsg extends AbstractEventMsg<Float> {
    }

    /* loaded from: classes2.dex */
    public static class BatteryCoolStatusEventMsg extends AbstractEventMsg<Boolean> {
    }

    /* loaded from: classes2.dex */
    public static class BatteryMinTemperatureEventMsg extends AbstractEventMsg<Float> {
    }

    /* loaded from: classes2.dex */
    public static class BatteryOverheatingEventMsg extends AbstractEventMsg<Boolean> {
    }

    /* loaded from: classes2.dex */
    public static class BatteryWarmmingEventMsg extends AbstractEventMsg<Boolean> {
    }

    /* loaded from: classes2.dex */
    public static class BreakTravelEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class CarGearLevelEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class CarSpeedEventMsg extends AbstractEventMsg<Float> {
    }

    /* loaded from: classes2.dex */
    public static class ChargeCompleteTimeEventMsg extends AbstractEventMsg<Float> {
    }

    /* loaded from: classes2.dex */
    public static class ChargeErrorEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class ChargeGunEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class ChargeLimitEventMsg extends AbstractEventMsg<int[]> {
    }

    /* loaded from: classes2.dex */
    public static class ChargeSpeedSlowEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class ChargeStatusEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class ColdWarningTipsEventMsg extends AbstractEventMsg<Boolean> {
    }

    /* loaded from: classes2.dex */
    public static class DcChargeCurrentEvengMsg extends AbstractEventMsg<Float> {
    }

    /* loaded from: classes2.dex */
    public static class DcChargeVoltEvengMsg extends AbstractEventMsg<Float> {
    }

    /* loaded from: classes2.dex */
    public static class DriveTravelEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class DriverOverrideEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class DrivingModeEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class EbsBatterySocEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class ElectriPercentEventMsg extends AbstractEventMsg<Float> {
    }

    /* loaded from: classes2.dex */
    public static class EnergyRecycleLevelEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class ErhDebugInfoEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class HvacConsumeEventMsg extends AbstractEventMsg<Float> {
    }

    /* loaded from: classes2.dex */
    public static class PowerSystemErrorLampOnEventMsg extends AbstractEventMsg<Boolean> {
    }

    /* loaded from: classes2.dex */
    public static class PureDriveModeFeedbackEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class RawCarSpeedEventMsg extends AbstractEventMsg<Float> {
    }

    /* loaded from: classes2.dex */
    public static class SohEventMsg extends AbstractEventMsg<Float> {
    }

    /* loaded from: classes2.dex */
    public static class SupDebugInfoEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class SystemReadyEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class onEvReadyStatusChange extends AbstractEventMsg<Integer> {
    }

    float getACChargingCurrent() throws Exception;

    float getACChargingVolt() throws Exception;

    boolean getACInputStatus() throws Exception;

    float getAverageVehConsume() throws Exception;

    float getAverageVehConsume100km() throws Exception;

    boolean getBatteryCoolStatus() throws Exception;

    float getBatteryMinTemperature() throws Exception;

    int[] getBatteryPtcErrorInfo() throws Exception;

    boolean getBatteryWarmingStatus() throws Exception;

    int getBreakTravel() throws Exception;

    float getCarSpeed() throws Exception;

    int getCcsWorkError() throws Exception;

    int getChargeSpeedLevel() throws Exception;

    int getChargeStatus() throws Exception;

    float getChargingCompleteTime() throws Exception;

    int getChargingError() throws Exception;

    int getChargingGunStatus() throws Exception;

    int[] getChargingMaxSoc() throws Exception;

    boolean getColdWarningTips() throws Exception;

    float getDCChargingCurrent() throws Exception;

    float getDCChargingVolt() throws Exception;

    int getDriveMode() throws Exception;

    int getDriveTravel() throws Exception;

    int getDriverOverride() throws Exception;

    int getEbsBatterySoc() throws Exception;

    float getElectricityPercent() throws Exception;

    int getEnergyRecycle() throws Exception;

    int getGearLever() throws Exception;

    int getGearWarningInfo() throws Exception;

    float getHvacConsume() throws Exception;

    int getMaxMileage(int i) throws Exception;

    int getMileageNumber() throws Exception;

    int getPureDriveModeFeedback() throws Exception;

    float getRawCarSpeed() throws Exception;

    float getSOH() throws Exception;

    @Deprecated
    int getStallState() throws Exception;

    int getSystemReady() throws Exception;

    int[] getTcmFailReason() throws Exception;

    int getVcuErhDebubInfo() throws Exception;

    int getVcuSupDebugInfo() throws Exception;

    boolean isAgsError() throws Exception;

    boolean isBatteryOverheadtingWarning() throws Exception;

    boolean isBatteryOverheating() throws Exception;

    boolean isBmsError() throws Exception;

    boolean isBmsScoLow() throws Exception;

    boolean isDcdcError() throws Exception;

    boolean isEbsError() throws Exception;

    boolean isElectricMotorSystemOverheating() throws Exception;

    boolean isElectricVacuumPumpError() throws Exception;

    boolean isHvCutoff() throws Exception;

    boolean isHvRelayAdhesion() throws Exception;

    boolean isLowBatteryVoltage() throws Exception;

    boolean isPowerSystemErrorLampOn() throws Exception;

    void setBestCharging() throws Exception;

    void setChargingLimit(int i) throws Exception;

    void setEnergyRecycle(int i) throws Exception;

    void setFullyCharging() throws Exception;

    void startCharge(int i) throws Exception;

    void stopACCharge(int i) throws Exception;

    void stopDCCharging() throws Exception;
}
