package com.xiaopeng.lib.framework.moduleinterface.carcontroller;

/* loaded from: classes2.dex */
public interface IIpuController extends ILifeCycle {

    /* loaded from: classes2.dex */
    public static class CtrlCurrEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class CtrlTempEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class CtrlVoltEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class IpuFailStInfoEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class MotorStatusEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class MotorTempEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class RollSpeedEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class TorqueEventMsg extends AbstractEventMsg<Float> {
    }

    int getCtrlCurr() throws Exception;

    int getCtrlTemp() throws Exception;

    int getCtrlVolt() throws Exception;

    int getIpuFailStInfo() throws Exception;

    int getMotorStatus() throws Exception;

    int getMotorTemp() throws Exception;

    int getRollSpeed() throws Exception;

    float getTorque() throws Exception;
}
