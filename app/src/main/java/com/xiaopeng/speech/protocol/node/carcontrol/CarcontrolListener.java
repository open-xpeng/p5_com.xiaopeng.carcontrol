package com.xiaopeng.speech.protocol.node.carcontrol;

import com.xiaopeng.speech.INodeListener;
import com.xiaopeng.speech.protocol.bean.ChangeValue;
import com.xiaopeng.speech.protocol.bean.SpeechParam;
import com.xiaopeng.speech.protocol.bean.base.CommandValue;
import com.xiaopeng.speech.protocol.node.carcontrol.bean.ControlReason;
import com.xiaopeng.speech.protocol.node.carcontrol.bean.SeatValue;
import com.xiaopeng.speech.protocol.node.carcontrol.bean.UserBookValue;

/* loaded from: classes2.dex */
public interface CarcontrolListener extends INodeListener {
    void onChargePortControl(int i, int i2);

    void onCheckUserBook(UserBookValue userBookValue);

    void onCloseUserBook();

    default void onControlComfortableDrivingModeClose() {
    }

    default void onControlComfortableDrivingModeOpen() {
    }

    void onControlElectricCurtainClose(int i);

    void onControlElectricCurtainOpen(int i);

    void onControlLightResume();

    void onControlScissorLeftDoorOff();

    void onControlScissorLeftDoorOn();

    void onControlScissorLeftDoorPause();

    void onControlScissorRightDoorOff();

    void onControlScissorRightDoorOn();

    void onControlScissorRightDoorPause();

    void onControlSeatResume();

    default void onControlSunShade(int i, int i2) {
    }

    void onControlXpedalOff();

    void onControlXpedalOn();

    void onEnergyRecycleDown();

    void onEnergyRecycleHigh();

    void onEnergyRecycleLow();

    void onEnergyRecycleMedia();

    void onEnergyRecycleUp();

    void onLegDown(int i);

    void onLegHighest(int i);

    void onLegLowest(int i);

    void onLegUp(int i);

    void onLightAtmosphereBrightnessDown();

    void onLightAtmosphereBrightnessMax();

    void onLightAtmosphereBrightnessMin();

    void onLightAtmosphereBrightnessSet(CommandValue commandValue);

    void onLightAtmosphereBrightnessUp();

    void onLightAtmosphereColor(CommandValue commandValue);

    void onLightAtmosphereOff(int i);

    void onLightAtmosphereOn();

    void onLightAutoOff();

    void onLightAutoOn();

    void onLightHomeOff();

    void onLightHomeOn();

    void onLightLowOff();

    void onLightLowOff(ControlReason controlReason);

    void onLightLowOn();

    void onLightPositionOff();

    void onLightPositionOn();

    void onLightTopAutoOff();

    void onLightTopAutoOn();

    void onLightTopOff();

    void onLightTopOn();

    void onLowVolumeOff();

    void onLowVolumeOn();

    void onMirrorRearClose();

    void onMirrorRearOn();

    void onMirrorRearSet(int i);

    void onMistLightOff();

    void onMistLightOn();

    void onModesConservationSupport();

    void onModesDrivingConservation();

    void onModesDrivingNormal();

    void onModesDrivingSport();

    void onModesSportSupport();

    void onModesSteeringNormal();

    void onModesSteeringSoft();

    void onModesSteeringSport();

    void onOpenUserBook();

    void onPsnSeatBackrestBack();

    void onPsnSeatBackrestForward();

    void onPsnSeatMoveBack();

    void onPsnSeatMoveDown();

    void onPsnSeatMoveForward();

    void onPsnSeatMoveUp();

    void onSeatAdjust(SeatValue seatValue);

    void onSeatBackrestBack(ChangeValue changeValue, SpeechParam speechParam);

    void onSeatBackrestForemost();

    void onSeatBackrestForward(ChangeValue changeValue);

    void onSeatBackrestRear(SpeechParam speechParam);

    void onSeatMoveBack(ChangeValue changeValue);

    void onSeatMoveDown();

    void onSeatMoveForemost();

    void onSeatMoveForward(ChangeValue changeValue);

    void onSeatMoveHighest();

    void onSeatMoveLowest();

    void onSeatMoveRear(SpeechParam speechParam);

    void onSeatMoveUp();

    void onSeatRestore();

    void onTirePressureShow();

    void onTrunkClose();

    void onTrunkOpen();

    void onTrunkUnlock();

    void onWindowDriverClose();

    void onWindowDriverOpen();

    void onWindowFrontClose();

    void onWindowFrontOpen();

    void onWindowLeftRearClose();

    void onWindowLeftRearOpen();

    void onWindowPassengerClose();

    void onWindowPassengerOpen();

    void onWindowRearClose();

    void onWindowRearOpen();

    void onWindowRightRearClose();

    void onWindowRightRearOpen();

    void onWindowsClose();

    void onWindowsControl(int i, int i2, int i3);

    void onWindowsOpen();

    void onWindowsVentilateOff();

    void onWindowsVentilateOn();

    void onWiperHigh();

    void onWiperMedium();

    void onWiperSlow();

    void onWiperSpeedDown();

    void onWiperSpeedUp();

    void onWiperSuperhigh();

    default void setCapsuleUniversal(String str) {
    }

    default void setLluSw(boolean z) {
    }
}
