package com.xiaopeng.lib.framework.netchannelmodule.messaging.events;

import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.IEvent;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.IMessaging;
import com.xiaopeng.lib.framework.netchannelmodule.common.BackgroundHandler;
import org.greenrobot.eventbus.EventBus;

/* loaded from: classes2.dex */
public class EventSender {
    private static final String THREAD_NAME = "EventSender";
    private static BackgroundHandler mHandler;
    private static volatile EventSender sCurrentSender;
    private IMessaging.CHANNEL mChannel;

    public static EventSender getCurrent() {
        return sCurrentSender;
    }

    public EventSender(IMessaging.CHANNEL channel) {
        this.mChannel = channel;
        sCurrentSender = this;
        mHandler = new BackgroundHandler(THREAD_NAME);
    }

    public void changeChannel(IMessaging.CHANNEL channel) {
        this.mChannel = channel;
    }

    public void postDeliveryCompleted(long j, byte[] bArr) {
        post(new ReportingEvent(IEvent.TYPE.DELIVERY_COMPLETED).channel(this.mChannel).messageId(j).message(bArr));
    }

    public void postDeliveryCompleted(long j) {
        post(new ReportingEvent(IEvent.TYPE.DELIVERY_COMPLETED).channel(this.mChannel).messageId(j));
    }

    public void postDeliveryFailure(long j, int i) {
        post(new ReportingEvent(IEvent.TYPE.DELIVERY_FAILURE).channel(this.mChannel).messageId(j).reasonCode(i));
    }

    public void postReceivedMessage(String str, byte[] bArr) {
        postAtFront(new ReceivedMessageEvent(IEvent.TYPE.RECEIVED_MESSAGE).channel(this.mChannel).topic(str).message(bArr));
    }

    public void postConnected(String str) {
        postAtFront(new ConnectionEvent(IEvent.TYPE.CONNECTED).channel(this.mChannel).serverUri(str));
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0036  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void postDisconnected(java.lang.Throwable r6) {
        /*
            r5 = this;
            r0 = 0
            if (r6 == 0) goto L21
            java.lang.String r1 = r6.getMessage()
            boolean r2 = r6 instanceof org.eclipse.paho.client.mqttv3.MqttException
            if (r2 == 0) goto L13
            r0 = 1
            org.eclipse.paho.client.mqttv3.MqttException r6 = (org.eclipse.paho.client.mqttv3.MqttException) r6
            int r6 = r6.getReasonCode()
            goto L23
        L13:
            boolean r2 = r6 instanceof com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.MessagingException
            if (r2 == 0) goto L22
            com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.MessagingException r6 = (com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.MessagingException) r6
            int r6 = r6.getReasonCode()
            r4 = r0
            r0 = r6
            r6 = r4
            goto L23
        L21:
            r1 = 0
        L22:
            r6 = r0
        L23:
            com.xiaopeng.lib.framework.netchannelmodule.messaging.events.ConnectionEvent r2 = new com.xiaopeng.lib.framework.netchannelmodule.messaging.events.ConnectionEvent
            com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.IEvent$TYPE r3 = com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.IEvent.TYPE.DISCONNECTED
            r2.<init>(r3)
            com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.IMessaging$CHANNEL r3 = r5.mChannel
            com.xiaopeng.lib.framework.netchannelmodule.messaging.events.ConnectionEvent r2 = r2.channel(r3)
            com.xiaopeng.lib.framework.netchannelmodule.messaging.events.ConnectionEvent r0 = r2.reasonCode(r0)
            if (r1 != 0) goto L38
            java.lang.String r1 = ""
        L38:
            com.xiaopeng.lib.framework.netchannelmodule.messaging.events.ConnectionEvent r0 = r0.serverUri(r1)
            com.xiaopeng.lib.framework.netchannelmodule.messaging.events.ConnectionEvent r6 = r0.protocolReasonCode(r6)
            r5.postAtFront(r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.lib.framework.netchannelmodule.messaging.events.EventSender.postDisconnected(java.lang.Throwable):void");
    }

    public void postSubscribed(String str) {
        post(new SubscribeEvent(IEvent.TYPE.SUBSCRIBED).channel(this.mChannel).topic(str));
    }

    public void postUnsubscribed() {
        post(new SubscribeEvent(IEvent.TYPE.UNSUBSCRIBED).channel(this.mChannel));
    }

    public void postTracingLog(String str) {
        post(new TracingEvent().channel(this.mChannel).message(str));
    }

    public void close() {
        mHandler.stop();
    }

    private void post(final IEvent iEvent) {
        mHandler.post(new Runnable() { // from class: com.xiaopeng.lib.framework.netchannelmodule.messaging.events.EventSender.1
            @Override // java.lang.Runnable
            public void run() {
                EventBus.getDefault().post(iEvent);
            }
        });
    }

    private void postAtFront(final IEvent iEvent) {
        mHandler.postAtFront(new Runnable() { // from class: com.xiaopeng.lib.framework.netchannelmodule.messaging.events.EventSender.2
            @Override // java.lang.Runnable
            public void run() {
                EventBus.getDefault().post(iEvent);
            }
        });
    }
}
