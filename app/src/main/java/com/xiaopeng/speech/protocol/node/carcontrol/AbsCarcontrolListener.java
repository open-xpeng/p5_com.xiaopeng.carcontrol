package com.xiaopeng.speech.protocol.node.carcontrol;

import com.xiaopeng.speech.protocol.bean.ChangeValue;
import com.xiaopeng.speech.protocol.bean.SpeechParam;
import com.xiaopeng.speech.protocol.bean.base.CommandValue;
import com.xiaopeng.speech.protocol.node.carcontrol.bean.ControlReason;
import com.xiaopeng.speech.protocol.node.carcontrol.bean.SeatValue;
import com.xiaopeng.speech.protocol.node.carcontrol.bean.UserBookValue;

/* loaded from: classes2.dex */
public abstract class AbsCarcontrolListener implements CarcontrolListener {
    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onChargePortControl(int i, int i2) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onCheckUserBook(UserBookValue userBookValue) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onCloseUserBook() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onControlComfortableDrivingModeClose() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onControlComfortableDrivingModeOpen() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onControlLightResume() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onControlScissorLeftDoorOff() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onControlScissorLeftDoorOn() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onControlScissorLeftDoorPause() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onControlScissorRightDoorOff() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onControlScissorRightDoorOn() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onControlScissorRightDoorPause() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onControlSeatResume() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onControlSunShade(int i, int i2) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onControlXpedalOff() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onControlXpedalOn() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onEnergyRecycleDown() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onEnergyRecycleHigh() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onEnergyRecycleLow() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onEnergyRecycleMedia() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onEnergyRecycleUp() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onLegDown(int i) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onLegHighest(int i) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onLegLowest(int i) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onLegUp(int i) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onLightAtmosphereBrightnessDown() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onLightAtmosphereBrightnessMax() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onLightAtmosphereBrightnessMin() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onLightAtmosphereBrightnessSet(CommandValue commandValue) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onLightAtmosphereBrightnessUp() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onLightAtmosphereColor(CommandValue commandValue) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onLightAtmosphereOff(int i) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onLightAtmosphereOn() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onLightAutoOff() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onLightAutoOn() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onLightHomeOff() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onLightHomeOn() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onLightLowOff() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onLightLowOff(ControlReason controlReason) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onLightLowOn() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onLightPositionOff() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onLightPositionOn() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onLightTopAutoOff() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onLightTopAutoOn() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onLightTopOff() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onLightTopOn() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onLowVolumeOff() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onLowVolumeOn() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onMirrorRearClose() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onMirrorRearOn() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onMirrorRearSet(int i) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onMistLightOff() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onMistLightOn() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onModesConservationSupport() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onModesDrivingConservation() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onModesDrivingNormal() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onModesDrivingSport() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onModesSportSupport() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onModesSteeringNormal() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onModesSteeringSoft() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onModesSteeringSport() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onOpenUserBook() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onPsnSeatBackrestBack() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onPsnSeatBackrestForward() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onPsnSeatMoveBack() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onPsnSeatMoveDown() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onPsnSeatMoveForward() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onPsnSeatMoveUp() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onSeatAdjust(SeatValue seatValue) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onSeatBackrestBack(ChangeValue changeValue, SpeechParam speechParam) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onSeatBackrestForemost() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onSeatBackrestForward(ChangeValue changeValue) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onSeatBackrestRear(SpeechParam speechParam) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onSeatMoveBack(ChangeValue changeValue) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onSeatMoveDown() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onSeatMoveForemost() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onSeatMoveForward(ChangeValue changeValue) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onSeatMoveHighest() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onSeatMoveLowest() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onSeatMoveRear(SpeechParam speechParam) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onSeatMoveUp() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onSeatRestore() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onTirePressureShow() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onTrunkClose() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onTrunkOpen() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onTrunkUnlock() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onWindowDriverClose() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onWindowDriverOpen() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onWindowFrontClose() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onWindowFrontOpen() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onWindowLeftRearClose() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onWindowLeftRearOpen() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onWindowPassengerClose() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onWindowPassengerOpen() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onWindowRearClose() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onWindowRearOpen() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onWindowRightRearClose() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onWindowRightRearOpen() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onWindowsClose() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onWindowsControl(int i, int i2, int i3) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onWindowsOpen() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onWindowsVentilateOff() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onWindowsVentilateOn() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onWiperHigh() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onWiperMedium() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onWiperSlow() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onWiperSpeedDown() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onWiperSpeedUp() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void onWiperSuperhigh() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener
    public void setLluSw(boolean z) {
    }
}
