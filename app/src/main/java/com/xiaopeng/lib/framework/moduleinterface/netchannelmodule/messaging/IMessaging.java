package com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging;

import android.app.Application;

/* loaded from: classes2.dex */
public interface IMessaging {
    public static final long DEFAULT_MESSAGE_ID = 0;

    /* loaded from: classes2.dex */
    public enum CHANNEL {
        COMMUNICATION,
        REPORTING,
        TESTING
    }

    boolean available();

    void initChannelWithContext(CHANNEL channel, Application application) throws Exception;

    long publish(String str, String str2) throws Exception;

    long publish(String str, String str2, QOS qos) throws Exception;

    long publish(String str, byte[] bArr) throws Exception;

    long publish(String str, byte[] bArr, QOS qos) throws Exception;

    void subscribe(String str) throws Exception;

    /* loaded from: classes2.dex */
    public enum QOS {
        LEVEL_0(0),
        LEVEL_1(1),
        LEVEL_2(2);
        
        private int value;

        QOS(int i) {
            this.value = i;
        }

        public int value() {
            return this.value;
        }
    }
}
