package com.xiaopeng.lib.http.tls;

import android.content.Context;
import android.os.Build;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingConstants;
import com.xiaopeng.lib.security.xmartv1.XmartV1Constants;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.TlsVersion;

/* loaded from: classes2.dex */
public class SSLHelper {
    private static ISSLSocketWrapper mWrapper;
    private static final boolean sUseLocalCertFile;

    /* loaded from: classes2.dex */
    public interface ISSLSocketWrapper {
        Socket createSocket(Socket socket, String str) throws IOException;
    }

    static {
        sUseLocalCertFile = Build.VERSION.SDK_INT == 19;
    }

    public static List<ConnectionSpec> getConnectionSpecs() {
        ConnectionSpec build = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS).tlsVersions(TlsVersion.TLS_1_2).cipherSuites(CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256).build();
        ArrayList arrayList = new ArrayList();
        arrayList.add(build);
        arrayList.add(ConnectionSpec.COMPATIBLE_TLS);
        arrayList.add(ConnectionSpec.CLEARTEXT);
        return arrayList;
    }

    public static SSLSocketFactory getTLS2SocketFactory(Context context) {
        if (sUseLocalCertFile) {
            return LocalFileCert.getTLS2SocketFactory(context);
        }
        return KeyStoreCert.getTLS2SocketFactory();
    }

    public static X509TrustManager getX509TrustManager(Context context) {
        if (sUseLocalCertFile) {
            return LocalFileCert.getX509TrustManager(context);
        }
        return KeyStoreCert.getX509TrustManager();
    }

    /* loaded from: classes2.dex */
    public static class TLS2SocketFactory extends SSLSocketFactory {
        private final String[] TLS_V12_ONLY = {XmartV1Constants.TLS_REVISION_1_2};
        final SSLSocketFactory delegate;

        public TLS2SocketFactory(SSLSocketFactory sSLSocketFactory) {
            this.delegate = sSLSocketFactory;
        }

        @Override // javax.net.ssl.SSLSocketFactory
        public String[] getDefaultCipherSuites() {
            return this.delegate.getDefaultCipherSuites();
        }

        @Override // javax.net.ssl.SSLSocketFactory
        public String[] getSupportedCipherSuites() {
            return this.delegate.getSupportedCipherSuites();
        }

        @Override // javax.net.ssl.SSLSocketFactory
        public Socket createSocket(Socket socket, String str, int i, boolean z) throws IOException {
            return patch(this.delegate.createSocket(socket, str, i, z), str + QuickSettingConstants.JOINER + i);
        }

        @Override // javax.net.SocketFactory
        public Socket createSocket(String str, int i) throws IOException {
            return patch(this.delegate.createSocket(str, i), str + QuickSettingConstants.JOINER + i);
        }

        @Override // javax.net.SocketFactory
        public Socket createSocket(String str, int i, InetAddress inetAddress, int i2) throws IOException {
            return patch(this.delegate.createSocket(str, i, inetAddress, i2), str + QuickSettingConstants.JOINER + i);
        }

        @Override // javax.net.SocketFactory
        public Socket createSocket(InetAddress inetAddress, int i) throws IOException {
            return patch(this.delegate.createSocket(inetAddress, i), (inetAddress == null ? new StringBuilder() : new StringBuilder().append(inetAddress.getHostName())).append(QuickSettingConstants.JOINER).append(i).toString());
        }

        @Override // javax.net.SocketFactory
        public Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress2, int i2) throws IOException {
            return patch(this.delegate.createSocket(inetAddress, i, inetAddress2, i2), (inetAddress == null ? new StringBuilder() : new StringBuilder().append(inetAddress.getHostName())).append(QuickSettingConstants.JOINER).append(i).toString());
        }

        private Socket patch(Socket socket, String str) throws IOException {
            if (SSLHelper.mWrapper != null) {
                socket = SSLHelper.mWrapper.createSocket(socket, str);
            }
            if (socket instanceof SSLSocket) {
                ((SSLSocket) socket).setEnabledProtocols(this.TLS_V12_ONLY);
            }
            return socket;
        }
    }

    public static void setSSLSocketWrapper(ISSLSocketWrapper iSSLSocketWrapper) {
        mWrapper = iSSLSocketWrapper;
    }
}
