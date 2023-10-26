package com.xiaopeng.speech.protocol.query.speech.radar;

import com.xiaopeng.speech.IQueryCaller;

/* loaded from: classes2.dex */
public interface ISpeechRadarQueryCaller extends IQueryCaller {
    float[] getFrontRadarData();

    int[] getFrontRadarFaultSt();

    int[] getFrontRadarLevel();

    float[] getTailRadarData();

    int[] getTailRadarFaultSt();

    int[] getTailRadarLevel();
}
