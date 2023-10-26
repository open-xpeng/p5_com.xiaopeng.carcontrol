package com.xiaopeng.lib.framework.netchannelmodule;

import com.xiaopeng.lib.framework.module.IModuleEntry;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IHttp;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.IMessaging;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.IRemoteStorage;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.websocket.IRxWebSocket;
import com.xiaopeng.lib.framework.netchannelmodule.http.HttpImpl;
import com.xiaopeng.lib.framework.netchannelmodule.messaging.MessagingImpl;
import com.xiaopeng.lib.framework.netchannelmodule.remotestorage.RemoteAwsStorageImpl;
import com.xiaopeng.lib.framework.netchannelmodule.remotestorage.RemoteOssStorageImpl;
import com.xiaopeng.lib.framework.netchannelmodule.websocket.RxWebSocketImpl;
import com.xiaopeng.lib.utils.info.DeviceInfoUtils;

/* loaded from: classes2.dex */
public class NetworkChannelsEntry implements IModuleEntry {
    private final Creator<? extends IRemoteStorage> mRemoteStorageChannel;
    private final Creator<MessagingImpl> mMessagingChannel = new Creator<>(MessagingImpl.class);
    private final Creator<HttpImpl> mHttpChannel = new Creator<>(HttpImpl.class);
    private final Creator<RxWebSocketImpl> mWebSocketChannel = new Creator<>(RxWebSocketImpl.class);

    public NetworkChannelsEntry() {
        if (DeviceInfoUtils.isInternationalVer()) {
            this.mRemoteStorageChannel = new Creator<>(RemoteAwsStorageImpl.class);
        } else {
            this.mRemoteStorageChannel = new Creator<>(RemoteOssStorageImpl.class);
        }
    }

    @Override // com.xiaopeng.lib.framework.module.IModuleEntry
    public Object get(Class cls) {
        if (IMessaging.class == cls) {
            return this.mMessagingChannel.get(this);
        }
        if (IRemoteStorage.class == cls) {
            return this.mRemoteStorageChannel.get(this);
        }
        if (IHttp.class == cls) {
            return this.mHttpChannel.get(this);
        }
        if (IRxWebSocket.class == cls) {
            return this.mWebSocketChannel.get(this);
        }
        return null;
    }

    /* loaded from: classes2.dex */
    private static class Creator<T> {
        private Class<T> mClassType;
        private volatile T mInstance;

        public Creator(Class<T> cls) {
            this.mClassType = cls;
        }

        public T get(Object obj) {
            if (this.mInstance == null) {
                synchronized (obj) {
                    if (this.mInstance == null) {
                        try {
                            this.mInstance = this.mClassType.newInstance();
                        } catch (Exception unused) {
                            throw new RuntimeException("Failed to initialize the channel.");
                        }
                    }
                }
            }
            return this.mInstance;
        }
    }
}
