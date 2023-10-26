package com.xiaopeng.lib.framework.netchannelmodule.http.traffic.tls;

import android.os.Build;
import com.xiaopeng.lib.http.HttpsUtils;
import com.xiaopeng.lib.http.tls.SSLHelper;
import com.xiaopeng.lib.utils.LogUtils;
import java.io.IOException;
import java.net.Socket;
import javax.net.ssl.SSLSocket;

/* loaded from: classes2.dex */
public class CountingTLSSocketInstrument implements SSLHelper.ISSLSocketWrapper {
    public static synchronized void initialize() {
        synchronized (CountingTLSSocketInstrument.class) {
            HttpsUtils.setSSLSocketWrapper(new CountingTLSSocketInstrument());
        }
    }

    public static synchronized void reset() {
        synchronized (CountingTLSSocketInstrument.class) {
            HttpsUtils.setSSLSocketWrapper(null);
        }
    }

    @Override // com.xiaopeng.lib.http.tls.SSLHelper.ISSLSocketWrapper
    public Socket createSocket(Socket socket, String str) throws IOException {
        try {
        } catch (Exception e) {
            LogUtils.d("hook tls socket failed for " + e);
        }
        if (isKitkat()) {
            HookTLSKitkatSocket.createSocket(socket, str);
            return socket;
        }
        return new HookTLSPSocket((SSLSocket) socket, str);
    }

    private static boolean isKitkat() {
        return Build.VERSION.SDK_INT == 19;
    }
}
