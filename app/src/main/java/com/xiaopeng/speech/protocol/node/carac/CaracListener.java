package com.xiaopeng.speech.protocol.node.carac;

import com.xiaopeng.speech.INodeListener;
import com.xiaopeng.speech.protocol.bean.SpeechParam;
import com.xiaopeng.speech.protocol.node.carac.bean.ChangeValue;

/* loaded from: classes2.dex */
public interface CaracListener extends INodeListener {
    void closeIntelligentPsn();

    void onAcOff();

    void onAcOn();

    void onAcPanelOn();

    void onAqsOff();

    void onAqsOn();

    void onAutoOff();

    void onAutoOffSupport();

    void onAutoOn();

    void onBlowFootOn();

    void onBlowHeadOn();

    void onDataTempTTS();

    void onDataWindTTS();

    void onDemistFootOn();

    void onDemistFootOnSupport();

    void onDemistFrontOff();

    void onDemistFrontOn();

    void onDemistRearOff();

    void onDemistRearOn();

    void onExitFastFridge();

    void onExitFreshAir();

    void onHeadFootOn();

    default void onHeadWindowFootOn() {
    }

    default void onHeadWindowOff() {
    }

    default void onHeadWindowOn() {
    }

    void onHeatingOff();

    void onHeatingOn();

    void onHvacOff();

    void onHvacOn();

    void onInnerOff();

    void onInnerOn();

    void onMirrorOff(int i);

    void onMirrorOn(int i);

    void onModesEcoOff();

    void onModesEcoOn();

    void onNatureOff();

    void onNatureOn();

    void onOpenFastFridge();

    void onOpenFreshAir();

    void onPsnTempSynOff();

    void onPsnTempSynOn();

    void onPurifierClose();

    void onPurifierCloseSupport();

    void onPurifierOpen();

    void onPurifierOpenSupport();

    void onPurifierPm25();

    void onRearHeatOff();

    void onRearHeatOffSupport();

    void onRearHeatOn();

    void onRearHeatOnSupport();

    default void onRearSeatHeatDown(int i, ChangeValue changeValue) {
    }

    default void onRearSeatHeatSet(int i, ChangeValue changeValue) {
    }

    default void onRearSeatHeatUp(int i, ChangeValue changeValue) {
    }

    default void onRearSeatHeatingOpenClose(int i, int i2) {
    }

    void onSeatHeatDriverSet(ChangeValue changeValue);

    void onSeatHeatPassengerDown(ChangeValue changeValue);

    void onSeatHeatPassengerSet(ChangeValue changeValue);

    void onSeatHeatPassengerUp(ChangeValue changeValue);

    void onSeatHeatingClose();

    void onSeatHeatingDown();

    void onSeatHeatingMax();

    void onSeatHeatingMin();

    void onSeatHeatingOpen();

    void onSeatHeatingUp(ChangeValue changeValue);

    void onSeatPsnHeatingClose();

    void onSeatPsnHeatingOpen();

    void onSeatVentilateDown();

    void onSeatVentilateDriverSet(ChangeValue changeValue);

    void onSeatVentilateMax();

    void onSeatVentilateMin();

    void onSeatVentilateOff();

    void onSeatVentilateOn(SpeechParam speechParam);

    void onSeatVentilateUp(ChangeValue changeValue);

    void onTempDown(ChangeValue changeValue);

    void onTempDownHalfSupport();

    void onTempDriveMax();

    void onTempDriveMin();

    void onTempDriverDown(ChangeValue changeValue);

    void onTempDriverDownSupport();

    void onTempDriverSet(ChangeValue changeValue);

    void onTempDriverUp(ChangeValue changeValue);

    void onTempDriverUpSupport();

    void onTempDualOff();

    void onTempDualSyn();

    void onTempMax();

    void onTempMin(SpeechParam speechParam);

    void onTempPassengerDown(ChangeValue changeValue);

    void onTempPassengerDownSupport();

    void onTempPassengerMax();

    void onTempPassengerMin();

    void onTempPassengerSet(ChangeValue changeValue);

    void onTempPassengerUp(ChangeValue changeValue);

    void onTempPassengerUpSupport();

    void onTempSet(ChangeValue changeValue);

    void onTempUp(ChangeValue changeValue);

    void onTempUpHalfSupport();

    void onWindAutoSweepOff();

    void onWindAutoSweepOn();

    void onWindDown(ChangeValue changeValue);

    void onWindEvad(int i, int i2);

    void onWindFree(int i, int i2);

    void onWindFront(int i, int i2);

    void onWindMax(SpeechParam speechParam);

    void onWindMin(SpeechParam speechParam);

    void onWindOutletOff(int i);

    void onWindOutletOn(int i);

    void onWindSet(ChangeValue changeValue);

    void onWindUnidirection(int i, int i2);

    void onWindUnidirectionSet(int i, int i2);

    void onWindUp(ChangeValue changeValue);

    void onWindowOn();

    void openIntelligentPsn();
}
