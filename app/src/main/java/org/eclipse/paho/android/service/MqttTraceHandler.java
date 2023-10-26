package org.eclipse.paho.android.service;

/* loaded from: classes3.dex */
public interface MqttTraceHandler {
    void traceDebug(String tag, String message);

    void traceError(String tag, String message);

    void traceException(String tag, String message, Exception e);
}
