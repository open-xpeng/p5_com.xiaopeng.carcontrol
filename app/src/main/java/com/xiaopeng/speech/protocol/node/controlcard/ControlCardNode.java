package com.xiaopeng.speech.protocol.node.controlcard;

import com.xiaopeng.speech.SpeechNode;
import com.xiaopeng.speech.protocol.bean.CardValue;

/* loaded from: classes2.dex */
public class ControlCardNode extends SpeechNode<ControlCardListener> {
    /* JADX INFO: Access modifiers changed from: protected */
    public void showAcTempCard(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            CardValue fromJsonForAcTemp = CardValue.fromJsonForAcTemp(str2);
            for (Object obj : collectCallbacks) {
                ((ControlCardListener) obj).showAcTempCard(fromJsonForAcTemp);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void showAcDriverTempCard(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            CardValue fromJsonForAcTemp = CardValue.fromJsonForAcTemp(str2);
            for (Object obj : collectCallbacks) {
                ((ControlCardListener) obj).showAcDriverTempCard(fromJsonForAcTemp);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void showAcPassengerTempCard(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            CardValue fromJsonForAcTemp = CardValue.fromJsonForAcTemp(str2);
            for (Object obj : collectCallbacks) {
                ((ControlCardListener) obj).showAcPassengerTempCard(fromJsonForAcTemp);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void showAcWindCard(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            CardValue fromJsonForAcWindLv = CardValue.fromJsonForAcWindLv(str2);
            for (Object obj : collectCallbacks) {
                ((ControlCardListener) obj).showAcWindCard(fromJsonForAcWindLv);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void showAtmosphereBrightnessCard(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            CardValue fromJsonForAtmosphereBrightness = CardValue.fromJsonForAtmosphereBrightness(str2);
            for (Object obj : collectCallbacks) {
                ((ControlCardListener) obj).showAtmosphereBrightnessCard(fromJsonForAtmosphereBrightness);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void showAtmosphereBrightnessColorCard(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            CardValue fromJsonForAtmosphereColor = CardValue.fromJsonForAtmosphereColor(str2);
            for (Object obj : collectCallbacks) {
                ((ControlCardListener) obj).showAtmosphereBrightnessColorCard(fromJsonForAtmosphereColor);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void showAcSeatHeatingDriverCard(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            CardValue fromJsonForAcSeatHeating = CardValue.fromJsonForAcSeatHeating(str2);
            for (Object obj : collectCallbacks) {
                ((ControlCardListener) obj).showAcSeatHeatingDriverCard(fromJsonForAcSeatHeating);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void showAcSeatHeatingPassengerCard(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            CardValue fromJsonForAcSeatHeating = CardValue.fromJsonForAcSeatHeating(str2);
            for (Object obj : collectCallbacks) {
                ((ControlCardListener) obj).showAcSeatHeatingPassengerCard(fromJsonForAcSeatHeating);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void showAcSeatVentilateDriverCard(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            CardValue fromJsonForAcSeatVentilate = CardValue.fromJsonForAcSeatVentilate(str2);
            for (Object obj : collectCallbacks) {
                ((ControlCardListener) obj).showAcSeatVentilateDriverCard(fromJsonForAcSeatVentilate);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void showSystemScreenBrightnessCard(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            CardValue fromJsonForScreenBrightness = CardValue.fromJsonForScreenBrightness(str2);
            for (Object obj : collectCallbacks) {
                ((ControlCardListener) obj).showSystemScreenBrightnessCard(fromJsonForScreenBrightness);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void showSystemIcmBrightnessCard(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            CardValue fromJsonForIcmBrightness = CardValue.fromJsonForIcmBrightness(str2);
            for (Object obj : collectCallbacks) {
                ((ControlCardListener) obj).showSystemIcmBrightnessCard(fromJsonForIcmBrightness);
            }
        }
    }
}
