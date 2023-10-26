package com.xiaopeng.speech.protocol.query.speech.hardware;

import com.xiaopeng.speech.SpeechQuery;
import com.xiaopeng.speech.protocol.query.speech.hardware.bean.StreamType;

/* loaded from: classes2.dex */
public class SpeechHardwareQuery extends SpeechQuery<ISpeechHardwareQueryCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public String getMcuHardWareId(String str, String str2) {
        return ((ISpeechHardwareQueryCaller) this.mQueryCaller).getMcuHardWareId();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getCpuTemperature(String str, String str2) {
        return ((ISpeechHardwareQueryCaller) this.mQueryCaller).getCpuTemperature();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getIpuFailStInfo(String str, String str2) {
        return ((ISpeechHardwareQueryCaller) this.mQueryCaller).getIpuFailStInfo();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public double getCtrlVolt(String str, String str2) {
        return ((ISpeechHardwareQueryCaller) this.mQueryCaller).getCtrlVolt();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public double getCtrlCurr(String str, String str2) {
        return ((ISpeechHardwareQueryCaller) this.mQueryCaller).getCtrlCurr();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getCtrlTemp(String str, String str2) {
        return ((ISpeechHardwareQueryCaller) this.mQueryCaller).getCtrlTemp();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getMotorTemp(String str, String str2) {
        return ((ISpeechHardwareQueryCaller) this.mQueryCaller).getMotorTemp();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public double getTorque(String str, String str2) {
        return ((ISpeechHardwareQueryCaller) this.mQueryCaller).getTorque();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getRollSpeed(String str, String str2) {
        return ((ISpeechHardwareQueryCaller) this.mQueryCaller).getRollSpeed();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getMotorStatus(String str, String str2) {
        return ((ISpeechHardwareQueryCaller) this.mQueryCaller).getMotorStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getCarType(String str, String str2) {
        return ((ISpeechHardwareQueryCaller) this.mQueryCaller).getCarType();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getStreamType(String str, String str2) {
        return ((ISpeechHardwareQueryCaller) this.mQueryCaller).getStreamType(StreamType.fromJson(str2));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getChecklistStatus(String str, String str2) {
        return ((ISpeechHardwareQueryCaller) this.mQueryCaller).getChecklistStatus(str2);
    }
}
