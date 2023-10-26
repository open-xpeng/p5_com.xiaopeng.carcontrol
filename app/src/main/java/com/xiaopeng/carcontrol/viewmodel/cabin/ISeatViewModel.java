package com.xiaopeng.carcontrol.viewmodel.cabin;

import com.xiaopeng.carcontrol.viewmodel.IBaseViewModel;
import java.util.List;
import java.util.Set;

/* loaded from: classes2.dex */
public interface ISeatViewModel extends IBaseViewModel {
    public static final int MASSAGER_ALL_INDEX = 4;
    public static final int MASSAGER_DRIVER_INDEX = 0;
    public static final int MASSAGER_DRVREAR_INDEX = 2;
    public static final int MASSAGER_PASSENGER_INDEX = 1;
    public static final int MASSAGER_PSNREAR_INDEX = 3;
    public static final int RHYTHM_ALL_INDEX = 2;
    public static final int RHYTHM_DRIVER_INDEX = 0;
    public static final int RHYTHM_PASSENGER_INDEX = 1;
    public static final int SEAT_MSG_STRENGTH_1 = 1;
    public static final int SEAT_MSG_STRENGTH_2 = 2;
    public static final int SEAT_MSG_STRENGTH_3 = 3;
    public static final int SEAT_RTM_PATTERN_1 = 0;
    public static final int SEAT_RTM_PATTERN_2 = 1;
    public static final int SEAT_RTM_PATTERN_3 = 2;
    public static final int SEAT_RTM_STRENGTH_1 = 1;
    public static final int SEAT_RTM_STRENGTH_2 = 2;
    public static final int SEAT_RTM_STRENGTH_3 = 3;
    public static final int SEAT_RTM_STRENGTH_4 = 4;
    public static final int SEAT_RTM_STRENGTH_5 = 5;

    boolean checkChairMovable();

    boolean controlDriverSeatStart(int controlType, int direction, int controlLocus);

    void controlPsnSeatEnd(int type);

    boolean controlPsnSeatStart(int controlType, int direction, int controlLocus);

    void doVibrate(int index, String effect, int intensity);

    void enterMeditationState(int tiltPos);

    void exitMeditationState();

    boolean getBackBeltSw();

    float getCarSpeed();

    int getCurrentSelectedPsnHabit();

    int getCurrentSelectedRLHabit();

    int getCurrentSelectedRRHabit();

    int getDSeatCushionPos();

    int getDSeatHorzPos();

    int getDSeatLegPos();

    int getDSeatTiltPos();

    int getDSeatVerPos();

    boolean getDrvLumbControlSw();

    int getDrvSeatPosIdx();

    String getEsbConfigMode();

    boolean getEsbMode();

    String getMassageEffect(int index);

    List<String> getMassagerEffectIDs();

    int getMassagerIntensity(int index);

    String getMassagerRunningEffectId(int index);

    int getPSeatHorzPos();

    int getPSeatLegPos();

    int getPSeatTiltPos();

    int getPSeatVerPos();

    boolean getPsnLumbControlSw();

    String getPsnSeatName(int selectId);

    boolean getPsnSrsEnable();

    int getRLSeatHeadHorPos();

    int getRLSeatHeadVerPos();

    int getRLSeatHorPos();

    int getRLSeatLegHorPos();

    int getRLSeatLegPos();

    int getRLSeatLegVerPos();

    String getRLSeatName(int selectId);

    int getRLSeatTiltPos();

    int getRRSeatHeadHorPos();

    int getRRSeatHeadVerPos();

    int getRRSeatHorPos();

    int getRRSeatLegHorPos();

    int getRRSeatLegPos();

    int getRRSeatLegVerPos();

    String getRRSeatName(int selectId);

    int getRRSeatTiltPos();

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

    boolean haveDefaultSeat(int pos);

    boolean isCapsuleSeatLieFlat();

    boolean isDrvBeltUnbuckled();

    boolean isDrvHeadrestNormal();

    boolean isDrvSeatCorrectForRearFold();

    boolean isDrvSeatEqualMemory();

    boolean isDrvSeatLieFlat();

    boolean isDrvTiltMovingSafe();

    boolean isLocalIgOn();

    boolean isMassagerRunning(int index);

    boolean isPsnBeltUnbuckled();

    boolean isPsnHeadrestNormal();

    boolean isPsnSeatCorrectForRearFold();

    boolean isPsnSeatEqualMemory();

    boolean isPsnSeatLieFlat();

    boolean isPsnSeatOccupied();

    boolean isPsnTiltMovingSafe();

    boolean isPsnWelcomeEnabled();

    boolean isRLSeatEqualMemory();

    boolean isRLSeatLieFlat();

    boolean isRRSeatEqualMemory();

    boolean isRRSeatLieFlat();

    boolean isRearSeatWelcomeEnabled();

    boolean isRhythmRunning(int index);

    boolean isRlSeatOccupied();

    boolean isRmSeatOccupied();

    boolean isRrSeatOccupied();

    boolean isSupportMSMP();

    boolean isSupportMsm();

    boolean isWelcomeModeEnabled();

    boolean memoryDrvSeatData(int index);

    boolean memoryPsnSeatData();

    boolean memorySelectPsnSeatData(int index, int selectId);

    boolean memorySelectRLSeatData(int selectId);

    boolean memorySelectRRSeatData(int selectId);

    void moveDrvSeatToSafeForRearSleep();

    void movePsnSeatToSafeForRearSleep();

    void moveToExhibitionPos();

    void onFollowedVehicleLostConfigChanged(boolean forceUpdate);

    void removeDropDownRun();

    void restoreDrvSeatAllPositions(int[] pos);

    boolean restoreDrvSeatPos();

    boolean restoreDrvSeatPos(int index);

    boolean restoreDrvSeatPos(int index, boolean ignoreDrvDoorState, boolean ignoreDrvState);

    boolean restoreDrvSeatPos(boolean ignoreDrvDoorState, boolean ignoreDrvState);

    boolean restorePsnSeatPos();

    boolean restorePsnSeatPos(int index, int selectId, boolean isSelected);

    boolean restorePsnSelectSeatPos(int selectId, boolean isSelect);

    boolean restoreRLSeatPos(int selectId, boolean isSelected);

    boolean restoreRLSelectSeatPos(int selectId, boolean isSelect);

    boolean restoreRRSeatPos(int selectId, boolean isSelected);

    boolean restoreRRSelectSeatPos(int selectId, boolean isSelect);

    void savePsnSeatName(String posName, int selectId);

    void saveRLSeatName(String posName, int selectId);

    void saveRRSeatName(String posName, int selectId);

    boolean saveSelectPsnSeatData(int selectId);

    boolean saveSelectRLSeatData(int selectId);

    boolean saveSelectRRSeatData(int selectId);

    boolean seatSaveOutMode();

    void setBackBeltSw(boolean on);

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

    void setDriverAllPositionsToDomain();

    void setDriverAllPositionsToMcu();

    void setDrvLumbControlSw(boolean enable);

    void setDrvSeatAllPositions(int horizonPos, int verticalPos, int tiltPos, int legPos, int cushionPos);

    void setDrvSeatLayFlat();

    void setDrvWelcomeModeActiveSt(boolean active);

    void setEsbMode(boolean enable);

    void setFollowedVehicleLostConfig(String config);

    void setMassagerEffect(int index, String effectId);

    void setMassagerEffect(Set<Integer> ids, String effectId);

    void setMassagerIntensity(int index, int intensity);

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

    void setPsnAllPositionsToDomain();

    void setPsnLumbControlSw(boolean enable);

    void setPsnSeatAllPositions(int horizonPos, int verticalPos, int tiltPos, int legPos);

    void setPsnSeatAllPositionsToMcu();

    void setPsnSeatLayFlat();

    void setPsnSrsEnable(boolean enable);

    void setPsnWelcomeMode(boolean enable);

    void setPsnWelcomeModeActiveSt(boolean active);

    void setRLAllPositionsToDomain();

    void setRLSeaHorzMove(int control, int direction);

    void setRLSeatAllPositions(int horizonPos, int anglePos, int tiltPos, int cushionPos, int legPos, int headVer, int headHor);

    void setRLSeatAllPositionsToMcu();

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

    void setRRAllPositionsToDomain();

    void setRRSeaHorzMove(int control, int direction);

    void setRRSeatAllPositions(int horizonPos, int anglePos, int tiltPos, int cushionPos, int legPos, int headVer, int headHor);

    void setRRSeatAllPositionsToMcu();

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

    void setRearSeatWelcomeMode(boolean enable);

    void setRhythmIntensity(int index, int intensity);

    void setRhythmMode(int mode);

    void setSeatMenuNotShowKeepTime(boolean drv, long keepTime);

    void setSeatUiCtrlResume(boolean resume);

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

    void setWelcomeMode(boolean enable);

    void showPsnSrsCloseDialog();

    void startMassager(int index, int intensity, String effectId);

    void startMassager(int index, String effectId);

    void startRhythm(int index, int mode);

    void startRhythm(int index, int mode, boolean playEffect);

    void stopCapsuleSeatMove();

    void stopDrvSeatMove();

    void stopMassager(int index);

    void stopPsnSeatMove();

    void stopRhythm(int index);

    void stopSeatControl();
}
