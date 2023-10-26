package com.xiaopeng.lib.framework.moduleinterface.carcontroller.v2extend;

import com.xiaopeng.lib.framework.moduleinterface.carcontroller.AbstractEventMsg;

/* loaded from: classes2.dex */
public interface IAvmController {

    /* loaded from: classes2.dex */
    public static class V2FrontRadarDataEventMsg extends AbstractEventMsg<float[]> {
    }

    /* loaded from: classes2.dex */
    public static class V2TailRadarDataEventMsg extends AbstractEventMsg<float[]> {
    }

    int getOverlayWorkSt() throws Exception;

    void setMultipleDisplayProperties(int i, int i2, int i3, int i4, int i5) throws Exception;

    void setOverlayWorkSt(int i) throws Exception;
}
