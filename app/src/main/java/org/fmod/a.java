package org.fmod;

import android.media.AudioRecord;
import android.util.Log;
import java.nio.ByteBuffer;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public final class a implements Runnable {
    private final FMODAudioDevice a;
    private final ByteBuffer b;
    private final int c;
    private final int d;
    private final int e = 2;
    private volatile Thread f;
    private volatile boolean g;
    private AudioRecord h;
    private boolean i;

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(FMODAudioDevice fMODAudioDevice, int i, int i2) {
        this.a = fMODAudioDevice;
        this.c = i;
        this.d = i2;
        this.b = ByteBuffer.allocateDirect(AudioRecord.getMinBufferSize(i, i2, 2));
    }

    private void d() {
        AudioRecord audioRecord = this.h;
        if (audioRecord != null) {
            if (audioRecord.getState() == 1) {
                this.h.stop();
            }
            this.h.release();
            this.h = null;
        }
        this.b.position(0);
        this.i = false;
    }

    public final int a() {
        return this.b.capacity();
    }

    public final void b() {
        if (this.f != null) {
            c();
        }
        this.g = true;
        this.f = new Thread(this);
        this.f.start();
    }

    public final void c() {
        while (this.f != null) {
            this.g = false;
            try {
                this.f.join();
                this.f = null;
            } catch (InterruptedException unused) {
            }
        }
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = 3;
        while (this.g) {
            if (!this.i && i > 0) {
                d();
                AudioRecord audioRecord = new AudioRecord(1, this.c, this.d, this.e, this.b.capacity());
                this.h = audioRecord;
                boolean z = audioRecord.getState() == 1;
                this.i = z;
                if (z) {
                    this.b.position(0);
                    this.h.startRecording();
                    i = 3;
                } else {
                    Log.e("FMOD", "AudioRecord failed to initialize (status " + this.h.getState() + ")");
                    i--;
                    d();
                }
            }
            if (this.i && this.h.getRecordingState() == 3) {
                AudioRecord audioRecord2 = this.h;
                ByteBuffer byteBuffer = this.b;
                this.a.fmodProcessMicData(this.b, audioRecord2.read(byteBuffer, byteBuffer.capacity()));
                this.b.position(0);
            }
        }
        d();
    }
}
