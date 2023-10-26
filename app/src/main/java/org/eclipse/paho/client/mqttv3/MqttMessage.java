package org.eclipse.paho.client.mqttv3;

import java.util.Objects;

/* loaded from: classes3.dex */
public class MqttMessage {
    private int messageId;
    private byte[] payload;
    private boolean mutable = true;
    private int qos = 1;
    private boolean retained = false;
    private boolean dup = false;

    public static void validateQos(int i) {
        if (i < 0 || i > 2) {
            throw new IllegalArgumentException();
        }
    }

    public MqttMessage() {
        setPayload(new byte[0]);
    }

    public MqttMessage(byte[] bArr) {
        setPayload(bArr);
    }

    public byte[] getPayload() {
        return this.payload;
    }

    public void clearPayload() {
        checkMutable();
        this.payload = new byte[0];
    }

    public void setPayload(byte[] bArr) {
        checkMutable();
        Objects.requireNonNull(bArr);
        this.payload = bArr;
    }

    public boolean isRetained() {
        return this.retained;
    }

    public void setRetained(boolean z) {
        checkMutable();
        this.retained = z;
    }

    public int getQos() {
        return this.qos;
    }

    public void setQos(int i) {
        checkMutable();
        validateQos(i);
        this.qos = i;
    }

    public String toString() {
        return new String(this.payload);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setMutable(boolean z) {
        this.mutable = z;
    }

    protected void checkMutable() throws IllegalStateException {
        if (!this.mutable) {
            throw new IllegalStateException();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setDuplicate(boolean z) {
        this.dup = z;
    }

    public boolean isDuplicate() {
        return this.dup;
    }

    public void setId(int i) {
        this.messageId = i;
    }

    public int getId() {
        return this.messageId;
    }
}
