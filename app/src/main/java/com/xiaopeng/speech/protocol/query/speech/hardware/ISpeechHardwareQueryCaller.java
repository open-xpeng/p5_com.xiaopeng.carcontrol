package com.xiaopeng.speech.protocol.query.speech.hardware;

import com.xiaopeng.speech.IQueryCaller;
import com.xiaopeng.speech.protocol.query.speech.hardware.bean.StreamType;

/* loaded from: classes2.dex */
public interface ISpeechHardwareQueryCaller extends IQueryCaller {
    String getCarType();

    int getChecklistStatus(String str);

    int getCpuTemperature();

    double getCtrlCurr();

    int getCtrlTemp();

    double getCtrlVolt();

    int getIpuFailStInfo();

    String getMcuHardWareId();

    int getMotorStatus();

    int getMotorTemp();

    int getRollSpeed();

    int getStreamType(StreamType streamType);

    double getTorque();
}
