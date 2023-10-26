package com.xiaopeng.speech.protocol.node.charge;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.xiaopeng.speech.SpeechNode;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class ChargeNode extends SpeechNode<ChargeListener> {
    public static final String CHARGE_CLTC_MILEAGE = "CLTC";
    public static final String CHARGE_NEDC_MILEAGE = "NEDC";
    public static final String CHARGE_WLTP_MILEAGE = "WLTP";

    protected void onPortOpenSupport(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ChargeListener) obj).onPortOpenSupport();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPortOpen(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ChargeListener) obj).onPortOpen();
            }
        }
    }

    protected void onStartSupport(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ChargeListener) obj).onStartSupport();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onStart(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ChargeListener) obj).onStart();
            }
        }
    }

    protected void onRestartSupport(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ChargeListener) obj).onRestartSupport();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRestart(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ChargeListener) obj).onRestart();
            }
        }
    }

    protected void onStopSupport(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ChargeListener) obj).onStopSupport();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onStop(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ChargeListener) obj).onStop();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onUiOpen(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ChargeListener) obj).onUiOpen();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onUiClose(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ChargeListener) obj).onUiClose();
            }
        }
    }

    protected void onModePercentSupport(String str, String str2) {
        int i;
        try {
            i = Integer.parseInt(new JSONObject(str2).optString(TypedValues.Attributes.S_TARGET));
        } catch (Exception e) {
            e.printStackTrace();
            i = 0;
        }
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ChargeListener) obj).onModePercentSupport(i);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onModePercent(String str, String str2) {
        int i;
        try {
            i = Integer.parseInt(new JSONObject(str2).optString(TypedValues.Attributes.S_TARGET));
        } catch (Exception e) {
            e.printStackTrace();
            i = 0;
        }
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ChargeListener) obj).onModePercent(i);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onModeFull(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ChargeListener) obj).onModeFull();
            }
        }
    }

    protected void onModeSmartOnSupport(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ChargeListener) obj).onModeSmartOnSupport();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onModeSmartOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ChargeListener) obj).onModeSmartOn();
            }
        }
    }

    protected void onModeSmartCloseSupport(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ChargeListener) obj).onModeSmartCloseSupport();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onModeSmartClose(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ChargeListener) obj).onModeSmartClose();
            }
        }
    }

    protected void onModeSmartOffSupport(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ChargeListener) obj).onModeSmartOffSupport();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onModeSmartOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ChargeListener) obj).onModeSmartOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onChangeWltpMileageMode(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ChargeListener) obj).onChangeMileageMode(CHARGE_WLTP_MILEAGE);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onChangeNedcMileageMode(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ChargeListener) obj).onChangeMileageMode(CHARGE_NEDC_MILEAGE);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onChargeTrunkPowerOpen(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ChargeListener) obj).onChargeTrunkPower(true);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onChargeTrunkPowerClose(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ChargeListener) obj).onChargeTrunkPower(false);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setDischargeLimit(String str, String str2) {
        int i;
        try {
            i = Integer.parseInt(new JSONObject(str2).optString(TypedValues.Attributes.S_TARGET));
        } catch (Exception e) {
            e.printStackTrace();
            i = 0;
        }
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ChargeListener) obj).setDischargeLimit(i);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onChangeCltcMileageMode(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((ChargeListener) obj).onChangeMileageMode(CHARGE_CLTC_MILEAGE);
            }
        }
    }
}
