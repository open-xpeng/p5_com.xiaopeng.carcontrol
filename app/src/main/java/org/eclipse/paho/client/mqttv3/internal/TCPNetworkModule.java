package org.eclipse.paho.client.mqttv3.internal;

import com.xiaopeng.carcontrol.quicksetting.QuickSettingConstants;
import com.xiaopeng.libconfig.ipc.AccountConfig;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

/* loaded from: classes3.dex */
public class TCPNetworkModule implements NetworkModule {
    private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.internal.TCPNetworkModule";
    private static final Logger log = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, TCPNetworkModule.class.getName());
    private int conTimeout;
    private SocketFactory factory;
    private String host;
    private int port;
    protected Socket socket;

    public TCPNetworkModule(SocketFactory socketFactory, String str, int i, String str2) {
        log.setResourceName(str2);
        this.factory = socketFactory;
        this.host = str;
        this.port = i;
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.NetworkModule
    public void start() throws IOException, MqttException {
        try {
            log.fine(CLASS_NAME, AccountConfig.FaceIDRegisterAction.STATUS_START, "252", new Object[]{this.host, new Integer(this.port), new Long(this.conTimeout * 1000)});
            InetSocketAddress inetSocketAddress = new InetSocketAddress(this.host, this.port);
            SocketFactory socketFactory = this.factory;
            if (socketFactory instanceof SSLSocketFactory) {
                Socket socket = new Socket();
                socket.connect(inetSocketAddress, this.conTimeout * 1000);
                this.socket = ((SSLSocketFactory) this.factory).createSocket(socket, this.host, this.port, true);
                return;
            }
            Socket createSocket = socketFactory.createSocket();
            this.socket = createSocket;
            createSocket.connect(inetSocketAddress, this.conTimeout * 1000);
        } catch (ConnectException e) {
            log.fine(CLASS_NAME, AccountConfig.FaceIDRegisterAction.STATUS_START, "250", null, e);
            throw new MqttException(32103, e);
        }
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.NetworkModule
    public InputStream getInputStream() throws IOException {
        return this.socket.getInputStream();
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.NetworkModule
    public OutputStream getOutputStream() throws IOException {
        return this.socket.getOutputStream();
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.NetworkModule
    public void stop() throws IOException {
        Socket socket = this.socket;
        if (socket != null) {
            socket.shutdownInput();
            this.socket.close();
        }
    }

    public void setConnectTimeout(int i) {
        this.conTimeout = i;
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.NetworkModule
    public String getServerURI() {
        return "tcp://" + this.host + QuickSettingConstants.JOINER + this.port;
    }
}
