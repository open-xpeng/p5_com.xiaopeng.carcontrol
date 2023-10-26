package com.xiaopeng.speech.protocol.node.autoparking;

import android.os.Binder;
import android.os.IBinder;
import com.xiaopeng.speech.SpeechClient;
import com.xiaopeng.speech.SpeechNode;
import com.xiaopeng.speech.jarvisproto.DMEnd;
import com.xiaopeng.speech.jarvisproto.WakeupResult;
import com.xiaopeng.speech.protocol.node.autoparking.bean.ParkingPositionBean;
import com.xiaopeng.speech.protocol.node.dialog.AbsDialogListener;
import com.xiaopeng.speech.protocol.node.dialog.DialogNode;
import com.xiaopeng.speech.protocol.node.dialog.bean.DialogEndReason;
import com.xiaopeng.speech.proxy.HotwordEngineProxy;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class AutoParkingNode extends SpeechNode<AutoParkingListener> {
    private static final String COMMAND_SPLIT = "#";
    private static final String FIND_SKILL_NAME = "沿途找车位";
    private static final String FIND_TASK_PARK = "沿途泊车";
    private static final String INTENT_FIND_POSITION_PARK = "找到车位";
    private static final long SET_EXIT_PARKING_TIMEOUT = 150;
    private static final String SKILL_NAME = "离线自动泊车";
    private static final String TASK_PARK = "自动泊车";
    private IBinder mWakeupBinder = new Binder();
    private volatile boolean isShouldExitParking = false;

    private void removeSetExitParkingRunnable() {
    }

    public AutoParkingNode() {
        SpeechClient.instance().getNodeManager().subscribe(DialogNode.class, new AbsDialogListener() { // from class: com.xiaopeng.speech.protocol.node.autoparking.AutoParkingNode.1
            @Override // com.xiaopeng.speech.protocol.node.dialog.AbsDialogListener, com.xiaopeng.speech.protocol.node.dialog.DialogListener
            public void onDialogEnd(DialogEndReason dialogEndReason) {
                super.onDialogEnd(dialogEndReason);
                if ("voice".equals(dialogEndReason.reason) || DMEnd.REASON_WHEEL.equals(dialogEndReason.reason)) {
                    AutoParkingNode.this.onExit("", "");
                }
            }
        });
    }

    public void findParkingPosition(String str) {
        findParkingPosition(str, false);
    }

    public void findParkingPosition(String str, boolean z) {
        String str2;
        String str3;
        String str4;
        String str5 = "";
        if (z) {
            try {
                str5 = new JSONObject().put("tts", str).put("isLocalSkill", true).put(WakeupResult.REASON_COMMAND, "command://autoparking.park.start#native://autoparking.park.carport.count#command://autoparking.find.parking.space.continue#command://autoparking.find.parking.space.exit").toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            str2 = str5;
            str3 = FIND_SKILL_NAME;
            str4 = FIND_TASK_PARK;
        } else {
            try {
                str5 = new JSONObject().put("tts", str).put("isLocalSkill", true).put(WakeupResult.REASON_COMMAND, "command://autoparking.park.start#native://autoparking.park.carport.count#command://autoparking.exit").toString();
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            str2 = str5;
            str3 = SKILL_NAME;
            str4 = TASK_PARK;
        }
        stopCarSpeech();
        SpeechClient.instance().getAgent().triggerIntentWithBinder(this.mWakeupBinder, str3, str4, INTENT_FIND_POSITION_PARK, str2);
    }

    public boolean isShouldExitParking() {
        return this.isShouldExitParking;
    }

    public void setShouldExitParking(boolean z) {
        removeSetExitParkingRunnable();
        this.isShouldExitParking = z;
    }

    public void setWheelWakeupEnabled(boolean z) {
        SpeechClient.instance().getWakeupEngine().setWheelWakeupEnabled(this.mWakeupBinder, z);
    }

    public void enableWakeup() {
        SpeechClient.instance().getWakeupEngine().enableMainWakeupWord(this.mWakeupBinder);
        SpeechClient.instance().getWakeupEngine().enableFastWakeEnhance(this.mWakeupBinder);
        SpeechClient.instance().getHotwordEngine().removeHotWords(HotwordEngineProxy.BY_AUTO_PARKING);
        SpeechClient.instance().getHotwordEngine().removeHotWords(HotwordEngineProxy.BY_PARKING_ALONG_THE_WAY);
    }

    public void disableWakeup() {
        SpeechClient.instance().getWakeupEngine().disableMainWakeupWord(this.mWakeupBinder);
        SpeechClient.instance().getWakeupEngine().disableFastWakeEnhance(this.mWakeupBinder);
    }

    public void stopCarSpeech() {
        removeSetExitParkingRunnable();
        this.isShouldExitParking = false;
        SpeechClient.instance().getWakeupEngine().stopDialogReason("auto_park");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onActivate(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((AutoParkingListener) obj).onActivate();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onExit(String str, String str2) {
        removeSetExitParkingRunnable();
        this.isShouldExitParking = false;
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((AutoParkingListener) obj).onExit();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onParkStart(String str, String str2) {
        removeSetExitParkingRunnable();
        this.isShouldExitParking = false;
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((AutoParkingListener) obj).onParkStart(str2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onParkCarportCount(String str, String str2) {
        ParkingPositionBean fromJson = ParkingPositionBean.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((AutoParkingListener) obj).onParkCarportCount(fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onMemoryParkingStart(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((AutoParkingListener) obj).onMemoryParkingStart();
            }
        }
    }
}
