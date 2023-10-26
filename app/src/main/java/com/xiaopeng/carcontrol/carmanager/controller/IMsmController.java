package com.xiaopeng.carcontrol.carmanager.controller;

import com.xiaopeng.carcontrol.carmanager.IBaseCallback;
import com.xiaopeng.carcontrol.carmanager.IBaseCarController;
import java.util.List;
import java.util.Set;

/* loaded from: classes.dex */
public interface IMsmController extends IBaseCarController<Callback> {
    public static final int ALL_POS_SET_MEMORY_TYPE_NO_REQ = 0;
    public static final int ALL_POS_SET_MEMORY_TYPE_RECALL_POSITION = 2;
    public static final int ALL_POS_SET_MEMORY_TYPE_SAVE_POSITION = 1;
    public static final int LUMBER_SWITCH_MODE_CUSHION_EXTENSION = 1;
    public static final int LUMBER_SWITCH_MODE_LUMBER = 0;
    public static final int LUMB_CTRL_SW_CTRL_DISABLE = 0;
    public static final int LUMB_CTRL_SW_CTRL_ENABLE = 1;
    public static final int MSM_CONTROL_PENDING = 2;
    public static final int MSM_CONTROL_START = 1;
    public static final int MSM_CONTROL_STOP = 3;
    public static final int MSM_SEAT_MAX_PERCENT = 98;
    public static final int MSM_SEAT_MAX_VALID_PERCENT = 100;
    public static final int MSM_SEAT_MIN_PERCENT = 2;
    public static final int MSM_SEAT_MIN_VALID_PERCENT = 0;
    public static final int SEAT_CONTROL_LOCUS_BOSS = 2;
    public static final int SEAT_CONTROL_LOCUS_MANUAL = 1;
    public static final int SEAT_CONTROL_LOCUS_SPEECH = 3;
    public static final int SEAT_DRIVER_INDEX = 0;
    public static final int SEAT_DRVREAR_INDEX = 2;
    public static final int SEAT_MEMORY_INDEX_NEW = -1;
    public static final int SEAT_MOVE_DIRECTION_BACKWARD = 2;
    public static final int SEAT_MOVE_DIRECTION_FORWARD = 1;
    public static final int SEAT_MOVE_HORZ = 1;
    public static final int SEAT_MOVE_LEG = 4;
    public static final int SEAT_MOVE_LEG_HORZ = 7;
    public static final int SEAT_MOVE_LUMB_HORZ = 5;
    public static final int SEAT_MOVE_LUMB_VERT = 6;
    public static final int SEAT_MOVE_TILT = 3;
    public static final int SEAT_MOVE_VERT = 2;
    public static final int SEAT_MSG_MODE_OFF = 0;
    public static final int SEAT_MSG_STRENGTH_1 = 1;
    public static final int SEAT_MSG_STRENGTH_2 = 2;
    public static final int SEAT_MSG_STRENGTH_3 = 3;
    public static final int SEAT_PASSENGER_INDEX = 1;
    public static final int SEAT_PSNREAR_INDEX = 3;
    public static final int SEAT_RTM_STRENGTH_1 = 1;
    public static final int SEAT_RTM_STRENGTH_2 = 2;
    public static final int SEAT_RTM_STRENGTH_3 = 3;
    public static final int SEAT_RTM_STRENGTH_4 = 4;
    public static final int SEAT_RTM_STRENGTH_5 = 5;
    public static final int SRS_NO_WARNING = 0;
    public static final int SRS_WARNING = 1;

    /* loaded from: classes.dex */
    public interface Callback extends IBaseCallback {
        default void onBackBeltWaringChanged(boolean status) {
        }

        default void onDSeatCushionPosChanged(int pos) {
        }

        default void onDSeatHorzPosChanged(int pos) {
        }

        default void onDSeatLegPosChanged(int pos) {
        }

        default void onDSeatTiltPosChanged(int pos) {
        }

        default void onDSeatVerPosChanged(int pos) {
        }

        default void onDrvBeltWaringChanged(int mode) {
        }

        default void onDrvHeadrestChanged(boolean normal) {
        }

        default void onDrvSeatLumberControlEnableChanged(boolean enable) {
        }

        default void onDrvSeatLumberSwitchPressStateChanged(int state) {
        }

        default void onEsbChanged(boolean enable) {
        }

        default void onMassageFailed(int index, String effect, int opCode, int errCode) {
        }

        default void onMassagerEffectChange(int index, String effectId) {
        }

        default void onMassagerIntensityChange(int index, int intensity) {
        }

        default void onMassagerStatusChange(int index, String effectId, int intensity, boolean running) {
        }

        default void onPSeatHorzPosChanged(int pos) {
        }

        default void onPSeatLegPosChanged(int pos) {
        }

        default void onPSeatTiltPosChanged(int pos) {
        }

        default void onPSeatVerPosChanged(int pos) {
        }

        default void onPsnBeltWaringChanged(int mode) {
        }

        default void onPsnHeadrestChanged(boolean normal) {
        }

        default void onPsnOnSeatChanged(boolean occupied) {
        }

        default void onPsnSeatLumberControlEnableChanged(boolean enable) {
        }

        default void onPsnSeatLumberSwitchPressStateChanged(int state) {
        }

        default void onPsnSrsEnableChanged(boolean enable) {
        }

        default void onPsnWelcomeModeChanged(boolean enabled) {
        }

        default void onRLSeatLegPosChanged(int pos) {
        }

        default void onRLSeatTiltPosChanged(int pos) {
        }

        default void onRRSeatLegPosChanged(int pos) {
        }

        default void onRRSeatTiltPosChanged(int pos) {
        }

        default void onRhythmIntensityChange(Set<Integer> ids, int intensity) {
        }

        default void onRhythmModeChange(int mode) {
        }

        default void onRhythmStatusChange(Set<Integer> ids, boolean running) {
        }

        default void onRlOnSeatChanged(boolean occupied) {
        }

        default void onRmOnSeatChanged(boolean occupied) {
        }

        default void onRrOnSeatChanged(boolean occupied) {
        }

        default void onSecRowLtEzOrZeroGStatusChanged(int status) {
        }

        default void onSecRowLtSeatHeReHeigPosChanged(int pos) {
        }

        default void onSecRowLtSeatHeReHorzPosChanged(int pos) {
        }

        default void onSecRowLtSeatHorzPosChanged(int pos) {
        }

        default void onSecRowLtSeatLeSuHeigPosChanged(int pos) {
        }

        default void onSecRowLtSeatZeroGravPosChanged(int pos) {
        }

        default void onSecRowRtEzOrZeroGStatusChanged(int status) {
        }

        default void onSecRowRtSeatHeReHeigPosChanged(int pos) {
        }

        default void onSecRowRtSeatHeReHorzPosChanged(int pos) {
        }

        default void onSecRowRtSeatHorzPosChanged(int pos) {
        }

        default void onSecRowRtSeatLeSuHeigPosChanged(int pos) {
        }

        default void onSecRowRtSeatZeroGravPosChanged(int pos) {
        }

        default void onTrdRowLtFoldStatusChanged(int status) {
        }

        default void onTrdRowLtSeatHeadVerticalPosChanged(int pos) {
        }

        default void onTrdRowLtSeatTiltPosChangedpos(int pos) {
        }

        default void onTrdRowMidSeatHeadVerticalPosChanged(int pos) {
        }

        default void onTrdRowRtFoldStatusChanged(int status) {
        }

        default void onTrdRowRtSeatHeadVerticalPosChanged(int pos) {
        }

        default void onTrdRowRtSeatTiltPosChanged(int pos) {
        }

        default void onTrdRowStowStatusChanged(int status) {
        }
    }

    void doVibrate(int index, String effectId, int loop, int type);

    boolean getBackBeltWStatus();

    int getCurrentSelectedPsnHabit();

    int getCurrentSelectedRLHabit();

    int getCurrentSelectedRRHabit();

    int getDSeatCushionPos();

    int getDSeatHorzPos();

    int getDSeatLegPos();

    int getDSeatTiltPos();

    int getDSeatVerPos();

    boolean getDriverSeatLumbControlSwitchEnable();

    int getDriverSeatLumberSwitchMode();

    int getDrvBeltStatus();

    int[] getDrvSeatPos(int index);

    int getDrvSeatPosIdx();

    boolean getEsbEnable();

    boolean getEsbModeSp();

    List<String> getMassagerEffectIDs();

    String getMassagerEffectId(int index);

    int getMassagerIntensity(int index);

    int getPSeatHorzPos();

    int getPSeatLegPos();

    int getPSeatTiltPos();

    int getPSeatVerPos();

    boolean getPassengerSeatLumbControlSwitchEnable();

    int getPassengerSeatLumberSwitchMode();

    int getPsnBeltStatus();

    int[] getPsnSeatDefaultPos();

    String getPsnSeatName(int selectId);

    int[] getPsnSeatPos();

    int getPsnSeatPosIdx(int selectId);

    boolean getPsnSeatWelcomeMode();

    int[] getPsnSelectSeatPos(int selectId);

    boolean getPsnSrsEnable();

    int getRLSeatHeadHorPos();

    int getRLSeatHeadVerPos();

    int getRLSeatHorPos();

    int getRLSeatLegHorPos();

    int getRLSeatLegPos();

    int getRLSeatLegVerPos();

    String getRLSeatName(int selectId);

    int[] getRLSeatNowPos();

    int[] getRLSeatPos();

    int getRLSeatTiltPos();

    int getRLSeatZeroAngle();

    int[] getRLSelectSeatPos(int selectId);

    int getRRSeatHeadHorPos();

    int getRRSeatHeadVerPos();

    int getRRSeatHorPos();

    int getRRSeatLegHorPos();

    int getRRSeatLegPos();

    int getRRSeatLegVerPos();

    String getRRSeatName(int selectId);

    int[] getRRSeatNowPos();

    int[] getRRSeatPos();

    int getRRSeatTiltPos();

    int getRRSeatZeroAngle();

    int[] getRRSelectSeatPos(int selectId);

    int getRhythmIntensity(int index);

    int getRhythmMode();

    int getSecRowLtSeatZeroGrav();

    int getSecRowRtSeatZeroGrav();

    int getTrdRowLtSeatFol();

    int getTrdRowLtSeatHeadVerticalPos();

    int getTrdRowLtSeatTiltPos();

    int getTrdRowMidSeatHeadVerticalPos();

    int getTrdRowRtSeatFol();

    int getTrdRowRtSeatHeadVerticalPos();

    int getTrdRowRtSeatTiltPos();

    int getTrdRowSeatStow();

    List<String> getVibrateEffectIDs();

    int getVibrateIntensity(int index);

    boolean isDrvHeadrestNormal();

    boolean isDrvTiltMovingSafe();

    boolean isInSeatMenuShowTime(boolean drv);

    boolean isMassagerRunning(int index);

    boolean isPsnHeadrestNormal();

    boolean isPsnSeatOccupied();

    boolean isPsnTiltMovingSafe();

    boolean isRhythmRunning(int index);

    boolean isRlSeatOccupied();

    boolean isRmSeatOccupied();

    boolean isRrSeatOccupied();

    String readFollowedVehicleLostConfig();

    void saveDrvSeatPos(int index, int[] position);

    void savePsnSeatName(String posName, int selectId);

    void savePsnSeatPos(int[] position);

    void savePsnSeatPos(int[] position, int selectId);

    void savePsnSeatPos(int[] position, int index, int selectId);

    void saveRLSeatName(String posName, int selectId);

    void saveRLSeatPos(int[] position, int selectId);

    void saveRRSeatName(String posName, int selectId);

    void saveRRSeatPos(int[] position, int selectId);

    void setBackBeltSw(boolean enable);

    void setDSeatCushionMove(int control, int direction);

    void setDSeatCushionPos(int pos);

    void setDSeatHorzMove(int control, int direction);

    void setDSeatHorzPos(int pos);

    void setDSeatLegMove(int control, int direction);

    void setDSeatLegPos(int pos);

    void setDSeatLumbHorzMove(int control, int direction);

    void setDSeatLumbVerMove(int control, int direction);

    void setDSeatTiltMove(int control, int direction);

    void setDSeatTiltPos(int pos);

    void setDSeatVerMove(int control, int direction);

    void setDSeatVerPos(int pos);

    void setDriverAllPositions(int horizonPos, int verticalPos, int tiltPos, int legPos, int cushionPos);

    void setDriverAllPositions(int memoryReq, int horizonPos, int verticalPos, int tiltPos, int legPos, int cushionPos);

    void setDriverAllPositionsToMcu(int horizonPos, int verticalPos, int tiltPos, int legPos, int cushionPos);

    void setDriverSeatLumbControlSwitchEnable(boolean enable);

    void setDrvSeatLayFlat();

    void setDrvWelcomeModeActiveSt(boolean active);

    void setEsbEnable(boolean enable);

    void setEsbEnable(boolean enable, boolean saveEnable);

    void setFollowedVehicleLostConfig(String config);

    void setMassagerEffect(Set<Integer> ids, String effectId);

    void setMassagerIntensity(Set<Integer> ids, int intensity);

    void setPSeatHorzMove(int control, int direction);

    void setPSeatHorzPos(int pos);

    void setPSeatLegMove(int control, int direction);

    void setPSeatLegPos(int pos);

    void setPSeatLumbHorzMove(int control, int direction);

    void setPSeatLumbVerMove(int control, int direction);

    void setPSeatTiltMove(int control, int direction);

    void setPSeatTiltPos(int pos);

    void setPSeatVerMove(int control, int direction);

    void setPSeatVerPos(int pos);

    void setPassengerSeatLumbControlSwitchEnable(boolean enable);

    void setPsnAllPositions(int horizonPos, int verticalPos, int tiltPos, int legPos);

    void setPsnAllPositions(int memoryReq, int horizonPos, int verticalPos, int tiltPos, int legPos);

    void setPsnAllPositionsToMcu(int horizonPos, int verticalPos, int tiltPos, int legPos);

    void setPsnSeatLayFlat();

    void setPsnSeatWelcomeMode(boolean enable);

    void setPsnSrsEnable(boolean enable);

    void setPsnWelcomeModeActiveSt(boolean active);

    void setRLAllPositions(int horizonPos, int anglePos, int tiltPos, int cushionPos, int legPos, int headVer, int headHor);

    void setRLAllPositions(int memoryReq, int horizonPos, int anglePos, int tiltPos, int cushionPos, int legPos, int headVer, int headHor);

    void setRLSeaHorzMove(int control, int direction);

    void setRLSeatFold(boolean fold);

    void setRLSeatHeadHorPos(int pos);

    void setRLSeatHeadRestHorzMove(int control, int direction);

    void setRLSeatHeadRestVerMove(int control, int direction);

    void setRLSeatHeadVerPos(int pos);

    void setRLSeatHorPos(int pos);

    void setRLSeatLegHorPos(int pos);

    void setRLSeatLegMove(int control, int direction);

    void setRLSeatLegPos(int pos);

    void setRLSeatLegVerMove(int control, int direction);

    void setRLSeatLegVerPos(int pos);

    void setRLSeatLumbHorzMove(int control, int direction);

    void setRLSeatLumbVerMove(int control, int direction);

    void setRLSeatStopMove();

    void setRLSeatTiltMove(int control, int direction);

    void setRLSeatTiltPos(int pos);

    void setRRAllPositions(int horizonPos, int anglePos, int tiltPos, int cushionPos, int legPos, int headVer, int headHor);

    void setRRAllPositions(int memoryReq, int horizonPos, int anglePos, int tiltPos, int cushionPos, int legPos, int headVer, int headHor);

    void setRRSeaHorzMove(int control, int direction);

    void setRRSeatFold(boolean fold);

    void setRRSeatHeadHorPos(int pos);

    void setRRSeatHeadRestHorzMove(int control, int direction);

    void setRRSeatHeadRestVerMove(int control, int direction);

    void setRRSeatHeadVerPos(int pos);

    void setRRSeatHorPos(int pos);

    void setRRSeatLegHorPos(int pos);

    void setRRSeatLegMove(int control, int direction);

    void setRRSeatLegPos(int pos);

    void setRRSeatLegVerMove(int control, int direction);

    void setRRSeatLegVerPos(int pos);

    void setRRSeatLumbHorzMove(int control, int direction);

    void setRRSeatLumbVerMove(int control, int direction);

    void setRRSeatStopMove();

    void setRRSeatTiltMove(int control, int direction);

    void setRRSeatTiltPos(int pos);

    void setRhythmEnable(Set<Integer> ids, boolean enable);

    void setRhythmIntensity(Set<Integer> ids, int intensity);

    void setRhythmMode(int mode);

    void setSeatMenuNotShowKeepTime(boolean drv, long keepTime);

    void setSecRowLtSeatEZ(int eZStatus);

    void setSecRowLtSeatZeroGrav(int zeroGravStatus);

    void setSecRowRtSeatEZ(int eZStatus);

    void setSecRowRtSeatZeroGrav(int zeroGravStatus);

    void setTrdRowLeftSeatHeadVerMove(int control, int direction);

    void setTrdRowLeftSeatTiltMove(int control, int direction);

    void setTrdRowLtSeatFol(int trdRowLtSeatFolStatus);

    void setTrdRowLtSeatHeadVerticalPos(int pos);

    void setTrdRowLtSeatTiltPos(int pos);

    void setTrdRowMidSeatHeadVerMove(int control, int direction);

    void setTrdRowMidSeatHeadVerticalPos(int pos);

    void setTrdRowRightSeatHeadVerMove(int control, int direction);

    void setTrdRowRightSeatTiltMove(int control, int direction);

    void setTrdRowRtSeatFol(int trdRowRtSeatFolStatus);

    void setTrdRowRtSeatHeadVerticalPos(int pos);

    void setTrdRowRtSeatTiltPos(int pos);

    void setTrdRowSeatStow(int stowStatus);

    void setVibrateIntensity(int index, int intensity);

    void startMassager(int id, int intensity, String effectId);

    void startMassager(Set<Integer> ids, int intensity, String effectId);

    void startMassager(Set<Integer> ids, String effectId);

    void stopDrvSeatMove();

    void stopMassager(Set<Integer> ids);

    void stopPsnSeatMove();

    void stopVibrate();
}
