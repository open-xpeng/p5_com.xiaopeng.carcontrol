package org.eclipse.paho.client.mqttv3.util;

import java.util.Enumeration;
import java.util.Properties;
import org.eclipse.paho.client.mqttv3.internal.ClientComms;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

/* loaded from: classes3.dex */
public class Debug {
    private static final String CLASS_NAME;
    private static final String lineSep;
    private static final Logger log;
    private static final String separator = "==============";
    private String clientID;
    private ClientComms comms;

    static {
        String name = ClientComms.class.getName();
        CLASS_NAME = name;
        log = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, name);
        lineSep = System.getProperty("line.separator", "\n");
    }

    public Debug(String str, ClientComms clientComms) {
        this.clientID = str;
        this.comms = clientComms;
        log.setResourceName(str);
    }

    public void dumpClientDebug() {
        dumpClientComms();
        dumpConOptions();
        dumpClientState();
        dumpBaseDebug();
    }

    public void dumpBaseDebug() {
        dumpVersion();
        dumpSystemProperties();
        dumpMemoryTrace();
    }

    protected void dumpMemoryTrace() {
        log.dumpTrace();
    }

    protected void dumpVersion() {
        StringBuffer stringBuffer = new StringBuffer();
        String str = lineSep;
        stringBuffer.append(String.valueOf(str) + separator + " Version Info " + separator + str);
        stringBuffer.append(String.valueOf(left("Version", 20, ' ')) + ":  " + ClientComms.VERSION + str);
        stringBuffer.append(String.valueOf(left("Build Level", 20, ' ')) + ":  " + ClientComms.BUILD_LEVEL + str);
        stringBuffer.append("==========================================" + str);
        log.fine(CLASS_NAME, "dumpVersion", stringBuffer.toString());
    }

    public void dumpSystemProperties() {
        log.fine(CLASS_NAME, "dumpSystemProperties", dumpProperties(System.getProperties(), "SystemProperties").toString());
    }

    public void dumpClientState() {
        ClientComms clientComms = this.comms;
        if (clientComms == null || clientComms.getClientState() == null) {
            return;
        }
        log.fine(CLASS_NAME, "dumpClientState", dumpProperties(this.comms.getClientState().getDebug(), String.valueOf(this.clientID) + " : ClientState").toString());
    }

    public void dumpClientComms() {
        ClientComms clientComms = this.comms;
        if (clientComms != null) {
            log.fine(CLASS_NAME, "dumpClientComms", dumpProperties(clientComms.getDebug(), String.valueOf(this.clientID) + " : ClientComms").toString());
        }
    }

    public void dumpConOptions() {
        ClientComms clientComms = this.comms;
        if (clientComms != null) {
            log.fine(CLASS_NAME, "dumpConOptions", dumpProperties(clientComms.getConOptions().getDebug(), String.valueOf(this.clientID) + " : Connect Options").toString());
        }
    }

    public static String dumpProperties(Properties properties, String str) {
        StringBuffer stringBuffer = new StringBuffer();
        Enumeration<?> propertyNames = properties.propertyNames();
        String str2 = lineSep;
        stringBuffer.append(String.valueOf(str2) + separator + " " + str + " " + separator + str2);
        while (propertyNames.hasMoreElements()) {
            String str3 = (String) propertyNames.nextElement();
            stringBuffer.append(String.valueOf(left(str3, 28, ' ')) + ":  " + properties.get(str3) + lineSep);
        }
        stringBuffer.append("==========================================" + lineSep);
        return stringBuffer.toString();
    }

    public static String left(String str, int i, char c) {
        if (str.length() >= i) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer(i);
        stringBuffer.append(str);
        int length = i - str.length();
        while (true) {
            length--;
            if (length >= 0) {
                stringBuffer.append(c);
            } else {
                return stringBuffer.toString();
            }
        }
    }
}
