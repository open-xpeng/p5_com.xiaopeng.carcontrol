package com.xiaopeng.speech.protocol.node.carac;

import com.xiaopeng.speech.protocol.bean.SpeechParam;
import com.xiaopeng.speech.protocol.node.carac.bean.ChangeValue;

/* loaded from: classes2.dex */
public abstract class AbsCaracListener implements CaracListener {
    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void closeIntelligentPsn() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onAcOff() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onAcOn() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onAcPanelOn() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onAqsOff() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onAqsOn() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onAutoOff() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onAutoOffSupport() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onAutoOn() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onBlowFootOn() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onBlowHeadOn() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onDataTempTTS() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onDataWindTTS() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onDemistFootOn() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onDemistFootOnSupport() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onDemistFrontOff() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onDemistFrontOn() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onDemistRearOff() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onDemistRearOn() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onExitFastFridge() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onExitFreshAir() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onHeadFootOn() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onHeatingOff() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onHeatingOn() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onHvacOff() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onHvacOn() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onInnerOff() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onInnerOn() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onMirrorOff(int i) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onMirrorOn(int i) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onModesEcoOff() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onModesEcoOn() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onNatureOff() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onNatureOn() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onOpenFastFridge() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onOpenFreshAir() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onPsnTempSynOff() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onPsnTempSynOn() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onPurifierClose() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onPurifierCloseSupport() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onPurifierOpen() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onPurifierOpenSupport() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onPurifierPm25() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onRearHeatOff() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onRearHeatOffSupport() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onRearHeatOn() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onRearHeatOnSupport() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onSeatHeatDriverSet(ChangeValue changeValue) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onSeatHeatPassengerDown(ChangeValue changeValue) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onSeatHeatPassengerSet(ChangeValue changeValue) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onSeatHeatPassengerUp(ChangeValue changeValue) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onSeatHeatingClose() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onSeatHeatingDown() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onSeatHeatingMax() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onSeatHeatingMin() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onSeatHeatingOpen() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onSeatHeatingUp(ChangeValue changeValue) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onSeatPsnHeatingClose() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onSeatPsnHeatingOpen() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onSeatVentilateDown() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onSeatVentilateDriverSet(ChangeValue changeValue) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onSeatVentilateMax() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onSeatVentilateMin() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onSeatVentilateOff() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onSeatVentilateOn(SpeechParam speechParam) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onSeatVentilateUp(ChangeValue changeValue) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onTempDown(ChangeValue changeValue) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onTempDownHalfSupport() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onTempDriveMax() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onTempDriveMin() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onTempDriverDown(ChangeValue changeValue) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onTempDriverDownSupport() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onTempDriverSet(ChangeValue changeValue) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onTempDriverUp(ChangeValue changeValue) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onTempDriverUpSupport() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onTempDualOff() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onTempDualSyn() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onTempMax() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onTempMin(SpeechParam speechParam) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onTempPassengerDown(ChangeValue changeValue) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onTempPassengerDownSupport() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onTempPassengerMax() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onTempPassengerMin() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onTempPassengerSet(ChangeValue changeValue) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onTempPassengerUp(ChangeValue changeValue) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onTempPassengerUpSupport() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onTempSet(ChangeValue changeValue) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onTempUp(ChangeValue changeValue) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onTempUpHalfSupport() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onWindAutoSweepOff() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onWindAutoSweepOn() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onWindDown(ChangeValue changeValue) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onWindEvad(int i, int i2) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onWindFree(int i, int i2) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onWindFront(int i, int i2) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onWindMax(SpeechParam speechParam) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onWindMin(SpeechParam speechParam) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onWindOutletOff(int i) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onWindOutletOn(int i) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onWindSet(ChangeValue changeValue) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onWindUnidirection(int i, int i2) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onWindUnidirectionSet(int i, int i2) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onWindUp(ChangeValue changeValue) {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void onWindowOn() {
    }

    @Override // com.xiaopeng.speech.protocol.node.carac.CaracListener
    public void openIntelligentPsn() {
    }
}
