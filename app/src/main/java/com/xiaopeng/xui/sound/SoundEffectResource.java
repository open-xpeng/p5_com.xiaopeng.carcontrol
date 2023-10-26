package com.xiaopeng.xui.sound;

/* loaded from: classes2.dex */
class SoundEffectResource {
    private int location;
    private String path;
    private int resId;
    private int streamType;

    /* JADX INFO: Access modifiers changed from: package-private */
    public SoundEffectResource(String str, int i, int i2) {
        this.path = str;
        this.location = i;
        this.streamType = i2;
    }

    SoundEffectResource(int i, int i2) {
        this.resId = i;
        this.location = 2;
        this.streamType = i2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public String getPath() {
        return this.path;
    }

    int getResId() {
        return this.resId;
    }

    void setResId(int i) {
        this.resId = i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getLocation() {
        return this.location;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getStreamType() {
        return this.streamType;
    }
}
