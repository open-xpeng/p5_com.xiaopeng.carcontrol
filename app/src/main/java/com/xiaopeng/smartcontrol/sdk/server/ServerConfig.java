package com.xiaopeng.smartcontrol.sdk.server;

/* loaded from: classes2.dex */
public class ServerConfig {
    private boolean sendAsync;

    private ServerConfig(Builder builder) {
        this.sendAsync = builder.sendAsync;
    }

    public boolean sendAsync() {
        return this.sendAsync;
    }

    /* loaded from: classes2.dex */
    public static class Builder {
        private boolean sendAsync;

        public Builder setSendAsync(boolean z) {
            this.sendAsync = z;
            return this;
        }

        public ServerConfig build() {
            return new ServerConfig(this);
        }
    }
}
