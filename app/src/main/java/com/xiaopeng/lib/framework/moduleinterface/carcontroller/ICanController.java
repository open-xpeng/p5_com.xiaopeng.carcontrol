package com.xiaopeng.lib.framework.moduleinterface.carcontroller;

/* loaded from: classes2.dex */
public interface ICanController extends ILifeCycle {

    /* loaded from: classes2.dex */
    public static class CanDiagnoseEventMsg extends AbstractEventMsg<String> {
    }

    /* loaded from: classes2.dex */
    public static class CanRawDataEventMsg extends AbstractEventMsg<int[]> {
    }

    byte[] getCanRawData() throws Exception;

    void sendCanDataSync() throws Exception;

    void setAdasMeta(byte[] bArr) throws Exception;

    void setAdasPosition(byte[] bArr) throws Exception;

    void setAdasProfLong(byte[] bArr) throws Exception;

    void setAdasProfShort(byte[] bArr) throws Exception;

    void setAdasSegment(byte[] bArr) throws Exception;

    void setAdasStub(byte[] bArr) throws Exception;
}
