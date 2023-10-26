package com.xiaopeng.smartcontrol.sdk.client;

import android.os.Handler;

/* loaded from: classes2.dex */
public class ClientConfig {
    private Handler callbackHandler;
    private int waitTimeout;

    private ClientConfig(Builder builder) {
        this.waitTimeout = builder.waitTimeout;
        this.callbackHandler = builder.callbackHandler;
    }

    public int getWaitTimeout() {
        return this.waitTimeout;
    }

    public Handler getCallbackHandler() {
        return this.callbackHandler;
    }

    /* loaded from: classes2.dex */
    public static class Builder {
        private Handler callbackHandler;
        private int waitTimeout;

        public Builder setWaitTimeout(int i) {
            this.waitTimeout = i;
            return this;
        }

        public Builder setCallbackHandler(Handler handler) {
            this.callbackHandler = handler;
            return this;
        }

        public ClientConfig build() {
            return new ClientConfig(this);
        }
    }
}
