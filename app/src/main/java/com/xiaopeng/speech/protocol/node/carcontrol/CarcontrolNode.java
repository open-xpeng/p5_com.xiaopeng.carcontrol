package com.xiaopeng.speech.protocol.node.carcontrol;

import android.text.TextUtils;
import com.xiaopeng.speech.SpeechNode;
import com.xiaopeng.speech.protocol.bean.ChangeValue;
import com.xiaopeng.speech.protocol.bean.SpeechParam;
import com.xiaopeng.speech.protocol.bean.base.CommandValue;
import com.xiaopeng.speech.protocol.node.carcontrol.bean.ControlReason;
import com.xiaopeng.speech.protocol.node.carcontrol.bean.SeatValue;
import com.xiaopeng.speech.protocol.node.carcontrol.bean.UserBookValue;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class CarcontrolNode extends SpeechNode<CarcontrolListener> {
    private static final String KEY_PERCENT = "percent";

    private int getPercent(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has(KEY_PERCENT)) {
                return jSONObject.optInt(KEY_PERCENT);
            }
            return 100;
        } catch (Exception e) {
            e.printStackTrace();
            return 100;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLightHomeOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onLightHomeOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLightHomeOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onLightHomeOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLightLowOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        ControlReason fromJson = ControlReason.fromJson(str2);
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onLightLowOff(fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLightLowOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onLightLowOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLightPositionOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onLightPositionOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLightPositionOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onLightPositionOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLightAutoOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onLightAutoOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLightAutoOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onLightAutoOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onMistLightOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onMistLightOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onMistLightOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onMistLightOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onMirrorRearClose(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onMirrorRearClose();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onMirrorRearOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onMirrorRearOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTrunkOpen(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onTrunkOpen();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindowDriverClose(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onWindowsControl(0, 1, getPercent(str2));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindowDriverOpen(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onWindowsControl(0, 0, getPercent(str2));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindowPassengerClose(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onWindowsControl(1, 1, getPercent(str2));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindowPassengerOpen(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onWindowsControl(1, 0, getPercent(str2));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindowsClose(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onWindowsControl(6, 1, getPercent(str2));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindowsOpen(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onWindowsControl(6, 0, getPercent(str2));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTrunkClose(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onTrunkClose();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindowsVentilateOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onWindowsVentilateOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindowsVentilateOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onWindowsVentilateOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onModesDrivingSport(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onModesDrivingSport();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onModesDrivingConservation(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onModesDrivingConservation();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onModesDrivingNormal(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onModesDrivingNormal();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onModesSteeringSoft(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onModesSteeringSoft();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onModesSteeringNormal(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onModesSteeringNormal();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onModesSteeringSport(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onModesSteeringSport();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onEnergyRecycleHigh(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onEnergyRecycleHigh();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onEnergyRecycleLow(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onEnergyRecycleLow();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onEnergyRecycleMedia(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onEnergyRecycleMedia();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onEnergyRecycleUp(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onEnergyRecycleUp();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onEnergyRecycleDown(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onEnergyRecycleDown();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindowRightRearOpen(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onWindowsControl(3, 0, getPercent(str2));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindowRightRearClose(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onWindowsControl(3, 1, getPercent(str2));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindowLeftRearOpen(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onWindowsControl(2, 0, getPercent(str2));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindowLeftRearClose(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onWindowsControl(2, 1, getPercent(str2));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindowFrontOpen(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onWindowsControl(7, 0, getPercent(str2));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindowFrontClose(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onWindowsControl(7, 1, getPercent(str2));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindowRearOpen(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onWindowsControl(8, 0, getPercent(str2));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindowRearClose(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onWindowsControl(8, 1, getPercent(str2));
            }
        }
    }

    protected void onModesSportSupport(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onModesSportSupport();
            }
        }
    }

    protected void onModesConservationSupport(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onModesConservationSupport();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatMoveUp(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onSeatMoveUp();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatMoveDown(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onSeatMoveDown();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatMoveHighest(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onSeatMoveHighest();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatMoveLowest(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onSeatMoveLowest();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatMoveBack(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        ChangeValue fromJson = ChangeValue.fromJson(str2);
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onSeatMoveBack(fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatMoveForward(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        ChangeValue fromJson = ChangeValue.fromJson(str2);
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onSeatMoveForward(fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatMoveRear(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        SpeechParam fromJson = SpeechParam.fromJson(str2);
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onSeatMoveRear(fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatMoveForemost(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onSeatMoveForemost();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatBackrestBack(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        SpeechParam fromJson = SpeechParam.fromJson(str2);
        ChangeValue fromJson2 = ChangeValue.fromJson(str2);
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onSeatBackrestBack(fromJson2, fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatBackrestForward(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        ChangeValue fromJson = ChangeValue.fromJson(str2);
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onSeatBackrestForward(fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatBackrestRear(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        SpeechParam fromJson = SpeechParam.fromJson(str2);
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onSeatBackrestRear(fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatBackrestForemost(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onSeatBackrestForemost();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatAdjust(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        SeatValue fromJson = SeatValue.fromJson(str2);
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onSeatAdjust(fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onControlLightResume(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onControlLightResume();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onControlSeatResume(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onControlSeatResume();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLowVolumeOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onLowVolumeOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLowVolumeOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onLowVolumeOff();
            }
        }
    }

    protected void onCheckUserBook(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        UserBookValue fromJson = UserBookValue.fromJson(str2);
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onCheckUserBook(fromJson);
            }
        }
    }

    protected void onOpenUserBook(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onOpenUserBook();
            }
        }
    }

    protected void onCloseUserBook(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onCloseUserBook();
            }
        }
    }

    protected void onLightTopAutoOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onLightTopAutoOn();
            }
        }
    }

    protected void onLightTopAutoOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onLightTopAutoOff();
            }
        }
    }

    protected void onLightTopOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onLightTopOn();
            }
        }
    }

    protected void onLightTopOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onLightTopOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLightAtmosphereOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onLightAtmosphereOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLightAtmosphereOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onLightAtmosphereOff(0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onMirrorRearSet(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onMirrorRearSet(0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWiperSpeedUp(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onWiperSpeedUp();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWiperSpeedDown(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onWiperSpeedDown();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWiperSlow(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onWiperSlow();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWiperHigh(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onWiperHigh();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWiperMedium(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onWiperMedium();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWiperSuperhigh(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onWiperSuperhigh();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTrunkUnlock(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onTrunkUnlock();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindowsLeftClose(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onWindowsControl(4, 1, getPercent(str2));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindowsLeftOpen(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onWindowsControl(4, 0, getPercent(str2));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindowsRightClose(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onWindowsControl(5, 1, getPercent(str2));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindowsRightOpen(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onWindowsControl(5, 0, getPercent(str2));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLegUp(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onLegUp(0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLegDown(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onLegDown(0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLegHighest(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onLegHighest(0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLegLowest(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onLegLowest(0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatRestore(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onSeatRestore();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRightChargePortOpen(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onChargePortControl(1, 0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRightChargePortClose(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onChargePortControl(1, 1);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLeftChargePortClose(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onChargePortControl(0, 1);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLeftChargePortOpen(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onChargePortControl(0, 0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTirePressureShow(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onTirePressureShow();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLightAtmosphereColor(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onLightAtmosphereColor(new CommandValue(str2));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLightAtmosphereBrightnessSet(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onLightAtmosphereBrightnessSet(new CommandValue(str2));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLightAtmosphereBrightnessUp() {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onLightAtmosphereBrightnessUp();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLightAtmosphereBrightnessDown() {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onLightAtmosphereBrightnessDown();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLightAtmosphereBrightnessMax() {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onLightAtmosphereBrightnessMax();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLightAtmosphereBrightnessMin() {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onLightAtmosphereBrightnessMin();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPsnSeatMoveUp() {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onPsnSeatMoveUp();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPsnSeatMoveDown() {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onPsnSeatMoveDown();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPsnSeatBackrestBack() {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onPsnSeatBackrestBack();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPsnSeatBackrestForward() {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onPsnSeatBackrestForward();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPsnSeatMoveBack() {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onPsnSeatMoveBack();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPsnSeatMoveForward() {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onPsnSeatMoveForward();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onControlXpedalOn() {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onControlXpedalOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onControlXpedalOff() {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onControlXpedalOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onControlScissorLeftDoorOn() {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onControlScissorLeftDoorOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onControlScissorRightDoorOn() {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onControlScissorRightDoorOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onControlScissorLeftDoorOff() {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onControlScissorLeftDoorOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onControlScissorRightDoorOff() {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onControlScissorRightDoorOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onControlScissorLeftDoorPause() {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onControlScissorLeftDoorPause();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onControlScissorRightDoorPause() {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onControlScissorRightDoorPause();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onControlElectricCurtainOpen(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onControlSunShade(0, getPercent(str2));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onControlElectricCurtainClose(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onControlSunShade(1, getPercent(str2));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onControlComfortableDrivingModeOpen(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onControlComfortableDrivingModeOpen();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onControlComfortableDrivingModeClose(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).onControlComfortableDrivingModeClose();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onControlLightLanguageOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).setLluSw(true);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onControlLightLanguageOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).setLluSw(false);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setCapsuleUniversal(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CarcontrolListener) obj).setCapsuleUniversal(getModeFromJson(str2));
            }
        }
    }

    private String getModeFromJson(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            return jSONObject.has("mode") ? jSONObject.optString("mode") : "";
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
}
