package com.xiaopeng.lib.framework.netchannelmodule.http.traffic.plain;

import android.os.Build;
import com.xiaopeng.lib.utils.LogUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketImpl;
import java.net.SocketImplFactory;

/* loaded from: classes2.dex */
public class CountingPlainSocketInstrument implements SocketImplFactory {
    private static Constructor mPlainSocketImplConstruct;
    private static Constructor mSocksSocketImplConstruct;
    private boolean mIsServer;

    public CountingPlainSocketInstrument(boolean z) {
        this.mIsServer = z;
    }

    public static synchronized void initialize() {
        synchronized (CountingPlainSocketInstrument.class) {
            try {
                Constructor<?> declaredConstructor = Class.forName("java.net.PlainSocketImpl").getDeclaredConstructor(new Class[0]);
                mPlainSocketImplConstruct = declaredConstructor;
                declaredConstructor.setAccessible(true);
                Socket.setSocketImplFactory(new CountingPlainSocketInstrument(false));
            } catch (Exception e) {
                LogUtils.w(e.toString());
            }
            if (!isKitkat()) {
                try {
                    Constructor<?> declaredConstructor2 = Class.forName("java.net.SocksSocketImpl").getDeclaredConstructor(new Class[0]);
                    mSocksSocketImplConstruct = declaredConstructor2;
                    declaredConstructor2.setAccessible(true);
                    ServerSocket.setSocketFactory(new CountingPlainSocketInstrument(true));
                } catch (Exception e2) {
                    LogUtils.w(e2.toString());
                }
            }
        }
    }

    private static boolean isKitkat() {
        return Build.VERSION.SDK_INT == 19;
    }

    public static synchronized void reset() {
        synchronized (CountingPlainSocketInstrument.class) {
            try {
                Field declaredField = Socket.class.getDeclaredField("factory");
                declaredField.setAccessible(true);
                declaredField.set(null, null);
            } catch (Exception e) {
                LogUtils.w(e.toString());
            }
            if (!isKitkat()) {
                try {
                    Field declaredField2 = ServerSocket.class.getDeclaredField("factory");
                    declaredField2.setAccessible(true);
                    declaredField2.set(null, null);
                } catch (Exception e2) {
                    LogUtils.w(e2.toString());
                }
            }
        }
    }

    @Override // java.net.SocketImplFactory
    public SocketImpl createSocketImpl() {
        try {
            if (isKitkat()) {
                return new HookPlainSocketKitKatImpl((SocketImpl) mPlainSocketImplConstruct.newInstance(new Object[0]));
            }
            return new HookPlainSocketPImpl((SocketImpl) (this.mIsServer ? mSocksSocketImplConstruct : mPlainSocketImplConstruct).newInstance(new Object[0]));
        } catch (Exception e) {
            if (!isKitkat() && this.mIsServer) {
                Constructor constructor = mSocksSocketImplConstruct;
                if (constructor != null) {
                    try {
                        return (SocketImpl) constructor.newInstance(new Object[0]);
                    } catch (Exception e2) {
                        LogUtils.w(e2.toString());
                        LogUtils.w(e.toString());
                        return new EmptySocketImpl();
                    }
                }
            } else {
                Constructor constructor2 = mPlainSocketImplConstruct;
                if (constructor2 != null) {
                    try {
                        return (SocketImpl) constructor2.newInstance(new Object[0]);
                    } catch (Exception e3) {
                        LogUtils.w(e3.toString());
                        LogUtils.w(e.toString());
                        return new EmptySocketImpl();
                    }
                }
            }
            LogUtils.w(e.toString());
            return new EmptySocketImpl();
        }
    }
}
