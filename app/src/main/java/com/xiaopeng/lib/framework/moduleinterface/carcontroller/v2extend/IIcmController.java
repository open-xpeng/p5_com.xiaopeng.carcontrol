package com.xiaopeng.lib.framework.moduleinterface.carcontroller.v2extend;

import com.xiaopeng.lib.framework.moduleinterface.carcontroller.AbstractEventMsg;

/* loaded from: classes2.dex */
public interface IIcmController {
    public static final int LOCAL_RADIO = 1;
    public static final int NET_RADIO = 2;
    public static final int NONE_RADIO = 3;

    /* loaded from: classes2.dex */
    public static class IcmConnectEventMsgV2 extends AbstractEventMsg<Integer> {
    }

    void setRadioType(int i) throws Exception;
}
