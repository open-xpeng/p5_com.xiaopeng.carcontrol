package com.xiaopeng.speech.protocol.node.controlcard;

import com.xiaopeng.speech.INodeListener;
import com.xiaopeng.speech.protocol.bean.CardValue;

/* loaded from: classes2.dex */
public interface ControlCardListener extends INodeListener {
    void showAcDriverTempCard(CardValue cardValue);

    void showAcPassengerTempCard(CardValue cardValue);

    void showAcSeatHeatingDriverCard(CardValue cardValue);

    void showAcSeatHeatingPassengerCard(CardValue cardValue);

    void showAcSeatVentilateDriverCard(CardValue cardValue);

    void showAcTempCard(CardValue cardValue);

    void showAcWindCard(CardValue cardValue);

    void showAtmosphereBrightnessCard(CardValue cardValue);

    void showAtmosphereBrightnessColorCard(CardValue cardValue);

    void showSystemIcmBrightnessCard(CardValue cardValue);

    void showSystemScreenBrightnessCard(CardValue cardValue);
}
