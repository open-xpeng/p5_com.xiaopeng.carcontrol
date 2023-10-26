package com.xiaopeng.speech.protocol.node.carac;

import com.xiaopeng.speech.SpeechNode;
import com.xiaopeng.speech.common.util.LogUtils;
import com.xiaopeng.speech.protocol.bean.SpeechParam;
import com.xiaopeng.speech.protocol.node.carac.bean.ChangeValue;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class CaracNode extends SpeechNode<CaracListener> {
    /* JADX INFO: Access modifiers changed from: protected */
    public void onHvacOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onHvacOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onHvacOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onHvacOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onAcOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onAcOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onAcOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onAcOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onAutoOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onAutoOn();
            }
        }
    }

    protected void onAutoOffSupport(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onAutoOffSupport();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onAutoOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onAutoOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onBlowFootOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onBlowFootOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onHeadFootOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onHeadFootOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onBlowHeadOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onBlowHeadOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onDemistFrontOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onDemistFrontOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onDemistFrontOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onDemistFrontOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onDemistRearOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onDemistRearOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onDemistRearOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onDemistRearOn();
            }
        }
    }

    protected void onDemistFootOnSupport(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onDemistFootOnSupport();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onDemistFootOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onDemistFootOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onInnerOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onInnerOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onInnerOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onInnerOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindDown(String str, String str2) {
        ChangeValue fromJson = ChangeValue.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onWindDown(fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindUp(String str, String str2) {
        ChangeValue fromJson = ChangeValue.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onWindUp(fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindSet(String str, String str2) {
        ChangeValue fromJson = ChangeValue.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onWindSet(fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRearHeatOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onRearHeatOff();
            }
        }
    }

    protected void onRearHeatOffSupport(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onRearHeatOffSupport();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRearHeatOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onRearHeatOn();
            }
        }
    }

    protected void onRearHeatOnSupport(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onRearHeatOnSupport();
            }
        }
    }

    protected void onTempDownHalfSupport(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onTempDownHalfSupport();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTempDown(String str, String str2) {
        ChangeValue fromJson = ChangeValue.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onTempDown(fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTempUp(String str, String str2) {
        ChangeValue fromJson = ChangeValue.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onTempUp(fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTempSet(String str, String str2) {
        ChangeValue fromJson = ChangeValue.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onTempSet(fromJson);
            }
        }
    }

    protected void onTempUpHalfSupport(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onTempUpHalfSupport();
            }
        }
    }

    protected void onPurifierOpenSupport(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onPurifierOpenSupport();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPurifierOpen(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onPurifierOpen();
            }
        }
    }

    protected void onPurifierCloseSupport(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onPurifierCloseSupport();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPurifierClose(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onPurifierClose();
            }
        }
    }

    protected void onPurifierPm25(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onPurifierPm25();
            }
        }
    }

    protected void onTempDriverUpSupport(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onTempDriverUpSupport();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTempDriverUp(String str, String str2) {
        ChangeValue fromJson = ChangeValue.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onTempDriverUp(fromJson);
            }
        }
    }

    protected void onTempDriverDownSupport(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onTempDriverDownSupport();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTempDriverDown(String str, String str2) {
        ChangeValue fromJson = ChangeValue.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onTempDriverDown(fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTempDriverSet(String str, String str2) {
        ChangeValue fromJson = ChangeValue.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onTempDriverSet(fromJson);
            }
        }
    }

    protected void onTempPassengerUpSupport(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onTempPassengerUpSupport();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTempPassengerUp(String str, String str2) {
        ChangeValue fromJson = ChangeValue.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onTempPassengerUp(fromJson);
            }
        }
    }

    protected void onTempPassengerDownSupport(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onTempPassengerDownSupport();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTempPassengerDown(String str, String str2) {
        ChangeValue fromJson = ChangeValue.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onTempPassengerDown(fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTempPassengerSet(String str, String str2) {
        ChangeValue fromJson = ChangeValue.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onTempPassengerSet(fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTempDualSyn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onTempDualSyn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTempDualOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onTempDualOff();
            }
        }
    }

    protected void onDataTempTTS(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onDataTempTTS();
            }
        }
    }

    protected void onDataWindTTS(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onDataWindTTS();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindMax(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        SpeechParam fromJson = SpeechParam.fromJson(str2);
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onWindMax(fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindMin(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        SpeechParam fromJson = SpeechParam.fromJson(str2);
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onWindMin(fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTempMin(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        SpeechParam fromJson = SpeechParam.fromJson(str2);
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onTempMin(fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTempMax(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onTempMax();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTempDriveMin(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onTempDriveMin();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTempDriveMax(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onTempDriveMax();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTempPassengerMin(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onTempPassengerMin();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTempPassengerMax(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onTempPassengerMax();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatHeatingOpen(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onSeatHeatingOpen();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatHeatingClose(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onSeatHeatingClose();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatPsnHeatingOpen(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onSeatPsnHeatingOpen();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatPsnHeatingClose(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onSeatPsnHeatingClose();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatHeatingMax(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onSeatHeatingMax();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatHeatingMin(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onSeatHeatingMin();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatHeatingUp(String str, String str2) {
        ChangeValue fromJson = ChangeValue.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onSeatHeatingUp(fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatHeatingDown(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onSeatHeatingDown();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatVentilateOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        SpeechParam fromJson = SpeechParam.fromJson(str2);
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onSeatVentilateOn(fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatVentilateOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onSeatVentilateOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatVentilateMax(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onSeatVentilateMax();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatVentilateMin(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onSeatVentilateMin();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatVentilateDown(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onSeatVentilateDown();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatVentilateUp(String str, String str2) {
        ChangeValue fromJson = ChangeValue.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onSeatVentilateUp(fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatVentilateDriverSet(String str, String str2) {
        ChangeValue fromJson = ChangeValue.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onSeatVentilateDriverSet(fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatHeatDriverSet(String str, String str2) {
        ChangeValue fromJson = ChangeValue.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onSeatHeatDriverSet(fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatHeatPassengerSet(String str, String str2) {
        ChangeValue fromJson = ChangeValue.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onSeatHeatPassengerSet(fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatHeatPassengerUp(String str, String str2) {
        ChangeValue fromJson = ChangeValue.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onSeatHeatPassengerUp(fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatHeatPassengerDown(String str, String str2) {
        ChangeValue fromJson = ChangeValue.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onSeatHeatPassengerDown(fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onOpenFastFridge(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onOpenFastFridge();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onOpenFreshAir(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onOpenFreshAir();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onExitFastFridge(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onExitFastFridge();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onExitFreshAir(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onExitFreshAir();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Removed duplicated region for block: B:10:0x003d A[LOOP:0: B:10:0x003d->B:12:0x0040, LOOP_START, PHI: r4 
      PHI: (r4v2 int) = (r4v1 int), (r4v3 int) binds: [B:9:0x003b, B:12:0x0040] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:13:0x004a A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onWindUnidirectionSet(java.lang.String r4, java.lang.String r5) {
        /*
            r3 = this;
            r4 = 0
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch: java.lang.Throwable -> L1b
            r0.<init>(r5)     // Catch: java.lang.Throwable -> L1b
            java.lang.String r1 = "pilot"
            java.lang.String r1 = r0.optString(r1)     // Catch: java.lang.Throwable -> L1b
            java.lang.String r2 = "direction"
            java.lang.String r0 = r0.optString(r2)     // Catch: java.lang.Throwable -> L1b
            int r1 = java.lang.Integer.parseInt(r1)     // Catch: java.lang.Throwable -> L1b
            int r5 = java.lang.Integer.parseInt(r0)     // Catch: java.lang.Throwable -> L1c
            goto L35
        L1b:
            r1 = r4
        L1c:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "onWindUnidirectionSet string data error, data = "
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.StringBuilder r5 = r0.append(r5)
            java.lang.String r5 = r5.toString()
            java.lang.String r0 = "CaracNode"
            com.xiaopeng.speech.common.util.LogUtils.e(r0, r5)
            r5 = 7
        L35:
            com.xiaopeng.speech.common.util.SimpleCallbackList<T> r0 = r3.mListenerList
            java.lang.Object[] r0 = r0.collectCallbacks()
            if (r0 == 0) goto L4a
        L3d:
            int r2 = r0.length
            if (r4 >= r2) goto L4a
            r2 = r0[r4]
            com.xiaopeng.speech.protocol.node.carac.CaracListener r2 = (com.xiaopeng.speech.protocol.node.carac.CaracListener) r2
            r2.onWindUnidirectionSet(r1, r5)
            int r4 = r4 + 1
            goto L3d
        L4a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.speech.protocol.node.carac.CaracNode.onWindUnidirectionSet(java.lang.String, java.lang.String):void");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Removed duplicated region for block: B:10:0x003d A[LOOP:0: B:10:0x003d->B:12:0x0040, LOOP_START, PHI: r4 
      PHI: (r4v2 int) = (r4v1 int), (r4v3 int) binds: [B:9:0x003b, B:12:0x0040] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:13:0x004a A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onWindUnindirection(java.lang.String r4, java.lang.String r5) {
        /*
            r3 = this;
            r4 = 0
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch: java.lang.Throwable -> L1b
            r0.<init>(r5)     // Catch: java.lang.Throwable -> L1b
            java.lang.String r1 = "pilot"
            java.lang.String r1 = r0.optString(r1)     // Catch: java.lang.Throwable -> L1b
            java.lang.String r2 = "direction"
            java.lang.String r0 = r0.optString(r2)     // Catch: java.lang.Throwable -> L1b
            int r1 = java.lang.Integer.parseInt(r1)     // Catch: java.lang.Throwable -> L1b
            int r5 = java.lang.Integer.parseInt(r0)     // Catch: java.lang.Throwable -> L1c
            goto L35
        L1b:
            r1 = r4
        L1c:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "onWindUnindirection string data error, data = "
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.StringBuilder r5 = r0.append(r5)
            java.lang.String r5 = r5.toString()
            java.lang.String r0 = "CaracNode"
            com.xiaopeng.speech.common.util.LogUtils.e(r0, r5)
            r5 = 7
        L35:
            com.xiaopeng.speech.common.util.SimpleCallbackList<T> r0 = r3.mListenerList
            java.lang.Object[] r0 = r0.collectCallbacks()
            if (r0 == 0) goto L4a
        L3d:
            int r2 = r0.length
            if (r4 >= r2) goto L4a
            r2 = r0[r4]
            com.xiaopeng.speech.protocol.node.carac.CaracListener r2 = (com.xiaopeng.speech.protocol.node.carac.CaracListener) r2
            r2.onWindUnidirection(r1, r5)
            int r4 = r4 + 1
            goto L3d
        L4a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.speech.protocol.node.carac.CaracNode.onWindUnindirection(java.lang.String, java.lang.String):void");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Removed duplicated region for block: B:10:0x003d A[LOOP:0: B:10:0x003d->B:12:0x0040, LOOP_START, PHI: r4 
      PHI: (r4v2 int) = (r4v1 int), (r4v3 int) binds: [B:9:0x003b, B:12:0x0040] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:13:0x004a A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onWindEvad(java.lang.String r4, java.lang.String r5) {
        /*
            r3 = this;
            r4 = 0
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch: java.lang.Throwable -> L1b
            r0.<init>(r5)     // Catch: java.lang.Throwable -> L1b
            java.lang.String r1 = "pilot"
            java.lang.String r1 = r0.optString(r1)     // Catch: java.lang.Throwable -> L1b
            java.lang.String r2 = "direction"
            java.lang.String r0 = r0.optString(r2)     // Catch: java.lang.Throwable -> L1b
            int r1 = java.lang.Integer.parseInt(r1)     // Catch: java.lang.Throwable -> L1b
            int r5 = java.lang.Integer.parseInt(r0)     // Catch: java.lang.Throwable -> L1c
            goto L35
        L1b:
            r1 = r4
        L1c:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "onWindEvad string data error, data = "
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.StringBuilder r5 = r0.append(r5)
            java.lang.String r5 = r5.toString()
            java.lang.String r0 = "CaracNode"
            com.xiaopeng.speech.common.util.LogUtils.e(r0, r5)
            r5 = 7
        L35:
            com.xiaopeng.speech.common.util.SimpleCallbackList<T> r0 = r3.mListenerList
            java.lang.Object[] r0 = r0.collectCallbacks()
            if (r0 == 0) goto L4a
        L3d:
            int r2 = r0.length
            if (r4 >= r2) goto L4a
            r2 = r0[r4]
            com.xiaopeng.speech.protocol.node.carac.CaracListener r2 = (com.xiaopeng.speech.protocol.node.carac.CaracListener) r2
            r2.onWindEvad(r1, r5)
            int r4 = r4 + 1
            goto L3d
        L4a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.speech.protocol.node.carac.CaracNode.onWindEvad(java.lang.String, java.lang.String):void");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Removed duplicated region for block: B:10:0x003d A[LOOP:0: B:10:0x003d->B:12:0x0040, LOOP_START, PHI: r4 
      PHI: (r4v2 int) = (r4v1 int), (r4v3 int) binds: [B:9:0x003b, B:12:0x0040] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:13:0x004a A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onWindFront(java.lang.String r4, java.lang.String r5) {
        /*
            r3 = this;
            r4 = 0
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch: java.lang.Throwable -> L1b
            r0.<init>(r5)     // Catch: java.lang.Throwable -> L1b
            java.lang.String r1 = "pilot"
            java.lang.String r1 = r0.optString(r1)     // Catch: java.lang.Throwable -> L1b
            java.lang.String r2 = "direction"
            java.lang.String r0 = r0.optString(r2)     // Catch: java.lang.Throwable -> L1b
            int r1 = java.lang.Integer.parseInt(r1)     // Catch: java.lang.Throwable -> L1b
            int r5 = java.lang.Integer.parseInt(r0)     // Catch: java.lang.Throwable -> L1c
            goto L35
        L1b:
            r1 = r4
        L1c:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "onWindFront string data error, data = "
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.StringBuilder r5 = r0.append(r5)
            java.lang.String r5 = r5.toString()
            java.lang.String r0 = "CaracNode"
            com.xiaopeng.speech.common.util.LogUtils.e(r0, r5)
            r5 = 7
        L35:
            com.xiaopeng.speech.common.util.SimpleCallbackList<T> r0 = r3.mListenerList
            java.lang.Object[] r0 = r0.collectCallbacks()
            if (r0 == 0) goto L4a
        L3d:
            int r2 = r0.length
            if (r4 >= r2) goto L4a
            r2 = r0[r4]
            com.xiaopeng.speech.protocol.node.carac.CaracListener r2 = (com.xiaopeng.speech.protocol.node.carac.CaracListener) r2
            r2.onWindFront(r1, r5)
            int r4 = r4 + 1
            goto L3d
        L4a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.speech.protocol.node.carac.CaracNode.onWindFront(java.lang.String, java.lang.String):void");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Removed duplicated region for block: B:10:0x003d A[LOOP:0: B:10:0x003d->B:12:0x0040, LOOP_START, PHI: r4 
      PHI: (r4v2 int) = (r4v1 int), (r4v3 int) binds: [B:9:0x003b, B:12:0x0040] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:13:0x004a A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onWindFree(java.lang.String r4, java.lang.String r5) {
        /*
            r3 = this;
            r4 = 0
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch: java.lang.Throwable -> L1b
            r0.<init>(r5)     // Catch: java.lang.Throwable -> L1b
            java.lang.String r1 = "pilot"
            java.lang.String r1 = r0.optString(r1)     // Catch: java.lang.Throwable -> L1b
            java.lang.String r2 = "direction"
            java.lang.String r0 = r0.optString(r2)     // Catch: java.lang.Throwable -> L1b
            int r1 = java.lang.Integer.parseInt(r1)     // Catch: java.lang.Throwable -> L1b
            int r5 = java.lang.Integer.parseInt(r0)     // Catch: java.lang.Throwable -> L1c
            goto L35
        L1b:
            r1 = r4
        L1c:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "onWindFree string data error, data = "
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.StringBuilder r5 = r0.append(r5)
            java.lang.String r5 = r5.toString()
            java.lang.String r0 = "CaracNode"
            com.xiaopeng.speech.common.util.LogUtils.e(r0, r5)
            r5 = 7
        L35:
            com.xiaopeng.speech.common.util.SimpleCallbackList<T> r0 = r3.mListenerList
            java.lang.Object[] r0 = r0.collectCallbacks()
            if (r0 == 0) goto L4a
        L3d:
            int r2 = r0.length
            if (r4 >= r2) goto L4a
            r2 = r0[r4]
            com.xiaopeng.speech.protocol.node.carac.CaracListener r2 = (com.xiaopeng.speech.protocol.node.carac.CaracListener) r2
            r2.onWindFree(r1, r5)
            int r4 = r4 + 1
            goto L3d
        L4a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.speech.protocol.node.carac.CaracNode.onWindFree(java.lang.String, java.lang.String):void");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindAutoSweepOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onWindAutoSweepOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindAutoSweepOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onWindAutoSweepOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onAqsOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onAqsOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onAqsOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onAqsOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onModesEcoOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onModesEcoOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onModesEcoOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onModesEcoOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onHeatingOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onHeatingOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onHeatingOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onHeatingOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onNatureOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onNatureOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onNatureOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onNatureOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindowOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onWindowOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPsnTempSynOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onPsnTempSynOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPsnTempSynOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onPsnTempSynOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onHvacPanelOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onAcPanelOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void openIntelligentPsn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).openIntelligentPsn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void closeIntelligentPsn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).closeIntelligentPsn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onMirrorOn(String str, String str2) {
        int i;
        try {
            i = Integer.parseInt(new JSONObject(str2).optString("pilot"));
        } catch (Throwable unused) {
            LogUtils.e("CaracNode", "onMirrorOn string data error, data = " + str2);
            i = 0;
        }
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onMirrorOn(i);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onMirrorOff(String str, String str2) {
        int i;
        try {
            i = Integer.parseInt(new JSONObject(str2).optString("pilot"));
        } catch (Throwable unused) {
            LogUtils.e("CaracNode", "onMirrorOff string data error, data = " + str2);
            i = 0;
        }
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onMirrorOff(i);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindOutletOn(String str, String str2) {
        int i;
        try {
            i = Integer.parseInt(new JSONObject(str2).optString("pilot"));
        } catch (Throwable unused) {
            LogUtils.e("CaracNode", "nWindOutletOn string data error, data = " + str2);
            i = 0;
        }
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onWindOutletOn(i);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindOutletOff(String str, String str2) {
        int i;
        try {
            i = Integer.parseInt(new JSONObject(str2).optString("pilot"));
        } catch (Throwable unused) {
            LogUtils.e("CaracNode", "onWindOutletOff string data error, data = " + str2);
            i = 0;
        }
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onWindOutletOff(i);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLeftRearSeatHeatingOpen(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onRearSeatHeatingOpenClose(0, 0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRightRearSeatHeatingOpen(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onRearSeatHeatingOpenClose(0, 1);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLeftRearSeatHeatingClose(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onRearSeatHeatingOpenClose(1, 0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRightRearSeatHeatingClose(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onRearSeatHeatingOpenClose(1, 1);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLeftRearSeatHeatSet(String str, String str2) {
        ChangeValue fromJson = ChangeValue.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onRearSeatHeatSet(0, fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRightRearSeatHeatSet(String str, String str2) {
        ChangeValue fromJson = ChangeValue.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onRearSeatHeatSet(1, fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLeftRearSeatHeatUp(String str, String str2) {
        ChangeValue fromJson = ChangeValue.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onRearSeatHeatUp(0, fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRightRearSeatHeatUp(String str, String str2) {
        ChangeValue fromJson = ChangeValue.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onRearSeatHeatUp(1, fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLeftRearSeatHeatDown(String str, String str2) {
        ChangeValue fromJson = ChangeValue.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onRearSeatHeatDown(0, fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRightRearSeatHeatDown(String str, String str2) {
        ChangeValue fromJson = ChangeValue.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onRearSeatHeatDown(1, fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onHeadWindowOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onHeadWindowOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onHeadWindowFootOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onHeadWindowFootOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onHeadWindowOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CaracListener) obj).onHeadWindowOff();
            }
        }
    }
}
