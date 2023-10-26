package com.xiaopeng.lib.framework.netchannelmodule.http.traffic.plain;

import com.lzy.okgo.model.HttpHeaders;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingConstants;
import com.xiaopeng.lib.framework.netchannelmodule.http.statistic.SocketCounter;
import com.xiaopeng.lib.framework.netchannelmodule.http.traffic.CountingInputStream;
import com.xiaopeng.lib.framework.netchannelmodule.http.traffic.CountingOutputStream;
import com.xiaopeng.lib.framework.netchannelmodule.http.traffic.ICollector;
import com.xiaopeng.lib.utils.LogUtils;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketImpl;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.paho.android.service.MqttServiceConstants;

/* loaded from: classes2.dex */
public class HookPlainSocketPImpl extends SocketImpl {
    private static final String TAG = "SocketImplHook";
    private static Field mFieldAddress;
    private static Field mFieldFd;
    private static Field mFieldLocalPort;
    private static Field mFieldPort;
    private static Class mSuperClass;
    private static Map<String, Method> methodMap = new HashMap();
    private ICollector mCollector = new ICollector() { // from class: com.xiaopeng.lib.framework.netchannelmodule.http.traffic.plain.HookPlainSocketPImpl.1
        @Override // com.xiaopeng.lib.framework.netchannelmodule.http.traffic.ICollector
        public String getDomain() {
            return HookPlainSocketPImpl.this.mDomain;
        }
    };
    private SocketCounter mCounter = SocketCounter.getInstance();
    private SocketImpl mDelegate;
    private String mDomain;

    static {
        try {
            mSuperClass = Class.forName("java.net.AbstractPlainSocketImpl");
            mFieldAddress = SocketImpl.class.getDeclaredField("address");
            mFieldPort = SocketImpl.class.getDeclaredField("port");
            mFieldLocalPort = SocketImpl.class.getDeclaredField("localport");
            mFieldFd = SocketImpl.class.getDeclaredField("fd");
            mFieldAddress.setAccessible(true);
            mFieldPort.setAccessible(true);
            mFieldFd.setAccessible(true);
            mFieldLocalPort.setAccessible(true);
        } catch (Exception e) {
            LogUtils.d(TAG, e);
        }
    }

    private static Method getMethod(String str, Class<?> cls, String str2, Class<?>... clsArr) {
        Method method = methodMap.get(str);
        if (method == null) {
            try {
                method = cls.getDeclaredMethod(str2, clsArr);
                method.setAccessible(true);
                methodMap.put(str, method);
                return method;
            } catch (Exception e) {
                LogUtils.d(TAG, e);
                return method;
            }
        }
        return method;
    }

    public HookPlainSocketPImpl(SocketImpl socketImpl) {
        this.mDelegate = socketImpl;
    }

    @Override // java.net.SocketImpl
    protected int getPort() {
        return ((Integer) getValue(mFieldPort, this.mDelegate, Integer.valueOf(this.port))).intValue();
    }

    @Override // java.net.SocketImpl
    protected InetAddress getInetAddress() {
        return (InetAddress) getValue(mFieldAddress, this.mDelegate, this.address);
    }

    @Override // java.net.SocketImpl
    protected int getLocalPort() {
        return ((Integer) getValue(mFieldLocalPort, this.mDelegate, Integer.valueOf(this.localport))).intValue();
    }

    @Override // java.net.SocketImpl
    protected FileDescriptor getFileDescriptor() {
        return (FileDescriptor) getValue(mFieldFd, this.mDelegate, this.fd);
    }

    private void syncFd() {
        this.fd = (FileDescriptor) getValue(mFieldFd, this.mDelegate, this.fd);
    }

    public void syncFromLocalToDelegate() {
        setValue(mFieldFd, this.mDelegate, this.fd);
        setValue(mFieldAddress, this.mDelegate, this.address);
        setValue(mFieldPort, this.mDelegate, Integer.valueOf(this.port));
        setValue(mFieldLocalPort, this.mDelegate, Integer.valueOf(this.localport));
    }

    @Override // java.net.SocketImpl
    protected void accept(SocketImpl socketImpl) throws IOException {
        if (socketImpl instanceof HookPlainSocketPImpl) {
            ((HookPlainSocketPImpl) socketImpl).syncFromLocalToDelegate();
        }
        invoke(getMethod("accept", mSuperClass, "accept", SocketImpl.class), true, this.mDelegate, socketImpl);
    }

    @Override // java.net.SocketImpl
    protected void shutdownInput() throws IOException {
        invoke(getMethod("shutdownInput", mSuperClass, "shutdownInput", new Class[0]), true, this.mDelegate, new Object[0]);
    }

    @Override // java.net.SocketImpl
    protected void shutdownOutput() throws IOException {
        invoke(getMethod("shutdownOutput", mSuperClass, "shutdownOutput", new Class[0]), true, this.mDelegate, new Object[0]);
    }

    @Override // java.net.SocketImpl
    protected int available() throws IOException {
        Integer num = (Integer) invoke(getMethod("available", mSuperClass, "available", new Class[0]), false, this.mDelegate, new Object[0]);
        if (num == null) {
            return -1;
        }
        return num.intValue();
    }

    @Override // java.net.SocketImpl
    protected void bind(InetAddress inetAddress, int i) throws IOException {
        invoke(getMethod("bind", mSuperClass, "bind", InetAddress.class, Integer.TYPE), true, this.mDelegate, inetAddress, Integer.valueOf(i));
    }

    @Override // java.net.SocketImpl
    protected void close() throws IOException {
        invoke(getMethod(HttpHeaders.HEAD_VALUE_CONNECTION_CLOSE, mSuperClass, HttpHeaders.HEAD_VALUE_CONNECTION_CLOSE, new Class[0]), true, this.mDelegate, new Object[0]);
    }

    @Override // java.net.SocketImpl
    protected void connect(String str, int i) throws IOException {
        LogUtils.d(TAG, "host=" + str + ",port=" + i);
        this.mDomain = str + QuickSettingConstants.JOINER + i;
        invoke(getMethod("connect0", mSuperClass, MqttServiceConstants.CONNECT_ACTION, String.class, Integer.TYPE), true, this.mDelegate, str, Integer.valueOf(i));
        this.mCounter.increaseRequest(this.mDomain, 0L);
    }

    @Override // java.net.SocketImpl
    protected void connect(InetAddress inetAddress, int i) throws IOException {
        LogUtils.d(TAG, "InetAddr=" + inetAddress + ", port=" + i);
        this.mDomain = inetAddress.getHostName() + QuickSettingConstants.JOINER + i;
        invoke(getMethod("connect1", mSuperClass, MqttServiceConstants.CONNECT_ACTION, InetAddress.class, Integer.TYPE), true, this.mDelegate, inetAddress, Integer.valueOf(i));
        this.mCounter.increaseRequest(this.mDomain, 0L);
    }

    @Override // java.net.SocketImpl
    protected void connect(SocketAddress socketAddress, int i) throws IOException {
        LogUtils.d(TAG, "SocketAddr=" + socketAddress);
        this.mDomain = socketAddress + "";
        invoke(getMethod("connect2", mSuperClass, MqttServiceConstants.CONNECT_ACTION, SocketAddress.class, Integer.TYPE), true, this.mDelegate, socketAddress, Integer.valueOf(i));
        this.mCounter.increaseRequest(this.mDomain, 0L);
    }

    @Override // java.net.SocketImpl
    protected void create(boolean z) throws IOException {
        invoke(getMethod("create", mSuperClass, "create", Boolean.TYPE), true, this.mDelegate, Boolean.valueOf(z));
        syncFd();
    }

    @Override // java.net.SocketImpl
    protected InputStream getInputStream() throws IOException {
        InputStream inputStream = (InputStream) invoke(getMethod("getInputStream", mSuperClass, "getInputStream", new Class[0]), false, this.mDelegate, new Object[0]);
        SocketCounter.getInstance().increaseSucceedWithSize(this.mDomain, 0L);
        if (inputStream == null) {
            return null;
        }
        return new CountingInputStream(this.mCollector, inputStream).setStatisticCounter(this.mCounter);
    }

    @Override // java.net.SocketImpl
    protected OutputStream getOutputStream() throws IOException {
        OutputStream outputStream = (OutputStream) invoke(getMethod("getOutputStream", mSuperClass, "getOutputStream", new Class[0]), false, this.mDelegate, new Object[0]);
        if (outputStream == null) {
            return null;
        }
        return new CountingOutputStream(this.mCollector, outputStream).setStatisticCounter(this.mCounter);
    }

    @Override // java.net.SocketImpl
    protected void listen(int i) throws IOException {
        invoke(getMethod("listen", mSuperClass, "listen", Integer.TYPE), true, this.mDelegate, Integer.valueOf(i));
    }

    @Override // java.net.SocketImpl
    protected void sendUrgentData(int i) throws IOException {
        invoke(getMethod("sendUrgentData", mSuperClass, "sendUrgentData", Integer.TYPE), true, this.mDelegate, Integer.valueOf(i));
    }

    @Override // java.net.SocketOptions
    public Object getOption(int i) throws SocketException {
        return this.mDelegate.getOption(i);
    }

    @Override // java.net.SocketOptions
    public void setOption(int i, Object obj) throws SocketException {
        syncFd();
        this.mDelegate.setOption(i, obj);
    }

    private <T> T getValue(Field field, Object obj, T t) {
        if (field != null) {
            try {
                return (T) field.get(obj);
            } catch (Exception e) {
                LogUtils.d(TAG, e);
            }
        }
        return t;
    }

    private <T> void setValue(Field field, Object obj, Object obj2) {
        if (field != null) {
            try {
                field.set(obj, obj2);
            } catch (Exception e) {
                LogUtils.d(TAG, e);
            }
        }
    }

    private <T> T invoke(Method method, boolean z, Object obj, Object... objArr) throws IOException {
        if (method == null) {
            return null;
        }
        try {
            if (z) {
                method.invoke(obj, objArr);
                return null;
            }
            return (T) method.invoke(obj, objArr);
        } catch (Exception e) {
            Throwable cause = e.getCause();
            if (cause instanceof IOException) {
                throw ((IOException) cause);
            }
            throw new IOException(e.toString());
        }
    }
}
