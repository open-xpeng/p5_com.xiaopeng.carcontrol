package com.xiaopeng.speech.protocol.query.speech;

import android.text.TextUtils;
import com.xiaopeng.lib.framework.aiassistantmodule.sensor.ContextSensor;
import com.xiaopeng.speech.SpeechQuery;
import com.xiaopeng.speech.common.util.LogUtils;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class SpeechSensorQuery extends SpeechQuery<ISpeechQueryCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public int getSoundLocation(String str, String str2) {
        return ((ISpeechQueryCaller) this.mQueryCaller).getSoundLocation();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isAppForeground(String str, String str2) {
        String str3;
        try {
            str3 = new JSONObject(str2).optString(ContextSensor.DATA_PACKAGE);
        } catch (JSONException e) {
            e.printStackTrace();
            str3 = "";
        }
        return ((ISpeechQueryCaller) this.mQueryCaller).isAppForeground(str3);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isAccountLogin(String str, String str2) {
        return ((ISpeechQueryCaller) this.mQueryCaller).isAccountLogin();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isEnableGlobalWakeup(String str, String str2) {
        return ((ISpeechQueryCaller) this.mQueryCaller).isEnableGlobalWakeup();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getCurrentMode(String str, String str2) {
        return ((ISpeechQueryCaller) this.mQueryCaller).getCurrentMode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getCarPlatform(String str, String str2) {
        return ((ISpeechQueryCaller) this.mQueryCaller).getCarPlatForm();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getVuiSceneSwitchStatus(String str, String str2) {
        return ((ISpeechQueryCaller) this.mQueryCaller).getVuiSceneSwitchStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getFirstSpeechState(String str, String str2) {
        return ((ISpeechQueryCaller) this.mQueryCaller).getFirstSpeechState();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getCurrentTtsEngine(String str, String str2) {
        return ((ISpeechQueryCaller) this.mQueryCaller).getCurrentTtsEngine();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean appIsInstalled(String str, String str2) {
        LogUtils.d("SpeechUiQuery", "enter appIsInstalled , event = " + str + ", data = " + str2);
        try {
            if (TextUtils.isEmpty(str2)) {
                return false;
            }
            return ((ISpeechQueryCaller) this.mQueryCaller).appIsInstalled(new JSONObject(str2).optString(VuiConstants.SCENE_PACKAGE_NAME, ""));
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isUserExpressionOpened(String str, String str2) {
        return ((ISpeechQueryCaller) this.mQueryCaller).isUserExpressionOpened();
    }
}
