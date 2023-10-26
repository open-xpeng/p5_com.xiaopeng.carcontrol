package org.eclipse.paho.client.mqttv3;

/* loaded from: classes3.dex */
public class DisconnectedBufferOptions {
    public static final boolean DELETE_OLDEST_MESSAGES_DEFAULT = false;
    public static final boolean DISCONNECTED_BUFFER_ENABLED_DEFAULT = false;
    public static final int DISCONNECTED_BUFFER_SIZE_DEFAULT = 5000;
    public static final boolean PERSIST_DISCONNECTED_BUFFER_DEFAULT = false;
    private int bufferSize = 5000;
    private boolean bufferEnabled = false;
    private boolean persistBuffer = false;
    private boolean deleteOldestMessages = false;

    public int getBufferSize() {
        return this.bufferSize;
    }

    public void setBufferSize(int i) {
        if (i < 1) {
            throw new IllegalArgumentException();
        }
        this.bufferSize = i;
    }

    public boolean isBufferEnabled() {
        return this.bufferEnabled;
    }

    public void setBufferEnabled(boolean z) {
        this.bufferEnabled = z;
    }

    public boolean isPersistBuffer() {
        return this.persistBuffer;
    }

    public void setPersistBuffer(boolean z) {
        this.persistBuffer = z;
    }

    public boolean isDeleteOldestMessages() {
        return this.deleteOldestMessages;
    }

    public void setDeleteOldestMessages(boolean z) {
        this.deleteOldestMessages = z;
    }
}
