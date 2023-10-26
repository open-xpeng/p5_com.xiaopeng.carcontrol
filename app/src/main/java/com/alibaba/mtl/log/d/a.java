package com.alibaba.mtl.log.d;

import com.alibaba.mtl.log.e.i;
import com.alibaba.mtl.log.e.r;
import com.xiaopeng.speech.common.SpeechConstant;
import java.util.Random;

/* compiled from: UploadEngine.java */
/* loaded from: classes.dex */
public class a {
    static a a = new a();
    private int A;
    protected long z = com.alibaba.mtl.log.a.a.a();
    private boolean F = false;

    public static a a() {
        return a;
    }

    public synchronized void start() {
        this.F = true;
        if (r.a().b(2)) {
            r.a().f(2);
        }
        c();
        Random random = new Random();
        if (!b.isRunning()) {
            r.a().a(2, new b() { // from class: com.alibaba.mtl.log.d.a.1
                @Override // com.alibaba.mtl.log.d.b
                public void J() {
                    if (a.this.F) {
                        com.alibaba.mtl.log.b.a.D();
                        a.this.c();
                        i.a("UploadTask", "mPeriod:", Long.valueOf(a.this.z));
                        if (r.a().b(2)) {
                            r.a().f(2);
                        }
                        if (b.isRunning()) {
                            return;
                        }
                        r.a().a(2, this, a.this.z);
                    }
                }

                @Override // com.alibaba.mtl.log.d.b
                public void K() {
                    a.this.I();
                }
            }, random.nextInt((int) this.z));
        }
    }

    public void I() {
        if (this.A == 0) {
            this.A = SpeechConstant.VAD_TIMEOUT;
        } else {
            this.A = 0;
        }
    }

    public synchronized void stop() {
        this.F = false;
        r.a().f(2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long c() {
        long a2;
        int i;
        i.a("UploadEngine", "UTDC.bBackground:", Boolean.valueOf(com.alibaba.mtl.log.a.o), "AppInfoUtil.isForeground(UTDC.getContext()) ", Boolean.valueOf(com.alibaba.mtl.log.e.b.b(com.alibaba.mtl.log.a.getContext())));
        com.alibaba.mtl.log.a.o = !com.alibaba.mtl.log.e.b.b(com.alibaba.mtl.log.a.getContext());
        boolean z = com.alibaba.mtl.log.a.o;
        com.alibaba.mtl.log.a.a.a();
        if (z) {
            a2 = com.alibaba.mtl.log.a.a.b();
            i = this.A;
        } else {
            a2 = com.alibaba.mtl.log.a.a.a();
            i = this.A;
        }
        this.z = a2 + i;
        if (com.alibaba.mtl.log.a.a.e()) {
            this.z = 3000L;
        }
        return this.z;
    }
}
