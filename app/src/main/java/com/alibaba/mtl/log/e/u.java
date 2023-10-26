package com.alibaba.mtl.log.e;

import android.net.SSLCertificateSocketFactory;
import android.os.Build;
import com.lzy.okgo.cookie.SerializableCookie;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.Socket;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/* compiled from: UtSslSocketFactory.java */
/* loaded from: classes.dex */
public class u extends SSLSocketFactory {
    private String ag;
    private Method b = null;

    @Override // javax.net.SocketFactory
    public Socket createSocket() throws IOException {
        return null;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String str, int i) throws IOException {
        return null;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String str, int i, InetAddress inetAddress, int i2) throws IOException {
        return null;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress inetAddress, int i) throws IOException {
        return null;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress2, int i2) throws IOException {
        return null;
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getDefaultCipherSuites() {
        return new String[0];
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getSupportedCipherSuites() {
        return new String[0];
    }

    public u(String str) {
        this.ag = str;
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public Socket createSocket(Socket socket, String str, int i, boolean z) throws IOException {
        if (this.ag == null) {
            this.ag = str;
        }
        i.a("", SerializableCookie.HOST, this.ag, "port", Integer.valueOf(i), "autoClose", Boolean.valueOf(z));
        InetAddress inetAddress = socket.getInetAddress();
        if (z) {
            socket.close();
        }
        SSLCertificateSocketFactory sSLCertificateSocketFactory = (SSLCertificateSocketFactory) SSLCertificateSocketFactory.getDefault(0);
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("X509");
            trustManagerFactory.init((KeyStore) null);
            sSLCertificateSocketFactory.setTrustManagers(new TrustManager[]{new a(trustManagerFactory.getTrustManagers()[0])});
        } catch (Exception e) {
            i.a("", e);
        }
        SSLSocket sSLSocket = (SSLSocket) sSLCertificateSocketFactory.createSocket(inetAddress, i);
        sSLSocket.setEnabledProtocols(sSLSocket.getSupportedProtocols());
        if (Build.VERSION.SDK_INT >= 17) {
            sSLCertificateSocketFactory.setHostname(sSLSocket, this.ag);
        } else {
            try {
                if (this.b == null) {
                    Method method = sSLSocket.getClass().getMethod("setHostname", String.class);
                    this.b = method;
                    method.setAccessible(true);
                }
                this.b.invoke(sSLSocket, this.ag);
            } catch (Exception e2) {
                i.a("", "SNI not useable", null, e2);
            }
        }
        i.a("", "SSLSession PeerHost", sSLSocket.getSession().getPeerHost());
        return sSLSocket;
    }

    /* compiled from: UtSslSocketFactory.java */
    /* loaded from: classes.dex */
    public static class a implements X509TrustManager {
        TrustManager a;

        @Override // javax.net.ssl.X509TrustManager
        public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
        }

        @Override // javax.net.ssl.X509TrustManager
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public a(TrustManager trustManager) {
            this.a = trustManager;
        }

        /* JADX WARN: Removed duplicated region for block: B:8:0x0010  */
        @Override // javax.net.ssl.X509TrustManager
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void checkServerTrusted(java.security.cert.X509Certificate[] r2, java.lang.String r3) throws java.security.cert.CertificateException {
            /*
                r1 = this;
                javax.net.ssl.TrustManager r0 = r1.a     // Catch: java.security.cert.CertificateException -> L8
                javax.net.ssl.X509TrustManager r0 = (javax.net.ssl.X509TrustManager) r0     // Catch: java.security.cert.CertificateException -> L8
                r0.checkServerTrusted(r2, r3)     // Catch: java.security.cert.CertificateException -> L8
                return
            L8:
                r2 = move-exception
                r3 = r2
            La:
                java.lang.Throwable r0 = r3.getCause()
                if (r0 == 0) goto L28
                java.lang.Throwable r3 = r3.getCause()
                boolean r0 = r3 instanceof java.security.cert.CertificateExpiredException
                if (r0 != 0) goto L1c
                boolean r0 = r3 instanceof java.security.cert.CertificateNotYetValidException
                if (r0 == 0) goto La
            L1c:
                r2 = 1
                java.lang.Object[] r2 = new java.lang.Object[r2]
                r0 = 0
                r2[r0] = r3
                java.lang.String r3 = ""
                com.alibaba.mtl.log.e.i.a(r3, r2)
                return
            L28:
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.alibaba.mtl.log.e.u.a.checkServerTrusted(java.security.cert.X509Certificate[], java.lang.String):void");
        }
    }
}
