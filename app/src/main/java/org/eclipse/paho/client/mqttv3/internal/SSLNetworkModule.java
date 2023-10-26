package org.eclipse.paho.client.mqttv3.internal;

import com.xiaopeng.carcontrol.quicksetting.QuickSettingConstants;
import java.io.IOException;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

/* loaded from: classes3.dex */
public class SSLNetworkModule extends TCPNetworkModule {
    private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.internal.SSLNetworkModule";
    private static final Logger log = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, SSLNetworkModule.class.getName());
    private String[] enabledCiphers;
    private int handshakeTimeoutSecs;
    private String host;
    private HostnameVerifier hostnameVerifier;
    private int port;

    public SSLNetworkModule(SSLSocketFactory sSLSocketFactory, String str, int i, String str2) {
        super(sSLSocketFactory, str, i, str2);
        this.host = str;
        this.port = i;
        log.setResourceName(str2);
    }

    public String[] getEnabledCiphers() {
        return this.enabledCiphers;
    }

    public void setEnabledCiphers(String[] strArr) {
        this.enabledCiphers = strArr;
        if (this.socket == null || strArr == null) {
            return;
        }
        if (log.isLoggable(5)) {
            String str = "";
            for (int i = 0; i < strArr.length; i++) {
                if (i > 0) {
                    str = String.valueOf(str) + ",";
                }
                str = String.valueOf(str) + strArr[i];
            }
            log.fine(CLASS_NAME, "setEnabledCiphers", "260", new Object[]{str});
        }
        ((SSLSocket) this.socket).setEnabledCipherSuites(strArr);
    }

    public void setSSLhandshakeTimeout(int i) {
        super.setConnectTimeout(i);
        this.handshakeTimeoutSecs = i;
    }

    public HostnameVerifier getSSLHostnameVerifier() {
        return this.hostnameVerifier;
    }

    public void setSSLHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.TCPNetworkModule, org.eclipse.paho.client.mqttv3.internal.NetworkModule
    public void start() throws IOException, MqttException {
        super.start();
        setEnabledCiphers(this.enabledCiphers);
        int soTimeout = this.socket.getSoTimeout();
        this.socket.setSoTimeout(this.handshakeTimeoutSecs * 1000);
        ((SSLSocket) this.socket).startHandshake();
        if (this.hostnameVerifier != null) {
            this.hostnameVerifier.verify(this.host, ((SSLSocket) this.socket).getSession());
        }
        this.socket.setSoTimeout(soTimeout);
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.TCPNetworkModule, org.eclipse.paho.client.mqttv3.internal.NetworkModule
    public String getServerURI() {
        return "ssl://" + this.host + QuickSettingConstants.JOINER + this.port;
    }
}
