package com.xiaopeng.speech.protocol.query.speech.ac;

import com.xiaopeng.speech.IQueryCaller;

/* loaded from: classes2.dex */
public interface ISpeechACQueryCaller extends IQueryCaller {
    boolean getBCMBackDefrostMode();

    boolean getBCMBackMirrorHeatMode();

    int getBCMSeatBlowLevel();

    int getBCMSeatHeatLevel();

    boolean getHvacAutoMode();

    int getHvacAutoModeBlowLevel();

    int getHvacCirculationMode();

    boolean getHvacFrontDefrostMode();

    float getHvacInnerTemp();

    boolean getHvacPowerMode();

    int getHvacQualityInnerPM25Value();

    int getHvacQualityOutsideLevel();

    int getHvacQualityOutsideStatus();

    boolean getHvacQualityPurgeMode();

    float getHvacTempDriverValue();

    float getHvacTempPsnValue();

    boolean getHvacTempSyncMode();

    int getHvacWindBlowMode();

    int getHvacWindSpeedLevel();

    int getPsnSeatHeatLv();
}
