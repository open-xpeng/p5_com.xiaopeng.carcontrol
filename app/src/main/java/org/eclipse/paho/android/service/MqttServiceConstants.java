package org.eclipse.paho.android.service;

/* loaded from: classes3.dex */
interface MqttServiceConstants {
    public static final String CALLBACK_ACTION = "MqttService.callbackAction";
    public static final String CALLBACK_ACTIVITY_TOKEN = "MqttService.activityToken";
    public static final String CALLBACK_CLIENT_HANDLE = "MqttService.clientHandle";
    public static final String CALLBACK_DESTINATION_NAME = "MqttService.destinationName";
    public static final String CALLBACK_ERROR_MESSAGE = "MqttService.errorMessage";
    public static final String CALLBACK_ERROR_NUMBER = "MqttService.ERROR_NUMBER";
    public static final String CALLBACK_EXCEPTION = "MqttService.exception";
    public static final String CALLBACK_EXCEPTION_STACK = "MqttService.exceptionStack";
    public static final String CALLBACK_INVOCATION_CONTEXT = "MqttService.invocationContext";
    public static final String CALLBACK_MESSAGE_ID = "MqttService.messageId";
    public static final String CALLBACK_MESSAGE_PARCEL = "MqttService.PARCEL";
    public static final String CALLBACK_RECONNECT = "MqttService.reconnect";
    public static final String CALLBACK_SERVER_URI = "MqttService.serverURI";
    public static final String CALLBACK_STATUS = "MqttService.callbackStatus";
    public static final String CALLBACK_TO_ACTIVITY = "MqttService.callbackToActivity.v0";
    public static final String CALLBACK_TRACE_ID = "MqttService.traceId";
    public static final String CALLBACK_TRACE_SEVERITY = "MqttService.traceSeverity";
    public static final String CALLBACK_TRACE_TAG = "MqttService.traceTag";
    public static final String CLIENT_HANDLE = "clientHandle";
    public static final String CONNECT_ACTION = "connect";
    public static final String CONNECT_EXTENDED_ACTION = "connectExtended";
    public static final String DESTINATION_NAME = "destinationName";
    public static final String DISCONNECT_ACTION = "disconnect";
    public static final String DUPLICATE = "duplicate";
    public static final String MESSAGE_ARRIVED_ACTION = "messageArrived";
    public static final String MESSAGE_DELIVERED_ACTION = "messageDelivered";
    public static final String MESSAGE_ID = "messageId";
    public static final int NON_MQTT_EXCEPTION = -1;
    public static final String ON_CONNECTION_LOST_ACTION = "onConnectionLost";
    public static final String PAYLOAD = "payload";
    public static final String PING_SENDER = "MqttService.pingSender.";
    public static final String PING_WAKELOCK = "MqttService.client.";
    public static final String QOS = "qos";
    public static final String RETAINED = "retained";
    public static final String SEND_ACTION = "send";
    public static final String SUBSCRIBE_ACTION = "subscribe";
    public static final String TRACE_ACTION = "trace";
    public static final String TRACE_DEBUG = "debug";
    public static final String TRACE_ERROR = "error";
    public static final String TRACE_EXCEPTION = "exception";
    public static final String UNSUBSCRIBE_ACTION = "unsubscribe";
    public static final String VERSION = "v0";
    public static final String WAKELOCK_NETWORK_INTENT = "MqttService";
}
