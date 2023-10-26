package com.xiaopeng.lib.framework.netchannelmodule.http.traffic.plain;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketImpl;

/* loaded from: classes2.dex */
public class EmptySocketImpl extends SocketImpl {
    @Override // java.net.SocketImpl
    protected void accept(SocketImpl socketImpl) throws IOException {
    }

    @Override // java.net.SocketImpl
    protected int available() throws IOException {
        return 0;
    }

    @Override // java.net.SocketImpl
    protected void bind(InetAddress inetAddress, int i) throws IOException {
    }

    @Override // java.net.SocketImpl
    protected void close() throws IOException {
    }

    @Override // java.net.SocketImpl
    protected void connect(String str, int i) throws IOException {
    }

    @Override // java.net.SocketImpl
    protected void connect(InetAddress inetAddress, int i) throws IOException {
    }

    @Override // java.net.SocketImpl
    protected void connect(SocketAddress socketAddress, int i) throws IOException {
    }

    @Override // java.net.SocketImpl
    protected void create(boolean z) throws IOException {
    }

    @Override // java.net.SocketImpl
    protected InputStream getInputStream() throws IOException {
        return null;
    }

    @Override // java.net.SocketOptions
    public Object getOption(int i) throws SocketException {
        return null;
    }

    @Override // java.net.SocketImpl
    protected OutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override // java.net.SocketImpl
    protected void listen(int i) throws IOException {
    }

    @Override // java.net.SocketImpl
    protected void sendUrgentData(int i) throws IOException {
    }

    @Override // java.net.SocketOptions
    public void setOption(int i, Object obj) throws SocketException {
    }
}
