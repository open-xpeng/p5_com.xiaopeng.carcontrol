package com.xiaopeng.lib.framework.moduleinterface.carcontroller;

/* loaded from: classes2.dex */
public interface IDcdcController extends ILifeCycle {

    /* loaded from: classes2.dex */
    public static class DcdcFailStInfoEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class DcdcStatusEventMsg extends AbstractEventMsg<Integer> {
    }

    int getDcdcFailStInfo() throws Exception;

    int getDcdcStatus() throws Exception;
}
