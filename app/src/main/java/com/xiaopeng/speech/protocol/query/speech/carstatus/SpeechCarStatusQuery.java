package com.xiaopeng.speech.protocol.query.speech.carstatus;

import com.xiaopeng.speech.SpeechQuery;

/* loaded from: classes2.dex */
public class SpeechCarStatusQuery extends SpeechQuery<ISpeechCarstatusQueryCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public int getCurrentMode(String str, String str2) {
        return ((ISpeechCarstatusQueryCaller) this.mQueryCaller).getCurrentMode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getAcPowerStatus(String str, String str2) {
        return ((ISpeechCarstatusQueryCaller) this.mQueryCaller).getAcPowerStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getAcQualityPurgeStatus(String str, String str2) {
        return ((ISpeechCarstatusQueryCaller) this.mQueryCaller).getAcQualityPurgeStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public float getAcTempDriverValue(String str, String str2) {
        return ((ISpeechCarstatusQueryCaller) this.mQueryCaller).getAcTempDriverValue();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public float getAcTempPsnValue(String str, String str2) {
        return ((ISpeechCarstatusQueryCaller) this.mQueryCaller).getAcTempPsnValue();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getAcWindSpeedLevel(String str, String str2) {
        return ((ISpeechCarstatusQueryCaller) this.mQueryCaller).getAcWindSpeedLevel();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getAcWindBlowMode(String str, String str2) {
        return ((ISpeechCarstatusQueryCaller) this.mQueryCaller).getAcWindBlowMode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getAcCirculationMode(String str, String str2) {
        return ((ISpeechCarstatusQueryCaller) this.mQueryCaller).getAcCirculationMode();
    }
}
