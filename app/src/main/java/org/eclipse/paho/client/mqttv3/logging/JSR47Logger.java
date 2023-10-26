package org.eclipse.paho.client.mqttv3.logging;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.MemoryHandler;

/* loaded from: classes3.dex */
public class JSR47Logger implements Logger {
    private java.util.logging.Logger julLogger = null;
    private ResourceBundle logMessageCatalog = null;
    private ResourceBundle traceMessageCatalog = null;
    private String catalogID = null;
    private String resourceName = null;
    private String loggerName = null;

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void initialise(ResourceBundle resourceBundle, String str, String str2) {
        this.traceMessageCatalog = this.logMessageCatalog;
        this.resourceName = str2;
        this.loggerName = str;
        this.julLogger = java.util.logging.Logger.getLogger(str);
        this.logMessageCatalog = resourceBundle;
        this.traceMessageCatalog = resourceBundle;
        this.catalogID = resourceBundle.getString("0");
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void setResourceName(String str) {
        this.resourceName = str;
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public boolean isLoggable(int i) {
        return this.julLogger.isLoggable(mapJULLevel(i));
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void severe(String str, String str2, String str3) {
        log(1, str, str2, str3, null, null);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void severe(String str, String str2, String str3, Object[] objArr) {
        log(1, str, str2, str3, objArr, null);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void severe(String str, String str2, String str3, Object[] objArr, Throwable th) {
        log(1, str, str2, str3, objArr, th);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void warning(String str, String str2, String str3) {
        log(2, str, str2, str3, null, null);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void warning(String str, String str2, String str3, Object[] objArr) {
        log(2, str, str2, str3, objArr, null);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void warning(String str, String str2, String str3, Object[] objArr, Throwable th) {
        log(2, str, str2, str3, objArr, th);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void info(String str, String str2, String str3) {
        log(3, str, str2, str3, null, null);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void info(String str, String str2, String str3, Object[] objArr) {
        log(3, str, str2, str3, objArr, null);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void info(String str, String str2, String str3, Object[] objArr, Throwable th) {
        log(3, str, str2, str3, objArr, th);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void config(String str, String str2, String str3) {
        log(4, str, str2, str3, null, null);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void config(String str, String str2, String str3, Object[] objArr) {
        log(4, str, str2, str3, objArr, null);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void config(String str, String str2, String str3, Object[] objArr, Throwable th) {
        log(4, str, str2, str3, objArr, th);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void log(int i, String str, String str2, String str3, Object[] objArr, Throwable th) {
        Level mapJULLevel = mapJULLevel(i);
        if (this.julLogger.isLoggable(mapJULLevel)) {
            logToJsr47(mapJULLevel, str, str2, this.catalogID, this.logMessageCatalog, str3, objArr, th);
        }
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void fine(String str, String str2, String str3) {
        trace(5, str, str2, str3, null, null);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void fine(String str, String str2, String str3, Object[] objArr) {
        trace(5, str, str2, str3, objArr, null);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void fine(String str, String str2, String str3, Object[] objArr, Throwable th) {
        trace(5, str, str2, str3, objArr, th);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void finer(String str, String str2, String str3) {
        trace(6, str, str2, str3, null, null);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void finer(String str, String str2, String str3, Object[] objArr) {
        trace(6, str, str2, str3, objArr, null);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void finer(String str, String str2, String str3, Object[] objArr, Throwable th) {
        trace(6, str, str2, str3, objArr, th);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void finest(String str, String str2, String str3) {
        trace(7, str, str2, str3, null, null);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void finest(String str, String str2, String str3, Object[] objArr) {
        trace(7, str, str2, str3, objArr, null);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void finest(String str, String str2, String str3, Object[] objArr, Throwable th) {
        trace(7, str, str2, str3, objArr, th);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void trace(int i, String str, String str2, String str3, Object[] objArr, Throwable th) {
        Level mapJULLevel = mapJULLevel(i);
        if (this.julLogger.isLoggable(mapJULLevel)) {
            logToJsr47(mapJULLevel, str, str2, this.catalogID, this.traceMessageCatalog, str3, objArr, th);
        }
    }

    private String getResourceMessage(ResourceBundle resourceBundle, String str) {
        try {
            return resourceBundle.getString(str);
        } catch (MissingResourceException unused) {
            return str;
        }
    }

    private void logToJsr47(Level level, String str, String str2, String str3, ResourceBundle resourceBundle, String str4, Object[] objArr, Throwable th) {
        if (str4.indexOf("=====") == -1) {
            str4 = MessageFormat.format(getResourceMessage(resourceBundle, str4), objArr);
        }
        LogRecord logRecord = new LogRecord(level, String.valueOf(this.resourceName) + ": " + str4);
        logRecord.setSourceClassName(str);
        logRecord.setSourceMethodName(str2);
        logRecord.setLoggerName(this.loggerName);
        if (th != null) {
            logRecord.setThrown(th);
        }
        this.julLogger.log(logRecord);
    }

    private Level mapJULLevel(int i) {
        switch (i) {
            case 1:
                return Level.SEVERE;
            case 2:
                return Level.WARNING;
            case 3:
                return Level.INFO;
            case 4:
                return Level.CONFIG;
            case 5:
                return Level.FINE;
            case 6:
                return Level.FINER;
            case 7:
                return Level.FINEST;
            default:
                return null;
        }
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public String formatMessage(String str, Object[] objArr) {
        try {
            return this.logMessageCatalog.getString(str);
        } catch (MissingResourceException unused) {
            return str;
        }
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void dumpTrace() {
        dumpMemoryTrace47(this.julLogger);
    }

    protected static void dumpMemoryTrace47(java.util.logging.Logger logger) {
        if (logger != null) {
            Handler[] handlers = logger.getHandlers();
            for (int i = 0; i < handlers.length; i++) {
                if (handlers[i] instanceof MemoryHandler) {
                    synchronized (handlers[i]) {
                        ((MemoryHandler) handlers[i]).push();
                    }
                    return;
                }
            }
            dumpMemoryTrace47(logger.getParent());
        }
    }
}
