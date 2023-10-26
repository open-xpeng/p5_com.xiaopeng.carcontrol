package com.xiaopeng.speech.protocol.query.speech.radar;

import com.xiaopeng.speech.SpeechQuery;

/* loaded from: classes2.dex */
public class SpeechRadarQuery extends SpeechQuery<ISpeechRadarQueryCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public float[] getFrontRadarData(String str, String str2) {
        return ((ISpeechRadarQueryCaller) this.mQueryCaller).getFrontRadarData();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public float[] getTailRadarData(String str, String str2) {
        return ((ISpeechRadarQueryCaller) this.mQueryCaller).getTailRadarData();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int[] getFrontRadarLevel(String str, String str2) {
        return ((ISpeechRadarQueryCaller) this.mQueryCaller).getFrontRadarLevel();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int[] getTailRadarLevel(String str, String str2) {
        return ((ISpeechRadarQueryCaller) this.mQueryCaller).getTailRadarLevel();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int[] getFrontRadarFaultSt(String str, String str2) {
        return ((ISpeechRadarQueryCaller) this.mQueryCaller).getFrontRadarFaultSt();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int[] getTailRadarFaultSt(String str, String str2) {
        return ((ISpeechRadarQueryCaller) this.mQueryCaller).getTailRadarFaultSt();
    }
}
